<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header title="My Reviews" leftBtn="n" rightBtn="Submit" ></topic-header>
        <div class="overflow-top">
            <div class="top">
                <div class="g-img">
                    <!--<image></image>-->
                    <div style="background-color: black;width: 160px;height: 160px"></div>
                </div>
                <div class="g-t">
                    <text class="g-t1">Toyota’s Latest is an Entire Mobility Service Platform</text>
                    <text class="g-t2">Pink L</text>
                </div>
            </div>
        </div>
        <div class="overflow-bottom">
            <div class="bottom">
                <div class="b-h">
                    <text class="b-h-t">Product Rating</text>
                    <div class="star-div">
                        <!--<image v-for="i in grayStarArr" class="star-icon" @click="selStar(i)" src="bmlocal://assets/star/0.0.png"></image>-->
                        <!--<image v-for="i in starArr" class="star-icon"  src="bmlocal://assets/star/1.0.png"></image>-->
                        <image   v-for="(i,index) in starArr" class="star-icon" @click="selStar(index)"  :src="'bmlocal://assets/star/'+ i +'.0.png'"></image>
                    </div>
                </div>
                <div class="b-ta">
                    <textarea class="text-review" name="" @input="oninput" @change="onchange" cols="30" rows="8" maxlength="10" placeholder="Write your review here…"></textarea>
                    <!--<text>{{imgSrc}}</text>-->
                    <div class="overflow-add-img" >
                        <div style="width: 686px; flex-direction: row;justify-content: start;align-items: center;flex-wrap: wrap;">
                            <div class="add-img"  v-for="(i,index) in localImg">
                                    <div class="i-photo-div"   :key="index" >
                                            <image  class="i-photo" resize="cover"  :src="i" ></image>
                                        <!--<div  class="i-photo" style="background-color: #EF8A31"></div>-->
                                    </div>
                                <text class="i-close iconfont" @click="delImg(i, index)" >&#xe632;</text>
                            </div>
                            <div class="add-img">
                                <div class="b-ta-a" @click="pickAndUpload">
                                    <text class="add-photo iconfont">&#xe75a;</text>
                                    <text class="add-txt">Add Photo</text>

                                </div>
                            </div>
                        </div>
                    </div>


                </div>
            </div>

        </div>
    </div>
</template>

<script>
    import header from './witheHeader';
    export default {
        components: {
            'topic-header': header
            // 'cimg': cimg,
            // 'star': star
        },
        name: 'writeReview',
        data () {
            return {
                starArr: [0, 0, 0, 0, 0],
                src: 'https://d1vs5fqeka2glf.cloudfront.net/1e/9a/1e5b60b752dc142345b4be1d34463a9a.jpg',
                imgSrc: [],
                localImg: [],
                imgArrLength: 0,
                tmpLength: 0
                // grayStarArr: [0, 1, 2, 3, 4],
                // star: 'bmlocal://assets/star/1.0.png',
                // gstar: 'bmlocal://assets/star/0.0.png',
                // g: 0
            }
        },
        methods: {
            selStar (i) {
                const tmp = i
                const tmpArr = this.starArr
                for (let j = 0; j < tmpArr.length; j++) {
                    if (j <= tmp) {
                        if (tmpArr[j] == 0) {
                            tmpArr[j] = 1
                        } else {
                            for (let k = 0; k < tmpArr.length; k++) {
                               if (tmp < k) {
                                   tmpArr[k] = 0
                               }
                            } // todo 只选择1星的情况下不能取消 还需要处理
                        }
                    }
                }
                this.$nextTick(() => {
                    this.starArr = [...tmpArr];
                })
                // this.$notice.alert({
                //     message: this.starArr
                // })
                // if (this.g == 0) {
                //     this.g = 1
                // } else {
                //     this.g = 0
                // }
            },
            pickAndUpload () {
                this.imgSrc = [];
                this.$image.pick({
                    maxCount: 6,
                    imageWidth: '180',
                    allowCrop: true
                }).then(
                    resData => {
                        const img = this.handlerArr(resData)
                        this.localImg = []
                        this.imgArrLength = img.length

                        for (let i = 0; i < img.length; i++) {
                            this.getPolicy(img[i], resData)
                            this.localImg.push(resData[i])
                                // this.$notice.alert({
                                //     message:
                                // });
                        }
                    },
                    error => {
                        this.$notice.toast({
                            message: 'Failed to save. Please try again.:' + error.errorMsg
                        });
                    }
                );
            },
            handlerArr (pics) {
                let tmp = [];
                let nameArr = [];
                for (let i = 0; i < pics.length; i++) {
                    tmp.push(pics[i].split('/'))
                }
                for (let j = 0; j < tmp.length; j++) {
                    for (let k = 0; k < tmp[j].length; k++) {
                        if (k == (tmp[j].length - 1)) {
                            this.imgSrc.push({
                                name: tmp[j][k],
                                src: ''
                            })
                            nameArr.push(
                                {
                                    type: 'avatar',
                                    fileName: tmp[j][k],
                                    width: 180,
                                    height: 180
                                }
                            )
                        }
                    }
                }
                return nameArr
            },
            getPolicy (params, source) {
                this.$fetch({
                    method: 'POST',
                    name: 'image.qiniu',
                    data: params,
                    header: {
                        needAuth: true
                    }
                }).then((data) => {
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
                        if (data) {
                            this.tmpLength++
                            for (let i = 0; i < this.imgSrc.length; i++) {
                                this.imgSrc[i].src = data.url + '/' + data.name
                            }
                        }
                        if (this.tmpLength == this.imgArrLength) {
                            // this.imgSrc.reverse()
                            // this.tmp = [...this.imgSrc]
                            this.$notice.alert({
                                message: this.imgSrc
                            });
                        }
                        // this.$notice.alert({
                        //     message: resData
                        // });
                    }).catch((res) => {
                        // this.$notice.toast({
                        //     message: res
                        // });
                    })
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // });
                })
            },
            delImg (item, index) {
                let tmp  = this.localImg
                tmp.splice(index, 1)
                this.$nextTick(() => {
                    this.localImg = [...tmp]
                });
                // this.localImg.splice(index, 1)
                // this.$notice.alert({
                //     message: this.localImg
                // });

            }
        }
    }
</script>

<style scoped>
    .iconfont{
        font-family: iconfont;
    }
    .blackheader{
        width: 750px;
        height: 48px;
        background-color: black;
    }
    .overflow-top{
        margin-top: 2px;
        width: 750px;
        background-color: #fff;
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }
    .top{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        margin-left: 32px;
        margin-right: 32px;
        margin-top: 16px;
        margin-bottom: 16px;
        /*background-color: #fff;*/
    }
    .g-img{
        box-shadow: 0 1px 1px 0 rgba(0,0,0,0.12);
        border-radius: 8px;
    }
    .g-t{
        flex-direction: column;
        justify-content: space-between;
        align-items: start;
        height: 140px;
        margin-left: 24px;
    }
    .g-t1{
        font-size: 28px;
        color: rgba(0,0,0,0.87);
        line-height: 38px;
        font-weight: 700;
        width: 490px;
    }
    .g-t2{
        font-size: 24px;
        color: rgba(0,0,0,.87);
        opacity: .54;
    }
    .overflow-bottom{
        margin-top: 16px;
        width: 750px;
        background-color: #fff;
    }
    .bottom{
        margin-left: 32px;
        margin-right: 32px;
        /*margin-top: 16px;*/
        margin-bottom: 16px;
    }
    .b-h{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        height: 112px;
        border-bottom-width: 2px;
        border-bottom-color: rgba(0,0,0,.32);
        border-bottom-style: dashed;
    }
    .b-h-t{
        font-size: 24px;
        color: #000000;
        margin-right: 32px;
    }
    .star-div{
        flex-direction: row;
        justify-content: start;
        align-items: center;
    }
    .star-icon{
        width: 50px;
        height: 50px;
        padding: 5px;
        margin-left: 10px;
        /*background-color: black;*/
    }
    .b-ta{
        margin-top: 32px;
    }
    .text-review{
        font-size: 28px;
        color: black;
        /*background-color: black;*/
    }
    .b-ta-a{
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 128px;
        height: 128px;
        border-style: dashed;
        border-color:rgba(0,0,0,.34);
        border-width: 2px;
        border-radius: 8px;
    }

    .overflow-add-img{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        flex-wrap: wrap;
        width: 686px;
        /*height: 160px;*/
    }
    .add-img{
        flex-direction: row;
        justify-content: start;
        align-items: center;
        height: 160px;
        /*width: 750px;*/
    }
    .add-photo{
        font-size: 40px;
        color: rgba(0,0,0,.44);
        margin-bottom: 16px;
    }
    .add-txt{
        font-size: 22px;
        color: rgba(0,0,0,.44);
    }
    .i-photo-div{
        border-radius: 8px;
        margin-right: 16px;
    }
    .i-close{
        width: 32px;
        height: 32px;
        color: whitesmoke;
        font-size: 25px;
        text-align: center;
        line-height: 32px;
        background-color: black;
        border-radius: 50%;
        position: absolute;
        top:0;
        right: 4px;

    }
    .i-photo{
        width: 128px;
        height: 128px;
    }
</style>
