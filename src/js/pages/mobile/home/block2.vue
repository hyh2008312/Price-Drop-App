<template>
    <div class="wrapper">
        <div class="tlt-box" @click="jumpActivity">
            <image class="tlt-image" resize="cover" :src="head.image"></image>
            <div class="tlt-bg" v-if="false">
                <text class="tlt tlt-new">{{head.name}}</text>
                <text class="btn-all">See More</text>
            </div>
            <text class="iconfont tlt-tri">&#xe6ff;</text>
        </div>
        <scroller class="box" scroll-direction="horizontal" flex-direction="row" show-scrollbar=false>
            <div class="i-good" v-for="i in head.topicProducts" :key="i.id" @click="jumpWeb(i.productId)">
                <div class="gd-bg">
                    <div class="gd-img">
                        <preload class="gd-img-image" :src="i.mainImage"></preload>
                    </div>
                </div>
                <div class="gd-tlt-bg">
                    <text class="gd-tlt">Rs.{{i.lowestPrice}}</text>
                    <text class="gd-info">{{countOff(i.lowestPrice, i.saleUnitPrice)}}</text>
                </div>
                <text class="gd-price">Rs.{{i.saleUnitPrice}}</text>
                <text class="gd-button">Buy Now</text>
            </div>
        </scroller>
    </div>
</template>
<script>
import preload from '../common/preloadImg';

export default {
    components: {
        preload
    },
    props: ['head'],
    methods: {
        jumpActivity () {
            this.$router.open({
                name: 'mobile.activity',
                type: 'PUSH',
                params: {
                    id: this.head.id,
                    name: this.head.name,
                    imageUrl: this.head.image,
                    type: 'activity'
                }
            })
        },
        jumpWeb (id) {
            this.$router.open({
                name: 'goods.details',
                type: 'PUSH',
                params: {
                    id: id
                }
            })
        },
        countOff (s, o) {
            if (o > 0) {
                return Math.floor((o - s) / o * 100) + '% OFF'
            } else {
                return ''
            }
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
    overflow: hidden;
}

.tlt-bg{
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    width: 750px;
    height: 375px;
    align-items: center;
    justify-content: center;
    background-color: rgba(0,0,0,0.18);
}

.tlt {
    text-align: center;
    font-size: 32px;
    line-height: 38px;
    font-weight: 700;
    color: rgba(255,255, 255, 0.87);
}

.tlt-tri{
    position: absolute;
    left: 375px;
    bottom: -1px;
    transform: translateX(-17px) rotate(180deg);
    width: 34px;
    line-height: 17px;
    color: #fff;
}

.btn-all {
    text-align: center;
    font-size: 24px;
    width: 128px;
    line-height: 44px;
    justify-content: center;
    align-items: center;
    margin-top: 24px;
    color: rgba(255,255,255,0.87);
    border-style: solid;
    border-width: 2px;
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
    height: 474px;
    padding-top: 16px;
    padding-bottom: 32px;
    padding-left: 8px;
    padding-right: 8px;
    flex-direction: row;
}

.i-good {
    padding-left: 8px;
    padding-right: 8px;
    width: 304px;
    align-items: center;
}

.gd-bg {
    height: 288px;
    width: 288px;
    border-radius: 8px;
    overflow: hidden;
    border-top-width: 1px;
    border-top-style: solid;
    border-top-color: rgba(0,0,0,.08);
    border-left-width: 1px;
    border-left-style: solid;
    border-left-color: rgba(0,0,0,.08);
    border-right-width: 1px;
    border-right-style: solid;
    border-right-color: rgba(0,0,0,.08);
    border-bottom-width: 2px;
    border-bottom-style: solid;
    border-bottom-color: rgba(0,0,0,.08);
}

.gd-img {
    width: 284px;
    height: 284px;
    border-radius: 8px;
    overflow: hidden;
}
.gd-img-image {
    width: 284px;
    height: 284px;
    border-radius: 8px;
    overflow: hidden;
    position: relative;
}

.gd-tlt {
    font-size: 28px;
    font-weight: bold;
    color: rgba(0,0,0,0.87);
    line-height: 34px;
}

.gd-tlt-bg{
    margin-top: 16px;
    width: 288px;
    flex-direction: row;
    align-items: center;
    justify-content: center;
}

.gd-info{
    margin-left: 8px;
    font-size: 20px;
    line-height: 24px;
    color: #EF8A31;
}

.gd-price {
    font-size: 20px;
    font-weight: 500;
    width: 288px;
    line-height: 24px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
    text-decoration: line-through;
}

.gd-button{
    margin-top: 16px;
    font-size: 24px;
    font-weight: bold;
    border-style: solid;
    border-width: 2px;
    border-color: #EF8A31;
    color: #EF8A31;
    border-radius: 4px;
    line-height: 44px;
    width: 144px;
    text-align: center;
    justify-content: center;
    align-items: center;
}
</style>
