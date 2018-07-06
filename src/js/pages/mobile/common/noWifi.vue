<template>
    <div class="wrapper">
        <div class="container-1">
            <div class="container-2">
                <image class="no-wifi-image" src="bmlocal://assets/error-05.png"></image>
            </div>
            <text class="no-wifi-title">It seems your internet is not stable. </text>
            <text class="no-wifi-title-1">Please try again or reopen your app!</text>
            <text class="gd-button" @click="reload">Reload</text>
        </div>
    </div>
</template>
<script>
export default {
    methods: {
        reload () {
            this.$notice.toast(111)
            this.$emit('onReload', {
                status: 'reload',
                data: {
                    hasWifi: false
                }
            });
        }
    }
}
</script>

<style scoped>
.iconfont {
    font-family: iconfont;
}

.wrapper {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    top: 0;
    background-color: #fff;
}

.container-1{
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background-color: #fff;
    align-items: center;
}

.container-2{
    margin-top: 86px;
    width: 750px;
    align-items: center;
}

.no-wifi-image{
    width: 208px;
    height: 216px;
}

.no-wifi-title{
    margin-top: 32px;
    font-size: 28px;
    line-height: 34px;
    text-align: center;
    color: rgba(0,0,0, 0.38);
}

.no-wifi-title-1{
    margin-top: 8px;
    font-size: 28px;
    line-height: 34px;
    text-align: center;
    color: rgba(0,0,0, 0.38);
}

.gd-button{
    margin-top: 48px;
    width: 200px;
    text-align: center;
    font-size: 28px;
    line-height: 72px;
    font-weight: bold;
    border-radius: 4px;
    background-color: #EF8A31;
    color: #FFF;
}

</style>
