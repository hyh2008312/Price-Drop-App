export const ORDER = {
    title: 'Toyota’s Latest is an Entire Mobility Service Platform',
    src: 'http://yanxuan.nosdn.127.net/630439320dae9f1ce3afef3c39721383.jpg',
    date: 'May 16th，2018',
    status: 'Unpaid',
    attribute: 'Pink L',
    originalPrice: '1299.00',
    droppedPrice: '299.00',
    saleUnitPrice: '1000.00',
    endTime: '23:45:39',
    number: 1
};

export const ORDERDETAIL = {
    'id': -1,
    'number': ' ',
    'currency': ' ',
    'fixedExchangeRate': ' ',
    'lines': [
        {
            'id': -1,
            'title': ' ',
            'quantity': 1,
            'supplierName': ' ',
            'priceExclTax': '0.00',
            'priceInclTax': '0.00',
            'shippingExclTax': '0.00',
            'productId': -1,
            'mainImage': '',
            'attributes': ' ',
            'totalTax': ' ',
            'unitTax': ' ',
            'paymentPrice': ' '
        }
    ],
    'totalInclTax': '0.00',
    'totalExclTax': '0.00',
    'modified': '',
    'totalTax': '0.00',
    'paymentAmount': '0.00',
    'shippingInclTax': '0.00',
    'shippingExclTax': '0.00',
    'shippingProvider': '',
    'orderStatus': '',
    'phoneNumber': ' ',
    'username': ' ',
    'address': ' ',
    'cancelTimestamp': ' ',
    'cancelTime': ' ',
    'created': ' '
};

export const CHANNELLIST = [{
    name: 'All',
    left: '0'
}, {
    name: 'Unpaid',
    left: '128'
}, {
    name: 'Packing',
    left: '284'
}, {
    name: 'Shipped',
    left: '448'
}, {
    name: 'Delivered',
    left: '620'
}];

export const ADDRESS = [{
    name: 'All',
    left: '0'
}, {
    name: 'Unpaid',
    left: '128'
}, {
    name: 'Packing',
    left: '284'
}, {
    name: 'Shipped',
    left: '448'
}, {
    name: 'Delivered',
    left: '620'
}];

export const PAYLIST = [{
    src: null,
    value: 1,
    checked: true
}, {
    src: null,
    value: 2
}];

export const TOKEN = 'EmTm3ZEb7s3oO9kA9OwV75aGPd16ZG';

export const ORDERSTATUS = [null, 'Unpaid', 'Packing', 'Shipped', 'Completed']

export const CANCELREASON = [
    'I need to change my order',
    'Find alternative item somewhere else',
    'Order shipment delayed',
    'I don’t want it anymore',
    'Other'
]
