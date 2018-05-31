// 这里的后缀都是 .js 因为打包出来的都是js文件，而不是.vue文件，我们要告诉客户端跳转那个js

//  这里的路径和dev.json路径的区别
//  pages里面配置的是路由跳转的地址，也就是dev生成的 dist文件夹下的js bundle路径 从dist/js开始
//  dev.json 的 exports 需要打包的js地址，填入src的需要被打包成js bundle的地址   从src开始

export default {
    'a': {
        title: '路由测试页',
        url: '/pages/mobile/shop/a.js'
    },
    'mobile': {
        url: '/pages/mobile/index.js'
    },
    'mobile.activity': {
        url: '/pages/mobile/activity/index.js'
    },
    'goods.details': {
        url: '/pages/mobile/shop/goodsDetail.js'
    },
    'cut.goods': {
        url: '/pages/mobile/shop/cutGoods.js'
    },
    'my.details': {
        url: '/pages/mobile/my/myDetail.js'
    },
    'my.faq': {
        url: '/pages/mobile/my/myFaq.js'
    },
    'my.faqTmp': {
        url: '/pages/mobile/my/faqTmp.js'
    },
    'my.about': {
        url: '/pages/mobile/my/myAboutUs.js'
    },
    'my.gender': {
        url: '/pages/mobile/my/myGender.js'
    },
    'my.support': {
        url: '/pages/mobile/my/mySupport.js'
    },
    'my.setting': {
        url: '/pages/mobile/my/mySetting.js'
    },
    'order': {
        url: '/pages/mobile/order/index.js'
    },
    'order.success': {
        url: '/pages/mobile/order/paySuccess.js'
    },
    'order.failure': {
        url: '/pages/mobile/order/payFailure.js'
    },
    'order.detail': {
        url: '/pages/mobile/order/payDetail.js'
    },
    'order.confirm': {
        url: '/pages/mobile/order/orderConfirm.js'
    },
    'order.address': {
        url: '/pages/mobile/order/chooseAddress.js'
    },
    'order.address.edit': {
        url: '/pages/mobile/order/editAddress.js'
    },
    'order.address.state': {
        url: '/pages/mobile/order/state.js'
    },
    'order.address.tracking': {
        url: '/pages/mobile/order/trackingPackage.js'
    },
    'drops.cutDetail': {
        url: '/pages/mobile/drops/cutDetail.js'
    },
    'login': {
        url: '/pages/mobile/login/index.js'
    }
}
