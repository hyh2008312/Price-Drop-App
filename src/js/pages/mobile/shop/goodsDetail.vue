<template>

    <div class="wrapper" @touchstart="ontouchstart" @touchmove="ontouchmove" @touchend="ontouchend"  >
        <topic-header ref="ref1" ></topic-header>

        <scroller class="main-list" @scroll="scrollHandler"   offset-accuracy="300px">

            <div style="background-color: white" >

                <slider class="slider" interval="3000" auto-play="true" :index="2">
                    <div class="frame" v-for="(img, idx) in goodsImg">
                        <image class="image" resize="cover" :src="img"></image>
                        <text style="right:20px;bottom:20px;color:black;position:absolute">{{idx+1}}/{{goodsImg.length}}</text>
                    </div>
                </slider>
                <text class="iiileft">&#xe6f6;</text>
                <text class="iiiright">&#xe6f1;</text>
                <text class="onetitle">{{goods.title}}</text>
                <text class="price">¥{{goods.price}}</text>
                <text class="count">{{goods.cut_get}}人已经砍到白菜价</text>
                <div  class="rightlogo" @click="getGoodsDetail()">
                    <image class="logoimg" :src="goods.brandLogo" alt="logo"></image>
                </div>

                <div class="guncell">
                    <block-4  :items="block1.items" @noticeFinished="noNoticeFinished"></block-4>
                </div>
            </div>
                <div v-if="tabshow==true" style="position: sticky">
                    <tab   @tabTo="onTabTo" :items="tabsItems"></tab>
                </div>

            <div class="mid">
                <wxc-cell :has-arrow="true"
                          title="按规格数量选择"
                          :cell-style="cellStyle"
                          :has-top-border="true"
                          :has-bottom-border="true"
                          @wxcCellClicked="wxcCellClick"
                          :auto-accessible="false">
                </wxc-cell>

                <div class="slogan">
                    <text class="i-slg iconfont">&#xe63a; 免费包邮</text>
                    <text class="i-slg iconfont">&#xe63a; 48小时发货</text>
                    <text class="i-slg iconfont">&#xe63a; 7天退换</text>
                    <text class="i-slg iconfont">&#xe63a; 假货必赔</text>
                </div>
            </div>

                <div style="width: 750px; height: 910px;background-color: rosybrown"></div>

                <div>


                <div class="bottom-div">
                    <!--<div >-->
                        <!--<text class="leftline"></text>-->
                    <!--</div>-->
                    <text class="bottom-head">砍价规则说明</text>
                    <!--<div class="rightline">-->
                        <!--<text style="letter-spacing: -10px">———————————</text>-->
                    <!--</div>-->

                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                    <text class="bottom-text">1.很多正在参与拼多多免费拿活动的小伙伴经常会问这样一个 问题就是 ：拼多多砍价商品如果砍不到零是不是可以剩余的 金额买下来？对于这个问题 小编遗憾的告诉大家：答案是否定的
                    </text>
                </div>
            </div>
            <wxc-popup :have-overlay="isTrue"
                       popup-color="rgb(255, 255, 255)"
                       :show="isBottomShow"
                       @wxcPopupOverlayClicked="popupOverlayBottomClick"
                       pos="bottom"
                       height="718">
            <div class="popup-content">
                <image src='http://doc.zwwill.com/yanxuan/imgs/banner-1.jpg'
                       class="popup-image"></image>
                <text class="popup-price">¥{{goods.price}}</text>
                <text class="popup-yet">已选：粉色&nbsp;&nbsp;&nbsp;&nbsp;S</text>
                <text class="popup-close">&#xe632;</text>


                <text class="popup-color">颜色</text>
                <text class="popup-size">尺寸</text>
            </div>
            </wxc-popup>
            <div class="bottom-btn">
                <text class="button" @click="reload">砍价立减，最低0元拿</text>
            </div>
        </scroller>
    </div>

</template>
<script>
    import header from './header';
    import { WxcCell, WxcButton, WxcPopup } from 'weex-ui'
    import block2 from '../home/block2';
    import block3 from '../home/block3';
    import block4 from './block4';
    import tab from './tab';
    import block5 from '../home/block5';
    import { YXBANNERS, BLOCK1, TAB, BLOCK4, GOODS1, GOODS2, GOODS3 } from '../home/config';
    const animation = weex.requireModule('animation')
    const axios = weex.requireModule('bmAxios')

    // import block from './block';
    // import refresher from '../common/refresh';
    // import { GOODS } from './config'

    export default {
        components: {
            'topic-header': header,
            WxcCell, WxcButton,WxcPopup,
            'block-2': block2,
            'block-3': block3,
            'block-4': block4,
            'block-5': block5,
            'tab': tab
            // 'refresher': refresher,
            // 'block': block
        },
        data () {
            return {
                cellStyle: {
                    'padding-top': '36px',
                    'padding-bottom': '36px'
                },
                block1: {
                    title: '',
                    url: '',
                    items: [{
                        name: 'mali 已经0元拿到此产品了',
                        bg: 'http://doc.zwwill.com/yanxuan/imgs/ppbg-1.jpg',
                        url: 'http://m.you.163.com/item/manufacturer?tagId=1001003&page=1&size=100'
                    },
                        {
                            name: 'MUJI制造商',
                            bg: 'http://doc.zwwill.com/yanxuan/imgs/ppbg-2.jpg',
                            url: 'http://m.you.163.com/item/manufacturer?tagId=1001003&page=1&size=100'
                        },
                        {
                            name: 'CK制造商',
                            bg: 'http://doc.zwwill.com/yanxuan/imgs/ppbg-3.jpg',
                            url: 'http://m.you.163.com/item/manufacturer?tagId=1001003&page=1&size=100'
                        },
                        {
                            name: 'Adidas制造商',
                            bg: 'http://yanxuan.nosdn.127.net/75523d4274d85825ece16370cdb1693f.jpg',
                            url: 'http://m.you.163.com/item/manufacturer?tagId=1001003&page=1&size=100'
                        }
                    ]
                },
                goodsId: '',
                goods: {
                    title: '',
                    price: '',
                    cut_get: '',
                    brandLogo: ''
                },
                goodsImg: [
                    'http://yanxuan.nosdn.127.net/630439320dae9f1ce3afef3c39721383.jpg',
                    'http://yanxuan.nosdn.127.net/5100f0176e27a167cc2aea08b1bd11d8.jpg',
                    'http://doc.zwwill.com/yanxuan/imgs/banner-1.jpg',
                    'http://doc.zwwill.com/yanxuan/imgs/banner-2.jpg',
                    'http://doc.zwwill.com/yanxuan/imgs/banner-4.jpg',
                    'http://doc.zwwill.com/yanxuan/imgs/banner-5.jpg',
                    'http://doc.zwwill.com/yanxuan/imgs/banner-6.jpg'
                ],
                tabsItems: [{
                    name: '产品详情',
                    key: 'detail'
                }, {
                    name: '砍价规则',
                    key: 'ruler'
                }],
                isBottomShow: false,
                height: 400,
                tabKey: 'detail',
                tabshow: false,
                headerShow: true,
                positionX: 0,
                positionY: 0,
                deltaX: 0,
                deltaY: 0,
                aay: 0
            }
        },
        created () {
            this.getGoodsDetail()
        },
        methods: {
            getGoodsDetail () {
                this.$router.getParams().then(resData => {
                    this.goodsId = resData.id
                    axios.fetch({
                        method: 'GET',
                        // url: 'http://47.104.171.91/product/customer/detail/' + resData.id + '/',
                        url: 'http://47.104.171.91/product/customer/detail/' + 8 + '/',
                        // name: 'product.customer.list',
                        data: {}
                    }, (res) => {
                        if (res.status == 200) {
                            this.goods.title = res.data.title
                            this.goods.price = res.data.saleUnitPrice
                            this.goods.brandLogo = res.data.brandLogo
                            this.goodsImg = res.data.images
                            if (res.data.cutGet == null) {
                                this.goods.cut_get = 0
                            } else {
                                this.goods.cut_get = res.data.cutGet
                            }
                            this.$notice.toast({
                                message: res.status
                            })
                        } else {
                            this.$notice.toast({
                                message: res.errorMsg
                            })
                        }

                        // this.goods = resData.data
                    })
                })
            },
            wxcCellClick () {
                this.openBottomPopup()
                this.$notice.toast({
                    message: 3333
                })
            },
            openBottomPopup () {
                this.isBottomShow = true;
            },
            //非状态组件，需要在这里关闭
            popupOverlayBottomClick () {
                this.isBottomShow = false;
            },
            ontouchstart (event) {
                this.positionX = event.changedTouches[0].screenX;
                this.positionY = event.changedTouches[0].screenY;
            },
            ontouchmove (event) { //  touchmove 实现 判断上移 还是下移
                const moveX = event.changedTouches[0].screenX;
                const moveY = event.changedTouches[0].screenY;
                this.deltaX = moveX - this.positionX;
                this.deltaY = moveY - this.positionY;
                // this.$notice.toast({
                //     message: this.deltaY
                // })
                if (this.deltaY > 0 && Math.abs(this.deltaY) > Math.abs(this.deltaX) && this.topval == '1') {

                    // this.headerShow = true
                }
                if (this.deltaY < -100 && Math.abs(this.deltaY) > Math.abs(this.deltaX)) {
                    this.headerShow = false;
                }
                // this.$notice.toast({
                //     message: event.changedTouches[0].screenY
                // })
            },
            ontouchend (event) {
                this.positionX = 0
                this.positionY = 0
                this.deltaX = 0
                this.deltaY = 0
            },
            scrollHandler (e) { //  scroller 滚动函数 通过动画实现的
                console.log(e.direction)

               if (e.contentOffset.y >= -10) {
                   animation.transition(this.$refs.ref1, {
                       styles: {
                           opacity: '1',
                           height: '48px'

                       },
                       duration: 1000, // ms
                       timingFunction: 'ease',
                       needLayout: false,
                       delay: 0 // ms
                   })
                   this.topval = '1'
               } else {
                   animation.transition(this.$refs.ref1, {
                       styles: {
                           opacity: '1',
                           height: '148px'
                       },
                       duration: 1000, // ms
                       timingFunction: 'ease',
                       needLayout: false,
                       delay: 0 // ms
                   })
                   this.topval = '2'
               }
               if (Math.abs(e.contentOffset.y) > 1200) {
                   this.$notice.toast({
                       message: '55555'
                   })
                    this.tabshow = true
               } else if (Math.abs(e.contentOffset.y) < 1100) {
                   this.tabshow = false
               }
            },
            onTabTo (event) {
                this.tabKey = event.data.key;
                this.goods3 = [];
                this.goods3 = GOODS3;
                this.$nextTick(() => {
                    dom.scrollToElement(this.$refs['tab'], { animated: false })
                })
            },
            noNoticeFinished (e) {
                this.block1.items = [...BLOCK4.items];
            },
            jump2 () {

            }

        }
    }
</script>
<style scoped>

    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
        opacity: 0;
    }
    .image {
        width: 750px;
        height: 700px;
    }
    .slider {
        width: 750px;
        height: 700px;
    }
    .frame {
        width: 750px;
        height: 700px;
        position: relative;
    }
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
        /*background-color: gold;*/
        top:848px;
        right:40px;
        width: 128px;
        height: 128px;
    }
    .logoimg{
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
    .leftline {
        width: 126px;
        height: 3px;
        margin-left: 50px;
        background-color: red;

    }
    .mid{
        margin-top: 80px;
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

    .popup-image {
        width: 200px;
        height: 200px;
        margin-bottom: 40px;
        margin-top: 32px;
        margin-left: 32px;
    }
    .popup-price{
        font-size: 32px;
        font-weight: 600;
        position: absolute;
        left: 262px;
        top: 134px;
    }
    .popup-yet{
        font-size: 32px;
        position: absolute;
        left: 262px;
        top: 200px;
    }
    .popup-close{
        font-size: 32px;
        position: absolute;
        right: 32px;
        top: 32px;
    }
    .popup-color{
        position: ;
    }
    .popup-size{

    }


</style>
