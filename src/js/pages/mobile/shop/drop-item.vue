<template>
    <div>
        <div class="overflow">
            <div class="purple">
                <text class="p-t1">₹{{parseInt(lowestPrice)}}</text>
                <text class="p-t2">by inviting NEW friends</text>
            </div>
            <div class="pink">
                <text class="p-t3">Original Price</text>
                <text class="p-t4">₹{{parseInt(saleUnitPrice)}}</text>
                <!--<text class="p-t4">₹{{'11111'}}</text>-->
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        props: ['saleUnitPrice', 'lowestPrice'],
        name: "drop-item"
    }
</script>

<style scoped>
    .overflow{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        /*height: 96px;*/
    }
    .purple{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #492799;
        width: 530px;
    }
    .p-t1{
        color: white;
        font-size: 40px;
        font-weight: 700;
        padding-left: 32px;
        padding-top: 22px;
        padding-bottom: 30px;
        padding-right: 10px;
    }
    .p-t2{
        color: white;
        font-size: 24px;
        padding-bottom: 7px;
    }
    .pink{
        background-color: #E78585 ;
        width: 220px;
    }
    .p-t3{
        color: white;
        font-size: 24px;
        padding-top: 16px;
        padding-left: 36px;
        padding-bottom: 4px;
        margin-right: 32px;
    }
    .p-t4{
        color: white;
        font-size: 24px;
        padding-bottom: 22px;
        padding-left: 88px;
        text-decoration: line-through;
    }

</style>
