function page_OnLoad() {
	PRes["FlowDefinitionListNotEmpty"] = "${f:getText('Com.FlowDefinitionList.NotEmpty')}";
	PRes["DeployDefinition"] = "${f:getText('Com.DeployDefinition')}";
	PRes["VRolesAndUser"]  = "${f:getText('Com.VRolesAndUsers')}";
	PRes["Deploying"]  = "${f:getText('Com.Deploying')}";
	var actionBarItems = [];
//	var deploy = new Ext.Action( {
//		text : PRes["DeployDefinition"],
//		id : 'deployBtn',
//		iconCls: 'ss_sprite ss_wrench',
//		handler: function() {
//			beginWaitCursor(PRes["Deploying"], false);
//			PAction.submitForm("deploy");
//		}
//	});
//	if($('entityId').value != DEFAULT_NEW_ID) actionBarItems[3] = new ERP.Button(deploy);
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.flowDefinition.FlowDefinitionAction();
	var flowLineGrid = PAction.initEPGrid(GRID_ID, {flowDefinitionId : $('entityId').value});
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 70,
				id : "general-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				id : "WORK_FLOW_PANEL_ID",
				collapseFirst : false,
				height : 450,
				layout : 'fit',
				title : ss_icon('ss_application_form') + "${f:getText('Com.FlowDefinition.List')}",
				dockedItems: [{
	                xtype: 'toolbar',
	                items: [{
	                	iconCls: 'ss_sprite ss_add',
	                    id : 'plus',
	                    text: PRes["add"],
	                    handler: function(event, toolEl, panel){
	                    	PAction.addLine();
	                    }
	                }, {
	                	iconCls: 'ss_sprite ss_delete',
	                    id : 'minus',
	                    text: PRes["delete"],
	                    handler: function(event, toolEl, panel){
	                    	 PAction.removeLine();
	                    }
	                }]
	            }],
				items : [flowLineGrid]
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
	if (!Strings.isEmpty('${infos}')) {
		CUtils.warningAlert('${infos}');
	}
}