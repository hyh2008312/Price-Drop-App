<template>
    <div class="wrapper">
        <scroller >
            <div class="blackheader"></div>
            <topic-header title="My Ponits" leftBtn="icon"  ref="ref1" ></topic-header>
            <div class="overflow-points-head">
                <div class="points-head">
                    <text class="points-head-txt1">Points Balance</text>
                    <text class="points-head-txt2">{{points}}</text>
                </div>
            </div>
            <div class="overflow-points-head">
                <div class="points-content">
                    <div class="points-content-head">
                        <text class="points-head-txt1">Detailed History</text>
                        <!--<text class="points-head-txt2">23000</text>-->
                    </div>

                    <div class="points-content-content">
                        <text class="points-date">July 17th 2018</text>
                        <div class="dot-line">
                            <div class="dot"></div>
                                <div class="line"></div>
                            </div>
                        <text class="txt">Dropped the price for Steven’s purchase</text>
                        <text >+100</text>
                    </div>


                </div>
            </div>
        </scroller>
    </div>
</template>

<script>
    import header from './header';
    export default {
        components: {
            'topic-header': header
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
                    this.$notice.alert({
                        message: res
                    })
                    this.pArr = res.records
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            tranDate () {
                const no = new Date
                Date.UTC()
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
        width: 688px;
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
        font-size: 28px;
    }
    .points-content{
        width: 688px;
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
    .dot-line{
        flex-direction: column;
        align-items: center;
        margin-right: 32px;
    }
    .dot{
        width: 14px;
        height:14px;
        border-radius: 14px;
        background-color: #EF8A31;
    }
    .line{
        width: 2px;
        height: 40px;
        background-color: rgba(0,0,0,0.12);
    }
    .txt{
        width: 330px;
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        margin-right: 80px;
        /*line-height: 28px;*/
    }
</style>
