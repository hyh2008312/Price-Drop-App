<template>
    <div>
        <div class="wrapper" v-if="order.orderStatus == 'Unpaid'">
            <text class="od-text">Total:  </text>
            <text class="od-text-1">Rs.{{order.paymentAmount}}</text>
            <text class="od-button" @click="confirm">Pay Now</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Canceled'">
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button" @click="jumpHome">Buy Again</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Packing'">
            <text class="od-button-1" @click="cancel">Cancel Order</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Audit canceled'">
            <text class="od-text-2">Order cancellation pending </text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Shipped'">
            <text class="od-button" @click="tracking">Track Package</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Completed'">
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button-1" @click="jumpHome">Buy Again</text>
            <text class="od-button" @click="tracking">Track Package</text>
        </div>
    </div>
</template>
<script>
    const pay = weex.requireModule('PayModule');
    import { baseUrl } from '../../../config/apis';

    export default {
        props: ['order'],
        data () {
            return {
                isPaid: false
            }
        },
        methods: {
            confirm () {
                const that = this;
                const user = that.$storage.getSync('user');
                const price = that.order.paymentAmount.split('.');
                const payAmount = price[0] + price[1];
                if (!this.isPaid) {
                    this.isPaid = true;
                    pay.startPayRequest(this.order.lines[0].title, '', this.order.lines[0].mainImage,
                        parseInt(payAmount), user.defaultAddress.phoneNumber, user.email,
                        function (param) {
                            that.$notice.loading.show();
                            that.$fetch({
                                method: 'POST', // 大写
                                url: `${baseUrl}/payment/razorpay/${that.order.id}/`,
                                data: {
                                    paymentId: param.paymentId,
                                    paymentAmount: that.order.paymentAmount
                                },
                                header: {
                                    needAuth: true
                                }
                            }).then(resData => {
                                that.$notice.loading.hide();
                                that.isPaid = false;
                                that.$event.once('paysuccess', () => {
                                    that.init();
                                });
                                that.$router.open({
                                    name: 'order.success',
                                    type: 'PUSH'
                                })
                            }, error => {
                                that.$notice.loading.hide();
                                that.$notice.toast({
                                    message: error
                                });
                            })
                        }, function (param) {
                            that.isPaid = false;
                            if (param.code != 0) {
                                that.$router.open({
                                    name: 'order.failure',
                                    type: 'PUSH'
                                });
                            }
                        });
                }
            },
            jumpHome () {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: this.order.lines[0].productId
                    }
                })
            },
            cancel () {
                this.$emit('cancel', {
                    status: 'cancel'
                })
            },
            tracking () {
                this.$router.open({
                    name: 'order.address.tracking',
                    type: 'PUSH',
                    data: {
                        id: this.order.id
                    }
                })
            },
            deleteOrder () {
                this.$emit('deleteOrder', {
                    status: 'deleteOrder'
                })
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
        margin-right: 16px;
        font-size: 28px;
        line-height: 80px;
        text-align: center;
        padding: 0 24px;
        background-color: #EF8A31;
        border-radius: 4px;
        color: #fff;
    }

    .od-button-1{
        margin-right: 16px;
        font-size: 28px;
        line-height: 76px;
        text-align: center;
        padding: 0 24px;
        background-color: #fff;
        border-radius: 4px;
        color: #EF8A31;
        border-color: #EF8A31;
        border-style: solid;
        border-width: 2px;
    }

    .od-text{
        font-size: 24px;
        line-height: 28px;
    }

    .od-text-1{
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        margin-right: 24px;
    }

    .od-text-2{
        font-size: 28px;
        line-height: 34px;
        color: rgba(0,0,0,0.54);
        margin-right: 32px;
    }

</style>
