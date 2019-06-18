<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/repairOrder/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'RepairOrder',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'seq', header : "${f:getText('Com.Seq')}", width : 45, editable : false},
		   { id : 'code', header : "${f:getText('Com.RepairCode')}", width : 150, editable : false},
           { id : 'project', header : "${f:getText('Com.RequestProject')}", width : 150, editable : false},
           { id : 'asset', header : "${f:getText('Com.RepairItem')}", width : 120, editable : false},
           { id : 'budget', header : "${f:getText('Com.RepairBudget')}", width : 120, fieldType: 'number',editable : false},
           { id : 'repairType', header : "${f:getText('Com.RepairType')}", width : 120, editable : false},
           { id : 'creationDate', header : "${f:getText('Com.RequestTime')}", width : 150, editable : false},
           { id : 'repairStatus', header : "${f:getText('Com.Status')}", width : 150, editable : false}
        ]
};
</script>