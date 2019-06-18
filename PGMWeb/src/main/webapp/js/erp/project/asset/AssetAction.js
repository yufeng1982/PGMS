/**
 * @author FengYu
 */
Ext.define('EMP.project.asset.AssetAction' ,{
	extend : 'ERP.FormAction',
	grid : null,
	constructor : function(config) {
		this.callParent([config]);
		Ext.apply(this , config);
		return this;
	},
	checkMsgCount : 0,
	formProcessingBeforeSave : function() {
		
	},
	formValidationBeforeSave : function() {
		var msgarray = [];
		var quantity = $('quantity').value;
		if (!VUtils.isPositiveIntegerWithoutZero(quantity)) {
			msgarray.push({fieldname:"quantity", message: PRes["Quantity"] + ":" + PRes["VInteger"], arg:null});
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
	},
	initParams4Contract : function(){
		return {sf_EQ_petrolStation : CUtils.getSSValue('petrolStation'), sf_EQ_contractType : 'Main',sf_EQ_contractStatus :'Archive'};
	},
	contractOnchange : function (id, text, data, obj) {
		CUtils.setSSValue('department', data.departmentId, data.department);
		CUtils.disableCmp('department', false);
		CUtils.setSSValue('vendor', data.cooperation,data.cooperationText);
		$('assetsCategory').value = data.assetsCategory;
		$('_assetsCategory').value = data.assetsCategoryText;
		return true;
	},
	initParams4Project : function(){
		return {sf_IN_id : $('ids').value};
	},
	projectOnchange : function (id, text, data, obj) {
		CUtils.setSSValue('vendor', '','');
		$('assetsCategory').value = '';
		$('_assetsCategory').value = '';
		CUtils.setSSValue('contract', '', '');
		return true;
	}
});