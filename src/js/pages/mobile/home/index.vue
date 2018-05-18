<template>
    <div class="wrapper" ref="header">
        <home-header></home-header>
        <div class="status-bar"></div>
        <top-channel @change="onchange" ref="topChannel"
                     :activeIndex="activeIndex" :channelList="channelList"></top-channel>
        <div :style="height" class="box" :class="[headerShow? '': 'wrapper-bottom']"
             @touchstart="ontouchstart" @touchmove="ontouchmove" @touchend="ontouchend" >
            <slider class="slider" infinite="false" ref="slider" @change="onchangeTab" :index="activeIndex">
                <suggest></suggest>
                <category v-for="(i, index) in channelList" v-if="index > 0" :key="i.id"
                          :activeIndex="index" :index="activeIndex" :id="i.id"></category>
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

const animation = weex.requireModule('animation')

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
        this.getChannel()
    },
    data () {
        return {
            channelList: [],
            activeIndex: 0,
            headerShow: true,
            positionX: 0,
            positionY: 0,
            deltaX: 0,
            deltaY: 0,
            headAni: false
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
                this.changeHeader()
            }
            if (this.deltaY < 0 && Math.abs(this.deltaY) > Math.abs(this.deltaX)) {
                this.headerShow = false
                this.changeHeader()
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
        },
        getChannel () {
            const data = this.$storage.getSync('channel');
            this.channelList = [];
            let left = 0;
            const firstCat = '推荐';
            this.channelList.push({
                name: firstCat,
                width: 56,
                left: 0,
                id: '0'
            })
            left += 48 + firstCat.length * 28;
            for (const item of data) {
                const width = this.getLength(item.fullName) * 14
                this.channelList.push({
                    name: item.fullName,
                    width: width,
                    left: left,
                    id: item.id
                })
                left += 48 + this.getLength(item.fullName) * 14
            }
        },
        getLength (str) {
            let realLength = 0;
            let charCode = -1;
            const len = str.length;

            for (let i = 0; i < len; i++) {
                charCode = str.charCodeAt(i);
                if (charCode >= 0 && charCode <= 128) {
                    realLength += 1;
                    if (charCode >= 65 && charCode <= 90) {
                        realLength += 0.05;
                    }
                } else {
                    realLength += 2;
                }
            }
            return realLength;
        },
        changeHeader () {
            if (!this.headAni) {
                if (this.headerShow) {
                    this.headAni = true
                    animation.transition(this.$refs.header, {
                        styles: {
                            transform: 'translateY(0px)'
                        },
                        duration: 300,
                        timingFunction: 'ease',
                        delay: 0
                    }, () => {
                        this.headAni = false
                    });
                } else {
                    this.headAni = true
                    animation.transition(this.$refs.header, {
                        styles: {
                            transform: 'translateY(-104px)'
                        },
                        duration: 300,
                        timingFunction: 'ease',
                        delay: 0
                    }, () => {
                        this.headAni = false
                    });
                }
            }
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
