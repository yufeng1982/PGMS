<div id="divGeneralLeft">
	<t:common type="text" tabindex='10000' path="sequence" id="sequence" key="Com.Code" disabled = "true"/>
	<t:common type="text" tabindex='10100' path="name" id="name" key="Com.Name" notNull="true" />
	<t:common type="text" tabindex='10200' path="price" id="price" key="Com.Price" notNull="true" />
	<t:common type="checkbox" tabindex='10300' path="enabled" id="enabled" key="Com.Enabled" />
	<t:common type="textarea" tabindex='10400' path="description" id="description" key="Com.Description" notNull="true" rows="10"/>
	
</div>
<div id="divGeneralCenter">
	<form:hidden path="imagePath" id="imagePath" value="${entity.imagePath}"/>
</div>

<div id="divGeneralRight">
	<img id="wrapped" src="${entity.imagePath}" width="300" height="300"/>
	<div id='imgBtn'></div>
</div>