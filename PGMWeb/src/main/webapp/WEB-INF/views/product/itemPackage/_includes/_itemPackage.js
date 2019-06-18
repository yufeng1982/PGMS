function page_OnLoad() {
	PRes['AtLine'] = "${f:getText('Com.AtLine')}";
	PRes['Line'] = "${f:getText('Com.Line')}";
	PRes['NotEmpty'] = "${f:getText('Com.Validate.NotEmpty')}";
	PRes['Item'] = "${f:getText('Com.Item')}";
	PRes['Price'] = "${f:getText('Com.Price')}";
	PRes['Quantity'] = "${f:getText('Com.Quantity')}";
	PRes['AddKitsData'] = "${f:getText('Com.AddKitsData')}";
	PRes['AddClothesData'] = "${f:getText('Com.AddClothesData')}";
	PRes["imageUpload"] = "${f:getText('Com.ImageUpload')}";
	PRes["selectFile"] = "${f:getText('Com.SelectFile')}";
	PRes["Uploading"] = "${f:getText('Com.Uploading')}";
	PRes["UploadSuccess"] = "${f:getText('Com.UploadSuccess')}";
	PRes["UploadFailed"] = "${f:getText('Com.UploadFailed')}";
	PRes["VImagePath"] = "${f:getText('Com.VImagePath')}";
	PRes["VUploadFileType"] = "${f:getText('Com.VUploadFileType')}";
	PRes["VFileSize"] = "${f:getText('Com.VFileSize')}";
	PRes["VChooseFile"] = "${f:getText('Com.VChooseFile')}";
	PRes["VRemote"] = "${f:getText('Com.VRemote')}";
	PRes["imageNote"] = "${f:getText('Com.ImageNotes')}";
	PRes["imageNoteValue"] = "${f:getText('Com.ImageWidthNote')}";
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new PGM.photo.ItemPackageAction();
	var itemPackageDetailGrid = PAction.initItemPackageLineGrid('ITEMPACKAGE_DETAIL_GRID_ID', {entityId : '${entityId}'});
	var itemPackageClothesGrid = PAction.initItemPackageClothesGrid('ITEMPACKAGE_CLOTHES_GRID_ID', {entityId : '${entityId}'},G_CLOTHES_CONFIG);
	var itemPackageImagesGrid = PAction.initItemPackageImagesGrid('ITEMPACKAGE_IMAGES_GRID_ID', {entityId : '${entityId}'},G_IMAGES_CONFIG);
	var specPanel = Ext.create('Ext.form.HtmlEditor', {
	    id : 'specPanelId',
		width: 600,
	    height: 210,
	    name : 'specification',
	    value : '${f:htmlEditorEscape(specification)}',
	    renderTo: 'divSpec'
	});
	var specialPanel = Ext.create('Ext.form.HtmlEditor', {
		 id : 'specialPanelId',
		width: 600,
	    height: 210,
	    name : 'specialSpecification',
	    value : '${f:htmlEditorEscape(specialSpecification)}',
	    renderTo: 'divSpecial'
	});
	
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 130,
				id : "GENERAL_PANEL",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				id : "ITEMPACKAGE_DETAIL_PANEL_ID",
				collapseFirst : false,
				height : 220,
				title : ss_icon('ss_application_form') + "${f:getText('Com.ItemDetail')}",
				tools: [{id : 'plus', type:'plus', qtip : PRes["add"], handler : function(event, toolEl, panel) { PAction.addLine();}}, 
				        {id : 'minus', type:'minus',qtip : PRes["delete"], handler : function(event, toolEl, panel) { PAction.removeLine();} }],
				items : [itemPackageDetailGrid]
			},{
				xtype : "portlet",
				id : "ITEMPACKAGE_CLOTHES_PANEL_ID",
				collapseFirst : false,
				height : 220,
				title : ss_icon('ss_application_form') + "${f:getText('Com.Clothes.List')}",
				tools: [{id : 'cplus', type:'plus', qtip : PRes["add"], handler : function(event, toolEl, panel) { PAction.addClothesLine();}}, 
				        {id : 'cminus', type:'minus', qtip : PRes["delete"], handler : function(event, toolEl, panel) { PAction.removeClothesLine();} }],
				items : [itemPackageClothesGrid]
			},{
				xtype : "portlet",
				id : "ITEMPACKAGE_IMAGES_PANEL_ID",
				collapseFirst : false,
				height : 220,
				title : ss_icon('ss_application_form') + "${f:getText('Com.Images.List')}",
				tools: [{id : 'iplus', type:'plus', qtip : PRes["add"], handler : function(event, toolEl, panel) { PAction.addImagesLine();}}, 
				        {id : 'iminus', type:'minus', qtip : PRes["delete"], handler : function(event, toolEl, panel) { PAction.removeImagesLine();} }],
				items : [itemPackageImagesGrid]
			},{
				xtype : "portlet",
				id : "ITEMPACKAGE_DESCRIPTION_PANEL_ID",
				collapseFirst : false,
				height : 250,
				title : ss_icon('ss_application_form') + "${f:getText('Com.Specification.List')}",
				layout : 'hbox',
				items : [{
			        xtype:'fieldset',
			        title: "${f:getText('Com.Specification.List.Spec')}",
			        height : 210,
			        layout: 'fit',
			        items :[specPanel]
			    },{
			        xtype:'fieldset',
			        title: "${f:getText('Com.Specification.List.Special')}",
			        height : 210,
			        layout: 'fit',
			        margin : '0 0 0 10',
			        items :[specialPanel]
			    }]
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
}