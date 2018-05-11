<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="i-gd" v-for="i in goods" @click="jumpWeb(i.url)">
                <div class="gd-bg">
                    <image class="gd-img" resize="cover" :src="i.img"></image>
                </div>
                <div class="gd-bg-right">
                    <text class="gd-info">{{i.info}}</text>
                    <text class="gd-tlt">{{i.tlt}}</text>
                    <text class="gd-sm">{{i.info}}</text>
                    <text class="gd-price">¥ {{i.price}}</text>
                    <text class="gd-button">砍价立减</text>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: ['newGoods', 'hotGoods', 'head', 'hasMore', 'goods'],
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
    padding-bottom: 10px;
    padding-top: 26px;
    width: 750px;
}

.gb-box {
    padding: 16px;
    display: flex;
}

.i-gd {
    width: 718px;
    height: 288px;
    margin-bottom: 32px;
    flex-direction: row;
    justify-content: start;
    align-items: stretch;
}

.gd-bg {
    width: 288px;
    height: 288px;
    background-color: #f4f4f4;
}

.gd-bg-right {
    margin-left: 32px;
    width: 398px;
    height: 288px;
}

.gd-img {
    width: 288px;
    height: 288px;
}

.gd-tlt {
    margin-top: 16px;
    font-size: 24px;
    font-weight: 300;
    line-height: 40px;
    width: 398px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-info {
    display: block;
    font-size: 28px;
    width: 398px;
    line-height: 40px;
    color: rgba(0,0,0,0.87);
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-price {
    font-size: 28px;
    width: 398px;
    margin-top: 32px;
    color: #987D1A;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-sm {
    margin-top: 4px;
    font-size: 20px;
    line-height: 28px;
    color: rgba(0,0,0, 0.54);
    width: 398px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-button {
    margin-top: 8px;
    width: 144px;
    text-align: center;
    font-size: 24px;
    line-height: 56px;
    border-style: solid;
    border-radius: 4px;
    border-width: 2px;
    border-color: #000;
}
</style>
