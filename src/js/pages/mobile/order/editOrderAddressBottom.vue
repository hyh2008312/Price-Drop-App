<template>
    <div class="wrapper">
        <div v-if="!isForbidden" class="od-button" @click="addAddress">
            <text class="od-text">Save Changes</text>
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
                    return;
                }
                if (this.address.phoneNumber.length < 10) {
                    this.$notice.toast({
                        message: 'The phone number is in wrong format.'
                    });
                    return;
                }
                if (this.address.postcode.length < 6) {
                    this.$notice.toast({
                        message: 'Your Pincode is in a wrong format.'
                    });
                    return;
                }

                if (!this.id && this.address.phoneNumber != this.address.phoneNumberConfirm) {
                    this.$notice.toast({
                        message: 'There is something wrong with your phone number!'
                    });
                    this.changeInput(1);
                    return;
                }
                this.$fetch({
                    method: 'POST', // 大写
                    url: `${baseUrl}/order/customer/update/address/${this.id}/`,
                    data: this.address,
                    header: {
                        needAuth: true
                    }
                }).then(resData => {
                    // 成功回调
                    this.$router.finish();
                    this.$event.emit('changeAddress');
                }, error => {
                    // 错误回调
                    this.$notice.toast({
                        message: error
                    });
                });
            },
            isChecked () {
                for (const k in this.address) {
                    if (k != 'line3' && k != 'phoneNumberConfirm') {
                        if (this.address[k] == '' || this.address[k] == null) {
                            this.changeInput(k);
                            this.$notice.toast({
                                message: this.address[k]
                            });
                            return true
                        }
                    }
                }
                return false;
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
        width : 686px;
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
