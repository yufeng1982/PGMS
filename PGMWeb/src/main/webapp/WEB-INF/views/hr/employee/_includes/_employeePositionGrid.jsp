<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/hr/employeePosition/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'Position',
		isEditable : true,
		isPaging : true,
		autoLoad : true,
		isInfinite : false, // deault value is false
		uniqueColumns : [["position"]],
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'primary', header : "${f:getText('Com.Primary')}", width:48, editable : true, fieldType : 'checkbox', defaultValue : false},
		   { id : 'positionId', header : "", width : 80, editable : false, hidden : true},
           { id : 'position', header : "${f:getText('Com.Position')}", width : 120, editable : true, fieldType: 'browerButton', popupSelectOnClick:'popupPosition', notNull : true},
           { id : 'entryDate', header : "${f:getText('Com.EntryDate')}", width : 120, editable : true, fieldType : 'date', format : 'Y-m-d'},
           { id : 'leaveDate', header : "${f:getText('Com.LeaveDate')}", width : 120, editable : true, fieldType : 'date', format : 'Y-m-d'},
           { id : 'description', header : "${f:getText('Com.Description')}", width : 120, editable : true}
        ]
};
</script>