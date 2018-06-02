<template>
    <div class="wrapper">
        <topic-header title="FAQ" leftBtn="icon"  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div class="mid-cell" >
            <div class="box-tlt " @click="openNew(1)">
                <div class="box-left">
                    <text class="box-txt">My Drops FAQs</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(2)">
                <div class="box-left">
                    <text class="box-txt">My Orders FAQs</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(3)">
                <div class="box-left">
                    <text class="box-txt">Shipping FAQs</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(4)">
                <div class="box-left">
                    <text class="box-txt">Customs Duty FAQs</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(5)">
                <div class="box-left">
                    <text class="box-txt">Returns FAQs</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>
            <div class="box-tlt "  @click="openNew(6)">
                <div class="box-left">
                    <text class="box-txt">Payment FAQs</text>
                </div>
                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

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
        name: 'myDetail',
        methods: {
            openNew (head) {
                this.$router.open({
                    name: 'my.faqTmp',
                    type: 'PUSH',
                    params: {
                        type: head
                    }
                })
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
