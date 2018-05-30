/**
 * @desc 分享的链接规范
 * @author lzq
 */
const ShareUrlUtil = {
    getShareUrl (userId, productId) {
        if (userId && productId) {
            return 'https://www.getpricedrop.com?userId=' + userId + '&productId=' + productId;
        }
    },
    getWhatsAppParams (userId, productId, peopleNumber) {
        return 'Hey! I just found this item and need your help to drop the price before it sells out:  \n' +
            'https://www.getpricedrop.com?userId=' + userId + '&productId=' + productId + '\n' + peopleNumber +
            ' people have got their favorite items at the lowest price on PriceDrop! Join me and save big together.'
    }
};
export default ShareUrlUtil;
