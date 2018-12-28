<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header title="Reviews" leftBtn="n"  ></topic-header>
        <div class="header">
            <div class="header-n">
                <text class="h-n1">{{points}}</text>
                <text class="h-n2"> / 5</text>
            </div>
            <div class="header-s">
                <div class="h-s-d">
                    <star :points="points"></star>
                </div>
                <div class="h-s-d1">
                    <text class="h-s-t">{{commentNum}} Rating</text>
                </div>
            </div>
        </div>
        <list class="content" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <cell v-for="i in userList">
                <card :content="i"></card>
            </cell>
            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
            <loading v-if="false" class="loading" @loading="onloading" :display="isLoading? 'show': 'hide'">
                <text class="indicator">{{loadingWord}}</text>
            </loading>
        </list>
    </div>
</template>

<script>
    import header from './witheHeader';
    import cimg from './customImg';
    import card from './reviewsCard';
    import star from './star-item';
    import { baseUrl } from '../../../config/apis';
    export default {
        components: {
            'topic-header': header,
            'cimg': cimg,
            'star': star,
            'card': card
        },
        data () {
            return {
                points: '',
                commentNum: '',
                loadingWord: '11111',
                userList: [],
                page: 1,
                pageSize: 4,
                length: 2,
                cardArr: [0, 1],
                isLoading: false
            }
        },
        created () {
            this.init()
        },
        methods: {
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true;
                    this.requestProduct(false)
                }
            },
            onloading () {
                this.isLoading = true
                this.requestProduct(false)
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
                    // url: `${baseUrl}/comment/product/comment/list/${id}/`,
                    url: `${baseUrl}/comment/product/comment/list/`,
                    data: {
                        product_id: 13,
                        page: this.page,
                        page_size: this.pageSize
                    }
                }).then((res) => {

                    this.$notice.loading.hide();

                    this.points = res.results.avgScore
                    this.commentNum = res.results.commentNum

                    let tmp = res.results.list
                    // this.length = Math.ceil(res.count / this.pageSize);

                    if (isfirst) {
                        this.userList = []
                    }

                    this.userList.push(...tmp)

                    this.page++;
                    // this.$nextTick(() => {
                    this.isLoading = false;
                    // });
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.toast({
                    //     message: res
                    // })
                })
            }
        }
    }
</script>

<style scoped>
    .blackheader{
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .content{
        background-color: #fff;
        width: 750px;
        margin-top: 16px;
    }
    .header{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        background-color:#FFFFFF ;
        margin-top: 2px;
        height: 128px;
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
    }
    .header-n{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        width: 178px;
        padding-bottom: 22px;
        padding-top: 22px;
        border-right-width: 2px;
        border-right-style: solid;
        border-right-color: rgba(0,0,0,.08);
        /*background-color: black;*/
    }
    .h-n1{
        font-size: 36px;
        color: #000000;
        font-weight: 700;
        margin-left: 60px;
    }
    .h-n2{
        font-size: 22px;
        color: rgba(0,0,0,.38);
        margin-right: 50px;
    }
    .header-s{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .h-s-d{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-left: 32px;
        margin-right: 16px;
    }
    .h-s-d1{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .h-s-t{
        font-size: 20px;
        color: rgba(0,0,0,.38);
        margin-top: 2px;
    }
    .star-icon{
        width: 24px;
        height: 24px;
        margin-left: 4px;
    }
    .card{
        margin-right: 32px;
        margin-left: 32px;
        padding-top: 34px;
        padding-bottom: 24px;
        border-bottom-width:2px ;
        border-bottom-style: dashed;
        border-bottom-color: rgba(0,0,0,.32);
    }
    .card-top{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .c-t-r{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .r-a{
        border-radius: 50%;
        margin-right: 16px;
    }
    .r-t{
        flex-direction: column;
        justify-content: space-between;
        align-items: start;
    }
    .r-t1{
        font-size: 24px;
        color: #000000;
    }
    .r-t2{
        font-size: 20px;
        color: rgba(0,0,0,.56);
    }
    .card-mid{
        margin-top: 16px;
    }
    .c-mt1{
        font-size: 20px;
        color: rgba(0,0,0,.56);
        margin-bottom: 16px;
    }
    .c-mt2{
        width: 648px;
        lines:3;
        text-overflow:ellipsis ;
        /*background-color: #EF8A31;*/
        font-size: 20px;
    }
    .card-bottom{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-top: 16px;
    }
    .img-div{
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
        margin-right: 16px;
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

