<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name}}</text>
        </div>
        <scroller style="background-color: #980100" :show-scrollbar="false">
            <div>
                <image style="width: 750px;height: 332px;" src="bmlocal://assets/home/spring-page.png"></image>
                <text class="center-txt">Longer delivery times expected: </text>
            </div>
            <div class="circle-bg">
                <image style="width: 750px;height: 710px;" src="bmlocal://assets/home/3.png"></image>
                <div class="cb-txt1">
                    <text class="normal" style="margin-bottom: 6px;">Due to Chinese New Year vacation,</text>
                    <div class="word-center" >
                        <text class="normal">it will take</text>
                        <text class="red-txt"> extra 3 weeks</text>
                        <text class="normal"> for our Chinese</text>
                    </div>
                    <text class="normal" style="margin-bottom: 6px;">suppliers & shipping partners to process </text>
                    <div class="word-center">
                        <text class="normal">orders placed during </text>
                        <text class="bold-txt">Jan 24 - Feb 15.</text>
                    </div>
                    <text class="normal" style="margin-bottom: 6px;"> Please check the product pages for processing  </text>
                    <text class="normal"> time updates and estimated arrival date.</text>
                </div>
                <div class="cb-txt2">
                    <text class="normal1">At PriceDrop, we respect local culture and look</text>
                    <text class="normal1">forward to observing China’s most important</text>
                    <text class="normal1"> holiday along with our suppliers and shipping</text>
                    <text class="normal1"> partners. Meanwhile, as a strong customer-</text>
                    <text class="normal1">oriented company, we will do the utmost to</text>
                    <text class="normal1"> ensure that we deliver your orders</text>
                    <text class="normal1"> as fast as possible. </text>
                </div>
            </div>
            <div class="bottom-bg">
                <text class="normal2">Thank you for accommodating the</text>
                <text class="normal2">Chinese New Year holiday shipping schedule.</text>
                <div class="bto-img">
                    <image style="width: 140px;height: 130px" src="bmlocal://assets/home/2.png"></image>
                    <image style="width: 59px;height: 115px" src="bmlocal://assets/home/1.png"></image>

                </div>
            </div>
        </scroller>
    </div>
</template>

<script>
    export default {
        name: 'topic3',
        data () {
            return {
               name: 'Announcement'
            }
        }
    }
</script>

<style scoped>

    .homeBack {
        font-family: iconfont;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .wrapper {
        background-color: white;
    }
    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .navigation {
        display: flex;
        width: 750px;
        height: 112px;
        background-color: #fff;
        flex-direction: row;
        justify-content: flex-start;
    }

    .title {
        font-size: 32px;
        font-weight: bold;
        height: 112px;
        line-height: 112px;
        margin-left: 32px;
        width: 558px;
        text-align: center;
        line: 1;
        text-overflow: ellipsis;
        color: rgba(0,0,0,0.87);
    }
    .center-txt{
        font-size: 40px;
        color: white;
        font-weight: 700;
        position: absolute;
        top: 154px;
        left: 104px;
    }
    .circle-bg{
        /*background-color: #980100;*/
        margin-top: -60px;
    }
    .cb-txt1{
        position: absolute;
        top: 150px;
        left: 122px;
        width: 514px;
    }
    .cb-txt2{
        position: absolute;
        top: 370px;
        left: 122px;
        width: 514px;
    }
    .normal{
        font-size: 24px;
        color: #000000;
        text-align: center;
    }
    .normal1{
        font-size: 24px;
        color: #000000;
        text-align: center;
        margin-bottom: 2px;
    }
    .red-txt{
        font-size: 24px;
        color: red;
        font-weight: 700;
        text-align: center;
    }
    .bold-txt{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        text-align: center;
    }
    .word-center{
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-bottom: 6px;
    }
    .bottom-bg{
        background-color: #980100;
    }
    .normal2{
        font-size: 28px;
        color: #FFFFFF;
        text-align: center;
    }
    .bto-img{
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
    }
</style>
