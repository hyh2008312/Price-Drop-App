<template>
    <div>
        <div class="card">
            <div class="card-top">
                <div class="c-t-r">
                    <div class="r-a">
                        <!--<div style="background-color: black;width: 64px;height: 64px"></div>-->
                        <image class="avatar" :src="content.userAvatar"></image>
                    </div>

                    <div class="r-t">
                        <!--<text class="r-t1">U***S</text>-->
                        <text class="r-t1">{{content.userName||''}}</text>
                        <text class="r-t2">{{tranTime(content.created)}}</text>
                        <!--<text class="r-t2">03 May 2018</text>-->
                    </div>
                </div>

                <div class="h-s-d" v-if="content.productScores">
                    <image class="star-icon" v-if="starArr.length>0" v-for="i in starArr" src="bmlocal://assets/star/1.0.png"></image>
                    <image class="star-icon"  v-for="j in grayStarArr" src="bmlocal://assets/star/0.0.png"></image>
                </div>
            </div>

            <div class="card-mid">
                <text class="c-mt1">{{content.variant}}</text>
                <text class="c-mt2">{{content.message}}</text>
            </div>

            <div class="card-bottom" v-if="content.uploadImages">
                <div class="img-div" v-for="item in content.uploadImages">
                    <image style="width: 80px;height: 80px;" :src="item" @click="blowUpImg(item)"></image>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
    import dayjs from 'dayjs';
    export default {
        name: "reviewsCard",
        props: ['content'],
        data () {
            return {
                // starArr: [1, 1, 1, 1, 1],
                // grayStarArr: [0, 0, 0, 0, 0],
                starArr: [],
                grayStarArr: [],
                // starArr: [],
                // grayStarArr: [],
                decimal: 0,
                point: '',
                obj: ''
            }
        },
        created () {
            this.obj = this.content
            // this.tranStar()
        },
        watch: {
            'obj': function (newData, oldData) {
                if (newData) {
                    this.tranStar()
                }
            }
        },
        methods: {
            tranTime (str) {
                if (str) {
                    return dayjs(str).format('DD MMM, YYYY')
                }
            },
            blowUpImg (img) {
                this.$image.preview({
                    index: 1,                     // 所点击图片下标
                    images: [img]       // 图片的网络地址
                })
            },
            tranStar (arr) {
                // let a = this.points.splice('.')
                const a = String(this.obj.productScores).split('.');
                for (let i = 0; i < parseInt(a[0]); i++) {
                    this.starArr.push(1)
                }
                for (let j = 0; j < (5 - parseInt(a[0])); j++) {
                    this.grayStarArr.push(0)
                }
                // if (parseInt(a[0]) === 5) {
                //     this.grayStarArr = []
                // } else {
                //     this.starArr.length = a[0]
                //     if (a[1] <= 3 && a[1] != 0) {
                //         this.decimal = 3
                //     } else if (a[1] > 3 && a[1] <= 5) {
                //         this.decimal = 5
                //     } else if (a[1] > 5 && a[1] <= 7) {
                //         this.decimal = 7
                //     }
                //     if (a[1] == 0) {
                //         this.grayStarArr.length = 5 - this.starArr.length
                //     } else {
                //         this.grayStarArr.length = 5 - (this.starArr.length + 1)
                //     }
                // }
            }
        }
    }
</script>

<style scoped>
    .card{
        margin-right: 32px;
        margin-left: 32px;
        padding-top: 34px;
        padding-bottom: 24px;
        /*border-bottom-width:2px ;*/
        /*border-bottom-style: dashed;*/
        /*border-bottom-color: rgba(0,0,0,.32);*/
    }
    .card-top{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .avatar{
        width: 64px;
        height: 64px;
    }
    .c-t-r{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .r-a{
        border-radius: 50%;
        margin-right: 16px;
    }
    .r-t{
        flex-direction: column;
        justify-content: space-between;
        align-items: start;
    }
    .r-t1{
        font-size: 24px;
        color: #000000;
    }
    .r-t2{
        font-size: 20px;
        color: rgba(0,0,0,.56);
    }
    .card-mid{
        margin-top: 16px;
    }
    .c-mt1{
        font-size: 20px;
        color: rgba(0,0,0,.56);
        margin-bottom: 16px;
    }
    .c-mt2{
        width: 648px;
        lines:3;
        text-overflow:ellipsis ;
        /*background-color: #EF8A31;*/
        font-size: 24px;
        line-height: 34px;
    }
    .card-bottom{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 16px;
    }
    .img-div{
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        margin-right: 16px;
    }
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
