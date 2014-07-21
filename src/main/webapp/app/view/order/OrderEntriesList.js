Ext.define('Orders.view.order.OrderEntriesList', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.orderEntriesList',

	store: 'OrderEntries',

	viewConfig: {
	    enableTextSelection: true
	},

	bbar: [{
		text: 'Add Article',
		action: 'addArticle'
	}],

	initComponent: function() {

		console.log("in order list");
		this.columns = [{
			header: 'Article',
			dataIndex: 'article.code'
		}, {
			header: 'Series',
			dataIndex: 'article.series'
		}, {
			header: 'Q',
			dataIndex: 'quantity',
			width: 40
		}, {
			header: 'NQ',
			dataIndex: 'newQuantity',
			width: 40,
			renderer: function(value, metaData, record){
				if(record.get('quantity')!=record.get('newQuantity')){
					metaData.style = 'background-color:blue;color:white;';
				}
				return value;
			}
		}, {
			header: 'W',
			dataIndex: 'residualWarehouseQuantity',
			width: 50
		}, {
			header: 'P',
			dataIndex: 'residualProductionQuantity',
			width: 50
		}, {
			header: 'Orders',
			dataIndex: 'ordersSoFar',
			flex: 1
		}];

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