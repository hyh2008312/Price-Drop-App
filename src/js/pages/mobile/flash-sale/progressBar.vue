<template>
    <div style="margin-bottom: 8px">
        <div class="all"></div>
        <div class="value" :style="pwidth"></div>
    </div>
</template>

<script>
    export default {
        props: ['all', 'value'],
        name: 'progressBar',
        data () {
            return {
                // all: '',
                // value: '',
                len: '',
                // pstyle: {
                //     width:
                // }
            }
        },
        created () {
            this.init()
            this.pwidth = { width: this.len + 'px' }
        },
        methods: {
            init () {
                let tmp = 0;
                tmp = this.value / this.all
                this.len = tmp * 320
            }
        }
    }
</script>

<style scoped>
    .all{
        background-color: #000;
        opacity: 0.12;
        width: 320px;
        height: 8px;
        border-radius: 8px;
    }
    .value{
        position: absolute;
        top: 0;
        left: 0;
        background-color: #4DD0E1;
        opacity: 0.53;
        /*width: 160px;*/
        height: 8px;
        border-radius: 8px;
    }

</style>
