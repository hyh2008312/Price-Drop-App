<template>
    <div class="images">
        <image resize="stretch" :src="imgsrc" :style="{width: '750px', height: height}" @load="onload"></image>
    </div>
</template>

<script>
    export default {
        name: 'customImg',
        data () {
            return {
                height: '750px'
            }
        },
        props: ['imgsrc'],
        computed: {
            height: {
                get: function () {
                    return this.height
                },

                set: function (v) {
                    this.height = v
                }
            }
        },
        created () {},
        methods: {
            onload (e) {
                if (e.success) {
                    this.height = Math.floor(e.size.naturalHeight / e.size.naturalWidth * 750) + 'px'
                }
            }
        }
    }
</script>

<style scoped>
    .images{}
</style>
