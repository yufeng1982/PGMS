<div id="divGeneralLeft">
	<t:common type="text" tabindex='1000' path="code" id="code" key="Com.EquipmentCode" maxlength="100" disabled="true"/>
	<t:common type="text" tabindex='1030' path="assetsCategory.name" id="_assetsCategory" bindModel="${bindModel}" key="Com.Contranct.AssetsCategory" onfocus="showAc" notNull="true"/>
	<input type="hidden" name="assetsCategory" id="assetsCategory" value="${entity.assetsCategory.id}">
	<t:common type="text" tabindex='1060' path="quantity" id="quantity" bindModel="${bindModel}" key="Com.Quantity" notNull="true" maxlength="10"/>
	<t:entity type="Cooperation" tabindex='1090' path="vendor" id="vendor"  key="Com.Vendor" bindModel="${bindModel}" />
	<t:maintenance type="Department" tabindex='10104' path="department" id="department" key="Com.Department" bindModel="${bindModel}"/>
</div>

<div id="divGeneralCenter">
	<t:entity type="OilPetrolStation" tabindex='1010' path="petrolStation" id="petrolStation" bindModel="${bindModel}" key="Com.Contranct.Project" notNull="true" initParameters="initParams4Project" onchange="projectOnchange"/>
	<t:common type="text" tabindex='1040' path="brand" id="brand" bindModel="${bindModel}" key="Com.Brand" maxlength="100"/>
	<t:common type="date" tabindex='1070' path="qualityPeriod" id="qualityPeriod" bindModel="${bindModel}" key="Com.QualityPeriod" notNull="true" />
	<t:common type="text" tabindex='1100' path="sapcode1" id="sapcode1" bindModel="${bindModel}" key="Com.Sapcode1" maxlength="100"/>
</div>

<div id="divGeneralRight">
	<t:entity type="Contract" tabindex='1020' path="contract" id="contract" bindModel="${bindModel}" key="Com.Contranct.Name" initParameters="initParams4Contract" onchange="contractOnchange"/>
	<t:common type="text" tabindex='1050' path="specification" id="specification" bindModel="${bindModel}" key="Com.Specification" maxlength="100"/>
	<t:common type="text" tabindex='1080' path="description" id="description" bindModel="${bindModel}" key="Com.CommentDescription" maxlength="200"/>
	<t:common type="text" tabindex='1110' path="sapcode2" id="sapcode2" bindModel="${bindModel}" key="Com.Sapcode2" maxlength="100"/>
</div>


