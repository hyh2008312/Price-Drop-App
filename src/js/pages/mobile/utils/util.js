/**
 * Created by zwwill on 2017/8/27.
 */

const utilFunc = {
    initIconFont () {
        const domModule = weex.requireModule('dom');
        domModule.addRule('fontFace', {
            'fontFamily': 'iconfont',
            'src': 'url(\'https://at.alicdn.com/t/font_568686_yfy1gxja62.ttf\')'
        });
    },
    setBundleUrl (url, jsFile) {
        const bundleUrl = url;
        let host = '';
        let path = '';
        let nativeBase;
        const isAndroidAssets = bundleUrl.indexOf('your_current_IP') >= 0 || bundleUrl.indexOf('file://assets/') >= 0;
        const isiOSAssets = bundleUrl.indexOf('file:///') >= 0 && bundleUrl.indexOf('WeexDemo.app') > 0;
        if (isAndroidAssets) {
            nativeBase = 'file://assets/dist';
        } else if (isiOSAssets) {
            // file:///var/mobile/Containers/Bundle/Application/{id}/WeexDemo.app/
            // file:///Users/{user}/Library/Developer/CoreSimulator/Devices/{id}/data/Containers/Bundle/Application/{id}/WeexDemo.app/
            nativeBase = bundleUrl.substring(0, bundleUrl.lastIndexOf('/') + 1);
        } else {
            const matches = /\/\/([^\/]+?)\//.exec(bundleUrl);
            const matchFirstPath = /\/\/[^\/]+\/([^\/]+)\//.exec(bundleUrl);
            if (matches && matches.length >= 2) {
                host = matches[1];
            }
            if (matchFirstPath && matchFirstPath.length >= 2) {
                path = matchFirstPath[1];
            }
            nativeBase = 'https://' + host + '/';
        }
        const h5Base = './index.html?page=';
        // in Native
        let base = nativeBase;
        if (typeof navigator !== 'undefined' && (navigator.appCodeName === 'Mozilla' || navigator.product === 'Gecko')) {
            // check if in weexpack project
            if (path === 'web' || path === 'dist') {
                base = h5Base + '/dist/';
            } else {
                base = h5Base + '';
            }
        } else {
            base = nativeBase + (path ? path + '/' : '');
        }

        const newUrl = base + jsFile;
        return newUrl;
    },
    getUrlSearch (url, name) {
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = url.slice(url.indexOf('?') + 1).match(reg);
        if (r != null) {
            try {
                return decodeURIComponent(r[2]);
            } catch (_e) {
                return null;
            }
        }
        return null;
    },
    formateDate (dateTimeStamp) {
        const number = ['zero', 'a', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine', 'ten'];
        const now = new Date().getTime();
        const diffValue = now - new Date(dateTimeStamp).getTime();
        if (diffValue < 0) {
            // 若日期不符则弹出窗口告之
            // alert("结束日期不能小于开始日期！");
            return 'now';
        }
        const minute = 1000 * 60;
        const hour = minute * 60;
        const day = hour * 24;
        const month = day * 30;
        const monthC = diffValue / month;
        const weekC = diffValue / (7 * day);
        const dayC = diffValue / day;
        const hourC = diffValue / hour;
        const minC = diffValue / minute;
        let result = '';
        if (monthC >= 1) {
            result = number[parseInt(monthC)] + (parseInt(monthC) > 1 ? ' months' : ' month') + 'ago';
        } else if (weekC >= 1) {
            result = number[parseInt(weekC)] + (parseInt(weekC) > 1 ? ' weeks' : ' week') + ' ago';
        } else if (dayC >= 1) {
            result = number[parseInt(dayC)] + (parseInt(dayC) > 1 ? ' days' : ' day') + ' ago';
        } else if (hourC >= 1) {
            result = (parseInt(hourC) == 1 ? 'an' : (number[parseInt(hourC)] <= 10 ? number[parseInt(hourC)] : 'ten')) + (parseInt(hourC) > 1 ? ' hours' : ' hour') + ' ago';
        } else if (minC >= 1) {
            result = (parseInt(minC) <= 10 ? number[parseInt(minC)] : 'ten') + (parseInt(minC) > 1 ? ' minutes' : ' minute') + ' ago';
        } else { result = 'just now'; }
        return result;
    }
};

export default utilFunc;
