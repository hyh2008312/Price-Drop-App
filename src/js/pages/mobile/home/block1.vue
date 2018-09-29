<template>
    <div class="wrapper">
        <div class="tlt-box" v-for="item in category" @click="jumpActivity(item)">
            <image class="tlt-image" :src="'bmlocal://assets/category_' + item.name.toLowerCase() + '.png'"></image>
            <text class="tlt-bg" v-if="">{{item.name == 'Electronic'?'Electronics' : item.name}}</text>
        </div>
    </div>
</template>
<script>
import preload from '../common/preloadImg';

export default {
    components: {
        preload
    },
    props: ['category'],
    methods: {
        jumpActivity (item) {
            this.$router.open({
                name: 'goods.category',
                type: 'PUSH',
                params: {
                    id: item.id,
                    name: item.name
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
    background-color: #fff;
    padding: 16px 16px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-around;
}

.tlt-box {
    width: 120px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    position: relative;
    overflow: hidden;
}

.tlt-bg{
    margin-top: 6px;
    text-align: center;
    font-size: 24px;
    line-height: 28px;
    color: rgba(0,0,0, 0.87);
    font-family: ProximaNova;
}

.tlt-image {
    width: 80px;
    height: 80px;
    background-color: #fff;
}



</style>
