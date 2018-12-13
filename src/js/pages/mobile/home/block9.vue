<template>
    <div class="wrapper-9">
        <div class="lucky-draw" @click="luckyDraw">
            <div class="ld-img">
                <image  src="bmlocal://assets/home/luckydraw.png" style="width: 338px;height:133px"></image>
            </div>
            <div class="ld-item">

                <div class="ld-word">
                    <text class="ld-w1">Win</text>
                    <text class="ld-w2">Free Gifts</text>
                </div>
                <div class="ld-btn">
                    <div class="ld-bw" ref="btn">
                        <text class="ld-bword">GO</text>
                        <text class="ld-a iconfont">&#xe626;</text>
                    </div>
                </div>

            </div>
        </div>

        <div class="rewards" @click="reward">
            <div class="re-img">
                <image  src="bmlocal://assets/home/rewards.png" style="width: 334px;height:133px"></image>
            </div>
            <div class="ld-item">

                <div class="ld-word">
                    <text class="ld-w1">Earn</text>
                    <text class="ld-w2">Cash & Points</text>
                </div>
                <div class="re-btn" >
                    <div class="re-bw" ref="btn1">
                        <text class="ld-bword">GO</text>
                        <text class="ld-a iconfont">&#xe626;</text>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
    const animation = weex.requireModule('animation')
    export default {
        data () {
            return {
                btnArr: [
                    { ref: 'btn', p: [0, 5], delay: 0 },
                    { ref: 'btn1', p: [0, 5], delay: 0 },
                ]
            }
        },
        created () {
            this.shake()
            this.setTime()
        },
        methods: {
            luckyDraw () {
                this.$emit('luckyDraw')
            },
            reward () {
                this.$emit('reward')
            },
            setTime () {
                setInterval(() => {
                    this.loadingAni()
                }, 500)
            },
            loadingAni () {
                for (let i = 0; i < this.btnArr.length; i++) {
                    this.shake(this.$refs[this.btnArr[i].ref], this.btnArr[i].p[0], this.btnArr[i].p[1]);
                }
            },
            shake (ref, x, y) {
                animation.transition(ref, {
                    styles: {
                        transform: `translate(${x}, ${y}px)`
                    },
                    duration: 100, // ms
                    timingFunction: 'linear',
                    delay: 0 // ms
                }, function () {
                    animation.transition(ref, {
                        styles: {
                            transform: 'translate(0px, 0px)'
                        },
                        duration: 100, // ms
                        timingFunction: 'linear',
                        delay: 0 // ms
                    })
                }.bind(this))
            }
        }
    }
</script>

<style scoped>
    .wrapper-9{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        margin: 0 32px 24px 32px;
        width: 686px;
        height: 133px;
    }
    .iconfont{
        font-family: iconfont;
    }
    .lucky-draw{
        width: 338px;
        height:133px;
        margin-right: 14px;

    }
    .ld-img{
        position: absolute;
        top: 0;
        left: 0;
        width: 338px;
        height:133px;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .ld-item{
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .ld-word{
        flex-direction: column;
        justify-content: start;
        align-items: start;
        margin-top: 28px;
        margin-left: 22px;
    }
    .ld-w1{
        font-size: 32px;
        color: #FFFFFF;
    }
    .ld-w2{
        font-size: 28px;
        color: #FFFFFF;
        font-weight: 700;
    }
    .ld-btn{
        width: 78px;
        height: 78px;
        flex-direction: row;
        justify-content: center;
        align-items: start;
        background-color: #FC6E22;
        border-radius: 100%;
        margin-right: 26px;
        margin-top: 28px;
    }
    .ld-bw{
        width: 70px;
        height: 70px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        border-radius: 100%;
        background-color: #FF8D50;
    }
    .ld-bword{
        margin-top: 6px;
        margin-left:8px;
        font-size: 28px;
        color: white;
    }
    .ld-a{
        margin-top: 5px;
        font-size: 20px;
        color: white;
    }
    .rewards{
        width: 334px;
        height:133px;
    }
    .re-img{
        position: absolute;
        top: 0;
        left: 0;
        width: 334px;
        height:133px;
        border-radius: 16px;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
    }
    .re-btn{
        width: 78px;
        height: 78px;
        flex-direction: row;
        justify-content: center;
        align-items: start;
        background-color: #26A0BA;
        border-radius: 100%;
        margin-right: 26px;
        margin-top: 28px;
    }
    .re-bw{
        width: 70px;
        height: 70px;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        border-radius: 100%;
        background-color: #32bddb;
    }
</style>
