<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.ContractSettlement')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<script type="text/javascript" src="/js/erp/lib/ui/field/ERPSearchingSelect.js"></script>
	<jsp:directive.include file="/WEB-INF/views/project/settleAccounts/_includes/_settleAccountsGrid.jsp" />
	<script type="text/javascript">
		function page_OnLoad() {
			var searchConfig = {
				layout : 'hbox',
	            defaults: {
		            margin : '3 15 3 15'
		        },
				items: [{
					fieldLabel: "${f:getText('Com.Contranct.Project')}",
					xtype : "erpsearchingselect",
					config : ${f:sess('PetrolStation')},
					labelWidth: 100,
			        width : 250,
			        id :  'project_filter',
			        name: 'sf_EQ_petrolStation',
			        value : ""
				},{
					xtype:'fieldset',
					defaultType: 'textfield',
					defaults: {margins:'2 2 2 2'},
					border : 0,
				    layout: 'vbox',
					items :[{
						fieldLabel: "${f:getText('Com.Contranct.Code')}",
						xtype : 'textfield',
						labelWidth: 100,
				        width : 250,
				        id : 'code_filter',
				        name: 'sf_LIKE_contractCode',
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
			Ext.define('ERP.SettleAccountsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showSettleAccounts"
			});
			PAction = new ERP.SettleAccountsAction();
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