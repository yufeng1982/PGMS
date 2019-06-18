<div id="divAll">
	<t:common type="textarea" tabindex='10110' path="opinions1" id="opinions1" key="Com.Opinion" disabled="${taskCode ne 'G000002'}"/>
</div>
<div id="divAmounts">
	<div id="divAmounts1" class="divAmounts">
		<t:common type="text" tabindex='1090' path="currentBudget" id="currentBudget" key="Com.CurrentBudget" notNull="${taskCode eq 'G000002'}" disabled="${taskCode ne 'G000002'}" onchange="currentBudgetOnchange"/>
	</div>
	<div id="divAmounts2" class="divAmounts">
		<t:common type="text" tabindex='1090' path="repairTime" id="repairTime" key="Com.RepairTime" notNull="${taskCode eq 'G000002'}" disabled="${taskCode ne 'G000002'}"/>
	</div>
	<div id="divAmounts3" class="divAmounts">
		<t:entity type="YearBudgets" tabindex='1000' path="yearBudgets" id="yearBudgets" bindModel="${bindModel}" key="Com.BudgetApproveContents" disabled="${taskCode ne 'G000002'}"/>
	</div>
</div>

<div id="divHours">
	<div id="divHours2">
		<div class="bl"><span>${f:getText('Com.ReapirBudgetFile')}<c:if test="${taskCode eq 'G000002'}"><span style="color:red;line-height:1.2em"> (*)</span></c:if></span></div><div id="file7" class="file"></div><div class="downLoad"><c:if test="${!empty entity.repairSettleAccountFilePath}"><span><a href="/app/pgm/project/repairOrder/form/${entityId}/downLoad?index=7">${f:getFileName(entity.repairSettleAccountFilePath)}</a></span></c:if></div>
	</div>
	<div id="divHours3">
		<div class="bl"><span>${f:getText('Com.RepairSolutionsFile')}</span></div><div id="file8" class="file"></div><div class="downLoad"><c:if test="${!empty entity.repairSolutionsFilePath}"><span><a href="/app/pgm/project/repairOrder/form/${entityId}/downLoad?index=8">${f:getFileName(entity.repairSolutionsFilePath)}</a></span></c:if></div>
	</div>
</div>

<div id="divAllSolutions">
	<t:common type="textarea" tabindex='10110' path="repairSolutions" id="repairSolutions" key="Com.RepairSolutions" disabled="${taskCode ne 'G000002'}"/>
</div>

<div id="divOperationAllow">
	<div id="divOperationAllow1">
		<t:common type="checkbox" tabindex='1010' path="lgyxk" id="lgyxk" key="Com.Lgyxk" disabled="${taskCode ne 'G000002'}"/>
	</div>
	<div id="divOperationAllow2">
		<t:common type="checkbox" tabindex='1010' path="rgyxk" id="rgyxk" key="Com.Rgyxk" disabled="${taskCode ne 'G000002'}"/>
	</div>
	<div id="divOperationAllow3">
		<t:common type="checkbox" tabindex='1010' path="dqxk" id="dqxk" key="Com.Dqxk" disabled="${taskCode ne 'G000002'}"/>
	</div>
	<div id="divOperationAllow4">
		<t:common type="checkbox" tabindex='1010' path="dgxk" id="dgxk" key="Com.Dgxk" disabled="${taskCode ne 'G000002'}"/>
	</div>
	<div id="divOperationAllow5">
		<t:common type="checkbox" tabindex='1010' path="sxxk" id="sxxk" key="Com.Sxxk" disabled="${taskCode ne 'G000002'}"/>
	</div>
</div>

		
