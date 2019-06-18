function page_OnLoad() {
	PRes["File"] = "${f:getText('Com.File')}";
	PRes["VBusinessLicense"] = "${f:getText('Com.Validation.BusinessLicense.NotNull')}";
	PRes["VUploadFileType"] = "${f:getText('Com.VUploadFileType')}";
	PRes["BusinessLicense"] = "${f:getText('Com.BusinessLicense')}";
	PRes["VBankCardAccount"] = "${f:getText('Com.Validation.BankCardAccount')}";
	PRes["ReceiveNo"] = "${f:getText('Com.ReceiveNo')}";
	
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.assetsCategory.AssetsCategoryAction();
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 240,
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