<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="i-gd"  @click="jumpWeb()">
                <div class="gd-bg">
                    <image class="gd-img" resize="cover" :src="goods.mainImage || src"></image>
                </div>
                <div class="gd-bg-right">
                    <text class="gd-info">{{goods.brandName}}</text>
                    <text class="gd-tlt">{{goods.title}}</text>
                    <text class="gd-sm">{{goods.cutGet? goods.cutGet: 0}}人已砍到了1折</text>
                    <text class="gd-price">¥ {{goods.saleUnitPrice}}</text>
                    <text class="gd-button">砍价立减</text>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: ['goods'],
    data () {
        return {
            src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
        }
    },
    methods: {
        jumpWeb () {
            // if (!url) return;
            this.$router.open({
                name: 'goods.details'
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
    padding-top: 26px;
    width: 750px;
}

.gb-box {
    padding: 0 16px;
    display: flex;
}

.i-gd {
    width: 718px;
    height: 288px;
    flex-direction: row;
}

.gd-bg {
    width: 288px;
    height: 288px;
    border-radius: 8px;
    overflow: hidden;
    border-width: 1px;
    border-style: solid;
    border-color: rgba(0,0,0,.12);
}

.gd-bg-right {
    margin-left: 32px;
    width: 398px;
    height: 288px;
}

.gd-img {
    width: 286px;
    height: 286px;
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
    font-weight: 600;
    margin-top: 16px;
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
    font-weight: 500;
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
    color: #AC0B0B;
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
