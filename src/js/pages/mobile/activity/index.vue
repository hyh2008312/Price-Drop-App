<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name}}</text>
        </div>
        <waterfall class="main-list" column-count="2" column-gap="14" ref="list" loadmoreoffset="30"
                   @loadmore="onloading">
            <refresher @loadingDown="loadingDown"></refresher>
            <header>
                <div class="banner">
                    <image class="banner-image" :src="imageUrl" resize="cover"></image>
                    <div class="banner-mask"></div>
                    <text class="banner-text">{{name}}</text>
                </div>
            </header>
            <cell v-for="(i ,index) in goods">
                <div class="i-gd" :class="[index % 2 ==0 ? 'margin-left16':'magin-right16']" @click="jumpWeb(i.productId)"">
                    <div class="gd-bg">
                        <image class="gd-img" resize="cover" :src="i.mainImage || src"></image>
                    </div>
                    <text class="gd-info">{{i.title}}</text>
                    <text class="gd-tip">{{i.cutGet ? i.cutGet : 0}}人已砍到了1折</text>
                    <text class="gd-price">¥{{i.saleUnitPrice}}</text>
                    <div class="gd-bargain">
                        <text class="text-bargain">砍价立减</text>
                    </div>

                </div>
            </cell>
            <!--<loading class="loading">
                <text class="indicator">加载中...</text>
            </loading>-->

        </waterfall>
    </div>
</template>
<script>
    import refresher from '../common/refresh';

    const axios = weex.requireModule('bmAxios')
    import util from '../utils/util';

    export default {
        components: {
            'refresher': refresher

        },
        eros: {},
        created() {
            this.getActivityParam();

        },
        data() {
            return {
                that: this,
                name: '',
                id: -1,
                imageUrl: '',
                testImage: 'http://yanxuan.nosdn.127.net/5100f0176e27a167cc2aea08b1bd11d8.jpg',
                goods: [],
                page: 1,
                pageSize: 6,
                src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
            }
        },
        methods: {
            getActivityParam() {
                this.$router.getParams().then(resData => {
                    this.id = resData.id;
                    this.name = resData.name;
                    this.imageUrl = resData.imageUrl;
                    this.getActivityProduct();
                });
            },
            getActivityProduct () {
                this.$fetch({
                    method: 'GET',
                    name: 'product.topic.products',
                    data: {
                        id: this.id,
                        page: this.page,
                        page_size: this.pageSize
                    }
                }).then(data => {
                    this.goods = [...data.results];
                },error => {
                    this.$notice.alert({
                        message: 'error'
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
            homeBack() {
                this.$router.back();
            }
        }
    }

</script>
<style scoped>
    .main-list {
        width: 750px;
    }

    .banner {
        width: 750px;
        height: 360px;
        display: flex;
        margin-top: -2px;
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
        height: 600px;
        margin-bottom: 20px;
    }

    .gd-bg {
        border-width: 1px;
        border-color: rgba(0, 0, 0, 0.12);
        border-style: solid;
        border-radius: 8px;
        /* background-color: #f4f4f4;*/
    }

    .gd-img {
        width: 350px;
        height: 350px;
    }

    .gd-tip {
        font-size: 20px;
        color: rgba(172, 11, 11, 0.87);
        text-align: center;
        width: 350px;
        margin-top: 10px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-price {
        font-size: 28px;
        width: 350px;
        margin-top: 4px;
        color: #000000;
        text-align: center;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-info {
        margin-top: 18px;
        text-align: center;
        lines: 1;
        font-size: 24px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-bargain {
        width: 350px;
        margin-top: 14px;
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    .text-bargain {
        width: 120px;
        height: 50px;
        line-height: 50px;
        color: black;
        font-size: 24px;
        border-style: solid;
        border-color: black;
        border-width: 2px;
        text-align: center;
    }


</style>
