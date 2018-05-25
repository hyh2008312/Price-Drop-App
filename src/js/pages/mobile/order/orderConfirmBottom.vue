<template>
    <div class="wrapper">
        <text class="od-text">Total:  </text>
        <text class="od-text-1">Rs.1000.00</text>
        <text class="od-button" @click="confirm">Pay Now</text>
    </div>
</template>
<script>
    export default {
        props: ['order'],
        methods: {
            confirm () {
                this.$router.open({
                    name: 'order.detail',
                    type: 'PUSH'
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
