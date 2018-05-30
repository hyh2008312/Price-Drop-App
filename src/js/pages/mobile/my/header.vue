<template>
    <div class="wrapper">
        <div class="left">
            <text class="leftTxt" v-if="leftSign==true" @click="$router.back">&#xe6f6;</text>
            <text class="leftTxt" v-else>{{leftBtn}}</text>

        </div>
        <text class="tlt">{{title}}</text>
        <div class="right">
            <text class="rightTxt" v-if="iconSign==true" @click="setting" >&#xe71e;</text>
            <text class="rightTxt" v-else @click="savedata">{{rightBtn}}</text>
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
            if (this.rightBtn == 'icon') {
                this.iconSign = true
            } else {
                this.iconSign = false
            }

            // this.$notice.toast({message: this.leftBtn})

            if (this.leftBtn == 'icon') {
                this.leftSign = true
            } else {
                this.leftSign = false
            }
        },
        savedata () {
           this.$emit('onsave',1)
        },
        setting () {
           this.$emit('setting',1)
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
    height: 48px;
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
    top: 48px;
    left: 0;
    right: 0;
    height: 112px;
    align-items: center;
    z-index: 101;
    flex-wrap: nowrap;
    flex-direction: row;
    justify-content: space-around;
    background-color: white;
    box-shadow: 0 0 4px 0 rgba(0,0,0,0.12), 0 1px 3px 0 rgba(0,0,0,0.19);
    /*box-shadow: 0 0 4px 0 rgba(0,0,7,0.12);*/
    /*border-bottom-width: 1px;*/
    /*border-bottom-color: #d9d9d9;*/
}
.left{
    font-family: iconfont;
    color: #FFFFFF;
    height: 80px;
    width: 120px;
    position: absolute;
    left: 50px;
    top:30px;
    padding-top: 10px;

}
.tlt{
    color: black;
}
.right {
    font-family: iconfont;
    color: #FFFFFF;
    height: 80px;
    width: 120px;
    position: absolute;
    right: 5px;
    top:28px;
    padding-top: 10px;

}

.leftTxt{
    font-family: iconfont;
    color: black;
    font-size: 32px;
    text-align: left;
}

.rightTxt {
    font-family: iconfont;
    color: #EF8A31;
    font-size: 32px;
    text-align: center;
}
</style>
