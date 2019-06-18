/**
 * @author FengYu
 */
Ext.define('EMP.project.PetrolStationAction' ,{
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
	},
	checkComponentUnique : function (obj) {
		var me = this;
		me.showLoadMark("fr_" + obj.id, "Checking...");
		var params = {};
		var url;
		if (obj.id == 'code') {
			params.code = $(obj.id).value;
			url = '/app/pgm/project/petrolStation/list/codeCheck'
		} else if (obj.id == 'sapCode') {
			params.sapCode = $(obj.id).value;
			url = '/app/pgm/project/petrolStation/list/sapcodeCheck'
		} else  {
			params.cnCode = $(obj.id).value;
			url = '/app/pgm/project/petrolStation/list/cncodeCheck'
		}
		Ext.Ajax.request({
		    url: url,
		    params : params,
		    timeout: 60000,
		    success: function(response){
		    	var returnValue = response.responseText;
		    	if(returnValue == 'true'){
		    		var msgarray = [];
		    		VUtils.removeFieldErrorCls(obj.id);
		    		VUtils.removeTooltip(obj.id);
		    		if (obj.id == 'code') {
		    			msgarray.push({fieldname:obj.id, message:PRes["OileName"] + obj.value + PRes["isExist"], arg: null});
		    		} else if (obj.id == 'sapCode') {
		    			msgarray.push({fieldname:obj.id, message:PRes["SAPCode"] + obj.value + PRes["isExist"], arg: null});
		    		} else  {
		    			msgarray.push({fieldname:obj.id, message:PRes["CNCode"] + obj.value + PRes["isExist"], arg: null});
		    		}
		    		VUtils.processValidateMessages(msgarray);
		    		me.hideLoadMark(true); 
		    	} else {
		    		VUtils.removeFieldErrorCls(obj.id);
		    		VUtils.removeTooltip(obj.id);
		    		me.hideLoadMark(false); 
		    	}
		    },
		});
		return true;
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
		$('pct').value = data.pct;
		$('address').value = data.address;
		return true;
	}
});