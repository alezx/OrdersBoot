Ext.define('Orders.view.order.OrderEntryEdit', {
	extend: 'Ext.window.Window',
	alias: 'widget.orderentryedit',

	title: 'Edit Order Entry',
	layout: 'fit',
	autoShow: true,

	initComponent: function() {
		this.items = [
			{
				xtype: 'form',
				items: [
					{
						xtype: 'textfield',
						name : 'newQuantity',
						fieldLabel: 'New Quantity'
					}
				]
			}
		];

		this.buttons = [
			{
				text: 'Save',
				action: 'save'
			},
			{
				text: 'Cancel',
				scope: this,
				handler: this.close
			}
		];

		this.callParent(arguments);
	}
});