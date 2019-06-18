<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/payItem/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'PayItem',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'projectText', header : "${f:getText('Com.Contranct.Project')}", width : 180, editable : false},
		   { id : 'contractText', header : "${f:getText('Com.Contranct.Name')}", width : 120, editable : false},
           { id : 'contractCode', header : "${f:getText('Com.Contranct.Code')}", width : 180, editable : false},
           { id : 'status', header : "${f:getText('Com.Status')}", width : 80, editable : false},
           { id : 'payCount', header : "${f:getText('Com.PayNumber')}", width : 80, editable : false},
           { id : 'cooperationText', header : "${f:getText('Com.Contranct.Cooperation')}", width : 180, editable : false},
           { id : 'receiveNo', header : "${f:getText('Com.Contranct.ReceiveNo')}", width : 180, editable : false},
           { id : 'amount', header : "${f:getText('Com.Contranct.Amount')}", width : 120, fieldType:'number', editable : false},
           { id : 'adjustAmount', header : "${f:getText('Com.Contranct.AdjustAmount')}", width : 120, fieldType:'number', editable : false},
           { id : 'settleAccounts', header : "${f:getText('Com.Contranct.SettleAccounts')}", width : 120, fieldType:'number', editable : false},
           { id : 'createUer', header : "${f:getText('Com.PayItem.CreateUser')}", width : 120, editable : false},
           { id : 'department', header : "${f:getText('Com.PayItem.Department')}", width : 100, editable : false},
           { id : 'assetNo', header : "${f:getText('Com.PayItem.AssetNo')}", width : 100, editable : false},
           { id : 'payUser', header : "${f:getText('Com.PayItem.PayUser')}", width : 120, editable : false},
           { id : 'payDate', header : "${f:getText('Com.PayItem.PayDate')}", width : 150, editable : false},
         
        ]
};
</script>