<template>
    <div class="wrapper" ref="header">
        <!--<home-header></home-header>-->
        <div class="status-bar"></div>
        <div class="header" v-if="!isHeaderBg" :style="{ backgroundColor: bgColor}">
            <div class="header-search" @click="jumpSearch">
                <text class="header-icon iconfont">&#xe621;</text>
                <text class="header-title">What are you looking for?</text>
            </div>
            <div class="box-bg" v-if="false" @click="openNotification">
                <text class="box-txt-icon iconfont">&#xe753;</text>
                <text class="box-dot" v-if="unread>0">{{unread > 99? '99+': unread}}</text>
            </div>
            <div class="box-bg"  @click="openCart">
                <text class="box-txt-icon iconfont">&#xe767;</text>
                <text class="box-dot" v-if="cartNum>0">{{cartNum > 99? '99+': cartNum}}</text>
            </div>
        </div>
        <div class="header-new" :style="{visibility : isHeaderBg? 'visible': 'hidden'}">
            <div class="header-search-new" @click="jumpSearch">
                <text class="header-icon-new iconfont">&#xe621;</text>
                <text class="header-title-new">What are you looking for?</text>
            </div>
            <div class="box-bg" @click="openNotification">
                <text class="box-txt-icon-new iconfont">&#xe767;</text>
                <text class="box-dot" v-if="unread>0">{{unread > 99? '99+': unread}}</text>
            </div>
        </div>
        <div class="slider-wrap-1">
            <div class="slider-bg-1" :style="{ backgroundColor: bgColor}"></div>
        </div>
        <div :style="height" class="box">
            <suggest :isHeaderBg="isHeaderBg" @colorChange="colorChange" @bannerColor="bannerColor" @openNewMask="openMask"></suggest>
        </div>
        <WxcMask
            height="587"
            width="397"
            border-radius="16"
            duration="200"
            mask-bg-color="rgba(255, 255, 255, 0)"
            :has-animation="true"
            :has-overlay="true"
            :show-close="false"
            :show="newShow"
            @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="maskcontent">
                <div class="m-top">
                    <image src="bmlocal://assets/home/mask-head.png" style="width:397px;height:162px; "></image>
                </div>
                <text class="iconfont m-ic">&#xe632;</text>
                <text class="m-c">₹20</text>
                <div class="mid-content">
                    <text class="m-c-w">You just got ₹20 cash bonus for shopping now.</text>
                    <div class="m-c-time">
                        <text class="m-c-word">Expire in </text>
                        <div  class="overflow-center-time">
                            <div class="center-time" v-if="asecond!=''">
                                <text class="center-time-hh">{{ahour}}</text>
                                <text style="font-size: 20px;">:</text>
                                <text class="center-time-hh">{{amin}}</text>
                                <text style="font-size: 20px;">:</text>
                                <text class="center-time-hh">{{asecond}}</text>
                            </div> <!-- 正常显示的 -->

                            <div class="center-time" v-if="asecond==''">
                                <text class="center-time-hh-empty"></text>

                                <text style="font-size: 24px; padding-top: 10px;">:</text>
                                <text class="center-time-hh-empty"></text>
                                <text style="font-size: 24px; padding-top: 10px;">:</text>
                                <text class="center-time-hh-empty"></text>
                            </div>  <!-- 做空白处理的 -->
                        </div>
                    </div>
                </div>
                <image src="bmlocal://assets/home/voucher-part.png" style="width: 398px;height: 28px;"></image>
                <div class="m-bottom">
                    <div class="overflow-mg">
                        <div style="border-radius: 32px;">
                            <image src="bmlocal://assets/home/maskBtn.png" style="width: 238px;height: 56px;"></image>
                        </div>
                    </div>

                    <text class="mb-word">Go Shopping Now</text>
                </div>
            </div>
        </WxcMask>
    </div>
</template>
<script>
import header from './header';
import suggest from './suggest';
import category from './category';
import topChannel from './topChannel';
import { Utils, WxcMask, WxcCountdown } from 'weex-ui';
import { baseUrl } from '../../../config/apis';

const animation = weex.requireModule('animation');
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

export default {
    components: {
        'home-header': header,
        'top-channel': topChannel,
        WxcMask,
        suggest, WxcCountdown,
        category
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight();
        this.height = { height: (pageHeight - 112 - 112 - 48) + 'px' };
        this.initGoogleAnalytics();
        this.$event.on('getRead', param => {
            this.unread = param.unread
        });
        this.$event.on('login', params => {
            this.getCartNumApi();
        });
        this.$event.on('cartNum', params => {
            this.getUnread();
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
            bgColor: '#EF8A31',
            cartNum: '',
            newShow: false,
            aday: '',
            ahour: '',
            amin: '',
            asecond: '',
            TIME: new Date().getTime() + 86400000
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
            this.$storage.get('user').then((data) => {
                if (data) {
                    this.getCartNumApi(data.id);
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
        getCartNumApi (id) {
            this.$fetch({
                method: 'GET',
                url: `${baseUrl}/cart/count/${id}/`,
                header: {
                    isLoginPop: true
                }
            }).then(data => {
                this.cartNum = data.count;
            });
        },
        openNotification () {
            const user = this.$storage.getSync('user');
            if (user) {
                this.$router.open({
                    name: 'my.notice',
                    type: 'PUSH'
                });
            } else {
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                });
            }
        },
        openCart () {
            const user = this.$storage.getSync('user');
            if (user) {
                this.$router.open({
                    name: 'cart',
                    type: 'PUSH'
                });
            } else {
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
        openMask () {
            this.newShow = true;
            this.countDate(this.TIME)
        },
        wxcMaskSetShareHidden () {
            this.newShow = false;
        },
        colorChange (event) {
            this.isHeaderBg = event.data.isHeaderBg;
        },
        bannerColor (event) {
            this.bgColor = event.data.bgColor;
        },
        countDate (time) {
            const self = this
            // if (this.purchaseMethod == 'flash') {
            setInterval(() => {
                this.NOW_DATE = new Date().getTime();

                const total = (new Date(time).getTime() - this.NOW_DATE) / 1000

                const day = Math.floor(total / (24 * 60 * 60))// 整天

                self.aday = day
                const afterDay = total - day * 24 * 60 * 60;
                self.ahour = Math.floor(afterDay / (60 * 60)); // 小时
                const afterHour = total - day * 24 * 60 * 60 - self.ahour * 60 * 60;
                self.amin = Math.floor(afterHour / 60); // 分钟
                if (self.amin < 10) {
                    self.amin = '0' + self.amin
                }

                const afterMin = total - day * 24 * 60 * 60 - self.ahour * 60 * 60 - self.amin * 60;
                self.asecond = Math.floor(afterMin)// 秒
                if (self.asecond < 10) {
                    self.asecond = '0' + self.asecond
                }
                // 加上减掉的天数
                self.ahour += (self.aday * 24)

                if (self.ahour < 10) {
                    self.ahour = '0' + self.ahour
                }
                // this.$notice.toast({
                //     message: self.aday + '天' + self.ahour + ':' + self.amin + ':' + self.asecond
                // })
            }, 1000);
            // }
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
