<template>

    <div class="wrapper" @touchstart="ontouchstart" @touchmove="ontouchmove" @touchend="ontouchend"  >
        <topic-header ref="ref1" ></topic-header>
        <div class="blackheader"></div>

        <scroller class="main-list" @scroll="scrollHandler"   offset-accuracy="300px">

            <div style="background-color: white" >

                <slider class="slider" interval="3000" auto-play="true" :index="2">
                    <div class="frame" v-for="(img, idx) in goodsImg">
                        <image class="image" resize="cover" :src="img"></image>
                        <text style="right:20px;bottom:20px;color:black;position:absolute">{{idx+1}}/{{goodsImg.length}}</text>
                    </div>
                </slider>
                <text class="iiileft"  @click="$router.back">&#xe6f6;</text>
                <text class="iiiright" @click="openLink">&#xe700;</text>
                <text class="onetitle">{{goods.title}}</text>
                <text class="price">Rs.{{goods.price}}</text>
                <text class="count">You can get it at Rs.{{goods.cut_get}} by inviting friends!  </text>
            </div>
                 <div class="learn-drop">
                    <div  class="learn-div" @click="openCard"><text class="learn-picon iconfont">&#xe723;</text><text class="learn-price" >How to Drop Price</text></div>
                    <div  class="learn-div" @click="openShip"><text class="learn-sicon iconfont">&#xe722;</text><text class="learn-ship">Shipping & Delivery</text></div>
                 </div>



                <div v-if="tabshow==true" style="position: sticky">
                    <tab   @tabTo="onTabTo" :items="tabsItems"></tab>
                </div>

            <div class="mid">
                <wxc-cell :has-arrow="true"
                          title="Size/Color"
                          :cell-style="cellStyle"
                          :has-top-border="true"
                          :has-bottom-border="true"
                          @wxcCellClicked="wxcCellClick"
                          :auto-accessible="false">
                </wxc-cell>

                <div class="slogan" >
                    <div class="slg">
                        <text class="i-slg-icon">&#xe714;</text>
                        <text class="i-slg"> Quality Guarantee </text>
                    </div>

                    <div class="slg">
                        <text class="i-slg-icon">&#xe715;</text>
                        <text class="i-slg"> 9 Days Return </text>
                    </div>

                    <div class="slg">
                        <text class="i-slg-icon">&#xe713;</text>
                        <text class="i-slg"> Secured Payments </text>
                    </div>
                </div>


            </div>
            <div class="dec-word" ref="dec" >
                <text class="dec">Description</text>

            </div>
                <div style="width: 750px; background-color: white">
                    <div v-for="i in newDescription ">
                        <div v-if="i.type=='text'">
                            <text class="bottom-text">{{i.context}}</text>
                        </div>
                        <div v-if="i.type=='image'">
                            <cimg :imgsrc="i.context"></cimg>
                            <!--<image :src="i.context" resize="stretch" style="width:750px;height:700px"></image>-->
                        </div>
                    </div>
                </div>

                <div>


                <div class="bottom-div" >
                    <!--<div >-->
                        <!--<text class="leftline"></text>-->
                    <!--</div>-->
                    <text class="bottom-head" >Return Policy</text>
                    <!--<div class="rightline">-->
                        <!--<text style="letter-spacing: -10px">———————————</text>-->
                    <!--</div>-->

                    <text class="bottom-text">You may return all items sold by PriceDrop within 9 days of delivery for a refund, as long as it is unused and in a good condition.
                    </text>
                    <text class="bottom-text">Currently we are not able to offer item exchange service for any shipped orders. If you want a new item, please apply for the refund and then place a new order.
                    </text>
                    <text class="bottom-text" >To learn more about our return policy, please visit our FAQ page.
                    </text>

                </div>
            </div>
            <div style="display: none" ref="policy"></div>
            <div class="bottom-btn" >
                <text class="button" @click="openBottomPopup">Drop The Price Now</text>
            </div>
        </scroller>



        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgb(255, 255, 255)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupOverlayBottomClick"
                   pos="bottom"
                   ref="wxcPopup"
                   height="718">
            <div class="popup-content">
                <div class="popup-top">

                    <image :src="selimgsrc"
                           class="popup-image"></image>

                    <div class="popup-py">
                        <text class="popup-price">Rs{{selsaleUnitPrice}}</text>
                        <text class="popup-lowprice-word">The lowest price that drop can get </text>
                        <text class="popup-lowprice">Rs.{{lowestPrice}}</text>
                        <text class="popup-yet">Chose：{{selcolor}}&nbsp;&nbsp;&nbsp;&nbsp;{{selsize}}</text>
                    </div>

                    <text class="popup-close" @click="popupOverlayBottomClick">&#xe632;</text>
                </div>
                <scroller class="scroller">

                    <div class="popup-bottom">
                        <div v-for="(val, index) in goodsType" :key="index">
                            <text class="popup-color">{{val.name}}</text>

                            <div  class="popup-color-chd" >
                                <text class="popup-color-chdname  "
                                      v-for="(val1, key1) in val.value"
                                      :key="key1"
                                      :class="[val1.isActive ?'popup-color-chdname-active':'',
                                      val1.seldisable ?'popup-color-chdname-disable':'']"
                                      @click="clickColor(val1, val.value)">{{val1.value}}</text>
                            </div>
                        </div>
                    </div>
                </scroller>
                <div class="popup-btn">
                    <text class="button" @click="wxcCellClick">Share to Drop the Price</text>
                </div>

            </div>
        </wxc-popup>

        <wxc-mask height="800"
                  width="702"
                  border-radius="0"
                  duration="200"
                  mask-bg-color="#FFFFFF"
                  :has-animation="hasCardAnimation"
                  :has-overlay="true"
                  :show-close="true"
                  :show="isCardShow"
                  @wxcMaskSetHidden="wxcCardSetHidden">
            <scroller >

            <div class="mask-content">
                    <div class="mask-head">
                        <text class="mask-title">How To Drop Price</text>
                    </div>
                    <text class="mask-text">Step1:  Click the “Drop The Price” button to start your DROP campaign immediately.
                    </text>
                    <text class="mask-text">Step2:  Invite friends to join you by sharing your DROP campaign to Facebook & WhatsAPP.
                    </text>
                    <text class="mask-text">Step3:  When people click the “Drop Price” button for you on the page you share, the price will drop automatically.
                    </text>
                    <text class="mask-text">Step4:  After the DROP campaign ends, you can purchase the item at the final price you have reached!
                    </text>

                    <div class="mask-head">
                        <text class="mask-title">Rules to Know</text>
                    </div>
                    <text class="mask-text">1. The duration of a Price Drop campaign is up to 24 hours. The campaign will end automatically once it reaches the lowest price.
                    </text>
                    <text class="mask-text">2. As soon as the DROP ends, you have 24 hours to make your purchase. If you do not complete the payment in time, you’re deemed to waive your purchase right, and the reduced price will also expire.
                    </text>
                    <text class="mask-text">3. Due to the limited stocks of each item, the products will be given out on a "first-pay, first-serve” basis. That means, if the stock has been running out before you complete the payment, the DROP may fail as well.
                    </text>

            </div>
            </scroller>

        </wxc-mask>
    </div>

</template>
<script>
    import header from './header';
    import cimg from './customImg';
    import { WxcCell, WxcButton, WxcPopup, WxcMask } from 'weex-ui'
    import tab from './tab';
    import { baseUrl } from '../../../config/apis';
    const animation = weex.requireModule('animation');
    const axios = weex.requireModule('bmAxios');
    const globalEvent = weex.requireModule('globalEvent');
    const dom = weex.requireModule('dom');

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
                cellStyle: {
                    'padding-top': '36px',
                    'padding-bottom': '36px'
                },
                block1: {
                    title: '',
                    url: '',
                },
                goodsId: '',
                goods: {
                    title: '',
                    price: '',
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
                selsize: '',
                selcolor: '',
                selimgsrc: '',
                variantsId: '',
                selsaleUnitPrice: '',
                lowestPrice: '',
                newDescription: [],
                proId: '',
                dectxt: [],
                cactiveId: 1,
                sactiveId: '',
                isBottomShow: false,
                isCardShow: false,
                overlayCardCanClose: true,
                isCardFalse: false,
                hasCardAnimation: true,
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

        },
        methods: {
            getGoodsDetail (id) {
                if (id) {
                    this.$fetch({
                        method: 'GET',
                        // url: 'http://47.104.171.91/product/customer/detail/135/',
                        // url: 'http://149.129.135.114/product/customer/detail/' + id.id + '/',
                        url: `${baseUrl}/product/customer/detail/${id.id}/`,
                        // name: 'product.customer.list',
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
                                this.goodsType = []
                                this.variantsId = res.variants[0].id
                            }
                            this.decimg = []
                            this.dectxt = []
                            // this.$notice.toast({
                            //     message: res.newDescription
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
            wxcCellClick: function () {
                this.isBottomShow = true;
                if (this.variantsId != '') {
                    this.$notice.alert({
                        message: this.variantsId
                    })
                    this.$fetch({
                        method: 'POST',
                        url: 'http://149.129.135.114/promotion/cut/create/',
                        data: { variant_id: this.variantsId },
                        header: {
                            Authorization: 'Bearer ' + 'IMSFn72n3aEZrIf7E0uvlphWnEEIut'
                        }
                    }).then((res) => {
                        this.$router.open({
                            name: 'drops.cutDetail',
                            type: 'PUSH',
                            params: {
                                isShowSharePanel: false,
                                id: res.id
                            }
                        })
                    }).catch((res) => {
                        this.$notice.alert({
                            message: res
                        })
                        // this.$notice.toast({
                        //     message: res
                        // })
                    })
                }
            },
            redirectLogin () {
                this.$event.once('login', params => {
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
            openBottomPopup () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.isBottomShow = true;
                }
            },

            // 非状态组件，需要在这里关闭
            popupOverlayBottomClick () {
                this.isBottomShow = false;
                this.isCardShow = false;
            },

            operateData (data) {
                for (let i = 0; i < data.length; i++) {
                    for (let j = 0; j < data[i].value.length; j++) {
                        data[i].value[j].isActive = false
                        data[i].value[j].seldisable = false
                    }
                }
                return data
            },
            clickColor (item, list) {
                if (item.seldisable) return
                item.isActive = !item.isActive
                for (let i = 0; i < list.length; i++) {
                    if (list[i].value != item.value) {
                        list[i].isActive = false
                    }
                }
                const color = []
                const discolor = []
                for (let j = 0; j < this.goodsVariants.length; j++) {
                    for (let k = 0; k < this.goodsVariants[j].attributeValues.length; k++) {
                        if (item.value == this.goodsVariants[j].attributeValues[k].value) {
                            color.push({
                                item: this.goodsVariants[j],
                                index: k
                            })
                            break;
                            // this.seldisable = true
                        }
                    }
                }

                if (item.isActive == true) {
                    for (let n = 0; n < color.length; n++) {
                        for (let m = 0; m < color[n].item.attributeValues.length; m++) {
                            if (m == color[n].index) {
                                continue
                            }
                            discolor.push(color[n].item.attributeValues[m].value)
                        }
                    }

                    for (let p = 0; p < this.goodsType.length; p++) {
                        if (item.id != this.goodsType[p].id) {
                            for (let u = 0; u < this.goodsType[p].value.length; u++) {
                                this.goodsType[p].value[u].seldisable = false
                                for (let o = 0; o < discolor.length; o++) {
                                    if (this.goodsType[p].value[u].value != discolor[o]) {
                                        this.goodsType[p].value[u].seldisable = true
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (let p = 0; p < this.goodsType.length; p++) {
                        if (item.id != this.goodsType[p].id) {
                            for (let u = 0; u < this.goodsType[p].value.length; u++) {
                                this.goodsType[p].value[u].seldisable = false
                            }
                        }
                    }
                }
                this.changeDom(item, color)
                // this.$notice.toast({
                //     message: item
                // })
                // this.cactiveId = id
            },
            changeDom (item, color) {
                if (color.length !== 0) {
                    this.selsaleUnitPrice = color[0].item.saleUnitPrice
                    this.variantsId = color[0].item.id
                } else {
                    this.$notice.toast({
                        message: 'this size/color is none'
                    })
                }
                if (item.isActive == true) {
                    if (item.id == 1) {
                        this.selsize = item.value
                    } else if (item.id == 2) {
                        this.selcolor = item.value
                    }
                } else if (item.isActive == false) {
                    if (item.id == 1) {
                        this.selsize = ''
                    } else if (item.id == 2) {
                        this.selcolor = ''
                    }
                }
                let tmp = []
                for (let i = 0; i < this.goodsType.length; i++) {
                    tmp = this.goodsType[i].images
                }
                for (let j = 0; j < tmp.length; j++) {
                    if (tmp[j].value == this.selcolor) {
                        this.selimgsrc = tmp[j].image
                    }
                }
            },

            openCard (e) {
                this.isCardShow = true;
                this.hasCardAnimation = true;
            },
            openShip (e) {
                this.$router.open({
                    name: 'goods.ship',
                    type: 'PUSH'
                })
            },

            wxcCardSetHidden () {
                this.isCardShow = false;
            },
            openLink () {
                this.$router.setBackParams({ tab: 'drops' })
                this.$router.back({
                    length: 9999,
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
                console.log(e.direction);

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
            },
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
        height: 96px;
        margin-top: 32px;
        margin-left: 32px;
        opacity: 0.435;
        font-family: PingFangSC-Light;
        font-size: 28px;
        color: #000;
        line-height: 48px;
        letter-spacing: 0;
    }
    .price{
        font-family: PingFangSC-Semibold;
        margin-top: 24px;
        margin-left: 32px;
        font-size: 48px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
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
    .count{
        color: rgba(172,11,11,0.87);
        margin-top: 24px;
        margin-left: 32px;
        margin-bottom: 26px;
        font-size:24px;
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
        margin-bottom: 16px;
    }
    .slg{
        align-items:center ;
    }
    .i-slg-icon{
        font-family: iconfont;
        text-align: center;
        font-size: 50px;
        margin: 25px 0;
    }
    .i-slg{
        height: 66px;
        font-size: 26px;
        padding-top: 16px;
        flex: 1;
        text-align: center;
        color: black;
    }
    .learn-drop{
        width: 750px;
        height: 96px;
        border-top-style:solid ;
        border-top-width:2px ;
        border-top-color:rgba(0,0,0,0.08) ;
        background-color: #fff;
        flex-direction: row;
        justify-content: space-around;
    }
    .learn-div{
        width: 375px;
        padding-top: 32px;
        border-right-color:rgba(0,0,0,0.08) ;
        border-right-width:1px ;
        border-right-style:solid ;
        flex-direction: row;
        justify-content: center;
    }
    .learn-picon{
        color: #1abdcd;
        font-size: 28px;
        margin-right: 8px;
    }
    .learn-sicon{
        color: #ffde00;
        font-size: 28px;
        margin-right: 8px;
    }
    .learn-price{
        font-weight: 700;
        font-size: 24px;
    }
    .learn-ship{
        font-weight: 700;
        font-size: 24px;
    }
    .dec-word{
        width: 750px;
        height: 110px;
        padding-top: 40px;
        padding-left: 30px;
        margin-top: 16px;
        border-bottom-color: rgba(0,0,0,0.12) ;
        border-bottom-width: 2px ;
        border-bottom-style: solid ;
        background-color: white;
    }
    .bottom-head{
        text-align: center;
        font-family: PingFangSC-Semibold;
        font-weight: 500;
        font-size: 28px;
        margin: 40px 0;
        color: rgba(0,0,0,0.87);
    }
    .bottom-div{
        width: 750px;
        box-shadow: 0 1px 1px 0;
        background-color: #fff;
        padding-top: 48px;
        margin-bottom: 100px;
    }
    .bottom-text{
        margin-left: 32px;
        margin-right: 32px;
        padding-top: 24px;
        text-align: left;
    }
    .bottom-btn{
        background-color: #fff;
        box-shadow: 0 -1px 1px 0;
        width: 750px;
        position: fixed;
        bottom: 0;
        right: 0;
        overflow: hidden;
    }
    .button{
        color: #fff;
        background-color: #EF8A31;
        border-color: #2e6da4;
        border-radius: 12px;
        padding-top: 20px;
        margin-top: 16px;
        margin-left: 12px;
        padding-bottom: 20px;
        margin-right: 12px;
        margin-bottom: 16px;
        font-size: 36px;
        text-align: center;
        font-weight: 500;
    }
    .scroller{
        max-height: 500px;

    }
    .popup-content{
        height: 718px;
        width: 750px;
        flex-direction: column;

    }
    .popup-image {
        width: 200px;
        height: 200px;
        margin-bottom: 27px;
        margin-top: 32px;
        margin-left: 32px;
    }
    .popup-top{
        flex-direction: row;
    }
    .popup-py{
        flex-direction: column;

    }
    .popup-price{
        font-size: 32px;
        font-weight: 600;
        margin-top: 75px;
        margin-left: 32px;

    }
    .popup-lowprice-word{
        font-size: 28px;
        margin-top: 15px;
        margin-left: 32px;

    }
    .popup-lowprice{
        font-size: 28px;
        font-weight: 600;
        color: #EF8A31;
        margin-left: 32px;

    }
    .popup-yet{
        font-size: 24px;
        margin-left: 32px;
        margin-top: 16px;
    }
    .popup-close{
        font-family: iconfont;
        font-size: 32px;
        position: absolute;
        right: 32px;
        top: 32px;
    }
    .popup-bottom{

    }
    .popup-color{
        font-size: 32px;
        margin-top: 27px;
        margin-left: 32px;

    }
    .popup-color-chd{
        width: 750px;
        margin-left: -16px;
        margin-top: 26px;
        flex-direction: row;
        display: flex;
    }
    .popup-color-chdname{
        font-size: 20px;
        height:48px;
        line-height: 45px;
        text-align:center;
        margin-left: 48px;
        padding-left: 20px;
        padding-right: 20px;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(0,0,0,0.12);
        border-radius: 4px;

    }
    .popup-color-chdname-active{
        font-size: 20px;
        height:48px ;
        line-height: 45px;
        text-align:center;
        margin-left: 48px;
        padding-left: 20px;
        padding-right: 20px;
        border-width: 1px;
        border-style: solid;
        color: white;
        background-color: #EF8A31;
        border-radius: 4px;

    }
    .popup-color-chdname-disable{
        font-size: 20px;
        background-color: #dcdcdc;
        color: #fff;
        height:48px ;
        line-height: 45px;
        text-align:center;
        margin-left: 48px;
        padding-left: 20px;
        padding-right: 20px;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(0,0,0,0.12);
        border-radius: 4px;

    }
    .popup-btn{
        position: relative;
        bottom: 0;
    }

    .mask-content {
        padding: 30px;
    }
    .mask-head {
        align-items: center;
        margin-bottom: 20px;
        margin-top: 40px;
    }
    .mask-title {
        color: #333333;
        font-size: 40px;
    }
    .mask-text {
        color: #333333;
        font-size: 30px;
        margin-top: 20px;
    }
</style>
