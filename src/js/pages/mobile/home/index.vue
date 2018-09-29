<template>
    <div class="wrapper" ref="header">
        <!--<home-header></home-header>-->
        <div class="status-bar"></div>
        <div class="header" v-if="!isHeaderBg" :style="{ backgroundColor: bgColor}">
            <div class="header-search" @click="jumpSearch">
                <text class="header-icon iconfont">&#xe621;</text>
                <text class="header-title">What are you looking for?</text>
            </div>
            <div class="box-bg" @click="openNotification">
                <text class="box-txt-icon iconfont">&#xe753;</text>
                <text class="box-dot" v-if="unread>0">{{unread > 99? '99+': unread}}</text>
            </div>

        </div>
        <div class="header-new" :style="{visibility : isHeaderBg? 'visible': 'hidden'}">
            <div class="header-search-new" @click="jumpSearch">
                <text class="header-icon-new iconfont">&#xe621;</text>
                <text class="header-title-new">What are you looking for?</text>
            </div>
            <div class="box-bg" @click="openNotification">
                <text class="box-txt-icon-new iconfont">&#xe753;</text>
                <text class="box-dot" v-if="unread>0">{{unread > 99? '99+': unread}}</text>
            </div>
        </div>
        <div class="slider-wrap-1">
            <div class="slider-bg-1" :style="{ backgroundColor: bgColor}"></div>
        </div>
        <div :style="height" class="box">
            <suggest :isHeaderBg="isHeaderBg" @colorChange="colorChange" @bannerColor="bannerColor"></suggest>
        </div>
    </div>
</template>
<script>
import header from './header';
import suggest from './suggest';
import category from './category';
import topChannel from './topChannel';
import { Utils } from 'weex-ui';
import { CHANNELLIST } from './config';

const animation = weex.requireModule('animation');
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

export default {
    components: {
        'home-header': header,
        'top-channel': topChannel,
        suggest,
        category
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight();
        this.height = { height: (pageHeight - 112 - 112 - 48) + 'px' };
        this.initGoogleAnalytics();
        this.$event.on('getRead', param => {
            this.unread = param.unread
        });
        this.getUnread();
    },
    data () {
        return {
            channelList: [],
            activeIndex: '0',
            headerShow: true,
            positionX: 0,
            positionY: 0,
            deltaX: 0,
            deltaY: 0,
            headAni: false,
            unread: 0,
            isFirstLoad: false,
            isHeaderBg: false,
            bgColor: '#EF8A31'
        }
    },
    computed: {
        bgColor: {
            get: function () {
                return this.bgColor
            },

            set: function (v) {
                this.bgColor = v
            }
        }

    },
    methods: {
        initGoogleAnalytics () {
            googleAnalytics.trackingScreen('Home');
        },
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
                this.headerShow = true;
                this.changeHeader();
            }
            if (this.deltaY < 0 && Math.abs(this.deltaY) > Math.abs(this.deltaX)) {
                this.headerShow = false;
                this.changeHeader();
            }
        },
        ontouchend (event) {
            this.positionX = 0;
            this.positionY = 0;
            this.deltaX = 0;
            this.deltaY = 0;
        },
        onchange (event) {
            this.activeIndex = event.data.index
        },
        onchangeTab (event) {
            if (event.index == 0) {
                this.activeIndex = '0';
            } else {
                this.activeIndex = event.index;
            }
        },
        changeHeader () {
            if (!this.headAni) {
                if (this.headerShow) {
                    this.headAni = true;
                    animation.transition(this.$refs.header, {
                        styles: {
                            transform: 'translateY(0px)'
                        },
                        duration: 300,
                        timingFunction: 'ease',
                        delay: 0
                    }, () => {
                        this.headAni = false;
                    });
                } else {
                    this.headAni = true;
                    animation.transition(this.$refs.header, {
                        styles: {
                            transform: 'translateY(-104px)'
                        },
                        duration: 300,
                        timingFunction: 'ease',
                        delay: 0
                    }, () => {
                        this.headAni = false;
                    });
                }
            }
        },
        getUnread () {
            this.$storage.get('token').then((data) => {
                if (data && !this.isFirstLoad) {
                    this.isFirstLoad = true;
                    this.getUreadApi();
                }
            });
        },
        getUreadApi () {
            this.$fetch({
                method: 'GET',
                name: 'user.userprofile',
                header: {
                    isLoginPop: true
                }
            }).then((res) => {
                this.unread = res.unreadNumber;
            }).catch((res) => {});
        },
        openNotification () {
            const user = this.$storage.getSync('user');
            if (user) {
                this.$router.open({
                    name: 'my.notice',
                    type: 'PUSH'
                });
            } else {
                this.$event.on('login', params => {
                    this.getUreadApi();
                });
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                });
            }
        },
        jumpSearch () {
            this.$router.open({
                name: 'search',
                type: 'PUSH'
            });
        },
        colorChange (event) {
            this.isHeaderBg = event.data.isHeaderBg;
        },
        bannerColor (event) {
            this.bgColor = event.data.bgColor;
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
