<template>
    <div class="wrapper">
        <list offset-accuracy="100" loadmoreoffset="100" @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown" v-if="order.length > 0"></refresher>
            <cell class="cell-button" v-for="(item, i) in order" :key="item.id">
                <order-item :order="item" :index="i" @pay="pay"
                            @cancel="cancel" @deleteOrder="deleteOrder"></order-item>
            </cell>
            <loading class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">加载中...</text>
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
                    <text class="popup-content-price">Rs.{{selectedOrder.saleUnitPrice}}</text>
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
                   height="583">
            <div class="popup-cancel">
                <div class="popup-cancel-top">
                    <text class="popup-cancel-title">Select a Cancelled Reason</text>
                    <div class="popup-cancel-mt">
                        <text class="popup-cancel-title-1"
                              :class="[reasonActive == i ? 'popup-cancel-active': '']"
                              v-for="(item, i) in reason" @click="changeReason(i)">{{item}}</text>
                    </div>
                </div>
                <div class="popup-cancel-bottom">
                    <text class="popup-cancel-button" @click="closeBottomPop">Cancel</text>
                    <text class="popup-cancel-button-1" @click="cancelOrder">OK</text>
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
            <div class="popup-delete-container">
                <text class="popup-delete-title">Are you sure you want to delete this order？</text>
                <div class="popup-delete-bottom">
                    <text class="popup-delete-button" @click="deleteOrderConfirm">DELETE</text>
                    <text class="popup-delete-button-1" @click="closeDeletePop">CANCEL</text>
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
    import { TOKEN, PAYLIST, ORDERSTATUS, CANCELREASON } from './config';
    import { baseUrl } from '../../../config/apis';

    export default {
        components: {
            'refresher': refresher,
            'order-item': orderItem,
            'pay-radio': payRadio,
            WxcPopup,
            WxcMask
        },
        props: ['index', 'activeIndex'],
        created () {
            this.resetPayList()
            if (this.index == 0 && this.activeIndex == 0) {
                this.init()
            }
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
                        this.init()
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
                reasonActive: 0,
                isCancelBottomShow: false,
                cancelId: -1,
                cancelIndex: 0,
                isDeleteShow: false,
                deleteId: -1,
                deleteIndex: 0,
                hasAnimation: true
            }
        },
        methods: {
            onLoadingMore () {
                this.getOrder(false)
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true
                    this.getOrder(false)
                }
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd()
                this.isLoading = false
                this.init();
            },
            init () {
                this.getOrder(true)
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
                    })
                    return
                }
                this.$fetch({
                    method: 'GET',
                    name: 'order.customer.list',
                    data: {
                        page: this.page,
                        pageSize: this.pageSize,
                        status: ORDERSTATUS[this.index]
                    },
                    header: {
                        Authorization: 'Bearer ' + TOKEN
                    }
                }).then(data => {
                    this.length = Math.ceil(data.count / this.pageSize)
                    this.page++
                    if (isfirst) {
                        this.order = []
                    }
                    this.order.push(...data.results)
                    if (!isfirst) {
                        this.$nextTick(() => {
                            this.isLoading = false
                        })
                    }
                    this.refreshApiFinished()
                }, error => {
                    // 错误回调
                    this.$notice.toast({
                        message: error
                    })
                })
            },
            refreshApiFinished () {
                this.$refs.refresh.refreshEnd();
            },
            pay ($event) {
                // this.$notice.toast({
                //     message: $event
                // })
                this.isBottomShow = true
            },
            popupOverlayAutoClick () {
                this.isBottomShow = false;
            },
            openBottomPopup () {
                this.$refs.wxcPopup.hide();
            },
            radioChecked (e) {
                this.checkedInfo = e.data.pay
            },
            resetPayList () {
                this.payList = [...PAYLIST]
            },
            payResult () {
                this.$router.open({
                    name: 'order.failure',
                    type: 'PUSH'
                })
            },
            popupCancelAutoClick () {
                this.isCancelBottomShow = false
            },
            closeBottomPop () {
                this.$refs.wxcCancelPopup.hide()
            },
            changeReason (index) {
                this.reasonActive = index
            },
            cancel (event) {
                this.isCancelBottomShow = true
                this.cancelId = event.data.id
                this.cancelIndex = event.data.index
            },
            cancelOrder () {
                this.$refs.wxcCancelPopup.hide()
                this.$fetch({
                    method: 'PUT', // 大写
                    url: `${baseUrl}/order/customer/cancel/${this.cancelId}/`,
                    data: {
                        reason: this.reason[this.reasonActive]
                    },
                    header: {
                        Authorization: 'Bearer ' + TOKEN
                    }
                }).then(resData => {
                    this.$notice.toast('Your order cancellation request has been submitted for review.')
                    this.order[this.cancelIndex] = resData
                }, error => {
                    this.$notice.toast({
                        message: error
                    })
                })
            },
            deleteOrder (event) {
                this.isDeleteShow = true
                this.deleteIndex = event.data.index
                this.deleteId = event.data.id
            },
            popupDeleteClick () {
                this.isDeleteShow = false
            },
            deleteOrderConfirm () {
                this.closeDeletePop()
                this.$fetch({
                    method: 'DELETE', // 大写
                    url: `${baseUrl}/order/customer/cancel/${this.deleteId}/`,
                    data: {},
                    header: {
                        Authorization: 'Bearer ' + TOKEN
                    }
                }).then(resData => {
                    this.$notice.toast(this.deleteIndex)
                    this.order.splice(this.deleteIndex, 1)
                }, error => {
                    this.$notice.toast({
                        message: error
                    })
                })
            },
            closeDeletePop () {
                this.isDeleteShow = false
            }
        }
    }

</script>
<style lang="sass" src="./index.scss"></style>
