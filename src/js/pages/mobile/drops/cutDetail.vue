<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="wrapper-background">
            <div class="wrapper-background-image"></div>
        </div>
        <scroller>
            <div class="wrapper-head">
                <div class="navigation">
                    <text class="homeBack" @click="back">&#xe6f6;</text>
                    <div class="cut-rule" @click="showCutRule">
                        <text class="cut-rule-icon">&#xe709;</text>
                        <text class="cut-rule-text">How to Reach Lowest Price?</text>
                    </div>
                </div>
            </div>
            <div class="drop-detail-head">
                 <div class="drop-detail-blank"></div>
                 <div class="drop-detail-card">
                     <div class="wrapper-head-word">
                     <div class="share-content-top">
                    <text class="wrapper-tip">You have dropped ₹{{((goodsDetail.salePrice * 100 - goodsDetail.currentPrice * 100) / 100).toFixed(2) }} off the price!</text>
                    <text class="wrapper-tip" v-if="goodsDetail.cutStatus=='progressing'">Invite more friends to help you reach the lowest price!</text>
                </div>
                 </div>
                     <div class="wrapper-head-line"></div>
                     <div class="wrapper-product" >
                      <div class="product-image">
                    <preload class="product-image-in" :src="goodsDetail.mainImage"></preload>
                </div>
                      <div class="product-content">
                    <text class="product-content-name">{{goodsDetail.title}}</text>
                    <text class="product-content-people">50,000+ people reached the lowest price!</text>
                </div>
                 </div>
                 </div>
                <div class="out-head-1"></div>
                 <div class="out-head">
                    <image class="in-head" resize="cover"
                           :src="goodsDetail.ownerAvatar"></image>
                </div>
            </div>

            <div class="drop-middle">
                <div class="drop-middle-top"></div>
                <!--内容-->
                <div class="drop-middle-content">
                    <!--进行-->
                    <div v-if="goodsDetail.cutStatus=='progressing' && goodsDetail.operationStatus=='pending'">
                       <div class="wrapper-progress">
                        <div class="current-price" :style="{'margin-left': distance+'px'}">
                            <text class="current-price-1-current">Current Price</text>
                            <text class="current-price-2">₹{{goodsDetail.currentPrice}}</text>
                            <image class="current-indicator" :style="{'margin-left': indicatorDistance+'px'}" src="bmlocal://assets/drop_indicators.png"></image>
                        </div>
                        <div class="wrapper-progress-line">
                            <div class="progress-line-bottom"></div>
                            <div class="progress-line-top" :style="{'width': percentage * 574+'px'}"></div>
                        </div>
                        <div class="wrapper-price">
                            <div class="wrapper-price-regular">
                                <text class="wrapper-price-3">₹{{goodsDetail.salePrice}}</text>
                                <text class="current-price-1">Original Price</text>
                            </div>
                            <div class="wrapper-price-lowest" :style="{'margin-left': '50px'}">
                                <text class="icon-key" v-if="dropStatus < 2">&#xe73e;</text>
                                <text class="icon-unlock" v-else>&#xe73d;</text>
                                <text class="wrapper-price-2" v-if="dropStatus < 2">50% OFF</text>
                                <text class="wrapper-price-2-1" v-else>50% OFF</text>
                            </div>
                            <div class="wrapper-price-lowest">
                                <text class="icon-key" v-if="dropStatus < 3">&#xe73e;</text>
                                <text class="icon-unlock" v-else>&#xe73d;</text>
                                <text class="wrapper-price-2" v-if="dropStatus < 3">70% OFF</text>
                                <text class="wrapper-price-2-1" v-else>70% OFF</text>
                            </div>
                            <div class="wrapper-price-lowest">
                                <text class="icon-key" v-if="dropStatus < 4">&#xe73e;</text>
                                <text class="icon-unlock" v-else>&#xe73d;</text>
                                <text class="wrapper-price-2" v-if="dropStatus < 4">₹{{goodsDetail.lowestPrice}}</text>
                                <text class="wrapper-price-2-1" v-else >₹{{goodsDetail.lowestPrice}}</text>
                            </div>
                        </div>
                    </div>
                       <text class="wrapper-share" @click="showSharePanel">Share to Drop Your Price Further</text>
                        <text class="wrapper-unlock-tip" v-if="dropStatus ==1">Invite NEW friends to reach lowest price! Super easy!</text>
                       <text class="wrapper-share-1" v-else-if="dropStatus == 2" @click="showBuyNow">Buy Now At 50% OFF</text>
                       <text class="wrapper-share-1" v-else-if="dropStatus == 3" @click="showBuyNow">Buy Now At 70% OFF</text>
                       <text class="wrapper-share-1" v-else-if="dropStatus == 4" @click="showBuyNow">Buy Now At Lowest Price</text>

                        <text class="wrapper-unlock-tip" v-if="dropStatus ==2">50% OFF unlocked! Continue to get 70% OFF and save more!</text>
                        <text class="wrapper-unlock-tip" v-else-if="dropStatus ==3">70% OFF unlocked! You're very closed to the lowest price!</text>
                        <div class="wrapper-timer-con">
                            <div class="wrapper-timer">
                                <wxc-countdown tpl="{h}:{m}:{s}"
                                               :time="goodsDetail.endTimestamp * 1000"
                                               :timeBoxStyle="{backgroundColor: '#F1F1F1',height: '64px', width: '64px','border-radius': '8px'}"
                                               :timeTextStyle="{fontSize: '28px', color: 'rgba(0,0,0,0.87)'}"
                                               :dotTextStyle="{color: 'rgba(0,0,0,0.87)', fontSize: '24px'}"
                                               :dotBoxStyle="{width: '64px'}"
                                               :style="{justifyContent: 'center'}">
                                </wxc-countdown>
                            </div>
                        </div>
                        <div class="prod-blank"></div>
                    </div>
                    <!--结束-->
                    <div  v-else>
                        <div class="cut-end-title">
                            <div class="cut-end-total-price">
                                <text class="cut-end-total-price-word">Your Drop has ended!</text>
                            </div>
                            <div class="cut-end-total-price-final" v-if="dropStatus != 1">
                                <text class="cut-end-total-price-word">Final Price Unlocked:</text>
                                <text class="cut-end-total-price-2" v-if="dropStatus ==2"> ₹{{(goodsDetail.salePrice/2).toFixed(2)}}</text>
                                <text class="cut-end-total-price-2" v-else-if="dropStatus ==3"> ₹{{(goodsDetail.salePrice * 0.3).toFixed(2)}}</text>
                                <text class="cut-end-total-price-2" v-else-if="dropStatus ==4"> ₹{{goodsDetail.lowestPrice}}</text>
                            </div>
                        </div>
                        <div class="cut-end-item-unlock" v-if="dropStatus == 1">
                            <text class="wrapper-unlock-tip-1">You didn't unlock any discount.</text>
                        </div>
                        <div class="wrapper-progress">
                            <div class="wrapper-progress-line">
                                <div class="progress-line-bottom"></div>
                                <div class="progress-line-top" :style="{'width': percentage * 574+'px'}"></div>
                            </div>
                            <div class="wrapper-price">
                                <div class="wrapper-price-regular">
                                    <text class="wrapper-price-3">₹{{goodsDetail.salePrice}}</text>
                                    <text class="current-price-1">Original Price</text>
                                </div>
                                <div class="wrapper-price-lowest" :style="{'margin-left': '50px'}">
                                    <text class="icon-key" v-if="dropStatus < 2">&#xe73e;</text>
                                    <text class="icon-unlock" v-else>&#xe73d;</text>
                                    <text class="wrapper-price-2" v-if="dropStatus < 2">50% OFF</text>
                                    <text class="wrapper-price-2-1" v-else>50% OFF</text>
                                </div>
                                <div class="wrapper-price-lowest">
                                    <text class="icon-key" v-if="dropStatus < 3">&#xe73e;</text>
                                    <text class="icon-unlock" v-else>&#xe73d;</text>
                                    <text class="wrapper-price-2" v-if="dropStatus < 3">70% OFF</text>
                                    <text class="wrapper-price-2-1" v-else>70% OFF</text>
                                </div>
                                <div class="wrapper-price-lowest">
                                    <text class="icon-key" v-if="dropStatus < 4">&#xe73e;</text>
                                    <text class="icon-unlock" v-else>&#xe73d;</text>
                                    <text class="wrapper-price-2" v-if="dropStatus < 4">₹{{goodsDetail.lowestPrice}}</text>
                                    <text class="wrapper-price-2-1" v-else >₹{{goodsDetail.lowestPrice}}</text>
                                </div>
                            </div>
                        </div>
                        <text class="wrapper-share" v-if="dropStatus ==1" @click="jumpProductDetail">Click to Drop It Again</text>
                        <text class="wrapper-share" v-if="goodsDetail.operationStatus=='paid' && dropStatus !=1" @click="jumpProductDetail">Click to Drop It Again</text>
                        <text class="wrapper-share" v-if="goodsDetail.operationStatus=='pending' && dropStatus ==2" @click="jumpConfirmOrder">Buy Now At 50% OFF</text>
                        <text class="wrapper-share" v-if="goodsDetail.operationStatus=='pending' && dropStatus ==3" @click="jumpConfirmOrder">Buy Now At 70% OFF</text>
                        <text class="wrapper-share" v-if="goodsDetail.operationStatus=='pending' && dropStatus ==4" @click="jumpConfirmOrder">Buy Now At Lowest Price</text>
                        <text class="wrapper-share" v-if="goodsDetail.operationStatus=='unpaid'  && dropStatus ==2" @click="jumpOrderDetail">Buy Now At 50% OFF</text>
                        <text class="wrapper-share" v-if="goodsDetail.operationStatus=='unpaid'  && dropStatus ==3" @click="jumpOrderDetail">Buy Now At 70% OFF</text>
                        <text class="wrapper-share" v-if="goodsDetail.operationStatus=='unpaid'  && dropStatus ==4" @click="jumpOrderDetail">Buy Now At Lowest Price</text>
                        <text class="wrapper-share" v-if="goodsDetail.operationStatus=='overdue' && dropStatus !=1" @click="jumpProductDetail">Click to Drop It Again</text>

                        <div class="cut-end-item-column"
                             v-if="(goodsDetail.operationStatus=='pending' || goodsDetail.operationStatus=='unpaid') && dropStatus != 1">
                            <div class="cut-end-item">
                                <text class="cut-end-item-icon-1">&#xe6fa;</text>
                                <text class="cut-end-item-2"> The final price will expire in:</text>
                                <wxc-countdown tpl="{h}:{m}:{s}"
                                               :time="goodsDetail.cancelTimestamp * 1000"
                                               :timeBoxStyle="{backgroundColor: 'transparent', height: '36px', width: '36px','border-radius': '4px'}"
                                               :timeTextStyle="{fontSize: '24px', color: '#000000' , fontWeight:600}"
                                               :dotTextStyle="{color: '#000000', fontSize: '24px'}"
                                               :dotBoxStyle="{width: '10px'}"
                                               :style="{justifyContent: 'center'}">
                                </wxc-countdown>
                            </div>
                        </div>
                        <div class="cut-end-item-column" v-else-if="goodsDetail.operationStatus=='overdue' && dropStatus != 1">
                            <div class="cut-end-item">
                                <text class="cut-end-item-icon-2">&#xe6fe;</text>
                                <text class="cut-end-item-2"> The final price has expired</text>
                            </div>
                        </div>
                        <div class="cut-end-item-column" v-else-if="goodsDetail.operationStatus=='paid' && dropStatus != 1">
                            <div class="cut-end-item">
                                <text class="cut-end-item-icon-3">&#xe6fb;</text>
                                <text class="cut-end-item-2"> Paid Successfully!</text>
                            </div>
                        </div>
                        <div class="cut-end-blank"></div>
                    </div>
                </div>
                <image class="drop-middle-line-left" src="bmlocal://assets/drop_lines.png"></image>
                <image class="drop-middle-line-right" src="bmlocal://assets/drop_lines.png"></image>
            </div>
            <div class="contributors">
                <text class="contributors-people">Price Drop Contributors</text>
                <div :style="{'margin-bottom':'60px'}">
                    <div class="contributors-info" v-for="(i ,index) in goodsDetail.friendCuts">
                        <div class="contributors-content">
                            <div class="contributors-content-left">
                                <div class="contributors-content-left-image">
                                    <image class="contributors-content-left-image-in" resize="cover"
                                           :src="i.avatar"></image>
                                </div>
                                <text class="contributors-content-left-name">{{i.firstName}} {{i.lastName}}</text>
                            </div>
                            <div class="contributors-content-right">
                                <text class="contributors-content-right-1">Dropped  </text>
                                <text class="contributors-content-right-2">₹{{i.cutAmount}}</text>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </scroller>
       <!-- <wxc-popup
            v-if="goodsDetail.cutStatus=='progressing'"
            popup-color="rgba(255, 255, 255, 255)"
            :show="isShowShare"
            @wxcPopupOverlayClicked="popupOverlayAutoClick"
            ref="wxcPopup"
            pos="bottom"
            height="560">
            <div class="share-content">
                <div>
                    <div class="share-content-top">
                        <div class="share-content-text">
                            <text class="share-content-text-1">You have dropped </text>
                            <text class="share-content-text-2">₹{{ ((goodsDetail.salePrice * 100 - goodsDetail.currentPrice * 100) / 100).toFixed(2) }}</text>
                            <text class="share-content-text-1"> off the price!</text>
                        </div>
                        <text class="share-content-text-1">Share to invite more friends to drop price for you!</text>
                        <text class="share-content-text-3">Your friends will earn free points too.
                        </text>
                    </div>
                    <div class="share-content-bottom">
                        <div class="share-content-icon">
                            <div class="facebook"  @click="shareFacebook">
                                <text class="facebook-icon">&#xe70f;</text>
                                <text class="facebook-text">Facebook</text>
                            </div>
                            <div class="whatsapp" @click="shareWhatsApp">
                                <text class="whatsapp-icon">&#xe710;</text>
                                <text class="whatsapp-text">WhatsApp</text>
                            </div>
                            <div class="copylink"  @click="copyShareLink">
                                <text class="copylink-icon">&#xe728;</text>
                                <text class="whatsapp-text">Copy Link</text>
                            </div>
                        </div>
                        <div class="share-content-icon-1" v-if="false">
                            <div class="messager"  @click="shareFacebookMessenger">
                                <text class="messager-icon">&#xe727;</text>
                                <text class="facebook-text">FBMessenger</text>
                            </div>
                            <div class="messager">
                                <text class="messager-icon"></text>
                                <text class="facebook-text"></text>
                            </div>
                            <div class="messager">
                                <text class="messager-icon"></text>
                                <text class="facebook-text"></text>
                            </div>
                        </div>
                        <div class="share-line"></div>
                        <div class="share-cancel" @click="popupOverlayAutoClick">
                            <text>CANCEL</text>
                        </div>
                    </div>
                </div>

            </div>
        </wxc-popup>-->
        <WxcMask
                 height="580"
                 width="622"
                 border-radius="16"
                 duration="200"
                 mask-bg-color="transparent"
                 :has-animation="hasAnimation"
                 :has-overlay="true"
                 :show-close="false"
                 :show="isShowShare"
                 @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="n-share-content">
                <div class="n-share-icon"></div>
                <div class="n-share-des">
                    <div class="share-content-text">
                        <text class="share-content-text-1">You have dropped </text>
                        <text class="share-content-text-2">₹{{ ((goodsDetail.salePrice * 100 - goodsDetail.currentPrice * 100) / 100).toFixed(2) }}</text>
                        <text class="share-content-text-1"> off the price!</text>
                    </div>
                    <text class="share-content-text-4">Share to invite more friends to drop price for you!</text>
                    <text class="share-content-text-3">Your friends will earn free points too.</text>
                    <div class="share-content-icon">
                        <div class="facebook"  @click="shareFacebook">
                            <text class="facebook-icon">&#xe70f;</text>
                            <text class="facebook-text">Facebook</text>
                        </div>
                        <div class="whatsapp" @click="shareWhatsApp">
                            <text class="whatsapp-icon">&#xe710;</text>
                            <text class="whatsapp-text">WhatsApp</text>
                        </div>
                        <div class="copylink"  @click="copyShareLink">
                            <text class="copylink-icon">&#xe728;</text>
                            <text class="whatsapp-text">Copy Link</text>
                        </div>
                    </div>
                </div>
                <image class="n-share-head" src="bmlocal://assets/congratulations.png"></image>

            </div>
        </WxcMask>
        <WxcMask height="900"
                 width="666"
                 border-radius="8"
                 duration="200"
                 mask-bg-color="#FFFFFF"
                 :has-animation="hasAnimation"
                 :has-overlay="true"
                 :show-close="false"
                 :show="isRuleShow"
                 @wxcMaskSetHidden="wxcMaskSetHidden">
            <div class="rule-content">
                <div class="rule-close-div">
                   <text class="rule-close" @click="wxcMaskSetHidden">&#xe632;</text>
                </div>
                <scroller>
                    <text class="rule-faq">FAQ</text>
                <div class="rule-content-title">
                    <text class="rule-title">What is a Price Drop campaign?</text>
                </div>
                <text class="rule-text">The Price Drop campaign is a fun and money-saving way that helps people shop their favorite products at the lowest price by teaming up with their friends!  We believe this model can not only help our customers save bigger, but also create a more engaging social selling channel for brands and manufacturers! </text>
                <div class="rule-content-title">
                    <text class="rule-title">How to reach the lowest price? </text>
                </div>
                <text class="rule-text">Share it with more friends and invite them to drop the price together. Each time when a user clicks the “Drop” button on this page, the price will drop automatically. Once it hits a certain number of clicks, the lowest price will be unlocked!  </text>
                 <div class="rule-content-title">
                        <text class="rule-title">Is there a time frame for a Price Drop?</text>
                 </div>
                    <text class="rule-text">A Price Drop campaign only lasts for 24 hours. Once the item reaches the lowest price, the campaign will end automatically. </text>
                    <div class="rule-content-title">
                        <text class="rule-title">How many times can a user help drop the price? </text>
                    </div>
                    <text class="rule-text">Each user can only help others drop the price up to 10 times a day for different products. </text>
                    <div class="rule-content-title">
                        <text class="rule-title">How to start my own Price Drop campaign?</text>
                    </div>
                    <text class="rule-text">Please download our app “PriceDrop”, in which you can start a DROP for any items you love. </text>
                </scroller>
            </div>
        </WxcMask>
    </div>
</template>
<script>
    import { WxcCountdown, WxcPopup, WxcMask } from 'weex-ui';
    import preload from '../common/preloadImg';
    import ShareUrlUtil from '../utils/ShareUtil';

    const clipboard = weex.requireModule('clipboard')
    const shareModule = weex.requireModule('ShareModule');
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    import { baseUrl } from '../../../config/apis';
    const common = weex.requireModule('CommonUtils');
    export default {
        components: {
            WxcCountdown,
            WxcPopup,
            WxcMask,
            preload
        },
        eros: {
            appeared (params, options) {
            }
        },
        created () {
            this.registerEvent();
            this.$router.getParams().then(resData => {
                // this.isShow = params.isShowSharePanel;
                this.isShow = true;
                this.id = resData.id;
                this.requestCutDetail();
                this.initGoogleAnalytics(this.id)
            })
            this.initBack();
        },
        destory () {
            this.$event.off('cutDetail')
        },
        data () {
            return {
                percentage: 0.1,
                TIME: new Date().getTime() + 86400000 + '',
                isShowShare: false,
                isShow: false,
                id: -1,
                goodsDetail: {
                    salePrice: '0.00',
                    currentPrice: '0.00',
                    lowestPrice: '0.00',
                    endTimestamp: 0,
                    cutStatus: 'progressing',
                    operationStatus: 'pending'
                },
                isRuleShow: false,
                distance: 1,
                indicatorDistance: 91,
                dropStatus: 1  //  1：小于50%  2：大于50% 小于70%   3：大于70% 小于100%   4：100%
            }
        },
        methods: {
            initBack () {
                common.setAndroidCanBack(true, (params) => {
                    this.wxcMaskSetShareHidden();
                })
            },
            loadingStart () {
                this.$notice.loading.show('');
            },
            loadingEnd () {
                this.$notice.loading.hide();
            },
            initGoogleAnalytics (dropId) {
                googleAnalytics.trackingScreen(`DropDetail/${dropId}`);
            },
            registerEvent () {
                this.$event.on('cutDetail', params => {
                    this.requestCutDetail();
                })
            },
            showSharePanel () {
                this.isShowShare = true;
                common.changeAndroidCanBack(false)
            },
            copyShareLink () {
                const url = ShareUrlUtil.getCopylinkUrl(this.id);
                clipboard.setString(url);
                this.wxcMaskSetShareHidden();
                this.$notice.toast('Copied successfully! You can share this link on your social media now.');
                googleAnalytics.recordEvent('DropStart', 'Share to Drop the Price Further', 'CopyURL', 0);
            },
            shareFacebook () {
                const that = this;
                const detail = `Top Brand sneakers, earphones, t-shirts & featured products at ₹${this.goodsDetail.lowestPrice} on #PriceDropAPP! Click this link to help me drop the price`;
                const url = ShareUrlUtil.getShareUrl(that.id);
                const imageUrl = this.goodsDetail.mainImage;
                shareModule.shareFacebook(
                    'Come help me drop the price before it sells out!', detail, url, imageUrl,
                    function (param) {
                        that.wxcMaskSetShareHidden();
                    }, function (param) {
                        that.wxcMaskSetShareHidden();
                    }
                );
            },
            shareFacebookMessenger () {
                const that = this;
                const detail = this.goodsDetail.title;
                const title = `Come join me to get this product at ₹${this.goodsDetail.lowestPrice} only!`;
                const url = ShareUrlUtil.getShareUrl(that.id);
                const imageUrl = this.goodsDetail.mainImage;
                shareModule.shareFacebookMessenger(title, detail, 'Join Now', url, imageUrl,
                    function (param) {
                        that.wxcMaskSetShareHidden();
                    }, function (param) {
                        that.wxcMaskSetShareHidden();
                    }
                );
            },
            shareWhatsApp () {
                const that = this;
                const discount = that.countOff(this.goodsDetail.lowestPrice, this.goodsDetail.salePrice);
                const detail = ShareUrlUtil.getWhatsAppParams(that.id, this.goodsDetail.title, this.goodsDetail.lowestPrice);
                shareModule.shareWhatsapp(detail, '',
                    function (param) {
                        that.wxcMaskSetShareHidden();
                    }, function (param) {
                        that.wxcMaskSetShareHidden();
                    }
                )
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.ceil((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            requestCutDetail () {
                const that = this;
                that.loadingStart();
                this.$fetch({
                    method: 'GET',
                    url: baseUrl + '/promotion/cut/detail/' + this.id + '/'
                }).then(data => {
                    that.loadingEnd();
                    this.goodsDetail = data;
                    this.percentage = (data.salePrice - data.currentPrice) / (data.salePrice - data.lowestPrice);
                    this.distance = this.percentage * 574 - 98;
                    if (this.percentage < 0.5) {
                        this.dropStatus = 1;
                    } else if (this.percentage >= 0.5 && this.percentage < 0.7) {
                        this.dropStatus = 2;
                    } else if (this.percentage >= 0.7 && this.percentage < 1) {
                        this.dropStatus = 3;
                    } else if (this.percentage === 1) {
                        this.dropStatus = 4;
                    }
                    if (this.distance > 372) {
                        this.indicatorDistance = 91 + this.distance - 400;
                        this.distance = 372;
                    }
                    if (this.distance < 0) {
                        this.indicatorDistance = 91 + this.distance;
                        this.distance = 0;
                    }
                    setTimeout(function () {
                        if (that.goodsDetail.cutStatus == 'progressing') {
                            that.isShowShare = that.isShow;
                            common.changeAndroidCanBack(false)
                        }
                    }, 1000);
                }, error => {
                    that.loadingEnd();
                })
            },
            showBuyNow () {
                this.jumpConfirmOrder();
            },
            jumpProductDetail () {
                googleAnalytics.recordEvent('DropEnd', 'Drop It Again', '', 0);
                this.$router.open({
                    name: 'goods.details',
                    params: {
                        id: this.goodsDetail.productId
                    }
                })
            },
            jumpConfirmOrder () {
                if (this.dropStatus === 2) {
                    googleAnalytics.recordEvent('DropStart', 'Buy Now At 50% OFF', '', 0);
                } else if (this.dropStatus === 3) {
                    googleAnalytics.recordEvent('DropStart', 'Buy Now At 70% OFF', '', 0);
                } else if (this.dropStatus === 4) {
                    googleAnalytics.recordEvent('DropStart', 'Click to Get It For Free', '', 0);
                }
                this.goodsDetail.proId = 'drop';
                this.$router.open({
                    name: 'order.confirm',
                    params: this.goodsDetail
                })
            },
            jumpOrderDetail () {
                googleAnalytics.recordEvent('DropEnd', 'Buy it Now', '', 0);
                this.$router.open({
                    name: 'order.detail',
                    params: {
                        id: this.goodsDetail.orderId,
                        type: 'drop'
                    }
                })
            },
            back () {
                this.$router.back();
            },
            showCutRule () {
                googleAnalytics.trackingScreen('Price Drop FAQ');
                this.isRuleShow = true;
            },
            wxcMaskSetHidden () {
                this.isRuleShow = false;
            },
            wxcMaskSetShareHidden () {
                this.isShowShare = false;
                common.changeAndroidCanBack(true)
            },
            // openDetail () {
            //     this.$router.open({
            //         name: 'simple.details',
            //         type: 'PUSH',
            //         params: {
            //             id: this.goodsDetail.productId
            //         }
            //     })
            // }

        }
    }
</script>
<style scoped>
    .n-share-head{
        width: 410px;
        height: 172px;
        position: absolute;
        top:0;
        left:106px;
    }
    .n-share-icon{
        height: 140px;
    }
    .n-share-des{
        height: 440px;
        background-color: #FFFFFF;
        border-radius: 16px;
    }
    .n-share-content{
        background-color: transparent;
        position: relative;
    }
    .wrapper-unlock-tip{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        text-align: center;
        margin-top: 12px;
        font-style: italic;

    }
    .wrapper-price-lowest{
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }
    .icon-key{
        font-family: iconfont;
        font-size: 24px;
        color: rgba(0,0,0,0.38);
        text-align: center;
    }
    .icon-unlock{
        font-family: iconfont;
        font-size: 24px;
        color: #492799;
        text-align: center;

    }
    .prod-blank{
        height: 48px;
    }
    .drop-middle-content{
        background-color: #FFFFFF;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
    }
    .drop-middle-line-left{
        width: 18px;
        height: 98px;
        position: absolute;
        top:0;
        left: 20px;
    }
    .drop-middle-line-right{
        width: 18px;
        height: 98px;
        position: absolute;
        top:0;
        right:20px;
    }
    .drop-middle-top{
        height: 65px;
    }
    .drop-middle{
        margin-top: -35px;
        margin-left: 32px;
        margin-right: 32px;
        position: relative;
    }
    .drop-detail-head{
        margin-left: 32px;
        margin-right: 32px;
        position: relative;
    }
    .drop-detail-blank{
        width: 750px;
        height: 48px;
    }
    .drop-detail-card{
       background-color: #FFFFFF;
        border-radius: 16px;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
    }
    .rule-faq{
        width: 594px;
        font-size: 40px;
        text-align: center;
        margin-top: 48px;
        font-weight: bold;
        color: #000000;
    }
    .rule-title {
        font-size: 32px;
        margin-top: 48px;
        margin-bottom: 32px;
        font-weight: bold;
        color: #000000;
    }

    .rule-content-title {

    }
    .rule-close-div{
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
        width: 666px;
    }
    .rule-close{
        font-family: iconfont;
        font-size: 40px;
        margin-right: 60px;
        margin-top: 30px;
        font-weight: bold;
        color: black;
    }

    .rule-content {
        padding-left: 36px;
        padding-right: 36px;
        height: 850px;
    }
    .cut-end-blank{
        height: 56px;
    }

    .cut-end-item-2 {
        font-size: 24px;
        color: #000000;
        font-weight: 400;
    }

    .cut-end-item-icon-1 {
        font-family: iconfont;
        color: #492799;
        font-size: 28px;
    }

    .cut-end-item-icon-2 {
        font-family: iconfont;
        color: #E93131;
        font-size: 28px;
    }

    .cut-end-item-icon-3 {
        font-family: iconfont;
        color: #26CB03;
        font-size: 28px;
    }

    .cut-end-item {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }
    .cut-end-item-column {
        margin-top: 24px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center
    }
    .cut-end-item-unlock {
        margin-top: 24px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .cut-end-total-price-2 {
        font-size: 32px;
        font-weight: bold;
        color: #000000;
        line-height: 28px;
    }

    .cut-end-total-price-1 {
        font-size: 28px;
        font-weight: bold;
        color: #FD7900;
        line-height: 28px;
    }

    .cut-end-total-price-word {
        font-size: 24px;
        line-height: 28px;
        font-weight: 400;
        color: #000000;
    }

    .cut-end-total-price {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
        margin-top: 54px;
    }

    .cut-end-total-price-final {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
        margin-top: 24px;
    }

    .cut-end {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .share-cancel {
        height: 110px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .share-line {
        height: 1px;
        background-color: rgba(0, 0, 0, 0.12);
    }

    .whatsapp-text {
        margin-top: 16px;
        font-size: 24px;
        color: rgba(0, 0, 0, 0.87);
        font-weight: 400;

    }

    .facebook-text {
        margin-top: 16px;
        font-size: 24px;
        font-weight: 400;
        color: rgba(0, 0, 0, 0.87);
    }

    .copylink {
        width: 250px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .whatsapp {
        width: 250px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .messager {
        width: 250px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }
    .messager-1 {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .facebook {
        width: 250px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .copylink-icon {
        font-family: iconfont;
        color: rgba(0, 0, 0, 0.54);
        font-size: 80px;
    }

    .whatsapp-icon {
        font-family: iconfont;
        color: #3AC34C;
        font-size: 80px;
    }

    .facebook-icon {
        font-family: iconfont;
        color: #3C5A99;
        font-size: 80px;
    }

    .messager-icon {
        font-family: iconfont;
        color: #0084FF;
        font-size: 96px;
    }

    .share-content-icon {
        height: 150px;
        margin-top: 50px;
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
    }
    .share-content-icon-1 {
        height: 200px;
        margin-top: -50px;
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
    }

    .share-content-bottom {
        height: 384px;
        background-color: white;
    }

    .share-content-top {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .share-content-text-1 {
        font-size: 24px;
        font-family: ProximaNova-Regular;
        line-height: 32px;
        font-weight: 400;
    }
    .share-content-text-4 {
        width: 622px;
        text-align: center;
        font-size: 24px;
        font-family: ProximaNova-Regular;
        line-height: 48px;
        font-weight: 400;
    }

    .share-content-text-2 {
        color: #492799;
        font-size: 28px;
        font-weight: bold;
        font-family: ProximaNova-Bold;
        line-height: 32px;
    }
    .share-content-text-3 {
        width: 622px;
        text-align: center;
        font-family: ProximaNova-Bold;
        color: #492799;
        font-size: 24px;
        font-weight: bold;
        line-height: 36px;
    }

    .share-content-text {
        display: flex;
        margin-top: 72px;
        flex-direction: row;
        justify-content: center;
    }

    .share-content {
    }

    .contributors-content-left-image-in {
        height: 80px;
        width: 80px;
    }

    .contributors-content-left-image {
        height: 80px;
        width: 80px;
        border-radius: 40px;
    }

    .contributors-content-left-name {
        margin-left: 24px;
        width: 250px;
        lines:1;
       text-overflow: ellipsis;
    }

    .contributors-content-right {
        margin-right: 32px;
        flex-direction: row;
        justify-content: flex-start;
    }

    .contributors-content-right-1 {
        font-size: 28px;
        font-weight: 400;
        color: #000000;
    }

    .contributors-content-right-2 {
        font-size: 28px;
        font-weight: bold;
        color: #000000;
    }

    .contributors-content-left {
        margin-left: 32px;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;

    }

    .contributors-content {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .contributors-info {
        height: 80px;
        margin-top: 32px;
    }

    .contributors-people {
        width: 718px;
        text-align: center;
        font-size: 28px;
        color: black;
        font-weight: bold;
        margin-top: 32px;
    }

    .contributors {
        margin: 35px 32px 40px 32px;
        background-color: #FFFFFF;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.12);
    }

    .wrapper-timer {
        margin-top: 24px;
        margin-left: 64px;
    }

    .wrapper-share {
        font-family: ProximaNova-Bold;
        width: 574px;
        height: 80px;
        font-size: 24px;
        border-radius: 8px;
        background-color: #492799;
        line-height: 80px;
        text-align: center;
        margin-left: 56px;
        margin-right: 56px;
        color: white;
        font-weight: bold;
        margin-top: 24px;
    }
    .wrapper-share-1 {
        font-family: ProximaNova-Bold;
        width: 574px;
        height: 80px;
        font-size: 24px;
        border-radius: 8px;
        border-width: 2px;
        border-style: solid;
        border-color: #492799;
        line-height: 80px;
        text-align: center;
        margin-left: 56px;
        margin-right: 56px;
        color: #492799;
        font-weight: bold;
        margin-top: 24px;
    }
    .wrapper-buy-now-go{
        font-family: iconfont;
        font-size: 24px;
        margin-right: 28px;
        font-weight: bold;
        color: #333333;
        opacity: 0.6;

    }

    .wrapper-buy-now {
        font-family: ProximaNova-Regular;
        font-size: 24px;
        line-height: 80px;
        color:#000000;
        margin-left: 28px;
        font-weight: bold;

    }
    .wrapper-buy-now-parent{
        width: 686px;
        height: 80px;
        display: flex;
        margin-top: 24px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        background-color: #FFEBD8;

    }

    .wrapper-price-2 {
        font-family: ProximaNova-Bold;
        font-weight: bold;
        font-size: 20px;
        color: #000000;
        line-height: 24px;
    }
    .wrapper-price-2-1 {
        font-family: ProximaNova-Bold;
        font-weight: bold;
        font-size: 20px;
        color: #492799;
        line-height: 24px;
    }
    .wrapper-price-3 {
        font-family: ProximaNova-Regular;
        font-weight: bold;
        font-size: 24px;
        color: rgba(0, 0, 0, 0.87);
        text-decoration: line-through;
    }

    .wrapper-price {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    .progress-line-icon {
        position: absolute;
        font-size: 40px;
        color: black;
        margin-left: 170px;
        font-family: iconfont;
    }

    .progress-line-top {
        border-radius: 18px;
        width: 200px;
        top: 5px;
        position: absolute;
        background-color: #492799;;
        height: 20px;
    }

    .progress-line-bottom {
        border-radius: 18px;
        width: 574px;
        position: absolute;
        top: 5px;
        background-color: rgba(0, 0, 0, 0.12);
        height: 20px;
    }

    .wrapper-progress-line {
        border-radius: 8px;
        width: 574px;
        position: relative;
        height: 40px;
        margin-top: 6px;
    }
    .current-indicator{
        width: 18px;
        height: 8px;
        margin-left: 91px;
    }

    .current-price-2 {
        font-size: 24px;
        line-height: 38px;
        height: 38px;
        width: 200px;
        text-align: center;
        font-weight: bold;
        color: black;
        border-radius: 16px;
        background-color: #D0C7E4;
    }

    .current-price-1 {
        font-family: ProximaNova-Regular;
        font-size: 20px;
        line-height: 24px;
        font-weight: 400;
        color: rgba(0, 0, 0, 0.87);
        letter-spacing: 0;
    }
    .current-price-1-current{
        font-family: ProximaNova-Regular;
        font-size: 20px;
        color: rgba(0, 0, 0, 0.87);
        letter-spacing: 0;
        width: 200px;
        text-align: center;
    }

    .current-price {
        margin-left: 200px;
        margin-top: 24px;
        position: relative;
    }

    .wrapper-progress {
        margin-left: 56px;
        margin-right: 56px;
        margin-top: 50px;
    }

    .product-content-people {
        font-size: 20px;
        color: #492799;
        margin-top: 48px;
        font-weight: bold;
    }

    .product-content-name {
        font-size: 24px;
        line-height: 36px;
        font-weight: 400;
        height: 72px;
        text-overflow: ellipsis;
        color: rgba(0, 0, 0, 0.87);
        lines: 2;
    }

    .product-content {
        width: 465px;
        margin-left: 24px;
        margin-top: 24px;
    }

    .product-image-in {
        width: 160px;
        height: 160px;
    }

    .product-image {
        margin-top: 16px;
        margin-left: 16px;
        width: 160px;
        height: 160px;
        border-radius: 8px;
    }

    .wrapper-product {
        height: 230px;
        margin-left: 16px;
        margin-top: 12px;
        background-color: #FFFFFF;
        margin-right: 16px;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
    }

    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .wrapper-head-line{
        height: 16px;
        opacity: 0.08;
        margin-left: 32px;
        margin-right: 32px;
        border-style: dashed;
        border-color: black;
        border-bottom-width: 2px;
    }

    .wrapper-head-word {
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
        margin-top: 70px;
    }
    .wrapper-head {
        width: 750px;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .out-head {
        position: absolute;
        top:5px;
        left: 295px;
        height: 96px;
        width: 96px;
        border-radius: 48px;
    }
    .out-head-1 {
        position: absolute;
        top:0;
        left: 290px;
        height: 106px;
        width: 106px;
        background-color: #FFFFFF;
        border-radius: 53px;
    }

    .in-head {
        height: 96px;
        width: 96px;
        overflow: hidden;
    }

    .navigation {
        display: flex;
        flex-direction: row;
        height: 112px;
        width: 750px;
        justify-content: space-between;
        align-items: center;
    }

    .homeBack {
        font-family: iconfont;
        color: black;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .cut-rule {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
        margin-right: 30px;
        background-color: rgba(0, 0, 0, 0.34);
        height: 60px;
        border-radius: 24px;
        padding-left: 24px;
        padding-right: 24px;
    }

    .rule-text{
        font-weight: 400;
        font-size: 24px;
        line-height: 40px;
        color: black;
    }

    .cut-rule-text {
        color: white;
        font-size: 20px;
        margin-left: 10px;
    }

    .cut-rule-icon {
        font-family: iconfont;
        color: white;
        font-size: 20px;
    }

    .wrapper-tip {
        font-size: 24px;
        font-weight: bold;
        color: rgba(0, 0, 0, 0.87);
        line-height: 36px;
    }
    .wrapper-background-image{
        width: 750px;
        height: 1440px;
        background-color: #D1C8E8 ;
    }
    .wrapper-background{
        position: absolute;
        top: 48px;
    }
    .wrapper{
        position: relative;
        color: rgba(255, 255, 255, 0.87);
        line-height: 40px;
        text-align: center;
    }
    .cut-end-title{
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }
    .wrapper-timer-con{
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

</style>
