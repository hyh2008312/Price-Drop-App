<template>
    <div class="wrapper">
        <topic-header title="Me" leftBtn="⬅️" rightBtn="SAVE" ref="ref1" ></topic-header>

        <div class="blackheader">

        </div>
        <div class="mid-cell" @click="pickAndUpload">
            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt">Photo</text>
                </div>
                <div class="box-right">
                    <div class="i-photo-div">
                        <image  class="i-photo" resize="cover" src="http://yanxuan.nosdn.127.net/885e3901d0a3501362530435d76bebb3.jpg"></image>
                    </div>
                    <text class="i-box iconfont">&#xe626;</text>
                </div>

            </div>

            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt">Name</text>
                </div>
                <input class="input" style="tint-color: #ef8a31;" type="text" placeholder="Click to Edit..." value="" />

                <div class="box-right">
                    <text class="i-box iconfont">&#xe626;</text>

                    <!--<text class="box-txt-left">Click to Edit</text>-->

                </div>

            </div>

            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt">Gender</text>
                </div>
                <div class="box-right">
                    <text class="box-txt-left">Select Gender</text>
                    <text class="i-box iconfont">&#xe626;</text>
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
        name: 'myDetail',
        methods: {
            pickAndUpload () {
                this.$image.pickAndUpload({
                        url: '',
                        maxCount: 3,
                        imageWidth: 1000,
                        allowCrop: true,
                        params: {},
                        header: {}
                    }).then(
                        resData => {
                            this.$notice.toast({
                                message: '上传成功'
                            });
                            console.log(resData);
                        },
                        error => {
                            this.$notice.toast({
                                message: '上传失败:' + error.errorMsg
                            });
                        }
                    );
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
        border-bottom-width: 3px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08);
        flex-direction: row;
        justify-content: space-between;
    }

    .box-txt-icon {
        font-family: iconfont;
        font-size: 40px;
        padding-top: 34px;
        padding-left: 32px;
        color: #ef8a31;
    }
    .box-txt-left{
        font-family: ProximaNova-Regular;
        padding-top: 30px;
        opacity: 0.54;
        font-size: 24px;
        color: rgba(0,0,0,0.87);
    }
    .box-txt {
        font-family: iconfont;
        font-size: 30px;
        padding-top: 34px;
        padding-left: 32px;
        color: #333;
    }

    .i-box {
        font-size: 20px;
        padding-top: 34px;
        padding-right: 32px;
        color: rgba(0,0,0,0.87);
    }
    .mid-cell{
        margin-top: 160px;
        border-radius: 4px;
        background-color: #fff;
    }
    .box-left{
        flex-direction: row;
        justify-content: space-between;
    }
    .box-right{
        flex-direction: row;
        justify-content: space-between;
    }
    .input {
        width: 550px;
        height: 100px;
        margin-left: 40px;
        color: #6a737d;
    }
    .i-photo-div{
        background-color: #1da1f2;
        width: 58px;
        height: 58px;
        margin-top: 15px;
        margin-left: 30px;
        border-radius: 64px;
    }
    .i-photo {
        width: 128px;
        height: 128px;

    }
</style>
