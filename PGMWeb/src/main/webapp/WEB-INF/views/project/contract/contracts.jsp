<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Contract')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<script type="text/javascript" src="/js/erp/lib/ui/field/ERPSearchingSelect.js"></script>
	<jsp:directive.include file="/WEB-INF/views/project/contract/_includes/_contractsGrid.jsp" />
	<script type="text/javascript">
		function page_OnLoad() {
			var searchConfig = {
				layout : 'hbox',
				items:[{
					xtype:'fieldset',
					defaultType: 'textfield',
					defaults: {margins:'2 2 2 2'},
					border : 0,
			        layout: 'vbox',
					items :[{
						fieldLabel: "${f:getText('Com.Contranct.Project')}",
						xtype : "erpsearchingselect",
						config : ${f:sess('PetrolStation')},
						labelWidth: 100,
				        width : 250,
				        id :  'project_filter',
				        name: 'sf_EQ_petrolStation',
				        //onchange : 'groupTypeOnchange',
				        value : ""
					},{
						fieldLabel: "${f:getText('Com.Contranct.ContractCategory')}",
						labelWidth: 100,
						width : 250,
						xtype : 'SelectField',
						//config : ${f:mss('ContractCategory')},
						id : 'contractCategory_filter',
						name : 'sf_EQ_contractCategory',
						data: ${ccList},
						//gridInitParameters : function () {
						//	return {sf_EQ_groupType : CUtils.getSSValue('groupType')}
						//}
					},{
						fieldLabel: "",
						xtype : 'hiddenfield',
						labelWidth: 100,
				        width : 250,
				        id :  'assetsCategory_filter',
				        name: 'sf_EQ_assetsCategory',
				        value : ''
					},{
						fieldLabel: "${f:getText('Com.Contranct.AssetsCategory')}",
						xtype : 'textfield',
						labelWidth: 100,
				        width : 250,
				        id :  '_assetsCategory_filter',
				        name: '_sf_EQ_assetsCategory',
				        listeners : {
				        	focus : {
				        		fn : function() {
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
						    		        		   Ext.getCmp('_assetsCategory_filter').setValue(record.get('name'));
						    		        		   Ext.getCmp('assetsCategory_filter').setValue(record.get('id'));
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
						    				var pctLeft = $('assetsCategory_filter').offsetLeft;
						    				var pctTop = $('assetsCategory_filter').offsetTop;
						    				obj.setPosition([pctLeft+125,pctTop+120],true);
						    			});
						    			PAction.pctWin.show();
						    		}
						    	}
				        	}
				        },
				        value : ""
					}]
				}, {
					xtype:'fieldset',
					defaultType: 'textfield',
					defaults: {margins:'2 2 2 2'},
					border : 0,
			        layout: 'vbox',
					items :[{
						fieldLabel: "${f:getText('Com.Contranct.Name')}",
						xtype : 'textfield',
						labelWidth: 100,
				        width : 250,
				        id : 'name_filter',
				        name: 'sf_LIKE_name',
				        value : ""
					},{
						fieldLabel: "${f:getText('Com.Contranct.Code')}",
						xtype : 'textfield',
						labelWidth: 100,
				        width : 250,
				        id : 'code_filter',
				        name: 'sf_LIKE_code',
				        value : ""
					},{
						fieldLabel: "${f:getText('Com.Contranct.SigningDate')}",
						xtype : 'datefield',
						labelWidth: 100,
				        width : 250,
				        id : 'creationDate_filter',
				        name: 'sf_EQ_creationDate',
				        value : ""
					}]
				}]
			};
			var actionBarItems = [];
			actionBarItems[1] = null;
			var actionBar = new ERP.ListActionBar(actionBarItems);
			PApp = new ERP.ListApplication({
				actionBar : actionBar,
				searchConfig : searchConfig,
				_gDockedItem : {
	                xtype: 'toolbar',
	                items: [btnCreate]
	            }
			});
			Ext.define('ERP.ContranctsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showContract"
			});
			PAction = new ERP.ContranctsAction();
		}
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="pageQueryInfo"/> 
	<form:form id="form1" action="" method="post" modelAttribute="${bindModel}">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>