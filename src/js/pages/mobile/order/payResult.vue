<template>
    <div class="wrapper">
        <top-header :title="title" :isBack="isBack"></top-header>
        <div class="status-bar"></div>
        <div class="wrapper-inner" :style="height" v-if="!result">
            <div class="container">
                <image class="pay-image" src="bmlocal://assets/pay-failure.png"></image>
            </div>
            <div class="pay-failure-bg">
                <text class="iconfont pay-failure-icon">&#xe632;</text>
                <text class="pay-failure-title">Payment Failed</text>
            </div>
            <text class="pay-failure-title-1">Your payment could not be processed</text>
            <text class="pay-failure-button" @click="back">Back</text>
        </div>
        <div class="wrapper-inner" :style="height" v-if="result">
            <div class="container">
                <image class="pay-image" src="bmlocal://assets/pay-success.png"></image>
            </div>
            <div class="pay-failure-bg">
                <text class="iconfont pay-success-icon">&#xe6e7;</text>
                <text class="pay-failure-title">Transaction Successful</text>
            </div>
            <text class="pay-failure-title-1">You have Paid Rs.1000.00</text>
            <text class="pay-failure-button" @click="back">Back</text>
        </div>
    </div>
</template>
<script>
import header from './header';
import { Utils } from 'weex-ui';

export default {
    components: {
        'top-header': header
    },
    eros: {
        appeared (params, options) {
            this.result = params.result;
            this.$notice.toast({
                message: this.result
            })
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 44 - 4) + 'px' }
    },
    data () {
        return {
            title: 'Pay',
            isBack: false,
            result: false
        }
    },
    methods: {
        back () {
            this.$router.finish()
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
        height: 44px;
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


</style>
