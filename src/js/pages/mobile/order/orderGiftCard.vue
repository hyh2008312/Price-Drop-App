<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header :title="title" ></topic-header>
        <scroller style="background-color: #f4f4f4">
        <div style="margin-top: 16px;margin-bottom: 32px">

            <div v-if="cardArr.length===0" class="empty-div">
                <image  src="bmlocal://assets/empty.png" class="empty-img"></image>
                <text class="empty-txt">No gift voucher available.</text>
            </div>


            <div class="overflow-gift" v-if="cardArr.length!==0" v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'overflow-gift-bottom':'',]">
                <div class="gift-card-c" @click="tickCard(index,i)">


                    <text class="iconfont item-checked" v-if="(index === flag&&cardMoney>=i.lowestAmount)||i.id===selCardId">&#xe6fb;</text>
                    <text class="iconfont item-disable-checked" v-else-if="cardMoney<i.lowestAmount">&#xe73f;</text>
                    <text class="iconfont item-no-checked" v-else>&#xe73f;</text>

                    <div class="gift-card">

                        <image class="gift-card-img" v-if="cardMoney>=i.lowestAmount"  :src="i.newImageDetail" ></image>
                        <image class="gift-card-img" v-if="cardMoney<i.lowestAmount&&(i.share===100||i.share===80||i.share===120)" :src="i.newImageExpired"></image>

                        <image class="gift-card-img" v-if="cardMoney<i.lowestAmount&&i.share===150" :src="i.newImageExpired"></image>
                        <image class="gift-card-img" v-if="cardMoney<i.lowestAmount&&i.share===200" :src="i.newImageExpired"></image>

                        <!--<div class="triangle-topright" v-if="(index === flag&&cardMoney>=i.lowestAmount)||i.id===selCardId">-->
                            <!--<text  class="triangle">&#xe741;</text>-->
                            <!--<text  class="tick">&radic;</text>-->
                        <!--</div>-->
                        <div class="gift-card-txt">
                            <div class="gift-card-right-txt">
                                <!--<text class="gift-card-txt1">{{i.name}} Gift Card </text>-->
                                <text class="gift-card-txt2">Order above ₹{{i.lowestAmount}} </text>
                            </div>
                            <text class="gift-card-txt3" style="color: rgba(0,0,0,0.38)" v-if="cardMoney<i.lowestAmount" >Not Applicable</text>
                            <text class="gift-card-txt3" v-if="cardMoney>=i.lowestAmount">Expire in&nbsp;{{tranDate(i.expiredTime)}}&nbsp;days</text>
                        </div>

                    </div>

                </div>
            </div>
        </div>
        </scroller>

        <div class="bottom-btn" v-if="cardArr.length!==0">
            <text class="bottom-btn-txt1" @click="backPre(1)">Apply</text>
            <text class="bottom-btn-txt2" @click="backPre(2)">Reset</text>
        </div>
    </div>
</template>

<script>
    import header from './header';
    import { baseUrl } from '../../../config/apis'
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    export default {
        components: {
            'topic-header': header
        },
        name: 'myCard',
        eros: {
            beforeAppear (a) {
                this.cardMoney = a.cardMoney
                if (a.card) {
                    this.selCard = a.card
                    this.selCardId = a.card.id  // 已经选择上一页传回来的
                    this.flag = ''
                }
            }
        },
        data () {
            return {
                title: 'Choose a Gift Voucher',
                selCard: '',
                selCardId: '',
                cardArr: false,
                cardMoney: '',
                flag: '',
                PS: '',
                emptyImg: {
                    point100: 'bmlocal://assets/100-min-no.png',
                    point150: 'bmlocal://assets/150-min-no.png',
                    point200: 'bmlocal://assets/200-min-no.png'
                }
            }
        },
        created () {
            this.getCard()
            googleAnalytics.trackingScreen('Choose a Gift Voucher');
        },
        methods: {
            getCard () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/point/voucher/user/`, // 通过get 获取我自己的积分卡
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.cardArr = res

                    for (let j = 0; j < this.cardArr.length - 1; j++) {
                        for (let i = 0; i < this.cardArr.length - 1 - j; i++) {
                            if (this.cardArr[i].share > this.cardArr[i + 1].share) {
                                const temp = this.cardArr[i];
                                this.cardArr[i] = this.cardArr[i + 1];
                                this.cardArr[i + 1] = temp;
                            }
                        }
                    }
                    if (this.cardArr.length === 0) {
                        this.card = ''
                    } else {
                        if (this.cardArr[0].lowestAmount <= this.cardMoney){
                            this.flag = 0;
                            this.selCard = this.cardArr[0]
                            this.selCardId = this.cardArr[0].id
                        }
                    }
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            tickCard (i, card) {
                if (this.cardMoney < card.lowestAmount) {
                    this.selCard = '';
                    return
                } else {
                    this.selCardId = ''
                    this.selCard = card
                    this.flag = i;
                }
            },
            backPre (p) {
                if (p === 1) {
                    // this.$notice.toast({
                    //     message: this.selCard
                    // });
                    if (this.selCard == '') {
                        this.$notice.toast({
                            message: 'Please choose a gift voucher first!'
                        });
                        return
                    } else {
                        googleAnalytics.recordEvent('PayGiftCard', 'Choose a Gift Voucher', this.selCard.share, 0);
                        this.$router.back();
                        this.$router.setBackParams({
                            card: this.selCard
                        })
                    }
                } else {
                    this.$router.back()
                    this.$router.setBackParams({
                        card: false
                    })
                }
            },
            tranDate (tamp) {
                const now = new Date().getTime()
                const tmp = new Date(tamp).getTime()
                const total = (tmp - now) / 1000
                const day = Math.floor(total / (24 * 60 * 60)); // 整天
                return day;
            }
        }
    }
</script>

<style scoped>
    .wrapper{
        /*background-color: #f4f4f4;*/
        background-color: black;
    }
    .iconfont{
        font-family: iconfont;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .overflow-gift{
        flex-direction: row;
        justify-content: start;
        margin-top: 32px;
        /*background-color: ;*/
    }
    .gift-card-c{
        flex-direction: row;
        align-items: center;
        justify-content: start;
    }
    .gift-card{
        box-shadow: 0 1px 6px 0 rgba(0,0,0,0.12);
        border-radius: 24px;
    }
    .item-checked{
        padding: 0 18px 0 32px ;
        font-size:38px ;
        color: #EF8A31;
    }
    .item-disable-checked{
        margin: 0 18px 0 32px ;
        font-size:38px ;
        opacity: 0.24;
        border-radius: 50%;
        background-color: #BBBBBB;
    }
    .item-no-checked{
        padding: 0 18px 0 32px ;
        font-size:38px ;
        color: rgba(0,0,0,.32);
    }
    .triangle-topright{
        position: absolute;
        border-radius: 24px;
        top:-11px;
        right:0;
    }
    .tick{
        position: relative;
        top:-90px;
        right:-75px;
        color: white;
        font-size: 40px;
    }
    .triangle{
        position: relative;
        top:10px;
        left:0;
        font-family: iconfont;
        font-size: 120px;
        color: #EF8A31;
    }
    .gift-card-img{
        width: 624px;
        height: 187px;
    }

    .gift-card-txt{
        width: 624px;
        flex-direction: row;
        justify-content: space-between;
        background-color: white;
        padding-top:26px ;
        padding-bottom:26px ;
        padding-left:24px ;
        padding-right:24px ;
    }
    .gift-card-right-txt{
        flex-direction: column;
        /*align-items: center;*/
        justify-content: flex-start;
    }
    .gift-card-txt1{
        font-family: ProximaNova;
        font-weight: 700;
        font-size: 24px;
        margin-bottom: 16px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-txt2{
        font-family: ProximaNova;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-txt3{
        font-family: ProximaNova;
        font-size: 24px;
        color: #EF8A31;
    }
    .bottom-btn{
        background-color:white;
        box-shadow: 0 -1px 3px 0 rgba(0,0,0,0.24);
        height: 118px;

        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .bottom-btn-txt1{
        background-color: #EF8A31;
        color: white;
        padding: 20px 40px;
        margin-right: 66px;
        border-radius: 50%;

    }
    .bottom-btn-txt2{
        color: #EF8A31;
    }
    .empty-div{
        flex-direction: column;
        justify-content:flex-start;
        align-items: center;
        height: 300px;
        margin-top: 140px;
        /*background-color: black;*/
    }
    .empty-img{
        width: 200px;
        height: 200px;
    }
    .empty-txt{
        opacity: 0.54;
        font-family: ProximaNova;
        font-size: 24px;
        color: #000000;
    }
</style>
