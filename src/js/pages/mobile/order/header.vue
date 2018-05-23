<template>
    <div class="wrapper">
        <text class="back iconfont" @click="back()">&#xe6f6;</text>
        <text class="title">{{title}}</text>
    </div>
</template>
<script>
    export default {
        props: ['title'],
        methods: {
            back () {
                this.$router.finish()
            }
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        position: sticky;
        margin-top: 44px;
        width: 750px;
        height: 112px;
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color: #fff;
        z-index: 100;
        box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
    }

    .back{
        font-size: 32px;
        line-height: 32px;
        padding-left: 32px;
        padding-top: 32px;
        padding-bottom: 32px;
    }

    .title{
        width: 622px;
        font-size: 32px;
        line-height: 38px;
        font-weight: bold;
        color: rgba(0,0,0,0.87);
        text-align: center;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
</style>
