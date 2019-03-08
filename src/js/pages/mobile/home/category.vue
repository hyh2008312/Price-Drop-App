<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="homeBack" @click="homeBack">&#xe6f6;</text>
            <text class="title">{{name == 'Electronic' ? 'Electronics': name}}</text>
            <text class="search iconfont" @click="jumpSearch">&#xe621;</text>
            <div  class="over-flow-cart" @click="openCart">
                <text class=" cart iconfont">&#xe767;</text>
                <text class="box-dot" v-if="cartNum>0">{{cartNum > 99? '99+': cartNum}}</text>
            </div>

        </div>
        <list class="main-list" ref="list" offset-accuracy="10" loadmoreoffset="400"
                   @loadmore="onLoadingMore">
            <refresher ref="refresh" @loadingDown="loadingDown"></refresher>
            <cell>
                <notice :items="noticeList" v-if="noticeList.length > 0"></notice>
            </cell>
            <cell v-if="subCategory.length>2">
                <scroller class="scroller" scroll-direction="horizontal">
                    <text class="tab-txt" :class="[item.id == selId ? 'tab-txt-active' : '']"
                          v-for="item in subCategory" @click="changeCategory(item.id)">{{item.name}}</text>
                </scroller>
            </cell>

            <header>
                <div class="category-header">
                    <div class="category-left" @click="openDialog">
                        <text class="category-left-text">{{selectedSort.text}}</text>
                        <text class="iconfont category-left-icon" v-if="isCancelBottomShow == false">&#xe6fd;</text>
                        <text class="iconfont category-left-icon-1" v-if="isCancelBottomShow == true">&#xe6fc;</text>
                    </div>
                    <text class="iconfont category-arrange" v-if="arrangement == false" @click="changeArrangement">&#xe742;</text>
                    <text class="iconfont category-arrange" v-if="arrangement == true" @click="changeArrangement">&#xe743;</text>
                </div>
            </header>
            <cell v-if="firstLogin&&newGift">
                <div class="new-co" @click="openNewCo">
                    <image class="new-co-img" src="bmlocal://assets/home/new-gift.png" ></image>
                    <text class="new-co-w">New User Gift</text>
                </div>
            </cell>
            <cell v-for="(i ,index) in goods" :key="index" >
                <block3 :goods="i" v-if="arrangement == false"></block3>
                <block7 :goods="i" v-if="arrangement == true"></block7>
            </cell>
            <cell class="cell-fixed" v-if="goods.length > 0"></cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">loading...</text>
            </loading>
        </list>
        <toggle :have-overlay="isTrue"
                   popup-color="rgba(255, 255, 255, 0)"
                   :show="isCancelBottomShow"
                   @wxcPopupOverlayClicked="popupCancelAutoClick"
                   ref="wxcCancelPopup"
                   pos="top"
                   height="288">
            <div class="popup-cancel">
                <div v-for="item in sort">
                    <text class="popup-text" :class="[item.value == selectedSort.value?'popup-text-active': '']"
                    @click="chooseSort(item)">{{item.text}}</text>
                </div>
            </div>
        </toggle>

        <WxcMask
            height="587"
            width="397"
            border-radius="16"
            duration="200"
            mask-bg-color="rgba(255, 255, 255, 0)"
            :has-animation="true"
            :has-overlay="true"
            :show-close="false"
            :show="newShow"
            @wxcMaskSetHidden="wxcMaskSetShareHidden">
            <div class="maskcontent">
                <div class="m-top">
                    <image src="bmlocal://assets/home/mask-head.png" style="width:397px;height:162px; "></image>
                </div>
                <text class="iconfont m-ic">&#xe632;</text>
                <text class="m-c">₹{{newUserBonus}}</text>
                <div class="mid-content">
                    <text class="m-c-w">You just got ₹{{newUserBonus}} cash bonus for shopping now.</text>
                    <div class="m-c-time">
                        <text class="m-c-word">Expire in </text>
                        <div  class="overflow-center-time">
                            <div class="center-time" v-if="asecond!=''">
                                <text class="center-time-hh">{{ahour}}</text>
                                <text style="font-size: 20px;">:</text>
                                <text class="center-time-hh">{{amin}}</text>
                                <text style="font-size: 20px;">:</text>
                                <text class="center-time-hh">{{asecond}}</text>
                            </div> <!-- 正常显示的 -->

                            <div class="center-time" v-if="asecond==''">
                                <text class="center-time-hh-empty"></text>

                                <text style="font-size: 24px; padding-top: 10px;">:</text>
                                <text class="center-time-hh-empty"></text>
                                <text style="font-size: 24px; padding-top: 10px;">:</text>
                                <text class="center-time-hh-empty"></text>
                            </div>  <!-- 做空白处理的 -->
                        </div>
                    </div>
                </div>
                <image src="bmlocal://assets/home/voucher-part.png" style="width: 398px;height: 28px;"></image>
                <div class="m-bottom">
                    <div class="overflow-mg">
                        <div style="border-radius: 32px;">
                            <image src="bmlocal://assets/home/maskBtn.png" style="width: 238px;height: 56px;"></image>
                        </div>
                    </div>

                    <text class="mb-word">Go Shopping Now</text>
                </div>
            </div>
        </WxcMask>
    </div>
</template>
<script>
    import { Utils,WxcMask } from 'weex-ui';
    import refresher from '../common/refresh';
    import preload from '../common/preloadImg';
    import block3 from './block3';
    import block7 from './block7';
    import toggle from './toggle';
    import notice from '../common/notification';

    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    import { baseUrl } from '../../../config/apis';

    export default {
        components: {
            'refresher': refresher,
            WxcMask,
            preload,
            notice,
            block7,
            block3,
            toggle
        },
        created () {
            this.getCartNum();
            this.getNotification();
            this.$event.on('first', parmas => {
                this.$storage.get('user').then((res) => {
                    if(res) {
                        this.user = res;
                        if(this.user.firstLogin.toString()=='false'){
                            this.newGift = false
                        }
                        this.$storage.get('firstLogin').then((data) => {
                            if(data) {
                                this.firstLogin = data
                                this.openNewCo()
                                this.user.firstLogin = false
                                this.$storage.set('user', this.user)
                            }
                        });
                    }
                })
            })
            this.user = this.$storage.getSync('user');
            if(this.user!=''){
                if(!this.user.firstLogin){
                    this.firstLogin = false;
                }
            }
        },
        eros: {
            appeared (params, options) {
                const arrange = this.$storage.getSync('categoryArrangement');
                if (arrange) {
                    this.arrangement = arrange;
                }
                this.getActivityParam(params);
            }
        },
        data () {
            return {
                name: '',
                id: -1,
                selId: false,
                imageUrl: '',
                testImage: '',
                goods: [],
                subCategory: [],
                noticeList: [],
                length: 2,
                page: 1,
                pageSize: 12,
                cartNum: '',
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid(),
                arrangement: false,
                newShow: false,
                firstLogin: true,
                newGift: true,
                aday: '',
                ahour: '',
                amin: '',
                asecond: '',
                newUserBonus:'',
                user: '',
                goodsSave: [],
                sort: [{
                    value: false,
                    text: 'Pick For You'
                }, {
                    value: 'public_date',
                    text: 'New Arrivals'
                }, {
                    value: 'price_low',
                    text: 'Price Low to High'
                }, {
                    value: 'price_high',
                    text: 'Price High to Low'
                }],
                selectedSort: {
                    value: false,
                    text: 'Pick For You'
                },
                isTrue: true,
                isCancelBottomShow: false,
                isFirstLoad: false
            }
        },
        methods: {
            getNotification () {
                this.$fetch({
                    method: 'GET',
                    name: 'notice.app.list',
                    data: {
                        placement: 'Category'
                    }
                }).then(resData => {
                    this.noticeList = [...resData];
                }, error => {});
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.getActivityProduct(true, false, this.selId);
            },
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.getActivityProduct(false, false, this.selId);
                }
            },
            onloading () {
                if (this.isPlatformAndroid) {
                    this.isLoading = true;
                    this.getActivityProduct(false, false, this.selId);
                }
            },
            getActivityParam (resData) {
                this.id = resData.id;
                googleAnalytics.trackingScreen(`Product/${this.name}`);
                this.$notice.loading.show();
                this.getActivityProduct(true, false, this.selId);
            },
            getActivityProduct (isfirst, arrange, id) {
                if (isfirst) {
                    if (!this.isFirstLoad) {
                        this.isFirstLoad = true;
                        this.getActivityCategory();
                        this.getSubCategory();
                    } else {
                        return;
                    }
                    this.page = 1;
                    this.length = 2;
                }
                if (this.page > this.length) {
                    this.$refs.refresh.refreshEnd();
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                this.$fetch({
                    method: 'GET',
                    name: 'product.app.category.product',
                    data: {
                        cat: id || this.id,
                        page: this.page,
                        page_size: this.pageSize,
                        sort: this.selectedSort.value
                    }
                }).then(data => {
                    this.$notice.loading.hide();
                    this.length = Math.ceil(data.count / this.pageSize);
                    if (isfirst) {
                        this.isFirstLoad = false;
                        this.goods = [];
                    }
                    this.page++;
                    if (arrange) {
                        this.arrangement = !this.arrangement;
                        this.$storage.set('categoryArrangement', this.arrangement);
                    }
                    if (!this.arrangement) {
                        let arr = [];
                        for (let i = 0; i < data.results.length; i++) {
                            const item = data.results[i];
                            arr.push(item);
                            if ((i > 0 && i % 2 == 1) || i == data.results.length - 1) {
                                this.goods.push(arr);
                                arr = [];
                            }
                        }
                    } else {
                        for (let i = 0; i < data.results.length; i++) {
                            const item = data.results[i];
                            this.goods.push(item);
                        }
                    }
                    if (!isfirst) {
                        this.isLoading = false;
                    }
                    this.refreshApiFinished();
                }, error => {
                    this.$notice.toast({
                        message: JSON.stringify(error)
                    });
                })
            },
            getActivityCategory () {
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/product/category/${this.id}/`
                }).then(data => {
                    this.name = data.name;
                }, error => {});
            },
            getSubCategory () {
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/product/app/category/${this.id}/`
                }).then(data => {
                    this.isFirstLoad = false;
                    this.subCategory = [...data];
                    this.subCategory.unshift({
                        id: false,
                        name: 'All'
                    });
                }, error => {});
            },
            jumpWeb (id) {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: id
                    }
                })
            },
            jumpSearch () {
                this.$router.open({
                    name: 'search',
                    type: 'PUSH'
                });
            },
            openCart () {
                const user = this.$storage.getSync('user');
                if (user) {
                    this.$router.open({
                        name: 'cart',
                        type: 'PUSH'
                    });
                } else {
                    this.$router.open({
                        name: 'login',
                        type: 'PUSH'
                    });
                }
            },
            getCartNum () {
                this.$fetch({
                    method: 'GET',
                    name: 'cart.count',
                    header: {
                        needAuth: true,
                        isLoginPop: true
                    }

                }).then(data => {
                    this.cartNum = data.count
                    // this.$notice.toast({
                    //     message: data
                    // })
                }, error => {})
            },
            redirectLogin () {
                this.$event.on('login', params => {
                    this.user = this.$storage.getSync('user')
                });
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            },
            openNewCo () {
                if (this.user==''){
                    this.redirectLogin()
                }else {
                    this.newGift = false
                    this.$storage.get('firstBonus').then((data) => {
                        if(data) {
                            this.newUserBonus = data;
                            this.$storage.delete('firstBonus');
                        }
                    });

                    let TIME = new Date().getTime() + 86400000*3
                    this.countDate(TIME)
                    this.newShow = true;
                }
            },
            homeBack () {
                this.$router.back();
            },
            refreshApiFinished () {
                this.$refs.refresh.refreshEnd();
            },
            countOff (s, o) {
                if (o > 0) {
                    return Math.ceil((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            changeArrangement () {
                this.$notice.loading.show();
                this.getActivityProduct(true, true, this.selId);
            },
            openDialog () {
                if (this.isCancelBottomShow == false) {
                    this.isCancelBottomShow = true;
                } else {
                    this.$refs.wxcCancelPopup.hide();
                }
            },
            popupCancelAutoClick () {
                this.isCancelBottomShow = false;
            },
            chooseSort (item) {
                this.selectedSort = {};
                this.selectedSort.text = item.text;
                this.selectedSort.value = item.value;
                this.$notice.loading.show();
                this.getActivityProduct(true, false, this.selId);
                this.$refs.wxcCancelPopup.hide();
            },
            changeCategory (id) {
                if (this.selId != id) {
                    this.selId = id;
                } else {
                    this.selId = false;
                }
                this.$notice.loading.show();
                this.getActivityProduct(true, false, this.selId);
            },
            countDate (time) {
                const self = this
                // if (this.purchaseMethod == 'flash') {
                setInterval(() => {
                    this.NOW_DATE = new Date().getTime();

                    const total = (new Date(time).getTime() - this.NOW_DATE) / 1000

                    const day = Math.floor(total / (24 * 60 * 60))// 整天

                    self.aday = day
                    const afterDay = total - day * 24 * 60 * 60;
                    self.ahour = Math.floor(afterDay / (60 * 60)); // 小时
                    const afterHour = total - day * 24 * 60 * 60 - self.ahour * 60 * 60;
                    self.amin = Math.floor(afterHour / 60); // 分钟
                    if (self.amin < 10) {
                        self.amin = '0' + self.amin
                    }

                    const afterMin = total - day * 24 * 60 * 60 - self.ahour * 60 * 60 - self.amin * 60;
                    self.asecond = Math.floor(afterMin)// 秒
                    if (self.asecond < 10) {
                        self.asecond = '0' + self.asecond
                    }
                    // 加上减掉的天数
                    self.ahour += (self.aday * 24)

                    if (self.ahour < 10) {
                        self.ahour = '0' + self.ahour
                    }
                    // this.$notice.toast({
                    //     message: self.aday + '天' + self.ahour + ':' + self.amin + ':' + self.asecond
                    // })
                }, 1000);
                // }
            }
        }
    }

</script>
<style scoped>
    .iconfont{
        font-family: iconfont;
    }

    .main-list {
        width: 750px;
        margin-top: -1px;
        background-color: #fff;
    }

    .scroller{
        background: #FFFFFF;
        border-top-style: solid;
        border-top-width: 1px;
        border-top-color: #E3E3E3;
        padding: 24px 16px 16px 16px;
        width: 750px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .tab-txt-active{
        background-color: #EF8A31;
        color: #fff;
    }

    .tab-txt{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 56px;
        font-weight: 300;
        padding: 0 24px;
        border-radius: 28px;
        background-color: rgba(0,0,0, 0.12);
        color: #000;
        margin: 0 16px;
    }

    .category-header {
        width: 750px;
        height: 96px;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        background-color: #ffffff;
        border-width: 2px;
        border-style: solid;
        border-color: rgba(0,0,0,.08);
    }

    .category-left{
        margin-left: 32px;
        flex-direction: row;
        align-items: center;
    }

    .category-left-text{
        font-family: ProximaNova;
        font-weight: bold;
        font-size: 24px;
        line-height: 80px;
    }

    .category-left-icon{
        margin-left: 16px;
        font-size: 20px;
        color: rgba(0,0,0,0.54);
    }

    .category-left-icon-1{
        margin-left: 16px;
        margin-bottom: 12px;
        font-size: 20px;
        color: rgba(0,0,0,0.54);
    }

    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .navigation {
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

    .margin-left16 {
        flex-direction: column;
        align-items: flex-end;
    }

    .margin-right16 {
        flex-direction: column;
        align-items: flex-start;
    }

    .i-gd {
        width: 359px;
        height: 400px;
        margin-top: 24px;
    }

    .gd-bg {
        border-radius: 8px;
        width: 327px;
        height: 327px;
    }

    .gd-img {
        width: 323px;
        height: 323px;
        border-top-width: 1px;
        border-top-style: solid;
        border-top-color: rgba(0,0,0,.08);
        border-left-width: 1px;
        border-left-style: solid;
        border-left-color: rgba(0,0,0,.08);
        border-right-width: 1px;
        border-right-style: solid;
        border-right-color: rgba(0,0,0,.08);
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,.08);
    }
    .gd-img {
        width: 323px;
        height: 323px;
        border-radius: 8px;
        overflow: hidden;
    }
    .gd-img-image {
        width: 323px;
        height: 323px;
        border-radius: 8px;
        overflow: hidden;
        position: relative;
    }
    .gd-tlt {
        font-family: ProximaNova;
        font-size: 28px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        line-height: 34px;
    }

    .gd-tlt-bg{
        margin-top: 6px;
        width: 327px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }

    .gd-info{
        font-family: ProximaNova;
        margin-left: 8px;
        font-size: 20px;
        line-height: 24px;
        color: #EF8A31;
    }

    .gd-price {
        font-family: ProximaNova;
        font-size: 20px;
        font-weight: 500;
        width: 327px;
        line-height: 24px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
        text-align: center;
        text-decoration: line-through;
    }

    .indicator {
        width: 750px;
        text-align: center;
        color: #888888;
        font-size: 28px;
        padding-top: 16px;
        padding-bottom: 16px;
    }

    .category-arrange{
        padding: 32px;
        font-size: 32px;
        color: #EF8A31;
    }

    .cell-fixed {
        width: 750px;
        height: 26px;
        background-color: #fff;
    }

    .popup-cancel {
        margin-left: 32px;
        height: 288px;
        width: 260px;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
        border-style: solid;
        border-width: 1px;
        border-color: rgba(0,0,0, 0.12);
        /*border-left-style: solid;*/
        /*border-left-width: 1px;*/
        /*border-left-color: rgba(0,0,0, 0.12);*/
        /*border-bottom-style: solid;*/
        /*border-bottom-width: 1px;*/
        /*border-bottom-color: rgba(0,0,0, 0.12);*/
        background-color: #fff;
        overflow: hidden;
        padding: 16px 32px;
    }

    .popup-text{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 64px;
        width: 686px;
    }

    .popup-text-active{
        font-weight: bold;
        color: #EF8A31;
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

    .shipping-bg{
        background-color: #FFE3B6;
        width: 750px;
        height: 64px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .shipping-tips{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        margin-left: 14px;
        font-weight: 700;
    }

    .shipping-icon{
        font-family: ProximaNova;
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
    }
    .search{
        font-size: 45px;
        font-weight: 700;
        position: absolute;
        right:114px ;
        top: 34px;
    }
    .over-flow-cart{
        width: 80px;
        height: 80px;
    }
    .cart{
        font-size: 40px;
        margin-top: 38px;
        margin-left: 20px;
    }
    .box-dot{
        position:absolute;
        right:12px;
        top: 30px;
        background-color: red;
        font-size: 16px;
        line-height: 22px;
        width: 20px;
        height: 20px;
        text-align: center;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        color: white;
        border-radius: 50%;
    }
    .new-co{
        position: fixed;
        bottom: 10px;
        right: 0;
        height: 150px;
    }
    .new-co-img{
        width: 160px;
        height: 110px;
        position: relative;
        top:30px;
        left: 0;
    }
    .new-co-w{
        font-size: 20px;
        color: #4B2E04;
        margin-left: 28px;
    }





    .new-co{
        position: fixed;
        bottom: 140px;
        right: 0;
        height: 150px;
    }
    .new-co-img{
        width: 160px;
        height: 110px;
        position: relative;
        top:30px;
        left: 0;
    }
    .new-co-w{
        font-size: 20px;
        color: #4B2E04;
        margin-left: 28px;
    }
    .m-ic{
        position: absolute;
        top: 24px;
        right: 24px;
        font-size: 50px;
        color: white;
        font-weight: 700;
    }
    .m-c{
        position: absolute;
        top: 65px;
        left: 145px;
        font-size: 64px;
        color: #FFFFFF;
        font-weight: 700;
    }
    .mid-content{
        background-color: white;
    }
    .m-c-w{
        margin: 24px 34px;
        font-size: 24px;
        color: #000000;
        text-align: center;
        line-height: 34px;
    }
    .m-c-time{
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .m-c-word{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
    }
    .m-top{
        border-top-right-radius: 32px;
        border-top-left-radius: 32px
    }
    .m-bottom{
        background-color: white;
        padding-bottom:32px ;
        padding-top: 20px;
        border-bottom-right-radius: 32px;
        border-bottom-left-radius: 32px
    }
    .overflow-mg{
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .mb-word{
        position: absolute;
        top: 32px;
        left: 100px;
        font-size: 24px;
        color: #FFFFFF;
        font-weight: 700;
    }

    .overflow-center-time{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin: 0px 0 8px 0 ;
    }
    .center-time-word{
        color: black;
        font-size: 24px;
    }
    .center-time{
        color: #FFFFFF;
        flex-direction: row;
        justify-content: start;
        margin-right: 32px;
    }
    .center-time-hh{
        background-color: black;
        color: white;
        border-radius: 6px;
        padding: 2px;
        margin: 8px 2px 2px 2px;
        font-size: 24px;
    }
    .center-time-hh-empty{
        background-color: black;
        color: white;
        border-radius: 6px;
        margin: 8px 2px 2px 2px;
        width: 32px;
        height: 32px;
    }
    .center-time-space{
        width: 750px;
        flex-direction: column;
        justify-content: center;
        text-align: center;
        color: black;
        font-size: 24px;
        margin-top: 4px;
        margin-bottom: 4px;
    }

</style>
