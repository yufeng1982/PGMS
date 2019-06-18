<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Cooperation')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:160px}
		#address{width: 68.5%;}
		#divGeneralLeft {float: left;width: 33%}
		#divGeneralCenter {float: left;width: 33%}
		#divGeneralRight {float: left;width: 33%}
		.divAll{float: left;width: 100%;}
		.file{float: left;width: 35%;}
		.downLoad{float: left;height:22px;width: 50%;  line-height:22px; margin-top: 2px;}
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 140px;margin-top: 2px;}
		.panel{float: left;width: 100%;}
		.divaddress1{float: left;width: 33%;}
		.divaddress2{float: left;width: 66%;}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/cooperation/_includes/_cooperationAccountsGrid.jsp" />
	<script type="text/javascript" src="/js/erp/project/cooperation/CooperationAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/cooperation/_includes/_cooperation.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/cooperation/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<input type="hidden" name="cooperationAccountJsons" id="cooperationAccountJsons" value=""/>
		<input type="hidden" name="cooperationAccountDeleteLines" id="cooperationAccountDeleteLines" value=""/>
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.name)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/cooperation/_includes/_cooperationGeneral.jsp" />
		</div>
		<div id = "fileUpload"></div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>