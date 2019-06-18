<jsp:directive.include file="/WEB-INF/views/utils/_taglibUtil.jsp"/>
<script type="text/javascript">
G_P_CONFIG = {
		url : '/app/${APP_NAME}/security/project/json',
		root : 'data',
		modelName : 'Project',
		idProperty : 'id',
		isEditable : true, // default value is false
		isPaging : true, // default value is true
		autoLoad : true,
		isInfinite : false, // deault value is false
		uniqueColumns : [["project"]],
		columns : [
		     {id : 'id', hidden: true, header : "id"},
	         {id : 'project', hidden: true, header : "${f:getText('Com.Contranct.Project')}"},
	         {id : 'projectText', fieldType : 'browerButton', popupSelectOnClick : 'projectSelect', editable : true, header : "${f:getText('Com.Contranct.Project')}", width : 180, notNull : true}
		]
};
</script>