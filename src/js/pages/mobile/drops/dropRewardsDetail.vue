<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <div class="header">
            <text class="close iconfont" @click="$router.back();" >&#xe6f6;</text>
            <text class="header-word" >{{'Reward Details'}}</text>
            <text class=" iconfont" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</text>
        </div>
        <div class="top-word">
            <text class="tw-w1">Total Rewards  </text>
            <text class="tw-w2">₹{{parseInt(totalNum)}}</text>
        </div>

        <div class="overflow-points-head">
            <div class="points-content">
                <!--<div class="points-content-head">-->
                    <!--<text class="points-head-txt1">&nbsp;&nbsp;&nbsp;</text>-->
                <!--</div>-->
                <div v-if="pArr.length===0" class="empty-div">

                    <image src="bmlocal://assets/empty.png" class="empty-img"></image>
                    <text class="empty-txt">You haven’t earned or spent any points yet.</text>
                </div>
                <div :style="height" class="mg-t32"  v-if="pArr.length!==0">
                    <list offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
                        <cell style="width: 750px"  v-for="(i,index) in pArr">
                            <div class="points-content-content" :class="[index==pArr.length-1 ?'bottom-last':'',]">
                                <div class="dot-line" :class="[index==0 ?'dot-line-f':'',index==pArr.length-1 ?'dot-line-f':'',]" >
                                    <div class="dot" :class="[index==0 ?'dot-f':'',index==pArr.length-1 ?'dot-f':'',]"></div>
                                    <div class="line"></div>
                                </div>
                                <div class="" >
                                    <text class="points-date" :class="[index==0 ?'points-date-f':'',index==pArr.length-1 ?'points-date-f':'',]">{{tranDate(i.created)}}</text>
                                    <text class="txt"  v-if="i.type == 'bonus earn'">₹{{i.operationAmount}}  Reward Available to Use Now</text>
                                    <text class="txt"  v-if="i.type == 'bonus expired'">₹{{i.operationAmount}}  Reward Was Cancelled</text>
                                    <text class="txt"  v-if="i.type == 'bonus pending'">₹{{i.operationAmount}}  Reward Pending to Release</text>

                                    <text class="txt1" v-if="i.type == 'bonus earn'">Congrats! Your reward is now available to use now. It will expire at March 15!</text>
                                    <text class="txt1" v-if="i.type == 'bonus expired'">Sorry! Your reward has been cancelled due to order cancellation of your friend.</text>
                                    <text class="txt1" v-if="i.type == 'bonus pending'">Your pending reward will become available once the Drop’s order is delivered</text>
                                    <div class="item">
                                        <text class="pc-txt1">Drop’s Owner : </text>
                                        <div class="pc-avatar">
                                            <image style="width:40px;height: 40px;" :src="i.avatar"></image>
                                        </div>
                                        <text class="pc-txt2">{{i.lastName}}</text>
                                    </div>
                                </div>
                            </div>
                        </cell>
                        <cell class="loading" v-if="isLoading">
                            <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
                        </cell>
                    </list>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import dayjs from 'dayjs';
    import { Utils } from 'weex-ui';
    export default {
        name: "dropRewardsDetail",
        data(){
            return {
                pArr: false,
                isLoading: false,
                page: 1,
                length: 2,
                pageSize: 5,
                totalNum: 5,
                height: '900px'
            }
        },
        eros: {
            appeared (params, options) {
                this.totalNum = params.totalNum
            }
        },
        created (){
            this.getList(true)
            const pageHeight = Utils.env.getScreenHeight();
            this.height = { height: (pageHeight - 132 - 246) + 'px' };
            this.$notice.loading.show();
            // this.$notice.alert({
            //     message: this.height
            // })
        },
        methods:{
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true
                    this.requestProduct(false)
                }
            },
            requestProduct (isFirst) {
                if (isFirst) {
                    this.page = 1;
                }
                if (this.page > this.length) {
                    this.$nextTick(() => {
                        this.isLoading = false
                    })
                    return
                }
                this.getList(isFirst);
            },
            getList (isFirst) {
                this.$fetch({
                    method: 'GET',
                    name: 'point.bonus.record.list',
                    data: {
                        page: this.page,
                        page_size: this.pageSize,
                        status: 'drop'
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    if (res.count == 0) {
                        this.length = 2;
                    } else {
                        this.length = Math.ceil(res.count / this.pageSize);
                    }

                    if (isFirst) {
                        this.pArr = [];
                    }
                    this.pArr.push(...res.results);


                    this.page++;
                    this.isLoading = false;
                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            tranDate (str) {
                return dayjs(new Date(str)).format('DD MMM YYYY')
            }
        }
    }
</script>

<style scoped>
    .wrapper {
        background-color: #f4f4f4;
    }
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
    .header{
        background-color: white;
        height: 112px;
        margin-top: 48px;
        /*margin-bottom: 32px;*/
        box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
    }
    .close{
        padding-left: 32px;
    }
    .header-word{
        font-size: 32px;
        font-weight: 700;
        /*width: 474px;*/
        color: black;
        text-align: center;
        line-height: 64px;
        /*margin-left:202px;*/
    }
    .top-word{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-left: 38px;
        margin-top: 32px;
        margin-bottom: 32px;
    }
    .tw-w1{
        font-size: 24px;
        color: rgba(0,0,0,0.54);
    }
    .tw-w2{
        font-size: 28px;
        color: #000000;
        font-weight: 700;
    }
    .overflow-points-head{
        flex-direction: row;
        justify-content: center;
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
        /*height: 68px;*/
    }
    .points-date{
        color: rgba(0,0,0,0.54);
        font-size: 20px;
        text-align: left;
        margin-top: -4px;
    }
    .points-date-f{
        color: rgba(0,0,0,0.54);
        font-size: 20px;
        text-align: left;
    }
    .dot-line{
        flex-direction: column;
        align-items: center;
        margin-right: 8px;
        margin-left: 40px;
    }
    .dot-line-f{
        margin-right: 4px;
        margin-left: 36px;
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
        height: 220px;
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
        font-size: 28px;
        color: #000000;
        font-weight: 700;
        margin-top: 14px;
    }
    .txt1{
        width: 570px;
        font-size: 20px;
        color: rgba(0,0,0,.54);
        margin-top: 14px;
    }
    .item{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        margin-top: 16px;
        margin-bottom: 40px;
    }
    .pc-txt1{
        font-size: 20px;
        color: #000000;
        font-weight: 700;
    }
    .pc-avatar{
        margin-left: 16px;
        margin-right: 12px;
        border-radius: 50%
    }
    .pc-txt2{
        font-size: 20px;
        color: #000000;
    }
    .mg-t32{
        margin-top: 32px;
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
</style>
