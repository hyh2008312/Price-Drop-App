<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <div class="container" :style="height">
            <wxc-indexlist class="city-mt" :normal-list="list"
                           :hot-list-config="hotListConfig"
                           :city-location-config="cityLocationConfig"
                           @wxcIndexlistItemClicked="wxcIndexlistItemClicked"
                           :show-index="showIndex"
            ></wxc-indexlist>
        </div>
    </div>
</template>
<script>
    import header from './header';
    import addressItem from './addressItem';
    import orderDetailItem from './orderDetailItem';
    import editAddressBottom from './editAddressBottom';
    import { Utils, WxcIndexlist } from 'weex-ui';
    import { STATE } from './config'

    export default {
        components: {
            WxcIndexlist,
            'top-header': header,
            'address-item': addressItem,
            'order-detail-item': orderDetailItem,
            'edit-address-bottom': editAddressBottom
        },
        eros: {
            beforeAppear (params) {
                this.getState()
            }
        },
        created () {
            const pageHeight = Utils.env.getScreenHeight()
            this.height = { height: (pageHeight - 112 - 44 - 2) + 'px' }
        },
        data () {
            return {
                title: 'State',
                list: [],
                showIndex: true,
                hotListConfig: {},
                cityLocationConfig: {}
            }
        },
        methods: {
            back () {
                this.$router.finish()
            },
            wxcIndexlistItemClicked (e) {
                this.$event.emit('state', e.item)
                this.$router.finish()
            },
            getState () {
                this.$storage.get('state').then((data) => {
                    if (data) {
                        this.list = []
                        for (const item of data) {
                            this.list.push({
                                id: item.id,
                                name: item.name,
                                desc: item.code,
                                pinYin: item.name,
                                py: item.name
                            })
                        }
                    }
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


</style>
