<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.ProjectApproveBudget')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/projectApproveBudget/_includes/_projectApproveBudgetsGrid.jsp" />
	<script type="text/javascript">
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
							fieldLabel: "${f:getText('Com.PAB.OilLevel')}",
							xtype : 'textfield',
							labelWidth: 100,
						    width : 200,
						    name: 'sf_EQ_oilLevel',
						    id : 'sf_EQ_oilLevel',
						    value : ""
						},{
							fieldLabel: "${f:getText('Com.PAB.ApproveLevel')}",
							xtype : 'textfield',
							labelWidth: 100,
						    width : 200,
						    name: 'sf_EQ_approveLevel',
						    id : 'sf_EQ_approveLevel',
						    value : ""
						},{
							fieldLabel: "${f:getText('Com.PAB.ApproveDate')}",
							xtype : 'datefield',
							labelSeparator : '',
							labelWidth: 100,
						    width : 200,
						    name: 'sf_GTE_approveDate',
						    id : 'sf_GTE_approveDate',
						    value : ""
						},{
							fieldLabel: "${f:getText('Com.Recline')}",
							xtype : 'datefield',
							labelSeparator : '',
							labelWidth: 10,
						    width : 110,
						    name: 'sf_LTE_approveDate',
						    id : 'sf_LTE_approveDate',
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
		    			oilLevel : Ext.getCmp('sf_EQ_oilLevel').getValue(),
		    			approveDateFrom : Ext.getCmp('sf_GTE_approveDate').getRawValue(),
			    		approveDateTo : Ext.getCmp('sf_LTE_approveDate').getRawValue(),
			    		approveLevel : Ext.getCmp('sf_EQ_approveLevel').getValue()
			    		
	    			};
	    	    	var viewArgs = {
	       				fileName : 'ProjectApproveBudget',
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
	                items: [btnCreate, btnExcel]
	            }
			});
			Ext.define('ERP.ProjectApproveBudgetsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showProjectApproveBudget"
			});
			PAction = new ERP.ProjectApproveBudgetsAction();
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