<div id="divGeneraDiv">
	<t:entity type="RepairOrder" tabindex='1010' path="repairOrder" id="repairOrder" bindModel="${bindModel}" key="Com.RepairCode" notNull="true" initParameters="initParams4Repair" onchange="repairOrderOnchange" disabled="${disabled}"/>
	<t:entity type="Asset" tabindex='1011' path="repairOrder.asset" id="asset" bindModel="${bindModel}" key="Com.RepairItem" notNull="true" disabled="true"/>
	<t:common type="text" tabindex='1012' path="yearBudget" id="yearBudget" key="Com.YearBudget" disabled="true"/>
	<t:common type="text" tabindex='1013' path="historySettle" id="historySettle" key="Com.HistorySettle" disabled="true"/>
</div>

<div id="divGeneraDiv">
	<t:entity type="PetrolStation" tabindex='1014' path="repairOrder.petrolStation" id="petrolStation" bindModel="${bindModel}" key="Com.Contranct.Project" notNull="true" disabled="true"/>
	<t:common type="select" tabindex='1015' path="repairOrder.breakdownReason" id="breakdownReason" key="Com.BreakdownReason" items="${breakdownReason}" itemValue="name" itemLabel="text" notNull="true" disabled="true"/>
	<t:common type="text" tabindex='1016' path="changeAmount" id="changeAmount" key="Com.ChangeAmount" disabled="${disabled}" onchange="modifyOnChange"/>
	<t:common type="text" tabindex='1017' path="hseAmount" id="hseAmount" key="Com.HseAmount" disabled="${disabled}" onchange="hseOnChange"/>
</div>

<div id="divGeneraDiv">
	<t:common type="text" tabindex='1018' path="repairOrder.createdBy.realName" id="createUser" key="Com.PayItem.CreateUser" disabled="true"/>
	<t:common type="select" tabindex='1019' path="repairOrder.repairType" id="repairType" key="Com.RepairType" items="${repairType}" itemValue="name" itemLabel="text" notNull="true" disabled="true"/>
	<div class="bl"><span>${f:getText('Com.ChangeFile')}</span></div><div id="file1" class="file"></div>
	<div class="bl"><span>${f:getText('Com.HSEFile')}</span></div><div id="file2" class="file"></div>
</div>

<div id="divGeneraDiv">
	<t:common type="text" tabindex='1020' path="repairOrder.contact" id="contact" bindModel="${bindModel}" key="Com.Contact" maxlength="100" disabled="true"/>
	<t:common type="select" tabindex='1021' path="repairOrder.repairStatus" id="repairStatus" key="Com.RepairStatus" items="${repairSettleAccountStatus}" itemValue="name" itemLabel="text" notNull="true"  disabled="true"/>
	<div class="downLoad"><c:if test="${!empty entity.imagePath1}"><span><a href="/app/pgm/project/repairSettleAccount/form/${entityId}/downLoad?index=1">${f:getFileName(entity.imagePath1)}</a></span></c:if></div>
	<div class="downLoad"><c:if test="${!empty entity.imagePath2}"><span><a href="/app/pgm/project/repairSettleAccount/form/${entityId}/downLoad?index=2">${f:getFileName(entity.imagePath2)}</a></span></c:if></div>
</div>

<div id="divGeneralAllLeft">
	<t:common type="text" tabindex='1022' path="repairOrder.budget" id="thisBudget" key="Com.ThisBudget" notNull="true" disabled="true"/>
	<t:common type="text" tabindex='1023' path="settleAccount" id="settleAccount" key="Com.SettleAccount" notNull="true" onchange="amountOnChange" disabled="${disabled}"/>
</div>

<div id="divGeneralAllCenter">
	<t:common type="text" tabindex='1024' path="thisBudgetUpper" id="thisBudgetUpper" key="Com.Upper" readonly="true"/>
	<t:common type="text" tabindex='1024' path="settleAccountUpper" id="settleAccountUpper" key="Com.Upper" readonly="true"/>
</div>
<div id="divGeneralAllRight">
	<t:common type="select" tabindex='1025' path="repairSettleAccountStatus" id="repairSettleAccountStatus" key="Com.RepairSettleAccountStatus" items="${repairSettleAccountStatus}" itemValue="name" itemLabel="text" notNull="true"  disabled="true"/>
</div>
