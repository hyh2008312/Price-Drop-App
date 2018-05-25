<template>
    <div class="wrapper">
        <div class="gb-box">
            <div class="gb-bg">
                <text class="gb-icon iconfont">&#xe702;</text>
                <text class="gb-text">Unpaid</text>
            </div>
            <div class="gb-bg-1">
                <text class="gb-icon iconfont gd-text-color">&#xe703;</text>
                <text class="gd-text gd-text-color">Ends in</text>
                <wxc-countdown tpl="{h}:{m}:{s}"
                               :time="time"
                               :timeBoxStyle="{backgroundColor: 'transparent', height: '28px', width: '28px','border-radius': '4px'}"
                               :timeTextStyle="{fontSize: '24px', color: '#EF8A31'}"
                               :dotTextStyle="{color: '#EF8A31', fontSize: '24px'}"
                               :dotBoxStyle="{width: '6px'}"
                               :style="{justifyContent: 'center'}">
                </wxc-countdown>
            </div>
        </div>
    </div>
</template>
<script>
    import line from './orderDetailLine';
    import { WxcCountdown } from 'weex-ui'

    export default {
        components: {
            'order-line': line,
            WxcCountdown
        },
        props: ['shipping'],
        data () {
            return {
                src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png',
                time: new Date().getTime() + 86400000 + ''
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
        font-size: 28px;
        width: 32px;
        text-align: center;
        line-height: 32px;
        margin-right: 24px;
        color: rgba(0,0,0,0.54);
    }

    .gb-text{
        font-size: 28px;
        line-height: 34px;
        color: rgba(0,0,0, 0.87);
    }

    .gb-bg{
        flex-direction: row;
        align-items: center;
    }

    .gb-bg-1{
        margin-top: 22px;
        flex-direction: row;
        align-items: center;
    }

    .gd-text{
        font-size: 24px;
        line-height: 28px;
    }

    .gd-text-color{
        color: #EF8A31;
    }

</style>
