<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="input-bg input-mt">
                <div>
                    <text class="input-title" :class="[inputIndex==0?'input-title-active':'']">Name</text>
                    <input type="text" placeholder="" class="input-1" :value="address.firstName"
                           @input="onInputFirstName" @return="onInputFirstName"
                           @focus="changeColor(0)" @blur="changeColor(-1)"/>
                </div>
                <div>
                    <text class="input-title" :class="[inputIndex==1?'input-title-active':'']">Phone</text>
                    <div class="input-bg-1">
                        <text class="input-title-1">+91</text>
                        <input type="number" placeholder="" class="input-1" :value="address.phoneNumber"
                               @input="onInputPhone" @return="onInputPhone"
                               @focus="changeColor(1)" @blur="changeColor(-1)"/>
                    </div>
                </div>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==2?'input-title-active':'']">Pincode</text>
                <input type="number" placeholder="" class="input" :value="address.postcode"
                       @input="onInputPostcode" @return="onInputPostcode"
                       @focus="changeColor(2)" @blur="changeColor(-1)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==3?'input-title-active':'']">House No./Floor/Buildng</text>
                <input type="text" placeholder="" class="input" :value="address.line1"
                       @input="onInputLine1" @return="onInputLine1"
                       @focus="changeColor(3)" @blur="changeColor(-1)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==4?'input-title-active':'']">Street/Locality</text>
                <input type="text" placeholder="" class="input" :value="address.line2"
                       @input="onInputLine2" @return="onInputLine2"
                       @focus="changeColor(4)" @blur="changeColor(-1)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==5?'input-title-active':'']">Landmark: Near/Behind(Optional)</text>
                <input type="text" placeholder="" class="input" :value="address.line3"
                       @input="onInputLine3" @return="onInputLine3"
                       @focus="changeColor(5)" @blur="changeColor(-1)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==6?'input-title-active':'']">City</text>
                <input type="text" placeholder="" class="input" :value="address.city"
                       @input="onInputCity" @return="onInputCity"
                       @focus="changeColor(6)" @blur="changeColor(-1)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title">State</text>
                <div class="choose-bg" @click="jumpState">
                    <text class="choose-txt">{{chooseState}}</text>
                    <text class="iconfont choose-arr">&#xe626;</text>
                </div>
            </cell>
        </list>
        <edit-address-bottom :address="address" :id="id"></edit-address-bottom>
    </div>
</template>
<script>
import header from './header';
import addressItem from './addressItem';
import orderDetailItem from './orderDetailItem';
import editAddressBottom from './editAddressBottom';
import { Utils } from 'weex-ui';
import { TOKEN } from './config';

export default {
    components: {
        'top-header': header,
        'address-item': addressItem,
        'order-detail-item': orderDetailItem,
        'edit-address-bottom': editAddressBottom
    },
    eros: {
        appeared (params, options) {
            if (params.id) {
                this.id = params.id
                this.getAddress(params.id)
            }
        }
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 112 - 44 - 2) + 'px' }
    },
    data () {
        return {
            title: 'Edit Address',
            inputIndex: -1,
            id: -1,
            address: {
                firstName: '',
                postcode: '',
                line1: '',
                line2: '',
                line3: '',
                city: '',
                stateId: '',
                phoneNumber: ''
            },
            chooseState: 'Choose'
        }
    },
    methods: {
        back () {
            this.$router.finish()
        },
        changeColor (i) {
            this.inputIndex = i
        },
        jumpState () {
            this.$event.off('state')
            this.$event.once('state', (params) => {
                if (params) {
                    this.address.stateId = params.id
                    this.chooseState = params.name
                }
            });
            this.$router.open({
                name: 'order.address.state',
                type: 'PUSH'
            })
        },
        onInputFirstName (event) {
            this.address.firstName = event.value;
        },
        onInputPhone (event) {
            this.address.phoneNumber = event.value;
        },
        onInputPostcode (event) {
            this.address.postcode = event.value;
        },
        onInputLine1 (event) {
            this.address.line1 = event.value;
        },
        onInputLine2 (event) {
            this.address.line2 = event.value;
        },
        onInputLine3 (event) {
            this.address.line3 = event.value;
        },
        onInputCity (event) {
            this.address.city = event.value;
        },
        getAddress (id) {
            this.$fetch({
                method: 'GET', // 大写
                url: `http://47.104.171.91/address/shipping/detail/${id}/`,
                header: {
                    Authorization: 'Bearer ' + TOKEN
                }
            }).then(resData => {
                // 成功回调
                this.$notice.toast({
                    message: resData
                })
                for (const k in this.address) {
                    this.address[k] = resData[k]
                }
                this.chooseState = resData.stateName
            }, error => {
                // 错误回调
                this.$notice.toast({
                    message: error
                })
            })
        }
    }
}

</script>
<style scoped>

    .iconfont{
        font-family: iconfont;
    }

    .wrapper{
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        background-color: #f1f1f1;
    }

    .status-bar{
        position: fixed;
        top: 0;
        height: 44px;
        background-color: #000;
        width: 750px;
    }

    .container{
        margin-top: 2px;
        width: 750px;
        background-color: #f1f1f1;
    }

    .input-bg{
        padding: 0 32px 32px;
        background-color: #fff;
    }

    .input-bg-1{
        flex-direction: row;
        width: 333px;
        justify-content: space-between;
        align-items: center;
    }

    .input-mt{
        padding: 32px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .input-title{
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        color: rgba(0,0,0,0.38);
    }

    .input-title-active{
        color: #F09F58;
    }

    .input-title-1{
        margin-top: 16px;
        font-size: 28px;
        line-height: 34px;
        color: #262626;
        margin-right: 16px;
    }

    .input{
        margin-top: 16px;
        width: 686px;
        font-size: 28px;
        line-height: 50px;
        color: rgba(0,0,0,0.87);
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.12);
        placeholder-color: rgba(0,0,0,0.54);
    }

    .input-1{
        margin-top: 16px;
        width: 333px;
        font-size: 28px;
        line-height: 50px;
        color: rgba(0,0,0,0.87);
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.12);
        placeholder-color: rgba(0,0,0,0.54);
    }

    .input-1:focus{
        line-height: 48px;
        border-bottom-width: 4px;
        border-bottom-style: solid;
        border-bottom-color: #F09F58;
    }

    .input:focus{
        line-height: 48px;
        border-bottom-width: 4px;
        border-bottom-style: solid;
        border-bottom-color: #F09F58;
    }

    .choose-bg{
        margin-top: 16px;
        height: 50px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.12);
    }

    .choose-txt{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.54);
    }

    .choose-arr{
        color: rgba(0,0,0,0.54);
        font-size: 28px;
        line-height: 28px;
    }

</style>
