<template>
    <div class="wrapper">
        <div class="gb-box">
            <image class="logo" :src="src"></image>
            <div class="gb-box-center">
                <div class="gb-box-bg">
                    <text class="gb-text">Carrier: </text>
                    <text class="gb-text-color">Fedex</text>
                </div>
                <div class="gb-box-bg gb-mt">
                    <text class="gb-text">Tracking Number: 68778989078678</text>
                    <text class="gb-icon iconfont">&#xe708;</text>
                </div>
            </div>
            <text class="gb-icon-1 iconfont">&#xe70e;</text>
        </div>
    </div>
</template>
<script>
    export default {
        props: ['order'],
        data () {
            return {
                src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
            }
        },
        methods: {}
    }
</script>
<style scoped>
    .iconfont{
        font-family: iconfont;
    }

    .wrapper{
        background-color: #fff;
        width: 750px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }

    .logo{
        width: 80px;
        height: 80px;
        margin-right: 16px;
    }

    .gb-box{
        padding: 32px;
        flex-direction: row;
        align-items: center;
    }

    .gb-box-center{
        width: 526px;
        border-right-width: 2px;
        border-bottom-style: solid;
        border-right-color: rgba(0,0,0,0.08);
    }

    .gb-box-bg{
        flex-direction: row;
        align-items: center;
    }

    .gb-text-color{
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        color: #EF8A31;
    }

    .gb-text{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0,0.54);
    }

    .gb-mt{
        margin-top: 22px;
    }

    .gb-icon{
        margin-left: 32px;
        font-size: 24px;
        line-height: 24px;
        color: rgba(0,0,0,0.38);
    }

    .gb-icon-1{
        font-size: 32px;
        line-height: 32px;
        margin-left: 32px;
        color: #EF8A31;
    }

</style>
