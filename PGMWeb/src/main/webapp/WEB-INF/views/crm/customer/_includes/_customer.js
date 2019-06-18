function page_OnLoad() {

	
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new PGM.crm.CustomerAction();
	
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 200,
				id : "GENERAL_PANEL_ID",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
}