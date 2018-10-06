<template>
    <div class="wrapper">
        <div class="gd-bg-bottom">
            <div class="i-good" v-for="i in item.goodsList" @click="jumpItem(i.id)">
                <div class="item-bg">
                    <preload class="gd-img" :src="i.mainImage"></preload>
                </div>
                <text class="gd-tlt">₹{{i.unitPrice}}</text>
                <text class="gd-price">₹{{i.saleUnitPrice}}</text>
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
    props: ['item'],
    methods: {
        jumpItem (id) {
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
    width: 750px;
}

.gd-bg-bottom {
    width: 750px;
    padding-bottom: 48px;
    padding-left: 32px;
    padding-right: 32px;
    flex-direction: row;
    align-items: start;
    justify-content: space-between;
}

.item-bg{
    width: 202px;
    height: 186px;
    padding-top: 16px;
    padding-left: 16px;
    padding-right: 16px;
}

.gd-img {
    width: 170px;
    height: 170px;
    overflow: hidden;
}

.i-good{
    width: 202px;
    box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
    border-radius: 8px;
    background-color: #fff;
}

.gd-tlt {
    margin-top: 8px;
    font-size: 24px;
    font-weight: bold;
    line-height: 34px;
    width: 202px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    padding: 0 16px;
    color: #EF8A31;
    text-align: center;
}

.gd-price {
    font-size: 20px;
    line-height: 24px;
    color: #000;
    text-decoration: line-through;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    padding: 0 16px;
    text-align: center;
    margin-bottom: 32px;
}

</style>
