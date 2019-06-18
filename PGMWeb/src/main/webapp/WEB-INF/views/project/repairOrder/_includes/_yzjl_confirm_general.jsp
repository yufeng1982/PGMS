<div id="divAll">
	<t:common type="textarea" tabindex='10110' path="opinions11" id="opinions11" key="Com.Opinion" disabled="${taskCode ne 'G000011'}"/>
</div>

<div id="images_finish">
	<div id="image1_finish">
		<div id="imageName1_finish"><span>${f:getText('Com.FinishBreakImage')}1<span style="color:red;line-height:1.2em"> (*)</span></span></div>
		<div id="imagePath1_finish">
			<img id="wrapped1_finish" src="${entity.repairedImagePath1}" width="240" height="200"/>
		</div>
		<div id="imageFile1_finish"></div>
		<input type="hidden" id="filePath1_finish" value="${entity.repairedImagePath1}" />
	</div>
	<div id="image2">
		<div id="imageName2_finish"><span>${f:getText('Com.FinishBreakImage')}2<span style="color:red;line-height:1.2em"> (*)</span></span></div>
		<div id="imagePath2_finish">
			<img id="wrapped2_finish" src="${entity.repairedImagePath2}" width="240" height="200"/>
		</div>
		<div id="imageFile2_finish"></div>
		<input type="hidden" id="filePath2_finish" value="${entity.repairedImagePath2}" />
	</div>
	<div id="image3">
		<div id="imageName3_finish"><span>${f:getText('Com.FinishBreakImage')}3<span style="color:red;line-height:1.2em"> (*)</span></span></div>
		<div id="imagePath3_finish">
			<img id="wrapped3_finish" src="${entity.repairedImagePath3}" width="240" height="200"/>
		</div>
		<div id="imageFile3_finish"></div>
		<input type="hidden" id="filePath3_finish" value="${entity.repairedImagePath3}" />
	</div>
</div>
	

		
