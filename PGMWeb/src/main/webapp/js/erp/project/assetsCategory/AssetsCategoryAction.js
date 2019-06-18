/**
 * @author FengYu
 */
Ext.define('EMP.project.assetsCategory.AssetsCategoryAction' ,{
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

		return msgarray;
	},
	checkComponentUnique : function (obj) {
		var me = this;
		me.showLoadMark("fr_" + obj.id, "Checking...");
		var params = {code : obj.value};
		Ext.Ajax.request({
		    url: '/app/pgm/project/assetsCategory/list/' + obj.id + "Check",
		    params : params,
		    timeout: 60000,
		    success: function(response){
		    	var returnValue = response.responseText;
		    	if(returnValue == 'true'){
		    		var msgarray = [];
		    		msgarray.push({fieldname:obj.id, message:"编号已经存在！", arg: null});
		    		VUtils.processValidateMessages(msgarray);
		    		me.checkMsgCount++;
		    		me.hideLoadMark(true); 
		    	} else {
		    		VUtils.removeFieldErrorCls(obj.id);
		    		VUtils.removeTooltip(obj.id);
		    		me.checkMsgCount--;
		    		me.hideLoadMark(me.checkMsgCount <= 0 ? false : true); 
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
		        		   $('_parent').value=record.get('name');
		        		   $('parent').value=record.get('id');
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
				var pctLeft = $('_parent').offsetLeft;
				var pctTop = $('_parent').offsetTop;
				obj.setPosition([pctLeft+20,pctTop+120],true);
			});
			PAction.pctWin.show();
		}
	}
});