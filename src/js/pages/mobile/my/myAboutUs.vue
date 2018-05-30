<template>
    <div class="wrapper">
        <topic-header title="AboutUs" leftBtn="icon"  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div class="mid-cell" >
            <!--<text class="header">About Us</text>-->
            <div class="content">
                <text>&nbsp;&nbsp;PriceDrop is a fun, social and money-saving shopping app that lets you shop your favorite products at the lowest price by teaming up with your friends on Facebook & WhatsApp. 
                </text>
                <text>&nbsp;&nbsp;Our mission is to help our customers save bigger, and create a more engaging social selling channel for brands and manufacturers from around the world! 
                </text>
                <text>&nbsp;&nbsp;PriceDrop is a venture-backed company founded by two Y Combinator alumnus and former executives from Amazon, Google and Alibaba Group. If you are passionate about transforming the way people shop online, come join us by sending your resume to: career@getpricedrop.com.
                </text>
            </div>

        </div>
    </div>
</template>

<script>
    import header from './header';

    export default {
        components: {
            'topic-header': header
        },
        methods: {}
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
    .mid-cell{
        margin-top: 160px;
        border-radius: 4px;
        background-color: #fff;
    }
    .header{
        width: 750px;
        font-size: 35px;
        font-weight: 700;
        text-align: center;
    }
    .content{
        width: 750px;
        text-align: center;
        margin-top: 30px;
        margin-left: 10px;
    }
</style>
