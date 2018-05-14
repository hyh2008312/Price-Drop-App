<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack">&#xe6f6;</text>
            <text class="title">美妆</text>
        </div>
        <waterfall class="main-list" column-count="2" column-gap="14" ref="list" loadmoreoffset="30"
                   @loadmore="onloading">
            <refresher @loadingDown="loadingDown"></refresher>
            <header>
                <div class="banner">
                    <image class="banner-image" :src="testImage" resize="cover"></image>
                    <text class="banner-text">精品的美妆</text>
                </div>
            </header>
            <cell v-for="(i, index) in goods3">
                <div class="i-gd" :class="[index % 2 ==0 ? 'margin-left16':'magin-right16']" @click="jumpWeb(i.url)">
                    <div class="gd-bg">
                        <image class="gd-img" resize="cover" :src="i.img"></image>
                    </div>
                    <text class="gd-info">{{i.info}}</text>
                    <text class="gd-tip">1122人已0元拿</text>
                    <text class="gd-price">¥89.00</text>
                    <div class="gd-bargain">
                        <text class="text-bargain">砍价立减</text>
                    </div>

                </div>
            </cell>
            <loading class="loading">
                <text class="indicator">加载中...</text>
            </loading>

        </waterfall>
    </div>
</template>
<script>
    import {GOODS1, GOODS2, GOODS3} from './config';
    import refresher from '../common/refresh';

    export default {
        components: {
            'refresher': refresher

        },
        eros: {},
        created() {
            this.init()
        },
        data() {
            return {
                testImage: 'http://yanxuan.nosdn.127.net/5100f0176e27a167cc2aea08b1bd11d8.jpg',
                goods1: [],
                goods2: [],
                goods3: [],
                showLoading: 'hide',
            }
        },
        methods: {
            jumpWeb(_url) {
                this.$router.toWebView({
                    url: _url,
                    title: ''
                })
            },
            onloading() {
                this.showLoading = 'show';
                this.goods3.push(...this.goods1);
                this.showLoading = 'hide';
            },
            loadingDown() {
                this.goods3 = [];
                this.goods3.push(...this.goods2);
                this.goods3.push(...this.goods1);
            },
            init() {
                this.getGoods1()
                this.getGoods2()
                this.getGoods3()
            },
            getGoods1() {
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
            getGoods2() {
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
            getGoods3() {
                // this.$fetch({
                //     method: 'GET',
                //     name: 'yanxuan_home_getGoods3',
                //     data: {}
                // }).then(resData => {
                //     this.goods3 = resData.data
                // }, error => {

                // })

                this.goods3 = GOODS3
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
