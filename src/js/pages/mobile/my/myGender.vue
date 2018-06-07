<template>
    <div class="wrapper">
        <topic-header title="Gender" leftBtn="icon"  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div class="mid-cell">
            <wxc-radio :list="list" @wxcRadioListChecked="wxcRadioListChecked"></wxc-radio>
            <!--<text class="radio-text">checkedItem: {{checkedInfo}}</text>-->
            <category title="Radio disabled"></category>
            <wxc-radio :list="list2"></wxc-radio>
        </div>
    </div>
</template>

<script>
    import header from './header';
    import { WxcRadio } from 'weex-ui'

    export default {
        components: {
            WxcRadio,
            'topic-header': header
        },
        data () {
            return {
                list: [
                    { title: 'Male', value: 'M'  },
                    { title: 'Female', value: 'F'  }
                ],
                checkedInfo: { title: 'option2', value: 2 }
            }
        },
        name: 'myDetail',
        methods: {
            openNew (head) {
            },
            wxcRadioListChecked (e) {
                this.$router.back()
                this.$router.setBackParams({
                    id: e
                })
                this.checkedInfo = e;
            }
        }
    }
</script>

<style>
    .iconfont{
        font-family: iconfont;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .box-tlt {
        height: 96px;
        border-bottom-width: 3px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08);
        flex-direction: row;
        justify-content: space-between;
    }

    .box-txt-icon {
        font-family: iconfont;
        font-size: 40px;
        padding-top: 34px;
        padding-left: 32px;
        color: #ef8a31;
    }
    .box-txt-left{
        font-family: ProximaNova-Regular;
        padding-top: 30px;
        opacity: 0.54;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .box-txt {
        font-family: iconfont;
        font-size: 30px;
        padding-top: 34px;
        padding-left: 32px;
        color: #333;
    }

    .i-box {
        font-size: 20px;
        padding-top: 34px;
        padding-right: 32px;
        color: rgba(0,0,0,0.87);
    }
    .mid-cell{
        margin-top: 160px;
        border-radius: 4px;
        background-color: #fff;
    }
    .box-left{
        flex-direction: row;
        justify-content: space-between;
    }
    .box-right{
        flex-direction: row;
        justify-content: space-between;
    }
</style>
