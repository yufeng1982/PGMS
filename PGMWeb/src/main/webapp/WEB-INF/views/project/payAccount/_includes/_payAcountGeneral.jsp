<div id="divGeneralLeft">
	<t:entity type="PetrolStation" tabindex='1000' path="contract.petrolStation" id="petrolStation" bindModel="${bindModel}" key="Com.Contranct.Project" disabled="true"/>
	<t:common type="text" tabindex='10010' path="contract.code" id="contractCode" key="Com.Contranct.Code" disabled="true"/>
	<t:common type="text" tabindex='10020' path="contract.amount" id="contractAmount" key="Com.Contranct.Amount"  disabled="true"/>
	<t:common type="text" tabindex='10030' path="contract.payAmount" id="contractPayAmount" key="Com.Contranct.PayAmount"  disabled="true"/>
	<t:common type="text" tabindex='10040' path="contract.contractAssetNo" id="assetNo" key="Com.PayItem.AssetNo" disabled="true"/>
</div>

<div id="divGeneralCenter">
	<t:entity type="Contract" tabindex='10040' path="contract" id="contract" bindModel="${bindModel}" key="Com.Contranct.Name" disabled="true"/>
	<t:entity type="Cooperation" tabindex='10040' path="contract.cooperation" id="cooperation" bindModel="${bindModel}" key="Com.Contranct.Cooperation" disabled="true" />
	<t:common type="text" tabindex='10061' id="settleAccounts" key="Com.Contranct.SettleAccounts"  disabled="true" value="${settleAccounts}"/>
	<t:common type="text" tabindex='10062' id="finishPercent" key="Com.Contranct.FinishPercent"  disabled="true" value="${finishPercent}"/>
	<t:common type="text" tabindex='10063' path="contract.adjustAmount" id="adjustAmount" key="Com.Contranct.AdjustAmount"  disabled="true"/>
</div>
