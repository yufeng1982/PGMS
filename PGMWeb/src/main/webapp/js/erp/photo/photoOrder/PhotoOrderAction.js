Ext.define('PGM.photo.PhotoOrderAction' ,{
	extend : 'ERP.FormAction',
	additionalLineGrid : null,
	
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;	
	},
	addLine :  function(data) {
		this.popupMultipleItem();
		//GUtils.addLine(Ext.getCmp('PHOTOORDER_ADDITIONAL_GRID_ID'), data);
		//GUtils.resetSequence(Ext.getCmp('PHOTOORDER_DETAIL_GRID_ID'));
	},
	removeLine : function(grid, record) { 
		GUtils.removeLine(Ext.getCmp('PHOTOORDER_ADDITIONAL_GRID_ID'),"additionalDeleteLines", record);
		//GUtils.resetSequence(Ext.getCmp('PHOTOORDER_DETAIL_GRID_ID'));
	},
	formProcessingBeforeSave : function() {
		$('photoOrderLineJsons').value = GUtils.allRecordsToJson(Ext.getCmp('PHOTOORDER_DETAIL_GRID_ID'));
		$('additionalLineJsons').value = GUtils.allRecordsToJson(this.additionalLineGrid);
		$('photoOrderWFJsons').value = GUtils.allRecordsToJson(Ext.getCmp('PHOTOORDER_WF_GRID_ID'));
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		if (!VUtils.isPositiveNumber($('amount').value)) {
			msgarray.push({fieldname: "amount", message: PRes['FAmount'] + ":    " + PRes['VNumber'], arg:null});
		}
		if (!VUtils.isPositiveFloat($('discount').value)) {
			msgarray.push({fieldname: "discount", message: PRes['FDiscount'] + ":    " + PRes['VNumber'], arg:null});
		}
		if (!VUtils.isPositiveFloat($('frontMoney').value)) {
			msgarray.push({fieldname: "frontMoney", message: PRes['FFrontMoney'] + ":    " + PRes['VNumber'], arg:null});
		}
		if (!VUtils.validatePhone($('customerPhone').value)) {
			msgarray.push({fieldname: "customerPhone", message: PRes['FPhoneCode'] + ":    " + PRes['VPhone'], arg:null});
		}
		if ($('netWorkContact').value.indexOf('@') != -1){
			if (!VUtils.validateEmail($('netWorkContact').value)){
				msgarray.push({fieldname: "netWorkContact", message: PRes['VQQAndEmail'], arg:null});
			}
		} else {
			if (!VUtils.validateNum($('netWorkContact').value)) {
				msgarray.push({fieldname: "netWorkContact", message: PRes['VQQAndEmail'], arg:null});
			}
		}
		
		var records = this.additionalLineGrid.getStore().getRange();
		for(var i = 0; i < records.length; i++){
			if (!VUtils.isPositiveNumber(records[i].get('price'))) {
				msgarray.push({fieldname: "PHOTOORDER_ADDITIONAL_GRID_ID", message: PRes['Additional'] + " : " + PRes['AtLine'] + (i+1) +PRes['Line'] + " " + PRes['Price'] + PRes['PositiveNumber'], arg:null});
			}
			if (!VUtils.isPositiveNumber(records[i].get('quantity'))) {
				msgarray.push({fieldname: "PHOTOORDER_ADDITIONAL_GRID_ID", message: PRes['Additional'] + " : " + PRes['AtLine'] + (i+1) +PRes['Line'] + " " + PRes['Quantity'] + PRes['PositiveNumber'], arg:null});
			}
		}
		
		var grid = Ext.getCmp('PHOTOORDER_DETAIL_GRID_ID');
		var records = grid.getView().store.getRange();
		if (records == 0){
			msgarray.push({fieldname: "PHOTOORDER_DETAIL_GRID_ID", message: PRes['VEditDetail'], arg:null});
		}
		return msgarray;
	},
	packageOnchange : function (id, text, data, obj) {
		$('amount').value = data.amount;
		var photoStore = Ext.getCmp('PHOTOORDER_DETAIL_GRID_ID').store;
		photoStore.proxy.url = '/app/pgm/photo/itemPackage/line/list/json';
		photoStore.proxy.extraParams = {entityId : id};
		photoStore.on('load', function (stroe, records) {
			for (var i = 0; i < records.length; i++) {
				records[i].set('id', '');
			}
		});
		photoStore.load();
		this.setOrderGeneralTotalAmount();
		return true;
	},
	orderPeopleOnchange : function () {
		return true;
	},
	initParams4Employee : function () {
		return {sf_EQ_employeeType : 'Employee'};
	},
	showEmployees : function (id) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "hr/employee/_includes/_employeesGrid";
		arg["callBack"] = 'popupEmployee_callBack';
		arg["parameters"] = {sf_EQ_employeeType : 'Employee'};
		arg["multiple"] = true;
		LUtils.showPopupSelector(arg);
	},
	popupEmployee_callBack : function (id, action, returnVal, arg) {
		if (action == "ok") {
			var employeeIds = "";
			var employees = "";
			var record = GUtils.getSelected(Ext.getCmp('PHOTOORDER_WF_GRID_ID'));
			if(!record) return false;
			for (var i = 0; i < returnVal.length; i++) {
				if(Strings.isEmpty(employeeIds)){
					employeeIds = returnVal[i].id;
					employees = returnVal[i].name;
				} else {
					employeeIds += "," + returnVal[i].id;
					employees += "," + returnVal[i].name;
				}
			}
			GUtils.setPopupValue(record, "employeeIds", employeeIds, "employees", employees,'PHOTOORDER_WF_GRID_ID');
		}
		
	},
	applyWf : function () {
		var record = GUtils.getSelected(Ext.getCmp('PHOTOORDER_WF_GRID_ID'));
		var msgarray = [];
		if (Strings.isEmpty(record.get('currentPoWFType'))) {
			msgarray.push({fieldname: "", message: PRes['AtLine'] + record.get('seq') +PRes['Line'] + " " + PRes['Status'] + PRes["NotEmpty"], arg:null});
		}
		if (Strings.isEmpty(record.get('employees'))) {
			msgarray.push({fieldname: "", message: PRes['AtLine'] + record.get('seq') +PRes['Line'] + " " + PRes['Executors'] + PRes["NotEmpty"], arg:null});
		}
		if (msgarray.length > 0) {
			VUtils.processValidateMessages(msgarray);
			return false;
		}
		Ext.Ajax.request({
		    url: '/app/pgm/photo/photoOrderWF/list/changeStatus',
		    params : {powf : record.get('id'), status : record.get('currentPoWFType')},
		    timeout: 60000,
		    success: function(response){
		    	if(CUtils.isTrueVal(response.responseText)){
		    		Ext.getCmp('PHOTOORDER_WF_GRID_ID').store.load();
		    	}
		    },
		});
	},
	initAdditionalLineGrid : function (id, args, config) {
		var me = this;
		var additionalLineGrid = GUtils.initErpGrid(id, args, config);
		additionalLineGrid.on('edit',function(editor, e){
			if (e.field == 'quantity' || e.field == 'price') {
				var record = e.record;
				var orginalAmount = record.get('amount');
				record.set('amount', CUtils.mul(record.get('price'), record.get('quantity')));
				$('additionalAmount').value = CUtils.add(CUtils.sub($('additionalAmount').value, orginalAmount), record.get('amount'));
				PAction.setAdditionalTotalAmount();
			}
		});
		me.additionalLineGrid = additionalLineGrid;
		return me.additionalLineGrid;
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
			var store = PAction.additionalLineGrid.getView().getStore();
			if(store.findRecord('itemId', returnVal[0].id)) return;
			var record = GUtils.getSelected(PAction.additionalLineGrid);
			var orginalAmaout = record.get('amount');
			GUtils.setPopupValue(record, "itemId", returnVal[0].id, "item",  returnVal[0].name,'PHOTOORDER_ADDITIONAL_GRID_ID');
			record.set('price', returnVal[0].price);
			record.set('quantity', 1);
			record.set('amount', returnVal[0].price);
			record.set('description', returnVal[0].description);
			$('additionalAmount').value = CUtils.add(CUtils.sub($('additionalAmount').value, orginalAmaout), returnVal[0].price);
			PAction.setAdditionalTotalAmount();
			this.setOrderGeneralTotalAmount();
		}
	},
	popupMultipleItem : function (id) {
		var arg = {};
		arg["cmpId"] = id;
		arg["gridUrl"] = "product/item/_includes/_itemsGrid";
		arg["callBack"] = 'popupMultipleItem_callBack';
		arg["parameters"] = {};
		arg["multiple"] = true;
		LUtils.showPopupSelector(arg);
	},
	popupMultipleItem_callBack : function (id, action, returnVal, arg) {
		if (action == "ok") {
			var store = PAction.additionalLineGrid.getView().getStore();
			for (var i = 0; i < returnVal.length; i++) {
				if (store.findRecord('itemId', returnVal[i].id)) continue;
				var record = GUtils.addLine(PAction.additionalLineGrid);
				GUtils.setPopupValue(record, "itemId", returnVal[i].id, "item",  returnVal[i].name,'PHOTOORDER_ADDITIONAL_GRID_ID');
				record.set('price', returnVal[i].price);
				record.set('quantity', 1);
				record.set('amount', returnVal[i].price);
				record.set('description', returnVal[i].description);
				$('additionalAmount').value = CUtils.add($('additionalAmount').value, returnVal[i].price);
			}
			PAction.setAdditionalTotalAmount();
			this.setOrderGeneralTotalAmount();
		}
	},
	setAdditionalTotalAmount : function () {
		Ext.getCmp('PHOTOORDER_ADDITIONAL_PANEL_ID').setTitle(ss_icon('ss_application_form') + PRes['Additional'] + " (" + PRes['TotalAmount'] + " : " + $('additionalAmount').value + ")");
	},
	setOrderGeneralTotalAmount : function(){
		Ext.getCmp('GENERAL_PANEL_ID').setTitle(ss_icon('ss_application_form') + 
				PRes['OrderGeneral'] + 
				" (" + PRes['TotalAmount'] + " : " + 
				CUtils.add($('additionalAmount').value, $('amount').value) + ", " + 
				PRes['Discount']  + " : " + $('discount').value + ", " + 
				PRes['FrontMoney'] + " : " + $('frontMoney').value + ", " + 
				PRes['BalanceAmount'] + " : " + 
				CUtils.sub(CUtils.add($('additionalAmount').value, CUtils.mul($('amount').value, 1 - CUtils.div($('discount').value,100))), $('frontMoney').value) +")");
	},
	amountOnchange : function() {
		this.setOrderGeneralTotalAmount();
	}
});