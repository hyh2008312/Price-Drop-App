<template>
    <div class="wrapper">
        <div class="blackheader"></div>

        <scroller >
            <!--<cell><text>111</text></cell>-->
            <div class="header">
                <text class="header-word" >Earn Points & Cash Bonus Every Day!</text>
            </div>
            <div class="overflow-mid1" >
                <div class="top-card">
                    <text class="t-c-h">Daily Cash Reward</text>
                </div>
                <div class="t-cm">
                    <div class="t-cm-c" v-if="!isCash"  @click="getPoints">
                        <text class="t-cm-cw" >Earn up to ₹ 200 cash bonus</text>
                        <text class="t-cm-ca iconfont" ref="getArrow">&#xe626;</text>
                    </div>
                    <div class="t-cm-cr" v-if="isCash" >
                        <text class="t-cm-cwr">Earned ₹ {{points}} Bonus!</text>
                        <text class="t-cm-car iconfont" >&#xe6ed;</text>
                    </div>
                </div>
                <div class="t-cb" @click="openRulerPage(1)">
                    <text class="t-cbw" >Rules </text>
                    <text class="iconfont" style="color:white;font-size: 18px ">&#xe626;</text>
                </div>
                <!--<div class="w-img">-->
                    <image class="w-i5"  style=" width: 100px;height: 43px;" src="bmlocal://assets/perks/5.png"></image>
                    <image class="w-i3"  style=" width: 62px;height: 98px;" src="bmlocal://assets/perks/3.png"></image>
                    <image class="w-i1"  style=" width: 60px;height: 110px;" src="bmlocal://assets/perks/1.png"></image>

                    <image class="w-i6"  style=" width: 98px;height: 106px;" src="bmlocal://assets/perks/6.png"></image>

                    <image class="w-i2"  style=" width: 100px;height: 102px;" src="bmlocal://assets/perks/2.png"></image>
                    <image class="w-i4"  style=" width: 80px;height: 96px;" src="bmlocal://assets/perks/4.png"></image>
                <!--</div>-->
            </div>

            <div class="overflow-mid2" >
                <div class="mid-head">
                    <text class="m-h1">Check In & Earn Points</text>
                </div>
                <div class="m2-content">
                    <div class="m2-r" @click="openRulerPage(2)">
                        <text class="m2-rw" >Rules </text>
                        <text class="iconfont" style="color:white;font-size: 18px ">&#xe626;</text>
                    </div>
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

            <!--<div class="overflow-mid" style="margin-bottom: 100px">-->


                <!--<div class="mid-card-noShadow">-->
                   <!--<div>-->
                       <!--<image  src="bmlocal://assets/perks/bg-1.jpg" style="width: 686px;height:520px"></image>-->
                       <!--<image class="center-img"  src="bmlocal://assets/perks/bg-3.png" style="width: 500px;height:294px"></image>-->
                   <!--</div>-->
                    <!--<div class="center-word">-->
                        <!--<text :class="[signObj.isSign?'c-w-hs':'c-w-h',]">Check In & Earn Points</text>-->
                        <!--<div class="c-wb" v-if="signObj.isSign" >-->
                            <!--<text class="c-wb-wn">Got </text>-->
                            <!--<text class="c-wb-wn">{{(signObj.originalPoints + (((signObj.signTimes - 1) % 15) <= 6 ? ((signObj.signTimes - 1) % 15) : 6)*signObj.gradientPoints) }}</text>-->
                            <!--<text class="c-wb-wn"> Points! </text>-->
                            <!--&lt;!&ndash;<text class="c-wb-wn iconfont">&#xe626;</text>&ndash;&gt;-->
                        <!--</div>-->
                        <!--<text class="c-w-r" @click="openRulerPage(2)">Rules >></text>-->
                    <!--</div>-->
                    <!--<div class="over-flow-cwb" ref="getBtn">-->
                        <!--<div class="c-w-b"  @click="getSign">-->
                            <!--<text class="c-w-bw" v-if="signObj.isSign">Get {{signObj.originalPoints+(((signObj.signTimes % 15) <= 6 ? (signObj.signTimes % 15) : 6)* signObj.gradientPoints)}} Points Tomorrow</text>-->
                            <!--<text class="c-w-bw" v-if="!signObj.isSign">Claim {{(signObj.originalPoints+(((signObj.signTimes % 15) <= 6 ? (signObj.signTimes % 15) : 6)*signObj.gradientPoints))||0}} Daily Points</text>-->
                            <!--<text class="c-w-bwa iconfont" v-if="!signObj.isSign" >&#xe626;</text>-->
                        <!--</div>-->
                    <!--</div>-->

                <!--</div>-->
            <!--</div>-->

            <div class="overflow-mid">

                <div class="mid-card">
                    <div class="top-right-div">
                        <image class="top-right" src="bmlocal://assets/anger.png"></image>
                    </div>

                    <div  class="mid-card-h">
                        <text class="g-ch-w"  ref="dec" >More Ways to Earn Points</text>
                    </div>
                    <div class="mid-card-item1">
                        <div><image class="img-icon" src="bmlocal://assets/pic-coupon.png"></image></div>

                        <div class="mid-card-text">
                            <text class="mid-card-text1">Make a Successful Purchase</text>
                            <div class="count-div">
                                <text class="mid-card-text2">Earn</text><text class="mid-card-text2b"> 200 points</text><text class="mid-card-text2"> for every</text><text class="mid-card-text2b"> ₹50</text><text class="mid-card-text2"> you spend</text>
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
                                <text class="mid-card-text2">Earn</text><text class="mid-card-text2b"> 100 points</text><text class="mid-card-text2"> for every</text><text class="mid-card-text2b"> ₹50</text><text class="mid-card-text2"> your friends spend</text>
                            </div>

                        </div>
                    </div>

                    <div class="bottom-btn" @click="openDetail">
                        <text class="bottom-btn-txt">Learn More</text>
                    </div>

                </div>
            </div>

            <div class="overflow-gift-card" >
                <div class="top-right-div">
                    <image class="top-right" src="bmlocal://assets/anger.png"></image>
                </div>
                <div>
                    <text class="g-ch-w"  style="">Redeem Gift Voucher</text>
                </div>
                <div >
                    <div v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'mg-b5':'mg-b3',]">

                        <div class="gift-card" @click="redeemCard(i)">
                            <image class="gift-card-img"  :src="i.newImageDetail"></image>
                            <div class="gift-card-txt">
                                <text class="gift-card-txt1">{{i.name}} Gift Voucher</text>
                                <div class="gift-card-txtr">

                                    <text class="gift-card-txt2">{{i.pointNumber}} Points Needed</text>
                                    <text class="gift-card-icon iconfont">&#xe626;</text>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </scroller>


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
            height="535"
            width="592"
            border-radius="16"
            duration="200"
            mask-bg-color="#FFFFFF"
            :has-animation="hasAnimation"
            :has-overlay="true"
            :show-close="false"
            :show="ruleShow"
            @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="mask-ruler-content">
                <div>
                    <text style="text-align: center;margin-bottom: 10px;font-size: 36px">Rules to Know</text>
                </div>
                <div>
                   <text class="r-cw">1. The daily cash bonus will expire after 12:00 PM every day. Please spend your bonus before it expires!</text>
                   <text class="r-cw">2. You can apply your cash bonus to your payment at the checkout page.</text>
                   <text class="r-cw">3. You will be asked to spend 300 points to unlock your gift box after the first time.</text>
                   <text class="r-cw">4. The cash bonus can only be used for shopping, which cannot be withdrawn.</text>
                </div>
                <div>

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
                    <text class="mask-pw1" >You need {{miniPoints}} points to open a gift box for bonus!</text>
                    <div class="mask-pw2">
                        <text  class="mask-pw21">Your current available points:</text><text class="mask-pw22">{{user.pointsAvailable}}</text>
                    </div>

                </div>
                <div class="mask-p-btn">
                    <text class="mask-p-btnw" v-if="user.pointsAvailable<miniPoints" @click="scroller">Earn More Points</text>
                    <text class="mask-p-btnw" v-if="user.pointsAvailable>=miniPoints" @click="requestP()">Get Cash Bonus Now</text>
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

    export default {
        name: 'index',
        components: {
            WxcMask
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
              hasAnimation: false,
              show: false,
              secShow: false,
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
              ruleShow: false,
              user: false,
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
                googleAnalytics.trackingScreen('Rewards');
                // this.$notice.alert({
                //     message: this.user
                // })
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
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
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
            openRuler () {
                this.ruleShow = true
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
                googleAnalytics.recordEvent('Rewards', 'DailyGiftBox', 'EarnCashBonus', 0);
                if (!this.isCash && this.user && this.isFirstGet) {
                    this.show = true
                    this.requestP()
                } else if (!this.user) {
                    this.redirectLogin()
                } else if (!this.isFirstGet) {
                    this.secShow = true
                }
            },
            requestP () {
                this.secShow = false
                if (!this.show) {
                    this.show = true
                }
                if (!this.secShow) {
                    this.$fetch({
                        method: 'GET',
                        name: 'point.cashing.prize',
                        header: {
                            needAuth: true
                        }
                    }).then((res) => {
                        this.points = res.amount
                        this.isCash = true
                        setTimeout(() => {
                            this.shake2()
                        }, 500)
                        this.topStopShake = true
                        this.$event.emit('getMyWallet')
                        // this.$notice.alert({
                        //     message: res.amount
                        // })
                        googleAnalytics.recordEvent('Rewards', 'DailyGiftBox', 'EarnCashBonusSuccess', 0);
                    }).catch((res) => {
                        // this.$notice.toast({
                        //     message: res
                        // })
                    })
                }
            },

            getSign () {
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
                            // this.$storage.setSync(user.pointsAvailable, 'weex-eros')
                            this.$notice.toast({
                                message: 'Congrats! You’ve got ' + (this.signObj.originalPoints + (((this.signObj.signTimes - 1) % 15) <= 6 ? ((this.signObj.signTimes - 1) % 15) : 6) * this.signObj.gradientPoints) + ' points today!'
                            })
                            // googleAnalytics.recordEvent('sign Success', '', '', 0);
                            this.$event.emit('mySign')
                            googleAnalytics.recordEvent('Rewards', 'CheckIn', 'CheckInSuccess', 0);
                        }).catch((res) => {
                            // this.$notice.toast({
                            //     message: res
                            // })
                        })
                    } else {
                        this.$notice.toast({
                            message: 'These points will be available to claim tomorrow!'
                        })
                    }
                } else {
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

                // this.$notice.alert({
                //     message: this.$refs.pG1
                // })
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
                this.secShow = false
                const el = this.$refs.dec
                dom.scrollToElement(el, { offset: -140 })
                // this.$nextTick(() => {
                //     dom.scrollToElement(this.$refs['tab'], { animated: false })
                // })
            },
            wxcMaskSetShareHidden () {
                this.show = false;
                this.ruleShow = false;
                this.secShow = false;
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
        border-radius: 50%;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .header-word{
        font-size: 40px;
        font-weight: 700;
        width: 474px;
        color: white;
        text-align: center;
        line-height: 64px;
    }
    .overflow-mid{
        flex-direction: row;
        justify-content: center;
    }
    .overflow-mid1{
        flex-direction: row;
        justify-content: center;
        height: 340px;
        margin-top: -70px;
        /*background-color: black*/
    }
    .overflow-mid2{
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .overflow-gift-bottom{
        margin-bottom: 150px;
    }
    .mid-card{
        width: 686px;
        /*height: 520px;*/
        background-color: white;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
        flex-direction: column;
        align-items: stretch;
    }
    .mid-card-noShadow{
        width: 686px;
        background-color: white;
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
        margin-bottom: 38px;
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
    .overflow-gift-card{
        width: 686px;
        margin:  30px 32px 140px 32px;
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
    .mid-card-h{
        flex-direction: row;
        justify-content: center;
        margin-bottom: -70px;
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
    .count-div{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }
    .mg-b3{
        margin-bottom: 30px;
    }
    .mg-b5{
        margin-bottom: 50px;
    }
    .top-card{
        width: 686px;
        height: 264px;
        background-color: #31005f;
        border-radius: 180px;
    }
    .w-i5{
        position: absolute;
        top: 158px;
        left: 113px;
    }
    .w-i3{
        position: absolute;
        top: 102px;
        left: 210px;
    }
    .w-i1{
        position: absolute;
        top: 91px;
        left: 274px;
    }
    .w-i6{
        position: absolute;
        top: 104px;
        left: 336px;
    }
    .w-i2{
        position: absolute;
        top: 98px;
        left: 435px;
    }
    .w-i4{
        position: absolute;
        top: 104px;
        right: 130px;
    }
    .t-c-h{
        position: absolute;
        top: 32px;
        left: 0;
        font-size: 36px;
        color: white;
        font-weight: 700;
        text-align: center;
    }
    .t-cb{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        position: absolute;
        top: 0;
        right: 70px;
        width: 104px;
        height: 42px;
        border-top-right-radius: 100%;
        border-top-left-radius: 50%;
        border-bottom-left-radius: 50%;
        background-color: #D71D2A ;
    }
    .t-cbw{
        font-size: 20px;
        color: white;
    }
    .t-cm{
        position: absolute;
        top: 200px;
        left: 32px;
        width: 686px;

    }
    .t-cm-c{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        background-color: #6900CB;
        height: 108px;
        border-radius:50%;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.50);
    }
    .t-cm-cw{
        font-size: 28px;
        color: white;
        font-weight: 700;
        margin-left: 10px;
    }
    .t-cm-ca{
        font-size: 36px;
        color: white;
        margin-left: 10px;
        font-weight: 700;
    }
    .t-cm-cr{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        background-color: #6900CB;
        height: 108px;
        /*width: 460px;*/
        border-radius:50%;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.50);
    }
    .t-cm-cwr{
        margin-left: 10px;
        font-size: 24px;
        color: white;
        font-weight: 700;
    }
    .t-cm-car{
        font-size: 36px;
        color: white;
        margin-left: 10px;
        /*font-weight: 700;*/
    }
    .mid-head{
        height: 96px;
        width: 502px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        border-top-right-radius: 100%;
        border-top-left-radius: 100%;
        background-color: #FFBD24;
    }
    .m-h1{
        font-size: 36px;
        color: #FFFFFF;
        font-weight: 700;
        margin-top: 16px;

    }
    .m2-content{
        background-color:#F1A800;
        width: 686px;
        border-radius: 32px;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    .m2-r{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        position: absolute;
        top: 0;
        right: 0;
        width: 100px;
        height: 48px;
        background-color: #389000;
        border-top-left-radius:100%;
        border-bottom-left-radius:100%;
    }
    .m2-rw{
        font-size: 20px;
        color: #FFFFFF;
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
    /*.m2-c4{*/
        /*width: 592px;*/
        /*height: 122px;*/
        /*flex-direction: row;*/
        /*justify-content: center;*/
        /*align-items: center;*/
    /*}*/
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
    .bg-tangle{
        position: absolute;
        top: 0;
        left: 0;
        width: 686px;
        height:328px;
    }
    .center-img{
        position: absolute;
        top: 40px;
        left: 90px;
    }
    .center-word{
        position: absolute;
        top: 135px;
        left: 150px;
        /*background-color: salmon;*/
        flex-direction: column;
        justify-content: center;
        align-items: start;
    }
    .over-flow-cwb{
        position: absolute;
        top: 340px;
        left: 128px;
        width: 435px;
        padding:0 0px 10px 0px;
        background-color: #DBAE12;
        border-radius: 50%;
    }
    .c-w-b{
        background-color: #F6C312;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        border-radius: 50%;
    }
    .c-w-h{
        width: 200px;
        font-size: 36px;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        font-weight: bold;
        margin-left: 90px;
    }
    .c-w-hs{
        /*width: 200px;*/
        margin-left: 30px;
        font-size: 32px;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        font-weight: bold;
    }
    .c-w-r{
        font-size: 24px;
        color: #FFFFFF;
        letter-spacing: 0;
        margin-top: 25px;
        margin-left: 145px;
        text-align: center;
        text-decoration: underline;
        /*background-color: black;*/
        padding: 15px;
    }
    .c-wb{
        /*width: 160px;*/
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        margin-left: 100px;
        margin-top: 18px;
        /*background-color: black;*/
    }
    .c-wb-w{
        font-size: 24px;
        color: #FFFFFF;
        letter-spacing: 0;
        padding: 0 4px;
    }
    .c-wb-wn{
        font-size: 24px;
        color: #FFFFFF;
        letter-spacing: 0;
        font-weight: 700;
    }
    .c-w-bw{
        padding: 18px 9px 18px 18px;
        color: white;
        font-size: 24px;
        font-weight: 700;
    }
    .c-w-bwa{
        color: white;
        font-size: 24px;
        font-weight: 700;
    }
</style>
