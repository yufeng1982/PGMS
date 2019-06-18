Ext.define('ERP.OurSideCorporation.OurSideCorporationAction',{
	extend : 'ERP.ListAction',
	launcherFuncName : "showOurSideCorporation",
	constructor : function(config) {
		this.callParent([ config ]);
		Ext.apply(this, config);
		return this;
	},
	addLine : function() {
		GUtils.addLine(PApp.grid);
		var record = GUtils.getSelected(PApp.grid);
	},
	removeLine : function () {
		var record = GUtils.getSelected(PApp.grid);
		GUtils.removeLine(PApp.grid);
		if (!Strings.isEmpty(record.get('id'))) {
			Ext.Ajax.request({
			    url: document.forms[0].action + 'delete',
			    params: {
			    	id: record.get('id')
			    },
			    success : function(response) {
			    	PApp.grid.store.load();
			    }
			});
		}
		
	},
	submitForm : function(action) {
		if (this.formProcessingBeforeValidation) {
			this.formProcessingBeforeValidation();
		}

		VUtils.removeAllErrorFields();

		var msg = new Array();
		msg.addAll(VUtils.commonComponentValidate());
		if (this.formValidationBeforeSave) {
			msg.addAll(this.formValidationBeforeSave());
		}
		if (msg.length == 0 || action.indexOf("cancel") != -1) {
			if (this.formProcessingBeforeSave) {
				this.formProcessingBeforeSave();
			}
			this.redirectFormSubmit(action);
		} else {
			endWaitCursor();
			VUtils.processValidateMessages(msg);
		}
	},
	redirectFormSubmit : function(action, showMainFrameMask) {
		this.appendFormAction(action);
		this.formSubmit(action);
	},
	appendFormAction : function(url) {
		if (document.forms[0].action.indexOf(url) < 0) {
			document.forms[0].$oldAction = document.forms[0].action;
			document.forms[0].action += url;
		}
	},
	formSubmit : function(action) {
		if (action) $("_action__").value = action;

		document.forms[0].submit();
	},
	formProcessingBeforeSave : function() {
		$("modifiedRecords").value = GUtils.allRecordsToJson(this.getPropGrid());
	},
	getPropGrid : function() {
		return PApp.grid;
	},
	formValidationBeforeSave : function() {
		var msgarray = new Array();
		return msgarray;
	},
	showPct : function(id,trigger) {
		var record = GUtils.getSelected(PApp.grid);
		showLocation(record.get('province'),record.get('city'),record.get('town'));
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
		        		   var record = GUtils.getSelected(PApp.grid);
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
		        			   var pct = '';
		        			   pct += $j("#loc_province").find("option:selected").text() + ' ';
		        			   pct += $j("#loc_city").find("option:selected").text() + ' ';
		        			   pct += $j("#loc_town").find("option:selected").text();
		        			   record.set('pct', pct);
			        		   record.set('province', province);
			        		   record.set('city', city); 
			        		   record.set('town', town);
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
				var record = GUtils.getSelected(PApp.grid);
				var column = GUtils.getColumn('pct');
				var pctLeft = column.x;
				var pctTop = column.y;
				obj.setPosition([trigger.el.getX(),trigger.el.getY() + trigger.getHeight()+1],true);
				if (!Strings.isEmpty(record.get('pct'))) {
					$j('#loc_province').val(record.get('province'));
					$j('#loc_city').val(record.get('city'));
					$j('#loc_town').val(record.get('town'));
				} else {
					$j('#loc_province').val('');
					$j('#loc_city').val('');
					$j('#loc_town').val('');
				}
				
			});
			PAction.pctWin.show();
		}
	}
});