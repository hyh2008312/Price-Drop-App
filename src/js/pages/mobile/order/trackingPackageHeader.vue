<template>
    <div class="wrapper">
        <div class="gb-box">
            <text class="logo iconfont">&#xe720;</text>
            <div class="gb-box-center">
                <div class="gb-box-bg">
                    <text class="gb-text">Carrier: </text>
                    <text class="gb-text-color">{{order.carrierCode}}</text>
                </div>
                <div class="gb-box-bg gb-mt">
                    <text class="gb-text">Tracking Number: </text>
                    <text class="gb-text-color">{{order.trackingNumber}}</text>
                    <text class="gb-icon iconfont" @click="setNumber">&#xe708;</text>
                </div>
            </div>
            <text class="gb-icon-1 iconfont" v-if="false">&#xe70e;</text>
        </div>
    </div>
</template>
<script>
    const clipboard = weex.requireModule('clipboard');

    export default {
        props: ['order'],
        data () {
            return {
                src: 'bmlocal://assets/occupy.png'
            }
        },
        methods: {
            setNumber () {
                clipboard.setString(this.order.trackingNumber)
                this.$notice.toast('Tracking number copied')
            }
        }
    }
</script>
<style scoped>
    .iconfont{
        font-family: iconfont;
    }

    .wrapper{
        background-color: #fff;
        width: 750px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }

    .logo{
        width: 80px;
        line-height: 80px;
        margin-right: 32px;
        text-align: center;
        border-radius: 40px;
        background-color: #EF8A31;
        color: #fff;
    }

    .gb-box{
        padding: 32px;
        flex-direction: row;
        align-items: center;
    }

    .gb-box-center{
        width: 574px;
    }

    .gb-box-bg{
        flex-direction: row;
        align-items: center;
    }

    .gb-text-color{
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        color: #EF8A31;
    }

    .gb-text{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.54);
    }

    .gb-mt{
        margin-top: 22px;
    }

    .gb-icon{
        margin-left: 32px;
        font-size: 24px;
        line-height: 24px;
        color: rgba(0,0,0,0.38);
    }

    .gb-icon-1{
        font-size: 32px;
        line-height: 32px;
        margin-left: 32px;
        color: #EF8A31;
    }

</style>
