<template>
    <div class="wrapper">
        <div class="od-button" @click="addAddress">
            <text class="od-icon iconfont">&#xe630;</text>
            <text class="od-text">Add New Address</text>
        </div>
    </div>
</template>
<script>
    export default {
        methods: {
            confirm () {
                this.$router.open({
                    name: 'order.confirm',
                    type: 'PUSH'
                })
            },
            addAddress () {
                this.$router.open({
                    name: 'order.address.edit',
                    type: 'PUSH'
                })
            }
        }
    }
</script>
<style scoped>
    .iconfont{
        font-family: iconfont;
    }

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
    }

    .od-button{
        margin: 16px 32px;
        background-color: #EF8A31;
        border-radius: 4px;
        height: 80px;
        width: 686px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .od-icon{
        font-size: 32px;
        line-height: 32px;
        margin-right: 16px;
        color: #fff;
    }

    .od-text{
        font-size: 28px;
        line-height: 34px;
        color: #fff;
    }

</style>
