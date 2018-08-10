<template>
    <div class="wrapper">
        <div class="gb-box" v-for="item in goods" @click="jumpWeb(item)">
            <div class="gd-bg">
                <preload class="gd-img-image" :src="item.mainImage"></preload>
            </div>
            <div class="gd-bg-right">
                <div class="gd-sm">
                    <text class="gd-price">Rs.{{item.unitPrice}}</text>
                    <text class="gd-price-1">{{countOff(item.unitPrice, item.saleUnitPrice)}}</text>
                </div>
                <text class="gd-info">Rs.{{item.saleUnitPrice}}</text>
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
    flex-direction: column;
    align-items: center;
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
    font-size: 28px;
    line-height: 34px;
    font-weight: bold;
    color: rgba(0,0,0,0.87);
}

.gd-price-1{
    font-family: ProximaNova;
    font-size: 20px;
    line-height: 24px;
    margin-left: 8px;
    color: #EF8A31;
}

.gd-sm {
    margin-top: 6px;
    flex-direction: row;
    justify-content: start;
    align-items: center;
}
</style>
