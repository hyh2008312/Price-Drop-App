<template>
    <div class="wrapper">
        <div v-if="!isForbidden" class="od-button" @click="addAddress">
            <text class="od-text">Save</text>
        </div>
        <div v-if="isForbidden" class="od-button-1">
            <text class="od-text">Change Your Address to Continue</text>
        </div>
    </div>
</template>
<script>
    import { baseUrl } from '../../../config/apis';

    export default {
        props: ['address', 'id', 'isForbidden'],
        methods: {
            addAddress () {
                if (this.isChecked()) {
                    this.$notice.toast({
                        message: 'Please fill in all the required fields.'
                    });
                    return
                }
                if (!this.id) {
                    this.$fetch({
                        method: 'POST', // 大写
                        name: 'address.shipping.list',
                        data: this.address,
                        header: {
                            needAuth: true
                        }
                    }).then(resData => {
                        // 成功回调
                        this.$router.finish()
                    }, error => {
                        // 错误回调
                        this.$notice.toast({
                            message: error
                        })
                    })
                } else {
                    this.$fetch({
                        method: 'PUT', // 大写
                        url: `${baseUrl}/address/shipping/detail/${this.id}/`,
                        data: this.address,
                        header: {
                            needAuth: true
                        }
                    }).then(resData => {
                        // 成功回调
                        this.$router.finish()
                    }, error => {
                        // 错误回调
                        this.$notice.toast({
                            message: error
                        })
                    })
                }
            },
            isChecked () {
                for (const k in this.address) {
                    if (k != 'line3') {
                        if (this.address[k] == '' || this.address[k] == null) {
                            this.changeInput(k);
                            this.$notice.toast({
                                message: this.address[k]
                            });
                            return true
                        }
                    }
                }
                return false
            },
            changeInput (k) {
                this.$emit('changeInput', {
                    status: 'changeInput',
                    data: {
                        key: k
                    }
                });
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

    .od-button-1{
        margin: 16px 32px;
        background-color: rgba(0,0,0,0.38);
        border-radius: 4px;
        height: 80px;
        width: 686px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .od-text{
        font-size: 28px;
        line-height: 34px;
        color: #fff;
    }

</style>
