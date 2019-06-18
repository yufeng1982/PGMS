<div id="divGeneralLeft">
	<t:common type="text" tabindex='10010' path="contract.contractAssetNo" id="assetNo" key="Com.PayItem.AssetNo" />
	<t:common type="text" tabindex='10030' path="payAmount" id="payAmount" key="Com.Contranct.ActualPayAmount"  notNull="true" />
</div>

<div id="divGeneralCenter">
	<t:common type="text" tabindex='10070' path="payUser.realName" id="payUser" key="Com.PayItem.PayUser" disabled="true"/>
	<t:common type="date" tabindex='10100' path="payDate" id="payDate" key="Com.PayItem.PayDate" disabled="true"/>
</div>

<div id="divGeneralRight">
	
</div>

<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment3')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file2" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath3}"><span><a href="/app/pgm/project/payItem/form/${entityId}/downLoad?index=3">${f:getFileName(entity.filePath3)}</a></span></c:if></div>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Attachment4')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file3" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath4}"><span><a href="/app/pgm/project/payItem/form/${entityId}/downLoad?index=4">${f:getFileName(entity.filePath4)}</a></span></c:if></div>
</div>