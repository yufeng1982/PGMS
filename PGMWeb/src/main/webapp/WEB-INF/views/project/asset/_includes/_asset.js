function page_OnLoad() {

	PRes["Quantity"] = "${f:getText('Com.Quantity')}";
	PRes["VInteger"] = "${f:getText('Com.PositiveInteger')}";
	
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.asset.AssetAction();
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