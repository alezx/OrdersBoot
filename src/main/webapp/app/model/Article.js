Ext.define('Orders.model.Article', {

	extend: 'Ext.data.Model',
	fields: [
	         {name: 'ID', type: 'int'},
	         {name: 'CODE', type: 'string'},
	         {name: 'PRICE', type: 'float'},
	         {name: 'PRD_Q', type: 'int'},
	         {name: 'WAREHOUSE_Q', type: 'int'},
	         {name: 'REQUESTED_Q', type: 'int'},
	         
	         {name: 'SERIES', type: 'string'},
	         {name: 'TITLE', type: 'string'},
	         {name: 'FORMAT', type: 'string'},
	         {name: 'INTERIOR', type: 'string'},
	         
	         {name: 'RESERVEDSTOCK_Q', type: 'int'},
	         {name: 'AVAILABLEONHAND_Q', type: 'int'},
	         {name: 'AVAILABLEFORSALE_Q', type: 'int'},
	         {name: 'RESERVATIONSTATUS', type: 'string'},
	         
	         {name: 'CASE_Q', type: 'int'}
	         
	]
	
});