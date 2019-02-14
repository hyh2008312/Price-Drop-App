<template>

    <div class="wrapper" >
        <topic-header ref="ref1" :style="{opacity:opacity}" @open="openMyCart" :leftBtn="1" :rightBtn="1" :dropGoods="cartNum"></topic-header>
        <div class="blackheader"></div>

        <scroller class="main-list" @scroll="scrollHandler"  offset-accuracy="10px">

            <div style="background-color: white" >

                <slider class="slider" interval="8000" auto-play="true" :index="2" >
                    <div class="frame" v-for="(img, idx) in goodsImg">
                        <preload class="image" :src="img"></preload>
                        <text style="right:20px;bottom:20px;color:black;position:absolute">{{idx+1}}/{{goodsImg.length}}</text>
                    </div>
                </slider>
                <div class="iiileft-div" @click="$router.back">
                    <text class="iiileft">&#xe6f6;</text>
                </div>
                <text class="iiiright-s"  @click="jumpSearch">&#xe621;</text>
                <text class="iiiright"  @click="openMyCart">&#xe767;</text>
                <div class="red-dot" ><text style="color: white;font-size: 16px">{{cartNum}}</text></div>

                <flash v-if="purchaseMethod==='flash'"
                       :hour="ahour"
                       :min="amin"
                       :second="asecond"
                       :fstatus="flashSale.flashStatus"
                       :saleUnitPrice="goods.price"
                       :unitPrice="goods.unitPrice"
                       :discount="flashSale.discount"
                ></flash>
                <!--<drop  v-if="purchaseMethod==='drop'"-->
                       <!--:saleUnitPrice="goods.unitPrice"-->
                       <!--:lowestPrice="lowestPrice"-->
                <!--&gt;</drop>-->

                <text class="onetitle"  @click="openReviews">{{goods.title}}</text>
                <!--<div class="count-div"  v-if="purchaseMethod === 'drop'">-->
                    <!--<div >-->
                        <!--<text class="drop-title">{{goods.title}}</text>-->
                        <!--<div class="drop-label">-->
                            <!--<text class="price-name price-off-fr">Free Shipping</text>-->
                            <!--<text class="price-name price-off-fr">Imported</text>-->
                            <!--<text class="price-name-16 price-off" v-if="!(purchaseMethod==='drop')">Tax Included</text>-->
                        <!--</div>-->
                    <!--</div>-->
                <!--</div>-->

                <div class="count-div">
                    <!--<text class="price-name" v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')" >Original Price: </text>-->
                    <!--注释为老版-->
                    <text class="price-name-1"  v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')" >₹{{parseInt(goods.unitPrice)}} </text>
                    <text class="price" v-if="((purchaseMethod==='flash'&&flashSale.flashStatus=='Scheduled')||purchaseMethod==='direct')" >₹{{parseInt(goods.price)}}</text>
                    <text class="price-name price-price" v-if="purchaseMethod==='direct'" >{{countOff(goods.unitPrice, goods.price)}}</text>
                    <text class="price-name price-price" v-if="flashSale.flashStatus=='Scheduled'" >{{countOff(goods.unitPrice, goods.price)}}</text>
                    <text class="price-name price-price" v-if="flashSale.flashStatus=='Ongoing'" >{{countOff(countPrice(goods.unitPrice, flashSale.discount), goods.price)}}</text>
                    <!--<text>{{goods.unitPrice}}&#45;&#45;{{flashSale.discount}}-&#45;&#45;{{goods.price}}</text>-->
                    <!--<text class="price-name-16 price-off-tax" v-if="!(purchaseMethod==='drop')" @click="openReviews1">Tax Included</text>-->
                </div>
                <div class="count-div">
                    <!--<text class=" price-name" >You Save:</text><text class="price">{{}}%</text>-->
                </div>

                <div class="ship-item" @click="openShipPopup" v-if="purchaseMethod==='direct'||purchaseMethod==='flash'">
                    <div class="s-i-1">
                        <div class="s-i-2">
                            <image class="s-i-img" src="bmlocal://assets/express-01.png"></image>
                            <text class="s-i-t">Estimated Arrival: {{getNowDay1(shipObj.shippingTimeMin)}} - {{getNowDay1(shipObj.shippingTimeMax)}}</text>
                        </div>
                        <text class="s-i-t1" >Details</text>
                    </div>
                    <div class="ship-label">
                        <text class="s-i-icon">Free Shipping</text>
                        <text class="s-i-icon-imp">Imported</text>
                    </div>

                </div>

                <div class="ship-item mg-b36" @click="openCODPopup" v-if="purchaseMethod==='direct'||purchaseMethod==='flash'">
                    <div class="s-i-1">
                        <div class="s-i-2">
                            <image class="s-i-img" src="bmlocal://assets/COD-01.png"></image>
                            <text class="s-i-t">Cash/Card on Delivery availability check</text>
                        </div>
                        <text class="s-i-t1" >Check</text>
                    </div>
                    <!--<text class="s-i-icon">Free Shipping</text>-->
                </div>
            </div>

            <div class="reward-points"  @click="openRuler">
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
            <!--<div class="learn-drop"  v-if="isDrop">-->
                <!--<div class="learn-drop-itme1">-->
                    <!--<text class="learn-drop-itmeh">Invite NEW Friends to Unlock Special Discount!</text>-->
                <!--</div>-->
                <!--<div class="learn-drop-itme2">-->
                    <!--<div class="progress">-->
                        <!--<text class="progress-word">5 new referrals</text>-->
                        <!--<div class="progress-numdiv"><text  class="progress-num"></text></div>-->
                        <!--<text class="progress-word1">50% OFF</text>-->
                    <!--</div>-->
                    <!--<div class="progress">-->
                        <!--<text class="progress-word">10 new referrals</text>-->
                        <!--<div class="progress-numdiv"><text  class="progress-num"></text></div>-->
                        <!--<text class="progress-word1">70% OFF</text>-->
                    <!--</div>-->
                    <!--<div class="progress">-->
                        <!--<text class="progress-word" style="color: #492799">13 new referrals</text>-->
                        <!--<div class="progress-numdiv"><text  class="progress-num-right"></text></div>-->
                        <!--<text class="progress-word1"  style="color: #492799" >75% OFF</text>-->
                        <!--&lt;!&ndash;<text class="progress-word">Lowest Price</text>&ndash;&gt;-->
                    <!--</div>-->
                <!--</div>-->
                <!--<div class="learn-drop-line">-->
                    <!--<div class="progress-line-left-last"></div>-->
                    <!--<div class="progress-line-left"></div>-->
                    <!--<div class="progress-line-right"></div>-->
                    <!--<div class="progress-line-right-last"></div>-->
                <!--</div>-->
                <!--<text class="l-d-lp">Lowest Price</text>-->
                    <!--<div class="learn-see-more" @click="openShip(1)">-->
                        <!--<text class="d-sm-w">SEE MORE</text>-->
                    <!--</div>-->
                 <!--</div>-->

                 <!--<div v-if="tabshow" style="position: sticky">-->
                    <!--<tab   @tabTo="onTabTo" :items="tabsItems" :indexKey="defaultTab"></tab>-->
                 <!--</div>-->
            <div class="create-drop" v-if="isDrop" @click="createDrop" >
                <div class="c-d-item1"  >
                    <text class="cd-item1-word">Drop & Save More</text>
                    <text class="">&nbsp;&nbsp;&nbsp;&nbsp;</text>
                </div>
                <div class="c-d-item2">
                    <text class="c-d-word">Start a drop to get extra discount!</text>
                    <div class="cd-btn">
                        <text class="cd-btn1">GO</text>
                        <text class="iconfont cd-btn2" ref="getArrow">&#xe626;</text>
                    </div>
                </div>
            </div>

            <div class="mid">
                <div class="dec-word" @click="wxcCellClick" v-if="hasVariants === true"  >
                    <text class="dec">Size / Color</text>
                    <text class="s-c-s">Select</text>

                </div>
                <div class="dec-word" @click="openGoodsSize"  v-if="goods.productSize!== '' && goods.productSize != undefined">
                    <text class="dec">Size Chart</text>
                    <text class="s-c-s">Check</text>
                </div>
            </div>



            <!--<div class="mid">-->
                <!--<div class="dec-word" >-->
                    <!--<text class="dec">Reviews</text>-->
                    <!--<div class="review-star">-->
                        <!--<star :points="points"></star>-->
                        <!--<text>3.7</text>-->
                        <!--<text>|</text>-->
                        <!--<text>85 Rating</text>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->

            <div class="mid">

                <div class="dec-word" @click="openShipPopup"  >
                    <text class="dec">Shipping Time & Cost</text>
                    <text class="iconfont dec-arrows">&#xe626;</text>
                </div>

                <div class="slogan" >
                    <div class="slg">
                        <text class="i-slg-icon">&#xe714;</text>
                        <text class="i-slg-1" > Quality  </text>
                        <text class="i-slg-2" > Guaranteed </text>
                    </div>

                    <div class="slg">
                        <text class="i-slg-icon">&#xe75d;</text>
                        <text class="i-slg-1" > Direct From </text>
                        <text class="i-slg-2"  > Manufacturers </text>
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
            <div class="mid">
                <div class="dec-word" ref="dec" >
                    <text class="dec">Description</text>
                </div>
            </div>


            <div @click="opendec" style="width: 750px; background-color: white ; padding-top: 20px;height: 568px">
                <div   v-for="(i, index) in trimNullObj(goods.productSpecification)" >
                    <div class="p-s"  :class="[index%2==0?'rowGray':'rowWithe']">

                        <text class="p-s-t1">{{i.name}}</text>
                        <text class="p-s-t2">{{i.content}}</text>
                    </div>

                    <!--goods.productSpecification-->
                </div>
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

        </scroller>

        <div class="cart-tip" v-if="cartTipShow">
            <image class="ct-img" src="bmlocal://assets/tips.png"></image>
            <text class="ct-word">Successfully</text>
            <text class="ct-word1">Added to Cart!</text>
        </div>
        <div class="bottom-btn" v-if="purchaseMethod === 'drop'" >
            <div v-if="productStatus==='unpublished'">
                <text class="button-gray"  >Unavailable</text>
            </div>
            <div v-if="productStatus==='published'">
                <text class="button-purple" @click="openCutPrice" v-if="canBuy">Invite Friends to Drop Price</text>
                <text class="button-gray"  v-if="!canBuy">Out of Stock</text>
            </div>
        </div>

        <div class="bottom-btn" v-if="purchaseMethod !== 'drop'">
            <div v-if="productStatus==='unpublished'">
                <text class="button-gray"  >Unavailable</text>
            </div>

            <div v-if="productStatus==='published'">
                <div class="over-flow-cart" >
                    <text class="a-t-c" @click="addCart" v-if="!cartWordStu">Add to Cart</text>
                    <text class="a-t-c" @click="openMyCart" v-if="cartWordStu">Go to Cart</text>
                    <text class="b-n" @click="openBuyNow">Buy Now</text>
                </div>

            </div>
        </div>

        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgb(255, 255, 255)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupCloseClick"
                   pos="bottom"
                   ref="wxcPopup"
                   height="870">
            <div class="popup-content">
                <div class="popup-top">
                    <image :src="selimgsrc"
                           class="popup-image"></image>

                    <div class="popup-py">
                        <text class="popup-price" >₹{{parseInt(selsaleUnitPrice)}}</text>
                        <text class="popup-lowprice-word" >Exclusive Price: </text>

                        <text class="popup-lowprice"      v-if="flashSale.flashStatus=='Ongoing'">₹{{ parseInt(selunitPrice*(flashSale.discount/100)) || parseInt(goods.unitPrice*(flashSale.discount/100))}}</text>
                        <text class="popup-lowprice"      v-if="flashSale.flashStatus=='Scheduled'||purchaseMethod==='direct'">₹{{parseInt(selunitPrice)||parseInt(goods.unitPrice)}}</text>

                        <div class="popup-yet-bg" v-if="hasVariants==true">
                            <text class="popup-yet">Chose:</text>
                            <text class="popup-yet" v-if="selcolor != ''">{{selcolor}}</text>
                            <text class="popup-yet" v-if="selsize != ''">{{selsize}}</text>
                        </div>
                    </div>
                    <text class="popup-close" @click="popupCloseClick">&#xe632;</text>
                </div>
                <scroller  class="scroller" show-scrollbar="true" v-if="hasVariants">
                    <div class="popup-bxottom">
                        <div v-for="(val, index) in goodsType" :key="index" >
                            <text class="popup-color" v-if="val.name=='Color'">{{goods.aliasColor || val.name}}</text>
                            <div class="popup-size-item" v-if="val.name=='Size'">
                                <text class="popup-color">{{goods.aliasSize || val.name}}</text>
                                <text class="popup-size-chart" @click="openGoodsSize"  v-if="goods.productSize!== '' && goods.productSize != undefined">Size Chart ></text>
                            </div>

                            <div style="width: 718px;flex-wrap: wrap;flex-direction: row;justify-content: start;align-items: center">

                                <div  class="popup-color-chd" v-for="(val1, key1) in val.value" >
                                    <text class="popup-color-chdname"
                                          :key="key1"
                                          :class="[val1.isActive ?'popup-color-chdname-active':'',
                                          val1.seldisable ?'popup-color-chdname-disable':'']"
                                          @click="clickColor(val1, val.value)">{{val1.value}}</text>
                                </div>
                            </div>
                                <!--<div  class="popup-color-chd"  v-for="(val1, key1) in tranArr(val.value)" :key="key1">-->
                                    <!--<text class="popup-color-chdname"-->
                                          <!--v-for="(i,key2) in val1"-->
                                          <!--:key="key2"-->
                                          <!--:class="[i.isActive ?'popup-color-chdname-active':'',i.seldisable ?'popup-color-chdname-disable':'']"-->
                                          <!--@click="clickColor(i, val.value)">{{i.value}}</text>-->
                                <!--</div>-->
                        </div>
                    </div>
                </scroller>
                <div class="popup-quantity" v-if="!isDrop">
                    <text class="popup-qt">Quantity</text>
                    <div class="lc-b">
                        <text class="lc-b-1" @click="delQuantity">-</text>
                        <text class="lc-b-n">{{selquantity}}</text>
                        <text class="lc-b-2" @click="addQuantity">+</text>
                    </div>
                </div>

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

        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgba(255, 255, 255, 0)"
                   :show="isShipShow"
                   @wxcPopupOverlayClicked="popupOverlayBottomClick"
                   pos="bottom"
                   height="414">
            <div class="popup-ship-content">
                <div style="margin: 48px 32px">

                    <div class="pShip-item">
                        <text class="s-item-b">Buy Now to Enjoy Free Shipping!</text>
                        <text class="s-item-i iconfont" @click="popupOverlayBottomClick">&#xe632;</text>
                    </div>
                    <div class="pShip-item1">
                        <text class="s-item-t">Shipping Cost:</text>
                        <text class="s-item-t1">₹ 0 </text>
                        <text class="s-item-t2">₹ 100</text>
                    </div>
                    <!--<div class="pShip-item1 mg-b44">-->
                        <!--<text class="s-item-t">Shipping Carrier:</text>-->
                        <!--<text class="s-item-t1">{{shipObj.shippingName}}</text>-->
                    <!--</div>-->
                    <div class="pShip-item1">
                        <text class="s-item-t">Order Processing Before Shipping:</text>
                        <text class="s-item-t1">5 - 7 days</text>
                    </div>
                    <div class="pShip-item1">
                        <text class="s-item-t">Shipping Time After Processing:</text>
                        <text class="s-item-t1">{{shipObj.shippingTimeMin}} - {{shipObj.shippingTimeMax}} days</text>
                    </div>
                    <div class="pShip-item1 mg-b32">
                    <text class="s-item-t">Estimated Arrival Date:</text>
                    <text class="s-item-t1"> {{getNowDay1(shipObj.shippingTimeMin)}} - {{getNowDay1(shipObj.shippingTimeMax)}}</text>
                </div>
                </div>

            </div>
        </wxc-popup>

        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgba(255, 255, 255, 1)"
                   :show="isCODShow"
                   @wxcPopupOverlayClicked="popupOverlayBottomClick"
                   pos="bottom"
                   :height="sHeight">
            <div class="popup-COD-content">
                <div class="cod-head">
                    <div class="cod-head-1">
                        <image class="c-h-i" src="bmlocal://assets/check-01.png"></image>
                        <text class="c-h-t"> Enter Pincode to check COD availability</text>
                    </div>
                    <text class="s-item-i iconfont" @click="popupOverlayBottomClick">&#xe632;</text>
                </div>
                <div class="cod-item">
                    <div class="cod-item-d">
                        <input class="cod-input" placeholder="Enter Pincode" type="number" :value="pinCode" @input="oninput">
                        <text v-if="!pinCodeLoad" class="c-i-t" @click="checkPinCode">Check</text>
                        <div class="c-i-load" v-if="pinCodeLoad">
                            <image style="width: 50px;height: 50px" src="bmlocal://assets/loading-white.gif"></image>
                        </div>
                    </div>
                    <text class="c-it-err"  v-if="pinCodeStatus==2">COD is not available for this area now. But we will support it later!</text>
                    <text class="c-it-err1" v-if="pinCodeStatus==1">COD is available for your area!</text>
                    <text class="c-it-err"  v-if="pinCodeStatus==3">Please enter your Pincode!</text>
                    <text class="c-it-err"  v-if="pinCodeStatus==0">&nbsp;</text>
                </div>
                <div class="cod-item1">
                    <image class="c-h-i" src="bmlocal://assets/rules-01.png"></image>
                    <text class="c-h-t">Read Our COD Policy</text>
                </div>
                <div class="c-it1-d">
                    <text class="c-it1-t">1. COD service is only available for certain locations now. We will expand to more locations very soon.</text>
                    <text class="c-it1-t">2. If you place a COD order, you cannot cancel or reject the package after it ships out.</text>
                    <text class="c-it1-t">3. If you refuse your COD package, you would be blocked by the carrier and cannot use COD service any more. </text>
                    <text class="c-it1-t">4. You can only place your next COD order after you complete your last COD order successfully.</text>
                    <text class="c-it1-t">5. COD delivery is currently not available for order above Rs.1500. </text>
                </div>
            </div>
        </wxc-popup>


    </div>
</template>
<script>
    import header from './header';
    import flash from './flash-item';
    import drop from './drop-item';
    import cimg from './customImg';
    import preload from '../common/preloadImg';
    import { WxcCell, WxcButton, WxcPopup, WxcMask, Utils } from 'weex-ui'
    import tab from './tab';
    import { baseUrl , dataUrl } from '../../../config/apis';
    import somegoods from './someGoods';
    import dayjs from 'dayjs';
    import star from './star-item';

    const dom = weex.requireModule('dom');
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    const animation = weex.requireModule('animation')

    const common = weex.requireModule('CommonUtils');

    // import block from './block';
    // import refresher from '../common/refresh';
    // import { GOODS } from './config'

    export default {
        components: {
            'topic-header': header,
            'flash': flash,
            'drop': drop,
            WxcCell, WxcButton, WxcPopup, WxcMask,
            'tab': tab,
            'cimg': cimg,
            'somegoods': somegoods,
            'star': star,
            preload
            // 'refresher': refresher,
            // 'block': block
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
                    cut_get: '',
                    brandLogo: '',
                    productSpecification: [],
                    productSize: '',
                    aliasSize: '',
                    aliasColor: ''
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
                selquantity: 1,
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
                isShipShow: false,
                isCODShow: false,
                overlayCardCanClose: true,
                isCardFalse: false,
                hasCardAnimation: true,
                height: 400,
                sHeight: '',
                tabshow: false,
                headerShow: true,
                isDrop: false,
                dropStatus: 1, // 1, 正常购买  2, 砍价
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
                categoryId: '',
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
                firstHeight: '200px',
                isAddCart: false,
                cartNum: '',
                pinCode: '',
                pinCodeStatus: 0,
                analyticsId: '',
                analyticsCount: 1,
                pinCodeLoad: false,
                cartTipShow: false,
                cartWordStu: false
            }
        },
        eros: {
            backAppeared (params) {
            },
            beforeDisappear (options) {
                if (this.analyticsCount==1) {
                    this.userBackProductAnalytics()
                }
            },
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
            const pageHeight = Utils.env.getScreenHeight();
            this.sHeight = pageHeight - 48
            this.$notice.loading.show();
            this.$router.getParams().then(resData => {
                this.proId = resData.id
                this.getGoodsDetail(this.proId)
                this.getSomeGoods(this.proId)
                this.getDropGoods()

            })
            if (this.$storage.getSync('user')) {
                this.user = this.$storage.getSync('user')
                this.getCartNum()
            } else {
                this.user = null
            }
            this.$event.on('cartNum', parmas => {
                this.getCartNum()
            })
            this.setTime();
            this.initBack();
            this.trimNullObj();
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
                    // this.$notice.alert({
                    //     message: id
                    // })
                    this.$fetch({
                        method: 'GET',
                        url: `${baseUrl}/product/customer/detail/${id}/`,
                        // url: `${baseUrl}/product/customer/detail/8112/`,

                        data: {}
                    }).then((res) => {
                        this.goods.productSpecification = res.productSpecification
                        this.goods.aliasSize = res.aliasSize
                        this.goods.aliasColor = res.aliasColor
                        this.goods.productSize = res.productSize
                        this.purchaseMethod = res.purchaseMethod;
                        if (this.purchaseMethod === 'flash') {
                            this.flashSale = res.flashSale;
                            this.nextPage.flashSale = res.flashSale;
                            if (this.flashSale.flashStatus == 'Ongoing') {
                                this.countDate(this.flashSale.endTime)
                            } else {
                                this.countDate(this.flashSale.startTime)
                            }
                        } else {
                            this.isDrop = true
                        }
                        // ---- 上面是闪购属性设置 下面是普通属性设置----
                        this.goods.title = res.title;
                        this.goods.price = res.saleUnitPrice;
                        this.goods.unitPrice = res.unitPrice;
                        this.selsaleUnitPrice = res.saleUnitPrice;
                        this.selunitPrice = res.unitPrice;
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
                        if (res.shipping) {
                            this.shipObj = res.shipping
                        }

                        this.divider = res.divider
                        this.dividend = res.dividend
                        this.productStatus = res.status
                        this.dectxt = []
                        this.nextPage.title = res.title;
                        this.nextPage.productId = id.id;
                        // this.$notice.alert({
                        //     message: id.id
                        // })
                        this.nextPage.shippingPrice = res.shipping.priceItem;
                        this.nextPage.shippingTimeMin = res.shipping.shippingTimeMin;
                        this.nextPage.shippingTimeMax = res.shipping.shippingTimeMax;

                        // this.isDrop = this.purchaseMethod == 'drop';
                        this.nextPage.proId = this.purchaseMethod;
                        // nextPage 传给下一页组织的数据


                        if (res.newDescription != null) {
                            this.newDescription = res.newDescription
                        }
                        if (res.categories && res.categories.length > 0) {
                            this.category = res.categories[0].name;
                            this.categoryId = res.categories[0].id;
                        }
                        this.userProductAnalytics();
                        this.$notice.loading.hide();
                        googleAnalytics.trackingScreen(`Product Detail/${this.category}`);
                        googleAnalytics.recordEvent('ProductViewCategory', this.purchaseMethod, this.category, 0);
                        googleAnalytics.facebookRecordEvent('fb_mobile_content_view', id.id, this.category, 'Rs', this.lowestPrice)
                    }).catch((res) => {
                        this.$notice.loading.hide();
                        // this.$notice.toast({
                        //     message: res
                        // });
                    })
                }
            },
            getSomeGoods (id) {
                this.$fetch({
                    methods: 'GET',
                    url: `${baseUrl}/product/relations/recommend/list/`,
                    // url: `${baseUrl}/product/customer/detail/654/`,
                    data: {
                        id: id
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
                    // this.$notice.alert({
                    //     message: res
                    // })
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
            userProductAnalytics () {
                this.$fetch({
                    method: 'POST',
                    url: `${dataUrl}/userdbanalysis/add/`,
                    data: {
                        user_id: this.user==null?'':this.user.id,
                        product_id: this.proId,
                        product_title: this.goods.title,
                        product_price: this.goods.unitPrice,
                        product_category: this.category,
                        category_id: this.categoryId ? this.categoryId : '',
                        product_image: this.goodsImg
                    }
                }).then((res) => {
                    // this.dropGoods = data.count
                   // this.$notice.alert({
                   //     message: res
                   // })
                    this.analyticsId = res
                }).catch((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            userBackProductAnalytics () {
                this.analyticsCount = 2  // 防止请求两次
                this.$fetch({
                    method: 'POST',
                    url: `${dataUrl}/userdbanalysis/notebacktime/`,
                    data: {
                        user_id: this.user==null?'':this.user.id,
                        product_id: this.proId,
                        session_id: this.analyticsId
                    }
                }).then((res) => {
                    // this.dropGoods = data.count
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.analyticsCount = 1;
                }).catch((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            getCartNum () {
                this.$fetch({
                    method: 'GET',
                    name: 'cart.count',
                    header: {
                        needAuth: true,
                        isLoginPop: true
                    }

                }).then(data => {
                    this.cartNum = data.count
                   // this.$notice.toast({
                   //     message: data
                   // })
                }, error => {})
            },
            wxcCellClick () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.isBottomShow = true;
                    common.changeAndroidCanBack(false)
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
            confirm () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.isBottomShow = true;
                    if (!this.checkedSelected()) {
                        return;
                    }
                    this.nextPage.id = this.variantsId;
                    this.nextPage.quantity = this.selquantity;
                    // this.$notice.toast({
                    //     message: this.variantsId
                    // })
                    // this.nextPage.currentPrice = this.nextPage.currentPric * this.selquantity;
                    if (this.isDrop == true && this.dropStatus==2) {
                        this.createDrop()
                    } else {
                        if (this.flashSale.flashStatus === 'Ongoing') {
                            this.nextPage.currentPrice = ((this.selunitPrice * this.flashSale.discount) / 100).toFixed(2)
                        }
                        if (this.nextPage.proId == 'flash' && this.flashSale.flashStatus !== 'Ongoing') {
                            this.nextPage.proId = 'direct'
                        }
                        if (this.isAddCart) {
                            this.postGoodsCart()
                        } else {
                            this.$router.open({
                                name: 'order.confirm',
                                type: 'PUSH',
                                params: this.nextPage
                            })

                        }
                    }
                }
            },
            createDrop () {
                this.dropStatus = 2
                if (this.variantsId=='') {
                    this.wxcCellClick()
                    return
                } else {
                    this.$notice.loading.show();
                    this.$fetch({
                        method: 'POST',
                        url: `${baseUrl}/drops/create/`,
                        data: {
                            variant_id: this.variantsId,
                        },
                        header: {
                            needAuth: true
                        }
                    }).then((res) => {
                        this.$event.emit('createDrop');
                        if (res.id) {
                            this.dropGoods += 1
                            this.$router.open({
                                name: 'drop.detail',
                                type: 'PUSH',
                                params: {
                                    id: res.id
                                }
                            })
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
                }
            },
            openBuyNow () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.dropStatus = 1
                    // if (this.hasVariants === false) {
                        if (this.variantsId != '' && this.isDrop == false) {
                            if (this.flashSale.flashStatus === 'Ongoing') {
                                this.nextPage.currentPrice = (((this.selunitPrice || this.goods.unitPrice) * this.flashSale.discount) / 100).toFixed(2);
                            }
                            if (this.nextPage.proId == 'flash' && this.flashSale.flashStatus !== 'Ongoing') {
                                this.nextPage.proId = 'direct'
                            }
                            // this.$router.open({
                            //     name: 'order.confirm',
                            //     type: 'PUSH',
                            //     params: this.nextPage
                            // })
                        }
                    // } else {
                        this.isAddCart = false;
                        this.isBottomShow = true;
                        common.changeAndroidCanBack(false)
                    // }
                }
            },
            addCart () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.isAddCart = true;
                    this.isBottomShow = true;
                    common.changeAndroidCanBack(false)
                }
            },
            postGoodsCart () {
                // this.$notice.alert({
                //     message: this.variantsId
                // })
                this.$notice.loading.show();

                this.$fetch({
                    method: 'POST',
                    name: 'cart.add',
                    data: {
                        'variantId': this.variantsId,
                        'attribute': this.selcolor + ' ' + this.selsize,
                        'quantity': this.selquantity
                    },
                    header: {
                        needAuth: true,
                        isLoginPop: true
                    }

                }).then(data => {
                    if (data.result === 'success') {
                        this.cartNum += 1
                        // this.$notice.toast({
                        //     message: 'Added to Cart Successfully!'
                        // });
                        this.$event.emit('cartNum')
                        this.$notice.loading.hide();
                        googleAnalytics.recordEvent('Payment', 'Add to Cart', this.purchaseMethod, 0);
                        googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', this.proId, '', 'Rs', this.selunitPrice);
                        this.isBottomShow = false
                        this.cartTipShow = true
                        this.cartWordStu = true
                        setTimeout(() => {
                            this.cartTipShow = false
                        }, 5000)
                    } else {
                        this.$notice.toast({
                            message: data
                        })
                    }
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.toast({
                    //     message: res.errorMsg
                    // })
                })
            },
            addQuantity () {
                if ((this.purchaseMethod === 'flash' && this.flashSale.flashStatus == 'Scheduled') || this.purchaseMethod === 'direct') {
                    if (this.selquantity == 10) {
                        return
                    } else {
                        this.selquantity += 1
                    }
                } else if (this.purchaseMethod === 'flash' && this.flashSale.flashStatus == 'Ongoing') {
                    if (this.selquantity == 3) {
                        return
                    } else {
                        this.selquantity += 1
                    }
                }
            },
            delQuantity () {
                if (this.selquantity == 1) {
                    return
                } else {
                    this.selquantity -= 1
                }
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
                this.changeDom(item, color)
            },
            changeDom (item, color) {
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
                if ((this.selsize == '') || (this.selcolor == '')) {
                    this.canBuy = true
                    this.variantsId = ''
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

                this.tmpArray = [];
                for (let i = 0; i < this.goodsType.length; i++) {
                    for (let j = 0; j < this.goodsType[i].value.length; j++) {
                        if (this.goodsType[i].value[j].isActive == true) {
                            this.tmpArray.push(this.goodsType[i].value[j])
                            break;
                        }
                    }
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
                        this.canBuy = this.goodsVariants[i].isCanBuy;
                        this.selsaleUnitPrice = this.goodsVariants[i].saleUnitPrice
                        this.selunitPrice = this.goodsVariants[i].unitPrice

                        this.nextPage.salePrice = this.selsaleUnitPrice;
                        this.nextPage.currentPrice = this.selunitPrice;
                        break;
                    }
                }
            },
            redirectLogin () {
                this.$event.on('login', params => {
                    this.getGoodsDetail(this.proId)
                    this.getSomeGoods(this.proId)
                    this.getCartNum()

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
            },
            // 非状态组件，需要在这里关闭
            popupOverlayBottomClick () {
                this.isBottomShow = false;
                this.isCardShow = false;
                this.isRulerShow = false;
                this.isShipShow = false;
                this.isCODShow = false;
                common.changeAndroidCanBack(true)
            },

            popupCloseClick () {
                // this.$refs.wxcPopup.hide();
                this.isAddCart = false
                this.isBottomShow = false;
                common.changeAndroidCanBack(true)
            },

            popupRulerClick () {
                // this.$refs.wxcPopup.hide();
                this.isRulerShow = false;
                common.changeAndroidCanBack(true)
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
                        dec: this.newDescription,
                        analyticsId: this.analyticsId,
                        productSpecification: this.goods.productSpecification
                    }
                })
            },
            openMyCart () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    this.$router.open({
                        name: 'cart',
                        type: 'PUSH'
                    })
                }
            },
            jumpSearch () {
                this.$router.open({
                    name: 'search',
                    type: 'PUSH'
                });
            },
            openGoodsSize () {
                this.$fetch({
                    method: 'POST',
                    url: `${dataUrl}/userdbanalysis/notebacktime/`,
                    data: {
                        status: 'size_chart',
                        session_id: this.analyticsId
                    }
                }).then((res) => {
                    // this.dropGoods = data.count
                    // this.$notice.alert({
                    //     message: res
                    // })
                }).catch((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
                this.$router.open({
                    name: 'goods.size',
                    type: 'PUSH',
                    params: {
                        productSize: this.goods.productSize,
                        analyticsId: this.analyticsId
                    }
                });
            },
            openReviews () {
                // this.$router.open({
                //     name: 'write.reviews',
                //     type: 'PUSH'
                // })
            },
            openReviews1 () {
                // this.$router.open({
                //     name: 'goods.reviews',
                //     type: 'PUSH'
                // })
            },

            openRuler () {
                this.isRulerShow = true;
                common.changeAndroidCanBack(false);
            },

            openShipPopup () {
                this.isShipShow = true;
                common.changeAndroidCanBack(false);
            },
            openCODPopup () {
                this.isCODShow = true;
                common.changeAndroidCanBack(false);
            },
            oninput (e) {
                this.pinCode = e.value
                this.pinCodeStatus = 0
            },
            checkPinCode () {
                if (this.pinCode.length == 0) {
                    this.pinCodeStatus = 3 // 1支持 2不支持 3 输入的pincode为空
                    return
                } else {
                    this.pinCodeLoad = true
                    this.$fetch({
                        method: 'POST',
                        name: 'order.cod.check',
                        data: {
                            postcode: parseInt(this.pinCode)
                        }
                    }).then((res) => {
                        if (res.enable) {
                            this.pinCodeStatus = 1
                        } else {
                            this.pinCodeStatus = 2
                        }
                        this.pinCodeLoad = false
                    }).catch((res) => {
                        // this.$notice.toast({
                        //     message: res
                        // })
                    })
                }
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.ceil((o - s) / o * 100) + '% OFF'
                    // return ((o - s) / o * 100)  + '% OFF'
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
            getNowDay (str) {
                if (str) {
                    const date = new Date().valueOf();
                    const tmp = (date + ((24 * 60 * 60 * 1000) * (7 + str)))
                    return dayjs(new Date(tmp)).format('MMMM DD')
                }
            },
            getNowDay1 (str) {
                if (str) {
                    const date = new Date().valueOf();
                    const tmp = (date + ((24 * 60 * 60 * 1000) * (7 + str)))
                    return dayjs(new Date(tmp)).format('MMM DD, YYYY')
                }
            },
            countDate (time) {
                const self = this
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
            trimNullObj (arr) {
                const tmpArr = []
                for (let i = 0; i < arr.length; i++) {
                    if (arr[i].content != '') {
                        tmpArr.push(arr[i])
                    }
                }
                return tmpArr
            },
            tranArr (data) {
                let arr = [];
                const rArr = [];
                // this.$notice.alert({
                //     message: data
                // })
                // if (data.length >= 4) {
                    for (let i = 0; i < data.length; i++) {
                        const item = data[i];
                        arr.push(item);
                        if ((i > 0 && i % 4 == 3) || i == data.length - 1) {
                            rArr.push(arr);
                            arr = [];
                        }
                    }
                    // this.$notice.alert({
                    //     message: rArr[0]
                    // })
                    return rArr //  4个一个的二维数组
                // } else {
                //
                //     return data
                // }
            },
            policyShow () {
                this.returnPolicy = !this.returnPolicy;
                if (!this.returnPolicy) {
                    this.firstHeight = '200px';
                } else {
                    this.firstHeight = 'auto';
                }
            },
            setTime () {
                setInterval(() => {
                    this.shakeArrow()
                }, 1000)
            },
            shakeArrow () {
                animation.transition(this.$refs.getArrow, {
                    styles: {
                        transform: 'translate(10px, 0px)'
                    },
                    duration: 500, // ms
                    timingFunction: 'ease',
                    delay: 0 // ms
                }, function () {
                    animation.transition(this.$refs.getArrow, {
                        styles: {
                            transform: 'translate(0px, 0px)'
                        },
                        duration: 500, // ms
                        timingFunction: 'ease',
                        delay: 0 // ms
                    })
                }.bind(this))
            },
        }
    }
</script>
<style lang="sass" src="./popup.scss"></style>

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
    .iiiright-s{
        font-family: iconfont;
        color: white;
        font-size: 40px;
        background-color: rgba(0,0,0,.38);
        position: absolute;
        border-radius:24px ;
        top:80px;
        right:125px;
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
        width: 24px;
        height: 24px;
        background-color: red;
        position: absolute;
        border-radius:24px ;
        top:75px;
        right:34px;
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
        font-size: 28px;
        color: rgba(0,0,0,.87);
        line-height: 48px;
        letter-spacing: 0;
    }
    .drop-title{
        width:586px;
        /*height: 120px;*/
        font-weight: 700;
        margin-top: 16px;
        /*margin-bottom: 32px;*/
        margin-left: 32px;
        font-size: 24px;
        color: rgba(0,0,0,.87);
        line-height: 38px;
        margin-bottom: 16px;
    }
    .drop-label{
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }
    .price-off-fr{
        border-width: 1px;
        border-style: solid ;
        border-color: #00CFE3;
        font-size: 20px;
        border-radius: 8px;
        padding: 4px 16px;
        color: #00CFE3;
        margin-right: -15px;
    }
    .price-off-imp{
        border-width: 1px;
        border-style: solid ;
        border-color: #E0064E;
        font-size: 20px;
        border-radius: 8px;
        padding: 4px 16px;
        color: #E0064E;
       /* margin-right: -15px;*/
    }
    .price-off-tax{
        border-width: 1px;
        border-style: solid ;
        border-color: #EF8A31;
        font-size: 20px;
        border-radius: 8px;
        margin-left: 16px;
        padding: 4px 16px;
        color: #EF8A31;
    }
    .price-name{
        color: rgba(0,0,0,1);
        margin-left: 32px;
        margin-bottom: 26px;
        font-size:24px;
    }
    .price-name-16{
        color: rgba(0,0,0,1);
        margin-bottom: 26px;
        font-size:24px;
    }
    .price-name-1{
        color: #EF8A31;
        letter-spacing: 0;
        text-align: left;
        margin-left: 32px;
        margin-bottom: 26px;
        font-size:40px;
        font-weight: 700;
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
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }
    .c-dd{
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
        width: 185px;
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
        /*height: 350px;*/
        border-top-style:solid ;
        border-top-width:1px ;
        border-top-color:rgba(0,0,0,0.08) ;
        background-color: #fff;
        flex-direction: column;
        margin-top: 12px;
        /*justify-content: space-around;*/
    }
    .l-d-lp{
        position: absolute;
        right: 52px;
        top: 220px;
        color: rgba(0,0,0,.38);
        font-size: 20px;
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
        /*margin-bottom: 22px;*/
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
        justify-content:center;
        margin-top: 20px;
    }
    .progress-num{
        width: 16px;
        height: 16px;
        text-align: center;
        padding-top: 5px;
        color: white;
        background-color: #EF8A31;
        font-size: 20px;
        border-radius: 50%;
    }
    .progress-num-right{
        width: 16px;
        height: 16px;
        text-align: center;
        padding-top: 5px;
        color: white;
        background-color: #492799;
        font-size: 20px;
        border-radius: 50%;
    }
    .learn-drop-line{
        flex-direction: row;
        justify-content:space-around ;
        margin-top: -60px;
    }
    .progress-line-left-last{
        height: 3px;
        width: 580px;
        background-color: #EF8A31;
        margin-left: 45px;
    }
    .progress-line-left{
        /*height: 3px;*/
        /*width: 285px;*/
        /*width: 10px;*/
        /*background-color: #EF8A31;*/
        /*margin-left: -135px;*/
    }
    /*.progress-line-right{*/
        /*height: 3px;*/
        /*width: 170px;*/
        /*background-color: #EF8A31;*/
        /*margin-right: -53px;*/
    /*}*/
    .progress-line-right-last{
        height: 3px;
        width: 80px;
        background-color: #492799;
        margin-right: 25px;
    }
    .progress-word{
        flex-direction: row;
        text-align: center;
        font-size: 24px;
        margin-top: 6px;
    }
    .progress-word1{
        flex-direction: row;
        text-align: center;
        font-size: 24px;
        font-weight: 700;
        margin-top: 22px;
    }
    .learn-see-more{
        flex-direction: row;
        justify-content: center;
        background-color: white;
        margin-bottom: 16px;
        margin-top: 96px;
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
    .s-c-s{
        font-size: 24px;
        color: #1D7CD4;
        margin-right: 32px;
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
        /*box-shadow:  0 -2px 3px 0 rgba(0,0,0,0.24);*/
        border-top-style: solid ;
        border-top-color:rgba(0,0,0,.08) ;
        border-top-width:1px ;
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
        border-radius: 50%;
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
    .button-purple{
        color: #fff;
        height: 80px;
        background-color: #492799;
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
        border-radius: 50%;
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
    .over-flow-cart{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        width: 750px;
    }
    .a-t-c{
        padding-top: 28px;
        padding-bottom: 29px;
        margin-left: 32px;
        margin-top: 12px;
        font-size: 28px;
        color: #EF8A31;
        text-align: center;
        border-radius: 50%;
        background-color: rgba(239,138,49,.16);
        font-weight: 700;
        width: 335px;
    }
    .b-n{
        padding-right: 94px;
        padding-left: 94px;
        padding-top: 28px;
        padding-bottom: 29px;
        margin-left: 16px;
        margin-top: 12px;
        font-size: 28px;
        color: white;
        text-align: center;
        border-radius: 50%;
        background-color: rgba(239,138,49,1);
        font-weight: 700;
        width: 335px;
    }

    .scroller{
        max-height: 380px;
        /*margin-bottom: 20px;*/
    }
    .reward-points{
        flex-direction: column;
        justify-content: space-between;
        background-color: white;
        padding: 0 32px;
        border-top-style:dashed ;
        border-top-color: rgba(0,0,0,.08);
        border-top-width:1px ;
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
        margin-bottom: 16px;
    }
    .d-sm-w{
        font-size: 24px;
        color: #1D7CD4;
        letter-spacing: 0;
        margin-top: 16px;
        padding: 30px  290px;
        border-top-style:dashed ;
        border-top-color: rgba(0,0,0,.32);
        border-top-width:1px ;
    }
    .bottom-goods{
        background-color: white;
        margin-bottom: 120px;
    }
    .ship-item{
        flex-direction: column;
        justify-content: space-between;
        align-items: start;
        padding-top: 30px;
        margin-left: 32px;
        margin-right: 32px;
        border-top-color: rgba(0,0,0,.24);
        border-top-width: 2px;
        border-top-style: dashed;
    }
    .ship-label{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .s-i-img{
        width:38px;
        height:32px;
        margin-right: 24px;
    }
    .s-i-t{
        font-size: 24px;
        color: rgba(0,0,0,.87);
    }
    .s-i-1{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .s-i-2{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .s-i-t1{
        font-size: 24px;
        color: #1D7CD4;
    }
    .s-i-icon{
        border-width: 1px;
        border-style: solid ;
        border-color: #00A9BA;
        font-size: 20px;
        border-radius: 8px;
        padding: 4px 16px;
        color: #00A9BA;
        margin-left: 62px;
        margin-top: 16px;
        margin-bottom: 32px;
    }
    .s-i-icon-imp{
        border-width: 1px;
        border-style: solid ;
        border-color: #00A9BA;
        font-size: 20px;
        border-radius: 8px;
        padding: 4px 16px;
        color: #00A9BA;
        margin-left: 12px;
        margin-top: 16px;
        margin-bottom: 32px;
    }
    .p-s{
        flex-direction: row;
        justify-content:start;
        align-items: center;
        margin: 0 32px;
    }
    .p-s-t1{
        width:234px;
        margin-left: 32px;
        font-size: 24px;
        color: rgba(0,0,0,.54);

    }
    .p-s-t2{
        width:234px;
        margin-left: 32px;
        font-size: 24px;
        color: rgba(0,0,0,.54);

    }
    .create-drop{
        flex-direction: column;
        justify-content: start;
        align-items: start;
        background-color: #FFFFFF;
        margin-top: 16px;
    }
    .c-d-item1{
        width: 686px;
        margin-left: 32px;
    }
    .cd-item1-word{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        margin-top: 32px;
        /*margin-bottom: 16px;*/
    }
    .c-d-item2{
        width: 686px;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        margin-left: 32px;
        margin-bottom: 24px;
        margin-top: -14px;
        padding-left:32px;
        background-color: rgba(231,226,246,1);
        border-radius: 8px;
    }
    .c-d-word{
        font-size: 24px;
        color: #000000;
    }
    .cd-btn{
        flex-direction: row;
        align-items: center;
        justify-content: center;
        width: 108px;
        height: 48px;
        background-color: #492799;
        margin-right: 32px;
        margin-top: 22px;
        margin-bottom: 24px;
        border-radius: 50%;
    }
    .cd-btn1{
        font-size: 24px;
        font-weight: 700;
        margin-left: 8px;
        color: #FFFFFF;
    }
    .cd-btn2{
        font-size: 20px;
        font-weight: 700;
        color: #FFFFFF;
    }
    .rowWithe{
        padding: 22px 32px 22px 0 ;
        background-color: white;
    }
    .rowGray{
        padding: 22px 32px 22px 0 ;
        background-color: rgba(0,0,0,.08);
    }

    .cart-tip{
        position: absolute;
        bottom: 65px;
        left: 80px;
    }
    .ct-img{
        width: 220px;
        height: 110px;
        opacity:.8;
    }
    .ct-word{
        position: relative;
        bottom: 98px;
        left: 38px;
        color: white;
        font-size: 24px;
    }
    .ct-word1{
        position: relative;
        bottom: 88px;
        left: 30px;
        color: white;
        font-size: 24px;
    }
    .review-star{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .mg-b36{
        margin-bottom: 36px;
    }
    .mg-b32{
        margin-bottom: 32px;
    }
    .mg-b44{
        margin-bottom: 44px;
    }
</style>
