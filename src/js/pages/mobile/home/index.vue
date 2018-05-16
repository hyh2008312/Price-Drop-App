<template>
    <div class="wrapper">
        <div class="status-bar"></div>
        <home-header v-if="headerShow"></home-header>
        <top-channel class="channel" @change="onchange" ref="topChannel" :activeIndex="activeIndex"></top-channel>
        <div :style="height" class="box" @touchstart="ontouchstart" @touchmove="ontouchmove" @touchend="ontouchend">
            <slider class="slider" infinite="false" ref="slider" @change="onchangeTab" :index="activeIndex">
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
import { Utils } from 'weex-ui';

export default {
    components: {
        'home-header': header,
        'top-channel': topChannel,
        suggest,
        category
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 44) + 'px' }
    },
    data () {
        return {
            channelList: [],
            activeIndex: 0,
            headerShow: true,
            positionX: 0,
            positionY: 0,
            deltaX: 0,
            deltaY: 0
        }
    },
    methods: {
        ontouchstart (event) {
            this.positionX = event.changedTouches[0].screenX;
            this.positionY = event.changedTouches[0].screenY;
        },
        ontouchmove (event) {
            const moveX = event.changedTouches[0].screenX;
            const moveY = event.changedTouches[0].screenY;
            this.deltaX = moveX - this.positionX;
            this.deltaY = moveY - this.positionY;
            if (this.deltaY > 0 && Math.abs(this.deltaY) > Math.abs(this.deltaX)) {
                this.headerShow = true
            }
            if (this.deltaY < 0 && Math.abs(this.deltaY) > Math.abs(this.deltaX)) {
                this.headerShow = false;
            }
        },
        ontouchend (event) {
            this.positionX = 0
            this.positionY = 0
            this.deltaX = 0
            this.deltaY = 0
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
