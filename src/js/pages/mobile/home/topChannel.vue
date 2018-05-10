<template>
    <div class="wrapper">
        <scroller class="scroller" scroll-direction="horizontal" flex-direction="row" loadmoreoffset="0" show-scrollbar=false>
            <div class="j-uline" ref="jcLine"></div>
            <text jact="true" class="i-c" v-for="(item,index) in channelList" :class="[ activeIndex == index ? 'c-act' : '']" @click="chooseChannel(index,item)" :ref="'channel_' + index">{{item.name}}</text>
        </scroller>
    </div>
</template>
<script>
import { CHANNELLIST } from './config'
const dom = weex.requireModule('dom');
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
            this.scrollToMenu(scrollIndex)
        },
        pullStatus (newVal) {
            const num = newVal ? 180 : 0
            const height = newVal ? 160 + 86 * (this.row - 1) : 0

            // animation.transition(this.$refs.panel, {
            //     styles: {
            //         height: height + 'px'
            //     },
            //     duration: 200,
            //     timingFunction: 'ease',
            //     delay: 0
            // }, function() {});

            animation.transition(this.$refs.more, {
                styles: {
                    transform: 'rotate(' + num + 'deg)'
                },
                duration: 200,
                timingFunction: 'ease',
                delay: 0
            }, function () {});
        }
    },
    created () {
        this.getChannel()
    },
    data () {
        return {
            channelList: [],
            row: 1,
            pullStatus: false,
            dataReady: false,
            panelHeight: 0
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

            this.channelList = CHANNELLIST
            this.row = Math.ceil(this.channelList.length / 4)
            this.panelHeight = 160 + 86 * (this.row - 1)
            this.dataReady = true
        },
        chooseChannel (index, channel) {
            this.pullStatus = false
            this.activeIndex = index
            const item = channel || this.channelList[index]
            animation.transition(this.$refs.jcLine, {
                styles: {
                    width: item.width + 'px',
                    transform: 'translateX(' + item.left + 'px)'
                },
                duration: 200,
                timingFunction: 'ease',
                delay: 0
            }, function () {});
            this.$emit('change', index)
        },
        scrollToMenu (index) {
            dom.scrollToElement(this.$refs[`channel_${index}`][0])
        },
        pull () {
            this.pullStatus = !this.pullStatus
        }
    }
}
</script>
<style lang="sass" src="./topChannel.scss"></style>
