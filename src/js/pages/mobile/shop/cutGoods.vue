<template>

    <div class="wrapper" @touchstart="ontouchstart" @touchmove="ontouchmove" @touchend="ontouchend"  >
        <topic-header ref="ref1" ></topic-header>

        <div style="background-color: white" >

            <text class="iiileft" @click="$router.back">&#xe6f6;</text>
            <text class="iiiright">&#xe6f1;</text>
        </div>

        <div class="mid">

            <div class="slogan">
                <text class="i-slg iconfont">&#xe63a; 免费包邮</text>
                <text class="i-slg iconfont">&#xe63a; 48小时发货</text>
                <text class="i-slg iconfont">&#xe63a; 7天退换</text>
                <text class="i-slg iconfont">&#xe63a; 假货必赔</text>
            </div>
        </div>


        <scroller class="main-list" @scroll="scrollHandler"   offset-accuracy="300px">





            <div style="width: 750px; background-color: rosybrown">
                <text v-for="txt in dectxt">
                    {{txt}}
                </text>
                <image v-for="i in decimg" :src="i" resize="cover" style="width:500px;height:500px"></image>
            </div>

            <div>


                <div class="bottom-div">
                    <!--<div >-->
                    <!--<text class="leftline"></text>-->
                    <!--</div>-->
                    <text class="bottom-head">砍价规则说明</text>
                    <!--<div class="rightline">-->
                    <!--<text style="letter-spacing: -10px">———————————</text>-->
                    <!--</div>-->

                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                </div>
            </div>
        </scroller>
    </div>

</template>
<script>
    import header from './header';
    import { WxcCell, WxcButton, WxcPopup } from 'weex-ui';
    const animation = weex.requireModule('animation');
    const axios = weex.requireModule('bmAxios');
    const globalEvent = weex.requireModule('globalEvent');

    // import block from './block';
    // import refresher from '../common/refresh';
    // import { GOODS } from './config'

    export default {
        components: {
            'topic-header': header,
            WxcCell, WxcButton, WxcPopup,
            // 'refresher': refresher,
            // 'block': block
        },
        data () {
            return {
                aa: ''
            }
        },
        created () {
            // this.popupOverlayBottomClick()
            this.androidFinishApp()
        },
        methods: {

            initData () {
            },
            openBottomPopup () {
                this.isBottomShow = true;
            },

            // 非状态组件，需要在这里关闭
            popupOverlayBottomClick () {
                this.isBottomShow = false;
            }
        }
    }
</script>
<style scoped>
    .frame {
        width: 750px;
        height: 700px;
        position: relative;
    }
    .iiileft{
        font-family: iconfont;
        color: white;
        font-size: 32px;
        background-color: rgba(0,0,0,.38);
        position: absolute;
        border-radius:24px ;
        top:80px;
        left:40px;
        width: 48px;
        line-height: 48px;
        text-align: center;
        align-items: center;
    }
    .iiiright{
        font-family: iconfont;
        color: white;
        font-size: 32px;
        background-color: rgba(0,0,0,.38);
        position: absolute;
        border-radius:24px ;
        top:80px;
        right:40px;
        width: 48px;
        line-height: 48px;
        text-align: center;
        align-items: center;
    }



</style>
