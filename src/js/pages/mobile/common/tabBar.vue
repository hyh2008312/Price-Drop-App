<template>
    <div class="wrapper">
        <div v-for="(i,index) in items" :key="index" class="bar-item" @click="tabTo(i.key)">
            <text class="bar-ic iconfont" :class="[pIndexKey == i.key ? 'bar-active' : '']">{{i.icon | myFilter}}</text>
            <text class="bar-txt" :class="[pIndexKey == i.key ? 'bar-active' : '']">{{i.name}}</text>
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
            pIndexKey: 'home'
        }
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
            if (_key == 'drops') {
                const token = this.$storage.getSync('token')
                if (!token || !token.accessToken) {
                    this.$router.open({
                        name: 'login',
                        type: 'PUSH'
                    })
                    return
                }
            }
            if (this.pIndexKey == _key) return;
            this.pIndexKey = _key;
            this.$emit('tabTo', {
                status: 'tabTo',
                data: {
                    key: _key
                }
            })
        }
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

.bar-txt,
.bar-ic {
    color: #666;
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
</style>
