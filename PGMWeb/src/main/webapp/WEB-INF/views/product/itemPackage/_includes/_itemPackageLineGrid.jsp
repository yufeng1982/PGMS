<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/photo/itemPackage/line/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'itemPackageLine',
		isEditable : true,
		isPaging : false,
		isInfinite : false,
		autoLoad : true,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'itemId', header:"itemId", width:100, hidden:true },
           { id : 'item', header : "${f:getText('Com.Item')}", width : 100, editable : true, fieldType: 'browerButton', popupSelectOnClick:'popupItem'},
           { id : 'description', header : "${f:getText('Com.Description')}", width : 220, editable : true},
           { id : 'price', header : "${f:getText('Com.Price')}", width : 100, editable : true, fieldType : "number"},
           { id : 'quantity', header : "${f:getText('Com.Quantity')}", width : 100, editable : true, fieldType : "number"},
           { id : 'amount', header : "${f:getText('Com.Amount')}", width : 100, editable : false, fieldType : "number"}
        ]
};
</script>