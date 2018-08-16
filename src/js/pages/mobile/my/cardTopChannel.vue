<template>
    <div class="wrapper">
        <text class="i-c" v-for="(item,index) in channelList"
              :class="[ activeIndex == index ? 'c-act' : '']"
              @click="chooseChannel(index,item)">{{item.cardStatus}}</text>
        <div ref="jcLine" class="j-uline"></div>
    </div>
</template>
<script>
    import dayjs from 'dayjs';

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
                this.$emit('change', newVal)
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
            },
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        position: sticky;
        width: 750px;
        height: 96px;
        background-color: white;
        border-bottom-width: 1px;
        border-bottom-color: rgba(0,0,0, .12);
        border-bottom-style: solid;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
        margin-top: 160px;
    }

    .i-c {
        font-size: 24px;
        margin-left: 32px;
        margin-right: 32px;
        line-height: 96px;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        /*background-color: black;*/
        margin-bottom: 8px;
    }
    .i-b {
        font-size: 30px;
        color: black;
    }

    .c-act {
        font-weight: 900;
        color: #EF8A31;
    }

    .j-uline {
        position: absolute;
        left: 110px;
        bottom: 20px;
        width: 46px;
        height: 6px;
        background-color: #EF8A31;
        border-radius: 4px;
    }



</style>
