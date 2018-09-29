<template>
    <slider class="slider" auto-play="true" interval="5000" @change='change' :index="index">
        <div class="frame" v-for="img in imageList"  @click="jumpDetail(img)">
            <image class="image" resize="cover" :src="img.image"></image>
        </div>
        <indicator class="indicator"></indicator>
    </slider>
</template>
<script>
export default {
    props: ['imageList', 'index'],
    data () {
        return {
            src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
        }
    },
    methods: {
        jumpDetail (img) {
            if (img.title == 'mobile.activity') {
                this.$router.open({
                    name: 'mobile.activity',
                    params: {
                        id: 2,
                        imageUrl: 'img.image',
                        name: '70% OFF',
                        type: 'activity'
                    }
                });
            } else if (img.title == 'flash') {
                this.$router.open({
                    name: 'flash',
                    type: 'PUSH'
                    // params: {
                    //     card: id
                    // }
                })
            } else if (img.title == 'luckydraw') {
                this.$event.emit('changeTab', {
                    tab: 'luckydraw'
                });
            } else if (img.title == 'rewards') {
                this.$event.emit('changeTab', {
                    tab: 'rewards'
                });
            }
        },
        change (event) {
            this.index = event.index;
            this.$emit('changeColor', {
                status: 'changeColor',
                data: {
                    color: this.imageList[event.index].color
                }
            })
        }
    }
}
</script>
<style scoped>

.image {
    width: 686px;
    height: 303px;
}

.slider {
    width: 686px;
    height: 303px;
    overflow: hidden;
    border-radius: 16px;
}

.frame {
    width: 686px;
    height: 303px;
    position: relative;
    overflow: hidden;
    background-color: #fff;
    border-radius: 16px;
}

.indicator {
    width: 686px;
    height: 12px;
    item-color: rgba(0,0,0,.24);
    item-selected-color: #EF8A31;
    item-size: 12px;
    position: absolute;
    bottom: 32px;
    right: 0px;
}
</style>
