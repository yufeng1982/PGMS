function page_OnLoad() {
	PRes["AttachmentNotNull"] = "${f:getText('Com.AttachmentNotNull')}";
	PRes["Attachment1"] = "${f:getText('Com.Attachment1')}";
	PRes["Attachment2"] = "${f:getText('Com.Attachment2')}";
	PRes["Attachment3"] = "${f:getText('Com.Attachment3')}";
	PRes["Attachment4"] = "${f:getText('Com.Attachment4')}";
	PRes["VAttachment"] = "${f:getText('Com.Validation.Attachment.NotNull')}";
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.AttachmentType')}";
	PRes["VAttachment"] = "${f:getText('Com.Validation.Attachment.NotNull')}";
	PRes["VRequestCount"] = "${f:getText('Com.Validation.RequestCount')}";
	PRes["File"] = "${f:getText('Com.File')}";
	PRes["Confirm"] = "${f:getText('Com.Confirm')}";
	PRes["VAssetNo"] = "${f:getText('Com.Validation.AssetNo.NotNull')}";
	PRes["ActualPayAmount"] = "${f:getText('Com.Contranct.ActualPayAmount')}";
	PRes["VGreaterThanZero"] = "${f:getText('Com.Validation.GreaterThanZero')}";
	PRes["VNumber"] = "${f:getText('Com.Validate.VNumber')}";
	PRes["RequestAmount"] = "${f:getText('Com.RequestAmount')}";
	PRes["VConfirmRequest"] = "${f:getText('Com.Validation.ConfirmRequest')}";
	PRes["Reject"] = "${f:getText('Com.Reject')}";
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment1',
		id : 'attachment1',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment2',
		id : 'attachment2',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file1',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment3',
		id : 'attachment3',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file2',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment4',
		id : 'attachment4',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file3',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	var approve = new ERP.Button({
		id : 'approveBtn',
		text : PRes["Confirm"],
		iconCls: 'ss_sprite ss_accept',
		handler: function() {
			var msgarray = [];
			var paidAmount = $('contractPayAmount').value;
			var settleAmount = $('settleAccounts').value;
			var assetNo = $('assetNo').value;
			var actualAmount = $('payAmount').value;
			var attachment3 = Ext.getCmp('attachment3').getValue();
			var attachment4 = Ext.getCmp('attachment4').getValue();
			if (Strings.isEmpty(attachment3)) {
				msgarray.push({fieldname:"attachment3", message: PRes["Attachment3"]+PRes["NotEmpty"], arg:null});
			}
			if (Strings.isEmpty(attachment4)) {
				msgarray.push({fieldname:"attachment4", message: PRes["Attachment4"]+PRes["NotEmpty"], arg:null});
			}
			if (!Strings.isEmpty(attachment3)) {
				if (!VUtils.validateAttachmentType(attachment3)) {
					msgarray.push({fieldname:"attachment3", message: PRes["Attachment3"]+PRes["VAttachmentType"], arg:null});
				}
			}
			if (!Strings.isEmpty(attachment4)) {
				if (!VUtils.validateAttachmentType(attachment4)) {
					msgarray.push({fieldname:"attachment4", message: PRes["Attachment4"]+PRes["VAttachmentType"], arg:null});
				}
			}
			if (CUtils.dv(CUtils.div(paidAmount, settleAmount)) * 100 >= 95) {
				if (Strings.isEmpty(assetNo)) {
					msgarray.push({fieldname:"assetNo", message: PRes["VAssetNo"], arg:null});
				}
			}
			if (actualAmount <= 0) {
				msgarray.push({fieldname:"payAmount", message: PRes["ActualPayAmount"] + PRes["VGreaterThanZero"], arg:null});
			}
			if (!VUtils.isFloat(actualAmount)) {
				msgarray.push({fieldname:"payAmount", message: PRes["ActualPayAmount"] + ":" + PRes["VNumber"], arg:null});
			}
			if (msgarray.length > 0) {
				VUtils.processValidateMessages(msgarray);
			} else {
				beginWaitCursor(PRes["Saving"], false);
				PAction.submitForm("approve");
			}
		}
	});
	
	var reject = new ERP.Button({
		id : 'rejectBtn',
		text : PRes["Reject"],
		iconCls: 'ss_sprite ss_cancel',
		handler: function() {
			beginWaitCursor(PRes["Saving"], false);
			document.forms[0].action = '/app/pgm/project/payItem/form/${entityId}/reject'
			document.forms[0].submit();
		}
	});
	
	var actionBarItems = [];
	actionBarItems[52] = approve;
	actionBarItems[53] = reject;
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.contract.PayItemAction();
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 330,
				id : "general-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				height : 200,
				id : "approveGeneralPortal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.PayConfirm')}",
				contentEl : "divApproveGeneral"
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
	if (CUtils.getSValue('payType') == 'UnPaid') {
		$('payAmount').value = $('requestAmount').value;
	}
	if (CUtils.getSValue('payType') != 'UnPaid') {
		if (Ext.getCmp('approveBtn')) Ext.getCmp('approveBtn').setDisabled(true);
		if (Ext.getCmp('rejectBtn')) Ext.getCmp('rejectBtn').setDisabled(true);
		if (Ext.getCmp('attachment3')) Ext.getCmp('attachment3').setDisabled(true);
		if (Ext.getCmp('attachment4')) Ext.getCmp('attachment4').setDisabled(true);
		if ($('assetNo')) $('assetNo').disabled = true;
		if ($('payAmount')) $('payAmount').disabled = true;
	}
	if (CUtils.getSValue('payType') != 'Draft') {
		CUtils.disableCmp('okBtn', true);
		CUtils.disableCmp('applyBtn',true);
		Ext.getCmp('attachment1').setDisabled(true);
		Ext.getCmp('attachment2').setDisabled(true);
		$('requestAmount').disabled = true;
		$('payContents').disabled = true;
	} else {
		// 如果是草案状态 如果是财务访问时不能点击应用和确定按钮
		if (Ext.getCmp('approveBtn')) {
			CUtils.disableCmp('okBtn', true);
			CUtils.disableCmp('applyBtn',true);
		}
	}
	
}