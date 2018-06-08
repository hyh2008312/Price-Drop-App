<template>
    <div class="wrapper">
        <text class="search iconfont" @click="jumpWeb()">&#xe6f0; 搜索</text>
        <div class="notice">
            <text class="ic iconfont">&#xe6f0;</text>
            <text class="txt">规则</text>
        </div>
    </div>
</template>
<script>
    export default {
        methods: {
            jumpWeb (_url) {
                this.$router.open({
                    name: 'mobile.activity',
                    type: 'PUSH'
                })
            }
        }
    }
</script>
<style scoped>
    .iconfont {
        font-family: iconfont;
    }

    .wrapper {
        margin-top: 40px;
        width: 750px;
        height: 112px;
        padding-top: 8px;
        flex-wrap: nowrap;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
        z-index: 101;
        background-color: #000;
    }

    .scan,
    .notice {
        height: 80px;
        width: 100px;
        justify-content: center;
        align-items: center;
    }

    .ic,
    .txt {
        text-align: center;
        color: #fff;
        font-weight: 300;
    }

    .txt {
        margin-top: 8px;
        font-size: 20px;
    }

    .ic {
        font-size: 32px;
    }

    .search {
        flex: 1;
        height: 80px;
        font-size: 26px;
        color: rgba(0, 0, 0, 0.54);
        background-color: #fff;
        border-radius: 6px;
        align-items: center;
        margin-left: 32px;
        padding: 22px 16px;
    }
</style>
