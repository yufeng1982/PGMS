<jsp:directive.include file="/WEB-INF/views/utils/_taglibUtil.jsp"/>
<script type="text/javascript">
G_CONFIG = {
		url : '/app/pgm/photo/item/list/json',
		root : 'data',
		modelName : 'Item',
		idProperty : 'id',
		isEditable : false, // default value is false
		isPaging : false, // default value is true
		isInfinite : true, // deault value is false
		columns : [{id : 'id', hidden: true, header : "id"},
           {id : 'sequence', header : "${f:getText('Com.Code')}", width : 100},
           {id : 'name', header : "${f:getText('Com.Name')}", width : 150},
           {id : 'description', header : "${f:getText('Com.Description')}", width : 150},
           {id : 'price', header : "${f:getText('Com.Price')}", width : 80, fieldType : 'number'}
		]
};
</script>