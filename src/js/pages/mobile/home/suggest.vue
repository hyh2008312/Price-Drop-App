<!--suppress ALL -->
<template>
    <div class="app-wrapper">
        <list class="transparent" offset-accuracy="50" loadmoreoffset="200" @loadmore="onLoadingMore" v-if="hasWifi" >
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell class="slider-wrap">
                <yx-slider class="slider-container" :imageList="YXBanners" @changeColor="changeColor" v-if="isLoaded"></yx-slider>
            </cell>

            <cell   v-if="isLoaded">
                <block-4 :items="noticeList" v-if="noticeList.length > 0"></block-4>
            </cell>
            <cell class="ac-wrap" >
                <block-9 v-on:luckyDraw="goLucky" v-on:reward="goReward" ></block-9>
            </cell>
            <cell v-if="false">
                <block-1 :category="category"></block-1>
            </cell>
            <cell v-if="firstLogin&&newGift">
                <div class="new-co" @click="openNewCo">
                    <image class="new-co-img" src="bmlocal://assets/home/new-gift.png" ></image>
                    <text class="new-co-w">New User Gift</text>
                </div>
            </cell>
            <cell class="cell-top" v-if="false"></cell>
            <cell class="cell-button" v-if="false">
                <div class="home-drop-1">
                    <block-6 :drops="drops"></block-6>
                </div>
            </cell>
            <cell v-if="isLoaded && activity && activity.length > 0" style="background-color: #f4f4f4">
                <block-2 :goodsList="activity" :time="time" v-on:zero="getActivity"></block-2>
            </cell>
            <cell class="cell-button" v-if="false">
                <block-5 :logo="block5.items"></block-5>
            </cell>
            <header v-if="false">
                <tab @tabTo="onTabTo" :items="tabsItems" :activeTab="activeTab"></tab>
            </header>
            <cell class="gd-bg-gray" v-for="item in products" v-if="isLoaded">
                <div class="gd-bg-mt" v-if="item.id">
                    <div class="gd-bg" @click="jumpCategory(item)">
                        <preload class="gd-img-image" :src="item.bgSrc"></preload>
                        <preload class="gd-img-image-1" :src="item.src" :style="{width: item.width}"></preload>
                        <text class="img-tlt">{{item.name}}</text>
                        <div class="gd-button">
                            <text class="gd-button-1">VIEW MORE</text>
                            <text class="gd-button-2 iconfont" :style="{color: item.color}">&#xe626;</text>
                        </div>
                    </div>
                </div>
                <div class="gd-bg-mrt" v-if="!item.id">
                    <block-8 :item="item.items"></block-8>
                </div>
            </cell>
            <cell class="gd-bg-white-gray"></cell>
            <cell v-if="goods3.length > 0 && isLoaded">
                <text class="home-title">Featured</text>
            </cell>
            <cell v-for="(item,index) in goods3" v-if="isLoaded">
                <block-3 :goods="item" :tab="tabKey"></block-3>
            </cell>
            <cell class="cell-fixed" v-if="goods3.length > 0 && isLoaded"></cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </loading>
        </list>
        <div class="loading-top" v-if="!isLoaded">
            <image class="loading-icon-1" src="bmlocal://assets/loading-3.gif"></image>
        </div>
        <no-wifi v-if="!hasWifi" @onReload="loadingDown"></no-wifi>
    </div>
</template>
<script>
import { Utils } from 'weex-ui';
import refresher from '../common/refresh';
import noWifi from '../common/noWifi';
import YXSlider from './YXSlider';
import tab from './tab';
import block1 from './block1';
import block2 from './block2';
import block3 from './block3';
import block4 from './block4';
import block5 from './block5';
import block6 from './block6';
import block7 from './block7';
import block8 from './block8';
import block9 from './block9';
import { TAB, CHANNELLIST, CHANNELLIST1, PRODUCTS } from './config';
import preload from '../common/preloadImg';

const SCROLL_FULL_WIDTH = 750;
const dom = weex.requireModule('dom');
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

export default {
    components: {
        'refresher': refresher,
        'yx-slider': YXSlider,
        'tab': tab,
        'block-2': block2,
        'block-3': block3,
        'block-4': block4,
        'block-5': block5,
        'block-1': block1,
        'block-6': block6,
        'block-7': block7,
        'block-8': block8,
        'block-9': block9,
        noWifi,
        preload
    },
    props: ['isHeaderBg'],
    created () {
        this.init();
        this.$event.on('first', parmas => {

            this.$storage.get('user').then((res) => {
                if(res) {
                    this.user = res;
                    if(this.user.firstLogin.toString()=='false'){
                        this.newGift = false
                    }
                    this.$storage.get('firstLogin').then((data) => {
                        if(data) {
                            this.firstLogin = data
                            this.openNewCo()
                            this.user.firstLogin = false
                            this.$storage.set('user', this.user)
                        }
                    });
                }
            })
        })
        this.user = this.$storage.getSync('user');
        if(this.user!=''){
            if(!this.user.firstLogin){
                this.firstLogin = false;
            }
        }
    },
    data () {
        return {
            YXBanners: [],
            noticeList: [],
            block4: {
                items: []
            },
            block5: {
                items: []
            },
            activity: false,
            time: false,
            tabsItems: [],
            activeTab: 'Electronics',
            goods3: [],
            showLoading: 'hide',
            tabKey: 'new',
            isLoading: false,
            pageNew: 1,
            pageHot: 1,
            pageSize: 12,
            lengthHot: 2,
            lengthNew: 2,
            countApi: 0,
            isPlatformAndroid: Utils.env.isAndroid(),
            isActiveLoading: false,
            hasWifi: true,
            category: [],
            drops: [],
            products: [],
            user: '',
            firstLogin: true,
            newGift: true,
            isLoaded: false
        }
    },
    methods: {
        jumpCategory (item) {
            if (!item.id) return;
            this.$router.open({
                name: 'goods.category',
                type: 'PUSH',
                params: {
                    id: item.id,
                    name: item.name
                }
            })
        },
        noNoticeFinished (e) {},
        onLoadingMore () {
            if(!this.isLoading) {
                this.countApi = 0;
                this.isLoading = true;
                if (this.tabKey == 'new') {
                    this.getNewGoods(false)
                } else {
                    this.getHotGoods(false)
                }
            }
        },
        onloading () {
            if (!this.isPlatformAndroid) {
                this.countApi = 0;
                this.isLoading = true;
                if (this.tabKey == 'new') {
                    this.getNewGoods(false);
                } else {
                    this.getHotGoods(false);
                }
            }
        },
        loadingDown () {
            this.countApi = 0;
            if (this.hasWifi) {
                this.$refs.refresh.refreshEnd();
            }
            this.isLoading = false;
            this.init();
        },
        init () {
            this.getYXBanners();
            this.getTabName();
            this.getProductCategory();
            this.getChannel();
            this.getNotification();
            // this.getDrops();
            this.getActivity();
            this.getBlock5();
            // this.user = this.$storage.getSync('user')
            if(this.tabKey == 'new') {
                this.getNewGoods(true);
            } else {
                this.getHotGoods(true);
            }
        },
        getYXBanners () {
            this.$fetch({
                method: 'GET',
                name: 'promotion.banner.list',
                data: {}
            }).then(resData => {
                this.YXBanners = [...resData];
                this.refreshing = false;
                this.refreshApiFinished();
                this.$emit('bannerColor', {
                    status: 'bannerColor',
                    data: {
                        bgColor: this.YXBanners[0].color
                    }
                });
            }, error => {
                if(error.status == 10) {
                    this.hasWifi = false;
                }
            })
        },
        changeColor(event) {
            this.$emit('bannerColor', {
                status: 'bannerColor',
                data: {
                    bgColor: event.data.color
                }
            });
        },
        getActivity() {
            this.$fetch({
                method: 'GET',
                name: `flashsale.flash.customer.home`
            }).then((res) => {
                if(res.length > 0){
                    this.activity = res.splice(0, 3);
                    this.time = new Date(this.activity[0].flashPromotionEndtime).getTime();
                }
                this.refreshApiFinished();
            }).catch((res) => {
                if(error.status == 10) {
                    this.hasWifi = false;
                }
            });
        },
        getTabName () {
            this.tabsItems = TAB;
        },
        getProductCategory() {
            this.products = [];
            this.$fetch({
                method: 'GET',
                name: 'product.category.product.home.new.list',
                data: {}
            }).then(resData => {
                this.products = [];
                for(let i = 0; i < resData.length;i++) {
                    const item = resData[i];
                    this.products.push(item)
                    if(item.product.length > 0) {
                        const goods = [];
                        const goods1 = [];
                        for(let i = 0; i < item.product.length; i++) {
                            const itm = item.product[i];
                            if(i < 3) {
                                goods.push(itm);
                            } else if(i >= 3 && i < 6) {
                                goods1.push(itm);
                            }
                        }
                        this.products.push({
                            items: [...goods]
                        });
                        this.products.push({
                            items: [...goods1]
                        });
                    }
                }
                this.refreshApiFinished();
            }, error => {
                if(error.status == 10) {
                    this.hasWifi = false;
                }
            });
        },
        getNotification () {
            this.$fetch({
                method: 'GET',
                name: 'notice.app.list',
                data: {
                    placement: 'Home'
                }
            }).then(resData => {
                this.noticeList = [...resData];
            }, error => {
                if(error.status == 10) {
                    this.hasWifi = false;
                }
            });
        },
        getBlock5 () {
            this.refreshApiFinished();
        },
        getNewGoods(isfirst) {
            if(isfirst) {
                this.pageNew = 1
            }
            if(this.pageNew > this.lengthNew) {
                this.$refs.refresh.refreshEnd();
                this.$nextTick(()=> {
                    this.isLoading = false;
                });
            }
            this.$fetch({
                method: 'GET', // 大写
                name: 'product.customer.list', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
                data: {
                    page: this.pageNew,
                    page_size: this.pageSize
                }
            }).then((data) => {
                this.$notice.loading.hide();
                if(isfirst) {
                    this.goods3 = [];
                }
                this.lengthNew = Math.ceil(data.count / this.pageSize);
                this.pageNew++;
                let arr = [];
                for(let i = 0; i < data.results.length; i++) {
                    let item = data.results[i];
                    arr.push(item);
                    if((i > 0 && i % 2 == 1) || (i == data.results.length - 1)) {
                        this.goods3.push(arr);
                        arr = [];
                    }
                }
                // this.goods3.push(...data.results);
                if(!isfirst) {
                    this.isLoading = false;
                }
                this.refreshApiFinished();
            }, (error) => {
                if(error.status == 10) {
                    this.hasWifi = false;
                }
            });
        },
        getHotGoods (isfirst) {
            if(isfirst) {
                this.pageHot = 1;
            }
            if(this.pageHot > this.lengthHot) {
                this.$refs.refresh.refreshEnd();
                this.$nextTick(()=> {
                    this.isLoading = false;
                });
            }
            this.$fetch({
                method: 'GET',
                name: 'product.selected.list',
                data: {
                    page: this.pageHot,
                    page_size: this.pageSize
                }
            }).then(data => {
                this.$notice.loading.hide();
                if(isfirst) {
                    this.goods3 = [];
                }
                this.lengthHot = Math.ceil(data.count / this.pageSize);
                this.pageHot++;
                let arr = [];
                for(let i = 0; i < data.results.length; i++) {
                    let item = data.results[i];
                    arr.push(item);
                    if(i > 0 && (i % 2 == 1 || i == data.results.length - 1)) {
                        this.goods3.push(arr);
                        arr = [];
                    }
                }
                if(!isfirst) {
                    this.isLoading = false;
                }
                this.refreshApiFinished();
            }, error => {
                if(error.status == 10) {
                    this.hasWifi = false;
                }
            });
        },
        getChannel () {
            // this.category = [...CHANNELLIST];
            // this.category1 = [...CHANNELLIST1];
            // this.$fetch({
            //     method: 'GET', // 大写
            //     name: 'category.list', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
            //     data: {}
            // }).then(data => {
            //     for (let i = 0; i < this.category.length; i++) {
            //         for (let j = 0; j < data.length; j++) {
            //             if(this.category[i].name == data[j].name) {
            //                 this.category[i].id = data[j].id;
            //             }
            //         }
            //     }
            //     for (let i = 0; i < this.category1.length; i++) {
            //         for (let j = 0; j < data.length; j++) {
            //             if(this.category1[i].name == data[j].name) {
            //                 this.category1[i].id = data[j].id;
            //             }
            //         }
            //     }
            // }, error => {})
        },
        getDrops() {
            this.$fetch({
                method: 'GET',
                name: 'product.cut.list',
                data: {
                    page: 1,
                    page_size: 3
                }}
            ).then(data => {
                this.drops = [...data.results];
                this.refreshApiFinished();
            }, error => {
                if (error.status == 10) {
                    this.isWifi = false;
                }
            })
        },
        scrollToHeader(tab) {
            this.$nextTick(() => {
                dom.scrollToElement(this.$refs[tab][0], {
                    offset: -96,
                    animated: false
                });
            });
        },
        onTabTo (event) {
            this.activeTab = event.data.key;
            this.scrollToHeader(this.activeTab);
        },
        refreshApiFinished() {
            this.countApi++;
            if(this.countApi >= 5) {
                this.hasWifi = true;
                this.isLoaded = true;
                this.$refs.refresh.refreshEnd();
                this.countApi = 0;
            }
        },
        scrollHandler (e) {
            // if (Math.abs(e.contentOffset.y) >= 488) {
            //     this.$emit('colorChange', {
            //         status: 'colorChange',
            //         data: {
            //             isHeaderBg: true
            //         }
            //     });
            //
            //     for(let item of this.products) {
            //         dom.getComponentRect(this.$refs[item.name][0], option => {
            //             if(option.size.top <= 333 && option.size.top > 96) {
            //                 this.activeTab = item.name;
            //             }
            //         });
            //     }
            // } else {
            //     this.$emit('colorChange', {
            //         status: 'colorChange',
            //         data: {
            //             isHeaderBg: false
            //         }
            //     });
            // }
        },
        redirectLogin () {
            this.$event.on('login', params => {
                this.user = this.$storage.getSync('user')
                if (this.user) {
                    this.init()
                }
            });
            this.$router.open({
                name: 'login',
                type: 'PUSH'
            })
        },
        openNewCo () {
            if (this.user == ''){
                this.redirectLogin()
            }else {
                this.newGift = false
                this.$emit('openNewMask')
                this.$event.emit('hideBlock')
            }
        },
        goLucky(){
            this.$router.open({
                name: 'raffle',
                type: 'PUSH',
            })
        },
        goReward(){
            this.$event.emit('changeTab', {
                tab: 'rewards'
            });
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
