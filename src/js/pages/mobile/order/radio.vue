<template>
    <div class="wrapper">
        <div class="item" @click="checked(item, index)" v-for="(item, index) in list">
            <image class="item-image" :src="item.src || src"></image>
            <text class="iconfont item-checked" v-if="item.checked">&#xe6fb;</text>
            <text class="item-no-checked" v-if="!item.checked"></text>
        </div>
    </div>
</template>
<script>
    export default {
        props: ['list'],
        data () {
            return {
                src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
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
        width: 686px;
        height: 220px;
        padding: 32px 0;
    }

    .item{
        padding: 16px 0;
        width: 686px;
        height: 78px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .item-image{
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
</style>
