<template>
    <div class="item-container" :style="contentStyle">
        <socialcommer-bar class="tab-bar" :tabItems="tabItems"></socialcommer-bar>
    </div>
</template>

<style scoped>
    .item-container {
        width: 750px;
        background-color: #f2f3f4;
        align-items: center;
        justify-content: center;
    }
    .tab-bar{
        position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
    }
</style>
<script>
    import { Utils } from 'weex-ui';
    import SocialcommerBar from '../../../components/tabs/bar.vue';
    import Config from './config'

    export default {
        components: { SocialcommerBar },
        data: () => ({
            tabItems: Config.tabItems
        }),
        created () {
            const tabPageHeight = Utils.env.getPageHeight();
            // If the page doesn't have a navigation bar
            // const tabPageHeight = env.deviceHeight / env.deviceWidth * 750;
            this.contentStyle = { height: tabPageHeight + 'px' };
            console.log(this.tabItems)
        },
        methods: {
            wxcTabBarCurrentTabSelected (e) {
                const index = e.page;
                switch (index) {
                    case 0:
                    this.$router.open({
                        name: 'mobile.home',
                        type: 'PUSH'
                    });
                    break;
                }
            }
        }
    };
</script>
