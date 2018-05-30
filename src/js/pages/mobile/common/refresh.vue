<template>
    <refresh class="u-refresh" @refresh="onrefresh" @pullingdown="onpullingdown" :display="refreshing ? 'show' : 'hide'">
        <image class="loading-bg" resize="cover" src="bmlocal://assets/loading.png"></image>
        <div class="l-txt-box">
            <text ref="lTxt1" class="l-txt l-txt-1">P</text>
            <text ref="lTxt2" class="l-txt l-txt-2">R</text>
            <text ref="lTxt3" class="l-txt l-txt-3">I</text>
            <text ref="lTxt4" class="l-txt l-txt-4">C</text>
            <text ref="lTxt5" class="l-txt l-txt-5">E</text>
            <text ref="lTxt6" class="l-txt l-txt-6">D</text>
            <text ref="lTxt7" class="l-txt l-txt-7">R</text>
            <text ref="lTxt8" class="l-txt l-txt-8">O</text>
            <text ref="lTxt9" class="l-txt l-txt-9">P</text>
        </div>
    </refresh>
</template>
<script>
var animation = weex.requireModule('animation')
export default {
    props: [],
    data () {
        return {
            refreshing: false,
            stopAnimation: false,
            loadingAR: [
                { ref: 'lTxt1', p: [-70, -45], delay: 0 },
                { ref: 'lTxt2', p: [-50, -52], delay: 50 },
                { ref: 'lTxt3', p: [-28, -57], delay: 100 },
                { ref: 'lTxt4', p: [-16, -60], delay: 150 },
                { ref: 'lTxt5', p: [4, -62], delay: 200 },
                { ref: 'lTxt6', p: [-5, -29], delay: 250 },
                { ref: 'lTxt7', p: [15, -35], delay: 300 },
                { ref: 'lTxt8', p: [35, -41], delay: 350 },
                { ref: 'lTxt9', p: [55, -41], delay: 400 }
            ]
        }
    },
    methods: {
        loadingAni () {
            for (var i = 0; i < this.loadingAR.length; i++) {
                this.shake(this.$refs[this.loadingAR[i].ref], this.loadingAR[i].p[0], this.loadingAR[i].p[1], 3, 1, 200, this.loadingAR[i].delay, 1);
            }
        },
        loadingAniDown () {
            for (let i = 0; i < this.loadingAR.length; i++) {
                this.shake(this.$refs[this.loadingAR[i].ref], 0, 0, 0, 0, 500, 0, 0, i == this.loadingAR.length - 1);
            }
        },
        endRefresh (time) {
            setTimeout(() => {
                this.$emit('loadingDown', {
                    status: 'loadingDown'
                })
            }, time)
        },
        shake (_ref, _x, _y, _k, _d, _duration, _delay, opacity, endShake) {
            animation.transition(_ref, {
                styles: {
                    opacity: opacity,
                    transform: 'translate(' + (_x - 0) + 'px,' + (_y - _k * _d) + 'px)'
                },
                duration: _duration | 500, // ms
                timingFunction: 'ease-out',
                delay: _delay | 0 // ms
            }, function () {
                !this.stopAnimation && this.shake(_ref, _x, _y, _k, -1 * _d);
                if (endShake) {
                    this.endRefresh(_duration + 100)
                }
            }.bind(this))
        },
        refreshEnd () {
            this.stopAnimation = false
            this.refreshing = false
            this.$notice.toast(this.refreshing)
        },
        onrefresh (event) {
            this.stopAnimation = false
            this.loadingAni();
            this.refreshing = true;
            setTimeout(() => {
                this.stopAnimation = true
                this.loadingAniDown();
            }, 1000)
        },
        onpullingdown (event) {}
    }
}
</script>
<style scoped>
.u-refresh {
    height: 170px;
    width: 750px;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: center;
}

.loading-bg {
    position: absolute;
    left: 325px;
    margin-top: 80px;
    width: 100px;
    height: 72px;
}

.l-txt-box {
    height: 126px;
    width: 200px;
    overflow: hidden;
}

.l-txt {
    height: 30px;
    width: 30px;
    font-size: 20px;
    position: absolute;
    top: 75px;
    left: 100px;
    color: #EF8A31;
    opacity: 0;
}
</style>
