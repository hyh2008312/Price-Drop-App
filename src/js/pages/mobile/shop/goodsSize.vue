<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header title="Size Chart" leftBtn="n"  ></topic-header>
        <scroller>
            <div class="tab">
                <div  v-for="t in sizeData.table" @click="tabTo(t)">
                    <text class="tab-text">{{t.name.slice(0, -4)}}</text>
                    <div style="flex-direction: row;align-items: center;justify-content: center;width: 324px">
                        <text class="tab-text1" :class="[activeTab == t.name?'tab-text1-active':'']">{{(t.name).slice(-4)}}</text>
                    </div>

                </div>
            </div>
            <div class="table" v-for="i in sizeData.table" >
                <div v-if="activeTab==i.name">
                    <!--<text>{{i}}</text>-->
                    <div  class="i-row" v-for="(item,index) in i.children">
                        <div class="item">
                            <text class="item-t" >{{item.name}}</text>
                        </div>
                        <div class="i-col" v-for="n in item.value">
                            <div class="block" >
                                <div v-if="index==0" class="item-block" >
                                    <text class="item-0" >{{n.value.slice(0, -4)}}</text>
                                    <text class="item-0" >{{n.value.slice(-4)}}</text>
                                </div>
                                <text v-else class="item-1">{{n.value}}</text>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <div>
                <text class="bottom-text">The Products come from all over the world. Product size may smaller for the local.</text>
            </div>

        </scroller>
    </div>
</template>

<script>
    import header from './witheHeader';
    export default {
        components: {
            'topic-header': header,
        },
        eros: {
            beforeAppear (params, options) {
                if (params) {
                    this.sizeData = params.productSize
                    this.inSize = this.sizeData.table[0]
                    this.activeTab = this.sizeData.table[0].name
                    this.cmSize = this.sizeData.table[1]
                    // this.sizeData1 = params.productSize.table
                }
            }
        },
        name: 'goodSize',
        data () {
            return {
                sizeData: '',
                inSize: '',
                cmSize: '',
                activeTab: ''
            }
        },
        methods: {
            tabTo (i) {
                // this.$notice.alert({
                //     message: i.name
                // })
                this.activeTab = i.name
            }
            // tranString (s) {
            //     this.$notice.alert({
            //         message: s.slice(0, -4)
            //     })
            //
            //     // return
            // }
        }
    }
</script>

<style scoped>
    .blackheader{
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .tab{
        width: 750px;
        flex-direction: row;
        align-items: center;
        justify-content: space-around;
        background-color: white;
        margin-top: 2px;

    }
    .tab-text{
        width: 324px;
        text-align: center;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        font-weight: 900;
        /*background-color: #689de5;*/
        margin:16px 0 4px 0;
    }
    .tab-text1{
        width: 88px;
        text-align: center;
        font-size: 24px;
        /*background-color: #689de5;*/
        padding-bottom: 4px;
        border-bottom-color: transparent;
        border-bottom-style: solid;
        border-bottom-width: 4px;
    }
    .tab-text1-active{
        width: 88px;
        text-align: center;
        font-size: 24px;
        /*background-color: #689de5;*/
        padding-bottom: 4px;
        border-bottom-color: #EF8A31;
        border-bottom-style: solid;
        border-bottom-width: 4px;
    }
    .table{
        border-top-color: rgba(0,0,0,.08);
        border-top-width: 2px;
        border-top-style: solid;
        background-color: white;
    }
    .item-block{
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    .i-row{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .i-col{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        height: 102px;
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,.08);
    }
    .item{
        width: 166px;
        height: 102px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        background-color: rgba(0,0,0,.08);
        border-bottom-width: 2px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,.08);
    }
    .item-t{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .block{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        width: 292px;
    }
    .item-0{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .item-1{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        opacity: 0.56;
    }
    .bottom-text{
        margin: 24px 0 32px 32px;
        font-size: 24px;
        line-height: 36px;
        color: rgba(0,0,0,0.87);
        opacity: 0.56;

    }
</style>
