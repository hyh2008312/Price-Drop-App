<template>
    <div class="wrapper">
        <div class="gb-box" v-if="address">
            <div class="gb-box-left">

            </div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe716;</text>
            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-right">
                <text class="gb-text">{{address}}</text>
            </div>
        </div>
        <div class="gb-box" v-if="order">
            <div class="gb-box-left">
                <text class="gb-text-2">{{getYear(order.Date)}}</text>
                <text class="gb-text-2">{{getMonth(order.Date)}}</text>
                <text class="gb-text-3">{{getHour(order.Date)}}</text>
            </div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon" v-if="order.checkpoint_status == 'delivered'">&#xe6fb;</text>
                <div class="gb-icon" v-if="order.checkpoint_status == 'transit'"></div>
            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-right">
                <text class="gb-text" :class="[order.checkpoint_status == 'transit'? 'gb-text-color': '']">{{order.StatusDescription}}</text>
                <text class="gb-text-1" :class="[order.checkpoint_status == 'transit'? 'gb-text-color': '']">{{order.Details}}</text>
            </div>
        </div>
        <div class="gb-box" v-if="payTime">
            <div class="gb-box-left">
                <text class="gb-text-2">{{getYear(payTime)}}</text>
                <text class="gb-text-2">{{getMonth(payTime)}}</text>
                <text class="gb-text-3">{{getHour(payTime)}}</text>
            </div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe716;</text>
            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-right">
                <text class="gb-text gb-text-colo">Your order is being processed and packed.</text>
            </div>
        </div>
        <div class="gb-box" v-if="shippedTime">
            <div class="gb-box-left">
                <text class="gb-text-2">{{getYear(shippedTime)}}</text>
                <text class="gb-text-2">{{getMonth(shippedTime)}}</text>
                <text class="gb-text-3">{{getHour(shippedTime)}}</text>
            </div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe716;</text>
            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-right">
                <text class="gb-text gb-text-colo">Your order has been shipped.</text>
            </div>
        </div>
        <div class="gb-box" v-if="payTime">
            <div class="gb-box-left">
                <text class="gb-text-2">{{getYear(payTime)}}</text>
                <text class="gb-text-2">{{getMonth(payTime)}}</text>
                <text class="gb-text-3">{{getHour(payTime)}}</text>
            </div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe716;</text>
            </div>
            <div class="gb-box-right">
                <text class="gb-text gb-text-colo">Your order is being processed and packed.</text>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        props: {
            order: {
                default: false
            },
            address: {
                default: false
            },
            payTime: {
                default: false
            },
            shippedTime: {
                efault: false
            }
        },
        data () {
            return {
                src: 'bmlocal://assets/occupy.png'
            }
        },
        methods: {
            getYear (str) {
                return str.split(' ')[0].split('-')[0]
            },
            getMonth (str) {
                return str.split(' ')[0].split('-')[1] + '-' + str.split(' ')[0].split('-')[2]
            },
            getHour (str) {
                return str.split(' ')[1]
            }
        }
    }
</script>
<style scoped>
    .iconfont{
        font-family: iconfont;
    }

    .wrapper{
        background-color: #fff;
        width: 750px;
    }

    .gb-box{
        flex-direction: row;
        align-items: stretch;
    }

    .gb-text{
        font-size: 24px;
        line-height: 28px;
        margin-bottom: 6px;
    }

    .gb-text-1{
        font-size: 24px;
        line-height: 40px;
        margin-bottom: 64px;
    }

    .gb-text-2{
        font-size: 24px;
        line-height: 28px;
        margin-bottom: 4px;
    }

    .gb-text-3{
        font-size: 20px;
        line-height: 24px;
        color: rgba(0,0,0,0.38);
    }

    .gb-text-color{
        color: rgba(0,0,0,0.38);
    }

    .gb-box-left{
        width: 126px;
        padding-right: 26px;
        align-items: flex-end;
    }

    .gb-box-center{
        width: 32px;
        align-items: center;
    }

    .gb-box-line{
        position: absolute;
        width: 2px;
        background-color: rgba(0,0,0,0.08);
        top: 0;
        bottom: 0;
        left: 140px;
    }

    .gb-box-right{
        width: 592px;
        padding-left: 26px;
        padding-right: 40px;
    }

    .gb-box-icon{
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
    }

    .gb-icon{
        width: 16px;
        height: 16px;
        background-color: #D8D8D8;
        border-radius: 8px;
        margin-top: 10px;
    }

</style>
