<template>
    <div class="wrapper">
        <top-header :title="title"></top-header>
        <div class="status-bar"></div>
        <list class="container" :style="height">
            <cell class="input-bg input-mt">
                <div>
                    <text class="input-title" :class="[inputIndex==0?'input-title-active':'']">Name</text>
                    <input type="text" placeholder="" class="input-1" :autofocus=true value=""
                           @change="onchange" @input="oninput" @focus="changeColor(0)"/>
                </div>
                <div>
                    <text class="input-title" :class="[inputIndex==1?'input-title-active':'']">Phone</text>
                    <div class="input-bg-1">
                        <text>+91</text>
                        <input type="text" placeholder="" class="input-1" value=""
                               @change="onchange" @input="oninput" @focus="changeColor(1)"/>
                    </div>
                </div>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==2?'input-title-active':'']">Pincode</text>
                <input type="text" placeholder="" class="input" value=""
                       @change="onchange" @input="oninput" @focus="changeColor(2)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==3?'input-title-active':'']">House No./Floor/Buildng</text>
                <input type="text" placeholder="" class="input" value=""
                       @change="onchange" @input="oninput" @focus="changeColor(3)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==4?'input-title-active':'']">Street/Locality</text>
                <input type="text" placeholder="" class="input" value=""
                       @change="onchange" @input="oninput" @focus="changeColor(4)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==5?'input-title-active':'']">Landmark: Near/Behind(Optional)</text>
                <input type="text" placeholder="" class="input" value=""
                       @change="onchange" @input="oninput" @focus="changeColor(5)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title" :class="[inputIndex==6?'input-title-active':'']">City</text>
                <input type="text" placeholder="" class="input" value=""
                       @change="onchange" @input="oninput" @focus="changeColor(6)"/>
            </cell>
            <cell class="input-bg">
                <text class="input-title">State</text>
                <div>
                    <text>Choose</text>
                    <text class="iconfont">&#xe626;</text>
                </div>
            </cell>
        </list>
        <address-bottom></address-bottom>
    </div>
</template>
<script>
import header from './header';
import addressItem from './addressItem';
import orderDetailItem from './orderDetailItem';
import addressBottom from './addressBottom';
import { Utils } from 'weex-ui';

export default {
    components: {
        'top-header': header,
        'address-item': addressItem,
        'order-detail-item': orderDetailItem,
        'address-bottom': addressBottom
    },
    created () {
        const pageHeight = Utils.env.getScreenHeight()
        this.height = { height: (pageHeight - 112 - 112 - 44 - 2) + 'px' }
    },
    data () {
        return {
            title: 'Edit Address',
            inputIndex: 0
        }
    },
    methods: {
        back () {
            this.$router.finish()
        },
        changeColor (i) {
            this.inputIndex = i
        }
    }
}

</script>
<style scoped>

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

</style>
