<div id="divGeneralLeft">
	<t:common type="text" tabindex='1010' path="code" id="code" key="Com.Code" maxlength="100" notNull="true" onchange="checkComponentUnique"/>
	<t:common type="text" tabindex='1011' path="name" id="name" key="Com.Name" maxlength="100" notNull="true" />
	<t:common type="text" tabindex='1012' path="parent.name" id="_parent" bindModel="${bindModel}" key="Com.AssetCategoryParent" onfocus="showAc" />
	<input type="hidden" name="parent" id="parent" value="${entity.parent.id}">
	<input type="hidden" name="oldParent" id="oldParent" value="${entity.parent.id}">
	<input type="hidden" name="oldName" id="oldName" value="${entity.name}">
	<input type="hidden" name="oldId" id="oldId" value="${entity.id}">
</div>

<div id="divGeneralCenter">
	
</div>

<div id="divGeneralRight">
	
</div>


