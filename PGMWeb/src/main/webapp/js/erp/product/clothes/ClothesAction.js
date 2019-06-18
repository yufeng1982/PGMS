Ext.define('PGM.photo.ClothesAction' ,{
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
		if (!this.isNew() && Strings.isEmpty($('imagePath').value)) {
			msgarray.push({fieldname:"imagePath", message: PRes["VImagePath"], arg:null});
		}
		return msgarray;
	}
});