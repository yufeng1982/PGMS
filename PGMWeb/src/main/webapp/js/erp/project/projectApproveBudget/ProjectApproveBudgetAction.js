/**
 * @author FengYu
 */
Ext.define('EMP.project.ProjectApproveBudgetAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	workAction : null, 
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	checkMsgCount : 0,
	formProcessingBeforeSave : function() {
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		var me = this;
		var attachment = Ext.getCmp('attachment1').getValue();
		if (me.isNew()) {
			if (Strings.isEmpty(attachment)) {
				msgarray.push({fieldname:"attachment1", message: PRes["PABAttachment"]+PRes["NotEmpty"], arg:null});
			}
		}
		if (!Strings.isEmpty(attachment)) {
			if (!VUtils.validatePDFType(attachment)) {
				msgarray.push({fieldname:"attachment1", message: PRes["PABAttachment"]+PRes["VAttachmentType"], arg:null});
			}
		}
		return msgarray;
	},
	loadMarkShowEvent : function () {
		Ext.getCmp('okBtn').setDisabled(true);
		Ext.getCmp('applyBtn').setDisabled(true);
	},
	loadMarkHideEvent : function (isDisabled) {
		Ext.getCmp('okBtn').setDisabled(isDisabled);
		Ext.getCmp('applyBtn').setDisabled(isDisabled);
	},
	projcet_onchange : function (id, text, data, obj) {
		$('projectName').value = data.name;
		return true;
	}
});