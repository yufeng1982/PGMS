/**
 * @author FengYu
 */
Ext.define('EMP.project.settleAccounts.SettleAccountsAction' ,{
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
		if (me.isNew()) {
			var fileName = $('attachmentName').value
			var file = Ext.getCmp('attachment1').getValue();
			if (Strings.isEmpty(fileName) && !Strings.isEmpty(file)){
				msgarray.push({fieldname:"attachmentName", message: PRes["AttachmentName"]+PRes["AttachmentNotNull"], arg:null});
			}
			if (!Strings.isEmpty(fileName) && Strings.isEmpty(file)){
				msgarray.push({fieldname:"attachment1", message: PRes["GiveAttachment"]+PRes["GiveAttachmentNotNull"], arg:null});
			}
		} else {
			var fileName = $('attachmentName').value
			var filePath = $('filePath').value
			var file = Ext.getCmp('attachment1').getValue();
			if (Strings.isEmpty(fileName) && (!Strings.isEmpty(file) || !Strings.isEmpty(filePath))){
				msgarray.push({fieldname:"attachmentName", message: PRes["AttachmentName"]+PRes["AttachmentNotNull"], arg:null});
			}
			if (!Strings.isEmpty(fileName) && (Strings.isEmpty(file) && Strings.isEmpty(filePath))){ 
				msgarray.push({fieldname:"attachment1", message: PRes["GiveAttachment"]+PRes["GiveAttachmentNotNull"], arg:null});
			}
		}
		var file = Ext.getCmp('attachment1').getValue();
		if (!Strings.isEmpty(file)) {
			if (!VUtils.validateAttachmentType(file)) {
				msgarray.push({fieldname:"attachment1", message: PRes["Attachment"]+PRes["VAttachmentType"], arg:null});
			}
		}
		var adjustAmount = $('adjustAmount').value;
		if (!VUtils.isFloat(adjustAmount)) {
			msgarray.push({fieldname:"adjustAmount", message: PRes["AdjustAmount"] + ":" + PRes["VNumber"], arg:null});
		}
		if (adjustAmount <= 0) {
			msgarray.push({fieldname:"adjustAmount", message: PRes["AdjustAmount"] + ":" + PRes["VGreaterThanZero"], arg:null});
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
	projectOnchange : function (id, text, data, obj) {
		$('contractCode').value = "";
		$('contractAmount').value = "";
		$('cooperation').value = "";
		$('adjustAmount').value = "";
		$('settleAmount').value = "";
		CUtils.setSSValue('contract',"", "");
		CUtils.setSSValue('cooperation',"","");
		return true;
	},
	contractOnchange : function (id, text, data, obj) {
		var project = CUtils.getSSValue('petrolStation');
		if (Strings.isEmpty(project)) {
			CUtils.setSSValue('petrolStation',data.project, data.projectText);
		}
		$('contractCode').value = data.code;
		$('contractAmount').value = data.amount;
		CUtils.setSSValue('cooperation',data.cooperation,data.cooperationText);
		if (data.adjustAmount < 0) {
			$('adjustAmount').value = -data.adjustAmount;
			CUtils.setSValue('adjustType','Sub')
		} else {
			$('adjustAmount').value = data.adjustAmount;
			CUtils.setSValue('adjustType','Add')
		}
		$('settleAmount').value = data.settleAccounts;
		
		return true;
	},
	initParams4Contract : function(){
		return {sf_EQ_petrolStation : CUtils.getSSValue('petrolStation'), sf_EQ_contractType : 'Main',sf_EQ_contractStatus :'Archive'};
	},
	adjustOnchange : function () {
		var adjustType = CUtils.getSValue('adjustType');
		var adjustAmount = $('adjustAmount').value;
		if (adjustType == 'Sub') {
			adjustAmount = -adjustAmount;
		}
		$('settleAmount').value = CUtils.add($('contractAmount').value, adjustAmount);
		return true;
	}
});