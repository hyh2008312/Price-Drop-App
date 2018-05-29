<template>
    <div class="wrapper">
        <div v-for="(i,index) in items" :key="index" class="tab-item" @click="tabTo(i.key)">
            <text class="tab-txt" :class="[pIndexKey == i.key ? 'tab-txt-active' : '']">{{i.name}}</text>
            <div class="tab-tri" v-if="i.tri" >
                <text class="iconfont tab-tri-1" :class="[priceIdx == 1?'tab-txt-active': '']">&#xe6fc;</text>
                <text class="iconfont tab-tri-2" :class="[priceIdx == 2?'tab-txt-active': '']">&#xe6fd;</text>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        items: {
            type: Array
        }
    },
    data () {
        return {
            pIndexKey: 'hot',
            priceIdx: 0
        }
    },
    methods: {
        tabTo (_key) {
            if (_key != 'price' && this.pIndexKey == _key) return;
            if (_key != 'price') {
                this.priceIdx = 0;
            }
            if (_key == 'price') {
                switch (this.priceIdx) {
                    case 0:
                        this.priceIdx = 1;
                        break;
                    case 1:
                        this.priceIdx = 2;
                        break;
                    case 2:
                        this.priceIdx = 1;
                        break;
                }
            }
            this.pIndexKey = _key;
            this.$emit('tabTo', {
                status: 'tabTo',
                data: {
                    key: _key,
                    priceStatus: this.priceIdx
                }
            })
        }
    }
}
</script>
<style scoped>

.iconfont {
    font-family: iconfont;
    color: #333;
}

.wrapper {
    background-color: #fff;
    height: 96px;
    width: 750px;
    flex-direction: row;
    justify-content: start;
    align-items: center;
    box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
}

.tab-item {
    width: 250px;
    height: 96px;
    flex-direction: row;
    align-items: center;
    justify-content: center;
}

.tab-txt {
    text-align: center;
    font-size: 24px;
    line-height: 34px;
}

.tab-txt-active {
    color: #EF8A31;
}

.tab-tri{
    align-items: center;
    justify-content: center;
}

.tab-tri-1{
    margin-left: 8px;
    font-size: 16px;
    flex-direction: row;
}

.tab-tri-2{
    margin-left: 8px;
    font-size: 16px;
}

</style>
