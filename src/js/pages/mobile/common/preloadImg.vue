<template>
    <div>
        <image class="images" resize="stretch" :src="src" @load="onload" :style="{opacity: opacity}"></image>
        <image class="images" resize="stretch" src="bmlocal://assets/occupy.png" :style="{opacity: opacity1}"></image>
    </div>
</template>

<script>
    export default {
        name: 'customImg',
        data () {
            return {
                opacity: 0,
                opacity1: 1
            }
        },
        props: ['src'],
        computed: {
            opacity: {
                get: function () {
                    return this.opacity
                },

                set: function (v) {
                    this.opacity = v
                }
            },
            opacity1: {
                get: function () {
                    return this.opacity1
                },

                set: function (v) {
                    this.opacity1 = v
                }
            }
        },
        methods: {
            onload (e) {
                if (e.success) {
                    this.opacity = 1
                    this.opacity1 = 0
                }
            }
        }
    }
</script>

<style scoped>
    .images{
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
    }
</style>
