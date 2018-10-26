<template>
    <div class="wrapper">
        <div class="ribbon">
            <image class="image" src="bmlocal://assets/rectangle-line.png" v-for="i in list"></image>
        </div>
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
                        <text class="gb-text">+91 {{address.phoneNumber}}</text>
                    </div>
                    <div class="gb-bg-1">
                        <text class="gb-icon iconfont">&#xe705;</text>
                        <text class="gb-text-1">{{address.line1}}, {{address.line2}}, {{address.line3}}, {{address.city}}, {{address.stateName}}</text>
                    </div>
                </div>
                <text class="gb-icon-1 iconfont">&#xe626;</text>
            </div>
            <div :class="[address.id?'arrow':'arrow1']">
                <text class="iconfont arrow-icon">&#xe626;</text>
            </div>

            <div class="gb-box gb-bg" v-if="!address.id">
                <text class="gb-text-2">Add New Address</text>
                <text class="gb-icon-1 iconfont">&#xe626;</text>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        components: {},
        data () {
            return {
                list: [1, 2, 3, 4, 5, 6, 7, 8]
            }
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
        width: 686px;
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
        width: 550px;
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
    .arrow{
        position: absolute;
        top: 100px;
        right: 30px;
    }
    .arrow1{
        position: absolute;
        top: 95px;
        right: 30px;
    }
    .arrow-icon{
        opacity: 0.6;
        font-weight: 900;
    }
    .ribbon{
        background-color: #fff;
        width: 750px;
        height: 12px;
        flex-direction: row;
    }
    .image{
        width: 104px;
        height: 12px;
    }
</style>
