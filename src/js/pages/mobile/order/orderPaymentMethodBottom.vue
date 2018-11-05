<template>
    <div class="wrapper">
        <text class="od-text">Total:  </text>
        <text class="od-text-1">₹{{((order.paymentAmount * 100 - (checked ? balance : 0 ) * 100 < 0 ? 0 : (order.paymentAmount * 100 - (checked ? balance : 0 ) * 100)) / 100).toFixed(2)}}</text>
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
                    if (that.method == 'paytm') {
                        that.$notice.loading.show();
                        googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', '', 'paytm', 'Rs', 0);
                        googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'paytm', 0);
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
                                that.$router.finish();
                                that.$router.open({
                                    name: 'order.success',
                                    type: 'PUSH',
                                    params: {
                                        source: that.source
                                    }
                                });
                                return;
                            }
                            googleAnalytics.trackingScreen('Select Payment');
                            const paymentAmount = resData.amount;
                            pay.startPaytmRequest(resData.paytmOrderId, resData.orderNumber, resData.amount,
                                resData.order.phoneNumber, resData.order.ownerEmail, resData.paytmCallbackUrl,
                                resData.paytmChecksum, (data) => {
                                    googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'paytm sdk success return', 0);
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
                                        that.$router.finish();
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
                                            that.$router.finish();
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
                    } else if (that.method == 'razorpay') {
                        that.$notice.loading.show();
                        googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', '', 'razorpay', 'Rs', 0);
                        googleAnalytics.recordEvent('Payment', 'Initial Checkout', 'razorpay sdk success return', 0);
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
                                that.$router.finish();
                                that.$router.open({
                                    name: 'order.success',
                                    type: 'PUSH',
                                    params: {
                                        source: that.source
                                    }
                                });
                                return;
                            }
                            googleAnalytics.trackingScreen('Select Payment');
                            pay.startPayRequest(order.razorpayOrderId, order.order.lines[0].title, 'Order#: ' + order.orderNumber,
                                that.order.lines[0].mainImage, parseInt(payAmount), user.defaultAddress.phoneNumber, user.email,
                                function (param) {
                                    googleAnalytics.recordEvent('Razorpay', 'Pay Now Success', that.order.id, 0);
                                    that.$notice.loading.show();
                                    that.$fetch({
                                        method: 'POST', // 大写
                                        name: 'payment.razorpay.check',
                                        data: {
                                            orderId: order.order.id,
                                            razorpayPaymentId: param.razorPaymentId,
                                            razorpayOrderId: param.razorOrderId,
                                            razorpaySignature: param.razorSignature
                                        },
                                        header: {
                                            needAuth: true
                                        }
                                    }).then(resData => {
                                        googleAnalytics.facebookRecordEvent('fb_mobile_purchase', '', '', 'Rs', payAmount);
                                        that.$event.emit('getMyWallet');
                                        that.$notice.loading.hide();
                                        that.$router.finish();
                                        that.$router.open({
                                            name: 'order.success',
                                            type: 'PUSH',
                                            params: {
                                                source: that.source
                                            }
                                        });
                                    }, error => {
                                        that.$notice.toast({
                                            message: error
                                        });
                                    })
                                }, function (param) {
                                    if (that.source == 'confirm') {
                                        that.$router.finish();
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
                                            that.$router.finish();
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
                            that.$router.finish();
                            that.$router.open({
                                name: 'order.success',
                                type: 'PUSH',
                                params: {
                                    source: that.source,
                                    order: that.order
                                }
                            });
                            this.$event.emit('closePayment');
                        }).catch((res) => {
                            // this.$notice.toast({
                            //     message: res
                            // })
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
        margin-right: 16px;
        font-size: 28px;
        line-height: 80px;
        text-align: center;
        padding: 0 24px;
        background-color: #EF8A31;
        border-radius: 4px;
        color: #fff;
    }

    .od-button-no{
        margin-left: 24px;
        margin-right: 16px;
        font-size: 28px;
        line-height: 80px;
        text-align: center;
        padding: 0 24px;
        background-color: rgba(128,128,128,.74);
        border-radius: 4px;
        color: #fff;
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

</style>
