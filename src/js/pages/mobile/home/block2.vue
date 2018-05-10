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
            </div>
            <div class="i-good" v-if="hasMore">
                <text class="gd-more" @click="jumpWeb(head.url)">查看全部</text>
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
    padding-bottom: 6px;
}

.tlt-box {
    height: 375px;
    width: 750px;
    display: flex;
    align-items: center;
    justify-content: center;
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
    color: rgba(255,255,255,0.87);
}

.tlt-hot {
    color: rgba(255,255,255,0.87);
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
    height: 500px;
    padding-top: 30px;
    padding-bottom: 30px;
    padding-left: 15px;
    padding-right: 15px;
}

.i-good {
    padding-left: 15px;
    padding-right: 15px;
}

.gd-bg {
    background-color: #f4f4f4;
}

.gd-img {
    height: 286px;
    width: 286px;
}

.gd-tlt {
    font-size: 28px;
    color: #333;
    width: 286px;
    margin-top: 16px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-info {
    display: block;
    font-size: 24px;
    width: 286px;
    margin-top: 8px;
    color: #7f7f7f;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-price {
    font-size: 28px;
    width: 286px;
    margin-top: 16px;
    color: #b4282d;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-more {
    height: 286px;
    width: 286px;
    font-size: 28px;
    line-height: 40px;
    border-width: 6px;
    padding-top: 120px;
    padding-bottom: 120px;
    border-color: #f4f4f4;
    text-align: center;
}
</style>
