<template>
    <div class="wrapper">
        <div v-for="(i,index) in items" :key="index" class="bar-item" @click="tabTo(i.key)">
            <text class="bar-ic iconfont" :class="[pIndexKey == i.key ? 'bar-active' : '']">{{i.icon | myFilter}}</text>
            <text class="bar-txt" :class="[pIndexKey == i.key ? 'bar-active' : '']">{{i.name}}</text>
            <div class="bar-red" v-if="i.redPoint">
                <!--<text class="dot-num">{{cartNum}}</text>-->
            </div>
        </div>
    </div>
</template>

<script>
    /* eslint-disable eqeqeq */

    import he from '../utils/he';
Vue.filter('myFilter', value => he.decode(value))
export default {
    props: {
        indexKey: {
            type: String,
            default: ''
        },
        items: {
            type: Array
        }
    },
    data () {
        return {
            pIndexKey: 'home',
            cartNum: 0,
            user: ''
        }
    },
    created () {
        this.user = this.$storage.getSync('user')
        this.$event.on('cartNum', params => {
            this.getCartNum();
        });
        this.getCartNum()
        this.$event.on('logout', parmas => {
            this.token = null
            this.user = ''
            this.cartNum = '0'
            this.getCartNum()
        })
    },
    watch: {
        indexKey: {
            handler: function (newVal, oldVal) {
                if (newVal) {
                    this.pIndexKey = newVal
                }
            },
            deep: true
        }
    },
    methods: {
        tabTo (_key) {
            this.user = this.$storage.getSync('user')
            if (this.pIndexKey == _key) return;
            this.pIndexKey = _key;
            if (_key == 'cart') {
                if (this.user == '') {
                    this.redirectLogin()
                } else {
                    this.$emit('tabTo', {
                        status: 'tabTo',
                        data: {
                            key: _key
                        }
                    })
                }
            } else {
                this.$emit('tabTo', {
                    status: 'tabTo',
                    data: {
                        key: _key
                    }
                })
            }
        },
        redirectLogin () {
            this.$event.on('login', params => {
                this.getCartNum()
                this.$storage.get('user').then(resData => {
                    this.user = resData
                })
                this.$event.emit('changeTab', {
                    tab: 'cart'
                });
            });
            this.$router.open({
                name: 'login',
                type: 'PUSH'
            })
        },
        getCartNum () {
            this.$fetch({
                method: 'GET',
                name: 'cart.count',
                header: {
                    needAuth: true,
                    isLoginPop: true
                }

            }).then(data => {
                this.cartNum = data.count
                // this.$notice.toast({
                //     message: data
                // })
            }, error => {})
        },
    }
}
</script>

<style scoped>
.iconfont {
    font-family: iconfont;
}

.wrapper {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    height: 112px;
    flex-wrap: nowrap;
    flex-direction: row;
    justify-content: space-around;
    overflow: hidden;
    width: 750px;
    border-top-style: solid;
    border-top-width: 1px;
    border-top-color: rgba(0,0,0, 0.19);
    background-color: #fff;
}

.bar-item {
    flex: 1;
    background-color: #fff;
}

.bar-txt{
    color: #666;
    font-size: 48px;
    text-align: center;
    font-family: ProximaNova;
}

.bar-ic {
    color: #666;
    font-size: 48px;
    text-align: center;
}

.bar-active {
    color: #F09F58;
}

.bar-ic {
    padding-top: 20px;
    font-size: 48px;
    color: rgba(0,0,0,.38);
}

.bar-txt {
    font-size: 24px;
    padding-top: 4px;
    color: rgba(0,0,0,.38);
}

.bar-red{
    position: absolute;
    right: 40px;
    top: 15px;
    width: 16px;
    height: 16px;
    border-radius: 50%;
    background-color: red;

}
.dot-num{
    font-size: 15px;
    color: white;
    text-align: center;
    margin-top: 1px;
}
</style>
