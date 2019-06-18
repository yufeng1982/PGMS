function page_OnLoad() {
	PRes["AttachmentName"] = "${f:getText('Com.AttachmentName')}";
	PRes["GiveAttachment"] = "${f:getText('Com.GiveAttachment')}";
	PRes["AttachmentNotNull"] = "${f:getText('Com.AttachmentNotNull')}";
	PRes["GiveAttachmentNotNull"] = "${f:getText('Com.GiveAttachmentNotNull')}";
	PRes["Attachment"] = "${f:getText('Com.Attachment')}";
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.AttachmentType')}";
	PRes["File"] = "${f:getText('Com.File')}";

	
	var actionBarItems = [];
	actionBarItems[80] = null;
	actionBarItems[100] = null;
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.payAccount.PayAccountAction();
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 160,
				id : "general-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				height : 280,
				id : "general-portal-one",
				title : ss_icon('ss_application_form') + "${f:getText('Com.OneRequest')}",
				contentEl : "divGeneralOne"
			},{
				xtype : "portlet",
				height : 280,
				id : "general-portal-two",
				title : ss_icon('ss_application_form') + "${f:getText('Com.TwoRequest')}",
				contentEl : "divGeneralTwo"
			},{
				xtype : "portlet",
				height : 280,
				id : "general-portal-three",
				title : ss_icon('ss_application_form') + "${f:getText('Com.ThreeRequest')}",
				contentEl : "divGeneralThree"
			},{
				xtype : "portlet",
				height : 280,
				id : "general-portal-four",
				title : ss_icon('ss_application_form') + "${f:getText('Com.FourRequest')}",
				contentEl : "divGeneralFour"
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
}