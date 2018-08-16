<template>
    <div class="wrapper">
        <top-header :title="title"  leftBtn="icon"></top-header>
        <div class="status-bar"></div>
        <top-channel @change="onchange" :activeIndex="activeIndex" :channelList="cardArr"></top-channel>
        <div :style="height" class="box">
            <slider class="slider" infinite="false" ref="slider" @change="onchangeTab" :index="activeIndex">
                <goods-list v-if="i.id" v-for="(i, index) in cardArr" :key="index"
                            :activeIndex="index" :index="activeIndex" :item="i" :listArr="cardArr"></goods-list>
            </slider>
        </div>
    </div>
</template>
<script>
    import header from './header';
    import goodsList from './cardList';
    import topChannel from './cardTopChannel';
    // import { CHANNELLIST } from './config';
    import { Utils } from 'weex-ui';
    import { baseUrl } from '../../../config/apis'

    export default {
        components: {
            'goods-list': goodsList,
            'top-header': header,
            'top-channel': topChannel
        },
        created () {
            const pageHeight = Utils.env.getScreenHeight()
            this.height = { height: (pageHeight - 112 - 96 - 48) + 'px' }
            this.$router.getParams().then(params => {
                if (params && params.tab) {
                    this.activeIndex = params.tab
                }
            })
            // this.getFlash()
        },
        data () {
            return {
                title: 'My Gift Vouchers',
                activeIndex: 0,
                index: 0,
                channelList: [],
                cardArr: [
                    {
                        cardStatus: 'Available',
                        id: 'available',
                        left: 0
                    },
                    {
                        cardStatus: 'Used',
                        id: 'used',
                        left: 250
                    },
                    {
                        cardStatus: 'Expired',
                        id: 'expired',
                        left: 490
                    }
                ]
            }
        },
        methods: {
            // getFlash () {
            //     this.$notice.loading.show();
            //     this.$fetch({
            //         method: 'GET',
            //         url: `${baseUrl}/flashsale/flash/customer/list/`
            //     }).then((res) => {
            //         this.cardArr = res[1]
            //         this.cardArr.unshift(res[0][0])
            //         for (let i = 0; i < this.cardArr.length; i++) {
            //             this.cardArr[i].left = i * 260
            //         }
            //         this.cardArr[2].left = 500
            //         // this.$notice.alert({
            //         //     message: this.cardArr
            //         // })
            //     }).catch((res) => {
            //         this.$notice.alert({
            //             message: res
            //         })
            //     })
            // },
            onchange (index) {
                this.activeIndex = index;
            },
            onchangeTab (event) {
                this.activeIndex = event.index;
                // this.$notice.toast({
                //     message: event
                // })
                // this.$emit('changeC', )
            }
        }
    }

</script>
<style lang="sass" src="./index.scss"></style>
