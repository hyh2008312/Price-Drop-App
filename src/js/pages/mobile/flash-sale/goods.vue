<template>
    <div class="wrapper">
        <div class="gb-box" v-for="item in goods" @click="jumpWeb(item)">
            <div class="gd-bg">
                <preload class="gd-img-image" :src="item.mainImage"></preload>
            </div>
            <text class="gd-txt">{{countOff(countPrice(item.unitPrice, item.discount), item.saleUnitPrice)}}</text>
            <!--<text class="gd-txt">50% OFF</text> -->
            <div class="gd-bg-right">
                <div class="gd-sm">
                    <text class="gd-price">₹{{countPrice(item.unitPrice, item.discount)}}</text>
                    <text class="gd-price-1">{{item.saleUnitPrice}}</text>
                </div>

                <div class="gd-pb" v-if="channelIndex==0">
                    <progressbar :value="item.soldStock" :all="item.totalFlashVariantStocks" :pId ="item.productId"></progressbar>
                    <!--<text class="goods-bottom-word">{{i.totalFlashVariantStocks}} limited</text>-->
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import preload from '../common/preloadImg';
    import ProgressBar from './progressBar';

    export default {
        components: {
            preload,
            'progressbar': ProgressBar,
        },
        props: ['goods', 'channelIndex'],
        data () {
            return {
                src: 'bmlocal://assets/occupy.png'
            }
        },
        methods: {
            jumpWeb (item) {
                const id = item.productId;
                // if (!url) return;
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id
                    }
                })
            },
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
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        background-color: #fff;
        padding: 24px 32px 0 32px;
        width: 750px;
        flex-direction: row;
        justify-content: space-between;
    }



    .gb-box {
        width: 327px;
        background-color: white;
    }

    .gd-bg {
        width: 327px;
        height: 327px;
        border-radius: 8px;
        overflow: hidden;
        /*border-style: solid;*/
        /*border-width: 2px ;*/
        /*border-color: red;*/
    }

    .gd-bg-right {
        width: 327px;
        margin-top: 7px;
        /*flex-direction: column;*/
        /*justify-content: start;*/
        /*align-items: center;*/
    }

    .gd-img-image {
        width: 327px;
        height: 327px;
        border-radius: 8px;
        overflow: hidden;
        position: relative;
    }


    .gd-info {
        display: block;
        font-size: 20px;
        line-height: 24px;
        text-decoration: line-through;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
        text-align: center;
        font-family: ProximaNova;
    }

    .gd-price {
        font-family: ProximaNova;
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
    }

    .gd-price-1{
        font-family: ProximaNova;
        font-size: 20px;
        line-height: 24px;
        margin-left: 8px;
        text-decoration: line-through;
        color: black;
    }

    .gd-sm {
        margin-top: 6px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .gd-pb{
        margin-top: 8px;
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
</style>
