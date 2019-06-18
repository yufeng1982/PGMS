<div id="divGeneralLeft">
	<t:common type="text" tabindex='10080' path="threeRequestUser.realName" id="threeRequestUser" key="Com.PayAccount.ThreeRequestUser" disabled="true"/>
	<t:common type="text" tabindex='10030' path="threeRequestAmount" id="threeRequestAmount" key="Com.PayAccount.ThreeRequestAmount" disabled="true"/>
</div>

<div id="divGeneralCenter">
	<t:maintenance type="Department" tabindex='10091' path="threeRequestUser.department" id="threeRequesDepartment" key="Com.PayAccount.ThreeRequestDepartment" bindModel="${bindModel}" disabled="true"/>
</div>
<div id="divAll" style="margin-bottom: 65px;">
	<t:common type="textarea" tabindex='10102' path="threePayContents" id="threePayContents" key="Com.PayAccount.ThreePayContents" rows="3" cols = "81" disabled="true"/>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment1')}</span></div><div class="downLoad"><c:if test="${!empty entity.threePayFile1}"><span><a href="/app/pgm/project/payItem/form/${entity.threePayItem.id}/downLoad?index=1">${f:getFileName(entity.threePayFile1)}</a></span></c:if></div>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment2')}</span></div><div class="downLoad"><c:if test="${!empty entity.threePayFile2}"><span><a href="/app/pgm/project/payItem/form/${entity.threePayItem.id}/downLoad?index=2">${f:getFileName(entity.threePayFile1)}</a></span></c:if></div>
</div>
<div id="divGeneralLeftThreePay">
	<t:common type="text" tabindex='10080' path="threePayUser.realName" id="threePayUser" key="Com.PayAccount.ThreePayUser" disabled="true"/>
	<t:common type="text" tabindex='10030' path="threePayAmount" id="threePayAmount" key="Com.PayAccount.ThreeAcctualPayAmount" disabled="true"/>
</div>

<div id="divGeneralCenterThreePay">
	<t:common type="date" tabindex='10091' path="threePayDate" id="threePayDate" key="Com.PayAccount.ThreePayDate" bindModel="${bindModel}" disabled="true"/>
	<t:common type="text" tabindex='10060' path="threePayItem.cooperationAccount.receiveNo" id="threeReceiveNo" key="Com.Contranct.ReceiveNo" disabled="true"/>
</div>