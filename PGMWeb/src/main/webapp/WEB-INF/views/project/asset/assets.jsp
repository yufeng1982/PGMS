<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.PetrolAsset')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<script type="text/javascript" src="/js/erp/lib/ui/field/ERPSearchingSelect.js"></script>
	<jsp:directive.include file="/WEB-INF/views/project/asset/_includes/_assetsGrid.jsp" />
	<script type="text/javascript">
		function page_OnLoad() {
			var searchConfig = {
				layout : 'hbox',
	            defaults: {
		            margin : '3 15 3 15'
		        },
				items: [{
					fieldLabel: "${f:getText('Com.EquipmentCode')}",
					xtype : 'textfield',
					labelWidth: 100,
			        width : 230,
			        name: 'sf_LIKE_code',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.Contranct.Project')}",
					xtype : "erpsearchingselect",
					config : ${f:sess('OilPetrolStation')},
					gridInitParameters : function(){
						return {sf_IN_id : '${ids}'};
					},
					labelWidth: 100,
			        width : 250,
			        id :  'project_filter',
			        name: 'sf_EQ_petrolStation',
			        value : ""
				},{
					fieldLabel: "${f:getText('Com.Department')}",
					xtype : "SelectField",
					//config : ${f:mss('Department')},
					labelWidth: 100,
			        width : 250,
			        id :  'department_filter',
			        name: 'sf_EQ_department',
			        data: ${dpList}
				}]
			};
			
			var actionBarItems = [];
			actionBarItems[1] = null;
			var actionBar = new ERP.ListActionBar(actionBarItems);
			
			
			PApp = new ERP.ListApplication({
				actionBar : actionBar,
				searchConfig : searchConfig,
				_gDockedItem : {
	                xtype: 'toolbar',
	                items: [btnCreate]
	            }
			});
			Ext.define('ERP.AssetsAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showAsset"
			});
			PAction = new ERP.AssetsAction({});
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