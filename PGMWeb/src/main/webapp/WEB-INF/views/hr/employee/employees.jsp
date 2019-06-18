<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Employee')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/hr/employee/_includes/_employeesGrid.jsp" />
	<script type="text/javascript">
		PRes["newEmployee"] = "${f:getText('Com.New')}";
		function page_OnLoad() {
			var searchConfig = {
				layout : 'hbox',
	            defaults: {
		            margin : '3 15 3 15'
		        },
				items: [{
					fieldLabel: "${f:getText('Com.EmployeeCode')}",
					xtype : 'textfield',
					labelWidth: 70,
			        width : 200,
			        name: 'sf_LIKE_code',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.Enabled')}",
					xtype : 'checkbox',
					labelWidth: 50,
			        width : 100,
			        id: 'sf_EQ_enabled',
			        name: 'sf_EQ_enabled',
			        checked  : true
                }]
			};
			var actionBarItems = [];
			actionBarItems[1] = null;
			var actionBar = new ERP.ListActionBar(actionBarItems);
			btnCreate = null;
			
			var extBtnCreate = new ERP.Button({
				id : 'newBtn',
				text: '<strong><font color="red">' + PRes["new"] + '</font></strong>',
				iconCls : 'ss_sprite ss_add',
			    handler: function() {
			    	PAction.createParams = {employeeType : 'Employee'};
			    	PAction.doCreate();
			    }
			});
			PApp = new ERP.ListApplication({
				actionBar : actionBar,
				searchConfig : searchConfig,
				_gDockedItem : {
	                xtype: 'toolbar',
	                items: [extBtnCreate]
	            }
			});
			Ext.define('ERP.EmployeesAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showEmployee",
				getGridSearchPara : function() {
					var params = {sf_EQ_employeeType : 'Employee'};
					if(CUtils.getExtObj('sf_EQ_enabled').checked) {
						params.sf_EQ_enabled = true;
					} else {
						params.sf_EQ_enabled = false;
					}
					return params;
				},
				prepearShowParams : function(record){
					return {id : record.get('id'), employeeType : record.get('employeeType')};
			    },
				cleanFields : function() {
					this.callParent(arguments);
					var cmp = Ext.getCmp("sf_EQ_enabled");
					if(cmp){
						cmp.setValue(true);
					}
				}
			});
			PAction = new ERP.EmployeesAction();
		}
	</script>
</head>
	
<body>
	<c:set var="bindModel" value="pageQueryInfo"/> 
	<form:form id="form1" action="" method="post" modelAttribute="${bindModel}">
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
	</form:form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>