<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header class="top" title="My Reviews" leftBtn="n"  ></topic-header>
        <list class="content" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <refresher class="gd-bg-gray" ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>
            <cell class="cell" v-for="i in userList">
                <div style="background-color: #fff;box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);">
                    <div @click="openWriteReview(i)">
                        <card :content="i" ></card>
                    </div>
                    <div class="card-item" @click="openProduct(i)">
                        <div>
                            <image class="c-t-image" :src="i.mainImage"></image>
                        </div>
                        <div class="c-t">
                            <text class="c-t-title">{{i.productTitle}}</text>
                            <text class="c-t-t1">{{i.variant}}</text>
                        </div>
                    </div>
                </div>
            </cell>

            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <!--<loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">-->
                <!--<text class="indicator">{{loadingWord}}</text>-->
            <!--</loading>-->
        </list>
    </div>
</template>

<script>
    import star from '../shop/star-item';
    import header from '../shop/witheHeader';
    import card from '../shop/reviewsCard';
    import { baseUrl } from '../../../config/apis';
    import refresher from '../common/refresh';

    export default {
        components: {
            'topic-header': header,
            'refresher': refresher,
            'star': star,
            'card': card
        },
        data () {
            return {
                userList: [],
                pageSize: 6,
                page: 1,
                length: 2,
                isLoading: false
            }
        },
        created () {
            this.init()
            this.$event.on('reviews', parmas => {
                this.init()
            })
        },
        methods: {
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.requestProduct(false)
                }
            },
            loadingDown () {
                this.$refs.refresh.refreshEnd();
                this.isLoading = false;
                this.init();
            },
            init () {
                this.$notice.loading.show();
                this.requestProduct(true);
            },
            requestProduct (isFirst) {
                if (isFirst) {
                    this.page = 1;
                }

                if (this.page > this.length) {
                    this.$nextTick(() => {
                        this.isLoading = false
                    })
                    return
                }
                this.getData(isFirst);
            },
            getData (isfirst) {
                this.$fetch({
                    method: 'GET',
                    url: `${baseUrl}/comment/customer/comment/detail/list/`,
                    header: {
                        needAuth: true
                    },
                    data: {
                        page_size: this.pageSize,
                        page: this.page
                    }
                }).then((res) => {
                    let tmp = res.results
                    this.length = Math.ceil(res.count / this.pageSize);
                    if (isfirst) {
                        this.userList = []
                    }
                    this.userList.push(...tmp)

                    this.$notice.loading.hide()
                    this.page++;
                    this.$nextTick(() => {
                        this.isLoading = false;
                    })
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res.results
                    // })
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },
            openProduct (i) {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: i.productId
                    }
                })
            },
            openWriteReview (i) {
                let order = {
                    id: i.orderId,
                    lines: [
                        {
                            mainImage: i.mainImage,
                            title: i.productTitle,
                            attributes: i.variant,
                            productId: i.productId
                        }
                    ]
                }
                this.$router.open({
                    name: 'write.reviews',
                    type: 'PUSH',
                    params: {
                        order: order,
                        update: 2
                    }
                });
            }

        }
    }
</script>

<style scoped>
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .top{
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
        margin-top: 48px;
    }
    .content{
        /*background-color: gainsboro;*/
        margin-top: 2px;
    }
    .cell{

        padding-bottom: 16px;
    }
    .card-item{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        background-color: #FFFFFF;
        height: 128px;
        border-radius: 8px;
        background-color: rgba(0,0,0,.04);
        margin-left: 32px;
        margin-right: 32px;
        margin-bottom: 32px;
    }
    .c-t{
        flex-direction: column;
        align-items: start;
        justify-content: space-between;
        height: 128px;
        margin-left: 16px;
    }
    .c-t-image{
        width: 128px;
        height: 128px;
    }
    .c-t-title{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        line-height: 38px;
        margin-top: 8px;
        width: 480px;
    }
    .c-t-t1{
        opacity: 0.54;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        margin-bottom: 8px;
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
</style>
