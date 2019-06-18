<div class="divGeneraDivAll">
	<t:common type="checkbox" tabindex='1005' path="adjust" id="adjust" key="Com.AdjustRepairOrder" disabled="${taskCode ne 'G000012'}" onclick="adjustOnclick"/>
</div>

<div class="divGeneraDivJS">
	<t:common type="select" tabindex='1015' path="adjustType" id="adjustType" key="Com.AmountAdjustType" items="${adjustType}" itemValue="name" itemLabel="text" disabled="true" onchange="changeAmountOnChange"/>
	<t:common type="text" tabindex='1022' path="settleAccount" id="settleAccount" key="Com.CurrentTotalSettle" disabled="true"/>
	<div class="bl"><span>${f:getText('Com.ChangeFile')}</span></div><div id="file9" class="file"></div>
</div>

<div class="divGeneraDivJSR">
	<t:common type="text" tabindex='1022' path="changeAmount" id="changeAmount" key="Com.ChangeAmounts" disabled="true" onchange="changeAmountOnChange"/>
	<t:common type="text" tabindex='1024' path="thisBudgetUpper" id="thisBudgetUpper" key="Com.Upper" disabled="true"/>
	<div class="downLoad"><c:if test="${!empty entity.imagePath9}"><span><a href="/app/pgm/project/repairOrder/form/${entityId}/downLoad?index=9">${f:getFileName(entity.imagePath9)}</a></span></c:if></div>
</div>

<div class="divGeneraDivAll">
	<t:common type="textarea" tabindex='10110' path="opinions12" id="opinions12" key="Com.Detail" disabled="true"/>
</div>
