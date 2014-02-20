Ext.define('Orders.view.general.MainTab' ,{
	extend: 'Ext.Panel',
	alias: 'widget.maintab',

	title: 'Status',
	
	id: 'MainTab',

	initComponent: function() {

		this.items = [
	    	{
	        	xtype : 'label'
	    	}
	    ];

		this.callParent(arguments);
	}
});