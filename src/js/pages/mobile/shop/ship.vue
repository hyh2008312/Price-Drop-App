<template>
    <div class="wrapper">
        <div class="state"></div>
        <text class="close" @click="$router.finish();">&#xe632;</text>
        <scroller >

        <div class="container-1" v-if="ship==true">
            <div class="item"><text class="shipname">Shipping Cost:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">Rs.50.00</text>  </div>
            <div class="item margin-btn"><text class="shipname">Shipping Carrier:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">EMS</text>  </div>
            <div class="item"><text class="shipname">Order Processing:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">2-4 days</text>  </div>
            <div class="item"> <text class="shipname">Shipping Time:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">{{formatDate(time.expectedShipDateMin)}} - {{formatDate(time.expectedShipDateMax)}}</text> </div>
            <!--<div class="item"> <text class="shipname">Estimated Arrival:</text>&nbsp;&nbsp;&nbsp;<text class="shipword">{{formatDate(time.expectedDeliveryDateMin)}} - {{formatDate(time.expectedDeliveryDateMax)}}</text>  </div>-->
        </div>
        <div  v-if="ship==false">

                <div class="mask-content">
                    <div class="mask-head">
                        <text class="mask-title">How To Drop Price</text>
                    </div>
                    <text class="mask-text">Step1:  Click the “Drop The Price” button to start your DROP campaign immediately.</text>
                    <text class="mask-text">Step2:  Invite friends to join you by sharing your DROP campaign to Facebook & WhatsAPP.</text>
                    <text class="mask-text">Step3:  When people click the “Drop Price” button for you on the page you share, the price will drop automatically.</text>
                    <text class="mask-text">Step4:  After the DROP campaign ends, you can purchase the item at the final price you have reached!</text>

                    <div class="mask-head">
                        <text class="mask-title">Rules to Know</text>
                    </div>
                    <text class="mask-text">1. The duration of a Price Drop campaign is up to 24 hours. The campaign will end automatically once it reaches the lowest price.</text>
                    <text class="mask-text">2. As soon as the DROP ends, you have 24 hours to make your purchase. If you do not complete the payment in time, you’re deemed to waive your purchase right, and the reduced price will also expire.</text>
                    <text class="mask-text">3. Due to the limited stocks of each item, the products will be given out on a "first-pay, first-serve” basis. That means, if the stock has been running out before you complete the payment, the DROP may fail as well.</text>
                    <text>&nbsp;</text>
                    <text>&nbsp;</text>
                </div>
        </div>
        </scroller>

    </div>
</template>
<script>
    import moment from 'moment'

    export default {
        components: {
        },
        created () {
            this.$router.getParams().then(params => {
                if (params) {
                    this.ship = true
                    this.time = params.time
                }
            })
            this.$notice.toast({
                message: this.time
            })
            // formatDate()
        },
        data () {
            return {
                time: '',
                aa: '',
                ship: '',
            }
        },
        methods: {
            loginBack () {
                this.$router.finish();
            },
            formatDate (str) {
                return moment(new Date(str)).format('MMMM Do YYYY')
            }
        }
    }

</script>
<style scoped>
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
    .close{
        font-family: iconfont;
        font-size: 45px;
        color: #333333;
        opacity: 0.56;
        margin-top: 40px;
        margin-left: 40px;
    }
    .state {
        width: 750px;
        height: 48px;
        background-color: black;
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
        font-size: 40px;
    }
    .mask-text {
        color: #333333;
        font-size: 28px;
        line-height: 40px;
        margin-top: 20px;
        margin-left: 20px;
    }
</style>
