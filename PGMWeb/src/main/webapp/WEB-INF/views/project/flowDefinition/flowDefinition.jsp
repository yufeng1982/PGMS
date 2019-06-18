<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.FlowDefinition')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:60px}
		.fieldRowTextField {width:160px}
		#address{width: 595px;}
		#divGeneralLeft {float: left;width: 33%}
		#divGeneralLeftFile {float: left;width: 33%}
		#divGeneralCenter {float: left;width: 66%}
		#divGeneralRight {float: left;width: 33%}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/flowDefinition/_includes/_flowDefinitionLinesGrid.jsp" />
	<script type="text/javascript" src="/js/erp/project/flowDefinition/FlowDefinitionAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/flowDefinition/_includes/_flowDefinition.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/flowDefinition/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<input type="hidden" name="flowDefinitionJsons" id="flowDefinitionJsons" value=""/>
		<input type="hidden" name="flowDefinitionDeleteLines" id="flowDefinitionDeleteLines" value=""/>
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.code)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/flowDefinition/_includes/_flowDefinitionGeneral.jsp" />
		</div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>