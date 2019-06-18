<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.PetrolStations')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:160px}
		#divGeneralLeft {float: left;width: 33%}
		#divGeneralCenter {float: left;width: 33%}
		#divGeneralRight {float: left;width: 33%}
		.file{float: left;width: 20%;}
		.downLoad{float: left;height:22px;width: 50%;  line-height:22px; margin-top: 2px;}
		.downLoad span a {text-decoration: none;}
		.bl{float: left;height:22px;width: 140px;}
	</style>
	<script type="text/javascript" src="/js/erp/project/petrolStation/PetrolStationAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/petrolStation/_includes/_petrolStation.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="entity"/>       
	<form:form id="form1" action="/app/${APP_NAME}/project/petrolStation/form/${entityId}/" method="post" modelAttribute="${bindModel}" enctype="multipart/form-data">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<form:hidden path="version" id="version" />
		<div id="divWinHeader" class="divWinHeader">${winTitle}</div>
		<div id="divGeneral">
			<jsp:directive.include file="/WEB-INF/views/project/petrolStation/_includes/_petrolStationGeneral.jsp" />
		</div>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>