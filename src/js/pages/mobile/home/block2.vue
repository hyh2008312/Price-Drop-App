<template>
    <div class="wrapper">
        <div class="tlt-box" @click="jumpActivity">
            <image class="tlt-image" resize="cover" :src="head.image"></image>
            <div class="tlt-bg">
                <text class="tlt tlt-new">{{head.name}}</text>
                <text class="btn-all">查看全部</text>
            </div>
        </div>
        <scroller class="box" scroll-direction="horizontal" flex-direction="row" show-scrollbar=false>
            <div class="i-good" v-for="i in goods" :key="i.id" @click="jumpWeb(i.productId)">
                <div class="gd-bg">
                    <image class="gd-img" resize="cover" :src="i.mainImage || src"></image>
                </div>
                <text class="gd-tlt">{{i.title}}</text>
                <text class="gd-info">{{i.cutGet? i.cutGet: 0}}人已砍到了1折</text>
                <text class="gd-price">¥{{i.saleUnitPrice}}</text>
                <text class="gd-button">砍价立减</text>
            </div>
        </scroller>
    </div>
</template>
<script>
export default {
    props: ['head'],
    created () {
        this.getActivityProduct()
    },
    data () {
        return {
            goods: [],
            page: 1,
            pageSize: 6,
            src: 'https://cdn.dribbble.com/users/179241/screenshots/1829868/nerfwarrior_dribbble.png'
        }
    },
    methods: {
        getActivityProduct () {
            this.$fetch({
                method: 'GET',
                name: 'product.topic.products',
                data: {
                    id: this.head.id,
                    page: this.page,
                    page_size: this.pageSize
                }
            }).then(data => {
                this.goods = [...data.results]
            }, error => {

            })
        },
        jumpActivity () {
            this.$router.open({
                name: 'mobile.activity',
                type: 'PUSH',
                params: {
                    id: this.head.id,
                    name: this.head.name,
                    imageUrl: this.head.image
                }
            })
        },
        jumpWeb (id) {
            // if (!url) return;
            this.$router.open({
                name: 'goods.details',
                type: 'PUSH',
                params: {
                    id: id
                }
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
    background-color: #fff;
    padding-bottom: 16px;
}

.tlt-box {
    height: 375px;
    width: 750px;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
}

.tlt-bg{
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    width: 750px;
    height: 375px;
    align-items: center;
    justify-content: center;
    background-color: rgba(0,0,0,0.18);
}

.tlt {
    text-align: center;
    font-size: 32px;
    font-weight: 700;
    color: rgba(255,255, 255, 0.87);
}

.tlt-hot {
    color: rgba(255,255,255, 0.87);
}

.btn-all {
    text-align: center;
    font-size: 24px;
    width: 114px;
    line-height: 44px;
    justify-content: center;
    align-items: center;
    margin-top: 28px;
    color: rgba(255,255,255,0.87);
    border-style: solid;
    border-width: 1px;
    border-color: #fff;
}


.tlt-image {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

.box {
    width: 750px;
    height: 532px;
    padding-top: 16px;
    padding-bottom: 32px;
    padding-left: 8px;
    padding-right: 8px;
    flex-direction: row;
}

.i-good {
    padding-left: 8px;
    padding-right: 8px;
    width: 304px;
    align-items: center;
}

.gd-bg {
    height: 288px;
    width: 288px;
    border-radius: 8px;
    overflow: hidden;
    border-width: 1px;
    border-style: solid;
    border-color: rgba(0,0,0,.12);
}

.gd-img {
    height: 286px;
    width: 286px;
}

.gd-tlt {
    font-size: 24px;
    font-weight: 500;
    color: rgba(0,0,0,0.87);
    width: 288px;
    margin-top: 16px;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
    line-height: 34px;
}

.gd-info {
    font-size: 20px;
    width: 286px;
    margin-top: 8px;
    color: #AC0B0B;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
    line-height: 28px;
}

.gd-price {
    font-size: 28px;
    font-weight: 500;
    width: 288px;
    line-height: 40px;
    margin-top: 6px;
    color: #957914;
    overflow: hidden;
    lines: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
}

.gd-button{
    margin-top: 14px;
    font-size: 24px;
    border-style: solid;
    border-width: 2px;
    border-color: #000;
    border-radius: 4px;
    line-height: 46px;
    width: 120px;
    text-align: center;
    justify-content: center;
    align-items: center;
}
</style>
