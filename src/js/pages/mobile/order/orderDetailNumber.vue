<template>
    <div class="wrapper">
        <div class="wrapper-inner">
            <div>
                <text class="gb-txt">Order Number: {{order.number}}</text>
                <text class="gb-txt gb-mt" v-if="order.paymentType">Payment Method: PayTM</text>
                <text class="gb-txt gb-mt">Order Time: {{formatDate(order.created, 'MMM DD, YYYY hh:mm:ss')}}</text>
            </div>
            <text class="cy iconfont" @click="setNumber">&#xe708;</text>
        </div>
    </div>
</template>
<script>
    import dayjs from 'dayjs';
    const clipboard = weex.requireModule('clipboard');

    export default {
        props: ['order'],
        data () {
            return {
                list: [1, 2, 3, 4, 5, 6, 7]
            }
        },
        methods: {
            formatDate (str, hmr) {
                return dayjs(new Date(str)).format(hmr)
            },
            setNumber () {
                clipboard.setString(this.order.number)
                this.$notice.toast('Order number copied');
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
