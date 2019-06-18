/**
 * @author FengYu
 */
Ext.define('EMP.project.repairOrder.RepairOrderAction' ,{
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
		$('budget').disabled = false;
		var repairType = CUtils.getSValue('repairType');
		if (repairType != 'Small' && $('taskCode').value == 'G000012') {
			$('settleAccount').disabled = false;
			$('thisBudgetUpper').disabled = false;
		}
	},
	formValidationBeforeSave : function() {
		var me = this;
		var msgarray = [];
		if ($('actions').value == 'reject') return msgarray;
		var budget = $('budget').value;
		var repairType = CUtils.getSValue('repairType');
		// 通用验证
		if (me.isNew()) {
			for (var i = 1; i <= 3; i++) {
				var file = Ext.getCmp('archive' + i).getValue();
				if (Strings.isEmpty(file)){
					msgarray.push({fieldname:"archive" + i, message: PRes["Image"]+i+PRes["AttachmentNotNull"], arg:null});
				}
			}
		} else {
			for (var i = 1; i <= 3; i++) {
				var filePath = $('filePath' + i).value
				var file = Ext.getCmp('archive' + i).getValue();
				if (Strings.isEmpty(file) && Strings.isEmpty(filePath)){ 
					msgarray.push({fieldname:"archive" + i, message: PRes["Image"]+i+PRes["AttachmentNotNull"], arg:null});
				}
			}
		}
		for (var i = 1; i <= 3; i++) {
			var file = Ext.getCmp('archive' + i).getValue();
			if (!Strings.isEmpty(file)) {
				if (!VUtils.validateImageType(file)) {
					msgarray.push({fieldname:"archive" + i, message: PRes["Image"]+i+PRes["VUploadFileType"], arg:null});
				}
			}
		}
		
		if (repairType == 'Small') {
			if (Strings.isEmpty(budget)){
				msgarray.push({fieldname:"budget", message: PRes["RepairBudget"] + PRes["NotEmpty"], arg:null});
			} else if (!VUtils.isPositiveFloatWithoutZero(budget)) {
				msgarray.push({fieldname:"budget", message: PRes["RepairBudget"] + ":" + PRes["VNumber"], arg:null});
			}
			if ($('taskCode').value == 'S000004') {
				var actualAmount = $('actualAmount').value;
				if (!VUtils.isPositiveFloatWithoutZero(actualAmount)) {
					msgarray.push({fieldname:"actualAmount", message: PRes["RepairBudget"] + ":" + PRes["VNumber"], arg:null});
				}
				for (var i = 4; i <= 6; i++) {
					var file = Ext.getCmp('archive' + i).getValue();
					if (Strings.isEmpty(file)){
						msgarray.push({fieldname:"archive" + i, message: PRes["ImageFinish"]+(i-3)+PRes["AttachmentNotNull"], arg:null});
					} else if (!VUtils.validateImageType(file)){
						msgarray.push({fieldname:"archive" + i, message: PRes["ImageFinish"]+(i-3)+PRes["VUploadFileType"], arg:null});
					}
				}
			}
		} else {
			if ($('taskCode').value == 'G000002') {
				var currentBudget = $('currentBudget').value;
				if (!VUtils.isPositiveFloatWithoutZero(currentBudget)) {
					msgarray.push({fieldname:"currentBudget", message: PRes["CurrentBudget"] + ":" + PRes["VNumber"], arg:null});
				}
				var repairTime = $('repairTime').value;
				if (!VUtils.isPositiveFloatWithoutZero(repairTime)) {
					msgarray.push({fieldname:"repairTime", message: PRes["RepairTime"] + ":" + PRes["VNumber"], arg:null});
				}
				var file = Ext.getCmp('archive7').getValue();
				if (Strings.isEmpty(file)){
					msgarray.push({fieldname:"archive7", message: PRes["ReapirBudgetFile"]+PRes["AttachmentNotNull"], arg:null});
				} else if (!VUtils.validatePDFType(file)){
					msgarray.push({fieldname:"archive7", message: PRes["ReapirBudgetFile"]+PRes["VAttachmentType"], arg:null});
				}
				if (repairTime > 1) {
					var file = Ext.getCmp('archive8').getValue();
					if (Strings.isEmpty(file)){
						msgarray.push({fieldname:"archive8", message: PRes["RepairSolutionsFile"]+PRes["AttachmentNotNull"], arg:null});
					} else if (!VUtils.validatePDFType(file)){
						msgarray.push({fieldname:"archive8", message: PRes["RepairSolutionsFile"]+PRes["VAttachmentType"], arg:null});
					}
				}
			} else if ($('taskCode').value == 'G000011') {
				for (var i = 4; i <= 6; i++) {
					var file = Ext.getCmp('archive' + i).getValue();
					if (Strings.isEmpty(file)){
						msgarray.push({fieldname:"archive" + i, message: PRes["ImageFinish"]+(i-3)+PRes["AttachmentNotNull"], arg:null});
					} else if (!VUtils.validateImageType(file)){
						msgarray.push({fieldname:"archive" + i, message: PRes["ImageFinish"]+(i-3)+PRes["VUploadFileType"], arg:null});
					}
				}
			} else  if ($('taskCode').value == 'G000012'){
				if($('adjust').checked){
					if (!VUtils.isPositiveFloatWithoutZero($('changeAmount').value)) {
						msgarray.push({fieldname:"changeAmount", message: PRes["ChangeAmount"] + ":" + PRes["VNumber"], arg:null});
					}
					var file = Ext.getCmp('archive9').getValue();
					if (Strings.isEmpty(file)){
						msgarray.push({fieldname:"archive9", message: PRes["ChangeAmountAttachment"]+PRes["AttachmentNotNull"], arg:null});
					} else if (!VUtils.validatePDFType(file)){
						msgarray.push({fieldname:"archive9", message: PRes["ChangeAmountAttachment"]+PRes["VAttachmentType"], arg:null});
					}
				}
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
	asstOnchange : function (id, text, data, obj) {
		$('brand').value = data.brand;
		$('specification').value = data.specification;
		$('vendor').value = data.vendor;
		return true;
	},
	currentBudgetOnchange : function () {
		$('budget').value = $('currentBudget').value
		return true;
	},
	approve : function (action) {
		var me = this;
		me.workAction = action;
		
		beginWaitCursor(PRes["Saving"], false);
		        		  
		PAction.submitForm(me.workAction);
		        	  
	},
	projectOnchange : function (id, text, data, obj) {
		CUtils.disableCmp('asset', false);
		return true;
	},
	adjustOnclick : function () {
		if($('adjust').checked){
			CUtils.disableCmp('adjustType',false);
			$('changeAmount').disabled=false;
			$('opinions12').disabled=false;
			CUtils.disableCmp('archive9', false);
		} else {
			CUtils.setSValue('adjustType','Add')
			$('settleAccount').value=0;
			$('changeAmount').value=0;
			$('opinions12').value='';
			$('thisBudgetUpper').value='';
			Ext.getCmp('archive9').setValue('');
			Ext.getCmp('archive9').setDisabled(true);
			CUtils.disableCmp('adjustType',true);
			$('changeAmount').disabled=true;
			$('opinions12').disabled=true;
		
		}
	},
	changeAmountOnChange : function() {
		if (CUtils.getSValue('adjustType') == 'Add') {
			$('settleAccount').value =  CUtils.add($('currentBudget').value, $('changeAmount').value);
			$('thisBudgetUpper').value = this.changeToBig($('settleAccount').value);
		} else {
			$('settleAccount').value =  CUtils.sub($('currentBudget').value, $('changeAmount').value);
			$('thisBudgetUpper').value = this.changeToBig($('settleAccount').value);
		}
		return true;
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
	initParams4Asst : function(){
		return {sf_EQ_petrolStation : CUtils.getSSValue('petrolStation')};
	},
	initParams4Project : function(){
		return {sf_IN_id : $('ids').value};
	},
	showHistory : function () {
		var grid =  GUtils.initErpGrid("GRID_RPO_ID", {entityId: $('entityId').value}, G_RPO_CONFIG);
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
				Ext.getCmp('GRID_RPO_ID').store.load();
			});
			me.historyWin.show();
		} else {
			if(!me.historyWin.isVisible()){
				me.historyWin.show();
			}
		}
	}
});