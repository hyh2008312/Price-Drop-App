const globalEvent = weex.requireModule('globalEvent');
const modal = weex.requireModule('modal');

/**
 * 消息推送
 * options 客户端个推推送的所有消息
 */
globalEvent.addEventListener('pushMessage', function (options) {
    modal.alert({
        message: 'hello' + JSON.stringify(options),
        duration: 0.3
    }, function (value) {
        console.log('alert callback', value)
    })
    console.log('》》》》》》》》' + JSON.stringify(options));
})
