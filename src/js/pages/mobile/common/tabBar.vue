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
            default: 'home'
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
        indexKey: function (newVal, oldVal) {
            if (newVal) {
                this.pIndexKey = newVal
            }
        }
    },
    methods: {
        tabTo (_key) {
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
    box-shadow: 0 -3px 5px 0 rgba(0,0,0,0.19);
    background-color: #fff;
}

.bar-item {
    flex: 1;
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
