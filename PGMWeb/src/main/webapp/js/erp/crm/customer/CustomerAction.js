Ext.define('PGM.crm.CustomerAction' ,{
	extend : 'ERP.FormAction',
	additionalLineGrid : null,
	
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;	
	},
	
	formProcessingBeforeSave : function() {
		
	},
	formValidationBeforeSave : function() {

	}
});