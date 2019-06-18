<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.YearBudgets')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/yearBudgets/_includes/_yearBudgetsGrid.jsp" />
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
							fieldLabel: "${f:getText('Com.Years')}",
							xtype : 'textfield',
							labelWidth: 100,
						    width : 200,
						    name: 'sf_EQ_years',
						    id : 'sf_EQ_years',
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
		    			years : Ext.getCmp('sf_EQ_years').getValue()
	    			};
	    	    	var viewArgs = {
	       				fileName : 'YearBudgets',
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
			Ext.define('ERP.YearBudgetsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showYearBudget"
			});
			PAction = new ERP.YearBudgetsAction();
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