<template>
    <div class="wrapper">
        <scroller >

            <div class="blackheader"></div>
            <div class="header">
                <text class="header-word" >Earn Points & Cash Bonus Every Day!</text>
            </div>
            <!--<div class="overflow-mid"  style="margin-bottom: 100px">-->
                <!--<div class="top-card">-->
                    <!--<div>-->
                        <!--<text>Daily Gift Box</text>-->
                    <!--</div>-->
                    <!--<image src="bmlocal://assets/bg.png" class="bg-tangle" @click="shake" ></image>-->
                    <!--<div  style="height: 328px;">-->
                        <!--<image ref="pG1" src="bmlocal://assets/perks/hat.png" class="gif-icon" ></image>-->

                        <!--<image ref="pG2" src="bmlocal://assets/perks/dress.png" class="gif-icon" ></image>-->

                        <!--<image ref="pG3" src="bmlocal://assets/perks/package.png" class="gif-icon" ></image>-->

                    <!--</div>-->
                    <!--<div class="t-cb">-->
                        <!--<div class="t-cb-c">-->
                            <!--<text>Earn up to 200 cash bonus</text>-->
                            <!--<text class="iconfont">&#xe626;</text>-->
                        <!--</div>-->
                        <!--<image class="t-cb-i" src="bmlocal://assets/perks/cup.png" ></image>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->

            <div class="overflow-mid"  style="margin-bottom: 100px">

                <div class="top-card">
                    <div>
                        <image src="bmlocal://assets/bg.png" class="bg-tangle" @click="shake" ></image>
                        <div  style="height: 328px;">
                            <image ref="pG1" src="bmlocal://assets/perks/hat.png" class="gif-icon" ></image>
                            <!--<image ref="pG1" src="bmlocal://assets/perks/watch.png" class="gif-icon" ></image>-->
                            <image ref="pG2" src="bmlocal://assets/perks/dress.png" class="gif-icon1" ></image>

                            <image ref="pG3" src="bmlocal://assets/perks/package.png" class="gif-icon2" ></image>

                            <image ref="pG4" src="bmlocal://assets/perks/gift.png" class="gif-icon3" ></image>


                            <image ref="pG5" src="bmlocal://assets/perks/bikini.png" class="gif-icon4" ></image>
                            <image ref="pG6" src="bmlocal://assets/perks/y-headset.png" class="gif-icon5" ></image>
                            <image ref="pG7" src="bmlocal://assets/perks/shoe.png" class="gif-icon6" ></image>
                            <image ref="pG8" src="bmlocal://assets/perks/headset.png" class="gif-icon7" ></image>
                            <image ref="pG9" src="bmlocal://assets/perks/watch.png" class="gif-icon8" ></image>
                            <!--<image ref="pG10" src="bmlocal://assets/perks/gift.png" class="gif-icon9" ></image>-->

                        </div>
                    </div>
                    <!--<image src="bmlocal://assets/perks/egg2_2.gif" class="bg-tangle" ></image>-->
                    <text class="t-c-h">Daily Gift Box</text>

                    <div class="t-cm">

                        <div class="t-cm-c" v-if="!isCash"  @click="getPoints">
                            <text class="t-cm-cw" >Earn up to 200 cash bonus</text>
                            <text class="t-cm-ca iconfont">&#xe626;</text>
                        </div>
                        <div class="t-cm-cr" v-if="isCash" >
                            <text class="t-cm-cwr" >Get Your Next Gift Tomorrow</text>
                            <text class="t-cm-car iconfont">&#xe6ed;</text>
                        </div>
                        <image class="t-cm-i" src="bmlocal://assets/perks/cup.png" ></image>

                    </div>
                    <text class="t-cb" @click="openRuler">Details >></text>


                </div>
            </div>
            <div class="overflow-mid">

                <div class="mid-card">
                    <div class="top-right-div">
                        <image class="top-right" src="bmlocal://assets/anger.png"></image>
                    </div>

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
                    <text class="g-ch-w"  style="">Redeem Gift Card</text>
                </div>
                <div >
                    <div v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'mg-b5':'mg-b3',]">

                        <div class="gift-card" @click="redeemCard(i)">
                            <image class="gift-card-img"  :src="i.image"></image>
                            <div class="gift-card-txt">
                                <text class="gift-card-txt1">{{i.name}} Gift Voucher</text>
                                <text class="gift-card-txt2">{{i.pointNumber}} Points Needed</text>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <!--<div class="overflow-gift"  v-for="(i,index) in cardArr" :class="[index==cardArr.length-1 ?'overflow-gift-bottom':'',]">-->
                <!--<div >-->
                    <!---->
                    <!--<div class="gift-card" @click="redeemCard(i)">-->
                        <!--<image class="gift-card-img"  :src="i.image"></image>-->
                        <!--<div class="gift-card-txt">-->
                            <!--<text class="gift-card-txt1">{{i.name}} Gift Voucher</text>-->
                            <!--<text class="gift-card-txt2">{{i.pointNumber}} Points Needed</text>-->
                        <!--</div>-->


                        <!--<div class="gift-card-txt" v-if="i.id==1">-->
                            <!--<text class="gift-card-txt1">₹.150 Gift Card</text>-->
                            <!--<text class="gift-card-txt2">1,500 Points Needed</text>-->
                        <!--</div>-->
                        <!--<div class="gift-card-txt" v-if="i.id==2">-->
                            <!--<text class="gift-card-txt1">₹200 Gift Card</text>-->
                            <!--<text class="gift-card-txt2">2,000 Points Needed</text>-->
                        <!--</div>-->
                    <!--</div>-->
                <!--</div>-->

            <!--</div>-->

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
                                <text class="mask-tnw">&nbsp;₹ {{points||0}}</text>
                            </div>
                            <image v-if="giftBox" ref="giftH" class="g-b-h"   src="bmlocal://assets/perks/cover.png" ></image>
                            <image v-if="giftBox" ref="giftC" class="g-b-c" src="bmlocal://assets/perks/open-box.png"></image>

                        </div>


                    </div>

                    <text class="mask-t-w1">Congrats</text>
                    <div class="mask-t-w2-d">
                        <text class="mask-t-w2">You just received</text><text class="mask-t-w2b"> ₹ {{points||0}} </text><text class="mask-t-w2">cash bonus!</text>
                    </div>
                    <text class="mask-t-w3">Your cash bonus will expire</text>
                    <text class="mask-t-w3b">by end of today. </text>
                    <div class="mask-t-w4">
                        <text class="mask-t-w4w">Spend Your Bonus Now</text>
                    </div>
                </div>
            </div>
        </WxcMask>
        <WxcMask
            height="498"
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
                   <text class="r-cw">The daily cash bonus will expire after 12:00 PM every day. Please spend your bonus before it expires!</text>
                   <text class="r-cw">Your order can be paid with cash bonus at your checkout page.</text>
                   <text class="r-cw">You can get FREE cash bonus for 3 times. After that, you need to spend 250 points to unlock a gift box.</text>
                   <text class="r-cw">The cash bonus cannot be withdrawn.</text>
                </div>
                <div>
                    <text style="text-align: right;color: #EF8A31;">OK</text>
                </div>
            </div>
        </WxcMask>

    </div>
</template>

<script>
    const animation = weex.requireModule('animation')
    import slider from './slider';
    import { WxcMask } from 'weex-ui';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    const common = weex.requireModule('CommonUtils');

    export default {
        name: 'index',
        components: {
            'slider': slider,
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
              backup: [],
              block1: {
                  title: '',
                  url: '',
                  items: []
              },
              stopAnimation: false,
              hasAnimation: false,
              show: false,
              giftBox: true,
              loginS: false,
              points: '',
              isCash: false,
              ruleShow: false,
              user: false,
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
            googleAnalytics.trackingScreen('Perks');
            this.getCard()
            this.getBtnStatus()
            this.user = this.$storage.getSync('user')
            this.setTime()
            this.$event.on('logout', parmas => {
                this.getCard()
                this.getBtnStatus()
            })
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
                if (this.backup.length > 0) {
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
            getBtnStatus () {
                this.$fetch({
                    method: 'GET',
                    name: 'point.whether.cashing.prize',
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.isCash = res.isCash
                    // this.$notice.alert({
                    //     message: res.isCash
                    // })
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
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
                    // this.shake2()
                }, 2000)
            },
            shake (_ref, _x, _y) {
                // this.$notice.alert({ message: 'animation finished.' })
                animation.transition(_ref, {
                    styles: {
                        transform: 'translate(' + _x + 'px, ' + _y + 'px)'
                    },
                    duration: 800, // ms
                    timingFunction: 'ease',
                    delay: 0 // ms
                }, function () {
                    animation.transition(_ref, {
                        styles: {
                            transform: 'translate(0px, 0px)'
                        },
                        duration: 1000, // ms
                        timingFunction: 'ease',
                        delay: 0 // ms
                    })
                })

                // this.$notice.alert({
                //     message: this.$refs.pG1
                // })
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
            getPoints () {
                if (!this.isCash && this.user) {
                    this.show = true
                    this.$fetch({
                        method: 'GET',
                        name: 'point.cashing.prize',
                        header: {
                            needAuth: true
                        }
                    }).then((res) => {
                        this.points = res.amount
                        setTimeout(() => {
                            this.shake2()
                        }, 500)
                        this.isCash = true
                        // this.$notice.alert({
                        //     message: res.amount
                        // })
                    }).catch((res) => {
                        this.$notice.toast({
                            message: res
                        })
                    })
                } else if (!this.user) {
                    this.redirectLogin()
                }
            },
            openRuler () {
                this.ruleShow = true
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

            wxcMaskSetShareHidden () {
                this.show = false;
                this.ruleShow = false;
                common.changeAndroidCanBack(true)
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
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .header-word{
        font-family: ProximaNova-Bold;
        font-size: 40px;
        font-weight: 700;
        width: 474px;
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
    .overflow-gift-card{
        width: 686px;
        margin:  60px 32px 140px 32px;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background-color: white;
        border-radius: 8px;

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
        width: 594px;
        height: 252px;
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
    .mg-b3{
        margin-bottom: 30px;
    }
    .mg-b5{
        margin-bottom: 50px;
    }
    .top-card{
        width: 686px;
        height: 328px;
        background-color: white;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
        flex-direction: column;
        align-items: stretch;

    }
    .t-c-h{
        position: absolute;
        top: 34px;
        left: 0px;
        font-size: 36px;
        color: #000000;
        letter-spacing: 0;
        font-weight: 700;
        text-align: center;
    }
    .t-cm{
        position: absolute;
        top: 112px;
        left: 0px;
        width: 686px;
        /*background-color: black;*/
        flex-direction: row;
        justify-content: start;
    }
    .t-cm-i{
        position: relative;
        top: -30px;
        right: 500px;
        width: 150px;
        height: 150px;
    }
    .t-cm-c{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #EF8A31;
        height: 108px;
        width: 460px;
        border-radius:50%;
        margin-left:114px;
        margin-top: 10px;
    }
    .t-cm-cw{
        margin-left: 90px;
        font-size: 24px;
        color: white;
        font-weight: 700;
    }
    .t-cm-ca{
        font-size: 36px;
        color: white;
        margin-left: 20px;
        font-weight: 700;
    }
    .t-cm-cr{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #EF8A31;
        height: 108px;
        width: 460px;
        border-radius:50%;
        margin-left:104px;
        margin-top: 10px;
    }
    .t-cm-cwr{
        margin-left: 80px;
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
    .t-cb{
        position: absolute;
        bottom: 40px;
        left: 274px;
        font-weight: 700;
        text-decoration: underline;
    }
    .bg-tangle{
        position: absolute;
        top: 0;
        left: 0;
        width: 686px;
        height:328px;
    }
    .mask-content{
        flex-direction: column;
        justify-content: center;
        /*align-items: center;*/
    }
    .mask-h{
        flex-direction: row;
        justify-content: space-between;
        /*align-items: ;*/
    }
    .mask-h-c{
        font-size: 40px;
        margin-top:36px;
        margin-right:36px;
    }
    .mask-t{
        flex-direction: column;
        align-items: center;
    }
    .mask-t-w1{
        /*margin-top: 66px;*/
        font-size: 40px;
        color: rgba(0,0,0,0.87);
        font-weight: 700;
    }
    .mask-t-w2-d{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 16px;
        margin-bottom: 10px;
    }
    .mask-t-w2{
        font-size: 28px;
        color: rgba(0,0,0,0.87);
    }
    .mask-t-w2b{
        font-size: 28px;
        font-weight: 700;
        color: rgba(0,0,0,0.87);
    }
    .mask-t-w3{
        font-size: 24px;
        color: #000000;
        line-height: 30px;
    }
    .mask-t-w3b{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        line-height: 30px;
        margin-bottom: 34px;
    }
    .mask-t-w4{
        background-color: #EF8A31;
        padding: 14px 40px;
        border-radius: 50%;

    }
    .mask-t-w4w{
        color: white;
        font-size: 24px;
    }
    .mask-tn{
        background-color: #EF8A31;
        width: 150px;
        position: absolute;
        top: 190px;
        left: 270px;
        transform: scale(0.3);
        border-radius: 8px;
    }
    .mask-tnw{
        font-weight: 700;
        margin: 10px;
        font-size: 40px;
        color: white;
        border-radius: 8px;
        border-width:7px ;
        border-style:solid ;
        border-color:white ;
    }
    .g-b-h{
        width: 203px;
        height: 104px;
        margin-top: 90px;
        margin-left: 240px;
    }
    .g-b-c{
        width: 175px;
        height: 126px;
        margin-left: 252px;
    }
    .mask-ruler-content{
        margin: 50px;
    }
    .r-cw{
        font-size: 28px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
        line-height: 30px;
        margin-bottom: 16px;
    }

</style>
