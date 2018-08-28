<template>
    <div class="wrapper">

        <list  offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell>
                <div v-if="channelIndex==0" class="overflow-center-time">
                    <text class="center-time-word" >This round ends in</text>

                    <div class="center-time">
                        <text class="center-time-hh">{{ahour||'00'}}</text>
                        <text style="font-size: 24px; padding-top: 10px;">:</text>
                        <text class="center-time-hh">{{amin||'00'}}</text>
                        <text style="font-size: 24px; padding-top: 10px;">:</text>
                        <text class="center-time-hh">{{asecond||'00'}}</text>
                    </div>

                </div>
                <!--<text class="center-time" v-if="channelIndex!==0">New round starts in {{tranTime(startTime)}}</text>-->
                <text class="center-time-space" v-if="channelIndex!==0">&nbsp;</text>
            </cell>
            <cell class="cell-button" v-for="(i, index) in order" :key="i.id" @click="openGoodsDetail(i.productId)" >
                <!--<div style="box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);border-radius: 16px;">-->
                    <!--<div class="goods">-->
                        <!--<div class="goods-top">-->
                            <!--<div>-->
                                <!--<image class="goods-img" :src="i.mainImage"></image>-->
                            <!--</div>-->
                            <!--<div class="goods-top-word">-->
                                <!--<text class="goods-title">{{i.title}}</text>-->
                                <!--<div class="goods-top-price" >-->
                                    <!--<text class="goods-low-price">Rs.{{calc(i.unitPrice,i.discount)}}</text>-->
                                    <!--<text class="goods-regular-price">Rs.{{i.saleUnitPrice}}</text>-->
                                    <!--<text class="goods-off-price">{{countOff(calc(i.unitPrice,i.discount),i.saleUnitPrice)}}</text>-->
                                <!--</div>-->
                            <!--</div>-->

                        <!--</div>-->
                        <!--<div class="goods-bottom">-->
                        <!--<div >-->
                            <!--<progressbar :value="i.soldStock" :all="i.totalFlashVariantStocks" :pId ="i.productId" v-if="channelIndex==0"></progressbar>-->
                            <!--&lt;!&ndash;<text class="goods-bottom-word">{{i.totalFlashVariantStocks}} limited</text>&ndash;&gt;-->
                        <!--</div>-->
                        <!--<div class="goods-bottom-btn">-->
                            <!--<text class="goods-bottom-btntxt" v-if="channelIndex==0">Buy Now</text>-->
                            <!--<text class="goods-bottom-btntxt" v-if="channelIndex!==0">View Detail</text>-->
                        <!--</div>-->
                    <!--</div>-->
                    <!--</div>-->
                <!--</div>-->


                <!--<cell v-for="(i ,index) in goods" :key="index" >-->
                    <goods :goods="i" :channelIndex="channelIndex" ></goods>
                    <!--<block7 :goods="i" v-if="arrangement == true"></block7>-->
                <!--</cell>-->

            </cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading  v-if="false" class="loading"  :display="isLoading? 'show': 'hide'" @loading="onloading" >
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
    import Goods from './goods';
    import { baseUrl } from '../../../config/apis';
    import dayjs from 'dayjs';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    export default {
        components: {
            'refresher': refresher,
            'progressbar': ProgressBar,
            'goods': Goods,
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
                endTime: new Date(),
                // end: {
                //
                // },
                aday: '',
                ahour: '',
                amin: '',
                asecond: ''
            }
        },
        created () {
            this.channelIndex = this.activeIndex
            if (this.index == 0 && this.activeIndex == 0) {
                this.proId = this.listArr[0].id
                this.endTime = this.listArr[0].endTime
                this.init()
                googleAnalytics.trackingScreen(`flash sale list/${this.channelIndex == 0 ? 'Ongoing' : 'Scheduled'}`)
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
                            googleAnalytics.trackingScreen(`flash sale list/${this.channelIndex == 0 ? 'Ongoing' : 'Scheduled'}`)
                        }
                    }
                },
                deep: true
            }
        },
        methods: {
            onLoadingMore () {
                if (!this.isLoading) {
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
                this.countDate(this.endTime)
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
                    }
                }).then(data => {
                    this.$notice.loading.hide();
                    this.length = Math.ceil(data.count / this.pageSize);
                    this.page++;
                    if (isfirst) {
                        this.order = []
                    }
                    let arr = [];
                    for (let i = 0; i < data.results.length; i++) {
                        const item = data.results[i];
                        arr.push(item);
                        if ((i > 0 && i % 2 == 1) || i == data.results.length - 1) {
                            this.order.push(arr);
                            arr = [];
                        }
                    }
                    // this.order.push(...data.results)
                    // this.$notice.alert({
                    //     message: this.order
                    // })
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
                    return dayjs(new Date(str)).format('MM-DD HH:mm:ss')
                } else {
                }
            },
            tranDateTime (str) {
                if (str != null) {
                    return dayjs(new Date(str)).format('MM-DD HH:mm:ss')
                }
            },
            calc (a, b) {
                return ((a * b) / 100).toFixed(2)
                // return (('0.00' * 100) / 100).toFixed(2)
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.floor((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            countDate (time) {
                const self = this
                // if (this.purchaseMethod == 'flash') {
                setInterval(() => {
                    this.NOW_DATE = new Date().getTime();

                    const total = (new Date(time).getTime() - this.NOW_DATE) / 1000

                    const day = Math.floor(total / (24 * 60 * 60))// 整天

                    self.aday = day
                    const afterDay = total - day * 24 * 60 * 60;
                    self.ahour = Math.floor(afterDay / (60 * 60)); // 小时
                    const afterHour = total - day * 24 * 60 * 60 - self.ahour * 60 * 60;
                    self.amin = Math.floor(afterHour / 60); // 分钟
                    if (self.amin < 10) {
                        self.amin = '0' + self.amin
                    }

                    const afterMin = total - day * 24 * 60 * 60 - self.ahour * 60 * 60 - self.amin * 60;
                    self.asecond = Math.floor(afterMin)// 秒
                    if (self.asecond < 10) {
                        self.asecond = '0' + self.asecond
                    }
                    // 加上减掉的天数
                    self.ahour += (self.aday * 24)

                    if (self.ahour < 10) {
                        self.ahour = '0' + self.ahour
                    }
                    // this.$notice.toast({
                    //     message: self.aday + '天' + self.ahour + ':' + self.amin + ':' + self.asecond
                    // })
                }, 1000);
                // }
            }
        }
    }
</script>
<style lang="sass" src="./index.scss"></style>
<style>
    .overflow-center-time{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin: 34px 0 8px 0 ;
    }
    .center-time-word{
        color: black;
        font-size: 24px;
    }
    .center-time{
        font-size: 28px;
        color: #FFFFFF;
        flex-direction: row;
        justify-content: start;
        margin-right: 32px;
    }
    .center-time-hh{
        font-family: ProximaNova;
        background-color: black;
        color: white;
        border-radius: 6px;
        padding: 5px;
        margin: 5px;
        font-size: 28px;
    }
    .center-time-space{
        width: 750px;
        flex-direction: column;
        justify-content: center;
        text-align: center;
        color: black;
        font-size: 24px;
        margin-top: 4px;
        margin-bottom: 4px;
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

