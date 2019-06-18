<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/project/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'Project',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'code', header : "${f:getText('Com.ProjectCode')}", width : 100, editable : false},
           { id : 'name', header : "${f:getText('Com.ProjectName')}", width : 100, editable : false},
           { id : 'pct', header : "${f:getText('Com.City')}", width : 100, editable : false}
        ]
};
</script>