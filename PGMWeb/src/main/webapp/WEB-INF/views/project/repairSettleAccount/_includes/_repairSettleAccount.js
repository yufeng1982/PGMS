function page_OnLoad() {
	PRes["File"] = "${f:getText('Com.File')}";
	PRes["Image"] = "${f:getText('Com.BreakImage')}";
	PRes["AttachmentNotNull"] = "${f:getText('Com.AttachmentNotNull')}";
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.AttachmentType')}";
	PRes["RepairBudget"] = "${f:getText('Com.RepairBudget')}";
	PRes["VNumber"] = "${f:getText('Com.Validate.VNumber')}";
	PRes["ChangeAmount"] = "${f:getText('Com.ChangeAmount')}";
	PRes["HseAmount"] = "${f:getText('Com.HseAmount')}";
	PRes["Submit"] = "${f:getText('Com.SubmitRequest')}";
	PRes["Reject"] = "${f:getText('Com.Reject')}";
	PRes["ShowHistory"] = "${f:getText('Com.ApproveRecords')}";
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.PDFAttachmentType')}";
	PRes["ChangeFile"] = "${f:getText('Com.ChangeFile')}";
	PRes["HSEFile"] = "${f:getText('Com.HSEFile')}";
	
	Ext.create('Ext.form.field.File', {
		name: 'archive1',
		id : 'archive1',
        height : 22,
		width : 160,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file1',
        disabled : CUtils.isTrueVal("${disabled}"),
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	Ext.create('Ext.form.field.File', {
		name: 'archive2',
		id : 'archive2',
        height : 22,
		width : 160,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file2',
        disabled : CUtils.isTrueVal("${disabled}"),
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	var actionBarItems = [];
	
	var pendingSend = new ERP.Button({
		id : 'pendingSendBtn',
		text : PRes["Submit"],
		iconCls: 'ss_sprite ss_css_go',
		hidden : CUtils.isTrueVal('${isHiddePendingBtn}'),
		disabled : CUtils.isTrueVal("${disabled}"),
		handler: function() {
			beginWaitCursor(PRes["Saving"], false);
			PAction.submitForm("send");
		}
	});
	
	var history = new ERP.Button({
		id : 'showHistoryBtn',
		text : PRes["ShowHistory"],
		iconCls: 'ss_sprite ss_page_edit',
		handler: function() {
			PAction.showHistory();
		}
	});
	actionBarItems[56] = history;
	if('${entity.repairSettleAccountStatus}' == 'Draft') actionBarItems[52] = pendingSend;
	if (CUtils.isTrueVal('${isShowFlowBtn}')) {
			var approve = new Ext.Action( {
				text : '${taskName}',
				iconCls: 'ss_sprite ss_application_edit',
				handler: function() {
					PAction.approve("${taskId}/approve");
				}
			});
			var reject = new Ext.Action( {
				text : PRes["Reject"],
				iconCls: 'ss_sprite ss_cancel',
				handler: function() {
					PAction.approve("${taskId}/reject");
				}
			});
			actionBarItems[54] = new ERP.Button(approve);
			actionBarItems[55] = new ERP.Button(reject);
	}
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.repairSettleAccount.RepairSettleAccountAction();
	var bodyItems = [];
	bodyItems.push({
		xtype : "portlet",
		height : 200,
		id : "general-portal",
		title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
		contentEl : "divGeneral"
	});
	if (CUtils.isTrueVal('${entityId}' != 'NEW')) {
		bodyItems.push({
			xtype : "portlet",
			height : 120,
			id : "samll-portal-wxgcs",
			title : ss_icon('ss_application_form') + "${f:getText('Com.WxgcsAO')}",
			contentEl : "divWxgcsyj"
		},{
			xtype : "portlet",
			height : 120,
			id : "samll-portal-hse",
			title : ss_icon('ss_application_form') + "${f:getText('Com.HseAO')}",
			contentEl : "divHseyj"
		},{
			xtype : "portlet",
			height : 120,
			id : "sportal-bmjl",
			title : ss_icon('ss_application_form') + "${f:getText('Com.BmjlAO')}",
			contentEl : "divBmjlyj"
		},{
			xtype : "portlet",
			height : 120,
			id : "portal-fgfz",
			title : ss_icon('ss_application_form') + "${f:getText('Com.FgfzAO')}",
			contentEl : "divFgfzyj"
		},{
			xtype : "portlet",
			height : 120,
			id : "portal-zjl",
			title : ss_icon('ss_application_form') + "${f:getText('Com.ZjlAO')}",
			contentEl : "divZjlyj"
		});
	}
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : bodyItems
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
	if (CUtils.isTrueVal('${entity.repairSettleAccountStatus}' == 'Draft')) {
		if ($('hseAmount').value != 0) {
			Ext.getCmp('archive2').setDisabled(false);
		} else {
			Ext.getCmp('archive2').setDisabled(true);
		}
		if ($('changeAmount').value != 0) {
			Ext.getCmp('archive1').setDisabled(false);
		} else {
			Ext.getCmp('archive1').setDisabled(true);
		}
	}
	CUtils.disableCmp('okBtn', CUtils.isTrueVal("${disabled}"));
	CUtils.disableCmp('applyBtn',CUtils.isTrueVal("${disabled}"));
}