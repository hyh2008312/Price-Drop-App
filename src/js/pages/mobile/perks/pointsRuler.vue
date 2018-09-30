<template>
    <div class="wrapper">
        <topic-header title="Rules to Know" leftBtn="icon"  ref="ref1" ></topic-header>
        <div class="blackheader"></div>
        <div v-if="type=1" class="overflow-box" >

            <text class="first-word">1. The daily cash bonus will expire after 12:00 PM every day. </text>
            <text class="second-word">Please spend your bonus before it expires!</text>
            <text class="first-word">2. You can apply your cash bonus to your payment at the </text>
            <text class="second-word">checkout page.</text>
            <text class="first-word">3. You will be asked to spend 300 points to unlock your gift </text>
            <text class="second-word">box after the first time.</text>
            <text class="first-word">4. The cash bonus can only be used for shopping, which </text>
            <text class="second-word">cannot be withdrawn.</text>

            <!--<text class="second-word">have to start with {{originalPoints}} points a day again.  </text>-->

        </div>

        <div v-else class="overflow-box" >

            <text class="first-word">1. You can only check-in once a day.</text>
            <text class="first-word">2. You will earn 450 points a day from the 8th day to the 15th</text>
            <text class="second-word">day if you constantly check-in.</text>
            <div style="margin-left: 32px;margin-top: 16px">
                <div class="row" v-for="(i, index) in rowArr">
                    <text class="rowl" v-if="i==7">Day {{i+1}} - Day 15</text>
                    <text :class="[index%2==0?'row1':'row1b']" v-if="" v-else >Day {{i+1}}</text>

                    <text class="row2" v-if="i==7">{{originalPoints+(i-1)*gradientPoints}}</text>

                    <text  :class="[index%2==0?'row1':'row1b']" v-else>{{originalPoints+i*gradientPoints}}</text>
                </div>

            </div>

            <text class="first-word">3. If you didn’t check-in on any particular day, then you will</text>
            <text class="second-word">have to start with {{originalPoints}} points a day again.  </text>

        </div>

    </div>
</template>

<script>
    import header from './header';
    import NewDialog from './newPopup';
    const googleAnalytics = weex.requireModule('GoogleAnalyticsModule');

    export default {
        components: {
            'topic-header': header,
            NewDialog
        },
        eros: {
            appeared (params, options) {
                if (params) {
                    this.originalPoints = params.originalPoints
                    this.gradientPoints = params.gradientPoints
                    this.type = params.type
                }
            }
        },
        data () {
            return {
                card: '',
                type: false,
                rowArr: [0, 1, 2, 3, 4, 5, 6, 7],
                originalPoints: '',
                gradientPoints: ''
            }
        },
        created () {

        },
        methods: {

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
    .overflow-box{
        height:1200px;
        background-color: #FFE9D5;
        margin-top: 160px
    }
    .first-word{
        margin-left: 32px;
        margin-top: 32px;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
    }
    .second-word{
        margin-left: 58px;
        margin-top: 8px;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        letter-spacing: 0;
    }
    .row{
        flex-direction: row;
    }
    .row1{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        background-color: #FFF6ED;
        padding: 18px 132px;
        border-bottom-width:2px ;
        border-bottom-style: solid;
        border-bottom-color:#FFD9B7;

        border-right-width:2px ;
        border-right-style: solid;
        border-right-color:#FFD9B7;
    }
    .row1b{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        background-color: #FFFFFF;
        padding: 18px 132px;
        border-bottom-width:2px ;
        border-bottom-style: solid;
        border-bottom-color:#FFD9B7;

        border-right-width:2px ;
        border-right-style: solid;
        border-right-color:#FFD9B7;
    }
    .rowl{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        background-color: #FFFFFF;
        padding: 18px 85px;
        border-bottom-width:2px ;
        border-bottom-style: solid;
        border-bottom-color:#FFD9B7;

        border-right-width:2px ;
        border-right-style: solid;
        border-right-color:#FFD9B7;
    }
    .row2{
        font-size: 24px;
        color: rgba(0,0,0,0.87);
        background-color: #FFFFFF;
        padding: 18px 132px;
        border-bottom-width:2px ;
        border-bottom-style: solid;
        border-bottom-color:#FFD9B7;
    }
</style>
