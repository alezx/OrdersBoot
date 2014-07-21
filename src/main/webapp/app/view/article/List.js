Ext.define('Orders.view.article.List' ,{
	extend: 'Ext.grid.Panel',
	alias: 'widget.articleslist',

	title: 'All Articles',

	store: 'Articles',
	
	viewConfig: {
	    enableTextSelection: true
	},

	initComponent: function() {
	
		this.features = [ this.getFilters() ];
		
		this.columns = [
			{header: 'Code',  dataIndex: 'code',  flex: 1, filterable : true},
			{header: 'Price', dataIndex: 'price', flex: 1, filterable : true},
			{header: 'Production', dataIndex: 'productionQuantity', flex: 1, filterable : true},
			
			
			{header: 'Series',  dataIndex: 'series', flex: 1, filterable : true},
			{header: 'Title',  dataIndex: 'title', flex: 1, filterable : true},
			{header: 'Format',  dataIndex: 'format', flex: 1, filterable : true},
			{header: 'Interior',  dataIndex: 'interior', flex: 1, filterable : true},
			 
			//{header: 'Reserved',  dataIndex: 'RESERVEDSTOCK_Q', flex: 1, filterable : true, hidden: true},
			//{header: 'Available For Sale',  dataIndex: 'AVAILABLEFORSALE_Q', flex: 1, filterable : true, hidden: true},
			//{header: 'Available H',  dataIndex: 'AVAILABLEONHAND_Q', flex: 1, filterable : true},
			
			{header: 'Warehouse', dataIndex: 'warehouseQuantity', flex: 1, filterable : true},
			 {header: 'Requested', dataIndex: 'requestedQuantity', flex: 1, filterable : true}

			// {header: 'Bal Prod', dataIndex: 'balanceProd', flex: 1, filterable : true},
			// {header: 'Bal Prod Price', dataIndex: 'balanceProdPrice', flex: 1, filterable : true},
			// {header: 'Bal Ware', dataIndex: 'balanceWarehouse', flex: 1, filterable : true},
			// {header: 'Bal Ware Price', dataIndex: 'balanceWarehousePrice', flex: 1, filterable : true}
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
	},
	
	getFilters : function() {
		return {
			ftype : 'filters',
			encode : true, // json encode the filter query
			local : false
		// defaults to false (remote filtering)
		};
	}
});