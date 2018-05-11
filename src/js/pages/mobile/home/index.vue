<template>
    <div class="wrapper">
        <!-- <div class="status-bar"></div> -->
        <home-header></home-header>

        <suggest class="box" ref="page" @touchstart="ontouchstart"></suggest>
        <!-- <bmmask animation="center" position="center" class="mask" :duration="300" ref="channel" disableMaskEvent="true"> -->
        <top-channel @change="onchange" ref="topChannel" :activeIndex="activeIndex"></top-channel>
        <!-- </bmmask> -->
    </div>
</template>
<script>
import header from './header';
import suggest from './suggest';
import topChannel from './topChannel';
import { CHANNELLIST } from './config';

export default {
    components: {
        'home-header': header,
        'top-channel': topChannel,
        'suggest': suggest
    },
    created () {
        this.getChannel()
    },
    data () {
        return {
            channelList: [],
            activeIndex: 0,
            x: 0,
            isInAnimation: false,
            opacity: 1,
            gesToken: 0
        }
    },
    methods: {
        getChannel () {
            this.channelList = CHANNELLIST
        },
        onchange (index) {
            console.log(index);
            this.activeIndex = index;
            // dom.scrollToElement(this.$refs[`page_${index}`][0], { animated: false })
        },
        ontouchstart (event) {
            // 如果在执行动画，就不触发
            if (this.isInAnimation) return
            // 解绑动画
            if (this.gesToken != 0) {
                this.$bindingx.unbind({
                    eventType: 'pan',
                    token: this.gesToken
                })
                this.gesToken = 0
                // return
            }
            // 找到元素 注意多了一个.ref
            const boxRef = this.$refs.page.ref
            const gesTokenObj = this.$bindingx.bind({
                anchor: boxRef,
                eventType: 'pan',
                props: [
                    { element: boxRef, property: 'transform.translateX', expression: 'x+0' }, // 这里是表达式，需要有运算符号，不能只写x
                    { element: boxRef, property: 'opacity', expression: '1-abs(x)/600' }] // 我们这里期望 opacity 无论在拖动盒子向左向右的时候 都有渐隐 所有取绝对值 移动到绝对 600px 时候为全隐
            }, (e) => {
                if (e.state === 'end') {
                    // 拖动结束事件 记录当前坐标 和 透明度
                    this.x += e.deltaX
                    //  开始进行下一步 回弹还是滑出的 动画
                    this.bindTiming()
                }
            })
            // 记录下取消的token 多次 ontouchstart 时要带着 token 把上次的解绑
            this.gesToken = gesTokenObj.token
        },
        dismissCallback () {
            this.$notice.toast({
                message: '您已经删除了小卡片。'
            })
        },
        bindTiming () {
            // 开始执行动画
            this.isInAnimation = true

            const boxRef = this.$refs.page.ref
            // 改变的 x 坐标，最终的 x 坐标，最终的透明值，位移 x 原点的表达式
            let changed_x, final_x, final_opacity, translate_x_origin
            // 通过一个变量来判断是否已经滑出
            let shouldDismiss = false

            // 生成表达式逻辑
            if (this.x >= -750 / 2 && this.x <= 750 / 2) {
                // weex 设置宽度默认都是750px 往左拖动或者往右拖动盒子一半以内时
                shouldDismiss = false // 标记为不消失
                final_x = 0 // 回到原点
                changed_x = 0 - this.x // 计算出需要位置的值
                final_opacity = 1 // 透明度变回 1
                translate_x_origin = `easeOutElastic(t,${this.x},${changed_x},1000)` // 运动曲线为easeOutElastic 生成位移到原点的表达式 1s内执行
            } else if (this.x < -750 / 2) {
                // 往左拖动盒子超过一半时
                shouldDismiss = true // 标记为消失
                final_x = -750 // 完全把盒子位移到左边屏外面
                changed_x = -750 - this.x// 计算出需要位置的值
                final_opacity = 0 // 透明度变回 0
                translate_x_origin = `easeOutExpo(t,${this.x},${changed_x},1000)` // 运动曲线为easeOutExpo 生成位移到 -750px 表达式 1s内执行
            } else {
                // 往右拖动盒子超过一半时
                shouldDismiss = true // 标记为消失
                final_x = 750 // 完全把盒子位移到右边边屏外面
                changed_x = 750 - this.x // 计算出需要位置的值
                final_opacity = 0 // 透明度变回 0
                translate_x_origin = `easeOutExpo(t,${this.x},${changed_x},1000)` // 运动曲线为easeOutExpo 生成位移到 750px 表达式 1s内执行
            }

            //  运动曲线为linear 计算出透明度表达式 1s内执行
            const opacity_origin = `linear(t,${this.opacity},${final_opacity - this.opacity},1000)`
            const result = this.$bindingx.bind({
                eventType: 'timing', // 结束的时候是没有任何监听的 用 timing 来做定时的动画
                exitExpression: 't>1000', // 当时间超过 10000ms 结束动画
                props: [
                    { element: boxRef, property: 'transform.translateX', expression: translate_x_origin },
                    { element: boxRef, property: 'opacity', expression: opacity_origin }
                ]

            }, (e) => {
                if (e.state === 'end' || e.state === 'exit') {
                    // reset x
                    this.x = final_x
                    this.opacity = final_opacity
                    this.isInAnimation = false

                    shouldDismiss && this.dismissCallback()
                }
            })
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
