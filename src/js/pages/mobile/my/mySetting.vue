<template>
    <div class="wrapper">
        <topic-header title="Settings" leftBtn="icon"  ref="ref1" ></topic-header>
        <div class="blackheader"></div>
        <div class="mid-cell" >
            <div class="box-tlt " @click="openNew(7)">
                <div class="box-left">
                    <text class="box-txt">Term & Conditions</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(8)">
                <div class="box-left">
                    <text class="box-txt">Privacy Policy</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt">Version</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont" style="font-size: 24px">{{version}}</text>
                </div>

            </div>
        </div>


        <div class="bottom-btn" @click="logOut" v-if="logsign==true">
            <text class="bottom-btn-txt">Logout</text>
        </div>
    </div>
</template>

<script>
    import header from './header';

    const commonUtils = weex.requireModule('CommonUtils');

    export default {
        components: {
            'topic-header': header
        },
        eros: {
            appeared (params, options) {
                if (params) {
                    this.params = params
                    if (params.setsign == '1') {
                        this.logsign = false
                    }
                }
            }
        },
        created () {
            this.startGetAppVersion();
        },
        data () {
            return {
                params: '',
                logsign: true,
                token: '',
                client_id: '',
                version: '1.0'
            }
        },
        name: 'myDetail',
        methods: {
            startGetAppVersion () {
                this.$notice.toast({
                    message: this.params
                })
                const that = this
                commonUtils.getAppVersionCode(function (params) {
                    if (params.code === 200) {
                        that.version = params.versionName
                    }
                })
            },
            openNew (head) {
                this.$router.open({
                    name: 'my.faqTmp',
                    type: 'PUSH',
                    params: {
                        type: head
                    }
                })
            },
            logOut () {
                const self = this
                this.$storage.deleteSync('user')
                this.$storage.deleteSync('token')
                this.$router.back({
                    length: 1,
                    type: 'PUSH',
                    callback () {
                        // 返回成功回调
                        self.$event.emit('logout')
                    }
                })
                // TODO 请求接口没有返回body 无法走回调
                // this.token = this.params.params.accessToken
                // this.client_id = cliendId
                // this.$notice.alert({
                //     message: axios.fetch
                // })
                //
                // axios.fetch({
                //     method: 'POST',
                //     url: `${baseUrl}/oauth2/revoke_token/`,
                //     data: {
                //         token: this.token,
                //         client_id: this.client_id
                //     }
                // }, res => {
                //    this.$notice.alert({ message: res })
                // })
                //     .then((res) => {
                //     // this.$storage.removeAll().then(resData => {
                //     //     console.log('本地所有持久化存储的数据都已被清空。')
                //     //     this.$notice.toast({
                //     //         message: 'logout success!!'
                //     //     })
                //         this.$notice.toast({
                //             message: res
                //         })
                //         // this.$router.back({
                //         //     length: 1,
                //         //     type: 'PUSH',
                //         //     callback() {
                //         //         // 返回成功回调
                //         //         this.$event.emit('logout')
                //         //     }
                //         // })
                //     // })
                // }).catch((res) => {
                //     this.$notice.toast({
                //         message: res
                //     })
                // })
            }
        }
    }
</script>

<style>
    .iconfont{
        font-family: iconfont;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .box-tlt {
        height: 96px;
        border-bottom-width: 3px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08);
        flex-direction: row;
        justify-content: space-between;
    }

    .box-txt-icon {
        font-family: iconfont;
        font-size: 40px;
        padding-top: 34px;
        padding-left: 32px;
        color: #ef8a31;
    }
    .box-txt-left{
        font-family: ProximaNova-Regular;
        padding-top: 30px;
        opacity: 0.54;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .box-txt {
        font-family: iconfont;
        font-size: 30px;
        padding-top: 34px;
        padding-left: 32px;
        color: #333;
    }

    .i-box {
        font-size: 20px;
        padding-top: 34px;
        padding-right: 32px;
        color: rgba(0,0,0,0.87);
    }
    .mid-cell{
        margin-top: 160px;
        border-radius: 4px;
        background-color: #fff;
    }
    .box-left{
        flex-direction: row;
        justify-content: space-between;
    }
    .box-right{
        flex-direction: row;
        justify-content: space-between;
    }
    .bottom-btn{
        width: 750px;
        padding-top: 32px;
        padding-bottom: 32px;
    }
    .bottom-btn-txt{
        background-color: white;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        text-align: center;
        padding-top: 32px;
        padding-bottom: 32px;
        color: rgba(207,9,9,0.87);
    }
</style>
