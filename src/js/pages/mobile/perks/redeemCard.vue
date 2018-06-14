<template>
    <div class="wrapper">
        <topic-header title="Gift Card" leftBtn="icon"  ref="ref1" ></topic-header>
        <div class="blackheader"></div>
        <!--<div>-->
            <div class="overflow-top">
                <div class="top-card">
                    <div class="top-card-imgdiv">
                        <image class="top-card-img"  :src="card.imageDetail"></image>
                    </div>
                    <div class="top-card-txt" >
                        <text class="top-card-txt1" >{{card.name}} Gift Card</text>

                        <div class="top-card-txt2">
                            <text style="line-height: 42px;">• Points Needed:{{card.pointNumber}}</text>
                            <text >• Only vaild for order above Rs.{{card.lowestAmount}}</text>
                        </div>
                    </div>
                </div>
            </div>

        <div class="overflow-bottom">
            <div class="bottom-card">

                <text class="bottom-card-txt1">Redeem Instruction</text>
                    <text class="bottom-card-txt2">1. To redeem this gift card, please check your balance to ensure you have enough points.</text>
                    <text class="bottom-card-txt2">2. Once you confirm to redeem this gift card, you cannot be redeemed for cash ai any time.</text>
                    <text class="bottom-card-txt2">3. Once you confirm to redeem this gift card, you cannot be redeemed for cash ai any time.</text>

            </div>
        </div>
        <div class="bottom-btn" >
            <text class="button" @click="openDialog">Redeem Now</text>
        </div>


        <NewDialog class="wxdialog"
                    :content="'To redeem this gift card, you need to spend ' + card.pointNumber +' reward points'"
                    :show="show"
                    :single="false"
                    :is-checked="isChecked"
                    @wxcDialogCancelBtnClicked="wxcDialogCancelBtnClicked"
                    @wxcDialogConfirmBtnClicked="wxcDialogConfirmBtnClicked"
                    @wxcDialogNoPromptClicked="wxcDialogNoPromptClicked">
        </NewDialog>
    </div>
</template>

<script>
    import header from './header';
    import NewDialog from './newPopup';

    export default {
        components: {
            'topic-header': header,
            NewDialog
        },
        eros: {
            appeared (params, options) {
                if (params) {
                    this.card = params.card
                }
            }
        },
        data () {
            return {
                imgsrc: [
                    'bmlocal://assets/100.png',
                    'bmlocal://assets/150.png',
                    'bmlocal://assets/200.png'
                ],
                card: '',
                img: '',
                show: false,
                isChecked: false,
                points: ''
            }
        },
        methods: {

            openDialog () {
                this.show = true;
            },
            wxcDialogCancelBtnClicked () {
                // 此处必须设置，组件为无状态组件，自己管理
                this.show = false;
            },
            wxcDialogConfirmBtnClicked () {
                this.$fetch({
                    method: 'POST',
                    name: 'point.exchange',
                    header: {
                        needAuth: true
                    },
                    data: {
                        vid: this.card.id
                    }
                }).then((res) => {
                    this.$notice.toast({
                        message: 'redeem success'
                    })
                }).catch((res) => {
                    this.$notice.toast({
                        message: 'Your Points balance is not enough to \n' +
                        'redeem this card.'
                    })
                    // this.$notice.toast({
                    //     message: res
                    // })
                })

                this.show = false;
            },
            wxcDialogNoPromptClicked (e) {
                // 此处必须设置，组件为无状态组件，自己管理
                this.isChecked = e.isChecked;
            }
        }
    }

</script>

<style>
    .wrapper{
        width: 750px;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .overflow-top{
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-top: 161px;
    }
    .top-card{
        width: 688px;
        height: 616px;
        background-color: white;
        overflow: hidden;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }
    .top-card-imgdiv{
        margin-top: 7px;
        align-items: center;
    }
    .top-card-img{
        width: 636px;
        height: 400px;
    }
    .top-card-txt{
        flex-direction: column;
        justify-content: space-around;
        height: 212px;
        margin-left:32px;
        margin-top: 24px;
    }

    .top-card-txt1{
        font-weight: 700;
        font-family: ProximaNova-Bold;
        font-size: 38px;
        color: rgba(0,0,0,0.87);
    }
    .top-card-txt2{
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
        margin-bottom: 48px;
        line-height: 21px;
    }
    .overflow-bottom{
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-top: 16px;
    }
    .bottom-card{
        height: 424px;
        width: 688px;
        background-color: white;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        flex-direction: column;
        justify-content: flex-start;

    }
    .bottom-card-txt1{
        margin-left: 32px;
        margin-top: 32px;
        margin-bottom: 24px;
        font-weight: 700;
        font-size: 28px;
        font-family: ProximaNova-Bold;
    }
    .bottom-card-txt2{
        margin-left: 32px;
        margin-bottom: 16px;
        font-family: ProximaNova-Regular;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        line-height: 36px;
    }




    /*.wxdialog{*/
        /*border-radius: 8px;*/
    /*}*/
    .bottom-btn{
        background-color: #fff;
        box-shadow:  0 -2px 3px 0 rgba(0,0,0,0.24);
        width: 750px;
        height: 112px;
        position: fixed;
        bottom: 0;
        right: 0;
        overflow: hidden;
    }
    .button{
        color: #fff;
        height: 80px;
        background-color: #EF8A31;
        border-color: #2e6da4;
        border-radius: 12px;
        padding-top: 22px;
        padding-bottom: 10px;
        margin-top: 16px;
        margin-left: 12px;
        margin-right: 12px;
        margin-bottom: 16px;
        font-size: 28px;
        text-align: center;
        font-weight: 700;
    }
</style>
