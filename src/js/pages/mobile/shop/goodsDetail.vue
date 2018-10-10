<template>

    <div class="wrapper" >
        <topic-header ref="ref1" :style="{opacity:opacity}" @open="openLink" :leftBtn="1" :rightBtn="1" :dropGoods="dropGoods"></topic-header>
        <div class="blackheader"></div>

        <scroller class="main-list" @scroll="scrollHandler"  offset-accuracy="10px">

            <div style="background-color: white" >

                <slider class="slider" interval="8000" auto-play="true" :index="2">
                    <div class="frame" v-for="(img, idx) in goodsImg">
                        <preload class="image" :src="img"></preload>
                        <text style="right:20px;bottom:20px;color:black;position:absolute">{{idx+1}}/{{goodsImg.length}}</text>
                    </div>
                </slider>
                <div class="iiileft-div" @click="$router.back">
                    <text class="iiileft">&#xe6f6;</text>
                </div>
                <text class="iiiright" v-if="purchaseMethod==='direct'||purchaseMethod==='drop'" @click="openLink">&#xe700;</text>
                <div class="red-dot" v-if="(dropGoods>0)&&(purchaseMethod==='direct'||purchaseMethod==='drop')"><text style="color: white;font-size: 20px">{{dropGoods}}</text></div>
                <flash v-if="purchaseMethod==='flash'"
                       :hour="ahour"
                       :min="amin"
                       :second="asecond"
                       :fstatus="flashSale.flashStatus"
                       :saleUnitPrice="goods.price"
                       :unitPrice="goods.unitPrice"
                       :discount="flashSale.discount"
                ></flash>

                <text class="onetitle">{{goods.title}}</text>
                <div class="count-div" >
                    <text class=" count" v-if="purchaseMethod==='drop'" >Get it at</text>
                    <text class=" price-name" v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')" >Exclusive Price:</text>
                    <text class="count-bold" v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')">₹{{goods.unitPrice}}</text>

                    <text class="count-bold" v-if="purchaseMethod==='drop'">₹{{lowestPrice}}</text>
                    <text class="count-1" v-if="purchaseMethod==='drop'">by inviting your friends!</text>
                </div>
                <div class="count-div">

                    <text class=" price-name" v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')" >Original Price: </text>
                    <text class="price" v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')" >₹{{goods.price}}</text>
                    <text class="price-name price-price" v-if="purchaseMethod==='direct'" >{{goods.priceoff}}% OFF</text>
                    <text class="price-name price-price" v-if="flashSale.flashStatus=='Scheduled'" >{{goods.priceoff}}% OFF</text>
                    <text class="price-name price-price" v-if="flashSale.flashStatus=='Ongoing'" >{{countOff(countPrice(goods.unitPrice, flashSale.discount), goods.price)}}</text>
                    <text class="price-name price-off" >Free Shipping</text>
                </div>
                <div class="count-div">
                    <!--<text class=" price-name" >You Save:</text><text class="price">{{}}%</text>-->
                </div>

                <div class="reward-points" v-if="!isDrop" @click="openRuler">
                    <div class="r-p-t" >
                        <text class="r-p-t1">Reward Points</text>
                        <text class="r-p-t2" >Details</text>
                    </div>
                    <div class="r-p-c">
                        <image class="r-p-i" src="bmlocal://assets/gold-coin.png"></image>
                        <div class="r-p-w" v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')">
                            <text class="r-p-w1">Earn</text> <text class="r-p-w1b">{{countPoints(goods.unitPrice,divider,dividend)}}</text> <text class="r-p-w1"> points by purchasing this product </text>
                        </div>
                        <div class="r-p-w" v-if="flashSale.flashStatus=='Ongoing'">
                            <text class="r-p-w1">Earn</text> <text class="r-p-w1b"> {{countPoints(calc(goods.unitPrice,flashSale.discount),divider,dividend)}} </text> <text class="r-p-w1"> points by purchasing this product </text>
                        </div>
                    </div>
                </div>
            </div>
            <div class="learn-drop" @click="openShip(1)" v-if="isDrop">
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
                 <!--<div v-if="tabshow" style="position: sticky">-->
                    <!--<tab   @tabTo="onTabTo" :items="tabsItems" :indexKey="defaultTab"></tab>-->
                 <!--</div>-->

            <div class="mid">
                <div class="dec-word" @click="wxcCellClick" v-if="hasVariants === true"  >
                    <text class="dec">Size/Color</text>
                    <text class="iconfont dec-arrows">&#xe626;</text>

                </div>

                <div class="dec-word" @click="openShip(2)"   >
                    <text class="dec">Shipping Time & Cost</text>
                    <text class="iconfont dec-arrows">&#xe626;</text>

                </div>

                <div class="slogan" v-if="!isDrop">
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

                <div class="dec-word" ref="dec" >
                    <text class="dec">Description</text>
                </div>
            </div>



            <div style="width: 750px; background-color: white ; padding-top: 20px;height: 568px">
                <div v-for="(i, index) in newDescription ">
                    <div v-if="i.type=='text'">
                        <text class="bottom-text">{{i.context}}</text>
                    </div>
                    <cimg v-if="i.type=='image'" :imgsrc="i.context"></cimg>
                </div>
            </div>
            <div class="dec-see-more" @click="opendec">
                <text class="d-sm-w">SEE MORE</text>
            </div>

            <div >
                <div class="bottom-div" :style="{height: firstHeight}" >
                    <text class="bottom-head" >Return Policy</text>

                    <text class="bottom-text">You may return all items sold by PriceDrop within 9 days of delivery for a refund, as long as it is unused and in a good condition.
                    </text>
                    <text class="bottom-text" v-if="isDrop==false">Currently we are not able to offer item exchange service for any shipped orders. If you want a new item, please apply for the refund and then place a new order.
                    </text>
                    <text class="bottom-text" v-if="isDrop">Currently we are not able to offer item exchange service for any shipped orders. If you want a new item, please apply for a refund and start to drop the price again. If this product is no longer available for a Drop, we will fully refund you.
                    </text>
                    <text class="bottom-text" >To learn more about our return policy, please visit our FAQ page.
                    </text>

                </div>
            </div>

            <div class="dec-see-return" v-if="!returnPolicy" @click="policyShow">
                <text class="d-sm-w">SEE MORE</text>
            </div>
            <div class="dec-see-return" v-if="returnPolicy" @click="policyShow">
                <text class="d-sm-w">SEE LESS</text>
            </div>
            <div class="bottom-goods">
                <text class="bottom-head" >You Might Also Like</text>
                <div   v-for="(i, index) in someGoodsList" :key="i.id"  >
                    <somegoods :goods="i"  ></somegoods>
                </div>
            </div>

            <!--<div style="display: none" ref="policy"></div>-->

            <div class="bottom-btn" v-if="purchaseMethod === 'drop'" >
                <div v-if="productStatus==='unpublished'">
                    <text class="button-gray"  >Unavailable</text>
                </div>
                <div v-if="productStatus==='published'">
                    <text class="button" @click="openCutPrice" v-if="canBuy">Invite Friends to Drop Price</text>
                    <text class="button-gray"  v-if="!canBuy">Out of Stock</text>
                </div>
            </div>

            <div class="bottom-btn" v-if="purchaseMethod !== 'drop'">
                <div v-if="productStatus==='unpublished'">
                    <text class="button-gray"  >Unavailable</text>
                </div>
                <div v-if="productStatus==='published'">
                    <text class="button" @click="openBuyNow" v-if="canBuy" >Buy Now</text>
                    <text class="button-gray"  v-if="!canBuy" >Out of Stock</text>
                </div>
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
                        <text class="popup-price">₹{{selsaleUnitPrice}}</text>
                        <text class="popup-lowprice-word" v-if="isDrop">Start a drop to get it at: </text>
                        <text class="popup-lowprice-word" v-if="!isDrop">Exclusive Price: </text>
                        <text class="popup-lowprice"      v-if="isDrop">₹{{lowestPrice}}</text>
                        <text class="popup-lowprice"      v-if="flashSale.flashStatus=='Ongoing'">₹{{ (selunitPrice*(flashSale.discount/100)).toFixed(2) || (goods.unitPrice*(flashSale.discount/100)).toFixed(2)}}</text>
                        <text class="popup-lowprice"      v-if="flashSale.flashStatus=='Scheduled'||purchaseMethod==='direct'">₹{{selunitPrice||goods.unitPrice}}</text>
                        <div class="popup-yet-bg" v-if="hasVariants==true">
                            <text class="popup-yet" v-if="selcolor != ''">{{selcolor}}</text>
                            <text class="popup-yet" v-if="selsize != ''">{{selsize}}</text>
                        </div>
                    </div>

                    <text class="popup-close" @click="popupCloseClick">&#xe632;</text>
                </div>
                <scroller  class="scroller">

                    <div class="popup-bottom">
                        <div v-for="(val, index) in goodsType" :key="index">
                            <text class="popup-color">{{val.name}}</text>

                            <div  class="popup-color-chd" >
                                <text class="popup-color-chdname"
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
                    <text class="button" @click="confirm()" v-if="canBuy==true" >Confirm</text>
                    <text class="button-gray"               v-if="canBuy==false" >Out of Stock</text>
                </div>

            </div>
        </wxc-popup>

        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgba(255, 255, 255, 0)"
                   :show="isRulerShow"
                   @wxcPopupOverlayClicked="popupOverlayBottomClick"
                   pos="bottom"
                   ref="wxcRulerPopup"
                   height="770">
            <div class="popup-ruler-content">
                <div class="pr-c1">
                    <div class="r-p-t3">
                        <text class="r-p-t4">Reward Points</text>
                        <text class="iconfont r-icon" @click="popupRulerClick">&#xe632;</text>
                    </div>
                    <div class="r-p-c">
                        <image class="r-p-i" src="bmlocal://assets/gold-coin.png"></image>
                        <div class="r-p-w" v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')">
                            <text class="r-p-w1">Earn</text> <text class="r-p-w1b">{{countPoints(goods.unitPrice,divider,dividend)}}</text> <text class="r-p-w1"> points by purchasing this product </text>
                        </div>
                        <div class="r-p-w" v-if="flashSale.flashStatus=='Ongoing'">
                            <text class="r-p-w1">Earn</text> <text class="r-p-w1b">{{countPoints(calc(goods.unitPrice,flashSale.discount),divider,dividend)}}</text> <text class="r-p-w1"> points by purchasing this product </text>
                        </div>
                    </div>
                </div>
                <div class="pr-c2 r-p-c-l">
                    <text class="r-p-t4">The Benefits of Points</text>
                    <div class="r-p-w r-p-c-l1">
                        <div class="r-p-c-4">
                            <text class="r-p-i iconfont" style="color: #EF8A31" >&#xe74a;</text>
                            <text class="r-p-w1">Join lucky draw to win free product</text>
                        </div>
                        <text class="r-p-t2" @click="jumpLuckDraw">Details</text>
                    </div>
                    <div class="r-p-w r-p-c-2">
                        <div class="r-p-c-4">
                            <text class="r-p-i iconfont" style="color: green;font-size: 20px" >&#xe752;</text>
                            <text class="r-p-w1">Redeem for free gift vouchers</text>
                        </div>
                        <text class="r-p-t2" @click="jumpPerks()">Details</text>
                    </div>

                </div>
                <div class="pr-c3 r-p-c-l">
                    <text class="r-p-t4">Earn Points by Shopping!</text>
                    <div class="r-p-c-3 r-p-m">
                        <image class="r-p-i1" src="bmlocal://assets/dot.png"></image>
                        <bmrichtext class="r-p-c-1">
                            <bmspan class="r-p-t5" value='Points will be added to your account as "'></bmspan>
                            <bmspan class="r-p-t6" value='Pending'></bmspan>
                            <bmspan class="r-p-t5" value='" once you Confirm Receipt of your order.'></bmspan>
                        </bmrichtext>
                    </div>
                    <div class="r-p-c-3 r-p-m-1">
                        <image class="r-p-i1" src="bmlocal://assets/dot.png"></image>
                        <bmrichtext class="r-p-c-1">
                            <bmspan class="r-p-t5" value='The pending points will become “'></bmspan>
                            <bmspan class="r-p-t6" value='Available'></bmspan>
                            <bmspan class="r-p-t5" value='" to use after 9-day return period.'></bmspan>
                        </bmrichtext>
                    </div>
                    <div class="r-p-c-3 r-p-m-1">
                        <image class="r-p-i1" src="bmlocal://assets/dot.png"></image>
                        <text class="r-p-t5">Please note the points will be removed if the order is returned. </text>
                    </div>
                </div>

            </div>
        </wxc-popup>
    </div>
</template>
<script>
    import header from './header';
    import flash from './flash-item';
    import cimg from './customImg';
    import preload from '../common/preloadImg';
    import { WxcCell, WxcButton, WxcPopup, WxcMask } from 'weex-ui'
    import tab from './tab';
    import { baseUrl } from '../../../config/apis';
    import somegoods from './someGoods';

    const dom = weex.requireModule('dom');
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    const common = weex.requireModule('CommonUtils');

    // import block from './block';
    // import refresher from '../common/refresh';
    // import { GOODS } from './config'

    export default {
        components: {
            'topic-header': header,
            'flash': flash,
            WxcCell, WxcButton, WxcPopup, WxcMask,
            'tab': tab,
            'cimg': cimg,
            'somegoods': somegoods,
            preload
            // 'refresher': refresher,
            // 'block': block
        },
        eros: {
          // appeared (a) {
          //     this.proId = a
          // },
          backAppeared (params) {
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
                    unitPrice: '0.00',
                    priceoff: '0.00',
                    cut_get: '',
                    brandLogo: ''
                },
                dropGoods: 0,
                nextPage: {
                   title: '',
                   mainImage: '',
                   salePrice: '',
                   currentPrice: '', // 计算价钱的金额
                   attributes: '',
                   productId: '',
                   quantity: '1',
                   id: '',
                   shippingPrice: '',
                   shippingTimeMin: '',
                   shippingTimeMax: '',
                   proId: '',
                   flashSale: {}
                },
                goodsImg: [],
                tabsItems: [{
                    name: 'Description',
                    key: 'dec'
                }, {
                    name: 'Return Policy',
                    key: 'policy'
                }],
                defaultTab: 'dec',
                goodsVariants: [],
                goodsType: {},
                selsize: '',
                selcolor: '',
                selimgsrc: '',
                selunitPrice: '',
                variantsId: '',
                selsaleUnitPrice: '',
                lowestPrice: '0.00',
                newDescription: [],
                proId: '',
                shipObj: '',
                hasVariants: true,
                canBuy: true,
                cactiveId: 1,
                sactiveId: '',
                isBottomShow: false,
                isCardShow: false,
                isRulerShow: false,
                overlayCardCanClose: true,
                isCardFalse: false,
                hasCardAnimation: true,
                height: 400,
                tabshow: false,
                headerShow: true,
                isDrop: false,
                divider: '',
                dividend: '',
                productStatus: '',
                positionX: 0,
                positionY: 0,
                deltaX: 0,
                deltaY: 0,
                user: null,
                opacity: 0,
                category: '',
                purchaseMethod: '',
                flashSale: '',
                NOW_DATE: '',
                ahour: '',
                amin: '',
                asecond: '',
                tmp: '',
                tmpArray: [],
                returnPolicy: false,
                someGoodsList: [],
                firstHeight: '200px'
            }
        },
        mounted () {
            // setInterval(() => {
            //     this.NOW_DATE = new Date().getTime();
            // }, 1000);
            // this.countDate(this.flashSale.endTime)
        },
        computed: {
            opacity: {
                get: function () {
                    return this.opacity
                },

                set: function (v) {
                    this.opacity = v
                }
            },
            firstHeight: {
                get: function () {
                    return this.firstHeight
                },

                set: function (v) {
                    this.firstHeight = v
                }
            }
        },
        created () {
            this.$notice.loading.show();
            this.$router.getParams().then(resData => {
                this.proId = resData.id
                this.isDrop = resData.isDrop
                this.getGoodsDetail(resData)
                this.getSomeGoods(resData)
                this.getDropGoods()
            })
            if (this.$storage.getSync('user')) {
                this.user = this.$storage.getSync('user')
            } else {
                this.user = null
            }
            this.initBack();
        },
        methods: {
            initBack () {
                common.setAndroidCanBack(true, (params) => {
                    this.popupOverlayBottomClick();
                    common.changeAndroidCanBack(true)
                })
            },
            getGoodsDetail (id) {
                if (id) {
                    // this.$notice.toast({
                    //     message: id
                    // })
                    this.$fetch({
                        method: 'GET',
                        url: `${baseUrl}/product/customer/detail/${id.id}/`,
                        // url: `${baseUrl}/product/customer/detail/654/`,
                        data: {}
                    }).then((res) => {
                        this.purchaseMethod = res.purchaseMethod;
                        if (this.purchaseMethod === 'flash') {
                            this.flashSale = res.flashSale;
                            this.nextPage.flashSale = res.flashSale;
                            if (this.flashSale.flashStatus == 'Ongoing') {
                                // this.goods.priceoff = parseInt((((this.goods.price - this.goods.unitPrice) / this.goods.price)) * 100)
                                this.countDate(this.flashSale.endTime)
                            } else {
                                this.countDate(this.flashSale.startTime)
                            }
                        }
                        // ---- 上面是闪购属性设置 下面是普通属性设置----
                        this.goods.title = res.title;
                        this.goods.price = res.saleUnitPrice;
                        this.goods.unitPrice = res.unitPrice;
                        this.goods.priceoff = parseInt((((this.goods.price - this.goods.unitPrice) / this.goods.price)) * 100)
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
                            this.nextPage.mainImage = this.selimgsrc
                        } else {
                            this.selimgsrc = ''
                        }
                        if (res.attributes != null && res.attributes.length > 0) {
                            if (this.goodsVariants.length == 1) {
                                this.hasVariants = false;
                                this.nextPage.attributes = '';
                                this.goodsType = [];
                                this.canBuy = res.variants[0].isCanBuy;
                                this.variantsId = res.variants[0].id;
                                this.nextPage.id = res.variants[0].id;
                                this.nextPage.salePrice = res.variants[0].saleUnitPrice;
                                this.nextPage.currentPrice = res.variants[0].unitPrice;
                            } else {
                                this.goodsType = res.attributes;
                                this.operateData(res.attributes);
                            }
                        } else {
                            this.hasVariants = false;
                            this.nextPage.attributes = '';
                            this.goodsType = [];
                            this.canBuy = res.variants[0].isCanBuy;
                            this.variantsId = res.variants[0].id;
                            this.nextPage.id = res.variants[0].id;

                            this.nextPage.salePrice = res.variants[0].saleUnitPrice;
                            this.nextPage.currentPrice = res.variants[0].unitPrice;
                            // this.nextPage.mainImage =
                        }
                        this.shipObj = res.shipping
                        this.divider = res.divider
                        this.dividend = res.dividend
                        this.productStatus = res.status
                        this.dectxt = []
                        this.nextPage.title = res.title;
                        this.nextPage.productId = id.id;
                        this.nextPage.shippingPrice = res.shipping.priceItem;
                        this.nextPage.shippingTimeMin = res.shipping.shippingTimeMin;
                        this.nextPage.shippingTimeMax = res.shipping.shippingTimeMax;
                        this.isDrop = this.purchaseMethod == 'drop';
                        this.nextPage.proId = this.purchaseMethod;
                        // nextPage 传给下一页组织的数据

                        if (res.newDescription != null) {
                            this.newDescription = res.newDescription
                        }
                        if (res.categories && res.categories.length > 0) {
                            this.category = res.categories[0].name;
                        }
                        this.$notice.loading.hide();
                        googleAnalytics.trackingScreen(`Product Detail/${this.category}`);
                        googleAnalytics.recordEvent('ProductViewCategory', this.purchaseMethod, this.category, 0);
                        googleAnalytics.facebookRecordEvent('fb_mobile_content_view', id.id, this.category, 'Rs', this.lowestPrice)
                    }).catch((res) => {
                        this.$notice.loading.hide();
                        this.$notice.toast({
                            message: res
                        });
                    })
                }
            },
            getSomeGoods (id) {
                this.$fetch({
                    methods: 'GET',
                    url: `${baseUrl}/product/relations/recommend/list/`,
                    // url: `${baseUrl}/product/customer/detail/654/`,
                    data: {
                        id: id.id
                    }
                }).then((res) => {
                    // this.someGoodsList = res
                    // this.$notice.alert({
                    //     message: res.length
                    // })
                    let arr = [];
                    for (let i = 0; i < res.length; i++) {
                        const item = res[i];
                        arr.push(item);
                        if ((i > 0 && i % 2 === 1) || i === res.length - 1) {
                            this.someGoodsList.push(arr);
                            arr = [];
                        }
                    }
                    // this.$notice.alert({
                    //     message: this.someGoodsList
                    // })
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            getDropGoods () {
                this.$fetch({
                    method: 'GET',
                    name: 'promotion.cut.list',
                    data: {
                        page: 1,
                        page_size: 999,
                        status: 'progressing'
                    },
                    header: {
                        needAuth: true,
                        isLoginPop: true
                    }

                }).then(data => {
                    this.dropGoods = data.count
                   // this.$notice.toast({
                   //     message: data.count
                   // })
                }, error => {})
            },
            createCut () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'POST',
                    url: `${baseUrl}/promotion/cut/down/create/`,
                    data: {
                        variant_id: this.variantsId,
                        version: 1
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.$event.emit('createCut');
                    if (res.cutId) {
                        this.$router.open({
                            name: 'drops.cutDetail',
                            type: 'PUSH',
                            params: {
                                isShowSharePanel: false,
                                id: res.cutId
                            }
                        })
                    } else {
                        this.$router.open({
                            name: 'drops.cutDetail',
                            type: 'PUSH',
                            params: {
                                isShowSharePanel: false,
                                id: res.id
                            }
                        })
                        this.dropGoods += 1
                        googleAnalytics.recordEvent('DropStart', 'Invite Friends to Drop Price', this.category + '-' + this.variantsId, 0);
                    }
                    this.$notice.loading.hide();
                }).catch((res) => {
                    if (res.status == 409) {
                        this.$notice.loading.hide();
                        // if (res.errorMsg == 'You are bargaining for this item, you cannot add it repeatedly') {
                        //     this.$event.emit('jumpMyDrop');
                        //     this.$router.setBackParams({ tab: 'drops' })
                        //     this.$router.back({
                        //         length: 9999,
                        //         type: 'PUSH'
                        //     })
                        // }
                        this.$notice.toast({
                            message: res.errorMsg
                        })
                    }
                })
            },
            wxcCellClick () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.isBottomShow = true;
                    common.changeAndroidCanBack(false)

                    if (this.variantsId != '') {
                        if (this.isDrop == true) {
                            if (!this.checkedSelected()) {
                                return;
                            }
                            this.createCut()
                        } else {
                            if (!this.checkedSelected()) {
                                return;
                            }
                            if (this.flashSale.flashStatus === 'Ongoing') {
                                this.nextPage.currentPrice = ((this.selunitPrice * this.flashSale.discount) / 100).toFixed(2)
                            }
                            if (this.nextPage.proId == 'flash' && this.flashSale.flashStatus !== 'Ongoing') {
                                this.nextPage.proId = 'direct'
                            }
                            this.$router.open({
                                name: 'order.confirm',
                                type: 'PUSH',
                                params: this.nextPage
                            })
                        }
                    }
                }
            },
            confirm () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.isBottomShow = true;
                    if (!this.checkedSelected()) {
                        return;
                    }
                    for (let i = 0; i < this.goodsVariants.length; i++) {
                        let isDoubleChecked = 0;
                        for (let j = 0; j < this.goodsVariants[i].attributeValues.length; j++) {
                            for (let m = 0; m < this.tmpArray.length; m++) {
                                if (this.tmpArray[m].id == this.goodsVariants[i].attributeValues[j].attributeId &&
                                    this.tmpArray[m].value == this.goodsVariants[i].attributeValues[j].value) {
                                    isDoubleChecked += 1;
                                }
                            }
                        }
                        if (isDoubleChecked == this.goodsType.length) {
                            this.variantsId = this.goodsVariants[i].id;
                            this.selsaleUnitPrice = this.goodsVariants[i].saleUnitPrice
                            this.selunitPrice = this.goodsVariants[i].unitPrice

                            this.nextPage.salePrice = this.selsaleUnitPrice;
                            this.nextPage.currentPrice = this.selunitPrice;
                            break;
                        }
                    }
                    this.nextPage.id = this.variantsId;
                        if (this.isDrop == true) {
                            this.createCut()
                        } else {
                            if (this.flashSale.flashStatus === 'Ongoing') {
                                this.nextPage.currentPrice = ((this.selunitPrice * this.flashSale.discount) / 100).toFixed(2)
                            }
                            if (this.nextPage.proId == 'flash' && this.flashSale.flashStatus !== 'Ongoing') {
                                this.nextPage.proId = 'direct'
                            }
                            this.$router.open({
                                name: 'order.confirm',
                                type: 'PUSH',
                                params: this.nextPage
                            })
                        }

                    // } else {
                    //     this.$notice.toast({
                    //         message: 'Please select an option first.'
                    //     })
                    // }
                }
            },
            checkedSelected () {
                this.tmpArray = [];
                for (let i = 0; i < this.goodsType.length; i++) {
                    for (let j = 0; j < this.goodsType[i].value.length; j++) {
                        if (this.goodsType[i].value[j].isActive == true) {
                            this.tmpArray.push(this.goodsType[i].value[j])
                            break;
                        }
                        if (j == this.goodsType[i].value.length - 1) {
                            this.$notice.toast({
                                message: 'Please select a ' + this.goodsType[i].name.toLowerCase() + '!'
                            });
                            return false
                        }
                    }
                }
                return true;
            },
            redirectLogin () {
                this.$event.on('login', params => {
                    this.getGoodsDetail(this.proId)
                    this.getSomeGoods(this.proId)
                    this.$storage.get('user').then(resData => {
                        this.user = resData
                    })
                });
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            },
            openCutPrice () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    if (this.hasVariants === false) {
                        this.createCut()
                    } else {
                        this.isBottomShow = true;
                        common.changeAndroidCanBack(false)
                    }
                }
            },
            openBuyNow () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    if (this.hasVariants === false) {
                        if (this.variantsId != '' && this.isDrop == false) {
                            if (this.flashSale.flashStatus === 'Ongoing') {
                                this.nextPage.currentPrice = (((this.selunitPrice || this.goods.unitPrice) * this.flashSale.discount) / 100).toFixed(2);
                            }
                            if (this.nextPage.proId == 'flash' && this.flashSale.flashStatus !== 'Ongoing') {
                                this.nextPage.proId = 'direct'
                            }
                            this.$router.open({
                                name: 'order.confirm',
                                type: 'PUSH',
                                params: this.nextPage
                            })
                        }
                    } else {
                        this.isBottomShow = true;
                        common.changeAndroidCanBack(false)
                    }
                }
            },

            // 非状态组件，需要在这里关闭
            popupOverlayBottomClick () {
                this.isBottomShow = false;
                this.isCardShow = false;
                this.isRulerShow = false;
            },

            popupCloseClick () {
                // this.$refs.wxcPopup.hide();
                this.isBottomShow = false;
                common.changeAndroidCanBack(true)
            },

            popupRulerClick () {
                // this.$refs.wxcPopup.hide();
                this.isRulerShow = false;
                common.changeAndroidCanBack(true)
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
                const color = []; // 点选的这个 有其他的颜色或者规格
                const discolor = [];
                for (let j = 0; j < this.goodsVariants.length; j++) {
                    for (let k = 0; k < this.goodsVariants[j].attributeValues.length; k++) {
                        if (item.value == this.goodsVariants[j].attributeValues[k].value) {
                            color.push({
                                item: this.goodsVariants[j],
                                index: k
                            });
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
                                this.goodsType[p].value[u].seldisable = true
                                for (let o = 0; o < discolor.length; o++) {
                                    if (this.goodsType[p].value[u].value == discolor[o]) {
                                        this.goodsType[p].value[u].seldisable = false
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
                // this.$notice.alert({
                //     message: color
                // })

                this.changeDom(item, color)
                // this.$notice.toast({
                //     message: item
                // })
                // this.cactiveId = id
            },
            changeDom (item, color) {
                if (color.length !== 0) {
                    this.selsaleUnitPrice = color[0].item.saleUnitPrice
                    this.selunitPrice = color[0].item.unitPrice
                    this.variantsId = color[0].item.id
                    for (let n = 0; n < this.goodsVariants.length; n++) {
                        if (this.goodsVariants[n].id === color[0].item.id) {
                            this.canBuy = this.goodsVariants[n].isCanBuy
                        }
                    }
                } else {
                    this.$notice.toast({
                        message: 'Please select an option first.'
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
                    if (this.goodsType[i].name == 'Color') {
                        tmp = this.goodsType[i].images
                    }
                }
                for (let j = 0; j < tmp.length; j++) {
                    if (tmp[j].value == this.selcolor) {
                        this.selimgsrc = tmp[j].image
                    }
                }
                this.nextPage.attributes = this.selcolor + ' ' + this.selsize;
                this.nextPage.mainImage = this.selimgsrc;
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
                            time: this.shipObj,
                            drop: this.isDrop
                        }
                    })
                }
            },
            openLink () {
                if (this.user == null) {
                    this.redirectLogin()
                    return
                }
                this.$event.emit('jumpMyDrop');
                this.$router.setBackParams({ tab: 'drops' })
                this.$router.back({
                    length: 9999,
                    type: 'PUSH'
                })
            },
            jumpLuckDraw () {
                this.$router.setBackParams({ tab: 'luckydraw' })
                this.$router.back({
                    length: 9999,
                    type: 'PUSH'
                })
            },
            jumpPerks () {
                this.$router.setBackParams({ tab: 'rewards' })
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
                    this.tabshow = true
                } else if (Math.abs(e.contentOffset.y) < 1100) {
                    this.tabshow = false
                }
                if (e.contentSize.height + e.contentOffset.y < 1350) {
                    this.defaultTab = 'policy'
                } else {
                    this.defaultTab = 'dec'
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
            opendec () {
                this.$router.open({
                    name: 'goods.dec',
                    type: 'PUSH',
                    params: {
                        dec: this.newDescription
                    }
                })
            },
            openRuler () {
                this.isRulerShow = true;
                common.changeAndroidCanBack(false);
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.floor((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            countPrice (s, o) {
                if (o > 0) {
                    return Math.floor(s * (o / 100)) + '.00'
                } else {
                    return ''
                }
            },
            countPoints (p, a, b) {
                return (Math.floor(parseInt(p) / a)) * b;
            },
            calc (a, b) {
                return ((a * b) / 100).toFixed(2)
            },

            countDate (time) {
                const self = this
                // if (this.purchaseMethod == 'flash') {
                setInterval(() => {
                    this.NOW_DATE = new Date().getTime();

                    const total = (new Date(time).getTime() - this.NOW_DATE) / 1000
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
                    //     message: self.ahour + ':' + self.amin + ':' + self.asecond
                    // })
                }, 1000);
                // }
            },

            policyShow () {
                this.returnPolicy = !this.returnPolicy;
                if (!this.returnPolicy) {
                    this.firstHeight = '200px';
                } else {
                    this.firstHeight = 'auto';
                }
            }
        }
    }
</script>
<style scoped>
    .wrapper{
        background-color: white;
    }
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
    .iiileft-div{
        padding: 50px;
        overflow: hidden;
        position: absolute;
        top:30px;
        left:-10px;
    }
    .iiileft{
        font-family: iconfont;
        color: white;
        font-size: 32px;
        background-color: rgba(0,0,0,.38);
        border-radius:24px ;
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
    .red-dot{
        width: 20px;
        height: 20px;
        background-color: red;
        position: absolute;
        border-radius:24px ;
        top:75px;
        right:38px;
       flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .onetitle{
        width:586px;
        /*height: 120px;*/
        font-weight: 700;
        margin-top: 32px;
        margin-bottom: 32px;
        margin-left: 32px;
        font-family: PingFangSC-Light;
        font-size: 28px;
        color: #000;
        line-height: 48px;
        letter-spacing: 0;
    }
    .price-name{
        color: rgba(0,0,0,1);
        margin-left: 32px;
        margin-bottom: 26px;
        font-size:24px;
    }
    .price-off{
        border-width: 1px;
        border-style: solid ;
        border-color: #00CFE3;
        font-size: 20px;
        border-radius: 8px;
        padding: 4px 16px;
        color: #00CFE3;
    }
    .price-price{
        border-width: 1px;
        border-style: solid ;
        border-color: #EF8A31;
        font-size: 20px;
        border-radius: 8px;
        padding: 4px 16px;
        color: #EF8A31;
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
        border-bottom-width: 2px;
        border-bottom-color: rgba(0,0,0,0.08);
        border-bottom-style: solid ;
    }
    .slg{
        margin-top: 48px;
        align-items:center ;
    }
    .i-slg-icon{
        font-family: iconfont;
        opacity: 0.54;
        text-align: center;
        font-size: 60px;
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
    .dec-word{
        width: 750px;
        height: 96px;
        padding-left: 38px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        border-bottom-color: rgba(0,0,0,0.12) ;
        border-bottom-width: 1px ;
        border-bottom-style: solid ;
        background-color: white;
    }
    .dec{
        font-size: 24px;
        font-weight:700;
    }
    .dec-arrows{
        opacity: 0.54;
        color: #333333;
        padding-right: 38px;
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
        /*box-shadow: 0 1px 1px 0;*/
        background-color: #fff;
        padding-top: 16px;
        /*margin-bottom: 300px;*/
        border: 0;
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
    .button-gray{
        color: #fff;
        height: 80px;
        background-color: rgba(0,0,0,0.48);
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
        font-size: 28px;
        font-weight: 600;
        margin-top: 75px;
        margin-left: 32px;
        text-decoration: line-through;
    }
    .popup-lowprice-word{
        font-size: 24px;
        margin-top: 16px;
        margin-left: 32px;
        margin-bottom: 4px;

    }
    .popup-lowprice{
        font-size: 32px;
        font-weight: 600;
        color: #EF8A31;
        margin-left: 32px;

    }
    .popup-yet{
        font-size: 24px;
        opacity: 0.54;
        margin-top: 16px;
        margin-right: 12px;
    }
    .popup-yet-bg{
        padding: 0 32px;
        flex-direction: row;
    }
    .popup-close{
        font-family: iconfont;
        font-size: 32px;
        position: absolute;
        right: 0;
        top: 0;
        padding-right:32px ;
        padding-top: 32px;
        padding-left: 32px;
        padding-bottom: 32px;
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
    .reward-points{
        flex-direction: column;
        justify-content: space-between;
        margin: 0 32px;
        border-top-style:dashed ;
        border-top-color: grey;
        border-top-width:1px ;
    }
    .r-icon{
        font-size: 40px;
    }
    .r-p-t{
        flex-direction: row;
        justify-content: space-between;
        margin-top: 24px;
    }
    .r-p-t1{
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        font-weight: 700;
    }
    .r-p-t2{
        font-size: 24px;
        color: #00CFE3;
        letter-spacing: 0;
        text-align: center;
    }
    .r-p-t3{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .r-p-t4{
        font-weight: 700;
        font-size: 28px;
        line-height: 34px;
        font-family: ProximaNova;
    }
    .r-p-c{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 32px;
        padding-bottom: 32px;
    }
    .r-p-c-1{
        font-size: 24px;
        line-height: 36px;
        lines: 2;
        width: 646px;
    }
    .r-p-c-2{
        padding: 0 0 32px;
    }
    .r-p-c-3{
        flex-direction: row;
        justify-content: start;
        align-items: start;
    }
    .r-p-c-4{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .r-p-c-l{
        border-top-style: dashed;
        border-top-color: grey;
        border-top-width: 1px;
        padding-top: 32px;
    }
    .r-p-c-l1{
        padding: 24px 0 32px;
    }
    .r-p-i{
        width:28px;
        height:28px;
        margin-right: 16px;
        font-size: 28px;
    }
    .r-p-i1{
        margin-top: 8px;
        width: 20px;
        height: 20px;
        margin-right: 24px;
    }
    .r-p-w{
        flex-direction: row;
        justify-content: space-between;
    }
    .r-p-w1{
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
    }
    .r-p-w1b{
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        font-weight: 700;
        margin: 0 10px;
    }
    .r-p-t5{
        font-family: ProximaNova;
        font-size: 24px;
        color: #000000;
        line-height: 36px;
        width: 646px;
        lines: 2;
    }
    .r-p-t6{
        font-family: ProximaNova;
        font-size: 24px;
        color: #000000;
        line-height: 36px;
        font-weight: bold;
    }
    .r-p-m{
        margin-top: 24px;
    }
    .r-p-m-1{
        margin-top: 16px;
    }
    .dec-see-more{
        flex-direction: row;
        justify-content: center;
        background-color: white;
        margin-bottom: 16px;
    }
    .dec-see-return{
        flex-direction: row;
        justify-content: center;
        background-color: white;
        margin-bottom: 11px;
    }
    .d-sm-w{
        font-size: 24px;
        color: #00CFE3;
        letter-spacing: 0;
        margin-top: 16px;
        padding: 30px  290px;
        border-top-style:dashed ;
        border-top-color: grey;
        border-top-width:1px ;
    }
    .popup-ruler-content{
        padding: 48px 32px;
        width: 750px;
        height: 770px;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
        background-color: #fff;
    }
    .bottom-goods{
        background-color: white;
        margin-bottom: 120px;
    }
</style>
