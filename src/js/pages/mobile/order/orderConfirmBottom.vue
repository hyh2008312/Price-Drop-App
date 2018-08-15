<template>
    <div class="wrapper">
        <text class="od-text">Total:  </text>
        <text class="od-text-1">Rs.{{getOrderTotal(order, card)}}</text>
        <text class="od-button" @click="confirm">Place Order</text>
    </div>
</template>
<script>
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    const pay = weex.requireModule('PayModule');
    export default {
        props: ['order', 'address', 'card'],
        data: {
            isFirst: false
        },
        methods: {
            getOrderTotal (order, card) {
                if (card) {
                    return ((order.total * 100 - card.share * 100) / 100).toFixed(2);
                } else {
                    return order.total;
                }
            },
            confirm () {
                const that = this;
                if (!that.address.id) {
                    that.$notice.toast('Please add address first!');
                    return
                }
                if (!this.isFirst) {
                    this.isFirst = true;
                    that.$notice.loading.show();
                    if (that.order.proId == 'product') {
                        const voucherId = that.card ? that.card.id : null;
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'order.create.pure',
                            data: {
                                vid: that.order.id,
                                quantity: that.order.quantity,
                                voucherId
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            googleAnalytics.recordEvent('PayStart', 'Pay Now', resData.id, 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', that.order.productId, '', 'Rs', that.order.currentPrice);
                            const order = resData;
                            that.$router.open({
                                name: 'order.payment',
                                type: 'PUSH',
                                params: {
                                    source: 'confirm',
                                    data: order
                                },
                                backCallback: () => {
                                    that.$router.open({
                                        name: 'order',
                                        type: 'PUSH'
                                    });
                                    that.$router.finish();
                                    that.$event.emit('closePayment');
                                }
                            });
                            that.isFirst = true;
                        }, error => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            that.isFirst = true;
                            that.$notice.toast({
                                message: error
                            });
                        });
                    } else if (that.order.proId == 'drop') {
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'order.cut.create.pure',
                            data: {
                                cutId: that.order.id
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            googleAnalytics.recordEvent('PayStart', 'Pay Now', resData.id, 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', that.order.productId, '', 'Rs', that.order.currentPrice);
                            const order = resData;
                            that.$router.open({
                                name: 'order.payment',
                                type: 'PUSH',
                                params: {
                                    source: 'confirm',
                                    data: order
                                },
                                backCallback: () => {
                                    that.$router.open({
                                        name: 'order',
                                        type: 'PUSH'
                                    });
                                    that.$router.finish();
                                    that.$event.emit('closePayment');
                                }
                            });
                            that.isFirst = true;
                        }, error => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            that.isFirst = true;
                            that.$notice.toast({
                                message: error
                            });
                        });
                    } else if (that.order.proId == 'flash') {
                        const voucherId = that.card ? that.card.id : null;
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'order.flash.pure',
                            data: {
                                flashPromotionId: that.order.flashSale.promotionId,
                                vid: that.order.id,
                                quantity: that.order.quantity,
                                voucherId
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            googleAnalytics.recordEvent('PayStart', 'Pay Now', resData.id, 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', that.order.productId, 'flash sale', 'Rs', that.order.currentPrice);
                            const order = resData;
                            that.$router.open({
                                name: 'order.payment',
                                type: 'PUSH',
                                params: {
                                    source: 'confirm',
                                    data: order
                                },
                                backCallback: () => {
                                    that.$router.open({
                                        name: 'order',
                                        type: 'PUSH'
                                    });
                                    that.$router.finish();
                                    that.$event.emit('closePayment');
                                }
                            });
                            that.isFirst = true;
                        }, error => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
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
