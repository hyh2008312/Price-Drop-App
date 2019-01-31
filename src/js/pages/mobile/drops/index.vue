<template>
    <div class="wrapper">
        <div class="state"></div>
        <tab   @tabTo="onTabTo" :items="tabsItems" :indexKey="activeTab"></tab>

        <list class="main-list" ref="list" offset-accuracy="10" loadmoreoffset="400" >
            <refresher class="gd-bg-gray" ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>
            <cell >
                <mydroptop :dropTab="activeTab" :allBouns="dropBonus"></mydroptop>
            </cell>
            <cell>
                <div class="overflow-mid1" v-if="user==''&&activeTab=='my'" @click="redirectLogin">
                    <div class="top-card"></div>
                    <div class="t-cm">
                        <div class="t-cm-c" >
                            <text class="t-cm-cw">Your friends started 200+ Drops & saved ₹20,000+</text>
                            <text class="t-cm-ca iconfont" ref="getArrow">&#xe626;</text>
                        </div>
                    </div>

                    <!--<div class="w-img">-->
                    <image class="w-i5" style=" width: 100px;height: 43px;" src="bmlocal://assets/perks/5.png"></image>
                    <image class="w-i3" style=" width: 62px;height: 98px;" src="bmlocal://assets/perks/3.png"></image>
                    <image class="w-i1" style=" width: 60px;height: 110px;" src="bmlocal://assets/perks/1.png"></image>

                    <image class="w-i6" style=" width: 98px;height: 106px;" src="bmlocal://assets/perks/6.png"></image>

                    <image class="w-i2" style=" width: 100px;height: 102px;" src="bmlocal://assets/perks/2.png"></image>
                    <image class="w-i4" style=" width: 80px;height: 96px;" src="bmlocal://assets/perks/4.png"></image>
                    <!--</div>-->
                </div>
            </cell>

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

            <!--<loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">-->
                <!--<image class="loading-icon" src="bmlocal://assets/loading.gif"></image>-->
            <!--</loading>-->
        </list>
    </div>
</template>
<script>
    import refresher from '../common/refresh';
    import { Utils } from 'weex-ui';
    import tab from './tab';
    import card from './card';
    import mydroptop from './myDropTop';
    import { baseUrl } from '../../../config/apis';

    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'refresher': refresher,
            tab, card, mydroptop
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
                user: '',
                page: 1,
                length: 2,
                pageSize: 10
            }
        },
        created () {
            const pageHeight = Utils.env.getScreenHeight();
            this.height = { height: (pageHeight - 48 - 112 - 128 - 96 - 112) + 'px' };
            this.$event.on('dropPrice', parmas => {
                this.getFriendsDropList()
            })
            this.$event.on('createDrop', parmas => {
                this.getMyDropList()
            })
            this.$event.on('placeOrder', parmas => {
                this.getMyDropList()
            })
            this.init()
        },
        methods: {
            init (){
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
                    this.myDropList = [...res.results]
                    this.productList = [...this.myDropList]
                    // this.totalPoints = res.totalPoints
                    // this.availablePoints = res.availablePoints
                    // this.pendingPoints = this.totalPoints - this.availablePoints
                    // // this.pArr = []
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            getFriendsDropList () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'drops.friends.list',
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.friendDropList = [...res.results]
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
                    // this.productList.splice(deleteIndex, 1);


                    this.$notice.loading.hide();
                    this.myDropList.splice(deleteIndex, 1);

                    this.$nextTick(() => {
                        // this.productList = [...this.myDropList]
                    })
                    this.$notice.toast({
                        message: res.result
                    })
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
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
        margin: 32px 32px 0 32px;
        background-color: white;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .om-img{
        width: 144px;
        height: 144px;
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
</style>
