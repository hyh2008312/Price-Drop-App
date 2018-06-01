<template>
    <div>
        <image class="images" :src="imgsrc" ref="images" @load="onload" ></image>
    </div>
</template>

<script>
    export default {
        name: 'customImg',
        data () {
            return {}
        },
        props: ['imgsrc', 'index'],
        created () {},
        methods: {
            onload (e) {
                this.$refs.images.height = Math.floor(e.size.naturalHeight / e.size.naturalWidth * 750) + 'px'
                this.$emit('resize', {
                    status: 'resize',
                    data: {
                        height: Math.floor(e.size.naturalHeight / e.size.naturalWidth * 750) + 'px',
                        index: this.index
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .images{
        width: 750px;
        height: 750px;
        overflow: hidden;
    }
</style>
