function page_OnLoad() {
	PRes["File"] = "${f:getText('Com.File')}";
	PRes["VBusinessLicense"] = "${f:getText('Com.Validation.BusinessLicense.NotNull')}";
	PRes["VUploadFileType"] = "${f:getText('Com.VUploadFileType')}";
	PRes["BusinessLicense1"] = "${f:getText('Com.BusinessLicense1')}";
	PRes["BusinessLicense2"] = "${f:getText('Com.BusinessLicense2')}";
	PRes["BusinessLicense3"] = "${f:getText('Com.BusinessLicense3')}";
	PRes["BusinessLicense4"] = "${f:getText('Com.BusinessLicense4')}";
	PRes["BusinessLicense5"] = "${f:getText('Com.BusinessLicense5')}";
	PRes["BusinessLicense6"] = "${f:getText('Com.BusinessLicense6')}";
	PRes["BusinessLicense7"] = "${f:getText('Com.BusinessLicense7')}";
	PRes["VBankCardAccount"] = "${f:getText('Com.Validation.BankCardAccount')}";
	PRes["ReceiveNo"] = "${f:getText('Com.ReceiveNo')}";
	PRes["Email"] = "${f:getText('Com.Validate.Employee.Email')}";
	PRes["Phone"] = "${f:getText('Com.Validate.Employee.Phone')}";
	PRes["VAddAccount"] = "${f:getText('Com.VAddAccount')}";
	PRes["VPdfAndImage"] = "${f:getText('Com.VPdfAndImage')}";
	PRes["cooperation"] = "${f:getText('Com.Cooperation')}";
	PRes["code"] = "${f:getText('Com.Code')}";
	PRes["isExist"] = "${f:getText('Com.Validate.isExist')}";
	PRes["Uploading"] = "${f:getText('Com.Uploading')}";
	PRes["UploadFailed"] = "${f:getText('Com.UploadFailed')}";
	PRes["selectFile"] = "${f:getText('Com.SelectFile')}";
	PRes["FileUpload"] = "${f:getText('Com.FileUpload')}";
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment1',
		id : 'attachment1',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file1',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment2',
		id : 'attachment2',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file2',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment3',
		id : 'attachment3',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file3',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment4',
		id : 'attachment4',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file4',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment5',
		id : 'attachment5',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file5',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment6',
		id : 'attachment6',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file6',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.project.CooperationAction();
	var cooGrid = PAction.initEPGrid(GRID_ID, {cooperationId : $('entityId').value});
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 270,
				id : "general-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				id : "COOPERATION_PANEL_ID",
				collapseFirst : false,
				height : 265,
				layout : 'fit',
				title : ss_icon('ss_application_form') + "${f:getText('Com.CooperationAccount.List')}",
				dockedItems: [{
	                xtype: 'toolbar',
	                items: [{
	                	iconCls: 'ss_sprite ss_add',
	                    id : 'plus',
	                    text: PRes["add"],
	                    handler: function(event, toolEl, panel){
	                    	PAction.addLine();
	                    }
	                }, {
	                	iconCls: 'ss_sprite ss_delete',
	                    id : 'minus',
	                    text: PRes["delete"],
	                    handler: function(event, toolEl, panel){
	                    	 PAction.removeLine();
	                    }
	                }]
	            }],
				items : [cooGrid]
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
	var errors = '${errors}';
	if (!Strings.isEmpty(errors)) {
		CUtils.warningAlert(errors);
	}
}