<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name}}</text>
        </div>
        <waterfall class="main-list" column-count="2" column-gap="14" ref="list" loadmoreoffset="30"
                   @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <header>
                <div class="banner">
                    <image class="banner-image" :src="imageUrl" resize="cover"></image>
                    <div class="banner-mask"></div>
                    <text class="banner-text">{{name}}</text>
                </div>
            </header>
            <cell v-for="(i ,index) in goods">
                <div class="i-gd" :class="[index % 2 ==0 ? 'margin-left16':'magin-right16']" @click="jumpWeb(i.productId)">
                    <div class="gd-bg">
                        <image class="gd-img" resize="cover" :src="i.mainImage || src"></image>
                    </div>
                    <div class="gd-tlt-bg">
                        <text class="gd-tlt">Rs.{{i.lowestPrice}}</text>
                        <text class="gd-info">{{countOff(i.lowestPrice, i.saleUnitPrice)}}</text>
                    </div>
                    <text class="gd-price">Rs.{{i.saleUnitPrice}}</text>
                    <text class="gd-button">Drop Price</text>
                </div>
            </cell>
            <loading class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">加载中...</text>
            </loading>
        </waterfall>
    </div>
</template>
<script>
    import { Utils } from 'weex-ui';
    import refresher from '../common/refresh';

    export default {
        components: {
            'refresher': refresher
        },
        created () {},
        eros: {
            beforeAppear (params, options) {
                this.getActivityParam(params)
            }
        },
        data () {
            return {
                that: this,
                name: '',
                id: -1,
                imageUrl: '',
                testImage: 'http://yanxuan.nosdn.127.net/5100f0176e27a167cc2aea08b1bd11d8.jpg',
                goods: [],
                length: 2,
                page: 1,
                pageSize: 6,
                src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png',
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid()
            }
        },
        methods: {
            loadingDown () {
                this.$refs.refresh.refreshEnd()
                this.isLoading = false
                this.getActivityProduct(true)
            },
            onLoadingMore () {
                this.getActivityProduct(false)
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true
                    this.getActivityProduct(false)
                }
            },
            getActivityParam (resData) {
                this.id = resData.id;
                this.name = resData.name;
                this.imageUrl = resData.imageUrl;
                this.type = resData.type;
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
                    })
                    return
                }
                if (this.type == 'activity') {
                    this.$fetch({
                        method: 'GET',
                        name: 'product.topic.products',
                        data: {
                            id: this.id,
                            page: this.page,
                            page_size: this.pageSize
                        }
                    }).then(data => {
                        this.length = Math.ceil(data.count / this.pageSize)
                        if (isfirst) {
                            this.goods = []
                        }
                        this.page++
                        this.goods.push(...data.results)
                        if (!isfirst) {
                            this.isLoading = false
                        }
                        this.refreshApiFinished()
                    }, error => {
                        this.$notice.toast({
                            message: JSON.stringify(error)
                        });
                    })
                } else {
                    this.$fetch({
                        method: 'GET',
                        name: 'product.customer.list',
                        data: {
                            brand: this.id,
                            page: this.page,
                            page_size: this.pageSize
                        }
                    }).then(data => {
                        this.length = Math.ceil(data.count / this.pageSize)
                        if (isfirst) {
                            this.goods = []
                        }
                        this.page++
                        this.goods.push(...data.results)
                        if (!isfirst) {
                            this.isLoading = false
                        }
                        this.refreshApiFinished()
                    }, error => {
                        this.$notice.toast({
                            message: JSON.stringify(error)
                        });
                    })
                }
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
                    return Math.floor((o - s) / o * 100) + '%off'
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
        margin-bottom: 18px;
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
        height: 24px;
        background-color: black;
    }

    .navigation {
        display: flex;
        width: 750px;
        height: 112px;
        background-color: black;
        flex-direction: row;
        justify-content: flex-start;
    }

    .title {
        font-size: 40px;
        height: 112px;
        line-height: 112px;
        margin-left: 32px;
        color: rgba(255, 255, 255, .87);
    }

    .homeBack {
        font-family: iconfont;
        color: white;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .wrapper {
        background-color: white;
    }

    .margin-left16 {
        margin-left: 16px;
    }

    .magin-right16 {
        margin-right: 8px;
    }

    .i-gd {
        width: 350px;
        height: 520px;
        margin-bottom: 20px;
        align-items: center;
    }

    .gd-bg {
        border-width: 1px;
        border-color: rgba(0, 0, 0, 0.12);
        border-style: solid;
        border-radius: 8px;
        width: 350px;
        height: 350px;
        /* background-color: #f4f4f4;*/
    }

    .gd-img {
        width: 348px;
        height: 348px;
    }
    .gd-tlt {
        font-size: 28px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        line-height: 34px;
    }

    .gd-tlt-bg{
        margin-top: 16px;
        width: 350px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }

    .gd-info{
        margin-left: 8px;
        font-size: 20px;
        line-height: 24px;
        color: #EF8A31;
    }

    .gd-price {
        font-size: 20px;
        font-weight: 500;
        width: 350px;
        line-height: 24px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
        text-align: center;
        text-decoration: line-through;
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
