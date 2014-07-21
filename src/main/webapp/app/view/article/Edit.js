Ext.define('Orders.view.article.Edit', {
	extend: 'Ext.window.Window',
	alias: 'widget.articleedit',

	title: 'Edit Article',
	layout: 'fit',
	autoShow: true,

	initComponent: function() {
		this.items = [
			{
				xtype: 'form',
				items: [
					{
						xtype: 'textfield',
						name : 'productionQuantity',
						fieldLabel: 'Production Qty'
					},
					{
						xtype: 'textfield',
						name : 'warehouseQuantity',
						fieldLabel: 'Warehouse Qty'
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