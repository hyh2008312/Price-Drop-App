<!--suppress ALL -->
<template>
    <div class="wrapper">
        <list offset-accuracy="100" loadmoreoffset="100" @loadmore="onLoadingMore" >
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell class="cell-button slider-wrap">
                <yx-slider class="slider-container" :imageList="YXBanners"></yx-slider>
            </cell>
            <cell class="notice-wrapper cell-button">
                <div class="notice-bg">
                    <block-4 :items="block1.items" @noticeFinished="noNoticeFinished"></block-4>
                </div>
            </cell>
            <cell v-for="(head, index) in activity" :key="index">
                <block-2 :head="head" :class="[activity.length - 1 == index ? 'cell-margin-button': '']"></block-2>
            </cell>
            <cell class="cell-button" v-if="false">
                <block-5 :logo="block5.items"></block-5>
            </cell>
            <cell ref="tab"></cell>
            <header v-if="tabsItems.length > 0">
                <tab @tabTo="onTabTo" :items="tabsItems"></tab>
            </header>
            <cell v-for="(item,index) in goods3">
                <block-3 :goods="item" :tab="tabKey"></block-3>
            </cell>
            <cell class="cell-fixed" v-if="goods3.length > 0"></cell>
            <loading class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">loading...</text>
            </loading>
        </list>
    </div>
</template>
<script>
import { Utils } from 'weex-ui';
import refresher from '../common/refresh';
import YXSlider from './YXSlider';
import tab from './tab';
import block2 from './block2';
import block3 from './block3';
import block4 from './block4';
import block5 from './block5';
import { BLOCK1, TAB, BLOCK4 } from './config';

const SCROLL_FULL_WIDTH = 750;
const dom = weex.requireModule('dom');

export default {
    components: {
        'refresher': refresher,
        'yx-slider': YXSlider,
        'tab': tab,
        'block-2': block2,
        'block-3': block3,
        'block-4': block4,
        'block-5': block5
    },
    created () {
        this.init()
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
            activity: [],
            tabsItems: [],
            goods3: [],
            showLoading: 'hide',
            tabKey: 'hot',
            isLoading: false,
            pageNew: 1,
            pageHot: 1,
            pageSize: 6,
            lengthHot: 2,
            lengthNew: 2,
            countApi: 0,
            isPlatformAndroid: Utils.env.isAndroid()
        }
    },
    methods: {
        noNoticeFinished (e) {
            if(this.backup.length > 0) {
                const newArr = this.backup.splice(0, 4);
                this.block1.items = [...newArr];
            } else {
                this.$fetch({
                    method: 'GET',
                    name: 'promotion.get.list',
                    data: {}
                }).then(resData => {
                    this.block1.backup = [...resData];
                    const newArr = this.backup.splice(0, 4);
                    this.block1.items = [...newArr];
                }, error => {})
            }
        },
        onLoadingMore () {
            this.countApi = 0;
            this.isLoading = true;
            if(this.tabKey == 'new') {
                this.getNewGoods(false)
            } else {
                this.getHotGoods(false)
            }
        },
        onloading () {
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
        loadingDown () {
            this.countApi = 0
            this.$refs.refresh.refreshEnd()
            this.isLoading = false
            this.init();
        },
        async init () {
            this.getYXBanners()
            this.getActivity()
            await this.getBlock4()
            await this.getTabName()
            this.getBlock5()
            if(this.tabKey == 'new') {
                this.getNewGoods(true)
            } else {
                this.getHotGoods(true)
            }
        },
        getYXBanners () {
            this.$fetch({
                method: 'GET',
                name: 'promotion.banner.list',
                data: {}
            }).then(resData => {
                this.YXBanners = [...resData]
                this.refreshing = false
                this.refreshApiFinished()
            }, error => {

            })
        },
        getActivity() {
            this.$fetch({
                method: 'GET',
                name: 'product.topic.list',
                data: {}
            }).then(resData => {
                this.activity = [...resData]
                this.refreshApiFinished()
            }, error => {

            })
        },
        getTabName () {
            return this.tabsItems = TAB;
        },
        getBlock4 () {
            return this.$fetch({
                method: 'GET',
                name: 'promotion.get.list',
                data: {}
            }).then(resData => {
                this.backup = [...resData];
                const newArr = this.backup.splice(0, 4);
                this.block1.items = [...newArr];
            }, error => {})
        },
        getBlock5 () {
            this.refreshApiFinished()
        },
        getNewGoods(isfirst) {
            if(isfirst) {
                this.pageNew = 1
            }
            if(this.pageNew > this.lengthNew) {
                this.$refs.refresh.refreshEnd()
                this.$nextTick(()=> {
                    this.isLoading = false
                })
            }
            this.$fetch({
                method: 'GET', // 大写
                name: 'product.customer.list', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
                data: {
                    page: this.pageNew,
                    page_size: this.pageSize
                }
            }).then((data) => {
                if(isfirst) {
                    this.goods3 = []
                }
                this.lengthNew = Math.ceil(data.count / this.pageSize)
                this.pageNew++
                this.goods3.push(...data.results)
                if(!isfirst) {
                    this.isLoading = false
                }
                this.refreshApiFinished()
            }, (error) => {

            })
        },
        getHotGoods (isfirst) {
            if(isfirst) {
                this.pageHot = 1
            }
            if(this.pageHot > this.lengthHot) {
                this.$refs.refresh.refreshEnd();
                this.$nextTick(()=> {
                    this.isLoading = false
                })
            }
            this.$fetch({
                method: 'GET',
                name: 'product.selected.list',
                data: {
                    page: this.pageHot,
                    page_size: this.pageSize
                }
            }).then(data => {
                if(isfirst) {
                    this.goods3 = []
                }
                this.lengthHot = Math.ceil(data.count / this.pageSize)
                this.pageHot++
                this.goods3.push(...data.results)
                if(!isfirst) {
                    this.isLoading = false
                }
                this.refreshApiFinished()
            }, error => {

            })
        },
        scrollToHeader() {
            return this.$nextTick(() => {
                dom.scrollToElement(this.$refs['tab'], { animated: false })
            })
        },
        async onTabTo (event) {
            this.tabKey = event.data.key;
            await this.scrollToHeader();
            if(event.data.key == 'new') {
                this.getNewGoods(true);
            } else {
                this.getHotGoods(true);
            }
        },
        refreshApiFinished() {
            this.countApi++
            if(this.countApi >= 3) {
                this.$refs.refresh.refreshEnd();
                this.countApi = 0
            }
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
