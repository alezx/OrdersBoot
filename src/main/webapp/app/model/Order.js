Ext.define('Orders.model.Order', {

	extend: 'Ext.data.Model',
	fields: [
	         {name: 'ID', type: 'int'},
	         'SYSTEM_CODE',
	         'CODE',
	         'CUSTOMER',
	         {name: 'FIRST_INSERT', type: 'date', dateFormat: 'Y-m-d H:i:s'},
	         {name: 'LAST_UPDATE', type: 'date', dateFormat: 'Y-m-d H:i:s'},
	         {name: 'TOTAL', type: 'float'},
	]
	
});