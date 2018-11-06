<template>
    <div class="wrapper">
        <topic-header class="t-h1" title="Confirm Order" leftBtn="y" :rightBtn="rightBtnWord" v-on:change="changeBtn($event)" ></topic-header>
        <div class="blackheader"></div>

        <list class="content" offset-accuracy="10" loadmoreoffset="400" >
            <cell class="overflow-box">
                <order-shipping :address="address" style="margin-top: 24px"></order-shipping>
            </cell>

            <cell class="overflow-box">
                <div class="gift-v" @click="jumpCard">
                    <text class="g-v-1">Gift Voucher</text>
                    <div class="gift-vw" >
                        <!--<text class="gift-vw1">Choose a Gift Voucher </text>-->
                        <text class="gift-vw1" v-if="card === false">Choose a Gift Voucher</text>
                        <text class="gift-vw1" v-if="card === ''">No Gift Voucher Available</text>
                        <text class="gift-vw1" v-if="card">₹{{card.share}} Gift Voucher</text>
                        <text class="iconfont gift-vw2">&#xe626;</text>
                    </div>

                </div>
            </cell>

            <cell class="overflow-box1" v-for="i in orderList">
                <goods-item :item="i" style="margin-bottom: 24px"></goods-item>
            </cell>
            <!--<cell  v-for="(i,index) in goodsList"-->
                   <!--:key="i.id" style="width: 750px">-->
                <!---->
            <!--</cell>-->

            <!--<cell class="loading" v-if="isLoading">-->
                <!--<image class="loading-icon" src="bmlocal://assets/loading.gif"></image>-->
            <!--</cell>-->
        </list>
        <div class="bottom-btn" >
            <div class="bb-d">
                <div class="bb-d-r">
                    <!--<div :class="[selAllStatus?'lc-t-dot-select':'lc-t-dot']">-->
                        <!--<text class="dot-sel">&radic;</text>-->
                    <!--</div>-->
                    <!--<text class="bb-dr-t">Select All</text>-->
                </div>

                <div class="bb-d-l">
                    <text class="bb-dl" >Total：</text>
                    <text class="bb-dl-p" >₹{{parseInt(allPrice).toFixed(2)}}</text>
                    <div class="bb-dl-b" @click="postOrder">
                        <text class="bb-dl-bf" >Place Order</text>
                    </div>

                </div>
            </div>
        </div>

    </div>
</template>

<script>
    import header from './witheHeader';
    import cartOrderShipping from './cartOrderShipping';
    import goodsItem from './cartGoods';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    import { WxcCountdown } from 'weex-ui';
    export default {
        components: {
            'topic-header': header,
            'order-shipping': cartOrderShipping,
            'goods-item': goodsItem,
            WxcCountdown
        },
        eros: {
            beforeAppear (params, options) {
                this.orderList = params;
                this.countPrice(this.orderList);
            },
            appeared (params, option) {
                this.card = false;
                this.getAddress();
            },
            backAppeared (params, options) {
                if (params.card) {
                    this.card = params.card;
                    this.countPrice(this.orderList)
                    this.allPrice = (parseInt(this.allPrice) - parseInt(this.card.share))
                }
                this.getAddress();
            }
        },
        data () {
            return {
                address: {
                    'id': false,
                    'firstName': '',
                    'lastName': '',
                    'postcode': '',
                    'line1': '',
                    'line2': '',
                    'line3': '',
                    'city': '',
                    'stateName': '',
                    'countryName': '',
                    'isDefault': false,
                    'phoneNumber': '',
                    'stateId': 5
                },
                orderList: false,
                card: false,
                allPrice: '0.00'
            }
        },
        created () {
            googleAnalytics.trackingScreen('CartOrder');
        },
        methods: {
            getAddress () {
                this.$fetch({
                    method: 'GET', // 大写
                    name: 'address.get.default',
                    header: {
                        needAuth: true
                    }
                }).then(resData => {
                    this.address = resData;
                }, error => {
                    // this.$notice.alert({
                    //     message: error
                    // })
                });
            },
            jumpCard () {
                this.$router.open({
                    name: 'order.detail.card',
                    params: {
                        cardMoney: this.allPrice,
                        card: this.card
                    }
                });
            },
            postOrder () {
                if (!this.address.id) {
                    this.$notice.toast('Please add address first!');
                    return
                }
                this.$notice.loading.show();
                let arr = []
                for (let i = 0; i < this.orderList.length; i++) {
                    if (this.orderList[i].productType === 'direct') {
                        arr.push({
                            class: 'direct',
                            order: {
                                vid: this.orderList[i].variantId,
                                quantity: this.orderList[i].quantity
                            }
                        })
                    } else if (this.orderList[i].productType === 'flash') {
                        arr.push({
                            class: 'flash',
                            order: {
                                vid: this.orderList[i].variantId,
                                quantity: this.orderList[i].quantity,
                                fpId: this.orderList[i].flashId
                            }
                        })
                    }
                }
                // if (this.card == null) {
                //     this.card.id = ''
                // }
                // let aa = {
                //         amount: this.allPrice,
                //         voucherId: this.card.id,
                //         cartData: arr
                //     }
                // this.$notice.alert({
                //     message: this.card.id
                // })
                // return
                this.$fetch({
                    method: 'POST', // 大写
                    name: 'order.cart.create',
                    data: {
                        amount: this.allPrice,
                        voucherId: this.card.id,
                        cartData: arr
                    },
                    header: {
                        needAuth: true
                    }
                }).then(resData => {

                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: resData
                    // })
                    //
                    // return

                    googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'PlaceOrder', 0);
                    googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', 'PlaceOrder', '', 'Rs', this.allPrice);
                    const order = resData;
                    order.paymentAmount = parseInt(this.allPrice)
                    // that.$router.finish();
                    this.$router.open({
                        name: 'cart.order.payment',
                        type: 'PUSH',
                        params: {
                            source: 'confirm',
                            data: order,
                            phone: this.address.phoneNumber
                        },
                        backCallback: () => {
                            this.$router.finish();
                            this.$router.open({
                                name: 'order',
                                type: 'PUSH'
                            });
                            this.$event.emit('closePayment');
                        }

                    });
                    // this.isFirst = false;
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            countPrice (arr) {
                const priceArr = []
                if (arr.length !== 0) {
                    for (let j = 0; j < arr.length; j++) {
                        if (arr[j].productType == 'flash') {
                            priceArr.push(
                                ((parseInt(this.calc(arr[j].unitPrice, arr[j].flashDiscount))) * arr[j].quantity)
                            ) // 计算浮点数 乘100
                        } else if (arr[j].productType == 'direct') {
                            priceArr.push((parseInt(arr[j].unitPrice) * arr[j].quantity)) // 计算浮点数 乘100
                        }
                    }
                    if (priceArr.length == 0) {
                    this.allPrice = '0.00'
                } else if (priceArr.length >= 1) {
                    this.allPrice = 0;
                        for (let i = 0; i < priceArr.length; i++) {
                            this.allPrice += parseInt(priceArr[i])
                        }
                    }
                }
            },
            calc (a, b) {
                return ((a * b) / 100).toFixed(2)
            }
        }
    }
</script>

<style scoped>
    .wrapper{
        width: 750px;
    }
    .iconfont {
        font-family: iconfont;
    }
    .t-h1{
        margin-top: 48px;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .content{
        margin-bottom: 112px;
        width: 750px;
        /*background-color: black;*/
    }
    .overflow-box{
        flex-direction: row;
        justify-content: center;
    }
    .overflow-box1{
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-top: 24px;
    }
    .gift-v{
        width: 686px;
        background-color: #FFFFFF;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        margin: 24px 0;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .g-v-1{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
        padding: 30px 36px 30px 36px;
        /*margin-left: 6px;*/
    }
    .gift-vw{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .gift-vw1{
        font-size: 24px;
        color: #53B2EA;
        text-align: left;
    }
    .gift-vw2{
        color: rgba(0,0,0,.54);
        text-align: left;
        font-weight: 900;
        margin-right: 30px;
        margin-left: 15px;
    }
    .bottom-btn{
        background-color: #fff;
        /*box-shadow:  0 -2px 3px 0 rgba(0,0,0,0.24);*/
        border-top-width: 1px;
        border-top-color: rgba(0,0,0,.12);
        border-top-style: solid ;
        width: 750px;
        height: 112px;
        position: fixed;
        bottom: 0;
        right: 0;
        overflow: hidden;
    }
    .bb-d{
        height: 112px;
        flex-direction: row;
        justify-content: space-between;
    }
    .bb-d-r{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        margin-left: 34px;
    }
    .bb-dr-t{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
        text-align: left;
        /*margin-left: 18px;*/
    }
    .bb-dr-t{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
        text-align: left;
        /*margin-left: 18px;*/
    }
    .bb-d-l{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .bb-dl-p{
        font-size: 28px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        font-weight: 700;
    }
    .bb-dl{
        font-size: 24px;
    }
    .bb-dl-b{
        height: 80px;
        width: 220px;
        background-color: #EF8A31;
        border-radius: 8px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin-right: 32px;
        margin-left: 20px;
    }
    .bb-dl-bf{
        color: white;
        font-weight: 700;
        font-size: 28px;
    }
</style>
