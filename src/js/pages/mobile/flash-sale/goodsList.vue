<template>
    <div class="wrapper">

        <list offset-accuracy="100" loadmoreoffset="100" @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell>
                <text class="center-time" v-if="channelIndex==0">This round ends in {{tranDateTime(endTime)}}</text>
                <text class="center-time" v-if="channelIndex!==0">New round starts in {{tranTime(startTime)}}</text>
            </cell>
            <cell class="cell-button" v-for="(i, index) in order" :key="i.id" @click="openGoodsDetail(i.productId)" >
                <div style="box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);border-radius: 16px;">
                    <div class="goods">
                    <div class="goods-top">
                        <div>
                            <image class="goods-img" :src="i.mainImage"></image>
                        </div>
                        <div class="goods-top-word">
                            <text class="goods-title">{{i.title}}</text>
                            <div class="goods-top-price" >
                                <text class="goods-low-price">Rs.{{i.unitPrice}}</text>
                                <text class="goods-regular-price">Rs.{{i.saleUnitPrice}}</text>
                                <text class="goods-off-price">{{i.discount}}% off</text>
                            </div>
                        </div>

                    </div>
                    <div class="goods-bottom">
                        <div>
                            <progressbar :value="i.totalStockLocked" :all="i.totalStock"></progressbar>
                            <text class="goods-bottom-word">{{i.totalStockLocked}} limited</text>
                        </div>
                        <div class="goods-bottom-btn">
                            <text class="goods-bottom-btntxt" v-if="channelIndex==0">Buy now</text>
                            <text class="goods-bottom-btntxt" v-if="channelIndex!==0">View detail</text>
                        </div>
                    </div>
                </div>
                </div>

            </cell>
            <loading class="loading"  :display="isLoading? 'show': 'hide'" @loading="onloading" >
                <text class="indicator">Loading...</text>
            </loading>
        </list>
        <div class="container-1" v-if="goods.length == 0">
            <div class="container-2">
                <image class="pay-image" src="bmlocal://assets/empty.png"></image>
            </div>
            <text class="address-title">There is no goods to show.</text>
        </div>
    </div>
</template>

<script>
    import { Utils, WxcPopup, WxcMask, WxcProgress } from 'weex-ui';
    import refresher from '../common/refresh';
    import ProgressBar from './progressBar';
    import { baseUrl } from '../../../config/apis';
    import dayjs from 'dayjs';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    export default {
        components: {
            'refresher': refresher,
            'progressbar': ProgressBar,
            WxcPopup, WxcProgress,
            WxcMask
        },
        props: ['index', 'activeIndex', 'item', 'listArr'],
        data () {
            return {
                goods: ['111'],
                proId: '',
                isF: false,
                order: [],
                page: 1,
                pageSize: 6,
                channelIndex: '',
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid(),
                startTime: '',
                endTime: new Date()
            }
        },
        created () {
            this.channelIndex = this.activeIndex
            if (this.index == 0 && this.activeIndex == 0) {
                this.proId = this.listArr[0].id
                this.endTime = this.listArr[0].endTime
                this.init()
            }
        },
        watch: {
            'index': {
                handler: function (val, oldVal) {
                    if (this.activeIndex == val) {
                        if (!this.isF) {
                            this.isF = true
                            this.proId = this.listArr[val].id
                            this.startTime = this.listArr[val].startTime
                            this.endTime = this.listArr[val].endTime
                            this.init()
                        }
                    }
                },
                deep: true
            }
        },
        methods: {
            onLoadingMore () {
                if (!this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getOrder(false)
                }
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getOrder(false)
                }
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd()
                this.isLoading = false
                this.init();
            },
            init () {
                this.$notice.loading.show();
                this.getOrder(true);
            },
            getOrder (isfirst) {
                if (isfirst) {
                    this.page = 1
                }
                if (this.page > this.length) {
                    if (this.order.length > 0) {
                        // this.$refs.refresh.refreshEnd()
                    }
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/flashsale/flash/customer/detail/`,
                    data: {
                        page: this.page,
                        pageSize: this.pageSize,
                        id: this.proId
                    },
                    header: {
                        needAuth: true
                    }
                }).then(data => {
                    this.$notice.loading.hide();
                    this.length = Math.ceil(data.count / this.pageSize);
                    this.page++;
                    if (isfirst) {
                        this.order = []
                    }
                    this.order.push(...data.results)
                    if (!isfirst) {
                        this.$nextTick(() => {
                            this.isLoading = false;
                        });
                    }
                    this.refreshApiFinished()
                    this.$notice.loading.hide();
                }, error => {
                    this.$notice.loading.hide();
                    // 错误回调
                    this.$notice.toast({
                        message: error
                    });
                })
            },
            refreshApiFinished () {
                this.$refs.refresh.refreshEnd();
            },
            openGoodsDetail (id) {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: id
                    }
                })
            },
            tranTime (str) {
                if (str != null) {
                    return dayjs(new Date(str)).format('HH:mm:ss')
                } else {
                }
            },
            tranDateTime (str) {
                if (str != null) {
                    return dayjs(new Date(str)).format('MM-DD HH:mm:ss')
                }
            }
        }
    }
</script>
<style lang="sass" src="./index.scss"></style>
<style>
    .center-time{
        width: 750px;
        flex-direction: column;
        justify-content: center;
        text-align: center;
        color: black;
        font-size: 24px;
        margin-top: 18px;
        margin-bottom: 18px;
        /*background-color: #f4f4f4;*/
    }
    .goods{
        width: 686px;
        flex-direction:column;
        justify-content: start;
        padding-top: 32px;
        padding-right: 32px;
        /*padding-bottom: 32px;*/
        padding-left: 32px;
        background-color: #FFFFFF ;
        border-radius: 16px;
        /*box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);*/

    }
    .goods-top{
        flex-direction: row;
        justify-content: space-between;
    }

    .goods-top-word{
        height: 180px;
        flex-direction: column;
        justify-content: space-between;
        margin-bottom: 24px;
    }
    .goods-title{
        width: 400px;
        /*background-color: #689de5;*/
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        text-align: left;
        line-height: 36px;
    }
    .goods-img{
        width: 176px;
        height: 176px;
        margin-right: 24px;
    }
    .goods-top-price{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        width: 400px;
    }
    .goods-low-price{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
        text-align: left;
        margin-right: 20px;
    }
    .goods-regular-price{
        font-size: 20px;
        color: #000000;
        text-align: left;
        text-decoration: line-through;
        margin-right: 20px;
    }
    .goods-off-price{
        font-size: 20px;
        color: #EF8A31;
        letter-spacing: 0;
        text-align: left;
    }
    .goods-bottom{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        border-top-width:1px ;
        border-top-color: grey ;
        border-top-style: dashed ;
        height: 108px;
    }
    .goods-bottom-word{
        opacity: 0.38;
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
    }
    .goods-bottom-btn{
        padding: 11px 15px;
        background-color: #EF8A31;
        border-radius:4px ;
    }
    .goods-bottom-btntxt{
        font-family: ProximaNova-Bold;
        font-size: 24px;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: left;
    }
</style>

