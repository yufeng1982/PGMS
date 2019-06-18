<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Project')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<script type="text/javascript" src="/js/erp/lib/ui/field/ERPSearchingSelect.js"></script>
	<jsp:directive.include file="/WEB-INF/views/project/payAccount/_includes/_payAccountsGrid.jsp" />
	<script type="text/javascript">
		function page_OnLoad() {
			var searchConfig = {
				layout : 'hbox',
				items: [{
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
				        value : ""
					},{
						fieldLabel: "${f:getText('Com.Contranct.Cooperation')}",
						labelWidth: 100,
						width : 250,
						xtype : 'erpsearchingselect',
						config : ${f:sess('Cooperation')},
						id : 'cooperation_filter',
						name : 'sf_EQ_cooperation'
					}]   
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
				},{
					xtype:'fieldset',
					defaultType: 'textfield',
					defaults: {margins:'2 2 2 2'},
					border : 0,
				    layout: 'vbox',
					items :[{
						fieldLabel: "${f:getText('Com.Contranct.AssetsCategory')}",
						xtype : 'SelectField',
						labelWidth: 100,
				        width : 250,
				        id :  'assetsCategory_filter',
				        name: 'sf_EQ_assetsCategory',
				        data: ${acList},
				        value : ""
					}]   
				}]       
			};
			var actionBarItems = [];
			actionBarItems[1] = null;
			var actionBar = new ERP.ListActionBar(actionBarItems);
			PApp = new ERP.ListApplication({
				actionBar : actionBar,
				searchConfig : searchConfig
			});
			Ext.define('ERP.PayAccountsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showPayAccount"
			});
			PAction = new ERP.PayAccountsAction();
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