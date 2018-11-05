<template>
    <div class="wrapper">
        <topic-header :title="title" leftBtn="icon"  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div style="margin-top: 160px;"></div>
        <list style=" width: 750px;"  ref="list" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore" >
            <cell  v-for="(i,index) in goods">
                <text class="first-time">{{tranDate(i.created)}}</text>

                <div class="mid-cell" >
                    <div class="overflow-card" >

                        <div class="overflow-card-content">
                            <div class="header1">
                                <image src="bmlocal://assets/pic-noti.png" class="icon-img" style=""></image>
                                <!--<text class="header1-word">Your DROP has ended!</text>-->
                                <text class="header1-word">{{i.title}}</text>
                            </div>
                            <text class="name">{{user}}</text>
                            <div class="pro-content">
                                <!--<text class="pro-word">Your DROP has ended and now you have 24 hours left to purchase your item at the final price.</text>-->
                                <text class="pro-word">{{i.context.text}}</text>
                                <image :src="i.context.image" class="pro-img" resize="contain"></image>
                            </div>

                            <div class="item-div" v-if="id!==5">
                                <text class="item-1">Item: </text>
                                <!--<text class="item-2">Retro Mirror Aviator Sunglasses Flash Tinted Lens Eyeglasses</text>-->
                                <text class="item-2">{{i.context.item}}</text>
                            </div>

                            <div v-if="id==1">
                                <div class="price-div" v-if="i.context.priceUnlocked">
                                    <text class="item-1 coloro" >Price Unlocked: </text>
                                    <text class="item-2 colorb" >₹{{i.context.priceUnlocked}}</text>
                                </div>
                            </div>

                            <div v-if="id==2">
                                <div class="price-div" v-if="i.noticeType=='flash_15_left_for_pay'||i.noticeType=='flash_expire_for_pay'">
                                    <text class="item-1 coloro" >Flash Sale Price: </text>
                                    <text class="item-2 colorb">₹{{i.context.flashSalePrice}}</text>
                                </div>

                                <div class="price-div" v-if="i.noticeType=='payment_success'||i.noticeType=='order_canceled'"> <!--付款成功 和 取消订单审核通过-->
                                    <text class="item-1 coloro" >Order #: </text>
                                    <text class="item-2 colorb">{{i.context.orderNumber}}</text>
                                </div>
                                <div class="price-div" v-if="i.noticeType=='payment_success'">
                                    <text class="item-1 coloro" >Payment Amount: </text>
                                    <text class="item-2 colorb">₹{{i.context.paymentAmount}}</text>
                                </div>
                                <div class="price-div" v-if="i.noticeType=='paid_to_packing'||i.noticeType=='already_shipped'"> <!--付款成功到发货 和 shipped-->
                                    <!--<text class="item-1" >Payment Amount: </text>-->
                                    <!--<text class="item-2">{{i.context.paymentAmount}}</text>-->
                                </div>
                            </div>

                            <div v-if="id==3">
                                <!--<div class="price-div">-->
                                    <!--<text class="item-1" >Points: </text>-->
                                    <!--<text class="item-2">{{i.context.orderNumber}}</text>-->
                                <!--</div>-->
                                <div class="price-div" v-if="i.noticeType==='point_increase_owner'||i.noticeType==='point_increase_friend'||i.noticeType==='cut_down_to'">
                                    <!--<text class="item-1" >Final Drop: </text>-->
                                    <!--<text class="item-2">₹{{i.context.price}}</text>-->
                                </div>
                                <div class="price-div" v-if="i.noticeType==='card_expired_reminder'||i.noticeType==='card_expired_notice'">
                                    <text class="item-1 coloro" >Gift Voucher: </text>
                                    <text class="item-2 colorb">₹{{i.context.giftVoucher}}</text>
                                </div>
                                <div class="price-div" v-if="i.noticeType==='card_expired_reminder'||i.noticeType==='card_expired_notice'">
                                    <text class="item-1 coloro" >Deadline: </text>
                                    <text class="item-2 colorb">{{tranDeadLineDate(i.context.deadline)}}</text>
                                </div>
                            </div>

                            <div v-if="id==4">
                                <div class="price-div">
                                    <text class="item-italic" >Please Note: If you bought this item with a gift voucher, we will refund the gift voucher to you in 3 business days.</text>
                                </div>
                            </div>

                            <div v-if="id==5">
                                <div  v-if="i.noticeType==='lottery_first'">
                                    <div class="price-div">
                                        <text class="item-1 coloro" >Your Prize: </text>
                                        <text class="item-2 coloro">{{i.context.prize}}</text>
                                    </div>
                                    <div class="price-div">
                                        <text class="item-2 colorb">{{i.context.item}}</text>
                                    </div>
                                </div>
                                <div  v-if="i.noticeType==='lottery_second'||i.noticeType==='lottery_third'">
                                    <div class="price-div">
                                        <text class="item-1 coloro" >Your Prize: </text>
                                        <text class="item-2 coloro">{{i.context.prize}} gift voucher</text>
                                    </div>
                                    <div class="price-div">
                                        <text class="item-2 colorb">{{i.context.item}}</text>
                                    </div>
                                </div>
                                <div class="price-div" v-if="i.noticeType==='lottery_no_prize'">
                                    <!--<text class="item-1" >Your Prize: </text>-->
                                    <text class="item-2">{{i.context.item}}</text>
                                </div>
                            </div>


                        </div>


                        <div class="card-bottom" v-if="i.noticeType=='cut_end'||i.noticeType=='cut_end_early'"  @click="openNew(1,i.context.customerCutId)">
                            <text class="card-bottom-word" >Click to View More Details</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom" v-if="i.noticeType=='expired_not_paid'" @click="openNew(1,i.context.customerCutId)">
                            <text class="card-bottom-word" >Click to Drop Price Again</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom"  v-if="i.noticeType=='reminder_payment'" @click="openNew(1,i.context.customerCutId)">
                            <text class="card-bottom-word" >Click to Pay Now </text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom" v-if="id==2" @click="openNew(2,i.context.orderId)" >
                            <text class="card-bottom-word" >Click to View Order Details</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom" v-if="i.noticeType=='card_expired_reminder'||i.noticeType=='card_expired_notice'" @click="openNew(5)">
                            <text class="card-bottom-word" >Click to Check Your Gift Voucher</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom" v-if="i.noticeType=='point_increase_owner'||i.noticeType=='point_increase_friend'" @click="openNew(3)">
                            <text class="card-bottom-word" >Click to View Your Ponits</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>
                        <div class="card-bottom" v-if="id==4"  @click="openNew(4)">
                            <text class="card-bottom-word" >Click to See Our New Offers</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>



                        <!-- 抽奖相关-->
                        <div class="card-bottom" v-if="i.noticeType=='lottery_first'" @click="openRaffle(1,i)" >
                            <text class="card-bottom-word" >Click to Submit Your Address</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>
                        <div class="card-bottom" v-if="i.noticeType==='lottery_second'||i.noticeType==='lottery_third'" @click="openRaffle(2,i)" >
                            <text class="card-bottom-word" >Click to Use Your Voucher</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>
                        <div class="card-bottom" v-if="i.noticeType==='lottery_no_prize'" @click="openRaffle(4,i)" >
                            <text class="card-bottom-word" >Click to See Prize Winners</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>
                    </div>

                </div>
                <!--<text>-->
                    <!--{{data}}-->
                <!--</text>-->
            </cell>

            <cell class="container-1" v-if="goods.length == 0">
                <!--<div class="container-1" v-if="order.length == 0">-->
                    <!--<div class="container-2">-->
                        <!--<image class="pay-image" src="bmlocal://assets/empty.png"></image>-->
                    <!--</div>-->
                    <!--<text class="address-title">There is no order to show.</text>-->
                <!--</div>-->
                <div class="container-2">
                    <image class="pay-image" src="bmlocal://assets/empty.png"></image>
                </div>
                <text class="address-title">There is no notification to show. </text>
            </cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">{{loadingWord}}</text>
            </loading>

        </list>



        <!--<text> time</text>-->


    </div>
</template>

<script>
    import header from './header';
    import dayjs from 'dayjs';
    import { baseUrl } from '../../../config/apis';

    export default {
        components: {
            'topic-header': header
        },
        eros: {
            appeared (params, options) {
                this.id = params.id
                if (params.id == 1) {
                    this.title = 'Drops Notifications'
                    // this.params = 'drop'
                } else if (params.id == 2) {
                    this.title = 'Orders Notifications'
                    // this.params = 'order'
                } else if (params.id == 3) {
                    this.title = 'Rewards Notifications'
                    // this.params = 'perk'
                } else if (params.id == 4) {
                    this.title = 'Refunds Notifications'
                    // this.params = 'refund'
                } else if (params.id == 5) {
                    this.title = 'Lucky Draw Notifications'
                    // this.params = 'raffle'
                }
                // this.getData()
            }
        },
        data () {
            return {
               id: '',
               title: '',
               params: '',
               user: '',
               data: '',
               isLoading: false,
               loadingWord: 'Loading...',
               page: 1,
               length: 2,
               pageSize: 3,
               goods: false
            }
        },
        created () {
            this.$storage.get('user').then(resData => {
                this.user = resData.firstName
            })
            this.$router.getParams().then(resData => {
                this.params = resData.type
                this.requestProduct(true);

                // this.$notice.alert({
                //     message: resData
                // })
            })
        },
        methods: {
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true
                    this.requestProduct(false)
                }
            },
            onloading () {
                this.isLoading = true
                this.requestProduct(false)
            },

            requestProduct (isFirst) {
                // this.loadingWord = 'Loading...'
                if (isFirst) {
                    this.page = 1;
                }
                if (this.page > this.length) {
                    // this.$refs.refresh.refreshEnd();
                    this.$nextTick(() => {
                        this.isLoading = false
                    })
                    return
                }
                // if (this.isCuting) {
                    this.getcutingProduct(isFirst);
                // } else {
                //     this.getcutendProduct(isFirst);
                // }
                this.$event.emit('readNotice', { type: this.params })
            },
            getcutingProduct (isFirst) {
                // this.$notice.alert({
                //     message: this.params
                // })
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/notice/detail/`,
                    data: {
                        page: this.page,
                        page_size: this.pageSize,
                        status: this.params
                    },
                    header: {
                        needAuth: true
                    }}
                ).then(data => {
                    this.data = data

                    if (data.count == 0) {
                        this.length = 2;
                    } else {
                        this.length = Math.ceil(data.count / this.pageSize);
                    }
                    if (isFirst) {
                        this.goods = [];
                    }
                    this.goods.push(...data.results);
                    this.$notice.alert({
                        message: this.goods
                    })
                    this.page++;
                    this.isLoading = false;

                    // this.$notice.alert({
                    //     message: data.results[0]
                    // })
                    // if (!isFirst) {
                    //     this.isLoading = false;
                    // }
                    // this.refreshApiFinished();
                }, error => {
                    // this.$notice.alert({
                    //     message: error
                    // })
                })
            },
            openNew (p, other) {
                if (p == 1) {
                    this.$router.open({
                        name: 'drops.cutDetail',
                        type: 'PUSH',
                        params: {
                            isShowSharePanel: false,
                            id: other
                        }
                    })
                } else if (p == 2) {
                    if (other !== 0) {
                        this.$router.open({
                            name: 'order.detail',
                            params: {
                                id: other
                            }
                        })
                    } else {
                        this.$router.open({
                            name: 'drops.cutDetail',
                            type: 'PUSH',
                            params: {
                                isShowSharePanel: false,
                                id: other
                            }
                        })
                    }
                } else if (p == 3) {
                    this.$router.open({
                        name: 'my.points',
                        type: 'PUSH'
                    })
                } else if (p == 5) {
                    this.$router.open({
                        name: 'my.card',
                        type: 'PUSH'
                    })
                } else {
                    this.$router.open({
                        name: 'mobile',
                        type: 'PUSH'
                    })
                }
            },

            openRaffle (par, item) {
                if (par == 1) {
                    // this.$router.open({
                    //     name: 'order.confirm',
                    //     type: 'PUSH',
                    //     params: {
                    //             title: item.context.prize,
                    //             mainImage: item.context.image,
                    //             salePrice: '0',
                    //             currentPrice: '0', // 计算价钱的金额
                    //             quantity: '1',
                    //             id: item.context.variantId,
                    //             drawId: item.context.drawId,
                    //             shippingPrice: '',
                    //             shippingTimeMin: item.context.shippingTimeMin,
                    //             shippingTimeMax: item.context.shippingTimeMax,
                    //             proId: 'lottery'
                    //     }
                    // })

                    this.$router.open({
                        name: 'my.raffle.draws',
                        type: 'PUSH'
                    })
                } else if (par === 2) {
                    this.$router.open({
                        name: 'my.card',
                        type: 'PUSH'
                    })
                } else if (par === 4) {
                    this.$router.open({
                        name: 'raffle.result',
                        type: 'PUSH',
                        params: {
                            id: item.context.drawId
                        }

                    })
                }
            },
            tranDate (str) {
                // this.$notice.alert({
                //     message: str
                // })
                if (str != null) {
                    return dayjs(new Date(str)).format('HH:mm a, MMMM DD, YYYY')
                } else {
                }
            },
            tranDeadLineDate (str) {
                // this.$notice.alert({
                //     message: str
                // })
                if (str != null) {
                    return str.substring(0, str.indexOf('.'))
                } else {
                }
            },
            tranDateD (str) {
                // this.$notice.alert({
                //     message: new Date(str)
                // })
                if (str != null) {
                    return dayjs(new Date(str)).format('MMM DD')
                } else {
                }
            }
        }
    }
</script>

<style>
    .wrapper{
        width: 750px;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .coloro{
        color: #EF8A31;
    }
    .colorb{
        color: black;
    }
    .first-time{
        padding-top: 36px;
        text-align: center;
        opacity: 0.54;
        font-family: ProximaNova-Regular;
        font-size: 20px;
        color: #000000;
    }
    .mid-cell{
        margin-top: 36px;
        width: 750px;
        flex-direction: row;
        justify-content: center;
    }
    .overflow-card{
        width: 718px;
        border-radius: 8px;
        background-color: #fff;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.19);

    }
    .overflow-card-content{
        padding-left: 36px;
        margin-top: 32px;
    }
    .header1{
        flex-direction: row;
        justify-content: start;
    }
    .icon-img{
        width: 32px;
        height: 32px;
    }
    .header1-word{
        width: 600px;
        margin-left: 16px;
        font-family: ProximaNova;
        font-weight: 700;
        font-size: 28px;
        color: #000000;
        line-height: 30px;
    }
    .name{
        font-size: 24px;
        color: #000000;
        margin: 8px 0 ;
    }
    .pro-content{
        flex-direction: row;
        justify-content: flex-start;
    }
    .pro-word{
        width: 448px;
        /*height: 108px;*/
        font-family: ProximaNova-Regular;
        font-size: 24px;
        line-height: 34px;
        color: #000000;
        margin-right: 34px;
        margin-bottom: 6px;
    }
    .pro-img{
        width: 130px;
        height: 130px;
    }
    .item-div{
        flex-direction: row;
        justify-content: flex-start;
        margin-bottom: 8px;
    }
    .item-1{
        font-family: ProximaNova;
        font-size: 24px;
        font-weight: 700;
    }
    .item-2{
        font-family: ProximaNova-Regular;
        width: 398px;
        font-size: 24px;
        text-align: left;
        line-height: 30px;
    }

    .item-italic{
        width: 616px;
        font-style:italic;
        font-size: 24px;
        color: #000000;
        text-align: left;
        line-height: 28px;
    }
    .price-div{
        flex-direction: row;
        justify-content: flex-start;
        margin-bottom: 8px;
    }
    .card-bottom{
        border-top-color:rgba(0,0,0,0.08) ;
        border-top-style:solid ;
        border-top-width:1px ;
        flex-direction: row;
        justify-content: space-between;
        margin-top: 32px;
    }
    .card-bottom-word{
        padding-top: 32px;
        padding-bottom: 28px;
        padding-left: 32px;
        font-size: 24px;
        font-weight: 700;
        color: #000000;
    }
    .card-bottom-more{
        font-family: iconfont;
        opacity: 0.6;
        background: #333333;
        font-weight: 700;
        padding-top: 32px;
        padding-bottom: 28px;
        padding-right: 32px;
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
