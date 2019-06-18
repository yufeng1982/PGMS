<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
CONFIG_OM_LINE = {
		url : "/app/pgm/project/oilMachineLine/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'OilMachineLine',
		isEditable : true,
		isPaging : false,
		isInfinite : false,
		autoLoad : true,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'seq', header:"seq", header : "${f:getText('Com.Seq')}", width:40, editable : false, sortable : false, menuDisabled : true,resizable : false},
           { id : 'pingpai', header : "${f:getText('Com.Pingpai')}", flex : 1, width : 208, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'xinghao', header : "${f:getText('Com.Xinghao')}", flex : 1, width : 208, editable : true,resizable : false},
           { id : 'mqty', header : "${f:getText('Com.Qty')}", flex : 1, width : 208, fieldType: 'number', editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'releaseDate', header:"${f:getText('Com.ReleaseDate')}", flex : 1, width:208, fieldType: 'date', editable: true,menuDisabled : true,sortable : false,resizable : false}
        ]
};
</script>