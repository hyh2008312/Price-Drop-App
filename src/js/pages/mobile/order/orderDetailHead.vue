<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="gb-bg">
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Canceled'">&#xe711;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Unpaid'">&#xe702;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Paid'">&#xe746;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Packing' || order.orderStatus == 'Audit canceled'">&#xe70d;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Shipped'">&#xe712;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Completed'">&#xe6ed;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Fully Refunded'">&#xe758;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Partially Refunded'">&#xe758;</text>
                <text class="gb-text" v-if="order.orderStatus == 'Fully Refunded'">Fully Refunded</text>
                <text class="gb-text" v-if="order.orderStatus == 'Partially Refunded'">Partially Refunded</text>
                <text class="gb-text" v-if="order.orderStatus == 'Audit canceled'">Pending Cancel</text>
                <text class="gb-text" v-if="order.orderStatus == 'Paid'">Confirmed</text>
                <text class="gb-text" v-if="order.orderStatus == 'Unpaid'">Pending</text>
                <text class="gb-text" v-if="order.orderStatus == 'Canceled'">Cancelled</text>
                <text class="gb-text" v-if="order.orderStatus == 'Packing'">Preparing</text>
                <text class="gb-text" v-if="order.orderStatus == 'Completed'">Delivered</text>
            </div>
            <div class="gb-bg-1" v-if="order.orderStatus == 'Unpaid'">
                <text class="gb-icon iconfont gd-text-color">&#xe703;</text>
                <text class="gd-text gd-text-color">Ends in</text>
                <wxc-countdown tpl="{h}:{m}:{s}"
                               :time="order.cancelTimestamp * 1000"
                               :timeBoxStyle="{backgroundColor: 'transparent', height: '28px', width: '28px','border-radius': '4px'}"
                               :timeTextStyle="{fontSize: '24px', color: '#EF8A31'}"
                               :dotTextStyle="{color: '#EF8A31', fontSize: '24px'}"
                               :dotBoxStyle="{width: '6px'}"
                               :style="{justifyContent: 'center'}">
                </wxc-countdown>
            </div>
            <div class="gb-bg-2" v-if="order.refundAmount > 0">
                <text class="gb-icon iconfont gd-text-color"></text>
                <text class="gd-text gd-text-color">Refund Amount: </text>
                <text class="gd-text">₹{{order.refundAmount.toFixed(2)}}</text>
            </div>
            <div class="gb-bg-2" v-if="order.refundDate">
                <text class="gb-icon iconfont gd-text-color"></text>
                <text class="gd-text gd-text-color">Refund Date: </text>
                <text class="gd-text">{{formatDate(order.refundDate, 'MMM DD, YYYY hh:mm:ss')}}</text>
            </div>
        </div>
    </div>
</template>
<script>
    import line from './orderDetailLine';
    import { WxcCountdown } from 'weex-ui';
    import dayjs from 'dayjs';

    export default {
        components: {
            'order-line': line,
            WxcCountdown
        },
        props: ['order'],
        data () {
            return {
                src: 'bmlocal://assets/occupy.png'
            }
        },
        methods: {
            formatDate (str, hmr) {
                return dayjs(new Date(str)).format(hmr)
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

    .gb-box {
        padding: 32px;
    }

    .gb-icon{
        font-size: 28px;
        width: 32px;
        text-align: center;
        line-height: 32px;
        margin-right: 24px;
        color: rgba(0,0,0,0.54);
    }

    .gb-text{
        font-size: 28px;
        line-height: 34px;
        color: rgba(0,0,0, 0.87);
    }

    .gb-bg{
        flex-direction: row;
        align-items: center;
    }

    .gb-bg-1{
        margin-top: 22px;
        flex-direction: row;
        align-items: center;
    }

    .gb-bg-2{
        margin-top: 12px;
        flex-direction: row;
        align-items: center;
    }

    .gd-text{
        font-size: 24px;
        line-height: 28px;
    }

    .gd-text-color{
        color: #EF8A31;
    }

</style>
