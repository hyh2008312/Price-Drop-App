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
    height: 96px;
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
</style>
