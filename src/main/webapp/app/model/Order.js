Ext.define('Orders.model.Order', {

	extend: 'Ext.data.Model',
	fields: [
	         {name: 'id', type: 'int'},
	         'code',
	         'customer',
	         'customerCode',
	         {name: 'total', type: 'float', useNull: true},
	         {name: 'percAvailableProd', type: 'float', useNull: true},
	         {name: 'percAvailableWare', type: 'float', useNull: true},
	         'ready',
	         'priority',
	         {name: 'twelveMonths', type : 'boolean'}
	]
	
});