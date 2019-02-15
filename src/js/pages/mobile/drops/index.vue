<template>
    <div class="wrapper">
        <div class="state"></div>
        <tab   @tabTo="onTabTo" :items="tabsItems" :indexKey="activeTab"></tab>

        <list class="main-list" ref="list" offset-accuracy="10" loadmoreoffset="400" >
            <refresher class="gd-bg-gray" ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>
            <cell >
                <mydroptop :dropTab="activeTab" :allBouns="dropBonus" v-on:rulerType="openRulerPage($event)"></mydroptop>
            </cell>
            <!--<cell>-->
                <!--<div class="overflow-mid1" v-if="user==''&&activeTab=='my'" @click="redirectLogin">-->
                    <!--<div class="top-card"></div>-->
                    <!--<div class="t-cm">-->
                        <!--<div class="t-cm-c" >-->
                            <!--<text class="t-cm-cw">Your friends started 200+ Drops & saved ₹20,000+</text>-->
                            <!--<text class="t-cm-ca iconfont" ref="getArrow">&#xe626;</text>-->
                        <!--</div>-->
                    <!--</div>-->

                    <!--&lt;!&ndash;<div class="w-img">&ndash;&gt;-->
                    <!--<image class="w-i5" style=" width: 100px;height: 43px;" src="bmlocal://assets/perks/5.png"></image>-->
                    <!--<image class="w-i3" style=" width: 62px;height: 98px;" src="bmlocal://assets/perks/3.png"></image>-->
                    <!--<image class="w-i1" style=" width: 60px;height: 110px;" src="bmlocal://assets/perks/1.png"></image>-->

                    <!--<image class="w-i6" style=" width: 98px;height: 106px;" src="bmlocal://assets/perks/6.png"></image>-->

                    <!--<image class="w-i2" style=" width: 100px;height: 102px;" src="bmlocal://assets/perks/2.png"></image>-->
                    <!--<image class="w-i4" style=" width: 80px;height: 96px;" src="bmlocal://assets/perks/4.png"></image>-->
                    <!--&lt;!&ndash;</div>&ndash;&gt;-->
                <!--</div>-->
            <!--</cell>-->

            <cell>
                <div class="overflow-mid2" v-if="user==''&&activeTab=='friend'" @click="redirectLogin">
                    <image class="om-img" style="" src="bmlocal://assets/default.png"></image>
                    <div class="om-btn">
                        <text class="om-txt">Login to Earn Reward</text>
                    </div>
                </div>
            </cell>


            <cell v-for="(i,index) in myDropList" v-if="activeTab=='my'&&user!=''">
                <div class="overflow-card" >
                    <card :content="i" :activeTab="activeTab" @del="delItem(i,index)"></card>
                </div>
            </cell>



            <cell v-for="i in friendDropList" v-if="activeTab=='friend'&&user!=''">
                <div class="overflow-card" >
                    <card :content="i" :activeTab="activeTab"></card>
                </div>
            </cell>

            <cell  v-if="activeTab=='friend'&&user!=''&&friendDropList.length===0">
                <div class="overflow-mid3">
                    <image class="om-img" style="" src="bmlocal://assets/empty.png"></image>
                    <text class="om-txt1">There is no Drop from your friends yet. </text>
                </div>
            </cell>


            <cell @click="openMoreList" v-if="myDropList.length>=5&&activeTab=='my'">
                <div class="overflow-va">
                    <text class="v-a-word">View All</text>
                </div>
            </cell>

            <cell @click="openMoreList" v-if="friendDropList.length>=5&&activeTab=='friend'">
                <div class="overflow-va">
                    <text class="v-a-word">View All</text>
                </div>
            </cell>
            <!--<loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">-->
                <!--<image class="loading-icon" src="bmlocal://assets/loading.gif"></image>-->
            <!--</loading>-->

            <cell v-if="someGoodsList.length!=0">
                <div class="top-title">
                    <text class="tt-txt">Popular Drops</text>
                </div>
                <div   v-for="(i, index) in someGoodsList" :key="i.id"  :class="[index==someGoodsList.length-1 ?'mg-b-32':'',]">
                    <somegoods :goods="i"  :type="1" ></somegoods>  <!-- 1：一列 2：两列 -->
                </div>
            </cell>
        </list>

        <WxcMask
            height="477"
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
                <div class="mc-top">
                    <text class="mct-word">₹{{parseInt(dropObj.rewardBonus)}}</text>
                </div>
                <div class="mask-mid">

                    <text class="mm-word">Thank you for your help!</text>
                    <text class="mm-word1">You will earn the cash reward once this order is delivered.</text>
                </div>

                <image src="bmlocal://assets/home/voucher-part.png" style="width: 398px;height: 28px;"></image>
                <div class="mask-bottom">
                    <div class="mb-btn">
                        <text class="mb-btn-word">OK</text>
                    </div>
                </div>
            </div>
        </WxcMask>
    </div>
</template>
<script>
    import refresher from '../common/refresh';
    import { Utils } from 'weex-ui';
    import tab from './tab';
    import card from './card';
    import mydroptop from './myDropTop';
    import { baseUrl } from '../../../config/apis';
    import somegoods from './someGoods';
    import { WxcMask } from 'weex-ui';



    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'refresher': refresher,
            tab, card, mydroptop,somegoods,WxcMask
        },
        eros: {
            beforeAppear (params, options) {
            }
        },
        destory () {
            this.$event.off('login')
            this.$event.off('createCut')
            this.$event.off('logout')
            this.$event.off('jumpMyDrop')
        },
        data () {
            return {
                tabsItems: [{
                    name: 'My Drops',
                    key: 'my'
                }, {
                    name: 'Help Friends',
                    key: 'friend'
                }],
                activeTab: 'my',
                myDropList: [],
                friendDropList: [],
                productList: [],
                dropBonus: '',
                someGoodsList:[],
                dropObj:{},
                newShow:false,
                user: '',
                page: 1,
                length: 2,
                pageSize: 5
            }
        },
        created () {
            const pageHeight = Utils.env.getScreenHeight();
            this.height = { height: (pageHeight - 48 - 112 - 128 - 96 - 112) + 'px' };
            this.$event.on('dropPrice', parmas => {
                this.dropObj = parmas.drop
                this.newShow = true
                this.getFriendsDropList()
            })
            this.$event.on('createDrop', parmas => {
                this.getMyDropList()
            })
            this.$event.on('placeOrder', parmas => {
                this.getMyDropList()
            })
            this.$event.on('paid', parmas => {
                this.getMyDropList()
            })
            this.$event.on('login', parmas => {
                this.init()
            })
            this.init()
        },
        methods: {
            init (){
                this.getSomeGoodsList()
                this.user = this.$storage.getSync('user')
                if (this.user) {
                    this.getMyDropList()
                    this.getFriendsDropList()
                    this.getMyDropBonus()
                }
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                if(this.activeTab == 'my'){
                    this.getMyDropList()

                } else {
                    this.getFriendsDropList()
                }
                this.getMyDropBonus()

            },
            getMyDropList () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'drops.list',
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res.results[1]
                    // })

                    this.myDropList = []
                    this.productList = []
                    setTimeout(() => {
                        this.myDropList = [...res.results]
                        this.productList = [...this.myDropList]
                    }, 10)



                    // // this.pArr = []
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            getSomeGoodsList () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'post',
                    name: 'drop.hot.push',
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.someGoodsList = [...res]
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    this.$notice.alert({
                        message: res
                    })
                })
            },
            getFriendsDropList () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'drops.friends.new.list',
                    // data: {
                    //     page: this.page,
                    //     page_size: this.pageSize
                    // },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.friendDropList = []
                    setTimeout(() => {
                        this.friendDropList = [...res]
                    }, 10)
                    // this.totalPoints = res.totalPoints
                    // this.availablePoints = res.availablePoints
                    // this.pendingPoints = this.totalPoints - this.availablePoints
                    // // this.pArr = []
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            getMyDropBonus () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'point.drops.bonus',
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.dropBonus = res[0]
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            delItem (i,deleteIndex) {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'POST',
                    url: `${baseUrl}/drops/delete/${i.id}/`,
                    header: {
                        needAuth: true
                    }
                }).then((res) => {

                    this.$notice.loading.hide();
                    this.myDropList.splice(deleteIndex, 1);
                    this.$notice.toast({
                        message: 'Successfully Deleted! '
                    })
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            openMoreList(){
                if(this.activeTab=='my'){
                    this.$router.open({
                        name: 'more.my.drop.list',
                        type: 'PUSH',

                    })
                }else {
                    this.$router.open({
                        name: 'more.friend.drop.list',
                        type: 'PUSH',

                    })
                }

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
            onTabTo (e) {
                this.activeTab = e.data.key
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
            wxcMaskSetShareHidden () {
                this.newShow = false;
            },
            openRulerPage (e) {
                // this.$notice.alert({
                //     message: e
                // })
                this.$router.open({
                    name: 'drop.ruler',
                    type: 'PUSH',
                    params: {
                        type: e
                    }
                })
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
        background-color: #D1C8E8;
        padding-bottom: 114px;
        margin-top: 2px;
    }
    .overflow-card{
        width: 686px;
        margin-left: 32px;
        margin-top: 24px;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }

    .wrapper {
        background-color: white;
    }
    .gd-bg-gray{
        background-color: transparent;
    }
    .top-card{
        width: 686px;
        height: 184px;
        margin-top: 32px;
        background-color: #31005f;
        border-radius: 180px;
    }
    .overflow-mid1{
        flex-direction: row;
        justify-content: center;
        height: 312px;
    }
    .t-cm{
        position: absolute;
        top: 180px;
        left: 32px;
        width: 686px;

    }
    .t-cm-c{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        background-color: #6900CB;
        height: 108px;
        border-radius:50%;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.50);
    }
    .t-cm-cw{
        font-size: 24px;
        color: white;
        font-weight: 700;
        margin-left: 10px;
    }
    .t-cm-ca{
        font-size: 32px;
        color: white;
        margin-left: 10px;
        font-weight: 700;
    }
    .w-i5{
        position: absolute;
        top: 138px;
        left: 113px;
    }
    .w-i3{
        position: absolute;
        top: 82px;
        left: 210px;
    }
    .w-i1{
        position: absolute;
        top: 71px;
        left: 274px;
    }
    .w-i6{
        position: absolute;
        top: 84px;
        left: 336px;
    }
    .w-i2{
        position: absolute;
        top: 78px;
        left: 435px;
    }
    .w-i4{
        position: absolute;
        top: 84px;
        right: 130px;
    }
    .overflow-mid2{
        width: 686px;
        margin: 32px ;
        background-color: white;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .overflow-mid3{
        width: 686px;
        margin: 32px 32px 0 32px ;
        background-color: white;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .overflow-va{
        width: 610px;
        margin: 32px 32px 20px 64px;
        background-color: transparent;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        border-radius: 50%;
        border-color: #492799;
        border-width: 1px;
        border-style: solid;
    }
    .v-a-word{
        font-size: 24px;
        color: #492799;
        font-weight: 700;
        margin-bottom: 16px;
        margin-top: 20px;
    }
    .om-img{
        width: 160px;
        height: 140px;
        margin-top: 20px;
        margin-bottom: 12px;
    }
    .om-btn{
        width: 312px;
        height: 64px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        background-color: #492799;
        border-radius: 32px;
        margin-bottom: 32px;
    }
    .om-txt{
        color: white;
        font-size: 24px;
        font-weight: 700;
    }
    .om-txt1{
        color: rgba(0,0,0,.54);
        font-size: 24px;
        font-weight: 700;
        margin-bottom: 42px;
    }
    .top-title{
        flex-direction: row;
        align-items: center;
        justify-content: center;
        width: 750px;
        margin-top: 32px;
        background-color: transparent;
    }
    .tt-txt{
        font-size: 32px;
        font-weight: 700;
    }
    .mg-b-32{
        margin-bottom: 32px;
    }
    .mc-top{
        background-image: linear-gradient(to right, #C1B1E8,#5B37AE);
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .mct-word{
        font-size: 72px;
        font-weight: 900;
        color: white;
        margin-bottom: 28px;
        margin-top: 42px;
    }
    .mask-mid{
        background-color: white;
        flex-direction: column;
        align-items: center;
        justify-content: start;
    }
    .mm-word{
        font-size: 24px;
        color: #000000;
        width:320px;
        text-align: center;
        margin-top: 32px;
        margin-bottom: 16px;
    }
    .mm-word1{
        font-size: 24px;
        color: #000000;
        width:320px;
        text-align: center;
        font-weight: 700;
        margin-bottom: 16px;

    }
    .mask-bottom{
        flex-direction: row;
        align-items: center;
        justify-content: center;
        background-color: white;
    }
    .mb-btn{
        width:140px;
        height:60px;
        margin-top: 36px;
        margin-bottom: 48px;
        border-radius: 100%;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        background-image: linear-gradient(to right,#5B37AE,#C1B1E8);

    }
    .mb-btn-word{
        font-size: 24px;
        color: #FFFFFF;
        font-weight: 700;
    }
</style>
