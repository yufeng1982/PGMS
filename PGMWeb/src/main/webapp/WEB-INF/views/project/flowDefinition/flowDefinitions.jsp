<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.Project')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<jsp:directive.include file="/WEB-INF/views/project/flowDefinition/_includes/_flowDefinitionsGrid.jsp" />
	<script type="text/javascript">
		PRes["newEmployee"] = "${f:getText('Com.New')}";
		PRes["VAddFlow"] = "${f:getText('Com.VAddFlow')}";
		function page_OnLoad() {
			var searchConfig = {
				layout : 'hbox',
	            defaults: {
		            margin : '3 15 3 15'
		        },
				items: [{
					fieldLabel: "${f:getText('Com.Code')}",
					xtype : 'textfield',
					labelWidth: 100,
			        width : 230,
			        name: 'sf_LIKE_code',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.Name')}",
					xtype : 'textfield',
					labelWidth: 100,
			        width : 230,
			        name: 'sf_LIKE_name',
			        value : ""
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
			    	var records = PApp.grid.store.getRange();
			    	if (records.length > 0) {
			    		CUtils.warningAlert(PRes["VAddFlow"]);
			    	} else {
			    		PAction.doCreate();
			    	}
			    	
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
			Ext.define('ERP.FlowDefinitionsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showFlowDefinition"
			});
			PAction = new ERP.FlowDefinitionsAction();
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