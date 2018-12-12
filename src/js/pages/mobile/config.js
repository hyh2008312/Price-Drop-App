export const tabConfig = [{
    icon: '&#xe73c;',
    name: 'Home',
    key: 'home',
    src: `${weex.config.eros.jsServer}/dist/js/pages/mobile/home/index.js`,
    visibility: 'visible'
}, {
    icon: '&#xe762;',
    name: 'Categories',
    key: 'categories',
    src: `${weex.config.eros.jsServer}/dist/js/pages/mobile/category/index.js`,
    visibility: 'hidden',
    // redPoint: true,
}, {
    icon: '&#xe733;',
    name: 'Rewards',
    key: 'rewards',
    src: `${weex.config.eros.jsServer}/dist/js/pages/mobile/perks/index.js`,
    visibility: 'hidden'
}, {
    icon: '&#xe701;',
    name: 'Account',
    key: 'account',
    src: `${weex.config.eros.jsServer}/dist/js/pages/mobile/my/index.js`,
    visibility: 'hidden'
}];
