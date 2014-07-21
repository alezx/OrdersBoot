Ext.define('Orders.store.Orders', {

	extend: 'Ext.data.Store',
	
	model: 'Orders.model.Order',
	
	autoLoad: true,
	
	remoteSort: true,
	
	pageSize: 100,

	proxy: {
		type: 'ajax',
		api:{
			read: 'order/orderList.do',
			update: 'order/saveOrder.do'
		},
		remoteSort: true,
		reader: {
			type: 'json',
			root: 'orders',
			successProperty: 'success'
		},
		simpleSortMode: true
	}

});