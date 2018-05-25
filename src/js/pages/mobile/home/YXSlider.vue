<template>
    <slider class="slider" auto-play="true" interval="5000">
        <div class="frame" v-for="img in imageList"  @click="jumpDetail(img.id)">
            <div class="image-bg">
                <image class="image" resize="cover" :src="img.mainImage || src"></image>
            </div>
            <div class="slider-right">
                <text class="slider-tlt">{{img.brandName}}</text>
                <text class="slider-sub">{{img.title}}</text>
                <text class="slider-info">{{img.cutGet? img.cutGet: 0}}人已砍到了1折</text>
                <text class="slider-money">¥ {{img.saleUnitPrice}}</text>
                <text class="slider-button">砍价立减</text>
            </div>
        </div>
        <indicator class="indicator"></indicator>
    </slider>
</template>
<script>
export default {
    props: ['imageList'],
    data () {
        return {
            src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
        }
    },
    methods: {
        jumpDetail (id) {
            this.$router.open({
                name: 'goods.details',
                params: {
                    id: id
                }
            })
        }
    }
}
</script>
<style scoped>

.image {
    width: 314px;
    height: 314px;
}

.image-bg {
    width: 316px;
    height: 316px;
    border-radius: 8px;
    border-width: 1px;
    border-style: solid;
    border-color: rgba(0,0,0,.12);
    overflow: hidden;
}

.slider {
    width: 718px;
    height: 438px;
    border-radius: 8px;
    box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    overflow: hidden;
}

.slider-right{
    margin-left: 20px;
    width: 342px;
    height: 320px;
    flex-direction: column;
    justify-content: start;
    align-items: start;
}

.slider-tlt {
    margin-top: 20px;
    font-size: 28px;
    line-height: 48px;
    font-weight: 600;
    color: rgba(0,0,0, .87);
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.slider-sub {
    margin-top: 4px;
    font-size: 24px;
    line-height: 48px;
    color: rgba(0,0,0, .87);
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-weight: 300;
}

.slider-info {
    margin-top: 4px;
    font-size: 20px;
    line-height: 28px;
    color: #AC0B0B;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.slider-money {
    margin-top: 62px;
    font-size: 28px;
    line-height: 40px;
    font-weight: 500;
    color: #957914;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.slider-button{
    margin-top: 8px;
    font-size: 24px;
    line-height: 52px;
    border-style: solid;
    border-width: 2px;
    border-color: #000;
    border-radius: 4px;
    width: 144px;
    color: #000;
    text-align: center;
}

.frame {
    width: 718px;
    height: 438px;
    position: relative;
    border-radius: 8px;
    overflow: hidden;
    padding-top: 27px;
    padding-left: 20px;
    padding-right: 20px;
    background-color: #fff;
    flex-direction: row;
}

.indicator {
    width: 718px;
    height: 12px;
    item-color: rgba(0,0,0,.24);
    item-selected-color: #957914;
    item-size: 12px;
    position: absolute;
    bottom: 32px;
    right: 0px;
}
</style>
