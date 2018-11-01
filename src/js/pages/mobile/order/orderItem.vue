<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="gb-top" @click="jumpWeb()">
                <text class="gb-date">{{order.created | formatDate('MMMM DD, YYYY hh:mm:ss')}}</text>
                <text class="gb-status" v-if="order.orderStatus != 'Canceled' && order.orderStatus != 'Completed' && order.orderStatus !='Unpaid' && order.orderStatus != 'Audit canceled' && order.orderStatus != 'Packing'">{{order.orderStatus}}</text>
                <text class="gb-status" v-if="order.orderStatus == 'Audit canceled'">Pending Cancel</text>
                <text class="gb-status" v-if="order.orderStatus == 'Paid'">Confirmed</text>
                <text class="gb-status" v-if="order.orderStatus == 'Unpaid'">Pending</text>
                <text class="gb-status" v-if="order.orderStatus == 'Canceled'">Cancelled</text>
                <text class="gb-status" v-if="order.orderStatus == 'Completed'">Delivered</text>
                <text class="gb-status" v-if="order.orderStatus == 'Packing'">Preparing</text>
            </div>
            <div class="i-gd" @click="jumpWeb()">
                <div class="gd-bg">
                    <preload class="gd-img" resize="cover" :src="order.lines[0].mainImage"></preload>
                </div>
                <div class="gd-bg-right">
                    <text class="gd-tlt">{{order.lines[0].title}}</text>
                    <div class="gd-bg-bottom">
                        <text class="gd-info">{{order.lines[0].attributes}}</text>
                        <div class="gd-bg-bottom-right">
                            <text class="gd-count"> x {{order.lines[0].quantity}}</text>
                        </div>
                    </div>
                </div>
            </div>
            <div class="gb-center" @click="jumpWeb()">
                <text class="gd-text">Total: </text>
                <text class="gb-price-2">₹{{order.paymentAmount}}</text>
            </div>
            <div class="gb-bottom" v-if="order.orderStatus == 'Unpaid' && order">
                <div class="gb-bottom-left">
                    <text class="iconfont gb-time">&#xe6fa;</text>
                    <text class="gd-text">  Ends in</text>
                    <wxc-countdown tpl="{h}:{m}:{s}"
                                   :time="order.cancelTimestamp * 1000"
                                   :timeBoxStyle="{backgroundColor: 'transparent', height: '36px', width: '36px','border-radius': '4px'}"
                                   :timeTextStyle="{fontSize: '24px', color: '#EF8A31'}"
                                   :dotTextStyle="{color: '#EF8A31', fontSize: '24px'}"
                                   :dotBoxStyle="{width: '10px'}"
                                   :style="{justifyContent: 'center'}">
                    </wxc-countdown>
                </div>
                <div class="gb-bottom-2">
                    <text class="od-button-1 gt-mr" @click="deleteOrder">Delete</text>
                    <text class="gd-button" @click="openBottomPopup">Pay Now</text>
                </div>
            </div>
            <div class="gb-bottom-1" v-if="order.orderStatus == 'Packing'">
                <text class="gd-button" @click="tracking">Track Package</text>
            </div>
            <div class="gb-bottom-1" v-if="order.orderStatus == 'Paid'">
                <text class="od-button-1" @click="cancel">Cancel Order</text>
            </div>
            <div class="gb-bottom-1" v-if="order.orderStatus == 'Audit canceled'">
                <text class="od-text-2">Order cancellation pending</text>
            </div>
            <div class="gb-bottom-1" v-if="order.orderStatus == 'Shipped'">
                <text class="od-button-1 gt-mr" @click="tracking">Track Package</text>
                <text class="gd-button" @click="receipt">Confirm Delivery</text>
            </div>
            <div class="gb-bottom-1" v-if="order.orderStatus == 'Canceled'">
                <text class="od-button-1 gt-mr" @click="deleteOrder">Delete</text>
                <text class="gd-button" @click="buyProduct">Buy Again</text>
            </div>
            <div class="gb-bottom-1" v-if="order.orderStatus == 'Partially Refunded'">
                <text class="od-button-1 gt-mr" @click="deleteOrder">Delete</text>
                <text class="gd-button" @click="buyProduct">Buy Again</text>
            </div>
            <div class="gb-bottom-1" v-if="order.orderStatus == 'Fully Refunded'">
                <text class="od-button-1 gt-mr" @click="deleteOrder">Delete</text>
                <text class="gd-button" @click="buyProduct">Buy Again</text>
            </div>
            <div class="gb-bottom-1" v-if="order.orderStatus == 'Completed'">
                <text class="od-button-1 gt-mr" @click="deleteOrder">Delete</text>
                <text class="od-button-1 gt-mr" @click="buyProduct">Buy Again</text>
                <text class="gd-button" @click="tracking">Track Package</text>
            </div>
        </div>
    </div>
</template>
<script>
    import preload from '../common/preloadImg';
    import { WxcCountdown, WxcPopup } from 'weex-ui';
    import dayjs from 'dayjs'
    Vue.filter('formatDate', function (str, hmr) {
        return dayjs(new Date(str)).format(hmr)
    })

    export default {
        components: { WxcCountdown, WxcPopup, preload },
        props: ['order', 'index'],
        data () {
            return {
                src: 'bmlocal://assets/occupy.png'
            }
        },
        methods: {
            jumpWeb () {
                // if (!url) return;
                this.$router.open({
                    name: 'order.detail',
                    params: {
                        id: this.order.id
                    }
                })
            },
            openBottomPopup () {
                this.$emit('pay', {
                    status: 'pay',
                    data: {
                        item: this.order
                    }
                })
            },
            cancel () {
                this.$emit('cancel', {
                    status: 'cancel',
                    data: {
                        index: this.index,
                        id: this.order.id
                    }
                })
            },
            tracking () {
                this.$router.open({
                    name: 'order.address.tracking',
                    type: 'PUSH',
                    params: {
                        id: this.order.id,
                        status: this.order.orderStatus,
                        carrierCode: this.order.carrierCode
                    }
                });
            },
            buyProduct () {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: this.order.lines[0].productId
                    }
                })
            },
            deleteOrder () {
                this.$emit('deleteOrder', {
                    status: 'deleteOrder',
                    data: {
                        index: this.index,
                        id: this.order.id
                    }
                })
            },
            receipt () {
                this.$emit('receiptOrder', {
                    status: 'receiptOrder',
                    data: {
                        index: this.index,
                        id: this.order.id,
                        orderType: this.order.orderType
                    }
                });
            }
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        background-color: #fff;
        width: 750px;
    }

    .gb-top{
        height: 80px;
        padding-left: 32px;
        padding-right: 32px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .gb-bottom{
        border-top-width: 1px;
        border-top-color: rgba(0,0,0,0.08);
        border-top-style: solid;
        width: 750px;
        height: 98px;
        padding-left: 16px;
        padding-right: 16px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .gb-bottom-1{
        border-top-width: 1px;
        border-top-color: rgba(0,0,0,0.08);
        border-top-style: solid;
        width: 750px;
        height: 98px;
        padding-left: 16px;
        padding-right: 16px;
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
    }

    .gb-bottom-2{
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
    }


    .gb-bottom-left{
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }

    .gb-center{
        height: 80px;
        width: 750px;
        padding-left: 32px;
        padding-right: 32px;
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
    }

    .gb-box {
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }

    .i-gd {
        width: 718px;
        height: 160px;
        margin-left: 16px;
        margin-right: 16px;
        background-color: rgba(0,0,0,.04);
        border-radius: 10px;
        flex-direction: row;
        justify-content: flex-start;
        align-items: stretch;
    }

    .gd-bg {
        width: 160px;
        height: 160px;
    }

    .gd-bg-right {
        margin-left: 24px;
        width: 534px;
        height: 160px;
        justify-content: space-between;
        align-items: start;
    }

    .gd-img {
        width: 158px;
        height: 158px;
        border-radius: 8px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }

    .gd-tlt {
        margin-top: 16px;
        font-size: 28px;
        font-weight: bold;
        line-height: 40px;
        width: 518px;
        overflow: hidden;
        lines: 2;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-info {
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.54);
    }

    .gd-price {
        font-size: 24px;
        line-height: 28px;
    }

    .gd-count{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.38);
        margin-right: 16px;
    }

    .gb-date{
        font-size: 24px;
        line-height: 28px;
    }

    .gb-status{
        font-size: 24px;
        line-height: 28px;
        color: #EF8A31;
    }

    .gd-bg-bottom{
        margin-bottom: 16px;
        font-size: 24px;
        line-height: 28px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .gd-bg-bottom-right{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .gd-text{
        font-size: 20px;
        line-height: 24px;
    }

    .gb-price-1{
        font-size: 24px;
        line-height: 28px;
        color: #EF8A31;
        margin-right: 24px;
    }

    .gb-price-2{
        font-size: 30px;
        line-height: 36px;
        font-weight: bold;
    }

    .gd-button {
        text-align: center;
        font-size: 24px;
        line-height: 56px;
        font-weight: bold;
        border-radius: 4px;
        padding-left: 26px;
        padding-right: 26px;
        background-color: #EF8A31;
        color: #fff;
    }

    .gb-time{
        font-size: 24px;
        line-height: 24px;
    }

    .od-button-1{
        font-size: 24px;
        line-height: 52px;
        text-align: center;
        padding: 0 24px;
        background-color: #fff;
        border-radius: 4px;
        color: #EF8A31;
        border-color: #EF8A31;
        border-style: solid;
        border-width: 2px;
    }

    .od-text-2{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.54);
    }

    .gt-mr{
        margin-right: 16px;
    }
</style>
