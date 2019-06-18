<jsp:directive.include file="/WEB-INF/views/utils/_taglibUtil.jsp"/>
<script type="text/javascript">
G_CONFIG = {
	url : '/app/pgm/project/city/json',
	modelName : 'City',
	columns : [
	     {id : 'id', header : "id", hidden: true},
	     {id : 'code', header : "${f:getText('Com.Code')}", width : 150},
	     {id : 'name', header : "${f:getText('Com.Name')}", width : 150}
	],
	autoLoad : true
};
</script>