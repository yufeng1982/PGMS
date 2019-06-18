<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Contract.PayItem')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<script type="text/javascript" src="/js/erp/lib/ui/field/ERPSearchingSelect.js"></script>
	<jsp:directive.include file="/WEB-INF/views/project/payItem/_includes/_payItemsGrid.jsp" />
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
			        //onchange : 'groupTypeOnchange',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.Contranct.Name')}",
					xtype : "erpsearchingselect",
					config : ${f:sess('Contract')},
					labelWidth: 100,
			        width : 250,
			        id :  'contract_filter',
			        name: 'sf_EQ_contract',
			        gridInitParameters : function (){
			        	var parm = {sf_EQ_contractType : 'Main'};
			        	if (!Strings.isEmpty(CUtils.getSSValue('project_filter'))){
			        		parm.sf_EQ_petrolStation = CUtils.getSSValue('project_filter');
			        	}
			        	return parm;
			        },
			        //onchange : 'groupTypeOnchange',
			        value : ""
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
			Ext.define('ERP.ProjectsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showPayItem"
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