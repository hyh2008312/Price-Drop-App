<template>
    <div class="wrapper" >
        <div :style="{ backgroundColor : bgcolor}">
            <div class="blackheader"></div>
            <div class="top-header" >
                <div class="th-right">
                    <text class="th-r-1" @click="openGuide">Guideline</text>
                    <div class="th-r-d" @click="openMyRaffleDraws">
                        <image class="th-r-img" src="bmlocal://assets/myprize-01.png"></image>
                    </div>
                    <text class="dot" v-if="dotNum>0">{{dotNum}}</text>
                </div>
                <div class="th-left">
                    <div class="th-l-div-icon" @click="goDefault">
                        <text class="iconfont th-l-icon ">&#xe74d;</text>
                    </div>
                    <div>
                        <text class="th-l-w1">{{tranDateY(time)}}</text>
                        <text class="th-l-w2">{{tranDate(time)}}</text>
                    </div>

                </div>
            </div>

            <div class="wrapper1" v-if="selindex">
                <NewS :slider-id="sliderId"
                      :card-length='cardArr.length'
                      ref="wxc-ep-slider"
                      :card-s="cardSize"
                      :select-index="selindex"
                      @wxcEpSliderPullMore="prePullMore"
                      @wxcEpSliderCurrentIndexSelected="wxcEpSliderCurrentIndexSelected">
                    <div class="more-slider"
                         v-if="isLoading"
                         slot="pull-more">
                        <image class="loading-icon" resize="contain" src="bmlocal://assets/loading.gif"></image>
                    </div>
                    <!--自动生成demo-->
                    <div v-for="(v,index) in cardArr"
                         :key="index"
                         :slot="`card${index}_${sliderId}`"
                         :class="['slider']"
                         :accessible="true"
                    >
                        <CenterCard :item="v"  :itemIndex="index" :selindex="selindex"  v-on:openMask="openM" v-on:openShare="openS" ></CenterCard>
                        <!--<text class="text">这里是第{{index + 1}}个滑块</text>-->
                    </div>
                </NewS>
            </div>
        </div>

        <WxcMask
            height="742"
            width="560"
            border-radius="16"
            duration="200"
            mask-bg-color="#FFFFFF"
            :has-animation="hasAnimation"
            :has-overlay="true"
            :show-close="false"
            :show="show"
            @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="mask-content">
                <image  style="width:560px;height:742px;" src="bmlocal://assets/wallet.png"></image>
                <!--<div >-->
                <div class="mask-top">
                    <div class="mask-c-h">
                        <image class="mask-c-i"  src="bmlocal://assets/raffle-2.png"></image>
                    </div>
                    <div class="mask-c-t">
                        <text class="mask-c-tw">Prize Winner to be Announced At:</text>
                        <text class="mask-c-tt" style="margin-bottom: 24px">{{tranDateM(endTime)}}</text>

                        <text class="mask-c-tw">To join this lucky draw,</text>
                        <div class="mask-c-r" style="margin-bottom: 36px">
                            <text class="mask-c-tw">you need to spend </text> <text class="mask-c-tt">500</text><text class="mask-c-tw"> points.</text>
                        </div>
                        <div class="mask-c-r" style="margin-bottom: 24px">
                            <text class="mask-c-tw">Your available points: </text> <text class="mask-c-tt">{{myPoints}}</text><text class="mask-c-tw"></text>
                        </div>
                    </div>
                    <div class="mask-btn" @click="getResult()" v-if="myPoints >= 500" style="margin-left: 110px">
                        <text class="mask-btn-w">ENTER</text>
                    </div>

                    <div class="mask-btn-5"  v-if="myPoints < 500" style="margin-left: 110px">
                        <text class="mask-btn-w">ENTER</text>
                    </div>
                </div>
                <text class="mask-bottom-w" v-if="myPoints < 500" >You need to earn more points to enter this lucky draw.</text>


                <!--<div class="mask-bottom">-->
                    <!--<text>11111</text>-->
                <!--</div>-->
                <!--</div>-->

            </div>
        </WxcMask>

        <wxc-popup popup-color="rgb(92, 184, 92)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupOverlayBottomClick"
                   pos="bottom"
                   height="400">
            <div class="demo-content">
                <div>
                    <div></div>
                </div>
            </div>
        </wxc-popup>
    </div>
</template>
<script>
    const common = weex.requireModule('CommonUtils');
    import { WxcEpSlider, WxcPanItem, WxcMask, WxcPopup } from 'weex-ui';
    import NewS from './test'
    import CenterCard from './centerCard'
    import dayjs from 'dayjs';
    export default {
        components: { WxcEpSlider, WxcPanItem, WxcMask, WxcPopup, NewS, CenterCard },
        data: () => ({
            sliderId: 1,
            autoSliderId: 2,
            // cardLength: 5,
            cardSize: {
                width: 624,
                height: 998,
                spacing: 42,
                scale: 1
            },
            selindex: '',
            defaultIndex: '',
            bgcolor: '',
            time: '',
            endTime: '',
            cardArr: false,
            page: 1,
            pageSize: 10,
            show: false,
            isLoading: false,
            isBottomShow: false,
            loginS: false,
            user: false,
            myPoints: false,
            dotNum: false
        }),
        created () {
            this.user = this.$storage.getSync('user')
            if (this.user) {
                this.loginS = true
                this.myPoints = this.user.pointsAvailable
                this.getCardA()
                this.getDot()
            } else {
                this.getCard()
            }
            // this.$event.on('login', parmas => {
            //     this.getCardA()
            // });
            this.$event.on('readR', parmas => {
                this.dotNum = 0
            })
            // this.$storage.get('user').then(resData => {
            //     this.user = resData
            //     if (this.user) {
            //         this.loginS = true
            //         this.myPoints = this.user.pointsAvailable
            //         this.getCardA()
            //     } else {
            //         this.getCard()
            //     }
            // })
           this.initBack()
        },
        computed: {
            // bgcolor: {
            //     get: function () {
            //         return this.bgcolor
            //     },
            //
            //     set: function (v) {
            //         this.bgcolor = v
            //     }
            // }

        },
        methods: {
            initBack () {
                common.setAndroidCanBack(true, (params) => {
                    if (this.show) {
                        this.wxcMaskSetShareHidden();
                    }
                    common.changeAndroidCanBack(true)
                })
            },
            openMyRaffleDraws () {
                if (!this.loginS) {
                    this.redirectLogin()
                } else {
                    this.$router.open({
                        name: 'my.raffle.draws',
                        type: 'PUSH'
                    })
                }
            },
            goDefault () {
                // this.selindex = this.defaultIndex
                this.$refs['wxc-ep-slider'].manualSetPage(this.defaultIndex);
            },
            openM () {
                if (!this.loginS) {
                   this.redirectLogin()
                } else {
                    this.show = true
                }
                common.changeAndroidCanBack(false);
            },
            openS () {
                // if (!this.loginS) {
                //    this.redirectLogin()
                // } else {
                //     this.openBottomPopup()
                // }
                // common.changeAndroidCanBack(false);
            },

            openBottomPopup () {
                this.isBottomShow = true;
            },
            popupOverlayBottomClick () {
                this.isBottomShow = false;
            },

            wxcMaskSetShareHidden () {
                this.show = false;
                common.changeAndroidCanBack(true)
            },
            wxcEpSliderCurrentIndexSelected (e) {
               const index = e.currentIndex;
               this.selindex = e.currentIndex;
               if (index == '') {
                   this.selindex = '0'
               }
               this.bgcolor = this.cardArr[this.selindex].background
               this.time = this.cardArr[this.selindex].startTime
               this.endTime = this.cardArr[this.selindex].endTime
            },
            getCard () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'lottery.draw.list',
                    // url: `${baseUrl}/lottery/draw/list/`,
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    }
                }).then((res) => {
                    this.cardArr = (res.results).reverse()
                    let tmp = ''
                    for (let i = 0; i < this.cardArr.length; i++) {
                        if (this.cardArr[i].drawStatus === 'Ongoing') {
                            tmp = i
                            break
                        } else {
                            tmp = 2
                            tmp = 2
                        }
                    }
                    this.selindex = tmp
                    this.defaultIndex = tmp
                    // this.$notice.alert({
                    //     message: this.selindex
                    // })
                    this.bgcolor = this.cardArr[this.selindex].background
                    this.time = this.cardArr[this.selindex].startTime
                    this.endTime = this.cardArr[this.selindex].endTime

                    this.page++
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            // prePullMore () {
            //     if (this.isLoading) {
            //         return
            //     } else {
            //         if (!this.loginS) {
            //             this.pullMore()
            //         } else if (this.loginS) {
            //             this.pullMoreA()
            //         }
            //     }
            // },
            pullMore () {
                this.isLoading = true
                if (this.page > this.length) {
                    if (this.cardArr.length > 0) {}
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                this.$fetch({
                    method: 'GET',
                    name: 'lottery.draw.list',
                    // url: `${baseUrl}/lottery/draw/list/`,
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    }
                }).then((res) => {
                    this.selindex = false;
                    for (const item of res.results) {
                        this.cardArr.unshift(item);
                    }
                    this.$nextTick(() => {
                        this.selindex = 0 + res.results.length - 1;
                    })
                    this.length = Math.ceil(res.count / this.pageSize);
                    this.page++
                    this.isLoading = false
                }).catch((res) => {
                    this.isLoading = false
                    this.$notice.toast({
                        message: res
                    })
                })
            },

            getResult () {
                this.$fetch({
                    method: 'POST',
                    name: 'lottery.draw',
                    data: {
                        id: this.cardArr[this.selindex].id
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.$notice.toast({
                        message: 'success'
                    })
                    this.cardArr[this.selindex] = res

                    // this.$notice.alert({
                    //     message: res.imageSet
                    // })
                    this.wxcMaskSetShareHidden()
                }).catch((res) => {
                    // this.$notice.loading.hide();
                    this.$notice.toast({
                        message: res.errorMsg
                    })
                })
            },

            getDot () {
                this.$fetch({
                    method: 'GET',
                    name: 'lottery.draw.prize.unread',
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.dotNum = res.count
                    // this.$notice.toast({
                    //     message: res.count
                    // })
                }).catch((res) => {
                    // this.$notice.loading.hide();
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            redirectLogin () {
                this.$event.on('login', params => {
                    this.$storage.get('user').then(resData => {
                        this.user = resData
                        this.myPoints = this.user.pointsAvailable
                        this.loginS = true
                        this.selindex = false
                        this.page = 1
                        this.getCardA()
                    })
                });
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            },

            getCardA () {
                this.$notice.loading.show();
                // this.$notice.alert({
                //     message: this.pageSize
                // })
                this.$fetch({
                    method: 'GET',
                    name: 'lottery.draw.list',
                    // url: `${baseUrl}/lottery/draw/list/`,
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.cardArr = (res.results).reverse()
                    let tmp = ''
                    for (let i = 0; i < this.cardArr.length; i++) {
                        if (this.cardArr[i].drawStatus === 'Ongoing') {
                            tmp = i
                            break
                        } else {
                            tmp = 2
                            tmp = 2
                        }
                    }
                    this.selindex = tmp
                    this.defaultIndex = tmp

                    this.bgcolor = this.cardArr[this.selindex].background
                    this.time = this.cardArr[this.selindex].startTime
                    this.endTime = this.cardArr[this.selindex].endTime

                    this.page++
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            pullMoreA () {
                this.isLoading = true
                if (this.page > this.length) {
                    if (this.cardArr.length > 0) {}
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                this.$fetch({
                    method: 'GET',
                    name: 'lottery.draw.list',
                    // url: `${baseUrl}/lottery/draw/list/`,
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.selindex = false;
                    for (const item of res.results) {
                        this.cardArr.unshift(item);
                    }
                    this.$nextTick(() => {
                        this.selindex = 0 + res.results.length - 1;
                    })
                    this.length = Math.ceil(res.count / this.pageSize);
                    this.page++
                    this.isLoading = false
                }).catch((res) => {
                    this.isLoading = false
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            tranDate (tmp) {
                return dayjs(new Date(tmp)).format('MMM DD')
            },
            tranDateM (tmp) {
                return dayjs(new Date(tmp)).format('MMM DD, HH:mm a')
            },
            tranDateY (tmp) {
                return dayjs(new Date(tmp)).format('YYYY')
            },
            onClick () {
                // 手动切换到第2张
                this.$refs['wxc-ep-slider'].manualSetPage(1);
            },
            openGuide () {
                this.$router.open({
                    name: 'raffle.guide',
                    type: 'PUSH'
                });
            }
            // wxcPanItemPan (e) {
            //     if (BindEnv.supportsEBForAndroid()) {
            //         this.$refs['wxc-ep-slider'].clearAutoPlay();
            //         this.$refs['wxc-ep-slider'].bindExp(e.element)
            //     }
            // },
            // wxcPanItemClicked (e) {
            //     modal.toast({
            //         message: e.extId,
            //         duration: 1
            //     })
            // }
        }
    }
</script>

<style scoped>
    .wrapper{}
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
    .top-header{
        flex-direction: row;
        justify-content: space-between;
        margin-top: 48px;
    }
    .th-right{
        flex-direction: row;
        justify-content: start;
        margin-top: 25px;
    }
    .th-r-1{
        background-color: white;
        height: 64px;
        width: 168px;
        text-align: center;
        flex-direction: column;
        align-items: center;
        line-height: 65px;
        color: #EF8A31;
        border-top-right-radius: 40px;
        border-bottom-right-radius:40px ;
        font-size: 24px;
        font-weight: 700;
    }
    .th-r-d{
        width: 64px;
        height: 64px;
        border-radius: 50%;
        background-color: white;
        margin-left: 8px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .th-r-img{
        width:40px;
        height:40px;
    }
    .dot{
        background-color: #F84242;
        color: white;
        font-size:20px;
        width: 20px;
        text-align: center;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        border-radius: 50%;
        margin-left: -12px;
    }
    .th-left{
        margin-top: 25px;
        flex-direction: row;
        justify-content: center;
        align-items: center;

    }
    .th-l-div-icon{
       margin-right: 14px;
    }
    .th-l-icon{
        color: white;
        font-size: 60px;
        opacity: 0.8;

    }
    .th-l-w1 {
        color: white;
        margin-bottom: 4px;
    }
    .th-l-w2{
        color: white;
        margin-right: 24px;
    }
    .scroller {
        flex: 1;
    }
    .wrapper1 {
        padding-top: 40px;
        height: 1150px;  /*卡片背景高度*/
    }
    .slider {
        width: 624px;
        height: 999px;
        background-color: white;
    }
    .more-slider{
        width: 100px;
        height: 998px;
        /*background-color: #ffc302;*/
    }
    .mask-content{

    }
    .mask-top{
        position: absolute;
        top:54px;
        left:108px
    }
    .mask-c-h{
        /*flex-direction: row;*/
        /*justify-content: center;*/
        /*align-items: center;*/
        /*margin-top: 54px;*/
        margin-left: 54px;
    }
    .mask-c-i{
        width: 190px;
        height: 190px;
    }
    .mask-c-t{
        margin-bottom: 24px;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .mask-c-tw{
        font-family: ProximaNova;
        font-size: 24px;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        line-height: 28px;
    }
    .mask-c-tt{
        font-size: 24px;
        color: #FFFFFF;
        letter-spacing: 0;
        font-weight: 700;
        line-height: 28px;
    }
    .mask-c-r{
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .mask-btn{
        border-radius: 100%;
        background-color:#EAD68E;
        width: 128px;
        height: 128px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .mask-btn-5{
        border-radius: 100%;
        background-color:#AFAFAF;
        width: 128px;
        height: 128px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .mask-btn-w{
        font-weight: 700;
    }
    .mask-bottom-w{
        position: absolute;
        bottom:78px ;
        left:20px;
        font-size: 20px;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        /*margin-left: -100px;*/
    }
    .loading-icon{
        position: absolute;
        top: 470px;
        width: 64px;
        height: 64px;
    }
</style>
