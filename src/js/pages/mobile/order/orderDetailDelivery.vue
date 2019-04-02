<template>
    <div class="wrapper">
        <div class="gb-box-1">
            <text class="gb-title">Delivery</text>
            <div class="gb-center">
                <text class="gb-text">Estimated Arrival</text>
                <text class="gb-text gb-text-color">{{formatMinDate(order,  'MMM DD, YYYY')}} - {{formatMaxDate(order,  'MMM DD, YYYY')}}</text>
            </div>
            <div class="gb-center">
                <text class="gb-text">Order Preparing Before Shipping</text>
                <text class="gb-text gb-text-color">{{order.processingTimeMin || 0}}-{{order.processingTimeMax || 0}} days</text>
            </div>
            <div class="gb-center">
                <text class="gb-text">Shipping Time After Processing</text>
                <text class="gb-text gb-text-color">{{order.shippingTimeMin || 0}}-{{order.shippingTimeMax || 0}} days</text>
            </div>
            <text class="gb-text gb-text-color-1">Please Note: In some cases, order might be delayed for a few days due to Custom Duty Check.</text>
        </div>
    </div>
</template>
<script>
    import dayjs from 'dayjs';

    export default {
        props: ['order'],
        methods: {
            formatMinDate (order, hmr) {
                return dayjs(new Date().getTime() + (order.shippingTimeMax + order.processingTimeMin + 1) * 24 * 60 * 60 * 1000).format(hmr);
            },
            formatMaxDate (order, hmr) {
                return dayjs(new Date().getTime() + (order.shippingTimeMax + order.processingTimeMax + 1) * 24 * 60 * 60 * 1000).format(hmr);
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

    .gb-text-color-1{
        color: rgba(0,0,0,0.54);
    }

    .gb-center-right{
        align-items: flex-end;
    }
</style>
