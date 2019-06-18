<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Employee')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:160px}
		#divGeneralLeft {float: left;width: 33%}
		#divGeneralCenter {float: left;width: 33%}
		#divGeneralRight {float: left;width: 33%}
	</style>
	<jsp:directive.include file="/WEB-INF/views/hr/employee/_includes/_employeePositionGrid.jsp" />
	<script type="text/javascript" src="/js/erp/hr/employee/EmployeeAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/hr/employee/_includes/_employee.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/hr/employee/form/${entityId}/" method="post" modelAttribute="${bindModel}">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<input type="hidden" name="employeeActivityJsons" id="employeeActivityJsons" value=""/>
		<input type="hidden" name="deleteLines" id="deleteLines" value=""/>
		<input type="hidden" name="positionJsons" id="positionJsons" value=""/>
		<input type="hidden" name="positionDeleteLines" id="positionDeleteLines" value=""/>
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.code)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/hr/employee/_includes/_employeeGeneral.jsp" />
		</div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>