<script type="text/javascript">
G_CLOTHES_CONFIG = {
		url : "/app/pgm/photo/itemPackageClothes/line/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'itemPackageClothes',
		isEditable : true,
		isPaging : false,
		isInfinite : false,
		autoLoad : true,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'clothesId', header:"clothesId", width:100, hidden:true },
           { id : 'sequence', header : "${f:getText('Com.Code')}", width : 100, editable : true, fieldType: 'browerButton', popupSelectOnClick:'popupClothes'},
           { id : 'name', header : "${f:getText('Com.Name')}", width : 100, editable : false},
           { id : 'description', header : "${f:getText('Com.Description')}", width : 220, editable : false}
        ]
};
</script>