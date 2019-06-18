<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Department')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/hr/department/_includes/_departmentsGrid.jsp" />
	<script type="text/javascript">
	
		function page_OnLoad() {
			DEFAULT_PAGE_SIZE = 300;
			var searchConfig = {
				layout : 'hbox',
	             defaults: {
		             margin : '3 15 3 15'
		         },
				items: [{
					fieldLabel: "${f:getText('Com.Name')}",
					xtype : 'textfield',
					labelWidth: 70,
			        width : 200,
			        name: 'sf_LIKE_name',
			        value : ""
				}]
			};
			PApp = new ERP.ListApplication({
				searchConfig : searchConfig
			});
			PAction = new ERP.ListAction({
				launcherFuncName : "showDepartment"
			});
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