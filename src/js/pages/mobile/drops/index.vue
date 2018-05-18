<template>
    <div class="wrapper">
        <div class="state"></div>
        <div class="navigation">
            <text class="title">我的砍价</text>
        </div>
        <div>
            <cutTab :items="tabsItems" @tabTo="onTabTo"></cutTab>
        </div>
        <list class="main-list" ref="list" v-if="isCuting">
            <!-- <refresher @loadingDown="loadingDown"></refresher>-->
            <cell v-for="(i ,index) in goods3">
                <cutingItem :goods=i :key="index"></cutingItem>
            </cell>
            <!-- <loading class="loading">
                 <text class="indicator">加载中...</text>
             </loading>-->
        </list>
        <list class="main-list" ref="list" v-else>
            <cell v-for="(i ,index) in ['2','3','5']">
                <cutEndItem :goods=i :key="index"></cutEndItem>
            </cell>
            <!--<loading class="loading">
                <text class="indicator">加载中...</text>
            </loading>-->
        </list>
    </div>
</template>
<script>
    import refresher from '../common/refresh';
    import tabTitle from './tabTitle';
    import cutingItem from './cutingItem';
    import cutEndItem from './cutEndItem';
    import { TAB } from './config'

    export default {
        components: {
            'refresher': refresher,
            'cutTab': tabTitle,
            cutingItem,
            cutEndItem
        },
        created() {
            this.init();
            this.goods3 = ['1', '2', '3', '1', '2', '3', '1', '2', '3', '1', '2', '3']
        },
        data() {
            return {
                tabsItems: [],
                isCuting: true
            }
        },
        methods: {
            init() {
                this.getTabName()
            },
            getTabName() {
                this.tabsItems = TAB;
            },
            onTabTo(event) {
                this.tabKey = event.data.key;
                if (this.tabKey === 'cutEnd') {
                    this.isCuting = false;
                } else {
                    this.isCuting = true;
                }
                /* this.$nextTick(() => {
                    dom.scrollToElement(this.$refs['tab'], {animated: false})
                }) */
            }
        }
    }
</script>
<style scoped>
    .main-list {
        width: 750px;
        margin-bottom: 114px;
    }

    .banner {
        width: 750px;
        height: 360px;
        display: flex;
        margin-bottom: 18px;
        align-items: center;
        justify-content: center;
        position: relative;
    }

    .banner-image {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
    }

    .banner-text {
        font-size: 40px;
        color: rgba(255, 255, 255, .87);
    }

    .state {
        width: 750px;
        height: 24px;
        background-color: black;
    }

    .navigation {
        display: flex;
        width: 750px;
        height: 112px;
        background-color: black;
        flex-direction: row;
        justify-content: flex-start;
    }

    .title {
        font-size: 40px;
        height: 112px;
        line-height: 112px;
        margin-left: 36px;
        color: rgba(255, 255, 255, .87);
    }

    .homeBack {
        font-family: iconfont;
        color: white;
        font-size: 32px;
        height: 112px;
        margin-left: 32px;
        line-height: 112px;
    }

    .wrapper {
        background-color: white;
    }

    .margin-left16 {
        margin-left: 16px;
    }

    .magin-right16 {
        margin-right: 8px;
    }

    .i-gd {
        width: 350px;
        height: 600px;
        margin-bottom: 20px;
    }

    .gd-bg {
        border-width: 1px;
        border-color: rgba(0, 0, 0, 0.12);
        border-style: solid;
        border-radius: 8px;
        /* background-color: #f4f4f4;*/
    }

    .gd-img {
        width: 350px;
        height: 350px;
    }

    .gd-tip {
        font-size: 20px;
        color: rgba(172, 11, 11, 0.87);
        text-align: center;
        width: 350px;
        margin-top: 10px;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-price {
        font-size: 28px;
        width: 350px;
        margin-top: 4px;
        color: #000000;
        text-align: center;
        overflow: hidden;
        lines: 1;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-info {
        margin-top: 18px;
        text-align: center;
        lines: 1;
        font-size: 24px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .gd-bargain {
        width: 350px;
        margin-top: 14px;
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    .text-bargain {
        width: 120px;
        height: 50px;
        line-height: 50px;
        color: black;
        font-size: 24px;
        border-style: solid;
        border-color: black;
        border-width: 2px;
        text-align: center;
    }


</style>
