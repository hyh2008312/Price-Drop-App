<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <!--<text>111</text>-->
        <topic-header title="Description" leftBtn="n"  ></topic-header>
        <list class="content">
            <cell >
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
                    }
                }
            }
        },
        data () {
            return {
                decData: ''
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
</style>
