export const tabConfig = [{
    icon: '&#xe704;',
    name: 'Home',
    key: 'home',
    src: `${weex.config.eros.jsServer}/dist/js/pages/mobile/home/index.js`,
    visibility: 'visible'
}, {
    icon: '&#xe6f8;',
    name: 'Drops',
    key: 'drops',
    src: `${weex.config.eros.jsServer}/dist/js/pages/mobile/drops/index.js`,
    visibility: 'hidden'
}, {
    icon: '&#xe701;',
    name: 'Account',
    key: 'account',
    src: `${weex.config.eros.jsServer}/dist/js/pages/mobile/my/index.js`,
    visibility: 'hidden'
}];
