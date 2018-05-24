<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom">
                <address-item></address-item>
            </cell>
            <cell class="cell-bottom">
                <address-item></address-item>
            </cell>
            <cell class="cell-bottom">
                <address-item></address-item>
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

export default {
    components: {
        'top-header': header,
        'address-item': addressItem,
        'order-detail-item': orderDetailItem,
        'address-bottom': addressBottom
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 112 - 44 - 2) + 'px' }
    },
    data () {
        return {
            title: 'Choose Address'
        }
    },
    methods: {
        back () {
            this.$router.finish()
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
