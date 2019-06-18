/**
 * @author FengYu
 */
Ext.define('EMP.project.YearBudgetAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	workAction : null, 
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	checkMsgCount : 0,
	checkComponentUnique : function (value) {
		var me = this;
		me.showLoadMark("fr_years", "Checking...");
		var params = {years : CUtils.iv(value)};
		Ext.Ajax.request({
		    url: '/app/pgm/project/yearBudgets/list/yearCheck',
		    params : params,
		    timeout: 60000,
		    success: function(response){
		    	var returnValue = response.responseText;
		    	if(returnValue == 'true'){
		    		var msgarray = [];
		    		VUtils.removeFieldErrorCls('years');
		    		VUtils.removeTooltip('years');
		    		msgarray.push({fieldname:'years', message:value + PRes["isExist"], arg: null});
		    		VUtils.processValidateMessages(msgarray);
		    		me.hideLoadMark(true); 
		    	} else {
		    		VUtils.removeFieldErrorCls('years');
		    		VUtils.removeTooltip('years');
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
	pab_onchange : function (id, text, data, obj) {
		$('budgets').value=data.amount;
		$('approveNo').value=data.code;
		CUtils.setSValue('budgetType','Invested')
		CUtils.setDateValue('approveDate', data.approveDate);
		$('projectCode').value=data.projectCode;
		$('projectName').value=data.projectName;
		return true;
	}
});