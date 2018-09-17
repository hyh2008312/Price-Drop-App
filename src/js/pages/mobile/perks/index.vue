<template>
    <div class="wrapper">
        <scroller >

            <div class="blackheader"></div>
            <div class="header">
                <text class="header-word" @click="openNewPage">Redeem Your Points for Free Gift Vouchers</text>
            </div>
            <div class="overflow-mid">

                <div class="mid-card">
                    <!--<div class="notice-wrapper">-->
                        <!--<div class="notice-bg">-->
                            <!--<slider :items="block1.items" v-if="block1.items.length > 0" @noticeFinished="noNoticeFinished"></slider>-->
                        <!--</div>-->
                    <!--</div>-->
                    <div class="mid-card-item1">
                        <div><image class="img-icon" src="bmlocal://assets/pic-coupon.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text1">Make a Successful Purchase</text>
                            <div class="count-div">
                                <text class="mid-card-text2">Earn</text><text class="mid-card-text2b"> 200 points</text><text class="mid-card-text2"> for every</text><text class="mid-card-text2b"> Rs.50</text><text class="mid-card-text2"> you spend</text>
                            </div>

                            <div class="count-div" style="margin-top: 6px;">
                                <text class="mid-card-text2">Earn</text><text class="mid-card-text2b"> 200 points</text><text class="mid-card-text2"> by dropping price to</text><text class="mid-card-text2b"> 50% OFF</text>
                            </div>

                            <div class="count-div" style="margin-top: 6px;">
                                <text class="mid-card-text2">Earn</text><text class="mid-card-text2b"> 300 points</text><text class="mid-card-text2"> by dropping price to</text><text class="mid-card-text2b"> 70% OFF</text>
                            </div>

                        </div>
                    </div>

                    <div class="mid-card-item2">
                        <div><image class="img-icon" src="bmlocal://assets/pic-gift.png"></image></div>
                        <div class="mid-card-text">
                            <text class="mid-card-text1">Help Your Friends Drop Price</text>
                            <div class="count-div">
                                <text class="mid-card-text2">Earn</text><text class="mid-card-text2b"> 100 points</text><text class="mid-card-text2"> for every</text><text class="mid-card-text2b"> Rs.50</text><text class="mid-card-text2"> your friends spend</text>
                            </div>

                        </div>
                    </div>

                    <div class="bottom-btn" @click="openDetail">
                        <text class="bottom-btn-txt">Learn More</text>
                    </div>

                </div>
            </div>
            <div class="overflow-gift"  v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'overflow-gift-bottom':'',]">
                <div class="gift-card" @click="redeemCard(i)">
                    <image class="gift-card-img"  :src="i.image"></image>
                    <div class="gift-card-txt">
                        <text class="gift-card-txt1">{{i.name}} Gift Voucher</text>
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
    import slider from './slider';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        name: 'index',
        components: {
            'slider': slider,
        },
        data () {
          return {
              cardArr: [
                  {
                      image: '',
                      name: '',
                      pointNumber: ''
                  }
              ],
              backup: [],
              block1: {
                  title: '',
                  url: '',
                  items: []
              }
          }
        },
        created () {
            googleAnalytics.trackingScreen('Perks');
            this.getCard()
        },
        methods: {
            // get.card
            getCard () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'get.card'
                }).then((res) => {
                    this.cardArr = res
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            noNoticeFinished (e) {
                if(this.backup.length > 0) {
                    const newArr = this.backup.splice(0, 4);
                    this.block1.items = [...newArr];
                } else {
                    this.block1.items = [];
                    this.$fetch({
                        method: 'GET',
                        name: 'promotion.get.list',
                        data: {}
                    }).then(resData => {
                        this.backup = [...resData];
                        const newArr = this.backup.splice(0, 4);
                        this.block1.items = [];
                        this.block1.items = [...newArr];
                    }, error => {
                        // if(error.status == 10) {
                        //     this.hasWifi = false;
                        // }
                    })
                }
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
            },
            openNewPage () {
                this.$router.open({
                    name: 'raffle',
                    type: 'PUSH'
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
    .notice-wrapper {
        width: 750px;
        height: 50px;
        justify-content: start;
        align-items: center;
        overflow: hidden;
        margin-top: 32px;
    }
    .notice-bg{
        width: 622px;
        height: 80px;
        background-color: #FFFFFF;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(0,0,0,0.12);
        border-radius: 40px;
        overflow: hidden;
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
        border-radius: 50%;
        /*border-bottom-left-radius: 225%;*/
        /*border-bottom-right-radius: 225%;*/
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
        width: 686px;
        height: 520px;
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
        color: black;
        line-height: 38px;
        font-size: 24px;
        font-family: ProximaNova-Regular;
    }
    .mid-card-text2b{
        color: #EF8A31;
        font-size: 24px;
        line-height: 38px;
        font-weight: 700;
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
        background-color: white;
    }
    .gift-card-img{
        width: 686px;
        height: 292px;
    }
    .gift-card-txt{
        width: 686px;
        flex-direction: row;
        justify-content: space-between;
        background-color: white;
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
        padding-right: 28px;
        color: rgba(0,0,0,0.87);
    }

    .count-div{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }
</style>
