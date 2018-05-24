<template>
    <div class="wrapper">
        <div class="wrapper-inner">
            <div>
                <text class="gb-txt">Order Number:  68778989078678586898</text>
                <text class="gb-txt gb-mt">Order Time: May 6th,2018</text>
            </div>
            <text class="cy iconfont">&#xe708;</text>
        </div>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                list: [1, 2, 3, 4, 5, 6, 7]
            }
        }
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

    .wrapper-inner{
        padding: 32px;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .cy{
        font-size: 24px;
        line-height: 24px;
        color: rgba(0,0,0,.38);
    }

    .gb-txt{
        font-size: 20px;
        line-height: 24px;
    }

    .gb-mt{
        margin-top: 16px;
    }

</style>
