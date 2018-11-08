<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation" @click="test">
            <text class="title">Drops</text>
        </div>
        <list  class="main-list" ref="list" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <refresher class="gd-bg-gray" ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>
            <cell class="notice-wrapper">
                <div class="notice-bg">
                    <block-4 :items="block1.items" v-if="block1.items.length > 0" @noticeFinished="noNoticeFinished"></block-4>
                </div>
            </cell>
            <cell class="price-drop-banner">
                <image class="price-drop-banner-image" src="bmlocal://assets/drop-banner.jpg"></image>
            </cell>
            <cell>
                <cutTab :items="tabsItems" @tabTo="onTabTo" ref="cubTab"></cutTab>
            </cell>
            <cell v-for="(i ,index) in goods">
                <cutingItem :goods=i :key="index" :flag="isCuting"></cutingItem>
            </cell>
            <cell class="cell-button">
                <text> </text>
            </cell>
            <cell class="container-1" v-if="goods.length == 0 && !(!isCuting &&!isMyDropLogin) && isWifi">
                <div class="bg-color" :style="height">
                    <div class="container-2">
                        <image class="pay-image" src="bmlocal://assets/empty.png"></image>
                    </div>
                    <text class="address-title">There is no drop to show.</text>
                </div>
            </cell>
            <cell class="container-1-1" v-if="!isCuting &&!isMyDropLogin && isWifi">
                <div class="bg-color" :style="height">
                    <div class="container-2-1">
                        <image class="pay-image" src="bmlocal://assets/pay-success.png"></image>
                    </div>
                    <text class="address-title-1">To view your Drops, please login first!</text>
                    <div class="drop-login" @click="toLogin">
                        <text class="drop-login-text">Log In / Sign Up</text>
                    </div>
                </div>
            </cell>
            <cell class="container-1-1" v-if="!isWifi">
                <div class="bg-color" :style="height">
                    <div class="container-2-1">
                        <image class="pay-image" src="bmlocal://assets/error-05.png"></image>
                    </div>
                    <text class="no-wifi-title">It seems your internet is not stable. </text>
                    <text class="no-wifi-title-1">Please try again or reopen your app!</text>
                    <div class="drop-login" @click="ReloadData">
                        <text class="drop-reload">Reload</text>
                    </div>
                </div>
            </cell>
            <cell class="loading gd-bg-gray" v-if="isLoading && !(!isCuting &&!isMyDropLogin && isWifi)">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </loading>
        </list>
    </div>
</template>
<script>
    import refresher from '../common/refresh';
    import tabTitle from './tabTitle';
    import cutingItem from './cutingItem';
    import { TAB } from './config'
    import { Utils } from 'weex-ui';
    import block4 from './block4';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'refresher': refresher,
            'cutTab': tabTitle,
             cutingItem,
            'block-4': block4
        },
        eros: {
            beforeAppear (params, options) {
            }
        },
        created () {
            const pageHeight = Utils.env.getScreenHeight();
            this.height = { height: (pageHeight - 48 - 112 - 128 - 96 - 112) + 'px' };
            this.init();
            this.initGoogleAnalytics();
            this.$event.on('createCut', params => {
                this.requestProduct(true);
            });
            this.$event.on('login', params => {
                this.isMyDropLogin = true;
                this.requestProduct(true);
            });
            this.$event.on('logout', params => {
                this.goods = false;
                this.isMyDropLogin = false;
                this.requestProduct(true);
            });
            this.$event.on('jumpMyDrop', params => {
                this.$refs.cubTab.changeTab('cutEnd');
                this.isCuting = false;
                googleAnalytics.trackingScreen('Drops/my drops');
                this.goods = false;
                if (this.isMyDropLogin || this.isCuting) {
                    this.loadingStart();
                }
                this.requestProduct(true);
            });
            this.getBlock4();
            this.initIsLogin();
        },
        destory () {
            this.$event.off('login')
            this.$event.off('createCut')
            this.$event.off('logout')
            this.$event.off('jumpMyDrop')
        },
        data () {
            return {
                first: true,
                tabsItems: [],
                isCuting: true,
                goods: false,
                page: 1,
                length: 2,
                pageSize: 12,
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid(),
                block1: {
                    title: '',
                    url: '',
                    items: []
                },
                backup: [],
                isMyDropLogin: false,
                isWifi: true
            }
        },
        methods: {
            ReloadData () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.requestProduct(true);
                this.getBlock4();
                },
            toLogin () {
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            },
            initIsLogin () {
                if (this.$storage.getSync('user')) {
                    this.isMyDropLogin = true;
                } else {
                    this.isMyDropLogin = false;
                }
            },
            getBlock4 () {
                this.block1.items = [];
                this.$fetch({
                    method: 'GET',
                    name: 'promotion.get.broadcast',
                    data: {}
                }).then(resData => {
                    this.backup = [...resData];
                    const newArr = this.backup.splice(0, 4);
                    this.block1.items = [...newArr];
                }, error => {})
            },
            noNoticeFinished (e) {
                if (this.backup.length > 0) {
                    const newArr = this.backup.splice(0, 4);
                    this.block1.items = [...newArr];
                } else {
                    this.block1.items = [];
                    this.$fetch({
                        method: 'GET',
                        name: 'promotion.get.broadcast',
                        data: {}
                    }).then(resData => {
                        this.backup = [...resData];
                        const newArr = this.backup.splice(0, 4);
                        this.block1.items = [];
                        this.block1.items = [...newArr];
                    }, error => {})
                }
            },
            loadingStart () {
                this.$notice.loading.show('');
            },
            loadingEnd () {
                this.$notice.loading.hide();
            },
            test () {
            },
            initGoogleAnalytics () {
                googleAnalytics.trackingScreen('Drops');
            },
            init () {
                this.getTabName();
                this.loadingStart();
                this.requestProduct(true);
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.requestProduct(true);
                this.getBlock4();
            },
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.requestProduct(false)
                }
            },
            onloading () {
                /* if (this.isPlatformAndroid) {
                    this.isLoading = true
                    this.requestProduct(false)
                } */
            },
            getTabName () {
                this.tabsItems = TAB;
            },
            requestProduct (isFirst) {
                if (isFirst) {
                    this.page = 1;
                }
                if (this.page > this.length) {
                    this.$refs.refresh.refreshEnd();
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                if (this.isCuting) {
                    this.getcutingProduct(isFirst);
                } else {
                    this.getcutendProduct(isFirst);
                }
            },
            getcutingProduct (isFirst) {
                this.$fetch({
                    method: 'GET',
                    name: 'product.cut.list',
                    data: {
                        page: this.page,
                        page_size: 12
                    }}
            ).then(data => {
                    this.loadingEnd();
                    this.isWifi = true;
                    if (data.count == 0) {
                        this.length = 2;
                    } else {
                        this.length = Math.ceil(data.count / this.pageSize);
                    }
                    if (isFirst) {
                        this.goods = [];
                    }
                    this.page++;
                    this.goods.push(...data.results);
                    if (!isFirst) {
                        this.isLoading = false;
                    }
                }, error => {
                    this.loadingEnd();
                    if (error.status == 10) {
                        this.isWifi = false;
                    }
                })
            },
            getcutendProduct (isFirst) {
                this.$fetch({
                    method: 'GET',
                    name: 'promotion.new.cut.list',
                    data: {
                        page: this.page,
                        page_size: 12
                    },
                    header: {
                        needAuth: true
                    }

                }).then(data => {
                    this.loadingEnd();
                    this.isWifi = true;
                    if (data.count == 0) {
                        this.length = 2;
                    } else {
                        this.length = Math.ceil(data.count / this.pageSize);
                    }
                    if (isFirst) {
                        this.goods = [];
                    }
                    this.page++;
                    this.goods.push(...data.results);
                    if (!isFirst) {
                        this.isLoading = false;
                    }
                }, error => {
                    this.loadingEnd();
                    if (error.status == 10) {
                        this.isWifi = false;
                    }
                })
            },
            onTabTo (event) {
                this.tabKey = event.data.key;
                this.isCuting = !(this.tabKey === 'cutEnd');
                if (this.isCuting) {
                    googleAnalytics.trackingScreen('Drops/Latest drops');
                } else {
                    googleAnalytics.trackingScreen('Drops/my drops');
                }
                this.goods = false;
                if (this.isMyDropLogin || this.isCuting) {
                    this.loadingStart();
                }
                this.requestProduct(true);
            }
        }
    }
</script>
<style scoped>
    .price-drop-banner{
        width: 750px;
        height: 312px;
    }
    .price-drop-banner-image{
        width: 750px;
        height: 312px;
    }

    .drop-login-text{
        width: 320px;
        height: 64px;
        line-height: 64px;
        text-align: center;
        background-color: #EF8A31;
        border-radius: 8px;
        font-family: ProximaNova-Bold;
        font-weight: bold;
        color: #FFFFFF;
        font-size: 24px;
    }
    .drop-reload{
        width: 200px;
        height: 64px;
        line-height: 64px;
        text-align: center;
        background-color: #EF8A31;
        border-radius: 8px;
        font-family: ProximaNova-Bold;
        font-weight: bold;
        color: #FFFFFF;
        font-size: 24px;
    }
    .drop-login{
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin-top: 32px;
    }
    .main-list {
        width: 750px;
        background-color: #EF8A31;
        padding-bottom: 114px;
    }

    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .navigation {
        display: flex;
        width: 750px;
        height: 112px;
        background-color: #FFFFFF;
        flex-direction: row;
        justify-content: flex-start;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.12);
        border-bottom-width: 1px;
    }

    .title {
        font-size: 32px;
        height: 112px;
        font-weight: bold;
        width: 750px;
        line-height: 112px;
        text-align: center;
        color: rgba(0,0,0,0.87);
    }

    .wrapper {
        background-color: white;
    }

    .container-1{
        width: 750px;
        padding: 0 32px 24px;
        background-color: #FEA162;
    }
    .container-2{
        margin-top: 86px;
        width: 686px;
        border-radius: 16px;
        align-items: center;
        background-color: #FFFFFF;
    }
    .container-1-1{
        padding: 0 32px 24px;
        background-color: #EF8A31;
    }
    .container-2-1{
        margin-top: 86px;
        width: 686px;
        border-radius: 16px;
        align-items: center;
    }

    .pay-image{
        width: 202px;
        height: 202px;
    }

    .address-title{
        margin-top: 32px;
        font-size: 28px;
        line-height: 34px;
        text-align: center;
    }
    .address-title-1{
        margin-top: 20px;
        font-size: 20px;
        line-height: 34px;
        text-align: center;
        color: rgba(0,0,0,0.38);
    }
    .no-wifi-title{
        margin-top: 32px;
        font-size: 28px;
        line-height: 34px;
        text-align: center;
        font-weight: bold;
        font-family: ProximaNova-Bold;
        color: rgba(0,0,0, 0.38);
    }

    .no-wifi-title-1{
        font-weight: bold;
        font-family: ProximaNova-Bold;
        margin-top: 8px;
        font-size: 28px;
        line-height: 34px;
        text-align: center;
        color: rgba(0,0,0, 0.38);
    }

    .notice-bg{
        width: 750px;
        height: 64px;
        background-color:#FEA162;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(0,0,0,0.12);
        overflow: hidden;
    }

    .notice-wrapper {
        width: 750px;
        height: 64px;
        justify-content: start;
        align-items: center;
        overflow: hidden;
    }

    .gd-bg-gray{
        background-color: #f1f1f1;
    }

    .bg-color{
        background-color: #ffffff;
        width: 686px;
        border-radius: 16px;
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

    .cell-button{
        padding-top: 24px;
    }
</style>
