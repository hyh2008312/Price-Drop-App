<template>
    <div class="wrapper">
        <div class="status-bar"></div>
        <div class="wrapper-header">
            <text class="back iconfont" @click="back()">&#xe632;</text>
            <text class="title">{{title}}</text>
        </div>
        <list class="container" :style="height">
            <cell>
                <text class="title-1">Payment Method</text>
            </cell>
            <cell class="cell-bottom" @click="chooseMethod('paytm')">
                <div>
                    <image class="item-image" resize="contain" :src="paytmSrc"></image>
                    <text class="item-text">Wallet / UPI / Credit&Debit Card / Net Banking </text>
                </div>
                <text class="iconfont item-checked" v-if="method == 'paytm'">&#xe6fb;</text>
                <text class="iconfont item-no-checked" v-if="method != 'paytm'">&#xe73f;</text>
            </cell>
            <cell class="cell-bottom" @click="chooseMethod('razorpay')">
                <div>
                    <image class="item-image-1" resize="contain" :src="razorpaySrc"></image>
                    <text class="item-text">UPI / Credit&Debit Card / Net Banking </text>
                </div>
                <text class="iconfont item-checked" v-if="method == 'razorpay'">&#xe6fb;</text>
                <text class="iconfont item-no-checked" v-if="method != 'razorpay'">&#xe73f;</text>
            </cell>
            <cell>
                <text class="title-2">Order#:{{order.number}}</text>
            </cell>
        </list>
        <order-payment-method-bottom :order="order" :method="method" :source="source"></order-payment-method-bottom>
    </div>
</template>
<script>
import orderPaymentMethodBottom from './orderPaymentMethodBottom';
import { Utils } from 'weex-ui';
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

export default {
    components: {
        orderPaymentMethodBottom
    },
    eros: {
        appeared (params, option) {
            this.order = params.data;
            this.source = params.source;
            this.$notice.alert({
                message: this.order
            })
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight();
        this.height = { height: (pageHeight - 112 - 112 - 48 - 2) + 'px' };
        googleAnalytics.trackingScreen('Payment Method');
        this.$event.on('closePayment', params => {
            this.$router.finish();
        });
    },
    data () {
        return {
            title: 'Payment',
            method: 'paytm',
            paytmSrc: 'bmlocal://assets/paytm.png',
            razorpaySrc: 'bmlocal://assets/razorpay.png',
            source: 'confirm',
            order: {
                'title': '',
                'mainImage': '',
                'salePrice': '',
                'currentPrice': '',
                'attributes': '',
                'quantity': 1,
                'id': -1,
                'shippingPrice': false,
                'total': '0.00'
            }
        }
    },
    methods: {
        back () {
            if (this.source == 'confirm') {
                this.$router.finish();
                this.$router.open({
                    name: 'order',
                    type: 'PUSH'
                });
            } else {
                this.$router.finish();
            }
        },
        chooseMethod (e) {
            this.method = e;
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
        background-color: #f1f1f1;
    }

    .wrapper-header{
        position: sticky;
        margin-top: 48px;
        width: 750px;
        height: 112px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #fff;
        z-index: 100;
        box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
    }

    .back{
        font-size: 40px;
        line-height: 40px;
        padding-left: 32px;
        padding-top: 32px;
        padding-bottom: 32px;
    }

    .title{
        width: 622px;
        font-size: 32px;
        line-height: 38px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        text-align: center;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .title-1{
        font-family: ProximaNova;
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        padding: 32px;
    }

    .title-2{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 80px;
        color: #000;
        width: 750px;
        padding: 0 32px;
        background-color: rgba(0,0,0,0.08);
        box-shadow: 0 2px 2px 0 rgba(0,0,0,0.12);
        border-bottom-right-radius: 4px;
        border-bottom-left-radius: 4px;
    }

    .status-bar{
        position: fixed;
        top: 0;
        height: 48px;
        background-color: #000;
        width: 750px;
    }

    .container{
        margin-top: 2px;
        width: 750px;
        background-color: #ffffff;
    }

    .cell-bottom{
        padding-bottom: 32px;
        padding-left: 32px;
        padding-right: 32px;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
    }

    .item-image{
        width: 152px;
        height: 48px;
    }

    .item-text{
        margin-top: 16px;
        font-family: ProximaNova;
        font-size: 20px;
        line-height: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
    }

    .item-image-1{
        width: 227px;
        height: 48px;
    }

    .item-checked{
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
    }

    .item-no-checked{
        font-size: 32px;
        line-height: 32px;
        color: rgba(0,0,0,0.32);
    }
</style>
