<template>
    <div>
        <div class="wrapper" v-if="order.orderStatus == 'Unpaid'">
            <text class="od-text">Total:  </text>
            <text class="od-text-1">₹{{order.paymentAmount}}</text>
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button" @click="confirm">Pay Now</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Canceled'">
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button" @click="jumpHome">Buy Again</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Paid'">
            <text class="od-button-1" @click="cancel">Cancel Order</text>
            <text class="od-button" @click="editAddress" v-if="order.paymentMode!='cod'">Change Address</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Expired'">
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button" @click="jumpHome">Buy Again</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Packing'">
            <text class="od-button-1" v-if="order.sourcingStatus == 'Not Started'"
                  @click="cancel">Cancel Order</text>
            <text class="od-button" @click="tracking">Track Package</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Audit canceled'">
            <text class="od-text-2">Order cancellation pending</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Shipped'">
            <text class="od-button-1" @click="tracking">Track Package</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Completed'">
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button-1 gt-mr" v-if="order.orderComment==''" @click="writeReviews(1)">Write Reviews</text>
            <text class="od-button-1 gt-mr" v-if="order.orderComment" @click="writeReviews(2)">See Reviews</text>
            <text class="od-button" @click="tracking">Track Package</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Partially Refunded'">
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button" @click="jumpHome">Buy Again</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Fully Refunded'">
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button" @click="jumpHome">Buy Again</text>
        </div>
        <div class="wrapper" v-if="order.orderStatus == 'Undelivered'">
            <text class="od-button-1" @click="deleteOrder">Delete</text>
            <text class="od-button" @click="tracking">Track Package</text>
        </div>
    </div>
</template>
<script>
    const pay = weex.requireModule('PayModule');

    export default {
        props: ['order'],
        data () {
            return {
                isPaid: false
            }
        },
        methods: {
            confirm () {
                this.$router.open({
                    name: 'order.payment',
                    type: 'PUSH',
                    params: {
                        source: 'order',
                        data: this.order
                    }
                });
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
            writeReviews (type) {
                this.$router.open({
                    name: 'write.reviews',
                    type: 'PUSH',
                    params: {
                        order: this.order,
                        update: type
                    }
                });
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
                    params: {
                        id: this.order.id,
                        status: this.order.orderStatus,
                        carrierCode: this.order.carrierCode
                    }
                })
            },
            deleteOrder () {
                this.$emit('deleteOrder', {
                    status: 'deleteOrder'
                })
            },
            receipt () {
                this.$emit('receiptOrder', {
                    status: 'receiptOrder'
                })
            },
            editAddress () {
                this.$router.open({
                    name: 'order.edit.address',
                    params: {
                        id: this.order.id
                    }
                });
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
        border-radius: 40px;
        color: #fff;
    }

    .od-button-1{
        margin-right: 16px;
        font-size: 28px;
        line-height: 76px;
        text-align: center;
        padding: 0 24px;
        background-color: #fff;
        border-radius: 40px;
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
