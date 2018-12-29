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
            if (img.title == 'activity') {
                const params = {};
                const items = JSON.parse(img.params);
                for (const item in items) {
                    params[item] = items[item];
                }
                this.$router.open({
                    name: 'mobile.activity',
                    params: {
                        id: params.id
                    }
                });
            } else if (img.title == 'event') {
                const params = {};
                const items = JSON.parse(img.params);
                for (const item in items) {
                    params[item] = items[item];
                }
                if (params.templateId == '1') {
                    this.$router.open({
                        name: 'mobile.activity.topic1',
                        params: {
                            id: params.id
                        }
                    });
                } else if (params.templateId == '2') {
                    this.$router.open({
                        name: 'mobile.activity.topic2',
                        params: {
                            id: params.id
                        }
                    });
                }
            } else if (img.title == 'flash') {
                this.$router.open({
                    name: 'flash',
                    type: 'PUSH'
                })
            } else if (img.title == 'luckydraw') {
                this.$router.open({
                    name: 'raffle',
                    type: 'PUSH'
                });
            } else if (img.title == 'rewards') {
                this.$router.open({
                    name: 'perks',
                    type: 'PUSH'
                });
            } else if (img.title == 'reward') {
                this.$event.emit('changeTab', {
                    tab: 'rewards'
                });
            } else if (img.title == 'drops') {
                this.$event.emit('changeTab', {
                    tab: 'drops'
                });
            } else if (img.title == 'category') {
                const params = {};
                const items = JSON.parse(img.params);
                for (const item in items) {
                    params[item] = items[item];
                }
                this.$router.open({
                    name: 'goods.category',
                    type: 'PUSH',
                    params: {
                        id: params.id,
                        name: params.name
                    }
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
    background-color: #f4f3f2;
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
