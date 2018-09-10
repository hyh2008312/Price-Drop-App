<template>
    <div class="wrapper">
        <topic-header title="My Notifications" leftBtn="icon"  ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div class="mid-cell" >

            <div class="overflow-box" @click="openNew(1,'drop')">
                <div class="overflow-box-content">
                    <div>
                        <div class="dot" v-if="drop.isRead==false"></div>
                        <image src="bmlocal://assets/ic-drop-noti.png" class="icon-img" style=""></image>
                    </div>
                    <div class="word">
                        <div class="title-time">
                            <text class="title">Drops Notifications</text>
                            <text class="time">{{tranDate(drop.created)}}</text>
                        </div>

                        <text class="title-item">{{drop.title||'There is no notification to show. '}}</text>
                    </div>
                </div>

            </div>
            <div class="overflow-box" @click="openNew(2,'order')">
                <div class="overflow-box-content">
                    <div>
                        <div class="dot" v-if="order.isRead==false"></div>
                        <image src="bmlocal://assets/ic-order-noti.png" class="icon-img" style=""></image>
                    </div>
                    <div class="word">
                        <div class="title-time">
                            <text class="title">Orders Notifications</text>
                            <text class="time">{{tranDate(order.created)}}</text>
                        </div>

                        <text class="title-item">{{order.title||'There is no notification to show. '}}</text>
                    </div>
                </div>

            </div>
            <div class="overflow-box" @click="openNew(3,'perk')">
                <div class="overflow-box-content">
                    <div>
                        <div class="dot" v-if="perk.isRead==false"></div>
                        <image src="bmlocal://assets/ic-perks-not.png" class="icon-img" style=""></image>
                    </div>
                    <div class="word">
                        <div class="title-time">
                            <text class="title">Perks Notifications</text>
                            <text class="time">{{tranDate(perk.created)}}</text>
                        </div>

                        <text class="title-item">{{perk.title||'There is no notification to show. '}}</text>
                    </div>
                </div>

            </div>
            <!--<div class="overflow-box" @click="openNew(4,'refund')">-->
                <!--<div class="overflow-box-content">-->
                    <!--<div>-->
                        <!--<div class="dot" v-if="refund.isRead==false"></div>-->
                        <!--<image src="bmlocal://assets/ic-refund-noti.png" class="icon-img" style=""></image>-->
                    <!--</div>-->
                    <!--<div class="word">-->
                        <!--<div class="title-time">-->
                            <!--<text class="title">Refunds Notifications</text>-->
                            <!--<text class="time">{{tranDate(refund.created)}}</text>-->
                        <!--</div>-->

                        <!--<text class="title-item">{{refund.title||'There is no notification to show. '}}</text>-->
                    <!--</div>-->
                <!--</div>-->

            <!--</div>-->
            <div class="overflow-box" @click="openNew(5,'raffle')">
                <div class="overflow-box-content">
                    <div>
                        <div class="dot" v-if="raffle.isRead==false"></div>
                        <image src="bmlocal://assets/ic-perks-not.png" class="icon-img" style=""></image>
                    </div>
                    <div class="word">
                        <div class="title-time">
                            <text class="title">Raffle Draw Notifications</text>
                            <text class="time">{{tranDate(raffle.created)}}</text>
                        </div>

                        <text class="title-item">{{raffle.title||'There is no notification to show. '}}</text>
                    </div>
                </div>

            </div>
        </div>

    </div>
</template>

<script>
    import header from './header';
    import dayjs from 'dayjs';

    export default {
        components: {
            'topic-header': header
        },
        data () {
           return {
               drop: {
                   isRead: true
               },
               order: {
                   isRead: true
               },
               perk: {
                   isRead: true
               },
               refund: {
                   isRead: true
               },
               raffle: {
                   isRead: true
               }
           }
        },
        created () {
            this.$event.on('readNotice', parmas => {
                if (parmas.type == 'drop') {
                    this.drop.isRead = true
                } else if (parmas.type == 'order') {
                    this.order.isRead = true
                } else if (parmas.type == 'perk') {
                    this.perk.isRead = true
                } else if (parmas.type == 'refund') {
                    this.refund.isRead = true
                } else if (parmas.type == 'raffle') {
                    this.raffle.isRead = true
                }
                this.returnPre()
            })
            this.getData()
        },
        methods: {
            returnPre () {
                // if (this.drop == {}) {
                //     this.drop.isRead = true
                // }
                // if (this.order == {}) {
                //     this.order.isRead = true
                // }
                // if (this.perk == {}) {
                //     this.perk.isRead = true
                // }
                // if (this.refund == {}) {
                //     this.refund.isRead = true
                // }
                if (this.drop.isRead == true &&
                     this.order.isRead == true &&
                    this.perk.isRead == true &&
                     this.refund.isRead == true &&
                     this.raffle.isRead == true) {
                    this.$event.emit('read', 1)
                }
            },
            openNew (id, type) {
                this.$router.open({
                    name: 'my.notice.tmp',
                    type: 'PUSH',
                    params: {
                        id: id,
                        type: type
                    }
                })
            }, // notice.list
            getData () {
                this.$fetch({
                    method: 'GET',
                    name: 'notice.list',
                    header: {
                        needAuth: true
                    }

                }).then((res) => {
                    if (Object.keys(res.drop).length !== 0) {
                        this.drop = res.drop
                    }
                    if (Object.keys(res.order).length !== 0) {
                        this.order = res.order
                    }
                    if (Object.keys(res.perk).length !== 0) {
                        this.perk = res.perk
                    }
                    if (Object.keys(res.refund).length !== 0) {
                        this.refund = res.refund
                    }
                    if (Object.keys(res.raffle).length !== 0) {
                        this.raffle = res.raffle
                    }
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            tranDate (str) {
                // this.$notice.alert({
                //     message: str
                // })
                if (str != null) {
                    return dayjs(new Date(str)).format('MMMM DD, YYYY')
                } else {
                }
            }
        }
    }
</script>

<style>
    .wrapper{
        width: 750px;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .mid-cell{
        margin-top: 160px;
        border-radius: 4px;
        /*height: 144px;*/
        background-color: #fff;
    }
    .overflow-box{
        height: 144px;
        flex-direction: row;
        align-items: center;
        border-bottom-width: 1px ;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08) ;

    }
    .overflow-box-content{
        width: 750px;
        margin-left: 32px;
        /*margin-right: px;*/
        flex-direction: row;
        justify-content: flex-start;
    }
    .icon-img{
        width: 112px;
        height: 112px;
    }
    .word{
        overflow: hidden;
        margin-left: 16px;
        margin-top: 18px;

    }
    .title-time{
        width: 560px;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;

    }
    .title{
        font-size: 28px;
        margin-bottom: 8px;
        font-weight: 700;
    }
    .time{
        opacity: 0.38;
        font-family: ProximaNova-Regular;
        font-size: 20px;
    }
    .title-item{
        opacity: 0.38;
        font-family: ProximaNova-Regular;
        font-size: 24px;
        lines:1;
        width: 500px;
        text-overflow:ellipsis;
        color: #000000;
    }
    .dot{
        position: absolute;
        top: 2px;
        left: 90px;
        height: 15px;
        width: 15px;
        border-radius: 50%;
        background-color: red;
    }
    .text-word{
        font-size: 28px;
        line-height: 48px;
        margin-bottom: 16px;
    }
    .content{
        width: 750px;
        text-align: center;
        margin-top: 48px;
        margin-bottom: 48px;
        padding-left: 32px;
        padding-right: 32px;
    }
</style>
