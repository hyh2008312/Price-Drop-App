<template>
    <div class="item">
        <div class="wrapper-1" v-if="flag">
            <div class="gb-box-1">
                <div class="i-gd-1"  @click="jumpWeb()">
                    <div class="gd-bg-1">
                        <div class="gd-img-1">
                            <preload class="gd-img-image-1" :src="goods.mainImage"></preload>
                        </div>
                    </div>
                    <div class="gd-bg-right-1">
                        <text class="gd-tlt-1">{{goods.title}}</text>
                        <div class="gd-sm-1">
                            <text class="gd-price-1">₹{{parseInt(goods.lowestPrice)}}</text>
                        </div>
                        <text class="gd-info-1">₹{{parseInt(goods.unitPrice)}}</text>
                        <text class="gd-button-1">Drop Price</text>
                    </div>
                </div>
            </div>
        </div>
        <div class="wrapper"  v-else >
            <div class="gb-box">
                <div class="i-gd" @click="jumpCutDetail">
                    <div class="gd-bg">
                        <preload class="gd-img" :src="goods.mainImage"></preload>
                    </div>
                    <div class="gd-bg-right">
                        <text class="gd-tlt">{{goods.title}}</text>
                        <div class="gb-price">
                            <!--<text class="gd-price-sale">₹{{goods.currentPrice}}</text>-->
                            <text class="gd-price-original">₹{{parseInt(goods.salePrice)}}</text>
                        </div>
                        <text class="gd-price-show" v-if="goods.cutStatus=='progressing'">Dropped ₹{{ ((goods.salePrice * 100 - goods.currentPrice * 100)/100).toFixed(2) }} by {{goods.cutTimes}} people</text>
                        <div class="gd-cut" v-if="goods.cutStatus=='progressing'">
                            <text class="gd-cut-price">₹{{ Math.floor((goods.currentPrice - goods.lowestPrice)*100)/100 }}</text>
                            <text class="gd-cut-price-tip"> left to reach Lowest price</text>
                        </div>
                        <div class="gd-cut-end"v-if="goods.cutStatus=='end'">
                            <text class="gd-price-show-end">{{computeLockMoney(goods.salePrice,goods.currentPrice,goods.lowestPrice)}}</text>
                            <!--<text class="gd-cut-price">₹{{ ((goods.salePrice * 100 - goods.currentPrice *100)/100).toFixed(2) }}</text>-->
                        </div>
                    </div>
                </div>
            </div>
            <div class="gb-line"></div>
            <!--正在砍价-->
            <div class="gd-handle" v-if="goods.cutStatus=='progressing'">
                <text class="gd-handle-tip">Ends in </text>
                <wxc-countdown tpl="{h}:{m}:{s}"
                               :time="goods.endTimestamp *1000"
                               :timeBoxStyle="{backgroundColor: '#000000', height: '36px', width: '36px','border-radius': '4px'}"
                               :timeTextStyle="{fontSize: '24px', color: '#FFFFFF'}"
                               :dotTextStyle="{color: '#000000', fontSize: '24px'}"
                               :dotBoxStyle="{width: '10px'}"
                               :style="{justifyContent: 'center'}">
                </wxc-countdown>
                <text class="gd-handle-cut" @click="goingCutPrice">Drop Price</text>
            </div>
            <!--砍价结束-->
            <div class="gd-end-handle" v-else>
                <div class="gd-end-handle-state" v-if="(computeIsCanPay(goods.salePrice,goods.currentPrice,goods.lowestPrice))&&(goods.operationStatus =='pending' || goods.operationStatus =='unpaid')">
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
                <!--<div class="gd-end-handle-state" v-if="computeIsCanPay(goods.salePrice,goods.currentPrice,goods.lowestPrice)">
                    <text class="icon-paid">&#xe6fb;</text>
                    <text class="gd-end-handle-state-text">Paid</text>
                </div>-->
                <div class="gd-end-handle-state" v-else>
                    <text class="icon-expired">&#xe6fe;</text>
                    <text class="gd-end-handle-state-text">Ended</text>
                </div>
                <!--pending-->
                <text class="gd-handle-cut"v-if="goods.operationStatus =='pending' && computeIsCanPay(goods.salePrice,goods.currentPrice,goods.lowestPrice)" @click="jumpConfirmOrder">Pay Now</text>
                <!--unpaid-->
                <text class="gd-handle-cut" v-else-if="goods.operationStatus =='unpaid' && computeIsCanPay(goods.salePrice,goods.currentPrice,goods.lowestPrice)" @click="jumpOrderDetail">Pay Now</text>
                <!---->
                <text class="gd-handle-cut-again" v-else @click="jumpProductDetail">Drop Again </text>
            </div>

        </div>
    </div>
</template>


<script>
    import preload from '../common/preloadImg';
    import { WxcCountdown } from 'weex-ui'

    export default {
        components: { WxcCountdown, preload },
        props: ['goods', 'flag'],
        data () {
            return {
                TIME: new Date().getTime() + 86400000 + '',
                dropStatus: 1
            }
        },
        methods: {
            computeIsCanPay (originalPrice, currentPrice, lowestPrice) {
                const percentage = (originalPrice - currentPrice) / (originalPrice - lowestPrice);
                return percentage >= 0.5;
            },
            computeLockMoney (originalPrice, currentPrice, lowestPrice) {
                const percentage = (originalPrice - currentPrice) / (originalPrice - lowestPrice);
                if (percentage < 0.5) {
                    this.dropStatus = 1;
                    return 'You didn\'t unlock any discount.'
                } else if (percentage >= 0.5 && percentage < 0.7) {
                    this.dropStatus = 2;
                    return `Final price unlocked: ₹${parseInt(originalPrice * 0.5)}`;
                } else if (percentage >= 0.7 && percentage < 1) {
                    this.dropStatus = 3;
                    return `Final price unlocked: ₹${parseInt(originalPrice * 0.3)}`;
                } else if (percentage === 1) {
                    this.dropStatus = 4;
                    return `Final price unlocked: ₹${parseInt(lowestPrice)}`;
                }
            },
            jumpWeb () {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: this.goods.id,
                        isDrop: true
                    }
                })
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.ceil((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            jumpProductDetail () {
                this.$router.open({
                    name: 'goods.details',
                    params: {
                        id: this.goods.productId
                    }
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
                const prama = this.goods;
                prama.proId = 'drop';
                this.$router.open({
                    name: 'order.confirm',
                    params: prama
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


    .wrapper-1 {
        background-color: #fff;
        padding-top: 24px;
        padding-bottom: 24px;
        width: 750px;
    }

    .gb-box-1 {
        padding: 0 16px;
        display: flex;
    }

    .i-gd-1 {
        width: 718px;
        height: 288px;
        flex-direction: row;
    }

    .gd-bg-1 {
        width: 288px;
        height: 288px;
        border-radius: 8px;
        overflow: hidden;
        border-top-width: 1px;
        border-top-style: solid;
        border-top-color: rgba(0,0,0,.08);
        border-left-width: 1px;
        border-left-style: solid;
        border-left-color: rgba(0,0,0,.08);
        border-right-width: 1px;
        border-right-style: solid;
        border-right-color: rgba(0,0,0,.08);
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,.08);
    }

    .gd-bg-right-1 {
        margin-left: 32px;
        width: 398px;
        height: 288px;
    }

    .gd-img-1 {
        width: 284px;
        height: 284px;
        border-radius: 8px;
        overflow: hidden;
    }
    .gd-img-image-1 {
        width: 284px;
        height: 284px;
        border-radius: 8px;
        overflow: hidden;
        position: relative;
    }

    .gd-tlt-1 {
        margin-top: 16px;
        font-size: 24px;
        font-weight: 300;
        line-height: 36px;
        width: 398px;
        overflow: hidden;
        lines: 2;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-info-1 {
        display: block;
        margin-top: 4px;
        font-size: 20px;
        width: 398px;
        line-height: 24px;
        color: rgba(0,0,0,0.87);
        text-decoration: line-through;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-price-1 {
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        color: #000;
    }

    .gd-price-1-1{
        font-size: 20px;
        line-height: 24px;
        margin-left: 8px;
        color: #EF8A31;
    }

    .gd-sm-1 {
        margin-top: 14px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .gd-button-1 {
        margin-top: 62px;
        width: 144px;
        text-align: center;
        font-size: 24px;
        line-height: 44px;
        font-weight: bold;
        border-style: solid;
        border-radius: 4px;
        border-width: 2px;
        border-color: #EF8A31;
        color: #EF8A31;
    }

</style>
