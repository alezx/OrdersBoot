Ext.Loader.setConfig({
    disableCaching: false
});
Ext.require(['Ext.ux.grid.FiltersFeature']);

Ext.application({
	requires: ['Ext.container.Viewport'],
	name: 'Orders',

	appFolder: 'app',

	controllers: ['Generals'
	              ,'Orders'
	              ,'Articles'
	              ],

	launch: function() {
		Ext.create('Ext.container.Viewport', {
			layout: 'fit',
			items: [
				{
					xtype : 'maintabs'
				}
			]
		});
	}
});