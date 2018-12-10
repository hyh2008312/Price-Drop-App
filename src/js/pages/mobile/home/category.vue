<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name == 'Electronic' ? 'Electronics': name}}</text>
        </div>
        <list class="main-list" ref="list" offset-accuracy="10" loadmoreoffset="400"
                   @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell>
                <div class="shipping-bg">
                    <text class="shipping-icon iconfont">&#xe760;</text>
                    <text class="shipping-tips">Free Shipping on Everything. Limited Time Only! </text>
                </div>
            </cell>
            <cell v-if="subCategory.length>0">
                <scroller class="scroller" scroll-direction="horizontal">
                    <text class="tab-txt" :class="[item.id == selId ? 'tab-txt-active' : '']"
                          v-for="item in subCategory" @click="changeCategory(item.id)">{{item.name}}</text>
                </scroller>
            </cell>
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
                   height="288">
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
    import { baseUrl } from '../../../config/apis';

    export default {
        components: {
            'refresher': refresher,
            preload,
            block7,
            block3,
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
                selId: false,
                imageUrl: '',
                testImage: '',
                goods: [],
                subCategory: [],
                length: 2,
                page: 1,
                pageSize: 12,
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid(),
                arrangement: false,
                goodsSave: [],
                sort: [{
                    value: false,
                    text: 'Pick For You'
                }, {
                    value: 'public_date',
                    text: 'New Arrivals'
                }, {
                    value: 'price_low',
                    text: 'Price Low to High'
                }, {
                    value: 'price_high',
                    text: 'Price High to Low'
                }],
                selectedSort: {
                    value: false,
                    text: 'Pick For You'
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
                this.getActivityProduct(true, false, this.selId);
            },
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.getActivityProduct(false, false, this.selId);
                }
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getActivityProduct(false, false, this.selId);
                }
            },
            getActivityParam (resData) {
                this.id = resData.id;
                googleAnalytics.trackingScreen(`Product/${this.name}`);
                this.$notice.loading.show();
                this.getActivityProduct(true, false, this.selId);
            },
            getActivityProduct (isfirst, arrange, id) {
                if (isfirst) {
                    if (!this.isFirstLoad) {
                        this.isFirstLoad = true;
                        this.getActivityCategory();
                        this.getSubCategory();
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
                    name: 'product.app.category.product',
                    data: {
                        cat: id || this.id,
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
            getActivityCategory () {
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/product/category/${this.id}/`
                }).then(data => {
                    this.name = data.name;
                }, error => {});
            },
            getSubCategory () {
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/product/app/category/${this.id}/`
                }).then(data => {
                    this.isFirstLoad = false;
                    this.subCategory = [...data];
                    this.subCategory.unshift({
                        id: false,
                        name: 'All'
                    });
                }, error => {});
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
                    return Math.ceil((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            changeArrangement () {
                this.$notice.loading.show();
                this.getActivityProduct(true, true, this.selId);
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
                this.$notice.loading.show();
                this.getActivityProduct(true, false, this.selId);
                this.$refs.wxcCancelPopup.hide();
            },
            changeCategory (id) {
                if (this.selId != id) {
                    this.selId = id;
                } else {
                    this.selId = false;
                }
                this.$notice.loading.show();
                this.getActivityProduct(true, false, this.selId);
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

    .scroller{
        background: #FFFFFF;
        border-top-style: solid;
        border-top-width: 1px;
        border-top-color: #E3E3E3;
        padding: 24px 16px 16px 16px;
        width: 750px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .tab-txt-active{
        background-color: #EF8A31;
        color: #fff;
    }

    .tab-txt{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 56px;
        font-weight: 300;
        padding: 0 24px;
        border-radius: 28px;
        background-color: rgba(0,0,0, 0.12);
        color: #000;
        margin: 0 16px;
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

    .navigation {
        width: 750px;
        height: 112px;
        background-color: #fff;
        flex-direction: row;
        justify-content: flex-start;
    }

    .title {
        font-size: 32px;
        font-weight: bold;
        height: 112px;
        line-height: 112px;
        margin-left: 32px;
        width: 558px;
        text-align: center;
        line: 1;
        text-overflow: ellipsis;
        color: rgba(0,0,0,0.87);
    }

    .homeBack {
        font-family: iconfont;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .wrapper {
        background-color: white;
    }

    .margin-left16 {
        flex-direction: column;
        align-items: flex-end;
    }

    .margin-right16 {
        flex-direction: column;
        align-items: flex-start;
    }

    .i-gd {
        width: 359px;
        height: 400px;
        margin-top: 24px;
    }

    .gd-bg {
        border-radius: 8px;
        width: 327px;
        height: 327px;
    }

    .gd-img {
        width: 323px;
        height: 323px;
        border-top-width: 1px;
        border-top-style: solid;
        border-top-color: rgba(0,0,0,.08);
        border-left-width: 1px;
        border-left-style: solid;
        border-left-color: rgba(0,0,0,.08);
        border-right-width: 1px;
        border-right-style: solid;
        border-right-color: rgba(0,0,0,.08);
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,.08);
    }
    .gd-img {
        width: 323px;
        height: 323px;
        border-radius: 8px;
        overflow: hidden;
    }
    .gd-img-image {
        width: 323px;
        height: 323px;
        border-radius: 8px;
        overflow: hidden;
        position: relative;
    }
    .gd-tlt {
        font-family: ProximaNova;
        font-size: 28px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        line-height: 34px;
    }

    .gd-tlt-bg{
        margin-top: 6px;
        width: 327px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }

    .gd-info{
        font-family: ProximaNova;
        margin-left: 8px;
        font-size: 20px;
        line-height: 24px;
        color: #EF8A31;
    }

    .gd-price {
        font-family: ProximaNova;
        font-size: 20px;
        font-weight: 500;
        width: 327px;
        line-height: 24px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
        text-align: center;
        text-decoration: line-through;
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
        height: 288px;
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

    .shipping-bg{
        background-color: #FFE3B6;
        width: 750px;
        height: 64px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .shipping-tips{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        margin-left: 14px;
        font-weight: 700;
    }

    .shipping-icon{
        font-family: ProximaNova;
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
    }
</style>
