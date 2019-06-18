<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/contract/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'Contract',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   //{ id : 'projectText', header : "${f:getText('Com.Contranct.Project')}", width : 180, editable : false,hidden:true },
		  
           { id : 'code', header : "${f:getText('Com.Contranct.Code')}", width : 180, editable : false},
           { id : 'name', header : "${f:getText('Com.Contranct.Name')}", width : 180, editable : false},
           { id : 'contractCategoryText', header : "${f:getText('Com.Contranct.ContractCategory')}", width : 100, editable : false},
           { id : 'contractStatus', header : "${f:getText('Com.Contranct.ApproveStatus')}", width : 60, editable : false},
           { id : 'payAmountPercent', header : "${f:getText('Com.Contranct.ExecuteStatus')}", width : 100, editable : false},
           { id : 'assetsCategoryText', header : "${f:getText('Com.Contranct.AssetsCategory')}", width : 120, editable : false},
           //{ id : 'ourSideCorporationText', header : "${f:getText('Com.Contranct.OurSideCorporation')}", width : 180, editable : false},
           //{ id : 'cooperationText', header : "${f:getText('Com.Contranct.Cooperation')}", width : 180, editable : false},
          
           { id : 'amount', header : "${f:getText('Com.Contranct.Amount')}", width : 120, fieldType:'number', editable : false},
           { id : 'adjustAmount', header : "${f:getText('Com.Contranct.AdjustAmount')}", width : 120, fieldType:'number', editable : false},
           { id : 'settleAccounts', header : "${f:getText('Com.Contranct.SettleAccounts')}", width : 120, fieldType:'number', editable : false},
           { id : 'signingDate', header : "${f:getText('Com.Contranct.SigningDate')}", width : 120, editable : false}
           //{ id : 'payAmount', header : "${f:getText('Com.Contranct.PayAmount')}", width : 120, fieldType:'number', editable : false},
           
           //{ id : 'qualityGuaranteePeriod', header : "${f:getText('Com.Contranct.QualityGuaranteePeriod')}", width : 120, editable : false},
           //{ id : 'createUer', header : "${f:getText('Com.Contranct.CreateUser')}", width : 120, editable : false},
           //{ id : 'department', header : "${f:getText('Com.Contranct.Department')}", width : 100, editable : false},
           //{ id : 'contractPropertyText', header : "${f:getText('Com.Contranct.Property')}", width : 120, editable : false},
           //{ id : 'ownerContractText', header : "${f:getText('Com.Contranct.Owner')}", width : 100, editable : false},
          
           //{ id : 'costCenterText', header : "${f:getText('Com.Contranct.CostCenter')}", width : 120, editable : false}
           
        ]
};
</script>