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
        <order-detail-bottom :order="order" @cancel="cancel" @deleteOrder="deleteOrder"></order-detail-bottom>
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
import header from './header';
import orderDetailHead from './orderDetailHead';
import orderDetailShipping from './orderDetailShipping';
import orderDetailItem from './orderDetailItem';
import orderDetailBottom from './orderDetailBottom';
import orderDetailNumber from './orderDetailNumber';
import { Utils, WxcPopup, WxcMask } from 'weex-ui';
import { TOKEN, ORDERDETAIL, CANCELREASON } from './config';

export default {
    components: {
        'top-header': header,
        'order-detail-head': orderDetailHead,
        'order-detail-shipping': orderDetailShipping,
        'order-detail-item': orderDetailItem,
        'order-detail-bottom': orderDetailBottom,
        'order-detail-number': orderDetailNumber,
        WxcPopup,
        WxcMask
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
            reasonActive: 0,
            isDeleteShow: false,
            hasAnimation: true
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
        },
        deleteOrder (event) {
            this.isDeleteShow = true
        },
        popupDeleteClick () {
            this.isDeleteShow = false
        },
        deleteOrderConfirm () {
            this.closeDeletePop()
            this.$fetch({
                method: 'DELETE', // 大写
                url: `http://47.104.171.91/order/customer/cancel/${this.order.id}/`,
                data: {},
                header: {
                    Authorization: 'Bearer ' + TOKEN
                }
            }).then(resData => {
                this.$router.setBackParams({
                    status: 'delete'
                })
                this.$router.finish()
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
        font-size: 28px;
        line-height: 34px;
        margin-right: 48px;
        font-weight: bold;
        color: #EF8A31;
    }

    .popup-delete-button-1{
        font-size: 28px;
        line-height: 34px;
        color: rgba(0,0,0,0.54);
    }

</style>