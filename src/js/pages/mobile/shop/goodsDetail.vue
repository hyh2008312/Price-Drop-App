<template>

    <div class="wrapper" >
        <topic-header ref="ref1" :style="{opacity:opacity}"></topic-header>
        <div class="blackheader"></div>

        <scroller class="main-list" @scroll="scrollHandler"  offset-accuracy="10px">

            <div style="background-color: white" >

                <slider class="slider" interval="3000" auto-play="true" :index="2">
                    <div class="frame" v-for="(img, idx) in goodsImg">
                        <preload class="image" :src="img"></preload>
                        <text style="right:20px;bottom:20px;color:black;position:absolute">{{idx+1}}/{{goodsImg.length}}</text>
                    </div>
                </slider>
                <text class="iiileft"  @click="$router.back">&#xe6f6;</text>
                <text class="iiiright" @click="openLink">&#xe700;</text>
                <text class="onetitle">{{goods.title}}</text>
                <text class="price">Rs.{{goods.price}}</text>

                <div class="count-div">
                    <text class=" count" >Get it at</text><text class="count-bold">Rs.{{lowestPrice}}</text><text class="count-1">by inviting friends!</text>

                </div>
            </div>
                 <div class="learn-drop">
                    <div  class="learn-div" @click="openShip(1)"><text class="learn-picon iconfont">&#xe723;</text><text class="learn-price" >How to Drop Price</text></div>
                    <div  class="learn-div" @click="openShip(2)"><text class="learn-sicon iconfont">&#xe722;</text><text class="learn-ship">Shipping & Delivery</text></div>
                 </div>



                <div v-if="tabshow==true" style="position: sticky">
                    <tab   @tabTo="onTabTo" :items="tabsItems"></tab>
                </div>

            <div class="mid">
                <!--<wxc-cell :has-arrow="true"-->
                          <!--title="Size/Color"-->
                          <!--:cell-style="cellStyle"-->
                          <!--:has-top-border="true"-->
                          <!--:has-bottom-border="true"-->
                          <!--@wxcCellClicked="wxcCellClick"-->
                          <!--:auto-accessible="false">-->
                <!--</wxc-cell>-->

                <div class="dec-word" @click="wxcCellClick" v-if="hasVariants === true"  >
                    <text class="dec">Size/Color</text>

                </div>

                <div class="slogan" >
                    <div class="slg">
                        <text class="i-slg-icon">&#xe714;</text>
                        <text class="i-slg"> Quality Guaranteed </text>
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
                <text class="button" @click="openBottomPopup">Invite Friends to Drop Price</text>
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
                        <text class="popup-yet" v-if="hasVariants==true">Chose：{{selcolor}}&nbsp;&nbsp;&nbsp;&nbsp;{{selsize}}</text>
                    </div>

                    <text class="popup-close" @click="popupOverlayBottomClick">&#xe632;</text>
                </div>
                <scroller  class="scroller">

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
                    <text class="button" @click="wxcCellClick()">Invite Friends to Drop Price</text>
                </div>

            </div>
        </wxc-popup>
    </div>
</template>
<script>
    import header from './header';
    import cimg from './customImg';
    import preload from '../common/preloadImg';
    import { WxcCell, WxcButton, WxcPopup, WxcMask } from 'weex-ui'
    import tab from './tab';
    import { baseUrl } from '../../../config/apis';
    const dom = weex.requireModule('dom');

    // import block from './block';
    // import refresher from '../common/refresh';
    // import { GOODS } from './config'

    export default {
        components: {
            'topic-header': header,
            WxcCell, WxcButton, WxcPopup, WxcMask,
            'tab': tab,
            'cimg': cimg,
            preload
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
                block1: {
                    title: '',
                    url: ''
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
                shipObj: '',
                hasVariants: true,
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
                user: null,
                opacity: 0
            }
        },
        computed: {
            opacity: {
                get: function () {
                    return this.opacity
                },

                set: function (v) {
                    this.opacity = v
                }
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
            createCut () {
                this.$fetch({
                    method: 'POST',
                    url: `${baseUrl}/promotion/cut/create/`,
                    data: { variant_id: this.variantsId },
                    header: {
                        needAuth: true
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
                    this.$notice.toast({
                        message: res
                    })
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            wxcCellClick () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.isBottomShow = true;

                    if (this.variantsId != '') {
                        this.createCut()
                    }
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
                if (this.hasVariants === false) {
                    this.createCut()
                } else {
                    if (this.user == null) {
                        this.redirectLogin()
                    } else {
                        this.isBottomShow = true;
                    }
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
            scrollHandler (e) {
                if (Math.abs(e.contentOffset.y) >= 112) {
                   this.opacity = (Math.abs(e.contentOffset.y) - 112) / 200 > 1 ? 1 : (Math.abs(e.contentOffset.y) - 112) / 200
                } else {
                   this.opacity = 0
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
    .price{
        font-family: PingFangSC-Semibold;
        font-weight: 700;
        margin-top: 16px;
        margin-left: 32px;
        font-size: 32px;
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
    .count-div{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }
    .count{
        color: rgba(0,0,0,1);
        margin-top: 16px;
        margin-left: 32px;
        margin-bottom: 26px;
        font-size:24px;
    }
    .count-1{
        color: #000000;
        margin-left: 16px;
        margin-top: 16px;
        margin-bottom: 26px;
        font-size:24px;
    }
    .count-bold{
        color: #EF8A31;
        margin-top: 16px;
        margin-left: 16px;
        margin-bottom: 26px;
        font-size:32px;
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
    .i-slg{
        font-size: 20px;
        opacity: 0.87;
        flex: 1;
        text-align: center;
        color: black;
        margin-bottom: 48px;
    }
    .learn-drop{
        width: 750px;
        height: 96px;
        border-top-style:solid ;
        border-top-width:1px ;
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
        margin-bottom: 100px;
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
        font-size: 24px;
        margin-top: 16px;
        margin-left: 32px;
        margin-bottom: 4px;

    }
    .popup-lowprice{
        font-size: 28px;
        font-weight: 600;
        color: #EF8A31;
        margin-left: 32px;

    }
    .popup-yet{
        font-size: 24px;
        opacity: 0.54;
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
        font-size: 24px;
        font-weight: 700;
        margin-top: 27px;
        margin-left: 32px;

    }
    .popup-color-chd{
        width: 750px;
        font-weight: 700;
        font-size: 24px;
        margin-left: -16px;
        margin-top: 16px;
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
