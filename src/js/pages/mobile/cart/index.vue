<template>
    <div class="wrapper">
        <topic-header class="t-h1" title="Cart" leftBtn="y" :rightBtn="rightBtnWord" v-on:change="changeBtn($event) " ></topic-header>
        <div class="blackheader"></div>
        <div class="top-buy-overflow" v-if="false">
            <div :class="[parseInt(allPrice)>0?'mg-tb16':'']" @click="jumpHome">

                <div class="top-buy1" v-if="parseInt(allPrice)>300" >
                    <text class="t-b-tb" v-if="parseInt(allPrice)>300&&parseInt(allPrice)<600">₹100 </text>
                    <text class="t-b-tb" v-if="parseInt(allPrice)>=600&&parseInt(allPrice)<900">₹150 </text>
                    <text class="t-b-tb" v-if="parseInt(allPrice)>=900">₹200</text>

                    <text class="t-b-t" > voucher unlocked!</text>
                </div>
                <div class="top-buy2" v-if="parseInt(allPrice)<900&&parseInt(allPrice)!=0" >
                <text class="t-b-t">Buy </text>

                <text class="t-b-tb" v-if="parseInt(allPrice)<300">₹{{300-parseInt(allPrice)}} </text>
                <text class="t-b-tb" v-if="parseInt(allPrice)>=300&&parseInt(allPrice)<600" >₹{{600-parseInt(allPrice)}} </text>
                <text class="t-b-tb" v-if="parseInt(allPrice)>=600&&parseInt(allPrice)<900" >₹{{900-parseInt(allPrice)}} </text>


                <text class="t-b-t">more to use your </text>

                <text class="t-b-tb" v-if="parseInt(allPrice)<300" >₹100 </text>
                <text class="t-b-tb" v-if="parseInt(allPrice)>=300&&parseInt(allPrice)<600" >₹150 </text>
                <text class="t-b-tb" v-if="parseInt(allPrice)>=600&&parseInt(allPrice)<900" >₹200 </text>

                <text class="t-b-t">voucher! </text>
                <text class="t-b-tc">Add Item >></text>
            </div>
            </div>
        </div>
        <notice :items="noticeList" v-if="noticeList.length > 0"></notice>
        <list class="content" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <refresher class="gd-bg-gray" ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>
            <cell  v-for="(i,index) in goodsList"
                   :key="i.id" style="width: 750px">
                <div :class="[index == goodsList.length-1?'mg-b10':'']">
                    <div class="line-card" @click="jumpGoodsDetail(i)">
                        <div class="lc-top" >
                            <div  @click="selGoods(i)" >
                                <div style="margin:16px" >
                                    <!--<text class="dot-sel">&radic;</text>-->
                                    <div v-if="parseInt(i.sumStock)!==0">   <!--可选-->
                                        <text class="iconfont item-checked" v-if="i.sel">&#xe6fb;</text>
                                        <text class="iconfont item-no-checked" v-if="!i.sel">&#xe73f;</text>
                                    </div>
                                    <text class="iconfont item-checked-disable" v-if="parseInt(i.sumStock)==0">&#xe73f;</text> <!--不可选-->
                                </div>
                            </div>
                            <div class="overflow-img">
                                <image class="lc-t-img" :src="i.mainImage" ></image>
                                <image class="lc-sold-out" v-if="parseInt(i.sumStock)==0" src="bmlocal://assets/sold-out.png"></image>
                                <!--<div>-->
                                <text class="blue-word" v-if="i.productType=='flash'">Flash Sale</text>
                                <!--</div>-->
                            </div>
                            <div class="lc-tw"  >
                                <!--<text class="lc-tw1">Toyota’s Latest is an Entire Mobility Service Platform</text>-->
                                <text class="lc-tw1">{{i.title}}</text>
                                <div class="lc-tw2" >
                                    <text class="lc-tw-b">{{i.attributes}}</text>
                                    <div class="lc-tw-p">
                                        <text class="lc-tw-p1" v-if="i.productType=='flash'" >₹{{parseInt(calc(i.unitPrice,i.flashDiscount))}}</text>
                                        <text class="lc-tw-p1" v-if="i.productType=='direct'" >₹{{parseInt(i.unitPrice)}}</text>

                                        <text class="lc-tw-p2">₹{{parseInt(i.saleUnitPrice)}}</text>

                                        <text class="lc-tw-p3" v-if="i.productType=='flash'" >{{countOff(calc(i.unitPrice,i.flashDiscount), i.saleUnitPrice)}}</text>
                                        <text class="lc-tw-p3" v-if="i.productType=='direct'" >{{countOff(i.unitPrice , i.saleUnitPrice)}}</text>
                                    </div>
                                </div>
                                <div class="lc-b">
                                    <text class="lc-b-1" @click="delQuantity(i)">-</text>
                                    <text class="lc-b-n">{{i.quantity}}</text>
                                    <text class="lc-b-2" @click="addQuantity(i)">+</text>
                                </div>
                            </div>
                        </div>
                        <div class="lc-bottom" v-if="i.productType=='flash'">
                            <div class="lc-tt" >
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
                            <div>
                                <!--<text>Price expires in</text>-->
                                <!--<text> 23 : 45 : 39 </text>-->
                            </div>
                            <div class="lc-b">
                                <!--<text class="lc-b-1" @click="delQuantity(i)">-</text>-->
                                <!--<text class="lc-b-n">{{i.quantity}}</text>-->
                                <!--<text class="lc-b-2" @click="addQuantity(i)">+</text>-->
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
                    <div style="margin-right: 18px">
                        <!--<text class="dot-sel">&radic;</text>-->
                        <text class="iconfont item-checked" v-if="selAllStatus">&#xe6fb;</text>
                        <text class="iconfont item-no-checked" v-if="!selAllStatus">&#xe73f;</text>
                    </div>
                    <text class="bb-dr-t">Select All</text>
                </div>

                <div class="bb-d-l" v-if="bottomWord=='Checkout'">
                    <div class="bd-div" v-if="parseInt(allPrice)>0" >
                        <text class="bb-dl-p" >₹{{parseInt(allPrice)}}</text>
                        <div class="bb-dl-b" @click="handleGoods" >
                            <text class="bb-dl-bf" >Checkout</text>
                        </div>
                    </div>

                    <div class="bd-div" v-if="parseInt(allPrice)<=0">
                        <text class="bb-dl-p" >&nbsp;&nbsp;&nbsp;</text>
                        <div class="bb-dl-b-no" >
                            <text class="bb-dl-bf-no" >Checkout</text>
                        </div>
                    </div>

                </div>

                <div class="bb-d-l" v-if="bottomWord=='Delete'">
                    <div class="bd-div" v-if="isSelected">
                        <text class="bb-dl-p" >&nbsp;&nbsp;&nbsp;</text>
                        <div class="bb-dl-b"  @click="handleGoodsDel">
                            <text class="bb-dl-bf" >Delete</text>
                        </div>
                    </div>

                    <div class="bd-div" v-if="!isSelected">
                        <text class="bb-dl-p" >&nbsp;&nbsp;&nbsp;</text>
                        <div class="bb-dl-b-no" v-if="!isSelected">
                            <text class="bb-dl-bf-no" >Delete</text>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>
</template>

<script>
    import header from './witheHeader';
    import refresher from '../common/refresh';
    import notice from '../common/notification';
    import { WxcCountdown, WxcRadio } from 'weex-ui';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'topic-header': header,
            'refresher': refresher,
            'notice': notice,
            WxcCountdown,
            WxcRadio
        },
        data () {
            return {
                goodsSel: true,
                page: 1,
                length: 2,
                pageSize: 100,
                goodsList: false,
                noticeList: [1,2,3,4,5],
                data: '',
                selAllStatus: true,
                isLoading: false,
                allPrice: '0.00',
                bottomWord: 'Checkout',
                rightBtnWord: 'Edit',
                nextPage: [],
                myCard: [],
                tmpArr: [],
                isSelected: true
            }
        },
        created () {
            this.requestProduct(true);
            this.getMyCard()
            this.$event.on('logout', parmas => {
                this.requestProduct(false)
            })
            this.$event.on('login', parmas => {
                this.requestProduct(false)
            })
            googleAnalytics.trackingScreen('Cart');
        },
        methods: {
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.requestProduct(true)
                this.isLoading = false;

            },
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true
                    this.requestProduct(false)
                }
            },
            requestProduct (isFirst) {
                if (isFirst) {
                    this.page = 1;
                }
                if (this.page > this.length) {
                    this.$refs.refresh.refreshEnd();
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
                    if (this.goodsList.length == 0) {
                        this.selAllStatus = false
                    } else {
                        for (let i = 0; i < this.goodsList.length; i++) {
                            this.goodsList[i].sel = true
                        }

                        this.countPrice();
                    }
                    this.page++;
                    this.isLoading = false;
                    // this.$notice.alert({
                    //     message: this.page
                    // })
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
            jumpGoodsDetail (i) {
                // this.$notice.alert({
                //     message: i.productId
                // })
                // return
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: i.productId
                    }
                })
            },
            getMyCard () {
                this.$fetch({
                    method: 'GET',
                    name: 'point.voucher.owner.list', // 通过get 获取我自己的积分卡
                    header: {
                        needAuth: true
                    },
                    data: {
                        page: 1,
                        page_size: 12,
                        status: 'available'
                    }
                }).then(data => {
                    this.myCard.push(...data.results);
                    const tmp = []
                    for (let i = 0; i < this.myCard.length; i++) {
                        tmp.push(parseInt(this.myCard[i].lowestAmount))
                    }
                    this.tmpArr = this.unique(tmp)
                    // this.$notice.alert({
                    //     message: this.tmpArr
                    // })
                    this.$notice.loading.hide();
                }, error => {
                    this.$notice.loading.hide();
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
                    return Math.ceil((o - s) / o * 100) + '% OFF'
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
                        this.$event.emit('cartNum')
                    }
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            changeBtn (p) {
                if (p === 1) {
                    this.bottomWord = 'Delete'
                } else {
                    this.bottomWord = 'Checkout';
                    this.getSelectStatus();
                    this.countPrice();
                }
            },
            handleGoods () {
                this.$notice.loading.show();
                    this.nextPage = [];
                    for (let i = 0; i < this.goodsList.length; i++) {
                        if (this.goodsList[i].sel) {
                            this.nextPage.push(this.goodsList[i])
                        }
                    }
                    googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'Checkout', 0);
                    // googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', 'Checkout', '', 'Rs', 0);
                    this.$notice.loading.hide();
                    this.$router.open({
                        name: 'cart.order',
                        type: 'PUSH',
                        params: this.nextPage
                    })
            },
            handleGoodsDel () {
                this.$notice.loading.show();
                const idArr = [];
                const arr = [...this.goodsList];
                const arr1 = [];
                    for (let j = 0; j < arr.length; j++) {
                        if (arr[j].sel) {
                            idArr.push(arr[j].id);
                        } else {
                            arr1.push(arr[j]);
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
                            this.$nextTick(() => {
                                this.goodsList = [...arr1];
                                this.getSelectStatus();
                                this.countPrice();
                                this.$event.emit('cartNum')
                            })
                            this.$notice.loading.hide();
                        }).catch((res) => {
                            this.$notice.loading.hide();
                            // this.$notice.toast({
                            //     message: res
                            // })
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
                        this.getSelectStatus();
                        this.countPrice();
                    });
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
                    this.getSelectStatus();
                    this.countPrice();
                });
            },
            getSelectStatus () {
                for (const item of this.goodsList) {
                    if (item.sel) {
                        this.isSelected = true;
                        return;
                    }
                }
                this.isSelected = false;
            },
            unique (arr) {
                arr.sort();
                const res = [];
                for (let i = 0; i < arr.length; i++) {
                    if (res.indexOf(arr[i]) == -1) {
                        res.push(arr[i]);
                    }
                }
                return res;
            },
            countPrice () {
                const priceArr = []
                for (let j = 0; j < this.goodsList.length; j++) {
                    if (this.goodsList[j].sel) {
                        if (this.goodsList[j].productType == 'flash') {
                            priceArr.push(
                                (parseInt(this.calc(this.goodsList[j].unitPrice, this.goodsList[j].flashDiscount)) * this.goodsList[j].quantity)
                            ) // 计算浮点数 乘100
                        } else if (this.goodsList[j].productType == 'direct') {
                            priceArr.push((parseInt(this.goodsList[j].unitPrice) * this.goodsList[j].quantity)) // 计算浮点数 乘100
                        }
                    }
                }
                if (priceArr.length == 0) {
                    this.allPrice = 0
                } else if (priceArr.length >= 1) {
                    this.allPrice = 0
                    for (let i = 0; i < priceArr.length; i++) {
                        this.allPrice += parseInt(priceArr[i])
                    }
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
    .top-buy-overflow{
        background-color:#FCEACF;
        /*padding: 8px 0 ;*/
    }
    .top-buy1{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-left: 32px;
        margin-top: 8px;
        margin-bottom: 8px;
    }
    .top-buy2{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-left: 32px;
        margin-bottom: 8px;
        margin-top: 8px;
    }
    .t-b-t{
        font-size: 24px;
        color: #000000;
    }
    .t-b-tb{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
    }
    .t-b-tc{
        font-size: 24px;
        color: #EF8A31;
    }
    .line-card{
        /*width: 750px;*/
        width: 686px;
        background-color: #FFFFFF;
        box-shadow: 0 2px 2px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
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
    .item-checked{
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
    }

    .item-no-checked{
        font-size: 32px;
        line-height: 32px;
        color: rgba(0,0,0,0.32);
    }
    .item-checked-disable{
        font-size: 32px;
        line-height: 32px;
        color:rgba(0,0,0,.19);
        background-color: rgba(0,0,0,.19);
        border-radius: 50%;
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
        width: 240px;
        height: 240px;
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
        justify-content: start;
        align-items: start;
        height:240px;
        /*background-color: red;*/
    }
    .lc-tw1{
        lines: 2;
        text-overflow: ellipsis;
        font-size: 24px;
        width: 298px;
        color: rgba(0,0,0,0.87);
        line-height: 32px;
        /*background-color: black;*/
    }
    .lc-tw2{
        font-family: ProximaNova-Regular;
        font-size: 20px;
        width: 400px;
        color: rgba(0,0,0,0.87);
        margin-top: 12px;
    }
    .lc-tw-b{
        font-size: 20px;
        color: rgba(0,0,0,0.87);
    }
    .lc-tw-p{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 24px;
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
        align-items: center;
        margin-top: 28px;
        margin-left: 100px;
        /*background-color: black;*/
    }
    .lc-b-1{
        font-size: 48px;
        /*padding: 14px 32px;*/
        height: 48px;
        width: 48px;
        background-color: rgba(239,138,49,.12);
        border-radius: 8px;
        margin-right: 6px;
        line-height: 42;
        text-align: center;
    }
    .lc-b-n{
        font-size: 24px;
        /*padding: 23px 10px;*/
        height: 48px;
        /*width: 48px;*/
        text-align: center;
        width: 100px;
        background-color: rgba(0,0,0,.08);
        border-radius: 8px;
        margin-right: 6px;
        line-height: 45;
    }
    .lc-b-2{
        font-size: 40px;
        height: 48px;
        width: 48px;
        /*padding: 14px 28px;*/
        background-color: rgba(239,138,49,.12);
        border-radius: 8px;
        text-align: center;
    }
    .lc-tt{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        height: 50px;
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
        justify-content: start;
        align-items: center;
    }
    .bd-div{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .bb-dl-p{
        font-size: 28px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        font-weight: 700;
    }
    .bb-dl-b-no{
        height: 80px;
        width: 160px;
        background-color: rgba(128,128,128,.74);
        border-radius: 50%;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin-right: 32px;
        margin-left: 20px;
    }
    .bb-dl-bf-no{
        color: white;
        font-weight: 700;
        font-size: 28px;
    }
    .bb-dl-b{
        height: 80px;
        width: 160px;
        background-color: #EF8A31;
        border-radius: 50%;
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
    .mg-tb16{
        margin-bottom: 16px;
        margin-top: 16px;
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
