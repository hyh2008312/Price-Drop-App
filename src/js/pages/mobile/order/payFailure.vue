<template>
    <div class="wrapper">
        <top-header :title="title" :isBack="isBack"></top-header>
        <div class="status-bar"></div>
        <div class="wrapper-inner" :style="height">
            <div class="container">
                <image class="pay-image" src="bmlocal://assets/pay-failure.png"></image>
            </div>
            <div class="pay-failure-bg">
                <text class="iconfont pay-failure-icon">&#xe632;</text>
                <text class="pay-failure-title">Your Payment Failed</text>
            </div>
            <text class="pay-failure-title-1">Please try again or change your payment method. Thanks!</text>
            <text class="pay-failure-button" @click="back">Retry Payment</text>
            <text class="pay-failure-button-1" @click="jumpHome">Back to Home Page</text>
        </div>
    </div>
</template>
<script>
import header from './header';
import { Utils } from 'weex-ui';
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

export default {
    components: {
        'top-header': header
    },
    eros: {
        appeared (params, options) {
            if (params && params.source) {
                this.source = params.source
            }
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 48 - 4) + 'px' }
        googleAnalytics.trackingScreen('Payment Result Failure');
        googleAnalytics.recordEvent('PayEnd', 'Pay Failure', '', 0);
    },
    data () {
        return {
            title: 'Payment Result',
            isBack: false,
            source: false
        }
    },
    methods: {
        back () {
            this.$router.finish()
            if (this.source == 'confirm') {
                this.$router.open({
                    name: 'order',
                    type: 'PUSH'
                })
            } else {
                this.$router.back({
                    length: 1,
                    type: 'PUSH'
                })
            }
        },
        jumpHome () {
            this.$router.setBackParams({ tab: 'home' })
            this.$router.back({
                length: 9999,
                type: 'PUSH'
            })
        }
    }
}

</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper{
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
    }

    .wrapper-inner{
        margin-top: 4px;
        width: 750px;
        background-color: #fff;
    }

    .status-bar{
        position: fixed;
        top: 0;
        height: 48px;
        background-color: #000;
        width: 750px;
    }

    .pay-image{
        width: 202px;
        height: 202px;
    }

    .container{
        margin-top: 86px;
        width: 750px;
        align-items: center;
    }

    .pay-failure-bg{
        margin-top: 56px;
        width: 750px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .pay-failure-icon{
        font-size: 48px;
        line-height: 48px;
        font-weight: bold;
        color: #C61A1A;
    }

    .pay-success-icon{
        font-size: 48px;
        line-height: 48px;
        color: #6CC035;
    }

    .pay-failure-title{
        font-size: 40px;
        line-height: 48px;
        margin-left: 26px;
    }

    .pay-failure-title-1{
        margin-top: 24px;
        font-size: 28px;
        line-height: 34px;
        text-align: center;
    }

    .pay-failure-button{
        margin-left: 100px;
        margin-top: 166px;
        width: 550px;
        font-size: 28px;
        line-height: 80px;
        text-align: center;
        background-color: #EF8A31;
        border-radius: 4px;
        color: #fff;
        font-weight: bold;
    }

    .pay-failure-button-1{
        margin-left: 100px;
        margin-top: 16px;
        width: 550px;
        font-size: 28px;
        line-height: 76px;
        text-align: center;
        background-color: #fff;
        border-radius: 4px;
        color: #EF8A31;
        font-weight: bold;
        border-style: solid;
        border-color: #EF8A31;
        border-width: 2px;
    }


</style>
