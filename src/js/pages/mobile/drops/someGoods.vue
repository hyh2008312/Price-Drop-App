<template>
    <div class="wrapper">
        <div :class="[type==1?'overflow-1':'overflow-2']">


            <div v-if="type==2" class="i-good" v-for="i in goods" :key="i.id"  @click="openNewDetail(i.id)" >
                <div class="gd-img" >
                    <preload class="gd-img-image" :src="i.mainImage"></preload>
                </div>

                <div class="gd-tlt-bg">
                    <text class="gd-tlt">₹{{i.unitPrice}}</text>
                    <text class="gd-info">Drop Price</text>
                </div>
            </div>

            <div v-if="type==1" class="p-good"  @click="openPopup()">
                <div class="p-gd-img">
                    <preload class="p-gd-img-image" :src="goods.mainImage"></preload>
                </div>

                <div class="p-gd-tlt-bg">
                    <text class="g-title">{{goods.title}}</text>
                    <text class="g-title1">{{goods.cutProductNum||0}} people have started to drop</text>
                    <div class="p-line3" @click="openPopup">
                        <text class="p-gd-tlt">₹{{parseInt(goods.unitPrice)}}</text>
                        <text class="p-gd-info">Drop Price</text>
                    </div>

                </div>
            </div>
        </div>

    </div>

</template>

<script>
    import preload from '../common/preloadImg';
    export default {
        components: {
            preload
        },
        props: ['goods' , 'type'],
        methods: {
            countOff (s, o) {
                if (o > 0) {
                    return Math.ceil((o - s) / o * 100) + '% OFF'
                } else {
                    return ''
                }
            },
            countPrice (s, o) {
                if (o > 0) {
                    return Math.floor(s * (o / 100)) + '.00'
                } else {
                    return ''
                }
            },
            openNewDetail (id) {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: id
                    }
                })
            },
            openPopup () {
                this.$emit('pop',this.goods)
            }
        }
    }
</script>

<style scoped>
    /*---------------*/
    .wrapper{

    }
    .overflow-1{
        /*background-color: #fff;*/
        padding: 24px 32px 0 32px;
        /*margin-bottom: 32px;*/
        width: 750px;
        flex-direction: row;
    }
    .overflow-2{
        /*background-color: #fff;*/
        padding: 0 32px;
        /*margin-bottom: 32px;*/
        width: 750px;
        flex-direction: row;
    }
    .i-good {
        width: 324px;
        margin-right: 32px;
        border-radius: 16px;
        background-color: white;
    }
    .gd-img {
        width: 324px;
        height: 324px;
        /*border-radius: 8px;*/
        overflow: hidden;
    }
    .gd-img-image {
        width: 324px;
        height: 324px;
        /*overflow: hidden;*/
        /*position: relative;*/
    }

    .gd-tlt-bg{
        margin-top: 16px;
        width: 324px;
        height: 80px;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        background-color: white;
    }
    .gd-tlt {
        font-size: 28px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        text-decoration: line-through;
        line-height: 34px;
        margin-left: 16px;
    }
    .gd-info{
        color: white;
        font-weight: 700;
        font-size: 20px;
        padding: 10px 16px;
        background-color: #492799;
        line-height: 24px;
        margin-right: 16px;
        border-radius: 50%;
    }
    .p-good{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        background-color: white;
        height: 256px;
        width: 686px;
        border-radius: 16px;
        margin-right: 32px;
    }
    .p-gd-img{
        margin-left: 32px;
        border-radius: 8px;
    }
    .p-gd-img-image{
        width: 192px;
        height: 192px;
    }
    .p-gd-tlt-bg{
        flex-direction: column;
        justify-content: start;
        align-items: start;
        height: 192px;
        margin-left:16px;
    }
    .g-title{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        lines:1;
        white-space: nowrap;
        text-overflow: ellipsis;
        width: 400px;
        line-height: 34px;
    }

    .p-line3{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top:20px;
    }
    .g-title1{
        font-size: 24px;
        color: #492799;
        margin-top: 60px;
    }
    .p-gd-tlt{
        font-size: 36px;
        text-decoration: line-through;
        color: #000000;
        margin-right: 20px;
    }
    .p-gd-info{
        color: white;
        background-color: #492799;
        padding: 10px 26px;
        font-size: 24px;
        border-radius: 50%;
    }
</style>
