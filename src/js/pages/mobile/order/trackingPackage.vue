<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom" @click="jumpAddress">
                <tracking-package-header :order="order.orderData"></tracking-package-header>
            </cell>
            <cell class="cell-line"></cell>
            <cell v-for="item in trackingInfo" v-if="trackingInfo.length > 0">
                <tracking-package-item-new :order="item"></tracking-package-item-new>
            </cell>
            <cell v-for="item in gatiTrackingInfo" v-if="gatiTrackingInfo.length > 0">
                <tracking-package-item-gati :order="item"></tracking-package-item-gati>
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
import trackingPackageItemGati from './trackingPackageItemGati';
import { Utils } from 'weex-ui';
import { baseUrl } from '../../../config/apis';
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

export default {
    components: {
        'top-header': header,
        'tracking-package-header': trackingPackageHeader,
        'tracking-package-item': trackingPackageItem,
        trackingPackageItemNew,
        trackingPackageItemGati
    },
    eros: {
        appeared (params, option) {
            if (params) {
                this.id = params.id;
                this.carrierCode = params.carrierCode;
                googleAnalytics.trackingScreen(`Tracking Package/${this.id}`);
                if (!this.isFirstLoad) {
                    this.isFirstLoad = true;
                   if (this.carrierCode == 'gaticn') {
                       this.getGATIOrderTracking();
                   } else {
                       this.getOrderTracking();
                   }
                }
                this.$event.once('login', params => {
                    if (this.carrierCode == 'gaticn') {
                        this.getGATIOrderTracking();
                    } else {
                        this.getOrderTracking();
                    }
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
            trackingInfo: [],
            gatiTrackingInfo: []
        }
    },
    methods: {
        getOrderTracking () {
            this.$fetch({
                method: 'GET', // 大写
                url: `${baseUrl}/order/tracking/${this.id}/`,
                header: {
                    needAuth: true
                }
            }).then(resData => {
                this.isFirstLoad = false;
                // 成功回调
                this.order = {};
                this.order = resData;
                if (resData.data && resData.data.originInfo.trackingInfo.length > 0) {
                    this.trackingInfo = [...resData.data.originInfo.trackingInfo];
                }
            }, error => {});
        },
        getGATIOrderTracking () {
            this.$fetch({
                method: 'GET', // 大写
                url: `${baseUrl}/order/gati/shipping/${this.id}/`,
                header: {
                    needAuth: true
                }
            }).then(resData => {
                this.isFirstLoad = false;
                // 成功回调
                this.order = {};
                this.order = resData;
                if (resData.data && resData.data.TrackData.length > 0) {
                    this.gatiTrackingInfo = [...resData.data.TrackData];
                }
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
