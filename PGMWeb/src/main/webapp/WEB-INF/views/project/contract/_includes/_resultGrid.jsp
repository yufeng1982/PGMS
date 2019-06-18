<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/approveResult/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'approveHistory',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		autoLoad : true,
		columns : [
		   { id : 'id', header:"id", width:120, hidden:true },
		   { id : 'entityId', header:"entityId", width:100, hidden:true },
		   { id : 'taskId', header:"taskId", width:100, hidden:true },
		   { id : 'taskName', header : "${f:getText('Com.TaskName')}", width : 120},
           { id : 'approveResult', header : "${f:getText('Com.Result')}", width : 80},
           { id : 'comleteTime', header : "${f:getText('Com.OperationDate')}", width : 150 },
           { id : 'opinions', header : "${f:getText('Com.ApproveOpinion')}", width : 280}
        ]
};
</script>