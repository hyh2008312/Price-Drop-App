<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <div class="header-bar">
            <div class="header-search" @click="jumpSearch">
                <text class="header-icon iconfont">&#xe621;</text>
                <text class="header-title">What are you looking for?</text>
            </div>
            <div class="box-bg"   @click="openCart">
                <text class="box-txt-icon iconfont">&#xe767;</text>
                <text class="box-dot" v-if="cartNum>0">{{cartNum > 99? '99+': cartNum}}</text>
            </div>

        </div>

        <div class="overflow" :style="{'height': pageHeight}" v-if="hasWifi">
            <div style="width: 183px">
                <list class="tag-list">
                    <cell v-for="(i,index) in tagList" @click="selTag(index, i)">
                        <div :class="[flag==index?'tag-div-active':'tag-div']">
                            <text :class="[(i.name.indexOf('&') != -1&&i.name.length>=13)?'tag-line':'tag']">{{tranString(i.name)}}</text>
                        </div>
                    </cell>
                </list>
            </div>
            <list style="background-color: white;" :style="{'height': pageHeight}">
                <cell  v-for="(i,index) in SList">
                    <text :class="[i.name.trim()!==''?'s-headline':'s-headlines']" >{{i.name}}</text>
                    <div class="i-row" v-for="(item, index) in tranArr(i.subCat)">
                        <div class="i-col" v-for="(n,index) in item" @click="jumpCategory(n)">
                            <image :src="n.image" style="width:112px;height: 112px " resize="contain"></image>
                            <text class="i-text">{{tranString(n.name)}}</text>
                        </div>
                    </div>
                </cell>
            </list>
        </div>
        <no-wifi v-if="!hasWifi" @onReload="initPage"></no-wifi>
    </div>
</template>

<script>
    import { Utils } from 'weex-ui';
    import { baseUrl } from '../../../config/apis';
    import noWifi from '../common/noWifi';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    export default {
        components: {
            noWifi
        },
        data () {
            return {
                // tagList: ['Electronics', 'Men', 'Women', 'Shoes', 'Bags', 'Home', 'Beauty', 'Kids', 'Bags', 'Home', 'Beauty', 'Kids', 'Bags', 'Home', 'Beauty', 'Kids'],
                tagList: [],
                SList: [],
                flag: '',
                cartNum: '',
                pageHeight: '',
                hasWifi: true,
                activeCategory: ''
            }
        },
        created () {
            this.initPage()
            this.$event.on('login', params => {
                this.getUnread();
            });
            this.pageHeight = Utils.env.getScreenHeight() - 277 + 'px';
            googleAnalytics.trackingScreen('Categories');
        },
        methods: {
            initPage () {
                this.getList()
                this.getUnread()
            },
            selTag (index, i) {
                this.flag = index;
                this.SList = [...i.subCat]
                this.activeCategory = i.name
                googleAnalytics.recordEvent('Categories', `level1-${i.name}`, 0, 0);
            },
            getList () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'directory.app.category.list',
                    data: {}
                }).then((res) => {
                    this.tagList = [...res]
                    this.SList = [...this.tagList[0].subCat]
                    this.$notice.loading.hide();
                    this.hasWifi = true
                }).catch((res) => {
                    if (res.status == 10) {
                        this.hasWifi = false;
                    }
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            getUnread () {
                this.$storage.get('user').then((data) => {
                    if (data) {
                        this.getCartNumApi(data.id);
                    }
                });
            },
            getCartNumApi (id) {
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/cart/count/${id}/`,
                    header: {
                        isLoginPop: true,
                        needAuth: true
                    }
                }).then(data => {
                    this.cartNum = data.count;
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
            jumpCategory (item) {
                if (!item.id) return;
                googleAnalytics.recordEvent('Categories', `level1-${this.activeCategory}`, `level2-${item.name}`, 0, 0);
                this.$router.open({
                    name: 'goods.category',
                    type: 'PUSH',
                    params: {
                        id: item.categoryId,
                        name: item.name
                    }
                })
            },
            jumpSearch () {
                this.$router.open({
                    name: 'search',
                    type: 'PUSH'
                });
            },
            tranString (s) {
                // this.$notice.toast({
                //     message: ' Accessories'.length
                // })
                if (s.indexOf('&') != -1 && s.length >= 13) {
                     // str2 = s.replace("\\n", " \n ");
                    // s.substring(0, s.indexOf('&'))
                    return s.replace(' & ', '\n')
                } else {
                    if (s.length >= 18) {
                        return s.replace(' ', '\n')
                    } else {
                        return s
                    }
                }
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
                    if ((i > 0 && i % 3 == 2) || i == data.length - 1) {
                        rArr.push(arr);
                        arr = [];
                    }
                }
                // this.$notice.alert({
                //     message: rArr[0]
                // })
                return rArr //  4个一个的二维数组
                // } else {
                //
                //     return data
                // }
            }
        }
    }
</script>

<style scoped>
    .wrapper{
        background-color: white;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .iconfont{
        font-family: iconfont;
    }
    .header-bar{
        /*padding:18px  32px  14px 32px;*/
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
    }
    .header-search{
        margin: 66px 32px 14px 32px;
        width: 620px;
        height: 80px;
        border-radius: 40px;
        /*background-color: black;*/
        background-color: rgba(0,0,0,0.12);
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }

    .header-title{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 32px;
        color: rgba(0,0,0,0.87);
        opacity: 0.54;
        letter-spacing: 0;
    }

    .header-icon{
        font-size: 32px;
        color: rgba(0,0,0,.54);
        font-weight: 700;
        margin-left: 16px;
        margin-right: 16px;
    }
    .cart-icon{
        color: red;
    }
    .box-bg{
        position: absolute;
        right: 24px;
        top: 76px;
        width: 60px;
        height: 60px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .box-txt-icon {
        width: 40px;
        height: 40px;
        color: #000;
        font-size: 40px;
        line-height: 40px;
        text-align: center;
    }

    .box-txt-icon-new {
        width: 40px;
        height: 40px;
        color: rgba(239,138,49,0.54);
        font-size: 40px;
        line-height: 40px;
        text-align: center;
    }

    .box-dot{
        position: absolute;
        right: 0;
        top: 0;
        background-color: red;
        font-size: 16px;
        line-height: 24px;
        width: 24px;
        height: 24px;
        text-align: center;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        color: white;
        border-radius: 50%;
    }
    .overflow{
        flex-direction: row;
        justify-content: start;
        align-items: start;
        width: 750px;
        margin-top: 4px;
        /*background-color: black;*/
    }
    .tag-list{
        width: 183px;
        background-color: #ECECEC;
    }
    .tag-div{
        height: 96px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        /*background-color: white;*/
    }
    .tag-div-active{
        height: 96px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        background-color: white;
    }
    .tag{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        text-align: center;
    }
    .tag-line{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        line-height: 30px;
        text-align: center;
    }
    .s-headline{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        margin-top: 40px;
        margin-left: 34px;
        margin-bottom: 48px;
    }
    .s-headlines{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        /*margin-top: 20px;*/
        margin-left: 34px;
        margin-bottom: 24px;
    }
    .i-row{
        flex-direction: row;
        justify-content: start;
        align-items: start;
        margin-left: 40px;
    }
    .i-col{
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
        margin-right: 48px;
        margin-bottom: 48px;
        width: 125px;
    }
    .i-text{
        font-size: 20px;
        color: #000000;
        text-align: center;
        margin-top: 16px;
    }
</style>
