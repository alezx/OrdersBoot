Ext.define('Orders.controller.Articles', {
	extend: 'Ext.app.Controller',

	stores: ['Articles'],

	models: ['Article'],

	views: ['article.List', 'article.Edit', 'article.Menu', 'article.OrderArticleList'],

	init: function() {
		this.control({
			'articleslist': {
				itemdblclick: this.editArticle,
				itemcontextmenu: this.createContextMenu
			},
			'articleedit button[action=save]': {
				click: this.updateArticle
			},
			'articlesMenu menuitem[action=open_orders]': {
				click: this.openOrders
			},
			'orderArticleList': {
				itemdblclick: this.editOrderArticle,
				itemcontextmenu: this.createOrderArticleContextMenu
			},
			'orderArticleEdit button[action=save]': {
				click: this.updateOrderArticle
			},
			'orderArticleMenu menuitem[action=open_order]': {
				click: this.openOrder
			},
		});
	},
	
	/* from article list */
	editArticle: function(grid, record) {
		var view = Ext.widget('articleedit');
		view.down('form').loadRecord(record);
	},
	
	createContextMenu: function(grid, record, item, index, e, eOpts ){
		e.preventDefault(); 
		var menu = Ext.widget('articlesMenu');
		menu.currentRecord = record;
		menu.showAt(e.getXY());
	},
	
	/* from article edit window */
	updateArticle: function(button) {
		var win = button.up('window'),
			form = win.down('form'),
			record = form.getRecord(),
			values = form.getValues();

		record.set(values);
		win.close();
		this.getArticlesStore().sync();
		this.getArticlesStore().reload();
	},
	
	/* from articlesMenu */
	openOrders: function(item, e, eOpts){
		var menu = item.up('menu');
		var record = menu.currentRecord;
		var mainTabs = Ext.ComponentQuery.query('maintabs')[0];
		var orderArticlesList = Ext.widget('orderArticleList',{
			title: 'Article: ' + record.get('CODE')
		});
		orderArticlesList.getStore().getProxy().setExtraParam('article', record.get("ID"));
		mainTabs.add(orderArticlesList);
		orderArticlesList.getStore().load(/*{params: { article : record.get("ID") }}*/);
	},	
	
	/* from OrderArticleList */
	editOrderArticle: function(grid, record) {
		var view = Ext.widget('orderArticleEdit');
		var form = view.down('form');
		form.referencedStore = grid.getStore();
		form.loadRecord(record);
	},
	
	createOrderArticleContextMenu: function(grid, record, item, index, e, eOpts ){
		e.preventDefault(); 
		var menu = Ext.widget('orderArticleMenu');
		menu.currentRecord = record;
		menu.showAt(e.getXY());
	},
	
	/* from OrderArticleEdit window */
	updateOrderArticle: function(button) {
		var win = button.up('window'),
			form = win.down('form'),
			record = form.getRecord(),
			values = form.getValues();

		record.set(values);
		win.close();
		//check that the store actually saw the record changing
		form.referencedStore.sync();
		form.referencedStore.reload();
	},
	
	/* from OrderArticleMenu */
	openOrder: function(item, e, eOpts){
		var menu = item.up('menu');
		var record = menu.currentRecord;
		var mainTabs = Ext.ComponentQuery.query('maintabs')[0];
		var sCode= record.get('ORDER_SYSTEM_CODE');
		var singleOrderPanel = Ext.widget('singleOrderPanel',{
			title: 'Order: ' + sCode
		});
		// simulates filtering
		var filter = '[{"type":"string","comparison":"eq","value":"'+sCode+'","field":"SYSTEM_CODE"}]';
		singleOrderPanel.ordersList.getStore().getProxy().setExtraParam('filters', filter);
		mainTabs.add(orderArticlesList);
		orderArticlesList.getStore().load(/*{params: { article : record.get("ID") }}*/);
	}

});