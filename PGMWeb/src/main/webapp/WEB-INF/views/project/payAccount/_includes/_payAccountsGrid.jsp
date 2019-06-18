<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/payAccount/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'PayAccount',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'assetsCategory', header : "${f:getText('Com.Contranct.AssetsCategory')}", width : 100, editable : false},
           { id : 'contractId', header:"contractId", width:100, hidden:true },
           { id : 'contractCode', header : "${f:getText('Com.Contranct.Code')}", width : 150, editable : false,
        	   renderer : function (value, metaData, record, rowIndex, colIndex, store, view) {
				   return '<a style="text-decoration: none;" href="javascript:showContract(\''+ record.data.contractId +'\')">'+ value +'</a>';
        	   }   
           },
           { id : 'ourSideCorText', header : "${f:getText('Com.Contranct.OurSideCorporation')}", width : 120, editable : false},
           { id : 'cooperationText', header : "${f:getText('Com.Contranct.Cooperation')}", width : 100, editable : false},
           { id : 'amount', header : "${f:getText('Com.Contranct.Amount')}", width : 100, fieldType:'number', editable : false},
           { id : 'adjustAmount', header : "${f:getText('Com.Contranct.AdjustAmount')}", width : 100, fieldType:'number', editable : false},
           { id : 'settleAccounts', header : "${f:getText('Com.Contranct.SettleAccounts')}", width : 100, fieldType:'number', editable : false},
           { id : 'onePayAmount', header : "${f:getText('Com.OnePayAmount')}", width : 100, fieldType:'number', editable : false},
           { id : 'onePayDate', header : "${f:getText('Com.OnePayDate')}", width : 150, editable : false},
           { id : 'twoPayAmount', header : "${f:getText('Com.TwoPayAmount')}", width : 100, fieldType:'number', editable : false},
           { id : 'twoPayDate', header : "${f:getText('Com.TwoPayDate')}", width : 150, editable : false},
           { id : 'threePayAmount', header : "${f:getText('Com.ThreePayAmount')}", width : 100, fieldType:'number', editable : false},
           { id : 'threePayDate', header : "${f:getText('Com.ThreePayDate')}", width : 150, editable : false},
           { id : 'fourPayAmount', header : "${f:getText('Com.FourPayAmount')}", width : 100, fieldType:'number', editable : false},
           { id : 'fourPayDate', header : "${f:getText('Com.FourPayDate')}", width : 150, editable : false},
           { id : 'finishAmount', header : "${f:getText('Com.FinishAmount')}", width : 100, fieldType:'number', editable : false},
           { id : 'blanceAmount', header : "${f:getText('Com.BlanceAmount')}", width : 100, fieldType:'number', editable : false}
        ]
};
function showContract(id) {
	LUtils.showContract({id:id});
}
</script>