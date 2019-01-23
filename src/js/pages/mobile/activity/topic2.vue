<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="$router.back();">&#xe6f6;</text>
            <text class="title">{{name}}</text>
        </div>

        <list  class="all-list" @scroll="scrollHandler" offset-accuracy="1300px" >
            <cell>
                <image style="width: 750px;height: 358px" :src="bgImg"></image>
            </cell>
            <header>
                <scroller class="scroller" scroll-direction="horizontal" :style="{backgroundColor:tabColor}" ref="top">
                    <text class="tab-txt" :class="[flag == item.id ? 'tab-txt-active' : '']"
                          v-for="item in productList" @click="choseActive(item.id)">{{item.name}}</text>
                </scroller>
            </header>
            <cell  v-for="item in products" :style="{backgroundColor:bgColor}" >
                <div  v-if="item.id" :ref="item.name">
                    <div class="empty-div" :ref="item.id"></div>  <!--设置假元素进行ref  进行offset-->
                    <div class="price-title"  >
                        <image style="width: 66px;height: 44px" :src="headImg.images[0]"></image>
                        <text class="p-t-word">{{item.name}}</text>
                        <image style="width: 66px;height: 44px" :src="headImg.images[1]"></image>
                    </div>
                </div>
                <div class="i-row" v-if="!item.id">

                    <div v-for="i in item.items" class="i-product" @click="openGoodDetail(i.productId)">

                        <image :src="i.mainImage" class="i-p-img"></image>
                        <div class="i-p-price">
                            <text class="i-p1">₹{{parseInt(i.unitPrice)}}</text>
                            <text class="i-p2">₹{{parseInt(i.saleUnitPrice)}}</text>
                            <!--<text class="i-p3">{{n.p3}}</text>-->
                            <text class="i-p3">{{countOff(i.unitPrice,i.saleUnitPrice)}}</text>
                        </div>

                    </div>
                </div>
            </cell>
        </list>
        <div class="ic-top" @click="scrollTop">
            <text class="i-t">&#xe76e;</text>
        </div>
    </div>
</template>

<script>
    const dom = weex.requireModule('dom');
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    import { baseUrl } from '../../../config/apis';
    export default {
        data () {
            return {
                name: '',
                bgColor: '',
                tabColor: '',
                bgImg: '',
                headImg: '',
                productList: [],
                flag: 1,
                defultFlag: '',
                products: ''
            }
        },
        eros: {
            appeared (params, options) {
                if (params) {
                    this.getActivityParam(params);
                }
            }
        },
        created () {
            // this.getData()
        },
        methods: {
            getActivityParam (resData) {
                googleAnalytics.trackingScreen(`Activity/${this.name}`);
                this.getData(resData.id);
            },
            getData (id) {
                this.$notice.loading.show()
                this.$fetch({
                    methods: 'GET',
                    url: `${baseUrl}/activity/app/detail/${id}/`
                    // url: `${baseUrl}/activity/app/detail/3/`
                }).then((res) => {
                    this.name = res.name
                    this.bgImg = res.image
                    this.productList = res.activityTabs
                    this.bgColor = res.color
                    this.tabColor = res.tabColor
                    this.headImg = res.templateContentJson
                    this.flag = res.activityTabs[0].id
                    this.defultFlag = res.activityTabs[0].id

                    this.handlerData(res)
                    // this.$notice.alert({
                    //     message: this.bgImg
                    // })
                    this.$notice.loading.hide()
                }).catch((res) => {
                    this.$notice.loading.hide()
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            choseActive (i) {
                this.flag = i
                const el = this.$refs[i][0]
                // dom.scrollToElement(el, { offset: -55 })
                this.$nextTick(() => {
                    dom.scrollToElement(el, { offset: -50, animated: false })
                })
            },
            scrollHandler (e) {
                for (let item of this.productList) {
                    dom.getComponentRect(this.$refs[item.name][0], (res) => {
                        if (res.size && res.size.top < 500 && res.size.top > 0) {
                            this.flag = item.id;
                        }
                    })
                }
            },
            handlerData (arr) {
                this.products = [];
                let PRODUCTS = []
                for (let j = 0; j < arr.activityTabs.length; j++) {
                    PRODUCTS.push({
                        id: arr.activityTabs[j].id,
                        name: arr.activityTabs[j].name
                    })
                }
                if (PRODUCTS.length > 0) {
                    for (let i = 0; i < PRODUCTS.length;i++) {
                        const item = PRODUCTS[i];
                        const m = arr.activityTabs[i];
                        item.id = m.id;
                        this.products.push(item)
                        if (m.activityProducts.length > 0) {
                            let goods = [];
                            for (let i = 0; i < m.activityProducts.length; i++) {
                                const itm = m.activityProducts[i];
                                goods.push(itm);
                                if ((i > 0 && i % 2 == 1) || i == m.activityProducts.length - 1) {
                                    this.products.push({
                                        items: [...goods]
                                    });
                                    goods = [];
                                }
                            }
                        }
                    }
                }
            },
            openGoodDetail (id) {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: id
                    }
                })
            },
            scrollTop () {
                this.flag = this.defultFlag
                const el = this.$refs['top']
                this.$nextTick(() => {
                    dom.scrollToElement(el, { offset: 0, animated: false })
                })
            },
            tranArr (data) {
                let arr = [];
                const rArr = [];
                // this.$notice.alert({
                //     message: data
                // })
                // if (data.length >= 4) {
                for (let i = 0; i < data.length; i++) {
                    const item = data[i];
                    arr.push(item);
                    if ((i > 0 && i % 2 == 1) || i == data.length - 1) {
                        rArr.push(arr);
                        arr = [];
                    }
                }
                return rArr
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.ceil((o - s) / o * 100) + '% OFF'
                    // return (o - s) / o * 100 + '% OFF'
                } else {
                    return ''
                }
            }
        }
    }
</script>

<style scoped>
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
    .scroller{
        width: 750px;
        height: 80px;
        /*background-color: #2F0354;*/
        flex-direction: row;
        justify-content: start;
        align-items: center;
        position: sticky;
    }
    .tab-txt{
        color: rgba(255,255,255,.72);
        font-size: 24px;
        padding-top: 24px;
        padding-bottom: 24px;
        margin-right: 48px;
        margin-left: 32px;
    }
    .tab-txt-active{
        color: white;
        font-size: 24px;
        font-weight: 700;
        padding-top: 24px;
        padding-bottom: 24px;
        border-bottom-style: solid;
        border-bottom-width: 4px;
        border-bottom-color: white;
    }
    .all-list{
        /*background-color: #400D6A;*/
    }
    .empty-div{
        height: 34px;
    }
    .ic-top{
       flex-direction: row;
        align-items: center;
        justify-content: center;
        position: absolute;
        bottom:112px;
        right:48px;
        height: 80px;
        width: 80px;
        background-color: #FFFFFF;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.38);
        border-radius: 50%;
    }
    .i-t{
        font-family: iconfont;
        font-size: 40px;
        color: black;
    }
    .price-title{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        /*margin-top: 34px;*/
        margin-bottom: 20px;
    }
    .p-t-word{
        font-size: 28px;
        color: #FFFFFF;
        font-weight: 700;
        margin: 0 16px;
    }
    .i-row{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin: 0 0 0 32px;
        /*height: 168px;*/
    }
    .i-product{
        background-color: white;
        margin-right: 24px;
        margin-bottom: 24px;
        border-radius: 8px;
    }
    .i-p-img{
        width: 332px;
        height: 332px;
        /*margin: 24px;*/
    }
    .i-p-price{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        margin:32px 0 22px 30px ;
    }
    .i-p1{
        font-size: 32px;
        color: #000000;
        font-weight: 700;
        margin-right: 10px;
    }
    .i-p2{
        text-decoration: line-through;
        font-size: 20px;
        margin-right: 10px;
    }
    .i-p3{
        font-size: 20px;
        color: #EE8E38;
    }
</style>
