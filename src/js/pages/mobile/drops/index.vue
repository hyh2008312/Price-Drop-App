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
                    <text class="om-txt1">Uh oh! Your friends haven’t started a Drop yet.</text>
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
                    <somegoods :goods="i"  :type="1" v-on:pop="openPopup($event)"></somegoods>  <!-- 1：一列 2：两列 -->
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
        <wxc-popup :have-overlay="true"
                   popup-color="rgb(255, 255, 255)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupCloseClick"
                   pos="bottom"
                   ref="wxcPopup"
                   height="870">
            <div class="popup-content">
                <div class="popup-top">
                    <div class="popup-title">
                        <text class="pt-1">Please select your preference first!</text>
                        <text class="popup-close" @click="popupCloseClick">&#xe632;</text>

                    </div>
                    <div class="popup-goods">
                        <div style="border-radius: 8px">
                            <image :src="singleGoods.mainImage" class="popup-image"></image>
                        </div>
                        <div class="popup-py">
                            <text class="pt-title">{{singleGoods.title}}</text>
                            <div class="pt-price">
                                <text class="popup-yet" v-if="selcolor != ''">{{selcolor}}</text>
                                <text class="popup-yet" v-if="selsize != ''">{{selsize}}</text>
                            </div>
                        </div>
                    </div>
                </div>

                <scroller  class="scroller" show-scrollbar="true" >
                    <div class="popup-bottom">
                        <div v-for="(val, index) in goodsType" :key="index" >
                            <text class="popup-color" v-if="val.name=='Color'">{{singleGoods.aliasColor || val.name}}</text>
                            <div class="popup-size-item" v-if="val.name=='Size'">
                                <text class="popup-color">{{singleGoods.aliasSize || val.name}}</text>
                                <!--<text class="popup-size-chart"   v-if="singleGoods.productSize!== '' && singleGoods.productSize != undefined">Size Chart ></text>-->
                            </div>

                            <div style="width: 718px;flex-wrap: wrap;flex-direction: row;justify-content: start;align-items: center">

                                <div  class="popup-color-chd" v-for="(val1, key1) in val.value" >
                                    <text class="popup-color-chdname"
                                          :key="key1"
                                          :class="[val1.isActive ?'popup-color-chdname-active':'',
                                          val1.seldisable ?'popup-color-chdname-disable':'']"
                                          @click="clickColor(val1, val.value)">{{val1.value}}</text>
                                </div>
                            </div>
                        </div>
                    </div>
                </scroller>
                <div class="popup-btn">
                    <text class="button-purple" @click="confirm()" v-if="canBuy==true" >Start Now</text>
                    <text class="button-gray"               v-if="canBuy==false" >Out of Stock</text>
                </div>

            </div>
        </wxc-popup>
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
    import { WxcMask, WxcPopup } from 'weex-ui';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'refresher': refresher,
            tab, card, mydroptop,somegoods,WxcMask,WxcPopup
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
                singleGoods:'',
                canBuy:true,
                hasVariants:true,
                selcolor:'',
                selsize:'',
                selimgsrc:'',
                selsaleUnitPrice:'',
                selunitPrice:'',

                goodsType:[],
                goodsVariants:[],
                tmpArray:'',
                variantsId:'',

                nextPage:{},
                dropObj:{},
                newShow:false,
                isBottomShow:false,
                user: '',
                page: 1,
                length: 2,
                pageSize: 5
            }
        },
        created () {
            googleAnalytics.trackingScreen(`Drop List/${this.activeTab}-Drop`);
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
                    //     message: res[0]
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
            confirm () {
                if (this.user == null) {
                    this.redirectLogin()
                } else {
                    if (!this.checkedSelected()) {
                        return;
                    }
                    this.createDrop()
                }
            },
            createDrop () {
                if (this.variantsId!=='') {
                    this.$notice.loading.show();
                    this.$fetch({
                        method: 'POST',
                        url: `${baseUrl}/drops/create/`,
                        data: {
                            variant_id: this.variantsId,
                        },
                        header: {
                            needAuth: true
                        }
                    }).then((res) => {
                        this.$event.emit('createDrop');
                        if (res.id) {
                            this.isBottomShow = false;
                            this.$router.open({
                                name: 'drop.detail',
                                type: 'PUSH',
                                params: {
                                    id: res.id
                                }
                            })
                            googleAnalytics.recordEvent('DropStart', 'Invite Friends to Drop Price', this.category + '-' + this.variantsId, 0);
                        }
                        this.$notice.loading.hide();
                    }).catch((res) => {
                        if (res.status == 409) {
                            this.$notice.loading.hide();
                            this.$notice.toast({
                                message: res.errorMsg
                            })
                        }
                    })
                }
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
                this.isBottomShow = false;
                this.selcolor ='';
                this.selsize ='';
                this.selimgsrc ='';
            },
            popupCloseClick () {
                // this.$refs.wxcPopup.hide();
                this.isBottomShow = false;
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
            },
            openPopup (goods) {
                this.selcolor ='';
                this.selsize ='';
                this.variantsId ='';
                this.singleGoods = goods
                this.selsaleUnitPrice = goods.saleUnitPrice
                this.goodsVariants = [...this.singleGoods.variants];
                if (this.singleGoods.attributes != null && this.singleGoods.attributes.length > 0) {
                    if (this.goodsVariants.length == 1) {
                        this.hasVariants = false;
                        this.nextPage.attributes = '';
                        this.goodsType = [];
                        this.canBuy = this.singleGoods.variants[0].isCanBuy;
                        this.variantsId = this.singleGoods.variants[0].id;
                        this.confirm()
                    } else {
                        this.goodsType = this.singleGoods.attributes;
                        this.operateData(this.singleGoods.attributes);
                        this.isBottomShow = true
                    }
                } else {
                    this.hasVariants = false;
                    this.nextPage.attributes = '';
                    this.goodsType = [];
                    this.canBuy = this.singleGoods.variants[0].isCanBuy;
                    this.variantsId = this.singleGoods.variants[0].id;
                    this.confirm()
                }
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
                const color = []; // 点选的这个 有其他的颜色或者规格
                const discolor = [];
                for (let j = 0; j < this.goodsVariants.length; j++) {
                    for (let k = 0; k < this.goodsVariants[j].attributeValues.length; k++) {
                        if (item.value == this.goodsVariants[j].attributeValues[k].value) {
                            color.push({
                                item: this.goodsVariants[j],
                                index: k
                            });
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
                                this.goodsType[p].value[u].seldisable = true
                                for (let o = 0; o < discolor.length; o++) {
                                    if (this.goodsType[p].value[u].value == discolor[o]) {
                                        this.goodsType[p].value[u].seldisable = false
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
                if ((this.selsize == '') || (this.selcolor == '')) {
                    this.canBuy = true
                    this.variantsId = ''
                }
                let tmp = []
                for (let i = 0; i < this.goodsType.length; i++) {
                    if (this.goodsType[i].name == 'Color') {
                        tmp = this.goodsType[i].images
                    }
                }
                for (let j = 0; j < tmp.length; j++) {
                    if (tmp[j].value == this.selcolor) {
                        this.selimgsrc = tmp[j].image
                    }
                }
                this.nextPage.attributes = this.selcolor + ' ' + this.selsize;
                this.nextPage.mainImage = this.selimgsrc;

                this.tmpArray = [];
                for (let i = 0; i < this.goodsType.length; i++) {
                    for (let j = 0; j < this.goodsType[i].value.length; j++) {
                        if (this.goodsType[i].value[j].isActive == true) {
                            this.tmpArray.push(this.goodsType[i].value[j])
                            break;
                        }
                    }
                }
                for (let i = 0; i < this.goodsVariants.length; i++) {
                    let isDoubleChecked = 0;
                    for (let j = 0; j < this.goodsVariants[i].attributeValues.length; j++) {
                        for (let m = 0; m < this.tmpArray.length; m++) {
                            if (this.tmpArray[m].id == this.goodsVariants[i].attributeValues[j].attributeId &&
                                this.tmpArray[m].value == this.goodsVariants[i].attributeValues[j].value) {
                                isDoubleChecked += 1;
                            }
                        }
                    }
                    if (isDoubleChecked == this.goodsType.length) {
                        this.variantsId = this.goodsVariants[i].id;
                        this.canBuy = this.goodsVariants[i].isCanBuy;
                        this.selsaleUnitPrice = this.goodsVariants[i].saleUnitPrice
                        this.selunitPrice = this.goodsVariants[i].unitPrice

                        this.nextPage.salePrice = this.selsaleUnitPrice;
                        this.nextPage.currentPrice = this.selunitPrice;
                        break;
                    }
                }
            },
            checkedSelected () {
                this.tmpArray = [];
                for (let i = 0; i < this.goodsType.length; i++) {
                    for (let j = 0; j < this.goodsType[i].value.length; j++) {
                        if (this.goodsType[i].value[j].isActive == true) {
                            this.tmpArray.push(this.goodsType[i].value[j])
                            break;
                        }
                        if (j == this.goodsType[i].value.length - 1) {
                            this.$notice.toast({
                                message: 'Please select a ' + this.goodsType[i].name.toLowerCase() + '!'
                            });
                            return false
                        }
                    }
                }
                return true;
            },
       }
    }
</script>
<style lang="sass" src="./index.scss"></style>

