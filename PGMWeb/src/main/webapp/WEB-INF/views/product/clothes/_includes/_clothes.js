function page_OnLoad() {
	PRes["imageUpload"] = "${f:getText('Com.ImageUpload')}";
	PRes["selectFile"] = "${f:getText('Com.SelectFile')}";
	PRes["Uploading"] = "${f:getText('Com.Uploading')}";
	PRes["UploadSuccess"] = "${f:getText('Com.UploadSuccess')}";
	PRes["UploadFailed"] = "${f:getText('Com.UploadFailed')}";
	PRes["VImagePath"] = "${f:getText('Com.VImagePath')}";
	PRes["VUploadFileType"] = "${f:getText('Com.VUploadFileType')}";
	PRes["VFileSize"] = "${f:getText('Com.VFileSize')}";
	PRes["VChooseFile"] = "${f:getText('Com.VChooseFile')}";
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new PGM.photo.ClothesAction();
	var wrapped = Ext.create('Ext.create', 'Ext.resizer.Resizer', {
	    target: 'wrapped',
	    pinned:true,
	    minWidth:50,
	    minHeight: 50,
	    preserveRatio: true
	});
	Ext.create('Ext.Button', {
	    text: PRes["imageUpload"],
	    renderTo: 'imgBtn',
	    width : 300,
	    disabled : PAction.isNew(),
	    handler: function() {
	    	Ext.create('Ext.window.Window', {
				id : 'imageUpload',
				height: 150,
				width: 450,
				x : 400,
				y :300,
			    resizable : false,
			    modal : true,
			    layout: 'vbox',
			    margin: '0 0 300 0',
			    title : PRes["imageUpload"],
			    items : {
					xtype: 'filefield',
					id:'attachment',
					name: 'attachment',
					labelWidth: 130,
					msgTarget: 'side',
					allowBlank: true,
					anchor: '100%',
					height : 20,
					width : 420,
					margin: '30 0 0 2',
					buttonText: PRes["imageUpload"]
			    },
			    renderTo : "uploadImages",
			    fbar: [{
		        	   type: 'button', 
		        	   text: PRes["ok"],
		        	   handler : function(){
		        		   var value = CUtils.getSValue("attachment");
		        		   if(Strings.isEmpty(value)) {
		        			   CUtils.warningAlert(PRes["VChooseFile"]);
		        			   return;
		        		   }
		        		   if(!VUtils.validateImageType(value)) {
		        			   CUtils.warningAlert(PRes["VUploadFileType"]);
		        			   return;
		        		   }
		        			beginWaitCursor(PRes["Uploading"], true);
		        			Ext.Ajax.request({
			        		  	isUpload:true,
			        		    form:'form2',
			        		    success: function(response){
			        		    	if(!Strings.isEmpty(response.responseText)){
	        				    		var returnVal = response.responseText;
	        				    		if (returnVal == 'false') {
	        				    			CUtils.infoAlert(PRes["VFileSize"]);
	        				    		} else {
	        				    			$('wrapped').src = returnVal;
		        				    		$('imagePath').value = returnVal;
		        				    		$('version').value = CUtils.add($('version').value, 1);
	        				    		}
//	        				    		CUtils.infoAlert(PRes["UploadSuccess"]); 
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
		}).show();
	    }
	});
	
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 500,
				id : "GENERAL_PANEL",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
}