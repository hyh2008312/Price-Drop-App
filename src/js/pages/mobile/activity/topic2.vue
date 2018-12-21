<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name}}</text>
        </div>

        <list  class="all-list" @scroll="scrollHandler" offset-accuracy="200px" @scrollToElement="">
            <cell>
                <image style="width: 750px;height: 358px" :src="bgImg"></image>
            </cell>
            <header>
                <scroller class="scroller" scroll-direction="horizontal" :style="{backgroundColor:tabColor}">
                    <text class="tab-txt" :class="[flag == item.id ? 'tab-txt-active' : '']"
                          v-for="item in productList" @click="choseActive(item.id)">{{item.name}}</text>
                </scroller>
            </header>


            <cell v-for="item in productList"  :style="{backgroundColor:bgColor}" :ref="item.name">
                <div class="empty-div" :ref="item.id"></div>  <!--设置假元素进行ref  进行offset-->
                <div class="price-title"  >
                    <image style="width: 66px;height: 44px" :src="headImg.images[0]"></image>
                    <text class="p-t-word">{{item.name}}</text>
                    <image style="width: 66px;height: 44px" :src="headImg.images[1]"></image>
                </div>

                <div  v-for="i in tranArr(item.activityProducts)" class="i-row" >

                    <div v-for="n in i" class="i-product">
                        <image :src="n.mainImage" class="i-p-img"></image>
                        <div class="i-p-price">
                            <text class="i-p1">{{n.saleUnitPrice}}</text>
                            <text class="i-p2">{{n.unitPrice}}</text>
                            <text class="i-p3">{{n.p3}}</text>
                        </div>
                    </div>
                </div>
            </cell>
        </list>

    </div>
</template>

<script>
    const dom = weex.requireModule('dom');
    import { baseUrl } from '../../../config/apis';
    export default {
        data () {
            return {
                name: 'Headphones',
                bgColor: '',
                tabColor: '',
                bgImg: '',
                headImg: '',
                productList: [],
                subCategory: [
                    {
                        id: 1,
                        name: 'Below $300'
                    }, {
                        id: 2,
                        name: '$300-$500'
                    }, {
                        id: 3,
                        name: '$500-$800'
                    }, {
                        id: 4,
                        name: 'Above $800'
                    }],
                flag: 1
                // productList: [
                //     {
                //         c: 'Below $300',
                //         id: 1,
                //         product: [
                //             {
                //                 p1: '$150',
                //                 p2: '$300',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             },
                //             {
                //                 p1: '$250',
                //                 p2: '$400',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             },
                //             {
                //                 p1: '$350',
                //                 p2: '$500',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             },
                //             {
                //                 p1: '$350',
                //                 p2: '$500',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             },
                //             {
                //                 p1: '$350',
                //                 p2: '$500',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             }
                //         ]
                //     },
                //     {
                //         c: '$300-$500',
                //         id: 2,
                //         product: [
                //             {
                //                 p1: '$1500',
                //                 p2: '$300',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             }, {
                //                 p1: '$2500',
                //                 p2: '$300',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             }, {
                //                 p1: '$3500',
                //                 p2: '$300',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             }, {
                //                 p1: '$150',
                //                 p2: '$300',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             }, {
                //                 p1: '$4500',
                //                 p2: '$300',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             }
                //         ]
                //     },
                //     {
                //         c: '$500-$800',
                //         id: 3,
                //         product: [
                //             {
                //                 p1: '$150',
                //                 p2: '$300',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             }
                //         ]
                //     },
                //     {
                //         c: 'Above $800',
                //         id: 4,
                //         product: [
                //             {
                //                 p1: '$150',
                //                 p2: '$300',
                //                 p3: '50%OFF',
                //                 img: 'bmlocal://assets/home/mask-head.png'
                //             }
                //         ]
                //     }
                // ]
            }
        },
        created () {
            this.getData()
        },
        methods: {
            choseActive (i) {
                this.flag = i
                const el = this.$refs[i][0]
                // dom.scrollToElement(el, { offset: -55 })
                // this.$nextTick(() => {
                    dom.scrollToElement(el, { offset: -50, animated: false });
                // })

            },
            getData () {
                this.$fetch({
                    methods: 'GET',
                    // url: `${baseUrl}/product/customer/detail/${id}/`,
                    url: `${baseUrl}/activity/app/detail/27/`
                }).then((res) => {
                    this.name = res.name
                    this.bgImg = res.image
                    this.productList = res.activityTabs
                    this.bgColor = res.color
                    this.tabColor = res.tabColor
                    this.headImg = res.templateContentJson
                    this.flag = res.activityTabs[0].id
                    // this.$notice.alert({
                    //     message: this.bgImg
                    // })
                }).catch((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            scrollHandler (e) {

                // this.$notice.alert({
                //     message: e.currentTarget.children[2].children[0].ref
                // })
                // this.$notice.alert({
                //     message: this.$refs['456'][0].ref
                // })

                // if (e.currentTarget.children[2].children[0].ref == this.$refs['456'][0].ref) {
                //     this.flag = 43;
                // }

                for (let item of this.productList) {

                    // let id = item.id
                    // this.$notice.alert({
                    //     message: e
                    // })
                    dom.getComponentRect(this.$refs[item.name][0], (res) => {
                        // this.$notice.alert({
                        //     message: res
                        // })
                        if (res.size && res.size.top < 500 && res.size.top > 0) {
                            this.flag = item.id;
                        }
                    })
                }

                // if (Math.abs(e.contentOffset.y) >= 112) {
                //     this.opacity = (Math.abs(e.contentOffset.y) - 112) / 200 > 1 ? 1 : (Math.abs(e.contentOffset.y) - 112) / 200
                // } else {
                //     this.opacity = 0
                // }
                //
                // if (Math.abs(e.contentOffset.y) > 1200) {
                //     this.tabshow = true
                // } else if (Math.abs(e.contentOffset.y) < 1100) {
                //     this.tabshow = false
                // }
                // if (e.contentSize.height + e.contentOffset.y < 1350) {
                //     this.defaultTab = 'policy'
                // } else {
                //     this.defaultTab = 'dec'
                // }
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
        margin: 0 32px;
        /*height: 168px;*/
    }
    .i-product{
        background-color: white;
        margin-right: 18px;
        margin-bottom: 18px;
        border-radius: 8px;
    }
    .i-p-img{
        width: 287px;
        height: 287px;
        margin: 24px;
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
