<template>
    <div class="wrapper">
        <text class="od-text">Total:  </text>
        <text class="od-text-1" v-if="method=='cod'">₹{{ (parseInt(order.paymentAmount) - parseInt(checked? balance: 0))<=0 ? 0: (parseInt(order.paymentAmount) - parseInt(checked? balance: 0)) }}</text>

        <text class="od-text-1" v-if="method!='cod'"> ₹{{(parseInt((order.paymentAmount)*0.95) - parseInt(checked? balance: 0))<=0? 0: parseInt((order.paymentAmount)*0.95) - parseInt(checked? balance: 0)}} </text>
        <text class="od-text-lt" v-if="method!='cod'">₹{{parseInt(order.paymentAmount)}}</text>

        <text class="percent" v-if="method!='cod'"> 5% OFF</text>
        <text class="od-button" v-if="isCanPay" @click="confirm">Pay Now</text>
        <text class="od-button-no" v-if="!isCanPay">Pay Now</text>
    </div>
</template>
<script>
    const pay = weex.requireModule('PayModule');
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    export default {
        props: ['order', 'method', 'isCanPay', 'source', 'balance', 'checked'],
        data () {
            return {
                isFirst: false
            }
        },
        methods: {
            confirm () {
                const that = this;
                if (!this.isFirst) {
                    this.isFirst = true;
                    if (that.method == 'payU') {
                        that.$notice.loading.show();
                        googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', '', 'PayU Clicks', 'Rs', 0);
                        googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'PayU Clicks', 0);
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'payment.payu.checksum',
                            data: {
                                orderId: that.order.id,
                                bonus: this.checked ? this.checked : null
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            const payAmount = resData.payment.amount;
                            if (payAmount <= 0) {
                                that.$event.emit('getMyWallet');
                                that.$event.emit('paid');
                                that.$event.emit('closePayment');
                                that.$router.finish();
                                that.$router.open({
                                    name: 'order.success',
                                    type: 'PUSH',
                                    params: {
                                        source: that.source,
                                        order: that.order
                                    }
                                });
                                return;
                            }
                            googleAnalytics.trackingScreen('Select Payment');
                            pay.startPayUmoneyRequest(resData.posted.txnid, resData.payment.amount, resData.posted.email,
                                resData.payment.order.phoneNumber, resData.posted.productinfo,
                                resData.posted.firstname, resData.payment.payuHash, (data) => {
                                googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'PayU Successful', 0);
                                    if (data.code == 200) {
                                        that.$notice.loading.show();
                                        that.$fetch({
                                            method: 'POST', // 大写
                                            name: 'payment.payu.status',
                                            data: {
                                                txnid: data.txnid,
                                                amount: data.amount,
                                                additionalCharges: data.additionalCharges,
                                                email: data.email,
                                                firstname: data.firstname,
                                                hash: data.hash,
                                                productinfo: data.productinfo,
                                                status: data.status,
                                                key: data.key,
                                                orderId: resData.payment.order.id,
                                                bonus: that.checked ? that.checked : null
                                            },
                                            header: {
                                                needAuth: true
                                            }
                                        }).then(resData => {
                                            googleAnalytics.facebookRecordEvent('fb_mobile_purchase', '', '', 'Rs', payAmount);
                                            that.$event.emit('getMyWallet');
                                            that.$event.emit('paid');
                                            that.$event.emit('closePayment');
                                            that.$notice.loading.hide();
                                            that.$router.finish();
                                            that.$router.open({
                                                name: 'order.success',
                                                type: 'PUSH',
                                                params: {
                                                    source: that.source,
                                                    order: that.order
                                                }
                                            });
                                        }, error => {
                                            that.$notice.toast({
                                                message: error
                                            });
                                        })
                                    }
                                that.isFirst = false;
                            }, (data) => {
                                if (that.source == 'confirm') {
                                    that.$event.emit('closePayment');
                                    if (data.code == 300) {
                                        that.$router.open({
                                            name: 'order.failure',
                                            type: 'PUSH',
                                            params: {
                                                source: that.source
                                            }
                                        });
                                    } else {
                                        that.$router.open({
                                            name: 'order',
                                            type: 'PUSH'
                                        });
                                    }
                                } else {
                                    if (data.code == 300) {
                                        that.$router.open({
                                            name: 'order.failure',
                                            type: 'PUSH',
                                            params: {
                                                source: that.source
                                            }
                                        });
                                    } else {
                                        that.$event.emit('closePayment');
                                    }
                                }
                                that.isFirst = false;
                            });
                        }, error => {
                            that.$notice.loading.hide();
                            that.isFirst = false;
                            that.$notice.toast({
                                message: error
                            });
                        });
                    } else if (that.method == 'paytm') {
                        that.$notice.loading.show();
                        googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', '', 'Paytm Clicks', 'Rs', 0);
                        googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'Paytm Clicks', 0);
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'payment.paytm.checksum',
                            data: {
                                orderId: that.order.id,
                                bonus: that.checked ? that.checked : null
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            if (resData.amount <= 0) {
                                that.$event.emit('getMyWallet');
                                that.$event.emit('paid');
                                that.$event.emit('closePayment');
                                that.$router.finish();
                                that.$router.open({
                                    name: 'order.success',
                                    type: 'PUSH',
                                    params: {
                                        source: that.source,
                                        order: that.order
                                    }
                                });
                                return;
                            }
                            googleAnalytics.trackingScreen('Select Payment');
                            const paymentAmount = resData.amount;
                            pay.startPaytmRequest(resData.paytmOrderId, resData.orderNumber, resData.amount,
                                resData.order.phoneNumber, resData.order.ownerEmail, resData.paytmCallbackUrl,
                                resData.paytmChecksum, (data) => {
                                    googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'Paytm Successful', 0);
                                    if (data.code == 200) {
                                        that.$notice.loading.show();
                                        that.$fetch({
                                            method: 'POST', // 大写
                                            name: 'payment.paytm.get.status',
                                            data: {
                                                paytmOrderId: data.orderId,
                                                checksum: data.checkSumHash,
                                                orderId: resData.order.id,
                                                bonus: that.checked ? that.checked : null
                                            },
                                            header: {
                                                needAuth: true
                                            }
                                        }).then(resData => {
                                            googleAnalytics.facebookRecordEvent('fb_mobile_purchase', '', '', 'Rs', paymentAmount);
                                            that.$event.emit('getMyWallet');
                                            that.$event.emit('paid');
                                            that.$event.emit('closePayment');
                                            that.$notice.loading.hide();
                                            that.$router.finish();
                                            that.$router.open({
                                                name: 'order.success',
                                                type: 'PUSH',
                                                params: {
                                                    source: that.source,
                                                    order: that.order
                                                }
                                            });
                                        }, error => {
                                            that.$notice.toast({
                                                message: error
                                            });
                                        })
                                    }
                                }, (data) => {
                                    if (that.source == 'confirm') {
                                        that.$event.emit('closePayment');
                                        if (data.code == 300) {
                                            that.$router.open({
                                                name: 'order.failure',
                                                type: 'PUSH',
                                                params: {
                                                    source: that.source
                                                }
                                            });
                                        } else {
                                            that.$router.open({
                                                name: 'order',
                                                type: 'PUSH'
                                            });
                                        }
                                    } else {
                                        if (data.code == 300) {
                                            that.$router.open({
                                                name: 'order.failure',
                                                type: 'PUSH',
                                                params: {
                                                    source: that.source
                                                }
                                            });
                                        } else {
                                            that.$event.emit('closePayment');
                                        }
                                    }
                                });
                            that.isFirst = false;
                        }, error => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            that.isFirst = false;
                            that.$notice.toast({
                                message: error
                            });
                        });
                    } else if (that.method == 'razorpay1'||that.method == 'razorpay2'||that.method == 'razorpay3') {
                        that.$notice.loading.show();
                        googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', '', 'Razorpay Clicks', 'Rs', 0);
                        googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'Razorpay Clicks', 0);
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'payment.razorpay.create',
                            data: {
                                orderId: that.order.id,
                                bonus: this.checked ? this.checked : null
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            const order = resData;
                            const user = that.$storage.getSync('user');
                            const price = resData.amount.split('.');
                            const payAmount = price[0] + price[1];
                            if (payAmount <= 0) {
                                that.$event.emit('getMyWallet');
                                that.$event.emit('paid');
                                that.$event.emit('closePayment');
                                that.$router.finish();
                                that.$router.open({
                                    name: 'order.success',
                                    type: 'PUSH',
                                    params: {
                                        source: that.source,
                                        order: that.order
                                    }
                                });
                                return;
                            }
                            googleAnalytics.trackingScreen('Select Payment');
                            pay.startPayRequest(order.razorpayOrderId, order.order.lines[0].title, 'Order#: ' + order.orderNumber,
                                that.order.lines[0].mainImage, parseInt(payAmount), user.defaultAddress.phoneNumber, user.email,
                                function (param) {
                                    googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'Razorpay Successful', 0);
                                    that.$notice.loading.show();
                                    that.$fetch({
                                        method: 'POST', // 大写
                                        name: 'payment.razorpay.check',
                                        data: {
                                            orderId: order.order.id,
                                            razorpayPaymentId: param.razorPaymentId,
                                            razorpayOrderId: param.razorOrderId,
                                            razorpaySignature: param.razorSignature,
                                            bonus: that.checked ? that.checked : null
                                        },
                                        header: {
                                            needAuth: true
                                        }
                                    }).then(resData => {
                                        googleAnalytics.facebookRecordEvent('fb_mobile_purchase', '', '', 'Rs', payAmount);
                                        that.$event.emit('getMyWallet');
                                        that.$event.emit('paid');
                                        that.$event.emit('closePayment');
                                        that.$notice.loading.hide();
                                        that.$router.finish();
                                        that.$router.open({
                                            name: 'order.success',
                                            type: 'PUSH',
                                            params: {
                                                source: that.source,
                                                order: that.order
                                            }
                                        });
                                    }, error => {
                                        that.$notice.toast({
                                            message: error
                                        });
                                    })
                                }, function (param) {
                                    if (that.source == 'confirm') {
                                        that.$event.emit('closePayment');
                                        if (param.code != 0) {
                                            that.$router.open({
                                                name: 'order.failure',
                                                type: 'PUSH',
                                                params: {
                                                    source: that.source
                                                }
                                            });
                                        } else {
                                            that.$router.open({
                                                name: 'order',
                                                type: 'PUSH'
                                            });
                                        }
                                    } else {
                                        if (param.code != 0) {
                                            that.$router.open({
                                                name: 'order.failure',
                                                type: 'PUSH',
                                                params: {
                                                    source: that.source
                                                }
                                            });
                                        } else {
                                            that.$event.emit('closePayment');
                                        }
                                    }
                                });
                            that.isFirst = false;
                        }, error => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            that.isFirst = false;
                            that.$notice.toast({
                                message: error
                            });
                        });
                    } else if (that.method == 'cod') {
                        that.$notice.loading.show();
                        googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', '', 'Cod Clicks', 'Rs', 0);
                        googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'Cod Clicks', 0);
                        this.$emit('change', 1);
                        this.$fetch({
                            method: 'POST',
                            name: 'order.cod.create',
                            header: {
                                needAuth: true
                            },
                            data: {
                                orderId: that.order.id,
                                type: 'normal',
                                bonus: this.checked ? this.checked : null
                            }
                        }).then((res) => {
                            that.$notice.loading.hide();
                            googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'Cod Successful', 0);
                            that.$router.open({
                                name: 'order.success',
                                type: 'PUSH',
                                params: {
                                    source: that.source,
                                    order: that.order
                                }
                            });
                            that.$event.emit('paid');
                            that.$event.emit('getMyWallet');
                            this.$event.emit('closePayment');
                        }).catch((res) => {
                            this.$notice.toast({
                                message: res.errorMsg
                            })
                        })
                    }
                }
            }
        }
    }
</script>
<style scoped>

    .wrapper {
        background-color: #fff;
        position: fixed;
        left: 0;
        right: 0;
        bottom: 0;
        width: 750px;
        height: 112px;
        box-shadow: 0 -1px 3px 0 rgba(0,0,0,0.12);
        overflow: hidden;
        flex-direction: row;
        align-items: center;
        justify-content: flex-end;
    }

    .od-button{
        margin-left: 24px;
        margin-right: 30px;
        font-size: 28px;
        line-height: 80px;
        text-align: center;
        padding: 0 32px;
        background-color: #EF8A31;
        border-radius: 50%;
        color: #fff;
        font-weight: 700;
    }

    .od-button-no{
        margin-left: 24px;
        margin-right: 30px;
        font-size: 28px;
        line-height: 80px;
        text-align: center;
        padding: 0 32px;
        background-color: rgba(128,128,128,.74);
        border-radius: 50%;
        color: #fff;
        font-weight: 700;
    }

    .od-text{
        font-size: 24px;
        line-height: 28px;
    }

    .od-text-1{
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
    }
    .percent{
        font-size: 20px;
        color: #EF8A31;
        margin-right: 32px;
    }
    .od-text-lt{
        font-size: 24px;
        text-decoration: line-through;
        color: #000000;
        margin: 0 7px;
    }
</style>
