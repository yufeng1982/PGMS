<div class= "divGeneral1001" id="divGeneral100">
	<t:common type="text" tabindex='5000' path="tdxz" id="tdxz" key="Com.Tdxz"/>
	<t:common type="select" tabindex='1040' path="ynYzhgzs" id="ynYzhgzs" key="Com.Yzhgzs" items="${yesNoType}" itemValue="name" itemLabel="text"  width="130"/>
	<t:common type="select" tabindex='1040' path="ynJchgz" id="ynJchgz" key="Com.Jchgz" items="${yesNoType}" itemValue="name" itemLabel="text"  width="130"/>
	<t:common type="select" tabindex='1040' path="ynJyxkz" id="ynJyxkz" key="Com.Jyxkz" items="${yesNoType}" itemValue="name" itemLabel="text"  width="130"/>
</div>
<div class= "divGeneral1002" id="divGeneral101">
	<t:common type="select" tabindex='1040' path="ynTdz" id="ynTdz" key="Com.Tdz" items="${yesNoType}" itemValue="name" itemLabel="text"  width="130"/>
	<t:common type="text" tabindex='5006' path="zsbh" id="zsbh" key="Com.Zsbh"/>
	<t:common type="text" tabindex='5013' path="hgzbh" id="hgzbh" key="Com.Hgzbh"/>
	<t:common type="text" tabindex='5009' path="xkzbh" id="xkzbh" key="Com.Xkzbh"/>
</div>
<div class= "divGeneral1002" id="divGeneral102">
	<t:common type="select" tabindex='1040' path="ynYdhx" id="ynYdhx" key="Com.Ydhx" items="${yesNoType}" itemValue="name" itemLabel="text"  width="130"/>
	<t:common type="date" tabindex='5007' path="releaseDate" id="releaseDate" key="Com.ApproveDate" width="130"/>
	<t:common type="date" tabindex='5014' path="yxqFrom" id="yxqFrom" key="Com.YxqFrom" width="130"/>
	<t:common type="date" tabindex='5015' path="yxqTo" id="yxqTo" key="Com.YxqTo" width="130"/>
</div>

<div class= "divGeneral1001" id="divGeneral103">
	<t:common type="text" tabindex='5010' path="xkzzl" id="xkzzl" key="Com.Xkzzl"/>
	<t:common type="text" tabindex='5003' path="jzkzxtydhxjl" id="jzkzxtydhxjl" key="Com.Jzkzxtydhxjl"/>
</div>

<div class= "divGeneral1001" id="divGeneral104">
	<t:common type="text" tabindex='5011' path="xkfw" id="xkfw" key="Com.Xkfw" />
	<t:common type="text" tabindex='5004' path="dlkzx" id="dlkzx" key="Com.Dlkzx"/>
</div>

<div id="attachmentDiv">
	<div class="divAll">
		<div class="bl"><span>${f:getText('Com.FileName1')}</span></div><div id="file1" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath1}"><span><a href="/app/pgm/project/project/form/${entityId}/downLoad?index=1">${f:getFileName(entity.filePath1)}</a></span></c:if></div>
	</div>	
	<div class="divAll">
		<div class="bl"><span>${f:getText('Com.FileName2')}</span></div><div id="file2" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath2}"><span><a href="/app/pgm/project/project/form/${entityId}/downLoad?index=2">${f:getFileName(entity.filePath2)}</a></span></c:if></div>
	</div>	
	<div class="divAll">
		<div class="bl"><span>${f:getText('Com.FileName3')}</span></div><div id="file3" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath3}"><span><a href="/app/pgm/project/project/form/${entityId}/downLoad?index=3">${f:getFileName(entity.filePath3)}</a></span></c:if></div>
	</div>	
	<div class="divAll">
		<div class="bl"><span>${f:getText('Com.FileName4')}</span></div><div id="file4" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath4}"><span><a href="/app/pgm/project/project/form/${entityId}/downLoad?index=4">${f:getFileName(entity.filePath4)}</a></span></c:if></div>
	</div>	
	<div class="divAll">
		<div class="bl"><span>${f:getText('Com.FileName5')}</span></div><div id="file5" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath5}"><span><a href="/app/pgm/project/project/form/${entityId}/downLoad?index=5">${f:getFileName(entity.filePath5)}</a></span></c:if></div>
	</div>
	<div class="divAll">
		<div class="bl"><span>${f:getText('Com.FileName6')}</span></div><div id="file6" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath6}"><span><a href="/app/pgm/project/project/form/${entityId}/downLoad?index=6">${f:getFileName(entity.filePath6)}</a></span></c:if></div>
	</div>	
	<div class="divAll">
		<div class="bl"><span>${f:getText('Com.FileName7')}</span></div><div id="file7" class="file"></div><div class="downLoad"><c:if test="${!empty entity.filePath7}"><span><a href="/app/pgm/project/project/form/${entityId}/downLoad?index=7">${f:getFileName(entity.filePath7)}</a></span></c:if></div>
	</div>
	<input type="hidden" id="filePath1" value="${entity.filePath1}" />
	<input type="hidden" id="filePath2" value="${entity.filePath2}" />
	<input type="hidden" id="filePath3" value="${entity.filePath3}" />
	<input type="hidden" id="filePath4" value="${entity.filePath4}" />
	<input type="hidden" id="filePath5" value="${entity.filePath5}" />
	<input type="hidden" id="filePath6" value="${entity.filePath6}" />
	<input type="hidden" id="filePath7" value="${entity.filePath7}" />
</div>

<div id="oilOtherDes"></div>

<div id="oilOtherSm">
	
</div>