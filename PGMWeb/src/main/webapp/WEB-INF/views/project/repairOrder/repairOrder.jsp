<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.RepairOrder')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:120px}
		.fieldRowTextField {width:160px}
		#divGeneraDiv {float: left;width: 25%}
		#divAll{float: left;width: 99%;height: 44px;}
		.fieldRowTextareaField{width: 88.5%;height: 55px;}
		#images{float: left;width: 99%;margin-top: 25px;}
		#image1{float: left;width: 30%}
		#imageName1{float: left;width: 99%}
		#imagePath1{float: left;width: 99%;margin-top: 5px;}
		#imageFile1{float: left;width: 99%}
		#image2{float: left;width: 30%}
		#imageName2{float: left;width: 99%}
		#imagePath2{float: left;width: 99%;margin-top: 5px;}
		#imageFile2{float: left;width: 99%}
		#image3{float: left;width: 30%}
		#imageName3{float: left;width: 99%}
		#imagePath3{float: left;width: 99%;margin-top: 5px;}
		#imageFile3{float: left;width: 99%}
		
		#images_finish{float: left;width: 99%;margin-top: 25px;}
		#image1_finish{float: left;width: 30%}
		#imageName1_finish{float: left;width: 99%}
		#imagePath1_finish{float: left;width: 99%;margin-top: 5px;}
		#imageFile1_finish{float: left;width: 99%}
		#image2_finish{float: left;width: 30%}
		#imageName2_finish{float: left;width: 99%}
		#imagePath2_finish{float: left;width: 99%;margin-top: 5px;}
		#imageFile2_finish{float: left;width: 99%}
		#image3_finish{float: left;width: 30%}
		#imageName3_finish{float: left;width: 99%}
		#imagePath3_finish{float: left;width: 99%;margin-top: 5px;}
		#imageFile3_finish{float: left;width: 99%}
		
		.response{float: left;width: 45%;margin-top: 20px;}
		#divAmounts{float: left;width: 99%;margin-top: 20px;}
		#divHours{float: left;width: 99%;}
		#divHours1{float: left;width: 99%;}
		#divHours2{float: left;width: 99%;}
		#divHours3{float: left;width: 99%;}
		.divAmounts{float: left;width: 25%}
		
		.file{float: left;width: 16%;}
		.downLoad{float: left;height:22px;line-height:22px; margin-top: 2px; width: 20%;}
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 120px;margin-top: 2px;}
		#divAllSolutions{float: left;width: 99%;height: 44px;}
		#divOperationAllow{float: left;width: 100%;;margin-top: 20px;}
		#divOperationAllow1{float: left;width: 20%;}
		#divOperationAllow2{float: left;width: 20%;}
		#divOperationAllow3{float: left;width: 20%;}
		#divOperationAllow4{float: left;width: 20%;}
		#divOperationAllow5{float: left;width: 20%;}
		.divGeneraDivAll{float: left;width: 100%;}
		.divGeneraDivJS{float: left;width: 25%;}
		.divGeneraDivJSR{float: left;width: 74%;}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_repairOrderApprovesGrid.jsp" />
	<script type="text/javascript" src="/js/erp/project/repairOrder/RepairOrderAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_repairOrder.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/repairOrder/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<input type="hidden" id="taskName" name="taskName" value="${taskName}"/> 
		<input type="hidden" id="taskCode" name="taskCode" value="${taskCode}"/>
		<input type="hidden" id="actions"  value=""/>  
		<input type="hidden" id='ids' value='${ids}'>
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.code)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_repairOrderGeneral.jsp" />
		</div>
		<c:if test="${entity.repairType eq 'Small' && entityId ne 'NEW'}">
			<div id="divWxgcsyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_wxgcs_opions_small.jsp" />
			</div>
			<div id="divQyjlwxjsyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_wxjsqyjl_opions_small.jsp" />
			</div>
			<div id="divQyjlwxjsxy">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_wxjsyzjlxy_opions_small.jsp" />
			</div>
			<div id="divYzjlqr">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_yzjl_confirm_small.jsp" />
			</div>
		</c:if>
		<c:if test="${entity.repairType ne 'Small' && entityId ne 'NEW'}">
			
			<div id="divWxgcsyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_wxgcs_opions_general.jsp" />
			</div>
			<div id="divQyjlwxjsyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_wxjsqyjl_opions_general.jsp" />
			</div>
			<div id="divYyjl">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_yyjl_opions_general.jsp" />
			</div>
			<div id="divBmjlyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_bmjl_opions_general.jsp" />
			</div>
			<div id="divHseyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_hse_opions_general.jsp" />
			</div>
			<div id="divFgfzyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_fgfz_opions_general.jsp" />
			</div>
			<div id="divZjlyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_zjl_opions_general.jsp" />
			</div>
			<div id="divCwyj">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_cw_opions_general.jsp" />
			</div>
			<div id="divWxgcspd">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_wxgcspd_opions_general.jsp" />
			</div>
			<div id="divGcsjsxy">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_gcsjsxy_opions_general.jsp" />
			</div>
			<div id="divYzjlqr">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_yzjl_confirm_general.jsp" />
			</div>
			<div id="divWxgcsqrjs">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_settleAccount_general.jsp" />
			</div>
			<div id="divJsBmjl">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_js_bmjl_opions_general.jsp" />
			</div>
			<div id="divJsFgfz">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_js_fgfz_opions_general.jsp" />
			</div>
			<div id="divJsZjl">
				<jsp:directive.include file="/WEB-INF/views/project/repairOrder/_includes/_js_zjl_opions_general.jsp" />
			</div>
		</c:if>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>