<!--suppress ALL -->
<template>
    <div class="wrapper">
        <list offset-accuracy="100" loadmoreoffset="200" @loadmore="onLoadingMore" v-if="hasWifi" >
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell class="slider-wrap">
                <yx-slider class="slider-container" :imageList="YXBanners"></yx-slider>
            </cell>
            <cell>
                <block-1 :category="category"></block-1>
            </cell>
            <cell class="cell-top" v-if="false"></cell>
            <cell class="home-drop" v-if="false">
                <div class="drop-bg"></div>
                <div class="drop-title-bg">
                    <div class="drop-title">
                        <text class="drop-title-text">Start Your Drop Now</text>
                    </div>
                    <text class="iconfont drop-title-icon">&#xe740;</text>
                </div>
                <div class="notice-wrapper">
                    <div class="notice-bg">
                        <block-4 :items="block1.items" v-if="block1.items.length > 0" @noticeFinished="noNoticeFinished"></block-4>
                    </div>
                </div>
            </cell>
            <cell class="cell-button" v-if="false">
                <div class="home-drop-1">
                    <block-6 :drops="drops"></block-6>
                </div>
            </cell>
            <cell class="cell-button">
                <text> </text>
            </cell>
            <cell v-if="activity">
                <block-2 :goodsList="activity" :time="time" class="cell-margin-button"></block-2>
            </cell>
            <cell class="cell-button" v-if="false">
                <block-5 :logo="block5.items"></block-5>
            </cell>
            <cell ref="tab"></cell>
            <header v-if="activity != false && false">
                <tab @tabTo="onTabTo" :items="tabsItems"></tab>
            </header>
            <cell v-if="goods3.length > 0">
                <text class="home-title">Featured</text>
            </cell>
            <cell v-for="(item,index) in goods3">
                <block-3 :goods="item" :tab="tabKey"></block-3>
            </cell>
            <cell class="cell-fixed" v-if="goods3.length > 0"></cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </loading>
        </list>
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
import { TAB, CHANNELLIST } from './config';

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
        noWifi
    },
    created () {
        this.init();
        googleAnalytics.trackingScreen('Home/Featured');
    },
    data () {
        return {
            YXBanners: [],
            block1: {
                title: '',
                url: '',
                items: []
            },
            backup: [],
            block4: {
                items: []
            },
            block5: {
                items: []
            },
            activity: false,
            time: false,
            tabsItems: [],
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
            drops: []
        }
    },
    methods: {
        noNoticeFinished (e) {
            if(this.backup.length > 0) {
                const newArr = this.backup.splice(0, 4);
                this.block1.items = [...newArr];
            } else {
                this.block1.items = [];
                this.$fetch({
                    method: 'GET',
                    name: 'promotion.get.broadcast',
                    data: {}
                }).then(resData => {
                    this.backup = [...resData];
                    const newArr = this.backup.splice(0, 4);
                    this.block1.items = [];
                    this.block1.items = [...newArr];
                }, error => {
                    if(error.status == 10) {
                        this.hasWifi = false;
                    }
                })
            }
        },
        onLoadingMore () {
            if (this.isPlatformAndroid) {
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
            this.getChannel();
            this.getBlock4();
            this.getDrops();
            this.getActivity();
            // this.getTabName();
            this.getBlock5();
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
            }, error => {
                if(error.status == 10) {
                    this.hasWifi = false;
                }
            })
        },
        getActivity() {
            this.$fetch({
                method: 'GET',
                name: `flashsale.flash.customer.home`
            }).then((res) => {
                this.activity = res.splice(0, 8);
                this.time = new Date(this.activity[0].flashPromotionEndtime).getTime();
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
        getBlock4 () {
            this.block1.items = [];
            this.$fetch({
                method: 'GET',
                name: 'promotion.get.broadcast',
                data: {}
            }).then(resData => {
                this.backup = [...resData];
                const newArr = this.backup.splice(0, 4);
                this.block1.items = [...newArr];
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
            this.category = [...CHANNELLIST];
            this.$fetch({
                method: 'GET', // 大写
                name: 'category.list', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
                data: {}
            }).then(data => {
                for (let i = 0; i < this.category.length; i++) {
                    for (let j = 0; j < data.length; j++) {
                        if(this.category[i].name == data[j].name) {
                            this.category[i].id = data[j].id;
                        }
                    }
                }

            }, error => {})
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
        scrollToHeader() {
            return this.$nextTick(() => {
                dom.scrollToElement(this.$refs['tab'], { animated: false });
            });
        },
        async onTabTo (event) {
            this.tabKey = event.data.key;
            await this.scrollToHeader();
            this.$notice.loading.show();
            if(event.data.key == 'new') {
                this.getNewGoods(true);
            } else {
                this.getHotGoods(true);
            }
        },
        refreshApiFinished() {
            this.countApi++;
            if(this.countApi >= 4) {
                this.hasWifi = true;
                this.$refs.refresh.refreshEnd();
                this.countApi = 0;
            }
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
