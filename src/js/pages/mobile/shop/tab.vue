<template>
    <div class="wrapper">
        <div v-for="(i,index) in items" :key="index" class="tab-item" @click="tabTo(i.key)">
            <text class="tab-txt" :class="[indexKey == i.key ? 'tab-txt-active' : '']">{{i.name}}</text>
        </div>
    </div>
</template>
<script>
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
    methods: {
        tabTo (_key) {
            if (this.indexKey == _key) return;
            this.indexKey = _key;
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
    margin-top: 145px;
    box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
}

.tab-item {
    width: 375px;
    height: 96px;
    align-items: center;
    justify-content: center;
}

.tab-txt {
    width: 160px;
    text-align: center;
    font-size: 24px;
    line-height: 34px;
    padding-bottom: 8px;
    border-bottom-width: 6px;
    border-bottom-color: rgba(0,0,0,0);
    border-bottom-style: solid;
}

.tab-txt-active {
    color: #EF8A31;
    border-bottom-color: #EF8A31;
}

</style>
