<template>
    <div>
        <image class="images" resize="cover" :src="imgsrc" ref="images" :style="style" ></image>
    </div>
</template>

<script>
    import { mediaUrl } from '../../../config/apis'
    export default {
        name: 'customImg',
        data () {
            return {}
        },
        props: ['imgsrc'],
        created () {
            this.style = {
                width: '750px',
                height: '750px'
            }
            if (this.imgsrc.includes(mediaUrl)) {
                const imgs = this.imgsrc.split('/').reverse();
                const rect = imgs[1].split('x');
                this.style = {
                    width: '750px',
                    height: Math.floor(rect[1] / rect[0] * 750) + 'px'
                }
            }
        },
        methods: {}
    }
</script>

<style scoped>
    .images{}
</style>
