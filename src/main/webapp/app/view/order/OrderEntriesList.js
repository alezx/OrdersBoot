Ext.define('Orders.view.order.OrderEntriesList' ,{
	extend: 'Ext.grid.Panel',
	alias: 'widget.orderEntriesList',

	store: 'OrderEntries',
	
	

	initComponent: function() {
		
		console.log("in order list");
		this.columns = [
			{header: 'Article',  dataIndex: 'ARTICLE_CODE',  flex: 1},
			{header: 'Quantity', dataIndex: 'QUANTITY', flex: 1}
		];
		
//		this.bbar = {
//			xtype: 'pagingtoolbar',
//	        store: this.store,       // grid and PagingToolbar using same store
//	        displayInfo: true,
//	        prependButtons: true,
//	        items: [
//	            'text 1'
//	        ]
//	    };

		this.callParent(arguments);
	}
});