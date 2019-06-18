<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
CONFIG_OIL_LINE = {
		url : "/app/pgm/project/petrolStationsLine/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'PetrolStationsLine',
		isEditable : true,
		isPaging : false,
		isInfinite : false,
		autoLoad : true,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'seq', header:"seq", width:100, hidden:true },
           { id : 'name', header : "${f:getText('Com.Name')}", flex : 1, width : 160, editable : false, sortable : false, menuDisabled : true,resizable : false},
           { id : 'zpmj', header : "${f:getText('Com.Zpmj')}", flex : 1, width : 160, fieldType: 'number', editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'ct', header : "${f:getText('Com.Ct')}", flex : 1, width : 160, fieldType: 'combobox', controlData : ${ct}, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'bulidYear', header : "${f:getText('Com.BulidYear')}", width : 160, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'bmzk', header : "${f:getText('Com.Bmzk')}", flex : 1, width : 160, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'wmsl', header : "${f:getText('Com.Wmsl')}", flex : 1, width : 160, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'wmps', header : "${f:getText('Com.Wmps')}", flex : 1, width : 160, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'ywdd', header : "${f:getText('Com.Ywdd')}", flex : 1, width : 160, fieldType: 'combobox', controlData : ${yw}, editable : true, sortable : false, menuDisabled : true,resizable : false}
        ]
};
</script>