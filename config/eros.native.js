module.exports = {
    'appName': 'Eros-mobile',
    'appBoard': '/config/index.js',
    // android 监听全局事件homeBack 如果为true 安卓端需要自行调用router.finish方法来关闭应用
    'androidIsListenHomeBack': 'true',
    'version': {
        'android': '1.0.0'
    },
    'page': {
        'homePage': '/pages/mobile/index.js',
        // 'homePage': '/pages/demo/index.js',
        'mediatorPage': '/mediator/index.js',
        'navBarColor': '#1DA1F2',
        'navItemColor': '#ffffff'
    },
    'url': {
        'image': 'https://lev-inf.benmu-health.com/test/xxx',
        'bundleUpdate': 'http://localhosts:3001/app/check'
    },
    'zipFolder': {
        'iOS': '/ios/WeexEros/WeexEros',
        'android': '/android/WeexFrameworkWrapper/app/src/main/assets'
    },
    'getui': {
        'enabled': 'false',
        'appId': 'C9ekVCzRSF8OHu2YQIXxG2',
        'appKey': 'UlOzLeMded8pgj3BjGTfm1',
        'appSecret': 'd9v10tH2A36hJwYM1gQKJ5'
    }
}
