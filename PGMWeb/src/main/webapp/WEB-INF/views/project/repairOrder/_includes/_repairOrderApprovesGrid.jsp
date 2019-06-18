<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
var G_RPO_CONFIG = {
		url : "/app/pgm/project/repairApprove/list/json4ro",
		root : 'data',
		idProperty : 'id',
		modelName : 'RepairOrderApprove',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'repairOrderId', header : "repairOrderId", width : 45, hidden:true },
		   { id : 'taskId', header:"taskId", width:100, hidden:true },
		   { id : 'taskName', header : "${f:getText('Com.TaskName')}", width : 150},
           { id : 'approveResult', header : "${f:getText('Com.Result')}", width : 80},
           { id : 'comleteTime', header : "${f:getText('Com.OperationDate')}", width : 150 },
           { id : 'responsePeople', header : "${f:getText('Com.ResponseUser')}", width : 80 },
           { id : 'opinions', header : "${f:getText('Com.ApproveOpinion')}", width : 320}
        ]
};
</script>