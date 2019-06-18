<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/hr/employee/activity/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'EmployeeActivity',
		isEditable : true,
		autoLoad : true,
		isPaging:false,
		isInfinite : true,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'code', header : "code", width : 80, editable : true},
           { id : 'name', header : "Name", width : 120, editable : true},
           { id : 'department', header : "department", width : 120, editable : true},
           { id : 'position', header : "position", width : 120, editable : true}
        ]
};
</script>