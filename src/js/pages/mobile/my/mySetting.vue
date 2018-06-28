<template>
    <div class="wrapper">
        <topic-header title="Settings" leftBtn="icon"  ref="ref1" ></topic-header>
        <div class="blackheader"></div>
        <div class="mid-cell" >
            <div class="box-tlt " @click="openNew(7)">
                <div class="box-left">
                    <text class="box-txt">Terms & Conditions</text>
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
            <div class="box-tlt" @click="openDialog">
                <div class="box-left">
                    <text class="box-txt">Version</text>
                </div>
                <div class="box-right">
                    <text class="i-box" v-if="!isUpdate">{{version}}</text>
                    <text class="i-new" v-if="isUpdate">new</text>
                </div>

            </div>
        </div>


        <div class="bottom-btn" @click="logOut" v-if="logsign==true">
            <text class="bottom-btn-txt">Logout</text>
        </div>
        <wxc-dialog title="update"
                    content="1.fixed the some bugs and improve the User exp"
                    :show="show"
                    :single="false"
                    :is-checked="false"
                    :show-no-prompt="false"
                    :cancel-text="'Cancel'"
                    :confirm-text="'Confirm'"
                    @wxcDialogCancelBtnClicked="wxcDialogCancelBtnClicked"
                    @wxcDialogConfirmBtnClicked="wxcDialogConfirmBtnClicked"
                    @wxcDialogNoPromptClicked="wxcDialogNoPromptClicked">
        </wxc-dialog>
    </div>
</template>

<script>
    import header from './header';

    const commonUtils = weex.requireModule('CommonUtils');
    import { WxcDialog } from 'weex-ui';

    export default {
        components: {
            'topic-header': header,
             WxcDialog
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
            this.checkUpdate();
           // this.startGetAppVersion();
        },
        data () {
            return {
                params: '',
                logsign: true,
                token: '',
                client_id: '',
                version: '1.0.0',
                versionCode: 100,
                show: false,
                isChecked: false,
                isUpdate: false,
                updateDetail: 'Fixed some bugs & improved user experience',
                apkUrl: ''
            }
        },
        name: 'myDetail',
        methods: {
            checkUpdate () {
                const that = this
                that.$notice.toast(that.isUpdate)
                this.$fetch({
                    method: 'GET',
                    name: 'app.get.version'
                }).then(data => {
                    commonUtils.getAppVersionCode(function (params) {
                        if (params.code === 200) {
                            that.version = params.versionName;
                            that.updateDetail = data.detail;
                            that.apkUrl = data.apkUrl;
                            that.versionCode = params.versionCode;
                            that.isUpdate = Number(data.version) > Number(that.versionCode);
                            // that.$notice.toast(`${that.isUpdate}`)
                        }
                    })
                }, error => {
                })
            },
            openDialog () {
                if (this.isUpdate) {
                    this.show = true;
                }
            },
            wxcDialogCancelBtnClicked () {
                // must setting,control by yourself
                this.show = false;
            },
            wxcDialogConfirmBtnClicked () {
                this.show = false;
                commonUtils.updateAndroidApp(this.apkUrl);
            },
            wxcDialogNoPromptClicked (e) {
                // must setting,control by yourself
                this.isChecked = e.isChecked;
            },
            startGetAppVersion () {
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
        border-bottom-width: 1px;
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
        font-size: 24px;
        padding-right: 32px;
        color: rgba(0,0,0,0.87);
    }
    .i-new {
        height: 40px;
        width: 60px;
        text-align: center;
        line-height: 40px;
        border-radius: 4px;
        font-size: 20px;
        margin-right: 32px;
        color: white;
        background-color: #ef8a31;
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
        align-items: center;
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
