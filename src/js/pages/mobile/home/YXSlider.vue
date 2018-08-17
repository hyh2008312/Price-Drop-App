<template>
    <slider class="slider" auto-play="true" interval="5000">
        <div class="frame" v-for="img in imageList"  @click="jumpDetail(img.url)">
            <image class="image" resize="cover" :src="img.image"></image>
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
                name: 'mobile.activity',
                params: {
                    id: 2,
                    imageUrl: 'http://image.getpricedrop.com/source/banner/cdd98933-2637-405c-bf01-7947c1b09732.jpg',
                    name: '70% OFF',
                    type: 'activity'
                }
            })
        }
    }
}
</script>
<style scoped>

.image {
    width: 750px;
    height: 333px;
}

.slider {
    width: 750px;
    height: 333px;
    overflow: hidden;
}

.frame {
    width: 750px;
    height: 333px;
    position: relative;
    overflow: hidden;
    background-color: #fff;
}

.indicator {
    width: 718px;
    height: 12px;
    item-color: rgba(0,0,0,.24);
    item-selected-color: #EF8A31;
    item-size: 12px;
    position: absolute;
    bottom: 32px;
    right: 0px;
}
</style>
