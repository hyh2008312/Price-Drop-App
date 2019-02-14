<template>
    <div class="wrapper">
        <div ref="notice">
            <div class="inner-container" v-for="message in items">
                <text class="icon iconfont" v-if="message.notificationType == 'Free Shipping'">&#xe760;</text>
                <text class="icon iconfont" v-if="message.notificationType == 'Server Maintenance'">&#xe76d;</text>
                <text class="icon iconfont" v-if="message.notificationType == 'Holiday Shipping'">&#xe76d;</text>
                <text class="icon iconfont" v-if="message.notificationType == 'Payment Discount'">&#xe76c;</text>
                <text class="icon iconfont" v-if="message.notificationType == 'Coupon Promotion'">&#xe76c;</text>
                <text class="icon iconfont" v-if="message.notificationType == 'Other'">&#xe76d;</text>
                <text class="tips">{{message.context}} </text>
            </div>
        </div>
    </div>
</template>
<script>
    const animation = weex.requireModule('animation');
    import utilFunc from '../utils/util'

    export default {
        props: ['items'],
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
                    this.isOnAni = true;
                    animation.transition(this.$refs.notice, {
                        styles: {
                            opacity: 1,
                            transform: 'translateY(' + (-64 * (e + 1)) + 'px)'
                        },
                        duration: 500,
                        timingFunction: 'ease',
                        needLayout: true,
                        delay: 0
                    }, function () {
                        that.isOnAni = false;
                        if (e < that.items.length - 1) {
                            e++;
                            if (e == that.items.length - 1) {
                                setTimeout(() => {
                                    that.resetAni();
                                }, 3500);
                                return
                            }
                            setTimeout(() => {
                                that.aniNotice(e);
                            }, 3500);
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
                });
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
        background-color: #FFE3B6;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 32px;
        width: 686px;
        height: 64px;

        margin: 0 32px 24px;
    }

    .inner-container{
        width: 686px;
        height: 64px;
        justify-content: center;
        flex-direction: row;
        align-items: center;
    }

    .tips{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        margin-left: 14px;
        font-weight: 700;
    }

    .icon{
        font-family: ProximaNova;
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
    }
</style>
