<template>
    <div class="wrapper">
        <div ref="notice">
            <div class="inner-container" v-for="message in items">
                <div class="header">
                    <preload class="header-image" :src="message.avatar"></preload>
                </div>
                <text class="tlt">{{message.username}} started a new Price Drop! </text>
                <!--<text class="tlt">Reshme redeemed a ₹150 gift card!</text>-->
            </div>
        </div>
    </div>
</template>
<script>
    import preload from '../common/preloadImg';

    const animation = weex.requireModule('animation');
    import utilFunc from '../utils/util'

    export default {
        components: {
            preload
        },
        props: ['items', 'index', 'activeIndex'],
        data () {
            return {
                isOnAni: false
            }
        },
        mounted () {
            this.init()
        },
        watch: {
            items (newVal, oldVal) {
                this.resetAni()
            }
        },
        methods: {
            init () {
                const index = -1;
                this.aniNotice(index)
            },
            aniNotice (e) {
                const that = this;
                if (!this.isOnAni) {
                    this.isOnAni = true
                    animation.transition(this.$refs.notice, {
                        styles: {
                            opacity: 1,
                            transform: 'translateY(' + (-80 * (e + 1)) + 'px)'
                        },
                        duration: 2500,
                        timingFunction: 'ease',
                        delay: 0
                    }, function () {
                        that.isOnAni = false
                        if (e < that.items.length - 1) {
                            e++;
                            if (e == that.items.length - 1) {
                                that.$emit('noticeFinished', {
                                    status: 'finished'
                                });
                                return
                            }
                            that.aniNotice(e)
                        }
                    });
                }
            },
            resetAni (e) {
                const that = this;
                animation.transition(this.$refs.notice, {
                    styles: {
                        opacity: 0,
                        transform: 'translateY(0px)'
                    },
                    duration: 0,
                    timingFunction: 'ease',
                    delay: 0
                }, function () {
                    that.isOnAni = false;
                    that.init()
                })
            },
            formateDate (dateTimeStamp) {
                return utilFunc.formateDate(dateTimeStamp);
            }
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper{
        width: 622px;
        height: 60px;
        overflow: hidden;
    }

    .inner-container{
        width: 622px;
        height: 80px;
        justify-content: start;
        flex-direction: row;
        align-items: center;
    }

    .header{
        width: 64px;
        height: 64px;
        overflow: hidden;
        border-radius: 32px;
        margin-left: 8px;
    }

    .header-image{
        width: 64px;
        height: 64px;
    }

    .tlt {
        color: rgba(0,0,0,0.87);
        width: 530px;
        font-size: 24px;
        lines: 1;
        margin-left: 12px;
        margin-right: 8px;
        text-overflow: ellipsis;
    }
</style>
