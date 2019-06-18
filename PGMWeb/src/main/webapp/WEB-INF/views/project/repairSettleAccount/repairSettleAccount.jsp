<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.RepairSettleAccount')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:120px}
		.fieldRowTextField {width:160px}
		#divGeneraDiv {float: left;width: 25%}
		#divAll{float: left;width: 99%;height: 44px;}
		.fieldRowTextareaField{width: 88.5%;height: 55px;}
		.file{float: left;width: 35%;}
		.downLoad{float: left;height:22px;line-height:22px; margin-top: 2px; width: 99%;}
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 120px;margin-top: 2px;}
		#divGeneralAllLeft{float: left;width: 25%;}
		#divGeneralAllCenter{float: left;width: 50%;}
		#divGeneralAllRight{float: left;width: 25%;}
		#thisBudgetUpper {width:75%}
		#settleAccountUpper {width:75%}
		.response{float: left;width: 45%;margin-top: 20px;}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_repairSettleAccountApprovesGrid.jsp" />
	<script type="text/javascript" src="/js/erp/project/repairSettleAccount/RepairSettleAccountAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_repairSettleAccount.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/repairSettleAccount/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<input type="hidden" id="taskName" name="taskName" value="${taskName}"/> 
		<input type="hidden" id='ids' value='${ids}'>
		<input type="hidden" id='user' value='${user}'>
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.repairOrder.code)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_repairSettleAccountGeneral.jsp" />
		</div>
		<c:if test="${entityId ne 'NEW'}">
			<div id="divWxgcsyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_wxgcs_opions.jsp" />
			</div>
			<div id="divHseyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_hse_opions.jsp" />
			</div>
			<div id="divBmjlyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_bmjl_opions.jsp" />
			</div>
			<div id="divFgfzyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_fgfz_opions.jsp" />
			</div>
			<div id="divZjlyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairSettleAccount/_includes/_zjl_opions.jsp" />
			</div>
		</c:if>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>