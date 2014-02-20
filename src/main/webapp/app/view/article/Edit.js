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
						name : 'PRD_Q',
						fieldLabel: 'Production'
					},
					{
						xtype: 'textfield',
						name : 'RESERVEDSTOCK_Q',
						fieldLabel: 'Reserved'
					},
					{
						xtype: 'textfield',
						name : 'AVAILABLEFORSALE_Q',
						fieldLabel: 'Available'
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