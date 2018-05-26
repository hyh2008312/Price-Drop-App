<template>
    <div class="wrapper">
        <div class="wrapper-inner">
            <div>
                <text class="gb-txt">Order Number: {{order.number}}</text>
                <text class="gb-txt gb-mt" v-if="order.paymentType">Payment Method: PayTM</text>
                <text class="gb-txt gb-mt">Order Time: {{order.created}}</text>
            </div>
            <text class="cy iconfont">&#xe708;</text>
        </div>
    </div>
</template>
<script>
    export default {
        props: ['order'],
        data () {
            return {
                list: [1, 2, 3, 4, 5, 6, 7]
            }
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        background-color: #fff;
        width: 750px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }

    .wrapper-inner{
        padding: 32px;
        flex-direction: row;
        justify-content: space-between;
        align-items: start;
    }

    .cy{
        font-size: 24px;
        line-height: 24px;
        color: rgba(0,0,0,.38);
    }

    .gb-txt{
        font-size: 20px;
        line-height: 24px;
    }

    .gb-mt{
        margin-top: 16px;
    }

</style>
