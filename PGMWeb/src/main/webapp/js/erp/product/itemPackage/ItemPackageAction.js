Ext.define('PGM.photo.ItemPackageAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	itemPackageClothesGrid : null,
	itemPackageImagesGrid : null,
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;	
	},
	initItemPackageLineGrid : function (id, args) {
		var me = this;
		var itemPackageDetailGrid = GUtils.initErpGrid(id, args);
		itemPackageDetailGrid.on('edit',function(editor, e){
			if (e.field == 'quantity' || e.field == 'price') {
				var record = e.record;
				record.set('amount',CUtils.mul(record.get('price'), record.get('quantity')));
			}
		});
		me.grid = itemPackageDetailGrid;
		return me.grid;
	},
	initItemPackageClothesGrid : function (id, args, config) {
		var me = this;
		var itemPackageClothesGrid = GUtils.initErpGrid(id, args, config);
		me.itemPackageClothesGrid = itemPackageClothesGrid;
		return me.itemPackageClothesGrid;
	},
	initItemPackageImagesGrid : function (id, args, config) {
		var me = this;
		var itemPackageImagesGrid = GUtils.initErpGrid(id, args, config);
		itemPackageImagesGrid.on('beforeedit',function(eidtor,e){
			if (!Strings.isEmpty(e.record.get('id'))) {
				return false;
			}
		});
		me.itemPackageImagesGrid = itemPackageImagesGrid;
		return me.itemPackageImagesGrid;
	},
	addLine :  function(data) {
		var me = this;
		GUtils.addLine(me.grid,data);
		GUtils.resetSequence(me.grid);
	},
	removeLine : function(grid, record) {
		var me = this;
		GUtils.removeLine(me.grid,"itemPackageDeleteLines",record);
		GUtils.resetSequence(me.grid);
	},
	addClothesLine :  function(data) {
		this.popupMultipleClothes();
	},
	removeClothesLine : function(grid, record) {
		var me = this;
		GUtils.removeLine(me.itemPackageClothesGrid,"itemPackageDeleteClothesLines",record);
		GUtils.resetSequence(me.itemPackageClothesGrid);
	},
	addImagesLine :  function(data) {
		var me = this;
		if (!me.isNew()) {
			GUtils.addLine(me.itemPackageImagesGrid, data);
		}
	},
	removeImagesLine : function(grid, record) {
		debugger;
		var me = this;
		var record = GUtils.getSelected(PAction.itemPackageImagesGrid);
		var id = record.get('id');
		GUtils.removeLine(me.itemPackageImagesGrid,"itemPackageDeleteImagesLines",record);
		if (!Strings.isEmpty(id)) {
			var params = {recordId : id};
			Ext.Ajax.request({
			    url: '/app/pgm/photo/itemPackage/form/'+ $(entityId).value + '/deleteImages',
			    params : params,
			    timeout: 60000,
			    success: function(response){
			    	var returnVal = response.responseText;
		    		if (returnVal == 'fileToBig') {
		    			CUtils.infoAlert(PRes["VFileSize"]);
		    		} else if (returnVal == 'remoteClose') {
		    			CUtils.infoAlert(PRes["VRemote"]);
		    		} else {
		    			PAction.itemPackageImagesGrid.store.load();
		    		}
			    },
			});
		}
	},
	formProcessingBeforeSave : function() {
		var me = this;
		$('itemPackageLineJsons').value = GUtils.allRecordsToJson(me.grid);
		$('itemPackageClothesJsons').value = GUtils.allRecordsToJson(me.itemPackageClothesGrid);
	},
	formValidationBeforeSave : function() {
		var me = this;
		var msgarray = [];
		var records = me.grid.getStore().getRange();
		var clothesRec = me.itemPackageClothesGrid.getStore().getRange();
		if (records.length == 0) {
			msgarray.push({fieldname:"ITEMPACKAGE_DETAIL_GRID_ID", message: PRes['AddKitsData'], arg:null});
		}
		if(clothesRec.length == 0){
			msgarray.push({fieldname:"ITEMPACKAGE_CLOTHES_GRID_ID", message: PRes['AddClothesData'], arg:null});
		}
		for(var i = 0; i < records.length; i++) {
			if (Strings.isEmpty(records[i].get('itemId'))){
				msgarray.push({fieldname:"ITEMPACKAGE_DETAIL_GRID_ID", message: PRes['AtLine'] + (i+1) + PRes['Line'] + PRes['Item'] +  PRes['NotEmpty'], arg:null});
			}
			if (Strings.isEmpty(records[i].get('price'))){
				msgarray.push({fieldname:"ITEMPACKAGE_DETAIL_GRID_ID", message: PRes['AtLine'] + (i+1) + PRes['Line'] + PRes['Price'] +  PRes['NotEmpty'], arg:null});		
			}
			if (Strings.isEmpty(records[i].get('quantity'))){
				msgarray.push({fieldname:"ITEMPACKAGE_DETAIL_GRID_ID", message: PRes['AtLine'] + (i+1) + PRes['Line'] + PRes['Quantity'] +  PRes['NotEmpty'], arg:null});
			}
		}
		return msgarray;
	},
	popupItem : function (id) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "product/item/_includes/_itemsGrid";
		arg["callBack"] = 'popupItem_callBack';
		arg["parameters"] = {};
		LUtils.showPopupSelector(arg);
	},
	popupItem_callBack : function (id, action, returnVal, arg) {
		if (action == "ok") {
			var record = GUtils.getSelected(PAction.grid);
			GUtils.setPopupValue(record, "itemId", returnVal[0].id, "item",  returnVal[0].name,'ITEMPACKAGE_DETAIL_GRID_ID');
			record.set('price', returnVal[0].price);
			record.set('description', returnVal[0].description);
		}
	},
	popupClothes : function (id) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "product/clothes/_includes/_clothessGrid";
		arg["callBack"] = 'popupClothes_callBack';
		arg["parameters"] = {};
		LUtils.showPopupSelector(arg);
	},
	popupClothes_callBack : function (id, action, returnVal, arg) {
		if (action == "ok") {
			var record = GUtils.getSelected(PAction.itemPackageClothesGrid);
			GUtils.setPopupValue(record, "clothesId", returnVal[0].id, "sequence",  returnVal[0].sequence,'ITEMPACKAGE_CLOTHES_GRID_ID');
			record.set('name', returnVal[0].name);
			record.set('description', returnVal[0].description);
		}
	},
	popupMultipleClothes : function (id) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "product/clothes/_includes/_clothessGrid";
		arg["callBack"] = 'popupMultipleClothes_callBack';
		arg["parameters"] = {};
		arg["multiple"] = true;
		LUtils.showPopupSelector(arg);
	},
	popupMultipleClothes_callBack : function (id, action, returnVal, arg) {
		if (action == "ok") {
			var store = PAction.itemPackageClothesGrid.getView().getStore();
			for (var i = 0; i < returnVal.length; i++) {
				if (store.findRecord('clothesId', returnVal[i].id)) continue;
				var record = GUtils.addLine(PAction.itemPackageClothesGrid);
				GUtils.setPopupValue(record, "clothesId", returnVal[i].id, "sequence",  returnVal[i].sequence,'ITEMPACKAGE_CLOTHES_GRID_ID');
				record.set('name', returnVal[i].name);
				record.set('description', returnVal[i].description);
			}
		}
	},
	openUploadFile : function (){
		Ext.create('Ext.window.Window', {
			id : 'imageUpload',
			height: 150,
			width: 450,
			x : 310,
			y :450,
		    resizable : false,
		    modal : true,
		    layout: 'vbox',
		    margin: '0 0 300 0',
		    title : PRes["imageUpload"],
		    items : [{
		    	xtype: 'displayfield',
		        fieldLabel: PRes["imageNote"],
		        value: PRes["imageNoteValue"]
		    },{
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
		    }],
		    renderTo : "fileUpload",
		    fbar: [{
	        	   type: 'button', 
	        	   text: PRes["ok"],
	        	   handler : function(){
	        		   var record = GUtils.getSelected(PAction.itemPackageImagesGrid);
	        		   var recordId = record.get('id');
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
		        		    form:'form1',
		        		    url : '/app/pgm/photo/itemPackage/form/'+ $(entityId).value + '/uploadImages',
		        		    params : {recordId : recordId},
		        		    success: function(response){
		        		    	if(!Strings.isEmpty(response.responseText)){
        				    		var returnVal = response.responseText;
        				    		if (returnVal == 'fileToBig') {
        				    			CUtils.infoAlert(PRes["VFileSize"]);
        				    		} else if (returnVal == 'remoteClose') {
        				    			CUtils.infoAlert(PRes["VRemote"]);
        				    		} else {
        				    			PAction.itemPackageImagesGrid.store.load();
        				    		}
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