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

                <div style="width: 750px; background-color: rosybrown">
                    <text v-for="txt in dectxt">
                        {{txt}}
                    </text>
                    <image v-for="i in decimg" :src="i" resize="cover" style="width:500px;height:500px"></image>
                </div>

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

            <div class="bottom-btn">
                <text class="button" @click="openBottomPopup">砍价立减，最低0元拿</text>
            </div>
        </scroller>



        <wxc-popup :have-overlay="isTrue"
                   popup-color="rgb(255, 255, 255)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupOverlayBottomClick"
                   pos="bottom"
                   ref="wxcPopup"
                   height="718">
            <div class="popup-content">
                <div class="popup-top">

                    <image :src="selimgsrc"
                           class="popup-image"></image>

                    <div class="popup-py">
                        <text class="popup-price">¥{{selsaleUnitPrice}}</text>
                        <text class="popup-yet">已选：{{selcolor}}&nbsp;&nbsp;&nbsp;&nbsp;{{selsize}}</text>
                    </div>

                    <text class="popup-close" @click="popupOverlayBottomClick">&#xe632;</text>
                </div>
                <scroller class="scroller">

                    <div class="popup-bottom">
                        <div v-for="(val, index) in goodsType" :key="index">
                            <text class="popup-color">{{val.name}}</text>

                            <div  class="popup-color-chd" >
                                <text class="popup-color-chdname  "
                                      v-for="(val1, key1) in val.value"
                                      :key="key1"
                                      :class="[val1.isActive ?'popup-color-chdname-active':'',
                                      val1.seldisable ?'popup-color-chdname-disable':'']"

                                    @click="clickColor(val1, val.value)">{{val1.value}}</text>
                            </div>
                        </div>
                    </div>
                </scroller>
                <div class="popup-btn">
                    <text class="button" @click="wxcCellClick">砍价立减，最低0元拿</text>
                </div>

            </div>
        </wxc-popup>
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
    const animation = weex.requireModule('animation');
    const axios = weex.requireModule('bmAxios');
    const globalEvent = weex.requireModule('globalEvent');

    // import block from './block';
    // import refresher from '../common/refresh';
    // import { GOODS } from './config'

    export default {
        components: {
            'topic-header': header,
            WxcCell, WxcButton, WxcPopup,
            'block-2': block2,
            'block-3': block3,
            'block-4': block4,
            'block-5': block5,
            'tab': tab
            // 'refresher': refresher,
            // 'block': block
        },
        eros: {
          beforeAppear (a) {
              this.getGoodsDetail(a)
          },
          beforeBackAppear (params) {
                this.$notice.toast({
                  message: 111111
              })
          }
        },
        // watch: {
        //     isBottomShow: function (val, oval) {
        //         if (val == true) {
        //             let self  =this
        //
        //             globalEvent.addEventListener('homeBack', options => {
        //                 self.$notice.toast({
        //                     message: val
        //                 })
        //                 self.isBottomShow = false
        //             })
        //             this.$notice.toast({
        //                 message: globalEvent
        //             })
        //         }
        //     }
        // },
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
                    // 'http://yanxuan.nosdn.127.net/630439320dae9f1ce3afef3c39721383.jpg',
                    // 'http://yanxuan.nosdn.127.net/5100f0176e27a167cc2aea08b1bd11d8.jpg',
                    // 'http://doc.zwwill.com/yanxuan/imgs/banner-1.jpg',
                    // 'http://doc.zwwill.com/yanxuan/imgs/banner-2.jpg',
                    // 'http://doc.zwwill.com/yanxuan/imgs/banner-4.jpg',
                    // 'http://doc.zwwill.com/yanxuan/imgs/banner-5.jpg',
                    // 'http://doc.zwwill.com/yanxuan/imgs/banner-6.jpg'
                ],
                tabsItems: [{
                    name: '产品详情',
                    key: 'detail'
                }, {
                    name: '砍价规则',
                    key: 'ruler'
                }],
                popup_color: ['粉色', '橙色', '黑色'],
                goodsVariants: [],
                goodsType: {},
                selsize: '',
                selcolor: '',
                selimgsrc: '',
                selsaleUnitPrice: '',
                decimg: [],
                dectxt: [],
                cactiveId: 1,
                sactiveId: '',
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
            // this.popupOverlayBottomClick()
            this.androidFinishApp()
        },
        methods: {
            getGoodsDetail (id) {
                if (id) {
                    this.$notice.toast({
                        message: id.id
                    })
                    axios.fetch({
                        method: 'GET',
                        // url: 'http://47.104.171.91/product/customer/detail/' + id.id + '/',
                        url: 'http://47.104.171.91/product/customer/detail/' + 8 + '/',
                        // name: 'product.customer.list',
                        data: {}
                    }, (res) => {
                        if (res.status == 200) {
                            this.goods.title = res.data.title;
                            this.goods.price = res.data.saleUnitPrice;
                            this.selsaleUnitPrice = res.data.saleUnitPrice;
                            this.goods.brandLogo = res.data.brandLogo;
                            this.goodsImg = res.data.images;
                            if (res.data.cutGet == null) {
                                this.goods.cut_get = 0
                            } else {
                                this.goods.cut_get = res.data.cutGet
                            }

                            this.goodsVariants = res.data.variants;
                            this.selimgsrc = res.data.images[0]
                            this.goodsType = this.operateData(res.data.attributes);
                            this.decimg = []
                            this.dectxt = []
                            for (let i = 0; i < res.data.description.length; i++) {
                                if (res.data.description[i].type == 'image') {
                                    this.decimg.push(res.data.description[i].context)
                                } else {
                                    this.dectxt.push(res.data.description[i].context)
                                }
                            }
                            // this.$notice.toast({
                            //     message: this.goodsType
                            // })
                        } else {
                            this.$notice.toast({
                                message: res.errorMsg
                            })
                        }

                        // this.goods = resData.data
                    })
                }
            },
            wxcCellClick () {
                this.openBottomPopup();
                this.$notice.toast({
                    message: 3333
                })
            },
            openBottomPopup () {
                this.isBottomShow = true;
            },

            // 非状态组件，需要在这里关闭
            popupOverlayBottomClick () {
                this.isBottomShow = false;
            },
            androidFinishApp () {
                // this.$event.on('goodDetail', (data) => {
                //     if (this.isBottomShow) {
                //         this.isBottomShow = false;
                //     } else {
                //         this.$router.finish()
                //     }
                // })
            },

            operateData (data) {
                for (let i = 0; i < data.length; i++) {
                    for (let j = 0; j < data[i].value.length; j++) {
                        data[i].value[j].isActive = false
                        data[i].value[j].seldisable = false
                    }
                }
                return data
            },
            clickColor (item, list) {
                if (item.seldisable) return
                item.isActive = !item.isActive
                for (let i = 0; i < list.length; i++) {
                    if (list[i].value != item.value) {
                        list[i].isActive = false
                    }
                }
                const color = []
                const discolor = []
                for (let j = 0; j < this.goodsVariants.length; j++) {
                    for (let k = 0; k < this.goodsVariants[j].attributeValues.length; k++) {
                        if (item.value == this.goodsVariants[j].attributeValues[k].value) {
                            color.push({
                                item: this.goodsVariants[j],
                                index: k
                            })
                            break;
                            // this.seldisable = true
                        }
                    }
                }

                if (item.isActive == true) {
                    for (let n = 0; n < color.length; n++) {
                        for (let m = 0; m < color[n].item.attributeValues.length; m++) {
                            if (m == color[n].index) {
                                continue
                            }
                            discolor.push(color[n].item.attributeValues[m].value)
                        }
                    }

                    for (let p = 0; p < this.goodsType.length; p++) {
                        if (item.id != this.goodsType[p].id) {
                            for (let u = 0; u < this.goodsType[p].value.length; u++) {
                                this.goodsType[p].value[u].seldisable = false
                                for (let o = 0; o < discolor.length; o++) {
                                    if (this.goodsType[p].value[u].value != discolor[o]) {
                                        this.goodsType[p].value[u].seldisable = true
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (let p = 0; p < this.goodsType.length; p++) {
                        if (item.id != this.goodsType[p].id) {
                            for (let u = 0; u < this.goodsType[p].value.length; u++) {
                                this.goodsType[p].value[u].seldisable = false
                            }
                        }
                    }
                }
                this.changeDom(item, color)
                // this.$notice.toast({
                //     message: item
                // })
                // this.cactiveId = id
            },
            changeDom (item, color) {
                if (item.isActive == true) {
                    if (item.id == 1) {
                        this.selsize = item.value
                    } else if (item.id == 2) {
                        this.selcolor = item.value
                    }
                } else if (item.isActive == false) {
                    if (item.id == 1) {
                        this.selsize = ''
                    } else if (item.id == 2) {
                        this.selcolor = ''
                    }
                }
                let tmp = []
                for (let i = 0; i < this.goodsType.length; i++) {
                    tmp = this.goodsType[i].images
                }
                for (let j = 0; j < tmp.length; j++) {
                    if (tmp[j].value == this.selcolor) {
                        this.selimgsrc = tmp[j].image
                    }
                }
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
                this.positionX = 0;
                this.positionY = 0;
                this.deltaX = 0;
                this.deltaY = 0
            },
            scrollHandler (e) { //  scroller 滚动函数 通过动画实现的
                console.log(e.direction);

               if (e.contentOffset.y >= -10) {
                   animation.transition(this.$refs.ref1, {
                       styles: {
                           opacity: '1',
                           height: '48px'

                       },
                       duration: 500, // ms
                       timingFunction: 'linear',
                       needLayout: false,
                       delay: 0 // ms
                   });
                   this.topval = '1'
               } else {
                   animation.transition(this.$refs.ref1, {
                       styles: {
                           opacity: '1',
                           height: '148px'
                       },
                       duration: 500, // ms
                       timingFunction: 'linear',
                       needLayout: false,
                       delay: 0 // ms
                   });
                   this.topval = '2'
               }
               if (Math.abs(e.contentOffset.y) > 1200) {
                   this.$notice.toast({
                       message: '55555'
                   });
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
    .scroller{
        max-height: 500px;

    }
    .popup-content{
        height: 718px;
        width: 750px;
        flex-direction: column;

    }
    .popup-image {
        width: 200px;
        height: 200px;
        margin-bottom: 27px;
        margin-top: 32px;
        margin-left: 32px;
    }
    .popup-top{
        flex-direction: row;
    }
    .popup-py{
        flex-direction: column;

    }
    .popup-price{
        font-size: 32px;
        font-weight: 600;
        margin-top: 134px;
        margin-left: 32px;

    }
    .popup-yet{
        font-size: 24px;
        margin-left: 32px;
        margin-top: 26px;
    }
    .popup-close{
        font-size: 32px;
        position: absolute;
        right: 32px;
        top: 32px;
    }
    .popup-bottom{

    }
    .popup-color{
        font-size: 32px;
        margin-top: 27px;
        margin-left: 32px;

    }
    .popup-color-chd{
        width: 750px;
        margin-left: -16px;
        margin-top: 26px;
        flex-direction: row;
        display: flex;
    }
    .popup-color-chdname{
        font-size: 20px;
        height:48px;
        line-height: 45px;
        text-align:center;
        margin-left: 48px;
        padding-left: 20px;
        padding-right: 20px;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(0,0,0,0.12);
        border-radius: 4px;

    }
    .popup-color-chdname-active{
        font-size: 20px;
        height:48px ;
        line-height: 45px;
        text-align:center;
        margin-left: 48px;
        padding-left: 20px;
        padding-right: 20px;
        border-width: 1px;
        border-style: solid;
        border-color: #987D1A;
        border-radius: 4px;

    }
    .popup-color-chdname-disable{
        font-size: 20px;
        background-color: #dcdcdc;
        color: #fff;
        height:48px ;
        line-height: 45px;
        text-align:center;
        margin-left: 48px;
        padding-left: 20px;
        padding-right: 20px;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(0,0,0,0.12);
        border-radius: 4px;

    }
    .popup-btn{
        position: relative;
        bottom: 0;
    }


</style>
