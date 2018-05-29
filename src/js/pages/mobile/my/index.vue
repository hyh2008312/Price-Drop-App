<template>

    <div class="wrapper">
        <topic-header title="Account" rightBtn="icon" ref="ref1" ></topic-header>

        <div class="blackheader">

        </div>

        <div class="header" @click="openMydetail()">
            <div class="i-photo-div">
                <image  class="i-photo" resize="cover" src="http://yanxuan.nosdn.127.net/885e3901d0a3501362530435d76bebb3.jpg"></image>
            </div>
            <div class="b-tlt">
                <text class="i-name">nickname</text>

                <text class="txt-tag">google.gmail.com</text>
            </div>
            <text class="b-qrcode iconfont">  &#xe626;  </text>
        </div>

        <div class="s-box ">

            <div class="box-tlt "  @click="jumpWeb(0)">
                <text class="box-txt">My Orders</text>

                <text class="i-box iconfont">SEE MORE&nbsp;&nbsp;&#xe626;</text>

            </div>

            <div class="box-line">
                <div class="i-box-l" @click="jumpWeb(1)">
                    <text class="i-box-icon iconfont">&#xe717;</text>
                    <text class="i-box-tlt">Unpaid</text>
                </div>
                <div class="i-box-l" @click="jumpWeb(2)">
                    <text class="i-box-icon iconfont">&#xe718;</text>
                    <text class="i-box-tlt">Packing</text>
                </div>
                <div class="i-box-l" @click="jumpWeb(3)">
                    <text class="i-box-icon iconfont">&#xe719;</text>
                    <text class="i-box-tlt">Shipped</text>
                </div>
                <div class="i-box-l" @click="jumpWeb(4)">
                    <text class="i-box-icon iconfont">&#xe71a;</text>
                    <text class="i-box-tlt">Delivered</text>
                </div>
            </div>
        </div>


        <div class="mid-cell">
            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt-icon">&#xe71b;</text>
                    <text class="box-txt">Customer Support</text>
                </div>
                <text class="i-box iconfont">&#xe626;</text>

            </div>

            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt-icon">&#xe705;</text>
                    <text class="box-txt">My Address</text>
                </div>
                <text class="i-box iconfont">&#xe626;</text>
            </div>
        </div>
        <div class="mid-cell">
            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt-icon">&#xe71c;</text>
                    <text class="box-txt">About Us</text>
                </div>
                <text class="i-box iconfont">&#xe626;</text>

            </div>

            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt-icon">&#xe71d;</text>
                    <text class="box-txt">FAQ</text>
                </div>
                <text class="i-box iconfont">&#xe626;</text>
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
                type: 'PUSH',
                params: {
                    tab: id
                }
            })
        },
        openMydetail () {
            this.$router.open({
                name: 'my.details',
                type: 'PUSH',
                // params: {
                //     tab: id
                // }
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
