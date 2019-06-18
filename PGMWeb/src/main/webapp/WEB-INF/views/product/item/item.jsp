<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.Item')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px;}
		.fieldRowTextField {width:160px;}
		#divGeneralLeft {float: left;width: 33%;}
		#divGeneralCenter {float: left;width: 33%;}
		#divGeneralRight {float: left;width: 33%;}
	</style>
	<script type="text/javascript" src="/js/erp/product/item/ItemAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/product/item/_includes/_item.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/photo/item/form/${entityId}/" method="post" modelAttribute="${bindModel}">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.sequence)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/product/item/_includes/_itemGeneral.jsp" />
		</div>
	</form:form>
	<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>