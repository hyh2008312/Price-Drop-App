<template>
    <div style="margin-bottom: 8px">
        <div class="all"></div>
        <div class="value" :style="pwidth"></div>
        <text class="goods-bottom-word">{{percentNum}}% Sold</text>
    </div>
</template>

<script>
    export default {
        props: ['all', 'value', 'pId'],
        name: 'progressBar',
        data () {
            return {
                // all: 50,
                // value: 5,
                // pId: 1191,
                len: '',
                percentNum: ''

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
                if ((tmp * 100) >= 50) {
                    this.len = tmp * 320
                } else {
                    let num, num1;
                    num = parseInt(this.pId.toString().split('').reverse().splice(0, 2).reverse().join(''))
                    num1 = parseInt(this.pId.toString().split('').reverse()[0])

                    if (num < 9) {
                        tmp = (30 + num1)
                    } else if (num >= 10 && num <= 50) {
                        tmp = num
                    } else if (num >= 51 && num <= 99) {
                        tmp = (num / 2)
                    } else {
                        tmp = 50
                    }
                    this.len = (tmp / 100) * 320
                    this.percentNum = parseInt(tmp)
                    // this.$emit('percent', this.percentNum)
                }
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
    .goods-bottom-word{
        opacity: 0.38;
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: #000000;
        letter-spacing: 0;
        margin-top: 8px;
        text-align: left;
    }

</style>
