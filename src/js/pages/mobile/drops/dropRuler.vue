<template>
    <div class="wrapper">
        <div class="state"  ></div>
        <scroller  >
            <div class="wrapper"  >

                <div class="overflow-box" >
                    <text class="close" @click="$router.finish();" >&#xe6f6;</text>
                    <text class="center-word"  >{{title}}</text>
                </div>
                <div class="mask-content" style="margin-top: -80px">
                    <div class="overflow-mid">
                        <div class="mid-card" v-if="type==1">
                            <div class="mask-head">
                                <text class="mask-title">How It Works</text>
                            </div>
                            <div class="mid-card-item1">
                                <div><image class="img-icon" src="bmlocal://assets/drop/fenxiang.png"></image></div>
                                <div class="mid-card-text">
                                    <text class="mid-card-text1">Start a Drop</text>
                                    <text class="mid-card-text2">Share and ask your friends for help!</text>
                                </div>
                            </div>
                            <div class="mid-card-item1">
                                <div><image class="img-icon" src="bmlocal://assets/drop/friend.png"></image></div>
                                <div class="mid-card-text">
                                    <text class="mid-card-text1">Friends Click Help</text>
                                    <text class="mid-card-text2">Friends will earn bonus by supporting you!</text>
                                </div>
                            </div>
                            <div class="mid-card-item1 mid-card-btm">
                                <div><image class="img-icon" src="bmlocal://assets/drop/lock.png"></image></div>
                                <div class="mid-card-text">
                                    <text class="mid-card-text1">You Save More!</text>
                                    <text class="mid-card-text2">Unlock extra discount for your purchase!</text>
                                </div>
                            </div>
                            <!--<div class="mid-card-item1 mid-card-btm">-->
                                <!--<div><image class="img-icon" src="bmlocal://assets/drop/lock.png"></image></div>-->
                                <!--<div class="mid-card-text">-->
                                    <!--<text class="mid-card-text1">Step 4</text>-->
                                    <!--<text class="mid-card-text2">Unlock a better price with help from friends</text>-->
                                <!--</div>-->
                            <!--</div>-->
                        </div>

                        <div class="mid-card" v-if="type==2">
                            <div class="mask-head">
                                <text class="mask-title">How to Earn Rewards</text>
                            </div>
                            <div class="mid-card-item1">
                                <div><image class="img-icon" src="bmlocal://assets/drop/dianjishoushi-01.png"></image></div>
                                <div class="mid-card-text">
                                    <text class="mid-card-text1">Click Help for Your Friends</text>
                                    <text class="mid-card-text2">Help your friends drop price for their products.</text>
                                </div>
                            </div>
                            <div class="mid-card-item1">
                                <div><image class="img-icon" src="bmlocal://assets/drop/gift-12.png"></image></div>
                                <div class="mid-card-text">
                                    <text class="mid-card-text1">Your Friends Buy the Products</text>
                                    <text class="mid-card-text2">Get cash rewards when your friends buy with your help.</text>
                                </div>
                            </div>
                            <div class="mid-card-item1 mid-card-btm">
                                <div><image class="img-icon" src="bmlocal://assets/drop/wallet-02.png"></image></div>
                                <div class="mid-card-text">
                                    <text class="mid-card-text1">Earn Rewards Once Order Delivers</text>
                                    <text class="mid-card-text2">The rewards will be credited to you after your friends sign the order.</text>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="overflow-bottom"  >

                    <div class="bottom-card"  v-if="type==1">
                        <text class="bottom-head-txt head-txt">Rules to Know</text>
                        <div class="bottom-card-item1" v-for="(i,index) in QAArr"  :class="[index==QAArr.length-1 ?'bottom-last':'',]">
                            <div><text class="bottom-num">{{index+1}}.</text></div>
                            <div class="bottom-card-txt">
                                <text class="bottom-card-question">{{i.question}}</text>
                            </div>
                        </div>
                    </div>

                    <div class="bottom-card"  v-if="type==2">
                        <text class="bottom-head-txt head-txt">FAQ</text>
                        <div class="bottom-card-item1" v-for="(i,index) in QAArr"  :class="[index==QAArr.length-1 ?'bottom-last':'',]">
                            <div><text class="bottom-num">{{index+1}}.</text></div>
                            <div class="bottom-card-txt">
                                <text class="bottom-card-question1">{{i.question}}</text>
                                <text class="bottom-card-answer" >{{i.answer}} </text>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </scroller>
    </div>
</template>

<script>
    import {learnMore, helpFriend } from './config'
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        name: "dropRuler",
        data () {
            return{
                QAArr:[],
                type:'',
                title:''
            }
        },
        eros:{
            appeared(params){
                if(params){
                    this.type = params.type
                    this.check(params.type)

                }
            },
            beforeAppear(params){

            }
        },
        created(){
            googleAnalytics.trackingScreen(`Drop Ruler`);
        },
        methods:{
            check(num){
                if(num == 1){
                    this.title = 'The more friends help you, the lower price you unlock! ';
                    this.QAArr = learnMore
                }else {
                    this.title = 'Drop price for friends, \ Earn cash for free!';
                    this.QAArr = helpFriend
                }

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

    .shipname-note{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        margin-bottom: 16px;
        width: 670px;
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
        background-color: #D1C8E8;
        height: 264px;
        border-bottom-left-radius: 40%;
        border-bottom-right-radius: 40%;
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
        color: black;
        font-size: 40px;
        font-weight: 900;
        width: 500px;
        line-height: 60px;
        margin-left: 130px;
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
        font-weight: 900;
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
        margin-left: 36px;
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
        font-weight: 700;
        margin-bottom: 16px;
        font-size: 28px;
        color: black;
    }
    .mid-card-text2{
        color: rgba(0, 0, 0, 0.87);
        font-size: 24px;
        width: 510px;
        line-height: 42px;
        font-family: ProximaNova-Regular;
    }
    .mid-card-text3{
        color: rgba(0, 0, 0, 0.87);
        /*opacity: 0.54;*/
        font-size: 24px;
        width: 510px;
        line-height: 42px;
        font-family: ProximaNova-Regular;
        font-style: italic;
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
        /*margin-bottom: 16px;*/
    }
    .bottom-card-question1{
        color: black;
        font-size: 28px;
        line-height: 42px;
        width: 560px;
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
        margin-bottom: 16px;
    }
    .bottom-last{
        /*margin-bottom: 50px;*/
    }

</style>
