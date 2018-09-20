<template>
    <div class="wrapper">
        <div class="gb-box" v-for="item in goods" @click="jumpWeb(item)">
            <div class="gd-bg">
                <preload class="gd-img-image" :src="item.mainImage"></preload>
                <text class="gd-logo" v-if="item.purchaseMethod == 'flash'">FLASH</text>
            </div>
            <div class="gd-bg-right">
                <text class="gd-title">{{item.title}}</text>
                <text class="gd-price">₹{{item.unitPrice}}</text>
                <div class="gd-sm">
                    <text class="gd-info">₹{{item.saleUnitPrice}}</text>
                    <text class="gd-price-1">{{countOff(item.unitPrice, item.saleUnitPrice)}}</text>
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
        jumpWeb (item) {
            const id = item.id;
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
    padding: 24px 32px 0 32px;
    width: 750px;
    flex-direction: row;
    justify-content: space-between;
}



.gb-box {
    width: 327px;
}

.gd-bg {
    width: 327px;
    height: 327px;
    border-radius: 8px;
    overflow: hidden;
}

.gd-bg-right {
    width: 327px;
}

.gd-img-image {
    width: 327px;
    height: 327px;
    border-radius: 8px;
    overflow: hidden;
    position: relative;
}


.gd-info {
    display: block;
    font-size: 20px;
    line-height: 24px;
    text-decoration: line-through;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
    font-family: ProximaNova;
}

.gd-price {
    font-family: ProximaNova;
    margin-top: 8px;
    font-size: 28px;
    line-height: 34px;
    font-weight: 700;
    color: rgba(0,0,0,0.87);
}

.gd-price-1{
    font-family: ProximaNova;
    font-size: 20px;
    line-height: 24px;
    margin-left: 16px;
    color: #EF8A31;
}

.gd-sm {
    flex-direction: row;
    justify-content: start;
    align-items: center;
}

.gd-title{
    margin-top: 12px;
    opacity: 0.54;
    font-family: ProximaNova-Regular;
    font-size: 20px;
    line-height: 24;
    color: rgba(0,0,0,0.87);
    letter-spacing: 0;
    lines: 2;
    height: 48px;
    text-overflow: ellipsis;
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
