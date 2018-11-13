<template>
    <div>
        <div class="flash-sales" v-if="fstatus== 'Ongoing'">  <!-- 正在进行的-->
            <!--<text class="flash-sales-word">Flash Sales</text> <text class="flash-sales-time">12:00:00</text>-->
            <div class="flash-sales-left">
                <text class="flash-icon" >&#xe745;</text>
                <text class="flash-sales-word">₹{{parseInt(calc(unitPrice,discount))}}</text>
                <text class="flash-sales-word1">₹{{parseInt(saleUnitPrice)}}</text>
            </div>
            <image class="flash-triangle" src="bmlocal://assets/right-01.png"></image>
            <div class="flash-sales-right">
                <text class="flash-sales-tword">Ends In</text>

                <div class="flash-sales-time" v-if="second!=''">
                    <text class="flash-sales-hh">{{hour}}</text>
                    <text style="color: #EF8A31;font-size: 24px; padding-top: 10px;">:</text>
                    <text class="flash-sales-hh">{{min}}</text>
                    <text style="color: #EF8A31;font-size: 24px; padding-top: 10px;">:</text>
                    <text class="flash-sales-hh" >{{second}}</text>
                </div>

                <div class="flash-sales-time" v-if="second==''">
                    <text class="flash-sales-hh-empty"></text>
                    <text style="color: #EF8A31;font-size: 24px; padding-top: 10px;">:</text>
                    <text class="flash-sales-hh-empty"></text>
                    <text style="color: #EF8A31;font-size: 24px; padding-top: 10px;">:</text>
                    <text class="flash-sales-hh-empty" ></text>
                </div>


            </div>
        </div>

        <div class="flash-saless" v-if="fstatus== 'Scheduled'"> <!-- 还未开始的-->
            <div class="flash-sales-lefts">
                <text class="flash-icon" >&#xe745;</text>
                <text class="flash-sales-words">₹{{calc(unitPrice,discount)}}</text>
                <!--<text class="flash-sales-words">₹{{unitPrice}}-{{discount}}</text>-->
            </div>

            <div class="flash-sales-rights">
                <text class="flash-sales-twords">Start In</text>

                <div class="flash-sales-times" v-if="second!=''">
                    <text class="flash-sales-hhs">{{hour}}</text>
                    <text style="color: white;font-size: 24px;">:</text>
                    <text class="flash-sales-hhs">{{min}}</text>
                    <text style="color: white;font-size: 24px; ">:</text>
                    <text class="flash-sales-hhs" >{{second}}</text>
                </div>

                <div class="flash-sales-times" v-if="second==''">
                    <text class="flash-sales-hhs-empty"></text>
                    <text style="color: white;font-size: 24px;">:</text>
                    <text class="flash-sales-hhs-empty"></text>
                    <text style="color: white;font-size: 24px; ">:</text>
                    <text class="flash-sales-hhs-empty" ></text>
                </div>


            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: ['hour', 'min', 'second', 'fstatus', 'saleUnitPrice', 'unitPrice', 'discount'],
        data () {
            return {
                // all: '',
                // value: '',
                // len: '',
                // pstyle: {
                //     width:
                // }
            }
        },
        methods: {
            calc (a, b) {
                // this.$notice.toast({
                //     message: a
                // })
                return parseInt((a * b) / 100)
                // return (('0.00' * 100) / 100).toFixed(2)
            }
        },
        name: 'flash-item'

    }
</script>

<style scoped>
    .flash-sales{
        flex-direction: row;
        justify-content: space-around;
        background-color: #FFEACF;
        /*padding: 30px 0;*/
        height: 80px;
    }
    .flash-sales-left{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #EF8A31;
        width: 360px;
    }
    .flash-sales-right{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #FFEACF;
        width: 310px;
    }

    .flash-triangle{
        width: 40px;
        height: 85px;
        margin-right: 40px;
    }
    .flash-icon{
        font-family: iconfont;
        margin-left: 32px;
        margin-top: 5px;
        font-size: 28px;
        color: white;
    }
    .flash-sales-word{
        margin-right: 14px;
        font-size: 32px;
        color: #FFFFFF;
        font-weight: 700;
    }
    .flash-sales-word1{
        font-size: 24px;
        color: #FFFFFF;
        padding-right:50px ;
        text-decoration: line-through;
    }
    .flash-sales-tword{
        color:#EF8A31;
        width: 125px;
        font-style: italic;
        /*margin-right: 18px;*/
        font-size: 32px;

    }
    .flash-sales-time{
        font-size: 28px;
        color: #FFFFFF;
        flex-direction: row;
        justify-content: start;
        margin-right: 32px;
    }
    .flash-sales-hh{
        font-family: ProximaNova;
        background-color: #EF8A31;
        color: white;
        border-radius: 6px;
        padding: 5px;
        margin: 5px;
        font-size: 28px;
    }
    .flash-sales-hh-empty {
        font-family: ProximaNova;
        background-color: #EF8A31;
        color: white;
        border-radius: 6px;
        /*padding: 5px;*/
        margin: 5px;
        /*font-size: 28px;*/
        width: 42px;
        height: 42px;
    }



    .flash-saless{
        flex-direction: row;
        justify-content: space-between;
        background-color: #00CFE3;
        /*padding: 30px 0;*/
        height: 80px;
    }
    .flash-sales-lefts{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #00CFE3;
    }
    .flash-sales-words{
        background-color: #00CFE3;
        color: white;
    }
    .flash-sales-twords{
        color:white;
        font-style: italic;
        margin-right: 18px;
        font-size: 32px;
    }
    .flash-sales-rights{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        color: white;
    }
    .flash-sales-times{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-right: 32px;
    }
    .flash-sales-hhs{
        color: white;
        font-family: ProximaNova;
        padding: 5px;
        margin: 5px;
        font-size: 28px;
    }
    .flash-sales-hhs-empty{
        color: white;
        font-family: ProximaNova;
        /*padding: 5px;*/
        margin: 5px;
        width: 42px;
        height: 42px;
    }
</style>
