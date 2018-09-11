<template>
    <div class="wrapper">
        <topic-header title="My Raffle Draws" leftBtn="icon"  ref="ref1" ></topic-header>
        <div class="blackheader"></div>
        <list class="list" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <refresher ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>
            <cell v-for="(i ,index) in goods">
                <rewardItem :goods=i :key="index"></rewardItem>
            </cell>
            <cell class="blank" v-if="goods">
            </cell>
            <cell class="container-1" :style="height" v-if="goods.length == 0">
                <div class="container-2">
                    <image class="pay-image" src="bmlocal://assets/empty.png"></image>
                </div>
                <text class="address-title">There is no reward to show.</text>
            </cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </loading>
        </list>
    </div>
</template>

<script>
    import header from './header';
    import rewardItem from './rewardItem';
    import { Utils } from 'weex-ui';
    import refresher from '../common/refresh';
    export default {
        components: {
            'topic-header': header,
            rewardItem,
            'refresher': refresher
        },
        created () {
            const pageHeight = Utils.env.getScreenHeight();
            this.height = { height: (pageHeight - 48 - 112) + 'px' };
            this.loadingStart();
            this.requestRewards(true);
            },
        data () {
            return {
                first: true,
                goods: false,
                page: 1,
                length: 2,
                pageSize: 12,
                isLoading: false,
                isPlatformAndroid: Utils.env.isAndroid()
            }
        },
        methods: {
            onloading () {
            },
            requestRewards (isFirst) {
                if (isFirst) {
                    this.page = 1;
                }
                if (this.page > this.length) {
                    this.$refs.refresh.refreshEnd();
                    this.$nextTick(() => {
                        this.isLoading = false
                    });
                    return
                }
                this.getAwardList(isFirst);
            },
            getAwardList (isFirst) {
                this.$fetch({
                    method: 'GET',
                    name: 'lottery.draw.prize.list',
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    },
                    header: {
                        needAuth: true
                    }

                }).then(data => {
                    this.loadingEnd();
                    if (data.count == 0) {
                        this.length = 2;
                    } else {
                        this.length = Math.ceil(data.count / this.pageSize);
                    }
                    if (isFirst) {
                        this.goods = [];
                    }
                    this.page++;
                    this.goods.push(...data.results);
                    if (!isFirst) {
                        this.isLoading = false;
                    }
                }, error => {
                    this.loadingEnd();
                })
            },
            loadingStart () {
                this.$notice.loading.show('');
            },
            loadingEnd () {
                this.$notice.loading.hide();
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.requestRewards(true);
            },
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.requestRewards(false);
                }
            }
        }
    }
</script>

<style>
    .iconfont{
        font-family: iconfont;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .list{
       margin-top: 160px;
    }
    .loading{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        padding: 16px 0;
    }
    .loading-icon{
        width: 64px;
        height: 64px;
    }
    .blank{
        height: 40px;
    }

</style>
