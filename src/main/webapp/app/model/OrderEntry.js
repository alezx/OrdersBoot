Ext.define('Orders.model.OrderEntry', {

	extend: 'Ext.data.Model',
	fields: [
	         {name: 'id', type: 'int'},
	         {name: 'article.code'},
	         {name: 'article.series'},
	         {name: 'quantity', type: 'int', useNull: true},
	         {name: 'newQuantity', type: 'int', useNull: true},
	         //'code', // article code
	         //{name: 'NEW_QUANTITY', type: 'int'},
	         {name: 'residualWarehouseQuantity', useNull: true},
	         {name: 'residualProductionQuantity', useNull: true},
	         {name: 'ordersSoFar', useNull: true}
	]
	
});