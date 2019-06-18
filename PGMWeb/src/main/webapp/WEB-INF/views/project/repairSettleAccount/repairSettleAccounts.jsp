<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.RepairSettleAccount')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<script type="text/javascript" src="/js/erp/lib/ui/field/ERPSearchingSelect.js"></script>
	<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_repairSettleAccountsGrid.jsp" />
	<script type="text/javascript">
		function page_OnLoad() {
			var searchConfig = {
				layout : 'hbox',
	            defaults: {
		            margin : '3 15 3 15'
		        },
				items: [{
					fieldLabel: "${f:getText('Com.RepairCode')}",
					xtype : 'textfield',
					labelWidth: 100,
			        width : 230,
			        name: 'sf_EQ_repairOrder',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.Contranct.Project')}",
					xtype : "erpsearchingselect",
					config : ${f:sess('OilPetrolStation')},
					gridInitParameters : function(){
						return {sf_IN_id : '${ids}'};
					},
					labelWidth: 100,
			        width : 250,
			        id :  'project_filter',
			        name: 'sf_EQ_petrolStation',
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
			Ext.define('ERP.RepairSettleAccountsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showRepairSettleAccount"
			});
			PAction = new ERP.RepairSettleAccountsAction({});
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