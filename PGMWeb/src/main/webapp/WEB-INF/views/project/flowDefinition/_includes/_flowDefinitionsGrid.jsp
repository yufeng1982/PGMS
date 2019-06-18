<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/flowDefinition/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'FlowDefinition',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'code', header : "${f:getText('Com.Code')}", width : 120, editable : false},
           { id : 'name', header : "${f:getText('Com.Name')}", width : 150, editable : false}
        ]
};
</script>