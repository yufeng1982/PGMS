<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.ProjectReport')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:160px}
		
	</style>
	<script type="text/javascript" src="/js/erp/lib/ui/field/ERPSearchingSelect.js"></script>
	<script type="text/javascript">
	function page_OnLoad() {
		
		PRes["VProject"] = "${f:getText('Com.VProject.NotEmpty')}";
		
		var actionBarItems = [];
		actionBarItems[ACTION_BAR_INDEX.ok] = null;
 	    actionBarItems[ACTION_BAR_INDEX.cancel] = null;
 	    actionBarItems[ACTION_BAR_INDEX.apply] = null;
		var actionBar = new ERP.FormActionBar(actionBarItems);
		
		PApp =  new ERP.FormApplication({
			pageLayout : {
				hiddenHeader : true,
				bodyItems : [{
					xtype : "panel",
					height : 240,
					id : "general-portal",
					layout : 'hbox',
					items : [{
						fieldLabel: "${f:getText('Com.Contranct.Project')}",
						xtype : "erpsearchingselect",
						margin : '20 20 20 20',
						config : ${f:sess('PetrolStation')},
						labelWidth: 100,
				        width : 230,
				        id :  'petrolStation',
				        inputId : 'project_input',
				        value : ""
					},{
						fieldLabel: "${f:getText('Com.AssetLevel')}",
						margin : '20 20 20 20',
						labelWidth: 100,
						width : 230,
						xtype : 'SelectField',
						inputId: 'asset_level',
					    id : 'assetLevel',
					    value : 2,
						data: ${alList}
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
						margin : '20 20 20 20',
						labelWidth: 100,
				        width : 230,
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
						    				obj.setPosition([pctLeft+525,pctTop+70],true);
						    			});
						    			PAction.pctWin.show();
						    		}
						    	}
				        	}
				        },
				        value : ""
				},{
						xtype: 'button',
						margin : '20 20 20 20',
						iconCls : 'ss_sprite ss_page_white_excel',
			            text : PRes["ExcelBtn"],
			            width : 60,
			            handler: function() {
			            	//if (!PAction.validations()) return;
			            	var jparams ={
			    				projectId : CUtils.getSSValue('petrolStation'),
			    				level : CUtils.getSValue('assetLevel'),
			    				assetCategory: Ext.getCmp('assetsCategory_filter').getValue()
			    			};
			    	    	var viewArgs = {
			       				fileName : 'ReportByProject',
			       				fileType : 'xlsx',
			       				pageSize : 'ISO_A4',
			       				url : "/app/pgm/report/downLoad",
			       				jasperParams : jparams
			    	    	};
			    	    	CUtils.downLoadReportFile(viewArgs);
			            }
					},{
						xtype: 'button',
						margin : '20 20 20 20',
						iconCls : 'ss_sprite ss_page_white_acrobat',
			            text : PRes["PdfBtn"],
			            width : 60,
			            handler: function() {
			            	var jparams ={
			            			projectId : CUtils.getSSValue('petrolStation'),
				    				level : CUtils.getSValue('assetLevel'),
				    				assetCategory: Ext.getCmp('assetsCategory_filter').getValue()
			    			};
			    	    	var viewArgs = {
			       				fileName : 'ReportByProject',
			       				fileType : 'pdf',
			       				pageSize : 'ISO_A4',
			       				jasperParams : jparams
			    	    	};
			    	    	CUtils.downLoadReportFile(viewArgs);
			            }
					}]
				}]
			},
			actionBar : actionBar
		});
		
		Ext.define('EMP.project.ProjectReportAction', {
			extend : 'ERP.FormAction'
		});
		
		PAction = new EMP.project.ProjectReportAction({
			validations : function () {
				var msgarray = [];
				VUtils.removeFieldErrorCls('project_input');
				var project = CUtils.getSSValue('petrolStation');
				if (Strings.isEmpty(project)) {
					msgarray.push({fieldname:"project_input", message: PRes["VProject"], arg:null});
				}
				if (msgarray.length > 0) {
					VUtils.processValidateMessages(msgarray);
					return false;
				}
				return true;
			}
		});
	}

	function page_AfterLoad() {
	}
	</script>
</head>
	
<body>
	<form id="form1" action="" method="post" >
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
	</form>
	<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>