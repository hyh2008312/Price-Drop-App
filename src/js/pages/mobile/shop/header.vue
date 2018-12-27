<template>
    <div class="wrappertop">
        <div class="left" @click="$router.back">
            <text class="leftTxt" v-if="leftSign==true" >&#xe6f6;</text>
            <text class="leftTxt" v-else                >&#xe632;</text>
        </div>
        <!--<text class="tlt">{{title}}</text>-->
        <text class="search-icon"  @click="jumpSearch" >&#xe621;</text>
        <div class="right">
            <text class="rightTxt" v-if="iconSign==true" @click="openLink" >&#xe767;</text>
            <!--<text class="rightTxt" v-if="iconSign==true" @click="openLink" >21312312</text>-->
        </div>
    </div>
</template>
<script>
export default {
    props: ['title', 'leftBtn', 'rightBtn', 'dropGoods'],
    data () {
        return {
            leftSign: '',
            iconSign: '',
            dropGoods: ''
        }
    },
    created () {
        this.editRight()
    },
    methods: {
        editRight () {
            if (this.rightBtn == '1') {
                this.iconSign = true
            } else {
                this.iconSign = false
            }

            // this.$notice.toast({message: this.leftBtn})

            if (this.leftBtn == '1') {
                this.leftSign = true
            } else {
                this.leftSign = false
            }
        },
        jumpSearch () {
            this.$router.open({
                name: 'search',
                type: 'PUSH'
            });
        },
        openLink () {
            this.$emit('open')
        }
    }
}
</script>
<style scoped>
.wrappertop {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: 148px;
    padding-top: 44px;
    z-index: 101;
    flex-wrap: nowrap;
    flex-direction: row;
    justify-content: space-around;
    background-color: #000;
    opacity: 1;
    /*border-bottom-width: 1px;*/
    /*border-bottom-color: #d9d9d9;*/
}
.wrapper {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: 112px;
    padding-top: 44px;
    z-index: 101;
    flex-wrap: nowrap;
    flex-direction: row;
    justify-content: space-around;
    background-color: #000;
    opacity: 1;
    /*border-bottom-width: 1px;*/
    /*border-bottom-color: #d9d9d9;*/
}
.left{
    font-family: iconfont;
    color: #FFFFFF;
    height: 80px;
    width: 120px;
    position: absolute;
    left: 0;
    top:47.5px;
    padding-top: 30px;
    padding-left: 47.5px;
}
.right {
    font-family: iconfont;
    color: #FFFFFF;
    height: 80px;
    width: 120px;
    position: absolute;
    right: 5px;
    top:67.5px;
    padding-top: 10px;
}
.search-icon{
    position: relative;
    top:35px;
    left: 228px;
    font-family: iconfont;
    color: #FFFFFF;
    font-size: 40px;
}

.leftTxt{
    font-family: iconfont;
    color: #FFFFFF;
    font-size: 32px;
    text-align: left;

}

.rightTxt {
    font-family: iconfont;
    color: #FFFFFF;
    font-size: 40px;
    text-align: center;
}
.red-dot{
    width: 24px;
    height: 24px;
    background-color: red;
    position: absolute;
    border-radius:24px ;
    padding-bottom: 3px;
    padding-right: 2px;
    top:0;
    right:25px;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}
</style>
