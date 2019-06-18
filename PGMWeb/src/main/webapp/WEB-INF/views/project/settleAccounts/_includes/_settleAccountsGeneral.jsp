<div id="divGeneralLeft">
	<t:entity type="PetrolStation" tabindex='10000' path="contract.petrolStation" id="petrolStation" bindModel="${bindModel}" key="Com.Contranct.Project" disabled="${entityId ne 'NEW'}" notNull="true" onchange="projectOnchange"/>
	<t:common type="text" tabindex='10010' path="contract.code" id="contractCode" key="Com.Contranct.Code" notNull="true" disabled="true"/>
	<t:common type="select" tabindex='10020' path="adjustType" id="adjustType" key="Com.AdjustType" items="${adjustType}" itemValue="name" itemLabel="text" notNull="true" onchange="adjustOnchange"/>
	<t:common type="text" tabindex='10030' path="contract.amount" id="contractAmount" key="Com.Contranct.Amount" notNull="true"  disabled="true"/>
	<t:common type="text" tabindex='10040' path="createdBy.realName" id="createUser" key="Com.Contranct.CreateUser" disabled="true"/>
</div>

<div id="divGeneralCenter">
	<t:entity type="Contract" tabindex='10060' path="contract" id="contract" bindModel="${bindModel}" key="Com.Contranct.Name" disabled="${entityId ne 'NEW'}" initParameters="initParams4Contract" notNull="true" onchange="contractOnchange"/>
	<t:entity type="Cooperation" tabindex='10070' path="contract.cooperation" id="cooperation" bindModel="${bindModel}" key="Com.Contranct.Cooperation" disabled="true" notNull="true"/>
	<t:common type="text" tabindex='10080' path="adjustAmount" id="adjustAmount" key="Com.Contranct.AdjustAmount" notNull="true" onchange="adjustOnchange"/>
	<t:common type="text" tabindex='10090' path="settleAmount" id="settleAmount" key="Com.Contranct.SettleAccounts" notNull="true"  disabled="true"/>
	<t:maintenance type="Department" tabindex='10100' path="department" id="department" key="Com.Contranct.Department" bindModel="${bindModel}" disabled="true"/>
</div>

<div id="divGeneralLeftFile">
	<t:common type="text" tabindex='10110' path="attachmentName" id="attachmentName" key="Com.AttachmentName" notNull="true" maxlength="50" />
	<t:common type="date" tabindex='10050' path="approveDate" id="approveDate" key="Com.ApproveDate" bindModel="${bindModel}" notNull="true"/>
</div>

<div id="divGeneralCenterFile">
	<div id="file" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath}"><span><a href="/app/pgm/project/settleAccounts/form/${entityId}/downLoad">${f:getFileName(entity.filePath)}</a></span></c:if></div>
		<input type="hidden" id="filePath" value="${entity.filePath}" />
</div>

<div id="divAll" style="margin-bottom: 65px;">
	<t:common type="textarea" tabindex='10120' path="adjustReason" id="adjustReason" key="Com.AdjustReason" rows="3" cols = "81" />
</div>
