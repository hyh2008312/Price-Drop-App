<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height" v-if="addressList.length > 0">
            <cell class="cell-bottom" v-for="(e, i) in addressList">
                <address-item :address="e" :index="i" @delete="deleteShipping"></address-item>
            </cell>
        </list>
        <div class="container-1" :style="height" v-if="addressList.length == 0">
            <div class="container-2">
                <image class="pay-image" src="bmlocal://assets/empty.png"></image>
            </div>
            <text class="address-title">You have no shipping address information.</text>
        </div>
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
import { baseUrl } from '../../../config/apis';

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
        this.height = { height: (pageHeight - 112 - 112 - 48 - 2) + 'px' }
        this.getAddress()
    },
    data () {
        return {
            title: 'Choose Address',
            addressList: null
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
                url: `${baseUrl}/address/shipping/detail/${event.data.id}/`,
                header: {
                    Authorization: 'Bearer ' + TOKEN
                }
            }).then(resData => {
                this.$notice.toast({
                    message: 'Delete address success!'
                })
                const address = this.addressList[event.data.index]
                if (address.isDefault) {
                    this.$storage.get('user').then((data) => {
                        let user = data
                        user.defaultAddress = null
                        this.$storage.set('user', user)
                    })
                }
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

    .iconfont{
        font-family: iconfont;
    }

    .wrapper{
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
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
        background-color: #f1f1f1;
    }

    .container-1{
        margin-top: 2px;
        width: 750px;
        background-color: #fff;
    }

    .cell-bottom{
        padding-bottom: 16px;
    }

    .container-2{
        margin-top: 86px;
        width: 750px;
        align-items: center;
    }

    .pay-image{
        width: 202px;
        height: 202px;
    }

    .address-title{
        margin-top: 32px;
        font-size: 28px;
        line-height: 34px;
        text-align: center;
    }


</style>
