<template>
    <div class="wrapper">
        <div v-for="(i) in items" :key="i.key" class="tab-item" @click="tabTo(i.key)">
            <text class="tab-txt" :class="[pIndexKey === i.key ? 'tab-txt-active' : '']">{{i.name}}</text>
            <div class="tab-underline" v-if="pIndexKey !== i.key"></div>
            <div class="tab-underline-active" v-if="pIndexKey === i.key"></div>
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
            pIndexKey: 'new'
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
    width: 375px;
    height: 96px;
    align-items: center;
    justify-content: center;
}

.tab-txt {
    margin-top: 10px;
    width: 100px;
    text-align: center;
    font-size: 24px;
    line-height: 34px;
    padding-bottom: 4px;
}
.tab-txt-active{
    color: #EF8A31;
    font-weight: bold;
}
.tab-underline{
    width: 56px;
    height: 6px;
    background-color: #fff;
    border-radius: 4px;
}

.tab-underline-active {
    background-color: #EF8A31;
    width: 56px;
    height: 6px;
    border-radius: 4px;
}

</style>
