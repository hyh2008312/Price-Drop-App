<template>
    <div class="wrapper">
        <div class="status-bar"></div>
        <home-header v-if="headerShow"></home-header>
        <top-channel @change="onchange" ref="topChannel" :activeIndex="activeIndex"></top-channel>
        <div :style="height" class="box">
            <slider class="slider" infinite="false" ref="slider" @swipe="onswipe" @change="onchangeTab" :index="activeIndex">
                <suggest></suggest>
                <category v-for="(i, index) in channelList" v-if="index > 0" :activeIndex="index" :index="activeIndex"></category>
            </slider>
        </div>
    </div>
</template>
<script>
import header from './header';
import suggest from './suggest';
import category from './category';
import topChannel from './topChannel';
import { CHANNELLIST } from './config';
import { Utils } from 'weex-ui';

export default {
    components: {
        'home-header': header,
        'top-channel': topChannel,
        suggest,
        category
    },
    created () {
        this.getChannel()
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 44) + 'px' }
    },
    data () {
        return {
            channelList: [],
            activeIndex: 0,
            headerShow: true
        }
    },
    methods: {
        onswipe (event) {
            if (event.direction == 'up') {
                this.headerShow = false;
            } else if (event.direction == 'down') {
                this.headerShow = true;
            }
        },
        getChannel () {
            this.channelList = CHANNELLIST
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
