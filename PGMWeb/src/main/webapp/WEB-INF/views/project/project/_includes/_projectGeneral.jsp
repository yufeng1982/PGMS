<div class="divGeneral1" id="divGeneral1">
	<t:common type="text" tabindex='1000' path="code" id="code" key="Com.ProjectCode" maxlength="50" disabled="true"/>
</div>

<div class="divGeneral1" id="divGeneral2">
	<t:common type="text" tabindex='1002' path="name" id="name" key="Com.ProjectName" maxlength="50" notNull="true" />
</div>

<div class="divGeneral1" id="divGeneral3">
	<t:common type="date" tabindex='1004' path="addDate" id="addDate" key="Com.Project.AddDate" notNull="true" disabled="${entityId ne 'NEW'}"  width="130"/>
</div>

<div class="divGeneral1" id="divGeneral4">
	<t:common type="checkbox" tabindex='1005' path="enabled" id="enabled" key="Com.Enabled"/>
</div>

<div class="divGeneral11" id="divGeneral21">

	<div class="divGeneral1" id="divGeneral11_1">
		<t:entity type="Employee" tabindex='1001' path="employee" id="employee" bindModel="${bindModel}" key="Com.DevResponsible" notNull="true" width="130"/>
		<t:common type="select" tabindex='1040' path="oilLevel" id="oilLevel" key="Com.OilLevel" items="${oilLevel}" itemValue="name" itemLabel="text" notNull="true" width="130"/>
	</div>
	
	<div class="divGeneral1" id="divGeneral11_2">
		<t:common type="text" tabindex='1003' path="phone" id="phone" key="Com.ContractPhone" maxlength="50" notNull="true" />
		<t:common type="text" tabindex='1014' path="carRoadCounts" id="carRoadCounts" key="Com.CarRoadCounts" maxlength="5" notNull="true"/>
	</div>


	<div class="divGeneral11_2" id="divGeneral11_3">
		<t:common type="text" tabindex='1006' path="pct"  id="pct" key="Com.City" maxlength="50" notNull="true" onfocus="showPct" />
		<form:hidden path="province" id="province"/>
		<form:hidden path="city" id="city"/>
		<form:hidden path="town" id="town"/>
		<t:common type="text" tabindex='1008' path="address" id="address" key="Com.ProjectAddress" notNull="true" />
	</div>

</div>

<div class="divGeneral1" id="divGeneral31">
	<t:common type="select" tabindex='1040' path="ywgld" id="ywgld" key="Com.Barrier" items="${ywgld}" itemValue="name" itemLabel="text" notNull="true" width="130"/>
</div>

<div class="divGeneral1" id="divGeneral32">
	<t:common type="select" tabindex='1040' path="hzWay" id="hzWay" key="Com.HzWay" items="${hzWay}" itemValue="name" itemLabel="text" notNull="true" width="130"/>
	
</div>

<div class="divGeneral1" id="divGeneral33">
	<div id='divGeneral33_1'>
		<t:common type="text" tabindex='1017' path="qiyouP" id="qiyouP" key="Com.QiChaiYouP" notNull="true" />
	</div>
	<div id='divGeneral33_3'>
		<label for="qcP" id="cqP" class="fieldRowLabel">/</label>
		<t:common type="text" tabindex='1018' path="caiyouP" id="caiyouP" notNull="true" />
	</div>
</div>

<div class="divGeneral1" id="divGeneral34">
	<t:common type="text" tabindex='1009' path="salesForecast"  id="salesForecast" key="Com.SalesForecast" maxlength="50" notNull="true" />
</div>