<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="gb-top">
                <text class="gb-date">{{order.date}}</text>
                <text class="gb-status">{{order.status}}</text>
            </div>
            <div class="i-gd"  @click="jumpWeb()">
                <div class="gd-bg">
                    <image class="gd-img" resize="cover" :src="order.src || src"></image>
                </div>
                <div class="gd-bg-right">
                    <text class="gd-tlt">{{order.title}}</text>
                    <div class="gd-bg-bottom">
                        <text class="gd-info">{{order.attribute}}</text>
                        <div class="gd-bg-bottom-right">
                            <text class="gd-price">Rs.{{order.originalPrice}}</text>
                            <text class="gd-count"> x {{order.number}}</text>
                        </div>
                    </div>
                </div>
            </div>
            <div class="gb-center">
                <text class="gd-text">Dropped:  </text>
                <text class="gb-price-1">-Rs.{{order.droppedPrice}}</text>
                <text class="gd-text">Final:  </text>
                <text class="gb-price-2">Rs.{{order.saleUnitPrice}}</text>
            </div>
            <div class="gb-bottom">
                <div class="gb-bottom-left">
                    <text class="iconfont gb-time">&#xe6fa;</text>
                    <text class="gd-text">  Ends in</text>
                    <wxc-countdown tpl="{h}:{m}:{s}"
                                   :time="time"
                                   :timeBoxStyle="{backgroundColor: 'transparent', height: '36px', width: '36px','border-radius': '4px'}"
                                   :timeTextStyle="{fontSize: '24px', color: '#EF8A31'}"
                                   :dotTextStyle="{color: '#EF8A31', fontSize: '24px'}"
                                   :dotBoxStyle="{width: '10px'}"
                                   :style="{justifyContent: 'center'}">
                    </wxc-countdown>
                </div>
                <text class="gd-button" @click="openBottomPopup">Pay Now</text>
            </div>
        </div>
    </div>
</template>
<script>
    import { WxcCountdown, WxcPopup } from 'weex-ui'

    export default {
        components: { WxcCountdown, WxcPopup },
        props: ['order'],
        data () {
            return {
                src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png',
                time: new Date().getTime() + 86400000 + ''
            }
        },
        methods: {
            jumpWeb () {
                // if (!url) return;
                this.$router.open({
                    name: 'order.detail'
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
        padding-left: 32px;
        padding-right: 32px;
        flex-direction: row;
        justify-content: space-between;
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
</style>
