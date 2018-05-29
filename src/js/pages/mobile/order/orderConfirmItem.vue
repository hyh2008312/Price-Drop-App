<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="i-gd"  @click="jumpWeb()">
                <div class="gd-bg">
                    <image class="gd-img" resize="cover" :src="order.mainImage || src"></image>
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
            <div class="gb-center">
                <div>
                    <text class="gd-text">Subtotal</text>
                    <text class="gd-text">Shipping</text>
                    <text class="gd-text">Tax</text>
                    <text class="gd-text">Price Drop</text>
                    <text class="gd-text-1">Total Price</text>
                </div>
                <div class="gb-center-right">
                    <text class="gb-text">Rs.{{order.salePrice}}</text>
                    <text class="gb-text">Rs.{{order.shippingPrice||'0.00'}}</text>
                    <text class="gb-text">Rs.0.00</text>
                    <text class="gb-text gb-text-color">- Rs.{{order.salePrice - order.currentPrice}}</text>
                    <text class="gb-text-1">Rs.{{order.currentPrice}}</text>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        props: ['order'],
        data () {
            return {
                src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
            }
        },
        methods: {
            jumpWeb () {
                // if (!url) return;
                this.$router.open({
                    name: 'goods.details'
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
