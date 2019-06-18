function page_OnLoad() {
	var actionBarItems = [];
	PRes["NameUnique"] = "${f:getText('Com.Validate.User.NameUnique')}";
	PRes["ResetPassword"] = "${f:getText('Com.ResetPassword')}";
	PRes["NewPassword"] = "${f:getText('Com.NewPassword')}";
	PRes["ConfirmPassword"] = "${f:getText('Com.Confirm.Password')}";
	PRes["NewPasswordNotEmpty"] = "${f:getText('Com.Validate.User.NewPassword.NotEmpty')}";
	PRes["ConfirmPasswordNotEmpty"] = "${f:getText('Com.Validate.User.ConfirmPassword.NotEmpty')}";
	PRes["PasswordNotSame"] = "${f:getText('Com.Validate.User.Password.NotSame')}";
	PRes["ValidationEmail"] = "${f:getText('Com.Validate.User.Email')}";
	PRes["ValidationDepartment"] = "${f:getText('Com.Validate.User.Department')}";
	PRes["departmentList"] = "${f:getText('Com.AddDepartment')}";
	PRes["RoleExist"] = "${f:getText('Com.Validate.Role.Exist')}";
	PRes["DepartmentExist"] = "${f:getText('Com.Validate.Department.Exist')}";
	PRes["ProjectExist"] = "${f:getText('Com.Validate.Project.Exist')}";
	PRes["ValidationPrimary"] = "${f:getText('Com.Validate.Primay')}";
	PRes["ProjectList"] = "${f:getText('Com.AddProject')}";
	var resetPassword = new Ext.Action( {
		text : PRes["modifyPassword"],
		iconCls: 'ss_sprite ss_wrench',
		handler: function() {
			PAction.resetPassword();
		}
	});
	if($('entityId').value != DEFAULT_NEW_ID) actionBarItems[52] = new Ext.Button(resetPassword);
	
	var actionBar = new ERP.FormActionBar(actionBarItems);
//	G_CONFIG.isPaging = false;
	PAction = new ERP.security.user.UserAction({
	});
	var roleGrid = GUtils.initErpGrid(GRID_ID, {userId: $('entityId').value});
	var departmentGrid = PAction.initDepartmentGrid("GRID_D_ID", {userId: $('entityId').value}, G_D_CONFIG);
	var projectGrid = PAction.initProjectGrid("GRID_P_ID", {userId: $('entityId').value}, G_P_CONFIG);
	
	PApp =  new ERP.FormApplication({
		roleGrid : roleGrid,
		pageLayout : {
			bodyItems : [ {
				xtype : "portlet",
				id : "userGeneralPanel",
				height : 160,
				title : ss_icon('ss_application_form') + PRes["general"],
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				id : "DEPARTMENT_GRID_PANEL_ID",
				collapseFirst: false,
				height : 230,
				layout : 'fit',
				title :ss_icon('ss_folder_user') + PRes["departmentList"],
				dockedItems: [{
	                xtype: 'toolbar',
	                items: [{
	                	iconCls: 'ss_sprite ss_add',
	                    id : 'plusD',
	                    text: PRes["add"],
	                    handler: function(event, toolEl, panel){
	                    	PAction.addDepartmentLine();
	                    }
	                }, {
	                	iconCls: 'ss_sprite ss_delete',
	                    id : 'minusD',
	                    text: PRes["delete"],
	                    handler: function(event, toolEl, panel){
	                    	 PAction.removeDepartmentLine();
	                    }
	                }]
	            }],
				items : [departmentGrid]
			},{
				xtype : "portlet",
				id : "PROJECT_GRID_PANEL_ID",
				collapseFirst: false,
				height : 230,
				layout : 'fit',
				title :ss_icon('ss_folder_user') + PRes["ProjectList"],
				dockedItems: [{
	                xtype: 'toolbar',
	                items: [{
	                	iconCls: 'ss_sprite ss_add',
	                    id : 'plusP',
	                    text: PRes["add"],
	                    handler: function(event, toolEl, panel){
	                    	PAction.addProjectLine();
	                    }
	                }, {
	                	iconCls: 'ss_sprite ss_delete',
	                    id : 'minusP',
	                    text: PRes["delete"],
	                    handler: function(event, toolEl, panel){
	                    	 PAction.removeProjectLine();
	                    }
	                }]
	            }],
				items : [projectGrid]
			}, {
				xtype : "portlet",
				id : "ROLE_GRID_PANEL_ID",
				collapseFirst: false,
				height : 230,
				layout : 'fit',
				title :ss_icon('ss_folder_user') + PRes["roleList"],
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
				items : [roleGrid]
			}]
		},
		actionBar : actionBar
	});
	
	
	PAction.loadRoleGrid();
}

function page_AfterLoad() {
	// handle those disable process ...
}