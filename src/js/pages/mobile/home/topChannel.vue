<template>
    <div class="wrapper">
        <scroller class="scroller" scroll-direction="horizontal" flex-direction="row" loadmoreoffset="0" show-scrollbar=false>
            <div class="j-uline" ref="jcLine"></div>
            <text class="i-c" v-for="(item,index) in channelList" :class="[ activeIndex == index ? 'c-act' : '']" @click="chooseChannel(index,item)" :ref="'channel_' + index">{{item.name}}</text>
        </scroller>
    </div>
</template>
<script>
import { CHANNELLIST } from './config'
const dom = weex.requireModule('dom')
const animation = weex.requireModule('animation')
export default {
    props: {
        activeIndex: {
            type: Number,
            default: 0
        }
    },
    watch: {
        activeIndex (newVal, oldVal) {
            const scrollIndex = newVal < 4 ? 0 : newVal - 4;
            this.changeRect(newVal)
            this.scrollToMenu(scrollIndex)
        }
    },
    created () {
        this.getChannel()
    },
    data () {
        return {
            channelList: [],
            dataReady: false
        }
    },
    methods: {
        getChannel () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getChannel',
            //     data: {}
            // }).then(resData => {
            //     this.channelList = resData.data
            //     this.row = Math.ceil(this.channelList.length / 4)
            //     this.panelHeight = 160 + 86 * (this.row - 1)
            //     this.dataReady = true
            // }, error => {

            // })
            this.$fetch({
                method: 'GET', // 大写
                name: 'category.list', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
                data: {}
            }).then(data => {
                this.channelList = [];
                let left = 0;
                for (const item of data) {
                    const width = item.fullName.length * 28
                    this.channelList.push({
                        name: item.fullName,
                        width: width,
                        left: left
                    })
                    left += 48 + item.fullName.length * 28
                }
                this.$notice.alert({
                    title: 'category',
                    message: JSON.stringify(this.channelList)
                });
                this.dataReady = true
            }, error => {
                this.$notice.toast(JSON.stringify(error));
            });
            // this.channelList = CHANNELLIST
        },
        chooseChannel (index, channel) {
            this.activeIndex = index
            const item = channel || this.channelList[index]
            animation.transition(this.$refs.jcLine, {
                styles: {
                    width: item.width + 'px',
                    transformOrigin: 'left center',
                    left: 'translateX(' + item.left + 'px)'
                },
                duration: 200,
                timingFunction: 'ease',
                delay: 0
            }, function () {});
            this.$emit('change', index)
        },
        changeRect (index) {
            const item = this.channelList[index]
            animation.transition(this.$refs.jcLine, {
                styles: {
                    width: item.width + 'px',
                    transformOrigin: 'left',
                    transform: 'translateX(' + item.left + 'px)'
                },
                duration: 200,
                timingFunction: 'ease',
                delay: 0
            }, function () {});
        },
        scrollToMenu (index) {
            dom.scrollToElement(this.$refs[`channel_${index}`][0])
        }
    }
}
</script>
<style lang="sass" src="./topChannel.scss"></style>
