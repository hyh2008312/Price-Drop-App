<template>
    <div class="wrapper">
        <list offset-accuracy="100" loadmoreoffset="100" @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell class="cell-button" v-for="item in order">
                <order-item :order="item" @pay="pay"></order-item>
            </cell>
            <loading class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">加载中...</text>
            </loading>
        </list>
        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgb(255, 255, 255, 255)"
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
    </div>
</template>
<script>
    import { Utils, WxcPopup } from 'weex-ui';
    import refresher from '../common/refresh';
    import orderItem from './orderItem';
    import payRadio from './radio';
    import { ORDERS, PAYLIST } from './config';

    export default {
        components: {
            'refresher': refresher,
            'order-item': orderItem,
            'pay-radio': payRadio,
            WxcPopup
        },
        props: ['index', 'activeIndex'],
        created () {
            this.resetPayList()
            if (this.index == 0 && this.activeIndex == 0) {
                this.init()
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
                order: [],
                selectedOrder: {
                    title: 'Toyota’s Latest is an Entire Mobility Service Platform',
                    src: 'http://yanxuan.nosdn.127.net/630439320dae9f1ce3afef3c39721383.jpg',
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
                isTrue: true

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
                    this.order = []
                }
                this.order.push(...ORDERS)
                if (!isfirst) {
                    this.$nextTick(() => {
                        this.isLoading = false
                    })
                }
                this.refreshApiFinished()
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
                    name: 'order.result',
                    type: 'PUSH',
                    params: {
                        result: true
                    }
                })
            }
        }
    }

</script>
<style lang="sass" src="./index.scss"></style>
