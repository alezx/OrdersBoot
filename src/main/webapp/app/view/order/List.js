Ext.define('Orders.view.order.List', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.orderslist',

	store : 'Orders',

	initComponent : function() {
		
		this.columns = this.getColumns();

		this.bbar = this.getBottomBar();

		if(!this.doNotDisplayFilters) {
			this.features = [ this.getFilters() ];
		}

		this.callParent(arguments);
	},

	getBottomBar : function() {
		var me = this;
		return {
			xtype : 'pagingtoolbar',
			store : this.store, // grid and PagingToolbar using same store
			displayInfo : true,
			prependButtons : false,
			items : [ {
				text : 'Clear Filter Data',
				handler : function() {
					me.filters.clearFilters();
				}
			} ]
		};
	},

	getFilters : function() {
		return {
			ftype : 'filters',
			encode : true, // json encode the filter query
			local : false
		// defaults to false (remote filtering)
		};
	},

	getColumns : function() {
		return [ {
			header : 'Code',
			dataIndex : 'CODE',
			flex : 1,
			filterable : true
		}, {
			header : 'Sales Order',
			dataIndex : 'SYSTEM_CODE',
			flex : 1,
			filterable : true
		}, {
			header : 'Customer',
			dataIndex : 'CUSTOMER',
			flex : 1,
			filterable : true
		}, {
			header : 'Total',
			dataIndex : 'TOTAL',
			flex : 1,
			filterable : true
		}, {
			header : 'First Insert',
			dataIndex : 'FIRST_INSERT',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			flex : 1,
			filterable : true
		}, {
			header : 'Last Update',
			dataIndex : 'LAST_UPDATE',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			flex : 1,
			filterable : true
		}];
	}
});