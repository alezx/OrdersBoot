Ext.define('Orders.model.OrderEntry', {

	extend: 'Ext.data.Model',
	fields: [
	         {name: 'ID', type: 'int'},
	         {name: 'ARTICLE', type: 'int'},
	         {name: 'QUANTITY', type: 'int'},
	         'ARTICLE_CODE',
	         {name: 'NEW_QUANTITY', type: 'int'},
	]
	
});