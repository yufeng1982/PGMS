<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/flowDefinitionLine/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'FlowDefinitionLine',
		isEditable : true,
		isPaging : true,
		isInfinite : false,
		autoLoad : true,
		uniqueColumns : [['fdlcode'],['fdlname']],
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'seqNo', header : "${f:getText('Com.SeqNo')}", width : 60, editable : false, menuDisabled : true, sortable : false},
           { id : 'fdlcode', header : "${f:getText('Com.Code')}", width : 120, editable : true, notNull: true, menuDisabled : true, sortable : false},
           { id : 'fdlname', header : "${f:getText('Com.Name')}", width : 150, editable : true, notNull: true, menuDisabled : true, sortable : false},
           { id : 'role', header:"role", width:100, hidden:true },
           { id : 'roleText', header : "${f:getText('Com.ApproveRoles')}", width : 120, fieldType : 'browerButton', popupSelectOnClick : 'roleSelect', editable : true, menuDisabled : true, sortable : false},
           { id : 'users', header:"role", width:100, hidden:true },
           { id : 'usersText', header : "${f:getText('Com.ApproveUsers')}", width : 150, fieldType : 'browerButton', popupSelectOnClick : 'userSelect', editable : true, menuDisabled : true, sortable : false},
           { id : 'edit', fieldType: 'checkbox', header : "${f:getText('Com.ModifyContract')}", defaultValue : false, width : 65, editable : true, menuDisabled : true, sortable : false},
           { id : 'description', header : "${f:getText('Com.Description')}", width : 250, editable : true, menuDisabled : true, sortable : false}
        ]
};
</script>