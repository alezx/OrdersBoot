Ext.define('Orders.store.OrderEntries', {

	extend: 'Ext.data.Store',

	model: 'Orders.model.OrderEntry',

	pageSize:  1000000,

	proxy: {
		type: 'ajax',
		api: {
			read: 'order/orderEntryList.do',
			update: 'order/saveOrderEntry.do'
		},
		reader: {
			type: 'json',
			root: 'orderentries',
			successProperty: 'success'
		}
	}

});