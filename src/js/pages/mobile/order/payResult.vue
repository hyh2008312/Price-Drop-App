<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <div class="container">
            <image class="pay-image" src="src"></image>
        </div>
        <div>
            <text class="iconfont pay-failure-icon">&#xe632;</text>
            <text class="pay-failure-title">Payment Failed</text>
        </div>
        <text class="pay-failure-title-1">Your payment could not be processed</text>
    </div>
</template>
<script>
import header from './header';
import orderList from './orderList';
import topChannel from './topChannel';
import { CHANNELLIST } from './config';
import { Utils } from 'weex-ui';

export default {
    components: {
        'top-header': header
    },
    eros: {
        appeared (params, options) {
            this.result = params.result;
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 96 - 44) + 'px' }
        this.getChannel()
    },
    data () {
        return {
            title: 'Pay',
            result: true,
            src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
        }
    },
    methods: {

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
        background-color: #f1f1f1;
    }
    .pay-image{
        width: 202px;
        height: 202px;
    }

    .container{
        margin-top: 86px;
        width: 750px;
        justify-content: center;
    }

    .pay-failure-bg{

    }
    .pay-failure-icon{
        font-size: 32px;
        line-height: 32px;
    }

    .pay-failure-title{
        font-size: 40px;
        line-height: 48px;
        margin-left: 26px;
    }

    .pay-failure-title-1{
        margin-top: 28px;
        font-size: 28px;
        line-height: 34px;
    }


</style>
