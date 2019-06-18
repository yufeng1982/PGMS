<jsp:directive.include file="/WEB-INF/views/utils/_taglibUtil.jsp"/>
<script type="text/javascript">
G_D_CONFIG = {
		url : '/app/${APP_NAME}/security/department/json',
		root : 'data',
		modelName : 'Departments',
		idProperty : 'id',
		isEditable : true, // default value is false
		isPaging : true, // default value is true
		autoLoad : true,
		isInfinite : false, // deault value is false
		columns : [{id : 'id', hidden: true, header : "id"},
		           {id : 'principal', header : "${f:getText('Com.Primary')}", width:48, editable : true, fieldType : 'checkbox', defaultValue : false},
		           {id : 'department', hidden: true, header : "department"},
		           {id : 'departmentText', fieldType : 'browerButton', popupSelectOnClick : 'departmentSelect', editable : true, header : "${f:getText('Com.DepartmentName')}", width : 100, notNull : true},
		           {id : 'description', header : "${f:getText('Com.Description')}", width : 220},
		           {id : 'belongTo', header : "${f:getText('Com.BelongTo')}", width:48, editable : true, fieldType : 'checkbox', defaultValue : false},
		]
};
</script>