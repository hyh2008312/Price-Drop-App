<template>
    <div class="wrapper">
        <topic-header title="About" leftBtn="icon"  ref="ref1" ></topic-header>

        <div class="blackheader"></div>

        <div class="mid-cell" >
            <div class="box-tlt " @click="openNew(9)">
                <div class="box-left">
                    <text class="box-txt">About Us</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(10)">
                <div class="box-left">
                    <text class="box-txt">Shipping Policy</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(11)">
                <div class="box-left">
                    <text class="box-txt">Return Policy</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(12)">
                <div class="box-left">
                    <text class="box-txt">Cancellation Policy</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(13)">
                <div class="box-left">
                    <text class="box-txt">Disclaimer</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(14)">
                <div class="box-left">
                    <text class="box-txt">Contact Us</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>

            <div class="box-tlt "  @click="openNew(7)">
                <div class="box-left">
                    <text class="box-txt">Privacy Policy</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(8)">
                <div class="box-left">
                    <text class="box-txt">Terms & Conditions</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
        </div>

        <!--<div class="mid-cell" >-->
            <!--&lt;!&ndash;<text class="header">About Us</text>&ndash;&gt;-->
            <!--<div class="content">-->
                <!--<text class="text-word">PriceDrop is a fun, social and money-saving shopping app that lets you shop your favorite products at the lowest price by teaming up with your friends on Facebook & WhatsApp. </text>-->
                <!--<text class="text-word">Our mission is to help our customers save bigger, and create a more engaging social selling channel for brands and manufacturers from around the world! </text>-->
                <!--<text class="text-word">PriceDrop is a venture-backed company founded by two Y Combinator alumnus and former executives from Amazon, Google and Alibaba Group. If you are passionate about transforming the way people shop online, come join us by sending your resume to: career@getpricedrop.com.</text>-->
            <!--</div>-->

        <!--</div>-->
    </div>
</template>

<script>
    import header from './header';

    export default {
        components: {
            'topic-header': header
        },
        methods: {
            openNew (head) {
                this.$router.open({
                    name: 'my.faqTmp',
                    type: 'PUSH',
                    params: {
                        type: head
                    }
                })
            },
        }
    }
</script>

<style>
    .wrapper{
        width: 750px;
    }
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
    .mid-cell{
        margin-top: 160px;
        border-radius: 4px;
        background-color: #fff;
    }
    .box-tlt {
        height: 96px;
        border-bottom-width: 1px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08);
        flex-direction: row;
        justify-content: space-between;
    }


    .box-txt {
        font-family: iconfont;
        font-size: 28px;
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
