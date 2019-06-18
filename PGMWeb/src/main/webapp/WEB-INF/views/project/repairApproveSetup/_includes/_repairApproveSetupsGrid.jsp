<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/repairApproveSetup/list/json?pat=${type}",
		root : 'data',
		idProperty : 'id',
		modelName : 'RepairApproveSetup',
		isEditable : true,
		isPaging : false,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'seq', header : "${f:getText('Com.Seq')}", width : 45, editable : false, menuDisabled : true, sortable : false},
		   { id : 'code', header : "${f:getText('Com.Code')}", width : 100, editable : false, menuDisabled : true, sortable : false},
           { id : 'name', header : "${f:getText('Com.Name')}", width : 150, editable : false, menuDisabled : true, sortable : false},
           { id : 'users', header:"role", width:100, hidden:true },
           { id : 'usersText', header : "${f:getText('Com.ApproveUsers')}", width : 180, fieldType : 'browerButton', popupSelectOnClick : 'userSelect', editable : true, menuDisabled : true, sortable : false},
           { id : 'role', header:"role", width:100, hidden:true },
           { id : 'roleText', header : "${f:getText('Com.ApproveRoles')}", width : 180, fieldType : 'browerButton', popupSelectOnClick : 'roleSelect', editable : true, menuDisabled : true, sortable : false},
           { id : 'judgeAmount', header : "${f:getText('Com.JudgeAmount')}", width : 150, editable : true, menuDisabled : true, sortable : false},
        ]
};
</script>