<template>
    <div class="wrapper">
        <topic-header title=""></topic-header>
        <scroller class="main-list" offset-accuracy="300px">
            <div style="background-color: white">
                <div class="topimg">
                    <div class="topblack"></div>
                </div>
                    <text class="iiileft">&#xe6f6;</text>
                    <text class="iiiright">&#xe6f1;</text>
                <text class="onetitle">UOOYAA/乌丫2018夏新款专柜正品复古木耳边印花喇叭袖亮丝雪纺衫</text>
                <text class="price">¥1299.00</text>
                <text class="count">1212人已经砍到白菜价</text>
                <div class="rightlogo">

                    <!--<image src="" alt="">-->
                </div>
                <wxc-cell :has-arrow="false"
                          title="mali 已100元的价格拿到了这个产品"
                          :has-top-border="true"
                          :auto-accessible="false">
                    <image class="image"
                           slot="label"
                           src="https://img.alicdn.com/tfs/TB1eLvjSXXXXXaiXXXXXXXXXXXX-144-166.jpg"></image>
                </wxc-cell>
            </div>

            <div class="mid">
                <wxc-cell :has-arrow="true"
                          title="按规格数量选择"
                          :cell-style="cellStyle"
                          :has-top-border="true"
                          :has-bottom-border="true"
                          :auto-accessible="false">
                </wxc-cell>

                <div class="slogan">
                    <text class="i-slg iconfont">&#xe63a; 免费包邮</text>
                    <text class="i-slg iconfont">&#xe63a; 48小时发货</text>
                    <text class="i-slg iconfont">&#xe63a; 7天退换</text>
                    <text class="i-slg iconfont">&#xe63a; 假货必赔</text>
                </div>
            </div>


            <web ref="webview" style="width: 750px; height: 910px" src="https://vuejs.org"
                 @pagestart="onPageStart" @pagefinish="onPageFinish" @error="onError" @receivedtitle="onReceivedTitle"></web>


            <div>
                <text class="bottom-head">砍价规则</text>
                <div class="bottom-div">
                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                </div>
            </div>
            <div class="bottom-btn">
                <text class="button" @click="reload">砍价立减，最低0元拿</text>
            </div>
        </scroller>
    </div>

</template>
<script>
    import header from './header';
    import { WxcCell, WxcButton } from 'weex-ui'

    // import block from './block';
    // import refresher from '../common/refresh';
    // import { GOODS } from './config'

    export default {
        components: {
            'topic-header': header,
            WxcCell, WxcButton
            // 'refresher': refresher,
            // 'block': block
        },
        data () {
            return {
                cellStyle: {
                    'padding-top': '36px',
                    'padding-bottom': '36px'
                },
                goods: [],
                goodList: []
            }
        },
        created () {
            this.$navigator.setCenterItem({
                navShow: false,
                fontSize: '30'
            }, () => {
                // 点击回调
                this.$notice.toast({
                    message: '点击了标题'
                })
            })
        },
        methods: {
            getGoods () {
                // this.$fetch({
                //     method: 'GET',
                //     name: 'yanxuan_shop_getGoods',
                //     data: {}
                // }).then(resData => {
                //     this.goods = resData.data
                // }, error => {

                // })

                this.goods = ''
            },
            jump2 () {

            }

        }
    }
</script>
<style scoped>
    .iiileft{
        font-family: iconfont;
        color: white;
        font-size: 32px;
        background-color: rgba(0,0,0,.38);
        position: absolute;
        border-radius:24px ;
        top:80px;
        left:40px;
        width: 48px;
        line-height: 48px;
        text-align: center;
        align-items: center;
    }
    .iiiright{
        font-family: iconfont;
        color: white;
        font-size: 32px;
        background-color: rgba(0,0,0,.38);
        position: absolute;
        border-radius:24px ;
        top:80px;
        right:40px;
        width: 48px;
        line-height: 48px;
        text-align: center;
        align-items: center;
    }
    .topimg{
        flex-direction:row;
        height: 720px;
        background-color: darksalmon;
    }
    .topblack{
        width: 750px;
        height:48px;
        position: fixed;
        top: 0;
        right: 0;
        opacity: .5;
        background-color: black;
    }
    .onetitle{
        width:586px;
        height: 96px;
        margin-top: 32px;
        margin-left: 32px;
        opacity: 0.435;
        font-family: PingFangSC-Light;
        font-size: 28px;
        color: #000;
        line-height: 48px;
        letter-spacing: 0;
    }
    .price{
        font-family: PingFangSC-Semibold;
        margin-top: 24px;
        margin-left: 32px;
        font-size: 48px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
    }
    .rightlogo{
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 64px;
        position: absolute;
        background-color: gold;
        top:848px;
        right:40px;
        width: 128px;
        height: 128px;
    }
    .count{
        color: rgba(172,11,11,0.87);
        margin-top: 24px;
        margin-left: 32px;
        margin-bottom: 44px;
        font-size:24px;
    }
    .image {
        width: 48px;
        height: 48px;
        margin-right: 20px;
    }
    .mid{
        margin-top: 14px;
        background-color: white;
    }
    .slogan{
        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
        background-color: #fff;
        border-bottom: 2px solid black;
        margin-bottom: 16px;
    }
    .i-slg{
        height: 66px;
        font-size: 26px;
        padding-top: 16px;
        flex: 1;
        text-align: center;
        color: black;
    }
    .bottom-head{
        text-align: center;
        font-family: PingFangSC-Semibold;
        font-weight: 500;
        font-size: 28px;
        margin: 40px 0;
        color: rgba(0,0,0,0.87);
    }
    .bottom-div{
        width: 750px;
        box-shadow: 0 1px 1px 0;
        background-color: #fff;
        padding-top: 48px;
        margin-bottom: 100px;
    }
    .bottom-text{
        margin-left: 50px;
        margin-right: 50px;
        padding-top: 24px;
        text-align: left;
    }
    .bottom-btn{
        background-color: white;
        border: solid 1px black ;
        box-shadow: 0 -1px 1px 0;
        width: 750px;
        position: fixed;
        bottom: 0;
        right: 0;
        overflow: hidden;
    }
    .button{
        color: #fff;
        background-color: #000;
        border-color: #2e6da4;
        border-radius: 12px;
        padding-top: 20px;
        margin-top: 16px;
        margin-left: 12px;
        padding-bottom: 20px;
        margin-right: 12px;
        margin-bottom: 16px;
        font-size: 36px;
        text-align: center;
        font-weight: 500;
    }



</style>
