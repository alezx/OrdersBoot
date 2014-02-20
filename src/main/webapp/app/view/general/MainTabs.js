Ext.define('Orders.view.general.MainTabs' ,{
	extend: 'Ext.tab.Panel',
	alias: 'widget.maintabs',

	title: 'Orders',


	initComponent: function() {
		
		console.log('MainTabs');
		
		this.items = [{
	        xtype : 'maintab'
	    }, {
	    	title: 'All Orders',
	    	layout: 'border',
	    	defaults: {
	            split: true
	        },
	    	items:[
	    	   {xtype : 'orderslist', region: 'center'},
	    	   {xtype : 'orderEntriesList', region: 'east', width: 200}
	    	]
	    }, {
	    	xtype : 'articleslist'
	    }];

		this.callParent(arguments);
	}
});