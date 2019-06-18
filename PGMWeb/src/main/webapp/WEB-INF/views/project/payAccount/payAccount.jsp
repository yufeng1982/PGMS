<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.PayAccount.DetailInfo')}"/>

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
		.downLoad{float: left;height:5px;width: 50%; }
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 140px;}
		#divGeneralLeftOnePay{float: left;width: 33%}
		#divGeneralCenterOnePay{float: left;width: 33%}
		#divGeneralLeftTwoPay{float: left;width: 33%}
		#divGeneralCenterTwoPay{float: left;width: 33%}
		#divGeneralLeftThreePay{float: left;width: 33%}
		#divGeneralCenterThreePay{float: left;width: 33%}
		#divGeneralLeftFourPay{float: left;width: 33%}
		#divGeneralCenterFourPay{float: left;width: 33%}
	</style>
	<script type="text/javascript" src="/js/erp/project/payAccount/PayAccountAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/payAccount/_includes/_payAccount.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/payAccount/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/payAccount/_includes/_payAcountGeneral.jsp" />
		</div>
		<div id="divGeneralOne">
			<jsp:directive.include file="/WEB-INF/views/project/payAccount/_includes/_payAcountGeneralOne.jsp" />
		</div>
		<div id="divGeneralTwo">
			<jsp:directive.include file="/WEB-INF/views/project/payAccount/_includes/_payAcountGeneralTwo.jsp" />
		</div>
		<div id="divGeneralThree">
			<jsp:directive.include file="/WEB-INF/views/project/payAccount/_includes/_payAcountGeneralThree.jsp" />
		</div>
		<div id="divGeneralFour">
			<jsp:directive.include file="/WEB-INF/views/project/payAccount/_includes/_payAcountGeneralFour.jsp" />
		</div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>