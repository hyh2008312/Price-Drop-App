<template>
    <div>
        <div class="h-s-d">
            <image class="star-icon" v-for="i in starArr" src="bmlocal://assets/star/1.0.png"></image>
            <image class="star-icon" :src="'bmlocal://assets/star/0.' + decimal + '.png'"></image>
            <image class="star-icon" v-for="i in grayStarArr" src="bmlocal://assets/star/0.0.png"></image>
        </div>
    </div>
</template>

<script>
    export default {
        name: "star-item",
        data () {
            return {
                starArr: [0, 1, 2, 3, 4],
                grayStarArr: [0, 1, 2, 3, 4],
                decimal: 0
            }
        },
        props: ['points'],
        created () {
           this.tranStar()
        },
        methods: {
            tranStar () {
                // let a = this.points.splice('.')
                const a = String(this.points).split('.');
                this.starArr.length = a[0]
                if (a[1] <= 3) {
                    this.decimal = 3
                } else if (a[1] > 3 && a[1] <= 5) {
                    this.decimal = 5
                } else if (a[1] > 5 && a[1] <= 7) {
                    this.decimal = 7
                }
                this.grayStarArr.length = 5 - (this.starArr.length + 1)
            }
        }
    }
</script>

<style scoped>
    .h-s-d{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .star-icon{
        width: 24px;
        height: 24px;
        margin-left: 4px;
    }
</style>
