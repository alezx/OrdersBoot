Ext.define('Orders.view.order.AddOrderEntry', {
	extend: 'Ext.window.Window',
	alias: 'widget.addorderentry',

	title: 'Add Article To Order',
	layout: 'fit',
	autoShow: true,

	initComponent: function() {
		this.items = [
			{
				xtype: 'form',
				items: [
					{
						xtype: 'textfield',
						name : 'articleCode',
						fieldLabel: 'Code'
					},
					{
						xtype: 'textfield',
						name : 'quantity',
						fieldLabel: 'Quantity'
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