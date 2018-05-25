<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
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
                <order-detail-number :order="order"></order-detail-number>
            </cell>
        </list>
        <order-detail-bottom :order="order"></order-detail-bottom>
    </div>
</template>
<script>
import header from './header';
import orderDetailHead from './orderDetailHead';
import orderDetailShipping from './orderDetailShipping';
import orderDetailItem from './orderDetailItem';
import orderDetailBottom from './orderDetailBottom';
import orderDetailNumber from './orderDetailNumber';
import { Utils } from 'weex-ui';
import { TOKEN } from './config';

export default {
    components: {
        'top-header': header,
        'order-detail-head': orderDetailHead,
        'order-detail-shipping': orderDetailShipping,
        'order-detail-item': orderDetailItem,
        'order-detail-bottom': orderDetailBottom,
        'order-detail-number': orderDetailNumber
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
            order: {}
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
                this.$notice.toast({
                    message: data
                })
                this.order = data
            }, error => {
                // 错误回调
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

</style>
