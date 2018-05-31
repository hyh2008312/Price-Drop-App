<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom" @click="jumpAddress">
                <tracking-package-header :order="order"></tracking-package-header>
            </cell>
            <cell class="cell-line"></cell>
            <cell v-for="item in order.data.trackinfo">
                <tracking-package-item></tracking-package-item>
            </cell>
            <cell>
                <tracking-package-item></tracking-package-item>
            </cell>
            <cell>
                <tracking-package-item></tracking-package-item>
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
                // this.getOrderTracking()
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
                'meta': {
                    'code': 200,
                    'type': 'Success',
                    'message': 'Success'
                },
                'data': {
                    'id': 'ec3710e75880fd1e9e44e3ac20de6f73',
                    'tracking_number': '413767243',
                    'carrier_code': 'gati-kwe',
                    'status': 'delivered',
                    'created_at': '2018-05-26T14:23:25+08:00',
                    'updated_at': '2018-05-26T14:23:45+08:00',
                    'order_create_time': null,
                    'customer_email': '',
                    'title': '',
                    'order_id': null,
                    'comment': null,
                    'customer_name': null,
                    'original_country': 'Ratangarh',
                    'destination_country': 'Manipal',
                    'itemTimeLength': 7,
                    'stayTimeLength': 1591,
                    'origin_info': {
                        'ItemReceived': '2014-01-10 01:51',
                        'ItemDispatched': null,
                        'DepartfromAirport': null,
                        'ArrivalfromAbroad': null,
                        'CustomsClearance': null,
                        'DestinationArrived': null,
                        'weblink': null,
                        'phone': null,
                        'carrier_code': 'gati-kwe',
                        'trackinfo': [{
                            'Date': '2014-01-16 19:23',
                            'StatusDescription': 'Delivered at destination city',
                            'Details': 'Manipal',
                            'checkpoint_status': 'delivered'
                        }, {
                            'Date': '2014-01-16 11:20',
                            'StatusDescription': 'Arrived',
                            'Details': 'Manipal',
                            'checkpoint_status': 'transit'
                        }, {
                            'Date': '2014-01-15 17:57',
                            'StatusDescription': 'Out for next station',
                            'Details': 'Bangalore',
                            'checkpoint_status': 'transit'
                        }, {
                            'Date': '2014-01-14 05:11',
                            'StatusDescription': 'Arrived',
                            'Details': 'Bangalore',
                            'checkpoint_status': 'transit'
                        }, {
                            'Date': '2014-01-12 02:52',
                            'StatusDescription': 'Out for next station',
                            'Details': 'Indore 247',
                            'checkpoint_status': 'transit'
                        }, {
                            'Date': '2014-01-11 20:38',
                            'StatusDescription': 'Arrived',
                            'Details': 'Indore 247',
                            'checkpoint_status': 'transit'
                        }, {
                            'Date': '2014-01-11 01:27',
                            'StatusDescription': 'Out for next station',
                            'Details': 'Jaipur',
                            'checkpoint_status': 'transit'
                        }, {
                            'Date': '2014-01-10 10:52',
                            'StatusDescription': 'Arrived',
                            'Details': 'Jaipur',
                            'checkpoint_status': 'transit'
                        }, {
                            'Date': '2014-01-10 01:51',
                            'StatusDescription': 'Out for next station',
                            'Details': 'Ratangarh',
                            'checkpoint_status': 'transit'
                        }]
                    },
                    'service_code': '',
                    'substatus': null,
                    'lastEvent': 'Delivered at destination city,Manipal,2014-01-16 19:23',
                    'lastUpdateTime': '2014-01-16 19:23'
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
