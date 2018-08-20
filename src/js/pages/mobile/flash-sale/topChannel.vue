<template>
    <div class="wrapper">
        <div v-for="(item,index) in channelList"
             :class="[ activeIndex == index ? 'c-act' : '',
             index==0?'i-c':'' ,index==1?'i-c1':'',index==2?'i-c2':'']"

             @click="chooseChannel(index,item)">
            <text class="i-b">{{tranTime(item.startTime,1)}}</text>
            <text class="i-b" v-if="item.flashStatus=='Ongoing'">{{item.flashStatus}}</text>
            <text class="i-b" v-if="item.flashStatus=='Scheduled'">{{tranTime(item.startTime,2)}}</text>
        </div>

        <div ref="jcLine" class="j-uline">11</div>
        <!--<text>{{style.left}}</text>-->
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
                dataReady: false,

            }
        },
        methods: {
            chooseChannel (index, channel) {
                // this.activeIndex = index
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
            tranTime (str, num) {
                if (str != null) {
                    if (num == 1) {
                        return dayjs(new Date(str)).format('HH:mm')
                    } else if (num == 2) {
                        return dayjs(new Date(str)).format('MM/DD')
                    }
                } else {
                    if (num == 1) {
                        return dayjs(new Date()).format('HH:mm')
                    } else if (num == 2) {
                        return dayjs(new Date()).format('MM/DD')
                    }
                }
            }
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
        background-color: #EF8A31;
        border-bottom-width: 1px;
        border-bottom-color: rgba(0,0,0, .12);
        border-bottom-style: solid;
        flex-direction: row;
        /*justify-content: space-around;*/
        align-items: center;
    }

    .i-c {
        position: absolute;
        left: 4px;
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
    .i-c1 {
        position: absolute;
        left: 262px;
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
    .i-c2 {
        position: absolute;
        left: 498px;
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
        color: white;
        width: 200px;
        text-align: center;
    }

    .c-act {
        font-weight: 900;
        color: white;
    }

    .j-uline {
        position: absolute;
        left: 110px;
        bottom: 10px;
        width: 46px;
        height: 6px;
        background-color: white;
        border-radius: 4px;
    }



</style>
