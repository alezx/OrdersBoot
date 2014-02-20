Ext.define('Orders.view.article.OrderArticleMenu', {
	extend: 'Ext.menu.Menu',
	alias: 'widget.orderArticleMenu',

	
	initComponent: function() {
		this.items = [
		    {
		    	text: 'Open Order',
		    	action: 'open_order'
		    }
		];

		this.callParent(arguments);
	}
});