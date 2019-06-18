<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
CONFIG_OC_LINE = {
		url : "/app/pgm/project/oilcanLine/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'OilCanLine',
		isEditable : true,
		isPaging : false,
		isInfinite : false,
		autoLoad : true,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'seq', header:"seq", header : "${f:getText('Com.Seq')}", width:40, editable : false, sortable : false, menuDisabled : true,resizable : false},
           { id : 'guanrong', header : "${f:getText('Com.Guanrong')}", flex : 1, width : 208, fieldType: 'number', editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'oilType', header : "${f:getText('Com.OilType')}", flex : 1, width : 208, fieldType: 'combobox', controlData : ${ot}, editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'qty', header : "${f:getText('Com.Qty')}", flex : 1, width : 208, fieldType: 'number', editable : true, sortable : false, menuDisabled : true,resizable : false},
           { id : 'age', header : "${f:getText('Com.Age')}", flex : 1, width : 208, editable : true, fieldType: 'number',sortable : false, menuDisabled : true,resizable : false}
        ]
};
</script>