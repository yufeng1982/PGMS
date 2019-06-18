/**
 * @author FengYu
 */
Ext.define('ERP.LauncherUtils' , {
	launchers : {},
	
	addLauncher : function(launcherName, launcher) {
		this.launchers[launcherName] = launcher;
	},
	
	getLauncher : function(launcherName) {
		return this.launchers[launcherName];
	},
	defaultCallback : function(action, returnVal,params, launcher) {
		if (action == "ok" && PApp.grid) {
			PAction.reloadDefaultGrid();
		}
		if(launcher.callBackName) {
			PAction[launcher.callBackName](action, returnVal,params, launcher);
		}
		if (launcher.free) launcher.free();
	},
	removeLauncher : function(launcherName) {
		this.launchers[launcherName] = null;
	},
	
	extractId : function(args) {
		return args && args['id'] && !Strings.isEmpty(args['id']) ? args['id'] : DEFAULT_NEW_ID;
	},
	extractSourceEntityId : function(args) {
		return args && args['ownerShipSourceEntityId'] && !Strings.isEmpty(args['ownerShipSourceEntityId']) ? args['ownerShipSourceEntityId'] : DEFAULT_NEW_ID;
	},
	extractEntityId : function(args) {
		return args && args['entityId'] && !Strings.isEmpty(args['entityId']) ? args['entityId'] : DEFAULT_NEW_ID;
	},
	extractCallback : function(args) {
		return args && args['callBack'] ? args['callBack'] : null;
	},
	resizeToWindow : function(y, x, w, h) {
		window.moveTo(y, x);
		window.resizeTo(w, h);
	},
	buildQueryString : function (args){
		var qs = new QueryString();
		for (var p in args){
			if (!Strings.isEmpty(args[p])){
				qs.setParameter(p, args[p]);
			}
		}
		return qs;
	},
	
	getParentLauncher : function(varName) {
		var parentLauncher = null;
	    if (window.opener) {
	        if (varName && (varName.length > 0)) {
	            try {
	            	var pLauncher = window.opener.LUtils;
	            	parentLauncher = pLauncher.getLauncher(varName);
	            }
	            catch (E) {
	            }
	        }
	    }
	    return parentLauncher;
	},
	
	showPopupSelector : function(args) {
		var appName = $('APP_NAME').value;
		var qs = this.buildQueryString(args);
		
		if (typeof args["searchable"] != "undefined") {
			qs.setParameter("searchable", args["searchable"]);
		} else {
			qs.setParameter("searchable", true);
		}
		if (args["parameters"]) qs.setParameter("parameters", CUtils.jsonEncode(args["parameters"]));
		var url = "/app/"+ appName + "/ui/popup/" + args["cmpId"] + "/show";
		var name = args["varName"] ? args["varName"] : ("PopUpSelect_" + args["cmpId"]);
		var launcher = new ERP.Launcher({
			varName : name,
			url : url,
			arguments : qs,
			maximizedWindow : false,
			top : args["y"] ? args.y : 100,
		    left : args["x"] ? args.x : 450,
		    width : args["w"] ? args.w : 540,
		    height : args["h"] ? args.h : 550,
		    callBack : args["callBack"] ? 
		    			function(action, returnVal, otherParameters) {
		    				otherParameters = otherParameters || {};
		    				args = Ext.apply(args, otherParameters);
		    				args.isPopup = true;
		    				if(PAction[args["callBack"]]) {
		    					PAction[args["callBack"]](args["cmpId"], action, returnVal, args);
		    				} else {
		    					args["callBack"](args["cmpId"], action, returnVal, args); // hack for filter's SS
		    				}
							if (this.free) this.free();
					    }
						: 
					    function (action, returnVal, otherParameters) {
							if (action == "ok" || action == "OK") {
		    					otherParameters = otherParameters || {};
		    					args = Ext.apply(args, otherParameters);
								var isSuccess = true;
								var selectedValue ="";
								var selectedText = "";
								var id = args["cmpId"];
								var popupSelectorCMP = Ext.getCmp(id);
								if(returnVal[0]){
									selectedValue = returnVal[0][args["valueField"]?args["valueField"]:"id"];
									selectedText = returnVal[0][args["displayField"]?args["displayField"]:"name"];
								}
								popupSelectorCMP.originalValue = $('H_' + id).value;
								popupSelectorCMP.originalText = $('H_' + id + '_Text').value;	
								popupSelectorCMP.setSelectValue(selectedValue, selectedText);
								popupSelectorCMP.isPopup = true;
								if (args["onchange"]) {
									isSuccess = PAction[args["onchange"]](selectedValue, selectedText, returnVal[0], popupSelectorCMP);
								}
								if(!isSuccess) {
									popupSelectorCMP.setSelectValue(popupSelectorCMP.originalValue, popupSelectorCMP.originalText);
								}
							}
							if (this.free) this.free();
								
						}
		    
		}).open();
	},
	showUser : function(args) {
		new ERP.Launcher({
			varName : 'userForm',
			url : "/app/" + $F('APP_NAME') + "/pgmuser/form/" + this.extractId(args) + "/show",
			//top : 50,
			//left : 100,
			width : 740,
			height : 610
		}).open();
	},
	showOrganization : function(args) {
		new ERP.Launcher({
			varName : 'organizationForm',
			url : "/app/" + $F('APP_NAME') + "/organization/form/" + this.extractId(args) + "/show",
			width : 740,
			height : 610
		}).open();
	},
	showPhotoOrder : function(args) {
		new ERP.Launcher({
			varName : 'photoOrderForm',
			url : "/app/" + $F('APP_NAME') + "/photo/photoOrder/form/" + this.extractId(args) + "/show",
			width : 740,
			height : 610
		}).open();
	},
	showCustomer : function(args) {
		new ERP.Launcher({
			varName : 'customerForm',
			url : "/app/core/crm/customer/form/" + this.extractId(args) + "/show",
			width : 740,
			height : 610
		}).open();
	},
	showEmployee : function(args) {
		var qs = new QueryString();	
		if(args != null){
			qs.setParameter("employeeType", args['employeeType']);
		}
		new ERP.Launcher({
			varName : 'employeeForm',
			url : "/app/" + $F('APP_NAME') + "/hr/employee/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showSettleAccounts : function(args){
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'settleAccountsForm',
			url : "/app/" + $F('APP_NAME') + "/project/settleAccounts/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showPayAccount : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'payAccountForm',
			url : "/app/" + $F('APP_NAME') + "/project/payAccount/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showProjectApproveBudget : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'projectApproveBudgetForm',
			url : "/app/" + $F('APP_NAME') + "/project/projectApproveBudget/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showPetrolStation : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'PetrolStationForm',
			url : "/app/" + $F('APP_NAME') + "/project/petrolStation/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showContract : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'contractForm',
			url : "/app/" + $F('APP_NAME') + "/project/contract/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showProject : function(args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'projectForm',
			url : "/app/" + $F('APP_NAME') + "/project/project/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 1350,
			height : 610
		}).open();
	},
	showPayItem : function(args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'payItemForm',
			url : "/app/" + $F('APP_NAME') + "/project/payItem/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showFlowDefinition : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'flowDefinitionForm',
			url : "/app/" + $F('APP_NAME') + "/project/flowDefinition/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showCooperation : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'cooperationForm',
			url : "/app/" + $F('APP_NAME') + "/project/cooperation/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showAssetsCategory : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'assetsCategoryForm',
			url : "/app/" + $F('APP_NAME') + "/project/assetsCategory/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showAsset : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'assetForm',
			url : "/app/" + $F('APP_NAME') + "/project/asset/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showYearBudget : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'yearBudgetForm',
			url : "/app/" + $F('APP_NAME') + "/project/yearBudgets/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showRepairOrder : function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'repairOrderForm',
			url : "/app/" + $F('APP_NAME') + "/project/repairOrder/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showRepairSettleAccount: function (args) {
		var qs = new QueryString();	
		new ERP.Launcher({
			varName : 'showRepairSettleAccountForm',
			url : "/app/" + $F('APP_NAME') + "/project/repairSettleAccount/form/" + this.extractId(args) + "/show",
			arguments : qs,
			width : 740,
			height : 610
		}).open();
	},
	showPopupEmployee : function(args) {
		new ERP.Launcher({
			varName : 'employeePopup',
			url : "/app/pgm/hr/employee/list/showPopup",
			width : 740,
			height : 610,
			callBack : args['callBack'] ? args['callBack'] : this.defaultCallback
		}).open();
	},
	showItem : function(args){
		new ERP.Launcher({
			varName : 'itemForm',
			url : "/app/" + $F('APP_NAME') + "/photo/item/form/"+ this.extractId(args) +"/show",
			width : 740,
			height : 610
		}).open();
	},
	showItemPackage : function(args){
		new ERP.Launcher({
			varName : 'itemPackageForm',
			url : "/app/" + $F('APP_NAME') + "/photo/itemPackage/form/"+ this.extractId(args) +"/show",
			width : 740,
			height : 610
		}).open();
	},
	showClothes : function(args){
		new ERP.Launcher({
			varName : 'clothesForm',
			url : "/app/" + $F('APP_NAME') + "/photo/clothes/form/"+ this.extractId(args) +"/show",
			width : 740,
			height : 610
		}).open();
	},
	showDepartment :function(args){
		new ERP.Launcher({
			varName : 'departmentForm',
			url : "/app/" + $F('APP_NAME') + "/department/form/"+ this.extractId(args) +"/show",
			width : 740,
			height : 610
		}).open();
	},
	showRole : function(args) {
		new ERP.Launcher({
			varName : 'roleForm',
			url : "/app/" + $F('APP_NAME') + "/role/form/" + this.extractId(args) + "/show",
			width : 780,
			height : 610
		}).open();
	},
	showSessionControl : function(args) {
		new ERP.Launcher({
			varName : 'contactForm',
			url : "/app/" + $F('APP_NAME') + "/ui/session/form/" + args['currentUser'] + "/show",
			width : 460,
			height : 350,
			callBack : function(action, returnVal) {
				if (action == "ok" || action == "resume") {
					var lng = returnVal.language;
					PAction.changeLanguage(lng);
				}
				if (this.free) this.free();
			}
		}).open();
	},
	showPrintView : function(args) {
		var qs = new QueryString();	
		qs.setParameter("jasperJson", Ext.encode(args));
		qs.setParameter("_FROM_URI__" , $F('_FROM_URI__'));
		new ERP.Launcher({
			varName : 'ReportViewer',
			url : args.url || "/app/" + $F('APP_NAME') + "/report/view",
			arguments : qs,
			width : 900,
			height : 600
		}).open();
	},
	showEmployeeGroupList : function(args) {
		var qs = new QueryString();	
		qs.setParameter("groupId", args.id);
		if(!Strings.isEmpty(args.id)){
			new ERP.Launcher({
				varName : 'EmployeeGroup',
				url : "/app/pgm/hr/employeeGroup/list/show",
				arguments : qs,
				width : 900,
				height : 600
			}).open();
		}else{
			CUtils.warningAlert("Select one group at least!");
		}
	
	},
	showPrintApplet : function(args) {
		var qs = this.buildQueryString(args);	
		var url = "/app/" + $F('APP_NAME') + "/scm/applet/print/show";	
		new ERP.Launcher({
			varName : 'printForm',
			url : url,
			arguments : qs,
			top : 10,
			left : 30,
			width : 400,
			height : 220
		}).open();
	}
});

function popupSelector(arg) {
	alert("Come on, refactor me !!");
}



