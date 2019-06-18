<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />

<title></title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/repairApproveSetup/_includes/_repairApproveSetupsGrid.jsp" />
	<script type="text/javascript">
		function page_OnLoad() {
			PRes["VApproveUser"] = "${f:getText('Com.VApproveUserOrRole')}";
			var actionBarItems = [];
			actionBarItems[1] = null;
			var actionBar = new ERP.ListActionBar(actionBarItems);
			
			var btnSave = new ERP.Button({
				id : 'saveBtn',
			    text: '<strong><font color="red">' + PRes["save"] + '</font></strong>',
			    width : 60,
			    iconCls : 'ss_sprite ss_accept',
			    hidden: CUtils.isTrueVal($F('historical')),
			    handler: function() {
			    	VUtils.removeAllErrorFields();
			    	var msgarray = [];
			    	var records = PApp.grid.store.getRange();
			    	for (var i = 0; i < records.length; i++) {
			    		var record = records[i];
			    		if (Strings.isEmpty(record.get('users')) && Strings.isEmpty(record.get('role'))) {
			    			msgarray.push({fieldname:"", message: PRes["Line"] + (i+1) + ":" + PRes["VApproveUser"], arg : null});
			    		}
			    	}
			    	if (msgarray.length > 0) {
			    		VUtils.processValidateMessages(msgarray);
			    	} else {
			    		$("modifiedRecords").value = GUtils.allRecordsToJson(PApp.grid);
				    	var params = {modifiedRecords:$("modifiedRecords").value};
				    	Ext.Ajax.request({
						    url: '/app/pgm/project/repairApproveSetup/list/save',
						    params : params,
						    timeout: 60000,
						    success: function(response){
						    	PApp.grid.store.load();
						    }
						});
			    	}
			    	
			    }
			});
			
			PApp = new ERP.ListApplication({
				actionBar : actionBar,
				_gDockedItem : {
	                xtype: 'toolbar',
	                items: [btnSave]
	            }
			});
			
			Ext.define('ERP.RepairApproveSetupsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showRepairApproveSetup"
			});
			
			PAction = new ERP.RepairApproveSetupsAction({
				userSelect : function(selectedId) {
					var arg = {};
					arg["cmpId"] = selectedId;
					arg["gridUrl"] = "security/user/_includes/_usersGrid";
					arg["callBack"] = "popupSelectUsers_callBack";
					arg["parameters"] = {};
				    arg["multiple"] = true;
				    arg["y"] = 5,
					LUtils.showPopupSelector(arg);
				},
				popupSelectUsers_callBack : function(id, action, returnVal, arg) {
					if (action == "ok") {
						var record = GUtils.getSelected(Ext.getCmp('GRID_ID'));
						var userIds = "";
						var userTexts = ""
						for (var i = 0; i < returnVal.length; i++) {
							if (i == 0) {
								userIds +=  returnVal[i]['id'];
								userTexts += returnVal[i]['name'];
							} else {
								userIds +=  "," + returnVal[i]['id'];
								userTexts +=   "," + returnVal[i]['name'];
							}
							
						}
						GUtils.setPopupValue(record, 'users', userIds, 'usersText', userTexts);
					}
				},
				roleSelect : function(selectedId) {
					var arg = {};
					arg["cmpId"] = selectedId;
					arg["gridUrl"] = "security/user/_includes/_rolesGrid";
					arg["callBack"] = "popupSelectRoles_callBack";
					arg["parameters"] = {};
					arg["y"] = 5,
					LUtils.showPopupSelector(arg);
				},
				popupSelectRoles_callBack : function(id, action, returnVal, arg) {
					if (action == "ok") {
						var record = GUtils.getSelected(Ext.getCmp('GRID_ID'));
						GUtils.setPopupValue(record, 'role', returnVal[0]['id'], 'roleText', returnVal[0]['name']);
						
					}
				}
			});
		}
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="pageQueryInfo"/> 
	<form:form id="form1" action="" method="post" modelAttribute="${bindModel}">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
		<input type="hidden" id="modifiedRecords" name="modifiedRecords"></input>
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>