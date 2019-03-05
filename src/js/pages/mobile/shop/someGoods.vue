<template>
    <div class="wrapper">
        <div class="i-good" v-for="i in goods" :key="i.id"  @click="openNewDetail(i.id)">
            <div class="gd-img">
                <preload class="gd-img-image" :src="i.mainImage"></preload>
                <!--<div class="gd-img-image" style="background-color: black"></div>-->
            </div>
            <div class="gd-tlt-bg">
                <text class="gd-tlt">₹{{parseInt(i.unitPrice)}}</text>
                <text class="gd-info">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
            </div>
            <text class="gd-price">₹{{parseInt(i.saleUnitPrice)}}</text>
        </div>
    </div>

</template>

<script>
    import preload from '../common/preloadImg';
    export default {
        components: {
            preload
        },
        props: ['goods'],
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
            }
        }
    }
</script>

<style scoped>
    /*---------------*/
    .wrapper{
        background-color: #fff;
        padding: 24px 32px 0 32px;
        width: 750px;
        flex-direction: row;
        justify-content: space-between;
    }

    .i-good {
        padding-left: 8px;
        padding-right: 8px;
        width: 319px;
        align-items: center;
    }
    .gd-img {
        width: 319px;
        height: 319px;
        border-radius: 16px;
    }
    .gd-img-image {
        width: 319px;
        height: 319px;
        border-radius: 8px;
        /*overflow: hidden;*/
        /*position: relative;*/
    }
    .gd-tlt {
        font-size: 28px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        line-height: 34px;
    }
    .gd-tlt-bg{
        margin-top: 16px;
        width: 288px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }

    .gd-info{
        margin-left: 8px;
        font-size: 20px;
        line-height: 24px;
        color: #EF8A31;
    }

    .gd-price {
        font-size: 20px;
        font-weight: 500;
        width: 288px;
        line-height: 24px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
        text-align: center;
        text-decoration: line-through;
    }
</style>
