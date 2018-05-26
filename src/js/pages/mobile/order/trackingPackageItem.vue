<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="gb-box-left">
                <text class="gb-text-2">2018</text>
                <text class="gb-text-2">04-18</text>
                <text class="gb-text-3">20:34</text>
            </div>
            <div class="gb-box-center">
                <text class="iconfont gb-box-icon">&#xe716;</text>
            </div>
            <div class="gb-box-line"></div>
            <div class="gb-box-right">
                <text class="gb-text">Delivered</text>
                <text class="gb-text-1">[Address] 75 W Thousand Oaks Blvd, Thousand Oaks, CA 91360</text>
            </div>
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
    }

    .gb-box{
        flex-direction: row;
        align-items: stretch;
    }

    .gb-text{
        font-size: 28px;
        line-height: 34px;
        margin-bottom: 6px;
    }

    .gb-text-1{
        font-size: 24px;
        line-height: 40px;
        margin-bottom: 64px;
    }

    .gb-text-2{
        font-size: 24px;
        line-height: 28px;
        margin-bottom: 4px;
    }

    .gb-text-3{
        font-size: 20px;
        line-height: 24px;
        color: rgba(0,0,0,0.38);
    }

    .gb-box-left{
        width: 126px;
        padding-right: 26px;
        align-items: flex-end;
    }

    .gb-box-center{
        width: 32px;
        align-items: center;
    }

    .gb-box-line{
        position: absolute;
        width: 2px;
        background-color: rgba(0,0,0,0.08);
        top: 32px;
        bottom: 0;
        left: 142px;
    }

    .gb-box-right{
        width: 592px;
        padding-left: 26px;
        padding-right: 40px;
    }

    .gb-box-icon{
        font-size: 32px;
        line-height: 32px;
        color: #EF8A31;
    }

</style>
