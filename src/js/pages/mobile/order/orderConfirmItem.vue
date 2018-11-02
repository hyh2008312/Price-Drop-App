<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="i-gd"  @click="jumpWeb()">
                <div class="gd-bg">
                    <preload class="gd-img" :src="order.mainImage"></preload>
                </div>
                <div class="gd-bg-right">
                    <text class="gd-tlt">{{order.title}}</text>
                    <div class="gd-bg-bottom">
                        <text class="gd-info">{{order.attributes}}</text>
                        <div class="gd-bg-bottom-right">
                            <text class="gd-count"> x {{order.quantity}}</text>
                        </div>
                    </div>
                </div>
            </div>
            <div class="gb-gift-bg" v-if="order.proId == 'direct' || order.proId == 'flash'">
                <div class="gb-gift" @click="jumpCard">
                    <div>
                        <text class="gd-text-2">Gift Voucher</text>
                    </div>
                    <div class="gb-center-right-1">
                        <text class="gb-text-3" v-if="card === false">Choose a Gift Voucher</text>
                        <text class="gb-text-5" v-if="card === ''">No Gift Voucher Available</text>
                        <text class="gb-text-3" v-if="card">₹{{card.share}} Gift Voucher</text>
                        <text class="iconfont gb-text-4">&#xe626;</text>
                    </div>
                </div>
            </div>
            <div class="gb-center">
                <div>
                    <text class="gd-text">Subtotal</text>
                    <text class="gd-text">Shipping</text>
                    <text class="gd-text">Tax</text>
                    <text class="gd-text" v-if="order.proId == 'drop'">Price Drop</text>
                    <text class="gd-text" v-if="card && card != ''">Gift Voucher</text>
                    <text class="gd-text-1">Total Price</text>
                </div>
                <div class="gb-center-right">
                    <text class="gb-text" v-if="order.proId == 'direct'">₹{{parseInt(order.currentPrice).toFixed(2)}}</text>
                    <text class="gb-text" v-if="order.proId == 'flash'">₹{{parseInt(order.currentPrice).toFixed(2)}}</text>
                    <text class="gb-text" v-if="order.proId == 'lottery'">₹{{parseInt(order.currentPrice).toFixed(2)}}</text>
                    <text class="gb-text" v-if="order.proId == 'drop'">₹{{parseInt(order.salePrice).toFixed(2)}}</text>
                    <text class="gb-text">₹{{order.shippingPrice||'0.00'}}</text>
                    <text class="gb-text">₹0.00</text>
                    <text class="gb-text gb-text-color" v-if="order.proId == 'drop'">- ₹{{parseInt((order.salePrice * 100 - order.total * 100) / 100).toFixed(2)}}</text>
                    <text class="gb-text gb-text-color" v-if="card">- ₹{{card.share.toFixed(2)}}</text>
                    <text class="gb-text-1">₹{{getOrderTotal(order, card)}}</text>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import preload from '../common/preloadImg';
    export default {
        components: {
            preload
        },
        props: ['order', 'card'],
        methods: {
            jumpWeb () {
                if (this.order.productId) {
                    this.$router.open({
                        name: 'goods.details',
                        params: {
                            id: this.order.productId
                        }
                    });
                }
            },
            jumpCard () {
                this.$router.open({
                    name: 'order.detail.card',
                    params: {
                        cardMoney: this.order.total,
                        card: this.card
                    }
                });
            },
            getOrderTotal (order, card) {
                if (card) {
                    return ((order.total * 100 - card.share * 100) / 100).toFixed(2);
                } else {
                    return order.total;
                }
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
        width: 686px;
        border-radius: 16px;
    }

    .gb-center{
        padding: 16px 32px 32px;
        width: 686px;
        flex-direction: row;
        justify-content: space-between;
        align-items: stretch;
    }

    .gb-gift-bg{
        padding: 0 32px;
        width: 686px;
    }

    .gb-gift{
        padding: 16px 0 16px;
        flex-direction: row;
        justify-content: space-between;
        align-items: stretch;
        border-bottom-style: solid;
        border-bottom-width: 1px;
        border-bottom-color: rgba(0,0,0,0.12);
    }

    .gb-box {
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }

    .i-gd {
        width: 622px;
        height: 176px;
        margin-left: 32px;
        margin-right: 32px;
        margin-top: 32px;
        padding-bottom: 16px;
        border-bottom-style: solid;
        border-bottom-width: 1px;
        border-bottom-color: rgba(0,0,0,0.12);
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
        width: 438px;
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

    .gd-count{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.38);
        margin-right: 16px;
    }

    .gd-bg-bottom{
        margin-bottom: 8px;
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
       margin-top: 4px;
       font-size: 24px;
       line-height: 40px;
    }

    .gd-text-1{
        margin-top: 4px;
        font-size: 28px;
        line-height: 40px;
        font-weight: bold;
    }

    .gd-text-2{
        font-family: ProximaNova;
        font-weight: bold;
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        line-height: 40px;
    }

    .gb-text-3{
        font-family: ProximaNova;
        font-size: 24px;
        color: #53B2EA;
        letter-spacing: 0;
        text-align: left;
        line-height: 40px;
    }

    .gb-text-4{
        margin-left: 16px;
        font-size: 24px;
        font-weight: bold;
        color: rgba(0,0,0,0.54);
    }

    .gb-text-5{
        font-family: ProximaNova;
        font-size: 24px;
        color: rgba(0,0,0,0.38);
        letter-spacing: 0;
        text-align: left;
        line-height: 40px;
    }

    .gb-text{
        margin-top: 4px;
        font-size: 24px;
        line-height: 40px;
        text-align: right;
    }

    .gb-center-right{
        align-items: flex-end;
    }

    .gb-center-right-1{
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
    }

    .gb-text-1{
        margin-top: 4px;
        font-size: 28px;
        line-height: 40px;
        text-align: right;
        font-weight: bold;
    }

    .gb-text-color{
        color: #EF8A31;
    }
</style>
