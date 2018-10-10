<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="header">
            <text class="header-icon iconfont" @click="homeBack">&#xe6f6;</text>
            <div class="header-search" @click="jumpSearch">
                <text class="header-icon-1 iconfont">&#xe621;</text>
                <text class="header-title">{{name}}</text>
                <text class="header-icon-2 iconfont">&#xe632;</text>
            </div>
        </div>
        <list class="main-list" ref="list" offset-accuracy="10" loadmoreoffset="400"
              @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <header>
                <div class="category-header">
                    <div class="category-left" @click="openDialog">
                        <text class="category-left-text">{{selectedSort.text}}</text>
                        <text class="iconfont category-left-icon" v-if="isCancelBottomShow == false">&#xe6fd;</text>
                        <text class="iconfont category-left-icon-1" v-if="isCancelBottomShow == true">&#xe6fc;</text>
                    </div>
                    <text class="iconfont category-arrange" v-if="arrangement == false" @click="changeArrangement">&#xe742;</text>
                    <text class="iconfont category-arrange" v-if="arrangement == true" @click="changeArrangement">&#xe743;</text>
                </div>
            </header>
            <cell v-for="(i ,index) in goods" :key="index" >
                <block3 :goods="i" v-if="arrangement == false"></block3>
                <block7 :goods="i" v-if="arrangement == true"></block7>
            </cell>
            <cell class="cell-fixed" v-if="goods.length > 0"></cell>
            <cell class="container-1" v-if="goods.length == 0">
                <div class="container-2">
                    <image class="pay-image" src="bmlocal://assets/empty.png"></image>
                </div>
                <text class="address-title">No search results. Please try another keywords! </text>
            </cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">loading...</text>
            </loading>
        </list>
        <toggle :have-overlay="isTrue"
                popup-color="rgba(255, 255, 255, 0)"
                :show="isCancelBottomShow"
                @wxcPopupOverlayClicked="popupCancelAutoClick"
                ref="wxcCancelPopup"
                pos="top"
                height="224">
            <div class="popup-cancel">
                <div v-for="item in sort">
                    <text class="popup-text" :class="[item.value == selectedSort.value?'popup-text-active': '']"
                          @click="chooseSort(item)">{{item.text}}</text>
                </div>
            </div>
        </toggle>
    </div>
</template>
<script>
    import { Utils } from 'weex-ui';
    import refresher from '../common/refresh';
    import preload from '../common/preloadImg';
    import block3 from './block3';
    import block7 from './block7';
    import toggle from './toggle';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'refresher': refresher,
            preload,
            block3,
            block7,
            toggle
        },
        created () {},
        eros: {
            appeared (params, options) {
                const arrange = this.$storage.getSync('categoryArrangement');
                if (arrange) {
                    this.arrangement = arrange;
                }
                this.getActivityParam(params);
            }
        },
        data () {
            return {
                name: '',
                id: -1,
                imageUrl: '',
                testImage: '',
                goods: false,
                length: 2,
                page: 1,
                pageSize: 12,
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid(),
                goodsSave: [],
                arrangement: false,
                sort: [{
                    value: false,
                    text: 'Default'
                }, {
                    value: 'price_low',
                    text: 'Price Low to High'
                }, {
                    value: 'price_high',
                    text: 'Price High to Low'
                }],
                selectedSort: {
                    value: false,
                    text: 'Default'
                },
                isTrue: true,
                isCancelBottomShow: false,
                isFirstLoad: false
            }
        },
        methods: {
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.getActivityProduct(true);
            },
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.getActivityProduct(false);
                }
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getActivityProduct(false);
                }
            },
            getActivityParam (resData) {
                this.name = resData.key;

                this.$notice.loading.show();
                this.getActivityProduct(true, false, true);
            },
            getActivityProduct (isfirst, arrange, isSearch) {
                if (isfirst) {
                    if (!this.isFirstLoad) {
                        googleAnalytics.trackingScreen(`Search/${this.name}`);
                        this.isFirstLoad = true;
                    } else {
                        return;
                    }
                    this.page = 1;
                    this.length = 2;
                }
                if (this.page > this.length) {
                    this.$refs.refresh.refreshEnd();
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                this.$fetch({
                    method: 'GET',
                    name: 'product.search.product.list',
                    data: {
                        key: this.name,
                        page: this.page,
                        page_size: this.pageSize,
                        sort: this.selectedSort.value
                    }
                }).then(data => {
                    this.$notice.loading.hide();
                    this.length = Math.ceil(data.count / this.pageSize);
                    if (isfirst) {
                        this.isFirstLoad = false;
                        this.goods = [];
                        if (isSearch) {
                            this.$fetch({
                                method: 'POST',
                                name: 'statistics.record.search.key',
                                data: {
                                    key: this.name,
                                    productCount: data.count
                                }
                            }).then((res) => {});
                        }
                    }
                    this.page++;
                    if (arrange) {
                        this.arrangement = !this.arrangement;
                        this.$storage.set('categoryArrangement', this.arrangement);
                    }
                    if (!this.arrangement) {
                        let arr = [];
                        for (let i = 0; i < data.results.length; i++) {
                            const item = data.results[i];
                            arr.push(item);
                            if ((i > 0 && i % 2 == 1) || i == data.results.length - 1) {
                                this.goods.push(arr);
                                arr = [];
                            }
                        }
                    } else {
                        for (let i = 0; i < data.results.length; i++) {
                            const item = data.results[i];
                            this.goods.push(item);
                        }
                    }
                    if (!isfirst) {
                        this.isLoading = false;
                    }
                    this.refreshApiFinished();
                }, error => {
                    this.$notice.toast({
                        message: JSON.stringify(error)
                    });
                })
            },
            jumpWeb (id) {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: id
                    }
                })
            },
            homeBack () {
                this.$router.back();
            },
            refreshApiFinished () {
                this.$refs.refresh.refreshEnd();
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.floor((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            changeArrangement () {
                this.$notice.loading.show();
                this.getActivityProduct(true, true);
            },
            openDialog () {
                if (this.isCancelBottomShow == false) {
                    this.isCancelBottomShow = true;
                } else {
                    this.$refs.wxcCancelPopup.hide();
                }
            },
            popupCancelAutoClick () {
                this.isCancelBottomShow = false;
            },
            chooseSort (item) {
                this.selectedSort = {};
                this.selectedSort.text = item.text;
                this.selectedSort.value = item.value;
                this.getActivityProduct(true);
                this.$refs.wxcCancelPopup.hide();
            },
            jumpSearch () {
                this.$router.finish();
                this.$router.open({
                    name: 'search',
                    type: 'PUSH',
                    params: {
                        key: this.name
                    }
                });
            }
        }
    }

</script>
<style scoped>
    .iconfont{
        font-family: iconfont;
    }

    .main-list {
        width: 750px;
        margin-top: -1px;
        background-color: #fff;
    }

    .category-header {
        width: 750px;
        height: 96px;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        background-color: #ffffff;
        border-width: 2px;
        border-style: solid;
        border-color: rgba(0,0,0,.08);
    }

    .category-left{
        margin-left: 32px;
        flex-direction: row;
        align-items: center;
    }

    .category-left-text{
        font-family: ProximaNova;
        font-weight: bold;
        font-size: 24px;
        line-height: 80px;
    }

    .category-left-icon{
        margin-left: 16px;
        font-size: 20px;
        color: rgba(0,0,0,0.54);
    }

    .category-left-icon-1{
        margin-left: 16px;
        margin-bottom: 12px;
        font-size: 20px;
        color: rgba(0,0,0,0.54);
    }

    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .header{
        position: sticky;
        width: 750px;
        height: 112px;
        margin-bottom: 1px;
        overflow: hidden;
        background-color: #fff;
        flex-direction: row;
        justify-content: start;
        align-items: center;
        box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
    }

    .header-search{
        margin-left: 32px;
        width: 622px;
        height: 80px;
        border-radius: 16px;
        background-color: rgba(0,0,0,0.08);
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .header-title{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 40px;
        placeholder-color: rgba(0,0,0,0.38);
        color: #000;
        letter-spacing: 0;
        width: 526px;
        tintColor: rgba(0,0,0,0.54);
    }

    .header-icon{
        font-size: 32px;
        margin-left: 32px;
    }

    .header-icon-1{
        font-size: 32px;
        margin: 0 16px;
    }

    .header-icon-2{
        position: absolute;
        font-size: 32px;
        right: 16px;
        top: 20px;
        width: 40px;
        height: 40px;
        border-radius: 20px;
        color: #fff;
        background-color: rgba(0,0,0,.38);
        line-height: 40px;
        text-align: center;
    }

    .wrapper {
        background-color: white;
    }

    .indicator {
        width: 750px;
        text-align: center;
        color: #888888;
        font-size: 28px;
        padding-top: 16px;
        padding-bottom: 16px;
    }

    .category-arrange{
        padding: 32px;
        font-size: 32px;
        color: #EF8A31;
    }

    .cell-fixed {
        width: 750px;
        height: 26px;
        background-color: #fff;
    }

    .popup-cancel {
        margin-left: 32px;
        height: 224px;
        width: 260px;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
        border-style: solid;
        border-width: 1px;
        border-color: rgba(0,0,0, 0.12);
        /*border-left-style: solid;*/
        /*border-left-width: 1px;*/
        /*border-left-color: rgba(0,0,0, 0.12);*/
        /*border-bottom-style: solid;*/
        /*border-bottom-width: 1px;*/
        /*border-bottom-color: rgba(0,0,0, 0.12);*/
        background-color: #fff;
        overflow: hidden;
        padding: 16px 32px;
    }

    .popup-text{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 64px;
        width: 686px;
    }

    .popup-text-active{
        font-weight: bold;
        color: #EF8A31;
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

    .container-1{
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        right: 0;
        background-color: #fff;
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
