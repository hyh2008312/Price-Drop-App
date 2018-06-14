<template>
    <div class="wrapper">
        <scroller >

            <div class="blackheader"></div>
            <div class="header">
                <text class="header-word">Redeem Your Points for Free Gift Cards</text>
            </div>
            <div class="overflow-mid">
            <div class="mid-card">
                <div class="mid-card-item1">
                    <div><image class="img-icon" src="bmlocal://assets/pic-coupon.png"></image></div>
                    <div class="mid-card-text">
                        <text class="mid-card-text1">Start Your Drop Campagin</text>
                        <text class="mid-card-text2">Earn 5 point for every Rs.10 you spend</text>
                    </div>
                </div>

                <div class="mid-card-item2">
                    <div><image class="img-icon" src="bmlocal://assets/pic-gift.png"></image></div>
                    <div class="mid-card-text">
                        <text class="mid-card-text1">Help Your Friends Drop Price</text>
                        <text class="mid-card-text2">Earn 1 point for every Rs.10 you friend spend</text>
                    </div>
                </div>

                <div class="bottom-btn" @click="openDetail">
                    <text class="bottom-btn-txt">Learn More</text>
                </div>

            </div></div>
            <div class="overflow-gift" v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'overflow-gift-bottom':'',]">
                <div class="gift-card" @click="redeemCard(i)">
                    <image class="gift-card-img"  :src="i.image"></image>
                    <div class="gift-card-txt">
                        <text class="gift-card-txt1">{{i.name}} Gift Card</text>
                        <text class="gift-card-txt2">{{i.pointNumber}} Points Needed</text>
                    </div>
                    <!--<div class="gift-card-txt" v-if="i.id==1">-->
                        <!--<text class="gift-card-txt1">Rs.150 Gift Card</text>-->
                        <!--<text class="gift-card-txt2">1,500 Points Needed</text>-->
                    <!--</div>-->
                    <!--<div class="gift-card-txt" v-if="i.id==2">-->
                        <!--<text class="gift-card-txt1">Rs.200 Gift Card</text>-->
                        <!--<text class="gift-card-txt2">2,000 Points Needed</text>-->
                    <!--</div>-->
                </div>
            </div>
        </scroller>
    </div>
</template>

<script>
    export default {
        name: 'index',
        data () {
          return {
              cardArr: []
          }
        },
        created () {
            this.getCard()
        },
        methods: {
            // get.card
            getCard () {
                this.$fetch({
                    method: 'GET',
                    name: 'get.card',
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.cardArr = res
                    // this.$notice.alert({
                    //     message: res
                    // })
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            openDetail () {
                this.$router.open({
                    name: 'card.detail',
                    type: 'PUSH'
                })
            },
            redeemCard (id) {
                this.$router.open({
                    name: 'redeem.card',
                    type: 'PUSH',
                    params: {
                        card: id
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .wrapper {
        background-color: #f4f4f4;
    }
    .iconfont{
        font-family: iconfont;
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
    .header{
        background-color: #EF8A31;
        height: 302px;
        border-bottom-left-radius: 50%   ;
        border-bottom-right-radius: 50%;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .header-word{
        font-family: ProximaNova-Bold;
        font-size: 40px;
        font-weight: 700;
        width: 464px;
        color: #FFFFFF;
        text-align: center;
        line-height: 64px;
    }
    .overflow-mid{
        flex-direction: row;
        justify-content: center;
        margin-top: -70px;
    }
    .overflow-gift-bottom{
        margin-bottom: 150px;
    }
    .mid-card{
        width: 656px;
        height: 420px;
        background-color: white;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
        flex-direction: column;
        align-items: stretch;

    }
    .mid-card-item1{
        margin-top: 64px;
        margin-left: 32px;
        flex-direction: row;
        justify-content: flex-start;
    }
    .mid-card-item2{
        margin-top: 54px;
        margin-left: 32px;
        flex-direction: row;
        justify-content: flex-start;
    }
    .mid-card-text{
        margin-left: 48px;
    }
    .mid-card-text1 {
        font-family: ProximaNova-Regular;
        margin-bottom: 16px;
        font-weight: 700;
        font-size: 28px;
        color: rgba(0, 0, 0, 0.87);
    }
    .mid-card-text2{
        color: rgba(0, 0, 0, 0.54);
        font-size: 24px;
        font-family: ProximaNova-Regular;
    }
    .bottom-btn{
        align-items: center;
        margin-top: 48px;

    }
    .bottom-btn-txt{
        background-color: #EF8A31;
        padding-top: 12px;
        font-size: 24px;
        text-align: center;
        color: white;
        width: 234px;
        height: 56px;
        border-radius: 4px;
    }
    .overflow-gift{
        flex-direction: row;
        justify-content: center;
        margin-top: 32px;
    }
    .gift-card{
        box-shadow: 0 1px 6px 0 rgba(0,0,0,0.12);
        border-radius: 24px;
    }
    .gift-card-img{
        width: 656px;
        height: 280px;
    }
    .gift-card-txt{
        width: 656px;
        flex-direction: row;
        justify-content: space-between;
        padding-top:26px ;
        padding-bottom:26px ;
        padding-left:24px ;
        padding-right:24px ;
    }
    .gift-card-txt1{
        font-family: ProximaNova-Bold;
        font-weight: 700;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-txt2{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
</style>
