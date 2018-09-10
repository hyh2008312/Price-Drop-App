<template>
    <div class="item">
        <div class="i-gd-1" @click="jumpWeb()">
            <div class="gd-bg-1">
                <div class="gd-img-1">
                    <preload class="gd-img-image-1" :src="goods.image"></preload>
                </div>
            </div>
            <div class="gd-bg-right-1">
                <text class="gd-tlt-1">{{formatDate(goods.created, 'MMMM DD, YYYY')}}</text>
                <text class="gd-price-1">{{goods.productTitle}}</text>
                <div class="prize-tip" v-if="goods.logFirstPrize=='first'">
                    <text class="icon-first">&#xe747;</text>
                    <text class="gd-info-1">Won the free product</text>
                </div>
                <div class="prize-tip" v-else-if="goods.logFirstPrize=='second'">
                    <text class="icon-second">&#xe749;</text>
                    <text class="gd-info-1">Won the {{goods.secondPrize}} voucher</text>
                </div>
                <div class="prize-tip" v-else-if="goods.logFirstPrize=='third'">
                    <text class="icon-third">&#xe749;</text>
                    <text class="gd-info-1">Won the {{goods.thirdPrize}} voucher</text>
                </div>
                <div class="prize-tip"v-else>
                    <text class="icon-none">&#xe747;</text>
                    <text class="gd-info-1-1">You didn't win any prize</text>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
    import preload from '../common/preloadImg';
    import dayjs from 'dayjs';

    export default {
        components: { preload },
        props: ['goods', 'flag'],
        data () {
            return {
                dropStatus: 1
            }
        },
        methods: {
            jumpWeb () {
               if (this.goods.logFirstPrize === 'first') {
                   this.$router.open({
                       name: 'order.confirm',
                       type: 'PUSH',
                       params: {
                           title: this.goods.productTitle,
                           mainImage: this.goods.image,
                           salePrice: '0',
                           currentPrice: '0', // 计算价钱的金额
                           quantity: '1',
                           id: this.goods.variantId,
                           shippingPrice: '',
                           shippingTimeMin: '',
                           shippingTimeMax: '',
                           proId: 'lottery'
                       }
                   })
               } else if (this.goods.logFirstPrize === 'second') {
                   this.$router.open({
                       name: 'my.card',
                       type: 'PUSH'
                   })
               }
            },
            formatDate (str, hmr) {
                return dayjs(new Date(str)).format(hmr)
            }
        }
    }
</script>
<style scoped>
    .icon-first {
        font-family: iconfont;
        font-size: 32px;
        color: #69549d;
    }
    .icon-second {
        font-family: iconfont;
        font-size: 32px;
        color: #ffba1b;
    }
    .icon-third {
        font-family: iconfont;
        font-size: 32px;
        color: #00CFE3;
    }
    .icon-none {
        font-family: iconfont;
        font-size: 32px;
        color: rgba(0,0,0,.24);
    }
    .item {
        display: flex;
        background-color: #F1F1F1;
    }
    .i-gd-1 {
        width: 686px;
        height: 256px;
        margin-left: 32px;
        margin-right: 32px;
        margin-top: 24px;
        background-color: #FFFFFF;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        flex-direction: row;
    }

    .gd-bg-1 {
        width: 192px;
        height: 192px;
        border-radius: 4px;
        margin-top: 32px;
        margin-left: 32px;
        overflow: hidden;
        border-top-width: 1px;
        border-top-style: solid;
        border-top-color: rgba(0,0,0,.08);
        border-left-width: 1px;
        border-left-style: solid;
        border-left-color: rgba(0,0,0,.08);
        border-right-width: 1px;
        border-right-style: solid;
        border-right-color: rgba(0,0,0,.08);
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,.08);
    }

    .gd-bg-right-1 {
        margin-left: 32px;
        width: 398px;
        height: 288px;
    }

    .gd-img-1 {
        width: 192px;
        height: 192px;
        border-radius: 4px;
        overflow: hidden;
    }
    .gd-img-image-1 {
        width: 192px;
        height: 192px;
        border-radius: 8px;
        overflow: hidden;
        position: relative;
    }

    .gd-tlt-1 {
        margin-top: 32px;
        font-family: ProximaNova;
        font-weight: bold;
        font-size: 24px;
        line-height: 28px;
        width: 398px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-info-1 {
        font-size: 24px;
        color: black;
        ont-family: ProximaNova;
        font-weight: bold;
        line-height: 28px;
        margin-left: 16px;
    }
    .gd-info-1-1 {
        font-size: 24px;
        color: rgba(0,0,0,0.38);
        ont-family: ProximaNova;
        font-weight: 300;
        line-height: 28px;
        margin-left: 16px;
    }

    .gd-price-1 {
        margin-top: 10px;
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 34px;
        font-weight: 300;
        color: #000000;
        text-overflow: ellipsis;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
    }
    .prize-tip{
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
        margin-top: 90px;
    }

</style>
