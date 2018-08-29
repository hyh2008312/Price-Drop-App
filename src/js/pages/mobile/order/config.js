export const ORDER = {
    title: 'Toyota’s Latest is an Entire Mobility Service Platform',
    src: '',
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
    name: 'Paid',
    left: '124'
}, {
    name: 'Preparing',
    left: '286'
}, {
    name: 'Shipped',
    left: '466'
}, {
    name: 'Closed',
    left: '634'
}];

export const ADDRESS = [{
    name: 'All',
    left: '0'
}, {
    name: 'Paid',
    left: '124'
}, {
    name: 'Preparing',
    left: '286'
}, {
    name: 'Shipped',
    left: '466'
}, {
    name: 'Closed',
    left: '634'
}];

export const PAYLIST = [{
    src: null,
    value: 1,
    checked: true
}, {
    src: null,
    value: 2
}];

export const ORDERSTATUS = [null, 'Paid', 'Packing', 'Shipped', 'Completed'];

export const CANCELREASON = [
    'I need to change my order',
    'Find alternative item somewhere else',
    'Order shipment delayed',
    'I don’t want it anymore',
    'Other'
];
