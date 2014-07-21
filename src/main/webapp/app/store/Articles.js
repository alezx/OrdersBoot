Ext.define('Orders.store.Articles', {

	extend: 'Ext.data.Store',
	
	model: 'Orders.model.Article',
	
	autoLoad: true,
	
	remoteSort: true,
	
	pageSize: 100,

	proxy: {
		type: 'ajax',
		api:{
			read: 'article/articleList.do',
			update: 'article/saveArticle.do'
		},
		remoteSort: true,
		reader: {
			type: 'json',
			root: 'articles',
			successProperty: 'success'
		},
		simpleSortMode: true
	}

});