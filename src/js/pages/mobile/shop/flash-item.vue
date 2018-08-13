<template>
    <div>
        <div class="flash-sales" v-if="fstatus== 'Ongoing'">
            <!--<text class="flash-sales-word">Flash Sales</text> <text class="flash-sales-time">12:00:00</text>-->
            <div class="flash-sales-left">
                <text class="flash-icon" >&#xe745;</text>
                <text class="flash-sales-word">Rs.6.00</text>
                <text class="flash-sales-word1">Rs.1299.00</text>
            </div>
            <div class="flash-sales-right">
                <text class="flash-sales-tword">Ends In</text>
                <div class="flash-sales-time">
                    <text class="flash-sales-hh">{{hour}}:</text>
                    <text class="flash-sales-hh">{{min}}:</text>
                    <text class="flash-sales-hh">{{second}}</text>
                </div>


            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: ['hour', 'min', 'second', 'fstatus'],
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
        name: 'flash-item'

    }
</script>

<style scoped>
    .flash-sales{
        flex-direction: row;
        justify-content: space-between;
        background-color: #EF8A31;
        /*padding: 30px 0;*/
        height: 80px;
        /*transform: rotate(10deg);*/
    }
    .flash-sales-left{
        flex-direction: row;
        justify-content: start;
        align-items: center;

        /*border-bottom-right-style:  ;*/
    }
    .flash-sales-right{
        flex-direction: row;
        justify-content: start;
        background-color: #FFEACF;
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
        text-decoration: line-through;
        font-weight: 700;
    }
    .flash-sales-tword{

    }
    .flash-sales-time{
        font-size: 28px;
        color: #FFFFFF;
        flex-direction: row;
        justify-content: start;
    }
    .flash-sales-hh{
        background-color: #EF8A31;
        color: white;
        padding: 2px;
        font-size: 24px;
    }
</style>
