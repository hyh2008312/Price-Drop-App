<template>
    <div class="item">
        <div class="wrapper">
            <div class="gb-box">
                <div class="i-gd" @click="jumpCutDetail">
                    <div class="gd-bg">
                        <image class="gd-img" resize="cover"
                               :src="goods.mainImage"></image>
                    </div>
                    <div class="gd-bg-right">
                        <text class="gd-tlt">{{goods.title}}</text>
                        <div class="gb-price">
                            <text class="gd-price-sale">Rs.{{goods.currentPrice}}</text>
                            <text class="gd-price-original">Rs.{{goods.salePrice}}</text>
                        </div>
                        <text class="gd-price-show" v-if="flag">Rs.{{ Math.floor((goods.salePrice - goods.currentPrice)*100)/100 }} price cut by {{goods.cutTimes}} people</text>
                        <div class="gd-cut" v-if="flag">
                            <text class="gd-cut-price">Rs.{{goods.lowestPrice}}</text>
                            <text class="gd-cut-price-tip"> left to unlock lowest price</text>
                        </div>
                        <div class="gd-cut-end"v-if="!flag">
                            <text class="gd-cut-price">Rs.{{ Math.floor((goods.salePrice - goods.currentPrice)*100)/100 }}</text>
                            <text class="gd-price-show-end"> price cut by {{goods.cutTimes}} people</text>
                        </div>
                    </div>
                </div>
            </div>
            <div class="gb-line"></div>
            <!--正在砍价-->
            <div class="gd-handle" v-if="flag">
                <text class="gd-handle-tip">Ends in </text>
                <wxc-countdown tpl="{h}:{m}:{s}"
                               :time="goods.endTimestamp *1000"
                               :timeBoxStyle="{backgroundColor: '#000000', height: '36px', width: '36px','border-radius': '4px'}"
                               :timeTextStyle="{fontSize: '24px', color: '#FFFFFF'}"
                               :dotTextStyle="{color: '#000000', fontSize: '24px'}"
                               :dotBoxStyle="{width: '10px'}"
                               :style="{justifyContent: 'center'}">
                </wxc-countdown>
                <text class="gd-handle-cut" @click="goingCutPrice">Cut Price</text>
            </div>
            <!--砍价结束-->
            <div class="gd-end-handle" v-else>
                <div class="gd-end-handle-state" v-if="goods.operationStatus =='pending' || goods.operationStatus =='unpaid'">
                    <text class="icon-timer">&#xe6fa;</text>
                    <text class="gd-end-handle-state-text">Time Left</text>
                    <wxc-countdown tpl="{h}:{m}:{s}"
                                   :time="goods.cancelTimestamp * 1000"
                                   :timeBoxStyle="{backgroundColor: 'transparent', height: '36px', width: '36px','border-radius': '4px'}"
                                   :timeTextStyle="{fontSize: '24px', color: '#EF8A31'}"
                                   :dotTextStyle="{color: '#EF8A31', fontSize: '24px'}"
                                   :dotBoxStyle="{width: '10px'}"
                                   :style="{justifyContent: 'center'}">
                    </wxc-countdown>
                </div>
                <div class="gd-end-handle-state" v-if="goods.operationStatus =='paid'">
                    <text class="icon-paid">&#xe6fb;</text>
                    <text class="gd-end-handle-state-text">Paid</text>
                </div>
                <div class="gd-end-handle-state" v-if="goods.operationStatus =='overdue'">
                    <text class="icon-expired">&#xe6fe;</text>
                    <text class="gd-end-handle-state-text">Expired</text>
                </div>
                <!--pending-->
                <text class="gd-handle-cut"v-if="goods.operationStatus =='pending'" @click="jumpConfirmOrder">Pay Now</text>
                <!--unpaid-->
                <text class="gd-handle-cut" v-else-if="goods.operationStatus =='unpaid'" @click="jumpOrderDetail">Pay Now</text>
                <!---->
                <text class="gd-handle-cut-again" v-else @click="jumpProductDetail" >Buy Again </text>
            </div>

        </div>
    </div>
</template>


<script>
    import { WxcCountdown } from 'weex-ui'

    export default {
        components: { WxcCountdown },
        props: ['goods', 'flag'],
        data () {
            return {
                TIME: new Date().getTime() + 86400000 + ''
            }
        },
        methods: {
            jumpProductDetail () {
                this.$router.open({
                    name: 'goods.details'
                })
            },
            goingCutPrice () {
                this.$router.open({
                    name: 'drops.cutDetail',
                    params: {
                        isShowSharePanel: true,
                        id: this.goods.id
                    }
                });
            },
            jumpConfirmOrder () {
                this.$router.open({
                    name: 'order.confirm',
                    params: this.goods
                })
            },
            jumpOrderDetail () {
                this.$router.open({
                    name: 'order.detail',
                    params: {
                        id: this.goods.orderId,
                        type: 'drop'
                    }
                })
            },
            jumpCutDetail () {
                this.$router.open({
                    name: 'drops.cutDetail',
                    params: {
                        isShowSharePanel: false,
                        id: this.goods.id
                    }
                });
            }
        }
    }
</script>
<style scoped>

    .icon-expired {
        font-family: iconfont;
        font-size: 24px;
        margin-left: 16px;
        color: #E93131;
    }
    .icon-paid{
        font-family: iconfont;
        font-size: 24px;
        margin-left: 16px;
        color: #26CB03;
    }
    .icon-timer{
        font-family: iconfont;
        font-size: 24px;
        margin-left: 16px;
        color: #EF8A31;

    }
    .item {
        display: flex;
        background-color: #F1F1F1;
        padding-bottom: 16px;
    }

    .wrapper {
        background-color: #fff;
        width: 750px;
        box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.12);
        border-radius: 4px;
    }

    .gb-box {
        margin-top: 32px;
        padding: 0 16px;
        display: flex;
    }

    .i-gd {
        width: 718px;
        height: 270px;
        flex-direction: row;
    }

    .gd-bg {
        width: 240px;
        height: 240px;
    }

    .gd-bg-right {
        margin-left: 32px;
        width: 446px;
        height: 240px;
    }

    .gd-img {
        width: 240px;
        height: 240px;
        border-radius: 8px;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(0, 0, 0, .12);
    }

    .gd-info {
        display: block;
        font-weight: bold;
        margin-top: 16px;
        font-size: 28px;
        width: 446px;
        line-height: 40px;
        color: black;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-tlt {
        line-height: 36px;
        font-size: 24px;
        font-weight: 300;
        width: 446px;
        overflow: hidden;
        height: 72px;
        lines: 2;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-cut {
        display: flex;
        margin-top: 10px;
        flex-direction: row;
        justify-content: flex-start;
    }
    .gd-cut-end {
        display: flex;
        margin-top: 78px;
        flex-direction: row;
        justify-content: flex-start;
    }

    .gd-cut-price-tip {
        font-size: 20px;
        height: 28px;
        font-weight: 400;
        line-height: 28px;
        color: #000000;
    }

    .gd-cut-price {
        font-size: 24px;
        line-height: 28px;
        height: 28px;
        color: #EF8A31;
        font-weight: bold;
    }

    .gb-price {
        display: flex;
        flex-direction: row;
        margin-top: 16px;
        justify-content: flex-start;
    }
    .gd-price-show{
        font-size: 24px;
        color: black;
        font-weight: bold;
        margin-top: 46px;
    }
    .gd-price-show-end{
        font-size: 24px;
        color: black;
        font-weight: bold;
    }

    .gd-price-sale {
        font-size: 28px;
        font-weight: bold;
        color: black;
        line-height: 36px;
        height: 36px;
    }

    .gd-price-original {
        font-size: 20px;
        font-weight: 400;
        color: rgba(0,0,0,0.55);
        margin-left: 16px;
        text-decoration: line-through;
        line-height: 36px;
        height: 36px;
    }

    .gb-line {
        width: 750px;
        height: 1px;
        background-color: black;
        opacity: 0.08;
    }

    .gd-handle {
        background-color: #FFFFFF;
        width: 750px;
        height: 96px;
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
    }

    .gd-handle-tip {
        font-size: 20px;
        font-weight: 400;
        color: #000000;
    }

    .gd-handle-cut {
        width: 144px;
        text-align: center;
        color: white;
        font-weight: 400;
        font-size: 24px;
        line-height: 56px;
        background-color: #EF8A31;
        margin-right: 28px;
        margin-left: 28px;
        border-radius: 4px;
    }
    .gd-handle-cut-again {
        width: 144px;
        text-align: center;
        color: #EF8A31;
        font-weight: 400;
        font-size: 24px;
        line-height: 56px;
        border-style: solid;
        border-width: 2px;
        border-color: #EF8A31;
        margin-right: 28px;
        margin-left: 28px;
        border-radius: 4px;
    }

    .gd-end-handle {
        background-color: #FFFFFF;
        width: 750px;
        height: 96px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .gd-end-handle-state {
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }

    .gd-end-handle-state-text {
        font-size: 20px;
        color: #000000;
        font-weight: 400;
        margin-left: 8px;
    }

</style>
