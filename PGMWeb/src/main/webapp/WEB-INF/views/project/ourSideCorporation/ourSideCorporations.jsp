<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.OurSideCorporation')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/ourSideCorporation/_includes/_ourSideCorporationsGrid.jsp"/>
	<script type="text/javascript" src="/static/_province_city/area.js"></script>
	<script type="text/javascript" src="/static/_province_city/location.js"></script>
	<script type="text/javascript" src="/static/jquery/1.11.1/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/js/erp/project/ourSideCorporation/OurSideCorporationAction.js"></script>
	<script type="text/javascript">
		<jsp:directive.include file="/WEB-INF/views/project/ourSideCorporation/_includes/_ourSideCorporations.js" />
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="pageQueryInfo"/> 
<form:form id="form1" action="/app/${APP_NAME}/project/ourSideCorporation/list/" method="post" modelAttribute="${bindModel}">
	<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
	<input type="hidden" id="modifiedRecords" name="modifiedRecords"/>
	<div id="pctDiv" style="display: none;">
		<select id="loc_province" name="province" style="width:80px;height:22px;margin-top:8px;margin-left:10px;"></select>
		<select id="loc_city" name="city" style="width:80px;height:22px;margin-top:8px;"></select>
		<select id="loc_town" name="town" style="width:80px;height:22px;margin-top:8px;"></select>
	</div>
</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>