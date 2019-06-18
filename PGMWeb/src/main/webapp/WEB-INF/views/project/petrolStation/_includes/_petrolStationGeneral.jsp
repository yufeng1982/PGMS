<div id="divGeneralLeft">
	<t:entity type="Project" tabindex='1000' path="project" id="projectCode" bindModel="${bindModel}" key="Com.ProjectCode" notNull="true" onchange="projcet_onchange"/>
	<t:common type="text" tabindex='10013' path="name" id="name" key="Com.ProjectFullName" notNull="true" />
	<t:common type="text" tabindex='10016' path="sapCode" id="sapCode" key="Com.SAPCode"  onchange="checkComponentUnique"/>
</div>

<div id="divGeneralCenter">
	<t:common type="text" tabindex='10011' path="project.pct" id="pct" key="Com.City"  disabled="true" />
	<t:common type="text" tabindex='10014' path="shortName" id="shortName" key="Com.OilShortName" notNull="true"/>
	<t:common type="text" tabindex='1017' path="cnCode" id="cnCode" key="Com.CNCode" onchange="checkComponentUnique"/>
</div>

<div id="divGeneralRight">
	<t:common type="text" tabindex='10012' path="project.address" id="address" key="Com.ProjectAddress" disabled="true"/>
	<t:common type="text" tabindex='10015' path="code" id="code" key="Com.OilEName"  notNull="true" onchange="checkComponentUnique"/>
</div>


