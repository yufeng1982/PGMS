<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
var G_RPSA_CONFIG = {
		url : "/app/pgm/project/repairApprove/list/json4rsa",
		root : 'data',
		idProperty : 'id',
		modelName : 'RepairSettleAccountApprove',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'repairSettleAccountId', header : "repairSettleAccountId", width : 45, hidden:true },
		   { id : 'taskId', header:"taskId", width:100, hidden:true },
		   { id : 'taskName', header : "${f:getText('Com.TaskName')}", width : 150},
           { id : 'approveResult', header : "${f:getText('Com.Result')}", width : 80},
           { id : 'comleteTime', header : "${f:getText('Com.OperationDate')}", width : 150 },
           { id : 'responsePeople', header : "${f:getText('Com.ResponseUser')}", width : 80 },
           { id : 'opinions', header : "${f:getText('Com.ApproveOpinion')}", width : 320}
        ]
};
</script>