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
                <order-detail-number :order="order"ß></order-detail-number>
            </cell>
        </list>
        <order-detail-bottom :order="order" @cancel="cancel"></order-detail-bottom>
        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgb(255, 255, 255, 255)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupOverlayAutoClick"
                   ref="wxcPopup"
                   pos="bottom"
                   height="583">
            <div class="popup-content">
                <div class="popup-content-top">
                    <text class="popup-content-title">Select a Cancelled Reason</text>
                    <div class="popup-content-mt">
                        <text class="popup-content-title-1" :class="[reasonActive == index ? 'popup-content-active': '']"
                              v-for="(item, index) in reason" @click="changeReason(index)">{{item}}</text>
                    </div>
                </div>
                <div class="popup-content-bottom">
                    <text class="popup-content-button" @click="closeBottomPop">Cancel</text>
                    <text class="popup-content-button-1" @click="cancelOrder">OK</text>
                </div>
            </div>
        </wxc-popup>
    </div>
</template>
<script>
import header from './header';
import orderDetailHead from './orderDetailHead';
import orderDetailShipping from './orderDetailShipping';
import orderDetailItem from './orderDetailItem';
import orderDetailBottom from './orderDetailBottom';
import orderDetailNumber from './orderDetailNumber';
import { Utils, WxcPopup } from 'weex-ui';
import { TOKEN, ORDERDETAIL, CANCELREASON } from './config';

export default {
    components: {
        'top-header': header,
        'order-detail-head': orderDetailHead,
        'order-detail-shipping': orderDetailShipping,
        'order-detail-item': orderDetailItem,
        'order-detail-bottom': orderDetailBottom,
        'order-detail-number': orderDetailNumber,
        WxcPopup
    },
    eros: {
        appeared (params) {
            this.getOrder(params.id)
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 112 - 44 - 2) + 'px' }
    },
    data () {
        return {
            title: 'Order Detail',
            order: ORDERDETAIL,
            isTrue: true,
            isBottomShow: false,
            reason: CANCELREASON,
            reasonActive: 0
        }
    },
    methods: {
        back () {
            this.$router.finish()
        },
        getOrder (id) {
            this.$fetch({
                method: 'GET',
                url: `http://47.104.171.91/order/customer/detail/${id}/`,
                data: {},
                header: {
                    Authorization: 'Bearer ' + TOKEN
                }
            }).then(data => {
                this.order = data
            }, error => {
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
            this.$refs.wxcPopup.hide()
        },
        changeReason (index) {
            this.reasonActive = index
        },
        cancel () {
            this.isBottomShow = true
        },
        cancelOrder () {
            this.$refs.wxcPopup.hide()
            this.$fetch({
                method: 'PUT', // 大写
                url: `http://47.104.171.91/order/customer/cancel/${this.order.id}/`,
                data: {
                    reason: this.reason[this.reasonActive]
                },
                header: {
                    Authorization: 'Bearer ' + TOKEN
                }
            }).then(resData => {
                this.$notice.toast('Your order cancellation request has been submitted for review.')
                this.order = resData
            }, error => {
                this.$notice.toast({
                    message: error
                })
            })
        }
    }
}

</script>
<style scoped>

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
        height: 44px;
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


    .popup-content {
        height: 583px;
        width: 750px;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
        background-color: #fff;
        overflow: hidden;
    }

    .popup-content-top{
        height: 463px;
        width: 750px;
        padding: 48px 32px;
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08);
        align-items: center;
    }

    .popup-content-title{
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        text-align: center;
    }

    .popup-content-title-1{
        font-size: 24px;
        line-height: 28px;
        margin-top: 32px;
        text-align: center;
        width: 686px;
    }

    .popup-content-active{
        color: #EF8A31;
    }

    .popup-content-mt{
        margin-top: 32px;
        width: 686px;
    }

    .popup-content-bottom{
        height: 120px;
        width: 750px;
        flex-direction: row;
        justify-content: space-between;
    }

    .popup-content-button{
        font-size: 28px;
        line-height: 120px;
        font-weight: bold;
        width: 375px;
        border-right-color: rgba(0,0,0, 0.08);
        border-right-width: 1px;
        border-right-style: solid;
        text-align: center;
        color: rgba(0,0,0,0.38);
    }

    .popup-content-button-1{
        font-size: 28px;
        line-height: 120px;
        font-weight: bold;
        width: 375px;
        text-align: center;
        color: #EF8A31;
    }

</style>
