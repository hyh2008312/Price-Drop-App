<template>
    <div>
        <text>这里是首页</text>
    </div>
</template>

<style scoped>
    .item-container {
        width: 750px;
        background-color: #f2f3f4;
        align-items: center;
        justify-content: center;
    }
</style>
<script>
    import { WxcTabBar, Utils } from 'weex-ui';

    export default {
        components: { WxcTabBar },
        data: () => ({
            tabTitles: Config.tabIconFontTitles,
            tabStyles: Config.tabIconFontStyles
        }),
        created () {
            const tabPageHeight = Utils.env.getPageHeight();
            // If the page doesn't have a navigation bar
            // const tabPageHeight = env.deviceHeight / env.deviceWidth * 750;
            const { tabStyles } = this;
            this.contentStyle = { height: (tabPageHeight - tabStyles.height) + 'px' };
        },
        methods: {
            wxcTabBarCurrentTabSelected (e) {
                const index = e.page;
                // console.log(index);
            }
        }
    };
</script>
