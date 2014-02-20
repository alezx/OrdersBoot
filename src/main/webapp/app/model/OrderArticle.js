Ext.define('Orders.model.OrderArticle', {

	extend: 'Ext.data.Model',
	fields: [
	         {name: 'ID', type: 'int'}, //is the OrderEntry id
	         {name: 'ORDER', type: 'int'}, // order id
	         {name: 'ARTICLE', type: 'int'}, // article id
	         {name: 'ORDER_CODE', type: 'string'},
	         {name: 'ORDER_SYSTEM_CODE', type: 'string'},
	         {name: 'QUANTITY', type: 'int'},
	         {name: 'NEW_QUANTITY', type: 'int'}
	]
	
});