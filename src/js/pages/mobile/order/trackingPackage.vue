<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom" @click="jumpAddress">
                <tracking-package-header :order="order.data"></tracking-package-header>
            </cell>
            <cell class="cell-line"></cell>
            <cell>
                <tracking-package-item :address="order.order_data.address"></tracking-package-item>
            </cell>
            <cell v-for="item in order.data.origin_info.trackinfo">
                <tracking-package-item :order="item"></tracking-package-item>
            </cell>
            <cell>
                <tracking-package-item :shippedTime="order.order_data.shipped_time"></tracking-package-item>
            </cell>
            <cell>
                <tracking-package-item :paidTime="order.order_data.paid_time"></tracking-package-item>
            </cell>
        </list>
    </div>
</template>
<script>
import header from './header';
import trackingPackageHeader from './trackingPackageHeader';
import trackingPackageItem from './trackingPackageItem';
import { Utils } from 'weex-ui';
import { baseUrl } from '../../../config/apis';
import { TOKEN } from './config';

export default {
    components: {
        'top-header': header,
        'tracking-package-header': trackingPackageHeader,
        'tracking-package-item': trackingPackageItem
    },
    eros: {
        appeared (params, option) {
            if (params) {
                this.id = params.id
                this.getOrderTracking()
            }
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 48 - 2) + 'px' }
    },
    data () {
        return {
            title: 'Tracking Package',
            id: false,
            order: {
                'order_data': {},
                'data': {
                    'tracking_number': ' ',
                    'carrier_code': ' ',
                    'origin_info': {
                        trackinfo: []
                    }
                }
            }
        }
    },
    methods: {
        getOrderTracking () {
            this.$fetch({
                method: 'GET', // 大写
                url: `${baseUrl}/order/tracking/${this.id}/`,
                header: {
                    Authorization: 'Bearer ' + TOKEN
                }
            }).then(resData => {
                // 成功回调
                this.order = resData
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
