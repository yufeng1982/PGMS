<div class="panel">
	<div id="divGeneralLeft">
		<t:common type="text" tabindex='1010' path="code" id="code" key="Com.CooperationCode" maxlength="100"  onchange="checkComponentUnique"/>
		<t:common type="text" tabindex='1031' path="phone" id="phone" key="Com.Phone" maxlength="20" notNull="true" />
	</div>
	<div id="divGeneralLeft">
		<t:common type="text" tabindex='1010' path="name" id="name" key="Com.CooperationName" maxlength="100" notNull="true" />
		<t:common type="text" tabindex='1061' path="email" id="email" key="Com.Email" maxlength="50" notNull="true" />
	</div>
	<div id="divGeneralRight">
		<t:common type="text" tabindex='1040' path="corporate" id="corporate" key="Com.Corporate" maxlength="100" notNull="true" />
		<t:common type="date" tabindex='1060' path="creationDate" id="creationDate" key="Com.CreationDate" disabled="true"/>
	</div>
</div>
<div class="panel">
	<div class="divaddress1">
		<t:common type="select" tabindex='1031' value="${entity.cooperationType.id}" path="cooperationType" id="cooperationType" key="Com.CooperationType" items="${cooperationType}" itemValue="id" itemLabel="name" notNull="true" />
	</div>
	<div class="divaddress2">
		<t:common type="text" tabindex='10070' path="address" id="address" key="Com.CompanyAddress" notNull="true" />
	</div>
</div>
<div class="divAll">
	<div class="bl"><span>${f:getText('Com.BusinessLicense1')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file1" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath1}"><span><a href="/app/pgm/project/cooperation/form/${entityId}/downLoad?index=1">${f:getFileName(entity.filePath1)}</a></span></c:if></div>
</div>
<div class="divAll">
	<div class="bl"><span>${f:getText('Com.BusinessLicense2')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file2" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath2}"><span><a href="/app/pgm/project/cooperation/form/${entityId}/downLoad?index=2">${f:getFileName(entity.filePath2)}</a></span></c:if></div>
</div>
<div class="divAll">
	<div class="bl"><span>${f:getText('Com.BusinessLicense3')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file3" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath3}"><span><a href="/app/pgm/project/cooperation/form/${entityId}/downLoad?index=3">${f:getFileName(entity.filePath3)}</a></span></c:if></div>
</div>
<div class="divAll">
	<div class="bl"><span>${f:getText('Com.BusinessLicense4')}</span></div><div id="file4" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath4}"><span><a href="/app/pgm/project/cooperation/form/${entityId}/downLoad?index=4">${f:getFileName(entity.filePath4)}</a></span></c:if></div>
</div>
<div class="divAll">
	<div class="bl"><span>${f:getText('Com.BusinessLicense5')}</span></div><div id="file5" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath5}"><span><a href="/app/pgm/project/cooperation/form/${entityId}/downLoad?index=5">${f:getFileName(entity.filePath5)}</a></span></c:if></div>
</div>
<div class="divAll">
	<div class="bl"><span>${f:getText('Com.BusinessLicense6')}</span></div><div id="file6" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath6}"><span><a href="/app/pgm/project/cooperation/form/${entityId}/downLoad?index=6">${f:getFileName(entity.filePath6)}</a></span></c:if></div>
</div>

