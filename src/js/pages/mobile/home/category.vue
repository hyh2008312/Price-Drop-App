<template>
    <div class="wrapper">
        <list ref="list" offset-accuracy="100" loadmoreoffset="100" @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell class="cell-top" ></cell>
            <cell class="notice-wrapper cell-button">
                <div class="notice-bg">
                    <block-4 :items="block1.items" v-if="block1.items.length > 0"
                             :activeIndex="activeIndex" :index="index"
                             @noticeFinished="noNoticeFinished"></block-4>
                </div>
            </cell>
            <cell ref="tab"></cell>
            <header v-if="false && tabsItems.length > 0">
                <tab @tabTo="onTabTo" :items="tabsItems"></tab>
            </header>
            <cell v-for="(item, index) in goods3">
                <block-3 :goods="item"></block-3>
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
import tab from './tabCategory';
import block3 from './block3';
import block4 from './block4';
import { BLOCK1, TABCAT } from './config';

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
            backup: [],
            block1: {
                items: []
            },
            tabsItems: [],
            goods3: [],
            tabKey: 'new',
            priceStatus: 0,
            pageNew: 1,
            pageHot: 1,
            pagePrice: 1,
            pageSize: 24,
            lengthNew: 2,
            lengthHot: 2,
            lengthPrice: 2,
            isLoading: false,
            isPlatformAndroid: Utils.env.isAndroid()
        }
    },
    methods: {
        noNoticeFinished (e) {
            if (this.backup.length > 0) {
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
            // this.getGoods3(false)
        },
        onloading () {
            if (this.isPlatformAndroid) {
                this.isLoading = true
                this.getGoods3(false)
            }
        },
        loadingDown () {
            this.$refs.refresh.refreshEnd()
            this.isLoading = false
            this.init();
        },
        init () {
            this.getBlock1()
            this.getTabName()
            this.getGoods3(true)
        },
        getBlock1 () {
            this.$fetch({
                method: 'GET',
                name: 'promotion.get.list',
                data: {}
            }).then(resData => {
                this.backup = [...resData];
                const newArr = this.backup.splice(0, 4);
                this.block1.items = [...newArr];
            }, error => {})
        },
        getTabName () {
            this.tabsItems = [...TABCAT];
        },
        getGoods3 (isfirst) {
            let page = 1;
            switch (this.tabKey) {
                case 'hot':
                    if (isfirst) {
                        this.pageHot = 1
                    }
                    page = this.pageHot;
                    if (this.pageHot > this.lengthHot) {
                        this.$refs.refresh.refreshEnd()
                        this.$nextTick(() => {
                            this.isLoading = false
                        })
                    }
                    this.getSelectedList(isfirst, page)
                    break;
                case 'new':
                    if (isfirst) {
                        this.pageNew = 1
                    }
                    page = this.pageNew;
                    if (this.pageNew > this.lengthNew) {
                        this.$refs.refresh.refreshEnd()
                        this.$nextTick(() => {
                            this.isLoading = false
                        })
                    }
                    this.getGoodsList(isfirst, page)
                    break;
                case 'price':
                    if (isfirst) {
                        this.pagePrice = 1
                    }
                    page = this.pagePrice;
                    if (this.pagePrice > this.lengthPrice) {
                        this.$refs.refresh.refreshEnd()
                        setTimeout(() => {
                            this.isLoading = false
                        }, 0)
                    }
                    this.getGoodsList(isfirst, page)
                    break;
            }
        },
        getGoodsList (isfirst, page) {
            let sort = null
            switch (this.priceStatus) {
                case 1:
                    sort = 'price_high'
                    break
                case 2:
                    sort = 'price_low'
                    break
            }
            this.$fetch({
                method: 'GET',
                name: 'product.customer.list',
                data: {
                    page,
                    page_size: this.pageSize,
                    cat: this.id,
                    sort
                }
            }).then(data => {
                switch (this.tabKey) {
                    case 'new':
                        this.pageNew++
                        this.lengthNew = Math.ceil(data.count / this.pageSize)
                        break
                    case 'price':
                        this.pagePrice++
                        this.lengthPrice = Math.ceil(data.count / this.pageSize)
                        break
                }
                if (isfirst) {
                    this.goods3 = []
                }
                this.goods3.push(...data.results)
                if (!isfirst) {
                    this.isLoading = false
                }
                this.refreshApiFinished()
            }, error => {

            })
        },
        getSelectedList (isfirst, page) {
            this.$fetch({
                method: 'GET',
                name: 'product.selected.list',
                data: {
                    page,
                    page_size: this.pageSize,
                    cat: this.id
                }
            }).then(data => {
                this.pageHot++
                this.lengthHot = Math.ceil(data.count / this.pageSize)
                if (isfirst) {
                    this.goods3 = [...data.results]
                } else {
                    this.goods3.push(...data.results)
                }
                if (!isfirst) {
                    this.isLoading = false
                }
                this.refreshApiFinished()
            }, error => {

            })
        },
        onTabTo (event) {
            this.tabKey = event.data.key
            this.priceStatus = event.data.priceStatus
            this.getGoods3(true)
            dom.scrollToElement(this.$refs['tab'], { animated: false })
        },
        refreshApiFinished () {
            this.$refs.refresh.refreshEnd();
        }
    }
}

</script>
<style lang="sass" src="./index.scss"></style>
