<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <div class="overflow-box" >
            <div class="header-div">
                <text class="close" @click="$router.finish();" >&#xe6f6;</text>
                <!--<text class="center-word"  >My Points</text>-->
                <text class="center-word"  >Wallet Balance</text>
                <text class="center-word"  >&nbsp;</text>
            </div>
            <text class="total-points">₹{{totalPoints||0}}</text>

        </div>
        <div class="overflow-mid">
            <div class="cash-bonus">
                <div class="c-b1">
                    <text class="iconfont cb1-icon">&#xe766;</text>
                    <div class="cb1-content">
                        <div class="cb1c-item">
                            <text class="cb1c-t1">Cash</text>
                            <text class="cb1c-t2">Use Unrestricted</text>
                        </div>
                        <text class="cb1c-t3">₹1000</text>
                    </div>
                </div>

                <div class="c-b1">
                    <text class="iconfont cb1-iconc">&#xe765;</text>
                    <div class="cb1-content">
                        <div class="cb1c-item">
                            <text class="cb1c-t1">Bonus</text>
                            <text class="cb1c-t2c">Use Unrestricted</text>
                        </div>
                        <text class="cb1c-t3">₹1000</text>
                    </div>
                </div>
            </div>

        </div>
        <div class="overflow-points-head">
            <div class="points-content">
                <div class="points-content-head">
                    <text class="points-head-txt1">Wallet Transactions</text>
                    <text class="points-head-txt2" @click="openDetail">View All</text>
                </div>
                <div v-if="pArr.length===0" class="empty-div">

                    <image src="bmlocal://assets/empty.png" class="empty-img"></image>
                    <text class="empty-txt">You haven’t earned or spent any points yet.</text>
                </div>

                <div style="height: 900px"  v-if="pArr.length!==0">
                    <!--<scroller>-->
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
                    <!--</scroller>-->
                </div>


            </div>
        </div>

    </div>
</template>

<script>
    import header from './header';
    import dayjs from 'dayjs';
    import NewDialog from './newPopup';
    export default {
        components: {
            'topic-header': header,
            NewDialog
        },
        name: 'myCard',
        eros: {
            appeared (params, options) {
                if (params) {
                    if (params.num) {
                        this.points = params.num
                    }
                }
            }
        },
        data () {
            return {
                totalPoints: '',
                availablePoints: '',
                pendingPoints: '',
                pArr: false,
                show: false,
                isChecked: false,
                content: 'All “Pending" points cannot be used until they ' +
                'change to “Available" status. \n' +
                'These points will become “Available" on the ' +
                '10th' + ' day after you confirm the receipt of your ' +
                'order with no return.'
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
                    this.totalPoints = res.totalPoints
                    this.availablePoints = res.availablePoints
                    this.pendingPoints = this.totalPoints - this.availablePoints
                    // this.pArr = []
                    this.$notice.loading.hide();
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            openDialog () {
                this.show = true;
            },
            openDetail () {
                this.$router.open({
                    name: 'wallet.tran',
                    type: 'PUSH'
                })
            },
            wxcDialogCancelBtnClicked () {
                // 此处必须设置，组件为无状态组件，自己管理
                this.show = false;
            },
            wxcDialogConfirmBtnClicked () {
                this.show = false;
            },
            wxcDialogNoPromptClicked (e) {
                // 此处必须设置，组件为无状态组件，自己管理
                this.isChecked = e.isChecked;
            },
            tranDate (str) {
                return dayjs(new Date(str)).format('DD MMM YYYY')
            }
        }
    }
</script>

<style scoped>
    .wrapper{
        background-color: #f4f4f4;
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
    .overflow-box{
        background-color: #EF8A31;
        height: 398px;
        margin-top: 48px;
        border-bottom-left-radius: 100%;
        border-bottom-right-radius: 100%;
        /*border-radius: 100%;*/
    }
    .header-div{
        flex-direction: row;
        justify-content:   start;
        align-items: center;
    }
    .close{
        font-family: iconfont;
        font-size: 32px;
        color: white;
        padding-top: 46px;
        padding-left: 32px;
        padding-right: 32px;
        margin-right: 190px;
        /*background-color: black;*/
    }
    .center-word{
        font-family: ProximaNova;
        text-align: center;
        color: white;
        font-size: 32px;
        font-weight: 700;
        margin-top: 46px;
        /*background-color: black;*/
    }
    .total-points{
        font-family: ProximaNova;
        font-size: 80px;
        margin-top: 78px;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        font-weight: 700;
    }


    .overflow-points-head{
        flex-direction: row;
        justify-content: center;
        margin-top: 24px;
    }
    .overflow-mid{
        flex-direction: row;
        justify-content: center;
        margin-top: -105px;
    }
    .cash-bonus{
        width: 686px;
        height: 240px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        background-color: white;
        border-radius: 16px;
        margin-top: 0;
    }
    .c-b1{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        height: 122px;
        /*background-color: black;*/
    }
    .cb1-icon{
        font-size: 52px;
        color: #FDAF04;
        margin: 36px 24px 24px 32px;
    }
    .cb1-content{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        width: 547px;
        height: 122px;
        border-bottom-color: rgba(0,0,0,.08);
        border-bottom-style: solid;
        border-bottom-width:2px ;
    }
    .cb1c-item{
        flex-direction: column;
        align-items: start;
        justify-content: center;
    }
    .cb1c-t1{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
        /*margin-top: 24px;*/
        margin-bottom: 5px;
    }
    .cb1c-t2{
        font-size: 20px;
        color: #5572FA;
        padding: 2px 16px;
        border-color:#5572FA;
        border-style: solid;
        border-width: 1px;
        border-radius: 50%;
        margin-top: 5px;
        /*margin-bottom: 18px;*/
    }
    .cb1c-t3{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
    }
    .cb1-iconc{
        font-size: 52px;
        color: #00D1E5 ;
        margin: 12px 24px 24px 32px;
    }
    .cb1c-t2c{
        font-size: 20px;
        color: rgba(0,0,0,.38);
        padding: 2px 16px;
        border-color:rgba(0,0,0,.38);
        border-style: solid;
        border-width: 1px;
        border-radius: 50%;
        margin-top: 5px;
        /*margin-bottom: 18px;*/
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
        margin-top: 0px;
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
