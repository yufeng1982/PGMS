Ext.define('EMP.hr.department.DepartmentAction' ,{
	extend : 'ERP.FormAction',
	
	EMPGrid:null,
	
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	
	gridItemDBClick : function(id){
		var args = {};
		args["id"] = id;
		args["employeeType"] = 'Employee';
		LUtils.showEmployee(args);
	},

	formProcessingBeforeSave : function(){
	    $('employeeJsons').value = GUtils.allRecordsToJson(PApp.employeeGrid);
	},
	
	initEMPGrid : function(args) {
		var grid = GUtils.initErpGrid("EMP_GRID", args, G_EMPLOYEE_CONFIG);
		this.EMPGrid = grid;
		return grid;
	},
	
	addEMPLine : function(data) {
		var args = {};
		args["gridUrl"] = "hr/employee/_includes/_employeesGrid";
		args['displayField'] = 'code';
		args["callBack"] = 'employeePopup_callBack';
		LUtils.showPopupSelector(args);
	},
	
	deleteEMPLine : function (record) {
		GUtils.removeLine(this.EMPGrid,'deleteEmployee',record);
	},
	
	employeePopup_callBack : function(id, action, returnVal, arg){
		if (returnVal) {
			if (!this.validateUniqueEmployee(returnVal, this.EMPGrid)) {
				CUtils.warningAlert("The employee have in the department!");
				return;
			}
			GUtils.addLine(this.EMPGrid);
			var record = GUtils.getSelected(this.EMPGrid);
			record.set("employeeId", returnVal[0].id);
			record.set("employeeCode", returnVal[0].code);
			record.set("employeeName", returnVal[0].name);
			if (!Strings.isEmpty(returnVal[0].departmnetId) && returnVal[0].departmnetId != '${entity.id}') {
				CUtils.warningAlert("The department is not the employee's primeryDepartment!");
			}
		}
	},
	
	validateUniqueEmployee : function(returnVal, grid){
		var id = returnVal[0].id;
		var records = grid.getStore().data.getRange();
		if (records.length > 0) {
			for ( var i = 0; i < records.length; i++) {
				if (id == records[i].get("employeeId")) {
					return false;
				}
			}
		}
		return true;
	}
	
});