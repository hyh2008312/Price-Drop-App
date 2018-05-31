<template>
    <div class="app-wrapper">
        <embed v-for="(item,index) in items" :src="item.src" type="weex" class="content" :style="{ visibility: item.visibility }"></embed>
        <tab-bar @tabTo="onTabTo" :items="items" :indexKey="selectedTab"></tab-bar>
        <div class="touch-bar"></div>
    </div>
</template>
<script>

import util from './utils/util';
import tabBar from './common/tabBar';
import { tabConfig } from './config';
import { cliendId } from '../../config/apis';
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
        beforeAppear () {
            this.refreshToken()
            this.getChannel()
            this.getState()
            this.getUser()
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
        }
    },
    components: {
        'tab-bar': tabBar
    },
    created () {
        this.$navigator.setNavigationInfo({
            title: '',
            navShow: false,
            statusBarStyle: 'LightContent'
        });
        util.initIconFont()
        this.androidFinishApp()
    },
    data () {
        return {
            items: tabConfig,
            selectedTab: 'home'
        }
    },
    methods: {
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
        getChannel () {
            // const result = this.$storage.getSync('channel')
            // if (!result) {
            //     this.$fetch({
            //         method: 'GET', // 大写
            //         name: 'category.list', // 当前是在apis中配置的别名，你也可以直接绝对路径请求 如：url:http://xx.xx.com/xxx/xxx
            //         data: {}
            //     }).then(data => {
            //         this.$storage.set('channel', data);
            //     }, error => {})
            // }
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
            this.$fetch({
                method: 'GET', // 大写
                name: 'user.user',
                data: {},
                header: {
                    Authorization: 'Bearer ' + 'NNuJ3K7Z37c0LzJkdjO3kHU3MO0BBr'
                }
            }).then(data => {
                this.$storage.set('user', data);
            }, error => {})
        },
        refreshToken () {
            this.$storage.get('token').then((data) => {
                if (data) {
                    this.$fetch({
                        method: 'POST', // 大写
                        name: 'oauth2.token',
                        data: {
                            refresh_token: data.refresh_token,
                            grant_type: 'refresh_token',
                            client_id: cliendId
                        }
                    }).then(data => {
                        this.$storage.set('token', data);
                    }, error => {})
                }
            })
        }
    }
}

</script>
<style>
body {
    margin: 0;
    padding: 0;
    background-color: #f1f1f1;
    color: #333;
}

</style>
<style lang="sass" scoped>
@import "src/js/css/core/base.scss";
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
