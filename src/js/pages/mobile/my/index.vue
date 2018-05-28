<template>

    <div class="wrapper">
        <topic-header ref="ref1" ></topic-header>


        <div class="header" @click="jumpWeb()">
            <!--<image class="header-bg" resize="cover" src="http://yanxuan.nosdn.127.net/6ae93353e95b3450a2710bb43f925a63.jpg"></image>-->
            <div class="i-photo-div">
                <image  class="i-photo" resize="cover" src="http://yanxuan.nosdn.127.net/885e3901d0a3501362530435d76bebb3.jpg"></image>
            </div>
            <div class="b-tlt">
                <text class="i-name">nickname</text>

                <text class="txt-tag">google.gmail.com</text>
            </div>
            <text class="b-qrcode iconfont">  >  </text>
        </div>

        <div class="s-box ">

            <div class="box-tlt ">
                <text class="box-txt">我的订单</text>

                <text class="i-box">all order&nbsp;&nbsp;></text>

            </div>

            <div class="box-line">
                <div class="i-box-l" @click="jumpWeb">
                    <text class="i-box-icon iconfont">&#xe673;</text>
                    <text class="i-box-tlt">待付款</text>
                </div>
                <div class="i-box-l">
                    <text class="i-box-icon iconfont">&#xe675;</text>
                    <text class="i-box-tlt">待发货</text>
                </div>
                <div class="i-box-l">
                    <text class="i-box-icon iconfont">&#xe671;</text>
                    <text class="i-box-tlt">已发货</text>
                </div>
                <div class="i-box-l">
                    <text class="i-box-icon iconfont">&#xe672;</text>
                    <text class="i-box-tlt">待评价</text>
                </div>
                <div class="i-box-l">
                    <text class="i-box-icon iconfont">&#xe6ac;</text>
                    <text class="i-box-tlt">退换/售后</text>
                </div>
            </div>
        </div>


        <div class="mid-cell">
            <div class="box-tlt ">
                <div>
                    <text class="box-txt"> 《》 </text>
                    <text class="">我的消息</text>
                </div>
                <text class="i-box">all order&nbsp;&nbsp;></text>

            </div>

            <div class="box-tlt ">
                <div>
                    <text class="box-txt"> 《》 </text>
                    <text class="box-txt">联系客服</text>
                </div>


                <text class="i-box">all order&nbsp;&nbsp;></text>

            </div>

        </div>

    </div>
</template>
<script>
import he from '../utils/he';
import header from './header';
import { SERVICES } from './config'

Vue.filter('myFilter', function (value) {
    return he.decode(value);
})

export default {
    components: {
        'topic-header': header,
    },
    mounted () {
        this.getService()
    },
    data () {
        return {
            services: [],
            serviceLength: 0
        }
    },
    methods: {
        jumpWeb (id) {
            // if (!url) return;
            // let self = this;
            this.$router.open({
                name: 'order',
                type: 'PUSH'
            })
        },
        getService () {
            // this.$fetch({
         //        method: 'GET',
         //        name: 'yanxuan_my_getService',
         //        data: {}
         //    }).then(resData => {
         //        this.services = resData.data
         //        this.getLength()
         //    }, error => {

         //    })

         this.services = SERVICES
         this.getLength()
        },
        getLength () {
            this.serviceLength = Math.ceil(this.services.length / 4)
        }
    }
}
</script>
<style lang="sass" src="./index.scss"></style>
