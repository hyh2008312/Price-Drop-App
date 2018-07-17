/**
 * @desc 分享的链接规范
 * @author lzq
 */
const ShareUrlUtil = {
    getShareUrl (productId) {
        return `https://app.getpricedrop.com/detail/${productId}/`;
    },
    getCopylinkUrl (productId) {
        return `https://app.getpricedrop.com/detail/${productId}?utm_source=link&utm_medium=urlshare`;
    },
    getWhatsAppParams (productId, productPrice, productCategory) {
        return `Hey, I need your help to drop the price of this ${productCategory} product, so we can both get it at Rs.${productPrice} ! Here is the link to join me: https://app.getpricedrop.com/detail/${productId}?utm_source=whatsapp&utm_medium=msg`;
    }
};
export default ShareUrlUtil;
