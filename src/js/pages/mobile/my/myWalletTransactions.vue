<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header title="Wallet Transactions" leftBtn="icon"  ref="ref1" ></topic-header>
        <div class="content">
            <div class="title">
                <text class="t-t1">Available Balance </text>
                <text class="t-t2"> ₹ 1400</text>
            </div>
            <div class="tab" >
                <div :class="[flag==i?'tab-item-active':'tab-item']" v-for="(i,index) in tab" @click="selTab(i)" v-if="">
                    <text :class="[flag==i?'tt-icon-active':'tt-icon']"class="iconfont " v-if="index==1">&#xe766;</text>
                    <text :class="[flag==i?'tt-icon1-active':'tt-icon1']" class="iconfont" v-if="index==2">&#xe765;</text>
                    <text :class="[flag==i?'tt-t1-active':'tt-t1']" v-if="index==0">{{i}}</text>
                    <text :class="[flag==i?'tt-t2-active':'tt-t2']" v-else>{{i}}</text>
                </div>
            </div>

            <div class="overflow-points-head">
                <div class="points-content">
                    <div v-if="pArr.length===0" class="empty-div">
                        <image src="bmlocal://assets/empty.png" class="empty-img"></image>
                        <text class="empty-txt">You haven’t earned or spent any points yet.</text>
                    </div>

                    <div style="height: 900px;margin-top: 32px"  v-if="pArr.length!==0">
                        <scroller>
                            <div class="points-content-content" v-for="(i,index) in pArr" :class="[index==pArr.length-1 ?'bottom-last':'',]">
                                <text class="points-date" :class="[index==0 ?'points-date-f':'',index==pArr.length-1 ?'points-date-f':'',]">&nbsp;</text>

                                <div class="dot-line">
                                    <div class="line-top" v-if="index!==0"></div>
                                    <div class="dot" :class="[index==0 ?'dot-f':'',index==pArr.length-1 ?'dot-f':'',]"></div>
                                    <div class="line-bottom"></div>
                                </div>
                                <div :class="[index==0 ?'pc-item-f':'pc-item']">
                                    <text class="pci-time1">{{tranDate(i.created)}}</text>
                                    <div class="pci-item">
                                        <div class="pci-right">
                                            <text class="iconfont pci-icon">&#xe764;</text>
                                            <div>
                                                <text class="pci-t1">Reward Bonus Expired</text>
                                                <text class="pci-t2">Expired on 03 Dec 2018</text>
                                            </div>
                                        </div>

                                        <text style="color: #b4282d">- ₹400</text>
                                    </div>

                                </div>
                            </div>
                        </scroller>
                    </div>


                </div>
            </div>

        </div>
    </div>

</template>


<script>
    import header from './header';
    import refresher from '../common/refresh';
    import dayjs from 'dayjs';

    export default {
        components: {
            'topic-header': header,
            'refresher': refresher
        },
        data () {
            return {
                tab: ['All', 'Cash', 'Bonus'],
                pArr: false,
                flag: 'All'
            }
        },
        created () {
            this.getPoints()
        },
        methods: {
            getPoints () {
                this.$notice.loading.show();
                this.$fetch({
                    method: 'GET',
                    name: 'point.detail', // 通过get 获取我自己的积分
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    this.pArr = res.records
                    // this.pArr = []
                    this.$notice.loading.hide();
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            selTab (i) {
               // this.$notice.alert({
               //     message: i
               // })
                this.flag = i
            },
            tranDate (str) {
                return dayjs(new Date(str)).format('DD MMM YYYY')
            }
        }
    }
</script>

<style scoped>
    .iconfont{
        font-family: iconfont;
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
        margin-top: 160px;
    }
    .title{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        margin: 32px 32px 26px 40px;
    }
    .t-t1{
        font-size: 24px;
        color: rgba(0,0,0,0.54);
    }
    .t-t2{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
    }
    .tab{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-left: 40px;
    }
    .tab-item{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 32px;
        background-color:#fff ;
        margin-right: 16px;
    }
    .tab-item-active{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 32px;
        background-color:#EF8A31 ;
        margin-right: 16px;
    }
    .tt-t1{
        font-size: 24px;
        color: #000000;
        font-weight: 700;
        margin: 18px 38px;
    }
    .tt-t2{
        font-size: 24px;
        color: #000000;
        margin-right: 28px;
    }
    .tt-t1-active{
        font-size: 24px;
        color: white;
        font-weight: 700;
        margin: 18px 38px;
    }
    .tt-t2-active{
        font-size: 24px;
        color: white;
        margin-right: 28px;
    }
    .tt-icon{
        color: #fdaf04;
        font-size: 36px;
        margin: 12px 8px 12px 28px;
    }
    .tt-icon1{
        font-size: 36px;
        color: #00CFE3;
        margin: 12px 8px 12px 28px;
    }
    .tt-icon-active{
        color: white;
        font-size: 36px;
        margin: 12px 8px 12px 28px;
    }
    .tt-icon1-active{
        font-size: 36px;
        color: white;
        margin: 12px 8px 12px 28px;
    }

    .overflow-points-head{
        flex-direction: row;
        justify-content: center;
        margin-top: 24px;
    }
    .points-head{
        width: 200px;
        height: 96px;
        margin-top: 178px;
        flex-direction: column;
        justify-content: center;
        align-items:center ;
        border-radius: 8px;
        box-shadow: 0 1px 6px 0 rgba(0,0,0,0.12);
        background-color: white;
    }
    .points-head-txt1{
        padding-left:38px;
        font-weight: 700;
        font-size: 28px;
    }
    .points-head-txt2{
        padding-right:32px;
        font-size: 24px;
        color: #1D7CD4;
        padding-top: 32px;
        padding-bottom: 40px;
    }
    .points-content{
        width: 686px;
        box-shadow: 0 1px 6px 0 rgba(0,0,0,0.12);
        background-color: white;
        border-radius: 16px;
        /*padding-bottom: ;*/
    }
    .points-content-head{
        flex-direction: row;
        justify-content: space-between;
        align-items:center ;
        /*height: 98px;*/

        border-radius: 8px;
    }
    .points-content-content{
        flex-direction: row;
        justify-content: flex-start;
        /*height: 68px;*/

    }
    .points-date{
        width: 10px;
        color: rgba(0,0,0,0.54);
        font-size: 20px;
        text-align: right;
        margin-right: 20px;
        margin-left: 16px;
    }
    .points-date-f{
        width: 10px;
        color: rgba(0,0,0,0.54);
        font-size: 20px;
        text-align: right;
        margin-right: 16.5px;
        margin-left: 16px;
    }
    .dot-line{
        flex-direction: column;
        align-items: center;
        /*margin-right: 32px;*/
    }
    .dot-f{
        width: 14px;
        height:14px;
        /*margin-left: -1px;*/
        border-radius: 14px;
        background-color: #EF8A31;
    }
    .dot{
        width: 8px;
        height:8px;
        /*margin-right: 12px;*/
        border-radius: 14px;
        background-color: #EF8A31;
    }
    .line-top{
        width: 2px;
        height: 6px;
        margin-left: 1px;
        background-color: rgba(0,0,0,0.12);
    }
    .line-bottom{
        width: 2px;
        height: 134px;
        margin-left: 1px;
        background-color: rgba(0,0,0,0.12);
    }
    .line-l{
        width: 2px;
        height: 140px;
        background-color: rgba(0,0,0,0.12);
    }
    .txt-f{
        width: 330px;
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        margin-right: 70px;
    }
    .txt{
        width: 330px;
        height: 100px;
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        margin-right: 75px;
        /*line-height: 28px;*/
    }
    .bottom-last{
        padding-bottom: 20px;
        margin-bottom: 60px;
    }
    .pc-item-f{
        flex-direction: column;
        justify-content: start;
        margin-top: -4px;
    }
    .pc-item{
        flex-direction: column;
        justify-content: start;
        height: 150px;
    }
    .pci-icon{
        color:#A975D9;
        font-size: 48px;
        margin-right: 24px;
    }
    .pci-time1{
        font-size: 20px;
        color: rgba(0,0,0,0.54);
        margin-left: 10px;
        margin-bottom: 16px;
    }
    .pci-item{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        width: 580px;
        margin-left: 20px;
    }
    .pci-right{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .pci-t1{
        font-size: 28px;
        font-weight: 700;
        color: #000000;
        margin-top: 14px;
        margin-bottom: 16px;
    }
    .pci-t2{
        font-size: 20px;
        color: rgba(0,0,0,.54);
    }
    .empty-div{
        flex-direction: column;
        justify-content:flex-start;
        align-items: center;
        height: 300px;
        /*background-color: black;*/
    }
    .empty-img{
        width: 200px;
        height: 200px;
    }
    .empty-txt{
        opacity: 0.54;
        font-family: ProximaNova-Bold;
        font-size: 24px;
        color: #000000;
    }
</style>
