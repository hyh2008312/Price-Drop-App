<template>
    <div class="wrapper">
        <div class="container">
            <text class="py-tlt">Payment Method</text>
            <div class="py-bg">
                <div class="item" @click="checked(item, index)" v-for="(item, index) in list">
                    <text class="iconfont item-checked" v-if="item.checked">&#xe6fb;</text>
                    <text class="item-no-checked" v-if="!item.checked"></text>
                    <image class="item-image" :src="item.src || src"></image>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        props: ['list'],
        data () {
            return {
                src: 'bmlocal://assets/occupy.png'
            }
        },
        methods: {
            checked (pay, index) {
                this.resetCheckedList(index)
                pay.checked = !pay.checked
                this.$emit('radioChecked', {
                    status: 'radioChecked',
                    data: {
                        pay: pay
                    }
                })
            },
            resetCheckedList (index) {
                this.list.forEach((item, i) => {
                    if (i != index) {
                        item.checked = false
                    }
                })
            }
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        background-color: #fff;
        width: 750px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }

    .container{
        padding: 32px 32px 48px;
    }

    .item{
        width: 343px;
        flex-direction: row;
        align-items: center;
    }

    .item-image{
        margin-left: 20px;
        width: 120px;
        height: 40px;
    }

    .item-checked{
        font-size: 32px;
        line-height: 32px;
        color: #4AB406;
    }

    .item-no-checked{
        width: 32px;
        height: 32px;
        border-style: solid;
        border-width: 2px;
        border-color: rgba(0,0,0,0.54);
        border-radius: 16px;
    }

    .py-tlt{
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
    }

    .py-bg{
        margin-top: 40px;
        flex-direction: row;
        align-items: center;
    }

</style>
