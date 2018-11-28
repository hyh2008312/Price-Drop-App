<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height" v-if="order.id > 0">
            <cell class="cell-bottom">
                <order-detail-head :order="order"></order-detail-head>
            </cell>
            <cell class="cell-bottom">
                <order-detail-shipping :order="order"></order-detail-shipping>
            </cell>
            <cell class="cell-bottom">
                <order-detail-item :order="order"></order-detail-item>
            </cell>
            <cell class="cell-bottom">
                <order-detail-delivery :order="order"></order-detail-delivery>
            </cell>
            <cell class="cell-bottom">
                <order-detail-number :order="order"></order-detail-number>
            </cell>
        </list>
        <order-detail-bottom :order="order" @cancel="cancel" @deleteOrder="deleteOrder"
                             @receiptOrder="receiptOrder"></order-detail-bottom>
        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgba(255, 255, 255, 0)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupCancelAutoClick"
                   ref="wxcPopup"
                   pos="bottom"
                   height="542">
            <div class="popup-cancel">
                <div class="popup-cancel-top">
                    <text class="popup-cancel-title">Select a Cancelled Reason</text>
                    <div class="popup-cancel-mt">
                        <div class="popup-cancel-title-bg" v-for="(item, i) in reason" @click="changeReason(i)">
                            <text class="iconfont popup-cancel-icon popup-cancel-active" v-if="reasonActive == i">&#xe6fb;</text>
                            <text class="iconfont popup-cancel-icon" v-if="reasonActive != i">&#xe73f;</text>
                            <text class="popup-cancel-title-1">{{item}}</text>
                        </div>
                    </div>
                </div>
                <div class="popup-cancel-bottom">
                    <text class="popup-cancel-button" @click="closeBottomPop">Cancel</text>
                    <text class="popup-cancel-button-2" :class="[reasonActive >= 0 ? 'popup-cancel-button-1': '']" @click="cancelOrder()">OK</text>
                </div>
            </div>
        </wxc-popup>
        <wxc-mask height="258"
                  width="514"
                  border-radius="0"
                  duration="200"
                  mask-bg-color="rgba(0,0,0,0)"
                  :has-animation="hasAnimation"
                  :has-overlay="true"
                  :show-close="false"
                  :show="isDeleteShow"
                  @wxcMaskSetHidden="popupDeleteClick">
            <div class="popup-delete-container" @click="prevent($event)">
                <text class="popup-delete-title">Are you sure you want to delete this order？</text>
                <div class="popup-delete-bottom">
                    <text class="popup-delete-button" @click="deleteOrderConfirm">Delete</text>
                    <text class="popup-delete-button-1" @click="closeDeletePop">Cancel</text>
                </div>
            </div>
        </wxc-mask>
        <wxc-mask height="260"
                  width="560"
                  border-radius="0"
                  duration="200"
                  mask-bg-color="rgba(0,0,0,0)"
                  :has-animation="hasAnimation"
                  :has-overlay="true"
                  :show-close="false"
                  :show="isReceiptShow"
                  @wxcMaskSetHidden="popupReceiptClick">
            <div class="popup-receipt-container" @click="prevent($event)">
                <text class="popup-delete-title">Once you confirm the delivery of your order, it will be marked as completed.</text>
                <div class="popup-delete-bottom">
                    <text class="popup-delete-button" @click="receiptOrderConfirm">Confirm Delivery</text>
                    <text class="popup-delete-button-1" @click="closeReceiptPop">Not Received Yet</text>
                </div>
            </div>
        </wxc-mask>
    </div>
</template>
<script>
import header from './header';
import orderDetailHead from './orderDetailHead';
import orderDetailShipping from './orderDetailShipping';
import orderDetailItem from './orderDetailItem';
import orderDetailBottom from './orderDetailBottom';
import orderDetailNumber from './orderDetailNumber';
import orderDetailDelivery from './orderDetailDelivery';
import { Utils, WxcPopup, WxcMask } from 'weex-ui';
import { ORDERDETAIL, CANCELREASON } from './config';
import { baseUrl } from '../../../config/apis';
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
const common = weex.requireModule('CommonUtils');

export default {
    components: {
        'top-header': header,
        'order-detail-head': orderDetailHead,
        'order-detail-shipping': orderDetailShipping,
        'order-detail-item': orderDetailItem,
        'order-detail-bottom': orderDetailBottom,
        'order-detail-number': orderDetailNumber,
        orderDetailDelivery,
        WxcPopup,
        WxcMask
    },
    eros: {
        appeared (params) {
            if (params && params.id) {
                googleAnalytics.trackingScreen(`Order Detail/${params.id}`);
                this.getOrder(params.id);
                this.$event.on('login', params => {
                    this.getOrder(params.id)
                });
            }
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight();
        this.height = { height: (pageHeight - 112 - 112 - 48 - 2) + 'px' };
        this.initMaskBack();
        this.$event.on('changeAddress', params => {
            this.$notice.toast('Address changed successfully!');
        });
    },
    destory () {
        this.$event.off('login')
    },
    data () {
        return {
            title: 'Order Detail',
            order: ORDERDETAIL,
            isTrue: true,
            isBottomShow: false,
            reason: CANCELREASON,
            reasonActive: -1,
            isDeleteShow: false,
            isReceiptShow: false,
            hasAnimation: true
        }
    },
    methods: {
        back () {
            this.$router.finish()
        },
        getOrder (id) {
            this.$notice.loading.show();
            this.$fetch({
                method: 'GET',
                url: `${baseUrl}/order/customer/detail/${id}/`,
                data: {},
                header: {
                    needAuth: true
                }
            }).then(data => {
                this.$notice.loading.hide();
                this.order = data;
            }, error => {
                this.$notice.loading.hide();
                // 错误回调
                this.$notice.toast({
                    message: error
                })
            })
        },
        popupOverlayAutoClick () {
            this.isBottomShow = false
        },
        closeBottomPop () {
            if (this.$refs.wxcPopup) {
                this.$refs.wxcPopup.hide();
            }
        },
        changeReason (index) {
            this.reasonActive = index
        },
        cancel () {
            this.isBottomShow = true;
            common.changeAndroidCanBack(false);
        },
        cancelOrder () {
            if (this.reasonActive < 0) {
                return;
            }
            this.$refs.wxcPopup.hide()
            this.$fetch({
                method: 'PUT', // 大写
                url: `${baseUrl}/order/customer/cancel/${this.order.id}/`,
                data: {
                    reason: this.reason[this.reasonActive]
                },
                header: {
                    needAuth: true
                }
            }).then(resData => {
                this.$notice.toast('Your order cancellation request has been submitted for review.')
                this.order = resData
            }, error => {
                this.$notice.toast({
                    message: error
                })
            })
        },
        deleteOrder (event) {
            this.isDeleteShow = true;
            common.changeAndroidCanBack(false);
        },
        receiptOrder (event) {
            this.isReceiptShow = true;
            common.changeAndroidCanBack(false);
        },
        popupDeleteClick () {
            this.isDeleteShow = false
        },
        popupReceiptClick () {
            this.isReceiptShow = false;
        },
        deleteOrderConfirm () {
            this.closeDeletePop();
            this.$notice.loading.show();
            this.$fetch({
                method: 'DELETE', // 大写
                url: `${baseUrl}/order/customer/cancel/${this.order.id}/`,
                data: {},
                header: {
                    needAuth: true
                }
            }).then(resData => {
                this.$notice.loading.hide();
                this.$notice.toast('Deleted successfully!');
                this.$router.setBackParams({
                    status: 'delete'
                });
                this.$router.finish();
            }, error => {
                this.$notice.toast({
                    message: error
                });
            })
        },
        receiptOrderConfirm () {
            this.closeReceiptPop();
            this.$fetch({
                method: 'POST', // 大写
                url: `${baseUrl}/order/customer/update/${this.order.id}/`,
                data: {
                    status: 'Completed'
                },
                header: {
                    needAuth: true
                }
            }).then(resData => {
                this.order.orderStatus = resData.order.orderStatus;
                if (resData.point != 0) {
                    this.$notice.toast(`Congratulations! You have got ${resData.point} points!!`);
                }
            }, error => {
                this.$notice.toast({
                    message: error
                });
            });
        },
        closeDeletePop () {
            this.isDeleteShow = false
        },
        closeReceiptPop () {
            this.isReceiptShow = false
        },
        initMaskBack () {
            common.setAndroidCanBack(true, (params) => {
                if (this.isReceiptShow) {
                    this.closeReceiptPop();
                }
                if (this.isDeleteShow) {
                    this.closeDeletePop();
                }
                if (this.isBottomShow) {
                    this.closeBottomPop();
                }
                common.changeAndroidCanBack(true);
            });
        },
        prevent (e) {
            e.stopPropagation();
        }
    }
}

</script>
<style scoped>

    .iconfont{
        font-family: iconfont;
    }
    .wrapper{
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        background-color: #f1f1f1;
    }

    .status-bar{
        position: fixed;
        top: 0;
        height: 48px;
        background-color: #000;
        width: 750px;
    }

    .container{
        margin-top: 2px;
        width: 750px;
        background-color: #f1f1f1;
    }

    .cell-bottom{
        padding-bottom: 16px;
    }

    .popup-delete-container{
        width: 514px;
        height: 258px;
        border-radius: 8px;
        background-color: #fff;
        padding: 32px;
    }

    .popup-delete-title{
        font-size: 28px;
        line-height: 48px;
    }

    .popup-delete-bottom{
        margin-top: 48px;
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
    }

    .popup-delete-button{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 34px;
        margin-right: 48px;
        font-weight: bold;
        color: #EF8A31;
    }

    .popup-delete-button-1{
        font-family: ProximaNova;
        font-weight: bold;
        font-size: 24px;
        line-height: 34px;
        color: rgba(0,0,0,0.54);
    }

    .popup-receipt-container{
        width: 560px;
        height: 260px;
        border-radius: 8px;
        background-color: #fff;
        padding: 32px;
    }

    .popup-cancel {
        height: 542px;
        width: 750px;
        border-top-left-radius: 16px;
        border-top-right-radius: 16px;
        background-color: #fff;
        overflow: hidden;
    }

    .popup-cancel-top{
        height: 428px;
        width: 750px;
        padding: 44px 32px;
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08);
    }

    .popup-cancel-title{
        font-family: ProximaNova;
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
    }

    .popup-cancel-title-1{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        margin-left: 24px;
        width: 686px;
    }

    .popup-cancel-title-bg{
        margin-top: 32px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .popup-cancel-icon{
        font-size: 24px;
        line-height: 28px;
    }

    .popup-cancel-active{
        color: #4AB406;
    }

    .popup-cancel-mt{
        margin-top: 18px;
        width: 686px;
    }

    .popup-cancel-bottom{
        height: 110px;
        width: 750px;
        padding: 0 32px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .popup-cancel-button{
        font-size: 28px;
        line-height: 78px;
        font-weight: bold;
        width: 338px;
        border-color: #EF8A31;
        border-width: 1px;
        border-style: solid;
        border-radius: 40px;
        text-align: center;
        color: #EF8A31;
    }

    .popup-cancel-button-1{
        font-size: 28px;
        line-height: 80px;
        font-weight: bold;
        width: 338px;
        border-radius: 40px;
        text-align: center;
        color: #fff;
        background-color: #EF8A31;
    }

    .popup-cancel-button-2{
        font-size: 28px;
        line-height: 80px;
        font-weight: bold;
        width: 338px;
        border-radius: 40px;
        text-align: center;
        color: #fff;
        background-color: rgba(0,0,0,0.38);
    }

</style>
