<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.Item')}"/>
	<title>${winTitle}</title>
	<jsp:directive.include file="/WEB-INF/views/product/item/_includes/_itemsGrid.jsp" />
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
				}]
			};
			PApp = new ERP.ListApplication({
				searchConfig : searchConfig
			});
			
			Ext.define('ERP.ItemAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showItem"
			
			});
			PAction = new ERP.ItemAction();
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