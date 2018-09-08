<template>
    <scroller class="wrapper" >
        <div class="status-bar"></div>
        <div class="banner-bg">
            <image class="top-banner" src="bmlocal://assets/guide-page-04.png"></image>
            <text class="leftTxt" @click="$router.back">&#xe6f6;</text>
            <text class="title">Win Great Prize Every Day!</text>
            <text class="content">Our daily Lucky Draw will randomly pick the prize winners among all participants. Every time you will have an equal chance to win the prize! </text>
        </div>
        <div class="wrapper-bottom">
            <div class="wrapper-content">
                <text class="title-1">How It Works</text>
                <div class="image-bg">
                    <div class="line"></div>
                    <div class="icon-bg">
                        <image class="icon" src="bmlocal://assets/join-01.png"></image>
                        <text class="icon-title">Join the</text>
                        <text class="icon-title">Lucky Draw</text>
                    </div>
                    <div class="icon-bg">
                        <image class="icon" src="bmlocal://assets/winner-01.png"></image>
                        <text class="icon-title">Winners Picked</text>
                        <text class="icon-title">Randomly</text>
                    </div>
                    <div class="icon-bg">
                        <image class="icon" src="bmlocal://assets/annouce-01.png"></image>
                        <text class="icon-title">Prize Winners</text>
                        <text class="icon-title">Announced</text>
                    </div>
                </div>
            </div>
            <div class="wrapper-content margin-top">
                <text class="title-1">Rules To Know</text>
                <div class="content-bg">
                    <text class="title-2">1.</text>
                    <text class="content-1">To join a lucky draw, you need to spend 500 points for entry. </text>
                </div>
                <div class="content-bg">
                    <text class="title-2">2.</text>
                    <text class="content-1">The prize winners will be announced at 9:00 AM every day. </text>
                </div>
                <div class="content-bg">
                    <text class="title-2">3.</text>
                    <text class="content-1">You will receive a notification for your lucky draw once the result is announced.</text>
                </div>
                <div class="content-bg">
                    <text class="title-2">4.</text>
                    <text class="content-1">If you win the free product, you must confirm your shipping address within 5 days. Otherwise, your prize will be withdrawn.</text>
                </div>
                <div class="content-bg margin-bottom">
                    <text class="title-2">5.</text>
                    <text class="content-1">We do not accept any return or exchange for any shipped prize. </text>
                </div>
            </div>
        </div>
    </scroller>
</template>
<script>
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        methods: {

        }
    }
</script>

<style scoped>

    .leftTxt{
        position: absolute;
        left: 0;
        top: 0;
        padding: 40px 32px;
        font-family: iconfont;
        color: #fff;
    }

    .status-bar{
        top: 0;
        height: 48px;
        background-color: #000;
        width: 750px;
    }

    .banner-bg{
        width: 750px;
        height: 696px;
        position: relative;
    }

    .top-banner{
        width: 751px;
        height: 696px;
        position: absolute;
        top: 0;
        left: -1px;
    }

    .title{
        margin-top: 100px;
        font-weight: bold;
        font-family: ProximaNova;
        font-size: 48px;
        line-height: 58px;
        color: #FFFFFF;
        letter-spacing: 0;
        width: 750px;
        text-align: center;
    }

    .title-1{
        font-family: ProximaNova;
        font-weight: bold;
        font-size: 28px;
        line-height: 34px;
        color: #610C0C;
        letter-spacing: 0;
        width: 686px;
        text-align: center;
        margin: 48px 0 32px;
    }

    .title-2{
        font-family: ProximaNova;
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        line-height: 36px;
    }

    .content{
        font-family: ProximaNova;
        font-size: 32px;
        font-weight: bold;
        line-height: 40px;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        width: 630px;
        line: 4;
        margin: 20px 60px;
    }

    .content-1{
        font-family: ProximaNova;
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        line-height: 36px;
        width: 550px;
    }

    .content-bg{
        width: 686px;
        padding: 0 48px 8px;
        flex-direction: row;
        justify-content: space-between;
        align-items: start;
    }

    .wrapper-bottom{
        margin-top: -1px;
        padding: 32px;
        width: 750px;
        background-color: #EC9729;
    }

    .wrapper-content{
        width: 686px;
        background-color: #FFC140;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
    }

    .margin-top{
        margin-top: 24px;
    }

    .margin-bottom{
        margin-bottom: 48px;
    }

    .image-bg{
        flex-direction: row;
        justify-content: space-between;
        align-items: start;
        padding: 0 48px 48px;
    }

    .icon{
        width: 80px;
        height: 80px;
        margin-bottom: 8px;
    }

    .icon-bg{
        width: 170px;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .icon-title{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        color: #000000;
        letter-spacing: 0;
        text-align: center;
    }

    .line{
        width: 400px;
        height: 2px;
        top: 40px;
        position: absolute;
        left: 143px;
        background-color: #AF7C06;
    }
</style>
