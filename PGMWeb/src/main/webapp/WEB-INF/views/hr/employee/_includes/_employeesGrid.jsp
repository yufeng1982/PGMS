<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/hr/employee/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'Employee',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'code', header : "${f:getText('Com.EmployeeCode')}", width : 80, editable : false},
           { id : 'name', header : "${f:getText('Com.EmployeeName')}", width : 80, editable : false},
           { id : 'loginUserText', header : "${f:getText('Com.UserName')}", width : 80, editable : false},
           { id : 'gender', header : "${f:getText('Com.Gender')}", width : 80, editable : false},
           { id : 'department', header : "${f:getText('Com.Department')}", width : 100, editable : true},
           { id : 'position', header : "${f:getText('Com.Position')}", width : 100, editable : true},
           { id : 'language', header : "${f:getText('Com.Language')}", width : 80, editable : false},
           { id : 'phoneNumber', header : "${f:getText('Com.PhoneNumber')}", width : 120, editable : false},
           { id : 'email', header : "${f:getText('Com.Email')}", width : 150, editable : false},
           { id : 'hireDate', header : "${f:getText('Com.HireDate')}", width : 120, editable : false},
           { id : 'birthday', header : "${f:getText('Com.Birthday')}", width : 120, editable : false},
           { id : 'employeeType', header:"employeeType", width:100, hidden:true},
        ]
};
</script>