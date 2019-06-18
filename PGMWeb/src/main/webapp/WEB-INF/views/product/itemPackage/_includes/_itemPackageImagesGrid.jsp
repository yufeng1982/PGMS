<script type="text/javascript">
G_IMAGES_CONFIG = {
		url : "/app/pgm/photo/itemPackageImages/line/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'itemPackageImages',
		isEditable : true,
		isPaging : false,
		isInfinite : false,
		autoLoad : true,
		columns : [
		   {id : 'id', header:"id", width:100, hidden:true },
		   {id : 'imagePath', header : "${f:getText('Com.ImageFile')}", fieldType:'browerButton',popupSelectOnClick:'openUploadFile',width : 280, editable : true},
		   {id : 'fileName', header : "${f:getText('Com.FileName')}", width : 180, editable : false}
        ]
};
</script>