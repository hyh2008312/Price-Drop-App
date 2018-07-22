<template>
    <div class="app-wrapper">
        <embed v-for="(item, index) in items" :src="item.src" type="weex" class="content" :style="{ visibility: item.visibility }"></embed>
        <tab-bar @tabTo="onTabTo" :items="items" :indexKey="selectedTab"></tab-bar>

        <NewDialog class="wxdialog"
                   :content="'Welcome back! We just released a new version. Please update your app to continue.'"
                   :show="show"
                   :single="true"
                   :is-checked="isChecked"
                   :confirmText="'Update Now'"
                   @wxcDialogCancelBtnClicked="wxcDialogCancelBtnClicked"
                   @wxcDialogConfirmBtnClicked="wxcDialogConfirmBtnClicked"
                   @wxcDialogNoPromptClicked="wxcDialogNoPromptClicked">
        </NewDialog>
    </div>
</template>
<script>

import util from './utils/util';
import tabBar from './common/tabBar';
import { tabConfig } from './config';
import { cliendId } from '../../config/apis';
import NewDialog from './home/newPopup';
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

const bmPush = weex.requireModule('bmPush');
const commonUtils = weex.requireModule('CommonUtils');

export default {
    bmRouter: {
        viewWillAppear () {
            console.log('home-index-viewWillAppear');
        },
        viewWillBackAppear (params) {
            console.log('home-index-viewWillBackAppear');
        }
    },
    eros: {
        beforeAppear (params) {
            this.refreshToken()
            this.getState()
        },
        backAppeared (params) {
            if (params && params.tab) {
                this.selectedTab = params.tab
                this.items.forEach((val) => {
                    if (val.key === this.selectedTab) {
                        val.visibility = 'visible'
                        return
                    }
                    val.visibility = 'hidden'
                })
            }
            if (params && params.type == 'login') {
                this.$router.refresh()
            }
        }
    },
    components: {
        'tab-bar': tabBar,
        NewDialog
    },
    created () {
        this.$navigator.setNavigationInfo({
            title: '',
            navShow: false,
            statusBarStyle: 'LightContent'
        });
        util.initIconFont()
        this.androidFinishApp()
        this.initPush()
        this.getVersion()
    },
    data () {
        return {
            items: tabConfig,
            selectedTab: 'drops',
            isFirstLogin: false,
            show: false
        }
    },
    methods: {
        initPush () {
            bmPush.initPush({});
        },
        onTabTo (_result) {
            const _key = _result.data.key || '';
            this.selectedTab = _key
            this.items.forEach((val) => {
                if (val.key === _key) {
                    val.visibility = 'visible'
                    return
                }
                val.visibility = 'hidden'
            })
        },
        androidFinishApp () {
            const globalEvent = weex.requireModule('globalEvent');
            const self = this;
            globalEvent.addEventListener('homeBack', options => {
                self.$router.finish();
                /* this.$notice.confirm({
                    title: '退出',
                    message: '确定退出Price Drop吗？',
                    okTitle: '确认',
                    cancelTitle: '取消',
                    okCallback () {
                        // 点击确认按钮的回调
                    },
                    cancelCallback () {
                       // 点击取消按钮的回调
                    }
                }) */
            })
        },
        getState () {
            this.$fetch({
                method: 'GET', // 大写
                name: 'address.state.list',
                data: {}
            }).then(data => {
                this.$storage.set('state', data);
            }, error => {})
        },

        getUser () {
            return this.$fetch({
                method: 'GET', // 大写
                name: 'user.user',
                data: {},
                header: {
                    needAuth: true,
                    isLoginPop: true
                }
            }).then(data => {
                this.$storage.set('user', data);
            }, error => {})
        },
        refreshToken () {
            const token = this.$storage.getSync('token')
            if (token && !this.isFirstLogin) {
                this.isFirstLogin = true;
                this.$fetch({
                    method: 'POST', // 大写
                    name: 'oauth2.token',
                    data: {
                        refresh_token: token.refreshToken,
                        grant_type: 'refresh_token',
                        client_id: cliendId
                    },
                    header: {
                        isLoginPop: true
                    }
                }).then(data => {
                    this.$storage.set('token', {
                        tokenType: data.token_type,
                        scope: data.scope,
                        accessToken: data.access_token,
                        refreshToken: data.refresh_token,
                        expiresIn: data.expires_in
                    })
                    this.getUser();
                }, error => {
                    this.$storage.deleteSync('token');
                    this.$storage.deleteSync('user');
                })
            }
        },
        getVersion () {
            commonUtils.getAppVersionCode((params) => {
                if (params.code === 200 && Number(params.versionCode) < 138) {
                    this.openDialog()
                }
            })
        },
        openDialog () {
            // if (this.user == null) {
            //     this.redirectLogin()
            // } else {
            this.show = true;
            // }
        },
        wxcDialogCancelBtnClicked () {
            // 此处必须设置，组件为无状态组件，自己管理
            this.show = false;
        },
        wxcDialogConfirmBtnClicked () {
            commonUtils.updateAndroidApp('');
            googleAnalytics.recordEvent('DownloadApp', 'DownLoad App 138', 'DownLoad App 138', 0);
        },
        wxcDialogNoPromptClicked (e) {
            // 此处必须设置，组件为无状态组件，自己管理
            this.isChecked = e.isChecked;
        }
    }
}

</script>
<style>
body {
    margin: 0;
    padding: 0;
    background-color: #f1f1f1;
}

</style>
<style scoped>
.iconfont {
    font-family: iconfont;
}

.content {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    margin-top: 0;
    background-color: #f1f1f1;
    /*margin-bottom: 100;*/
}

.app-wrapper {
    background-color: #f1f1f1;
}

.r-box {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

.iconfont {
    font-family: iconfont;
}

.wrapper {}


.slogan {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    background-color: #fff;
}

.i-slg {
    height: 66px;
    font-size: 26px;
    padding-top: 16px;
    flex: 1;
    text-align: center;
    color: #b4282d;
}

</style>
