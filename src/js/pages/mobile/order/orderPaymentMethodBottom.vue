<template>
    <div class="wrapper">
        <text class="od-text">Total:  </text>
        <text class="od-text-1">Rs.{{order.paymentAmount}}</text>
        <text class="od-button" @click="confirm">Pay Now</text>
    </div>
</template>
<script>
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    const pay = weex.requireModule('PayModule');
    export default {
        props: ['order', 'method'],
        data: {
            isFirst: false
        },
        methods: {
            confirm () {
                const that = this;
                if (!this.isFirst) {
                    this.isFirst = true;
                    if (that.method == 'paytm') {
                        that.$fetch({
                            method: 'GET', // 大写
                            name: 'payment.paytm.checksum',
                            data: {
                                orderId: that.order.id
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            this.$event.emit('cutDetail');
                            const order = resData;
                            const user = that.$storage.getSync('user');
                            const price = that.order.total.split('.');
                            const payAmount = price[0] + price[1];
                            pay.startPayRequest(order.razorpayOrderId, that.order.title, 'Order#: ' + order.order.number, that.order.mainImage,
                                parseInt(payAmount), user.defaultAddress.phoneNumber, user.email,
                                function (param) {
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
                                        that.$notice.loading.hide();
                                        that.$router.finish();
                                        that.$event.once('paySuccess', () => {
                                            that.init()
                                        });
                                        that.$router.open({
                                            name: 'order.success',
                                            type: 'PUSH',
                                            params: {
                                                source: 'confirm'
                                            }
                                        });
                                    }, error => {
                                        that.$notice.loading.hide();
                                        that.$notice.toast({
                                            message: error
                                        });
                                    });
                                }, function (param) {
                                    if (param.code != 0) {
                                        that.$router.open({
                                            name: 'order.failure',
                                            type: 'PUSH',
                                            params: {
                                                source: 'confirm'
                                            }
                                        });
                                    } else {
                                        that.$router.open({
                                            name: 'order',
                                            type: 'PUSH',
                                            params: {
                                                tab: 1
                                            }
                                        });
                                    }
                                });
                            that.isFirst = true;
                        }, error => {
                            that.$event.emit('cutDetail');
                            that.isFirst = true;
                            that.$notice.toast({
                                message: error
                            });
                        });
                    } else if (that.order.proId == 'drop') {
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'order.cut.create.payment',
                            data: {
                                cutId: that.order.id,
                                zero: 1
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            this.$event.emit('cutDetail');
                            googleAnalytics.recordEvent('PayStart', 'Pay Now', resData.id, 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', that.order.productId, '', 'Rs', that.order.currentPrice);
                            const order = resData;
                            const user = that.$storage.getSync('user');
                            const price = that.order.total.split('.');
                            const payAmount = price[0] + price[1];
                            if (payAmount <= 0) {
                                that.$router.finish();
                                that.$event.once('paySuccess', () => {
                                    that.init()
                                });
                                that.$router.open({
                                    name: 'order.success',
                                    type: 'PUSH',
                                    params: {
                                        source: 'confirm'
                                    }
                                });
                                return;
                            }
                            pay.startPayRequest(order.razorpayOrderId, that.order.title, 'Order#: ' + order.order.number, that.order.mainImage,
                                parseInt(payAmount), user.defaultAddress.phoneNumber, user.email,
                                function (param) {
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
                                        that.$notice.loading.hide();
                                        that.$router.finish();
                                        that.$event.once('paySuccess', () => {
                                            that.init()
                                        });
                                        that.$router.open({
                                            name: 'order.success',
                                            type: 'PUSH',
                                            params: {
                                                source: 'confirm'
                                            }
                                        });
                                    }, error => {
                                        that.$notice.toast({
                                            message: error
                                        });
                                    })
                                }, function (param) {
                                    if (param.code != 0) {
                                        that.$router.open({
                                            name: 'order.failure',
                                            type: 'PUSH',
                                            params: {
                                                source: 'confirm'
                                            }
                                        });
                                    } else {
                                        that.$router.open({
                                            name: 'order',
                                            type: 'PUSH',
                                            params: {
                                                tab: 1
                                            }
                                        });
                                    }
                                });
                            that.isFirst = true;
                        }, error => {
                            this.$event.emit('cutDetail');
                            that.isFirst = true;
                            that.$notice.toast({
                                message: error
                            });
                        });
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
