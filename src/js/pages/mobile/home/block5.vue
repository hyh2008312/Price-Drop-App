<template>
    <div class="wrapper">
        <scroller class="box" scroll-direction="horizontal" flex-direction="row" show-scrollbar=false>
            <div class="i-good" v-for="i in logo" @click="jumpActivity(i)">
                <div class="gd-bg">
                    <preload class="gd-img" :src="i.logo"></preload>
                </div>
            </div>
        </scroller>
    </div>
</template>
<script>
import preload from '../common/preloadImg';
export default {
    components: {
        preload
    },
    props: ['logo'],
    methods: {
        jumpActivity (item) {
            this.$router.open({
                name: 'mobile.activity',
                type: 'PUSH',
                params: {
                    id: item.id,
                    name: item.name,
                    imageUrl: item.logo,
                    type: 'brand'
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
}

.box {
    width: 750px;
    height: 192px;
    padding-top: 52px;
    flex-direction: row;
}

.i-good {
    padding-left: 24px;
    padding-right: 24px;
    align-items: center;
}

.gd-bg {
    background-color: #fff;
}

.gd-img {
    height: 88px;
    width: 88px;
}
</style>
