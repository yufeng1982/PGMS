<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
CONFIG_OTH_LINE = {
		url : "/app/pgm/project/otherEquipmentLine/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'OtherEquipmentLine',
		isEditable : true,
		isPaging : false,
		isInfinite : false,
		autoLoad : true,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'seq', header:"seq", header : "${f:getText('Com.Sequence')}", width:60, hidden:true , sortable : false, menuDisabled : true,resizable : false},
		   { id : 'enabled', header : "${f:getText('Com.HaveNo')}", width : 40, fieldType: 'checkbox', editable : true, sortable : false, menuDisabled : true,resizable : false},
		   { id : 'name', header : "${f:getText('Com.Name')}", flex : 1,width : 208, editable : false, sortable : false, menuDisabled : true,resizable : false},
           { id : 'pingpai', header : "${f:getText('Com.Pingpai')}", flex : 1,width : 208, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'xinghao', header : "${f:getText('Com.Xinghao')}", flex : 1,width : 208, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'oelReleaseDate', header : "${f:getText('Com.ReleaseDate')}", flex : 1,width : 208, fieldType: 'date', editable : true, menuDisabled : true,sortable : false,resizable : false}
        ]
};
</script>