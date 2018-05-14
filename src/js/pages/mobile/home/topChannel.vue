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

            this.channelList = CHANNELLIST
            this.dataReady = true
        },
        chooseChannel (index, channel) {
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
        changeRect (index) {
            const item = this.channelList[index]
            animation.transition(this.$refs.jcLine, {
                styles: {
                    width: item.width + 'px',
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
