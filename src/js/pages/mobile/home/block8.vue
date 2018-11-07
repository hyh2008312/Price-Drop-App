<template>
    <div class="wrapper">
        <div class="gd-bg-bottom">
            <div class="i-good" v-for="i in item" @click="jumpItem(i.id)">
                <div class="item-bg">
                    <preload class="gd-img" :src="i.mainImage"></preload>
                </div>
                <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                <div class="gd-price-bg">
                    <text class="gd-price">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
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
    margin-top: 22px;
    padding-left: 32px;
    padding-right: 32px;
    flex-direction: row;
    align-items: start;
    justify-content: space-between;
    padding-bottom: 2px;
}

.item-bg{
    width: 218px;
    height: 218px;
}

.gd-img {
    width: 218px;
    height: 218px;
    overflow: hidden;
}

.i-good{
    width: 218px;
    box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
    border-radius: 8px;
    background-color: #fff;
    flex-direction: column;
    justify-content: start;
    align-items: center;
}

.gd-tlt {
    margin-top: 8px;
    font-size: 28px;
    font-weight: 700;
    line-height: 36px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    padding: 0 16px;
    text-align: center;
}

.gd-price {
    font-size: 20px;
    line-height: 28px;
    color: #fff;
    background-color: #00CFE3;
    border-radius: 14px;
    padding: 0 16px;
    text-align: center;
}

.gd-price-bg{
    margin-top: 4px;
    height: 28px;
    margin-bottom: 16px;
}

</style>
