<div id="divGeneralLeft">
	<t:common type="text" tabindex='10080' path="oneRequestUser.realName" id="oneRequestUser" key="Com.PayAccount.OneRequestUser" disabled="true"/>
	<t:common type="text" tabindex='10030' path="oneRequestAmount" id="oneRequestAmount" key="Com.PayAccount.OneRequestAmount" disabled="true"/>
</div>

<div id="divGeneralCenter">
	<t:maintenance type="Department" tabindex='10091' path="oneRequestUser.department" id="oneRequesDepartment" key="Com.PayAccount.OneRequestDepartment" bindModel="${bindModel}" disabled="true"/>
</div>
<div id="divAll" style="margin-bottom: 65px;">
	<t:common type="textarea" tabindex='10102' path="onePayContents" id="onePayContents" key="Com.PayAccount.OnePayContents" rows="3" cols = "81" disabled="true"/>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment1')}</span></div><div class="downLoad"><c:if test="${!empty entity.onePayFile1}"><span><a href="/app/pgm/project/payItem/form/${entity.onePayItem.id}/downLoad?index=1">${f:getFileName(entity.onePayFile1)}</a></span></c:if></div>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment2')}</span></div><div class="downLoad"><c:if test="${!empty entity.onePayFile2}"><span><a href="/app/pgm/project/payItem/form/${entity.onePayItem.id}/downLoad?index=2">${f:getFileName(entity.onePayFile2)}</a></span></c:if></div>
</div>
<div id="divGeneralLeftOnePay">
	<t:common type="text" tabindex='10080' path="onePayUser.realName" id="onePayUser" key="Com.PayAccount.OnePayUser" disabled="true"/>
	<t:common type="text" tabindex='10030' path="onePayAmount" id="onePayAmount" key="Com.PayAccount.OneAcctualPayAmount" disabled="true"/>
</div>

<div id="divGeneralCenterOnePay">
	<t:common type="date" tabindex='10091' path="onePayDate" id="onePayDate" key="Com.PayAccount.OnePayDate" bindModel="${bindModel}" disabled="true"/>
	<t:common type="text" tabindex='10060' path="onePayItem.cooperationAccount.receiveNo" id="oneReceiveNo" key="Com.Contranct.ReceiveNo" disabled="true"/>
</div>