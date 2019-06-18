Ext.define('EMP.hr.employee.EmployeeGroupAction' ,{
	extend : 'ERP.FormAction',
	
	empGrid : null,
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	searchValidate : Ext.emptyFn,
	
	doSearch : function() {
    	if(this.validate()) {
    	   var searchParams =  this.getSearchParams();
    	   PApp.grid.store.proxy.extraParams = {};
	       Ext.apply(PApp.grid.store.proxy.extraParams, searchParams);
	       PApp.grid.getStore().load();
    	}
    },
    
    getSearchParams : function(){
 	   var searchParams ={};
		   if($('historical') && $('historical').value)  searchParams = {'historical' : $('historical').value};
		   
	       var params = this._getSearchParames();
	       if(params){
	    	   Ext.apply(searchParams, params);
	       } 
	       if(PApp.defaultSearchPara){
 		   Ext.apply(searchParams, PApp.defaultSearchPara);
	       }
	       if (this.getGridSearchPara) {
	    	   Ext.apply(searchParams, this.getGridSearchPara());
 	   }
	       return searchParams;
    },
    _getSearchParames : function() {
    	var params = {};
    	if(PApp.filterForm) {
    		var myForm = PApp.filterForm.getForm();
    		params = myForm.getValues();

			// TODO, fuck extjs, HACK for now, have check if it could be fix in extjs next version
			var items = myForm.getFields().items;
			for (var i = 0; i < items.length; i ++) {
				var field = items[i];
				var name = field.name;
				var xtype = field.xtype;
				if(field.disabled === true) {
					params[name] = field.getValue();
				}
				if(xtype == 'erpsearchingselect') {
					params[name] = field.getSelectValue();
				}
			}
			//////////////////////////////////////////////////////////////////////////////////////
    	}
    	return params;
    },
    
    validate : function() {
		var msgarray = [];
		msgarray.addAll(this.searchValidate());
		if (msgarray.length > 0) {
			VUtils.processValidateMessages(msgarray);
			return false;
		}
		return true;				
	},
	
	doReset : function(){
    	if(PApp.filterForm) {
    		this.cleanFields();
			
    		this.doSearch();
    	}
    },
    
    cleanFields : function() {
    	var myForm = PApp.filterForm.getForm();
		
		// myForm.reset();
		// reset one by one, ignore the disable field
		
		var items = myForm.getFields().items;
		for (var i = 0; i < items.length; i ++) {
			var field = items[i];
			if(field.disabled === true) {
				continue ;
			} else {
				// TODO, fuck extjs, HACK for now, have check if it could be fix in extjs next version
				var xtype = field.xtype;
				if(xtype == 'erpsearchingselect' || xtype == 'SelectField') {
					field.setSelectValue('', '');
				} else {
					field.setValue('');
				}
			}
		}
    },
	
	initEMPGrid : function(args) {
		var me = this;
		var grid = GUtils.initErpGrid("GROUPEMP_GRID", args, G_CONFIG);
		me.empGrid = grid;
		return grid;
	},
	
	removeLine : function (record) {
		GUtils.removeLine(this.empGrid,'employeesToDelete',record);
		$('employeeJsons').value = GUtils.allRecordsToJson(PAction.empGrid);
		this.saveEmployee();
	},
	
	employeePopup_callBack : function(id, action, returnVal, arg){
		if(returnVal){
			for(var i = 0; i < returnVal.length; i++){
				GUtils.addLine(this.empGrid);
				record = GUtils.getSelected(this.empGrid);
				record.set("employeeId", returnVal[i].id);
				record.set("employeeCode", returnVal[i].code);
				record.set("employeeName", returnVal[i].name);
			}
			$('employeeJsons').value = GUtils.allRecordsToJson(PAction.empGrid);
			this.saveEmployee();
		}
		return ;
	},
	
	saveEmployee : function(){
		Ext.Ajax.request({
     	    url: "/app/pgm/hr/employeeGroup/list/apply" ,
     	    params: {employeeJsons : $('employeeJsons').value, groupId : $('groupId').value, employeesToDelete : $('employeesToDelete').value},
     	    success : function(response){
     	    	PAction.empGrid.getStore().load();
     	    }
     	});
	}
});