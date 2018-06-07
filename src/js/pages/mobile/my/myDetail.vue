<template>
    <div class="wrapper">
        <topic-header title="Me" leftBtn="icon" rightBtn="SAVE" @onsave="saveData" ref="ref1" ></topic-header>

        <div class="blackheader"></div>
        <div class="mid-cell" @click="pickAndUpload">
            <div class="box-tlt-fir ">
                <div class="box-left-fir">
                    <text class="box-txt">Photo</text>
                </div>
                <div class="box-right">
                    <div class="i-photo-div">
                        <image  class="i-photo" resize="cover"  :src="src"></image>
                    </div>
                    <text class="i-box-fir iconfont">&#xe626;</text>
                </div>

            </div>

            <div class="box-tlt ">
                <div class="box-left">
                    <text class="box-txt">Name</text>
                </div>
                <!--<input class="input" style="tint-color: #ef8a31;" type="text" placeholder="Edit Name" :value="this.name" @input="oninput" />-->

                <div class="box-right">
                    <text class="box-txt-left">Click to Edit</text>

                    <text class="i-box-input iconfont">&#xe626;</text>
                </div>

            </div>

            <div class="box-tlt " @click="openGender">
                <div class="box-left">
                    <text class="box-txt">Gender</text>
                </div>
                <div class="box-right">
                    <text class="box-txt-left" >{{selgender}}</text>
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
                this.src = resData.useravatar
                this.name = resData.username
                if (resData.usergender == 'F') {
                    this.selgender = 'Fale'
                } else {
                    this.selgender = 'Female'
                }
            })
        },
        data () {
           return {
               src: 'http://yanxuan.nosdn.127.net/885e3901d0a3501362530435d76bebb3.jpg',
               name: '',
               gender: '',
               selgender: 'Select Gender',
               defalutgender: ''
           }
        },
        // gender
        methods: {
            pickAndUpload () {
                this.$image.pick({
                    maxCount: 1,
                    imageWidth: '180',
                    allowCrop: true
                }).then(
                        resData => {
                            const pics = resData[0].split('/');
                            const name = pics[pics.length - 1];
                            const params = {
                                type: 'avatar',
                                fileName: name,
                                width: 180,
                                height: 180
                            }
                            this.getPolicy(params, resData)
                        },
                        error => {
                            this.$notice.toast({
                                message: '上传失败:' + error.errorMsg
                            });
                        }
                    );
            },
            getPolicy (params, source) {
                this.$fetch({
                    method: 'POST',
                    name: 'image.qiniu',
                    data: params,
                    header: {
                        needAuth: true
                    }
                }).then(data => {
                    this.$image.upload({
                        url: data.domain, // 自定义图片上传地址，默认上传地址是 eros.native.js 中的 image 地址
                        params: {
                            token: data.token,
                            key: data.name
                        }, // 传递的参数
                        header: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        source: source // 图片路径
                    }).then(resData => {
                            this.src = data.url + '/' + data.name
                        }, error => {
                            console.log(error)
                        })
                }, error => {
                    // 错误回调
                    this.$notice.toast({
                        message: error
                    })
                })
            },

            oninput (event) {
                this.name = event.value;
            },
            opentest (event) {
            },
            saveData () {
                const self = this
                const params = {
                    avatar: this.src,
                    firstName: this.name,
                    gender: this.gender.value
                }
                this.$fetch({
                    method: 'PUT',
                    name: 'user.userprofile',
                    header: {
                       needAuth: true
                    },
                    data: params
                }).then((res) => {
                    this.$notice.toast({
                        message: 'save success'
                    })
                    this.setback(params)
                }).catch((res) => {
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            openGender () {
                this.$router.open({
                    name: 'my.gender',
                    type: 'PUSH'
                })
            },
            setback (par) {
                let self = this
                this.$router.back({
                    length: 1,
                    type: 'PUSH',
                    callback () {
                        // 返回成功回调
                        self.$event.emit('setdetail', par)
                    }
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
    }
    .box-tlt-fir {
        height: 112px;
        border-bottom-width: 1px;
        border-bottom-style: solid;
        border-bottom-color: rgba(0,0,0,0.08);
        flex-direction: row;
        justify-content: space-between;
    }
    .box-left-fir{
        padding-top: 10px;
    }
    .i-box-fir{
        font-size: 20px;
        padding-top: 44px;
        padding-right: 32px;
        padding-left: 16px;
        color: rgba(0,0,0,0.87);
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
        font-size: 28px;
        padding-top: 34px;
        padding-left: 32px;
        color: #333;
    }

    .i-box {
        font-size: 20px;
        padding-top: 34px;
        padding-right: 32px;
        padding-left: 16px;
        color: rgba(0,0,0,0.87);
    }
    .i-box-input {
        font-size: 20px;
        padding-top: 40px;
        padding-right: 32px;
        padding-left: 16px;
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
        width: 510px;
        text-align: right;
        font-size:24px;
        height: 100px;
        margin-left: 40px;
        color: #6a737d;
    }
    .i-photo-div{
        background-color: #1da1f2;
        width: 68px;
        height: 68px;
        margin-top: 25px;
        margin-left: 30px;
        border-radius: 64px;
    }
    .i-photo {
        width: 68px;
        height: 68px;

    }
</style>
