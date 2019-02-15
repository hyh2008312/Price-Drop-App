<template>
    <div class="wrapper">
        <div @click="openRuler(dropTab)">
            <image style="width: 750px;height: 166px" v-if="dropTab=='my'" src="bmlocal://assets/home/drop-mine-v1.3.png"></image>
            <image style="width: 750px;height: 166px" v-if="dropTab=='friend'" src="bmlocal://assets/home/drop-friends-v1.3.png"></image>
            <!--<text class="center-word">Save More with Help from Friends</text>-->
            <div class="top-target" v-if="dropTab=='my'">
                <text class="t-word" >Learn More</text>
                <text class="t-word1 iconfont">&#xe626;</text>
            </div>
            <div class="top-target" v-if="dropTab=='friend'">
                <text class="t-word" >How to Earn</text>
                <text class="t-word1 iconfont">&#xe626;</text>
            </div>
        </div>
        <div class="content" v-if="dropTab=='my'">
            <text class="c-tword">Start to Drop Price Now</text>
            <div class="cimg-group">
                <div class="c-img">
                    <image class="icon-img" src="bmlocal://assets/pic-discount.png"></image>
                </div>
                <div class="c-line"></div>
                <div class="c-img">
                    <image class="icon-img" src="bmlocal://assets/pic-help.png"></image>
                </div>
                <div class="c-line"></div>
                <div class="c-img">
                    <image class="icon-img" src="bmlocal://assets/pic-cash.png"></image>
                </div>
            </div>
            <div class="c-bottom">
                <div class="cb-txt-of">
                    <text class="cb-txt1">Share</text>
                    <text class="cb-txt1">with Friends</text>
                </div>
                <div class="cb-txt-of">
                    <text class="cb-txt2">Friends</text>
                    <text class="cb-txt2">Click Help</text>

                </div>
                <div class="cb-txt-of">
                    <text class="cb-txt1">You</text>
                    <text class="cb-txt1">Save More</text>
                </div>

            </div>
        </div>
        <div class="content" v-if="dropTab=='friend'">

            <div class="help-fri">
                <div class="hf-content1">
                    <text class="hfc-txt1">Total Rewards</text>
                    <text class="hfc-price1">₹{{parseInt(allBouns.dropRewardTotal) || 0}}</text>
                </div>
                <div class="hf-content2">
                    <text class="hfc-txt2">Earned</text>
                    <text class="hfc-price2">₹{{parseInt(allBouns.dropRewardEarned) || 0}}</text>
                </div>

                <div class="hf-content3">
                    <text class="hfc-txt3">Pending</text>
                    <text class="hfc-price3">₹{{parseInt(allBouns.dropRewardTotal) - parseInt(allBouns.dropRewardEarned) || 0}}</text>
                </div>
            </div>

        </div>
    </div>

</template>

<script>
    export default {
        props: ['dropTab', 'allBouns'],
        name: 'myDropTop',
        methods:{
            openRuler(key){
                if(key=='my'){
                    this.$emit('rulerType',1)
                } else {
                    this.$emit('rulerType',2)
                }
            }
        }
    }
</script>

<style scoped>
    .wrapper{

    }
    .iconfont{
        font-family: iconfont;
    }
    .center-word{
        position: absolute;
        top: 55px;
        left: 170px;
        color: white;
    }
    .top-target{
        position: absolute;
        top: 0;
        right: 0;
        background-color: #FFC800;
        padding: 10px 0;
        border-bottom-left-radius: 50%;
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .t-word{
        font-size: 24px;
        color: #000000;
        margin-left: 32px;
    }
    .t-word1{
        font-size: 22px;
        margin-right: 4px;
    }
    .content{
        flex-direction: column;
        align-items: center;
        justify-content: space-between;
        background-color: #705CAA;
        width: 686px;
        /*height: 128px;*/
        border-radius: 16px;
        margin-left: 32px;
        margin-top: -26px;
    }
    .c-tword{
        font-weight: 900;
        font-size: 34px;
        color: #FFFFFF;
        margin-top: 26px;
    }
    .cimg-group{
        flex-direction: row;

    }
    .c-img{
        width: 56px;
        height: 56px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-top: 20px;
        margin-bottom: 8px;
        border-radius: 50%;
        background-color: white;
    }
    .icon-img{
        width: 36px;
        height: 36px;
    }
    .c-line{
        width: 164px;
        height: 2px;
        background-color: #fff;
        opacity: .24;
        margin-top: 45px;
    }
    .c-bottom{
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 20px;
        width: 686px;
    }
    .cb-txt-of{
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-bottom: 8px;
    }
    .cb-txt1{
        font-weight: 700;
        font-size: 28px;
        margin-left: 48px;
        margin-right: 55px;
        color: #FFFFFF;
        /*background-color: #EF8A31;*/
    }
    .cb-txt2{
        font-weight: 700;
        font-size: 28px;
        margin-right: 10px;
        color: #FFFFFF;
        /*background-color: #EF8A31;*/
    }
    .cb-txt3{
        font-weight: 700;
        font-size: 20px;
        margin-left: 46px;
        color: #FFFFFF;
        /*background-color: #EF8A31;*/
    }
    .help-fri{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        width: 686px;
        margin-top: 24px;
        margin-bottom: 26px;
    }
    .hf-content1{
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-left: 72px;
        padding-right: 50px;
        border-right-color: rgba(255,255,255, .3);
        border-right-style: solid;
        border-right-width: 1px;
    }
    .hfc-txt1{
        font-size: 24px;
        margin-bottom: 4px;
        color: #FFFFFF;
    }
    .hfc-price1{
        font-size: 40px;
        color: white;
        font-weight: 700;
    }
    .hf-content2{
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-left: 54px;
        padding-right: 50px;
        border-right-color: rgba(255,255,255, .3);
        border-right-style: solid;
        border-right-width: 1px;

    }
    .hfc-txt2{
        font-size: 24px;
        color: #FFFFFF;
        margin-bottom: 10px;

    }
    .hfc-price2{
        font-size: 28px;
        color: #FFFFFF;
    }
    .hf-content3{
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-left: 50px;
    }
    .hfc-txt3{
        font-size: 24px;
        color: #FFFFFF;
        margin-bottom: 10px;
    }
    .hfc-price3{
        font-size: 28px;
        color: #FFFFFF;
    }
</style>
