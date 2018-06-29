<template>
    <div class="wrapper">
        <topic-header :title="title" leftBtn="icon"  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div style="margin-top: 160px;"></div>
        <list style=" width: 750px;"  ref="list" offset-accuracy="100" loadmoreoffset="100" @loadmore="onLoadingMore" >
            <cell  v-for="(i,index) in goods">
                <text class="first-time">{{tranDate(i.modified)}}</text>

                <div class="mid-cell" >
                    <div class="overflow-card" @click="openNew(1)">

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
                                <image src="http://image.getpricedrop.com/appweb/line.png" class="pro-img"></image>
                            </div>

                            <div class="item-div">
                                <text class="item-1">Item: </text>
                                <!--<text class="item-2">Retro Mirror Aviator Sunglasses Flash Tinted Lens Eyeglasses</text>-->
                                <text class="item-2">{{i.context.item}}</text>
                            </div>

                            <div v-if="id==1">
                                <div class="price-div">
                                    <text class="item-1" >Price Drop: </text>
                                    <text class="item-2">Rs.{{i.context.priceDrop}}</text>
                                </div>
                                <div class="price-div">
                                    <text class="item-1" >Final Drop: </text>
                                    <text class="item-2">Rs.{{i.context.finalPrice}}</text>
                                </div>
                            </div>

                            <div v-if="id==2">
                                <div class="price-div">
                                    <text class="item-1" >Order #: </text>
                                    <text class="item-2">{{i.context.orderNumber}}</text>
                                </div>
                            </div>

                            <div v-if="id==3">
                                <!--<div class="price-div">-->
                                    <!--<text class="item-1" >Points: </text>-->
                                    <!--<text class="item-2">{{i.context.orderNumber}}</text>-->
                                <!--</div>-->
                                <div class="price-div" v-if="i.context.price!==''">
                                    <text class="item-1" >Final Drop: </text>
                                    <text class="item-2">Rs.{{i.context.price}}</text>
                                </div>
                            </div>

                            <div v-if="id==4">
                                <div class="price-div">
                                    <text class="item-italic" >Please Note: If you bought this item with a gift card, we will refund the gift card to you in 3 business days.</text>
                                </div>
                            </div>


                        </div>


                        <div class="card-bottom" v-if="i.noticeType=='cut_end'||i.noticeType=='cut_end_early'"  @click="openNew(1)">
                            <text class="card-bottom-word" >Click to View More Details</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom" v-if="i.noticeType=='expired_not_paid'" @click="openNew(1)">
                            <text class="card-bottom-word" >Click to Drop Price Again</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom"  v-if="i.noticeType=='reminder_payment'" @click="openNew(1)">
                            <text class="card-bottom-word" >Click to Pay Now</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom" v-if="id==2" @click="openNew(2)" >
                            <text class="card-bottom-word" >Click to View Order Details</text>
                            <text class="card-bottom-more" >&#xe626;</text>
                        </div>

                        <div class="card-bottom" v-if="i.noticeType=='card_expired_reminder'||i.noticeType=='card_expired_notice'" @click="openNew(5)">
                            <text class="card-bottom-word" >Click to Check Your Gift Card</text>
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
                <text class="address-title">TThere is no notification to show. </text>
            </cell>

            <loading class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
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
                    this.title = 'Drop Notifications'
                    // this.params = 'drop'
                } else if (params.id == 2) {
                    this.title = 'Orders Notifications'
                    // this.params = 'order'
                } else if (params.id == 3) {
                    this.title = 'Perks Notifications'
                    // this.params = 'perk'
                } else {
                    this.title = 'Refunds Notifications'
                    // this.params = 'refund'
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
                // this.getData()
                this.requestProduct(true);

                // this.$notice.alert({
                //     message: resData
                // })
            })
            // this.requestProduct(true);
            // if (this.params != '') {
            //     this.getData()
            // }
        },
        methods: {
            onLoadingMore () {
                this.requestProduct(false)
                // this.isLoading = true
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
            },
            getcutingProduct (isFirst) {
                // this.$notice.alert({
                //     message: this.page
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
                    this.page++;
                    this.isLoading = false;

                    // this.$notice.alert({
                    //     message: data
                    // })
                    // if (!isFirst) {
                    //     this.isLoading = false;
                    // }
                    // this.refreshApiFinished();
                }, error => {})
            },
            openNew (p) {
                if (p == 1) {
                    this.$router.setBackParams({ tab: 'drops' })
                    this.$router.back({
                        length: 9999,
                        type: 'PUSH'
                    })
                } else if (p == 2) {
                    this.$router.open({
                        name: 'order',
                        type: 'PUSH'
                    })
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
            tranDate (str) {
                // this.$notice.alert({
                //     message: str
                // })
                if (str != null) {
                    return dayjs(new Date(str)).format('MMMM DD, YYYY')
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
        height: 108px;
        font-family: ProximaNova-Regular;
        font-size: 24px;
        line-height: 34px;
        color: #000000;
        margin-right: 34px;
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
        color: #000000;
    }
    .item-2{
        font-family: ProximaNova-Regular;
        width: 398px;
        font-size: 24px;
        color: #EF8A31;
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
</style>
