<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <div class="overflow-box" >
            <div class="header-div">
                <text class="close" @click="$router.finish();" >&#xe6f6;</text>
                <text class="center-word"  >My Points</text>
                <text class="center-word"  >&nbsp;</text>
            </div>
            <text class="total-points">{{totalPoints||0}}</text>
            <div class="point-item" @click="openDialog">
                <text class="point-num">{{availablePoints||0}}</text>
                <text class="point-word">points available</text>
                <text class="point-num">{{pendingPoints||0}}</text>
                <text class="point-word">points pending</text>
                <text class="iconfont point-icon" >&#xe71d;</text>
            </div>
        </div>
        <div class="overflow-points-head">
                <div class="points-content">
                    <div class="points-content-head">
                        <text class="points-head-txt1">Detailed History</text>
                        <!--<text class="points-head-txt2">23000</text>-->
                    </div>
                    <div v-if="pArr.length===0" class="empty-div">

                        <image src="bmlocal://assets/empty.png" class="empty-img"></image>
                        <text class="empty-txt">You haven’t earned or spent any points yet.</text>
                    </div>
                    <div style="height: 900px"  v-if="pArr.length!==0">
                        <scroller>
                            <div class="points-content-content" v-for="(i,index) in pArr" :class="[index==pArr.length-1 ?'bottom-last':'',]">
                                <text class="points-date" :class="[index==0 ?'points-date-f':'',index==pArr.length-1 ?'points-date-f':'',]">{{tranDate(i.created)}}</text>
                                <div class="dot-line">
                                    <div class="dot" :class="[index==0 ?'dot-f':'',index==pArr.length-1 ?'dot-f':'',]"></div>
                                    <div class="line"></div>
                                </div>
                                <text class="txt" :class="[index==0 ?'txt-f':'',index==pArr.length-1 ?'txt-f':'',]" >{{i.contents}}</text>
                                <text v-if="i.operationType=='decrease'">-{{i.operationPoints}}</text>
                                <text v-if="i.operationType=='increase'">+{{i.operationPoints}}</text>
                            </div>
                        </scroller>
                    </div>


                </div>
            </div>

        <NewDialog class="wxdialog"
                   :content="content"
                   :show="show"
                   :single="false"
                   :is-checked="isChecked"
                   @wxcDialogCancelBtnClicked="wxcDialogCancelBtnClicked"
                   @wxcDialogConfirmBtnClicked="wxcDialogConfirmBtnClicked"
                   @wxcDialogNoPromptClicked="wxcDialogNoPromptClicked">
        </NewDialog>
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
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            openDialog () {
                this.show = true;
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
                return dayjs(new Date(str)).format('MMMM DD, YYYY')
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
        height: 446px;
        margin-top: 48px;
        border-bottom-left-radius: 100%;
        border-bottom-right-radius: 100%;
        /*border-radius: 100%;*/
    }
    .header-div{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .close{
        font-family: iconfont;
        font-size: 32px;
        color: white;
        padding-top: 46px;
        padding-left: 32px;
        padding-right: 32px;
        margin-right: 220px;
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
    .point-item{
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .point-num{
        font-weight: 700;
        font-size: 24px;
        color: white;
        margin-left: 12px;
    }
    .point-word{
        font-size: 24px;
        color: white;
        margin-left: 12px;
    }
    .point-icon{
        font-size: 24px;
        color: white;
        margin-left: 12px;
        margin-top: 4px;
    }

    .overflow-points-head{
        flex-direction: row;
        justify-content: center;
        margin-top: -105px;
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
        padding-left:32px;
        font-weight: 700;
        font-size: 28px;
    }
    .points-content{
        width: 686px;
        box-shadow: 0 1px 6px 0 rgba(0,0,0,0.12);
        background-color: white;
        border-radius: 8px;
        margin-top: 0px;
    }
    .points-content-head{
        flex-direction: row;
        justify-content: space-between;
        align-items:center ;
        height: 98px;
        border-radius: 8px;
    }
    .points-content-content{
        flex-direction: row;
        justify-content: flex-start;
        height: 68px;
    }
    .points-date{
        width: 100px;
        color: rgba(0,0,0,0.54);
        font-size: 20px;
        text-align: right;
        margin-right: 20px;
        margin-left: 16px;
    }
    .points-date-f{
        width: 100px;
        color: rgba(0,0,0,0.54);
        font-size: 20px;
        text-align: right;
        margin-right: 16.5px;
        margin-left: 16px;
    }
    .dot-line{
        flex-direction: column;
        align-items: center;
        margin-right: 32px;
    }
    .dot-f{
        width: 14px;
        height:14px;
        /*margin-left: -1px;*/
        border-radius: 14px;
        background-color: #EF8A31;
    }
    .dot{
        width: 6px;
        height:6px;
        /*margin-right: 12px;*/
        border-radius: 14px;
        background-color: #EF8A31;
    }
    .line{
        width: 2px;
        height: 70px;
        /*margin-left: 12px;*/
        background-color: rgba(0,0,0,0.12);
    }
    .line-l{
        width: 2px;
        height: 60px;
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
