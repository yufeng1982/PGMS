<div id="divGeneraDiv">
	<t:entity type="OilPetrolStation" tabindex='1010' path="petrolStation" id="petrolStation" bindModel="${bindModel}" key="Com.Contranct.Project" notNull="true" onchange="projectOnchange" disabled="${disabled}" initParameters="initParams4Project"/>
	<t:entity type="Asset" tabindex='1010' path="asset" id="asset" bindModel="${bindModel}" key="Com.RepairItem" notNull="true" onchange="asstOnchange" initParameters="initParams4Asst" disabled="${disabled}"/>
	<t:common type="select" tabindex='1010' path="breakdownReason" id="breakdownReason" key="Com.BreakdownReason" items="${breakdownReason}" itemValue="name" itemLabel="text" notNull="true" disabled="${disabled}"/>
</div>

<div id="divGeneraDiv">
	<t:common type="text" tabindex='1090' path="createdBy.realName" id="createUser" key="Com.PayItem.CreateUser" disabled="true"/>
	<t:common type="text" tabindex='1040' path="asset.brand" id="brand" bindModel="${bindModel}" key="Com.EquipmentBrand" maxlength="100" disabled="true"/>
	<t:common type="text" tabindex='1100' path="budget" id="budget" bindModel="${bindModel}" key="Com.RepairBudget" maxlength="100" disabled="${disabled}"/>
</div>

<div id="divGeneraDiv">
	<t:common type="date" tabindex='10106' path="creationDate" id="creationDate" key="Com.RequestTime" disabled="true"/>
	<t:common type="text" tabindex='1050' path="asset.specification" id="specification" bindModel="${bindModel}" key="Com.EquipmentSpecification" maxlength="100" disabled="true"/>
	<t:common type="select" tabindex='1010' path="repairType" id="repairType" key="Com.RepairType" items="${repairType}" itemValue="name" itemLabel="text" notNull="true" disabled="${disabled}"/>
</div>

<div id="divGeneraDiv">
	<t:common type="text" tabindex='1040' path="contact" id="contact" bindModel="${bindModel}" key="Com.Contact" maxlength="100" disabled="${disabled}"/>
	<t:common type="text" tabindex='1050' path="asset.vendor.name" id="vendor" bindModel="${bindModel}" key="Com.EquipmentVendor" maxlength="100" disabled="true"/>
	<t:common type="select" tabindex='1010' path="repairStatus" id="repairStatus" key="Com.Status" items="${repairStatus}" itemValue="name" itemLabel="text" notNull="true"  disabled="true"/>
</div>

<div id="divAll">
	<t:common type="textarea" tabindex='10110' path="comment" id="comment" key="Com.BreakDownComment" disabled="${disabled}"/>
</div>

<div id="images">
	<div id="image1">
		<div id="imageName1"><span>${f:getText('Com.BreakImage')}1<span style="color:red;line-height:1.2em"> (*)</span></span></div>
		<div id="imagePath1">
			<img id="wrapped1" src="${entity.imagePath1}" width="240" height="200"/>
		</div>
		<div id="imageFile1"></div>
		<input type="hidden" id="filePath1" value="${entity.imagePath1}" />
	</div>
	<div id="image2">
		<div id="imageName2"><span>${f:getText('Com.BreakImage')}2<span style="color:red;line-height:1.2em"> (*)</span></span></div>
		<div id="imagePath2">
			<img id="wrapped2" src="${entity.imagePath2}" width="240" height="200"/>
		</div>
		<div id="imageFile2"></div>
		<input type="hidden" id="filePath2" value="${entity.imagePath2}" />
	</div>
	<div id="image3">
		<div id="imageName3"><span>${f:getText('Com.BreakImage')}3<span style="color:red;line-height:1.2em"> (*)</span></span></div>
		<div id="imagePath3">
			<img id="wrapped3" src="${entity.imagePath3}" width="240" height="200"/>
		</div>
		<div id="imageFile3"></div>
		<input type="hidden" id="filePath3" value="${entity.imagePath3}" />
	</div>
</div>
		
