<template>

    <div class="wrapper">
        <topic-header title="Account" rightBtn="icon" leftBtn='' @setting="openCell(0)"  ref="ref1" ></topic-header>

        <div class="blackheader">

        </div>

        <!--<div class="header" v-if="user!=null" @click="openMydetail(1)">-->
            <!--<div class="i-photo-div">-->
                <!--<image  class="i-photo" resize="cover" v-if="" :src="img"></image>-->
            <!--</div>-->
            <!--<div class="b-tlt">-->
                <!--<text class="i-name">{{nickname}}</text>-->

                <!--<text class="txt-tag">{{email}}</text>-->
            <!--</div>-->
            <!--<text class="b-qrcode iconfont">  &#xe626;  </text>-->
        <!--</div>  v-if="user==null"  -->

        <div class="header"  @click="openMydetail(3)">
            <div class="no-login">
                <text class="">Welcome!</text>
                <text class="no-login-text"> Log in </text>
            </div>
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
            <div class="box-tlt " @click="openCell(1)">
                <div class="box-left">
                    <text class="box-txt-icon">&#xe71b;</text>
                    <text class="box-txt">Customer Support</text>
                </div>
                <text class="i-box iconfont">&#xe626;</text>

            </div>

            <div class="box-tlt " @click="openCell(2)">
                <div class="box-left">
                    <text class="box-txt-icon">&#xe705;</text>
                    <text class="box-txt">My Address</text>
                </div>
                <text class="i-box iconfont">&#xe626;</text>
            </div>
        </div>
        <div class="mid-cell">
            <div class="box-tlt " @click="openCell(3)">
                <div class="box-left">
                    <text class="box-txt-icon">&#xe71c;</text>
                    <text class="box-txt">About Us</text>
                </div>
                <text class="i-box iconfont">&#xe626;</text>

            </div>

            <div class="box-tlt " @click="openCell(4)">
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
        'topic-header': header
    },
    eros: {
        beforeAppear (a) {
        },
        beforeBackAppear (params) {
            this.getUserData()
        }
    },
    created () {
        this.getUserData()
    },
    // mounted () {
    //     this.getUserData()
    // },
    data () {
        return {
            nickname: 'set nickname',
            email: 'google.gmail.com',
            img: 'bmlocal://assets/empty.png',
            user: null
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
        openMydetail (type) {
            if (type == 1) {
                this.$router.open({
                    name: 'my.details',
                    type: 'PUSH',
                    params: {
                        tab: id
                    }
                })
            } else if (type == 2) {
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            }
        },
        openCell (type) {
            if (type == 0) {
                this.$router.open({
                    name: 'my.setting',
                    type: 'PUSH'
                    // params: {
                    //     tab: id
                    // }
                })
            } else if (type == 1) {
               this.$router.open({
                   name: 'my.support',
                   type: 'PUSH'
                   // params: {
                   //     tab: id
                   // }
               })
           } else if (type == 2) {
               this.$router.open({
                   name: 'order.address',
                   type: 'PUSH',
                   params: {
                       source: 'account'
                   }
               })
           } else if (type == 3) {
               this.$router.open({
                   name: 'my.about',
                   type: 'PUSH'
                   // params: {
                   //     tab: id
                   // }
               })
           } else if (type == 4) {
               this.$router.open({
                   name: 'my.faq',
                   type: 'PUSH'
                   // params: {
                   //     tab: id
                   // }
               })
           }
        },
        getUserData () {
            // this.$notice.toast({
            //     message: 111
            // })
            this.$storage.get('token').then(resData => {
                this.token = resData
            })
            this.$storage.get('user').then(resData => {
                this.user = resData
                this.nickname = this.user.firstName + this.user.lastName
                this.email = this.user.email
                this.img = this.user.avatar
                this.$notice.alert({
                    message: resData
                })
                if (this.user !== null) {
                    this.$fetch({
                        method: 'GET',
                        name: 'user.userprofile',
                        header: {
                            Authorization: 'Bearer ' + this.token.accessToken
                        }
                    }).then((res) => {
                        this.$notice.toast({
                            message: res
                        })
                    }).catch((res) => {
                        this.$notice.toast({
                            message: res
                        })
                    })
                } else {
                    this.$notice.toast({
                        message: 'please login'
                    })
                }
            })
        }
        // getService () {
        //     this.$fetch({
        //         method: 'GET',
        //         name: 'yanxuan_my_getService',
        //         data: {}
        //     }).then(resData => {
        //         this.services = resData.data
        //         this.getLength()
        //     }, error => {
        //
        //     })
        //
        //  this.services = SERVICES
        //  this.getLength()
        // },
        // getLength () {
        //     this.serviceLength = Math.ceil(this.services.length / 4)
        // }
    }
}
</script>
<style lang="sass" src="./index.scss"></style>
