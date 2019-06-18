<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Project')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectsGrid.jsp" />
	<script type="text/javascript">
		PRes["newEmployee"] = "${f:getText('Com.New')}";
		function page_OnLoad() {
			var searchConfig = {
				layout : 'vbox',
	            defaults: {
		            margin : '1 1 1 1'
		        },
				items: [{
					xtype : 'container',
					layout : 'hbox',
					defaults: {
			            margin : '3 5 3 5'
			        },
					items : [{
							fieldLabel: "${f:getText('Com.City')}",
							xtype : 'textfield',
							labelWidth: 100,
						    width : 230,
						    name: 'sf_LIKE_pct',
						    id : 'sf_LIKE_pct',
						    value : ""
						},{
							fieldLabel: "${f:getText('Com.Project.AddDate')}",
							xtype : 'datefield',
							labelWidth: 100,
						    width : 200,
						    name: 'sf_GTE_addDate',
						    id : 'sf_GTE_addDate',
						    value : ""
						},{
							fieldLabel: "${f:getText('Com.Recline')}",
							xtype : 'datefield',
							labelSeparator : '',
							labelWidth: 10,
						    width : 110,
						    name: 'sf_LTE_addDate',
						    id : 'sf_LTE_addDate',
						    value : ""
						},{
							fieldLabel: "${f:getText('Com.Enabled')}",
							xtype : 'checkbox',
							labelWidth: 100,
						    width : 230,
						    id: 'sf_EQ_enabled',
						    name: 'sf_EQ_enabled',
						    checked  : true
						}
					]
				},{
					xtype : 'container',
					layout : 'hbox',
					defaults: {
			            margin : '3 5 3 5'
			        },
					items : [{
							fieldLabel: "${f:getText('Com.HzWay')}",
							xtype : 'SelectField',
							labelWidth: 100,
						    width : 230,
						    name: 'sf_EQ_hzWay',
						    id : 'sf_EQ_hzWay',
						    data: ${hzWay}
						},{
							fieldLabel: "${f:getText('Com.SalesForecast')}",
							xtype : 'textfield',
							labelWidth: 100,
						    width : 200,
						    name: 'sf_GTE_salesForecast',
						    id : 'sf_GTE_salesForecast',
						    value : ""
						},{
							fieldLabel: "${f:getText('Com.Recline')}",
							xtype : 'textfield',
							labelSeparator : '',
							labelWidth: 10,
						    width : 110,
						    name: 'sf_LTE_salesForecast',
						    id : 'sf_LTE_salesForecast',
						    value : ""
						}
					]
				}]
			};
			
			var btnExcel = new ERP.Button({
				id : 'excelBtn',
			    text: '<strong>' + PRes["ExcelBtn"] + '</strong>',
			    width : 100,
			    iconCls : 'ss_sprite ss_page_white_excel',
			    handler: function() {
			    	var jparams ={
		    			city : Ext.getCmp('sf_LIKE_pct').getValue(),
		    			addDateFrom : Ext.getCmp('sf_GTE_addDate').getRawValue(),
			    		addDateTo : Ext.getCmp('sf_LTE_addDate').getRawValue(),
			    		hzWay : CUtils.getSValue('sf_EQ_hzWay'),
			    		salesForecastFrom : Ext.getCmp('sf_GTE_salesForecast').getValue(),
			    		salesForecastTo : Ext.getCmp('sf_LTE_salesForecast').getValue(),
	    			};
	    	    	var viewArgs = {
	       				fileName : 'Project',
	       				fileType : 'xlsx',
	       				pageSize : 'ISO_A4',
	       				jasperParams : jparams
	    	    	};
	    	    	CUtils.downLoadReportFile(viewArgs);
			    }
			});
			
			var actionBarItems = [];
			actionBarItems[1] = null;
			var actionBar = new ERP.ListActionBar(actionBarItems);
			PApp = new ERP.ListApplication({
				actionBar : actionBar,
				searchConfig : searchConfig,
				_gDockedItem : {
	                xtype: 'toolbar',
	                items: [btnCreate,btnExcel]
	            }
			});
			Ext.define('ERP.ProjectsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showProject",
				getGridSearchPara : function() {
					var params = {};
					if(CUtils.getExtObj('sf_EQ_enabled').checked) {
						params.sf_EQ_enabled = true;
					} else {
						params.sf_EQ_enabled = false;
					}
					return params;
				},
				cleanFields : function() {
					this.callParent(arguments);
					var cmp = Ext.getCmp("sf_EQ_enabled");
					if(cmp){
						cmp.setValue(true);
					}
				}
			});
			PAction = new ERP.ProjectsAction();
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