<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header title="My Coupons" leftBtn="icon"  ref="ref1" ></topic-header>
        <div class="overflow-points-head">
            <div class="points-content">
                <div class="points-content-head">
                    <text class="points-head-txt1">Detailed History</text>
                    <!--<text class="points-head-txt2">23000</text>-->
                </div>
                <div v-if="pArr.length===0" class="empty-div">

                    <image src="bmlocal://assets/empty.png" class="empty-img"></image>
                    <text class="empty-txt">You haven’t enarned or spent any coupons yet.</text>
                </div>
                <div style="height: 800px"  v-if="pArr.length!==0">
                    <scroller>


                    </scroller>
                </div>


            </div>
        </div>
    </div>
</template>

<script>
    import header from './header';
    import dayjs from 'dayjs';
    export default {
        components: {
            'topic-header': header
        },
        name: 'myCard',
        eros: {
            appeared (params, options) {
            }
        },
        data () {
            return {
                points: '',
                pArr: []
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
                    // this.pArr = res.records
                    // this.pArr = []
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
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
    .overflow-points-head{
        flex-direction: row;
        justify-content: center;
    }
    .points-head{
        width: 712px;
        height: 96px;
        margin-top: 178px;
        flex-direction: row;
        justify-content: space-between;
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
    .points-head-txt2{
        padding-right:32px;
        color: #EF8A31;
        font-weight: 700;
        font-size: 28px;
    }
    .points-content{
        width: 712px;
        box-shadow: 0 1px 6px 0 rgba(0,0,0,0.12);
        background-color: white;
        border-radius: 8px;
        margin-top: 19px;
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
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        margin-right: 75px;
        /*line-height: 28px;*/
    }
    .bottom-last{
        padding-bottom: 20px;
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
