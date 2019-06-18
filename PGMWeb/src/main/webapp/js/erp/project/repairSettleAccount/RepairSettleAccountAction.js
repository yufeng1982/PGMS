/**
 * @author FengYu
 */
Ext.define('EMP.project.repairSettleAccount.RepairSettleAccountAction' ,{
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
		var changeAmount = $('changeAmount').value;
		if (!VUtils.isFloat(changeAmount)) {
			msgarray.push({fieldname:"changeAmount", message: PRes["ChangeAmount"] + ":" + PRes["VNumber"], arg:null});
		}
		var hseAmount = $('hseAmount').value;
		if (!VUtils.isFloat(hseAmount)) {
			msgarray.push({fieldname:"hseAmount", message: PRes["HseAmount"] + ":" + PRes["VNumber"], arg:null});
		}
		
		var file1 = Ext.getCmp('archive1').getValue();
		if (!Strings.isEmpty(file1)) {
			if (!VUtils.validatePDFType(file1)) {
				msgarray.push({fieldname:"archive1-inputEl", message: PRes["ChangeFile"]+PRes["VAttachmentType"], arg:null});
			}
		}
		var file2 = Ext.getCmp('archive2').getValue();
		if (!Strings.isEmpty(file2)) {
			if (!VUtils.validatePDFType(file2)) {
				msgarray.push({fieldname:"archive2-inputEl", message: PRes["HSEFile"]+PRes["VAttachmentType"], arg:null});
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
	repairOrderOnchange : function (id, text, data, obj) {
		$('createUser').value = data.createUser;
		$('contact').value = data.contact;
		$('thisBudget').value = data.budget;
		$('settleAccount').value = data.budget;
		$('changeAmount').value = 0.00;
		CUtils.setSSValue('petrolStation',data.projectId,data.project);
		CUtils.setSSValue('asset',data.assetId,data.asset);
		CUtils.setSValue('breakdownReason',data.breakdownReasonCode, data.breakdownReason);
		CUtils.setSValue('repairType',data.repairTypeCode, data.repairType);
		CUtils.setSValue('repairStatus',data.repairStatusCode, data.repairStatus);
		$('thisBudgetUpper').value = this.changeToBig($('thisBudget').value);
		$('settleAccountUpper').value = this.changeToBig($('settleAccount').value);
		Ext.getCmp('archive1').reset();
		Ext.getCmp('archive2').reset();
		Ext.getCmp('archive1').setDisabled(true);
		Ext.getCmp('archive2').setDisabled(true);
		VUtils.removeAllErrorFields();
		return true;
	},
	initParams4Repair : function () {
		return {sf_EQ_repairStatus : 'Closed', sf_EQ_createdBy : $('user').value};
	},
	amountOnChange : function (obj) {
		$(obj.id+'Upper').value = this.changeToBig($(obj.id).value);
	},
	changeToBig : function (num) {
		var strOutput = "";  
		var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';  
	 	num += "00";  
	 	var intPos = num.indexOf('.');  
	 	if (intPos >= 0) num = num.substring(0, intPos) + num.substr(intPos + 1, 2);  
	 	strUnit = strUnit.substr(strUnit.length - num.length);  
	 	for (var i=0; i < num.length; i++)  {
	 		strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);   
	 	}
	 	return strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");  
	},
	approve : function (action) {
		var me = this;
		me.workAction = action;
		beginWaitCursor(PRes["Saving"], false);
		PAction.submitForm(me.workAction);
		        	  
	},
	initParams4Project : function(){
		return {sf_IN_id : $('ids').value};
	},
	showHistory : function () {
		var grid =  GUtils.initErpGrid("GRID_RPSA_ID", {entityId: $('entityId').value}, G_RPSA_CONFIG);
		var me = this;
		if(!me.historyWin){
			me.historyWin = Ext.create('Ext.window.Window', {
				id : 'historyWin',
			    height: 600,
			    width: 800,
			    modal :  true,
			    closable : false,
			    layout:'fit',
			    title : PRes["ShowHistory"],
			    items : [grid],
			    fbar: [{
		        	   type: 'button', 
		        	   text: PRes["cancel"],
		        	   handler : function(){
		        		   Ext.getCmp('historyWin').hide();
		        	   }
			    }]
			});
			me.historyWin.on('show',function(win,opts){
				Ext.getCmp('GRID_RPSA_ID').store.load();
			});
			me.historyWin.show();
		} else {
			if(!me.historyWin.isVisible()){
				me.historyWin.show();
			}
		}
	},
	modifyOnChange : function () {
		$('changeAmount').value = $('changeAmount').value.replace(/\+/g,'');
		if ($('changeAmount').value != 0) {
			Ext.getCmp('archive1').setDisabled(false);
		} else {
			Ext.getCmp('archive1').setDisabled(true);
			Ext.getCmp('archive1').reset();
		}
		$('settleAccount').value = CUtils.add($('thisBudget').value, $('changeAmount').value);
		$('settleAccountUpper').value = this.changeToBig($('settleAccount').value);
	},
	hseOnChange : function () {
		$('hseAmount').value = $('hseAmount').value.replace(/\+/g,'');
		if ($('hseAmount').value != 0) {
			Ext.getCmp('archive2').setDisabled(false);
		} else {
			Ext.getCmp('archive2').setDisabled(true);
			Ext.getCmp('archive2').reset();
		}
	}
});