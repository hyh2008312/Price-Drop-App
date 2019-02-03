<template>
    <div class="wrapper">
        <div class="state"></div>
        <scroller class="main-scroller">
            <div class="wrapper-head">
                <div class="navigation">
                    <text class="homeBack" @click="$router.back()">&#xe6f6;</text>
                    <div class="cut-rule" @click="showCutRule">
                        <text class="cut-rule-icon iconfont">&#xe709;</text>
                        <text class="cut-rule-text">Price Drop FAQ</text>
                    </div>
                </div>
            </div>
            <div class="top">
                <div class="top-friend" v-if="drop.user=='friend'">
                    <div class="topf-img" >
                        <image style="width:56px;height:56px " :src="drop.avatar"></image>
                    </div>
                    <text class="topf-txt">{{drop.userName}} started a Drop</text>
                </div>
                <div v-if="drop.user=='owner'" class="empty" style="height:20px"></div>  <!--占位-->
                <div class="top-my">

                    <image class="pro-img" :src="drop.mainImage"></image>
                    <div class="pro-word">
                        <text class="pro-t1" >{{drop.title}}</text>
                        <text class="pro-t2">{{drop.dropsPeopleNumber|| 0}}+ people have started to drop</text>
                    </div>
                </div>

            </div>
            <div class="middle" v-if="drop.user=='owner'">

                <div class="m1-price">
                    <text class="m1-p1">₹{{parseInt(drop.currentPrice)||0}}</text>
                    <text class="m1-p2">₹{{parseInt(drop.salePrice)||0}}</text>
                </div>
                <text class="m2-txt">Invite {{5-drop.friendsDrop.length}} more friends to drop the price</text>

                <div class="m3-progress">
                    <div class="m3p-user" >
                        <div  v-for="(i,index) in drop.friendsDrop " >
                            <div style="margin-left: 80px" v-if="index==drop.friendsDrop.length-1">
                                <image style="width: 40px;height: 48px;" src="bmlocal://assets/drop-avatar-icon.png"></image>
                                <div class="m3-user">
                                    <preload style="width: 35px;height: 35px" :src="i.avatar"></preload>
                                </div>
                            </div>
                            <div style="margin-left: 85px" v-else>
                                <image style="width: 40px;height: 48px;" src="bmlocal://assets/drop-avatar-icon.png"></image>
                                <div class="m3-user">
                                    <preload style="width: 35px;height: 35px" :src="i.avatar"></preload>
                                </div>
                            </div>
                        </div>

                        <div  v-for="(j,index) in handleArr([1,1,1,1,1],drop.friendsDrop.length) " >
                            <div   style="margin-left: 77px" >
                                <preload style="width: 46px;height: 52px;" src="bmlocal://assets/drop-avatar-icon.png"></preload>
                            </div>
                        </div>

                    </div>

                    <div class="m-progress">
                        <div class="m-line2"></div>
                        <div class="m-line1" :style="{'width':percent*615+'px'}"></div>
                    </div>
                </div>

                <div class="m4-btn" v-if="drop.dropStatus=='progressing'" @click="share">
                    <text class="m4-btn-word color1">Share to Drop Price</text>
                </div>
                <div v-if="drop.dropStatus=='end'">
                    <div class="m4-btn" v-if="drop.payStatus=='unpaid'" @click="buyGoods">
                        <text class="m4-btn-word color2">Buy Now</text>
                    </div>
                    <div class="m4-btn" v-else  @click="openGoodsDetail">
                        <text class="m4-btn-word color3">Drop Again</text>
                    </div>
                </div>
                <div class="m-time">
                    <div class="flash-sales-time" >
                        <text class="flash-sales-hh">{{ahour||'00'}}</text>
                        <text class="flash-sales-dot" >:</text>
                        <text class="flash-sales-hh">{{amin||'00'}}</text>
                        <text class="flash-sales-dot" >:</text>
                        <text class="flash-sales-hh" >{{asecond||'00'}}</text>
                    </div>
                </div>
            </div>

            <div class="middle" v-if="drop.user=='friend'">

                <div class="m1-price" v-if="drop.canDrop.toString()=='true'">
                    <text class="m1-p1-iconfont1 iconfont">&#xe766;</text>
                    <text class="m1-p1-friend">Reward for Help</text>
                    <text class="m1-p1" style="color: #E30000">₹{{parseInt(drop.rewardBonus)||0}}</text>
                </div>
                <div class="m1-price" v-if="drop.canDrop.toString()=='false'">
                    <text class="m1-p1-iconfont iconfont">&#xe6fb;</text>
                    <text class="m1-p1-friend">Earned</text>
                    <text class="m1-p1" style="color: #E30000">₹{{parseInt(drop.rewardBonus)||0}}</text>
                </div>

                <div class="m1-price-friend">
                    <text class="m1-p3">₹{{parseInt(drop.currentPrice)||0}}</text>
                    <text class="m1-p2">₹{{parseInt(drop.salePrice)||0}}</text>
                </div>
                <div class="m3-progress">

                    <div class="m3p-user" >
                        <div  v-for="(i,index) in drop.friendsDrop " >
                            <div style="margin-left: 80px" v-if="index==drop.friendsDrop.length-1">
                                <image style="width: 40px;height: 48px;" src="bmlocal://assets/drop-avatar-icon.png"></image>
                                <div class="m3-user">
                                    <preload style="width: 35px;height: 35px" :src="i.avatar"></preload>
                                </div>
                            </div>
                            <div style="margin-left: 85px" v-else>
                                <image style="width: 40px;height: 48px;" src="bmlocal://assets/drop-avatar-icon.png"></image>
                                <div class="m3-user">
                                    <preload style="width: 35px;height: 35px" :src="i.avatar"></preload>
                                </div>
                            </div>
                        </div>

                        <div  v-for="(j,index) in handleArr([1,1,1,1,1],drop.friendsDrop.length) " >
                            <div   style="margin-left: 77px" >
                                <preload style="width: 46px;height: 52px;" src="bmlocal://assets/drop-avatar-icon.png"></preload>
                            </div>
                        </div>

                    </div>


                    <div class="m-progress" >
                        <div class="m-line2"></div>
                        <div class="m-line1" :style="{'width':percent*615+'px'}"></div>
                    </div>

                </div>



                <div class="m4-btn" v-if="drop.canDrop.toString()=='true'" @click="dropPrice">
                    <text class="m4-btn-word color1">Help & Earn Rewards</text>
                </div>
                <div class="m4-btn" v-if="drop.canDrop.toString()=='false'" >
                    <text class="m4-btn-word color4">Successfully Helped</text>
                </div>

                <div class="m-time">
                    <div class="flash-sales-time" >
                        <text class="flash-sales-hh">{{ahour||'00'}}</text>
                        <text class="flash-sales-dot" >:</text>
                        <text class="flash-sales-hh">{{amin||'00'}}</text>
                        <text class="flash-sales-dot" >:</text>
                        <text class="flash-sales-hh" >{{asecond||'00'}}</text>
                    </div>
                </div>
            </div>




            <div :class="[drop.user=='owner'? 'dot-line':'dot-line-friend' ]">
                <div>
                    <div class="t-line-dot"></div>
                    <div class="t-line"></div>
                    <div class="t-line-dot"></div>
                </div>
                <div>
                    <div class="t-line-dot"></div>
                    <div class="t-line"></div>
                    <div class="t-line-dot"></div>
                </div>
            </div>

            <div class="contributors" v-if="drop.friendsDrop.length>0">
                <text class="contributors-people">Price Drop Contributors</text>
                <div :style="{'margin-bottom':'60px'}">
                    <div class="contributors-info" v-for="(i ,index) in drop.friendsDrop">
                        <div class="contributors-content">
                            <div class="contributors-content-left">
                                <div class="contributors-content-left-image">
                                    <preload class="contributors-content-left-image-in" resize="cover"
                                           :src="i.avatar"></preload>
                                </div>
                                <text class="contributors-content-left-name">{{i.userName}}</text>
                            </div>
                            <div class="contributors-content-right">
                                <text class="contributors-content-right-1">Dropped  </text>
                                <text class="contributors-content-right-2">₹{{parseInt(i.dropAmount)}}</text>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div>
                <div class="top-title">
                    <text class="tt-txt">You may also like</text>
                </div>
                <div   v-for="(i, index) in someGoodsList" :key="i.id"  >
                    <somegoods :goods="i"  :type="1" ></somegoods>  <!-- 1：一列 2：两列 -->
                </div>
            </div>

        </scroller>
    </div>
</template>

<script>
    import { WxcCountdown, WxcPopup, WxcMask } from 'weex-ui';
    const shareModule = weex.requireModule('ShareModule');

    import { baseUrl } from '../../../config/apis';
    import preload from '../common/preloadImg';
    import somegoods from './someGoods';
    export default {
        components: {
            preload, somegoods
        },
        data () {
            return {
                ahour: '',
                amin: '',
                asecond: '',
                percent: '',
                dropId: '',
                user_id: '',
                drop: '',
                user: '',
                dropLink: '',
                someGoodsList: [],
                nextPage: {
                    title: '',
                    mainImage: '',
                    salePrice: '',
                    currentPrice: '', // 计算价钱的金额
                    attributes: '',   // 变体字符串
                    dropId: '',
                    productId: '',
                    quantity: '1',
                    id: '',           // 变体ID
                    shippingPrice: '',
                    shippingTimeMin: '',
                    shippingTimeMax: '',
                    proId: '',
                    flashSale: {}
                }
            }
        },
        created () {
            // this.percent = 50/100
            this.$router.getParams().then(resData => {
                this.dropId = resData.id
                this.getDropDetail(this.dropId)
            })
            this.init()
        },
        methods: {
            init(){
                this.user = this.$storage.getSync('user')
                this.getSomeGoods()
                this.nextPage.title = this.drop.title;
                this.nextPage.mainImage = this.drop.mainImage;
                this.nextPage.salePrice = this.drop.salePrice;
                this.nextPage.currentPrice = this.drop.currentPrice;
                this.nextPage.id = this.drop.variantId;
                this.nextPage.proId = 'dropGoods';
                this.nextPage.dropId = this.drop.id;
                this.nextPage.attributes = this.drop.attribute;
                this.nextPage.productId = this.drop.productId;
                this.nextPage.shippingPrice = this.drop.shipping.priceItem;
                this.nextPage.shippingTimeMin = this.drop.shipping.shippingTimeMin;
                this.nextPage.shippingTimeMax = this.drop.shipping.shippingTimeMax;
            },
            getDropDetail (id) {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/drops/detail/${id}/`,
                    data: {
                        user_id: this.user.id
                    }
                }).then((res) => {
                    this.drop = res
                    // this.$notice.alert({
                    //     message: this.drop.canDrop.toString()
                    // })
                    this.dropLink = res.dropLink
                    if (this.drop.dropStatus == 'progressing') {
                        this.countDate(this.drop.endTime)
                    } else {
                        this.countDate(this.drop.cancelTime)
                    }
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            share () {
                shareModule.shareMorePlatform(this.dropLink)
            },
            dropPrice () {
                if(this.user ==''){
                    this.redirectLogin()
                } else {
                    this.$notice.loading.show();
                    this.$fetch({
                        method: 'POST',
                        url: `${baseUrl}/drops/friend/down/${this.dropId}/`,
                        header: {
                            needAuth: true
                        }
                    }).then((res) => {
                        // this.$notice.alert({
                        //     message: res
                        // })

                        this.drop = res
                        this.$event.emit('dropPrice');
                        this.$notice.loading.hide();
                    }).catch((res) => {
                        this.$notice.loading.hide();
                        this.$notice.toast({
                            message: res.errorMsg
                        })
                    })
                }
            },
            handleArr (arr, l) {
                this.percent = l / 5
                // this.percent = 0.6
                let a
                arr.splice(0, l)
                a = arr
                return a
            },
            buyGoods (){
                if(this.content.orderId!=''){
                    this.$router.open({
                        name: 'order.detail',
                        params: {
                            id: this.content.orderId
                        }
                    });
                } else {
                    this.$router.open({
                        name: 'order.confirm',
                        type: 'PUSH',
                        params: this.nextPage
                    })
                }
            },
            openGoodsDetail () {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: this.content.productId
                    }
                })
            },
            redirectLogin () {
                this.$event.on('login', params => {
                    this.user = this.$storage.getSync('user')
                    if (this.user) {
                        this.init()
                    }
                });
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            },

            getSomeGoods (id) {
                this.$fetch({
                    methods: 'GET',
                    url: `${baseUrl}/product/relations/recommend/list/`,
                    // url: `${baseUrl}/product/customer/detail/654/`,
                    data: {
                        id: 10000
                    }
                }).then((res) => {
                    this.someGoodsList = [...res]
                    // this.$notice.alert({
                    //     message: res
                    // })
                    // let arr = [];
                    // for (let i = 0; i < res.length; i++) {
                    //     const item = res[i];
                    //     arr.push(item);
                    //     if ((i > 0 && i % 2 === 1) || i === res.length - 1) {
                    //         this.someGoodsList.push(arr);
                    //         arr = [];
                    //     }
                    // }
                    // this.$notice.alert({
                    //     message: this.someGoodsList
                    // })
                }).catch((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            countDate (time) {
                const self = this
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
                    //     message: self.ahour
                    // })
                    if ((self.ahour.toString()).indexOf('-') > 0) {
                        this.ahour= '00'
                        this.amin= '00'
                        this.asecond= '00'
                    }
                }, 1000);
                // }
            },
        }

    }
</script>

<style scoped>
    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .wrapper {
        background-color: white;
    }
    .iconfont{
        font-family: iconfont;
    }
    .main-scroller{
        background-color:#D1C8E8;
    }
    .wrapper-head {
        width: 750px;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }
    .navigation {
        display: flex;
        flex-direction: row;
        height: 112px;
        width: 750px;
        justify-content: space-between;
        align-items: center;
    }
    .homeBack {
        font-family: iconfont;
        color: black;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .cut-rule {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
        margin-right: 30px;
        background-color: white;
        height: 40px;
        border-radius: 24px;
        padding-left: 12px;
        padding-right: 12px;
    }
    .cut-rule-text {
        color: black;
        font-size: 20px;
        margin-left: 10px;
    }
    .cut-rule-icon {
        font-family: iconfont;
        color: #00CFE3;
        font-size: 25px;
    }
    .top{
        width: 686px;
        margin: 0 32px;
        border-radius: 16px;
        background-color: white;
    }
    .top-friend{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        height: 56px;
        margin-left: 30px;
        margin-top: 24px;
    }
    .topf-img{
        margin-right: 16px;
        border-radius: 50%;
    }
    .topf-txt{
        font-size: 24px;
        color: #000000;
    }
    .top-my{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 16px;
    }
    .pro-img{
        width: 160px;
        height: 160px;
        margin: 0px 24px 48px 30px;
    }
    .pro-word{
        flex-direction: column;
        justify-content: space-between;
        align-items: start;
        height: 160px;
        margin-bottom: 42px;
        /*background-color: #EF8A31;*/
    }
    .pro-t1{
        width: 460px;
        font-size: 26px;
        text-overflow:ellipsis;
        white-space: nowrap;
        lines:2;
        color: rgba(0,0,0,0.87);
    }
    .pro-t2{
        font-size: 20px;
        color: #492799;
    }
    .dot-line{
        position: absolute;
        top:328px;
        left: 0;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        width: 618px;
        margin-left: 66px;
    }
    .dot-line-friend{
        position: absolute;
        top:388px;
        left: 0;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        width: 618px;
        margin-left: 66px;
    }
    .t-line-dot{
        width: 18px;
        height: 18px;
        border-radius: 50%;
        background-color: #492799 ;
    }
    .t-line{
        width: 4px;
        height: 70px;
        margin-left: 6px;
        background-color: #726786 ;
    }
    .middle{
        width: 686px;
        margin: 32px;
        border-radius: 16px;
        background-color: white;
    }
    .m1-price{
        margin-top: 54px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .m1-price-friend{
        margin-top: 20px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .m1-p1{
        font-size: 48px;
        font-weight: 700;
    }
    .m1-p1-iconfont{
        font-size: 32px;
        color: #00B437 ;
    }
    .m1-p1-iconfont1{
        font-size: 32px;
        color: #EF8A31 ;
    }
    .m1-p1-friend{
        font-size: 32px;
        font-weight: 700;
        margin-left: 10px;
        margin-right: 17px;
    }
    .m1-p2{
        font-size: 24px;
        color: #000000;
        margin-left: 12px;
        text-decoration: line-through;
    }
    .m1-p3{
        font-size: 28px;
        font-weight: 700;
    }
    .m2-txt{
        font-size: 24px;
        color: #492799;
        margin-top:30px;
        text-align: center;
    }
    .m3-progress{
        margin: 24px 32px  14px 32px;
        height: 100px;
        flex-direction: column;
        align-items: center;
        justify-content: start;
    }
    .m3p-user{
        flex-direction: row;
        align-items: center;
        justify-content: start;
    }
    .m3-user{
        border-radius: 100%;
        width: 35px;
        height: 35px;
        position: absolute;
        top: 2px;
        left: 2px;
    }
    .m-progress{
        width: 615px;
        height: 32px;
        margin-top: 6px;
    }
    .m-line1{
        background-image: linear-gradient(to right, #5B37AE, #C1B1E8);
        height: 20px;
        margin-top: -20px;
        border-radius: 32px;
    }
    .m-line2{
        height: 20px;
        background-color: rgba(0,0,0,.12);
        border-radius: 32px;
    }
    .m4-btn{
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-bottom: 24px;
    }
    .m4-btn-word{
        width: 528px;
        font-size: 24px;
        padding: 28px 0;
        border-radius: 50%;
        text-align: center;
        color: white;
        font-weight: 700;
    }
    .color1{
        background-color: #492799 ;
    }
    .color2{
        background-color: #EF8A31;
    }
    .color3{
        background-color: #CB1200;
    }
    .color4{
        background-color: #8F8F8F;
    }
    .m-time{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        margin-right: 16px;
        margin-bottom: 50px;
    }
    .flash-sales-time{
        flex-direction: row;
        justify-content: start;
    }
    .flash-sales-dot{
        color: black;
        font-size: 28px;
        padding-top: 18px;
        margin: 0 12px;
        font-weight: 900;
    }
    .flash-sales-hh{
        /*background-color: #EF8A31;*/
        box-shadow: 0 1px 2px 0 rgba(0,0,0,0.17);
        color: black;
        border-radius: 12px;
        padding: 18px;
        margin: 2px;
        font-size: 28px;
        font-weight: 900;
    }
    /*.bottom{*/
        /*width: 686px;*/
        /*margin: 0 32px;*/
    /*}*/

    .contributors-content-left-image-in {
        height: 80px;
        width: 80px;
    }

    .contributors-content-left-image {
        height: 80px;
        width: 80px;
        border-radius: 40px;
    }

    .contributors-content-left-name {
        margin-left: 24px;
        width: 250px;
        lines:1;
        text-overflow: ellipsis;
    }

    .contributors-content-right {
        margin-right: 32px;
        flex-direction: row;
        justify-content: flex-start;
    }

    .contributors-content-right-1 {
        font-size: 28px;
        font-weight: 400;
        color: #000000;
    }

    .contributors-content-right-2 {
        font-size: 28px;
        font-weight: bold;
        color: #000000;
    }
    .contributors-content-left {
        margin-left: 32px;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;

    }

    .contributors-content {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .contributors-info {
        height: 80px;
        margin-top: 32px;
    }

    .contributors-people {
        width: 718px;
        text-align: center;
        font-size: 28px;
        color: black;
        font-weight: bold;
        margin-top: 32px;
    }

    .contributors {
        margin: 35px 32px 40px 32px;
        background-color: #FFFFFF;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.12);
    }

    .top-title{
        flex-direction: row;
        align-items: center;
        justify-content: center;
        width: 718px;
        background-color: transparent;
    }
    .tt-txt{
        font-size: 32px;
        font-weight: 700;
    }
</style>
