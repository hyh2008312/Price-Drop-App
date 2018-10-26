<template>
    <div class="wrapper">
        <topic-header class="t-h1" title="Cart" leftBtn="y" :rightBtn="rightBtnWord" v-on:change="changeBtn($event)" ></topic-header>
        <div class="blackheader"></div>
        <!--<div>-->
            <!--<text>Buy</text>-->
            <!--<text>₹ 100</text>-->
            <!--<text>more to use your a</text>-->
            <!--<text>₹ 100</text>-->
            <!--<text>voucher!</text>-->
            <!--<text>Add Item >></text>-->
        <!--</div>-->

        <list class="content" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <cell  v-for="(i,index) in goodsList"
                   :key="i.id" style="width: 750px">
                <div :class="[index == goodsList.length-1?'mg-b10':'']">
                    <div class="line-card">
                        <div class="lc-top">
                            <div  @click="selGoods(i)">
                                <div :class="[i.sel?'lc-t-dot-select':'lc-t-dot',i.sumStock==0?'lc-t-dot-disable':'']" >
                                    <text class="dot-sel">&radic;</text>
                                </div>
                            </div>
                            <div class="overflow-img">
                                <image class="lc-t-img" :src="i.mainImage" ></image>
                                <image class="lc-sold-out" v-if="i.sumStock==0" src="bmlocal://assets/sold-out.png"></image>
                                <!--<div>-->
                                <text class="blue-word" v-if="i.productType=='flash'">Flash Sale</text>
                                <!--</div>-->
                            </div>
                            <!--<div class="lc-t-img"  ></div>-->
                            <div class="lc-tw">
                                <!--<text class="lc-tw1">Toyota’s Latest is an Entire Mobility Service Platform</text>-->
                                <text class="lc-tw1">{{i.title}}</text>
                                <div class="lc-tw2">
                                    <text class="lc-tw-b">{{i.attributes}}</text>
                                    <div class="lc-tw-p">
                                        <text class="lc-tw-p1" v-if="i.productType=='flash'">₹{{calc(i.unitPrice,i.flashDiscount)}}</text>
                                        <text class="lc-tw-p1" v-if="i.productType=='direct'">₹{{i.unitPrice}}</text>
                                        <text class="lc-tw-p2">₹{{i.saleUnitPrice+'.00'}}</text>
                                        <text class="lc-tw-p3">{{countOff(calc(i.unitPrice,i.flashDiscount), i.saleUnitPrice)}}</text>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="lc-bottom">
                            <div class="lc-tt" v-if="i.productType=='flash'">
                                <text class="lc-tt-w">Price expires in</text>
                                <div class="wrapper-timer">
                                    <wxc-countdown tpl="{h}:{m}:{s}"
                                                   :time="i.endTimestamp * 1000"
                                                   :timeBoxStyle="{backgroundColor: '#FFF','border-radius': '8px'}"
                                                   :timeTextStyle="{fontSize: '20px', color: '#EF8A31'}"
                                                   :dotTextStyle="{color: '#EF8A31', fontSize: '20px'}"
                                                   :dotBoxStyle="{}"
                                                   :style="{justifyContent: 'center'}">
                                    </wxc-countdown>
                                </div>
                            </div>
                            <div v-if="i.productType!=='flash'">
                                <!--<text>Price expires in</text>-->
                                <!--<text> 23 : 45 : 39 </text>-->
                            </div>
                            <div class="lc-b">
                                <text class="lc-b-1" @click="delQuantity(i)">-</text>
                                <text class="lc-b-n">{{i.quantity}}</text>
                                <text class="lc-b-2" @click="addQuantity(i)">+</text>
                            </div>

                        </div>
                    </div>
                </div>
            </cell>
            <cell class="container-1" v-if="goodsList.length == 0">
                <div class="container-2">
                    <text class="empty-icon iconfont">&#xe755;</text>
                </div>
                <text class="address-title">Your cart is empty.</text>
                <div class="container-2">
                    <text class="empty-btn" @click="jumpHome">Go Shopping</text>
                </div>
            </cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
        </list>
        <div class="bottom-btn" >
            <div class="bb-d">
                <div class="bb-d-r" @click="selAll">
                    <div :class="[selAllStatus?'lc-t-dot-select':'lc-t-dot']">
                        <text class="dot-sel">&radic;</text>
                    </div>
                    <text class="bb-dr-t">Select All</text>
                </div>

                <div class="bb-d-l">
                    <text class="bb-dl-p" v-if="bottomWord=='Checkout'">₹ {{allPrice}}</text>
                    <div class="bb-dl-b" @click="handleGoods">
                        <text class="bb-dl-bf" >{{bottomWord}}</text>
                    </div>

                </div>
            </div>
        </div>

    </div>
</template>

<script>
    import header from './witheHeader';
    import { WxcCountdown, WxcRadio } from 'weex-ui';
    export default {
        components: {
            'topic-header': header,
            WxcCountdown,
            WxcRadio
        },
        data () {
            return {
                goodsSel: true,
                page: 1,
                length: 2,
                pageSize: 4,
                goodsList: false,
                data: '',
                selAllStatus: false,
                isLoading: false,
                allPrice: '0.00',
                bottomWord: 'Checkout',
                rightBtnWord: 'Edit',
                nextPage: []
            }
        },
        created () {
            this.requestProduct(true);
        },
        methods: {
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true
                    this.requestProduct(false)
                }
            },
            requestProduct (isFirst) {
                // this.loadingWord = 'Loading...'
                if (isFirst) {
                    this.page = 1;
                }
                if (this.page > this.length) {
                    this.$nextTick(() => {
                        this.isLoading = false
                    })
                    return
                }
                this.getCartList(isFirst);
            },
            getCartList (isFirst) {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'cart.list',
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    },
                    header: {
                        needAuth: true,
                        isLoginPop: true
                    }
                }).then((res) => {
                    this.data = res
                    if (res.count == 0) {
                        this.length = 2;
                    } else {
                        this.length = Math.ceil(res.count / this.pageSize);
                    }
                    if (isFirst) {
                        this.goodsList = [];
                    }
                    // this.goodsList = [...res.results]
                    this.goodsList.push(...res.results);

                    for (let i = 0; i < this.goodsList.length; i++) {
                        this.goodsList[i].sel = false
                    }
                    this.page++;
                    this.isLoading = false;
                    this.$notice.alert({
                        message: this.goodsList[0]
                    })
                    this.$notice.loading.hide();
                    // this.goodsList = [];
                }).catch((res) => {
                    this.$notice.loading.hide();
                    this.isLoading = false;
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            jumpHome () {
                this.$router.setBackParams({ tab: 'home' })
                this.$router.back({
                    length: 9999,
                    type: 'PUSH'
                })
            },
            calc (a, b) {
                return ((a * b) / 100).toFixed(2)
                // return (('0.00' * 100) / 100).toFixed(2)
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.floor((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            addQuantity (item) {
                // this.$notice.toast({
                //     message: '111'
                // })
                if (item.productType == 'flash') {
                    if (item.quantity >= 3) {
                        return
                    }
                } else {
                    if (item.quantity >= 10) {
                        return
                    }
                }
                const arr = [...this.goodsList];
                for (let i = 0; i < arr.length; i++) {
                    if (arr[i].id === item.id) {
                       arr[i].quantity += 1
                       this.postQuantity(item.id, arr[i].quantity)
                    }
                }
                this.$nextTick(() => {
                    this.goodsList = [...arr];
                })
            },
            delQuantity (item) {
                if (item.quantity == 1) {
                    return
                }
                const arr = [...this.goodsList];
                for (let i = 0; i < arr.length; i++) {
                    if (arr[i].id === item.id) {
                       arr[i].quantity -= 1
                       this.postQuantity(item.id, arr[i].quantity)
                    }
                }
                this.$nextTick(() => {
                    this.goodsList = [...arr];
                })
            },
            postQuantity (id, q) {
                this.$fetch({
                    method: 'POST',
                    name: 'cart.update.quantity',
                    data: {
                        'id': id,
                        'quantity': q
                    },
                    header: {
                        needAuth: true,
                        isLoginPop: true
                    }
                }).then((res) => {
                    if (res.result == 'success') {
                        this.countPrice()
                    }
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            changeBtn (p) {
                p === 1 ? [this.bottomWord = 'Delete', this.allPrice = '0.00'] : this.bottomWord = 'Checkout'
            },
            handleGoods () {
                this.$notice.loading.show();
                const idArr = []
                const arr = [...this.goodsList];
                if ((this.bottomWord === 'Delete')) {
                    for (let j = 0; j < arr.length; j++) {
                        if (arr[j].sel) {
                            idArr.push(arr[j].id)
                            arr.splice(j, 1)
                        }
                    }
                    if (arr.length !== 0) {
                        this.$fetch({
                            method: 'DELETE',
                            name: 'cart.delete',
                            data: {
                                'ids': idArr
                            },
                            header: {
                                needAuth: true,
                                isLoginPop: true
                            }
                        }).then((res) => {
                            this.$notice.toast({
                                message: res.result
                            })
                            this.$nextTick(() => {
                                this.goodsList = [...arr];
                            })
                            this.allPrice = "0.00"
                            this.$notice.loading.hide();
                        }).catch((res) => {
                            this.$notice.loading.hide();
                            this.$notice.toast({
                                message: res
                            })
                        })
                    }
                } else {
                    // this.getCartList(false)
                    this.nextPage = [];
                    for (let i = 0; i < this.goodsList.length; i++) {
                        if (this.goodsList[i].sel) {
                            this.nextPage.push(this.goodsList[i])
                        }
                    }
                    this.$notice.loading.hide();
                    // TODO 需要重新请求一下列表 获取最新的库存 进行判断是否有库存可以使用
                    this.$router.open({
                        name: 'cart.order',
                        type: 'PUSH',
                        params: this.nextPage
                    })
                }
            },
            selGoods (item) {
                if (item.sumStock > 0) {
                    item.sel = !item.sel
                    const arr = [...this.goodsList];
                    let trueCount = 0
                    for (let i = 0; i < arr.length; i++) {
                        if (!arr[i].sel) {
                            this.selAllStatus = false
                        } else {
                            this.allPrice = arr[i].unitPrice
                            trueCount++
                        }
                        if (trueCount == arr.length) {
                            this.selAllStatus = true
                        }
                    }
                    this.$nextTick(() => {
                        this.goodsList = [...arr];
                    })
                    this.countPrice()
                }

                // // this.goodList[index].sel = !this.goodList[index].sel
                // this.$notice.alert({
                //     message: item
                // })
            },
            selAll () {
                this.selAllStatus = !this.selAllStatus
                const arr = [...this.goodsList];
                for (let i = 0; i < arr.length; i++) {
                    if (this.selAllStatus && arr[i].sumStock > 0) {
                        arr[i].sel = true
                    } else {
                        arr[i].sel = false
                    }
                    // arr[i].sel = !arr[i].sel
                }

                this.$nextTick(() => {
                    this.goodsList = [...arr];
                })
                this.countPrice()
            },
            countPrice () {
                const priceArr = []
                for (let j = 0; j < this.goodsList.length; j++) {
                    if (this.goodsList[j].sel) {
                        if (this.goodsList[j].productType == 'flash') {
                            priceArr.push(
                                (((this.calc(this.goodsList[j].unitPrice, this.goodsList[j].flashDiscount)) * 100) * this.goodsList[j].quantity)
                            ) // 计算浮点数 乘100
                        } else if (this.goodsList[j].productType == 'direct') {
                            priceArr.push(((this.goodsList[j].unitPrice * 100) * this.goodsList[j].quantity)) // 计算浮点数 乘100
                        }
                    }
                }
                if (priceArr.length == 0) {
                    this.allPrice = '0.00'
                } else if (priceArr.length >= 1) {
                    this.allPrice = 0
                    for (let i = 0; i < priceArr.length; i++) {
                        this.allPrice += parseInt(priceArr[i])
                    }
                    this.allPrice /= 100
                }
            }
        }
    }
</script>

<style scoped>
    .wrapper{
        width: 750px;
    }
    .iconfont{
        font-family:iconfont;
    }
    .t-h1{
        margin-top: 48px;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .content{
        margin-bottom: 112px;
        width: 750px;
        /*background-color: black;*/
    }
    .line-card{
        /*width: 750px;*/
        width: 686px;
        background-color: #FFFFFF;
        box-shadow: 0 2px 2px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        flex-direction: column;
        justify-content: space-between;
        margin-top: 24px;
        margin-left: 32px;
        margin-right: 32px;
    }
    .mg-b10{
        margin-bottom: 20px;
    }
    .lc-top{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-bottom: 24px;
        margin-top: 32px;
    }
    .lc-t-dot{
        width: 32px;
        height: 32px;
        margin: 16px;
        background-color: #fff;
        border-width:  1px;
        border-style:  solid;
        border-color:  grey;
        border-radius: 50%;
    }
    .lc-t-dot-disable{
        width: 32px;
        height: 32px;
        margin:  16px;
        background-color: rgba(104,104,104,.19);
        border-radius: 50%;
        border-width:  1px;
        border-style:  solid ;
        border-color:  rgba(104,104,104,.19);

    }
    .lc-t-dot-select{
        width: 32px;
        height: 32px;
        margin: 16px;
        /*padding-left: 16px;*/
        /*padding-right: 16px;*/
        background-color: #EF8A31;
        border-radius: 50%;
    }
    .dot-sel{
        color: white;
        padding-left:6px;
    }
    .overflow-img{
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-right: 24px;
    }
    .blue-word{
        text-align: center;
        width: 118px;
        background-color: #00CFE3;
        margin-top: -28px;
        font-size: 20px;
        color: #FFFFFF;
        letter-spacing: 0;
    }
    .lc-t-img{
        width: 176px;
        height: 176px;
    }
    .lc-sold-out{
        width: 176px;
        height: 176px;
        position: absolute;
        top: 0;
        left: 0;
    }
    .lc-tw{
        flex-direction: column;
        justify-content: space-between;
        align-items: start;
        height:182px ;
        /*background-color: red;*/
    }
    .lc-tw1{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        width: 288px;
        color: rgba(0,0,0,0.87);
        line-height: 28px;
        /*margin-bottom: 40px;*/
    }
    .lc-tw2{
        font-family: ProximaNova-Regular;
        font-size: 20px;
        width: 400px;
        color: rgba(0,0,0,0.87);
        line-height: 28px;
        /*margin-bottom: 8px;*/
    }
    .lc-tw-b{
        font-size: 20px;
        color: rgba(0,0,0,0.87);
        margin-bottom: 18px;
    }
    .lc-tw-p{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .lc-tw-p1{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
        margin-right: 20px;
    }
    .lc-tw-p2{
        font-size: 20px;
        color: #000000;
        text-decoration: line-through;
        margin-right: 20px;
    }
    .lc-tw-p3{
        font-family: ProximaNova-Regular;
        font-size: 20px;
        color: #EF8A31;
    }
    .lc-bottom{
        flex-direction: row;
        justify-content: space-between;
        margin-right: 32px;
        margin-bottom: 32px;
    }

    .lc-b{
        flex-direction: row;
    }
    .lc-b-1{
        font-size: 40px;
        padding: 14px 32px;
        background-color: rgba(239,138,49,.12);
        border-radius: 8px;
        margin-right: 6px;
    }
    .lc-b-n{
        font-size: 24px;
        padding: 23px 10px;
        text-align: center;
        width: 100px;
        background-color: rgba(0,0,0,.08);
        border-radius: 8px;
        margin-right: 6px;
    }
    .lc-b-2{
        font-size: 40px;
        padding: 14px 28px;
        background-color: rgba(239,138,49,.12);
        border-radius: 8px;

    }
    .lc-tt{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        height: 80px;
        margin-left: 64px;
    }
    .lc-tt-w{
        font-size: 20px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
    }
    .bottom-btn{
        background-color: #fff;
        /*box-shadow:  0 -2px 3px 0 rgba(0,0,0,0.24);*/
        border-top-width: 1px;
        border-top-color: rgba(0,0,0,.12);
        border-top-style: solid ;
        width: 750px;
        height: 112px;
        position: fixed;
        bottom: 0;
        right: 0;
        overflow: hidden;
    }
    .bb-d{
        height: 112px;
        flex-direction: row;
        justify-content: space-between;
    }
    .bb-d-r{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        margin-left: 34px;
    }
    .bb-dr-t{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
        text-align: left;
        /*margin-left: 18px;*/
    }
    .bb-dr-t{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
        text-align: left;
        /*margin-left: 18px;*/
    }
    .bb-d-l{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .bb-dl-p{
        font-size: 28px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        font-weight: 700;
    }
    .bb-dl-b{
        height: 80px;
        width: 160px;
        background-color: #EF8A31;
        border-radius: 4px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin-right: 32px;
        margin-left: 20px;
    }
    .bb-dl-bf{
        color: white;
        font-weight: 700;
        font-size: 28px;
    }
    .container-1{
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        right: 0;
        background-color: #f4f4f4;
    }
    .container-2{
        margin-top: 86px;
        width: 750px;
        align-items: center;
    }

    .empty-icon{
        margin-top: 100px;
        opacity: 0.45;
        font-size: 200px;
    }
    .empty-btn{
        font-size: 24px;
        color: #FFFFFF;
        letter-spacing: 0;
        background-color: #EF8A31;
        padding:18px 40px;
        font-weight: 700;
        text-align: center;
        border-radius: 8px;
    }
    .address-title{
        margin-top: 32px;
        font-size: 28px;
        line-height: 34px;
        text-align: center;
    }
    .loading{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        padding: 16px 0;
    }
    .loading-icon{
        width: 64px;
        height: 64px;
    }
</style>
