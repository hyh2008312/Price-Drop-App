<template>
    <div class="wrappertop">
        <div class="left">
            <text class="leftTxt" v-if="leftSign==true" @click="$router.back">&#xe6f6;</text>
            <text class="leftTxt" v-else>&#xe632;</text>
        </div>
        <!--<text class="tlt">{{title}}</text>-->
        <div class="right">
            <text class="rightTxt" v-if="iconSign==true" @click="openLink" >&#xe700;</text>
        </div>
    </div>
</template>
<script>
export default {
    props: ['title', 'leftBtn', 'rightBtn'],
    data () {
        return {
            leftSign: '',
            iconSign: ''
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
    opacity: 0;
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
    left: 47.5px;
    top:77.5x;
    padding-top: 10px;

}
.right {
    font-family: iconfont;
    color: #FFFFFF;
    height: 80px;
    width: 120px;
    position: absolute;
    right: 5px;
    top:77.5px;
    padding-top: 10px;

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
    font-size: 32px;
    text-align: center;
}
</style>
