<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name}}</text>
        </div>
        <waterfall class="main-list" column-count="2" ref="list" loadmoreoffset="30"
                   @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <header>
                <div class="category-header">
                    <text class="iconfont category-arrange">&#xe742;</text>
                </div>
            </header>
            <cell v-for="(i ,index) in goods">
                <div class="i-gd" :class="[index % 2 ==0 ? 'margin-left16':'margin-right16']" @click="jumpWeb(i.productId)">
                    <div class="gd-bg">
                        <div class="gd-img">
                            <preload class="gd-img-image" :src="i.mainImage"></preload>
                        </div>
                    </div>
                    <div class="gd-tlt-bg">
                        <text class="gd-tlt">Rs.{{i.unitPrice}}</text>
                        <text class="gd-info">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                    <text class="gd-price">Rs.{{i.saleUnitPrice}}</text>
                </div>
            </cell>
            <loading class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">loading...</text>
            </loading>
        </waterfall>
    </div>
</template>
<script>
    import { Utils } from 'weex-ui';
    import refresher from '../common/refresh';
    import preload from '../common/preloadImg';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'refresher': refresher,
            preload
        },
        created () {},
        eros: {
            appeared (params, options) {
                this.getActivityParam(params)
            }
        },
        data () {
            return {
                name: '',
                id: -1,
                imageUrl: '',
                testImage: '',
                goods: [],
                length: 2,
                page: 1,
                pageSize: 12,
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid()
            }
        },
        methods: {
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.getActivityProduct(true);
            },
            onLoadingMore () {
                if (!this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getActivityProduct(false);
                }
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getActivityProduct(false);
                }
            },
            getActivityParam (resData) {
                this.id = resData.id;
                this.name = resData.name;
                googleAnalytics.trackingScreen(`Activity/${this.name}`);
                this.getActivityProduct(true);
            },
            getActivityProduct (isfirst) {
                if (isfirst) {
                    this.page = 1
                }
                if (this.page > this.length) {
                    this.$refs.refresh.refreshEnd();
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                this.$fetch({
                    method: 'GET',
                    name: 'product.customer.list',
                    data: {
                        cat: this.id,
                        page: this.page,
                        page_size: this.pageSize
                    }
                }).then(data => {
                    this.length = Math.ceil(data.count / this.pageSize)
                    if (isfirst) {
                        this.goods = []
                    }
                    this.page++;
                    this.goods.push(...data.results);
                    if (!isfirst) {
                        this.isLoading = false
                    }
                    this.refreshApiFinished()
                }, error => {
                    this.$notice.toast({
                        message: JSON.stringify(error)
                    });
                })
            },
            jumpWeb (id) {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: id
                    }
                })
            },
            homeBack () {
                this.$router.back();
            },
            refreshApiFinished () {
                this.$refs.refresh.refreshEnd();
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.floor((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            }
        }
    }

</script>
<style scoped>
    .iconfont{
        font-family: iconfont;
    }

    .main-list {
        width: 750px;
        margin-top: -1px;
    }

    .category-header {
        margin-top: 1px;
        width: 750px;
        height: 96px;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        position: relative;
        background-color: #ffffff;
        border: 2px solid rgba(0,0,0,0.08);
    }

    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .navigation {
        display: flex;
        width: 750px;
        height: 112px;
        background-color: #fff;
        flex-direction: row;
        justify-content: flex-start;
    }

    .title {
        font-size: 32px;
        font-weight: bold;
        height: 112px;
        line-height: 112px;
        margin-left: 32px;
        width: 558px;
        text-align: center;
        line: 1;
        text-overflow: ellipsis;
        color: rgba(0,0,0,0.87);
    }

    .homeBack {
        font-family: iconfont;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .wrapper {
        background-color: white;
    }

    .margin-left16 {
        flex-direction: column;
        align-items: flex-end;
    }

    .margin-right16 {
        flex-direction: column;
        align-items: flex-start;
    }

    .i-gd {
        width: 359px;
        height: 400px;
        margin-top: 24px;
    }

    .gd-bg {
        border-radius: 8px;
        width: 327px;
        height: 327px;
    }

    .gd-img {
        width: 323px;
        height: 323px;
        border-top-width: 1px;
        border-top-style: solid;
        border-top-color: rgba(0,0,0,.08);
        border-left-width: 1px;
        border-left-style: solid;
        border-left-color: rgba(0,0,0,.08);
        border-right-width: 1px;
        border-right-style: solid;
        border-right-color: rgba(0,0,0,.08);
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,.08);
    }
    .gd-img {
        width: 323px;
        height: 323px;
        border-radius: 8px;
        overflow: hidden;
    }
    .gd-img-image {
        width: 323px;
        height: 323px;
        border-radius: 8px;
        overflow: hidden;
        position: relative;
    }
    .gd-tlt {
        font-family: ProximaNova;
        font-size: 28px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        line-height: 34px;
    }

    .gd-tlt-bg{
        margin-top: 6px;
        width: 327px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }

    .gd-info{
        font-family: ProximaNova;
        margin-left: 8px;
        font-size: 20px;
        line-height: 24px;
        color: #EF8A31;
    }

    .gd-price {
        font-family: ProximaNova;
        font-size: 20px;
        font-weight: 500;
        width: 327px;
        line-height: 24px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
        text-align: center;
        text-decoration: line-through;
    }

    .indicator {
        width: 750px;
        text-align: center;
        color: #888888;
        font-size: 28px;
        padding-top: 16px;
        padding-bottom: 16px;
    }

    .category-arrange{
        font-size: 32px;
        color: #EF8A31;
    }
</style>
