<div id="divGeneralLeft">
	<t:entity type="PetrolStation" tabindex='1000' path="petrolStation" id="petrolStation" bindModel="${bindModel}" key="Com.Contranct.Project" disabled="${entityId ne 'NEW'}" notNull="true" onchange="projectOnchange"/>
	<t:common type="text" tabindex='10010' path="contract.code" id="contractCode" key="Com.Contranct.Code" notNull="true" disabled="true"/>
	<t:common type="text" tabindex='10020' path="contract.amount" id="contractAmount" key="Com.Contranct.Amount" notNull="true" disabled="true"/>
	<t:common type="text" tabindex='10021' path="adjustAmount" id="adjustAmount" key="Com.Contranct.AdjustAmount" notNull="true" disabled="true"/>
	<t:common type="text" tabindex='10030' path="requestAmount" id="requestAmount" key="Com.RequestAmount" notNull="true"/>
</div>

<div id="divGeneralCenter">
	<t:entity type="Contract" tabindex='10040' path="contract" id="contract" bindModel="${bindModel}" key="Com.Contranct.Name" disabled="${entityId ne 'NEW'}" initParameters="initParams4Contract" notNull="true" onchange="contractOnchange"/>
	<t:entity type="Cooperation" tabindex='10040' path="cooperation" id="cooperation" bindModel="${bindModel}" key="Com.Contranct.Cooperation" disabled="true" notNull="true"/>
	<t:entity type="CooperationAccount" tabindex='10050' path="cooperationAccount" id="cooperationAccount" key="Com.Contranct.ReceiveNo" notNull="true" bindModel="${bindModel}" initParameters="initParams4CooperationAccount" disabled="${entityId ne 'NEW'}"/>
	<t:common type="text" tabindex='10061' path="settleAccounts" id="settleAccounts" key="Com.Contranct.SettleAccounts" notNull="true" disabled="true"/>
	<t:common type="text" tabindex='10070' path="contract.payAmount" id="contractPayAmount" key="Com.Contranct.PayAmount" notNull="true" disabled="true"/>
</div>

<div id="divGeneralRight">
	<t:common type="select" tabindex='10071' path="payType" id="payType" key="Com.Status" items="${payStatus}" itemValue="name" itemLabel="text" disabled="true"/>
	<t:common type="text" tabindex='10072' path="contract.payCounts" id="payCount" key="Com.PayCount" disabled="true"/>
	<t:common type="text" tabindex='10080' path="createdBy.realName" id="createUser" key="Com.Contranct.CreateUser" disabled="true"/>
	<t:maintenance type="Department" tabindex='10091' path="department" id="department" key="Com.Contranct.Department" bindModel="${bindModel}" disabled="true"/>
</div>

<div id="divAll" style="margin-bottom: 65px;">
	<t:common type="textarea" tabindex='10102' path="payContents" id="payContents" key="Com.PayContents" rows="3" cols = "81"/>
</div>

<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment1')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath1}"><span><a href="/app/pgm/project/payItem/form/${entityId}/downLoad?index=1">${f:getFileName(entity.filePath1)}</a></span></c:if></div>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment2')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file1" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath2}"><span><a href="/app/pgm/project/payItem/form/${entityId}/downLoad?index=2">${f:getFileName(entity.filePath2)}</a></span></c:if></div>
</div>

