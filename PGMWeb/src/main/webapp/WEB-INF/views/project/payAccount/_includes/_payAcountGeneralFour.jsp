<div id="divGeneralLeft">
	<t:common type="text" tabindex='10080' path="fourRequestUser.realName" id="fourRequestUser" key="Com.PayAccount.FourRequestUser" disabled="true"/>
	<t:common type="text" tabindex='10030' path="fourRequestAmount" id="fourRequestAmount" key="Com.PayAccount.FourRequestAmount" disabled="true"/>
</div>

<div id="divGeneralCenter">
	<t:maintenance type="Department" tabindex='10091' path="fourRequestUser.department" id="fourRequesDepartment" key="Com.PayAccount.FourRequestDepartment" bindModel="${bindModel}" disabled="true"/>
</div>
<div id="divAll" style="margin-bottom: 65px;">
	<t:common type="textarea" tabindex='10102' path="fourPayContents" id="fourPayContents" key="Com.PayAccount.FourPayContents" rows="3" cols = "81" disabled="true"/>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment1')}</span></div><div class="downLoad"><c:if test="${!empty entity.fourPayFile1}"><span><a href="/app/pgm/project/payItem/form/${entity.fourPayItem.id}/downLoad?index=1">${f:getFileName(entity.fourPayFile1)}</a></span></c:if></div>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment2')}</span></div><div class="downLoad"><c:if test="${!empty entity.fourPayFile2}"><span><a href="/app/pgm/project/payItem/form/${entity.fourPayItem.id}/downLoad?index=2">${f:getFileName(entity.fourPayFile2)}</a></span></c:if></div>
</div>
<div id="divGeneralLeftFourPay">
	<t:common type="text" tabindex='10080' path="fourPayUser.realName" id="fourPayUser" key="Com.PayAccount.FourPayUser" disabled="true"/>
	<t:common type="text" tabindex='10030' path="fourPayAmount" id="fourPayAmount" key="Com.PayAccount.FourAcctualPayAmount" disabled="true"/>
</div>

<div id="divGeneralCenterFourPay">
	<t:common type="date" tabindex='10091' path="fourPayDate" id="fourPayDate" key="Com.PayAccount.FourPayDate" bindModel="${bindModel}" disabled="true"/>
	<t:common type="text" tabindex='10060' path="fourPayItem.cooperationAccount.receiveNo" id="fourReceiveNo" key="Com.Contranct.ReceiveNo" disabled="true"/>
</div>