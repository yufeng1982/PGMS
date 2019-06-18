Ext.define('ERP.hr.timeEntry.TrakingSegmentAction' ,{
	extend : 'ERP.FormAction',
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	
	addLine :  function() {
		var sequence = GUtils.nextSequence(this.getPropGrid());
		GUtils.addLine(this.getPropGrid(), {'trackingSegmentColumnNum' : sequence}, 0);
	},
	removeLine : function () {
		GUtils.removeLine(this.getPropGrid(), "lineToDelete");
	},
	
	getPropGrid : function () {
		return PApp.grid;
	},
	
	getCurrentGridTypeId : function () {
		return GUtils.getSelectedId(CUtils.getExtObj(GRID_ID));
	},
	formProcessingBeforeSave : function() {
		$("modifiedRecords").value = GUtils.allRecordsToJson(this.getPropGrid());
	},
	loadFieldOptionStore : function(obj) {
		var segementType = obj.value;
		var optionDatas = {};
		var record = obj.record;
		
		if(segementType =='RuleType') {
			optionDatas = ruleTypes;
		} else if(segementType =='GroupType'){
			optionDatas = groupTypes;
		} else if(segementType =='TextType') {
			optionDatas = textTypes;
		}
		
		var selected = GUtils.getSelected();
		var store = selected.store;
		var columns = store.cols;
		for ( var i = 0; i < columns.length; i++) {
			var column = columns[i];
			if(column.dataIndex == 'code') {
				var field = Ext.getCmp(column.field.id);
				record.set('code','','');
				record.set('description','','');
				field.getStore().loadData(optionDatas);
				break;
			}
		} 
	},
	loadSelectedRowOptionStore : function(segementType) {
		var selected = GUtils.getSelected();
		var store = selected.store;
		
		if(segementType =='RuleType') {
			optionDatas = ruleTypes;
		} else if(segementType =='GroupType'){
			optionDatas = groupTypes;
		} else if(segementType =='TextType') {
			optionDatas = textTypes;
		} else {
			return;
		}
		
		var selected = GUtils.getSelected();
		var store = selected.store;
		var columns = store.cols;
		for ( var i = 0; i < columns.length; i++) {
			var column = columns[i];
			if(column.dataIndex == 'code') {
				var field = Ext.getCmp(column.field.id);
				field.getStore().loadData(optionDatas);
				break;
			}
		} 
	},
	initGrid : function(){
		var me = this;
		PApp.grid.on('edit', function(editor, obj) {
			if (obj.value == obj.originalValue) {
				return;
			}
			var fieldName = obj.field;
			if (fieldName == 'trackingSegmentType') {
				me.loadFieldOptionStore(obj);
			}
		});
		
		PApp.grid.getSelectionModel().on('selectionchange', function(selModel, records) {
			var selected = GUtils.getSelected();
			me.loadSelectedRowOptionStore(selected.get('trackingSegmentType'))
		});  
	}
});