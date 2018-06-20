<template>
    <div class="wrapper">
        <div class="state"></div>
        <text class="close" @click="loginBack">&#xe632;</text>
        <div class="container-1">
            <image class="price-drop" src="bmlocal://assets/price-drop-logo.png"></image>
            <text class="price-drop-text">PriceDrop </text>
            <text class="price-drop-tip">A fun, social and money-saving way for online shopping.</text>
            <div class="google-login" @click="startGoogleLogin">
               <text class="google-login-icon">&#xe71f;</text>
               <text class="google-login-text">Continue with Google</text>
            </div>
            <div class="google-login" v-if="false" @click="startGoogleSignOut">
               <text class="google-login-icon">&#xe71f;</text>
               <text class="google-login-text">Logout with Google</text>
            </div>
        </div>
    </div>
</template>
<script>
    const googleLogin = weex.requireModule('GoogleLoginModule');
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
     import { cliendId } from '../../../config/apis'
    export default {
        components: {
        },
        created () {
            this.initGoogleAnalytics();
        },
        eros: {
            beforeAppear (params, options) {
            }
        },
        data () {
            return {
            }
        },
        methods: {
            initGoogleAnalytics () {
                googleAnalytics.trackingScreen('Login');
            },
            loginBack () {
                this.$router.finish();
            },
           startGoogleLogin () {
               const that = this;
               googleLogin.startGoogleLogin(function (param) {
                   that.requestUserInfo(param.tokenId, that);
               }, function (param) {
                   that.$notice.toast('google login failed')
               })
           },
            startGoogleSignOut () {
                const that = this;
                googleLogin.startGoogleLSignOut(function (param) {
                    that.$storage.deleteSync('user');
                    that.$storage.deleteSync('token');
                })
            },
            requestUserInfo (id_token, that) {
                that.$fetch({
                    method: 'POST',
                    name: 'user.google.sign',
                    data: {
                        client_id: cliendId,
                        idToken: id_token
                    }
                }).then(data => {
                    that.$storage.deleteSync('user');
                    that.$storage.deleteSync('token');
                    that.$storage.setSync('user', data.user);
                    that.$storage.setSync('token', data.token);
                    googleAnalytics.recordEvent('login', 'google', data.user, 0);
                    that.$router.back({
                        length: 1,
                        type: 'PUSH',
                        callback () {
                            // 返回成功回调
                            that.$event.emit('login')
                        }
                    })
                }, error => {
                    that.$notice.toast(JSON.stringify(error));
                })
            }
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
    .price-drop{
        width: 160px;
        height: 160px;
    }
    .container-1{
        margin-top: 140px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
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
