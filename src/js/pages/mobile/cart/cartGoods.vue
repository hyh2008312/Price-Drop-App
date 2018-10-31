<template>
    <div class="wrapper">
        <div class="overflow-box">

            <div class="top">
                <image class="t-i" :src="item.mainImage"></image>
                <div class="t-c">
                    <!--<text class="t-t">Toyota’s Latest is an Entire Mobility Service Platform</text>-->
                    <text class="t-t">{{item.title}}</text>
                    <div class="t-w2">
                        <text class="t-w3">{{item.attributes}}</text>
                        <text class="t-w3">x {{item.quantity}}</text>
                    </div>
                </div>
            </div>

            <div class="bottom">
                <div class="b-item">
                    <text class="b-i-w1">Subtotal</text>
                    <text class="b-i-w1" v-if="item.productType=='flash'">₹{{parseInt(calc(item.unitPrice,item.flashDiscount))}}</text>
                    <text class="b-i-w1" v-if="item.productType=='direct'">₹{{parseInt(item.unitPrice)}}</text>
                </div>
                <div class="b-item">
                    <text class="b-i-w1">Shipping</text>
                    <text class="b-i-w1">₹0</text>
                </div>
                <div class="b-item">
                    <text class="b-i-w1">Tax</text>
                    <text class="b-i-w1">₹0</text>
                </div>
                <div class="b-item">
                    <text class="b-i-w1 fb">Total</text>
                    <text class="b-i-w1" v-if="item.productType=='flash'">₹{{parseInt(calcPrice(item.quantity,calc(item.unitPrice,item.flashDiscount)))}}</text>
                    <text class="b-i-w1" v-if="item.productType=='direct'">₹{{parseInt(calcPrice(item.quantity,item.unitPrice))}}</text>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'cartGoods',
        props: ['item'],
        data () {
            return {
                allPrice: '',
                tmpArr: []
            }
        },
        created () {

        },
        methods: {
            calc (a, b) {
                return ((a * b) / 100).toFixed(2)
                // return (('0.00' * 100) / 100).toFixed(2)
            },
            calcPrice (q, p) {
                return (q * (p * 100)) / 100
            },
        }
    }
</script>

<style scoped>
    .wrapper {
        background-color: #fff;
        width: 686px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }
    .overflow-box{
        padding-top:32px;
        padding-right:32px;
        padding-bottom:16px;
        padding-left:32px;
    }
    .top{
        flex-direction: row;
        justify-content: start;
        border-bottom-style:solid ;
        border-bottom-color: rgba(0,0,0,.09);
        border-bottom-width:1px;
    }
    .t-i{
        width: 160px;
        height: 160px;
        margin-bottom: 16px;
        margin-right: 24px;
    }
    .t-c{}
    .t-t{
        font-size: 28px;
        color: rgba(0,0,0,0.87);
        text-align: left;
        line-height: 48px;
        height: 136px;
        width: 348px;
        font-weight: 700;
    }
    .t-w2{
        width: 440px;
        flex-direction: row;
        justify-content: space-between;
    }
    .t-w3{
        opacity: 0.54;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .bottom{
        margin-top: 16px;
    }
    .b-item{
        flex-direction: row;
        justify-content: space-between;
    }
    .b-i-w1{
        font-size: 24px;
        color: #000000;
        text-align: left;
        line-height: 48px;
    }
    .fb{
        font-weight: 700;
    }
</style>
