function page_OnLoad() {
	PRes["employee"] = "${f:getText('Com.Employee.List')}";
	PAction = new EMP.hr.department.DepartmentAction({});
	var employeeGrid = PAction.initEMPGrid({departmentId : "${entityId}"});
	
	employeeGrid.on('itemdblclick', function(/* Ext.view.View*/ view, /*Ext.data.Model*/ record, /*HTMLElement*/ item, /*Number*/ index, /*Ext.EventObject*/ e ){
		PAction.gridItemDBClick(GUtils.getSelected(employeeGrid).get("employeeId"));
	});  
	
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	
	PApp =  new ERP.FormApplication({
		
		employeeGrid : employeeGrid,
	    		
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				id : "generalPanel",
				height : 120,
				title : ss_icon('ss_application_form') + PRes["general"],
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				id : "phonePanel",
				collapseFirst : false,
				height :250,
				title : ss_icon('ss_application_form') + PRes["employee"],
				layout : 'fit',	
				tools : [
				   {id : 'emp_plus',type : 'plus',qtip : PRes["add"],handler : function(event, toolEl, panel) {PAction.addEMPLine();}},
				   {id : 'emp_minus',type : 'minus',qtip : PRes["delete"],handler : function(event, toolEl, panel) {PAction.deleteEMPLine();}}
				],
				items : [employeeGrid]
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
}