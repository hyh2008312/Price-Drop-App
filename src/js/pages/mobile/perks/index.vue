<template>
    <div class="wrapper">
        <div class="blackheader"></div>

        <scroller v-if="hasWifi">
            <!--<cell><text>111</text></cell>-->
            <div class="header">
                <!--<text class="close iconfont" @click="$router.back();" >&#xe6f6;</text>-->
                <text class="header-word" >Earn Points & Rewards Every Day</text>
            </div>

            <div class="overflow-mid2" >
                <!--<div class="mid-head" >-->
                    <!--<text class="m-h1">Check In & Earn Points</text>-->
                <!--</div>-->
                <div class="o-m-head">
                    <text class="o-mt1">Check In & Earn Points</text>
                    <div class="m2-r" @click="openRulerPage(2)">
                        <text class="m2-rw" >Rules </text>
                        <text class="iconfont" style="color:#1D7CD4;font-size: 24px ">&#xe626;</text>
                    </div>
                </div>
                <div class="m2-content">
                    <div class="m2-c1">
                        <image style=" width: 32px;height: 32px;" src="bmlocal://assets/perks/goldcoin.png"></image>
                        <text class="m2-c1w"> My Points</text>
                        <text class="m2-c1p">{{user.points||'0'}}</text>
                    </div>

                    <div class="m2-c2" ref="getBtn1" v-if="!signObj.isSign"  @click="getSign">
                        <div class="m2-c2-shadow" >
                            <text class="m2-c2w">Claim {{(signObj.originalPoints+(((signObj.signTimes % 15) <= 6 ? (signObj.signTimes % 15) : 6)*signObj.gradientPoints))||0}} Points Today</text>
                            <text class="m2-c2a iconfont">&#xe626;</text>
                        </div>
                    </div>

                    <div class="m2-c2r"  v-if="signObj.isSign">
                        <div class="m2-c2-shadowr" >
                            <text class="m2-c2wr">Claim {{signObj.originalPoints+(((signObj.signTimes % 15) <= 6 ? (signObj.signTimes % 15) : 6)* signObj.gradientPoints)}} Points Tomorrow</text>
                            <text class="m2-c2ar iconfont"> &#xe6ed;</text>
                        </div>
                    </div>


                    <div class="m2-c3" v-if="!signObj.isSign">
                        <!--<image style=" width: 26px;height: 26px;margin-right:10px;" src="bmlocal://assets/perks/goldcoin.png"></image>-->
                        <text class="iconfont m2-c3-gift">&#xe75e;</text>
                        <text class="m2-c3w">Check-In every day for up to 5700 points!</text>
                    </div>
                    <div class="m2-c3" v-if="signObj.isSign">
                        <!--<text class="m2-c3w">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</text>-->
                    </div>
                </div>

                <!--<div class="m2-c4">-->
                <scroller class="s-box" scroll-direction="horizontal" flex-direction="row">
                    <div class="s-box-c" >
                        <div class="single" v-for="(i,index) in scrollerPoint">
                            <div class="single-dot">
                                <image style=" width: 44px;height: 44px;" v-if="index >= signObj.signTimes " src="bmlocal://assets/perks/8.png"></image>
                                <text class="single-word">{{i.p}}</text>
                                <image style=" width: 44px;height: 44px;" v-if="index < signObj.signTimes " src="bmlocal://assets/perks/7.png"></image>
                                <div :class="[index<14?'single-line':'single-line-last']" ></div>
                            </div>
                            <div>
                                <text class="single-day" v-if="index==0">1st</text>
                                <text class="single-day" v-else-if="index==1">2nd</text>
                                <text class="single-day" v-else-if="index==2">3rd</text>
                                <text class="single-day" v-else>{{index+1}}th</text>
                            </div>

                        </div>
                    </div>
                </scroller>
                <!--</div>-->
            </div>


            <!--<div class="overflow-mid1" >-->
                <!--<div class="top-card">-->
                    <!--<text class="t-c-h">Redeem Cash Bonus</text>-->
                <!--</div>-->
                <!--<div class="t-cm">-->
                    <!--<div class="t-cm-c" v-if="!isCash"  @click="getPoints">-->
                        <!--<text class="t-cm-cw" >Claim daily cash bonus. Up to ₹100</text>-->
                        <!--<text class="t-cm-ca iconfont" ref="getArrow">&#xe626;</text>-->
                    <!--</div>-->
                    <!--<div class="t-cm-cr" v-if="isCash" >-->
                        <!--<text class="t-cm-cwr">Earned ₹ {{points}} Bonus!</text>-->
                        <!--<text class="t-cm-car iconfont" >&#xe6ed;</text>-->
                    <!--</div>-->
                <!--</div>-->
                <!--<div class="t-cb" @click="openRulerPage(1)">-->
                    <!--<text class="t-cbw" >Rules </text>-->
                    <!--<text class="iconfont" style="color:white;font-size: 18px ">&#xe626;</text>-->
                <!--</div>-->
                <!--&lt;!&ndash;<div class="w-img">&ndash;&gt;-->
                    <!--<image class="w-i5"  style=" width: 100px;height: 43px;" src="bmlocal://assets/perks/5.png"></image>-->
                    <!--<image class="w-i3"  style=" width: 62px;height: 98px;" src="bmlocal://assets/perks/3.png"></image>-->
                    <!--<image class="w-i1"  style=" width: 60px;height: 110px;" src="bmlocal://assets/perks/1.png"></image>-->

                    <!--<image class="w-i6"  style=" width: 98px;height: 106px;" src="bmlocal://assets/perks/6.png"></image>-->

                    <!--<image class="w-i2"  style=" width: 100px;height: 102px;" src="bmlocal://assets/perks/2.png"></image>-->
                    <!--<image class="w-i4"  style=" width: 80px;height: 96px;" src="bmlocal://assets/perks/4.png"></image>-->
                <!--&lt;!&ndash;</div>&ndash;&gt;-->
            <!--</div>-->


            <div class="overflow-mid2 mg-b3">
                <div class="o-m-head">
                    <text class="o-mt1">Redeem Bonus with Points</text>
                    <div class="m2-r" @click="openRulerPage(1)" >
                        <text class="m2-rw" >Rules </text>
                        <text class="iconfont" style="color:#1D7CD4;font-size: 24px ">&#xe626;</text>
                    </div>
                </div>
                <div class="red-block">
                    <image class="rb-bg-img" src="bmlocal://assets/perks/cash-bonus-2.png"></image>
                    <div class="red-right" v-if="!isCash" @click="getPoints">
                        <div class="rr-top">
                            <text class="rrt-t1">Up to ₹100 daily bonus</text>
                            <div class="rrt-btn">
                                <text class="rrt-btn-t">Claim Now</text>
                                <text class="iconfont" style="font-size: 22px;color: #EC8830;font-weight: 700;margin-left: 4px;">&#xe626;</text>
                            </div>
                        </div>
                        <text class="rrt-t2">400 Points Needed</text>
                    </div>

                    <div class="red-right1" v-if="isCash">
                        <div class="red-right" style="margin-right: 34px">

                            <div class="rr-top">
                                <text class="rrt-t3">Your have claimed</text>
                                <text class="rrt-t4"> ₹ {{points}}. </text>
                            </div>
                            <text class="rrt-t3">The bonus will expire at 23:59:59 today!</text>
                        </div>

                        <text v-if="isCash" class="iconfont rrt1-icon" > &#xe6ed;</text>

                    </div>

                </div>
            </div>



            <!--<div class="overflow-gift-card" >-->
                <!--<div class="top-right-div">-->
                    <!--<image class="top-right" src="bmlocal://assets/anger.png"></image>-->
                <!--</div>-->
                <!--<div>-->
                    <!--<text class="g-ch-w"  style="">Redeem Gift Voucher</text>-->
                <!--</div>-->
                <!--<div >-->
                    <!--<div v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'mg-b5':'mg-b3',]">-->

                        <!--<div class="gift-card" @click="redeemCard(i)">-->
                            <!--<image class="gift-card-img"  :src="i.newImageDetail"></image>-->
                            <!--<div class="gift-card-txt">-->
                                <!--<text class="gift-card-txt1">{{i.name}} Gift Voucher</text>-->
                                <!--<div class="gift-card-txtr">-->

                                    <!--<text class="gift-card-txt2">{{i.pointNumber}} Points Needed</text>-->
                                    <!--<text class="gift-card-icon iconfont">&#xe626;</text>-->
                                <!--</div>-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->

            <div class="overflow-mid2 mg-b5">
                <div class="o-m-head">
                    <text class="o-mt1">Redeem Voucher with Points</text>
                    <div class="m2-r" >
                        <!--<text class="m2-rw" >Rules </text>-->
                        <!--<text class="iconfont" style="color:#1D7CD4;font-size: 24px ">&#xe626;</text>-->
                    </div>
                </div>
                <scroller scroll-direction="horizontal" flex-direction="row" class="card-row">
                    <div class="cr-content">
                        <div class="cr-card" v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'mg-r32':'',]" @click="redeemCard(i)">
                            <image  :src="i.newImageMini" style="width: 272px;height: 205px;"></image>
                            <!--<div style="width: 272px;height: 205px;background-color: #EF8A31;"></div>-->
                            <div class="cr-bottom" >

                                <text class="crc-t1">Valid on order above ₹{{parseInt(i.lowestAmount)}}</text>
                                <text class="crc-t2">{{i.pointNumber}} Points Needed</text>
                                <div class="crc-btn">
                                    <text class="crc-btn-t">Redeem</text>
                                    <text class="iconfont" style="font-size: 18px;color: #EC8830;font-weight: 700;">&#xe626;</text>
                                </div>
                            </div>

                        </div>
                    </div>

                </scroller>

            </div>
            <div>
                <text>&nbsp;</text>
                <text>&nbsp;</text>
            </div>

        </scroller>

        <no-wifi v-if="!hasWifi" @onReload="initPage"></no-wifi>


        <WxcMask
            height="798"
            width="592"
            border-radius="16"
            duration="200"
            mask-bg-color="#FFFFFF"
            :has-animation="hasAnimation"
            :has-overlay="true"
            :show-close="false"
            :show="show"
            @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="mask-content">
                <div class="mask-h" >
                    <text>&nbsp;</text>
                    <text class="iconfont mask-h-c">&#xe632;</text>
                </div>
                <div class="mask-t">
                    <div style="width: 686px;">

                        <div  style="height: 400px;" >
                            <div class="mask-tn" ref="giftW">
                                <text class="mask-tnw">&nbsp;₹{{points||0}}</text>
                            </div>
                            <image v-if="giftBox" ref="giftH" class="g-b-h"   src="bmlocal://assets/perks/cover.png" ></image>
                            <image v-if="giftBox" ref="giftC" class="g-b-c" src="bmlocal://assets/perks/open-box.png"></image>
                        </div>
                    </div>

                    <text class="mask-t-w1">Congrats</text>
                    <div class="mask-t-w2-d">
                        <text class="mask-t-w2">You just received</text><text class="mask-t-w2b"> ₹{{points||0}} </text><text class="mask-t-w2">cash bonus!</text>
                    </div>
                    <text class="mask-t-w3">Your cash bonus will expire</text>
                    <text class="mask-t-w3b">by end of today. </text>
                    <div class="mask-t-w4" @click="openIndex">
                        <text class="mask-t-w4w">Spend Your Bonus Now</text>
                    </div>
                </div>
            </div>
        </WxcMask>

        <WxcMask
            height="287"
            width="562"
            border-radius="16"
            duration="200"
            mask-bg-color="rgba(255, 255, 255, 0)"
            :has-animation="hasAnimation"
            :has-overlay="true"
            :show-close="false"
            :show="pointSuccessShow"
            @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="mask-point-success-content">
                <image  src="bmlocal://assets/perks/congratulations.png" style="width: 562px;height: 106px"></image>
                <text class="mask-t-word" >Congrats!</text>
                <text class="mask-t-close iconfont" >&#xe632;</text>

                <div class="m-ps-word">
                   <text class="m-ps-w">{{(this.signObj.originalPoints + (((this.signObj.signTimes - 1) % 15) <= 6 ? ((this.signObj.signTimes - 1) % 15) : 6) * this.signObj.gradientPoints)}}</text>
                   <text class=""> points have been added to your </text>
                   <!--<text class="r-cw">3. You will be asked to spend 300 points to unlock your gift box after the first time.</text>-->
                   <!--<text class="r-cw">4. The cash bonus can only be used for shopping, which cannot be withdrawn.</text>-->
                </div>
                <text class="m-ps-word1">account now</text>
                <div class="m-ps-btn">
                    <text style="text-align: right;color: #EF8A31;">OK</text>
                </div>
            </div>
        </WxcMask>

        <WxcMask
            height="364"
            width="592"
            border-radius="16"
            duration="200"
            mask-bg-color="#FFFFFF"
            :has-animation="hasAnimation"
            :has-overlay="true"
            :show-close="false"
            :show="secShow"
            @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="mask-p-content">
                <div class="mask-p" >
                    <text>&nbsp;</text>
                    <text class="iconfont mask-p-c">&#xe632;</text>
                </div>
                <div class="mask-pw">
                    <bmrichtext class="mpw-t">
                        <bmspan class="mpw-t1" value='You need'></bmspan>
                        <bmspan class="mpw-t2" value=' 400 '></bmspan>
                        <bmspan class="mpw-t1" value='points to claim the bonus. Your current points balance is not enough.'></bmspan>
                    </bmrichtext>
                    <!--<text class="mask-pw1" >You need {{miniPoints}} points to open a gift box for bonus!</text>-->
                    <!--<text>You need 400 points to claim the bonus. Your current points balance is not enough.</text>-->
                    <!--<div class="mask-pw2">-->
                        <!--<text  class="mask-pw21">You need </text><text class="mask-pw22">{{miniPoints}}</text><text class="mask-pw21">points to claim the bonus. Your current points balance is not enough.</text>-->
                    <!--</div>-->

                </div>
                <div class="mask-p-btn">
                    <text class="mask-p-btnw"  @click="wxcMaskSetShareHidden">Earn More Points</text>
                </div>
            </div>
        </WxcMask>

        <WxcMask
            height="587"
            width="397"
            border-radius="16"
            duration="200"
            mask-bg-color="rgba(255, 255, 255, 0)"
            :has-animation="true"
            :has-overlay="true"
            :show-close="false"
            :show="newShow"
            @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="maskcontent">
                <div class="m-top">
                    <image src="bmlocal://assets/home/mask-head.png" style="width:397px;height:162px; "></image>
                </div>
                <text class="iconfont m-ic">&#xe632;</text>
                <text class="m-c">₹{{points}}</text>
                <div class="mid-content">
                    <text class="m-c-w">Your cash got bonus will expire</text>
                    <text class="m-c-w1">by 23:59:59 PM today</text>
                </div>
                <image src="bmlocal://assets/home/voucher-part.png" style="width: 398px;height: 28px;"></image>
                <div class="m-bottom">
                    <div class="overflow-mg">
                        <div style="border-radius: 32px;">
                            <image src="bmlocal://assets/home/maskBtn.png" style="width: 238px;height: 56px;"></image>
                        </div>
                    </div>

                    <text class="mb-word" @click="wxcMaskSetShareHidden">Go Shopping Now</text>
                </div>
            </div>
        </WxcMask>

    </div>
</template>

<script>
    const animation = weex.requireModule('animation')
    const dom = weex.requireModule('dom');
    import { WxcMask } from 'weex-ui';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    const common = weex.requireModule('CommonUtils');
    import noWifi from '../common/noWifi';

    export default {
        name: 'index',
        components: {
            WxcMask, noWifi
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
              block1: {
                  title: '',
                  url: '',
                  items: []
              },
              stopAnimation: false,
              hasAnimation: true,
              show: false,
              secShow: false,
              newShow: false,
              giftBox: true,
              loginS: false,
              points: '00',
              miniPoints: '',
              centerPoints: '',
              signObj: {
                  isSign: '',
                  signTimes: 0,
                  gradientPoints: 50,
                  isContinue: '',
                  originalPoints: 150
              },
              signT: '',
              stopShake: false,
              topStopShake: false,
              isCash: false,
              isFirstGet: false,
              pointSuccessShow: false,
              user: false,
              hasWifi: true,
              scrollerPoint: [
                  {
                      p: 150,
                      s: false
                  }, {
                       p: 200,
                       s: false
                  }, {
                       p: 250,
                       s: false
                  }, {
                       p: 300,
                       s: false
                  }, {
                       p: 350,
                       s: false
                  }, {
                       p: 400,
                       s: false
                  }, {
                       p: 450,
                       s: false
                  }, {
                      p: 450,
                      s: false
                  }, {
                      p: 450,
                      s: false
                  }, {
                      p: 450,
                      s: false
                  }, {
                      p: 450,
                      s: false
                  }, {
                      p: 450,
                      s: false
                  }, {
                      p: 450,
                      s: false
                  }, {
                      p: 450,
                      s: false
                  }, {
                      p: 450,
                      s: false
                  }
              ],
              loadingAR: [
                  { ref: 'pG1', p: [-140, -100], delay: 0 },
                  { ref: 'pG2', p: [-110, -70], delay: 0 },
                  { ref: 'pG3', p: [-130, -10], delay: 0 },
                  { ref: 'pG4', p: [-130, 20], delay: 0 },

                  { ref: 'pG5', p: [160, -120], delay: 0 },
                  { ref: 'pG6', p: [90, -70], delay: 0 },
                  { ref: 'pG7', p: [100, -20], delay: 0 },
                  { ref: 'pG8', p: [120, 10], delay: 0 },
                  { ref: 'pG9', p: [120, 10], delay: 0 }
              ]
          }
        },
        created () {
            googleAnalytics.trackingScreen('Rewards');
            this.initPage()
            this.$event.on('logout', parmas => {
                this.initPage()
                this.signObj.isSign = false
                this.signObj.signTimes = 0
                this.stopShake = false
            })
            this.$event.on('login', parmas => {
                this.initPage()
            })
        },
        methods: {
            initPage () {
                this.user = this.$storage.getSync('user')
                this.getCard()
                this.getBtnStatus()
                this.getSignTime()
                this.setTime()
                this.shakeBtn()
                this.shakeArrow()
                this.initBack()
                googleAnalytics.trackingScreen('Rewards');
                // this.$notice.alert({
                //     message: this.user
                // })
            },
            initBack () {
                // this.$notice.toast({ message: '1111' })
                common.setAndroidCanBack(true, (params) => {
                    this.wxcMaskSetShareHidden();
                })
            },
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
                    this.hasWifi = true
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    if (res.status == 10) {
                        this.hasWifi = false;
                    }
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            getBtnStatus () {
                this.$fetch({
                    method: 'GET',
                    name: 'point.whether.cashing.prize',
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.isCash = res.isCash
                    this.isFirstGet = res.isFirst
                    this.miniPoints = res.points
                    this.points = res.amount
                    if (this.isCash) {
                        this.topStopShake = true
                    } else {
                        this.topStopShake = false
                    }
                    // this.$notice.alert({
                    //     message: res
                    // })
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            getSignTime () {
                this.$fetch({
                    method: 'GET',
                    name: 'point.sign.times',
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    // if (!res.isContinue) {
                    //     this.signObj.isSign
                    // }
                    this.signObj = res
                    if (this.signObj.isSign) {
                        this.stopShake = true
                    }

                    if (!this.signObj.isContinue) {
                        this.signObj.signTimes = 0
                        for (let i = 0; i < this.scrollerPoint.length ; i++) {
                            this.scrollerPoint[i].s = false
                        }
                    } else {
                        for (let i = 0; i < this.scrollerPoint.length ; i++) {
                            if (i < this.signObj.signTimes) {
                                this.scrollerPoint[i].s = true
                            }
                        }
                    }
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            openDetail () {
                this.$router.open({
                    name: 'card.detail',
                    type: 'PUSH'
                })
            },
            openRulerPage (p) {
                this.$router.open({
                    name: 'points.ruler',
                    type: 'PUSH',
                    params: {
                        originalPoints: this.signObj.originalPoints,
                        gradientPoints: this.signObj.gradientPoints,
                        type: p
                    }
                })
            },
            openPointSuccess () {
                this.pointSuccessShow = true
                common.changeAndroidCanBack(false);
            },
            // openMyPoints () {
            //     this.$router.open({
            //         name: 'my.points',
            //         type: 'PUSH',
            //     })
            // },
            openNewPage () {
                this.$router.open({
                    name: 'raffle',
                    type: 'PUSH'
                })
            },
            openIndex () {
                // this.$event.emit('changeTab', {
                //     tab: 'home'
                // });
                this.wxcMaskSetShareHidden()
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
            getPoints () {
                if (this.user.pointsAvailable < 400) {
                    this.secShow = true
                    return
                } else {
                    googleAnalytics.recordEvent('Rewards', 'DailyGiftBox', 'EarnCashBonus', 0);
                    if (!this.isCash && this.user) {
                        this.requestP()
                    } else if (!this.user) {
                        this.redirectLogin()
                    }
                }
            },
            requestP () {
                this.$fetch({
                    method: 'GET',
                    name: 'point.cashing.prize',
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.points = res.amount
                    this.isCash = true
                    // setTimeout(() => {
                    //     this.shake2()
                    // }, 500)
                    this.newShow = true
                    this.$event.emit('getMyWallet')
                    googleAnalytics.recordEvent('Rewards', 'DailyGiftBox', 'EarnCashBonusSuccess', 0);
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },

            getSign () {
                this.$notice.loading.show();
                if (this.user) {
                    // googleAnalytics.recordEvent('get Sign', '', '', 0);
                    googleAnalytics.recordEvent('Rewards', 'CheckIn', 'CheckIn', 0);

                    if (!this.signObj.isSign) {
                        this.$fetch({
                            method: 'GET',
                            name: 'point.punch.clock',
                            header: {
                                needAuth: true
                            }
                        }).then((res) => {
                            this.signObj.signTimes = res.signTimes
                            for (let i = 0; i < this.scrollerPoint.length; i++) {
                                if (i < this.signObj.signTimes) {
                                    this.scrollerPoint[i].s = true
                                }
                            }
                            this.signObj.isSign = true
                            this.stopShake = true
                            this.centerPoints = res.points
                            this.user.points = res.totalPoints;
                            this.user.pointsAvailable = res.availablePoints;
                            this.$storage.setSync('user', this.user)
                            this.$notice.loading.hide();

                            this.openPointSuccess()
                            // this.$notice.toast({
                            //     message: 'Congrats! You’ve got ' + + ' points today!'
                            // })
                            // googleAnalytics.recordEvent('sign Success', '', '', 0);
                            this.$event.emit('mySign')
                            googleAnalytics.recordEvent('Rewards', 'CheckIn', 'CheckInSuccess', 0);
                        }).catch((res) => {
                            this.$notice.loading.hide();
                            // this.$notice.toast({
                            //     message: res
                            // })
                        })
                    } else {
                        this.$notice.loading.hide();
                        this.$notice.toast({
                            message: 'These points will be available to claim tomorrow!'
                        })
                    }
                } else {
                    this.$notice.loading.hide();
                    this.redirectLogin()
                }
            },
            loadingAni () {
                for (let i = 0; i < this.loadingAR.length; i++) {
                    this.shake(this.$refs[this.loadingAR[i].ref], this.loadingAR[i].p[0], this.loadingAR[i].p[1]);
                }
            },
            // loadingAniDown () {
            //     for (let i = 0; i < this.loadingAR.length; i++) {
            //         this.shake(this.$refs[this.loadingAR[i].ref], 0, 0, 0, 0, 500, 0, 0, i == this.loadingAR.length - 1);
            //     }
            // },
            setTime () {
                this.loadingAni()
                setInterval(() => {
                    this.loadingAni()
                    this.shakeBtn()
                    this.shakeArrow()
                }, 2000)
            },
            shake (_ref, _x, _y) {
                animation.transition(_ref, {
                    styles: {
                        transform: 'translate(' + _x + 'px, ' + _y + 'px)'
                    },
                    duration: 800, // ms
                    timingFunction: 'ease',
                    delay: 0 // ms
                }, function () {
                    if (!this.topStopShake) {
                        animation.transition(_ref, {
                            styles: {
                                transform: 'translate(0px, 0px)'
                            },
                            duration: 1000, // ms
                            timingFunction: 'ease',
                            delay: 0 // ms
                        })
                    }
                }.bind(this))
            },
            shakeBtn () {
                if (!this.stopShake) {
                    animation.transition(this.$refs.getBtn1, {
                        styles: {
                            transform: 'translate(0px, -15px)'
                        },
                        duration: 800, // ms
                        timingFunction: 'ease',
                        delay: 0 // ms
                    }, function () {
                        animation.transition(this.$refs.getBtn1, {
                            styles: {
                                transform: 'translate(0px, 0px)'
                            },
                            duration: 1000, // ms
                            timingFunction: 'ease',
                            delay: 0 // ms
                        })
                    }.bind(this))
                }
            },
            shakeArrow () {
                if (!this.topStopShake) {
                    animation.transition(this.$refs.getArrow, {
                        styles: {
                            transform: 'translate(15px, 0px)'
                        },
                        duration: 800, // ms
                        timingFunction: 'ease',
                        delay: 0 // ms
                    }, function () {
                        animation.transition(this.$refs.getArrow, {
                            styles: {
                                transform: 'translate(0px, 0px)'
                            },
                            duration: 1000, // ms
                            timingFunction: 'ease',
                            delay: 0 // ms
                        })
                    }.bind(this))
                }
            },
            shake2 () {
                // this.$notice.alert({ message: '1111' })
                animation.transition(this.$refs.giftH, { // 盖子上升 旋转
                    styles: {
                        // transform: 'translate(' + _x + 'px, ' + _y + 'px)'
                        transform: 'translate(10px,-90px) rotate(0.6)'
                    },
                    duration: 800, // ms
                    timingFunction: 'ease',
                    delay: 0 // ms
                }, function () {
                    animation.transition(this.$refs.giftH, { // 盖子下滑和旋转
                        styles: {
                            transform: 'translate(160px, 65px) rotate(1.05)'
                        },
                        duration: 1000, // ms
                        timingFunction: 'ease',
                        delay: 0 // ms
                    }, function () {
                        animation.transition(this.$refs.giftW, { // 红包的位移和放大
                            styles: {
                                transform: 'translate(-10px, -140px) scale(1.1)'
                            },
                            duration: 1000, // ms
                            timingFunction: 'ease',
                            delay: 0 // ms
                        }, function () {
                            animation.transition(this.$refs.giftH, { // 盖子和盒子的隐藏
                                styles: {
                                    opacity: '0'
                                    // transform: 'translate(160px, 65px)'
                                },
                                duration: 1000, // ms
                                timingFunction: 'ease',
                                delay: 0 // ms
                            })
                            animation.transition(this.$refs.giftC, { // 盖子和盒子的隐藏
                                styles: {
                                    opacity: '0'
                                    // transform: 'translate(160px, 65px)'
                                },
                                duration: 1000, // ms
                                timingFunction: 'ease',
                                delay: 0 // ms
                            })
                        }.bind(this))
                    }.bind(this))
                }.bind(this))
            },
            redirectLogin () {
                this.$event.on('login', params => {
                    this.user = this.$storage.getSync('user')
                    // this.$storage.get('user').then(resData => {
                    //     this.user = resData
                    if (this.user) {
                        this.loginS = true
                        this.getBtnStatus()
                    }

                    // })
                });
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            },
            openBottomPopup () {
                this.isBottomShow = true;
            },
            popupOverlayBottomClick () {
                this.isBottomShow = false;
            },
            scroller () {
                // this.secShow = false
                // const el = this.$refs.dec
                // dom.scrollToElement(el, { offset: -140 })
                // this.$nextTick(() => {
                //     dom.scrollToElement(this.$refs['tab'], { animated: false })
                // })
            },
            wxcMaskSetShareHidden () {
                this.show = false;
                this.pointSuccessShow = false;
                this.secShow = false;
                this.newShow = false;
                common.changeAndroidCanBack(true)
            },
            tranString (s) {
                if (s.length > 8) {
                    return s.substring(0, 8) + '...'
                } else {
                    return s
                }
            }
        }
    }
</script>
<style lang="sass" src="./iconPosition.scss"></style>

<style scoped>
    .wrapper {
        background-color: #f4f4f4;
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
    .header{
        background-color: white;
        height: 112px;
        margin-top: 48px;
        margin-bottom: 24px;
        box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .header-word{
        font-size: 32px;
        font-weight: 700;
        /*width: 474px;*/
        color: black;
        text-align: center;
        line-height: 64px;
    }
    .overflow-mid1{
        flex-direction: row;
        justify-content: center;
        height: 312px;
    }
    .overflow-mid2{
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .o-m-head{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        width: 686px;
        margin-bottom: 24px;
    }
    .o-mt1{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
    }

    .overflow-gift-card{
        width: 686px;
        margin:  24px 32px 140px 32px;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background-color: white;
        border-radius: 16px;
    }
    .g-ch-w{
        margin-bottom: 50px;
        margin-top: 48px;
        font-size: 40px;
        font-weight: 700;
    }
    .top-right-div{
        position: absolute;
        top:-11px;
        right:0;
    }
    .top-right {
        width: 204px;
        height: 100px;
    }
    .gift-card{
        box-shadow: 0 1px 6px 0 rgba(0,0,0,0.12);
        border-radius: 24px;
        background-color: white;
        width: 594px;
    }
    .gift-card-img{
        width: 592px;
        height: 174px;
    }
    .gift-card-txt{
        width: 594px;
        flex-direction: row;
        justify-content: space-between;
        background-color: white;
        padding-top:26px ;
        padding-bottom:26px ;
        padding-left:24px ;
        padding-right:24px ;
    }
    .gift-card-txt1{
        font-weight: 700;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-txtr{
        flex-direction: row;
        justify-content: space-between;
    }
    .gift-card-txt2{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .gift-card-icon{
        color: #333333;
    }
    .mg-b3{
        margin-bottom: 30px;
    }
    .mg-b5{
        margin-bottom: 50px;
    }
    .mg-r32{
        margin-right: 32px;
    }
    .top-card{
        width: 686px;
        height: 264px;
        background-color: #31005f;
        border-radius: 180px;
    }
    .m2-content{
        background-color:#F1A800;
        width: 686px;
        border-radius: 32px;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .m2-r{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        padding: 10px 0;
    }
    .m2-rw{
        font-size: 24px;
        color: #1D7CD4;
    }
    .m2-c1{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        margin-top: 30px;
        margin-bottom: 18px;
    }
    .m2-c1w{
        margin-left: 16px;
        margin-right: 16px;
        font-size: 28px;
        font-weight: 600;
        color: white;
    }
    .m2-c1p{
        font-size: 48px;
        font-weight: 700;
        color: white;
    }
    .m2-c2{
        background-color: #327D03;
        height: 78px;
        width: 424px;
        border-radius: 50%;
    }
    .m2-c2-shadow{
        background-color: #389000;
        height: 70px;
        width: 424px;
        flex-direction: row;
        justify-content: center;
        align-items:center;
    }
    .m2-c2w{
        color: white;
        font-size: 28px;
        font-weight: 700;
        margin-left: 14px;
    }
    .m2-c2a{
        color: white;
        font-size: 28px;
        font-weight: 700;
    }
    .m2-c2r{
        /*background-color: #327D03;*/
        height: 78px;
        width: 424px;
        /*border-radius: 50%;*/
    }
    .m2-c2-shadowr{
        /*background-color: #389000;*/
        height: 70px;
        width: 424px;
        flex-direction: row;
        justify-content: center;
        align-items:center;
    }
    .m2-c2wr{
        color: white;
        font-size: 28px;
        font-weight: 700;
        margin-left: 14px;
    }
    .m2-c2ar{
        color: white;
        font-size: 28px;
        font-weight: 700;
    }
    .m2-c3{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        margin-top: 12px;
        margin-bottom: 46px;
    }
    .m2-c3-gift{
        font-size: 24px;
        color: #FBE945 ;
        margin-right:10px;
    }
    .m2-c3w{
        font-size: 24px;
        font-weight: 700;
        color: #FFFFFF;
    }
    .s-box{
        padding-top: 8px;
        padding-left: 32px;
        margin-bottom: 32px;
        width: 592px;
        height: 122px;
        margin-top: -32px;
        border-radius: 16px;
        flex-direction: row;
        background-color:#FFDD90;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .s-box-c{
        flex-direction: row;
        justify-content: center;
        align-items: center
    }
    .single{
        flex-direction: column;
        justify-content: start;
        align-items: start;
    }
    .single-dot{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .single-line{
        width:34px;
        height: 2px;
        background-color: #eeb07a
    }
    .single-line-last{
        width:34px;
        /*height: 2px;*/
        background-color: #eeb07a
    }
    .single-word{
        position: absolute;
        top:10px;
        left:4px;
        font-size: 20px;
        color: #717171;
    }
    .single-day{
        font-size: 20px;
        color: #34260B;
        margin-top: 4px;
        margin-left: 6px;
    }
    .red-block{
        width: 686px;
        height: 160px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
    }
    .rb-bg-img{
        position: absolute;
        top: 0;
        left: 0;
        width: 686px;
        height: 160px;
    }
    .red-right{
        margin-left: 130px;
        flex-direction: column;
        align-items:start;
        justify-content: center;
        height: 160px;
    }
    .red-right1{
        /*margin-left: 130px;*/
        flex-direction: row;
        align-items:center;
        justify-content: start;
        height: 160px;
    }
    .rr-top{
        flex-direction: row;
        align-items: center;
        justify-content: start;
    }
    .rrt-t1{
        font-size: 28px;
        color: #FFFFFF;
        font-weight: 700;
    }
    .rrt-t2{
        margin-top: 6px;
        font-size: 20px;
        color: #FFFFFF;
    }
    .rrt-t3{
        margin-top: 6px;
        font-size: 24px;
        color: #FFFFFF;
    }
    .rrt-t4{
        margin-top: 6px;
        font-size: 24px;
        font-weight: 900;
        color: #FFFFFF;
    }
    .rrt-btn{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        margin-left: 16px;
        background-color: #FFFFFF;
        padding: 10px 18px 10px 26px;
        border-radius: 50%;
    }
    .rrt-btn-t{
        font-size: 24px;
        color: #EC8830;
        font-weight: 700;
    }
    .rrt1-icon{
        color: white;
        font-size: 38px;
    }


    .card-row{
        width: 750px;
        height: 366px;
        flex-direction: row;
        /*background-color:#1D7CD4;*/
    }
    .cr-content{
        flex-direction: row;
        justify-content: center;
        align-items: center
    }
    .cr-card{
        flex-direction: column;
        justify-content: start;
        align-items: start;
        margin-left: 32px;
        background-color: white;
        border-radius: 16px;
        /*height: 174px;*/
        height: 348px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .cr-bottom{
        margin-left: 16px;
    }
    .crc-t1{
        font-size: 20px;
        color: #000000;
        margin-top: 15px;
    }
    .crc-t2{
        font-size: 20px;
        color: rgba(0,0,0,.54);
        margin-top: 4px;
    }
    .crc-btn{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        padding:6px;
        border-radius: 50%;
        border-style:solid ;
        border-width: 2px;
        border-color: #EC8830 ;
        margin: 16px 0;
    }
    .crc-btn-t{
        font-size: 24px;
        color: #EC8830;
    }
    .mpw-t{
        line-height: 38px;
    }
    .mpw-t1{
        font-size: 28px;
        color: #000000;
    }
    .mpw-t2{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
    }




    .m-ic{
         position: absolute;
         top: 24px;
         right: 24px;
         font-size: 50px;
         color: white;
         font-weight: 700;
     }
    .m-c{
        position: absolute;
        top: 65px;
        left: 145px;
        font-size: 64px;
        color: #FFFFFF;
        font-weight: 700;
    }
    .mid-content{
        background-color: white;
        padding: 32px 34px;
    }
    .m-c-w{
        font-size: 24px;
        color: #000000;
        text-align: center;
        line-height: 34px;
    }
    .m-c-w1{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        text-align: center;
        line-height: 34px;
    }
    .m-c-time{
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .m-c-word{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
    }
    .m-top{
        border-top-right-radius: 32px;
        border-top-left-radius: 32px
    }
    .m-bottom{
        background-color: white;
        padding-bottom:32px ;
        padding-top: 20px;
        border-bottom-right-radius: 32px;
        border-bottom-left-radius: 32px
    }
    .overflow-mg{
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .mb-word{
        position: absolute;
        top: 32px;
        left: 100px;
        font-size: 24px;
        color: #FFFFFF;
        font-weight: 700;
    }

    .overflow-center-time{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin: 0px 0 8px 0 ;
    }
    .center-time-word{
        color: black;
        font-size: 24px;
    }
    .center-time{
        color: #FFFFFF;
        flex-direction: row;
        justify-content: start;
        margin-right: 32px;
    }
    .center-time-hh{
        background-color: black;
        color: white;
        border-radius: 6px;
        padding: 2px;
        margin: 8px 2px 2px 2px;
        font-size: 24px;
    }
    .center-time-hh-empty{
        background-color: black;
        color: white;
        border-radius: 6px;
        margin: 8px 2px 2px 2px;
        width: 32px;
        height: 32px;
    }
    .center-time-space{
        width: 750px;
        flex-direction: column;
        justify-content: center;
        text-align: center;
        color: black;
        font-size: 24px;
        margin-top: 4px;
        margin-bottom: 4px;
    }
</style>
