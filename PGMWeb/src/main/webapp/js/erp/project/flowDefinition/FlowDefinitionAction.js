/**
 * @author FengYu
 */
Ext.define('EMP.project.flowDefinition.FlowDefinitionAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	checkMsgCount : 0,
	formProcessingBeforeSave : function() {
		$('flowDefinitionJsons').value = GUtils.allRecordsToJson(this.grid);
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		var me = this;
		var records = me.grid.getStore().getRange();
		if (records.length == 0) {
			msgarray.push({fieldname:"GRID_ID", message: PRes["FlowDefinitionListNotEmpty"] , arg : null});
		}
		for (var i = 0; i < records.length; i++) {
			var record = records[i];
			if (Strings.isEmpty(record.get('role')) && Strings.isEmpty(record.get('users'))) {
				msgarray.push({fieldname:"GRID_ID", message: PRes["Line"] + (i+1) + ":" + PRes["VRolesAndUser"], arg : null});
			}
		}
		return msgarray;
	},
	initEPGrid : function(grid_id, params) {
		var me = this;
		var fdGrid = GUtils.initErpGrid(grid_id, params);
		me.grid = fdGrid;
		return fdGrid;
	},
	addLine :  function() {
		GUtils.addLine(this.grid);
		GUtils.resetSequence(this.grid,'seqNo');
	},
	removeLine : function(record) {
		var me = this;
		GUtils.removeLine(me.grid,'flowDefinitionDeleteLines',record);
		GUtils.resetSequence(this.grid,'seqNo');
	},
	loadMarkShowEvent : function () {
		Ext.getCmp('okBtn').setDisabled(true);
		Ext.getCmp('applyBtn').setDisabled(true);
	},
	loadMarkHideEvent : function (isDisabled) {
		Ext.getCmp('okBtn').setDisabled(isDisabled);
		Ext.getCmp('applyBtn').setDisabled(isDisabled);
	},
	roleSelect : function(selectedId) {
		var arg = {};
		arg["cmpId"] = selectedId;
		arg["gridUrl"] = "security/user/_includes/_rolesGrid";
		arg["callBack"] = "popupSelectRoles_callBack";
		arg["parameters"] = {};
		arg["y"] = 5,
		LUtils.showPopupSelector(arg);
	},
	popupSelectRoles_callBack : function(id, action, returnVal, arg) {
		if (action == "ok") {
			var record = GUtils.getSelected(Ext.getCmp('GRID_ID'));
			GUtils.setPopupValue(record, 'role', returnVal[0]['id'], 'roleText', returnVal[0]['name']);
			
		}
	},
	userSelect : function(selectedId) {
		var arg = {};
		arg["cmpId"] = selectedId;
		arg["gridUrl"] = "security/user/_includes/_usersGrid";
		arg["callBack"] = "popupSelectUsers_callBack";
		arg["parameters"] = {};
	    arg["multiple"] = true;
	    arg["y"] = 5,
		LUtils.showPopupSelector(arg);
	},
	popupSelectUsers_callBack : function(id, action, returnVal, arg) {
		if (action == "ok") {
			var record = GUtils.getSelected(Ext.getCmp('GRID_ID'));
			var userIds = "";
			var userTexts = ""
			for (var i = 0; i < returnVal.length; i++) {
				if (i == 0) {
					userIds +=  returnVal[i]['id'];
					userTexts += returnVal[i]['name'];
				} else {
					userIds +=  "," + returnVal[i]['id'];
					userTexts +=   "," + returnVal[i]['name'];
				}
				
			}
			GUtils.setPopupValue(record, 'users', userIds, 'usersText', userTexts);
		}
	}
});