<template>
    <div class="wrapper">
        <div class="tlt-box" v-if="newGoods">
            <image class="tlt-image" resize="cover" src="http://doc.zwwill.com/yanxuan/imgs/bg-new.png"></image>
            <div class="tlt-bg">
                <text class="tlt tlt-new">{{head.tlt}}</text>
                <text class="btn-all" @click="jumpWeb(head.url)">查看全部</text>
            </div>
        </div>
        <div class="tlt-box" v-else-if="hotGoods">
            <image class="tlt-image" resize="cover" src="http://doc.zwwill.com/yanxuan/imgs/bg-hot.png"></image>
            <div class="tlt-bg">
                <text class="tlt tlt-hot">{{head.tlt}}</text>
                <text class="btn-all" @click="jumpWeb(head.url)">查看全部</text>
            </div>
        </div>
        <scroller class="box" scroll-direction="horizontal" flex-direction="row" show-scrollbar=false>
            <div class="i-good" v-for="i in goods" @click="jumpWeb(i.url)">
                <div class="gd-bg">
                    <image class="gd-img" resize="cover" :src="i.img"></image>
                </div>
                <text class="gd-tlt">{{i.tlt}}</text>
                <text class="gd-info">{{i.info}}</text>
                <text class="gd-price">¥{{i.price}}</text>
                <text class="gd-button">砍价立减</text>
            </div>
        </scroller>
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
    padding-bottom: 16px;
}

.tlt-box {
    height: 375px;
    width: 750px;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
}

.tlt-bg{
    width: 750px;
    height: 116px;
    align-items: center;
    justify-content: center;
}

.tlt {
    text-align: center;
    font-size: 32px;
    font-weight: 700;
    color: rgba(255,255, 255, 0.87);
}

.tlt-hot {
    color: rgba(255,255,255, 0.87);
}

.btn-all {
    text-align: center;
    font-size: 24px;
    width: 114px;
    line-height: 44px;
    justify-content: center;
    align-items: center;
    margin-top: 28px;
    color: rgba(255,255,255,0.87);
    border-style: solid;
    border-width: 1px;
    border-color: #fff;
}


.tlt-image {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

.box {
    width: 750px;
    height: 532px;
    padding-top: 16px;
    padding-bottom: 32px;
    padding-left: 20px;
    padding-right: 20px;
    flex-direction: row;
}

.i-good {
    padding-left: 8px;
    padding-right: 8px;
    width: 304px;
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
    border-width: 2px;
    border-color: #000;
    border-radius: 4px;
    line-height: 46px;
    width: 120px;
    text-align: center;
    justify-content: center;
    align-items: center;
}
</style>
