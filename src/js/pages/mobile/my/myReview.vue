<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header class="top" title="My Reviews" leftBtn="n"  ></topic-header>
        <list class="content" offset-accuracy="10" loadmoreoffset="400">
            <cell class="cell" v-for="i in userList">
                <card :content="i"></card>
            </cell>
            <cell>
                <div class="card-item">
                    <div>
                        <div class="c-t-image" style="width: 160px;height: 160px;background-color: black"></div>
                        <!--<image class="c-t-image"></image>-->
                    </div>
                    <div class="c-t">
                        <text class="c-t-title">Toyota’s Latest is an Entire Mobility Service Platform</text>
                        <text class="c-t-t1">Pink L</text>
                    </div>
                </div>
            </cell>
        </list>
    </div>
</template>

<script>
    import star from '../shop/star-item';
    import header from '../shop/witheHeader';
    import card from '../shop/reviewsCard';
    import { baseUrl } from '../../../config/apis';
    export default {
        components: {
            'topic-header': header,
            'star': star,
            'card': card
        },
        data () {
            return {
                userList: []
            }
        },
        created () {
            this.getData()
        },
        methods: {
            getData () {
                this.$notice.loading.show()
                this.$fetch({
                    method: 'GET',
                    // url: `${baseUrl}/comment/product/comment/list/${id}/`,
                    url: `${baseUrl}/comment/customer/comment/list/`,
                    header: {
                        needAuth: true
                    },
                    data: {
                        id: 99
                    }
                }).then((res) => {
                    this.userList = res
                    this.$notice.loading.hide()
                    // this.$notice.alert({
                    //     message: res
                    // })
                }).catch((res) => {
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: res
                    // })
                })
            }

        }
    }
</script>

<style scoped>
    .top{
        box-shadow: 0 1px 3px 0 rgba(0,0,0,0.12);
    }
    .content{
        /*background-color: #fff;*/
        margin-top: 2px;
    }
    .cell{
        background-color: #fff;
        margin-bottom: 16px;
    }
    .card-item{
        flex-direction: row;
        align-items: center;
        justify-content: start;
        background-color: #FFFFFF;
        margin-top: 16px;
    }
    .c-t{
        flex-direction: column;
        align-items: start;
        justify-content: space-between;
        height: 160px;
    }
    .c-t-image{
        margin: 16px 24px 16px 24px;
    }
    .c-t-title{
        width: 460px;
        font-size: 28px;
        color: rgba(0,0,0,0.87);
        font-weight: 700;
        line-height: 38px;
        margin-top: 8px;
    }
    .c-t-t1{
        opacity: 0.54;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        margin-bottom: 8px;
    }
</style>
