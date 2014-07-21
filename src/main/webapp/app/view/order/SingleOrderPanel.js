Ext.define('Orders.view.order.SingleOrderPanel' ,{
	extend: 'Ext.Panel',
	alias: 'widget.singleOrderPanel',

	layout: 'border',

	initComponent: function() {
		
		this.ordersList = Ext.widget('orderslist',{
			region: 'center',
			store: Ext.create('Orders.store.Orders'),
			doNotDisplayFilters: true
		});

		this.items = [
		   this.ordersList,
		   {	
			   xtype : 'orderEntriesList',
			   region: 'east',
			   width: 300,
			   store: Ext.create('Orders.store.OrderEntries')
		    }
	    ];

		this.callParent(arguments);
	}
});