<template>
    <div class="wrapper">
        <div class="gb-box-1" @click="jumpAddress">
            <text class="gb-title">Shipping To</text>
            <div class="gb-box" v-if="address.id">
                <div class="gb-box-2">
                    <div class="gb-bg">
                        <text class="gb-icon iconfont">&#xe707;</text>
                        <text class="gb-text">{{address.firstName}}</text>
                    </div>
                    <div class="gb-bg-1">
                        <text class="gb-icon iconfont">&#xe706;</text>
                        <text class="gb-text">+19 {{address.phoneNumber}}</text>
                    </div>
                    <div class="gb-bg-1">
                        <text class="gb-icon iconfont">&#xe705;</text>
                        <text class="gb-text-1">{{address.line1}}, {{address.line2}}, {{address.line3}}, {{address.city}}, {{address.stateName}}</text>
                    </div>
                </div>
                <text class="gb-icon-1 iconfont">&#xe626;</text>
            </div>
            <div class="gb-box gb-bg" v-if="!address.id">
                <text class="gb-text-2">Add New Address</text>
                <text class="gb-icon-1 iconfont">&#xe626;</text>
            </div>
        </div>
        <order-line></order-line>
    </div>
</template>
<script>
    import line from './orderDetailLine';

    export default {
        components: {
            'order-line': line
        },
        props: ['address'],
        methods: {
            jumpAddress () {
                this.$router.open({
                    name: 'order.address'
                })
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

    .gb-box{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        width: 686px;
    }

    .gb-box-1{
        padding: 32px;
    }

    .gb-box-2{
        width: 658px;
    }

    .gb-title{
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
    }

    .gb-icon{
        font-size: 24px;
        line-height: 24px;
        margin-right: 16px;
        color: rgba(0,0,0,0.54);
    }

    .gb-icon-1{
        font-size: 28px;
        line-height: 28px;
        color: rgba(0,0,0,0.54);
    }

    .gb-text{
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        color: rgba(0,0,0, 0.87);
    }

    .gb-text-1{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0, 0.87);
        width: 646px;
    }

    .gb-text-2{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0, 0.87);
    }

    .gb-bg{
        margin-top: 32px;
        flex-direction: row;
        align-items: center;
    }

    .gb-bg-1{
        margin-top: 16px;
        flex-direction: row;
        align-items: start;
    }

</style>
