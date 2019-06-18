<div id="divGeneralLeft">
	<t:common type="select" tabindex='1040' path="years" id="years" key="Com.Years" items="${years}" itemValue="name" itemLabel="text" notNull="true"/>
	<t:entity type="ProjectApproveBudget" tabindex='1000' path="pab" id="pab" bindModel="${bindModel}" key="Com.ProjectBudget" onchange="pab_onchange"/>
	<t:common type="text" tabindex='10011' path="approveNo" id="approveNo" key="Com.PAB.ApproveNo" notNull="true"/>
</div>

<div id="divGeneralCenter">
	<t:common type="select" tabindex='1040' path="budgetType" id="budgetType" key="Com.BudgetType" items="${budgetType}" itemValue="name" itemLabel="text" notNull="true" />
	<t:common type="text" tabindex='10011' path="pab.project.code" id="projectCode" key="Com.ProjectCode" disabled="true"/>
	<t:common type="text" tabindex='10015' path="budgets" id="budgets" key="Com.ApproveAmount"  notNull="true"/>
	
</div>

<div id="divGeneralRight">
	<t:common type="text" tabindex='10011' path="approveContents" id="approveContents" key="Com.ApproveContents" notNull="true"/>
	<t:common type="text" tabindex='10011' path="pab.project.name" id="projectName" key="Com.ProjectName" disabled="true"/>
	<t:common type="date" tabindex='10011' path="pab.approveDate" id="approveDate" key="Com.PAB.ApproveDate" disabled="true"/>
</div>


