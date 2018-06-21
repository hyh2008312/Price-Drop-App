/**
 * @desc 分享的链接规范
 * @author lzq
 */
const ShareUrlUtil = {
    getShareUrl (productId) {
        return `https://app.getpricedrop.com/detail/${productId}`;
    },
    /* getWhatsAppParams (productId, peopleNumber) {
        return 'Hey! I just found this item and need your help to drop the price before it sells out:  \n' +
            'https://app.getpricedrop.com/detail/' + productId + '\n' + '1,000+ people have got their favorite items at the lowest price on PriceDrop! Join me and save big together.'
    } */
    getWhatsAppParams (productId, peopleNumber) {
        return 'Hi, friends, I am dropping the price of this fantastic product, could you please help me drop the price? Really really really appreciate it, I do want this! Just click the link and help me drop the price, thank u guys!! Participants will receive points to redeem cash rewards!  https://app.getpricedrop.com/detail/' + productId
    }
};
export default ShareUrlUtil;
