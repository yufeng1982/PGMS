Ext.define('ERP.hr.timeEntry.TrakingSegmentAction' ,{
	extend : 'ERP.FormAction',
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	timeEntryGrid : null,
	
	addLine :  function() {
		GUtils.addLine(this.getPropGrid(), {'logDate' : new Date(), 'startTime' : PRes['startTime'], 'costPerHour' : PRes['costPerHour']});
	},
	
	getPropGrid : function () {
		return PApp.grid;
	},
	
	formProcessingBeforeSave : function() {
		$("modifiedRecords").value = GUtils.allRecordsToJson(this.getPropGrid());
	},
	
	setTime : function(year, month, day, startTimeHours, startTimeMinutes, endTimeHours, endTimeMinutes, record){
		var costPerHour = record.get("costPerHour");
		var starTime = this.getLoggedDate(year, month, day, startTimeHours, startTimeMinutes);
		var endTime = this.getLoggedDate(year, month, day, endTimeHours, endTimeMinutes);
		
		var hours = (endTime.getTime() - starTime.getTime())/(1000*3600);
		
		if(hours < 0){
    		CUtils.warningAlert("StartTime Must Earlier Than EndTime!");
    		record.set("hourLogged", "");
			record.set("amount", "");
			return;
    	}
		
		record.set("hourLogged", hours);
		record.set("amount", hours * costPerHour);
		
	},
	
	getLoggedDate : function(year, month, day, hours, minutes) {
		var date = new Date();
		date.setFullYear(year, month, day);
		date.setHours(hours);
		date.setMinutes(minutes);
		return date;
	},
	initGrid : function() {
		var me = this;
		PApp.grid.on('edit', function(editor, obj) {
			var record = obj.record;
			var fieldName = obj.field;
			
			if(fieldName == "startTime" || fieldName == "endTime"){
				var endTime = record.get("endTime");
				var startTime = record.get("startTime");
				var logTime = record.get("logDate");
				var year = logTime.getFullYear();
				var month = logTime.getMonth();
				var day = logTime.getDate();
				
				if(!Strings.isEmpty(endTime) && !Strings.isEmpty(startTime)) {
					var starHour = '';
					var starMin = '';
					var endHour = '';
					var endMin = '';
					
					if(endTime.length == 5) {
						endHour = endTime.substr(0,2);
						endMin =  endTime.substr(3,2);
					} else {
						endHour = endTime.getHours();
						endMin = endTime.getMinutes();
					}
					
					if(startTime.length == 5) {
						starHour = startTime.substr(0,2);
						starMin =  startTime.substr(3,2);
					} else {
						starHour = startTime.getHours();
						starMin = startTime.getMinutes();
					}
					
					me.setTime(year, month, day, starHour, starMin, endHour, endMin, record);
				}
			}
		});
	}
});