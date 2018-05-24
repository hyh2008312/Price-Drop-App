<template>
    <div class="wrapper">
        <div class="state"></div>
        <scroller>
            <div class="wrapper-head">
                <div class="navigation">
                    <text class="homeBack">&#xe6f6;</text>
                    <div class="cut-rule">
                        <text class="cut-rule-icon">&#xe709;</text>
                        <text class="cut-rule-text">How to Cut Price</text>
                    </div>
                </div>
                <div class="out-head">
                    <image class="in-head" resize="cover"
                           :src="'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'"></image>
                </div>
                <text class="wrapper-tip">The more friends cut price for you,
                    the lower price you get.
                </text>
            </div>
            <div class="wrapper-product">
                <div class="product-image">
                    <image class="product-image-in" resize="cover"
                           :src="'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'"></image>
                </div>
                <div class="product-content">
                    <text class="product-content-name">toyota's Latest is an Entire Mobility Service Platform.</text>
                    <text class="product-content-people">25 people bought this item at the lowest price</text>
                </div>
            </div>
            <div class="wrapper-progress">
                <div class="current-price" :style="{'margin-left': percentage * 750 - 30+'px'}">
                    <text class="current-price-1">Current Price</text>
                    <text class="current-price-2">Rs.10.00</text>
                </div>
                <div class="wrapper-progress-line">
                    <div class="progress-line-bottom"></div>
                    <div class="progress-line-top" :style="{'width': percentage * 750+'px'}"></div>
                    <text class="progress-line-icon" :style="{'margin-left':percentage * 750 - 30+'px'}">&#xe70a;</text>
                </div>
                <div class="wrapper-price">
                    <div class="wrapper-price-regular">
                        <text class="current-price-1">Regular Price</text>
                        <text class="wrapper-price-2">Rs.10.00</text>
                    </div>
                    <div class="wrapper-price-lowest">
                        <text class="current-price-1">Current Price</text>
                        <text class="wrapper-price-2">Rs.10.00</text>
                    </div>
                </div>
            </div>
            <text class="wrapper-share">Share to Cut the Price Further</text>
            <div class="wrapper-timer">
                <wxc-countdown tpl="{h}:{m}:{s}"
                               :time="TIME"
                               :timeBoxStyle="{backgroundColor: '#FFFFFF',height: '64px', width: '64px','border-radius': '8px'}"
                               :timeTextStyle="{fontSize: '28px', color: 'rgba(0,0,0,0.87)'}"
                               :dotTextStyle="{color: 'rgba(0,0,0,0.87)', fontSize: '24px'}"
                               :dotBoxStyle="{width: '64px'}"
                               :style="{justifyContent: 'center'}">
                </wxc-countdown>
            </div>
            <div class="contributors">
                <text class="contributors-people">Price Cut Contributors</text>
                <div :style="{'margin-bottom':'60px'}">
                    <div class="contributors-info" v-for="(i ,index) in ['1', '2', '3' , '4']">
                        <div class="contributors-content">
                            <div class="contributors-content-left">
                                <div class="contributors-content-left-image">
                                    <image class="contributors-content-left-image-in" resize="cover"
                                           :src="'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'"></image>
                                </div>
                                <text class="contributors-content-left-name">Luzhenqiang</text>
                            </div>
                            <div class="contributors-content-right">
                                <text>Cut Rs.122.33</text>
                            </div>

                        </div>
                    </div>
                </div>
            </div>


        </scroller>

    </div>
</template>
<script>
    import {WxcCountdown} from 'weex-ui'

    export default {
        components: {WxcCountdown},
        eros: {},
        created() {
        },
        data() {
            return {
                percentage: 0.34,
                TIME: new Date().getTime() + 86400000 + ''
            }
        },
        methods: {}
    }
</script>
<style scoped>
    .contributors-content-left-image-in {
        height: 80px;
        width: 80px;
    }

    .contributors-content-left-image {
        height: 80px;
        width: 80px;
        border-radius: 40px;
    }

    .contributors-content-left-name {
        margin-left: 24px;
    }

    .contributors-content-right {
        margin-right: 32px;
    }

    .contributors-content-left {
        margin-left: 32px;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;

    }

    .contributors-content {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .contributors-info {
        height: 80px;
        margin-top: 32px;
    }

    .contributors-people {
        width: 718px;
        text-align: center;
        font-size: 28px;
        color: black;
        font-weight: bold;
        margin-top: 32px;
    }

    .contributors {
        width: 718px;
        margin: 35px 16px 40px 16px;
        background-color: #FFFFFF;
        border-radius: 8px;
        box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.12);
    }

    .wrapper-timer {
        margin-top: 24px;
    }

    .wrapper-share {
        width: 606px;
        height: 80px;
        font-size: 24px;
        border-radius: 8px;
        background-color: #EF8A31;
        line-height: 80px;
        text-align: center;
        margin-left: 72px;
        margin-right: 72px;
        color: white;
        font-weight: bold;
        margin-top: 24px;
    }

    .wrapper-price-2 {
        font-weight: bold;
        font-size: 24px;
        color: rgba(0, 0, 0, 0.87);
    }

    .wrapper-price {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    .progress-line-icon {
        position: absolute;
        font-size: 40px;
        color: black;
        margin-left: 170px;
        font-family: iconfont;
    }

    .progress-line-top {
        border-radius: 8px;
        width: 200px;
        top: 15px;
        position: absolute;
        background-color: #EF8A31;
        height: 8px;
    }

    .progress-line-bottom {
        border-radius: 8px;
        width: 606px;
        position: absolute;
        top: 15px;
        background-color: rgba(0, 0, 0, 0.12);
        height: 8px;
    }

    .wrapper-progress-line {
        border-radius: 8px;
        width: 606px;
        position: relative;
        height: 40px;
        margin-top: 14px;
    }

    .current-price-2 {
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        color: #FD7900;
    }

    .current-price-1 {
        font-size: 24px;
        color: rgba(0, 0, 0, 0.87);
        font-weight: 400;
    }

    .current-price {
        margin-left: 200px;
        margin-top: 24px;
    }

    .wrapper-progress {
        width: 606px;
        margin-left: 72px;
        margin-right: 72px;
    }

    .product-content-people {
        font-size: 20px;
        color: #EF8A31;
        margin-top: 48px;
        font-weight: bold;
    }

    .product-content-name {
        font-size: 24px;
        line-height: 36px;
        font-weight: 400;
        color: rgba(0, 0, 0, 0.87);
        max-lines: 2;
    }

    .product-content {
        width: 465px;
        margin-left: 24px;
        margin-top: 24px;
    }

    .product-image-in {
        width: 160px;
        height: 160px;
    }

    .product-image {
        margin-top: 16px;
        margin-left: 16px;
        width: 160px;
        height: 160px;
        border-radius: 8px;
    }

    .wrapper-product {
        width: 718px;
        height: 192px;
        margin-left: 16px;
        background-color: #FFFFFF;
        box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12);
        margin-right: 16px;
        margin-top: -135px;
        border-radius: 8px;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
    }

    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .wrapper-head {
        background-color: #EF8A31;
        width: 750px;
        height: 452px;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .out-head {
        height: 80px;
        width: 80px;
        border-radius: 40px;
    }

    .in-head {
        height: 80px;
        width: 80px;
    }

    .navigation {
        display: flex;
        flex-direction: row;
        height: 112px;
        width: 750px;
        justify-content: space-between;
        align-items: center;
    }

    .homeBack {
        font-family: iconfont;
        color: white;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .cut-rule {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
        margin-right: 30px;
        background-color: rgba(0, 0, 0, 0.34);
        height: 36px;
        border-radius: 24px;
        padding-left: 16px;
        padding-right: 16px;
    }

    .cut-rule-text {
        color: white;
        font-size: 20px;
        margin-left: 10px;
    }

    .cut-rule-icon {
        font-family: iconfont;
        color: white;
        font-size: 20px;
    }

    .wrapper-tip {
        font-size: 24px;
        font-weight: bold;
        color: rgba(255, 255, 255, 0.87);
        line-height: 40px;
        margin-top: 16px;
    }

</style>
