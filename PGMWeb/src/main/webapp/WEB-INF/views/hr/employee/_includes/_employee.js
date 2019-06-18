function page_OnLoad() {
	PRes["EmployeeId"] = "${f:getText('Com.Validate.EmployeeCompensation.EmployeeId')}";
	PRes["Email"] = "${f:getText('Com.Validate.Employee.Email')}";
	PRes["Phone"] = "${f:getText('Com.Validate.Employee.Phone')}";
	PRes["Primary"] = "${f:getText('Com.Validate.EmployeePosition.Primary')}";
	PRes["NameUnique"] = "${f:getText('Com.Validate.User.NameUnique')}";
	PRes["ResetPassword"] = "${f:getText('Com.ResetPassword')}";
	PRes["ResetUserName"] = "${f:getText('Com.ResetUserName')}";
	PRes["NewPassword"] = "${f:getText('Com.NewPassword')}";
	PRes["ConfirmPassword"] = "${f:getText('Com.Confirm.Password')}";
	PRes["NewPasswordNotEmpty"] = "${f:getText('Com.Validate.User.NewPassword.NotEmpty')}";
	PRes["ConfirmPasswordNotEmpty"] = "${f:getText('Com.Validate.User.ConfirmPassword.NotEmpty')}";
	PRes["PasswordNotSame"] = "${f:getText('Com.Validate.User.Password.NotSame')}";
	PRes["modifyPassword"] = "${f:getText('Com.ModifyPassword')}";
	PRes["isExist"] = "${f:getText('Com.Validate.isExist')}";
	PRes["employee"] = "${f:getText('Com.Employee')}";
	PRes["code"] = "${f:getText('Com.Code')}";
	PRes["userName"] = "${f:getText('Com.UserName')}";
	PRes["UserNameNotEmpty"] = "${f:getText('Com.Validate.UserNameNotEmpty')}";
	var resetPassword = new Ext.Action( {
		text : PRes["modifyPassword"],
		iconCls: 'ss_sprite ss_wrench',
		handler: function() {
			PAction.resetPassword();
		}
	});
	
	var resetUserName = new ERP.Button({
		id : 'resetUserBtn',
		text: PRes["ResetUserName"],
		iconCls: 'ss_sprite ss_wrench',
	    handler: function() {
	    	PAction.resetUserName();
	    }
	});
	
	var actionBarItems = [];
	if($('entityId').value != DEFAULT_NEW_ID) actionBarItems[53] = new Ext.Button(resetPassword);
	if($('entityId').value != DEFAULT_NEW_ID) actionBarItems[52] = resetUserName;
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.hr.employee.EmployeeAction();
	var epGrid = PAction.initEPGrid(GRID_ID, {employeeId : $('entityId').value});
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 260,
				id : "general-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				id : "EMPLOYEE_POSITION_PANEL_ID",
				collapseFirst : false,
				height : 280,
				layout : 'fit',
				title : ss_icon('ss_application_form') + "${f:getText('Com.Position.List')}",
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
				items : [epGrid]
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
}