<template>
    <div class="wrapper">
        <div class="status-bar"></div>
        <div class="header" @click="onfocusFn()">
            <text class="header-icon iconfont" @click="homeBack">&#xe6f6;</text>
            <div class="header-search">
                <text class="header-icon-1 iconfont">&#xe621;</text>
                <input ref="searchInput" class="header-title" type="text" return-key-type="search"
                       :autofocus="autofocusNew"
                       @focus="onfocus"
                       @onblur="onblur"
                       @return="search" @input="changeKeywords" placeholder="What are you looking for?" v-model="key" />
                <text class="header-icon-2 iconfont" @click="clearKeywords" v-if="key">&#xe632;</text>
            </div>
            <div class="box-bg"  @click="openCart">
                <text class="box-txt-icon iconfont">&#xe767;</text>
                <text class="box-dot" v-if="cartNum>0">{{cartNum > 99? '99+': cartNum}}</text>
            </div>
        </div>
        <div class="search-h" v-if="searchList.length > 0">
            <text class="sh-1">SEARCH HISTORY</text>
            <text class="sh-2" @click="clearSearch">Clear</text>
        </div>
        <div class="search-line" >
            <div v-for="(item, index) in searchList" class="search-tag" @click="searchKeywords(item)">
                <text class="search-tag-text" >{{item}}</text>
            </div>
        </div>

        <!--<div class="search-line" v-for="(item, index) in searchList" @click="searchKeywords(item)">-->
            <!--<div class="search-bg" >-->
            <!--<text>{{item}}</text>-->
                <!--<text class="search-text">{{item}}</text>-->
            <!--</div>-->
        <!--</div>-->
        <!--<text class="search-text-1" v-if="searchList.length > 0" @click="clearSearch">Clear Search History</text>-->
    </div>
</template>
<script>
    import refresher from '../common/refresh';
    import preload from '../common/preloadImg';
    import block3 from './block3';
    import block7 from './block7';
    import toggle from './toggle';
    import { baseUrl } from '../../../config/apis'

    export default {
        components: {
            'refresher': refresher,
            preload,
            block7,
            block3,
            toggle
        },
        eros: {
            appeared (params) {
                if (params && params.key) {
                    this.key = params.key;
                }
            }
        },
        created () {
            const search = this.$storage.getSync('search');
            if (search && search.length > 0) {
                this.searchList = [...search];
            }
            this.getUnread()
        },
        data () {
            return {
                searchList: [],
                key: '',
                cartNum: '',
                deletestatus: false,
                searchstatus: false,
                autofocusNew: false,
            }
        },
        mounted () {
        },
        methods: {

            homeBack () {
                this.$router.back();
            },
            onfocusFn (event) {
                this.autofocusNew = true;
            },
            onfocus (event) {
                this.searchstatus = true;
            },
            onblur (event) {
                this.autofocusNew = false;
            },
            search (event) {
                if (event.value) {
                    this.key = event.value.replace(/(^\s*)|(\s*$)/g, '');
                    const index = this.searchList.findIndex((e) => {
                        return e == this.key;
                    }).toString();
                    if (index >= 0) {
                        this.searchList.splice(index, 1);
                        this.searchList.unshift(this.key);
                    } else {
                        if (this.searchList.length == 6) {
                            this.searchList.pop();
                            this.searchList.unshift(this.key);
                        } else {
                            this.searchList.unshift(this.key);
                        }
                    }
                    this.$storage.setSync('search', this.searchList);
                } else {
                    this.$tools.resignKeyboard().then(resData => {
                        // 收起成功的回调
                    }, error => {});
                }
                if (event.value) {
                    this.$router.finish();
                    this.$router.open({
                        name: 'search.result',
                        type: 'PUSH',
                        params: {
                            key: this.key
                        }
                    });
                }
            },
            getUnread () {
                this.$storage.get('user').then((data) => {
                    if (data) {
                        this.getCartNumApi(data.id);
                    }
                });
            },
            getCartNumApi (id) {
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/cart/count/${id}/`,
                    header: {
                        isLoginPop: true
                    }
                }).then(data => {
                    this.cartNum = data.count;
                });
            },
            openCart () {
                const user = this.$storage.getSync('user');
                if (user) {
                    this.$router.open({
                        name: 'cart',
                        type: 'PUSH'
                    });
                } else {
                    this.$router.open({
                        name: 'login',
                        type: 'PUSH'
                    });
                }
            },
            changeKeywords (event) {
                this.key = event.value;
                if (this.key.length == 0) this.deletestatus = false;
                else this.deletestatus = true;
            },
            searchKeywords (e) {
                this.key = e.replace(/(^\s*)|(\s*$)/g, '');
                this.$router.finish();
                this.$router.open({
                    name: 'search.result',
                    type: 'PUSH',
                    params: {
                        key: this.key
                    }
                });
            },
            clearSearch () {
                this.searchList = [];
                this.$storage.setSync('search', this.searchList);
            },
            clearKeywords ($event) {
                this.autofocusNew = false;
                this.deletestatus = false;
                this.key = '';
                if (this.$refs.searchInput.clear) {
                    this.$refs.searchInput.clear();
                }
            }
        }
    }

</script>
<style lang="sass" src="./index.scss"></style>
