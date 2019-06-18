var $j;
function page_OnLoad() {
	$j=jQuery.noConflict(); 
	//此处$j就代表JQuery 
	PRes['PROPApply'] = "${f:getText('Com.Apply')}";
	PRes['PROPAdd'] = "${f:getText('Com.Add')}";
	PRes['PROPRemove'] = "${f:getText('Com.Remove')}";
	var actionBarItems = [];
	var addLine = new Ext.Action({
		text : PRes['PROPAdd'],
		iconCls : 'ss_sprite ss_add',
		handler : function() {
			PAction.addLine();
		}
	});
	var removeLine = new Ext.Action({
		id : 'removeLine',
		text : PRes['PROPRemove'],
		iconCls : 'ss_sprite ss_delete',
		handler : function() {
			PAction.removeLine();
		}
	});
	var apply = new Ext.Action({
		text : PRes['PROPApply'],
		iconCls : 'ss_sprite ss_accept',
		handler : function() {
			PAction.actionName = "apply";//BAD CODE
			PAction.submitForm("apply");
		}
	});
	
	
	var actionBar = new ERP.ListActionBar(actionBarItems);
	
	PApp = new ERP.ListApplication({
		searchConfig : null,
		actionBar : actionBar,
		_gDockedItem : {
            xtype: 'toolbar',
            items: [addLine,removeLine,apply]
        }
	});


	PAction = new ERP.OurSideCorporation.OurSideCorporationAction({});
}
function page_AfterLoad() {
	$('pctDiv').style.display = 'block';
}