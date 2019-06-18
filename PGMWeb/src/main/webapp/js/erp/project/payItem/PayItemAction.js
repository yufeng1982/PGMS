/**
 * @author FengYu
 */
Ext.define('EMP.project.contract.PayItemAction' ,{
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
		$('settleAccounts').disabled = false;
		$('adjustAmount').disabled = false;
		CUtils.disableCmp('attachment', false);
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		var me = this;
		var attachment1 = Ext.getCmp('attachment1').getValue();
		var attachment2 = Ext.getCmp('attachment2').getValue();
		if (me.isNew()) {
			if (Strings.isEmpty(attachment1)) {
				msgarray.push({fieldname:"attachment1", message: PRes["Attachment1"]+PRes["NotEmpty"], arg:null});
			}
			if (Strings.isEmpty(attachment2)) {
				msgarray.push({fieldname:"attachment2", message: PRes["Attachment2"]+PRes["NotEmpty"], arg:null});
			}
		}
		if (!Strings.isEmpty(attachment1)) {
			if (!VUtils.validateAttachmentType(attachment1)) {
				msgarray.push({fieldname:"attachment1", message: PRes["Attachment1"]+PRes["VAttachmentType"], arg:null});
			}
		}
		if (!Strings.isEmpty(attachment2)) {
			if (!VUtils.validateAttachmentType(attachment2)) {
				msgarray.push({fieldname:"attachment2", message: PRes["Attachment2"]+PRes["VAttachmentType"], arg:null});
			}
		}
		var amount = $('contractAmount').value;
		var settleAmount = $('settleAccounts').value;
		var payCounts = $('payCount').value ;
		var onePay = $('onePay').value;
		var twoPay = $('twoPay').value;
		var threePay = $('threePay').value;
		var fourPay = $('fourPay').value;
		var paidAmount = $('paidAmount').value;
		var requestAmount = $('requestAmount').value;
		if (!VUtils.isFloat(requestAmount)) {
			msgarray.push({fieldname:"requestAmount", message: PRes["RequestAmount"] + ":" + PRes["VNumber"], arg:null});
		}
		if (requestAmount <= 0) {
			msgarray.push({fieldname:"requestAmount", message: PRes["RequestAmount"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		
		var pays = [];
		if (onePay != 0) {
			pays.push(onePay);
		}
		if (twoPay != 0) {
			pays.push(twoPay);
		}
		if (threePay != 0) {
			pays.push(threePay);
		}
		if (fourPay != 0) {
			pays.push(fourPay);
		}
		debugger;
		if (payCounts == 0) {
			var oneP = pays.length == 3 || pays.length == 4 ?  CUtils.div(requestAmount, amount) * 100 : CUtils.div(requestAmount, settleAmount) * 100;
			if (oneP > pays[0]) {
				msgarray.push({fieldname:"requestAmount", message: "第一次付款比例为：" + pays[0] + "%, 此次应付款不能超过：" + CUtils.dv(CUtils.div(CUtils.mul(amount, pays[0]),100)), arg:null});
			}
		}
		if (payCounts == 1) {
			var twoP =  pays.length == 4 ?  CUtils.div(CUtils.add(requestAmount, paidAmount), amount) * 100 : CUtils.div(CUtils.add(requestAmount, paidAmount), settleAmount) * 100;
			var totalTwoP = CUtils.add(pays[0], pays[1])
			if (twoP > totalTwoP) {
				msgarray.push({fieldname:"requestAmount", message: "已付款：" + paidAmount, arg:null});
				msgarray.push({fieldname:"requestAmount", message: "第二次付款比例为：" + pays[1] + "% 总付款比例不能超过 " + totalTwoP + "%", arg:null});
				msgarray.push({fieldname:"requestAmount", message: "此次应付款不能超过：" + CUtils.dv(CUtils.sub(CUtils.div(CUtils.mul(amount, totalTwoP), 100), paidAmount)), arg:null});
			}
		}
		if (payCounts == 2) {
			var threeP = CUtils.div(CUtils.add(requestAmount, paidAmount), settleAmount) * 100;
			var totalThreeP = CUtils.add(pays[0], pays[1], pays[2])
			if (threeP > totalThreeP) {
				msgarray.push({fieldname:"requestAmount", message: "已付款：" + paidAmount, arg:null});
				msgarray.push({fieldname:"requestAmount", message: "第三次付款比例为：" + pays[2] + "% 总付款比例不能超过" + totalThreeP + "%", arg:null});
				msgarray.push({fieldname:"requestAmount", message: "此次应付款不能超过：" + CUtils.dv(CUtils.sub(CUtils.div(CUtils.mul(settleAmount, totalThreeP), 100), paidAmount)), arg:null});
			}
		}
		if (payCounts == 3) {
			var fourP = CUtils.div(CUtils.add(requestAmount, paidAmount), settleAmount) * 100;
			var totalFourP = CUtils.add(pays[0], pays[1], pays[2], pays[3])
			if (fourP > totalFourP) {
				msgarray.push({fieldname:"requestAmount", message: "已付款：" + paidAmount, arg:null});
				msgarray.push({fieldname:"requestAmount", message: "第四次付款比例为：" + pays[3] + "% 总付款比例不能超过" + totalFourP + "%", arg:null});
				msgarray.push({fieldname:"requestAmount", message: "此次应付款不能超过：" + CUtils.dv(CUtils.sub(CUtils.div(CUtils.mul(settleAmount, totalFourP), 100), paidAmount)), arg:null});
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
	initParams4CooperationAccount : function () {
		return {cooperationId : CUtils.getSSValue('cooperation')};
	},
	projectOnchange : function (id, text, data, obj) {
		$('contractCode').value = "";
		$('contractAmount').value = "";
		$('cooperation').value = "";
		CUtils.setSSValue('cooperationAccount', '','');
		$('settleAccounts').value = "";
		$('adjustAmount').value = "";
		CUtils.setSSValue('contract',"", "");
		CUtils.setSSValue('cooperation',"","");
		$('payCount').value = "";
		return true;
	},
	contractOnchange : function (id, text, data, obj) {
		if (data.payCounts == 4) { // 最多4次申请
			CUtils.infoAlert(PRes["VRequestCount"]);
			return false;
		}
		if (!CUtils.isTrueVal(data.continueRequest)) {
			CUtils.infoAlert(PRes["VConfirmRequest"]);
			return false;
		}
		$('onePay').value = data.onePercent;
		$('twoPay').value = data.twoPercent;
		$('threePay').value = data.threePercent;
		$('fourPay').value = data.fourPercent;
		$('paidAmount').value = data.payAmount;
		var project = CUtils.getSSValue('petrolStation');
		if (Strings.isEmpty(project)) {
			CUtils.setSSValue('petrolStation',data.project, data.projectText);
		}
		$('contractCode').value = data.code;
		$('contractAmount').value = data.amount;
		CUtils.setSSValue('cooperation',data.cooperation,data.cooperationText);
		CUtils.setSSValue('cooperationAccount', data.cooperationAccount,data.receiveNo);
		$('settleAccounts').value = data.settleAccounts;
		$('adjustAmount').value = data.adjustAmount;
		$('payCount').value = data.payCounts;
		$('contractPayAmount').value  = data.payAmount;
		return true;
	},
	initParams4Contract : function(){
		return {sf_EQ_petrolStation : CUtils.getSSValue('petrolStation'), sf_EQ_contractType : 'Main',sf_EQ_contractStatus :'Archive'};
	}
});