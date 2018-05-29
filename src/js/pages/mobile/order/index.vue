<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <top-channel @change="onchange" :activeIndex="activeIndex" :channelList="channelList"></top-channel>
        <div :style="height" class="box">
            <slider class="slider" infinite="false" ref="slider" @change="onchangeTab" :index="activeIndex">
                <order-list v-for="(i, index) in channelList" :key="index"
                            :activeIndex="index" :index="activeIndex" ></order-list>
            </slider>
        </div>
    </div>
</template>
<script>
import header from './header';
import orderList from './orderList';
import topChannel from './topChannel';
import { CHANNELLIST } from './config';
import { Utils } from 'weex-ui';

export default {
    components: {
        'order-list': orderList,
        'top-header': header,
        'top-channel': topChannel
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 96 - 48) + 'px' }
        this.getChannel()
        this.$router.getParams().then(params => {
            if (params && params.tab) {
                this.activeIndex = params.tab
            }
        })
    },
    data () {
        return {
            title: 'My Orders',
            activeIndex: 0,
            index: 0,
            channelList: []
        }
    },
    methods: {
        getChannel () {
            this.channelList = [...CHANNELLIST]
        },
        onchange (index) {
            this.activeIndex = index;
        },
        onchangeTab (event) {
            this.activeIndex = event.index
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
