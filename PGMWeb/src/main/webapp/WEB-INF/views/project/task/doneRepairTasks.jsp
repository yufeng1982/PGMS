<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.FinishRepairTask')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/task/_includes/_repairTasksGrid.jsp" />
	<script type="text/javascript">
		function page_OnLoad() {
			
			var actionBarItems = [];
			actionBarItems[ACTION_BAR_INDEX.create] = null;
			var actionBar = new ERP.ListActionBar(actionBarItems);
			PApp = new ERP.ListApplication({
				actionBar : actionBar,
				searchConfig : null
			});
			Ext.define('ERP.ToDoTasksAction' ,{
				extend : 'ERP.ListAction',
				gridItemDBClick : function(view , record , item  , index , e){
					return;
				}
			});
			PAction = new ERP.ToDoTasksAction();
		}
		function showRepair(id) {
			var args = {};
			args.id = id;
			LUtils.showRepairOrder(args);
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