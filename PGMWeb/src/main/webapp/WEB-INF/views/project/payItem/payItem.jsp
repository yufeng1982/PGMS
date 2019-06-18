<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Contract.PayItem')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:160px}
		#address{width: 595px;}
		#divGeneralLeft {float: left;width: 33%}
		#divGeneralLeftFile {float: left;width: 33%}
		#divGeneralCenter {float: left;width: 33%}
		#divGeneralRight {float: left;width: 33%}
		#divGeneralCenterFile {float: left;width: 44%}
		#divGeneralRigthFile {float: left;width: 22%}
		#divAll{float: left;width: 99%;}
		.file{float: left;width: 35%;}
		.downLoad{float: left;height:22px;width: 50%;  line-height:22px; margin-top: 2px;}
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 140px;margin-top: 2px;}
	</style>
	<script type="text/javascript" src="/js/erp/project/payItem/PayItemAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/payItem/_includes/_payItem.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/payItem/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<input type="hidden" id="onePay" value="${entity.contract.onePercent}"/>
		<input type="hidden" id="twoPay" value="${entity.contract.twoPercent}"/>
		<input type="hidden" id="threePay" value="${entity.contract.threePercent}"/>
		<input type="hidden" id="fourPay" value="${entity.contract.fourPercent}"/>
		<input type="hidden" id="paidAmount" value="${entity.contract.payAmount}"/>
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.contract.code)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/payItem/_includes/_payItemGeneral.jsp" />
		</div>
		<div id="divApproveGeneral">
		<jsp:directive.include file="/WEB-INF/views/project/payItem/_includes/_payItemApproveGeneral.jsp" />
		</div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>