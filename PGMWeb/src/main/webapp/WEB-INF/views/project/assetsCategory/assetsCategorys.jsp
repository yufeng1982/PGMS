<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('Com.AssetsCategory')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:150px}
	</style>
	<script type="text/javascript">
		function page_OnLoad() {
			PRes["AddCategory"] = "${f:getText('Com.AddCategory')}";
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
			btnCreate.text = '<strong><font color="red">' + PRes["AddCategory"] + '</font></strong>';
			btnCreate.width = 90;
			Ext.define('text', {
		        extend: 'Ext.data.Model',
		        fields: [
		            {name: 'id', type: 'string'},
		            {name: 'code', type: 'string'},
		            {name: 'name', type: 'string'},
		            {name: 'description', type: 'string'}
		        ]
			});

		    var store = Ext.create('Ext.data.TreeStore', {
		        model: 'text',
		        proxy: {
		            type: 'ajax',
		            url: '/app/pgm/project/assetsCategory/list/json',
		        },
		        autoLoad : false,
		        clearOnLoad : true,
		    });
		    
		    var treeGrid = Ext.create('Ext.tree.Panel', {
		        collapsible: false,
		        useArrows: true,
		        rootVisible: false,
		        store: store,
		        multiSelect: true,
		        singleExpand: false,
		        columnLines: true,
		        viewConfig:  {
			        stripeRows: true,
			        trackOver: false
			    },
			    height: 500,
		        //the 'columns' property is now 'headers'
		        columns: [{
		            text: 'Id',
		            sortable: true,
		            hidden : true,
		            dataIndex: 'id'
		        },{
		        	xtype: 'treecolumn', //this is so we know which column will show the tree
		        	text: PRes["name"],
		        	flex: 1.2,
		        	sortable: true,
		        	dataIndex: 'name'
		        },{
		        	text: PRes["code"],
		        	flex: 1.5,
		        	sortable: true,
		        	dataIndex: 'code'
		        },{
		            text: PRes["description"],
		            flex: 2,
		            sortable: true,
		            dataIndex: 'description'
		        }],
				dockedItems: [{
					xtype: 'toolbar',
					items: [btnCreate]
				}]
		    });
			
			
			PApp = new ERP.ListApplication({
				actionBar : actionBar,
				searchConfig : searchConfig,
				grid : treeGrid
			});
			Ext.define('ERP.AssetsCategorysAction' ,{
				extend : 'ERP.ListAction',
				launcherFuncName : "showAssetsCategory"
			});
			PAction = new ERP.AssetsCategorysAction({
				gridAutoLoad : false,
				doSearch : function() {
		    	   var searchParams =  this.getSearchParams();
		    	   PApp.grid.store.proxy.extraParams = {};
			       Ext.apply(PApp.grid.store.proxy.extraParams, searchParams);
			       PApp.grid.getStore().load();
			    }
			});
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