<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom" @click="jumpAddress">
                <order-detail-shipping :isOrderConfirm="isOrderConfirm"></order-detail-shipping>
            </cell>
            <cell class="cell-bottom">
                <order-detail-item :order="order"></order-detail-item>
            </cell>
            <cell class="cell-bottom">
                <order-confirm-pay-method :list="payList" @radioChecked="radioChecked"></order-confirm-pay-method>
            </cell>
        </list>
        <order-detail-bottom :order="order"></order-detail-bottom>
    </div>
</template>
<script>
import header from './header';
import orderDetailShipping from './orderDetailShipping';
import orderDetailItem from './orderDetailItem';
import orderDetailBottom from './orderDetailBottom';
import orderDetailNumber from './orderDetailNumber';
import orderConfirmPayMethod from './orderConfirmPayMethod'
import { Utils } from 'weex-ui';
import { ORDER, PAYLIST } from './config';

export default {
    components: {
        'top-header': header,
        'order-detail-shipping': orderDetailShipping,
        'order-detail-item': orderDetailItem,
        'order-detail-bottom': orderDetailBottom,
        'order-detail-number': orderDetailNumber,
        'order-confirm-pay-method': orderConfirmPayMethod
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 112 - 44 - 2) + 'px' }
        this.getOrder()
        this.resetPayList()
    },
    data () {
        return {
            title: 'Confirm Order',
            order: {},
            isOrderConfirm: true,
            payList: [],
            checkedInfo: { src: null, value: 1 }
        }
    },
    methods: {
        back () {
            this.$router.finish()
        },
        getOrder () {
            this.order = ORDER
        },
        radioChecked (e) {
            this.checkedInfo = e.data.pay
        },
        resetPayList () {
            this.payList = [...PAYLIST]
        },
        jumpAddress () {
            this.$router.open({
                name: 'order.address'
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

</style>
