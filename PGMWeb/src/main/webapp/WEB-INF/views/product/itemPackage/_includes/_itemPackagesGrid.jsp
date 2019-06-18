<jsp:directive.include file="/WEB-INF/views/utils/_taglibUtil.jsp"/>
<script type="text/javascript">
G_CONFIG = {
		url : '/app/pgm/photo/itemPackage/list/json',
		root : 'data',
		modelName : 'Item',
		idProperty : 'id',
		isEditable : false, // default value is false
		isPaging : false, // default value is true
		isInfinite : true, // deault value is false
		columns : [{id : 'id', hidden: true, header : "id"},
           {id : 'sequence', header : "${f:getText('Com.Code')}", width : 100},
           {id : 'name', header : "${f:getText('Com.Name')}", width : 150},
           {id : 'amount', header : "${f:getText('Com.Amount')}", width : 150},
           {id : 'frontMoney', header : "${f:getText('Com.FrontMoney')}", width : 150},
           {id : 'description', header : "${f:getText('Com.Description')}", width : 150}
		]
};
</script>