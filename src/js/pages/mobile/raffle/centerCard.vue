<template>
    <div class="wrapper">
        <div class="triangle-topright" v-if="item.isDraw">
            <text  class="triangle">&#xe741;</text>
            <text  class="tick">Joined</text>
        </div>
        <div class="goods">
            <text class="goods-h">Lucky Draw Prize</text>
            <!--<text class="goods-h">{{item.}}</text>-->
            <text class="goods-t">{{item.product}}</text>
            <div class="goods-p">
                <text class="goods-p1">₹0.00</text>
                <text class="goods-p2">₹{{item.saleUnitPrice+'.00'}}</text>
                <text class="goods-p3">Free Shipping</text>
            </div>
        </div>
        <div class="goods-image">
            <image class="goods-i" resize="contain" :src="item.image"></image>
        </div>

        <div v-if="item.drawStatus == 'Ongoing'|| item.drawStatus == 'Ended'" style="margin-left: 48px">
            <div class="goods-p">
                <text class="goods-num">1st Prize</text>
                <div class="goods-pri">
                    <text class="goods-p-b">Free Product - </text><text class="goods-p-o">{{item.quantity}}</text> <text class="goods-p-b"> {{item.quantity > 1 ? 'Participants' : 'Participant'}}</text>
                </div>
            </div>
            <div class="goods-p" style="margin: 8px 0;">
                <text class="goods-num">2nd Prize</text>
                <div class="goods-pri">
                    <text class="goods-p-b">{{item.secondPrize}} Voucher - </text><text class="goods-p-o">{{item.discountSecond}}%</text> <text class="goods-p-b"> of Participants</text>
                </div>
            </div>
            <div class="goods-p">
                <text class="goods-num">3rd Prize</text>
                <div class="goods-pri">
                    <text class="goods-p-b">{{item.thirdPrize}} Voucher - </text><text class="goods-p-o">{{item.discountThird}}%</text> <text class="goods-p-b"> of Participants</text>
                </div>

            </div>
            <div class="goods-p" v-if="loginS"  style="margin-bottom: 48px">
                <div v-if="item.drawStatus == 'Ongoing'">
                    <div v-if="!item.isDraw" class="goods-p margin-top-0">
                        <text class="goods-num">Time Left to Join</text>
                        <text class="goods-time">{{ahour||'00'}}:{{amin||'00'}}:{{asecond||'00'}}</text>
                    </div>
                    <div v-if="item.isDraw" class="goods-p margin-top-0">
                        <text class="goods-num">Winner Announcement Time:</text>
                        <text class="goods-time"> {{tranDateM(item.endTime)}}</text>
                        <!--<text class="goods-time"> 9:00 am, Aug 18th</text>-->
                    </div>
                </div>
                <div v-if="item.drawStatus == 'Ended'">
                    <div  class="goods-p margin-top-0">
                        <text class="goods-num">Winner Announcement Time:</text>
                        <text class="goods-time"> {{tranDateM(item.endTime)}}</text>
                        <!--<text class="goods-time"> 9:00 am, Aug 18th</text>-->
                    </div>
                </div>

            </div>

            <div class="goods-p" v-if="!loginS"  style="margin-bottom: 48px">
                <div v-if="item.drawStatus == 'Ongoing'" class="goods-p margin-top-0">
                    <text class="goods-num">Time Left to Join</text>
                    <text class="goods-time">{{ahour||'00'}}:{{amin||'00'}}:{{asecond||'00'}}</text>
                </div>
                <div   v-if="item.drawStatus == 'Ended'" class="goods-p margin-top-0">
                    <text class="goods-num">Winner Announcement Time:</text>
                    <text class="goods-time"> {{tranDateM(item.endTime)}}</text>
                    <!--<text class="goods-time"> 9:00 am, Aug 18th</text>-->
                </div>
            </div>

            <div class="goods-people" v-if="item.drawCount>0" style="margin-bottom: 50px">
                <div class="goods-a" v-for="i in item.images.slice(0,6).reverse()">
                    <image class="goods-a-i" :src="i"></image>
                </div>
                <text class="goods-w">{{item.drawCount}} people joined</text>
            </div>
        </div>

        <div v-if="item.drawStatus == 'Scheduled'">
            <div class="goods-snum-d">
                <text class="goods-snum" >Starts In</text>
            </div>
            <div class="overflow-center-time">
                <div class="center-time">
                    <text class="center-time-hh">{{ahour||'00'}}</text>
                    <text style="font-size: 24px; padding-top: 35px;">:</text>
                    <text class="center-time-hh">{{amin||'00'}}</text>
                    <text style="font-size: 24px; padding-top: 35px;">:</text>
                    <text class="center-time-hh">{{asecond||'00'}}</text>
                </div>
            </div>
        </div>

        <div class="goods-btn" v-if="item.drawStatus == 'Ongoing' ">

            <!--<div class="goods-btn-s " @click="openshare()" v-if="item.isDraw" >-->
            <!--<div class="goods-btn-s " @click="openshare()">-->
                <!--<text class="iconfont goods-btn-sicon" >&#xe74b;</text>-->
                <!--<text class="goods-btn-sword"  >Share to Your Friends</text>-->
            <!--</div>-->
            <text class="goods-btn-w-bw" @click="openNewPage()" v-if="item.isDraw">See the Winners</text>

            <text class="goods-btn-w" @click="opendialog()" v-if="!item.isDraw">Join Now & Win Prize</text>

        </div>
        <div class="goods-btn" v-if="item.drawStatus == 'Scheduled'" style="margin-top: 96px" >
            <div class="goods-btn-s " @click="openshare()"  v-if="false">
                <text class="iconfont goods-btn-sicon" >&#xe74b;</text>
                <text class="goods-btn-sword"  >Share to Your Friends</text>
            </div>
        </div>
        <div class="goods-btn" v-if="item.drawStatus == 'Ended'">
            <text class="goods-btn-w-bw" v-if="!item.prizePublic" @click="openNewPage()">See the Winners</text>

            <text class="goods-btn-b" v-if="item.prizePublic" @click="openPage()">See the Winners</text>
        </div>
    </div>
</template>

<script>
    const common = weex.requireModule('CommonUtils');
    import { WxcMask } from 'weex-ui';
    import { baseUrl } from '../../../config/apis';
    import dayjs from 'dayjs';
    export default {
        components: { WxcMask },
        data () {
            return {
                hasAnimation: true,
                show: false,
                isShow: false,
                cItem: {},
                aday: '',
                ahour: '',
                amin: '',
                asecond: '',
                tranTime: false,
                btnS: false,
                userAvatar: [],
                user: ''
            }
        },
        props: ['item', 'itemIndex', 'selindex', 'loginS'],
        created () {
            this.user = this.$storage.getSync('user')
            this.cItem = this.item
            if (this.selindex == this.itemIndex) {
                if (this.item.drawStatus == 'Ongoing') {
                    if (this.item.isDraw) {
                        clearInterval(this.tranTime)
                    } else {
                        this.countDate(this.cItem.endTime)
                    }
                } else {
                    this.countDate(this.cItem.startTime)
                }
            } else {
                clearInterval(this.tranTime)
            }
        },
        watch: {
            'selindex': {
                handler: function (val, oldVal) {
                    if (this.selindex == this.itemIndex) {
                        if (this.item.drawStatus == 'Ongoing') {
                            if (this.item.isDraw) {
                                clearInterval(this.tranTime)
                            } else {
                                this.countDate(this.cItem.endTime)
                            }
                        } else {
                            this.countDate(this.cItem.startTime)
                        }
                    } else {
                        clearInterval(this.tranTime)
                    }
                },
                deep: true
            }
        },
        methods: {
            opendialog () {
                this.$emit('openMask')
            },
            openshare () {
                this.$emit('openShare')
            },
            openPage () {
                this.$router.open({
                    name: 'raffle.result',
                    type: 'PUSH',
                    params: {
                        id: this.item.id
                    }
                });
            },
            openNewPage () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/lottery/draw/detail/${this.item.id}`,
                    data: {
                        user_id: this.user.id
                    }
                }).then((res) => {
                    this.item = res;
                    this.$emit('changeItem', {
                        status: 'changeItem',
                        data: {
                            index: this.itemIndex,
                            item: this.item
                        }
                    });
                    this.$notice.loading.hide();
                    if (!res.prizePublic) {
                        this.$notice.toast({
                            message: ' Please check it at the annoucement time. Thanks! '
                        });
                    } else {
                        if (res.drawStatus == 'Ended') {
                            this.$router.open({
                                name: 'raffle.result',
                                type: 'PUSH',
                                params: {
                                    id: this.item.id
                                }
                            })
                        } else {
                            this.$notice.toast({
                                message: ' Please check it at the annoucement time. Thanks! '
                            });
                        }
                    }
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            changeStates () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/lottery/draw/detail/${this.item.id}`,
                    data: {
                        user_id: this.user.id
                    }
                }).then((res) => {
                    this.item = res;
                    this.$emit('changeItem', {
                        status: 'changeItem',
                        data: {
                            index: this.itemIndex,
                            item: this.item
                        }
                    });
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                });
            },
            tranDateM (tmp) {
                if (tmp) {
                    return dayjs(new Date(tmp).getTime() + 2 * 60 * 60 * 1000).format('HH:mm a, MMM DD')
                }
            },
            countDate (time) {
                // this.$notice.toast({
                //     message: '1111'
                // })
                const self = this;
                // if (this.purchaseMethod == 'flash') {
                self.tranTime = setInterval(() => {
                    self.NOW_DATE = new Date().getTime();

                    const total = (new Date(time).getTime() - self.NOW_DATE) / 1000;

                    if (total <= 0) {
                        clearInterval(self.tranTime);
                        if (self.item.drawStatus == 'Ongoing') {
                            self.changeStates();
                        }
                        if (self.item.drawStatus == 'Scheduled') {
                            self.changeStates();
                        }
                        return;
                    }

                    const day = Math.floor(total / (24 * 60 * 60))// 整天

                    self.aday = day
                    const afterDay = total - day * 24 * 60 * 60;
                    self.ahour = Math.floor(afterDay / (60 * 60)); // 小时
                    const afterHour = total - day * 24 * 60 * 60 - self.ahour * 60 * 60;
                    self.amin = Math.floor(afterHour / 60); // 分钟
                    if (self.amin < 10) {
                        self.amin = '0' + self.amin
                    }

                    const afterMin = total - day * 24 * 60 * 60 - self.ahour * 60 * 60 - self.amin * 60;
                    self.asecond = Math.floor(afterMin)// 秒
                    if (self.asecond < 10) {
                        self.asecond = '0' + self.asecond
                    }
                    // 加上减掉的天数
                    self.ahour += (self.aday * 24)

                    if (self.ahour < 10) {
                        self.ahour = '0' + self.ahour
                    }
                    // this.$notice.toast({
                    //     message: self.aday + '天' + self.ahour + ':' + self.amin + ':' + self.asecond
                    // })
                }, 1000);
                // }
            }
        }
    }
</script>

<style scoped>
    .wrapper{
        /*margin-left: 48px;*/
    }
    .iconfont{
        font-family: iconfont;
    }
    .goods{
        margin-left: 48px;
    }
    .goods-h{
        font-family: ProximaNova;
        font-size: 38px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        font-weight: 700;
        margin-top: 48px;
    }
    .goods-t{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        height: 30px;
        width: 500px;
        text-align: left;
        margin: 4px 0 8px 0;
    }
    .goods-p{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 8px;
    }
    .goods-p1{
        font-family: ProximaNova;
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        font-weight: 700;
        margin-right: 14px;
    }
    .goods-p2{
        font-family: ProximaNova;
        font-size: 20px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        text-decoration: line-through;
        margin-right: 18px;
    }
    .goods-p3{
        font-family: ProximaNova-Regular;
        font-size: 20px;
        background-color: #00CFE3;
        color: white;
        padding: 2px 6px;
        border-radius: 4px;
    }
    .goods-image{
        /*flex-direction: row;*/
        /*justify-content: center;*/
        /*align-items: center;*/
        margin: 20px 0 20px 48px;
    }
    .goods-i{
        width: 400px;
        height: 380px;
        margin-left: 64px;
    }
    .goods-num{
        font-family: ProximaNova;
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        font-weight: 700;
        text-align: left;
        margin-right: 16px;
    }
    .goods-pri{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .goods-p-o{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #EF8A31;
        letter-spacing: 0;
        text-align: left;
    }
    .goods-p-b{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: black;
        letter-spacing: 0;
        text-align: left;
    }
    .goods-time{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #EF8A31;
        letter-spacing: 0;
        text-align: left;
    }
    .goods-people{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .goods-a {
        flex-direction: row;
        justify-content: start;
        border-radius: 50%;
    }
    .goods-a-i{
        width: 48px;
        height: 48px;
    }
    .goods-w{
        opacity: 0.38;
        font-size: 20px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        margin-left: 10px;
    }
    .goods-btn{
        width: 624px;
        height:62px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-bottom: 64px;
        margin-left: 15px;
        /*background-color: #689de5;*/
    }
    .goods-btn-w-bw{
        font-size: 24px;
        font-weight: 700;
        background-color: white;
        color: #00CFE3;
        padding: 0 32px;
        line-height: 60px;
        text-align: center;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        border-width: 1px ;
        border-style: solid ;
        border-color: #00CFE3;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.08);
        border-radius: 32px;
    }
    .goods-btn-w{
        font-size: 24px;
        font-weight: 700;
        background-color: #EF8A31;
        color: white;
        padding: 0 32px;
        line-height: 64px;
        text-align: center;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.08);
        border-radius: 32px;
    }
    .goods-btn-b{
        font-size: 24px;
        font-weight: 700;
        color: white;
        background-color:#00CFE3;
        padding: 0 32px;
        line-height: 64px;
        text-align: center;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.08);
        border-radius: 32px;
    }
    .goods-btn-s{
        background-color: white;
        color:black;
        padding: 18px 32px;
        border: 2px solid rgba(0,0,0,0.08);
        box-shadow: 0 2px 2px 0 rgba(0,0,0,0.24);
        text-align: center;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        border-radius: 50%;
    }
    .goods-btn-sword{
        color:black;
        font-weight: 700;
        font-size: 24px;
        padding: 18px 32px;
    }
    .goods-btn-sicon{
        text-align: center;
        margin-left: 25px;
        color:#00CFE3;
    }
    .goods-snum-d{
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .goods-snum{
        font-size: 32px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        font-weight: 700;
    }
    .overflow-center-time{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin: 34px 0 8px 48px ;
    }
    .center-time-word {
        color: black;
        font-size: 24px;
    }
    .center-time{
        font-size: 28px;
        color: #FFFFFF;
        flex-direction: row;
        justify-content: start;
        margin-right: 32px;
    }
    .center-time-hh{
        font-family: ProximaNova;
        background-color: white;
        border: 1px solid rgba(0,0,0,0.08);
        box-shadow: 0 2px 2px 0 rgba(0,0,0,0.24);
        color: black;
        border-radius: 6px;
        padding: 16px;
        margin: 18px;
        font-size: 28px;
    }
    .triangle-topright{
        position: absolute;
        border-radius: 24px;

        top:-11px;
        right:0;
    }
    .tick{
        position: relative;
        top:-105px;
        right:-58px;
        color: white;
        font-size: 24px;
    }
    .triangle{
        position: relative;
        top:10px;
        left:0;
        font-family: iconfont;
        font-size: 140px;
        color: #00CFE3;
    }
    .margin-top-0{
        margin-top: 0;
    }
</style>
