<template>
    <div class="wrapper">
        <slider class="slider" auto-play="true" interval="5000">
            <div class="inner-container" v-for="item in items">
                <image class="header" :src="item.bg" resize="contain"></image>
                <text class="tlt">{{item.name}}</text>
            </div>
        </slider>
    </div>
</template>
<script>
export default {
    props: ['items'],
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
    width: 750px;
    justify-content: center;
    align-items: center;
}

.slider{
    width: 718px;
    height: 96px;
}

.inner-container{
    width: 718px;
    height: 96px;
    background-color: #FFFFFF;
    box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    border-radius: 8px;
    justify-content: start;
    flex-direction: row;
    align-items: center;
    overflow: hidden;
}

.header{
    width: 64px;
    height: 64px;
    overflow: hidden;
    border-radius: 32px;
    margin-left: 32px;
}

.tlt {
    color: rgba(0,0,0,0.87);
    width: 582px;
    padding: 10px;
    font-size: 24px;
    lines: 1;
    margin-left: 20px;
    margin-right: 20px;
    text-overflow: ellipsis;
}

.gb-box {
    padding: 16px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-between;
}

.i-gd {
    width: 350px;
    height: 510px;
    margin-bottom: 20px;
}

.gd-bg {
    background-color: #f4f4f4;
}

.gd-img {
    width: 350px;
    height: 350px;
}

.gd-tlt {
    font-size: 28px;
    color: #333;
    width: 350px;
    margin-top: 15px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-info {
    display: block;
    font-size: 28px;
    width: 350px;
    height: 65px;
    padding-left: 10px;
    padding-right: 10px;
    padding-top: 15px;
    padding-bottom: 15px;
    color: #9F8A60;
    background-color: #F1ECE2;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-price {
    font-size: 28px;
    width: 350px;
    margin-top: 10px;
    color: #b4282d;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>
