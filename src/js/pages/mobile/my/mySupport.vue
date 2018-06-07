<template>
    <div class="wrapper">
        <topic-header title="Customer Support" leftBtn="icon"  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div class="mid-cell" >
            <!--<text class="header">Customer Support</text>-->
            <div class="content">
                <text class="text-word">Welcome to PriceDrop and we would like to hear from you! </text>

                <div style="margin-bottom: 16px"><text class="text-word">If you have any questions about drops, orders, shipping or return policy, we recommend you to check our FAQ first. If you still can’t find an answer, please send your questions to: </text><text class="haveline text-word-no" style="font-weight: 700">support@getpricedrop.com.</text></div>

                <div>
                    <text class="text-word">If you want to return an item for a full refund, please send your return request to</text></div>
                    <div><text value="support@getpricedrop.com" class="haveline text-word-no"></text><text>along with your order number and the name of the item you want to return.</text> </div>
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
        margin-bottom: 60px;
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
        margin-top: 48px;
        margin-bottom: 48px;
        padding-right: 32px;
        padding-left: 32px;
    }
    .text-word{
        margin-bottom: 8px;
        margin-top: 8px;
        line-height: 48px;
    }
    .text-word-no{
        line-height: 48px;
        margin-top: -10px;
    }
    .haveline{
        color: #000;
        font-weight: 700;
        text-decoration:underline;
    }
</style>
