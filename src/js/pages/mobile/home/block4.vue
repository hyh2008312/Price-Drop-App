<template>
    <div class="wrapper">
        <div class="inner-container" v-for="item in items">
            <image class="header" :src="item.bg"></image>
            <text class="tlt">{{item.name}}</text>
        </div>
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
    margin-top: 16px;
    margin-bottom: 16px;
    width: 750px;
    justify-content: center;
    flex-direction: column;
    align-items: center;
}

.inner-container{
    width: 718px;
    height: 96px;
    background-color: #FFFFFF;
    box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    border-radius: 4px;

}

.header{
    width: 64px;

}

.tlt {
    text-align: center;
    color: #333;
    width: 750px;
    padding: 10px;
    font-size: 30px;
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
