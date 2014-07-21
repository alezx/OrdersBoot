Ext.define('Orders.controller.Orders', {
	extend: 'Ext.app.Controller',

	stores: ['Orders', 'OrderEntries'],

	models: ['Order', 'OrderEntry'],

	views: [
		'order.List', 'order.Edit',
		'order.OrderEntriesList',
		'order.OrderEntryEdit',
		'order.AddOrderEntry'
	],

	init: function() {
		this.control({
			'orderslist': {
				//itemdblclick: this.editOrder,
				select: this.updateOrderEntryPanel
			},
			// 'orderedit button[action=save]': {
			// 	click: this.updateOrder
			// },
			'orderEntriesList': {
				itemdblclick: this.editOrderEntry
			},
			'orderEntriesList button[action=addArticle]': {
				click: this.addArticle
			},
			'orderentryedit button[action=save]': {
				click: this.updateOrderEntry
			},
			'orderslist button[action=increasePriority]': {
				click: this.increasePriority
			},
			'orderslist button[action=calculatePercentages]': {
				click: this.calculatePercentages
			},
			'addorderentry button[action=save]': {
				click: this.saveNewOrderEntry
			}
		});
	},

	editOrder: function(grid, record) {
		//console.log('Double clicked on ' + record.get('CODE'));
		var view = Ext.widget('orderedit');
		view.down('form').loadRecord(record);
	},

	// updateOrder: function(button) {
	// 	var win = button.up('window'),
	// 		form = win.down('form'),
	// 		record = form.getRecord(),
	// 		values = form.getValues();

	// 	record.set(values);
	// 	win.close();
	// 	this.getOrdersStore().sync();
	// },

	updateOrderEntryPanel: function(view, record, item, index, e, eOpts) {
		var orderId = record.get("id");
		Ext.ComponentQuery.query('orderEntriesList')[0].orderId = orderId;
		this.getOrderEntriesStore().load({
			params: {
				orderId: orderId
			}
		});
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

	increasePriority: function(button) {
		var me = this;
		var selected = button.up('grid').getSelectionModel().getSelection()[0];
		Ext.MessageBox.prompt('Insert', 'Enter new priority:', function(btn, text) {
			if (btn == 'ok') {
				Ext.Ajax.request({
					url: 'order/increasePriority.do',
					params: {
						orderId: selected.get('id'),
						priority: text
					},
					success: function(response) {
						Ext.MessageBox.show({
							title: 'Success',
							msg: 'Priority changed'
						});
						me.getOrdersStore().load();
					},
					failure: function(response) {
						Ext.MessageBox.show({
							title: 'Error',
							msg: '!!'
						});
					}
				});
			}
		});
	},

	calculatePercentages: function() {
		var me = this;
		Ext.Ajax.request({
			url: 'order/calculatePercentages.do',
			success: function(response) {
				Ext.MessageBox.show({
					title: 'Success',
					msg: 'done'
				});
				me.getOrdersStore().load();
			},
			failure: function(response) {
				Ext.MessageBox.show({
					title: 'Error',
					msg: response.responseText
				});
			}
		});
	},

	addArticle: function(button) {
		var win = Ext.widget('addorderentry');
		win.orderId = button.up('grid').orderId;
	},

	saveNewOrderEntry: function(button) {
		// button.up('window').down('form').submit({
		// 	url: 'order/addArticle.do',
		// 	headers: {
		// 		"Content-Type": "application/json"
		// 	},
		// 	// params: {
		// 	// 	orderId : button.up('window').orderId
		// 	// },
		// 	success: function(form, action) {
		// 		Ext.Msg.alert('Success', 'Article Added');
		// 	},
		// 	failure: function(form, action) {
		// 		Ext.Msg.alert('Failure', action);
		// 	}
		// });
		var form = button.up('window').down('form');
		Ext.Ajax.request({
			url: 'order/addArticle.do',
			params: {
				orderId: button.up('window').orderId,
				articleCode: form.down('textfield[name=articleCode]').getValue(),
				quantity: form.down('textfield[name=quantity]').getValue()
			},
			success: function(response) {
				Ext.Msg.alert('Success', response.responseText);
			},
			failure: function(response) {
				Ext.Msg.alert('Failure', response.responseText);
			}
		});
	}

});