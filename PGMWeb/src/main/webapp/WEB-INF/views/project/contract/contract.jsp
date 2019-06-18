<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Contract')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:120px}
		.fieldRowTextField {width:160px}
		#address{width: 595px;}
		#divGeneralLeft {float: left;width: 25%}
		#divGeneralLeftFile {float: left;width: 33%}
		#divGeneralCenter1 {float: left;width: 25%}
		#divGeneralCenter2 {float: left;width: 25%}
		#divGeneralRight {float: left;width: 25%}
		#divGeneralRight2 {float: left;width: 25%}
		#divGeneralRight3 {float: left;width: 25%}
		#divGeneralCenterFile {float: left;width: 44%}
		#divGeneralRigthFile {float: left;width: 22%}
		#divAll{float: left;width: 99%;}
		.file{float: left;width: 35%;}
		.downLoad{float: left;height:22px;width: 50%;  line-height:22px; margin-top: 2px;}
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 120px;margin-top: 2px;}
		#divTextAray1{float: left;width: 75%;height: 44px;}
		#divTextAray2{float: left;width: 75%;height: 44px;margin-top: -7px;}
		.fieldRowTextareaField{width: 83.5%;height: 35px;}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/contract/_includes/_resultGrid.jsp" />
	<script type="text/javascript" src="/js/erp/project/contract/ContractAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/contract/_includes/_contract.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/contract/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<input type="hidden" id="taskName" name="taskName" value="${taskName}"/> 
		<input type="hidden" id="opinions" name="opinions" value=""/> 
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.code)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/contract/_includes/_contractGeneral.jsp" />
		</div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>