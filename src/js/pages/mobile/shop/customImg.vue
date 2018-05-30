<template>
    <div>
        <image :src="imgsrc" :style="styObj" @load="onload" ></image>
    </div>
</template>

<script>
    export default {
        name: 'customImg',
        data () {
            return {
                styObj: {
                    'width': '750px',
                    'height': '750px'
                }
            }
        },
        props: ['imgsrc'],
        created () {},
        methods: {
            onload (e) {
                // JSON.stringify(e)
                this.styObj.height = Math.floor(e.size.naturalHeight / e.size.naturalWidth * 750) + 'px'
                this.size = e.size.naturalWidth + ',' + e.size.naturalHeight;
                // this.$notice.alert({
                //     message: this.styObj.height
                // })
            }
        }
    }
</script>

<style scoped>

</style>
