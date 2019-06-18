<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/settleAccounts/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'SettleAccounts',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'projectText', header : "${f:getText('Com.Contranct.Project')}", width : 180, editable : false},
		   { id : 'contractText', header : "${f:getText('Com.Contranct.Name')}", width : 120, editable : false},
           { id : 'contractCode', header : "${f:getText('Com.Contranct.Code')}", width : 120, editable : false},
           { id : 'cooperation', header : "${f:getText('Com.Contranct.Cooperation')}", width : 120, editable : false},
           { id : 'amount', header : "${f:getText('Com.Contranct.Amount')}", width : 100, fieldType:'number', editable : false},
           { id : 'adjustType', header : "${f:getText('Com.AdjustType')}", width : 120, editable : false},
           { id : 'adjustAmount', header : "${f:getText('Com.Contranct.AdjustAmount')}", width : 120, fieldType:'number', editable : false},
           { id : 'settleAmount', header : "${f:getText('Com.Contranct.SettleAccounts')}", width : 120, fieldType:'number', editable : false}
        ]
};
</script>