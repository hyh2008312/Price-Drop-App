<template>
    <div  class="wrapper">
        <div class="state"></div>
        <div class="header">
            <text class="close iconfont" @click="$router.back();" >&#xe6f6;</text>
            <text class="header-word" >{{'My Drops'}}</text>
            <text class=" iconfont" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</text>
        </div>
        <!--<tab class="top-tab" v-if="type == 'friend'"  @tabTo="onTabTo" :items="tabsItems" :indexKey="activeTab"></tab>-->
        <list class="main-list" ref="list" offset-accuracy="10" loadmoreoffset="400" @loadmore="onLoadingMore">
            <!--<refresher class="gd-bg-gray" ref="refresh" :key="1" @loadingDown="loadingDown"></refresher>-->

            <cell v-for="(i,index) in myDropList"  >
                <div class="overflow-card" :class="[index==myDropList.length-1 ?'mg-b-20':'',]">
                    <card  :content="i" :activeTab="'my'" ></card>
                </div>
            </cell>

            <!--<cell v-for="(i,index) in friendDropList" v-if="type=='friend'&&activeTab=='help'"  >-->
                <!--<div class="overflow-card" :class="[index==friendDropList.length-1 ?'mg-b-20':'',]">-->
                    <!--<card  :content="i" :activeTab="type" ></card>-->
                <!--</div>-->
            <!--</cell>-->

            <!--<cell v-for="(i,index) in friendDropList" v-if="type=='friend'&&activeTab=='helped'"  >-->
                <!--<div class="overflow-card" :class="[index==friendDropList.length-1 ?'mg-b-20':'',]">-->
                    <!--<card  :content="i" :activeTab="type" ></card>-->
                <!--</div>-->
            <!--</cell>-->

            <cell class="loading" v-if="isLoading">
                <image class="loading-icon" src="bmlocal://assets/loading.gif"></image>
            </cell>
        </list>
    </div>
</template>

<script>
    import tab from './moreTab';
    import card from './card';
    import refresher from '../common/refresh';
    export default {
        name: "moreDropList",
        props:[''],
        components: {
            'refresher': refresher,
            tab,card
        },
        data(){
            return {
                type: '',
                tabsItems: [{
                    name: 'Looking for Help',
                    key: 'help'
                }, {
                    name: 'Successfully Helped',
                    key: 'helped'
                }],
                activeTab: 'help',
                myDropList: [],
                friendDropList: [],
                isLoading: false,
                page: 1,
                length: 2,
                pageSize: 3
            }
        },
        created () {
            this.requestProduct(true);
        },
        methods :{
            onLoadingMore () {
                if (!this.isLoading) {
                    this.isLoading = true
                    this.requestProduct(false)
                }
            },
            loadingDown () {
                this.requestProduct(true)
                this.$notice.loading.show();
                this.isLoading = false;
            },
            requestProduct(isFirst){
                if (isFirst) {
                    this.page = 1;
                }
                if (this.page > this.length) {
                    this.$nextTick(() => {
                        this.isLoading = false
                    })
                    return
                }
                this.getMyDropList(isFirst);
            },
            getMyDropList (isFirst) {
                this.$fetch({
                    method: 'GET',
                    name: 'drops.list',
                    data: {
                        page: this.page,
                        page_size: this.pageSize
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    if (res.count == 0) {
                        this.length = 2;
                    } else {
                        this.length = Math.ceil(res.count / this.pageSize);
                    }
                    if (isFirst) {
                        this.myDropList = [];
                    }

                    this.myDropList.push(...res.results)
                    this.page++;
                    this.isLoading = false;

                    this.$notice.loading.hide();
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            },


            onTabTo (e) {
                this.activeTab = e.data.key
            },
        }
    }
</script>

<style scoped>
    .iconfont{
        font-family: iconfont;
    }
    .state {
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .header{
        background-color: white;
        height: 112px;
        /*margin-bottom: 32px;*/
        box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
    }
    .close{
        padding-left: 32px;
    }
    .header-word{
        font-size: 32px;
        font-weight: 700;
        /*width: 474px;*/
        color: black;
        text-align: center;
        line-height: 64px;
        /*margin-left:202px;*/
    }
    .main-list {
        width: 750px;
        background-color: #D1C8E8;
        /*padding-bottom: 20px;*/
        /*margin-bottom: 20px;*/
        margin-top: 2px;
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
    .top-tab{
        /*margin-top:-10px;*/
    }
    .overflow-card{
        width: 686px;
        margin-left: 32px;
        margin-top: 24px;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .mg-b-20{
        margin-bottom: 20px;
    }
</style>
