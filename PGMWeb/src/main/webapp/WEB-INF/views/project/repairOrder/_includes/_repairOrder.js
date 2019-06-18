function page_OnLoad() {
	PRes["File"] = "${f:getText('Com.File')}";
	PRes["Image"] = "${f:getText('Com.BreakImage')}";
	PRes["ImageFinish"] = "${f:getText('Com.BreakImageFinish')}";
	PRes["AttachmentNotNull"] = "${f:getText('Com.AttachmentNotNull')}";
	PRes["VUploadFileType"] = "${f:getText('Com.VUploadFileType')}";
	PRes["RepairBudget"] = "${f:getText('Com.RepairBudget')}";
	PRes["VNumber"] = "${f:getText('Com.Validation.GreaterThanZero')}";
	PRes["Submit"] = "${f:getText('Com.SubmitRequest')}";
	PRes["Reject"] = "${f:getText('Com.Reject')}";
	PRes["RepairBudget"] = "${f:getText('Com.ActualAmount')}";
	PRes["CurrentBudget"] = "${f:getText('Com.CurrentBudget')}";
	PRes["RepairTime"] = "${f:getText('Com.RepairTime')}";
	PRes["ReapirBudgetFile"] = "${f:getText('Com.ReapirBudgetFile')}";
	PRes["RepairSolutionsFile"] = "${f:getText('Com.RepairSolutionsFile')}";
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.PDFAttachmentType')}";
	PRes["ShowHistory"] = "${f:getText('Com.ApproveRecords')}";
	PRes["ChangeAmount"] = "${f:getText('Com.ChangeAmounts')}";
	PRes["ChangeAmountAttachment"] = "${f:getText('Com.ChangeFile')}";
	PRes["PrintExcel"] = "${f:getText('Com.PrintExcel')}";
	var actionBarItems = [];
	
	var wrapped1 = Ext.create('Ext.resizer.Resizer', {
	    target: 'wrapped1',
	    minWidth:50,
	    minHeight: 50,
	    preserveRatio: true,
        transparent:true
	});
	
	var wrapped2 = Ext.create('Ext.resizer.Resizer', {
	    target: 'wrapped2',
	    minWidth:50,
	    minHeight: 50,
	    preserveRatio: true,
        transparent:true
	});
	
	var wrapped3 = Ext.create('Ext.resizer.Resizer', {
	    target: 'wrapped3',
	    minWidth:50,
	    minHeight: 50,
	    preserveRatio: true,
        transparent:true
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'archive1',
		id : 'archive1',
        height : 22,
		width : 240,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'imageFile1',
        disabled : CUtils.isTrueVal("${disabled}"),
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'archive2',
		id : 'archive2',
        height : 22,
		width : 240,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'imageFile2',
        disabled : CUtils.isTrueVal("${disabled}"),
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	Ext.create('Ext.form.field.File', {
		name: 'archive3',
		id : 'archive3',
        height : 22,
		width : 240,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'imageFile3',
        disabled : CUtils.isTrueVal("${disabled}"),
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	if(CUtils.isTrueVal('${entityId}' != 'NEW')) {
		var wrapped1_finish = Ext.create('Ext.resizer.Resizer', {
		    target: 'wrapped1_finish',
		    minWidth:50,
		    minHeight: 50,
		    preserveRatio: true,
	        transparent:true
		});
		
		var wrapped2_finish = Ext.create('Ext.resizer.Resizer', {
		    target: 'wrapped2_finish',
		    minWidth:50,
		    minHeight: 50,
		    preserveRatio: true,
	        transparent:true
		});
		
		var wrapped3_finish = Ext.create('Ext.resizer.Resizer', {
		    target: 'wrapped3_finish',
		    minWidth:50,
		    minHeight: 50,
		    preserveRatio: true,
	        transparent:true
		});
		
		Ext.create('Ext.form.field.File', {
			name: 'archive4',
			id : 'archive4',
	        height : 22,
			width : 240,
	        msgTarget: 'side',
	        allowBlank: true,
	        anchor: '100%',
	        renderTo : 'imageFile1_finish',
	        disabled : !CUtils.isTrueVal("${taskCode eq 'S000004' || taskCode eq 'G000011'}"),
	        margin: '2 0 0 2',
	        buttonMargin : '2 0 0 2',
	        buttonText: PRes["File"]
		});
		
		Ext.create('Ext.form.field.File', {
			name: 'archive5',
			id : 'archive5',
	        height : 22,
			width : 240,
	        msgTarget: 'side',
	        allowBlank: true,
	        anchor: '100%',
	        renderTo : 'imageFile2_finish',
	        disabled : !CUtils.isTrueVal("${taskCode eq 'S000004' || taskCode eq 'G000011'}"),
	        margin: '2 0 0 2',
	        buttonMargin : '2 0 0 2',
	        buttonText: PRes["File"]
		});
		
		Ext.create('Ext.form.field.File', {
			name: 'archive6',
			id : 'archive6',
	        height : 22,
			width : 240,
	        msgTarget: 'side',
	        allowBlank: true,
	        anchor: '100%',
	        renderTo : 'imageFile3_finish',
	        disabled : !CUtils.isTrueVal("${taskCode eq 'S000004' || taskCode eq 'G000011'}"),
	        margin: '2 0 0 2',
	        buttonMargin : '2 0 0 2',
	        buttonText: PRes["File"]
		});
	}
	if (CUtils.isTrueVal('${entityId}' != 'NEW') && '${entity.repairType}' != 'Small') {
		Ext.create('Ext.form.field.File', {
			name: 'archive7',
			id : 'archive7',
	        height : 22,
			width : 160,
	        msgTarget: 'side',
	        allowBlank: true,
	        anchor: '100%',
	        renderTo : 'file7',
	        disabled : CUtils.isTrueVal("${taskCode ne 'G000002'}"),
	        margin: '2 0 0 2',
	        buttonMargin : '2 0 0 2',
	        buttonText: PRes["File"]
		});
		
		Ext.create('Ext.form.field.File', {
			name: 'archive8',
			id : 'archive8',
	        height : 22,
			width : 160,
	        msgTarget: 'side',
	        allowBlank: true,
	        anchor: '100%',
	        renderTo : 'file8',
	        disabled : CUtils.isTrueVal("${taskCode ne 'G000002'}"),
	        margin: '2 0 0 2',
	        buttonMargin : '2 0 0 2',
	        buttonText: PRes["File"]
		});
		
		Ext.create('Ext.form.field.File', {
			name: 'archive9',
			id : 'archive9',
	        height : 22,
			width : 160,
	        msgTarget: 'side',
	        allowBlank: true,
	        anchor: '100%',
	        renderTo : 'file9',
	        disabled : true,
	        margin: '2 0 0 2',
	        buttonMargin : '2 0 0 2',
	        buttonText: PRes["File"]
		});
	}
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
	if('${entity.repairStatus}' == 'Draft') actionBarItems[52] = pendingSend;
	if (CUtils.isTrueVal('${isShowFlowBtn}')) {
		if('${entity.repairType}' == 'Small') {
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
					$('actions').value = 'reject';
					PAction.approve("${taskId}/reject");
				}
			});
			actionBarItems[54] = new ERP.Button(approve);
			actionBarItems[55] = new ERP.Button(reject);
		} else {
			var approve = new Ext.Action( {
				text : '${taskName}',
				iconCls: 'ss_sprite ss_application_edit',
				handler: function() {
					PAction.approve("${taskId}/approve");
				}
			});
			if (!CUtils.isTrueVal("${taskCode eq 'G000008' || taskCode eq 'G000009' || taskCode eq 'G000010' || taskCode eq 'G000011' || taskCode eq 'G000012'}")) {
				var reject = new Ext.Action( {
					text : PRes["Reject"],
					iconCls: 'ss_sprite ss_cancel',
					handler: function() {
						$('actions').value = 'reject';
						PAction.approve("${taskId}/reject");
					}
				});
				actionBarItems[55] = new ERP.Button(reject);
			}
			actionBarItems[54] = new ERP.Button(approve);
			
		}
	}
	
	var btnPringExcel = new ERP.Button({
		id : 'excelPrintBtn',
	    text: PRes["PrintExcel"],
	    width : 100,
	    iconCls : 'ss_sprite ss_page_white_excel',
	    disabled : '${entity.repairStatus}' != 'Confirm' && '${entity.repairStatus}' != 'Adjusting' && '${entity.repairStatus}' != 'Closed',
	    handler: function() {
	    	var jparams ={
    			repairOrder : $('entityId').value
			};
	    	var viewArgs = {
   				fileName : 'RepairOrder',
   				fileType : 'pdf',
   				pageSize : 'ISO_A4',
   				hasSubReport : true,
   				jasperParams : jparams
	    	};
	    	CUtils.downLoadReportFile(viewArgs);
	    }
	});
	actionBarItems[60] = btnPringExcel;
	var actionBar = new ERP.FormActionBar(actionBarItems);
	PAction = new EMP.project.repairOrder.RepairOrderAction();
	var bodyItems = [];
	bodyItems.push({
		xtype : "portlet",
		height : 440,
		id : "general-portal",
		title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
		contentEl : "divGeneral"
	});
	if (CUtils.isTrueVal('${entityId}' != 'NEW')) {
		if('${entity.repairType}' == 'Small') {
			bodyItems.push({
				xtype : "portlet",
				height : 120,
				id : "samll-portal-wxgcs",
				hidden : CUtils.isTrueVal("${taskCode eq 'S000001'}") ? false : !CUtils.isTrueVal("${entity.tk1approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.WxgcsAO')}",
				contentEl : "divWxgcsyj"
			},{
				xtype : "portlet",
				height : 120,
				id : "samll-portal-qyjlwxjs",
				hidden : CUtils.isTrueVal("${taskCode eq 'S000002'}") ? false : !CUtils.isTrueVal("${entity.tk2approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.QyjlWxjsAO')}",
				contentEl : "divQyjlwxjsyj"
			},{
				xtype : "portlet",
				height : 120,
				id : "samll-portal-yzjlwxjsxy",
				hidden : CUtils.isTrueVal("${taskCode eq 'S000003'}") ? false : !CUtils.isTrueVal("${entity.tk3approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.QyjlWxjsXY')}",
				contentEl : "divQyjlwxjsxy"
			},{
				xtype : "portlet",
				height : 380,
				hidden : CUtils.isTrueVal("${taskCode eq 'S000004'}") ? false : !CUtils.isTrueVal("${entity.tk4approve}"),
				id : "samll-portal-yzjlqr",
				title : ss_icon('ss_application_form') + "${f:getText('Com.YzjlQR')}",
				contentEl : "divYzjlqr"
			});
		} else {
			bodyItems.push({
				xtype : "portlet",
				height : 300,
				hidden : CUtils.isTrueVal("${taskCode eq 'G000002'}") ? false : !CUtils.isTrueVal("${entity.tk2approve}"),
				id : "general-portal-wxgcs",
				title : ss_icon('ss_application_form') + "${f:getText('Com.WxgcsAO')}",
				contentEl : "divWxgcsyj"
			},{
				xtype : "portlet",
				height : 120,
				hidden : CUtils.isTrueVal("${taskCode eq 'G000003'}") ? false : !CUtils.isTrueVal("${entity.tk3approve}"),
				id : "general-portal-zjgcsyj",
				title : ss_icon('ss_application_form') + "${f:getText('Com.QyjlWxjsAO')}",
				contentEl : "divQyjlwxjsyj"
			},{
				xtype : "portlet",
				height : 120,
				hidden : CUtils.isTrueVal("${taskCode eq 'G0000041'}") ? false : !CUtils.isTrueVal("${entity.tk41approve}"),
				id : "general-portal-yyjl",
				title : ss_icon('ss_application_form') + "${f:getText('Com.YyjlAO')}",
				contentEl : "divYyjl"
			},{
				xtype : "portlet",
				height : 120,
				id : "general-portal-hseyj",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000005'}") ? false : !CUtils.isTrueVal("${entity.tk5approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.HseAO')}",
				contentEl : "divHseyj"
			},{
				xtype : "portlet",
				height : 120,
				id : "general-portal-bmjlyj",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000004'}") ? false : !CUtils.isTrueVal("${entity.tk4approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.BmjlAO')}",
				contentEl : "divBmjlyj"
			},{
				xtype : "portlet",
				height : 120,
				id : "general-portal-fgfzyj",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000006'}") ? false : !CUtils.isTrueVal("${entity.tk6approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.FgfzAO')}",
				contentEl : "divFgfzyj"
			},{
				xtype : "portlet",
				height : 120,
				id : "general-portal-zjlyj",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000007'}") ? false : !CUtils.isTrueVal("${entity.tk7approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.ZjlAO')}",
				contentEl : "divZjlyj"
			},{
				xtype : "portlet",
				height : 120,
				id : "general-portal-cwsp",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000008'}") ? false : !CUtils.isTrueVal("${entity.tk8approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.CwAO')}",
				contentEl : "divCwyj"
			},{
				xtype : "portlet",
				height : 140,
				id : "general-portal-wxgcspd",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000009'}") ? false : !CUtils.isTrueVal("${entity.tk9approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.WxgcsPD')}",
				contentEl : "divWxgcspd"
			},{
				xtype : "portlet",
				height : 120,
				id : "general-portal-gcsjsxy",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000010'}") ? false : !CUtils.isTrueVal("${entity.tk10approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.GcsjsXY')}",
				contentEl : "divGcsjsxy"
			},{
				xtype : "portlet",
				height : 360,
				id : "general-portal-yzjlqr",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000011'}") ? false : !CUtils.isTrueVal("${entity.tk11approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.YzjlQR')}",
				contentEl : "divYzjlqr"
			},{
				xtype : "portlet",
				height : 200,
				id : "general-portal-wxgcsqrjs",
				hidden : CUtils.isTrueVal("${taskCode eq 'G000012'}") ? false : !CUtils.isTrueVal("${entity.tk12approve}"),
				title : ss_icon('ss_application_form') + "${f:getText('Com.Wxgcsjsqr')}",
				contentEl : "divWxgcsqrjs"
			},{
				xtype : "portlet",
				height : 120,
				hidden : CUtils.isTrueVal("${taskCode eq 'G000013'}") ? false : !CUtils.isTrueVal("${entity.tk13approve}"),
				id : "general-portal-js-bmjl",
				title : ss_icon('ss_application_form') + "${f:getText('Com.JsBmjlAO')}",
				contentEl : "divJsBmjl"
			},{
				xtype : "portlet",
				height : 120,
				hidden : CUtils.isTrueVal("${taskCode eq 'G000014'}") ? false : !CUtils.isTrueVal("${entity.tk14approve}"),
				id : "general-portal-js-fgfz",
				title : ss_icon('ss_application_form') + "${f:getText('Com.JsFgfzAO')}",
				contentEl : "divJsFgfz"
			},{
				xtype : "portlet",
				height : 120,
				hidden : CUtils.isTrueVal("${taskCode eq 'G000015'}") ? false : !CUtils.isTrueVal("${entity.tk15approve}"),
				id : "general-portal-js-zjl",
				title : ss_icon('ss_application_form') + "${f:getText('Com.JsZjlAO')}",
				contentEl : "divJsZjl"
			});
		}
	}
	
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : bodyItems
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
	CUtils.disableCmp('okBtn', CUtils.isTrueVal("${disabled}"));
	CUtils.disableCmp('applyBtn',CUtils.isTrueVal("${disabled}"));
	if (Strings.isEmpty(CUtils.getSSValue('petrolStation'))) {
		CUtils.disableCmp('asset', true);
	}
	if(CUtils.isTrueVal("${taskCode eq 'G000012'}") && $('adjust').checked){
		CUtils.disableCmp('adjustType',false);
		$('changeAmount').disabled=false;
		$('opinions12').disabled=false;
		CUtils.disableCmp('archive9', false);
	}
}