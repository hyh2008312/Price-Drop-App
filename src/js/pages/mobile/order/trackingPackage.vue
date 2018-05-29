<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="cell-bottom" @click="jumpAddress">
                <tracking-package-header></tracking-package-header>
            </cell>
            <cell class="cell-line"></cell>
            <cell>
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

export default {
    components: {
        'top-header': header,
        'tracking-package-header': trackingPackageHeader,
        'tracking-package-item': trackingPackageItem
    },
    eros: {
        backAppeared (params, options) {
        },
        beforeAppear (params, options) {
        },
        appeared (params, option) {
            this.order = params
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 48 - 2) + 'px' }
    },
    data () {
        return {
            title: 'Tracking Package'
        }
    },
    methods: {}
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
