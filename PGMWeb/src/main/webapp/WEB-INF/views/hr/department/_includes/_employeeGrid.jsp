<script type="text/javascript">
G_EMPLOYEE_CONFIG = {
		url : "/app/pgm/hr/employeeDepartment/list/getEmployee",
		root : 'data',
		idProperty : 'id',
		isEditable : false,
		autoLoad : true,
		isPaging : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'code', header : "Code", width : 80, hidden:true},
           { id : 'name', header : "Name", width : 120, hidden:true},
		   { id : 'primary', header:"${f:getText('Com.Primary')}", width:48, fieldType: 'checkbox', editable: true},
		   { id : 'employeeId', header:"", width:100, hidden:true},
		   { id : 'employeeCode', header:"${f:getText('Com.Code')}", width:100},
		   { id : 'employeeName', header:"${f:getText('Com.Name')}", width:100},
		   { id : 'entryDate', header:"${f:getText('Com.CommingDate')}", width:100, fieldType: 'date', editable: true},
		   { id : 'departmentId', header:"departmentId", width:100, hidden:true},
        ]
};
</script>