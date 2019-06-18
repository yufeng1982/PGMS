<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.PetrolStations')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:100px}
		.fieldRowTextField {width:130px}
		.divGeneral1 {float: left;width: 25%}
		.divGeneral11{float: left;width: 100%}
		.divGeneral11_2 {float: left;width: 50%}
		#pct{width: 70%;}
		#address{width: 70%;}
		.divGeneral2 {float: left;width: 25%}
		
		#divTextAray1{float: left;width: 100%;height: 44px;}
		.fieldRowTextareaField{width: 60%;height: 40px;}
		#divTextAray2{float: left;width: 100%;height: 44px;}
		#divTextAray2 .fieldRowTextareaField{width: 85%;height: 40px;}
		
		#divGeneralLeftFile {float: left;width: 20%}
		#divGeneralCenterFile {float: left;width: 99%}
		#divGeneralRigthFile {float: left;width: 22%}
		.divAll{float: left;width: 99%;}
		.file{float: left;width: 35%;}
		.downLoad{float: left;height:22px;width: 30%;  line-height:22px; margin-top: 2px;}
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 422px;margin-top: 2px;}
		
		._divGeneral1 {float: left;width: 20%;padding: 4px 0px;}
		._divGeneral2 {float: left;width: 18%}
		.divGeneral2_1{float: left;width: 50%}
		._divGeneral3 {float: left;width: 18%}
		._divGeneral4 {float: left;width: 18%}
		._divGeneral5 {float: left;width: 18%}
		._divGeneral6 {float: left;width: 50%}
		._divGeneral7 {float: left;width: 25%}
		._divGeneral8 {float: left;width: 25%}
		
		
		#divELInfo{float: left;width: 100%;}
		#divELInfo1{float: left;width: 25%;}
		#divELInfo2{float: left;width: 25%;}
		#divELInfo3{float: left;width: 50%;}
		#equipmentSpec{width: 70%;}
		#oamyg{float: left;width: 100%;text-align: center;}
		#oamyj{float: left;width: 100%;text-align: center;}
		#othequipment{float: left;width: 100%;text-align: center;}
		
		.divGeneral1001{float: left;width: 50%;}
		.divGeneral1002{float: left;width: 25%;}
		#attachmentDiv{float: left;width: 99%;}
		#label_jzkzxtydhxjl{width: 170px;}
		
		#label_ynYzhgzs{width: 422px;}
		#label_tdxz{width: 422px;}
		#label_ynJchgz{width: 422px;}
		#label_ynJyxkz{width: 422px;}
		#label_jzkzxtydhxjl{width: 422px;}
		#label_xkzzl{width: 422px;}
		#label_ynTdz{width: 150px;}
		#label_zsbh{width: 150px;}
		#label_hgzbh{width: 150px;}
		#label_xkzbh{width: 150px;}
		#label_xkfw{width: 150px;}
		#label_dlkzx{width: 150px;}
		
		#label_ynYdhx{width: 120px;}
		#label_releaseDate{width: 120px;}
		#label_yxqFrom{width: 120px;}
		#label_yxqTo{width: 120px;}
		
		#xkfw{width: 66%;}
		#dlkzx{width: 66%;}
		
		#oilOtherDes{float: left;width: 99%;}
		#oilOtherSm{float: left;width: 99%;margin-top: 5px;}
		.oilOtherSm1{float: left;width: 100%;}
		#divOilleft_title{float: left;width: 57%;}
		#divOilright_title{float: left;width: 43%;}
		#divEacleft_title{float: left;width: 51%;}
		#divEacright_title{float: left;width: 49%;}
		#qiyouP{float: left;width: 62px;}
		#caiyouP{float: left;width: 62px;}
		#divGeneral33_1{float: left;width: 170px;}
		#cqP{float: left;margin-top: 6px;margin-left: -2px;}
		#divGeneral33_3{float: left;width: 44%;}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectPetrolStationsLinesGrid.jsp" />
	<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectOilCanLinesGrid.jsp" />
	<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectOilMachineLinesGrid.jsp" />
	<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectOtherEquipmentLinesGrid.jsp" />
	<script type="text/javascript" src="/static/_province_city/area.js"></script>
	<script type="text/javascript" src="/static/_province_city/location.js"></script>
	<script type="text/javascript" src="/static/jquery/1.11.1/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/js/erp/project/project/ProjectAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_project.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/project/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<input type="hidden" name="oilCanLineJsons" id="oilCanLineJsons" value=""/>
		<input type="hidden" name="oilMachineLineJsons" id="oilMachineLineJsons" value=""/>
		<input type="hidden" name="otherEquipmentJsons" id="otherEquipmentJsons" value=""/>
		<input type="hidden" name="petrolStationsJsons" id="petrolStationsJsons" value=""/>
		<input type="hidden" name="yjts" id="yjts" value="${entity.yjts}"/>
		<input type="hidden" name="yggs" id="yggs" value="${entity.yggs}"/>
		<div id="divWinHeader" class="divWinHeader">${winTitle} - ${f:htmlEscape(entity.code)}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectGeneral.jsp" />
		</div>
		<div id="divOilSummary">
			<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectOilSummaryGeneral.jsp" />
		</div>
		<div id="divEquipmentSummary">
			<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectEquipmentSummaryGeneral.jsp" />
		</div>
		<div id="divElectricAndCommunication">
			<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projecElectricAndCommunicationGeneral.jsp" />
		</div>
		<div id="divAttachment">
			<jsp:directive.include file="/WEB-INF/views/project/project/_includes/_projectAttachmentGeneral.jsp" />
		</div>
		<div id="pctDiv">
			<select id="loc_province" style="width:80px;height:22px;margin-top:8px;margin-left:10px;"></select>
			<select id="loc_city" style="width:80px;height:22px;margin-top:8px;"></select>
			<select id="loc_town" style="width:80px;height:22px;margin-top:8px;"></select>
		</div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>