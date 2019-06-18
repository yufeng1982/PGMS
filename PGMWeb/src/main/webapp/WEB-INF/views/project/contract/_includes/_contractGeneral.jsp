<div id="divGeneralLeft">
	<t:common type="text" tabindex='1000' path="name" id="name" key="Com.Contranct.Name" maxlength="50" notNull="true" disabled="${isDisabled}"/>
	<t:common type="select" tabindex='1010' path="contractType" id="contractType" key="Com.Contranct.Property" items="${contractPropertys}" itemValue="name" itemLabel="text" notNull="true" onchange="contractPropertyOnchange" disabled="${isDisabled}"/>
	<t:common type="select" tabindex='1020' value="${entity.contractCategory.id}" path="contractCategory" id="contractCategory" key="Com.Contranct.ContractCategory" items="${contractCategorys}" itemValue="id" itemLabel="displayString" notNull="true" disabled="${isDisabled}" onchange="contractCategoryOnchange"/>
	<t:entity type="Cooperation" tabindex='1030' path="cooperation" id="cooperation"  notNull="true" key="Com.Contranct.Cooperation" bindModel="${bindModel}" onchange="cooperationOnchange" disabled="${isDisabled}"/>
	<t:common type="select" tabindex='1040' value="${entity.ourSideCorporation.id}" path="ourSideCorporation" id="ourSideCorporation" key="Com.Contranct.OurSideCorporation" items="${ourSideCorporations}" itemValue="id" itemLabel="name" notNull="true" />
</div>

<div id="divGeneralCenter1">
	<t:entity type="PetrolStation" tabindex='1050' path="petrolStation" id="petrolStation" bindModel="${bindModel}" key="Com.Contranct.Project" disabled="${entityId ne 'NEW'}" notNull="true" onchange="projectOnchange"/>
	<t:entity type="Contract" tabindex='1060' path="ownerContract" id="ownerContract" bindModel="${bindModel}" key="Com.Contranct.Owner" disabled="true" initParameters="initParams4OwnerContract"/>
	<t:common type="text" tabindex='1070' path="assetsCategory.name" id="_assetsCategory" bindModel="${bindModel}" key="Com.Contranct.AssetsCategory" notNull="true" onfocus="showAc" />
	<input type="hidden" name="assetsCategory" id="assetsCategory" value="${entity.assetsCategory.id}">
	<t:entity type="CooperationAccount" tabindex='1080' path="cooperationAccount" id="receiveNo" key="Com.Contranct.ReceiveNo" notNull="true" bindModel="${bindModel}" onchange="cooperationAccountOnchange" initParameters="initParams4CooperationAccount" disabled="${isDisabled}"/>
	<t:common type="text" tabindex='1090' path="createdBy.realName" id="createUser" key="Com.Contranct.CreateUser" disabled="true"/>
</div>

<div id="divGeneralCenter2">
	<t:common type="text" tabindex='10100' path="signingReason" id="signingReason" key="Com.SigningReason" maxlength="50" notNull="true" disabled="${isDisabled}" onchange="signingReasonOnchange"/>
	<t:common type="text" tabindex='10101' path="code" id="code" key="Com.Contranct.Code" maxlength="50" notNull="true" disabled="${isEdit}"/>
	<t:common type="select" tabindex='10102' path="contractStatus" id="contractStatus" key="Com.Contranct.Status" items="${contractStatus}" itemValue="name" itemLabel="text" disabled="true"/>
	<t:common type="text" tabindex='10103' path="cooperationAccount.bank" id="bank" key="Com.Bank" maxlength="50" notNull="true" disabled="true"/>
	<t:maintenance type="Department" tabindex='10104' path="department" id="department" key="Com.Contranct.Department" bindModel="${bindModel}" disabled="true"/>
</div>
<div id="divGeneralRight">
	<t:common type="select" tabindex='10105' value="${entity.costCenter.id}" path="costCenter" id="costCenter" key="Com.Contranct.CostCenter" items="${costCenters}" itemValue="id" itemLabel="name" notNull="true" disabled="${isDisabled}"/>
	<t:common type="date" tabindex='10106' path="creationDate" id="creationDate" key="Com.Contranct.SigningDate" disabled="${isDisabled}"/>
	<t:common type="text" tabindex='10107' path="qualityGuaranteePeriod" id="qualityGuaranteePeriod" key="Com.Contranct.QualityGuaranteePeriod" maxlength="10" notNull="true" disabled="${isDisabled}"/>
	<t:common type="text" tabindex='10108' path="amount" id="amount" key="Com.Contranct.Amount" maxlength="20" notNull="true" disabled="${isDisabled}"/>
	<t:common type="text" tabindex='10109' path="onePercent" id="onePercent" key="Com.Contranct.OnePercent" maxlength="10" notNull="true" disabled="${isDisabled}"/>
</div>
<div id="divTextAray1">
	<t:common type="textarea" tabindex='10110' path="payCondition" id="payCondition" key="Com.Contranct.PayCondition" disabled="${isDisabled}"/>
</div>
<div id="divGeneralRight2">
	<t:common type="text" tabindex='10111' path="twoPercent" id="twoPercent" key="Com.Contranct.TwoPercent" maxlength="10" notNull="true" disabled="${isDisabled}"/>
	<t:common type="text" tabindex='10112' path="threePercent" id="threePercent" key="Com.Contranct.ThreePercent" maxlength="10" notNull="true" disabled="${isDisabled}"/>
</div>
<div id="divTextAray2">
	<t:common type="textarea" tabindex='10113' path="contents" id="contents" key="Com.Contranct.PayContents"  disabled="${isDisabled}"/>
</div>
<div id="divGeneralRight3">
	<t:common type="text" tabindex='10114' path="fourPercent" id="fourPercent" key="Com.Contranct.FourPercent" maxlength="10" notNull="true" disabled="${isDisabled}"/>
	<t:entity type="YearBudgets" tabindex='1000' path="yearBudgets" id="yearBudgets" bindModel="${bindModel}" key="Com.BudgetApproveContents" notNull="true" disabled="${isDisabled}"/>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Contranct.Attachment')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath}"><span><a href="/app/pgm/project/contract/form/${entityId}/downLoad?isArchive=false">${f:getFileName(entity.filePath)}</a></span></c:if></div>
</div>
<div id="divAll">
	<div class="bl"><span>${f:getText('Com.Contranct.Archive')}<span style="color:red;line-height:1.2em"> (*)</span></span></div><div id="file1" class="file"></div><div class="downLoad"><c:if test="${!empty entity.archiveFilePath}"><span><a href="/app/pgm/project/contract/form/${entityId}/downLoad?isArchive=true">${f:getFileName(entity.archiveFilePath)}</a></span></c:if></div>
</div>
<div id="divAll">
	<c:if test="${!empty entity.ownerContract}">
		<div class="bl"><a href="javascript:PAction.showMainContract()">${f:getText('Com.Contract.Link')}</a></div>
	</c:if>
</div>
<div id = "divPayCondition"></div>
<div id = "divPayContents"></div>
<input type="hidden" name="seqNo" id="seqNo" value="${empty entity.id ? seqNo : entity.seqNo}"/>
<input type="hidden" id="projectCode" value="${entity.petrolStation.code}"/>