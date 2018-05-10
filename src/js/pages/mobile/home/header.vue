<template>
    <div class="wrapper">
        <text class="search iconfont" @click="jumpWeb()">&#xe6f0;  搜索</text>
        <div class="notice">
            <text class="ic iconfont">&#xe6f0;</text>
            <text class="txt">规则</text>
        </div>
    </div>
</template>
<script>
export default {
    methods: {
        jumpWeb (_url) {
            if (!_url) _url = 'http://m.you.163.com/search';
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
    position: fixed;
    top: 0;
    /*margin-top: 40;*/
    left: 0;
    right: 0;
    height: 152px;
    padding-top: 48px;
    display: flex;
    flex-wrap: nowrap;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    z-index: 101;
    background-color: #000;
}

.scan,
.notice {
    height: 80px;
    width: 100px
}

.ic,
.txt{
    text-align: center;
    color: #fff;
    font-weight: 300;
}

.txt{
    margin-top: 8px;
    font-size: 20px;
}

.ic {
    font-size: 32px;
}

.search {
    flex: 1;
    height: 80px;
    font-size: 26px;
    color: rgba(0,0,0,0.54);
    background-color: #fff;
    border-radius: 6px;
    align-items: center;
    margin-left: 32px;
    padding: 22px 16px;
}
</style>
