function page_OnLoad() {
	PRes["isExist"] = "${f:getText('Com.Validate.isExist')}";
	PRes["OileName"] = "${f:getText('Com.OilEName')}";
	PRes["SAPCode"] = "${f:getText('Com.SAPCode')}";
	PRes["CNCode"] = "${f:getText('Com.CNCode')}";
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.PetrolStationAction();
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