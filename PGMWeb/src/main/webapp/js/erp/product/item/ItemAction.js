Ext.define('PGM.photo.ItemAction' ,{
	extend : 'ERP.FormAction',
	
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;	
	},
	formProcessingBeforeSave : function() {

	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		return msgarray;
	}
});