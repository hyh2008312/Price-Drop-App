<template>
    <div class="wrapper">
        <topic-header title="Name" leftBtn="icon" rightBtn="Confirm" @onsave="saveData" ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div class="mid-cell" >

            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt">FirstName</text>
                </div>

                <input class="input" style="tint-color: #ef8a31;" type="text" placeholder="Edit FirstName" :value="this.fname" @input="oninput" />

                <div class="box-right">

                </div>

            </div>

            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt">LastName</text>
                </div>

                <input class="input" style="tint-color: #ef8a31;" type="text" placeholder="Edit LastName" :value="this.lname" @input="oninputl" />

                <div class="box-right">

                    <!--<text class="i-box-input iconfont">&#xe626;</text>-->
                </div>

            </div>

        </div>
    </div>
</template>

<script>
    import header from './header';

    export default {
        components: {
            'topic-header': header
        },
        name: 'myName',
        eros: {
            backAppeared (params, options) {
                if (params) {
                    this.gender = params.id
                    this.selgender = params.id.title
                }
            }
        },
        created () {
            this.$router.getParams().then(resData => {
                this.fname = resData.fname
                this.lname = resData.lname
            })
        },
        data () {
            return {
                fname: '',
                lname: '',
            }
        },
        // gender
        methods: {

            oninput (event) {
                this.fname = event.value;
            },
            oninputl (event) {
                this.lname = event.value;
            },
            opentest (event) {
            },
            saveData () {
                this.$notice.toast({
                    message: 111
                })
                const self = this
                const params = {
                    fname: this.fname,
                    lname: this.lname
                }
                this.$router.back()
                this.$router.setBackParams({
                    name: params
                })
            }
        }
    }
</script>

<style>
    .iconfont{
        font-family: iconfont;
    }
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .box-tlt {
        height: 96px;
        border-bottom-width: 1px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08);
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .box-txt {
        font-size: 28px;
        padding-left: 32px;
        color: #333;
    }
    .mid-cell{
        margin-top: 160px;
        border-radius: 4px;
        background-color: #fff;
    }
    .box-left{
        flex-direction: column;
        justify-content: space-between;
        align-items: center;

    }
    .box-right{
        flex-direction: column;
        justify-content: space-between;
        align-items: center;

    }
    .input {
        width: 510px;
        text-align: right;
        font-size:24px;
        height: 80px;
        line-height: 5px;
        margin-right: 40px;
        color: #6a737d;
    }
</style>
