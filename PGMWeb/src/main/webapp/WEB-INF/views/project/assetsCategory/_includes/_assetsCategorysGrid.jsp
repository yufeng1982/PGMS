<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/assetsCategory/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'AssetsCategory',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'code', header : "${f:getText('Com.Code')}", width : 180, editable : false},
           { id : 'name', header : "${f:getText('Com.AssetsCategoryName')}", width : 180, editable : false},
           { id : 'level', header : "${f:getText('Com.Level')}", width : 120, editable : false},
           { id : 'pathName', header : "${f:getText('Com.Category')}", width : 280, editable : false}
        ]
};
</script>