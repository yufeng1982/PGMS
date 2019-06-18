<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/department/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'Department',
		isEditable : false,
		autoLoad : true,
		isPaging : false,
		isInfinite : true,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'code', header : "${f:getText('Com.Code')}", width : 80, editable : false},
           { id : 'name', header : "${f:getText('Com.Name')}", width : 120, editable : false}
        ]
};
</script>