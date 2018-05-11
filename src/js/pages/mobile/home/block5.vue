<template>
    <div class="wrapper">
        <scroller class="box" scroll-direction="horizontal" flex-direction="row" show-scrollbar=false>
            <div class="i-good" v-for="i in logo" @click="jumpWeb(i.url)">
                <div class="gd-bg">
                    <image class="gd-img" resize="cover" :src="i.img"></image>
                </div>
            </div>
        </scroller>
    </div>
</template>
<script>
export default {
    props: ['logo'],
    methods: {
        jumpWeb (_url) {
            if (!_url) return;
            this.$router.toWebView({
                url: _url,
                title: '',
                navShow: true
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
