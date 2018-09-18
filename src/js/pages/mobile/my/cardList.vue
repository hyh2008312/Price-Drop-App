<template>
    <div class="wrapper">

        <list offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell class="cell-button" v-for="(i, index) in order" :key="i.id" >
                <div class="overflow-gift">
                    <div class="gift-card">
                        <image class="gift-card-img"  :src="i.image"></image>
                        <div class="gift-card-txt">
                            <div class="gift-card-right-txt">
                                <text class="gift-card-txt1">{{i.name}} Gift Voucher</text>
                                <text class="gift-card-txt2">Only vaild for order above ₹{{i.lowestAmount}}</text>
                            </div>
                            <text class="gift-card-txt3">Expire in&nbsp;{{tranDate(i.expiredTimestamp)}}&nbsp;days</text>
                        </div>
                    </div>
                </div>
            </cell>

            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading  v-if="false" class="loading"  :display="isLoading? 'show': 'hide'" @loading="onloading" >
                <text class="indicator">Loading...</text>
            </loading>
        </list>
        <div class="container-1" v-if="order.length == 0">
            <div class="container-2">
                <image class="pay-image" src="bmlocal://assets/empty.png"></image>
            </div>
            <text class="address-title">There is no gift voucher to show.</text>
        </div>
    </div>
</template>

<script>
    import { Utils, WxcPopup, WxcMask, WxcProgress } from 'weex-ui';
    import refresher from '../common/refresh';
    import { baseUrl } from '../../../config/apis';
    import dayjs from 'dayjs';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    export default {
        components: {
            'refresher': refresher,
            WxcPopup, WxcProgress,
            WxcMask
        },
        props: ['index', 'activeIndex', 'item', 'listArr'],
        data () {
            return {
                goods: ['111'],
                proId: '',
                isF: false,
                order: false,
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
                            this.init()
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
                    name: 'point.exchange', // 通过get 获取我自己的积分卡
                    header: {
                        needAuth: true
                    },
                    data: {
                        page: this.page,
                        pageSize: this.pageSize,
                        status: this.proId
                    }
                }).then(data => {
                    // this.$notice.alert({
                    //     message: data
                    // });
                    this.$notice.loading.hide();
                    // this.length = Math.ceil(data.count / this.pageSize);
                    this.length = 0;
                    this.page++;
                    if (isfirst) {
                        this.order = []
                    }
                    this.order.push(...data);
                    if (!isfirst) {
                        this.$nextTick(() => {
                            this.isLoading = false;
                        });
                    }

                    this.refreshApiFinished();
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
            tranTime (str) {
                if (str != null) {
                    return dayjs(new Date(str)).format('HH:mm:ss')
                } else {
                }
            },
            tranDate (tamp) {
                const now = new Date().getTime()
                const total = tamp - now / 1000
                const day = Math.floor(total / (24 * 60 * 60)); // 整天
                return day;
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
    .overflow-gift{
        flex-direction: row;
        justify-content: center;
        margin-top: 32px;
    }
    .gift-card{
        box-shadow: 0 1px 6px 0 rgba(0,0,0,0.12);
        border-radius: 24px;
    }
    .gift-card-img{
        width: 686px;
        height: 304px;
    }

    .gift-card-txt{
        width: 686px;
        flex-direction: row;
        justify-content: space-between;
        background-color: white;
        padding-top:26px ;
        padding-bottom:26px ;
        padding-left:24px ;
        padding-right:24px ;
    }
    .gift-card-right-txt{
        flex-direction: column;
        /*align-items: center;*/
        justify-content: flex-start;
    }
    .gift-card-txt1{
        font-family: ProximaNova-Bold;
        font-weight: 700;
        font-size: 24px;
        margin-bottom: 16px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-txt2{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-txt3{
        font-family: ProximaNova-Regular;
        font-size: 24px;
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
    .empty-div{
        flex-direction: column;
        justify-content:flex-start;
        align-items: center;
        height: 300px;
        margin-top: 140px;
        /*background-color: black;*/
    }
    .empty-img{
        width: 200px;
        height: 200px;
    }
    .empty-txt{
        opacity: 0.54;
        font-family: ProximaNova-Bold;
        font-size: 24px;
        color: #000000;
    }
</style>

