<template>
    <div class="wrapper">
        <text class="od-text">Total:  </text>
        <text class="od-text-1">₹{{getOrderTotal(order, card)}}</text>
        <text class="od-button" @click="confirm">Place Order</text>
    </div>
</template>
<script>
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');
    export default {
        props: ['order', 'address', 'card'],
        data: {
            isFirst: false
        },
        methods: {
            getOrderTotal (order, card) {
                if (card) {
                    return parseInt((order.total * 100 - card.share * 100) / 100).toFixed(2);
                } else {
                    return parseInt(order.total).toFixed(2);
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
                    if (that.order.proId == 'direct') {
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

                            googleAnalytics.recordEvent('Payment', 'Add to Cart', 'normal', 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', that.order.productId, '', 'Rs', that.order.currentPrice);

                            const order = resData;
                            // that.$notice.alert({
                            //     message: order
                            // });
                            // return
                            // that.$router.finish();
                            that.$router.open({
                                name: 'order.payment',
                                type: 'PUSH',
                                params: {
                                    source: 'confirm',
                                    data: order
                                },
                                backCallback: () => {
                                    that.$router.finish();
                                    that.$router.open({
                                        name: 'order',
                                        type: 'PUSH'
                                    });
                                    that.$event.emit('closePayment');
                                }
                            });
                            that.isFirst = false;
                        }, error => {
                            that.isFirst = false;
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            that.$notice.toast({
                                message: error.errorMsg
                            })
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
                            googleAnalytics.recordEvent('Payment', 'Add to Cart', 'drop', 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_add_to_cart', that.order.productId, '', 'Rs', that.order.currentPrice);
                            const order = resData;
                            // that.$router.finish();
                            that.$router.open({
                                name: 'order.payment',
                                type: 'PUSH',
                                params: {
                                    source: 'confirm',
                                    data: order
                                },
                                backCallback: () => {
                                    that.$router.finish();
                                    that.$router.open({
                                        name: 'order',
                                        type: 'PUSH'
                                    });
                                    that.$event.emit('closePayment');
                                }
                            });
                            that.isFirst = false;
                        }, error => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            that.isFirst = false;
                            that.$notice.toast({
                                message: error.errorMsg
                            })
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
                            googleAnalytics.recordEvent('Payment', 'Add to Cart', 'flash sale', 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_add_to_cart', that.order.productId, 'flash sale', 'Rs', that.order.currentPrice);
                            const order = resData;
                            // that.$router.finish();
                            that.$router.open({
                                name: 'order.payment',
                                type: 'PUSH',
                                params: {
                                    source: 'confirm',
                                    data: order
                                },
                                backCallback: () => {
                                    that.$router.finish();
                                    that.$router.open({
                                        name: 'order',
                                        type: 'PUSH'
                                    });
                                    that.$event.emit('closePayment');
                                }
                            });
                            that.isFirst = false;
                        }, error => {
                            that.$notice.loading.hide();
                            that.$event.emit('cutDetail');
                            that.isFirst = false;
                            that.$notice.toast({
                                message: error.errorMsg
                            })
                        });
                    } else if (that.order.proId == 'lottery') {
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'order.lottery.pure',
                            data: {
                                drawId: that.order.drawId,
                                vid: that.order.id,
                                ownerId: that.order.ownerId
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            that.$notice.loading.hide();

                            googleAnalytics.recordEvent('Payment', 'Add to Cart', 'lottery', 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_initiated_checkout', that.order.productId, 'lucky draw', 'Rs', that.order.currentPrice);

                            if (resData.orderId != null) {
                                that.$router.finish();
                                this.$router.open({
                                    name: 'order.detail',
                                    params: {
                                        id: resData.orderId
                                    }
                                });
                                return;
                            }
                            that.$router.finish();
                            that.$router.open({
                                name: 'order',
                                type: 'PUSH'
                            });
                            that.isFirst = false;
                        }, error => {
                            that.$notice.loading.hide();
                            that.isFirst = false;
                            that.$notice.toast({
                                message: error.errorMsg
                            })
                        });
                    } else if (that.order.proId == 'dropGoods') {
                        const voucherId = that.card ? that.card.id : null;
                        that.$fetch({
                            method: 'POST', // 大写
                            name: 'order.drop.create.pure',
                            data: {
                                dropId: that.order.dropId,
                                vid: that.order.id,
                                voucherId
                            },
                            header: {
                                needAuth: true
                            }
                        }).then(resData => {
                            that.$notice.loading.hide();
                            that.$event.emit('placeOrder');

                            googleAnalytics.recordEvent('Payment', 'Add to Cart', 'drop', 0);
                            googleAnalytics.facebookRecordEvent('fb_mobile_add_to_cart', that.order.productId, '', 'Rs', that.order.currentPrice);

                            const order = resData;
                            // that.$router.finish();
                            that.$router.open({
                                name: 'order.payment',
                                type: 'PUSH',
                                params: {
                                    source: 'confirm',
                                    data: order
                                },
                                backCallback: () => {
                                    that.$router.finish();
                                    that.$router.open({
                                        name: 'order',
                                        type: 'PUSH'
                                    });
                                    that.$event.emit('closePayment');
                                }
                            });
                            that.isFirst = false;

                        }, error => {
                            that.$notice.loading.hide();
                            that.isFirst = false;
                            that.$notice.toast({
                                message: error.errorMsg
                            })
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
        border-radius: 50%;
        text-align: center;
        padding: 0 24px;
        background-color: #EF8A31;
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
