<template>
    <slider class="slider" auto-play="true" interval="5000">
        <div class="frame" v-for="img in imageList">
            <image class="image" resize="cover" :src="img.src"></image>
            <div class="slider-right">
                <text class="slider-tlt">UOOYAA·乌丫</text>
                <text class="slider-sub">印花木耳边雪纺拼接上衣T恤</text>
                <text class="slider-info">1200人已0元拿</text>
                <text class="slider-money">¥ 1299.00</text>
                <text class="slider-button">砍价立减</text>
            </div>
        </div>
        <indicator class="indicator"></indicator>
    </slider>
</template>
<script>
export default {
    props: ['imageList']
}
</script>
<style scoped>

.image {
    width: 316px;
    height: 316px;
    border-radius: 8px;
    border-width: 1px;
    border-style: solid;
    border-color: rgba(0,0,0,.12);
}

.slider {
    width: 718px;
    height: 438px;
    border-radius: 8px;
    box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
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
    font-weight: 500;
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
    color: rgba(0,0,0, .54);
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
