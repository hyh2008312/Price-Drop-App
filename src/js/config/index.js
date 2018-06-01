import Widget from 'eros-widget'
import { apis, baseUrl } from './apis'
import routes from './routes'
import './push'

let ISAUTHLOGIN = false;

new Widget({
    router: {
        /**
         * 路由配置表
         */
        routes
    },
    ajax: {
        baseUrl,
        /**
         * 接口别名
         */
        apis,
        // 接口超时时间
        timeout: 30000,

        /**
         * 请求发送统一拦截器 （可选）
         * options 你请求传入的所有参数和配置
         * next
         */
        requestHandler (options, next) {
            if (options.header && options.header.needAuth == true) {
                const storage = weex.requireModule('bmStorage');
                storage.getData('token', (res) => {
                    if (res && res.data && JSON.parse(res.data)) {
                        options.header.Authorization = 'Bearer ' + JSON.parse(res.data).accessToken
                        next()
                    }
                })
            } else {
                next()
            }
        },
        /**
         * 请求返回统一拦截器 （可选）
         * options 你请求传入的所有参数和配置
         * resData 服务器端返回的所有数据
         * resolve 请求成功请resolve你得结果，这样请求的.then中的成功回调就能拿到你resolve的数据
         * reject 请求成功请resolve你得结果，这样请求的.then中的失败回调就能拿到你reject的数据
         */
        responseHandler (options, resData, resolve, reject) {
            const { status, errorMsg, data } = resData
            if (status !== 200) {
                console.log(`invoke error status: ${status}`)
                console.log(`invoke error message: ${errorMsg}`)
                if (status == 401 && !options.isLoginPop && !ISAUTHLOGIN) {
                    ISAUTHLOGIN = true
                    const router = weex.requireModule('bmRouter')
                    router.open({
                        url: '/pages/mobile/login/index.js', // 页面对应的 js 地址(注意路径从 /pages/ 开始)
                        type: 'PRESENT', // 客户端定义动态类型，默认值为 PUSH 1. PUSH (将页面压栈到当前容器栈) 2.PRESENT （新建容器栈在进行压栈,从下面弹出新页面）3.TRANSLATION (动画类似QQ的侧滑抽屉效果)
                        params: {}, // 传到下一个页面的参数，params 通过 router.getParams(callback) 获取
                        canBack: true, // 是否需要返回按钮
                        gesBack: true, // 是否开启手势返回（默认开启，仅支持 iOS）
                        navTitle: '', // 页面标题
                        navShow: false, // 是否显示原生导航栏
                        statusBarStyle: 'LightContent', // 系统状态栏文字颜色 1.Default：黑色， 2.LightContent: 白色
                        isRunBackCallback: true, // 为true时，用户点击返回（或Android物理返回按钮）时回调下面的callback方法，native端不做页面跳转
                        backgroundColor: '#f1f1f1' // 原生页面背景颜色（可以通过设置原生页面背景颜色从感官上减少白屏）
                    }, function () {
                        // callback
                    });
                }
                reject(resData)
            } else {
                // 自定义请求逻辑
                resolve(data)
            }
        }
    }
})
