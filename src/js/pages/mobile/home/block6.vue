<template>
    <div class="wrapper">
        <div class="inner-box" @click="jumpActivity(i)">
            <text class="title">Invite Friends to Unlock ₹0 Price!</text>
            <div class="drop-bg">
                <div class="i-good" v-for="i in drops" >
                    <div class="gd-bg">
                        <div class="gd-img">
                            <preload class="gd-img-image" :src="i.mainImage"></preload>
                        </div>
                    </div>
                    <text class="gd-tlt">₹{{i.lowestPrice}}</text>
                    <text class="gd-price">₹{{i.saleUnitPrice}}</text>
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
    props: ['drops'],
    methods: {
        jumpActivity (item) {
            this.$event.emit('changeTab', { tab: 'drops' });
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
    height: 412px;
    width: 750px;
    align-items: center;
}

.inner-box {
    height: 412px;
    width: 686px;
    background-color: #fff;
    border-radius: 16px;
    box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
}

.title{
    font-family: ProximaNova;
    font-weight: bold;
    font-size: 32px;
    line-height: 38px;
    margin-top: 48px;
    margin-bottom: 32px;
    width: 686px;
    color: #000000;
    text-align: center;
}

.drop-bg{
    width: 686px;
    height: 294px;
    flex-direction: row;
    justify-content: space-between;
    align-items: start;
    padding-left: 32px;
    padding-right: 32px;
}

.i-good{
    width: 186px;
    height: 280px;
}

.gd-bg {
    height: 186px;
    width: 186px;
    border-radius: 4px;
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
    width: 182px;
    height: 182px;
    border-radius: 4px;
    overflow: hidden;
}
.gd-img-image {
    width: 182px;
    height: 182px;
    border-radius: 4px;
    overflow: hidden;
    position: relative;
}

.gd-tlt {
    margin-top: 8px;
    font-size: 28px;
    font-family: ProximaNova;
    font-weight: bold;
    color: rgba(0,0,0,0.87);
    line-height: 34px;
    width: 186px;
    text-align: center;
}

.gd-price {
    font-size: 20px;
    width: 186px;
    line-height: 24px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
    text-decoration: line-through;
}
</style>
