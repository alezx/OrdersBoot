Ext.define('Orders.model.OrderArticle', {

	extend: 'Ext.data.Model',
	fields: [
	         {name: 'id', type: 'int'}, //is the OrderEntry id
	         //{name: 'order', type: 'int'}, // order id
	         {name: 'article', type: 'int'}, // article id
	         {name: 'orderCode', type: 'string'},
	         //{name: 'ORDER_SYSTEM_CODE', type: 'string'},
	         {name: 'quantity', type: 'int'},
	         {name: 'residualWarehouseQuantity', type: 'int'},
	         'residualProductionQuantity'
	]
	
});