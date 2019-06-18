<div id="divGeneralLeft">
	<t:common type="text" tabindex='1000' path="code" id="code" key="Com.EmployeeCode" maxlength="50" notNull="true" disabled="${entityId ne 'NEW'}" onchange="checkComponentUnique"/>
	<t:common type="text" tabindex='1010' path="name" id="name" key="Com.EmployeeName" maxlength="50" notNull="true" />
	<t:common type="text" tabindex='1020' path="userName" id="userName" key="Com.UserName" maxlength="50" notNull="true" onchange="checkUserNameUnique" disabled="${entityId ne 'NEW'}"/>
	<t:common type="text" tabindex='1030' path="email" id="email" key="Com.Email" maxlength="50" notNull="true" onchange="checkComponentUnique"/>
	
</div>

<div id="divGeneralCenter">
	<t:common type="checkbox" tabindex='1050' path="enabled" id="enabled" key="Com.Enabled"/>
	<t:maintenance type="Department" tabindex='1052' path="department" id="department"  notNull="true" key="Com.Department" bindModel="${bindModel}" />
	<t:common type="select" tabindex='1060' path="gender" id="gender" key="Com.Gender" items="${gender}" itemValue="name" itemLabel="text" notNull="true"/>
	<t:common type="text" tabindex='1061' path="phoneNumber" id="phoneNumber" key="Com.PhoneNumber" maxlength="50" notNull="true" />
</div>

<div id="divGeneralRight">
	<t:common type="select" tabindex='1090' path="employeeStatus" id="employeeStatus" key="Com.EmployeeStatus" items="${employeeStatus}" itemValue="name" itemLabel="text" notNull="true"/>
	<t:common type="date" tabindex='1100' path="hireDate" id="hireDate" key="Com.HireDate" notNull="true"/>
	<t:common type="date" tabindex='1130' path="birthday" id="birthday" key="Com.Birthday" />
	<t:common type="textarea" tabindex='1140' path="description" id="description" key="Com.Description" cols='28' rows='4'/>
	<form:hidden path="employeeType" id="employeeType" value = "${entity.employeeType}"/>
</div>