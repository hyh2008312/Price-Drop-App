<template>
    <div class="item">
        <div class="wrapper">
            <div class="gb-box">
                <div class="i-gd" @click="jumpWeb()">
                    <div class="gd-bg">
                        <image class="gd-img" resize="cover"
                               :src="goods.mainImage"></image>
                    </div>
                    <div class="gd-bg-right">
                        <text class="gd-info">{{goods.brandName}}</text>
                        <text class="gd-tlt">{{goods.title}}</text>
                        <div class="gd-cut">
                            <text class="gd-cut-price-tip">已砍</text>
                            <text class="gd-cut-price">¥{{goods.salePrice - goods.currentPrice }}</text>
                        </div>
                        <div class="gb-price">
                            <text class="gd-price-sale">¥{{goods.currentPrice}}</text>
                            <text class="gd-price-original">¥{{goods.salePrice}}</text>
                        </div>
                    </div>
                </div>
            </div>
            <div class="gb-line"></div>
            <!--正在砍价-->
            <div class="gd-handle" v-if="flag">
                <wxc-countdown tpl="{h}:{m}:{s}"
                               :time="goods.endTimestamp *1000"
                               :timeBoxStyle="{backgroundColor: '#000000', height: '36px', width: '36px','border-radius': '4px'}"
                               :timeTextStyle="{fontSize: '24px', color: '#FFFFFF'}"
                               :dotTextStyle="{color: '#000000', fontSize: '24px'}"
                               :dotBoxStyle="{width: '10px'}"
                               :style="{justifyContent: 'center'}">
                </wxc-countdown>
                <text class="gd-handle-tip">后结束砍价</text>
                <text class="gd-handle-cut" @click="goingCutPrice">继续砍价</text>
            </div>
            <!--砍价结束-->
            <div class="gd-end-handle" v-else>
                <div class="gd-end-handle-state">
                    <text class="gd-end-handle-state-text">付款倒计时</text>
                    <wxc-countdown tpl="{h}:{m}:{s}"
                                   :time="TIME"
                                   :timeBoxStyle="{backgroundColor: 'transparent', height: '36px', width: '36px','border-radius': '4px'}"
                                   :timeTextStyle="{fontSize: '24px', color: '#987D1A'}"
                                   :dotTextStyle="{color: '#987D1A', fontSize: '24px'}"
                                   :dotBoxStyle="{width: '10px'}"
                                   :style="{justifyContent: 'center'}">
                    </wxc-countdown>
                </div>
                <text class="gd-handle-cut">立刻购买</text>
                <text class="gd-handle-cut" v-if="false">查看订单</text>
                <text class="gd-handle-cut" v-if="false">再砍一次</text>
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
            jumpWeb () {
                this.$router.open({
                    name: 'goods.details'
                })
            },
            goingCutPrice () {
                this.$notice.alert({
                    message: this.TIME
                });
            }
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
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
        line-height: 40px;
        font-size: 24px;
        font-weight: 300;
        width: 446px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-cut {
        display: flex;
        flex-direction: row;
        margin-top: 52px;
        justify-content: flex-start;
    }

    .gd-cut-price-tip {
        font-size: 20px;
        height: 28px;
        line-height: 28px;
        color: #000000;
    }

    .gd-cut-price {
        font-size: 24px;
        line-height: 28px;
        height: 28px;
        color: #AC0B0B;

    }

    .gb-price {
        display: flex;
        flex-direction: row;
        margin-top: 16px;
        justify-content: flex-start;
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
        color: black;
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
        color: #000000;
        font-weight: 400;
        font-size: 24px;
        line-height: 56px;
        border-style: solid;
        margin-right: 28px;
        margin-left: 28px;
        border-radius: 4px;
        border-width: 2px;
        border-color: #000;
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
        margin-left: 16px;
    }

</style>
