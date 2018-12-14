<template>

    <div class="wrapper">
        <div class="blackheader"></div>

        <list class="account-list" offset-accuracy="10" loadmoreoffset="400" >
            <refresher class="gd-bg-gray" ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>
            <cell  v-if="user!=null" >
                <div class="header">
                    <div class="overflow-photo"   >
                        <div class="i-photo-div" @click="openMydetail(1)">
                            <image  class="i-photo" resize="cover" v-if="" :src="img"></image>
                        </div>
                        <div class="b-tlt" @click="openMydetail(1)" >
                            <text class="i-name" >{{fname}} {{lname}}</text>
                            <div class="txt-tag ">
                                <text class="txt-tag-txt" >{{email}}</text>&nbsp;<text class="txt-icon iconfont">&#xe74f;</text>
                            </div>
                        </div>
                    </div>
                    <div class="overflow-setting-icon" >

                        <div class="box-bg pdr-42"  @click="openMyCart">
                            <text class="iconfont setting-icon">&#xe767;</text>
                            <text class="box-dot1" v-if="cartNum>0">{{cartNum > 99? '99+': cartNum}}</text>
                        </div>

                        <div class="box-bg pdr-42"  @click="openNoti"  >
                            <text class="iconfont setting-icon">&#xe753;</text>
                            <text class="box-dot1" v-if="unread>0">{{unread > 99? '99+': unread}}</text>
                        </div>

                        <div class="box-bg" @click="openCell(0)" >
                            <text class="iconfont setting-icon" style="">&#xe71e;</text>
                        </div>

                    </div>
                </div>

            </cell>
            <cell   v-if="user==null" @click="openMydetail(2)">
                <div class="header">

                    <div class="overflow-photo">
                    <div class="no-login">
                        <text class="" style="color: white">Welcome!</text>
                        <text class="no-login-text">Log In / Sign Up</text>
                    </div>
                </div>
                </div>

            </cell>

            <!--<cell style="width: 750px;">-->
                <!--<div style="height:64px;background-color: #EF8A31"></div>-->
                <!--<div style="height:64px;background-color: #f4f4f4"></div>  &lt;!&ndash;无法设置负的margin-top 设置的背景&ndash;&gt;-->
                <!--<div class="overflow-point-card">-->
                    <!--<div class="point-card">-->
                        <!--<div class="point" @click="openCell(5)">-->
                            <!--<text class="point-card-num">{{points || 0}}</text>-->
                            <!--<text class="point-card-txt">My Points</text>-->
                        <!--</div>-->
                        <!--<text class="center-border"></text>-->
                        <!--<div class="card" @click="openCell(6)">-->
                            <!--<text class="point-card-num">{{cardNumber}}</text>-->
                            <!--<text class="point-card-txt">My Gift Vouchers</text>-->
                        <!--</div>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</cell>-->

            <cell style="width: 750px;" >
                <div style="height:64px;background-color: #EF8A31;border-bottom-left-radius:64px;border-bottom-right-radius: 64px;"></div>
                <div style="height:64px;background-color: #f4f4f4"></div>  <!--无法设置负的margin-top 设置的背景-->

                <div class="s-box"  >
                    <div class="box-tlt "  @click="jumpWeb(0)">
                        <text class="box-txt1">My Orders</text>
                        <text class="i-box iconfont">SEE MORE&nbsp;&nbsp;&#xe626;</text>
                    </div>

                    <div class="box-line">
                        <div class="i-box-l" @click="jumpWeb(1)">
                            <image class="i-box-icon" src="bmlocal://assets/ic-paid.png"></image>

                            <!--<text class="i-box-icon iconfont">&#xe717;</text>-->
                            <text class="i-box-tlt">Confirmed</text>
                        </div>
                        <div class="i-box-l" @click="jumpWeb(2)">
                            <image class="i-box-icon" src="bmlocal://assets/ic-packed.png"></image>

                            <!--<text class="i-box-icon iconfont">&#xe718;</text>-->
                            <text class="i-box-tlt">Preparing</text>
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

            </cell>



            <cell class="overflow-box" v-if="user">
                <div class="mid-cell">
                    <div class="box-tlt "  >
                        <div class="box-left">
                            <image class="box-txt-icon" src="bmlocal://assets/wallet-01.png"></image>
                            <text class="box-txt">My Wallet</text>
                            <!--<text class=" box-dot" v-if="unreadR>0"></text>-->
                        </div>
                        <text class="i-box wallet-num">₹ {{myWallet||0}}</text>
                    </div>

                    <div class="box-tlt "    @click="openCell(5)">
                        <div class="box-left">
                            <image class="box-txt-icon" src="bmlocal://assets/account/jifen2-01.png"></image>
                            <text class="box-txt">My Points</text>
                            <!--<text class=" box-dot" v-if="unreadR>0"></text>-->
                        </div>
                        <div style="flex-direction: row;justify-content: space-between;align-items: center">
                            <text class="i-box1 wallet-num">{{points||0}}</text>
                            <text class="i-box2 iconfont">&#xe626;</text>

                        </div>

                    </div>

                    <div class="box-tlt " @click="openMyRaffleDraws()" >
                        <div class="box-left">
                            <image class="box-txt-icon" src="bmlocal://assets/mylucky.png"></image>
                            <text class="box-txt">My Lucky Draws</text>
                            <text class=" box-dot" v-if="unreadR>0"></text>
                        </div>
                        <text class="i-box iconfont">&#xe626;</text>
                    </div>

                    <div class="box-tlt " @click="openCell(6)" >
                        <div class="box-left">
                            <image class="box-txt-icon" src="bmlocal://assets/account/gift2-01.png"></image>
                            <text class="box-txt">My Vouchers</text>
                        </div>
                        <div style="flex-direction: row;justify-content: space-between;align-items: center">
                            <text class="i-box1 wallet-num">{{cardNumber||0}}</text>
                            <text class="i-box2 iconfont">&#xe626;</text>
                        </div>

                    </div>

                </div>
            </cell>

            <cell class="overflow-box" >
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

                    <!--<div class="box-tlt " @click="openNoti()" v-if="user">-->
                        <!--<div class="box-left">-->
                            <!--<image class="box-txt-icon" src="bmlocal://assets/pic-my-noti.png"></image>-->
                            <!--<text class="box-txt">My Notifications</text>-->
                            <!--<text class=" box-dot" v-if="unread>0"></text>-->
                        <!--</div>-->
                        <!--<text class="i-box iconfont">&#xe626;</text>-->
                    <!--</div>-->
                </div>

            </cell>

            <cell class="overflow-box">
                <div class="mid-cell mid-cell-bottom ">
                    <div class="box-tlt " @click="openCell(3)">
                        <div class="box-left">
                            <image class="box-txt-icon" src="bmlocal://assets/pic-about.png"></image>

                            <!--<text class="box-txt-icon">&#xe721;</text>-->
                            <text class="box-txt">About</text>
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

            </cell>

        </list>
    </div>
</template>
<script>
    import he from '../utils/he';
    import header from './header';
    import { SERVICES } from './config'
    import refresher from '../common/refresh';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    Vue.filter('myFilter', function (value) {
        return he.decode(value);
    })

    export default {
        components: {
            'refresher': refresher,
            'topic-header': header
        },
        created () {
            googleAnalytics.trackingScreen('Account');
            this.getCartNum()
            this.$event.on('login', params => {
                this.getUserData()
            })
            this.$event.on('logout', parmas => {
                this.token = null
                this.user = null
                this.points = '0'
                this.cardNumber = '0'
            })
            this.getUserData()
            this.$event.on('redeem', parmas => {
                this.getUserData()
            })
            this.$event.on('read', parmas => {
                this.unread = 0
            })
            this.$event.on('readR', parmas => {
                this.unreadR = 0
            })
            this.$event.on('raffle', parmas => {
                this.getUserData()
            })
            this.$event.on('mySign', parmas => {
                this.getUserData()
            })
            this.$event.on('getMyWallet', parmas => {
                this.getMyWallet()
            })
        },
        destory () {
            this.$event.off('login')
            this.$event.off('logout')
            this.$event.off('setdetail')
            this.$event.off('read')
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
                points: '0',
                cardNumber: '0',
                setsign: '',
                myWallet: '',
                unread: 0,
                cartNum: 1,
                unreadR: 0
            }
        },
        methods: {
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.getUserData();
                this.getMyWallet();
            },
            openMyRaffleDraws () {
                this.$router.open({
                    name: 'my.raffle.draws',
                    type: 'PUSH'
                })
            },
            openMyCart () {
                this.$router.open({
                    name: 'cart',
                    type: 'PUSH'
                })
            },
            openTmp () {
                this.$router.open({
                    name: 'category',
                    type: 'PUSH'
                })
            },
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
                    if (this.user == null) {
                        this.$router.open({
                            name: 'login',
                            type: 'PUSH'
                        })
                    } else {
                        this.$router.open({
                            name: 'my.points',
                            type: 'PUSH',
                            params: {
                                num: this.points
                            }
                        })
                    }
                } else {
                    if (this.user == null) {
                        this.$router.open({
                            name: 'login',
                            type: 'PUSH'
                        })
                    } else {
                        this.$router.open({
                            name: 'my.card',
                            type: 'PUSH'
                        })
                    }
                }
            },
            openNoti () {
                this.$router.open({
                    name: 'my.notice',
                    type: 'PUSH'
                })
            },
            getCartNum () {
                this.$fetch({
                    method: 'GET',
                    name: 'cart.count',
                    header: {
                        needAuth: true,
                        isLoginPop: true
                    }

                }).then(data => {
                    this.cartNum = data.count
                    // this.$notice.toast({
                    //     message: data
                    // })
                }, error => {})
            },
            getUserData () {
                this.$event.on('setdetail', parmas => {
                    this.img = parmas.avatar
                    this.fname = parmas.firstName
                    this.lname = parmas.lastName
                    this.gender = parmas.gender
                    // this.$notice.alert({
                    //     message: parmas
                    // })
                })

                this.$storage.get('token').then(resData => {
                    this.token = resData
                    // this.$notice.alert({
                    //     message: resData
                    // })
                    if (this.token !== null) {
                        this.$fetch({
                            method: 'GET',
                            name: 'user.userprofile',
                            header: {
                                needAuth: true
                            }
                        }).then((res) => {
                            this.$notice.alert({
                                message: res
                            })
                            this.nickname = res.firstName + ' ' + res.lastName
                            this.fname = res.firstName
                            this.lname = res.lastName
                            this.img = res.avatar
                            this.points = res.points
                            this.cardNumber = res.cardNumber
                            this.gender = res.gender
                            this.unread = res.unreadNumber
                            this.$event.emit('getRead', {
                                unread: this.unread
                            });
                        }).catch((res) => {
                            // this.$notice.toast({
                            //     message: res
                            // })
                        })
                        this.$fetch({
                            method: 'GET',
                            name: 'lottery.draw.prize.unread',
                            header: {
                                needAuth: true
                            }
                        }).then((res) => {
                            this.unreadR = res.count
                        }).catch((res) => {
                            // this.$notice.loading.hide();
                            // this.$notice.toast({
                            //     message: res
                            // })
                        })
                        this.getMyWallet()
                    } else {
                        this.$notice.toast({
                            message: 'please login'
                        })
                    }
                })
                this.$storage.get('user').then(resData => {
                    this.user = resData
                    this.email = this.user.email
                    this.fname = resData.firstName
                    this.lname = resData.lastName
                })
            },
            getMyWallet () {
                this.$fetch({
                    method: 'GET',
                    name: 'point.cashing.amount',
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    this.myWallet = res.amount
                    // this.$notice.alert({
                    //     message: res.amount
                    // })
                }).catch((res) => {
                    // this.$notice.loading.hide();
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            },
            tranString (s) {
                // const a = s.split()
                // if (a.length > 18) {
                //     a.splice(18, a.length)
                // }
                return s.substring(0,18) + '...'
            }
        }
    }
</script>
<style lang="sass" src="./index.scss"></style>
