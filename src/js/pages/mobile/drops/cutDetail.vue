<template>
    <div class="wrapper">
        <div class="state"></div>
        <scroller>
            <div class="wrapper-head">
                <div class="navigation">
                    <text class="homeBack" @click="back">&#xe6f6;</text>
                    <div class="cut-rule" @click="showCutRule">
                        <text class="cut-rule-icon">&#xe709;</text>
                        <text class="cut-rule-text">Price Drop FAQ</text>
                    </div>
                </div>
                <div class="out-head">
                    <image class="in-head" resize="cover"
                           :src="goodsDetail.ownerAvatar"></image>
                </div>
                <div class="share-content-top">
                    <text class="wrapper-tip">You have dropped Rs.{{Math.floor((goodsDetail.salePrice - goodsDetail.currentPrice) * 100) / 100 }} Off the price!</text>
                    <text class="wrapper-tip" v-if="goodsDetail.cutStatus=='progressing'">Share this item and invite more friends to drop price for you!</text>
                </div>
            </div>
            <div class="wrapper-product" @click="openDetail()">
                <div class="product-image">
                    <preload class="product-image-in" :src="goodsDetail.mainImage"></preload>
                </div>
                <div class="product-content">
                    <text class="product-content-name">{{goodsDetail.title}}</text>
                    <text class="product-content-people">{{goodsDetail.cutGet == null ? 10 + "+" : goodsDetail.cutGet + 10}} people bought this item at the lowest price</text>
                </div>
            </div>
            <!--正在进行-->
            <div v-if="goodsDetail.cutStatus=='progressing' && goodsDetail.operationStatus=='pending'">
                <div class="wrapper-progress">
                    <div class="current-price" :style="{'margin-left': distance+'px'}">
                        <text class="current-price-1">Current Price</text>
                        <text class="current-price-2">Rs.{{goodsDetail.currentPrice}}</text>
                    </div>
                    <div class="wrapper-progress-line">
                        <div class="progress-line-bottom"></div>
                        <div class="progress-line-top" :style="{'width': percentage * 606+'px'}"></div>
                        <text class="progress-line-icon" :style="{'margin-left':percentage * 606 - 30+'px'}">&#xe70a;
                        </text>
                    </div>
                    <div class="wrapper-price">
                        <div class="wrapper-price-regular">
                            <text class="current-price-1">Regular Price</text>
                            <text class="wrapper-price-2">Rs.{{goodsDetail.salePrice}}</text>
                        </div>
                        <div class="wrapper-price-lowest">
                            <text class="current-price-1">Lowest Price</text>
                            <text class="wrapper-price-2">Rs.{{goodsDetail.lowestPrice}}</text>
                        </div>
                    </div>
                </div>
                <text class="wrapper-share" @click="showSharePanel">Share to Drop the Price Further</text>
                <text class="wrapper-buy-now" @click="showBuyNow">Buy It At Current Price</text>

                <div class="wrapper-timer">
                    <wxc-countdown tpl="{h}:{m}:{s}"
                                   :time="goodsDetail.endTimestamp * 1000"
                                   :timeBoxStyle="{backgroundColor: '#FFFFFF',height: '64px', width: '64px','border-radius': '8px'}"
                                   :timeTextStyle="{fontSize: '28px', color: 'rgba(0,0,0,0.87)'}"
                                   :dotTextStyle="{color: 'rgba(0,0,0,0.87)', fontSize: '24px'}"
                                   :dotBoxStyle="{width: '64px'}"
                                   :style="{justifyContent: 'center'}">
                    </wxc-countdown>
                </div>
            </div>
            <div class="cut-end" v-else>
                <div class="cut-end-total-price">
                    <text class="cut-end-total-price-word">Total Price Dropped:</text>
                    <text class="cut-end-total-price-1"> Rs.{{ Math.floor((goodsDetail.salePrice - goodsDetail.currentPrice)*100)/100 }}</text>
                </div>
                <div class="cut-end-total-price-final">
                    <text class="cut-end-total-price-word">Final Price:</text>
                    <text class="cut-end-total-price-2"> Rs.{{goodsDetail.currentPrice }}</text>
                </div>
                <text class="wrapper-share" v-if="goodsDetail.operationStatus=='paid'" @click="jumpProductDetail">Drop It Again</text>
                <text class="wrapper-share" v-if="goodsDetail.operationStatus=='pending'" @click="jumpConfirmOrder">Buy It Now</text>
                <text class="wrapper-share" v-if="goodsDetail.operationStatus=='unpaid'" @click="jumpOrderDetail">Buy It Now</text>
                <text class="wrapper-share" v-if="goodsDetail.operationStatus=='overdue'" @click="jumpProductDetail">Drop It Again</text>
                <div class="cut-end-item"
                     v-if="goodsDetail.operationStatus=='pending' || goodsDetail.operationStatus=='unpaid'">
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
                <div class="cut-end-item" v-else-if="goodsDetail.operationStatus=='overdue'">
                    <text class="cut-end-item-icon-2">&#xe6fe;</text>
                    <text class="cut-end-item-2"> The final price has expired</text>
                </div>
                <div class="cut-end-item" v-else-if="goodsDetail.operationStatus=='paid'">
                    <text class="cut-end-item-icon-3">&#xe6fb;</text>
                    <text class="cut-end-item-2"> Paid Successfully!</text>
                </div>
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
                                <text class="contributors-content-right-2">Rs.{{i.cutAmount}}</text>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </scroller>
        <wxc-popup
            v-if="goodsDetail.operationStatus=='pending'"
            popup-color="rgba(255, 255, 255, 255)"
            :show="isShowShare"
            @wxcPopupOverlayClicked="popupOverlayAutoClick"
            ref="wxcPopup"
            pos="bottom"
            height="384">
            <div class="share-content">
                <div>
                    <div class="share-content-bottom">
                        <div class="share-content-icon">
                            <div class="facebook" v-if="false" @click="shareFacebook">
                                <text class="facebook-icon">&#xe70f;</text>
                                <text class="facebook-text">Facebook</text>
                            </div>
                            <div class="messager" v-if="false" @click="shareFacebookMessenger">
                                <text class="messager-icon">&#xe727;</text>
                                <text class="facebook-text">FBMessenger</text>
                            </div>
                            <div class="whatsapp" @click="shareWhatsApp">
                                <text class="whatsapp-icon">&#xe710;</text>
                                <text class="whatsapp-text">WhatsApp</text>
                            </div>
                            <div class="copylink" @click="copyShareLink">
                                <text class="copylink-icon">&#xe728;</text>
                                <text class="whatsapp-text">Copy Link</text>
                            </div>
                        </div>
                        <div class="share-line"></div>
                        <div class="share-cancel" @click="popupOverlayAutoClick">
                            <text>CANCEL</text>
                        </div>
                    </div>
                </div>

            </div>
        </wxc-popup>
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
    import { baseUrl } from '../../../config/apis'
    export default {
        components: {
            WxcCountdown,
            WxcPopup,
            WxcMask,
            preload
        },
        eros: {
            appeared (params, options) {
                console.log('beforeAppear');
               // this.isShow = params.isShowSharePanel;
                this.isShow = false;
                this.id = params.id;
                this.requestCutDetail();
            }
        },
        created () {
            this.registerEvent();
        },
        data () {
            return {
                percentage: 0.34,
                TIME: new Date().getTime() + 86400000 + '',
                isShowShare: false,
                isShow: false,
                id: -1,
                goodsDetail: {},
                isRuleShow: false,
                distance: 1
            }
        },
        methods: {
            registerEvent() {
                this.$event.on('cutDetail', params => {
                    this.requestCutDetail();
                })
            },
            showSharePanel () {
                this.isShowShare = true;
            },
            popupOverlayAutoClick () {
                this.isShowShare = false;
            },
            copyShareLink() {
                const url = ShareUrlUtil.getShareUrl(this.id);
                clipboard.setString(url);
                this.popupOverlayAutoClick();
                this.$notice.toast('Copied successfully! You can share this link on your social media now.');
            },
            shareFacebook () {
                const that = this;
                const detail = '1,000+ got the lowest price here.';
                const url = ShareUrlUtil.getShareUrl(that.id);
                const imageUrl = this.goodsDetail.mainImage;
                shareModule.shareFacebook(
                    'Come help me drop the price before it sells out!', detail, url, imageUrl,
                    function (param) {
                        that.popupOverlayAutoClick();
                    }, function (param) {
                        that.popupOverlayAutoClick();
                    }
                );
            },
            shareFacebookMessenger () {
                const that = this;
                const detail = '1,000+ got the lowest price here.';
                const url = ShareUrlUtil.getShareUrl(that.id);
                const imageUrl = this.goodsDetail.mainImage;
                shareModule.shareFacebookMessenger(
                    'Come help me drop the price before it sells out!', detail, url, imageUrl,
                    function (param) {
                        that.popupOverlayAutoClick();
                    }, function (param) {
                        that.popupOverlayAutoClick();
                    }
                );
            },
            shareWhatsApp () {
                const that = this;
                const detail = ShareUrlUtil.getWhatsAppParams(that.id, that.goodsDetail.cutGet == null ? 0 : that.goodsDetail.cutGet);
                shareModule.shareWhatsapp(detail, '',
                    function (param) {
                        that.popupOverlayAutoClick();
                    }, function (param) {
                        that.popupOverlayAutoClick();
                    }
                )
            },
            requestCutDetail () {
                this.$fetch({
                    method: 'GET',
                    url: baseUrl + '/promotion/cut/detail/' + this.id + '/'
                }).then(data => {
                    this.goodsDetail = data;
                    this.percentage = (data.salePrice - data.currentPrice) / (data.salePrice - data.lowestPrice);
                    if (this.percentage < 0.05) {
                        this.percentage = 0.05
                    }
                    this.distance = this.percentage * 606 - 30;
                    if (this.distance > 450) {
                        this.distance = 450;
                    }
                    this.isShowShare = this.isShow;
                }, error => {
                    this.$notice.toast('network is error');
                })
            },
            showBuyNow () {
                this.jumpConfirmOrder();
            },
            jumpProductDetail () {
                this.$router.open({
                    name: 'goods.details',
                    params: {
                        id: this.goodsDetail.productId
                    }
                })
            },
            jumpConfirmOrder () {
                this.$router.open({
                    name: 'order.confirm',
                    params: this.goodsDetail
                })
            },
            jumpOrderDetail () {
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
                this.isRuleShow = true;
            },
            wxcMaskSetHidden () {
                this.isRuleShow = false;
            },
            openDetail () {
                this.$router.open({
                    name: 'simple.details',
                    type: 'PUSH',
                    params: {
                        id: this.goodsDetail.productId
                    }
                })
            }

        }
    }
</script>
<style scoped>
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

    .rule-content {
        padding-left: 36px;
        padding-right: 36px;
        height: 850px;
    }

    .cut-end-item-2 {
        font-size: 24px;
        color: #000000;
        font-weight: 400;
    }

    .cut-end-item-icon-1 {
        font-family: iconfont;
        color: #EF8A31;
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
        margin-top: 24px;
        display: flex;
        flex-direction: row;
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
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .whatsapp {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .messager {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .facebook {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .copylink-icon {
        font-family: iconfont;
        color: rgba(0, 0, 0, 0.54);
        font-size: 96px;
    }

    .whatsapp-icon {
        font-family: iconfont;
        color: #3AC34C;
        font-size: 96px;
    }

    .facebook-icon {
        font-family: iconfont;
        color: #3C5A99;
        font-size: 96px;
    }

    .messager-icon {
        font-family: iconfont;
        color: #0084FF;
        font-size: 96px;
    }

    .share-content-icon {
        height: 270px;
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
        color: white;
        font-size: 28px;
        line-height: 48px;
        font-weight: 400;
    }

    .share-content-text-2 {
        color: #EF8A31;
        font-size: 28px;
        font-weight: bold;
        line-height: 48px;
    }

    .share-content-text {
        display: flex;
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
        width: 718px;
        margin: 35px 16px 40px 16px;
        background-color: #FFFFFF;
        border-radius: 8px;
        box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.12);
    }

    .wrapper-timer {
        margin-top: 24px;
    }

    .wrapper-share {
        width: 606px;
        height: 80px;
        font-size: 24px;
        border-radius: 8px;
        background-color: #EF8A31;
        line-height: 80px;
        text-align: center;
        margin-left: 72px;
        margin-right: 72px;
        color: white;
        font-weight: bold;
        margin-top: 24px;
    }

    .wrapper-buy-now {
        width: 606px;
        height: 80px;
        font-size: 24px;
        border-radius: 8px;
        border-width: 1px;
        border-color: #EF8A31;
        border-style: solid;
        line-height: 80px;
        text-align: center;
        margin-left: 72px;
        margin-right: 72px;
        color: #EF8A31;
        font-weight: bold;
        margin-top: 24px;
    }

    .wrapper-price-2 {
        font-weight: bold;
        font-size: 24px;
        color: rgba(0, 0, 0, 0.87);
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
        border-radius: 8px;
        width: 200px;
        top: 15px;
        position: absolute;
        background-color: #EF8A31;
        height: 8px;
    }

    .progress-line-bottom {
        border-radius: 8px;
        width: 606px;
        position: absolute;
        top: 15px;
        background-color: rgba(0, 0, 0, 0.12);
        height: 8px;
    }

    .wrapper-progress-line {
        border-radius: 8px;
        width: 606px;
        position: relative;
        height: 40px;
        margin-top: 14px;
    }

    .current-price-2 {
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        color: #FD7900;
    }

    .current-price-1 {
        font-size: 24px;
        color: rgba(0, 0, 0, 0.87);
        font-weight: 400;
    }

    .current-price {
        margin-left: 200px;
        margin-top: 24px;
    }

    .wrapper-progress {
        width: 606px;
        margin-left: 72px;
        margin-right: 72px;
    }

    .product-content-people {
        font-size: 20px;
        color: #EF8A31;
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
        width: 718px;
        height: 192px;
        margin-left: 16px;
        background-color: #FFFFFF;
        box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12);
        margin-right: 16px;
        margin-top: -135px;
        border-radius: 8px;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
    }

    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .wrapper-head {
        background-color: #EF8A31;
        width: 750px;
        height: 452px;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    .out-head {
        height: 80px;
        width: 80px;
        border-radius: 40px;
    }

    .in-head {
        height: 80px;
        width: 80px;
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
        color: white;
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
        color: rgba(255, 255, 255, 0.87);
        line-height: 40px;
        margin-top: 16px;
    }

</style>
