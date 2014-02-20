Ext.define('Orders.store.OrderEntries', {

	extend: 'Ext.data.Store',
	
	model: 'Orders.model.OrderEntry',
	
	pageSize: 100,
	
	proxy: {
		type: 'ajax',
		api:{
			read: 'order/orderEntryList.do',
			update: 'order/saveOrderEntry.do',
		},
		remoteSort: true,
		reader: {
			type: 'json',
			root: 'orderentries',
			successProperty: 'success'
		},
		simpleSortMode: true
	}

});