<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.Clothes')}"/>
	<title>${winTitle}</title>
	<jsp:directive.include file="/WEB-INF/views/product/clothes/_includes/_clothessGrid.jsp" />
	<script type="text/javascript">
	
		function page_OnLoad() {
			DEFAULT_PAGE_SIZE = 300;
			var searchConfig = {
				layout : 'hbox',
	            defaults: {
		             margin : '3 15 3 15'
		        },
				items: [{
					fieldLabel: "${f:getText('Com.Name')}",
					xtype : 'textfield',
					labelWidth: 70,
			        width : 200,
			        name: 'sf_LIKE_name',
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
			PApp = new ERP.ListApplication({
				searchConfig : searchConfig
			});
			
			Ext.define('ERP.ClothesAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showClothes",
				getGridSearchPara : function() {
					if(CUtils.getExtObj('sf_EQ_enabled').checked) {
						return {sf_EQ_enabled: true};
					} else {
						return {sf_EQ_enabled: false};
					}
				},
				cleanFields : function() {
					this.callParent(arguments);
					var cmp = Ext.getCmp("sf_EQ_enabled");
					if(cmp){
						cmp.setValue(true);
					}
				}
			
			});
			PAction = new ERP.ClothesAction();
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