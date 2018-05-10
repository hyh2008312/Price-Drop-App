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
    props: ['newGoods', 'hotGoods', 'head', 'hasMore', 'logo'],
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
}

.i-good {
    padding-left: 8px;
    padding-right: 8px;
    align-items: center;
}

.gd-bg {
    background-color: #f4f4f4;
}

.gd-img {
    height: 288px;
    width: 288px;
}

.gd-tlt {
    font-size: 24px;
    color: rgba(0,0,0,0.87);
    width: 288px;
    margin-top: 16px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
    line-height: 34px;
}

.gd-info {
    font-size: 20px;
    width: 286px;
    margin-top: 8px;
    color: rgba(0,0,0, .54);
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
    line-height: 28px;
}

.gd-price {
    font-size: 28px;
    width: 288px;
    line-height: 40px;
    margin-top: 6px;
    color: #957914;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
}

.gd-button{
    margin-top: 14px;
    font-size: 24px;
    border-style: solid;
    border-width: 1px;
    border-color: #000;
    border-radius: 4px;
    line-height: 50px;
    width: 120px;
    text-align: center;
    justify-content: center;
    align-items: center;
}
</style>
