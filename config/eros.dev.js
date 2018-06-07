// appBoard，mediator 不建议进行修改 如果修改了 也请对应修改
module.exports = {
    'eslint': false,
    'webpackWarnings': false,
    'exports': [
        // appBoard
        'js/config/index.js',
        // mediator
        'js/mediator/index.vue',
        // home
        'js/pages/mobile/index.vue',
        'js/pages/mobile/home/index.vue',
        'js/pages/mobile/drops/index.vue',
        'js/pages/mobile/drops/cutDetail.vue',
        // 'js/pages/mobile/shop/index.vue',
        'js/pages/mobile/my/index.vue',
        'js/pages/mobile/my/myDetail.vue',
        'js/pages/mobile/my/myFaq.vue',
        'js/pages/mobile/my/faqTmp.vue',
        'js/pages/mobile/my/myAboutUs.vue',
        'js/pages/mobile/my/myGender.vue',
        'js/pages/mobile/my/mySupport.vue',
        'js/pages/mobile/my/mySetting.vue',
        'js/pages/mobile/shop/a.vue',
        'js/pages/mobile/shop/goodsDetail.vue',
        'js/pages/mobile/shop/simpleGoodsDetail.vue',
        'js/pages/mobile/shop/ship.vue',
        'js/pages/mobile/activity/index.vue',
        'js/pages/mobile/order/index.vue',
        'js/pages/mobile/order/paySuccess.vue',
        'js/pages/mobile/order/payFailure.vue',
        'js/pages/mobile/order/payDetail.vue',
        'js/pages/mobile/order/orderConfirm.vue',
        'js/pages/mobile/order/chooseAddress.vue',
        'js/pages/mobile/order/editAddress.vue',
        'js/pages/mobile/order/state.vue',
        'js/pages/mobile/order/trackingPackage.vue',
        'js/pages/mobile/login/index.vue'
    ],
    'alias': {
        'Components': 'js/components',
        'Common': 'js/common',
        'Config': 'js/config',
        'Widget': 'js/widget',
        'Pages': 'js/pages',
        'Utils': 'js/utils'
    },
    'diff': {
        'pwd': '/Users/yangmingzhe/Work/opensource/eros-diff-folder',
        'proxy': 'https://app.weex-eros.com/source'
    },
    'server': {
        'path': './',
        'port': 8889
    },
    'mockServer': {
        'port': 52077,
        'mockDir': './dist/mock'
    },
    'socketServer': {
        'port': 8890,
        'switch': true
    }
}
