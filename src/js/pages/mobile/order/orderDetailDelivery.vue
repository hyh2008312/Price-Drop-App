<template>
    <div class="wrapper">
        <div class="gb-box-1">
            <text class="gb-title">Delivery</text>
            <div class="gb-center">
                <div>
                    <text class="gb-text">Order Prcessing</text>
                    <text class="gb-text">Shipping Time</text>
                </div>
                <div class="gb-center-right">
                    <text class="gb-text gb-text-color">3-5 business days</text>
                    <text class="gb-text gb-text-color">{{order.shippingTimeMin || 0}}-{{order.shippingTimeMax || 0}} days</text>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        props: ['order']
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        background-color: #fff;
        width: 750px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }

    .gb-box-1{
        padding: 32px;
    }

    .gb-title{
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
    }

    .gb-text{
        margin-top: 24px;
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0, 0.87);
    }

    .gb-center{
        width: 686px;
        flex-direction: row;
        justify-content: space-between;
        align-items: stretch;
    }

    .gb-text-color{
        color: #EF8A31;
    }

    .gb-center-right{
        align-items: flex-end;
    }
</style>
