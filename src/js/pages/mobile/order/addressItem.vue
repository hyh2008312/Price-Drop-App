<template>
    <div class="wrapper">
        <div class="gb-box">
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
        <div class="gb-bottom">
            <div class="gb-bottom-bg">
                <text class="iconfont item-checked">&#xe6fb;</text>
                <text class="item-no-checked"></text>
                <text class="gb-text-3">Select</text>
            </div>
            <div class="gb-bottom-bg">
                <text class="iconfont gb-icon-1">&#xe70b;</text>
                <text class="gb-text-2">Edit</text>
                <text class="iconfont gb-icon-2 gb-ml">&#xe70c;</text>
                <text class="gb-text-2">Delete</text>
            </div>
        </div>
    </div>
</template>
<script>
    import line from './orderDetailLine';

    export default {
        components: {
            'order-line': line
        },
        props: {
            shipping: {},
            isOrderConfirm: {
                default: false
            }
        },
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
        flex-direction: row;
        align-items: center;
    }

    .gb-bg-1{
        margin-top: 16px;
        flex-direction: row;
        align-items: start;
    }

    .gb-bottom{
        padding: 16px 32px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        border-top-width: 2px;
        border-top-style: solid;
        border-top-color: rgba(0,0,0,0.08);
    }

    .gb-mt{
        margin-top: 16px;
    }

    .item-checked{
        font-size: 32px;
        line-height: 32px;
        margin-right: 16px;
        color: #4AB406;
    }

    .item-no-checked{
        width: 32px;
        height: 32px;
        border-style: solid;
        border-width: 2px;
        border-color: rgba(0,0,0,0.54);
        border-radius: 16px;
        margin-right: 16px;
    }

    .gb-icon-1{
        font-size: 32px;
        line-height: 32px;
        margin-right: 8px;
        color: #F09F58;
    }

    .gb-icon-2{
        font-size: 28px;
        line-height: 28px;
        margin-right: 8px;
        color: #F09F58;
    }

    .gb-text-2{
        font-size: 24px;
        line-height: 28px;
        color: #F09F58;
    }

    .gb-text-3{
        font-size: 24px;
        line-height: 28px;
    }

    .gb-ml{
        margin-left: 48px;
    }

    .gb-bottom-bg{
        flex-direction: row;
        align-items: center;
    }

</style>
