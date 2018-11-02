<template>
    <div class="wrapper">
        <topic-header :title="title" leftBtn='icon'  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <scroller>
            <text class="header1">&nbsp;</text>
                <div v-if="settingPage==0" class="content" v-for="(i,index) in QAArr ">
                    <text class="question">{{index+1}}. {{i.question}}</text>
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
                    <text class="h2">PriceDrop hereby reserves the right to modify/amend or otherwise change this privacy policy, as it deems necessary or appropriate, due to legal compliance requirements or changes to our business practices. If you have provided us with an email address we will endeavor to notify you, by email to that address, of any material change to how we will use personally identifiable information.</text>
                </div>
            </div>
            <div v-if="settingPage==3" class="content" >  <!--about us-->
                <text class="policy">PriceDrop is a fun, social and money-saving shopping app that lets you shop your favorite products at the lowest price by teaming up with your friends on Facebook & WhatsApp. </text>
                <text class="policy">Our mission is to help our customers save bigger, and create a more engaging social selling channel for brands and manufacturers from around the world! </text>
                <text class="policy">PriceDrop is a venture-backed company founded by two Y Combinator alumnus and former executives from Amazon, Google and Alibaba Group. If you are passionate about transforming the way people shop online, come join us by sending your resume to: career@getpricedrop.com.</text>
            </div>
            <div v-if="settingPage==4" class="content" >  <!--Shipping Policy-->
                <div>
                    <text class="policy">All the products will be shipped in 4-5 working days as long as the order is placed, and the delivery time will depends on the shipping location and shipping method.
                    </text>
                </div>
            </div>
            <div v-if="settingPage==5" class="content" >  <!--Return Policy-->
                <div>
                    <text class="policy">You may return all items sold by PriceDrop within 9 days of delivery for a refund, as long as it is unused and in a good condition.
                    </text>
                    <text class="policy">Currently we are not able to offer item exchange service for any shipped orders. If you want a new item, please apply for the refund and then place a new order.
                    </text>
                </div>
                <div v-for="(i,index) in QAArr ">
                    <text class="question">{{index+1}}.{{i.question}}</text>
                    <text class="answer">{{i.answer}}</text>
                </div>
            </div>
            <div v-if="settingPage==6" class="content" >  <!--Cancellation Policy-->
                <div>
                       <text class="policy">To cancel an unfulfilled order, please contact our customer support team by sending an email along with your order number to the following email address: support@getpricedrop.com ;
                       </text>
                    <text class="policy">Note: If your item has been shipped or under packing, you can’t cancel your order any more.</text>
                </div>
            </div>
            <div v-if="settingPage==7" class="content" >  <!--Disclaimer-->
                <div>
                    <text class="policy">All goods sold by Our Company are warranted to Buyer to be free from defects in material and workmanship, and manufactured in accordance with industry standards. Therefore going warranty is non-assignable and in lieu of and excludes all other warranties not expressly set forth herein, whether express or implied by operation of law or other wise including but not limited to any implied warranties of merchant ability or fitness. No agent, employee, or representative of Our Company has any authority to bind Our Company to any representation, affirmation, or warranty concerning the goods and any such representation, affirmation, or warranty shall not be deemed to have become apart of the basis of this agreement and shall be unenforceable.
                    </text>
                    <text class="policy">Any claimed defect in material or workmanship shall be deemed waived by Buyer unless submitted to Our Company in writing within five (5) days from the date the goods are received by Buyer. Our Company shall not be liable under the foregoing warranty if any loss or damage is caused by improper application or use of the goods. Our Company disclaims all liability with respect to the design of the goods and makes no warranty with respect to such design. This warranty is in lieu of and excludes all other warranties, whether express, implied, or statutory, including implied warranties of merchantability or fitness.
                    </text>
                </div>

            </div>
            <div v-if="settingPage==8" class="content" >  <!--Contact Us-->
                <div>
                    <text class="policy">If you have any questions, please feel free to drop us an email or give us a call.
                    </text>

                    <div v-for="(i,index) in QAArr ">
                        <text class="question">{{i.question}}</text>
                        <text class="answer">{{i.answer}}</text>
                    </div>
                </div>

            </div>

            <text class="answer">&nbsp;&nbsp;&nbsp;</text>
        </scroller>
    </div>
</template>

<script>
    import header from './header';
    import { DropsQA, OrderQA,
        ShippingQA, CustomsQA,
        ReturnsQA, PaymentQA,
        TermAndCondittions, Privacy,
        Return, Contact, VoucherQA, CODDeliveryQA } from './config'

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
                    this.title = 'My Drops FAQs';
                    this.QAArr = DropsQA
                } else if (p.type == 2) {
                    this.title = 'My Orders FAQs';
                    this.QAArr = OrderQA
                } else if (p.type == 3) {
                    this.title = 'Shipping FAQs';
                    this.QAArr = ShippingQA
                } else if (p.type == 4) {
                    this.title = 'Customs Duty FAQs';
                    this.QAArr = CustomsQA
                } else if (p.type == 5) {
                    this.title = 'Returns FAQs';
                    this.QAArr = ReturnsQA
                } else if (p.type == 6) {
                    this.title = 'Payment FAQs';
                    this.QAArr = PaymentQA
                } else if (p.type == 7) {
                    this.title = 'Privacy Policy';
                    this.settingPage = 1;
                    this.term = TermAndCondittions
                } else if (p.type == 8) {
                    this.title = 'Terms & Conditions';
                    this.settingPage = 2;
                    this.QAArr = Privacy
                } else if (p.type == 9) {
                    this.title = 'About Us';
                    this.settingPage = 3
                } else if (p.type == 10) {
                    this.title = 'Shipping Policy';
                    this.settingPage = 4
                } else if (p.type == 11) {
                    this.title = 'Return Policy';
                    this.settingPage = 5;
                    this.QAArr = Return
                } else if (p.type == 12) {
                    this.title = 'Cancellation Policy';
                    this.settingPage = 6
                } else if (p.type == 13) {
                    this.title = 'Disclaimer';
                    this.settingPage = 7
                } else if (p.type == 14) {
                    this.title = 'Contact Us';
                    this.settingPage = 8;
                    this.QAArr = Contact
                } else if (p.type == 15) {
                    this.title = 'Voucher FAQs'
                    this.QAArr = VoucherQA
                } else if (p.type == 16) {
                    this.title = 'COD Delivery FAQs'
                    // this.settingPage = 8
                    this.QAArr = CODDeliveryQA
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
        padding-left: 32px ;
        padding-right: 32px ;
        padding-top: 48px;

    }
    .h1{
        font-size: 28px;
        padding-right:32px ;
        padding-left:32px ;
        font-weight: 700;
        line-height: 40px;
    }
    .h2{
        padding-right:30px ;
        padding-left:30px ;
        line-height: 50px;
    }
    .header1{
        margin-top: 160px;
        font-size: 24px;
        font-weight: 700;
    }
    .question{
        font-weight: 700;
        margin-top: 16px;
        padding-right:30px ;
        padding-left:30px ;
        line-height: 48px;
        font-size: 28px;
    }
    .term{
        font-size: 24px;
        line-height: 40px;
    }
    .policy{
        font-size: 28px;
        padding-right:32px ;
        padding-left:32px ;
        line-height: 48px;
    }
    .answer{
        margin-top: 4px;
        margin-right: 32px;
        margin-left: 32px;
        margin-bottom: 12px;
        line-height: 40px;
        font-size: 24px;

    }
</style>
