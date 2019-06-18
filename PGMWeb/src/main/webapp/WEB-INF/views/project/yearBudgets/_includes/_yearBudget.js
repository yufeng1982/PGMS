function page_OnLoad() {
	PRes["isExist"] = "${f:getText('Validation.YearExist')}";
	PRes["Years"] = "${f:getText('Com.Years')}";
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.YearBudgetAction();
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