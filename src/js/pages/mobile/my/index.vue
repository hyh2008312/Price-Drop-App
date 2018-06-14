<template>

    <div class="wrapper">
        <div class="blackheader"></div>

        <!--<scroller>-->

            <div class="header" v-if="user!=null" @click="openMydetail(1)">
                <div class="overflow-photo">
                    <div class="i-photo-div">
                        <image  class="i-photo" resize="cover" v-if="" :src="img"></image>
                    </div>
                    <div class="b-tlt">
                        <text class="i-name">{{fname}}{{lname}}</text>
                        <div class="txt-tag ">
                            <text class="txt-tag-txt" >{{email}}</text>&nbsp;&nbsp;<text class="txt-icon iconfont">&#xe626;</text>

                        </div>
                    </div>
                </div>
                <div class="overflow-setting-icon" @click="openCell(0)">
                    <text class="iconfont setting-icon" style="">&#xe71e;</text>
                </div>
            </div>
<<<<<<< HEAD
=======
            <div class="b-tlt">
                <text class="i-name">{{fname}}&nbsp;{{lname}}</text>
>>>>>>> cb0a2534294e70b1a1ebffc133af81d35fc5b9ae

            <div class="header"  v-if="user==null" @click="openMydetail(2)">
                <div class="overflow-photo">
                    <div class="no-login">
                        <text class="">Welcome!</text>
                        <text class="no-login-text">Log In / Sign Up</text>
                    </div>
                </div>
            </div>
            <div class="overflow-point-card">
                <div class="point-card">
                    <div class="point" @click="openCell(5)">
                        <text class="point-card-num">{{points}}</text>
                        <text class="point-card-txt">My Points</text>
                    </div>
                    <text class="center-border"></text>
                    <div class="card" @click="openCell(6)">
                        <text class="point-card-num">{{cardNumber}}</text>
                        <text class="point-card-txt">My Gift Card</text>
                    </div>

                </div>
            </div>
        <div class="overflow-box">

            <div class="s-box ">
                <div class="box-tlt "  @click="jumpWeb(0)">
                    <text class="box-txt">My Orders</text>
                    <text class="i-box iconfont">SEE MORE&nbsp;&nbsp;&#xe626;</text>
                </div>

                <div class="box-line">
                    <div class="i-box-l" @click="jumpWeb(1)">
                        <image class="i-box-icon" src="bmlocal://assets/ic-paid.png"></image>

                        <!--<text class="i-box-icon iconfont">&#xe717;</text>-->
                        <text class="i-box-tlt">Unpaid</text>
                    </div>
                    <div class="i-box-l" @click="jumpWeb(2)">
                        <image class="i-box-icon" src="bmlocal://assets/ic-pack.png"></image>

                        <!--<text class="i-box-icon iconfont">&#xe718;</text>-->
                        <text class="i-box-tlt">Packing</text>
                    </div>
                    <div class="i-box-l" @click="jumpWeb(3)">
                        <image class="i-box-icon" src="bmlocal://assets/ic-shipped.png"></image>

                        <!--<text class="i-box-icon iconfont">&#xe719;</text>-->
                        <text class="i-box-tlt">Shipped</text>
                    </div>
                    <div class="i-box-l" @click="jumpWeb(4)">
                        <image class="i-box-icon" src="bmlocal://assets/ic-delivered.png"></image>

                        <!--<text class="i-box-icon iconfont">&#xe71a;</text>-->
                        <text class="i-box-tlt">Delivered</text>
                    </div>
                </div>
            </div>

        </div>

        <div class="overflow-box">
            <div class="mid-cell">
                <div class="box-tlt " @click="openCell(1)">
                    <div class="box-left">
                        <image class="box-txt-icon" src="bmlocal://assets/pic-customer.png"></image>
                        <!--<text class="box-txt-icon">&#xe71b;</text>-->
                        <text class="box-txt">Customer Support</text>
                    </div>
                    <text class="i-box iconfont">&#xe626;</text>

                </div>

                <div class="box-tlt " @click="openCell(2)">
                    <div class="box-left">
                        <image class="box-txt-icon" src="bmlocal://assets/pic-address.png"></image>

                        <!--<text class="box-txt-icon">&#xe705;</text>-->
                        <text class="box-txt">My Address</text>
                    </div>
                    <text class="i-box iconfont">&#xe626;</text>
                </div>
            </div>

        </div>
        <div class="overflow-box">
            <div class="mid-cell mid-cell-bottom ">
                <div class="box-tlt " @click="openCell(3)">
                    <div class="box-left">
                        <image class="box-txt-icon" src="bmlocal://assets/pic-aboutus.png"></image>

                        <!--<text class="box-txt-icon">&#xe721;</text>-->
                        <text class="box-txt">About Us</text>
                    </div>
                    <text class="i-box iconfont">&#xe626;</text>

                </div>

                <div class="box-tlt " @click="openCell(4)">
                    <div class="box-left">
                        <image class="box-txt-icon" src="bmlocal://assets/pic-FAQ.png"></image>
                        <!--<text class="box-txt-icon">&#xe71d;</text>-->
                        <text class="box-txt">FAQ</text>
                    </div>
                    <text class="i-box iconfont">&#xe626;</text>
                </div>
            </div>

        </div>

        <!--</scroller>-->
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
    created () {
        this.$event.on('login', params => {
            this.getUserData()
        })
        this.$event.on('logout', parmas => {
            this.token = null
            this.user = null
        })
        this.getUserData()
        this.$event.on('redeem', parmas => {
            this.getUserData()
        })
    },
    destory () {
        this.$event.off('login')
        this.$event.off('logout')
        this.$event.off('setdetail')
    },
    data () {
        return {
            nickname: 'set nickname',
            fname: '',
            lname: '',
            email: 'google.gmail.com',
            img: 'bmlocal://assets/default.png',
            token: null,
            user: null,
            gender: '',
            points: '',
            cardNumber: '',
            setsign: ''
        }
    },
    methods: {
        jumpWeb (id) {
            if (this.user == null) {
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            } else {
                this.$router.open({
                    name: 'order',
                    type: 'PUSH',
                    params: {
                        tab: id
                    }
                })
            }
        },
        openMydetail (type) {
            if (type == 1) {
                this.$router.open({
                    name: 'my.details',
                    type: 'PUSH',
                    params: {
                        useravatar: this.img,
                        usergender: this.gender,
                        fname: this.fname,
                        lname: this.lname
                    }
                })
            } else if (type == 2) {
                this.$router.open({
                    name: 'login',
                    type: 'PUSH'
                })
            } else {
                this.$router.open({
                    name: 'my.details',
                    type: 'PUSH'
                })
            }
        },
        openCell (type) {
            if (type == 0) {
                if (this.user == null) {
                    this.$router.open({
                        name: 'my.setting',
                        type: 'PUSH',
                        params: {
                            params: this.token,
                            setsign: '1'
                        }
                    })
                } else {
                    this.$router.open({
                        name: 'my.setting',
                        type: 'PUSH',
                        params: {
                            params: this.token,
                            setsign: '2'
                        }
                    })
                }
            } else if (type == 1) {
               this.$router.open({
                   name: 'my.support',
                   type: 'PUSH'
                   // params: {
                   //     tab: id
                   // }
               })
           } else if (type == 2) {
                if (this.user == null) {
                    this.$router.open({
                        name: 'login',
                        type: 'PUSH'
                    })
                } else {
                    this.$router.open({
                        name: 'order.address',
                        type: 'PUSH',
                        params: {
                            source: 'account'
                        }
                    })
                }
           } else if (type == 3) {
               this.$router.open({
                   name: 'my.about',
                   type: 'PUSH'
               })
           } else if (type == 4) {
               this.$router.open({
                   name: 'my.faq',
                   type: 'PUSH'
               })
           } else if (type == 5) {
                this.$router.open({
                    name: 'my.points',
                    type: 'PUSH',
                    params: {
                        num: this.points
                    }
                })
           } else {
                this.$router.open({
                    name: 'my.card',
                    type: 'PUSH'
                })
           }
        },
        getUserData () {
            this.$event.on('setdetail', parmas => {
                this.img = parmas.avatar
                this.fname = parmas.firstName
                this.lname = parmas.lastName
            })

            this.$storage.get('token').then(resData => {
                this.token = resData
                if (this.token !== null) {
                    this.$fetch({
                        method: 'GET',
                        name: 'user.userprofile',
                        header: {
                            needAuth: true
                        }
                    }).then((res) => {
                        // this.$notice.alert({
                        //     message: res
                        // })

                        this.nickname = res.firstName + res.lastName
                        this.fname = res.firstName
                        this.lname = res.lastName
                        this.img = res.avatar
                        this.points = res.points
                        this.cardNumber = res.cardNumber
                        this.gender = res.gender
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
            this.$storage.get('user').then(resData => {
                // this.$notice.alert({
                //     message: resData
                // })
                this.user = resData
                this.email = this.user.email
                this.fname = resData.firstName
                this.lname = resData.lastName
            })
        }
    }
}
</script>
<style lang="sass" src="./index.scss"></style>
