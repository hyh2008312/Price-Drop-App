// noinspection JSAnnotator
/**
 * Created by Heyh on 2018/05/03.
 */

export default {

    // 正常模式的tab title配置
    tabTitles: [
        {
            title: '首页',
            icon: 'https://gw.alicdn.com/tfs/TB1MWXdSpXXXXcmXXXXXXXXXXXX-72-72.png',
            activeIcon: 'https://gw.alicdn.com/tfs/TB1kCk2SXXXXXXFXFXXXXXXXXXX-72-72.png'
        },
        {
            title: '特别推荐',
            icon: 'https://gw.alicdn.com/tfs/TB1ARoKSXXXXXc9XVXXXXXXXXXX-72-72.png',
            activeIcon: 'https://gw.alicdn.com/tfs/TB19Z72SXXXXXamXFXXXXXXXXXX-72-72.png'
        },
        {
            title: '消息中心',
            icon: 'https://gw.alicdn.com/tfs/TB1VKMISXXXXXbyaXXXXXXXXXXX-72-72.png',
            activeIcon: 'https://gw.alicdn.com/tfs/TB1aTgZSXXXXXazXFXXXXXXXXXX-72-72.png',
            badge: 5
        },
        {
            title: '我的主页',
            icon: 'https://gw.alicdn.com/tfs/TB1Do3tSXXXXXXMaFXXXXXXXXXX-72-72.png',
            activeIcon: 'https://gw.alicdn.com/tfs/TB1LiNhSpXXXXaWXXXXXXXXXXXX-72-72.png',
            dot: true
        }
    ],
    tabStyles: {
        bgColor: '#FFFFFF',
        titleColor: '#666666',
        activeTitleColor: '#000',
        activeBgColor: '#FFFFFF',
        isActiveTitleBold: false,
        iconWidth: 48,
        iconHeight: 48,
        width: 160,
        height: 112,
        fontSize: 24,
        textPaddingLeft: 10,
        textPaddingRight: 10
    },
    // 使用 iconFont 模式的tab title配置 '&#xe608;' -> '\ue608'
    tabIconFontTitles: [
        {
            title: 'Shop',
            codePoint: '\ue6f0'
        },
        {
            title: 'Rewards',
            codePoint: '\ue6f0'
        },
        {
            title: 'Cart',
            codePoint: '\ue6f0' // badge: 5
        },
        {
            title: 'Account',
            codePoint: '\ue6f0' // dot: true
        }
    ],
    tabIconFontStyles: {
        bgColor: '#FFFFFF',
        titleColor: 'rgba(0,0,0,.54)',
        activeTitleColor: '#000',
        activeBgColor: '#FFFFFF',
        isActiveTitleBold: false,
        width: 160,
        height: 112,
        fontSize: 24,
        textPaddingLeft: 10,
        textPaddingRight: 10,
        iconFontSize: 48,
        iconFontMarginBottom: 4,
        iconFontColor: 'rgba(0,0,0,.54)',
        activeIconFontColor: '#000',
        iconFontUrl: '//at.alicdn.com/t/font_568686_dxffcu5dfpy9o1or.ttf'
    },
    tabItems: [
        {
            title: 'Home',
            icon: '\ue6f0',
            index: 0,
            titleColor: '#000'
        },
        {
            title: 'Group',
            icon: '\ue6f0',
            index: 1,
            titleColor: '#000'
        },
        {
            title: 'Drops',
            icon: '\ue6f0',
            index: 2,
            titleColor: '#000'
        },
        {
            title: 'Me',
            icon: '\ue6f0',
            index: 3,
            titleColor: '#000'
        }
    ]
};
