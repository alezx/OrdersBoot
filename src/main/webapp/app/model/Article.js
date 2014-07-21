Ext.define('Orders.model.Article', function(){






	return {

	extend: 'Ext.data.Model',
	fields: [
	         {name: 'id', type: 'int'},
	         {name: 'code', type: 'string'},

	         {name: 'series', type: 'string', useNull: true},
	         {name: 'title', type: 'string', useNull: true},
	         {name: 'format', type: 'string', useNull: true},
	         {name: 'interior', type: 'string', useNull: true},

	         {name: 'price', type: 'float', useNull: true},

	         {name: 'productionQuantity', type: 'int', useNull: true},
	         {name: 'warehouseQuantity', type: 'int', useNull: true},
	         {name: 'requestedQuantity', type: 'int', useNull: true}	         
	         
	]
	
	};
}());