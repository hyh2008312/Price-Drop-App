<template>
    <div class="wrapper">
        <div class="state"></div>
        <text class="close">&#xe632;</text>



        <text class="login" @click="startGoogleLogin">登录按钮</text>

        <text class="login" @click="startGoogleSignOut">退出按钮</text>
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
           startGoogleLogin () {
               const that = this;
               googleLogin.startGoogleLogin(function (param) {
                   that.requestUserInfo(param.tokenId, that);
               },function (param) {
                   that.$notice.toast('google login failed')
               })
           },
            startGoogleSignOut () {
                const that = this;
                googleLogin.startGoogleLSignOut(function (param) {
                    that.$storage.set('user', null);
                    that.$storage.set('token', null);
                })
            },
            requestUserInfo (id_token,that) {
                that.$fetch({
                    method: 'POST',
                    name: 'user.google.sign',
                    data: {
                        client_id: cliendId,
                        idToken: id_token
                    }
                }).then(data => {
                    that.$storage.set('user', data.user);
                    that.$storage.set('token', data.token);
                    that.$router.finish();
                }, error => {
                    that.$notice.toast(JSON.stringify(error));
                })
            }
        }
    }

</script>
<style scoped>
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
    .login{
        margin-top: 300px;
        margin-left: 300px;
        background-color: #95ca78;
        width: 300px;
        text-align: center;
        line-height: 300px;
        height: 300px;
    }
</style>
