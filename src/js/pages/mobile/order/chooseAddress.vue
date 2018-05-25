<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom" v-for="(e, i) in addressList">
                <address-item :address="e" :index="i" @delete="deleteShipping"></address-item>
            </cell>
        </list>
        <address-bottom></address-bottom>
    </div>
</template>
<script>
import header from './header';
import addressItem from './addressItem';
import orderDetailItem from './orderDetailItem';
import addressBottom from './addressBottom';
import { Utils } from 'weex-ui';
import { TOKEN } from './config';

export default {
    components: {
        'top-header': header,
        'address-item': addressItem,
        'order-detail-item': orderDetailItem,
        'address-bottom': addressBottom
    },
    eros: {
        backAppeared (params, options) {
            this.getAddress()
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 112 - 44 - 2) + 'px' }
        this.getAddress()
    },
    data () {
        return {
            title: 'Choose Address',
            addressList: []
        }
    },
    methods: {
        getAddress () {
            this.$fetch({
                method: 'GET',
                name: 'address.shipping.list',
                data: {},
                header: {
                    Authorization: 'Bearer ' + TOKEN
                }
            }).then(data => {
                this.addressList = [...data];
            }, error => {
                // 错误回调
                this.$notice.toast({
                    message: error
                })
            })
        },
        back () {
            this.$router.finish()
        },
        deleteShipping (event) {
            this.$fetch({
                method: 'DELETE', // 大写
                url: `http://47.104.171.91/address/shipping/detail/${event.data.id}/`,
                header: {
                    Authorization: 'Bearer ' + TOKEN
                }
            }).then(resData => {
                this.$notice.toast({
                    message: 'Delete address success!'
                })
                this.addressList.splice(event.data.index, 1)
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

</style>
