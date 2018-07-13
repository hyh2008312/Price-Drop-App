<template>
    <div>
        <div class="state"  ></div>
        <scroller  v-if="shipA">
        <div class="wrapper"  >

        <div class="overflow-box" >
            <text class="close" @click="$router.finish();" >&#xe6f6;</text>
            <text class="center-word"  >Detail</text>

        </div>
        <div class="mask-content" style="margin-top: -120px">
            <div class="overflow-mid">
                <div class="mid-card">
                    <div class="mask-head">
                        <text class="mask-title">How To Drop Price</text>
                    </div>
                    <div class="mid-card-item1">
                        <div><image class="img-icon" src="bmlocal://assets/pic-click.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text1">Step 1</text>
                            <text class="mid-card-text2">Click the “Drop Price” button to start your DROP campaign immediately.</text>
                        </div>
                    </div>
                    <div class="mid-card-item1">
                        <div><image class="img-icon" src="bmlocal://assets/pic-share.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text1">Step 2</text>
                            <text class="mid-card-text2">Invite friends to support your DROP by sharing to Facebook & WhatsAPP. </text>
                        </div>
                    </div>
                    <div class="mid-card-item1">
                        <div><image class="img-icon" src="bmlocal://assets/pic-crowed.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text1">Step 3</text>
                            <text class="mid-card-text2">The more people click the “Drop Price” button for you, the lower price you can reach.</text>
                        </div>
                    </div>
                    <div class="mid-card-item1 mid-card-btm">
                        <div><image class="img-icon" src="bmlocal://assets/pic-discount.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text1">Step 4</text>
                            <text class="mid-card-text2">After the DROP ends, you can purchase the item at the final discounted price!</text>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="overflow-bottom"  >

            <div class="bottom-card"  v-if="shipA">
                <text class="bottom-head-txt head-txt">Rules to Know</text>
                <div class="bottom-card-item1" v-for="(i,index) in QAArr"  :class="[index==QAArr.length-1 ?'bottom-last':'',]">
                    <div><text class="bottom-num">{{index+1}}.</text></div>
                    <div class="bottom-card-txt">
                        <text class="bottom-card-question">{{i.question}}</text>
                        <!--<text class="bottom-card-answer">{{i.answer}} </text>-->
                    </div>
                </div>
            </div>
        </div>

    </div>
        </scroller>

        <div class="wrapper" v-if="shipB">

            <!--<scroller >-->
            <div class="overflow-box1" >
                <text class="close1" @click="$router.finish();" style="color:black;"  >&#xe6f6;</text>

            </div>

            <div class="container-1">
                <div class="item"><text class="shipname">Shipping Cost:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">Rs.{{time.priceItem}}</text>  </div>
                <!--<div class="item"><text class="shipname">Shipping Cost:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">Rs.0.00</text>  </div>-->
                <div class="item margin-btn"><text class="shipname">Shipping Carrier:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">{{time.shippingName}}</text>  </div>
                <div class="item"><text class="shipname">Order Processing:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">2 - 4  business days</text>  </div>
                <div class="item"> <text class="shipname">Shipping Time:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">{{time.shippingTimeMin}} - {{time.shippingTimeMax}} business days</text> </div>
                <!--<div class="item"> <text class="shipname">Estimated Arrival:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">{{formatDate(time.expectedDeliveryDateMin)}} - {{formatDate(time.expectedDeliveryDateMax)}}</text>  </div>-->
            </div>
            <!--</scroller>-->

        </div>

    </div>
</template>
<script>
    import { QA } from './config'
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    export default {
        components: {
        },
        created () {
            this.$router.getParams().then(params => {
                if (params.time) {
                    this.shipB = true
                    this.time = params.time
                } else {
                    this.shipA = true
                }
            })
            this.QAArr = QA
            googleAnalytics.trackingScreen('Product Detail Ship');
        },
        data () {
            return {
                time: '',
                aa: '',
                shipA: false,
                shipB: false
            }
        },
        methods: {
            loginBack () {
                this.$router.finish();
            }
        }
    }

</script>
<style scoped>
    /*.wrapper {*/
        /*background-color: #fff;*/
       /**/
    /*}*/
    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .item {
        flex-direction: row;
        margin-left: 38px;
    }
    .shipname {
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        margin-bottom: 16px;
    }


    .shipword {
        font-family: ProximaNova-Bold;
        font-size: 24px;
        font-weight: 700;
        line-height: 28px;
        color: #000000;
    }
    .container-1{
        margin-top: 48px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: left;
    }
    .margin-btn{
        margin-bottom: 48px;
    }
    .overflow-box{
        background-color: #EF8A31;
        height: 264px;
        border-bottom-left-radius: 100%;
        border-bottom-right-radius: 100%;
        /*border-radius: 100%;*/
    }
    .close{
        font-family: iconfont;
        font-size: 32px;
        color: white;
        margin-top: 44px;
        margin-left: 32px;
    }
    .close1{
        font-family: iconfont;
        font-size: 32px;
        color: black;
        margin-top: 44px;
        margin-left: 32px;
    }
    .center-word{
        font-family: ProximaNova;
        text-align: center;
        color: white;
        font-size: 40px;
        font-weight: 700;
        /*margin-top: -10px;*/
    }

    .mask-content {
        width: 750px;
        padding: 30px;
    }
    .mask-head {
        align-items: center;
        margin-bottom: 20px;
        margin-top: 40px;
    }
    .mask-title {
        color: #333333;
        font-weight: 700;
        font-size: 32px;
    }
    .mask-text {
        color: #333333;
        font-size: 28px;
        line-height: 40px;
        margin-top: 20px;
        margin-left: 20px;
    }


    .overflow-mid{
        flex-direction: row;
        justify-content: center;

    }
    .overflow-gift-bottom{
        margin-bottom: 150px;
    }
    .mid-card{
        width: 686px;
        /*height: 420px;*/
        background-color: white;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12) ;
        border-radius: 16px;
        flex-direction: column;
        align-items: stretch;

    }
    .img-icon{
        width: 48px;
        height: 48px;
    }
    .mid-card-item1{
        margin-top: 30px;
        margin-left: 32px;
        flex-direction: row;
        justify-content: flex-start;
    }
    .mid-card-btm{
        margin-bottom: 48px;
    }
    .mid-card-text{
        margin-left: 48px;
    }
    .mid-card-text1 {
        font-family: ProximaNova-Regular;
        margin-bottom: 16px;
        font-size: 28px;
        color: #EF8A31;
    }
    .mid-card-text2{
        color: rgba(0, 0, 0, 0.87);
        opacity: 0.54;
        font-size: 24px;
        width: 510px;
        line-height: 42px;
        font-family: ProximaNova-Regular;
    }
    .overflow-bottom{
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-top: 16px;
        margin-bottom: 32px;
    }
    .head-txt{
        font-family: ProximaNova;
        font-size: 32px;
        color: black;
        font-weight: 700;
        text-align: center;
    }
    .bottom-card{
        width: 688px;
        overflow: hidden;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        background-color: white;
        border-radius: 8px;
    }
    .bottom-head-txt{
        margin-top: 48px;
        margin-bottom: 32px;
    }
    .bottom-card-item1{
        flex-direction: row;
        justify-content: space-around;
        margin-bottom: 32px;
    }
    .bottom-card-txt{
        margin-left: -90px;
    }
    .bottom-num{
        color: black;
        font-size: 28px;
        line-height: 36px;
        font-family: ProximaNova-Regular;
    }
    .bottom-card-question{
        color: black;
        font-size: 24px;
        line-height: 42px;
        width: 560px;
        font-family: ProximaNova-Regular;
        /*font-weight: 700;*/
        margin-bottom: 16px;
    }
    .bottom-card-answer{
        color: black;
        font-size: 24px;
        line-height: 36px;
        width: 550px;
        font-family: ProximaNova-Regular;
        padding-right: 24px;
        margin-top: -5px;
        margin-bottom: 48px;
    }
    .bottom-last{
        /*margin-bottom: 50px;*/
    }
</style>
