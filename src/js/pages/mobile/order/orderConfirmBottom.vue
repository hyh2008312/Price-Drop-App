<template>
    <div class="wrapper">
        <text class="od-text">Total:  </text>
        <text class="od-text-1">Rs.{{order.currentPrice}}</text>
        <text class="od-button" @click="confirm">Pay Now</text>
    </div>
</template>
<script>
    const pay = weex.requireModule('PayModule');
    import { baseUrl } from '../../../config/apis';
    export default {
        props: ['order', 'address'],
        methods: {
            confirm () {
                const that = this
                if (!that.address.id) {
                    that.$notice.toast('Please add address first!')
                    return
                }
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
                    const order = resData
                    pay.startPayRequest(that.order.title, '', that.order.mainImage,
                        that.order.currentPrice * 100, '', '', function (param) {
                            that.$fetch({
                                method: 'PUT', // 大写
                                url: `${baseUrl}/payment/razorpay/${order.orderId}/`,
                                data: {
                                    paymentId: param.paymentId,
                                    paymentAmount: that.order.currentPrice
                                },
                                header: {
                                    needAuth: true
                                }
                            }).then(resData => {
                                that.$router.finish()
                                that.$router.open({
                                    name: 'order.success',
                                    type: 'PUSH'
                                })
                            }, error => {
                                that.$notice.toast({
                                    message: error
                                })
                            })
                        }, function (param) {});
                }, error => {
                    this.$notice.toast({
                        message: error
                    })
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
