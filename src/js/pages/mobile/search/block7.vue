<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="i-gd"  @click="jumpWeb()">
                <div class="gd-bg">
                    <div class="gd-img">
                        <preload class="gd-img-image" :src="goods.mainImage"></preload>
                        <text class="gd-logo" v-if="goods.purchaseMethod == 'flash'">FLASH</text>
                    </div>
                </div>
                <div class="gd-bg-right">
                    <div>
                        <text class="gd-tlt">{{goods.title}}</text>
                        <div class="gd-sm">
                            <text class="gd-price">₹{{goods.unitPrice}}</text>
                            <text class="gd-price-1">{{countOff(goods.unitPrice, goods.saleUnitPrice)}}</text>
                        </div>
                        <text class="gd-info">₹{{goods.saleUnitPrice}}</text>
                    </div>
                    <text class="gd-button">Buy Now</text>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import preload from '../common/preloadImg';

export default {
    components: {
        preload
    },
    props: ['goods', 'tab'],
    data () {
        return {
            src: 'bmlocal://assets/occupy.png'
        }
    },
    methods: {
        jumpWeb () {
            let id = '';
            if (this.tab == 'hot') {
                id = this.goods.productId
            } else {
                id = this.goods.id
            }
            // if (!url) return;
            this.$router.open({
                name: 'goods.details',
                type: 'PUSH',
                params: {
                    id
                }
            })
        },
        countOff (s, o) {
            if (o > 0) {
                return Math.ceil((o - s) / o * 100) + '% OFF'
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
    padding-top: 26px;
    width: 750px;
}

.gb-box {
    padding: 0 32px;
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

.gd-bg-right {
    margin-left: 32px;
    width: 366px;
    height: 288px;
    justify-content: space-between;
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
    margin-top: 16px;
    font-size: 24px;
    font-weight: 300;
    line-height: 36px;
    width: 366px;
    overflow: hidden;
    lines: 2;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-info {
    display: block;
    margin-top: 4px;
    font-size: 20px;
    width: 366px;
    line-height: 24px;
    color: rgba(0,0,0,0.87);
    text-decoration: line-through;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.gd-price {
    font-size: 28px;
    line-height: 34px;
    font-weight: bold;
    color: #000;
}

.gd-price-1{
    font-size: 20px;
    line-height: 24px;
    margin-left: 8px;
    color: #EF8A31;
}

.gd-sm {
    margin-top: 14px;
    flex-direction: row;
    justify-content: start;
    align-items: center;
}

.gd-button {
    margin-bottom: 24px;
    width: 144px;
    text-align: center;
    font-size: 24px;
    line-height: 44px;
    font-weight: bold;
    border-style: solid;
    border-radius: 4px;
    border-width: 2px;
    border-color: #EF8A31;
    color: #EF8A31;
}

.gd-logo{
    font-size: 20px;
    line-height: 64px;
    text-align: center;
    width: 64px;
    border-radius: 8px;
    color: #fff;
    background-color: #00CFE3;
    position: absolute;
    left: 0;
    top: 0;
}
</style>
