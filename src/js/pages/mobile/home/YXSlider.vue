<template>
    <slider class="slider" auto-play="true" interval="5000">
        <div class="frame" v-for="img in imageList">
            <image class="image" resize="cover" :src="img.src"></image>
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
.iconfont {
    font-family: iconfont;
}

.image {
    width: 718px;
    height: 438px;
}

.slider {
    width: 718px;
    height: 438px;
    border-radius: 8px;
    box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
}

.frame {
    width: 718px;
    height: 438px;
    position: relative;
    border-radius: 8px;
    overflow: hidden;
    background-color: #fff;
}

.indicator {
    width: 718px;
    height: 12px;
    item-color: white;
    item-selected-color: #957914;
    item-size: 12px;
    position: absolute;
    bottom: 32px;
    right: 0px;
}
</style>
