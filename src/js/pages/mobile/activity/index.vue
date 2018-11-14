<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name}}</text>
        </div>
        <waterfall class="main-list" column-count="3" column-gap="0" ref="list" loadmoreoffset="30"
                   @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <header>
                <div class="banner">
                    <image class="banner-image" :src="imageUrl" resize="cover"></image>
                    <div class="banner-mask" v-if="false"></div>
                    <text class="banner-text" v-if="false">{{name}}</text>
                </div>
            </header>
            <cell class="gd-bg-bottom" v-for="(i ,index) in goodsTop">
                <div class="i-good margin-left32" v-if="index % 3 == 0" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
                <div class="i-good" v-if="index % 3 == 1" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
                <div class="i-good margin-right32" v-if="index % 3 == 2" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
            </cell>
            <header>
                <text>{{name}}</text>
            </header>
            <cell class="gd-bg-bottom" v-for="(i ,index) in goodsTopic1">
                <div class="i-good margin-left32" v-if="index % 3 == 0" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
                <div class="i-good" v-if="index % 3 == 1" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
                <div class="i-good margin-right32" v-if="index % 3 == 2" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
            </cell>
            <header>
                <text>{{name}}</text>
            </header>
            <cell class="gd-bg-bottom" v-for="(i ,index) in goodsTopic2">
                <div class="i-good margin-left32" v-if="index % 3 == 0" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
                <div class="i-good" v-if="index % 3 == 1" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
                <div class="i-good margin-right32" v-if="index % 3 == 2" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
            </cell>
            <header>
                <text>{{name}}</text>
            </header>
            <cell class="gd-bg-bottom" v-for="(i ,index) in goodsTopic3">
                <div class="i-good margin-left32" v-if="index % 3 == 0" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
                <div class="i-good" v-if="index % 3 == 1" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
                </div>
                <div class="i-good margin-right32" v-if="index % 3 == 2" @click="jumpItem(i.productId)">
                    <div class="item-bg">
                        <preload class="gd-img" :src="i.mainImage"></preload>
                    </div>
                    <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                    <div class="gd-price-bg">
                        <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
                    </div>
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
                if (!this.isFirstLoad) {
                    this.isFirstLoad = true;
                    this.getActivityParam(params);
                }
            }
        },
        data () {
            return {
                that: this,
                name: '',
                id: -1,
                imageUrl: '',
                testImage: '',
                page: 1,
                goodsTop: [],
                goodsTopic1: [],
                goodsTopic2: [],
                goodsTopic3: [],
                isLoading: false,
                isFirstLoad: false,
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

                googleAnalytics.trackingScreen(`Activity/${this.name}`);
                this.getActivityProduct(true);
            },
            getActivityProduct (isfirst) {
                this.$fetch({
                    method: 'GET',
                    name: 'product.topic.list'
                }).then(data => {
                    this.name = data[0].name;
                    this.imageUrl = data[0].image;
                    this.length = Math.ceil(data.count / this.pageSize)
                    if (isfirst) {
                        this.goodsTop = [];
                        this.goodsTopic1 = [];
                        this.goodsTopic2 = [];
                        this.goodsTopic3 = [];
                        this.isFirstLoad = false;
                    }
                    for (let i = 0; i < data[0].topicProducts.length; i++) {
                        const item = data[0].topicProducts[i];
                        if (i < 3) {
                            this.goodsTop.push(item);
                        } else if (i >= 3 && i < 12) {
                            this.goodsTopic1.push(item);
                        } else if (i >= 12 && i < 21) {
                            this.goodsTopic2.push(item);
                        } else if (i >= 21 && i < 30) {
                            this.goodsTopic3.push(item);
                        }
                    }
                    if (!isfirst) {
                        this.isLoading = false;
                    }
                    this.refreshApiFinished();
                }, error => {
                    this.$notice.toast({
                        message: error
                    });
                });
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
                    return Math.ceil((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            }
        }
    }

</script>
<style scoped>
    .main-list {
        width: 750px;
        margin-top: -1px;
    }

    .banner {
        width: 750px;
        height: 360px;
        display: flex;
        align-items: center;
        justify-content: center;
        position: relative;
    }

    .banner-image {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
    }

    .banner-mask {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, 0.18);
    }

    .banner-text {
        font-size: 40px;
        color: rgba(255, 255, 255, .87);
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

    .i-good{
        width: 218px;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        background-color: #fff;
        flex-direction: column;
        justify-content: start;
        align-items: center;
        margin-left: 16px;
        margin-right: 16px;
    }

    .margin-left32 {
        margin-left: 32px;
        margin-right: 0;
    }

    .margin-right32 {
        margin-left: 0;
        margin-right: 32px;
    }

    .item-bg{
        width: 218px;
        height: 218px;
    }

    .gd-img {
        width: 218px;
        height: 218px;
        overflow: hidden;
    }

    .gd-bg-bottom {
        width: 250px;
        flex-direction: row;
        align-items: start;
        justify-content: space-between;
        padding-bottom: 24px;
    }

    .gd-tlt {
        margin-top: 8px;
        font-size: 28px;
        font-weight: 700;
        line-height: 36px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
        padding: 0 16px;
        text-align: center;
    }

    .gd-price {
        font-size: 20px;
        line-height: 28px;
        color: #fff;
        background-color: #00CFE3;
        border-radius: 14px;
        padding: 0 16px;
        text-align: center;
    }

    .gd-price-bg{
        margin-top: 4px;
        height: 28px;
        margin-bottom: 16px;
    }

    .gd-button{
        margin-top: 16px;
        font-size: 24px;
        font-weight: bold;
        border-style: solid;
        border-width: 2px;
        border-color: #EF8A31;
        color: #EF8A31;
        border-radius: 4px;
        line-height: 44px;
        width: 144px;
        text-align: center;
        justify-content: center;
        align-items: center;
    }

    .indicator {
        width: 750px;
        text-align: center;
        color: #888888;
        font-size: 28px;
        padding-top: 16px;
        padding-bottom: 16px;
    }

</style>
