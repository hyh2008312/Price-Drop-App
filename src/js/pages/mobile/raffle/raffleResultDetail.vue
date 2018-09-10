<template>
    <div class="wrapper">
        <list class="content">
            <cell>
                <topic-header :title="name" leftBtn="icon"  ref="ref1" ></topic-header>
            </cell>
            <cell class="content-4" v-if="index == 0">
                <div class="product-bg">
                    <image class="product-image" :src="firstPrize.product.image"></image>
                </div>
                <text class="product-title">{{firstPrize.product.productTitle}}</text>
                <text class="title-1">Winner ({{firstPrize.drawer.length}})</text>
            </cell>
            <cell v-if="index == 0" class="content-2" v-for="item in firstPrize.drawer">
                <div class="avatar-bg">
                    <image class="avatar" :src="item.avatar"></image>
                </div>
                <text class="content-1">{{item.userName}}</text>
                <text class="content-3">won a free product</text>
            </cell>
            <cell class="content-4" v-if="index == 1">
                <div class="voucher-bg">
                    <image class="product-image-1" :src="secondPrize.voucher.image"></image>
                    <text class="product-title-1">{{secondPrize.voucher.name}} Gift Voucher</text>
                </div>
                <text class="title-2">Winner ({{secondPrize.drawer.length}})</text>
            </cell>
            <cell v-if="index == 1" class="content-2" v-for="item in secondPrize.drawer">
                <div class="avatar-bg">
                    <image class="avatar" :src="item.avatar"></image>
                </div>
                <text class="content-1">{{item.userName}}</text>
                <text class="content-3">won a gift voucher</text>
            </cell>
            <cell class="content-4 md-padding-bottom" v-if="index == 2">
                <div class="voucher-bg">
                    <image class="product-image-1" :src="thirdPrize.voucher.image"></image>
                    <text class="product-title-1">{{thirdPrize.voucher.name}} Gift Voucher</text>
                </div>
                <text class="title-2">Winner ({{thirdPrize.drawer.length}})</text>
            </cell>
            <cell v-if="index == 2" class="content-2" v-for="item in thirdPrize.drawer">
                <div class="avatar-bg">
                    <image class="avatar" :src="item.avatar"></image>
                </div>
                <text class="content-1">{{item.userName}}</text>
                <text class="content-3">won a gift voucher</text>
            </cell>
            <cell class="md-padding-bottom"></cell>
        </list>

        <div class="blackheader"></div>
    </div>
</template>

<script>
    import header from './header';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'topic-header': header
        },
        eros: {
            appeared (params, options) {
                this.index = params.index;
                this.name = params.name;
                switch (this.index) {
                    case 0:
                        this.firstPrize = params;
                        break;
                    case 1:
                        this.secondPrize = params;
                        this.secondVoucher = params.voucher.name;
                        break;
                    case 2:
                        this.thirdPrize = params;
                        this.thirdVoucher = params.voucher.name;
                        break;
                }
                googleAnalytics.trackingScreen('Raffle/Result/Detail');
            }
        },
        data () {
            return {
                id: 26,
                index: 0,
                name: '',
                firstPrize: {
                    index: 0,
                    product: {
                        image: '',
                        productTitle: ' '
                    },
                    drawer: [],
                    isLongArr: false,
                    isShow: false
                },
                secondPrize: {
                    index: 1,
                    voucher: {
                        image: '',
                        name: 'Rs.150'
                    },
                    drawer: [],
                    isLongArr: false,
                    isShow: false
                },
                secondVoucher: '',
                thirdPrize: {
                    index: 2,
                    voucher: {
                        image: '',
                        name: 'Rs.100'
                    },
                    drawer: [],
                    isLongArr: false,
                    isShow: false
                },
                thirdVoucher: ''
            }
        },
        methods: {}
    }
</script>

<style scoped>
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .content{
        margin-top: 48px;
        background-color: #fff;
        width: 750px;
    }

    .title{
        font-family: ProximaNova;
        font-size: 32px;
        line-height: 38px;
        font-weight: bold;
        margin-top: 32px;
        margin-bottom: 16px;
    }

    .title-1{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        padding-top: 24px;
        border-top-width: 1px;
        border-top-color: rgba(0,0,0,.08);
        border-top-style: solid;
    }

    .title-2{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        padding-top: 24px;
    }

    .product-bg{
        width: 686px;
        height: 320px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .product-image{
        width: 320px;
        height: 320px;
    }

    .product-image-1{
        position: relative;
        width: 686px;
        height: 292px;
    }

    .product-title{
        font-size: 24px;
        line-height: 28px;
        font-family: ProximaNova;
        margin-top: 16px;
        margin-bottom: 16px;
    }

    .product-title-1{
        width: 686px;
        padding-left: 24px;
        font-family: ProximaNova;
        font-weight: bold;
        font-size: 24px;
        line-height: 80px;
        color: rgba(0,0,0,0.87);
    }

    .voucher-bg{
        position: relative;
        flex-direction: column;
        justify-content: start;
        align-items: start;
        width: 686px;
        height: 372px;
        border-radius: 24px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.12);
        border-bottom-width: 1px;
        border-right-style: solid;
        border-right-color: rgba(0,0,0,0.12);
        border-right-width: 1px;
    }

    .avatar{
        width: 64px;
        height: 64px;
    }

    .avatar-bg{
        width: 64px;
        height: 64px;
        border-radius: 32px;
        margin-right: 16px;
    }

    .content-1{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        margin-right: 8px;
    }

    .content-2{
        width: 750px;
        height: 80px;
        padding-top: 16px;
        padding-left: 32px;
        padding-right: 32px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .content-3{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
    }

    .content-4{
        padding: 0 32px;
    }

    .md-padding-bottom{
        padding-bottom: 32px;
    }
</style>
