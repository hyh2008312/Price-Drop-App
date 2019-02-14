<template>
    <div class="wrapper">
        <div class="c-1" v-if="activeTab=='my'">

            <div class="c1-time" >
                <text class="iconfont " :class="[content.dropStatus=='progressing'?'c1-icon':'c1-icon-gray']">&#xe6fa;</text>
                <div class="flash-sales-time" v-if="content.dropStatus=='progressing'">
                    <text class="flash-sales-hh">{{ahour||'00'}}</text>
                    <text style="color: black;font-size: 24px; padding-top: 2px;">:</text>
                    <text class="flash-sales-hh">{{amin||'00'}}</text>
                    <text style="color: black;font-size: 24px; padding-top: 2px;">:</text>
                    <text class="flash-sales-hh" >{{asecond||'00'}}</text>
                </div>
                <text class="c1-word" v-if="content.dropStatus=='end'">Ended</text>
            </div>
            <text class="c1-h1" v-if="content.dropStatus=='progressing'&&parseInt(ahour)<=2">Hurry!</text>

        </div>

        <div class="c-1s" v-if="activeTab=='friend'">
            <div class="c1s-user">
                <div class="friend-avatar">
                    <preload style="width: 56px;height: 56px;" :src="content.avatar"></preload>
                </div>
                <text class="c1s-user-txt">{{content.userName}} started a Drop</text>
            </div>

            <div class="c1-time">
                <div class="flash-sales-time" >
                    <text class="flash-sales-hh">{{ahour||'00'}}</text>
                    <text style="color: black;font-size: 24px; padding-top: 2px;">:</text>
                    <text class="flash-sales-hh">{{amin||'00'}}</text>
                    <text style="color: black;font-size: 24px; padding-top: 2px;">:</text>
                    <text class="flash-sales-hh" >{{asecond||'00'}}</text>
                </div>
            </div>
            <!--<text class="c1-month">Sep 25th 2018</text>-->
        </div>


        <div class="c-2" @click="openDetail(content)">
            <preload class="c2-img"  :src="content.mainImage"></preload>

            <div >
                <text class="c2-txt">{{content.title}}</text>

                <div v-if="content.dropStatus=='progressing'">
                    <div class="c2-progress" >
                        <div class="c2-line2"></div>
                        <div class="c2-line1" :style="{'width':percent*376+'px'}"></div>
                    </div>
                    <div class="c2-avatar" >
                    <div v-for="i in content.friendsDrop" :class="[index===content.friendsDrop.length-1?'':'mg-r-14']">
                        <div class="c2a-co">
                            <preload style="width: 36px;height: 36px;" :src="i.avatar"></preload>
                            <!--<div style="width: 40px;height: 40px;background-color: #1D7CD4"></div>-->
                        </div>
                    </div>
                    <div v-for="j in handleArr([1,1,1,1,1],content.friendsDrop.length)" :class="[index===content.friendsDrop.length-1?'':'mg-r-14']">
                        <div class="c2a-co1">
                            <text class="c2a-txt">?</text>
                        </div>
                    </div>
                    <!--<text class="c2-txt1"  v-if="activeTab=='my'">Invite {{5-content.friendsDrop.length}} more friends</text>-->
                    <text class="c2-txt1"  v-if="activeTab=='my'"> Ask {{5-content.friendsDrop.length}} {{5-content.friendsDrop.length<5?'more':''}} friends to drop price</text>
                    <text class="c2-txt1"  v-if="activeTab=='friend'" >{{content.friendsDrop.length}} {{content.friendsDrop.length>1?'friends':'friend'}} helped</text>
                </div>
                </div>
                <div v-if="content.dropStatus=='end'">
                    <text class="c2-end-txt">Wow, your price dropped by ₹{{parseInt(content.salePrice)-parseInt(content.currentPrice)}}</text>
                </div>


                <div class="c3-price">
                    <text class="c3-p1-end" v-if="content.dropStatus=='end'&&content.payStatus=='unpaid'">Buy now for: </text>
                    <text class="c3-p1-end" v-if="content.dropStatus=='end'&&(content.payStatus=='overdue'||content.payStatus=='paid')"></text>
                    <text class="c3-p1" v-if="content.dropStatus=='progressing'">Current Price:</text>
                    <text class="c3-p2">₹{{parseInt(content.currentPrice)}}</text>
                    <text class="c3-p3">₹{{parseInt(content.salePrice)}}</text>
                </div>

            </div>

        </div>

        <div v-if="activeTab=='my'">

            <div class="c4-btn" v-if="content.dropStatus=='progressing'" @click="share">
                <text class="c4-txt">Share to Drop Price</text>
            </div>
            <div v-if="content.dropStatus=='end'">
                <div class="c4-btn1" v-if="content.payStatus=='unpaid'" @click="buyGoods">
                    <text class="c4-txt">Buy Now</text>
                </div>
                <div class="c4-end-time" v-if="content.payStatus=='unpaid'">
                    <text class="c4-end-tword">Price Expires in </text>
                    <div class="flash-sales-time" >
                        <text class="flash-sales-hh">{{ahour||'00'}}</text>
                        <text style="color: black;font-size: 24px; padding-top: 2px;">:</text>
                        <text class="flash-sales-hh">{{amin||'00'}}</text>
                        <text style="color: black;font-size: 24px; padding-top: 2px;">:</text>
                        <text class="flash-sales-hh" >{{asecond||'00'}}</text>
                    </div>
                </div>
                <div class="c4-end-btn" v-if="content.payStatus=='overdue'||content.payStatus=='paid'">
                    <text class="c4-end-btn1" @click="delDrop">Delete</text>
                    <text class="c4-end-btn2" @click="openGoodsDetail">Drop Again</text>
                </div>
            </div>
        </div>

        <div v-if="activeTab=='friend'">
            <div class="c4-friend">
                <div class="c4f-item">
                    <text class="iconfont c4f-i1">&#xe766;</text>
                    <text class="c4f-i2">  Reward for Help:  </text>
                    <text class="c4f-i3">₹{{parseInt(content.rewardBonus)}}</text>
                </div>
                <div class="c4f-btn color1"  v-if="content.canDrop" @click="dropPrice">
                    <text class="c4f-btn-txt">Help & Earn Reward</text>
                </div>
                <div class="c4f-btn color4"  v-else>
                    <text class="c4f-btn-txt">Successfully Helped</text>
                </div>
            </div>

        </div>

    </div>
</template>

<script>
    const shareModule = weex.requireModule('ShareModule');
    import preload from '../common/preloadImg';
    import { baseUrl } from '../../../config/apis';

    export default {
        props: ['content', 'activeTab'],
        components: { preload },
        data () {
            return {
                ahour: '',
                amin: '',
                asecond: '',
                percent: '',
                userAvatar: [1, 1, 1, 1, 1],
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
                },
            }
        },
        created () {
            // this.$notice.alert({
            //     message: this.content.dropLink
            // })
            if (this.content.dropStatus == 'progressing') {
                this.countDate(this.content.endTime)
            } else {
                this.countDate(this.content.cancelTime)
            }
            this.nextPage.title = this.content.title;
            this.nextPage.mainImage = this.content.mainImage;
            this.nextPage.salePrice = this.content.salePrice;
            this.nextPage.currentPrice = this.content.currentPrice;
            this.nextPage.id = this.content.variantId;
            this.nextPage.proId = 'dropGoods';
            this.nextPage.dropId = this.content.id
            this.nextPage.attributes = this.content.attribute;
            this.nextPage.productId = this.content.productId;
            this.nextPage.shippingPrice = this.content.shipping.priceItem;
            this.nextPage.shippingTimeMin = this.content.shipping.shippingTimeMin;
            this.nextPage.shippingTimeMax = this.content.shipping.shippingTimeMax;
        },
        methods: {
            dropPrice () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'POST',
                    url: `${baseUrl}/drops/friend/down/${this.content.id}/`,
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
            },
            delDrop () {
                this.$emit('del')
            },
            buyGoods (){
                if(this.content.orderId){
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
            handleArr (arr, l) {
                this.percent = l / 5
                let a
                arr.splice(0, l)
                a = arr
                return a
            },
            share () {
                shareModule.shareMorePlatform(this.content.dropLink)
            },
            openDetail (i) {
                this.$router.open({
                    name: 'drop.detail',
                    type: 'PUSH',
                    params: {
                        id: i.id
                    }
                })
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
                        this.ahour = '00'
                        this.amin = '00'
                        this.asecond = '00'
                    }
                }, 1000);
                // }
            },
        }
    }
</script>

<style scoped>
    .wrapper{
        padding: 32px;
        background-color: white;
    }
    .iconfont{
        font-family: iconfont;
    }
    .c-1{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .c-1s{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .c1s-user{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .c1s-user-txt{
        font-size: 24px;
        color: #000000;
    }
    .c1-h1{
        font-size: 28px;
        color: #492799;
        font-weight: 700;
    }
    .c1-time{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-right: 16px;

    }
    .c1-icon{
        margin-right: 16px;
        color: #492799;
    }
    .c1-icon-gray{
        margin-right: 16px;
        color: rgba(0,0,0,.38);
    }
    .c1-word{
        font-size: 24px;
        font-weight: 700;
        color: #000000;
    }
    .c1-month{
        font-size: 20px;
        color: rgba(0,0,0,.54);
    }
    .friend-avatar{
        width: 56px;
        height: 56px;
        border-radius: 50%;
        margin-right: 16px;
    }
    .flash-sales-time{
        flex-direction: row;
        justify-content: start;
    }
    .flash-sales-hh{
        font-family: ProximaNova;
        /*background-color: #EF8A31;*/
        color: black;
        border-radius: 6px;
        padding: 2px;
        margin: 2px;
        font-size: 24px;
        font-weight: 900;
    }
    .c-2{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top:24px;
    }
    .c2-img{
        width:176px;
        height: 176px;
        margin-right: 16px;
        border-radius: 8px;
    }
    .c2-txt{
        font-size: 22px;
        width: 352px;
        lines:1;
        color: rgba(0,0,0,0.87);
        line-height: 36px;
    }
    .c2-progress{
        width: 376px;
        height: 32px;
        margin-top: 22px;
    }
    .c2-line1{
        background-color: #5C0FC1;
        height: 8px;
        margin-top: -8px;
        border-radius: 32px;
    }
    .c2-line2{
        height: 8px;
        background-color: rgba(0,0,0,.12);
        border-radius: 32px;
    }
    .c2-avatar{
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .c2a-co{
        border-radius: 50%;

    }
    .c2a-co1{
        border-radius: 50%;
        border-style: dashed;
        border-width: 2px;
        border-color: rgba(0,0,0,.58);
        background-color: white;
    }

    .c2a-txt{
        font-size: 22px;
        color: #000000;
        padding: 2px 12px;
    }
    .c2-end-txt{
        font-size: 24px;
        color: #492799;
        margin-top: 55px;
        /*margin-bottom: 10px;*/
    }
    .c2-txt1{
        color: #492799;
        font-size: 20px;
        margin-left: 20px;
    }
    .c3-price{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top:12px
    }
    .c3-p1{
        font-size: 24px;
        color: #000000;
        margin-right: 6px;
    }
    .c3-p1-end{
        font-size: 24px;
        color: #000000;
    }
    .c3-p2{
        font-size: 48px;
        font-weight: 700;
        color: #000000;
        margin-right: 10px;
    }
    .c3-p3{
        font-size: 24px;
        color: #000000;
        text-decoration: line-through;
    }
    .c4-btn{
        width: 620px;
        /*width: 592px;*/
        height: 64px;
        background-color: #492799;
        align-items: center;
        justify-content: center;
        border-radius: 32px;
        margin-top:24px;
    }
    .c4-btn1{
        width: 620px;
        /*width: 592px;*/
        height: 64px;
        background-color: #EF8A31;
        align-items: center;
        justify-content: center;
        border-radius: 32px;
        margin-top:24px;
    }
    .c4-txt{
        font-size: 24px;
        color: #FFFFFF;
        font-weight: 700;
    }
    .c4-end-time{
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-top:16px;
    }
    .c4-end-tword{
        font-size: 24px;
        color: #000000;
    }
    .c4-end-btn{
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-top: 20px;
    }
    .c4-end-btn1{
        color:#CB1200;
        font-size: 24px;
        font-weight: 700;
        padding: 18px 112px;
        border-radius: 50%;
        border-width: 2px;
        border-style: solid ;
        border-color: #CB1200;
        margin-right: 16px;
    }
    .c4-end-btn2{
        color:white;
        font-size: 24px;
        font-weight: 700;
        padding: 18px 92px;
        background-color:#CB1200;
        border-radius: 50%;
        border-width: 2px;
        border-style: solid ;
        border-color: #CB1200;
    }
    .c4-friend{
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        margin-top: 24px;
    }
    .c4f-item{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        height: 50px;
    }
    .c4f-i1{
        font-size: 38px;
        color: #EF8A31;
    }
    .c4f-i11{
        font-size: 38px;
        color: #00B437;
    }
    .c4f-i2{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
    }
    .c4f-i3{
        font-size: 30px;
        color: red;
        font-weight: 700;
    }
    .c4f-btn{
        width: 264px;
        height: 64px;
        border-radius: 50%;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .c4f-btn-txt{
        font-size: 24px;
        color: #FFFFFF;
        font-weight: 700;
    }
    .color1{
        background-color: #492799 ;
    }
    .color4{
        background-color: #8F8F8F;
    }
    .mg-r-14{
        margin-right: -16px;
    }
</style>
