Ext.define('Orders.view.article.OrderArticleList' ,{
	extend: 'Ext.grid.Panel',
	alias: 'widget.orderArticleList',

	title: 'Order - Articles',
	
	closable: true,

	initComponent: function() {
		
		this.store = Ext.create('Orders.store.OrderArticles');
	
		this.columns = [
			{header: 'Order Code', dataIndex: 'ORDER_CODE', flex: 1},
			{header: 'Sales Order', dataIndex: 'ORDER_SYSTEM_CODE', flex: 1},
			{header: 'Quantity', dataIndex: 'QUANTITY', flex: 1}
		];
		
		this.bbar = {
			xtype: 'pagingtoolbar',
	        store: this.store,       // grid and PagingToolbar using same store
	        displayInfo: true,
	        prependButtons: true,
	        items: [
	            'text 1'
	        ]
	    };

		this.callParent(arguments);
	}
});