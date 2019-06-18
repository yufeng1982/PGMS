<div id="divGeneralLeft">
	<t:common type="text" tabindex='10080' path="twoRequestUser.realName" id="twoRequestUser" key="Com.PayAccount.TwoRequestUser" disabled="true"/>
	<t:common type="text" tabindex='10030' path="twoRequestAmount" id="twoRequestAmount" key="Com.PayAccount.TwoRequestAmount" disabled="true"/>
</div>

<div id="divGeneralCenter">
	<t:maintenance type="Department" tabindex='10091' path="twoRequestUser.department" id="twoRequesDepartment" key="Com.PayAccount.TwoRequestDepartment" bindModel="${bindModel}" disabled="true"/>
</div>
<div id="divAll" style="margin-bottom: 65px;">
	<t:common type="textarea" tabindex='10102' path="twoPayContents" id="twoPayContents" key="Com.PayAccount.TwoPayContents" rows="3" cols = "81" disabled="true"/>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment1')}</span></div><div class="downLoad"><c:if test="${!empty entity.twoPayFile1}"><span><a href="/app/pgm/project/payItem/form/${entity.twoPayItem.id}/downLoad?index=1">${f:getFileName(entity.twoPayFile1)}</a></span></c:if></div>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment2')}</span></div><div class="downLoad"><c:if test="${!empty entity.twoPayFile2}"><span><a href="/app/pgm/project/payItem/form/${entity.twoPayItem.id}/downLoad?index=2">${f:getFileName(entity.twoPayFile2)}</a></span></c:if></div>
</div>
<div id="divGeneralLeftTwoPay">
	<t:common type="text" tabindex='10080' path="twoPayUser.realName" id="twoPayUser" key="Com.PayAccount.TwoPayUser" disabled="true"/>
	<t:common type="text" tabindex='10030' path="twoPayAmount" id="twoPayAmount" key="Com.PayAccount.TwoAcctualPayAmount" disabled="true"/>
</div>

<div id="divGeneralCenterTwoPay">
	<t:common type="date" tabindex='10091' path="twoPayDate" id="twoPayDate" key="Com.PayAccount.TwoPayDate" bindModel="${bindModel}" disabled="true"/>
	<t:common type="text" tabindex='10060' path="twoPayItem.cooperationAccount.receiveNo" id="twoReceiveNo" key="Com.Contranct.ReceiveNo" disabled="true"/>
</div>