<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.Customer')}"/>
	<title>${winTitle}</title>
	<jsp:directive.include file="/WEB-INF/views/crm/customer/_includes/_customersGrid.jsp" />
	<script type="text/javascript">
	
		function page_OnLoad() {
			DEFAULT_PAGE_SIZE = 300;
			var searchConfig = {
				layout : 'hbox',
	            defaults: {
		             margin : '3 15 3 15'
		        },
				items: [{
					fieldLabel: "${f:getText('Com.Code')}",
					xtype : 'textfield',
					labelWidth: 70,
			        width : 200,
			        name: 'sf_LIKE_sequence',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.Name')}",
					xtype : 'textfield',
					labelWidth: 70,
			        width : 200,
			        name: 'sf_LIKE_name',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.PhoneCode')}",
					xtype : 'textfield',
					labelWidth: 70,
			        width : 200,
			        name: 'sf_EQ_phone',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.Vip')}",
					xtype : 'checkboxfield',
					id : 'sf_EQ_vip',
					labelWidth: 70,
			        width : 200,
			        name: 'sf_EQ_vip',
			        checked : true
				}]
			};
			var actionBarItems = [];
			actionBarItems[1] = null;
			var actionBar = new ERP.ListActionBar(actionBarItems);
			PApp = new ERP.ListApplication({
				searchConfig : searchConfig,
				actionBar : actionBar
			});
			
			Ext.define('ERP.CustomersAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showCustomer",
				getGridSearchPara : function () {
					return {sf_EQ_vip : Ext.getCmp('sf_EQ_vip').getValue()};
				}
			});
			PAction = new ERP.CustomersAction();
		}
	</script>
</head>
<body>
<c:set var="bindModel" value="pageQueryInfo"/>
<form id="form1" action="" method="post" modelAttribute="${bindModel}">
	<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
</form>
<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>