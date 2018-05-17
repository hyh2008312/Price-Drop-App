<template>
    <div class="wrapper">
        <list loadmoreoffset="100" @loadmore="onloading">
            <refresher @loadingDown="loadingDown"></refresher>
            <cell class="cell-button cell-top" >
                <block-4 :items="block1.items" @noticeFinished="noNoticeFinished"
                         :activeIndex="activeIndex" :index="index"
                         v-if="block1.items.length > 0" ></block-4>
            </cell>
            <cell ref="tab"></cell>
            <header v-if="tabsItems.length > 0">
                <tab @tabTo="onTabTo" :items="tabsItems"></tab>
            </header>
            <cell v-for="item in goods3">
                <block-3 :goods="item"></block-3>
            </cell>
            <loading class="loading" >
                <text class="indicator">加载中...</text>
            </loading>
        </list>
    </div>
</template>
<script>
import refresher from '../common/refresh';
import tab from './tabCategory';
import block3 from './block3';
import block4 from './block4';
import { BLOCK1, TABCAT, GOODS3, GOODS1 } from './config';

const SCROLL_FULL_WIDTH = 750
const dom = weex.requireModule('dom');

export default {
    components: {
        'refresher': refresher,
        'tab': tab,
        'block-3': block3,
        'block-4': block4
    },
    props: ['index', 'activeIndex', 'id'],
    created () {
        // this.init()
    },
    watch: {
        'index': {
            handler: function (val, oldVal) {
                if (this.activeIndex == val) {
                    this.init()
                }
            },
            deep: true
        }
    },
    data () {
        return {
            YXBanners: [],
            block1: {
                title: '',
                url: '',
                items: []
            },
            tabsItems: [],
            goods3: [],
            showLoading: 'hide',
            tabKey: 'hot',
            refresh: true,
            pageNew: 1,
            pageHot: 1,
            pagePrice: 1,
            pageSize: 6,
            length: 2
        }
    },
    methods: {
        noNoticeFinished (e) {
            this.block1.items = [...BLOCK1.items];
        },
        onloading () {
            this.getGoods3();
        },
        loadingDown () {
            this.init();
        },
        init () {
            this.getBlock1()
            this.getTabName()
            this.getGoods3(true)
        },
        getBlock1 () {

            this.block1.title = BLOCK1.title
            this.block1.url = BLOCK1.url
            this.block1.items = BLOCK1.items
        },
        getTabName () {
            this.tabsItems = TABCAT;
        },
        getGoods3 (isfirst) {
            let page = 1;
            switch (this.tabKey) {
                case 'hot':
                    if (isfirst) {
                        this.pageHot = 1
                    }
                    page = this.pageHot;
                    if (this.pageHot > this.length) {
                        return
                    }
                    break;
                case 'new':
                    if (isfirst) {
                        this.pageNew = 1
                    }
                    page = this.pageNew;
                    if (this.pageNew > this.length) {
                        return
                    }
                    break;
                case 'price':
                    if (isfirst) {
                        this.pagePrice = 1
                    }
                    page = this.pagePrice;
                    if (this.pagePrice > this.length) {
                        return
                    }
                    break;
            }
            this.$fetch({
                method: 'GET',
                name: 'product.selected.list',
                data: {
                    page,
                    page_size: this.pageSize
                }
            }).then(data => {
                switch (this.tabKey) {
                    case 'hot':
                        this.pageHot++;
                        break;
                    case 'new':
                        this.pageNew++;
                        break;
                    case 'price':
                        this.pagePrice++;
                        break;
                }
                this.length = Math.ceil(data.count / this.pageSize)
                if (isfirst) {
                    this.goods3 = []
                }
                this.goods3.push(...data.results)
            }, error => {

            })
        },
        onTabTo (event) {
            this.goods3 = [];
            dom.scrollToElement(this.$refs['tab'], { animated: false })
            this.tabKey = event.data.key;
            this.getGoods3(true);
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
