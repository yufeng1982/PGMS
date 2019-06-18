/**
 * @author FengYu
 */
Ext.define('EMP.hr.employee.EmployeeAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	checkMsgCount : 0,
	formProcessingBeforeSave : function() {
//		$('employeeActivityJsons').value = GUtils.allRecordsToJson(this.grid);
		$('positionJsons').value = GUtils.allRecordsToJson(this.grid);
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		var me = this;
		if(!Strings.isEmpty($('email').value) && !VUtils.validateEmail($('email').value)){
			msgarray.push({fieldname:"email", message: PRes["Email"], arg:null});
		}
		if(!Strings.isEmpty($('phoneNumber').value) && !VUtils.validatePhone($('phoneNumber').value)){
			msgarray.push({fieldname:"phoneNumber", message: PRes["Phone"], arg:null});
		}
		if(!me.validationPrimary()){
			msgarray.push({fieldname:"GRID_ID", message: PRes["Primary"], arg:null});
		}
		return msgarray;
	},
	initEPGrid : function(grid_id, params) {
		var me = this;
		var primary = "primary";
		var epGrid = GUtils.initErpGrid(grid_id, params);
		me.grid = epGrid;
		me.grid.on('edit', function(editor, obj) {
			if(obj.field == primary && obj.record.get(primary)) {
				var records = obj.grid.getStore().getRange();
				for(var i = 0; i < records.length; i++) {
					if (obj.rowIdx != i && records[i].get(primary)) {
						records[i].set(primary, false);
						break;
					}
				}
			}
			return true;
		});
		return epGrid;
	},
	validationPrimary : function() {
		var records = PAction.grid.getStore().getRange();
		if(records.length != 0){
			var p = 0;
			for(var i = 0; i < records.length; i++) {
				if(records[i].get("primary")) {
					p++;
					break;
				}
			}
			if(p == 0) {
				return false;
			}
		}
		return true;
	},
	addLine :  function() {
		GUtils.addLine(this.grid);
	},
	removeLine : function(record) {
		var me = this;
		GUtils.removeLine(me.grid,'positionDeleteLines',record);
	},
	popupPosition : function(id){
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "maintenance/_includes/_maintenanceGrid";
		arg["callBack"] = 'popupPosition_callBack';
		arg["mtype"] = "Position";
		LUtils.showPopupSelector(arg);
	},
	popupPosition_callBack : function (id, action, returnVal, arg) {
		if (action == "ok") {
			var me = this;
			var record = GUtils.getSelected(me.grid);
			if(!record) {
				return false;
			}
			GUtils.setPopupValue(record, "positionId", returnVal[0].id, "position", returnVal[0].name);
		}
	},
	popupGroup : function (id) {
		var record = GUtils.getSelected(Ext.getCmp('EMPLOYEE_HOME_GROUP_GRID_ID'));
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "hr/employee/_includes/_groupGrid";
		arg["callBack"] = 'popupGroup_callBack';
		arg["parameters"] = {groupTypeId : record.get('groupTypeId'), employeeId : $('entityId').value};
		LUtils.showPopupSelector(arg);
	},
	popupMultipleGroups : function (id) {
		var record = GUtils.getSelected(Ext.getCmp('EMPLOYEE_HOME_GROUP_GRID_ID'));
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "maintenance/_includes/_maintenanceGrid";
		arg["callBack"] = 'popupGroup_callBack';
		arg["mtype"] = "Group";
		arg["multiple"] = true;
		arg["parameters"] = {groupType : record.get('groupTypeId')};
		LUtils.showPopupSelector(arg);
	},
	popupGroup_callBack : function (id, action, returnVal, arg) {
		var me = this;
		var groupIds = "";
		var groupNames = "";
		var deleteIds = "";
		var saveIds = "";
		var record = GUtils.getSelected(Ext.getCmp('EMPLOYEE_HOME_GROUP_GRID_ID'));
		if(!record) {
			return false;
		}
		if(arg["multiple"]){
			var originalGroupIds = record.get('originalGroupIds');
			for(var i = 0; i < returnVal.length; i++){
				// set default home group code
				if (i == 0) {
					if (Strings.isEmpty(record.get('groupId'))) {
						GUtils.setPopupValue(record, "groupId", returnVal[i].id, "group", returnVal[i].name,'EMPLOYEE_HOME_GROUP_GRID_ID');
					}
				}
				// set groupd ids
				if(Strings.isEmpty(groupIds)){
					groupIds = returnVal[i].id;
					groupNames = returnVal[i].name;
				} else {
					groupIds += "," + returnVal[i].id;
					groupNames += "," + returnVal[i].name;
				}
			}
			var gIdArrary = groupIds.split(",");
			var oIdArrary = originalGroupIds.split(",");
			// set save group ids
			saveIds = me.compareGroupIds(gIdArrary, oIdArrary);
			// set delete group ids
			deleteIds = me.compareGroupIds(oIdArrary, gIdArrary);
			record.set('groupDelIds', deleteIds);
			record.set('groupSaveIds', saveIds);
			GUtils.setPopupValue(record, "groupIds", groupIds, "groups", groupNames,'EMPLOYEE_HOME_GROUP_GRID_ID');
		} else {
			GUtils.setPopupValue(record, "groupId", returnVal[0].id, "group", returnVal[0].name,'EMPLOYEE_HOME_GROUP_GRID_ID');
		}
	},
	
	compareGroupIds : function (groupIds, originalGroupIds) {
		var returnIds = "";
		var count = 0;
		for (var i = 0; i < groupIds.length; i++) {
			count = 0;
			var gid = groupIds[i].trim();
			for (var j = 0; j < originalGroupIds.length; j++) {
				var ogid = originalGroupIds[j].trim();
				if (gid == ogid) {
					count++;
				}
			}
			if (count == 0) {
				if (Strings.isEmpty(returnIds)) {
					returnIds = gid;
				} else {
					returnIds += "," + gid;
				}
			}
		}
		return returnIds;
	},
	checkComponentUnique : function (obj) {
		var me = this;
		me.showLoadMark("fr_" + obj.id, "Checking...");
		var params = {employeeType : $('employeeType').value};
		if (obj.id == 'code') {
			params.code = obj.value;
		} else {
			params.email = obj.value;
		}
		Ext.Ajax.request({
		    url: '/app/pgm/hr/employee/list/' + obj.id + "Check",
		    params : params,
		    timeout: 60000,
		    success: function(response){
		    	var returnValue = response.responseText;
		    	if(returnValue == 'true'){
		    		var msgarray = [];
		    		msgarray.push({fieldname:obj.id, message:PRes["employee"] + (obj.id == 'code' ? PRes["code"] : obj.id) + obj.value + PRes["isExist"], arg: null});
		    		VUtils.processValidateMessages(msgarray);
		    		me.checkMsgCount++;
		    		me.hideLoadMark(true); 
		    	} else {
		    		VUtils.removeFieldErrorCls(obj.id);
		    		VUtils.removeTooltip(obj.id);
		    		me.checkMsgCount--;
		    		me.hideLoadMark(me.checkMsgCount <= 0 ? false : true); 
		    	}
		    },
		});
		return true;
	},
	checkUserNameUnique : function (obj) {
		var me = this;
		me.showLoadMark("fr_" + obj.id, PRes["Checking"]);
		var params = {};
		params.name = obj.value;
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
	loadMarkShowEvent : function () {
		Ext.getCmp('okBtn').setDisabled(true);
		Ext.getCmp('applyBtn').setDisabled(true);
	},
	loadMarkHideEvent : function (isDisabled) {
		Ext.getCmp('okBtn').setDisabled(isDisabled);
		Ext.getCmp('applyBtn').setDisabled(isDisabled);
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
			        				    url: '/app/pgm/hr/employee/form/' + me.entityId + '/resetPassword',
			        				    params: {
			        				    	employee : me.entityId,
			        				    	plainPassword : newPassword
			        				    },
			        				    success: function(response) {
//			        				    	var version = parseInt($("version").value);
//			        						$("version").value = ++ version;
			        				    	endWaitCursor();
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
	resetUserName :  function() {
		var msgarray;
		var items = [{
	    	fieldLabel: PRes["userName"], 
	    	id: 'resetUserName',
	    	inputId : 'resetUserNameInputId',
	    	name : 'resetUserName',
	    	xtype:'textfield',
	    	value : '',
	    	margins: '5 0 5 5',
	    	height : 20,
	    	width : 280
	    }];
		var me = this;
		if(!me.resetUserWin){
			me.resetUserWin= Ext.create('Ext.window.Window', {
				id : 'resetUserNameWin',
			    height: 100,
			    width: 350,
			    modal :  true,
			    closable : false,
			    layout:'hbox',
			    title : PRes["ResetUserName"],
			    items : items,
			    fbar: [{
			        	   type: 'button', 
			        	   text: PRes["ok"],
			        	   handler : function() {
			        		   msgarray = [];
			        		   var resetUserName = Ext.getCmp('resetUserName').getValue();
			        		   if(Strings.isEmpty(resetUserName)){
			        			   msgarray.push({fieldname:'resetUserName', message:PRes["UserNameNotEmpty"], arg: null});
			        		   }
			        		   if(msgarray.length > 0){
			        			   	VUtils.removeFieldErrorCls('resetUserName');
			   		    			VUtils.removeTooltip('resetUserName');
			   		    			VUtils.processValidateMessages(msgarray);
			        		   } else {
			        			   beginWaitCursor(PRes["Saving"],false);
				        		   Ext.Ajax.request({
			        				    url: '/app/pgm/hr/employee/form/' + me.entityId + '/resetUserName',
			        				    params: {
			        				    	employee : me.entityId,
			        				    	userName : resetUserName
			        				    },
			        				    success: function(response) {
			        				    	var returnValue = response.responseText;
			        				    	endWaitCursor();
			        				    	var version = parseInt($("version").value);
			        						$("version").value = ++ version;
			        						$('userName').value = resetUserName;
			        				    	if(CUtils.isTrueVal(returnValue)){
				        						Ext.getCmp('resetUserNameWin').hide();
			        				    	} else {
			        				    		msgarray.push({fieldname:'resetUserName', message:PRes["NameUnique"], arg: null});
			        				    		VUtils.processValidateMessages(msgarray);
			        				    	}
			        				    	
			        				    }
				        		   });
			        		   }
			        	   }
			           }, {
			        	   type: 'button', 
			        	   text: PRes["cancel"],
			        	   handler : function(){
			        		   Ext.getCmp('resetUserNameWin').hide();
			        	   }
			    }]
			}).show();
		} else {
			if(!me.resetUserWin.isVisible()){
				VUtils.removeFieldErrorCls('resetUserName');
    			VUtils.removeTooltip('resetUserName');
				Ext.getCmp('resetUserName').setValue('');
				me.resetUserWin.show();
			}
		}
	}
});