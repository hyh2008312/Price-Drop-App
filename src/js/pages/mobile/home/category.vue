<template>
    <div class="wrapper">
        <list loadmoreoffset="30" @loadmore="onloading">
            <refresher @loadingDown="loadingDown"></refresher>
            <cell class="cell-button cell-top">
                <block-4 :items="block1.items" ></block-4>
            </cell>
            <cell ref="tab"></cell>
            <header>
                <tab @tabTo="onTabTo" :items="tabsItems" ref="tab"></tab>
            </header>
            <cell v-for="item in goods3"  :style="{ visibility: tabKey == 'hot'}">
                <block-3 :goods="item"></block-3>
            </cell>
            <cell v-for="item in goods3"  :style="{ visibility: tabKey == 'new'}">
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
import tab from './tab';
import block3 from './block3';
import block4 from './block4';
import { BLOCK1, TAB, GOODS3, GOODS1 } from './config';

const SCROLL_FULL_WIDTH = 750
const dom = weex.requireModule('dom');

export default {
    components: {
        'refresher': refresher,
        'tab': tab,
        'block-3': block3,
        'block-4': block4
    },
    props: ['index', 'activeIndex'],
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
            goods1: [],
            goods3: [],
            showLoading: 'hide',
            tabKey: 'hot',
            refresh: true
        }
    },
    methods: {
        onloading () {
            this.goods3.push(...this.goods1);
        },
        loadingDown () {
            this.init();
        },
        init () {
            this.getBlock1()
            this.getTabName()
            this.getGoods1()
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
            dom.scrollToElement(this.$refs['tab'], { animated: false })
            this.goods3 = [];
            this.goods3 = GOODS3;
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
