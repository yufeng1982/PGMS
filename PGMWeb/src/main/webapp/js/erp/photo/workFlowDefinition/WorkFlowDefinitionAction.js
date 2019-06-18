Ext.define('PGM.WorkFlowDefinitionAction' ,{
	extend : 'ERP.ListAction',
	
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		PApp.grid.getSelectionModel().on('select', function (/*Ext.selection.RowSelectionModel*/ rm, record, index){
			var id = record.get("id");
			var cmp = Ext.getCmp('removeLine');
			if(!Strings.isEmpty(id)) {
				cmp.setDisabled(true);
			} else {
				cmp.setDisabled(false);
			}
		});
		return this;	
	},
	addLine :  function() {
		GUtils.addLine(this.getWFDGrid());
		GUtils.resetSequence(this.getWFDGrid(),'seq');
	},
	removeLine : function () {
		GUtils.removeLine(this.getWFDGrid());
	},
	save : function() {
		beginWaitCursor("saving");
		Ext.Ajax.request({
     	    url: "/app/pgm/photo/photoWFD/list/save" ,
     	    params: {
     	    	modifiedRecords : GUtils.modifiedRecordsToJson(this.getWFDGrid())
     	    },
     	    success : function(response){
     	    	VUtils.removeFieldErrorCls(GRID_ID);
     	        VUtils.removeTooltip(GRID_ID);
     	    	PApp.grid.getStore().load();
     	    	endWaitCursor();
     	    },
     	    failure : function(response) {
     	    	endWaitCursor();
     	    }
     	});
	},
	validationWFD : function() {
		VUtils.removeFieldErrorCls(GRID_ID);
	    VUtils.removeTooltip(GRID_ID);
		var msgarray = [];
		var records = PApp.grid.getStore().getRange();
		if(records.length != 0){
			var param ={};
			param.uniqueColumnsArray = [["code"]];
			param.grid = PApp.grid;
			var unique = VUtils.validateGridUniqueValue("GRID_ID","",param);
			if(unique.length > 0) {
				msgarray.push(unique[0]);
			}
			for(var i = 0; i < records.length; i++) {
				if(Strings.isEmpty(records[i].get("code"))) {
					msgarray.push({fieldname:"GRID_ID", message: PRes["WFDLines"] + " " + PRes["code"], arg : [i+1]});
				}
				if(Strings.isEmpty(records[i].get("name"))) {
					msgarray.push({fieldname:"GRID_ID", message: PRes["WFDLines"] + " " + PRes["name"], arg : [i+1]});
				}
			}
		} else {
			return false;
		}
		if(msgarray.length > 0) {
			 VUtils.processValidateMessages(msgarray);
			 return false;
		}
		return true;
	},
	getWFDGrid : function() {
		return PApp.grid;
	},
	popupRole : function (id) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "security/user/_includes/_rolesGrid";
		arg["callBack"] = 'popupRole_callBack';
//		arg["parameters"] = {};
		LUtils.showPopupSelector(arg);
	},
	popupRole_callBack : function (id, action, returnVal, arg) {
		if (action == "ok") {
			var record = GUtils.getSelected(Ext.getCmp('GRID_ID'));
			GUtils.setPopupValue(record, "roleId", returnVal[0].id, "approveRole", returnVal[0].name,'GRID_ID');
		}
	}
});