<template>
    <div class="wrapper">
        <div class="state"></div>
        <text class="close" @click="loginBack">&#xe632;</text>
        <div class="container-1">
            <text>Shipping Cost</text>       <text>Rs.11.00</text>
            <text>Shipping Carrier</text>    <text>Rs.11.00</text>
            <text>Order Processing</text>    <text>Rs.11.00</text>
            <text>Shipping Time</text>       <text>Rs.11.00</text>
            <text>Estimasted Arrival</text>  <text>Rs.11.00</text>

        </div>
    </div>
</template>
<script>
    const googleLogin = weex.requireModule('GoogleLoginModule');
    import { cliendId } from '../../../config/apis'
    export default {
        components: {
        },
        created () {},
        eros: {
            beforeAppear (params, options) {
            }
        },
        data () {
            return {
            }
        },
        methods: {
            loginBack () {
                this.$router.finish();
            },
        }
    }

</script>
<style scoped>
    .google-login-text{
        font-size: 24px;
        color: #FFFFFF;
        font-weight: bold;
        margin-left: 24px;
    }
    .google-login-icon{
        font-family: iconfont;
        font-size: 36px;
        color: #FFFFFF;
    }
    .google-login {
        width: 600px;
        height: 80px;
        margin-top: 190px;
        background-color: #DF4A32;
        border-radius: 8px;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .price-drop-tip {
        font-size: 24px;
        font-weight: 400;
        color: #000000;
        margin-top: 32px;
    }

    .price-drop-text {
        margin-top: 16px;
        font-size: 40px;
        font-style: italic;
        color: #EF8A31;
        font-weight: bold;
    }
    .container-1{
        margin-top: 140px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: left;
    }
    .close{
        font-family: iconfont;
        font-size: 45px;
        color: #333333;
        opacity: 0.56;
        margin-top: 40px;
        margin-left: 40px;
    }
    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }
</style>
