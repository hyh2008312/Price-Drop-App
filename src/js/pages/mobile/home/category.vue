<template>
    <div class="wrapper" >
        <scroller offset-accuracy="300" loadmoreoffset="300"  @loadmore="onloading">
            <refresher @loadingDown="loadingDown"></refresher>
            <div class="cell-button">
                <block-4 :items="block1.items" ></block-4>
            </div>
            <div class="cell-button">
                <tab @tabTo="onTabTo" :items="tabsItems" ref="tab"></tab>
                <block-3 :goods="goods3" v-if="tabKey == 'hot'"></block-3>
                <block-3 :goods="goods3" v-if="tabKey == 'new'"></block-3>
            </div>
            <loading class="loading" :display="showLoading">
                <text class="indicator">加载中...</text>
            </loading>
        </scroller>
    </div>
</template>
<script>
import refresher from '../common/refresh';
import tab from './tab';
import block3 from './block3';
import block4 from './block4';
import { BLOCK1, TAB, GOODS3 } from './config';

const SCROLL_FULL_WIDTH = 750
const dom = weex.requireModule('dom');

export default {
    components: {
        'refresher': refresher,
        'tab': tab,
        'block-3': block3,
        'block-4': block4
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
            refresh: true
        }
    },
    methods: {
        onloading () {
            this.showLoading = 'show';
            this.goods3.push(...this.goods1);
            this.showLoading = 'hide';
        },
        init () {
            this.getBlock1()
            this.getTabName()
            this.getGoods3()
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
        getGoods3 () {
            // this.$fetch({
            //     method: 'GET',
            //     name: 'yanxuan_home_getGoods3',
            //     data: {}
            // }).then(resData => {
            //     this.goods3 = resData.data
            // }, error => {

            // })

            this.goods3 = GOODS3
        },
        onTabTo (event) {
            this.tabKey = event.data.key;
            this.goods3 = [];
            this.goods3 = GOODS3;
            dom.scrollToElement(this.$refs['tab'], { animated: false })
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
