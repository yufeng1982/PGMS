/**
 * @author FengYu
 */
Ext.define('EMP.project.project.ProjectAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	checkMsgCount : 0,
	formProcessingBeforeSave : function() {
		$('oilCanLineJsons').value = GUtils.allRecordsToJson(Ext.getCmp('OC_LINE_GRID_ID'));
		$('oilMachineLineJsons').value = GUtils.allRecordsToJson(Ext.getCmp('OM_LINE_GRID_ID'));
		$('otherEquipmentJsons').value = GUtils.allRecordsToJson(Ext.getCmp('OTH_LINE_GRID_ID'));
		$('petrolStationsJsons').value = GUtils.allRecordsToJson(Ext.getCmp('OIL_LINE_GRID_ID'));
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		var me = this;
		var phone = $('phone').value;
		if (!(VUtils.validateMobile(phone) || VUtils.validatePhone(phone))) {
			msgarray.push({fieldname:"phone", message: PRes["Phone"], arg:null});
		}
		var carRoadCounts = $('carRoadCounts').value;
		if (!VUtils.isFloat(carRoadCounts)) {
			msgarray.push({fieldname:"carRoadCounts", message: PRes["CarRoadCounts"] + ":" + PRes["VNumber"], arg:null});
		}
		if (carRoadCounts <= 0) {
			msgarray.push({fieldname:"carRoadCounts", message: PRes["CarRoadCounts"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
//		var zjgs = $('zjgs').value;
//		if (!VUtils.isFloat(zjgs)) {
//			msgarray.push({fieldname:"zjgs", message: PRes["Zjgs"] + ":" + PRes["VNumber"], arg:null});
//		}
//		if (zjgs <= 0) {
//			msgarray.push({fieldname:"zjgs", message: PRes["Zjgs"] + ":" + PRes["VGreaterThanZero"], arg:null});
//		}
		var salesForecast = $('salesForecast').value;
		if (!VUtils.isFloat(salesForecast)) {
			msgarray.push({fieldname:"salesForecast", message: PRes["SalesForecast"] + ":" + PRes["VNumber"], arg:null});
		}
		if (salesForecast <= 0) {
			msgarray.push({fieldname:"salesForecast", message: PRes["SalesForecast"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		var qiyouP = $('qiyouP').value;
		if (!VUtils.isFloat(qiyouP)) {
			msgarray.push({fieldname:"qiyouP", message: PRes["QiyouP"] + ":" + PRes["VNumber"], arg:null});
		}
		if (qiyouP <= 0) {
			msgarray.push({fieldname:"qiyouP", message: PRes["QiyouP"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		var caiyouP = $('caiyouP').value;
		if (!VUtils.isFloat(caiyouP)) {
			msgarray.push({fieldname:"caiyouP", message: PRes["CaiyouP"] + ":" + PRes["VNumber"], arg:null});
		}
		if (caiyouP <= 0) {
			msgarray.push({fieldname:"caiyouP", message: PRes["CaiyouP"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		var ynTdz = CUtils.getSValue('ynTdz');
		var ynYdhx = CUtils.getSValue('ynYdhx');
		var ynYzhgzs = CUtils.getSValue('ynYzhgzs');
		var ynJchgz = CUtils.getSValue('ynJchgz');
		var ynJyxkz = CUtils.getSValue('ynJyxkz');
		
		var filePath1 = $('filePath1').value
		var file1 = Ext.getCmp('attachment1').getValue();
		if (ynTdz == 'Yes' && Strings.isEmpty(file1) && Strings.isEmpty(filePath1)){
			msgarray.push({fieldname:"attachment1", message: PRes["Com.FileName1"]+PRes["AttachmentNotNull"], arg:null});
		}
		var filePath2 = $('filePath2').value
		var file2 = Ext.getCmp('attachment2').getValue();
		if (ynYdhx == 'Yes' && Strings.isEmpty(file2) && Strings.isEmpty(filePath2)){
			msgarray.push({fieldname:"attachment2", message: PRes["Com.FileName2"]+PRes["AttachmentNotNull"], arg:null});
		}
		var filePath3 = $('filePath3').value
		var file3 = Ext.getCmp('attachment3').getValue();
		if (ynYzhgzs == 'Yes' && Strings.isEmpty(file3) && Strings.isEmpty(filePath3)){
			msgarray.push({fieldname:"attachment3", message: PRes["Com.FileName3"]+PRes["AttachmentNotNull"], arg:null});
		}
		var filePath4 = $('filePath4').value
		var file4 = Ext.getCmp('attachment4').getValue();
		if (ynJchgz == 'Yes' && Strings.isEmpty(file4) && Strings.isEmpty(filePath4)){
			msgarray.push({fieldname:"attachment4", message: PRes["Com.FileName4"]+PRes["AttachmentNotNull"], arg:null});
		}
		var filePath5 = $('filePath5').value
		var file5 = Ext.getCmp('attachment5').getValue();
		if (ynJyxkz == 'Yes' && Strings.isEmpty(file5) && Strings.isEmpty(filePath5)){
			msgarray.push({fieldname:"attachment5", message: PRes["Com.FileName5"]+PRes["AttachmentNotNull"], arg:null});
		}
		for (var i = 1; i <= 7; i++) {
			var file = Ext.getCmp('attachment' + i).getValue();
			if (!Strings.isEmpty(file)) {
				if (!VUtils.validateAttachmentType(file)) {
					msgarray.push({fieldname:"attachment" + i, message: PRes["Com.FileName" + i]+PRes["VAttachmentType"], arg:null});
				}
			}
		}
		
		return msgarray;
	},
	loadMarkShowEvent : function () {
		Ext.getCmp('okBtn').setDisabled(true);
		Ext.getCmp('applyBtn').setDisabled(true);
	},
	loadMarkHideEvent : function (isDisabled) {
		Ext.getCmp('okBtn').setDisabled(isDisabled);
		Ext.getCmp('applyBtn').setDisabled(isDisabled);
	},
	oscorOnchange : function () {
		return true;
	},
	cityOnchange : function () {
		return true;
	},
	showPct:function () {
		if (PAction.pctWin) {
			PAction.pctWin.show();
		} else {
			PAction.pctWin = Ext.create('Ext.window.Window', {
			    id    : 'dataWindow',
			    height: 80,
			    width : 280,
			    modal : true,
			    layout: 'hbox',
			    closable : false,
			    draggable : false,
			    resizable : false,
			    contentEl: 'pctDiv',
				fbar: [{
		        	   type: 'button', 
		        	   text: PRes["ok"],
		        	   handler : function() {
		        		   var msg = ''
		        		   var province = $j('#loc_province').val();
		        		   var city = $j('#loc_city').val();
		        		   var town = $j('#loc_town').val();
		        		   if (Strings.isEmpty(province)) {
		        			   msg += PRes["VProvince"] + '<br>';
		        		   }
		        		   if (Strings.isEmpty(city)) {
		        			   msg += PRes["VCity"] + '<br>';
		        		   }
		        		   if (Strings.isEmpty(town)) {
		        			   msg += PRes["VTown"];
		        		   }
		        		   if (!Strings.isEmpty(msg)) {
		        			   CUtils.warningAlert(msg);
		        		   } else {
		        			   $('pct').value='';
			        		   $('pct').value += $j("#loc_province").find("option:selected").text() + ' ';
			        		   $('pct').value += $j("#loc_city").find("option:selected").text() + ' ';
			        		   $('pct').value += $j("#loc_town").find("option:selected").text();
			        		   $('province').value = province;
			        		   $('city').value = city;
			        		   $('town').value = town;
			        		   Ext.getCmp('dataWindow').hide();
		        		   }
		        	   }	
		        },{
		        	   type: 'button', 
		        	   text: PRes["cancel"],
		        	   handler : function(){
		        		   Ext.getCmp('dataWindow').hide();
		        	   }
		    	}]     
			});
			PAction.pctWin.on('show',function(obj){
				var pctLeft = $('pct').offsetLeft;
				var pctTop = $('pct').offsetTop;
				obj.setPosition([pctLeft+20,pctTop+120],true);
				$j('#loc_province').val($('province').value);
				$j('#loc_city').val($('city').value);
				$j('#loc_town').val($('town').value);
			});
			PAction.pctWin.show();
		}
		
	},
	initOilLineGrid : function () {
		var oilLineGrid = GUtils.initErpGrid('OIL_LINE_GRID_ID',{sf_EQ_project: $('entityId').value}, CONFIG_OIL_LINE);
		oilLineGrid.store.on('load',function(stroe, records) {
			if (records.length == 0) {
				var grid = Ext.getCmp('OIL_LINE_GRID_ID');
				var r = GUtils.addLine(grid);
				r.set('name', '罩棚')
				var r = GUtils.addLine(grid);
				r.set('name', '站房')
				var r = GUtils.addLine(grid);
				r.set('name', '辅房')
				var r = GUtils.addLine(grid);
				r.set('name', '其他')
				GUtils.resetSequence(grid,'seq');
				$('name').focus();
			}
		});
		return oilLineGrid;
	},
	initOilCanLineGrid : function () {
		var oilCanGrid = GUtils.initErpGrid('OC_LINE_GRID_ID',{sf_EQ_project: $('entityId').value}, CONFIG_OC_LINE);
		oilCanGrid.store.on('load',function(stroe, records) {
			if (records.length == 0) {
				var grid = Ext.getCmp('OC_LINE_GRID_ID');
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				GUtils.resetSequence(grid,'seq');
				$('name').focus();
			}
		});
		oilCanGrid.on('edit', function(editor, e) {
		    if (e.field == 'qty') {
		    	var records = e.grid.store.getRange();
		    	var yggs = 0;
		    	for (var i = 0; i < records.length; i++) {
		    		yggs = CUtils.add(yggs, records[i].get('qty'));
		    	}
		    	$('yggs').value = yggs;
		    	Ext.getCmp('oamygPanel').setTitle('<div id="oamyg">'+ PRes["YG"] +'('+ yggs + PRes["Ge"] +')</div>');
		    }
		});
		return oilCanGrid;
	},
	initOilMachineLineGrid : function () {
		var oilMachineGrid = GUtils.initErpGrid('OM_LINE_GRID_ID',{sf_EQ_project: $('entityId').value}, CONFIG_OM_LINE);
		oilMachineGrid.store.on('load',function(stroe, records) {
			if (records.length == 0) {
				var grid = Ext.getCmp('OM_LINE_GRID_ID');
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				var r = GUtils.addLine(grid);
				GUtils.resetSequence(grid,'seq');
				$('name').focus();
			}
		});
		oilMachineGrid.on('edit', function(editor, e) {
			if (e.field == 'mqty') {
				var records = e.grid.store.getRange();
				var yjts = 0; 
		    	for (var i = 0; i < records.length; i++) {
		    		yjts = CUtils.add(yjts, records[i].get('mqty'));
		    	}
		    	$('yjts').value = yjts;
		    	Ext.getCmp('oamyjPanel').setTitle('<div id="oamyj">'+ PRes["YJ"] +'('+ yjts + PRes["Tai"] +')</div>');
		    }
		});
		return oilMachineGrid;
	},
	initOtherEquipmentLineGrid : function () {
		var otherEquipmentLineGrid = GUtils.initErpGrid('OTH_LINE_GRID_ID',{sf_EQ_project: $('entityId').value}, CONFIG_OTH_LINE);
		otherEquipmentLineGrid.store.on('load',function(stroe, records) {
			if (records.length == 0) {
				var grid = Ext.getCmp('OTH_LINE_GRID_ID');
				var r = GUtils.addLine(grid);
				r.set('name', '潜油泵')
				var r = GUtils.addLine(grid);
				r.set('name', '液位仪')
				var r = GUtils.addLine(grid);
				r.set('name', '发电机')
				var r = GUtils.addLine(grid);
				r.set('name', '稳压器')
				var r = GUtils.addLine(grid);
				r.set('name', '监  控')
				var r = GUtils.addLine(grid);
				r.set('name', '压力罐')
				var r = GUtils.addLine(grid);
				r.set('name', '锅  炉 ')
				GUtils.resetSequence(grid,'seq');
				$('name').focus();
			}
		});
		return otherEquipmentLineGrid;
	}
});