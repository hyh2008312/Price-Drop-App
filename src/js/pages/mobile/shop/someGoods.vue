<template>
    <div class="wrapper">

        <div class="i-good" v-for="i in goods" :key="i.id" >
            <div class="gd-bg">
            <div class="gd-img">
                <preload class="gd-img-image" :src="i.mainImage"></preload>
            </div>
            <!--<text class="gd-txt">{{countOff(countPrice(i.unitPrice, i.discount), i.saleUnitPrice)}} 111</text>-->
            <!--<text class="gd-txt">11111111</text>-->
            <!--<text class="gd-txt"></text>-->
            </div>
        <!--<text class="gd-tlt1">{{i.title}}</text>-->
            <div class="gd-tlt-bg">
                <text class="gd-tlt">₹{{i.unitPrice}}</text>
                <text class="gd-info">{{countOff(i.unitPrice, i.saleUnitPrice)}}</text>
            </div>
        <text class="gd-price">₹{{i.saleUnitPrice}}</text>
            <!--<text class="gd-price">₹44444</text>-->
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
                    return Math.floor((o - s) / o * 100) + '% OFF'
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
        width: 304px;
        align-items: center;
    }
    .gd-bg {
        height: 288px;
        width: 288px;
        border-radius: 8px;
        overflow: hidden;
        /*border-top-width: 1px;*/
        /*border-top-style: solid;*/
        /*border-top-color: rgba(0,0,0,.08);*/
        /*border-left-width: 1px;*/
        /*border-left-style: solid;*/
        /*border-left-color: rgba(0,0,0,.08);*/
        /*border-right-width: 1px;*/
        /*border-right-style: solid;*/
        /*border-right-color: rgba(0,0,0,.08);*/
        /*border-bottom-width: 2px;*/
        /*border-bottom-style: solid;*/
        /*border-bottom-color: rgba(0,0,0,.08);*/
    }
    .gd-txt{
        font-family: ProximaNova-Bold;
        position: absolute;
        top: 0;
        left: 0;
        background-color: #00CFE3 ;
        width: 66px;
        height: 66px;
        font-size: 24px;
        font-weight: 700;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        line-height: 28px;
        border-radius: 8px;
        padding-top: 4px;
    }
    .gd-img {
        width: 284px;
        height: 284px;
        border-radius: 8px;
        overflow: hidden;
    }
    .gd-img-image {
        width: 284px;
        height: 284px;
        border-radius: 8px;
        overflow: hidden;
        position: relative;
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
