<script type="text/javascript">
G_SUPERVISOR_CONFIG = {
		url :"/app/hr/supervisor/list/getSupervisor",
		root : 'data',
		idProperty : 'id',
		isEditable : false,
		autoLoad : true,
		isPaging : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'sequence', header : "Sequence", width : 80, editable : false, fieldType :'number'},
           { id : 'code', header : "", width : 80, hidden:true},
           { id : 'name', header : "", width : 120, hidden:true},
           { id : 'employeeId', header:"", width:100, hidden:true},
           { id : 'employeeCode', header:"Code", width:100},
           { id : 'employeeName', header:"Name", width:100},
           { id : 'defaultSupervisor', header:"defaultSupervisor", width : 80, hidden:true}
        ]
};
</script>