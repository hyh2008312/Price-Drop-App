<!--suppress ALL -->
<template>
    <div class="wrapper" >
        <list loadmoreoffset="100" @loadmore="onloading">
            <refresher @loadingDown="loadingDown"></refresher>
            <cell class="cell-button slider-wrap">
                <div class="slider-bg"></div>
                <yx-slider class="slider-container" :imageList="YXBanners"></yx-slider>
            </cell>
            <cell class="cell-button">
                <block-4 v-if="block1.items.length > 0" :items="block1.items" @noticeFinished="noNoticeFinished"></block-4>
            </cell>
            <cell class="cell-button">
                <block-2 hasMore=true newGoods=true :head="head1" :goods="goods1"></block-2>
                <block-2 hasMore=true hotGoods=true :head="head2" :goods="goods2"></block-2>
            </cell>
            <cell class="cell-button">
                <block-5 :logo="block5.items"></block-5>
            </cell>
            <cell ref="tab"></cell>
            <header>
                <tab @tabTo="onTabTo" :items="tabsItems"></tab>
            </header>
            <cell v-for="(item,index) in goods3">
                <block-3 :goods="item"></block-3>
            </cell>
            <loading class="loading">
                <text class="indicator">加载中...</text>
            </loading>
        </list>
    </div>
</template>
<script>
import refresher from '../common/refresh';
import YXSlider from './YXSlider';
import tab from './tab';
import block2 from './block2';
import block3 from './block3';
import block4 from './block4';
import block5 from './block5';
import { YXBANNERS, BLOCK1, TAB, BLOCK4, GOODS1, GOODS2, GOODS3 } from './config';

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
            block4: {
                items: []
            },
            block5: {
                items: []
            },
            head1: {
                tlt: '周一周四 · 新品发布',
                tltBg: 'http://doc.zwwill.com/yanxuan/imgs/bg-new.png',
                url: 'http://m.you.163.com/item/newItem'
            },
            head2: {
                tlt: '人气推荐 · 好物精选',
                tltBg: 'http://doc.zwwill.com/yanxuan/imgs/bg-hot.png',
                url: 'http://m.you.163.com/item/recommend'
            },
            tabsItems: [],
            goods1: [],
            goods2: [],
            goods3: [],
            showLoading: 'hide',
            tabKey: 'hot',
            refresh: true,
            pageNew: 1,
            pageHot: 1,
            pageSize: 6,
            lengthNew: 1
        }
    },
    methods: {
        jumpWeb (_url) {
            this.$router.toWebView({
                url: _url,
                title: ''
            })
        },
        noNoticeFinished (e) {
            this.block1.items = [...BLOCK4.items];
        },
        onloading () {
            if(this.tabKey == 'new') {
                this.getNewGoods()
            } else {
                this.goods3.push(...this.goods3)
            }
        },
        loadingDown () {
            this.init();
        },
        init () {
            this.getYXBanners();
            this.getBlock1();
            this.getTabName();
            this.getBlock4();
            this.getBlock5();
            this.getGoods1();
            this.getGoods2();
            this.getGoods3()
        },
        getYXBanners () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getYXBanners',
            //     data: {}
            // }).then(resData => {
            //     this.YXBanners = resData.data
            // }, error => {

            // })

            this.YXBanners = YXBANNERS
        },
        getBlock1 () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getBlock1',
            //     data: {}
            // }).then(resData => {
            //     this.block1.title = resData.data.title
            //     this.block1.url = resData.data.url
            //     this.block1.items = resData.data.items
            // }, error => {

            // })

            this.block1.title = BLOCK1.title
            this.block1.url = BLOCK1.url
            this.block1.items = BLOCK1.items
        },
        getTabName () {
            this.tabsItems = TAB;
        },
        getBlock4 () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getBlock1',
            //     data: {}
            // }).then(resData => {
            //     this.block1.title = resData.data.title
            //     this.block1.url = resData.data.url
            //     this.block1.items = resData.data.items
            // }, error => {

            // })

            this.block4.items = BLOCK4.items
        },
        getBlock5 () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getBlock1',
            //     data: {}
            // }).then(resData => {
            //     this.block1.title = resData.data.title
            //     this.block1.url = resData.data.url
            //     this.block1.items = resData.data.items
            // }, error => {

            // })

            this.block5.items = GOODS2
        },
        getGoods1 () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getGoods1',
            //     data: {}
            // }).then(resData => {
            //     this.goods1 = resData.data
            // }, error => {

            // })

            this.goods1 = GOODS1
        },
        getGoods2 () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getGoods2',
            //     data: {}
            // }).then(resData => {
            //     this.goods2 = resData.data
            // }, error => {

            // })

            this.goods2 = GOODS2
        },
        getGoods3 () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getGoods3',
            //     data: {}
            // }).then(resData => {
            //     this.goods3 = resData.data
            // }, error => {

            // })

            this.goods3 = [...GOODS3]
        },
        getNewGoods(isfirst) {
            if(isfirst) {
                this.pageNew = 1
            }
            if(this.pageNew > this.lengthNew) {
                return
            }
            this.$fetch({
                method: 'GET', // 大写
                name: 'product.customer.list', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
                data: {
                    page: this.pageNew,
                    page_size: this.pageSize
                }
            }).then((data) => {
                this.lengthNew = Math.ceil(data.count / this.pageSize)
                if(isfirst) {
                    this.goods3 = []
                }
                this.pageNew++
                this.goods3.push(...data.results)
                this.$nextTick(() => {
                    dom.scrollToElement(this.$refs['tab'], { animated: false })
                })
            }, (error) => {

            })
        },
        onTabTo (event) {
            this.tabKey = event.data.key;
            this.$notice.toast(event.data.key)
            if(event.data.key == 'new') {
                this.getNewGoods(true)
            } else {
                this.getGoods3()
                dom.scrollToElement(this.$refs['tab'], { animated: false })
            }

        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
