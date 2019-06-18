<div id="divGeneralLeft">
	<t:entity type="Project" tabindex='1000' path="project" id="projectCode" bindModel="${bindModel}" key="Com.ProjectCode" notNull="true" onchange="projcet_onchange"/>
	<t:common type="text" tabindex='10010' path="oilLevel" id="oilLevel" key="Com.PAB.OilLevel" notNull="true"/>
	 <t:common type="date" tabindex='1004' path="approveDate" id="approveDate" key="Com.PAB.ApproveDate" notNull="true" />
</div>

<div id="divGeneralCenter">
	<t:common type="text" tabindex='10061' path="project.name" id="projectName" key="Com.ProjectName"  disabled="true" />
	<t:common type="text" tabindex='10062' path="approveLevel" id="approveLevel" key="Com.PAB.ApproveLevel" notNull="true"/>
	<div class="bl"><span>${f:getText('Com.PAB.FileName')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file" class="file"></div>
</div>

<div id="divGeneralRight">
	<t:common type="text" tabindex='10061' path="amount" id="amount" key="Com.PAB.Amount" notNull="true"/>
	<t:common type="text" tabindex='10062' path="code" id="code" key="Com.PAB.ApproveNo"  notNull="true"/>
	<div class="downLoad"><c:if test="${!empty entity.filePath}"><span><a href="/app/pgm/project/projectApproveBudget/form/${entityId}/downLoad">${f:getFileName(entity.filePath)}</a></span></c:if></div>
</div>


