<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="gb-bg">
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Canceled'">&#xe711;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Unpaid'">&#xe702;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Packing' || order.orderStatus == 'Audit canceled'">&#xe70d;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Shipped'">&#xe712;</text>
                <text class="gb-icon iconfont" v-if="order.orderStatus == 'Completed'">&#xe6ed;</text>
                <text class="gb-text" v-if="order.orderStatus != 'Unpaid' && order.orderStatus != 'Canceled' && order.orderStatus != 'Packing'">{{order.orderStatus == 'Audit canceled' ? 'Preparing': order.orderStatus}}</text>
                <text class="gb-text" v-if="order.orderStatus == 'Unpaid'">Pending</text>
                <text class="gb-text" v-if="order.orderStatus == 'Canceled'">Cancelled</text>
                <text class="gb-text" v-if="order.orderStatus == 'Packing'">Preparing</text>
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
        </div>
    </div>
</template>
<script>
    import line from './orderDetailLine';
    import { WxcCountdown } from 'weex-ui';

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
        methods: {}
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

    .gd-text{
        font-size: 24px;
        line-height: 28px;
    }

    .gd-text-color{
        color: #EF8A31;
    }

</style>
