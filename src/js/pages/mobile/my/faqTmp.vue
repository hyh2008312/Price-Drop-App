<template>
    <div class="wrapper">
        <topic-header :title="title" leftBtn='icon'  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <scroller>
            <text class="header"></text>
            <div v-if="settingPage==0" class="" v-for="(i,index) in QAArr ">
                <text class="question">{{index+1}}.{{i.question}}</text>
                <text class="answer">{{i.answer}}</text>
            </div>

            <div v-if="settingPage==1" class="content">     <!--term & conditions-->
                <div>
                    <text class="h1">By using this app and/or placing an order, you agree to be bound by the terms and conditions set out below. You must read, agree with and accept all of the terms and conditions contained in this agreement including our privacy policy before you may become our registered user or place an order.
                    </text>
                    <text class="h1">We reserve the right to update and change the Terms & Conditions by posting updates and changes to the PriceDrop App. You are advised to check the Terms & Conditions from time to time for any updates or changes that may impact you.
                    </text>
                </div>
                <div class="question" v-for="(item,index) in term" >
                    <text class="term">{{index+1}}.{{item}}</text>
                </div>
            </div>

            <div v-if="settingPage==2" class="content" >  <!--privacy-->
                <div>
                    <text class="policy">Your privacy is important to us at PriceDrop App. This Privacy Policy explains how we collect and utilize information about our customers. By using our app, you are accepting the practices described in this Privacy Policy.
                    </text>
                </div>
                <div v-for="(i,index) in QAArr ">
                    <text class="question">{{index+1}}.{{i.question}}</text>
                    <text class="answer">{{i.answer}}</text>
                </div>

                <div>
                    <text class="h1">CHANGES TO THE PRIVACY POLICY
                    </text>
                    <text>PriceDrop hereby reserves the right to modify/amend or otherwise change this privacy policy, as it deems necessary or appropriate, due to legal compliance requirements or changes to our business practices. If you have provided us with an email address we will endeavor to notify you, by email to that address, of any material change to how we will use personally identifiable information.</text>
                </div>
            </div>


        </scroller>
    </div>
</template>

<script>
    import header from './header';
    import { DropsQA, OrderQA, ShippingQA, CustomsQA, ReturnsQA, PaymentQA, TermAndCondittions, Privacy } from './config'

    export default {
        components: {
            'topic-header': header
        },
        eros: {
            beforeAppear (a) {
                this.check(a)
            }
        },
        // props: ['type', 'rightBtn', 'leftBtn'],
        data () {
            return {
                QAArr: '',
                title: '',
                settingPage: 0,
                term: []
            }
        },
        created () {
            // this.$notice.toast({ message: 111 })
           this.check(11)
        },
        methods: {
            check (p) {
                if (p.type == 1) {
                    this.title = 'My Drops FAQs'
                    this.QAArr = DropsQA
                } else if (p.type == 2) {
                    this.title = 'My Orders FAQs'
                    this.QAArr = OrderQA
                } else if (p.type == 3) {
                    this.title = 'Shipping FAQs'
                    this.QAArr = ShippingQA
                } else if (p.type == 4) {
                    this.title = 'Customs Duty FAQs'
                    this.QAArr = CustomsQA
                } else if (p.type == 5) {
                    this.title = 'Returns FAQs'
                    this.QAArr = ReturnsQA
                } else if (p.type == 6) {
                    this.title = 'Payment FAQs'
                    this.QAArr = PaymentQA
                } else if (p.type == 7) {
                    this.title = 'Terms & Conditions'
                    this.settingPage = 1
                    this.term = TermAndCondittions
                } else if (p.type == 8) {
                    this.title = 'Privacy Policy'
                    this.settingPage = 2
                    this.QAArr = Privacy
                }
            }
        }
    }
</script>

<style scoped>
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
    .content{
        padding-left: 30px ;
        padding-right: 30px ;
        padding-top: 50px;

    }
    .h1{
        padding-right:30px ;
        padding-left:30px ;
        font-weight: 700;
        line-height: 50px;
    }
    .header{
        margin-top: 160px;
        font-size: 24px;
        font-weight: 700;
    }
    .question{
        font-weight: 700;
        margin-top: 16px;
        padding-right:30px ;
        padding-left:30px ;
        line-height: 50px;
    }
    .term{
        line-height: 50px;
    }
    .policy{
        padding-right:30px ;
        padding-left:30px ;
        line-height: 50px;
    }
    .answer{
        margin: 20px;
        line-height: 40px;

    }

</style>
