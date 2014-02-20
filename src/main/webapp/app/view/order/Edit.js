Ext.define('Orders.view.order.Edit', {
	extend: 'Ext.window.Window',
	alias: 'widget.orderedit',

	title: 'Edit Order',
	layout: 'fit',
	autoShow: true,

	initComponent: function() {
		this.items = [
			{
				xtype: 'form',
				items: [
					{
						xtype: 'textfield',
						name : 'CODE',
						fieldLabel: 'Code'
					},
					{
						xtype: 'textfield',
						name : 'CUSTOMER',
						fieldLabel: 'Customer'
					},
					{
						xtype: 'textfield',
						name : 'SYSTEM_CODE',
						fieldLabel: 'Sales Order'
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