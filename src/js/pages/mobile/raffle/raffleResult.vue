<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header title="Prize Winners" leftBtn="icon"  ref="ref1" ></topic-header>
        <list class="content">
            <cell class="cell-content">
                <div class="content-1">
                    <!--<div class="title">-->
                        <!--<image class="t-img" src="bmlocal://assets/raffle/tubiaozhizuomoban-01.png"></image>-->

                        <!--<text class="t-word">First Prize</text>-->
                    <!--</div>-->

                    <div class="first">
                        <image class="f-img" :src="firstPrize.product.image"></image>
                        <div class="f-t-p">
                            <text class="f-title">{{firstPrize.product.productTitle}}</text>
                            <!--<text class="f-title">Kunsto Women's Leather Casual Loafer Shoes</text>-->
                            <div class="f-price">
                                <text class="f-p-1">FREE </text>
                                <text class="f-p-2">₹ {{saleUnitPrice}}</text>
                            </div>
                        </div>
                    </div>

                    <div class="bottom-s" @click="changeIsShow(firstPrize)" v-if="firstPrize.drawer.length>1" >  <!--1等奖有一个以上中一等奖-->
                        <div class="b-user-s">
                            <image class="t-imgs" src="bmlocal://assets/raffle/tubiaozhizuomoban-01.png"></image>
                            <div v-for="(item,index) in firstPrize.drawer" :class="[index===firstPrize.drawer.length-1?'':'mg-r-10']" >
                                <div class="b-u-s">
                                    <image class="b-u-i" :src="item.avatar"></image>
                                </div>
                            </div>
                            <text class="b-w">{{firstPrize.count}} {{firstPrize.count > 1 ? 'Winners' : 'Winner'}} </text>
                        </div>
                        <div class="b-i">
                            <text class="iconfont b-arrow">&#xe626;</text>
                        </div>

                    </div>

                    <div class="bottom" @click="changeIsShow(firstPrize)" v-if="firstPrize.drawer.length==1"><!--1等奖只有一个中一等奖-->

                        <div class="b-user" v-for="item in firstPrize.drawer">
                            <image class="t-img" src="bmlocal://assets/raffle/tubiaozhizuomoban-01.png"></image>
                            <div class="b-u">
                                <image class="b-u-i" :src="item.avatar"></image>
                            </div>
                            <text class="b-u-w">{{item.userName}} </text>
                            <text class="b-w1">{{firstPrize.count}} {{firstPrize.count > 1 ? 'Winners' : 'Winner'}} </text>
                        </div>
                        <div class="b-i">
                            <text class="b-wB">Details</text>
                            <text class="iconfont b-arrow">&#xe626;</text>
                        </div>
                    </div>

                </div>
            </cell>     <!--1等奖-->

            <cell class="cell-content">
                <div class="content-1">
                    <!--<div class="title">-->
                        <!--<image class="t-img" src="bmlocal://assets/raffle/tubiaozhizuomoban-02.png"></image>-->

                        <!--<text class="t-word">Second Prize</text>-->
                    <!--</div>-->

                    <div class="second">
                        <div style="border-radius: 16px">
                            <image class="s-img" :src="secondPrize.voucher.newImageDetail"></image>
                        </div>
                        <!--<image class="s-img" src="bmlocal://assets/raffle/150.png"></image>-->
                    </div>
                    <div class="bottom-s" @click="changeIsShow(secondPrize)">
                        <div class="b-user-s">
                            <image class="t-imgs" src="bmlocal://assets/raffle/tubiaozhizuomoban-02.png"></image>
                            <div v-for="(item,index) in secondPrize.drawer" :class="[index===secondPrize.drawer.length-1?'':'mg-r-10']" >
                                <div class="b-u-s">
                                    <image class="b-u-i" :src="item.avatar"></image>
                                </div>
                            </div>
                            <text class="b-w">{{secondPrize.count}} {{secondPrize.count > 1 ? 'Winners' : 'Winner'}} </text>
                        </div>
                        <div class="b-i">
                            <text class="b-wB">Details</text>
                            <text class="iconfont b-arrow">&#xe626;</text>
                        </div>

                    </div>
                </div>
            </cell>     <!--2等奖-->

            <cell class="cell-content">
                <div class="content-1">
                    <!--<div class="title">-->
                        <!--<image class="t-img" src="bmlocal://assets/raffle/tubiaozhizuomoban-03.png"></image>-->

                        <!--<text class="t-word">Third Prize</text>-->
                    <!--</div>-->

                    <div class="third">
                        <div style="border-radius: 16px">
                            <image class="th-img" :src="thirdPrize.voucher.newImageDetail"></image>
                        </div>

                    </div>
                    <div class="bottom-s" @click="changeIsShow(thirdPrize)">
                        <div class="b-user-s">
                            <image class="t-imgs" src="bmlocal://assets/raffle/tubiaozhizuomoban-03.png"></image>
                            <div v-for="(item,index) in thirdPrize.drawer" :class="[index===thirdPrize.drawer.length-1?'':'mg-r-10']" >
                                <div class="b-u-s">
                                    <image class="b-u-i" :src="item.avatar"></image>
                                </div>
                            </div>
                            <text class="b-w">{{thirdPrize.count}} {{thirdPrize.count > 1 ? 'Winners' : 'Winner'}} </text>
                        </div>
                        <div class="b-i">
                            <text class="b-wB">Details</text>
                            <text class="iconfont b-arrow">&#xe626;</text>
                        </div>
                    </div>
                </div>
            </cell>     <!--3等奖-->
        </list>
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
                this.saleUnitPrice = params.saleUnitPrice;
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
                    count: 0,
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
                    count: 0,
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
                    count: 0,
                    drawer: [],
                    isLongArr: false,
                    isShow: false
                },
                thirdHeight: 'auto',
                thirdIsLongArr: false,
                saleUnitPrice: false
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
                item.id = this.id;
                this.$router.open({
                    name: 'raffle.result.detail',
                    type: 'PUSH',
                    params: item

                });
            }
        }
    }
</script>

<style scoped>
    .blackheader{
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .iconfont{
        font-family: iconfont;
    }
    .content{
        background-color: #f4f4f4;
        width: 750px;
    }
    .cell-content{
        width: 750px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .content-1{
        width: 688px;
        margin-top: 24px;
        background-color: white;
        border-radius: 20px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .title{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin: 28px 32px;
    }
    .t-img{
        width: 36px;
        height: 36px;
        margin-right: 8px;
    }
    .t-imgs{
        width: 36px;
        height: 36px;
        margin-top: 6px;
        margin-right: 16px;
    }
    .t-word{
        font-size: 32px;
        color: black;
        font-weight: 700;
    }
    .first{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-left: 32px;
    }
    .f-t-p{
        flex-direction: column;
        justify-content: start;
        align-items: start;
        height: 148px;
    }
    .f-title{
        font-size: 24px;
        color: #000000;
        line-height: 34px;
        width: 300px;
        margin-bottom: 10px;

    }
    .f-img{
        width: 240px;
        height: 240px;
        margin-right: 24px;
    }
    .f-price{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .f-p-1{
        font-size: 32px;
        color: #EF8A31;
        font-weight: 700;
        margin-right: 28px;
    }
    .f-p-2{
        font-size: 24px;
        color: #000000;
        text-decoration: line-through;
    }
    .bottom{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        border-top-style: solid;
        border-top-width: 1px;
        border-top-color:rgba(0,0,0,.08);
        padding:14px 32px;
        margin-top: 26px;
    }
    .b-user{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .b-u{
        border-radius: 50%;
        width: 48px;
        height: 48px;
        margin-right: 10px;
    }
    .b-u-i{
        width: 48px;
        height: 48px;
    }
    .b-u-w{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
    }
    .b-w{
        font-size: 24px;
        color: #000000;
        margin-left: 24px;
        margin-top: 12px;
    }
    .b-w1{
        font-size: 24px;
        color: #000000;
        margin-left: 24px;
    }
    .b-wB{
        font-size: 24px;
        color: #1D7CD4;
    }
    .bottom-s{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        border-top-style: solid;
        border-top-width: 1px;
        border-top-color:rgba(0,0,0,.08);
        padding:14px 32px;
        margin-top: 26px;
    }
    .b-user-s{
        flex-direction: row;
        justify-content: start;
        /*align-items: center;*/
    }
    .second{
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-top: 32px;
    }
    .s-img{
        width: 592px;
        height: 174px;
    }
    .b-u-s{
        border-radius: 50%;
        width: 48px;
        height: 48px;
    }
    .third{
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-top: 32px;
    }
    .th-img{
        width: 592px;
        height: 174px;
    }
    .b-i{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .b-arrow{
        font-size: 24px;
        color: #333;
        opacity: .6;
        font-weight: 700;
    }
    .mg-r-10{
        margin-right: -10px;
    }
</style>
