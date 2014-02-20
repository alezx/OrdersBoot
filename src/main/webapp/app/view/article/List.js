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
			{header: 'Code',  dataIndex: 'CODE',  flex: 1, filterable : true},
			{header: 'Price', dataIndex: 'PRICE', flex: 1, filterable : true},
			{header: 'Production', dataIndex: 'PRD_Q', flex: 1, filterable : true},
			
			{header: 'Requested', dataIndex: 'REQUESTED_Q', flex: 1, filterable : true},
			
			{header: 'Series',  dataIndex: 'SERIES', flex: 1, filterable : true},
			{header: 'Title',  dataIndex: 'TITLE', flex: 1, filterable : true},
			{header: 'Format',  dataIndex: 'FORMAT', flex: 1, filterable : true},
			{header: 'Interior',  dataIndex: 'INTERIOR', flex: 1, filterable : true},
			 
			{header: 'Reserved',  dataIndex: 'RESERVEDSTOCK_Q', flex: 1, filterable : true},
			{header: 'Available',  dataIndex: 'AVAILABLEFORSALE_Q', flex: 1, filterable : true},
			{header: 'Res + Avail',  dataIndex: 'AVAILABLEONHAND_Q', flex: 1, filterable : true}
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