<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation" @click="test">
            <text class="title">Drops</text>
        </div>
        <div>
            <cutTab :items="tabsItems" @tabTo="onTabTo"></cutTab>
        </div>
        <list class="main-list" ref="list" offset-accuracy="100" loadmoreoffset="100" @loadmore="onLoadingMore">
            <refresher ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>
            <cell v-for="(i ,index) in goods">
                <cutingItem :goods=i :key="index" :flag="isCuting"></cutingItem>
            </cell>
            <loading class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">Loading...</text>
            </loading>
        </list>
        <div class="container-1" :style="height" v-if="goods.length == 0">
            <div class="container-2">
                <image class="pay-image" src="bmlocal://assets/empty.png"></image>
            </div>
            <text class="address-title">There is no drop to show.</text>
        </div>
        <wxc-loading :show="isShow"></wxc-loading>
    </div>
</template>
<script>
    import refresher from '../common/refresh';
    import tabTitle from './tabTitle';
    import cutingItem from './cutingItem';
    import { TAB } from './config'
    import { Utils, WxcLoading } from 'weex-ui';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'refresher': refresher,
            'cutTab': tabTitle,
            cutingItem,
            WxcLoading
        },
        eros: {
            beforeAppear (params, options) {
            }
        },
        created () {
            const pageHeight = Utils.env.getScreenHeight();
            this.height = { height: (pageHeight - 48 - 112 - 96 - 112) + 'px' };
            this.init();
            this.initGoogleAnalytics();
            this.$event.on('createCut', params => {
                this.requestProduct(true);
            });
        },
        destory () {
            this.$event.off('login')
            this.$event.off('createCut')
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
                isShow: false
            }
        },
        methods: {
            test () {
                /* const bmPush = weex.requireModule('bmPush')
                bmPush.getCliendId() */
            },
            initGoogleAnalytics () {
                googleAnalytics.trackingScreen('Drops');
            },
            init () {
                this.getTabName();
                this.requestProduct(true);
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.requestProduct(true);
            },
            onLoadingMore () {
                this.requestProduct(false)
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true
                    this.requestProduct(false)
                }
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
                    })
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
                    name: 'promotion.cut.list',
                    data: {
                        page: this.page,
                        page_size: 6,
                        status: 'progressing'
                    },
                    header: {
                            needAuth: true
            }}
            ).then(data => {
                    this.isShow = false;
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
                    this.refreshApiFinished();
                }, error => {
                    this.$notice.alert({
                        message: error
                    })
                })
            },
            getcutendProduct (isFirst) {
                this.$fetch({
                    method: 'GET',
                    name: 'promotion.cut.list',
                    data: {
                        page: this.page,
                        page_size: 6,
                        status: 'end'
                    },
                    header: {
                        needAuth: true
                    }

                }).then(data => {
                    this.isShow = false;
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
                    this.refreshApiFinished();
                }, error => {})
            },
            onTabTo (event) {
                this.tabKey = event.data.key;
                this.isCuting = !(this.tabKey === 'cutEnd');
                if (this.isCuting) {
                    googleAnalytics.trackingScreen('Drops/ongoing');
                } else {
                    googleAnalytics.trackingScreen('Drops/ended');
                }
                this.goods = false;
                this.isShow = true;
                this.requestProduct(true);
            }
        }
    }
</script>
<style scoped>
    .main-list {
        width: 750px;
        background-color: #F1F1F1;
        padding-bottom: 114px;
    }

    .banner {
        width: 750px;
        height: 360px;
        display: flex;
        margin-bottom: 18px;
        align-items: center;
        justify-content: center;
        position: relative;
    }

    .banner-image {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
    }

    .banner-text {
        font-size: 40px;
        color: rgba(255, 255, 255, .87);
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

    .homeBack {
        font-family: iconfont;
        color: white;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .wrapper {
        background-color: white;
    }

    .margin-left16 {
        margin-left: 16px;
    }

    .magin-right16 {
        margin-right: 8px;
    }

    .i-gd {
        width: 350px;
        height: 600px;
        margin-bottom: 20px;
    }

    .gd-bg {
        border-width: 1px;
        border-color: rgba(0, 0, 0, 0.12);
        border-style: solid;
        border-radius: 8px;
        /* background-color: #f4f4f4;*/
    }

    .gd-img {
        width: 350px;
        height: 350px;
    }

    .gd-tip {
        font-size: 20px;
        color: rgba(172, 11, 11, 0.87);
        text-align: center;
        width: 350px;
        margin-top: 10px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-price {
        font-size: 28px;
        width: 350px;
        margin-top: 4px;
        color: #000000;
        text-align: center;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-info {
        margin-top: 18px;
        text-align: center;
        lines: 1;
        font-size: 24px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-bargain {
        width: 350px;
        margin-top: 14px;
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    .text-bargain {
        width: 120px;
        height: 50px;
        line-height: 50px;
        color: black;
        font-size: 24px;
        border-style: solid;
        border-color: black;
        border-width: 2px;
        text-align: center;
    }
    .indicator {
        width: 750px;
        text-align: center;
        color: #888888;
        font-size: 28px;
        padding-top: 16px;
        padding-bottom: 16px;
    }
    .container-1{
        width: 750px;
        background-color: #F1F1F1;
    }
    .container-2{
        margin-top: 86px;
        width: 750px;
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
</style>
