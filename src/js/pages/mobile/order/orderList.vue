<template>
    <div class="wrapper">
        <list offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown" v-if="order.length > 0"></refresher>
            <cell class="cell-button" v-for="(item, i) in order" :key="item.id">
                <order-item :order="item" :index="i" @pay="pay"
                            @cancel="cancel" @deleteOrder="deleteOrder"
                            @receiptOrder="receiptOrder"></order-item>
            </cell>
            <cell class="cell-button">
                <text> </text>
            </cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">Loading...</text>
            </loading>
        </list>
        <div class="container-1" v-if="order.length == 0">
            <div class="container-2">
                <image class="pay-image" src="bmlocal://assets/empty.png"></image>
            </div>
            <text class="address-title">There is no order to show.</text>
        </div>
        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgba(255, 255, 255, 0)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupOverlayAutoClick"
                   ref="wxcPopup"
                   pos="bottom"
                   height="416">
            <div class="popup-content">
                <div class="popup-content-top">
                    <div class="popup-content-title-bg">
                        <text class="popup-content-title">Select a Payment Method</text>
                        <text class="popup-close iconfont" @click="openBottomPopup">&#xe632;</text>
                    </div>
                    <pay-radio :list="payList" @radioChecked="radioChecked"></pay-radio>
                </div>
                <div class="popup-content-bottom">
                    <text class="popup-content-text">Total:  </text>
                    <text class="popup-content-price">₹{{selectedOrder.saleUnitPrice}}</text>
                    <text class="popup-content-button" @click="payResult">Pay Now</text>
                </div>
            </div>
        </wxc-popup>
        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgba(255, 255, 255, 0)"
                   :show="isCancelBottomShow"
                   @wxcPopupOverlayClicked="popupCancelAutoClick"
                   ref="wxcCancelPopup"
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
                <text class="popup-delete-title">Once you confirm the receipt of your order, it will be marked as completed.</text>
                <div class="popup-delete-bottom">
                    <text class="popup-delete-button" @click="receiptOrderConfirm">Confirm Delivery</text>
                    <text class="popup-delete-button-1" @click="closeReceiptPop">Not Received Yet</text>
                </div>
            </div>
        </wxc-mask>
    </div>
</template>
<script>
    import { Utils, WxcPopup, WxcMask } from 'weex-ui';
    import refresher from '../common/refresh';
    import orderItem from './orderItem';
    import payRadio from './radio';
    import { PAYLIST, ORDERSTATUS, CANCELREASON } from './config';
    import { baseUrl } from '../../../config/apis';

    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    const common = weex.requireModule('CommonUtils');

    export default {
        components: {
            'refresher': refresher,
            'order-item': orderItem,
            'pay-radio': payRadio,
            WxcPopup,
            WxcMask
        },
        props: ['index', 'activeIndex', 'item'],
        created () {
            this.resetPayList()
            if (this.index == 0 && this.activeIndex == 0) {
                this.init()
            }
            this.$event.on('login', params => {
                this.init()
            });
            this.$event.on('balanceUsed', params => {
                this.init();
            });
            this.$event.on('paid', params => {
                this.init();
            });
            this.$event.on('reviews', params => {
                this.init();
            });
        },
        destory () {
            this.$event.off('login')
        },
        eros: {
            backAppeared (params) {
                if (params && params.status == 'delete') {
                    if (this.index == this.activeIndex) {
                        this.init()
                    }
                }
            }
        },
        watch: {
            'index': {
                handler: function (val, oldVal) {
                    if (this.activeIndex == val) {
                        if (!this.isFirstLoad) {
                            this.isFirstLoad = true
                            googleAnalytics.trackingScreen(`${this.item.name}`);
                            this.init()
                        }
                    }
                },
                deep: true
            }
        },
        data () {
            return {
                order: false,
                selectedOrder: {
                    title: 'Toyota’s Latest is an Entire Mobility Service Platform',
                    src: 'bmlocal://assets/occupy.png',
                    date: 'May 16th，2018',
                    status: 'Unpaid',
                    attribute: 'Pink L',
                    originalPrice: '1299.00',
                    droppedPrice: '299.00',
                    saleUnitPrice: '1000.00',
                    endTime: '23:45:39',
                    number: 1
                },
                payList: [],
                checkedInfo: { src: null, value: 1 },
                page: 1,
                pageSize: 6,
                length: 2,
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid(),
                isBottomShow: false,
                isTrue: true,
                reason: CANCELREASON,
                reasonActive: -1,
                isCancelBottomShow: false,
                cancelId: -1,
                cancelIndex: 0,
                isDeleteShow: false,
                deleteId: -1,
                deleteIndex: 0,
                hasAnimation: true,
                payOrder: {},
                isFirstLoad: false,
                isPaid: false,
                isReceiptShow: false,
                receiptIndex: 0,
                receiptId: -1,
                orderType: 'Normal'
            }
        },
        methods: {
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.getOrder(false);
                }
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getOrder(false);
                }
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd()
                this.isLoading = false
                this.init();
            },
            init () {
                this.$notice.loading.show();
                this.getOrder(true);
                this.initMaskBack();
            },
            getOrder (isfirst) {
                if (isfirst) {
                    this.page = 1
                }
                if (this.page > this.length) {
                    if (this.order.length > 0) {
                        this.$refs.refresh.refreshEnd()
                    }
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                this.$fetch({
                    method: 'GET',
                    name: 'order.customer.list',
                    data: {
                        page: this.page,
                        pageSize: this.pageSize,
                        version: 1,
                        status: ORDERSTATUS[this.index]
                    },
                    header: {
                        needAuth: true
                    }
                }).then(data => {
                    this.$notice.loading.hide();
                    this.length = Math.ceil(data.count / this.pageSize);
                    this.page++;
                    if (isfirst) {
                        this.order = []
                    }
                    this.order.push(...data.results)
                    if (!isfirst) {
                        this.$nextTick(() => {
                            this.isLoading = false;
                        });
                    }
                    this.refreshApiFinished()
                }, error => {
                    this.$notice.loading.hide();
                    // 错误回调
                    this.$notice.alert({
                        message: error
                    });
                })
            },
            refreshApiFinished () {
                this.$refs.refresh.refreshEnd();
            },
            pay ($event) {
                this.payOrder = $event.data.item;
                this.$router.open({
                    name: 'order.payment',
                    type: 'PUSH',
                    params: {
                        source: 'order',
                        data: this.payOrder
                    }
                });
            },
            popupOverlayAutoClick () {
                this.isBottomShow = false;
            },
            openBottomPopup () {
                if (this.$refs.wxcPopup) {
                    this.$refs.wxcPopup.hide();
                }
            },
            radioChecked (e) {
                this.checkedInfo = e.data.pay;
            },
            resetPayList () {
                this.payList = [...PAYLIST];
            },
            payResult () {
            },
            popupCancelAutoClick () {
                this.isCancelBottomShow = false;
            },
            closeBottomPop () {
                if (this.$refs.wxcCancelPopup) {
                    this.$refs.wxcCancelPopup.hide();
                }
            },
            changeReason (index) {
                this.reasonActive = index;
            },
            cancel (event) {
                this.isCancelBottomShow = true;
                this.cancelId = event.data.id;
                this.cancelIndex = event.data.index;
                common.changeAndroidCanBack(false);
            },
            cancelOrder () {
                if (this.reasonActive < 0) {
                    return;
                }
                this.$refs.wxcCancelPopup.hide();
                this.$fetch({
                    method: 'PUT', // 大写
                    url: `${baseUrl}/order/customer/cancel/${this.cancelId}/`,
                    data: {
                        reason: this.reason[this.reasonActive]
                    },
                    header: {
                        needAuth: true
                    }
                }).then(resData => {
                    this.order[this.cancelIndex].orderStatus = 'Audit canceled';
                    this.$notice.toast('Your order cancellation request has been submitted for review.');
                }, error => {
                    this.$notice.toast({
                        message: error
                    });
                })
            },
            deleteOrder (event) {
                this.isDeleteShow = true;
                this.deleteIndex = event.data.index;
                this.deleteId = event.data.id;
                common.changeAndroidCanBack(false);
            },
            receiptOrder (event) {
                this.isReceiptShow = true;
                this.receiptIndex = event.data.index;
                this.receiptId = event.data.id;
                this.orderType = event.data.orderType;
                common.changeAndroidCanBack(false);
            },
            popupDeleteClick () {
                this.isDeleteShow = false;
            },
            popupReceiptClick () {
                this.isReceiptShow = false;
            },
            deleteOrderConfirm () {
                this.closeDeletePop();
                this.$notice.loading.show();
                this.$fetch({
                    method: 'DELETE', // 大写
                    url: `${baseUrl}/order/customer/cancel/${this.deleteId}/`,
                    data: {},
                    header: {
                        needAuth: true
                    }
                }).then(resData => {
                    this.$notice.loading.hide();
                    this.$notice.toast('Deleted successfully!');
                    this.order.splice(this.deleteIndex, 1);
                }, error => {
                    this.$notice.toast({
                        message: error
                    });
                });
            },
            receiptOrderConfirm () {
                this.closeReceiptPop();
                this.$fetch({
                    method: 'POST', // 大写
                    url: `${baseUrl}/order/customer/update/${this.receiptId}/`,
                    data: {
                        status: 'Completed'
                    },
                    header: {
                        needAuth: true
                    }
                }).then(resData => {
                    if (resData.point != 0) {
                        this.$notice.toast(`Congratulations! You have got ${resData.point} points!!`);
                    }
                    if (this.index == 3) {
                        this.order.splice(this.deleteIndex, 1);
                    } else {
                        this.order[this.receiptIndex].orderStatus = 'Completed';
                    }
                }, error => {
                    this.$notice.toast({
                        message: error
                    });
                });
            },
            closeDeletePop () {
                this.isDeleteShow = false;
            },
            closeReceiptPop () {
                this.isReceiptShow = false;
            },
            initMaskBack () {
                common.setAndroidCanBack(true, (params) => {
                    if (this.isReceiptShow) {
                        this.closeReceiptPop();
                    }
                    if (this.isDeleteShow) {
                        this.closeDeletePop();
                    }
                    if (this.isCancelBottomShow) {
                        this.closeBottomPop();
                    }
                    common.changeAndroidCanBack(true)
                })
            },
            prevent (e) {
                e.stopPropagation();
            }
        }
    }

</script>
<style lang="sass" src="./index.scss"></style>
