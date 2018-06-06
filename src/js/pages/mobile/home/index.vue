<template>
    <div class="wrapper" ref="header">
        <!--<home-header></home-header>-->
        <div class="status-bar"></div>
        <top-channel @change="onchange" ref="topChannel"
                     :activeIndex="activeIndex" :channelList="channelList"></top-channel>
        <div :style="height" class="box">
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
import { CHANNELLIST } from './config'

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
        this.height = { height: (pageHeight - 112 - 112 - 48) + 'px' }
        this.getChannel()
    },
    data () {
        return {
            channelList: [],
            activeIndex: '',
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
        onchange (event) {
            this.activeIndex = event.data.index
        },
        onchangeTab (event) {
            if (event.index == 0) {
                this.activeIndex = ''
            } else {
                this.activeIndex = event.index
            }
        },
        getChannel () {
            this.channelList = [...CHANNELLIST];
            this.$fetch({
                method: 'GET', // 大写
                name: 'category.list', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
                data: {}
            }).then(data => {
                this.channelList = [];
                let index = 0;
                const channel = CHANNELLIST;
                this.channelList.push({
                    name: 'Featured',
                    width: 56,
                    left: 0,
                    id: '0'
                })

                for (const item of data) {
                    index++
                    this.channelList.push({
                        name: item.fullName,
                        left: channel[index].left,
                        id: item.id
                    })
                }
            }, error => {})
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
