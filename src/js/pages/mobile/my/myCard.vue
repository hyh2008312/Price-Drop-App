<template>
    <div class="wrapper">
        <scroller >
        <div class="blackheader"></div>
        <topic-header title="My Gift Cards" leftBtn="icon"  ref="ref1" ></topic-header>
        <div style="margin-top: 160px">

            <div v-if="cardArr.length===0" class="empty-div">
                <image src="bmlocal://assets/empty.png" class="empty-img"></image>
                <text class="empty-txt">You haven’t redeemed any gift cards yet.</text>
            </div>


            <div class="overflow-gift" v-if="cardArr.length!==0" v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'overflow-gift-bottom':'',]">
                <div class="gift-card">
                    <image class="gift-card-img"  :src="i.image"></image>
                    <div class="gift-card-txt">
                        <div class="gift-card-right-txt">
                            <text class="gift-card-txt1">{{i.name}} Gift Card</text>
                            <text class="gift-card-txt2">Only vaild for order above Rs.{{i.lowestAmount}}</text>
                        </div>

                        <text class="gift-card-txt3">Expired in&nbsp;{{tranDate(i.expiredTimestamp)}}&nbsp;days</text>
                    </div>
                </div>
            </div>
        </div>
        </scroller>
    </div>
</template>

<script>
    import header from './header';
    export default {
        components: {
            'topic-header': header
        },
        name: 'myCard',
        data () {
            return {
                cardArr: []
            }
        },
        created () {
          this.getCard()
        },
        methods: {
            getCard () {
                this.$fetch({
                    method: 'GET',
                    name: 'point.exchange', // 通过get 获取我自己的积分卡
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.cardArr = res
                    // this.cardArr = []
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            tranDate (tamp) {
                const now = new Date().getTime()
                const total = tamp - now / 1000
                const day = Math.floor(total / (24 * 60 * 60)); // 整天
                return day;
            }
        }
    }
</script>

<style scoped>
    .wrapper{
        background-color: #f4f4f4;
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
        font-family: ProximaNova-Bold;
        font-weight: 700;
        font-size: 24px;
        margin-bottom: 16px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-txt2{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-txt3{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #EF8A31;
    }
    .empty-div{
        flex-direction: column;
        justify-content:flex-start;
        align-items: center;
        height: 300px;
        margin-top: 40px;
        /*background-color: black;*/
    }
    .empty-img{
        width: 200px;
        height: 200px;
    }
    .empty-txt{
        opacity: 0.54;
        font-family: ProximaNova-Bold;
        font-size: 24px;
        color: #000000;
    }
</style>
