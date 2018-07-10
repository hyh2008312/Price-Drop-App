<template>
    <div class="wrapper">
        <text class="od-text">Total:  </text>
        <text class="od-text-1">Rs.{{order.total}}</text>
        <text class="od-button" @click="confirm">Pay Now</text>
    </div>
</template>
<script>
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    const pay = weex.requireModule('PayModule');
    import { baseUrl } from '../../../config/apis';
    export default {
        props: ['order', 'address'],
        data: {
            isFirst: false
        },
        methods: {
            confirm () {
                const that = this;
                if (!that.address.id) {
                    that.$notice.toast('Please add address first!')
                    return
                }
                if (!this.isFirst) {
                    this.isFirst = true;
                    if (that.order.proId == 'product') {
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'order.create',
                            data: {
                                vid: that.order.id,
                                quantity: that.order.quantity
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
                            pay.startPayRequest(that.order.title, '', that.order.mainImage,
                                parseInt(payAmount), user.defaultAddress.phoneNumber, user.email,
                                function (param) {
                                    that.$notice.loading.show();
                                    that.$fetch({
                                        method: 'POST', // 大写
                                        url: `${baseUrl}/payment/razorpay/${order.id}/`,
                                        data: {
                                            paymentId: param.paymentId,
                                            paymentAmount: that.order.currentPrice
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
                    } else {
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'order.cut.create',
                            data: {
                                cutId: that.order.id
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
                            pay.startPayRequest(that.order.title, '', that.order.mainImage,
                                parseInt(payAmount), user.defaultAddress.phoneNumber, user.email,
                                function (param) {
                                    that.$fetch({
                                        method: 'POST', // 大写
                                        url: `${baseUrl}/payment/razorpay/${order.id}/`,
                                        data: {
                                            paymentId: param.paymentId,
                                            paymentAmount: that.order.currentPrice
                                        },
                                        header: {
                                            needAuth: true
                                        }
                                    }).then(resData => {
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
