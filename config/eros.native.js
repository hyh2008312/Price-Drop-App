module.exports = {
    'appName': 'PriceDrop-mobile',
    'appBoard': '/config/index.js',
    // android 监听全局事件homeBack 如果为true 安卓端需要自行调用router.finish方法来关闭应用
    'androidIsListenHomeBack': 'true',
    'version': {
        'android': '1.3.1'
    },
    'page': {
        'homePage': '/pages/mobile/welcome/guide.js',
       // 'homePage': '/pages/mobile/index.js',
        // 'homePage': '/pages/demo/index.js',
        'mediatorPage': '/mediator/index.js',
        'navBarColor': '#1DA1F2',
        'navItemColor': '#ffffff'
    },
    'url': {
        'image': 'https://lev-inf.benmu-health.com/test/xxx',
        'bundleUpdate': 'https://app-api.getpricedrop.com/app/check/'
    },
    'zipFolder': {
        'iOS': '/ios/WeexEros/WeexEros',
        'android': '/android/WeexFrameworkWrapper/app/src/main/assets'
    },
    'getui': {
        'enabled': 'true',
        'appId': 'Qx7Uxdw9XI8lHCJJjOtux5',
        'appKey': 'ff7flmAdSJ7t9CogJp7f55',
        'appSecret': 'DaHJ0RvCfp51UsGbGkU9P9'
    }
}
