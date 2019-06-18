<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.Clothes')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px;}
		.fieldRowTextField {width:160px;}
		#divGeneralLeft {float: left;width: 33%;}
		#divGeneralCenter {float: left;width: 33%;}
		#divGeneralRight {float: left;width: 33%;}
	</style>
	<script type="text/javascript" src="/js/erp/product/clothes/ClothesAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/product/clothes/_includes/_clothes.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/photo/clothes/form/${entityId}/" method="post" modelAttribute="${bindModel}">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.sequence)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/product/clothes/_includes/_clothesGeneral.jsp" />
		</div>
	</form:form>
	<form:form id="form2" action="/app/${APP_NAME}/photo/clothes/form/${entityId}/uploadImage" method="post" enctype="multipart/form-data">
		<div id="uploadImages">
		</div>
	</form:form>
	<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>