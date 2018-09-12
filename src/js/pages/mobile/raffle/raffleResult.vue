<template>
    <div class="wrapper">
        <list class="content">
            <cell>
                <topic-header title="Prize Winners" leftBtn="icon"  ref="ref1" ></topic-header>
            </cell>
            <cell class="content-4">
                <div class="title-bg">
                    <text class="icon-first">&#xe747;</text>
                    <text class="title">First Prize</text>
                </div>
                <div class="product-bg">
                    <image class="product-image" :src="firstPrize.product.image"></image>
                </div>
                <text class="product-title">{{firstPrize.product.productTitle}}</text>
                <div class="content-5 border-top">
                    <text class="title-1">{{firstPrize.drawer.length > 1 ? 'Winners' : 'Winner'}} ({{firstPrize.drawer.length}})</text>
                    <text v-if="firstPrize.isLongArr" class="btn" @click="changeIsShow(firstPrize)">VIEW MORE</text>
                </div>
                <list :style="{height: firstHeight}">
                    <cell class="content-2" v-for="item in firstPrize.drawer">
                        <div class="avatar-bg">
                            <image class="avatar" :src="item.avatar"></image>
                        </div>
                        <text class="content-1">{{item.userName}}</text>
                        <text class="content-3">won a free product</text>
                    </cell>
                </list>
            </cell>
            <cell class="content-4">
                <div class="title-bg">
                    <text class="icon-second">&#xe749;</text>
                    <text class="title">Second Prize</text>
                </div>
                <div class="voucher-bg">
                    <image class="product-image-1" :src="secondPrize.voucher.image"></image>
                    <text class="product-title-1">{{secondPrize.voucher.name}} Gift Voucher</text>
                </div>
                <div class="content-5">
                    <text class="title-2">{{secondPrize.drawer.length > 1 ? 'Winners' : 'Winner'}} ({{secondPrize.drawer.length}})</text>
                    <text v-if="secondPrize.isLongArr" class="btn" @click="changeIsShow(secondPrize)">VIEW MORE</text>
                </div>
                <list :style="{height: secondHeight}">
                    <cell class="content-2" v-for="item in secondPrize.drawer">
                        <div class="avatar-bg">
                            <image class="avatar" :src="item.avatar"></image>
                        </div>
                        <text class="content-1">{{item.userName}}</text>
                        <text class="content-3">won a gift voucher</text>
                    </cell>
                </list>
            </cell>
            <cell class="content-4 md-padding-bottom">
                <div class="title-bg">
                    <text class="icon-third">&#xe749;</text>
                    <text class="title">Third Prize</text>
                </div>
                <div class="voucher-bg">
                    <image class="product-image-1" :src="thirdPrize.voucher.image"></image>
                    <text class="product-title-1">{{thirdPrize.voucher.name}} Gift Voucher</text>
                </div>
                <div class="content-5">
                    <text class="title-2">{{thirdPrize.drawer.length > 1 ? 'Winners' : 'Winner'}} ({{thirdPrize.drawer.length}})</text>
                    <text v-if="thirdPrize.isLongArr" class="btn" @click="changeIsShow(thirdPrize)">VIEW MORE</text>
                </div>
                <list :style="{height: thirdHeight}">
                    <cell class="content-2" v-for="item in thirdPrize.drawer">
                        <div class="avatar-bg">
                            <image class="avatar" :src="item.avatar"></image>
                        </div>
                        <text class="content-1">{{item.userName}}</text>
                        <text class="content-3">won a gift voucher</text>
                    </cell>
                </list>
                <div class="content-5" v-if="thirdPrize.isLongArr" @click="changeIsShow(thirdPrize)">
                    <text v-if="!thirdPrize.isShow" class="btn" >SEE ALL</text>
                </div>
            </cell>
        </list>

        <div class="blackheader"></div>
    </div>
</template>

<script>
    import header from './header';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    import { baseUrl } from '../../../config/apis';

    export default {
        components: {
            'topic-header': header
        },
        eros: {
            appeared (params, options) {
                this.id = params.id;
                googleAnalytics.trackingScreen(`Raffle/Detail/${this.id}`);
                this.getPrizeResult();
            }
        },
        data () {
            return {
                id: '',
                icon: {},
                firstPrize: {
                    index: 0,
                    product: {
                        image: '',
                        productTitle: ''
                    },
                    drawer: [],
                    isLongArr: false,
                    isShow: false
                },
                firstHeight: 'auto',
                firstIsLongArr: false,
                secondPrize: {
                    index: 1,
                    voucher: {
                        image: '',
                        name: ''
                    },
                    drawer: [],
                    isLongArr: false,
                    isShow: false
                },
                secondHeight: 'auto',
                secondIsLongArr: false,
                thirdPrize: {
                    index: 2,
                    voucher: {
                        image: '',
                        name: ''
                    },
                    drawer: [],
                    isLongArr: false,
                    isShow: false
                },
                thirdHeight: 'auto',
                thirdIsLongArr: false
            }
        },
        computed: {
            firstHeight: {
                get: function () {
                    return this.firstHeight
                },

                set: function (v) {
                    this.firstHeight = v
                }
            }
        },
        methods: {
            getPrizeResult () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    url: baseUrl + '/lottery/draw/result/' + this.id + '/'
                }).then((res) => {
                    this.$notice.loading.hide();
                    this.firstPrize = res.firstPrize;
                    this.secondPrize = res.secondPrize;
                    this.thirdPrize = res.thirdPrize;
                    if (this.firstPrize.drawer.length > 3) {
                        this.firstPrize.isLongArr = true;
                        this.firstHeight = '240px';
                    }
                    if (this.secondPrize.drawer.length > 3) {
                        this.secondPrize.isLongArr = true;
                        this.secondHeight = '240px';
                    }
                    if (this.thirdPrize.drawer.length > 3) {
                        this.thirdPrize.isLongArr = true;
                        this.thirdHeight = '240px';
                    }
                    this.firstPrize.isShow = false;
                    this.secondPrize.isShow = false;
                    this.thirdPrize.isShow = false;
                    this.firstPrize.index = 0;
                    this.secondPrize.index = 1;
                    this.thirdPrize.index = 2;
                    this.firstPrize.name = 'First Prize';
                    this.secondPrize.name = 'Second Prize';
                    this.thirdPrize.name = 'Third Prize';
                }).catch((res) => {
                    // this.$notice.loading.hide();
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            changeIsShow (item) {
                this.$router.open({
                    name: 'raffle.result.detail',
                    type: 'PUSH',
                    params: item

                })
            }
        }
    }
</script>

<style scoped>
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .content{
        margin-top: 48px;
        background-color: #fff;
        width: 750px;
    }

    .title{
        font-family: ProximaNova;
        font-size: 32px;
        line-height: 38px;
        font-weight: bold;
        margin-left: 16px;
    }

    .title-bg{
        margin-top: 48px;
        margin-bottom: 16px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .title-1{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
    }

    .border-top {
        border-top-width: 1px;
        border-top-color: rgba(0,0,0,.08);
        border-top-style: solid;
    }

    .title-2{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
    }

    .product-bg{
        width: 686px;
        height: 320px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .product-image{
        width: 320px;
        height: 320px;
    }

    .product-image-1{
        width: 686px;
        height: 292px;
    }

    .product-title{
        font-size: 24px;
        line-height: 28px;
        font-family: ProximaNova;
        margin-top: 16px;
        margin-bottom: 16px;
    }

    .product-title-1{
        width: 686px;
        padding-left: 24px;
        font-family: ProximaNova;
        font-weight: 700;
        font-size: 24px;
        line-height: 80px;
        color: rgba(0,0,0,0.87);
    }

    .voucher-bg{
        width: 686px;
        height: 372px;
        border-radius: 24px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.12);
        border-bottom-width: 1px;
        border-right-style: solid;
        border-right-color: rgba(0,0,0,0.12);
        border-right-width: 1px;
    }

    .avatar{
        width: 64px;
        height: 64px;
    }

    .avatar-bg{
        width: 64px;
        height: 64px;
        border-radius: 32px;
        margin-right: 16px;
    }

    .content-1{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        margin-right: 8px;
    }

    .content-2{
        width: 686px;
        height: 80px;
        padding-top: 16px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .content-3{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
    }

    .content-4{
        padding: 0 32px;
    }

    .content-5{
        width: 686px;
        padding-top: 24px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .btn{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        text-align: center;
        color: #EF8A31;
        font-weight: 700;
    }

    .md-padding-bottom{
        padding-bottom: 32px;
    }

    .icon-first {
        font-family: iconfont;
        font-size: 32px;
        color: #69549d;
    }
    .icon-second {
        font-family: iconfont;
        font-size: 32px;
        color: #ffba1b;
    }
    .icon-third {
        font-family: iconfont;
        font-size: 32px;
        color: #00CFE3;
    }
</style>
