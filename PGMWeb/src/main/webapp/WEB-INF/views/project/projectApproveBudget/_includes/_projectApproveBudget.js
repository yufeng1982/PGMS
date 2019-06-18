function page_OnLoad() {
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.PDFAttachmentType')}";
	PRes["PABAttachment"] = "${f:getText('Com.PAB.FileName')}";
	PRes["File"] = "${f:getText('Com.File')}";

	Ext.create('Ext.form.field.File', {
		name: 'attachment1',
		id : 'attachment1',
        height : 22,
		width : 160,
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
	PAction = new EMP.project.ProjectApproveBudgetAction();
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 160,
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