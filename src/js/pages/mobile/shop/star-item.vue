<template>
    <div>
        <div class="h-s-d" v-if="points">
            <image class="star-icon" v-if="starArr.length>0" v-for="i in starArr" src="bmlocal://assets/star/1.0.png"></image>

            <image class="star-icon" v-if="decimal>0" :src="'bmlocal://assets/star/0.' + decimal + '.png'"></image>

            <image class="star-icon"  v-for="i in grayStarArr" src="bmlocal://assets/star/0.0.png"></image>
        </div>

        <div class="h-s-d" v-if="!points">
            <!--<image class="star-icon" v-for="i in starArr" src="bmlocal://assets/star/1.0.png"></image>-->
            <!--<image class="star-icon" :src="'bmlocal://assets/star/0.' + decimal + '.png'"></image>-->
            <image class="star-icon"  v-for="i in grayStarArr" src="bmlocal://assets/star/0.0.png"></image>
            <!--<text>{{points}}</text>  <!--空的站位图-->
        </div>
    </div>
</template>

<script>
    export default {
        name: 'star-item',
        data () {
            return {
                starArr: [1, 1, 1, 1, 1],
                grayStarArr: [0, 0, 0, 0, 0],
                // starArr: [],
                // grayStarArr: [],
                decimal: 0,
                point: ''
            }
        },
        props: ['points'],
        created () {
            // this.point = this.points
        },
        mounted () {
            // this.$notice.alert({
            //     message: this.points
            // })
        },
        watch: {
            'points': function (newData, oldData) {
                if (newData) {
                    this.tranStar()
                    // this.$notice.alert({
                    //     message: '111'
                    // })
                }
            }
        },
        methods: {
            tranStar () {
                // let a = this.points.splice('.')
                const a = String(this.points).split('.');

                if (parseInt(a[0]) === 5) {
                    this.grayStarArr = []
                } else {
                    this.starArr.length = a[0]
                    if (a[1] <= 3 && a[1] != 0) {
                        this.decimal = 3
                    } else if (a[1] > 3 && a[1] <= 5) {
                        this.decimal = 5
                    } else if (a[1] > 5 && a[1] <= 7) {
                        this.decimal = 7
                    }
                    if (a[1] == 0) {
                        this.grayStarArr.length = 5 - this.starArr.length
                    } else {
                        this.grayStarArr.length = 5 - (this.starArr.length + 1)
                    }
                }
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
