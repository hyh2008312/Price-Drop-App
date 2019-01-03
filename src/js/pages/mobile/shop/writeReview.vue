<template>
    <div class="wrapper">
        <div class="blackheader"></div>
        <topic-header title="My Reviews" leftBtn="n"  :rightBtn="rightBtnWord" v-on:change="changeBtn($event) "></topic-header>
        <div class="overflow-top" @click="openProduct">
            <div class="top">
                <div class="g-img">
                    <image style="width: 160px;height: 160px" :src="product.img"></image>
                    <!--<div style="background-color: black;width: 160px;height: 160px"></div>-->
                </div>
                <div class="g-t">
                    <text class="g-t1">{{product.title}}</text>
                    <!--<text class="g-t1">Toyota’s Latest is an Entire Mobility Service Platform</text>-->
                    <text class="g-t2">{{product.variant}}</text>
                    <!--<text class="g-t2">Pink L</text>-->
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
                    <!--<textarea class="text-review"   disabled="true" @input="oninput" @change="onchange" cols="30" rows="8" maxlength="240" placeholder="Write your review here…" :value="content"></textarea>-->
                    <textarea class="text-review"   :disabled="textAreaS" @input="oninput" @change="onchange" cols="30" rows="8" maxlength="240" placeholder="Write your review here…" :value="content"></textarea>
                    <!--<text>{{imgSrc}}</text>-->
                    <div class="overflow-add-img" >
                        <div style="width: 686px; flex-direction: row;justify-content: start;align-items: center;flex-wrap: wrap;">
                            <div class="add-img"  v-for="(i,index) in imgSrc1">
                                    <div class="i-photo-div"   :key="index" >
                                            <image  class="i-photo" resize="cover"  :src="i" ></image>
                                        <!--<div  class="i-photo" style="background-color: #EF8A31"></div>-->
                                    </div>
                                <text class="i-close iconfont" @click="delImg(i, index)" v-if="rightBtnWord == 'Submit'">&#xe632;</text>
                            </div>
                            <div class="add-img" v-if="imgSrc1.length<6">
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
    import { baseUrl } from '../../../config/apis';
    export default {
        components: {
            'topic-header': header
            // 'cimg': cimg,
            // 'star': star
        },
        eros: {
            beforeAppear (params) {
                this.order_id = params.order.id
                this.product_id = params.order.lines[0].productId
                this.product.title = params.order.lines[0].title
                this.product.variant = params.order.lines[0].attributes
                this.product.img = params.order.lines[0].mainImage
                this.status = params.update

                if (params.update == 2) { // 2: 修改评论
                    this.getReviewData()
                }
                // this.$notice.alert({
                //     message: params.order.id
                // })
            }
        },
        name: 'writeReview',
        data () {
            return {
                starArr: [0, 0, 0, 0, 0],
                status: 1,   // 1: 新建评论 2: 修改评论
                imgSrc: [],   // 每一次选择的图片
                imgSrc1: [],   // 最终传给后端的图片
                imgSrc1Tag: true,
                tmpImg: [],   // 本地处理
                localImg: [], // 本地的图片
                imgSign: [],  //  后端返回的 token 和domain
                tmpLength: 0,
                product: {
                    title: '',
                    variant: '',
                    img: ''
                },
                content: '',
                order_id: '',
                product_id: '',
                product_score: '',
                updateData: '',
                rightBtnWord: 'Submit',
                commentId: '',
                textAreaS: false,
                justOne: true
            }
        },
        methods: {
            addReview () {
                this.$notice.loading.show()
                this.$fetch({
                    method: 'POST',
                    name: 'comment.comment.add',
                    data: {
                        order_id: this.order_id,
                        product_id: this.product_id,
                        product_score: parseInt(this.product_score),
                        message: this.content,
                        upload_image: this.imgSrc1,
                        variant: this.product.variant,
                        product_title: this.product.title
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    if (res.result == 'success') {
                        this.$notice.loading.hide()

                        this.$notice.toast({
                            message: 'save success'
                        })
                        this.$router.back()
                    }
                }).catch((res) => {
                    this.$notice.loading.hide()
                    this.$notice.toast({
                        message: res
                    })
                })
            },
            editReview () {
                this.$notice.loading.show()
                this.$fetch({
                    method: 'PUT',
                    name: 'comment.comment.update',
                    data: {
                        id: this.commentId,
                        product_score: parseInt(this.product_score),
                        message: this.content,
                        upload_image: this.imgSrc1,
                        variant: this.product.variant,
                        product_title: this.product.title
                    },
                    header: {
                        needAuth: true
                    }
                }).then((res) => {
                    // this.$notice.alert({
                    //     message: res
                    // })
                    if (res.result == 'success') {
                        this.$notice.loading.hide()

                        this.$notice.toast({
                            message: 'save success'
                        })
                        this.$router.back()
                    }
                }).catch((res) => {
                    this.$notice.loading.hide()

                    this.$notice.toast({
                        message: res
                    })
                })
            },
            oninput (e) {
                this.content = e.value
            },
            selStar (i) {
                if (this.rightBtnWord == 'Edit') {
                    return
                }
                const tmp = i
                const tmpArr = this.starArr
                let sign = false
                for (let j = 0; j < tmpArr.length; j++) {
                    sign = true
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
                let scoreArr = [...tmpArr]
                for (let n = 0; n < scoreArr.length; n++) {
                    if (scoreArr[n] == 0) {
                        scoreArr.splice(n, scoreArr.length - 1)
                    }
                }
                this.product_score = scoreArr.length
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
                if (this.localImg.length == 0) {
                    this.$image.pick({
                        maxCount: 6 ,
                        imageWidth: '180',
                        allowCrop: true
                    }).then(
                        resData => {
                            this.tmpImg = this.handlerArr(resData)
                            const img = this.handlerArr(resData)
                            this.localImg = []
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
                } else {
                    this.$image.pick({
                        maxCount: 6 - this.imgSrc1.length,
                        imageWidth: '180',
                        allowCrop: true
                    }).then(
                        resData => {
                            const img = this.handlerArr(resData)
                            this.imgSrc = []
                            this.imgSign = []
                            for (let i = 0; i < img.length; i++) {
                                // this.$notice.alert({
                                //     message: img[0]
                                // });
                                this.getPolicy(img[i], resData)
                                this.localImg.push(resData[i])
                                this.tmpImg.push(img[i])
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
                }

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
                            // this.imgSrc.push({
                            //     name: tmp[j][k],
                            //     src: ''
                            // })
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
                this.$notice.loading.show();
                this.$fetch({
                    method: 'POST',
                    name: 'image.qiniu',
                    data: params,
                    header: {
                        needAuth: true
                    }
                }).then((data) => {
                    // this.$notice.alert({
                    //     message: this.tmpImg
                    // });
                    // this.$notice.loading.hide();
                    // return
                    if (data) {
                        this.imgSrc.push(data.url + '/' + data.name)
                        this.imgSign.push(data)
                    }
                    this.imgSrc1Tag = true
                    if (this.imgSign.length > 0) {
                        for (let i = 0; i < this.imgSign.length; i++) {
                            this.getImg(this.imgSign[i], source[i])
                        }
                    }
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // });
                })
            },
            getImg (data, source) {
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
                    if (this.imgSrc1Tag) {
                        this.onSuccess()
                        this.imgSrc1Tag = false
                    }
                    this.$notice.loading.hide();
                    // this.$notice.alert({
                    //     message: resData
                    // });
                }).catch((res) => {
                    // this.$notice.toast({
                    //     message: res
                    // });
                })
            },
            onSuccess () {
                if (this.imgSrc1.length == 0) {
                    this.imgSrc1 = [...this.imgSrc]
                } else {
                    for (let i = 0; i < this.imgSrc.length; i++) {
                        this.imgSrc1.push(this.imgSrc[i])
                    }
                }
            },
            delImg (item, index) {
                let tmp1  = this.imgSrc1
                tmp1.splice(index, 1)
                this.$nextTick(() => {
                    this.imgSrc1 = [...tmp1]
                });
            },
            getReviewData () {
                this.$notice.loading.show()
                this.$fetch({
                    method: 'GET',
                    // url: `${baseUrl}/comment/product/comment/list/${id}/`,
                    url: `${baseUrl}/comment/customer/comment/list/`,
                    header: {
                        needAuth: true
                    },
                    data: {
                        order_id: this.order_id
                    }
                }).then((res) => {
                    this.updateData = res
                    this.commentId = res.id
                    this.rightBtnWord = 'Edit'
                    this.textAreaS = true
                    this.$notice.loading.hide()
                    if (this.justOne) {
                        this.product_score = parseInt(this.updateData.productScores)
                        this.content = this.updateData.message
                        this.imgSrc1 = this.updateData.uploadImages
                        for (let i = 0; i < this.product_score; i++) {
                            this.starArr.unshift(1)
                            this.starArr.pop()
                        }
                        this.justOne = false
                    }
                    // this.$notice.alert({
                    //     message: res
                    // })
                }).catch((res) => {
                    this.$notice.loading.hide();
                    this.$notice.alert({
                        message: res
                    })
                })
            },
            changeBtn (p) {
                if (p === 2) {
                    if (this.status == 1) {
                        this.addReview()
                    } else if (this.status == 2) {
                        this.editReview()
                    }
                } else {
                    this.rightBtnWord = 'Submit'
                    this.textAreaS = false
                    // this.rightBtnWord = 'Edit';
                    // this.getSelectStatus();
                    // this.countPrice();
                }
            },
            openProduct (i) {
                this.$router.open({
                    name: 'goods.details',
                    type: 'PUSH',
                    params: {
                        id: this.product_id
                    }
                })
            },

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
