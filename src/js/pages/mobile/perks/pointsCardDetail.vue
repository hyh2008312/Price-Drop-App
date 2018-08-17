<template>
    <div class="wrapper">
        <topic-header title="Details" leftBtn="icon"  ref="ref1" ></topic-header>
        <scroller>
            <div class="blackheader"></div>
            <div class="overflow-top">
                <div class="top-card">
                    <text class="top-head-txt head-txt">The Perks</text>
                    <div class="top-card-imgdiv">
                        <image class="top-card-img"  src="bmlocal://assets/pic-2.png"></image>
                    </div>
                    <div class="top-bottom-txtdiv">
                        <text class="top-bottom-txt">The perks program is designed to reward our users for starting their DROP campaigns and helping their friends to drop the price on products they love.</text>
                        <text class="top-bottom-txt" style="padding-top: 5px"> With all reward points you earn from your participation, you can redeem for FREE gift vouchers!</text>
                    </div>
                </div>
            </div>
            <div class="overflow-mid">
                <div class="mid-card">
                    <text class="mid-head-txt head-txt">Ways to Earn Points</text>

                    <div class="mid-card-item1">
                        <div><image class="img-icon" src="bmlocal://assets/color-locked-01.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text2">Invite friends to drop price for your campaign and earn 200 / 300 points by unlocking 50% / 70% OFF. Please note: The daily limit is up to 600 points.
                            </text>
                        </div>
                    </div>
                    <div class="mid-card-item1">
                        <div><image class="img-icon" src="bmlocal://assets/pic-purchase.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text2">Purchase an item by starting your DROP, you earn 200 reward points for every Rs.50 you spend.
                            </text>
                        </div>
                    </div>
                    <div class="mid-card-item1">
                        <div><image class="img-icon" src="bmlocal://assets/pic-help.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text2">Help your friends make successful purchases by supporting their DROP, you earn 100 point for every Rs.50 your friends spend.</text>
                        </div>
                    </div>
                    <text>&nbsp;</text>
                    <!--<div class="mid-card-item1">-->
                        <!--<div class="mid-card-text">-->
                            <!--<text class="mid-card-text2 mid-card-bto">&nbsp;</text>-->
                        <!--</div>-->
                    <!--</div>-->
                </div>
                <text>&nbsp;</text>
            </div>


            <div class="overflow-bottom">
                <div class="bottom-card">
                    <text class="bottom-head-txt head-txt">FAQ</text>

                    <div class="bottom-card-item1" v-for="(i,index) in QAArr"  :class="[index==QAArr.length-1 ?'bottom-last':'',]">
                        <div><text class="bottom-num">{{index+1}}.</text></div>
                        <div class="bottom-card-txt">
                            <text class="bottom-card-question">{{i.question}}</text>
                            <text class="bottom-card-answer">{{i.answer}} </text>
                        </div>
                    </div>
                </div>
            </div>
        </scroller>
    </div>
</template>

<script>
    import header from './header';
    import { QA } from './config'
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        name: 'pointsCardDetail',
        components: {
            'topic-header': header
        },
        data () {
            return {
                QAArr: []
            }
        },
        created () {
            googleAnalytics.trackingScreen('Perks/CardDetail');
            this.QAArr = QA
        }
    }
</script>

<style scoped>
    .wrapper{

        width: 750px;
        background-color: #f4f4f4;
    }
    .img-icon{
        width: 48px;
        height: 48px;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .head-txt{
        font-family: ProximaNova;
        font-size: 40px;
        color: black;
        font-weight: 700;
        text-align: center;
    }
    .overflow-top{
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-top: 179px;
    }
    .top-card{
        width: 688px;
        height: 540px;
        background-color: white;
        overflow: hidden;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        flex-direction: column;
        justify-content: flex-end;
    }
    .top-head-txt{

    }
    .top-card-imgdiv{
        flex-direction: row;
        justify-content: center;
        margin-top: 64px;
        margin-bottom: -3px;
    }
    .top-card-img{
        width: 590px;
        height: 143px;
    }
    .top-bottom-txtdiv{
        background-color: #EEA85C;
        color: white;
    }
    .top-bottom-txt{
        color: white;
        font-size: 24px;
        line-height: 28px;
        padding: 32px 30px;
    }

    .overflow-mid{
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 588px;
        margin-top: 16px;
    }
    .mid-card{
        width: 688px;
        height: 580px;
        background-color: white;
        overflow: hidden;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        flex-direction: column;
        justify-content: space-between;
    }
    .mid-head-txt{
        margin-top: 48px;
        margin-bottom: 32px;
    }
    .mid-card-item1{
        margin-left: 32px;
        flex-direction: row;
        justify-content: space-around;
    }
    .mid-card-text{
        margin-left: 32px;
    }
    .mid-card-text2{
        color: black;
        font-size: 24px;
        line-height: 36px;
        width: 550px;
        font-family: ProximaNova-Regular;
        padding-right: 24px;
        margin-top: -5px;
    }
    .mid-card-bto{
        margin-left:75px;
        margin-top: 16px;
        margin-bottom: 48px;
        color:rgba(0,0,0,0.54) ;
    }
    .overflow-bottom{
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-top: 16px;
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
    }
    .bottom-card-txt{
        margin-left: -90px;
    }
    .bottom-num{
        color: black;
        font-size: 28px;
        line-height: 36px;
        font-family: ProximaNova-Regular;
        font-weight: 700;
    }
    .bottom-card-question{
        color: black;
        font-size: 28px;
        line-height: 36px;
        width: 550px;
        font-family: ProximaNova-Regular;
        font-weight: 700;
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
        margin-bottom: 50px;
    }
</style>
