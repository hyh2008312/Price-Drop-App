<template>
    <div>
        <image class="images" :src="imgsrc" ref="images" @load="onload" ></image>
    </div>
</template>

<script>
    const animation = weex.requireModule('animation')
    export default {
        name: 'customImg',
        data () {
            return {}
        },
        props: ['imgsrc'],
        created () {},
        methods: {
            onload (e) {
                // JSON.stringify(e)
                animation.transition(this.$refs.images, {
                    styles: {
                        height: Math.floor(e.size.naturalHeight / e.size.naturalWidth * 750) + 'px'
                    },
                    duration: 50,
                    timingFunction: 'ease',
                    delay: 0
                }, function () {});
            }
        }
    }
</script>

<style scoped>
    .images{
        height: 750px;
        width: 750px;
    }
</style>
