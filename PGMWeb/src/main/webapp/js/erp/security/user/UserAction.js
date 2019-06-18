/**
 * @author FengYu
 */
Ext.define('ERP.security.user.UserAction' ,{
	extend : 'ERP.FormAction',
	userNameUnique: false,
	checkMsgCount : 0,
	resetWin : null,
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	initDepartmentGrid : function (gridId, params, config) {
		var grid = GUtils.initErpGrid(gridId, params, config);
		grid.store.on('load', function(stroe, records){
		});
		grid.on('edit', function(editor, obj) {
			if(obj.field == "principal" && obj.record.get("principal")) {
				var records = obj.grid.getStore().getRange();
				for(var i = 0; i < records.length; i++) {
					if (obj.rowIdx != i && records[i].get("principal")) {
						records[i].set("principal", false);
						break;
					}
				}
			}
			return true;
		});
		return grid;
	},
	addDepartmentLine :  function() {
		GUtils.addLine(Ext.getCmp('GRID_D_ID'));
	},
	removeDepartmentLine : function() {
		var record = GUtils.getSelected(Ext.getCmp('GRID_D_ID'));
		if(record) {
			GUtils.removeLine(Ext.getCmp('GRID_D_ID'),"deleteDepartments");
		}
	},
	
	initProjectGrid : function (gridId, params, config) {
		var grid = GUtils.initErpGrid(gridId, params, config);
		return grid;
	},
	addProjectLine :  function() {
//		GUtils.addLine(Ext.getCmp('GRID_P_ID'));
		this.projectMulSelect('projectText');
	},
	removeProjectLine : function() {
		var record = GUtils.getSelected(Ext.getCmp('GRID_P_ID'));
		if(record) {
			GUtils.removeLine(Ext.getCmp('GRID_P_ID'),"deleteProjects");
		}
	},
	
	addLine :  function() {
		GUtils.addLine(this.getRoleGrid());
	},
	removeLine : function() {
		var record = GUtils.getSelected();
		if(record) {
			GUtils.removeLine(this.getRoleGrid());
		}
	},
	resetPassword :  function() {
		var msgarray;
		var items = [{
	    	fieldLabel: PRes["NewPassword"], 
	    	id: 'plainPassword',
	    	inputId : 'plainPasswordInputId',
	    	inputType : 'password',
	    	name : 'plainPassword',
	    	xtype:'textfield',
	    	value : '',
	    	margins: '5 0 5 5',
	    	height : 20,
	    	width : 280
	    },{
	    	fieldLabel: PRes["ConfirmPassword"], 
	    	id: 'confirmPassword',
	    	inputId : 'confirmPasswordInputId',
	    	inputType : 'password',
	    	name : 'confirmPassword',
	    	xtype:'textfield',
	    	value : '',
	    	margins: '5 0 5 5',
	    	height : 20,
	    	width : 280
	    }];
		var me = this;
		if(!me.resetWin){
			me.resetWin= Ext.create('Ext.window.Window', {
				id : 'resetPasswordWin',
			    height: 125,
			    width: 350,
			    modal :  true,
			    closable : false,
			    layout:'vbox',
			    title : PRes["ResetPassword"],
			    items : items,
			    fbar: [{
			        	   type: 'button', 
			        	   text: PRes["ok"],
			        	   handler : function() {
			        		   msgarray = [];
			        		   var newPassword = Ext.getCmp('plainPassword').getValue();
			        		   var confirmPassword = Ext.getCmp('confirmPassword').getValue();
			        		   if(Strings.isEmpty(newPassword)){
			        			   msgarray.push({fieldname:'plainPasswordInputId', message:PRes["NewPasswordNotEmpty"], arg: null});
			        		   }
			        		   if(Strings.isEmpty(confirmPassword)){
			        			   msgarray.push({fieldname:'confirmPasswordInputId', message:PRes["ConfirmPasswordNotEmpty"], arg: null});
			        		   }
			        		   if(newPassword != confirmPassword){
			        			   msgarray.push({fieldname:'confirmPasswordInputId', message:PRes["PasswordNotSame"], arg: null});
			        		   }
			        		   if(msgarray.length > 0){
			        			   	VUtils.removeFieldErrorCls('plainPasswordInputId');
			   		    			VUtils.removeTooltip('plainPasswordInputId');
			   		    			VUtils.removeFieldErrorCls('confirmPasswordInputId');
			   		    			VUtils.removeTooltip('confirmPasswordInputId');
			   		    			VUtils.processValidateMessages(msgarray);
			        		   } else {
			        			   beginWaitCursor(PRes["Saving"],false);
				        		   Ext.Ajax.request({
			        				    url: '/app/pgm/user/form/' + me.entityId + '/password/superResetOk',
			        				    params: {
			        				    	userId : me.entityId,
			        				    	plainPassword : newPassword
			        				    },
			        				    success: function(response) {
			        				    	endWaitCursor();
			        				    	var version = parseInt($("version").value);
			        						$("version").value = ++ version;
			        						Ext.getCmp('resetPasswordWin').hide();
			        				    }
				        		   });
			        		   }
			        	   }
			           }, {
			        	   type: 'button', 
			        	   text: PRes["cancel"],
			        	   handler : function(){
			        		   Ext.getCmp('resetPasswordWin').hide();
			        	   }
			    }]
			}).show();
		} else {
			if(!me.resetWin.isVisible()){
				VUtils.removeFieldErrorCls('plainPasswordInputId');
    			VUtils.removeTooltip('plainPasswordInputId');
    			VUtils.removeFieldErrorCls('confirmPasswordInputId');
    			VUtils.removeTooltip('confirmPasswordInputId');
				Ext.getCmp('plainPassword').setValue('');
     		   	Ext.getCmp('confirmPassword').setValue('');
				me.resetWin.show();
			}
		}
	},
	getRoleGrid : function() {
		return PApp.roleGrid;
	},
	loadRoleGrid : function() {
		this.getRoleGrid().getStore().load();
	},

	initParameters4Warehouse : function() {
		return {corporation : CUtils.getSValue('corporation')};
	},

	departmentSelect : function(id) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "maintenance/_includes/_maintenanceGrid";
		arg["callBack"] = 'departmentSelect_callBack';
		arg["mtype"] = "Department";
		LUtils.showPopupSelector(arg);
	},
	departmentSelect_callBack : function(id, action, returnVal) {
		if (action == "ok") {
			var me = this;
			var record = GUtils.getSelected(Ext.getCmp('GRID_D_ID'));
			if(!record) {
				return false;
			}
			var newDepartmentId = returnVal[0]['id'];
			var records = Ext.getCmp('GRID_D_ID').getStore().data.getRange();
			for(var i = 0; i < records.length; i ++) {
				var existId = records[i].get("department");
				if (existId == newDepartmentId) {
					CUtils.errorAlert(PRes["DepartmentExist"]);
					return false;
				}
			}
			GUtils.setPopupValue(record, "department", returnVal[0].id, "departmentText", returnVal[0].name, "GRID_D_ID");
			record.set("description", returnVal[0]['description']);
		}
	},
	
	projectMulSelect : function(id) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "project/petrolStation/_includes/_petrolStationsGrid";
		arg["callBack"] = 'projectMulSelect_callBack';
		arg["parameters"] = {};
		arg["multiple"] = true;
		LUtils.showPopupSelector(arg);
	},
	projectMulSelect_callBack : function(id, action, returnVal) {
		if (action == "ok") {
			var currentRecords = Ext.getCmp('GRID_P_ID').store.getRange();
			for (var i = 0; i < returnVal.length; i++) {
				var addflag = true;
				for (var j = 0; j < currentRecords.length; j++) {
					if(currentRecords[j].get('project') == returnVal[i].id){
						addflag = false;
						break;
					}
				}
				if(addflag){
					var record = GUtils.addLine(Ext.getCmp('GRID_P_ID'));
					GUtils.setPopupValue(record, "project", returnVal[i].id, "projectText", returnVal[i].name, "GRID_P_ID");
				}
			}
		}
	},
	
	projectSelect : function(id, mul) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "project/petrolStation/_includes/_petrolStationsGrid";
		arg["callBack"] = 'projectSelect_callBack';
		arg["parameters"] = {};
		LUtils.showPopupSelector(arg);
	},
	projectSelect_callBack : function(id, action, returnVal) {
		if (action == "ok") {
			var me = this;
			var record = GUtils.getSelected(Ext.getCmp('GRID_P_ID'));
			if(!record) {
				return false;
			}
			var newProjectId = returnVal[0]['id'];
			var records = Ext.getCmp('GRID_P_ID').getStore().data.getRange();
			for(var i = 0; i < records.length; i ++) {
				var existId = records[i].get("project");
				if (existId == newProjectId) {
					CUtils.errorAlert(PRes["ProjectExist"]);
					return false;
				}
			}
			GUtils.setPopupValue(record, "project", returnVal[0].id, "projectText", returnVal[0].name, "GRID_P_ID");
		}
	},
	
	roleSelect : function(selectedId) {
		if (selectedId == "name") {
			var arg = {};
			arg["cmpId"] = selectedId;
			arg["gridUrl"] = "security/user/_includes/_rolesGrid";
			arg["callBack"] = "popupSelect_callBack";
			arg["parameters"] = {};
			LUtils.showPopupSelector(arg);
		}
	},
	popupSelect_callBack : function(selectedId, action, returnVal) {
		/*/debugger;//*/
		if (action == "ok") {

			var record = GUtils.getSelected();
			var records = this.getRoleGrid().getStore().data.getRange();
			if(!record) {
				return false;
			}
			var newRoleId = returnVal[0]['id'];
			for(var i = 0; i < records.length; i ++) {
				var existId = records[i].get("id");
				if (existId == newRoleId) {
					alert(PRes["RoleExist"]);
					return false;
				}
			}
			//record.set("id", newRoleId);
			//record.set("name", returnVal[0]['name']);
			GUtils.setPopupValue(record, 'id', newRoleId, 'name', returnVal[0]['name']);
			record.set("description", returnVal[0]['description']);
			var corporation = returnVal[0]['corporation'];
			record.set("corporation", corporation);
			var corporationName = returnVal[0]['corporationName'];
			record.set("corporationName", corporationName);
			//this.addCorporation(corporation);
		}
	},
	checkComponentUnique : function (obj) {
		var me = this;
		me.showLoadMark("fr_" + obj.id, PRes["Checking"]);
		var params = {};
		if (obj.id == 'name') {
			params.name = obj.value;
		} else {
			params.email = obj.value;
		}
		Ext.Ajax.request({
		    url: '/app/core/user/list/isNameExsit',
		    params : params,
		    timeout: 60000,
		    success: function(response){
		    	var returnValue = response.responseText;
		    	if(CUtils.isTrueVal(returnValue)){
		    		var msgarray = [];
		    		msgarray.push({fieldname:obj.id, message:PRes["NameUnique"], arg: null});
		    		VUtils.removeFieldErrorCls(obj.id);
		    		VUtils.removeTooltip(obj.id);
		    		VUtils.processValidateMessages(msgarray);
		    		me.checkMsgCount++;
		    		me.hideLoadMark(true); 
		    		PAction.userNameUnique=false;
		    	} else {
		    		VUtils.removeFieldErrorCls(obj.id);
		    		VUtils.removeTooltip(obj.id);
		    		me.checkMsgCount--;
		    		me.hideLoadMark(me.checkMsgCount <= 0 ? false : true);
		    		PAction.userNameUnique=true;
		    	}
		    },
		});
		return true;
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		if(!Strings.isEmpty( $('name').value) && $('entityId').value == 'NEW' && !CUtils.isTrueVal(PAction.userNameUnique)){
			msgarray.push({fieldname:"name", message: PRes["NameUnique"], arg:null});
		}
		var records = this.getRoleGrid().getStore().data.getRange();
		if(records.length == 0){
			msgarray.push({fieldname:"ROLE_GRID_PANEL_ID", message: PRes["RoleListValidation"], arg:null});
		}
		if($('entityId').value == 'NEW'){
			var confirmPassword = $('plainPassword').value;
			var password = $('password').value;
			if(!Strings.isEmpty(confirmPassword) && !Strings.isEmpty(password) && password != confirmPassword){
				msgarray.push({fieldname:"plainPassword", message: PRes["validationPassword"], arg:null});
			}
		}
		if(!VUtils.validateEmail($('email').value)) {
			msgarray.push({fieldname:"email", message: PRes["ValidationEmail"], arg:null});
		}
		if(!CUtils.isTrueVal($('isSuperAdmin').value)){
			var d_records = Ext.getCmp('GRID_D_ID').store.getRange();
			if (d_records.length == 0) {
				msgarray.push({fieldname:"DEPARTMENT_GRID_PANEL_ID", message: PRes["ValidationDepartment"], arg:null});
			}
			var isIncludePrimary = false;
			for (var i = 0; i < d_records.length; i++) {
				if (CUtils.isTrueVal(d_records[i].get('principal'))) {
					isIncludePrimary = true;
					break;
				}
			}
			if (!isIncludePrimary && d_records.length > 0) {
				msgarray.push({fieldname:"DEPARTMENT_GRID_PANEL_ID", message: PRes["ValidationPrimary"], arg:null});
			}
		}
		return msgarray;
	},
	formProcessingBeforeSave : function() {
		$("roleListJSON").value = GUtils.allRecordsToJson(this.getRoleGrid());
		$("departmentListJSON").value = GUtils.allRecordsToJson(Ext.getCmp('GRID_D_ID'));
		$("projectListJSON").value = GUtils.allRecordsToJson(Ext.getCmp('GRID_P_ID'));
	},
	loadMarkShowEvent : function () {
		Ext.getCmp('okBtn').setDisabled(true);
		Ext.getCmp('applyBtn').setDisabled(true);
	},
	loadMarkHideEvent : function (isDisabled) {
		Ext.getCmp('okBtn').setDisabled(isDisabled);
		Ext.getCmp('applyBtn').setDisabled(isDisabled);
	},
});