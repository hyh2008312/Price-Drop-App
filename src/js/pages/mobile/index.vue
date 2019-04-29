<template>
    <div class="app-wrapper">
        <embed v-for="(item, index) in items" :src="item.src" type="weex" class="content" :style="{ visibility: item.visibility }"></embed>
        <tab-bar @tabTo="onTabTo" :items="items" :indexKey="selectedTab"></tab-bar>

        <NewDialog class="wxdialog"
                   :content="content"
                   :show="show"
                   :single="true"
                   :is-checked="isChecked"
                   :confirmText="'Update Your App’s Version'"
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
            this.refreshToken();
            this.getState();
        },
        backAppeared (params) {
            if (params && params.tab) {
                this.selectedTab = params.tab;
                this.items.forEach((val) => {
                    if (val.key === this.selectedTab) {
                        val.visibility = 'visible';
                        return
                    }
                    val.visibility = 'hidden';
                });
            }
            if (params && params.type == 'login') {
                this.$router.refresh();
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
        util.initIconFont();
        this.androidFinishApp();
        this.initPush();
        this.getVersion();
        this.$event.on('changeTab', param => {
            this.selectedTab = param.tab;
            this.items.forEach((val) => {
                if (val.key === this.selectedTab) {
                    val.visibility = 'visible';
                    return
                }
                val.visibility = 'hidden';
            })
        });
        const that = this;
        commonUtils.getAndroidData('pricedrop', (data) => {
            that.jumpNativePage(data);
            commonUtils.deleteAndroidData('pricedrop');
        }, (data) => {
            // that.$notice.toast({
            //     message: data
            // })
        });
    },
    data () {
        return {
            items: tabConfig,
            selectedTab: 'home',
            isFirstLogin: false,
            show: false,
            content: 'Welcome back! We just released a new version. Please update your app to continue.',
            closeCount: 1
        }
    },
    methods: {
        jumpNativePage (link) {
            if (link) {
                const parseUrl = /^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
                const result = parseUrl.exec(link);
                // 'url', 'scheme', 'slash', 'host', 'port', 'path', 'query', 'hash';
                const path = result[5];
                const query = result[6];

                if (path == 'home') {
                    const params = this.getParams(query);
                    this.$event.emit('changeTab', {
                        tab: params.tab
                    });
                    if (params.activeTab) {
                        this.$storage.set('activeTab', params.activeTab);
                    }
                } else {
                    const params = this.getParams(query);
                    let router = path.split('/');
                    if (router.length > 0) {
                        router = router.join('.');
                    }
                    this.$router.open({
                        name: router,
                        type: 'PUSH',
                        params: params
                    });
                }
            }
        },
        getParams (query) {
            const reg = /(([^?&=]+)(?:=([^?&=]*))*)/g;
            const result = {};
            let match;
            while (match = reg.exec(query)) {
                const key = match[2];
                const value = match[3] || '';
                result[key] = decodeURIComponent(value);
            }
            return result;
        },
        initPush () {
            bmPush.initPush({});
        },
        onTabTo (_result) {
            const _key = _result.data.key || '';
            this.selectedTab = _key;
            this.items.forEach((val) => {
                if (val.key === _key) {
                    val.visibility = 'visible';
                    return
                }
                val.visibility = 'hidden';
            })
        },
        androidFinishApp () {
            const globalEvent = weex.requireModule('globalEvent');
            const self = this;
            globalEvent.addEventListener('homeBack', options => {
                if (self.closeCount === 2) {
                    self.$router.finish();
                }
                self.closeCount = self.closeCount + 1;
                if (self.closeCount === 2) {
                    self.$notice.toast('Press again to exit')
                }

                setTimeout(function () {
                    self.closeCount = 1
                }, 2000);
            })
        },
        getState () {
            this.$fetch({
                method: 'GET',
                name: 'address.state.list',
                data: {}
            }).then(data => {
                this.$storage.set('state', data);
            }, error => {});
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
            }, error => {});
        },
        refreshToken () {
            const token = this.$storage.getSync('token');
            if (token && !this.isFirstLogin) {
                this.isFirstLogin = true;
                // this.$fetch({
                //     method: 'POST', // 大写
                //     name: 'oauth2.token',
                //     data: {
                //         refresh_token: token.refreshToken,
                //         grant_type: 'refresh_token',
                //         client_id: cliendId
                //     },
                //     header: {
                //         isLoginPop: true
                //     }
                // }).then(data => {
                //     this.$storage.set('token', {
                //         tokenType: data.token_type,
                //         scope: data.scope,
                //         accessToken: data.access_token,
                //         refreshToken: data.refresh_token,
                //         expiresIn: data.expires_in
                //     });
                // }, error => {
                //     this.$storage.deleteSync('token');
                //     this.$storage.deleteSync('user');
                // });
                this.getUser();
            }
        },
        getVersion () {
            commonUtils.getAppVersionCode((params) => {
                if (params.code === 200) {
                    this.$fetch({
                        method: 'GET',
                        name: 'app_version.force.app.update',
                        data: {}
                    }).then(data => {
                        const version = data.versionNumber ? data.versionNumber.split('.').join('') : 0;
                        if (Number(params.versionCode) <= Number(version)) {
                            this.content = data.updateNotification;
                            this.openDialog();
                        }
                    }, error => {});
                }
            });
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
    background-color: #f5f4f3;
    /*margin-bottom: 100;*/
}

.app-wrapper {
    background-color: #f5f4f3;
}
</style>
