Ext.define('Orders.controller.Generals', {
	extend: 'Ext.app.Controller',

	// stores: [ 'Users' ],

	// models: ['User'],

	views: ['general.MainTabs', 'general.MainTab'],
	

	init: function() {
		this.startMonitor();
	},

	startMonitor: function(){
		// Ext.Ajax.request({
		// 	url: "home/startFolderMonitor.do",
		// 	success: function(response, opts) {
		// 		var o = Ext.decode(response.responseText);
		// 		Ext.ComponentQuery.query('#MainTab > label')[0].update(o.started);
		// 	},
		// 	failure: function(response, opts) {
		// 		Ext.ComponentQuery.query('#MainTab > label')[0].update('error');
		// 	}
		// });
	}

});