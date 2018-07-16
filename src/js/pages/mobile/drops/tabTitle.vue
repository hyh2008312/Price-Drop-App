<template>
    <div class="wrapper">
        <div v-for="(i,index) in items" :key="index" class="tab-item" @click="tabTo(i.key)">
            <text class="tab-txt" :class="[pIndexKey == i.key ? 'tab-txt-active' : '']">{{i.name}}</text>
            <div class="tab-txt-bottom" v-if="pIndexKey == i.key"></div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        items: { type: Array }
    },
    data () {
        return {
            pIndexKey: 'cutIng'
        }
    },
    methods: {
        changeTab (tab_key) {
            if (this.pIndexKey == tab_key) return;
            this.pIndexKey = tab_key;
        },
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
.tab-txt-bottom{
    height: 6px;
    width: 56px;
    margin-top: 8px;
    border-radius: 4px;
    background-color: #EF8A31;
}
.wrapper {
    background-color: #fff;
    height: 96px;
    width: 750px;
    flex-direction: row;
    justify-content: start;
    align-items: center;
    border-bottom-width: 1px;
    border-bottom-color: rgba(0,0,0,0.09);
    border-bottom-style: solid;
   /* box-shadow: 0 3px 3px 0 rgba(0,0,0,0.19);*/
}

.tab-item {
    width: 375px;
    height: 96px;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
}

.tab-txt {
    width: 180px;
    text-align: center;
    font-size: 24px;
    margin-top: 31px;
    line-height: 34px;
}

.tab-txt-active {
    color: #EF8A31;
    font-weight: bold;
}

</style>
