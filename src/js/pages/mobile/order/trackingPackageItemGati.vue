<template>
    <div class="wrapper">
        <div class="gb-box" v-if="address">
            <div class="gb-box-left">

            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe716;</text>
            </div>
            <div class="gb-box-right">
                <text class="gb-text">{{address}}</text>
            </div>
        </div>
        <div class="gb-box" v-if="order">
            <div class="gb-box-left">
                <text class="gb-text-2">{{getYear(order.DateTime)}}</text>
                <text class="gb-text-2">{{getMonth(order.DateTime)}}</text>
                <text class="gb-text-3">{{getHour(order.DateTime)}}</text>
            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon" v-if="getDelivered(order.Info)">&#xe6fb;</text>
                <div class="gb-icon" v-if="order.Info == 'Out for next station' || order.Info == 'In Transit' ||  order.Info == 'Arrived' ||  order.Info == 'Check in'"></div>
            </div>
            <div class="gb-box-right">
                <text class="gb-text" :class="[order.Info == 'Out for next station' || order.Info == 'In Transit'? 'gb-text-color': '']">{{order.Info}}</text>
                <text class="gb-text-1" :class="[order.Info == 'Out for next station' || order.Info == 'In Transit'? 'gb-text-color': '']">{{order.Place}}</text>
            </div>
        </div>
        <div class="gb-box" v-if="shippedTime">
            <div class="gb-box-left">
                <text class="gb-text-2">{{getNewYear(shippedTime)}}</text>
                <text class="gb-text-2">{{getNewMonth(shippedTime)}}</text>
                <text class="gb-text-3">{{getNewHour(shippedTime)}}</text>
            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe6fb;</text>
            </div>
            <div class="gb-box-right">
                <text class="gb-text">Your order has been shipped out from our warehouse. Your tracking details will be updated by courier later.</text>
            </div>
        </div>
        <div class="gb-box" v-if="paidTime">
            <div class="gb-box-left">
                <text class="gb-text-2">{{getNewYear(paidTime)}}</text>
                <text class="gb-text-2">{{getNewMonth(paidTime)}}</text>
                <text class="gb-text-3">{{getNewHour(paidTime)}}</text>
            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe6fb;</text>
            </div>
            <div class="gb-box-right">
                <text class="gb-text">The manufacturer is preparing for your order now. Your products will be shipped to our warehouse in 3 days.</text>
            </div>
        </div>
        <div class="gb-box" v-if="paidTime">
            <div class="gb-box-left">
                <text class="gb-text-2">{{getNewYear(paidTime)}}</text>
                <text class="gb-text-2">{{getNewMonth(paidTime)}}</text>
                <text class="gb-text-3">{{getNewHour(paidTime)}}</text>
            </div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe6fb;</text>
            </div>
            <div class="gb-box-right">
                <text class="gb-text">Your order has been confirmed by manufacturer.</text>
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
            paidTime: {
                default: false
            },
            shippedTime: {
                default: false
            }
        },
        data () {
            return {
                src: 'bmlocal://assets/occupy.png'
            }
        },
        methods: {
            getYear (str) {
                if (str == '') return '';
                return str.split(' ')[0].split('-')[0]
            },
            getMonth (str) {
                if (str == '') return '';
                return str.split(' ')[0].split('-')[1] + '-' + str.split(' ')[0].split('-')[2]
            },
            getHour (str) {
                if (str == '') return '';
                return str.split(' ')[1]
            },
            getNewYear (str) {
                return new Date(str).getFullYear();
            },
            getNewMonth (str) {
                return (new Date(str).getMonth() + 1 < 10 ? '0' + (new Date(str).getMonth() + 1) : (new Date(str).getMonth() + 1)) +
                     '-' + (new Date(str).getDate() < 10 ? '0' + new Date(str).getDate() : new Date(str).getDate())
            },
            getNewHour (str) {
                return (new Date(str).getHours() < 10 ? '0' + new Date(str).getHours() : new Date(str).getHours()) + ':' +
                    (new Date(str).getMinutes() < 10 ? '0' + new Date(str).getMinutes() : new Date(str).getMinutes()) + ':' +
                    (new Date(str).getSeconds() < 10 ? '0' + new Date(str).getSeconds() : new Date(str).getSeconds());
            },
            getDelivered (info) {
                if (info.split('Delivered').length >= 2) {
                    return true;
                } else {
                    return false;
                }
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
        margin-bottom: 40px;
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
        left: 142px;
    }

    .gb-box-right{
        width: 592px;
        padding-left: 26px;
        padding-right: 40px;
        padding-bottom: 64px;
    }

    .gb-box-icon{
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
        width: 32px;
        height: 32px;
        background-color: #fff;
    }

    .gb-icon{
        width: 16px;
        height: 16px;
        background-color: #D8D8D8;
        border-radius: 8px;
        margin-top: 10px;
    }

</style>
