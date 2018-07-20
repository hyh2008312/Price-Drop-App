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
    getWhatsAppParams (productId, productName, productDiscount) {
        return `Hey, I want to get this ${productName} at Rs.${productDiscount} and I need your help to click on the PriceDrop button in this page to help me drop the price. And if you like this product, I can help you too. https://app.getpricedrop.com/detail/${productId}?utm_source=whatsapp&utm_medium=msg`;
    }
};
export default ShareUrlUtil;
