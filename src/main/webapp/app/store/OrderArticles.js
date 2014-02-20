Ext.define('Orders.store.OrderArticles', {

	extend: 'Ext.data.Store',
	
	model: 'Orders.model.OrderArticle',
	
	pageSize: 100,

	proxy: {
		type: 'ajax',
		api:{
			read: 'article/orderArticleList.do',
			update: 'article/saveOrderEntry.do',
		},
		remoteSort: true,
		reader: {
			type: 'json',
			root: 'orderArticles',
			successProperty: 'success'
		},
		simpleSortMode: true
	}

});