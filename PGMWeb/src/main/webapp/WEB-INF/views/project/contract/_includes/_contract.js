function page_OnLoad() {
	PRes["File"] = "${f:getText('Com.File')}";
	PRes["VAttachment"] = "${f:getText('Com.Validation.Attachment.NotNull')}";
	PRes["VArchive"] = "${f:getText('Com.Validation.Archive.NotNull')}";
	PRes["VGreaterThanZero"] = "${f:getText('Com.Validation.GreaterThanZero')}";
	PRes["Amount"] = "${f:getText('Com.Contranct.Amount')}";
	PRes["OnePercent"] = "${f:getText('Com.Contranct.OnePercent')}";
	PRes["TwoPercent"] = "${f:getText('Com.Contranct.TwoPercent')}";
	PRes["ThreePercent"] = "${f:getText('Com.Contranct.ThreePercent')}";
	PRes["FourPercent"] = "${f:getText('Com.Contranct.FourPercent')}";
	PRes["VTotalPercent"] = "${f:getText('Com.Validation.TotalPrecent.Equal100')}";
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.PDFAttachmentType')}";
	PRes["ContranctAttachment"] = "${f:getText('Com.Contranct.Attachment')}";
	PRes["ContranctArchive"] = "${f:getText('Com.Contranct.Archive')}";
	PRes["PendingSent"] = "${f:getText('Com.PendingSend')}";
	PRes["Reject"] = "${f:getText('Com.Reject')}";
	PRes["Archive"] = "${f:getText('Com.ArchiveFile')}";
	PRes["VOwnerContract"] = "${f:getText('Com.Validation.OwnerContract.NotNull')}";
	PRes["ApproveOpinion"] = "${f:getText('Com.ApproveOpinion')}";
	PRes["QualityGuaranteePeriod"] = "${f:getText('Com.Contranct.QualityGuaranteePeriod')}";
	PRes["VPositiveInteger"] = "${f:getText('Com.Validation.PositiveInteger')}";
	PRes["VNumber"] = "${f:getText('Com.Validate.VNumber')}";
	Ext.create('Ext.form.field.File', {
		name: 'attachment',
		id : 'attachment',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'archive',
		id : 'archive',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file1',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"],
        disabled : true
	});
	
	var actionBarItems = [];
	var pendingSend = new ERP.Button({
		id : 'pendingSendBtn',
		text : PRes["PendingSent"],
		iconCls: 'ss_sprite ss_css_go',
		hidden : CUtils.isTrueVal('${isHiddePendingBtn}'),
		handler: function() {
			beginWaitCursor(PRes["Saving"], false);
			PAction.submitForm("pendingSend");
		}
	});
	var archive = new ERP.Button({
		id : 'archiveBtn',
		text : PRes["Archive"],
		iconCls: 'ss_sprite ss_folder_page',
		handler: function() {
			var msgarray = [];
			var attachment = Ext.getCmp('archive').getValue();
			if (Strings.isEmpty(attachment)) {
				msgarray.push({fieldname:"archive", message: PRes["VArchive"], arg:null});
			} else {
				if (!VUtils.validatePDFType(attachment)) {
					msgarray.push({fieldname:"archive", message: PRes["ContranctArchive"]+PRes["VAttachmentType"], arg:null});
				} else {
//					beginWaitCursor(PRes["Saving"], false);
//					PAction.submitForm("archive");
					PAction.showOpinionWin("archive");
				}
			}
			if (msgarray.length > 0) {
				VUtils.processValidateMessages(msgarray);
			}
			
		}
	});
	
	if('${entity.contractStatus}' == 'PendingSend') actionBarItems[52] = pendingSend;
	if('${entity.contractStatus}' == 'PendingArchive') actionBarItems[53] = archive;
	if (CUtils.isTrueVal('${isShowFlowBtn}')) {
		var approve = new Ext.Action( {
			text : '${taskName}',
			iconCls: 'ss_sprite ss_application_edit',
			handler: function() {
				PAction.showOpinionWin("${taskId}/approve");
			}
		});
		var reject = new Ext.Action( {
			text : PRes["Reject"],
			iconCls: 'ss_sprite ss_cancel',
			handler: function() {
				PAction.showOpinionWin("${taskId}/reject");
			}
		});
		actionBarItems[54] = new ERP.Button(approve);
		actionBarItems[55] = new ERP.Button(reject);
	}
	var actionBar = new ERP.FormActionBar(actionBarItems);
	var resultGrid = GUtils.initErpGrid(GRID_ID, {entityId : $('entityId').value});
	PAction = new EMP.project.contract.ContractAction();
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 320,
				id : "general-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				id : "RESULT_PANEL_ID",
				collapseFirst : false,
				height : 280,
				layout : 'fit',
				title : "${f:getText('Com.ApporveResult')}",
				items : [resultGrid]
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
	
	if (!Strings.isEmpty('${infos}')) {
		CUtils.warningAlert('${infos}');
	}
	
	if (CUtils.getSValue('contractType') == 'Main') {
		CUtils.disableCmp('ownerContract', true);
		CUtils.setSSValue('ownerContract','','')
	} else {
		if (CUtils.isTrueVal('${isDisabled}')) {
			CUtils.disableCmp('ownerContract', true);
		} else {
			CUtils.disableCmp('ownerContract', false);
		}
		
	}
	if ('${entity.contractStatus}' == 'PendingSend' || '${entity.contractStatus}' == 'Draft') {
		CUtils.disableCmp('flowDefinition', false);
	} else {
		CUtils.disableCmp('flowDefinition', true);
	}
	CUtils.disableCmp('attachment', CUtils.isTrueVal('${isDisabled}'));
	CUtils.disableCmp('patCondition', CUtils.isTrueVal('${isDisabled}'));
	CUtils.disableCmp('payContents', CUtils.isTrueVal('${isDisabled}'));
	CUtils.disableCmp('okBtn', CUtils.isTrueVal('${isHiddePendingBtn}') ||  CUtils.isTrueVal('${isDisabled}'));
	CUtils.disableCmp('applyBtn', CUtils.isTrueVal('${isHiddePendingBtn}') || CUtils.isTrueVal('${isDisabled}'));
	if ('${entity.contractStatus}' == 'PendingArchive') {
		CUtils.disableCmp('archive', false);
	}
	
}