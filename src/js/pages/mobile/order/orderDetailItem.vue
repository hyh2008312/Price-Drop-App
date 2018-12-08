<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="i-gd"  @click="jumpWeb()">
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
            <div class="gb-center">
                <div>
                    <text class="gd-text">Subtotal</text>
                    <text class="gd-text">Shipping</text>
                    <text class="gd-text">Tax</text>
                    <text class="gd-text" v-if="order.orderType == 'Cut'">Price Drop</text>
                    <text class="gd-text" v-if="(order.orderType == 'Normal' || order.orderType == 'Flash') && order.voucherShare > 0">Voucher</text>
                    <text class="gd-text" v-if="order.balanceUsed && order.balance > 0">Wallet</text>
                    <text class="gd-text" v-if="parseInt(order.paymentDiscount)>0">Payment Discount</text>
                    <text class="gd-text-1">Total</text>
                </div>
                <div class="gb-center-right">
                    <text class="gb-text" v-if="order.orderType == 'Normal' || order.orderType == 'Flash' || order.orderType == 'Lottery'">₹{{((order.paymentAmount * 100 + order.voucherShare * 100 - order.shippingExclTax * 100) / 100).toFixed(2)}}</text>
                    <text class="gb-text" v-if="order.orderType == 'Cut'">₹{{order.totalExclTax}}</text>
                    <text class="gb-text">₹{{order.shippingExclTax}}</text>
                    <text class="gb-text">₹0.00</text>
                    <text class="gb-text gb-text-color" v-if="order.orderType == 'Cut'">- ₹{{((order.totalExclTax * 100 + order.shippingExclTax * 100 - order.paymentAmount * 100) / 100).toFixed(2)}}</text>
                    <text class="gd-text gb-text-color" v-if="(order.orderType == 'Normal'|| order.orderType == 'Flash') && order.voucherShare > 0">- ₹{{order.voucherShare}}</text>

                    <text class="gd-text gb-text-color" v-if="order.balanceUsed && order.balance > 0">- ₹{{order.balance}}</text>
                    <text class="gb-text gb-text-color " v-if="parseInt(order.paymentDiscount)>0">- ₹{{order.paymentDiscount}}</text>

                    <text class="gb-text-1">₹{{order.realAmount}}</text>
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
        props: ['order'],
        methods: {
            jumpWeb () {
                if (this.order.orderType == 'Lottery') return;
                this.$router.open({
                    name: 'goods.details',
                    params: {
                        id: this.order.lines[0].productId
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
            }
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        font-family: ProximaNova;
        background-color: #fff;
        width: 750px;
    }

    .gb-center{
        padding: 16px 32px 32px;
        width: 750px;
        flex-direction: row;
        justify-content: space-between;
        align-items: stretch;
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
        margin-top: 16px;
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

    .gd-count{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.38);
        margin-right: 16px;
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

    .gb-text{
        margin-top: 4px;
        font-size: 24px;
        line-height: 40px;
        text-align: right;
    }

    .gb-center-right{
        align-items: flex-end;
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
