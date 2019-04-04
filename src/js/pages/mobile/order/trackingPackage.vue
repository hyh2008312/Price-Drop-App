<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom" @click="jumpAddress">
                <tracking-package-header :order="order.orderData"></tracking-package-header>
            </cell>
            <cell class="cell-line"></cell>
            <cell v-for="(item,i) in trackingData" v-if="trackingData">
                <tracking-package-item-new :order="item" :complete="true"
                   v-if="i == 0 && order.orderData.orderStatus == 'Completed'"></tracking-package-item-new>
                <tracking-package-item-new :order="item"
                   v-if="i > 0 || (i == 0 && order.orderData.orderStatus != 'Completed')"></tracking-package-item-new>
            </cell>
            <cell>
                <tracking-package-item-new :shippedTime="order.orderData.shippedTime"></tracking-package-item-new>
            </cell>
            <cell>
                <tracking-package-item-new :paidTime="order.orderData.paidTime"></tracking-package-item-new>
            </cell>
        </list>
    </div>
</template>
<script>
import header from './header';
import trackingPackageHeader from './trackingPackageHeader';
import trackingPackageItem from './trackingPackageItem';
import trackingPackageItemNew from './trackingPackageItemNew';
import { Utils } from 'weex-ui';
import { baseUrl } from '../../../config/apis';
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

export default {
    components: {
        'top-header': header,
        'tracking-package-header': trackingPackageHeader,
        'tracking-package-item': trackingPackageItem,
        trackingPackageItemNew
    },
    eros: {
        appeared (params, option) {
            if (params) {
                this.id = params.id;
                this.carrierCode = params.carrierCode;
                googleAnalytics.trackingScreen(`Tracking Package/${this.id}`);
                if (!this.isFirstLoad) {
                    this.isFirstLoad = true;
                    this.getOrderTracking();
                }
                this.$event.once('login', params => {
                    this.getOrderTracking();
                })
            }
        }
    },
    destory () {
        this.$event.off('login')
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 48 - 2) + 'px' }
    },
    data () {
        return {
            title: 'Tracking Information',
            id: false,
            carrierCode: '',
            isFirstLoad: false,
            order: {
                orderData: {
                    trackingNumber: ' ',
                    carrierCode: ' '
                }
            },
            trackingData: []
        }
    },
    methods: {
        getOrderTracking () {
            this.$notice.loading.show();
            this.$fetch({
                method: 'GET', // 大写
                url: `${baseUrl}/order/tracking/shipping/${this.id}/`,
                header: {
                    needAuth: true
                }
            }).then(resData => {
                this.$notice.loading.hide();
                this.isFirstLoad = false;
                // 成功回调
                this.order = {};
                this.order = resData;

                this.trackingData = resData.trackingData;
            }, error => {});
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
        height: 48px;
        background-color: #000;
        width: 750px;
    }

    .container{
        margin-top: 2px;
        width: 750px;
        background-color: #fff;
    }

    .cell-bottom{
        padding-bottom: 16px;
        background-color: #f1f1f1;
    }

    .cell-line{
        height: 46px;
    }

</style>
