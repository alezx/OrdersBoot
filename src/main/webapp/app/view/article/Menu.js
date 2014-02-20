Ext.define('Orders.view.article.Menu', {
	extend: 'Ext.menu.Menu',
	alias: 'widget.articlesMenu',

	
	initComponent: function() {
		this.items = [
		    {
		    	text: 'Open Related Orders',
		    	action: 'open_orders'
		    }
		];

		this.callParent(arguments);
	}
});