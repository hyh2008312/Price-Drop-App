<template>

    <div class="wrapper" @touchstart="ontouchstart" @touchmove="ontouchmove" @touchend="ontouchend"  >
        <topic-header ref="ref1" @open="openLink" :leftBtn="2" :rightBtn="2" ></topic-header>
        <div class="blackheader"></div>

        <scroller class="main-list" @scroll="scrollHandler"   offset-accuracy="300px">

            <div style="background-color: white" >

                <slider class="slider" interval="3000" auto-play="true" :index="2">
                    <div class="frame" v-for="(img, idx) in goodsImg">
                        <image class="image" resize="cover" :src="img"></image>
                        <text style="right:20px;bottom:20px;color:black;position:absolute">{{idx+1}}/{{goodsImg.length}}</text>
                    </div>
                </slider>
                <text class="iiileft"  @click="$router.back">&#xe632;</text>
                <text class="onetitle">{{goods.title}}</text>

                <div class="count-div">
                    <text class=" count" >Get it at</text><text class="count-bold">₹{{parseInt(lowestPrice)}}</text><text class="count-1">by inviting friends!</text>
                </div>
                <div class="count-div">
                    <text class=" price-name" >Original Price:</text><text class="price">₹{{parseInt(goods.price)}}</text>

                </div>

            </div>
            <div class="learn-drop" @click="openShip(1)" >
                <div class="learn-drop-itme1">
                    <text class="learn-drop-itmeh">How It Works</text>
                    <div class="learn-drop-itmed">
                        <text class="learn-drop-itmem">More Details </text><text class="iconfont learn-drop-itmem" style="padding-top: 2px">&#xe626;</text>
                    </div>
                </div>

                <div class="learn-drop-itme2">
                    <div class="progress">
                        <div class="progress-numdiv"><text  class="progress-num">1</text></div>
                        <text class="progress-word">Start a Drop</text>
                    </div>
                    <div class="progress">
                        <div class="progress-numdiv"><text  class="progress-num">2</text></div>
                        <text class="progress-word">Invite Friends</text>
                    </div>
                    <div class="progress">
                        <div class="progress-numdiv"><text  class="progress-num">3</text></div>
                        <text class="progress-word">Get Lowest Price</text>
                    </div>
                </div>
                <div class="learn-drop-line">
                    <div class="progress-line-left"></div>
                    <div class="progress-line-right"></div>
                </div>
            </div>

                <div v-if="tabshow==true" style="position: sticky">
                    <tab   @tabTo="onTabTo" :items="tabsItems"></tab>
                </div>

            <div class="mid">

                <div class="slogan" >
                    <div class="slg">
                        <text class="i-slg-icon">&#xe714;</text>
                        <text class="i-slg-1" > Quality  </text>
                        <text class="i-slg-2" > Guaranteed </text>
                    </div>

                    <div class="slg">
                        <text class="i-slg-icon">&#xe751;</text>
                        <text class="i-slg-1" > Worldwide  </text>
                        <text class="i-slg-2"  > Brands </text>
                    </div>

                    <div class="slg">
                        <text class="i-slg-icon">&#xe713;</text>
                        <text class="i-slg-1" > Secured  </text>
                        <text class="i-slg-2"  > Payments </text>
                    </div>

                    <div class="slg">
                        <text class="i-slg-icon">&#xe715;</text>
                        <text class="i-slg-1" > Easy </text>
                        <text class="i-slg-2" > Return </text>
                    </div>
                </div>


            </div>
            <div class="dec-word" ref="dec" >
                <text class="dec">Description</text>
            </div>
                <div style="width: 750px; background-color: white ; padding-top: 20px">
                    <div v-for="(i, index) in newDescription ">
                        <div v-if="i.type=='text'">
                            <text class="bottom-text">{{i.context}}</text>
                        </div>
                        <cimg v-if="i.type=='image'" :imgsrc="i.context"></cimg>
                    </div>
                </div>

                <div>


                <div class="bottom-div" >

                    <text class="bottom-head" >Return Policy</text>

                    <text class="bottom-text">You may return all items sold by PriceDrop within 9 days of delivery for a refund, as long as it is unused and in a good condition.
                    </text>
                    <text class="bottom-text">Currently we are not able to offer item exchange service for any shipped orders. If you want a new item, please apply for the refund and then place a new order.
                    </text>
                    <text class="bottom-text" >To learn more about our return policy, please visit our FAQ page.
                    </text>

                </div>
            </div>
            <div style="background-color: white" ref="policy"></div>
        </scroller>
    </div>
</template>
<script>
    import header from './header';
    import cimg from './customImg';
    import { WxcCell, WxcButton, WxcPopup, WxcMask } from 'weex-ui'
    import tab from './tab';
    import { baseUrl } from '../../../config/apis';
    const animation = weex.requireModule('animation');
    const dom = weex.requireModule('dom');
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    // import block from './block';
    // import refresher from '../common/refresh';
    // import { GOODS } from './config'

    export default {
        components: {
            'topic-header': header,
            WxcCell, WxcButton, WxcPopup, WxcMask,
            'tab': tab,
            'cimg': cimg
            // 'refresher': refresher,
            // 'block': block
        },
        eros: {
          beforeAppear (a) {
              this.proId = a
              googleAnalytics.trackingScreen(`Cut Product Detail/${this.proId.id}`);
              this.getGoodsDetail(a)
          },
          beforeBackAppear (params) {
              //   this.$notice.toast({
              //     message: 111111
              // })
          }
        },
        data () {
            return {
                block1: {
                    title: '',
                    url: ''
                },
                goodsId: '',
                goods: {
                    title: '',
                    price: '0.00',
                    cut_get: '',
                    brandLogo: ''
                },
                goodsImg: [],
                tabsItems: [{
                    name: 'Description',
                    key: 'dec'
                }, {
                    name: 'Return Policy',
                    key: 'policy'
                }],
                goodsVariants: [],
                goodsType: {},
                selsaleUnitPrice: '',
                lowestPrice: '0.00',
                newDescription: [],
                proId: '',
                height: 400,
                tabshow: false,
                headerShow: true,
                positionX: 0,
                positionY: 0,
                deltaX: 0,
                deltaY: 0,
                user: null
            }
        },
        created () {
            if (this.$storage.getSync('user')) {
                this.user = this.$storage.getSync('user')
            } else {
                this.user = null
            }
        },
        methods: {
            getGoodsDetail (id) {
                if (id) {
                    this.$fetch({
                        method: 'GET',
                        url: `${baseUrl}/product/customer/detail/${id.id}/`,
                        data: {}
                    }).then((res) => {
                            this.goods.title = res.title;
                            this.goods.price = res.saleUnitPrice;
                            this.selsaleUnitPrice = res.saleUnitPrice;
                            this.lowestPrice = res.lowestPrice;
                            this.goods.brandLogo = res.brandLogo;
                            this.goodsImg = res.images;
                            if (res.cutGet == null) {
                                this.goods.cut_get = 0
                            } else {
                                this.goods.cut_get = res.cutGet
                            }

                            this.goodsVariants = res.variants;
                            if (res.images != null) {
                                this.selimgsrc = res.images[0]
                            } else {
                                this.selimgsrc = ''
                            }
                            if (res.attributes != null && res.attributes.length > 0) {
                                this.goodsType = this.operateData(res.attributes);
                            } else {
                                this.hasVariants = false
                                this.goodsType = []
                                this.variantsId = res.variants[0].id
                            }

                            this.shipObj = res.shipping
                            this.dectxt = []
                            // this.$notice.toast({
                            //     message: res.expectedDeliveryDateMax
                            // })
                            if (res.newDescription != null) {
                                this.newDescription = res.newDescription
                                // for (let i = 0; i < res.description.length; i++) {
                                //     if (res.description[i].type == 'image') {
                                //         this.decimg.push(res.description[i].context)
                                //     } else {
                                //         this.dectxt.push(res.description[i].context)
                                //     }
                                // }
                            }
                    }).catch((res) => {
                        // this.$notice.toast({
                        //     message: res
                        // })
                    })
                }
            },
            redirectLogin () {
                this.$event.on('login', params => {
                    this.getGoodsDetail(this.proId)
                    this.$storage.get('user').then(resData => {
                        this.user = resData
                    })
                })
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            },
            ontouchstart (event) {
                this.positionX = event.changedTouches[0].screenX;
                this.positionY = event.changedTouches[0].screenY;
            },
            ontouchmove (event) { //  touchmove 实现 判断上移 还是下移
                const moveX = event.changedTouches[0].screenX;
                const moveY = event.changedTouches[0].screenY;
                this.deltaX = moveX - this.positionX;
                this.deltaY = moveY - this.positionY;
                // this.$notice.toast({
                //     message: this.deltaY
                // })
                if (this.deltaY > 0 && Math.abs(this.deltaY) > Math.abs(this.deltaX) && this.topval == '1') {

                    // this.headerShow = true
                }
                if (this.deltaY < -100 && Math.abs(this.deltaY) > Math.abs(this.deltaX)) {
                    this.headerShow = false;
                }
                // this.$notice.toast({
                //     message: event.changedTouches[0].screenY
                // })
            },
            ontouchend (event) {
                this.positionX = 0;
                this.positionY = 0;
                this.deltaX = 0;
                this.deltaY = 0
            },
            scrollHandler (e) { //  scroller 滚动函数 通过动画实现的
               if (e.contentOffset.y >= -10) {
                   animation.transition(this.$refs.ref1, {
                       styles: {
                           opacity: '0',
                           height: '148px'

                       },
                       duration: 200, // ms
                       timingFunction: 'linear',
                       needLayout: false,
                       delay: 0 // ms
                   });
                   this.topval = '1'
               } else {
                   animation.transition(this.$refs.ref1, {
                       styles: {
                           opacity: '1',
                           height: '148px'
                       },
                       duration: 200, // ms
                       timingFunction: 'linear',
                       needLayout: false,
                       delay: 0 // ms
                   });
                   this.topval = '2'
               }
               if (Math.abs(e.contentOffset.y) > 1200) {
                   // this.$notice.toast({
                   //     message: '55555'
                   // });
                    this.tabshow = true
               } else if (Math.abs(e.contentOffset.y) < 1100) {
                   this.tabshow = false
               }
            },
            openShip (e) {
                if (e == 1) {
                    this.$router.open({
                        name: 'goods.ship',
                        type: 'PUSH'
                    })
                } else {
                    this.$router.open({
                        name: 'goods.ship',
                        type: 'PUSH',
                        params: {
                            time: this.shipObj
                        }
                    })
                }
            },
            onTabTo (key) {
                if (key.data.key == 'dec') {
                    const el = this.$refs.dec
                    dom.scrollToElement(el, { offset: -140 })
                    this.$nextTick(() => {
                        dom.scrollToElement(this.$refs['tab'], { animated: false })
                    })
                } else {
                    const el = this.$refs.policy
                    dom.scrollToElement(el, { offset: 2 })
                    this.$nextTick(() => {
                        dom.scrollToElement(this.$refs['tab'], { animated: false })
                    })
                }
            }

        }
    }
</script>
<style scoped>

    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
        opacity: 0;
    }
    .image {
        width: 750px;
        height: 700px;
    }
    .slider {
        width: 750px;
        height: 700px;
    }
    .frame {
        width: 750px;
        height: 700px;
        position: relative;
    }
    .iiileft{
        font-family: iconfont;
        color: white;
        font-size: 32px;
        background-color: rgba(0,0,0,.38);
        position: absolute;
        border-radius:24px ;
        top:80px;
        left:40px;
        width: 48px;
        line-height: 48px;
        text-align: center;
        align-items: center;
    }
    .iiiright{
        font-family: iconfont;
        color: white;
        font-size: 32px;
        background-color: rgba(0,0,0,.38);
        position: absolute;
        border-radius:24px ;
        top:80px;
        right:40px;
        width: 48px;
        line-height: 48px;
        text-align: center;
        align-items: center;
    }
    .onetitle{
        width:586px;
        height: 120px;
        margin-top: 32px;
        margin-left: 32px;
        font-family: PingFangSC-Light;
        font-size: 28px;
        color: #000;
        line-height: 40px;
        letter-spacing: 0;
    }
    .price-name{
        color: rgba(0,0,0,1);
        margin-left: 32px;
        margin-bottom: 26px;
        font-size:24px;
    }
    .price{
        font-family: PingFangSC-Semibold;
        font-weight: 700;
        margin-left: 6px;
        margin-bottom: 26px;
        font-size: 24px;
        color: rgba(0,0,0,0.54);
        letter-spacing: 0;
        text-align: left;
        text-decoration: line-through;
    }
    .rightlogo{
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 64px;
        position: absolute;
        /*background-color: gold;*/
        top:848px;
        right:40px;
        width: 128px;
        height: 128px;
    }
    .iconfont{
        font-family: iconfont;
    }
    .count-div{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }
    .count{
        color: rgba(0,0,0,1);
        margin-left: 32px;
        margin-bottom: 20px;
        font-size:24px;
    }
    .count-1{
        color: #000000;
        margin-left: 16px;
        margin-bottom: 26px;
        font-size:24px;
    }
    .count-bold{
        color: #EF8A31;
        margin-left: 16px;
        margin-bottom: 26px;
        font-size:40px;
        font-weight: 700;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .mid{
        margin-top: 14px;
        background-color: white;
    }

    .slogan{
        flex-direction: row;
        justify-content: space-around;
        background-color: #fff;
        border-bottom: 2px solid black;
    }
    .slg{
        margin-top: 48px;
        align-items:center ;
    }
    .i-slg-icon{
        font-family: iconfont;
        opacity: 0.54;
        text-align: center;
        font-size: 50px;
        margin-bottom:16px ;
    }
    .i-slg-1{
        font-size: 20px;
        opacity: 0.87;
        flex: 1;
        text-align: center;
        color: black;
        margin-top: 14px;
        margin-bottom: 7px
    }
    .i-slg-2{
        font-size: 20px;
        opacity: 0.87;
        flex: 1;
        text-align: center;
        color: black;
        margin-bottom: 48px
    }
    .dec-word{
        width: 750px;
        height: 96px;
        padding-top: 36px;
        padding-left: 30px;
        margin-top: 16px;
        border-bottom-color: rgba(0,0,0,0.12) ;
        border-bottom-width: 1px ;
        border-bottom-style: solid ;
        background-color: white;
    }
    .dec{
        font-size: 24px;
        font-weight:700;
    }
    .bottom-head{
        text-align: left;
        font-family: PingFangSC-Semibold;
        font-weight: 700;
        padding-left: 32px;
        font-size: 24px;
        margin: 32px 0 24px 0;
        color: rgba(0,0,0,0.87);
    }
    .bottom-div{
        width: 750px;
        box-shadow: 0 1px 1px 0;
        background-color: #fff;
        padding-top: 16px;
        /*margin-bottom: 100px;*/
    }
    .bottom-text{
        margin-left: 32px;
        margin-right: 32px;
        font-size: 24px;
        line-height: 36px;
        text-align: left;
    }
    .bottom-btn{
        background-color: #fff;
        box-shadow:  0 -2px 3px 0 rgba(0,0,0,0.24);
        width: 750px;
        height: 112px;
        position: fixed;
        bottom: 0;
        right: 0;
        overflow: hidden;
    }
    .button{
        color: #fff;
        height: 80px;
        background-color: #EF8A31;
        border-color: #2e6da4;
        border-radius: 12px;
        padding-top: 22px;
        padding-bottom: 10px;
        margin-top: 16px;
        margin-left: 12px;
        margin-right: 12px;
        margin-bottom: 16px;
        font-size: 28px;
        text-align: center;
        font-weight: 700;
    }

    .learn-drop{
        width: 750px;
        height: 208px;
        border-top-style:solid ;
        border-top-width:1px ;
        border-top-color:rgba(0,0,0,0.08) ;
        background-color: #fff;
        flex-direction: column;
        margin-top: 12px;
        /*justify-content: space-around;*/
    }
    .learn-drop-itme1{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .learn-drop-itmeh{
        padding-left: 32px;
        padding-top: 32px;
        font-size: 24px;
        font-weight: 700;
    }
    .learn-drop-itmed{
        flex-direction: row;
        padding-top: 32px;
        padding-right: 32px;

    }
    .learn-drop-itmem{
        font-size: 20px;
        color: #EF8A31;
    }
    .learn-drop-itme2{
        flex-direction: row;
        justify-content:space-between ;
    }
    .progress{
        /*background-color: #f4f4f4;*/
        padding-top: 32px;
        justify-content:center ;
        width: 190px;
        margin-right: 32px ;
        margin-left: 32px ;
    }
    .progress-numdiv{
        flex-direction: row;
        justify-content:center ;

    }
    .progress-num{
        width: 32px;
        height: 32px;
        text-align: center;
        padding-top: 5px;
        color: white;
        background-color: #EF8A31;
        font-size: 20px;
        border-radius: 50%;
    }
    .learn-drop-line{
        flex-direction: row;
        justify-content:space-around ;
        margin-top: -50px;
    }
    .progress-line-left{
        height: 3px;
        width: 225px;
        background-color: rgba(0,0,0,0.12);
        margin-left: 125px;
    }
    .progress-line-right{
        height: 3px;
        width: 225px;
        background-color: rgba(0,0,0,0.12);
        margin-right: 113px;
    }
    .progress-word{
        flex-direction: row;
        text-align: center;
        font-size: 24px;
        margin-top: 6px;
    }
</style>
