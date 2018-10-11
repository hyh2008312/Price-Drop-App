<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height" v-if="addressList.length > 0">
            <cell class="cell-bottom" v-for="(e, i) in addressList">
                <address-item :address="e" :index="i" @delete="deleteShipping" @chooseAddress="chooseAddress" :source="source"></address-item>
            </cell>
        </list>
        <div class="container-1" :style="height" v-if="addressList.length == 0">
            <div class="container-2">
                <image class="pay-image" src="bmlocal://assets/empty.png"></image>
            </div>
            <text class="address-title">You have no shipping address information.</text>
        </div>
        <address-bottom></address-bottom>
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
                <text class="popup-delete-title">Do you want to delete this address? </text>
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
import addressItem from './addressItem';
import orderDetailItem from './orderDetailItem';
import addressBottom from './addressBottom';
import { Utils, WxcMask } from 'weex-ui';
import { baseUrl } from '../../../config/apis';
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

export default {
    components: {
        'top-header': header,
        'address-item': addressItem,
        'order-detail-item': orderDetailItem,
        'address-bottom': addressBottom,
        WxcMask
    },
    eros: {
        appeared (params, options) {
            if (params) {
                this.source = params.source
            }
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 112 - 48 - 2) + 'px' }
        googleAnalytics.trackingScreen('My Address');
        this.getAddress();
        this.$event.on('login', params => {
            this.getAddress();
        });
        this.$event.on('editAddress', params => {
            this.getAddress();
        });
    },
    destory () {
        this.$event.off('login')
    },
    data () {
        return {
            title: 'My Address',
            addressList: false,
            source: false,
            isDeleteShow: false,
            deleteId: -1,
            deleteIndex: 0
        }
    },
    methods: {
        getAddress () {
            this.$notice.loading.show();
            this.$fetch({
                method: 'GET',
                name: 'address.shipping.list',
                data: {},
                header: {
                    needAuth: true
                }
            }).then(data => {
                this.$notice.loading.hide();
                this.addressList = [...data];
            }, error => {
                this.isShow = false;
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
            this.isDeleteShow = true;
            this.deleteIndex = event.data.index;
            this.deleteId = event.data.id;
        },
        chooseAddress (event) {
            this.addressList.forEach((e, i) => {
                if (i == event.data.index) {
                    e.isDefault = true;
                } else {
                    e.isDefault = false;
                }
            })
        },
        popupDeleteClick () {
            this.isDeleteShow = false;
        },
        deleteOrderConfirm () {
            this.closeDeletePop();
            const that = this;
            this.$fetch({
                method: 'DELETE', // 大写
                url: `${baseUrl}/address/shipping/detail/${this.deleteId}/`,
                header: {
                    needAuth: true
                }
            }).then(resData => {
                this.$notice.toast({
                    message: 'Delete address success!'
                });
                const address = that.addressList[that.deleteIndex];
                if (address.isDefault) {
                    that.$storage.get('user').then((data) => {
                        const user = data
                        user.defaultAddress = false
                        that.$storage.set('user', user)
                    })
                }
                that.addressList.splice(that.deleteIndex, 1)
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

    .popup-delete-container{
        width: 514px;
        height: 258px;
        border-radius: 8px;
        background-color: #fff;
        padding: 32px;
        justify-content: space-between;
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
