<template>
    <div class="wrapper">
        <div class="gd-top">
            <div class="gd-top-left">
                <text class="title-icon iconfont">&#xe745;</text>
                <text class="title">Flash Sale</text>
                <wxc-countdown tpl="{h}:{m}:{s}"
                               :time="time"
                               :timeBoxStyle="{backgroundColor: '#000', height: '36px', width: '36px','border-radius': '4px'}"
                               :timeTextStyle="{fontSize: '24px', color: '#FFF'}"
                               :dotTextStyle="{color: '#000', fontSize: '24px'}"
                               :dotBoxStyle="{width: '10px'}"
                               :style="{justifyContent: 'center'}">
                </wxc-countdown>
            </div>
            <text class="title-1" @click="openNewPage()">VIEW MORE</text>
        </div>
        <div class="tlt-box" @click="jumpActivity" v-if="false">
            <image class="tlt-image" resize="cover" :src="head.image"></image>
            <div class="tlt-bg" v-if="false">
                <text class="tlt tlt-new">{{head.name}}</text>
                <text class="btn-all">See More</text>
            </div>
            <text class="iconfont tlt-tri">&#xe6ff;</text>
        </div>

        <!--<scroller class="box" scroll-direction="horizontal" flex-direction="row" show-scrollbar=false>-->
        <div class="box">

            <div class="i-good" v-for="i in goodsList" :key="i.id" @click="jumpWeb(i.productId)">
                <div style="height:230px;padding-top: 15px">
                    <div class="gd-bg">
                        <div class="gd-img">
                            <preload class="gd-img-image" :src="i.mainImage"></preload>
                        </div>
                        <!--<text class="gd-txt"></text>-->
                    </div>
                    <text class="gd-txt">-{{countOff(countPrice(i.unitPrice, i.discount), i.saleUnitPrice)}}</text>
                </div>
                <div class="gd-tlt-bg">
                    <text class="gd-tlt">₹{{countPrice(i.unitPrice, i.discount)}}</text>
                    <!--<text class="gd-info">{{countOff(countPrice(i.unitPrice, i.discount), i.saleUnitPrice)}}</text>-->
                </div>
                <text class="gd-price">₹{{i.saleUnitPrice}}</text>
            </div>
        </div>

        <!--</scroller>-->

    </div>
</template>
<script>
import preload from '../common/preloadImg';
import { WxcCountdown } from 'weex-ui';
export default {
    components: {
        preload,
        WxcCountdown
    },
    props: ['time', 'goodsList'],
    data () {
        return {
            finalPrice: ''
        }
    },
    created () {
      this.getGoodsDetail()
    },
    methods: {
        jumpActivity () {
            this.$router.open({
                name: 'mobile.activity',
                type: 'PUSH',
                params: {
                    id: this.head.id,
                    name: this.head.name,
                    imageUrl: this.head.image,
                    type: 'activity'
                }
            })
        },
        jumpWeb (id) {
            this.$router.open({
                name: 'goods.details',
                type: 'PUSH',
                params: {
                    id: id
                }
            })
        },
        openNewPage () {
            this.$router.open({
                name: 'flash',
                type: 'PUSH'
                // params: {
                //     card: id
                // }
            })
        },
        countOff (s, o) {
            if (o > 0) {
                return Math.floor((o - s) / o * 100) + '%'
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
}
.gd-top{
    width: 750px;
    height: 90px;
    padding: 48px 32px 24px 32px;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}

.gd-top-left{
    flex-direction: row;
    align-items: center;
    height: 38px;
}

.title-icon{
    font-family: ProximaNova;
    font-weight: bold;
    font-size: 36px;
    line-height: 38px;
    color: #00CFE3 ;
    margin-left: 6px;
}
.title{
    font-family: ProximaNova;
    font-weight: bold;
    font-size: 32px;
    line-height: 38px;

}

.title-1{
    font-family: ProximaNova;
    font-size: 24px;
    color: #EF8A31;
    line-height: 28px;
}

.tlt-box {
    height: 380px;
    width: 750px;
    display: flex;
    align-items: center;
    justify-content: center;

}

.tlt-bg{
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    width: 750px;
    height: 375px;
    align-items: center;
    justify-content: center;
    background-color: rgba(0,0,0,0.18);
}

.tlt {
    text-align: center;
    font-size: 32px;
    line-height: 38px;
    font-weight: 700;
    color: rgba(255,255, 255, 0.87);
}

.tlt-tri{
    position: absolute;
    left: 375px;
    bottom: -1px;
    transform: translateX(-17px) rotate(180deg);
    width: 34px;
    line-height: 17px;
    color: #fff;
}

.btn-all {
    text-align: center;
    font-size: 24px;
    width: 128px;
    line-height: 44px;
    justify-content: center;
    align-items: center;
    margin-top: 24px;
    color: rgba(255,255,255,0.87);
    border-style: solid;
    border-width: 2px;
    border-color: #fff;
}


.tlt-image {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

.box {
    padding-top: 8px;
    padding-left: 32px;
    margin-bottom: 32px;
    flex-direction: row;
}

.i-good {
    width: 230px;
    align-items: center;
    justify-content: start;
}

.gd-bg {
    border-radius: 8px;
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
.gd-txt{
    font-family: ProximaNova-Bold;
    position: absolute;
    top: 0;
    left: 0;
    background-color: #00CFE3 ;
    width: 80px;
    height: 33px;
    font-size: 24px;
    font-weight: 700;
    color: #FFFFFF;
    text-align: center;
    line-height: 28px;
    border-top-right-radius: 50%;
    border-bottom-right-radius: 50%;
    padding-top: 4px;
}
.gd-img {
    width: 208px;
    height: 208px;
    border-radius: 8px;
    overflow: hidden;
}
.gd-img-image {
    width: 208px;
    height: 208px;
    border-radius: 8px;
    overflow: hidden;
}

.gd-tlt {
    font-size: 28px;
    font-weight: bold;
    color: #EF8A31;
    line-height: 34px;
}

.gd-tlt-bg{
    width: 208px;
    margin-top: 8px;
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
