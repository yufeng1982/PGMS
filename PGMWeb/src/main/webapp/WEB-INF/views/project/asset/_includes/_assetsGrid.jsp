<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/asset/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'AssetsCategory',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'seq', header : "${f:getText('Com.Seq')}", width : 45, editable : false},
		   { id : 'department', header : "${f:getText('Com.Department')}", width : 100, editable : false}, 
		   { id : 'code', header : "${f:getText('Com.EquipmentCode')}", width : 150, editable : false},
           { id : 'contract', header : "${f:getText('Com.ContractCode')}", width : 150, editable : false},
           { id : 'assetsCategory', header : "${f:getText('Com.AssetName')}", width : 120, editable : false},
           { id : 'brand', header : "${f:getText('Com.Brand')}", width : 120, editable : false},
           { id : 'specification', header : "${f:getText('Com.Specification')}", width : 120, editable : false},
           { id : 'quantity', header : "${f:getText('Com.Quantity')}", width : 70, editable : false},
           { id : 'qualityPeriod', header : "${f:getText('Com.QualityPeriod')}", width : 90, editable : false},
           { id : 'description', header : "${f:getText('Com.CommentDescription')}", width : 180, editable : false},
           { id : 'vendor', header : "${f:getText('Com.Vendor')}", width : 120, editable : false},
           { id : 'sapcode1', header : "${f:getText('Com.Sapcode1')}", width : 120, editable : false},
           { id : 'sapcode2', header : "${f:getText('Com.Sapcode2')}", width : 120, editable : false}
        ]
};
</script>