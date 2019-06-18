<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.SetsOfLines')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px;}
		.fieldRowTextField {width:160px;}
		#divGeneralLeft {float: left;width: 33%;}
		#divGeneralCenter {float: left;width: 33%;}
		#divGeneralRight {float: left;width: 33%;}
	</style>
	<jsp:directive.include file="/WEB-INF/views/product/itemPackage/_includes/_itemPackageLineGrid.jsp" />
	<jsp:directive.include file="/WEB-INF/views/product/itemPackage/_includes/_itemPackageClothesGrid.jsp" />
	<jsp:directive.include file="/WEB-INF/views/product/itemPackage/_includes/_itemPackageImagesGrid.jsp" />
	<script type="text/javascript" src="/js/erp/product/itemPackage/ItemPackageAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/product/itemPackage/_includes/_itemPackage.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/photo/itemPackage/form/${entityId}/" method="post" modelAttribute="${bindModel}">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<input type="hidden" name="itemPackageLineJsons" id="itemPackageLineJsons" value=""/>
		<input type="hidden" name="itemPackageDeleteLines" id="itemPackageDeleteLines" value=""/>
		<input type="hidden" name="itemPackageClothesJsons" id="itemPackageClothesJsons" value=""/>
		<input type="hidden" name="itemPackageDeleteClothesLines" id="itemPackageDeleteClothesLines" value=""/>
		<input type="hidden" name="itemPackageImagesJsons" id="itemPackageImagesJsons" value=""/>
		<input type="hidden" name="itemPackageDeleteImagesLines" id="itemPackageDeleteImagesLines" value=""/>
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.sequence)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/product/itemPackage/_includes/_itemPackageGeneral.jsp" />
		</div>
		<div id = "fileUpload"></div>
		<div id = "divSpec"></div>
		<div id = "divSpecial"></div>
	</form:form>
	<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>