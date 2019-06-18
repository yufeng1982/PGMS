<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.Customer')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px;}
		.fieldRowTextField {width:160px;}
		#sinaWeiBoAdr{width: 400px;}
		#divGeneralLeft {float: left;width: 33%;}
		#divGeneralCenter {float: left;width: 33%;}
		#divGeneralRight {float: left;width: 33%;}
		#divAll{float: left;width: 99%;}
	</style>
	<script type="text/javascript" src="/js/erp/crm/customer/CustomerAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/crm/customer/_includes/_customer.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/core/crm/customer/form/${entityId}/" method="post" modelAttribute="${bindModel}">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.sequence)} </div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/crm/customer/_includes/_customerGeneral.jsp" />
		</div>
	</form:form>
	<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>