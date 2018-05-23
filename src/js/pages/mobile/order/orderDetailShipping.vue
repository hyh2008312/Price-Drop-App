<template>
    <div class="wrapper">
        <order-line></order-line>
        <div class="gb-box">
            <text class="gb-title">Shipping To</text>
            <div class="gb-bg">
                <text class="gb-icon iconfont">&#xe707;</text>
                <text class="gb-text">Steven Huang</text>
            </div>
            <div class="gb-bg-1">
                <text class="gb-icon iconfont">&#xe706;</text>
                <text class="gb-text">+1 805-497-3701</text>
            </div>
            <div class="gb-bg-1">
                <text class="gb-icon iconfont">&#xe705;</text>
                <div>
                    <text class="gb-text-1">House, Street, Landmark</text>
                    <text class="gb-text-1 gb-mt">75 W Thousand Oaks Blvd, Thousand Oaks, CA 91360</text>
                </div>
            </div>
        </div>
        <order-line></order-line>
    </div>
</template>
<script>
    import line from './orderDetailLine';

    export default {
        components: {
            'order-line': line
        },
        props: ['shipping'],
        data () {
            return {
                src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
            }
        },
        methods: {}
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        background-color: #fff;
        width: 750px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }

    .gb-box {
        padding: 32px;
    }

    .gb-title{
        font-size: 28px;
        line-height: 34px;
        font-weight: bold;
    }

    .gb-icon{
        font-size: 24px;
        line-height: 24px;
        margin-right: 16px;
        color: rgba(0,0,0,0.54);
    }

    .gb-text{
        font-size: 24px;
        line-height: 28px;
        font-weight: bold;
        color: rgba(0,0,0, 0.87);
    }

    .gb-text-1{
        font-size: 24px;
        line-height: 28px;
        color: rgba(0,0,0, 0.87);
    }

    .gb-bg{
        margin-top: 32px;
        flex-direction: row;
        align-items: center;
    }

    .gb-bg-1{
        margin-top: 16px;
        flex-direction: row;
        align-items: start;
    }

    .gb-mt{
        margin-top: 16px;
    }

</style>
