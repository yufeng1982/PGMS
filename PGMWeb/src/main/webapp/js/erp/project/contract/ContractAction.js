/**
 * @author FengYu
 */
Ext.define('EMP.project.contract.ContractAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	workAction : null, 
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	checkMsgCount : 0,
	formProcessingBeforeSave : function() {
		CUtils.disableCmp('attachment', false);
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		var me = this;
		var attachment = Ext.getCmp('attachment').getValue();
		if (me.isNew()) {
			if (Strings.isEmpty(attachment)) {
				msgarray.push({fieldname:"attachment", message: PRes["VAttachment"], arg:null});
			}
		}
		if (!Strings.isEmpty(attachment)) {
			if (!VUtils.validatePDFType(attachment)) {
				msgarray.push({fieldname:"attachment", message: PRes["ContranctAttachment"]+PRes["VAttachmentType"], arg:null});
			}
		}
		var contractType = CUtils.getSValue('contractType')
		if (contractType == 'Supplementary') {
			var ownerContract = CUtils.getSSValue('ownerContract');
			if (Strings.isEmpty(ownerContract)) {
				msgarray.push({fieldname:"ownerContract", message: PRes["VOwnerContract"], arg:null});
			}
		}
		var qualityGuaranteePeriod = $('qualityGuaranteePeriod').value;
		var amount = $('amount').value;
		var onePercent = $('onePercent').value;
		var twoPercent = $('twoPercent').value;
		var threePercent = $('threePercent').value;
		var fourPercent = $('fourPercent').value;
		if (!VUtils.isPositiveInteger(qualityGuaranteePeriod)) {
			msgarray.push({fieldname:"qualityGuaranteePeriod", message: PRes["QualityGuaranteePeriod"] + ":" + PRes["VPositiveInteger"], arg:null});
		}
		if (!VUtils.isFloat(amount)) {
			msgarray.push({fieldname:"amount", message: PRes["Amount"] + ":" + PRes["VNumber"], arg:null});
		}
		if (!VUtils.isFloat(onePercent)) {
			msgarray.push({fieldname:"onePercent", message: PRes["OnePercent"] + ":" + PRes["VNumber"], arg:null});
		}
		if (!VUtils.isFloat(twoPercent)) {
			msgarray.push({fieldname:"twoPercent", message: PRes["TwoPercent"] + ":" + PRes["VNumber"], arg:null});
		}
		if (!VUtils.isFloat(threePercent)) {
			msgarray.push({fieldname:"threePercent", message: PRes["ThreePercent"] + ":" + PRes["VNumber"], arg:null});
		}
		if (!VUtils.isFloat(fourPercent)) {
			msgarray.push({fieldname:"fourPercent", message: PRes["FourPercent"] + ":" + PRes["VNumber"], arg:null});
		}
		
		
		if (amount <= 0) {
			msgarray.push({fieldname:"amount", message: PRes["Amount"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		if (onePercent < 0) {
			msgarray.push({fieldname:"onePercent", message: PRes["OnePercent"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		if (twoPercent < 0) {
			msgarray.push({fieldname:"twoPercent", message: PRes["TwoPercent"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		if (threePercent < 0) {
			msgarray.push({fieldname:"threePercent", message: PRes["ThreePercent"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		if (fourPercent < 0) {
			msgarray.push({fieldname:"fourPercent", message: PRes["FourPercent"] + ":" + PRes["VGreaterThanZero"], arg:null});
		}
		if (CUtils.add(onePercent,twoPercent,threePercent,fourPercent) != 100) {
			msgarray.push({fieldname:"fourPercent", message: PRes["VTotalPercent"], arg:null});
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
	projectOnchange : function (id, text, data, obj) {
		$('projectCode').value = data.code;
		var ccCode = CUtils.getSText('contractCategory');
		if (Strings.isEmpty(ccCode)) {
			ccCode = ""
		} else {
			ccCode = ccCode.split("-")[0];
		}
		this.setCode(data.code, ccCode , $('seqNo').value);
		$('name').value = data.name + $('signingReason').value;
		return true;
	},
	signingReasonOnchange : function (id, text, data, obj) {
		$('name').value = CUtils.getSSText('petrolStation') + $('signingReason').value;
		return true;
	},
	contractCategoryOnchange : function(id, text, data, obj) {
		var code = text.split("-");
		this.setCode($('projectCode').value, code[0], $('seqNo').value);
		return true;
	},
	cooperationOnchange : function (id, text, data, obj) {
		return true;
	},
	cooperationAccountOnchange : function (id, text, data, obj) {
		$('bank').value = data.bank;
		return true;
	},
	contractPropertyOnchange : function (id, text, data, obj) {
		if (id == 'Main') {
			CUtils.disableCmp('ownerContract', true);
			CUtils.setSSValue('ownerContract','','')
		} else {
			CUtils.disableCmp('ownerContract', false);
		}
		return true;
	},
	setCode : function (projectCode, contractCategoryCode, seqNo) {
		var newDate = new Date();
		var year = newDate.getFullYear();
		var month = CUtils.add(newDate.getMonth() + 1);
		if (month < 10) {
			month = "0" + month;
		}
		var date = year + month;
		$('code').value = projectCode + "-" + contractCategoryCode + "-" + date + "-" + seqNo;
	},
	initParams4OwnerContract : function () {
		return {sf_EQ_contractType : 'Main', sf_EQ_contractStatus : 'Archive,PendingArchive'};
	},
	initParams4CooperationAccount : function () {
		return {cooperationId : CUtils.getSSValue('cooperation')};
	},
	showOpinionWin : function (action) {
		var me = this;
		me.workAction = action;
		if (!me.win) {
			me.win = Ext.create('Ext.window.Window', {
			    title     : PRes["ApproveOpinion"],
			    id        : 'approveOpinionWin',
			    height    : 200,
			    width     : 400,
			    modal     : true,
			    layout    : 'fit',
			    closeAction : 'hide',
			    items: {  
			    	xtype     : 'textareafield',
			        grow      : true,
			        id        : 'opinionsTextarea',
			        anchor    : '100%'
			    },
			    fbar: [{
		        	   type: 'button', 
		        	   text: PRes["ok"],
		        	   handler : function() {
		        		   Ext.getCmp('approveOpinionWin').hide();
		        		   beginWaitCursor(PRes["Saving"], false);
		        		   $('opinions').value = Ext.getCmp('opinionsTextarea').getValue();
		   				   PAction.submitForm(me.workAction);
		        	   }
		           }, {
		        	   type: 'button', 
		        	   text: PRes["cancel"],
		        	   handler : function(){
		        		   $('opinions').value = '';
		        		   Ext.getCmp('approveOpinionWin').hide();
		        	   }
		        }]
			}).show();
		} else {
			Ext.getCmp('opinionsTextarea').setValue('');
			$('opinions').value = '';
			me.win.show();
		}
	},
	showMainContract : function () {
		var args = {id : CUtils.getSSValue('ownerContract')}
		LUtils.showContract(args);
	},
	showAc : function() {
		if (!PAction.treeGrid) {
			Ext.define('text', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {name: 'id', type: 'string'},
		            {name: 'code', type: 'string'},
		            {name: 'name', type: 'string'},
		            {name: 'description', type: 'string'}
		        ]
			});

		    var store = Ext.create('Ext.data.TreeStore', {
		        model: 'text',
		        proxy: {
		            type: 'ajax',
		            url: '/app/pgm/project/assetsCategory/list/json',
		        },
		        autoLoad : false,
		        clearOnLoad : true,
		    });
		    
		    PAction.treeGrid = Ext.create('Ext.tree.Panel', {
		        collapsible: false,
		        useArrows: true,
		        rootVisible: false,
		        store: store,
		        multiSelect: true,
		        singleExpand: false,
		        columnLines: true,
		        viewConfig:  {
			        stripeRows: true,
			        trackOver: false
			    },
			    height: 400,
		        //the 'columns' property is now 'headers'
		        columns: [{
		            text: 'Id',
		            sortable: true,
		            hidden : true,
		            dataIndex: 'id'
		        },{
		        	xtype: 'treecolumn', //this is so we know which column will show the tree
		        	text: PRes["name"],
		        	flex: 1.5,
		        	sortable: true,
		        	dataIndex: 'name'
		        },{
		        	text: PRes["code"],
		        	flex: 1.5,
		        	sortable: true,
		        	dataIndex: 'code'
		        },{
		            text: PRes["description"],
		            flex: 2,
		            sortable: true,
		            dataIndex: 'description'
		        }]
		    });
		}
		
		
		if (PAction.pctWin) {
			PAction.pctWin.show();
		} else {
			PAction.pctWin = Ext.create('Ext.window.Window', {
			    id    : 'dataWindow',
			    height: 400,
			    width : 580,
			    modal : true,
			    layout: 'fit',
			    closable : false,
			    draggable : false,
			    resizable : false,
			    items: [PAction.treeGrid],
				fbar: [{
		        	   type: 'button', 
		        	   text: PRes["ok"],
		        	   handler : function() {
		        		   var record = GUtils.getSelected(PAction.treeGrid);
		        		   $('_assetsCategory').value=record.get('name');
		        		   $('assetsCategory').value=record.get('id');
		        		   Ext.getCmp('dataWindow').hide();
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
				var pctLeft = $('_assetsCategory').offsetLeft;
				var pctTop = $('_assetsCategory').offsetTop;
				obj.setPosition([pctLeft+20,pctTop+120],true);
			});
			PAction.pctWin.show();
		}
	}
});