<template>
    <div class="wrapper">
        <scroller class="scroller" scroll-direction="horizontal" flex-direction="row" loadmoreoffset="0" show-scrollbar=false>
            <div class="j-uline" ref="jcLine" v-if="channelList.length > 0"></div>
            <text class="i-c" v-for="(item,index) in channelList" :class="[ activeIndex == index ? 'c-act' : '']"
                  @click="chooseChannel(index,item)" :ref="'channel_' + index">{{item.name}}</text>
        </scroller>
    </div>
</template>
<script>
const dom = weex.requireModule('dom')
const animation = weex.requireModule('animation')
export default {
    props: {
        activeIndex: {
            type: Number,
            default: 0
        },
        channelList: {
            type: Array,
            default: []
        }
    },
    watch: {
        activeIndex (newVal, oldVal) {
            const scrollIndex = newVal < 4 ? 0 : newVal - 4;
            this.changeRect(newVal)
            this.scrollToMenu(scrollIndex)
        }
    },
    created () {},
    data () {
        return {
            dataReady: false
        }
    },
    methods: {
        chooseChannel (index, channel) {
            this.activeIndex = index
            this.$emit('change', index)
        },
        changeRect (index) {
            const item = this.channelList[index]
            animation.transition(this.$refs.jcLine, {
                styles: {
                    width: item.width + 'px',
                    transform: 'translateX(' + (item.left) + 'px)'
                },
                duration: 100,
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
