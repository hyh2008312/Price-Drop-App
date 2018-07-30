<template>
    <div>
        <slider class="slider" infinite="false" :index="0">
            <div :style="{height:height}"class="frame">
                <image :style="{height:height}" class="image-content" resize="cover" src="bmlocal://assets/guide_1.png"></image>
                <div class="guide-text">
                    <text class="guide-text-content">Team up with friends to get the lowest price on products you love</text>
                </div>
                <div class="guide-circle">
                    <div class="guide-circle-white"></div>
                    <div class="guide-circle-gray" style="margin-left: 32px"></div>
                    <div class="guide-circle-gray" style="margin-left: 32px"></div>
                </div>
            </div>
            <div :style="{height:height}">
                <image :style="{height:height}" class="image-content" resize="cover" src="bmlocal://assets/guide_3.png"></image>
                <div class="guide-text">
                    <text class="guide-text-content">Earn points to redeem for free gifts & cashback rewards</text>
                </div>
                <div class="guide-circle">
                    <div class="guide-circle-gray"></div>
                    <div class="guide-circle-white" style="margin-left: 32px"></div>
                    <div class="guide-circle-gray" style="margin-left: 32px"></div>
                </div>
            </div>
            <div :style="{height:height}">
                <image :style="{height:height}" class="image-content" resize="cover" src="bmlocal://assets/guide_2.png"></image>
                <div class="guide-text">
                    <text class="guide-text-content">Discover unique and high quality products from global manufacturers</text>
                </div>
                <div class="guild-start" @click="setHomePage">
                    <text class="guide-start-text">Get Started Now</text>
                </div>
            </div>
        </slider>
    </div>
</template>
<script>
    import { Utils } from 'weex-ui';
    const globalEvent = weex.requireModule('globalEvent');

    export default {
        components: {
        },
        created () {
            this.height = Utils.env.getScreenHeight();
            const self = this;
            globalEvent.addEventListener('homeBack', options => {
                self.$router.finish();
            })
        },
        eros: {
        },
        data () {
            return {
                height: 1250,
                isPlatformAndroid: Utils.env.isAndroid()
            }
        },
        methods: {
            setHomePage () {
                this.$router.setHomePage('/pages/mobile/index.js');
                this.$router.finish();
                /* this.$router.open({
                    name: 'mobile',
                    type: 'PUSH'
                }) */
            }
        }
    }

</script>
<style scoped>
    .guide-start-text{
        font-weight: bold;
        font-size: 28px;
        width: 260px;
        height: 72px;
        line-height: 72px;
        background-color: #EF8A31;
        color: rgba(255,255,255,0.87);
        border-radius: 8px;
        text-align: center;
    }
    .guild-start{
        position: absolute;
        bottom: 180px;
        width: 750px;
        height: 72px;
        flex-direction: row;
        justify-content: center ;
        align-items: center;
    }
    .guide-circle{
        position: absolute;
        bottom: 50px;
        width: 750px;
        height: 16px;
        flex-direction: row;
        justify-content: center ;
        align-items: center;
    }
    .guide-circle-gray{
        width: 16px;
        height: 16px;
        opacity: 0.38;
        background-color: #FFFFFF;
        border-radius: 8px;
    }
    .guide-circle-white{
        width: 16px;
        height: 16px;
        background-color: #FFFFFF;
        border-radius: 8px;
    }
    .guide-text-content{
        margin-left: 50px;
        margin-right: 50px;
        /*font-weight: bold;*/
        font-size: 32px;
        text-align: center;
        font-family: ProximaNova-Bold;
        color: rgba(255,255,255,0.87);
        line-height: 48px;
    }
    .guide-text{
        width: 750px;
        position: absolute;
        bottom: 280px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start ;
        align-items: center;

    }
    .slider{
        position: relative;
        width: 750px;


    }
    .image-content{
        position: relative;
        width: 750px;

    }

</style>
