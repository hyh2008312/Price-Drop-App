<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header :title="name" leftBtn="icon"  ref="ref1" ></topic-header>
        <list class="content" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore" v-if="hasWifi" >
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell class="content-4" v-if="index == 0">
                <div class="product-bg">
                    <image class="product-image" :src="firstPrize.product.image"></image>
                </div>
                <text class="product-title">{{firstPrize.product.productTitle}}</text>
                <text class="title-1">{{count > 1 ? 'Winners' : 'Winner'}} ({{count}})</text>
            </cell>
            <cell v-if="index == 0" class="content-2" v-for="item in drawer">
                <div class="avatar-bg">
                    <image class="avatar" :src="item.avatar"></image>
                </div>
                <text class="content-1">{{item.drawerFullName}}</text>
                <text class="content-3">won a free product</text>
            </cell>
            <cell class="content-4" v-if="index == 1">
                <div class="voucher-bg">
                    <image class="product-image-1" :src="secondPrize.voucher.newImageDetail"></image>
                    <text class="product-title-1">{{secondPrize.voucher.name}} Gift Voucher</text>
                </div>
                <text class="title-2">{{count > 1 ? 'Winners' : 'Winner'}} ({{count}})</text>
            </cell>
            <cell v-if="index == 1" class="content-2" v-for="item in drawer">
                <div class="avatar-bg">
                    <image class="avatar" :src="item.avatar"></image>
                </div>
                <text class="content-1">{{item.drawerFullName}}</text>
                <text class="content-3">won a gift voucher</text>
            </cell>
            <cell class="content-4 md-padding-bottom" v-if="index == 2">
                <div class="voucher-bg">
                    <image class="product-image-1" :src="thirdPrize.voucher.newImageDetail"></image>
                    <text class="product-title-1">{{thirdPrize.voucher.name}} Gift Voucher</text>
                </div>
                <text class="title-2">{{count > 1 ? 'Winners' : 'Winner'}} ({{count}})</text>
            </cell>
            <cell v-if="index == 2" class="content-2" v-for="item in drawer">
                <div class="avatar-bg">
                    <image class="avatar" :src="item.avatar"></image>
                </div>
                <text class="content-1">{{item.drawerFullName}}</text>
                <text class="content-3">won a gift voucher</text>
            </cell>

            <cell class="md-padding-bottom"></cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </loading>
        </list>
        <no-wifi v-if="!hasWifi" @onReload="loadingDown"></no-wifi>
    </div>
</template>

<script>
    import header from './header';
    import refresher from '../common/refresh';
    import { Utils } from 'weex-ui';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'topic-header': header,
            refresher
        },
        eros: {
            appeared (params, options) {
                this.index = params.index;
                this.name = params.name;
                this.id = params.id;
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
                this.init();
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
                thirdVoucher: '',
                count: 0,
                isLoading: false,
                drawer: [],
                pageNew: 1,
                pageSize: 12,
                lengthNew: 2,
                hasWifi: true,
                isPlatformAndroid: Utils.env.isAndroid(),
                isFirstLoad: false
            }
        },
        methods: {
            init () {
                this.$notice.loading.show();
                this.getNewGoods(true);
            },
            getNewGoods (isfirst) {
                if (isfirst) {
                    if (!this.isFirstLoad) {
                        this.isFirstLoad = true;
                    } else {
                        return;
                    }
                    this.pageNew = 1
                }
                if (this.pageNew > this.lengthNew) {
                    this.$refs.refresh.refreshEnd();
                    this.$nextTick(() => {
                        this.isLoading = false;
                    });
                    return;
                }
                let type = 'first';
                switch (this.index) {
                    case 0:
                        type = 'first';
                        break;
                    case 1:
                        type = 'second';
                        break;
                    case 2:
                        type = 'third';
                        break;
                }
                this.$fetch({
                    method: 'GET', // 大写
                    name: 'lottery.draw.prize.roster', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
                    data: {
                        type,
                        id: this.id,
                        page: this.pageNew,
                        page_size: this.pageSize
                    }
                }).then((data) => {
                    this.$notice.loading.hide();
                    if (isfirst) {
                        this.drawer = [];
                        this.isFirstLoad = false;
                    }
                    this.count = data.count;
                    this.lengthNew = Math.ceil(data.count / this.pageSize);
                    this.pageNew++;
                    this.drawer.push(...data.results);
                    if (!isfirst) {
                        this.isLoading = false;
                    }
                    this.refreshApiFinished();
                }, (error) => {
                    if (error.status == 10) {
                        this.hasWifi = false;
                    }
                });
            },
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.getNewGoods(false);
                }
            },
            onloading () {
                if (!this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getNewGoods(false);
                }
            },
            loadingDown () {
                if (this.hasWifi) {
                    this.$refs.refresh.refreshEnd();
                }
                this.isLoading = false;
                this.init();
            },
            refreshApiFinished () {
                this.$refs.refresh.refreshEnd();
            }
        }
    }
</script>

<style scoped>
    .blackheader{
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .content{
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
        padding-top: 32px;
        width: 686px;
        height: 352px;
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
        /*height: 292px;*/
        /*width: 592px;*/
        height: 202px;
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
        /*padding-top: 32px;*/
        position: relative;
        flex-direction: column;
        justify-content: start;
        align-items: start;
        width: 686px;
        /*height: 404px;*/
        /*width: 592px;*/
        height: 202px;

        border-radius: 24px;
        border-style: solid;
        border-color: rgba(0,0,0,0.12);
        border-width: 1px;
        /*border-right-style: solid;*/
        /*border-right-color: rgba(0,0,0,0.12);*/
        /*border-right-width: 1px;*/
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

    .loading{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        padding: 16px 0;
    }

    .loading-icon{
        width: 64px;
        height: 64px;
    }
</style>
