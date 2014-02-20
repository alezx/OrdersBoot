Ext.define('Orders.view.article.OrderArticleEdit', {
	extend: 'Ext.window.Window',
	alias: 'widget.orderArticleEdit',

	title: 'Edit Quantity',
	layout: 'fit',
	autoShow: true,

	initComponent: function() {
		this.items = [
			{
				xtype: 'form',
				items: [
					{
						xtype: 'textfield',
						name : 'NEW_QUANTITY',
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