<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.AssetsCategory')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:160px}
		#address{width: 595px;}
		#divGeneralLeft {float: left;width: 99%}
		#divGeneralLeftFile {float: left;width: 33%}
		#divGeneralCenter {float: left;width: 33%}
		#divGeneralRight {float: left;width: 33%}
		#divGeneralCenterFile {float: left;width: 44%}
		#divGeneralRigthFile {float: left;width: 22%}
		#divAll{float: left;width: 99%;}
		.file{float: left;width: 44%;}
		.downLoad{float: left;height:22px;width: 22%;  line-height:22px; margin-top: 2px;}
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 140px;margin-top: 2px;}
	</style>
	<script type="text/javascript" src="/js/erp/project/assetsCategory/AssetsCategoryAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/assetsCategory/_includes/_assetsCategory.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/assetsCategory/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.name)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/assetsCategory/_includes/_assetsCategoryGeneral.jsp" />
		</div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>