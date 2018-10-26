<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <!--<text>111</text>-->
        <topic-header title="Description" leftBtn="n"  ></topic-header>
        <list class="content">
            <cell >
                <div class="p-s"  v-for="(i, index) in productSpecificationData" :class="[index%2==0?'rowGray':'rowWithe']">
                    <text class="p-s-t1">{{i.name}}</text>
                    <text class="p-s-t2">{{i.content}}</text>
                    <!--goods.productSpecification-->
                </div>
                <div v-for="(i, index) in decData ">
                    <div v-if="i.type=='text'">
                        <text class="bottom-text">{{i.context}}</text>
                    </div>
                    <cimg v-if="i.type=='image'" :imgsrc="i.context"></cimg>
                </div>
            </cell>
        </list>
    </div>
</template>

<script>
    import header from './witheHeader';
    import cimg from './customImg';
    export default {
        components: {
            'topic-header': header,
            'cimg': cimg,
        },
        eros: {
            appeared (params, options) {
                if (params) {
                    // this.$notice.alert({message:params})
                    if (params.dec) {
                        this.decData = params.dec
                        this.productSpecificationData = params.productSpecification
                    }
                }
            }
        },
        data () {
            return {
                decData: '',
                productSpecificationData: ''
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
    }
    .bottom-text{
        margin: 0 30px;
    }
    .p-s{
        flex-direction: row;
        justify-content:start;
        align-items: center;
        margin: 0 32px;
    }
    .p-s-t1{
        width:234px;
        margin-left: 32px;
        font-size: 24px;
        color: rgba(0,0,0,.54);

    }
    .p-s-t2{
        width:234px;
        margin-left: 32px;
        font-size: 24px;
        color: rgba(0,0,0,.54);

    }
    .rowWithe{
        padding: 22px 32px 22px 0 ;
        background-color: white;
    }
    .rowGray{
        padding: 22px 32px 22px 0 ;
        background-color: rgba(0,0,0,.08);
    }
</style>
