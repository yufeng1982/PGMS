function page_OnLoad() {
	PRes["AttachmentName"] = "${f:getText('Com.AttachmentName')}";
	PRes["GiveAttachment"] = "${f:getText('Com.GiveAttachment')}";
	PRes["AttachmentNotNull"] = "${f:getText('Com.AttachmentNotNull')}";
	PRes["GiveAttachmentNotNull"] = "${f:getText('Com.GiveAttachmentNotNull')}";
	PRes["Attachment"] = "${f:getText('Com.Attachment')}";
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.AttachmentType')}";
	PRes["File"] = "${f:getText('Com.File')}";
	PRes["VGreaterThanZero"] = "${f:getText('Com.Validation.GreaterThanZero')}";
	PRes["VNumber"] = "${f:getText('Com.Validate.VNumber')}";
	PRes["AdjustAmount"] = "${f:getText('Com.Contranct.AdjustAmount')}";
	Ext.create('Ext.form.field.File', {
		name: 'attachment1',
		id : 'attachment1',
        height : 22,
		width : 300,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.settleAccounts.SettleAccountsAction();
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 330,
				id : "general-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
	
}