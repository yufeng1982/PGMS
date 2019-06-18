var $j;
function page_OnLoad() {
	
	$j=jQuery.noConflict(); 
	//此处$j就代表JQuery 
	
	PRes["AttachmentName"] = "${f:getText('Com.AttachmentName')}";
	PRes["GiveAttachment"] = "${f:getText('Com.GiveAttachment')}";
	PRes["AttachmentNotNull"] = "${f:getText('Com.AttachmentNotNull')}";
	PRes["GiveAttachmentNotNull"] = "${f:getText('Com.GiveAttachmentNotNull')}";
	PRes["Attachment"] = "${f:getText('Com.Attachment')}";
	PRes["VAttachmentType"] = "${f:getText('Com.Validation.AttachmentType')}";
	PRes["File"] = "${f:getText('Com.File')}";
	PRes["VProvince"] = "${f:getText('Com.Validation.Province')}";
	PRes["VCity"] = "${f:getText('Com.Validation.City')}";
	PRes["VTown"] = "${f:getText('Com.Validation.Town')}";
	PRes["OtherSpc"] = "${f:getText('Com.OtherSpc')}";
	PRes["Yzzbzk"] = "${f:getText('Com.Yzzbzk')}";
	PRes["YG"] = "${f:getText('Com.YG')}";
	PRes["YJ"] = "${f:getText('Com.YJ')}";
	PRes["Ge"] = "${f:getText('Com.Ge')}";
	PRes["Tai"] = "${f:getText('Com.Tai')}";
	PRes["Othersm"] = "${f:getText('Com.Othersm')}";
	PRes["Fstk1"] = "${f:getText('Com.Fstk1')}";
	PRes["Fstk2"] = "${f:getText('Com.Fstk2')}";
	PRes["Fstk3"] = "${f:getText('Com.Fstk3')}";
	PRes["Fstk4"] = "${f:getText('Com.Fstk4')}";
	PRes["Fstk5"] = "${f:getText('Com.Fstk5')}";
	PRes["Fstk6"] = "${f:getText('Com.Fstk6')}";
	PRes["Fstk7"] = "${f:getText('Com.Fstk7')}";
	PRes["Fstk8"] = "${f:getText('Com.Fstk8')}";
	PRes["Phone"] = "${f:getText('Com.Validate.Employee.Phone')}";
	PRes["VNumber"] = "${f:getText('Com.Validate.VNumber')}";
	PRes["VGreaterThanZero"] = "${f:getText('Com.Validation.GreaterThanZero')}";
	PRes["Zjgs"] = "${f:getText('Com.Zjgs')}";
	PRes["SalesForecast"] = "${f:getText('Com.SalesForecast')}";
	PRes["QiyouP"] = "${f:getText('Com.QiyouP')}";
	PRes["CaiyouP"] = "${f:getText('Com.CaiyouP')}";
	PRes["CarRoadCounts"] = "${f:getText('Com.CarRoadCounts')}";
	PRes["Com.FileName1"] = "${f:getText('Com.FileName1')}";
	PRes["Com.FileName2"] = "${f:getText('Com.FileName2')}";
	PRes["Com.FileName3"] = "${f:getText('Com.FileName3')}";
	PRes["Com.FileName4"] = "${f:getText('Com.FileName4')}";
	PRes["Com.FileName5"] = "${f:getText('Com.FileName5')}";
	PRes["Com.FileName6"] = "${f:getText('Com.FileName6')}";
	PRes["Com.FileName7"] = "${f:getText('Com.FileName7')}";
	PAction = new EMP.project.project.ProjectAction();
	
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
	
	Ext.create('Ext.form.field.File', {
		name: 'attachment7',
		id : 'attachment7',
        height : 22,
		width : 420,
        msgTarget: 'side',
        allowBlank: true,
        anchor: '100%',
        renderTo : 'file7',
        margin: '2 0 0 2',
        buttonMargin : '2 0 0 2',
        buttonText: PRes["File"]
	});
	
	var oilLineGrid = PAction.initOilLineGrid();
	var ocLineGrid = PAction.initOilCanLineGrid();
	var omLineGrid = PAction.initOilMachineLineGrid();
	var equipmentLineGrid = PAction.initOtherEquipmentLineGrid();
	
	var oilSummaryPanel = Ext.create('Ext.form.Panel', {
        layout: 'border',
        items:[{
            region: 'north',
            layout: 'fit',
            items  : [oilLineGrid]
            
        },{
            region: 'center',
            contentEl : 'divOilSummary'
        }]
    });
	
	var oilEquipmentPanel = Ext.create('Ext.form.Panel', {
        layout: 'border',
        items:[{
            region: 'center',
            layout: 'border',
            items : [{
			    region: 'north',
			    layout: 'fit',
			    items  : [{
					xtype : 'panel',
					id    : 'oamygPanel',
					title : '<div id="oamyg">'+ PRes["YG"] +'(${entity.yggs}'+ PRes["Ge"] +')</div>',
					items : [ocLineGrid]
			    }]
			},{
			    region: 'center',
			    layout: 'fit',
			    items  : [{
					xtype : 'panel',
					id    : 'oamyjPanel',
					title : '<div id="oamyj">'+ PRes["YJ"] +'(${entity.yjts}'+ PRes["Tai"] +')</div>',
					items : [omLineGrid]
			    }]
			},{
			    region: 'south',
			    layout: 'fit',
			    items  : [{
					xtype : 'panel',
					title : '<div id="othequipment">' + "${f:getText('Com.OtherEquipment')}" + '</div>',
					items : [equipmentLineGrid]
			    }]
			}]
        },{
            region: 'south',
            contentEl : 'divEquipmentSummary'
        }]
    });
	
	var oilElectricAndCommunicationPanel = Ext.create('Ext.form.Panel', {
        layout: 'border',
        items:[{
            region: 'center',
            layout: 'fit',
            contentEl : 'divElectricAndCommunication'
        }]
    });
	
	Ext.create('Ext.form.FieldSet', {
		id : 'oilOhterDesPanelId',
        height : 72,
        renderTo : 'oilOtherDes',
        title  : PRes["Othersm"],
        items : [{
    		xtype     : 'textareafield',
        	grow      : true,
        	height    : 50,
        	width     : '100%',
        	name      : 'othersm',
        	value     : "${entity.othersm}"
		}]
	});
	
	
	Ext.create('Ext.form.FieldSet', {
		id : 'qtsmtkPanelId',
        height : 180,
        renderTo : 'oilOtherSm',
        layout : 'vbox',
        defaults: { 
        	margin: '5 0 5 0'
        },
        items : [{
    		xtype     : 'label',
    		text      : PRes["Fstk1"]
		},{
    		xtype     : 'label',
    		text      : PRes["Fstk2"]
		},{
    		xtype     : 'label',
    		text      : PRes["Fstk3"]
		},{
    		xtype     : 'label',
    		text      : PRes["Fstk4"]
		},{
    		xtype     : 'label',
    		text      : PRes["Fstk5"]
		},{
    		xtype     : 'label',
    		text      : PRes["Fstk6"]
		},{
    		xtype     : 'label',
    		text      : PRes["Fstk7"]
		},{
    		xtype     : 'label',
    		text      : PRes["Fstk8"]
		}]
	});
	
	var actionBarItems = [];
	var actionBar = new ERP.FormActionBar(actionBarItems);
	
	PApp =  new ERP.FormApplication({
		pageLayout : {
			bodyItems : [{
				xtype : "portlet",
				height : 160,
				id : "general-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.General')}",
				contentEl : "divGeneral"
			},{
				xtype : "portlet",
				height : 280,
				id : "oilsummary-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.OilSummary')}",
				items : [oilSummaryPanel]
			},{
				xtype : "portlet",
				height : 635,
				id : "equipmentSummary-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.EquipmentSummary')}",
				items : [oilEquipmentPanel]
			},{
				xtype : "portlet",
				height : 140,
				id : "electricAndCommunication-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.ElectricAndCommunication')}",
				items : [oilElectricAndCommunicationPanel]
			},{
				xtype : "portlet",
				height : 640,
				id : "attachment-portal",
				title : ss_icon('ss_application_form') + "${f:getText('Com.OilAttachment')}",
				contentEl : "divAttachment"
			}]
		},
		actionBar : actionBar
	});
}

function page_AfterLoad() {
	$('name').focus();
	showLocation($('province').value, $('city').value, $('town').value);
}