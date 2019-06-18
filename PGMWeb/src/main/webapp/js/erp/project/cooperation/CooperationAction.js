/**
 * @author FengYu
 */
Ext.define('EMP.project.project.CooperationAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	checkMsgCount : 0,
	formProcessingBeforeSave : function() {
		$('cooperationAccountJsons').value = GUtils.modifiedRecordsToJson(this.grid);
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		var me = this;
		var file1 = Ext.getCmp('attachment1').getValue();
		var file2 = Ext.getCmp('attachment2').getValue();
		var file3 = Ext.getCmp('attachment3').getValue();
		var file4 = Ext.getCmp('attachment4').getValue();
		var file5 = Ext.getCmp('attachment5').getValue();
		var file6 = Ext.getCmp('attachment6').getValue();
		if (me.isNew()) {
			if (Strings.isEmpty(file1)) {
				msgarray.push({fieldname:"attachment1", message: PRes["VBusinessLicense"], arg:null});
			}
			if (Strings.isEmpty(file2)) {
				msgarray.push({fieldname:"attachment2", message: PRes["VBusinessLicense"], arg:null});
			}
			if (Strings.isEmpty(file3)) {
				msgarray.push({fieldname:"attachment3", message: PRes["VBusinessLicense"], arg:null});
			}
		}
		if (!Strings.isEmpty(file1)) {
			if (!VUtils.validateImageType(file1)) {
				msgarray.push({fieldname:"attachment1", message: PRes["BusinessLicense1"]+PRes["VUploadFileType"], arg:null});
			}
		}
		if (!Strings.isEmpty(file2)) {
			if (!VUtils.validatePDFAndImangeType(file2)) {
				msgarray.push({fieldname:"attachment2", message: PRes["BusinessLicense2"]+PRes["VPdfAndImage"], arg:null});
			}
		}
		if (!Strings.isEmpty(file3)) {
			if (!VUtils.validatePDFAndImangeType(file3)) {
				msgarray.push({fieldname:"attachment3", message: PRes["BusinessLicense3"]+PRes["VPdfAndImage"], arg:null});
			}
		}
		if (!Strings.isEmpty(file4)) {
			if (!VUtils.validatePDFAndImangeType(file4)) {
				msgarray.push({fieldname:"attachment4", message: PRes["BusinessLicense4"]+PRes["VPdfAndImage"], arg:null});
			}
		}
		if (!Strings.isEmpty(file5)) {
			if (!VUtils.validatePDFAndImangeType(file5)) {
				msgarray.push({fieldname:"attachment5", message: PRes["BusinessLicense5"]+PRes["VPdfAndImage"], arg:null});
			}
		}
		if (!Strings.isEmpty(file6)) {
			if (!VUtils.validatePDFAndImangeType(file6)) {
				msgarray.push({fieldname:"attachment6", message: PRes["BusinessLicense6"]+PRes["VPdfAndImage"], arg:null});
			}
		}
		
		var email = $('email').value;
		if (!VUtils.validateEmail(email)) {
			msgarray.push({fieldname:"email", message: PRes["Email"], arg:null});
		}
		var phone = $('phone').value;
		if (!(VUtils.validateMobile(phone) || VUtils.validatePhone(phone))) {
			msgarray.push({fieldname:"phone", message: PRes["Phone"], arg:null});
		}
		var records = this.grid.store.getRange();
		if (records.length == 0) {
			msgarray.push({fieldname:"GRID_ID", message: PRes["VAddAccount"], arg:null});
		} else {
//			for (var i = 0; i < records.length; i++) {
//				var receiveNo = records[i].get('receiveNo');
//				if (!VUtils.isBankAcount(receiveNo)) {
//					msgarray.push({fieldname:"receiveNo", message: PRes["ReceiveNo"] + PRes["VBankCardAccount"], arg:null});
//				}
//			}
		}
		return msgarray;
	},
	initEPGrid : function(grid_id, params) {
		var me = this;
		var cooGrid = GUtils.initErpGrid(grid_id, params);
		cooGrid.on('beforeedit', function (editor, obj, OeOpts){
			if (obj.field == 'filePath') {
				if (Strings.isEmpty(obj.record.get("id"))) {
					return false;
				}
			}
			return true;
		});
		me.grid = cooGrid;
		return cooGrid;
	},
	addLine :  function() {
		GUtils.addLine(this.grid);
	},
	removeLine : function(record) {
		var me = this;
		GUtils.removeLine(me.grid,'cooperationAccountDeleteLines',record);
	},
	checkComponentUnique : function (obj) {
		var me = this;
		me.showLoadMark("fr_" + obj.id, "Checking...");
		var params = {code : obj.value};
		Ext.Ajax.request({
		    url: '/app/pgm/project/cooperation/list/codeCheck',
		    params : params,
		    timeout: 60000,
		    success: function(response){
		    	var returnValue = response.responseText;
		    	if(returnValue == 'true'){
		    		var msgarray = [];
		    		VUtils.removeFieldErrorCls(obj.id);
		    		VUtils.removeTooltip(obj.id);
		    		msgarray.push({fieldname:obj.id, message:PRes["cooperation"] + PRes["code"] + obj.value + PRes["isExist"], arg: null});
		    		VUtils.processValidateMessages(msgarray);
		    		me.hideLoadMark(true); 
		    	} else {
		    		VUtils.removeFieldErrorCls(obj.id);
		    		VUtils.removeTooltip(obj.id);
		    		me.hideLoadMark(false); 
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
	openUploadFile : function (){
		var win = Ext.create('Ext.window.Window', {
			id : 'imageUpload',
			height: 100,
			width: 450,
			x : 710,
			y :300,
		    resizable : false,
		    draggable : true,
		    modal : true,
		    layout: 'vbox',
		    margin: '3 0 3 0',
		    title : PRes["imageUpload"],
		    items : [{
				xtype: 'filefield',
				id:'attachment',
				name: 'attachment',
				labelWidth: 130,
				msgTarget: 'side',
				allowBlank: true,
				anchor: '100%',
				height : 20,
				width : 420,
				margin: '5 0 5 5',
				buttonText: PRes["FileUpload"]
		    }],
		    renderTo : "fileUpload",
		    fbar: [{
	        	   type: 'button', 
	        	   text: PRes["ok"],
	        	   handler : function(){
	        		   var record = GUtils.getSelected(PAction.grid);
	        		   var recordId = record.get('id');
	        		   var receiveName = record.get('receiveName');
	        		   var receiveNo = record.get('receiveNo');
	        		   var bank = record.get('bank');
	        		   var value = CUtils.getSValue("attachment");
	        		   if(!VUtils.validatePDFAndImangeType(value)) {
	        			   endWaitCursor();
	        			   CUtils.warningAlert(PRes["BusinessLicense7"]+PRes["VPdfAndImage"]);
	        			   Ext.getCmp('imageUpload').destroy();
	        			   return;
	        		   }
	        		   endWaitCursor();
	        		   beginWaitCursor(PRes["Uploading"], true);
	        		   Ext.Ajax.request({
		        		  	isUpload:true,
		        		    form:'form1',
		        		    url : '/app/pgm/project/cooperation/form/'+ $("entityId").value + '/uploadFiles',
		        		    params : {recordId : recordId, receiveName : receiveName, receiveNo : receiveNo,bank : bank},
		        		    success: function(response){
		        		    	if(!Strings.isEmpty(response.responseText)){
        				    		var returnVal = response.responseText;
        				    		PAction.grid.store.load();
        				    		endWaitCursor();
	        				    }
		        		     },
		        		     failure: function(response){
		        		    	 CUtils.warningAlert(PRes["UploadFailed"]);
		        		     }
			        	});
			        	Ext.getCmp('imageUpload').destroy();
	        	   }	
	        }, {
	        	   type: 'button', 
	        	   text: PRes["cancel"],
	        	   handler : function(){
	        		   Ext.getCmp('imageUpload').destroy();
	        	   }
	    	}]
    	});
		
		win.on('show', function (win,opts){
			beginWaitCursor("", true);
		});
		win.show();
	}
});