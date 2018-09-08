<template>
    <div class="wrapper" >
        <div :style="{ backgroundColor : bgcolor}">
            <div class="blackheader"></div>
            <div class="top-header" >
                <div class="th-right">
                    <text class="th-r-1" @click="openGuide">Guideline</text>
                    <div class="th-r-d">
                        <image class="th-r-img" src="bmlocal://assets/myprize-01.png"></image>
                    </div>
                    <text class="dot">1</text>
                </div>
                <div class="th-left">
                    <text class="th-l-w1">2018</text>
                    <!--<text class="th-l-w2">Aug 17th</text>-->
                    <text class="th-l-w2">{{time}}</text>
                </div>
            </div>

            <div class="wrapper1">
                <NewS :slider-id="sliderId"
                      :card-length='cardArr.length'
                      :card-s="cardSize"
                      :select-index="selindex"
                      @wxcEpSliderPullMore="pullMore"
                      @wxcEpSliderCurrentIndexSelected="wxcEpSliderCurrentIndexSelected">
                    <!--自动生成demo-->
                    <div v-for="(v,index) in cardArr"
                         :key="index"
                         :slot="`card${index}_${sliderId}`"
                         :class="['slider']"
                         :accessible="true"
                    >
                        <CenterCard :item="v"  v-on:openMask="openM"></CenterCard>
                        <!--<text class="text">这里是第{{index + 1}}个滑块</text>-->
                    </div>
                    <div class="more-slider"
                         slot="pull-more"
                         :style="{left: `${cardArr.length * (cardSize.width + cardSize.spacing)+60}px`,marginLeft:`${(750 - cardSize.width) / 2}px`}">
                        <text>加载更多</text>
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
                        <text class="mask-c-tw">Prize Winner Announced At:</text>
                        <text class="mask-c-tt" style="margin-bottom: 24px">16:00 Aug 17th</text>

                        <text class="mask-c-tw">To join this raffle draw,</text>
                        <div class="mask-c-r" style="margin-bottom: 36px">
                            <text class="mask-c-tw">you need to spend </text> <text class="mask-c-tt">500</text><text class="mask-c-tw"> points.</text>
                        </div>
                        <div class="mask-c-r" style="margin-bottom: 24px">
                            <text class="mask-c-tw">Your points balance: </text> <text class="mask-c-tt">1200</text><text class="mask-c-tw"> points</text>
                        </div>
                    </div>
                    <div class="mask-btn" @click="getResult()" style="margin-left: 110px">
                        <text class="mask-btn-w">ENTER</text>
                    </div>
                </div>
                <text class="mask-bottom-w">You need to earn more points to enter this raffle.</text>


                <!--<div class="mask-bottom">-->
                    <!--<text>11111</text>-->
                <!--</div>-->
                <!--</div>-->

            </div>
        </WxcMask>
    </div>
</template>
<script>
    const common = weex.requireModule('CommonUtils');
    import { WxcEpSlider, WxcPanItem, WxcMask } from 'weex-ui';
    import NewS from "./test"
    import CenterCard from "./centerCard"
    export default {
        components: { WxcEpSlider, WxcPanItem, WxcMask, NewS, CenterCard },
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
            selindex: 1,
            bgcolor: '',
            time: '',
            cardArr: false,
            page: 1,
            pageSize: 5,
            show: false
        }),
        created () {
           this.getCard()
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
            openM () {
                this.show = true
                common.changeAndroidCanBack(false);
            },
            wxcMaskSetShareHidden () {
                this.show = false;
                common.changeAndroidCanBack(true)
            },
            wxcEpSliderCurrentIndexSelected (e) {
               const index = e.currentIndex;
               this.bgcolor = this.cardArr[index].background
               this.time = this.cardArr[index].startTime
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
                    this.cardArr = res.results
                    this.bgcolor = this.cardArr[this.selindex].background
                    this.time = this.cardArr[this.selindex].startTime
                    // this.$notice.alert({
                    //     message: this.bgcolor
                    // })
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
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
                    this.wxcMaskSetShareHidden()
                }).catch((res) => {
                    // this.$notice.loading.hide();
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            onClick () {
                // 手动切换到第2张
                this.$refs['wxc-ep-slider'].manualSetPage(1);
            },
            pullMore () {
                this.$notice.toast({
                    message: 'pull more'
                })
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
        margin-top: 32px;
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
        margin-top: 30px;

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
        height: 1050px;
    }
    .slider {
        width: 624px;
        height: 999px;
        background-color: white;
    }
    .more-slider{
        width: 100px;
        height: 300px;
        background-color: #ffc302;
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
</style>
