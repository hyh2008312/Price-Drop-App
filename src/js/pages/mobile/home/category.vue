<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name}}</text>
        </div>
        <list class="main-list" ref="list" loadmoreoffset="30"
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
            <cell v-for="(i ,index) in goods" >
                <block3 :goods="i" v-if="arrangement == false"></block3>
                <block7 :goods="i" v-if="arrangement == true"></block7>
            </cell>
            <cell class="cell-fixed" v-if="goods.length > 0"></cell>
            <loading class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
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
                imageUrl: '',
                testImage: '',
                goods: [],
                length: 2,
                page: 1,
                pageSize: 12,
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid(),
                arrangement: false,
                goodsSave: [],
                sort: [{
                    value: false,
                    text: 'New Arrivals'
                }, {
                    value: 'price_high',
                    text: 'Price Low to High'
                }, {
                    value: 'price_low',
                    text: 'Price High to Low'
                }],
                selectedSort: {
                    value: false,
                    text: 'New Arrivals'
                },
                isTrue: true,
                isCancelBottomShow: false
            }
        },
        methods: {
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.getActivityProduct(true);
            },
            onLoadingMore () {
                if (!this.isPlatformAndroid) {
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
                this.id = resData.id;
                this.name = resData.name;
                googleAnalytics.trackingScreen(`Activity/${this.name}`);
                this.getActivityProduct(true);
            },
            getActivityProduct (isfirst, arrange) {
                if (isfirst) {
                    this.page = 1
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
                    name: 'product.customer.list',
                    data: {
                        cat: this.id,
                        page: this.page,
                        page_size: this.pageSize,
                        sort: this.selectedSort.value
                    }
                }).then(data => {
                    this.$notice.loading.hide();
                    this.length = Math.ceil(data.count / this.pageSize)
                    if (isfirst) {
                        this.goods = []
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
                            if (i > 0 && (i % 2 == 1 || i == data.results.length - 1)) {
                                this.goods.push(arr);
                                arr = [];
                            }
                        }
                        this.goodsSave.push(...data.results);
                    } else {
                        this.goods.push(...data.results);
                        this.goodsSave.push(...data.results);
                    }
                    if (!isfirst) {
                        this.isLoading = false
                    }
                    this.refreshApiFinished()
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
                this.selectedSort = item;
                this.$refs.wxcCancelPopup.hide();
                this.getActivityProduct(true);
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
</style>
