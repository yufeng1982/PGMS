<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/repairSettleAccount/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'RepairSettleAccount',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'code', header : "${f:getText('Com.RepairCode')}", width : 150, editable : false},
           { id : 'project', header : "${f:getText('Com.RequestProject')}", width : 150, editable : false},
           { id : 'asset', header : "${f:getText('Com.RepairItem')}", width : 120, editable : false},
           { id : 'budget', header : "${f:getText('Com.RepairBudget')}", width : 120, fieldType: 'number',editable : false},
           { id : 'repairType', header : "${f:getText('Com.RepairType')}", width : 120, editable : false},
           { id : 'requestUser', header : "${f:getText('Com.PayItem.CreateUser')}", width : 100, editable : false},
           { id : 'creationDate', header : "${f:getText('Com.RequestTime')}", width : 150, editable : false},
           { id : 'repairSettleAccountStatus', header : "${f:getText('Com.RepairSettleAccountStatus')}", width : 90, editable : false}
        ]
};
</script>