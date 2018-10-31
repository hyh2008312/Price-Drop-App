<template>
    <div class="wrapper">
        <div class="status-bar"></div>
        <div class="wrapper-header">
            <text class="back iconfont" @click="back()">&#xe6f6;</text>
            <text class="title">{{title}}</text>
        </div>
        <list class="container" :style="height">
            <!--<cell class="container-bg">-->
                <!--<text class="title-1">Payment Method</text>-->
            <!--</cell>-->

            <cell class="cell-bottom "  @click="chooseMethod('paytm')">
                <div  class="overflow-box b-top-r">
                    <div class="overflow-box1" >
                        <div>
                            <image class="item-image" resize="contain" :src="paytmSrc"></image>
                            <text class="item-text">Wallet / UPI / Credit&Debit Card / Net Banking </text>
                        </div>
                        <text class="iconfont item-checked" v-if="method == 'paytm'">&#xe6fb;</text>
                        <text class="iconfont item-no-checked" v-if="method != 'paytm'">&#xe73f;</text>
                    </div>
                </div>
            </cell>

            <!--<cell  class="cell-bottom" @click="chooseMethod('razorpay')">-->
                <!--<div  class="overflow-box">-->
                    <!--<div class="overflow-box1" >-->
                        <!--<div>-->
                            <!--<image class="item-image-1" resize="contain" :src="razorpaySrc"></image>-->
                            <!--<text class="item-text">UPI / Credit&Debit Card / Net Banking </text>-->
                        <!--</div>-->
                        <!--<text class="iconfont item-checked" v-if="method == 'razorpay'">&#xe6fb;</text>-->
                        <!--<text class="iconfont item-no-checked" v-if="method != 'razorpay'">&#xe73f;</text>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</cell>-->

            <cell  class="cell-bottom" @click="chooseMethod('cod')">
                <div  class="overflow-box b-bottom-r">
                    <div class="overflow-box1" >
                        <div>
                            <div class="cod-d">
                                <image class="item-image-2"  :src="codSrc"></image>
                                <text class="cod-text">Cash/Card on Delivery</text>
                            </div>
                            <text class="item-text">Cash / Debit Card / Credit Card at your doorstep</text>
                        </div>
                        <div v-if="order.cod && (CODStatus==1||CODStatus==2)">
                            <text class="iconfont item-checked" v-if="method == 'cod'">&#xe6fb;</text>
                            <text class="iconfont item-no-checked" v-if="method != 'cod'">&#xe73f;</text>
                        </div>
                        <text class="iconfont item-checked-disable" v-if="!order.cod ||CODStatus==3">&#xe73f;</text>
                    </div>
                </div>
            </cell>

            <cell>
                <text class="title-2">Order#:{{order.number}}</text>
            </cell>
            <cell v-if="isShowBalance">
                <div class="balance-bg">
                    <div class="balance-bg-left">
                        <text class="title-3">Wallet Balance</text>
                        <text class="title-4">₹{{balance}}</text>
                    </div>
                    <div class="switch-bg" v-if="!checked" @click="changeSwitch">
                        <div class="circle-no-checked-bg"></div>
                        <div class="circle-no-checked"></div>
                    </div>
                    <div class="switch-bg" v-if="checked" @click="changeSwitch">
                        <div class="circle-checked-bg"></div>
                        <div class="circle-checked"></div>
                    </div>
                </div>
            </cell>
        </list>
        <order-payment-method-bottom :order="order" :method="method" :source="source" :balance="balance" :checked="checked" ></order-payment-method-bottom>

        <wxc-popup width="750"
                   :height="sHeight"
                   pos="bottom"
                   :show="isShow"
                   @wxcPopupOverlayClicked="overlayClicked">
            <div class="popup-content">
                <div class="popup-wrapper">
                    <div class="popup-item1">
                        <text class="p-t1-i iconfont" @click="overlayClicked">&#xe632;</text>
                        <text class="p-t1-t">Confirm COD Order</text>
                    </div>
                    <div class="popup-item2">
                        <text class="p-i2-t">Mobile Phone</text>
                        <div class="p-i2-d">
                            <text class="p-i2-dt">+91</text>
                            <!--<input class="p-t2-i" type="number" :value="phone" @input="oninput"  />-->
                            <text class="p-t2-i" >{{prePhone}}</text>
                        </div>
                    </div>
                    <div class="popup-item3">
                        <text class="p-i3-t">Verify Code</text>
                        <input class="p-t3-i" type="number" placeholder="" :value="verifyCode" @input="oninput1"  />
                        <text class="p-t3-t1" v-if="!disabled" @click="checkPhoneNumber">{{'Get Code'}}</text>
                        <text class="p-t3-t2" v-if="disabled" >{{second}}</text>
                    </div>
                    <div class="popup-item3">
                        <text :class="[successS?'p-t3-success':'p-t3-err']">{{errMsg||'&nbsp;&nbsp;&nbsp;&nbsp;'}}</text>
                    </div>

                    <div class="popup-item4">
                        <div :class="[selItem4?'lc-t-dot-select':'lc-t-dot']" >
                            <text class="dot-sel">&radic;</text>
                        </div>
                        <text class="p-i4-t">By confirming this order, you agree to our COD Policy & Terms of Service.</text>
                    </div>
                    <div class="popup-item5" v-if="selItem4" @click="postCode">
                        <text class="p-i5-t" >Confirm</text>
                    </div>
                    <div class="popup-item5-dis" v-if="!selItem4" >
                        <text class="p-i5-t" >Confirm</text>
                    </div>
                </div>
            </div>
        </wxc-popup>
    </div>
</template>
<script>
import orderPaymentMethodBottom from './cartOrderPaymentMethodBottom';
import { Utils, WxcPopup } from 'weex-ui';
const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
const common = weex.requireModule('CommonUtils');
const tool = weex.requireModule('bmTool')

export default {
    components: {
        orderPaymentMethodBottom, WxcPopup
    },
    eros: {
        appeared (params, option) {
            this.order = params.data;
            this.$notice.alert({
                message: params.data
            })
            this.prePhone = params.phone;
            this.checkCODStatus();
            this.source = params.source;
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight();
        this.sHeight = pageHeight - 48

        this.height = { height: (pageHeight - 112 - 112 - 48 - 2) + 'px' };
        googleAnalytics.trackingScreen('Confirm Billing and Shipping');
        this.getBalance();
        this.initBack();
        // this.checkCODStatus();
        if (this.$storage.getSync('user')) {
            this.user = this.$storage.getSync('user')
        } else {
            this.user = null
        }
        this.$event.on('closePayment', params => {
            this.$router.finish();
        });
    },
    data () {
        return {
            title: 'Payment',
            method: 'paytm',
            paytmSrc: 'bmlocal://assets/paytm.png',
            razorpaySrc: 'bmlocal://assets/razorpay.png',
            codSrc: 'bmlocal://assets/COD-01.png',
            source: 'confirm',
            order: {},
            prePhone: '',
            checked: false,
            balance: 0,
            isShowBalance: false,
            isShow: false,
            sHeight: 1340,
            phone: '',
            verifyCode: '',
            second: 0,
            disabled: '',
            errMsg: '',
            successS: false,
            user: '',
            selItem4: true,
            CODStatus: ''
        }
    },
    methods: {
        initBack () {
            common.setAndroidCanBack(true, (params) => {
                this.overlayClicked();
                common.changeAndroidCanBack(true)
            })
        },
        back () {
            if (this.source == 'confirm') {
                this.$router.finish();
                this.$router.open({
                    name: 'order',
                    type: 'PUSH'
                });
            } else {
                this.$router.finish();
            }
        },
        changeSwitch () {
            this.checked = !this.checked;
        },
        checkItem () {
            this.selItem4 = !this.selItem4
        },
        checkCODStatus () {
            // this.$notice.alert({
            //     message: this.prePhone
            // })
            this.$fetch({
                method: 'POST', // 大写
                name: 'user.check.mobile.status',
                header: {
                    needAuth: true
                },
                data: {
                    mobile: this.prePhone
                }
            }).then((res) => {
                // 成功回调
                if (res.code == 30000) {
                    this.CODStatus = 1     // 1 可以绑定 2 已经绑定 3黑名单手机号
                } else if (res.code == 30003) {
                    this.CODStatus = 2
                } else if (res.code == 30002) {
                    this.CODStatus = 3
                }
            }).catch((res) => {
                // this.$notice.toast({
                //     message: res
                // })
            })
        },
        chooseMethod (e) {
            if (e == 'cod') {
                if (this.order.cod && this.CODStatus==1) {
                    this.method = e;
                    this.isShow = true;
                    common.changeAndroidCanBack(false)
                } else if (this.CODStatus==2) {
                    this.method = e;
                } else if (this.CODStatus==3) {}
            } else {
                this.method = e;
            }
        },
        getBalance () {
            this.$fetch({
                method: 'GET', // 大写
                name: 'point.cashing.amount',
                header: {
                    needAuth: true
                }
            }).then(resData => {
                // 成功回调
                this.balance = resData.amount;
                if (resData.amount > 0 && this.order.paymentAmount > 0) {
                    this.isShowBalance = true;
                }
            }, error => {
                // 错误回调
                // this.$notice.toast({
                //     message: error
                // })
            })
        },
        oninput (e) {
            this.phone = e.value
        },
        oninput1 (e) {
            this.verifyCode = e.value
        },
        checkPhoneNumber () {
            if (this.second > 0) {
                return
            }
            if (this.prePhone.length < 10) {
                this.errMsg = 'Your phone number is in wrong format.'
            } else {
                this.time()
                this.$fetch({
                    method: 'POST',
                    name: 'user.send.mobile.message',
                    header: {
                        needAuth: true
                    },
                    data: {
                        phoneMobile: this.prePhone,
                        internationalCode: 86
                    }
                }).then((res) => {
                    if (res.code == 30000) {
                        this.successS = true
                    } else {
                        this.successS = false
                    }
                    this.errMsg = res.message
                    // this.$notice.alert({
                    //     message: res
                    // })
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            }
        },
        postCode () {
            this.$fetch({
                method: 'POST',
                name: 'user.verify.mobile.code',
                header: {
                    needAuth: true
                },
                data: {
                    mobile: this.prePhone,
                    code: this.verifyCode
                }
            }).then((res) => {
                if (res.code == 30000) {
                    tool.resignKeyboard((resData) => {});
                    this.$notice.toast({
                        message: 'success'
                    })
                    this.isShow = false
                    common.changeAndroidCanBack(true)
                    this.successS = true
                } else {
                    this.successS = false
                }
                this.errMsg = res.message
                // this.$notice.alert({
                //     message: res
                // })
            }).catch((res) => {
                // this.$notice.toast({
                //     message: res
                // })
            })
        },
        time () {
            let timer = null;
            clearInterval(timer);
            let time = 60;
            // let time = 10;
            timer = setInterval(() => {
                console.log(time);
                if (time < 0) {
                    this.second = '';
                    this.disabled = false;
                } else if (time == 0) {
                    this.second = 'Resend Code';
                    this.disabled = false;
                    // this.isF = true;
                    clearInterval(timer);
                } else {
                    this.disabled = true;
                    this.second = '';
                    this.second = time + 's';
                    time--;
                }
            }, 1000);
        },
        buttonClicked () {
            common.changeAndroidCanBack(false)
            this.isShow = true;
        },
        overlayClicked () {
            this.isShow = false;
        }
    }
}

</script>
<style scoped>

    .iconfont {
        font-family: iconfont;
    }

    .wrapper{
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        background-color: #f1f1f1;
    }

    .wrapper-header{
        position: sticky;
        margin-top: 48px;
        width: 750px;
        height: 112px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #fff;
        z-index: 100;
        box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
    }

    .back{
        font-size: 40px;
        line-height: 40px;
        padding-left: 32px;
        padding-top: 32px;
        padding-bottom: 32px;
    }

    .title{
        width: 622px;
        font-size: 32px;
        line-height: 38px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        text-align: center;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .title-1{
        font-family: ProximaNova;
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        padding: 32px;
    }

    .title-2{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 80px;
        color: #000;
        width: 750px;
        padding: 0 32px;
        background-color: rgba(0,0,0,0.08);
        box-shadow: 0 2px 2px 0 rgba(0,0,0,0.12);
        border-bottom-right-radius: 4px;
        border-bottom-left-radius: 4px;
    }

    .title-3{
        font-family: ProximaNova;
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        margin-left: 32px;
    }

    .title-4{
        font-family: ProximaNova;
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.87);
        margin-left: 20px;
    }

    .status-bar{
        position: fixed;
        top: 0;
        height: 48px;
        background-color: #000;
        width: 750px;
    }

    .container{
        margin-top: 2px;
        width: 750px;
        background-color: #f1f1f1;
    }

    .container-bg{
        background-color: #fff;
    }

    .cell-bottom{
        /*padding-bottom: 32px;*/
        padding-left: 32px;
        padding-right: 32px;
    }
    .overflow-box{
        width: 686px;
        background-color: #fff;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .b-top-r{
        margin-top: 28px;
        border-top-right-radius: 8px;
        border-top-left-radius: 8px;
    }
    .b-bottom-r{
        margin-bottom: 28px;
        border-bottom-right-radius: 8px;
        border-bottom-left-radius: 8px;
    }
    .overflow-box1{
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        width: 620px;
        margin: 32px 32px 0 32px;
        padding-bottom: 32px;
        border-bottom-width:1px ;
        border-bottom-style:solid ;
        border-bottom-color: rgba(0,0,0,.08) ;
    }

    .item-image{
        width: 152px;
        height: 48px;
    }

    .item-text{
        margin-top: 16px;
        font-family: ProximaNova;
        font-size: 20px;
        line-height: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
    }
    .cod-d{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        /*background-color: black;*/
    }
    .cod-text{
        font-size: 32px;
        color: #000000;
        letter-spacing: 0;
        text-align: left;
        font-weight: 700;
    }
    .item-image-1{
        width: 227px;
        height: 48px;
    }
    .item-image-2{
        width: 50px;
        height: 40px;
        margin-right: 16px;
    }

    .item-checked{
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
    }

    .item-no-checked{
        font-size: 32px;
        line-height: 32px;
        color: rgba(0,0,0,0.32);
    }
    .item-checked-disable{
        font-size: 32px;
        line-height: 32px;
        color:rgba(0,0,0,.19);
        background-color: rgba(0,0,0,.19);
        border-radius: 50%;
    }
    .balance-bg{
        margin-top: 32px;
        margin-left: 32px;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        width: 686px;
        height: 96px;
        background-color: #ffffff;
    }

    .balance-bg-left{
        flex-direction: row;
        align-items: center;
        justify-content: start;
    }

    .switch-bg{
        padding: 16px 32px;
        width: 124px;
        height: 64px;
    }

    .circle-no-checked{
        background-color: #FFFFFF;
        box-shadow: 0 1px 2px 0 rgba(0,0,0,0.24);
        width: 32px;
        height: 32px;
        border-radius: 16px;
        position: absolute;
        left: 32px;
        top: 16px;
    }

    .circle-no-checked-bg{
        background-color: #D9D9D9;
        box-shadow: inset 0 0 2px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
        width: 60px;
        height: 24px;
        position: absolute;
        right: 32px;
        top: 20px;
    }

    .circle-checked{
        background-color: #EF8A31;
        box-shadow: 0 1px 2px 0 rgba(0,0,0,0.24);
        width: 32px;
        height: 32px;
        border-radius: 16px;
        position: absolute;
        right: 32px;
        top: 16px;
    }
    .circle-checked-bg{
        background-color: #F6DCC5;
        box-shadow: inset 0 0 2px 0 rgba(0,0,0,0.12);
        border-radius: 16px;
        width: 60px;
        height: 24px;
        position: absolute;
        right: 32px;
        top: 20px;
    }
    .popup-content{
        width: 750px;
        /*height: 522px;*/
        flex-direction: row;
        justify-content: center;
    }
    .popup-wrapper{
        width: 686px;
        /*background-color: #689de5;*/
    }
    .popup-item1{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 48px;
    }
    .p-t1-t{
        font-size: 28px;
        color: #000000;
        letter-spacing: 0;
        font-weight: 700;
        width: 600px;
        text-align: center;
        /*background-color: #EF8A31;*/
    }
    .p-t1-i{
        font-size: 50px;
        color: #333333;
        /*background-color: #689de5;*/
    }
    .popup-item2{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 48px;
    }
    .p-i2-t{
        font-size: 24px;
        color: rgba(0,0,0,.87);
        letter-spacing: 0;
    }
    .p-i2-d{
        flex-direction: row;
        justify-content: start;
        margin-left: 34px;
    }
    .p-i2-dt{
        height: 64px;
        /*width: 66px;*/
        padding-top: 16px;
        padding-right: 16px;
        padding-left: 16px;
        text-align: center;
        font-size: 24px;
        border-top-left-radius: 8px;
        border-bottom-left-radius: 8px;

        border-top-style: solid ;
        border-top-color: rgba(0,0,0,.12);
        border-top-width:1px;

        border-bottom-style: solid ;
        border-bottom-color: rgba(0,0,0,.12);
        border-bottom-width:1px;

        border-left-style: solid ;
        border-left-color: rgba(0,0,0,.12);
        border-left-width:1px;
    }
    .p-t2-i{
        width: 408px;
        height: 64px;
        border-top-right-radius: 8px;
        border-bottom-right-radius: 8px;
        color: black;
        padding-left: 16px;
        padding-top: 14px;
        background-color: rgba(0,0,0,.12);
        border-style: solid;
        border-color: rgba(0,0,0,.12);
        border-width:1px ;
    }
    .popup-item3{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 24px;
    }
    .p-i3-t{
        font-size: 24px;
        color: rgba(0,0,0,.87);
        margin-left: 24px;
        margin-right: 34px;
    }
    .p-t3-i{
        width: 322px;
        height: 64px;
        padding-left: 16px;
        color: black;
        border-radius: 8px;
        border-style: solid;
        border-color: rgba(0,0,0,.12);
        border-width:1px ;
        margin-right: 16px;
    }
    .p-t3-err{
        font-size: 20px;
        color: red;
        width: 580px;
        margin-left: 185px;
    }
    .p-t3-success{
        font-size: 20px;
        color: green;
        width: 580px;
        margin-left: 185px;
    }
    .p-t3-t1{
        font-size: 24px;
        color: #FFFFFF;
        font-weight: 700;
        padding: 21px 22px;
        border-radius: 8px;
        background-color: #579CDE;
    }
    .p-t3-t2{
        font-size: 24px;
        color: #FFFFFF;
        font-weight: 700;
        padding: 21px 0;
        border-radius: 8px;
        background-color: #579CDE;
        width: 146px;
        text-align: center;
    }
    /*.p-t3-t1-d{*/
        /*font-size: 24px;*/
        /*color: #FFFFFF;*/
        /*font-weight: 700;*/
        /*padding: 21px;*/
        /*border-radius: 8px;*/
        /*background-color: grey;*/
    /*}*/
    .popup-item4{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 24px;
    }
    .p-i4-t{
        font-size: 20px;
        color: rgba(0,0,0,.54);
        width: 580px;
        line-height: 34px;
        margin-top: 16px;
    }
    .dot-sel{
        color: white;
        padding-left:6px;
    }
    .lc-t-dot{
        width: 32px;
        height: 32px;
        margin-right: 22px;
        background-color: #fff;
        border-width:  1px;
        border-style:  solid;
        border-color:  grey;
        border-radius: 4px;
    }
    .lc-t-dot-select{
        width: 32px;
        height: 32px;
        margin-right: 22px;
        /*padding-left: 16px;*/
        /*padding-right: 16px;*/
        background-color: #EF8A31;
        border-radius: 4px;
    }
    .popup-item5{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        width: 686px;
        height: 80px;
        background-color: #EF8A31;
        margin-top: 24px;
        border-radius: 8px;
    }
    .popup-item5-dis{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        width: 686px;
        height: 80px;
        background-color: grey;
        margin-top: 24px;
        border-radius: 8px;
    }
    .p-i5-t{
        font-size: 24px;
        color: #FFFFFF;
        letter-spacing: 0;
        font-weight: 700;
    }

</style>
