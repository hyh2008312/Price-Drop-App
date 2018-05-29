<template>
    <div class="wrapper">
        <text class="i-c" v-for="(item,index) in channelList"
              :class="[ activeIndex == index ? 'c-act' : '']"
              @click="chooseChannel(index,item)">{{item.name}}</text>
        <div ref="jcLine" class="j-uline"></div>
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
                this.changeRect(newVal)
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
                        transform: 'translateX(' + (item.left) + 'px)'
                    },
                    duration: 100,
                    timingFunction: 'ease',
                    delay: 0
                }, function () {});
            }
        }
    }
</script>
<style lang="sass" src="./topChannel.scss"></style>
