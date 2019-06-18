<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />

<c:choose>
    <c:when test="${empty entity.id}">
		<c:set var="winTitle">NEW - Department</c:set>
	</c:when>
	<c:otherwise>
	    <c:set var="winTitle"> Departmenmt - ${entity.code}</c:set>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${entity.newEntity}"><c:set var="method" value="post"/><c:set var="readonly" value="false"/></c:when>
	<c:otherwise><c:set var="method" value="put"/><c:set var="readonly" value="true"/></c:otherwise>
</c:choose>

	<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
		.divLeft{margin-left:3px;float:left; width:48%;}
		.divRight{margin-left:3px;float:left; width:48%;}
		
	</style>
	<jsp:directive.include file="/WEB-INF/views/hr/department/_includes/_employeeGrid.jsp" />
    <script type="text/javascript" src="/js/erp/hr/department/DepartmentAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/hr/department/_includes/_department.js" />
	</script>
</head>
<body>

<c:set var="bindModel" value="entity"/>

<form:form id="form1" action="/app/${APP_NAME}/department/form/${entityId}/" method="${method}" modelAttribute="${bindModel}">
<div id="divWinHeader">${winTitle}</div>
	<form:hidden path="version" id="version" />
	<input type="hidden" name="employeeJsons" id="employeeJsons" />
	<input type="hidden" name="supervisorJsons" id="supervisorJsons" />
	<input type="hidden" name="deleteEmployee" id="deleteEmployee" />
	<input type="hidden" name="deleteSupervisor" id="deleteSupervisor" />
	
	<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
	
	<div id="divGeneral">
		<jsp:directive.include file="/WEB-INF/views/hr/department/_includes/_departmentGeneral.jsp" />
	</div>
	
	<div id="divEmployee">
		<jsp:directive.include file="/WEB-INF/views/hr/department/_includes/_departmentEmployee.jsp" />
	</div>
	
</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>