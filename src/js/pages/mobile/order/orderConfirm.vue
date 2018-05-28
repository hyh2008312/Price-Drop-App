<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom" @click="jumpAddress">
                <order-confirm-shipping :address="address"></order-confirm-shipping>
            </cell>
            <cell class="cell-bottom">
                <order-confirm-item :order="order"></order-confirm-item>
            </cell>
            <cell class="cell-bottom">
                <order-confirm-pay-method :list="payList" @radioChecked="radioChecked"></order-confirm-pay-method>
            </cell>
        </list>
        <order-confirm-bottom :order="order" :address="address"></order-confirm-bottom>
    </div>
</template>
<script>
import header from './header';
import orderConfirmShipping from './orderConfirmShipping';
import orderConfirmItem from './orderConfirmItem';
import orderConfirmBottom from './orderConfirmBottom';
import orderDetailNumber from './orderDetailNumber';
import orderConfirmPayMethod from './orderConfirmPayMethod'
import { Utils } from 'weex-ui';
import { PAYLIST } from './config';

export default {
    components: {
        'top-header': header,
        'order-confirm-shipping': orderConfirmShipping,
        'order-confirm-item': orderConfirmItem,
        'order-confirm-bottom': orderConfirmBottom,
        'order-detail-number': orderDetailNumber,
        'order-confirm-pay-method': orderConfirmPayMethod
    },
    eros: {
        backAppeared (params, options) {
            this.getAddress()
        },
        beforeAppear (params, options) {
            this.getAddress()
        },
        appeared (params, option) {
            this.order = params
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 112 - 44 - 2) + 'px' }
        this.resetPayList()
    },
    data () {
        return {
            title: 'Confirm Order',
            order: {
                'title': '',
                'mainImage': '',
                'salePrice': '',
                'currentPrice': '',
                'attributes': '',
                'quantity': 1,
                'id': -1,
                'shippingPrice': null
            },
            isOrderConfirm: true,
            payList: [],
            checkedInfo: { src: null, value: 1 },
            address: {
                'id': null,
                'firstName': '',
                'lastName': '',
                'postcode': '',
                'line1': '',
                'line2': '',
                'line3': '',
                'city': '',
                'stateName': '',
                'countryName': '',
                'isDefault': false,
                'phoneNumber': '',
                'stateId': 5
            }
        }
    },
    methods: {
        back () {
            this.$router.finish()
        },
        radioChecked (e) {
            this.checkedInfo = e.data.pay
        },
        resetPayList () {
            this.payList = [...PAYLIST]
        },
        getAddress () {
            this.$storage.get('user').then((params) => {
                if (params.defaultAddress) {
                    this.address = params.defaultAddress
                } else {
                    this.address.id = null
                }
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