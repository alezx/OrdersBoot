Ext.define('Orders.controller.Orders', {
	extend: 'Ext.app.Controller',

	stores: ['Orders', 'OrderEntries'],

	models: ['Order','OrderEntry'],

	views: ['order.List', 'order.Edit', 'order.OrderEntriesList','order.OrderEntryEdit'],

	init: function() {
		this.control({
			'orderslist': {
				itemdblclick: this.editOrder,
				itemclick: this.updateOrderEntryPanel,
			},
			'orderedit button[action=save]': {
				click: this.updateOrder
			},
			'orderEntriesList': {
				itemdblclick: this.editOrderEntry,
			},
			'orderentryedit button[action=save]': {
				click: this.updateOrderEntry
			}
		});
	},

	editOrder: function(grid, record) {
		//console.log('Double clicked on ' + record.get('CODE'));
		var view = Ext.widget('orderedit');
		view.down('form').loadRecord(record);
	},
	
	updateOrder: function(button) {
		var win = button.up('window'),
			form = win.down('form'),
			record = form.getRecord(),
			values = form.getValues();

		record.set(values);
		win.close();
		this.getOrdersStore().sync();
	},
	
	updateOrderEntryPanel: function(view, record, item, index, e, eOpts){
		this.getOrderEntriesStore().load({params: { orderId : record.get("ID") }});
	},

	editOrderEntry: function(grid, record) {
		//console.log('Double clicked on ' + record.get('CODE'));
		var view = Ext.widget('orderentryedit');
		view.down('form').loadRecord(record);
	},

	updateOrderEntry: function(button) {
		var win = button.up('window'),
			form = win.down('form'),
			record = form.getRecord(),
			values = form.getValues();

		record.set(values);
		win.close();
		this.getOrderEntriesStore().sync();
	},

});