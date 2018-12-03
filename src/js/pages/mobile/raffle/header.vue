<template>
    <div class="wrapper">
        <div class="left">
            <text class="leftTxt" v-if="leftSign==true" @click="$router.back">&#xe632;</text>

            <text class="leftTxt1" v-else @click="$router.back">&#xe6f6;</text>

        </div>
        <text class="tlt">{{title}}</text>
        <div class="right">
            <text class="rightTxt" v-if="iconSign==true" @click="setting" >&#xe71e;</text>
            <text class="rightword " v-else @click="savedata">{{rightBtn}}</text>
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
.wrapper {
    left: 0;
    right: 0;
    height: 112px;
    align-items: center;
    z-index: 101;
    flex-wrap: nowrap;
    flex-direction: row;
    justify-content: space-around;
    background-color: white;
    box-shadow: 0 0 4px 0 rgba(0,0,7,0.12);
}
.left{
    font-family: iconfont;
    color: #FFFFFF;
    height: 80px;
    position: absolute;
    left: 22px;
    top: 26px;
}
.tlt{
    color: black;
    font-size: 32px;
    font-weight: 700;
}
.right {
    font-family: iconfont;
    color: #FFFFFF;
    height: 80px;
    position: absolute;
    right: 32px;
    top:32px;
}

.leftTxt{
    font-family: iconfont;
    color: black;
    font-size: 40px;
    padding: 10px;
    text-align: left;
}
.leftTxt1{
    font-family: iconfont;
    color: black;
    font-size: 30px;
    padding: 15px 10px 10px 10px;
    text-align: left;
    /*background-color: black;*/
}

.rightTxt {
    font-family: iconfont;
    color: #EF8A31;
    font-size: 32px;
    padding: 10px;
    text-align: center;
}
.rightword {
    color: #EF8A31;
    margin-top: 4px;
    font-size: 24px;
    padding: 10px;
    /*line-height: 28px;*/
    text-align: center;
}
</style>
