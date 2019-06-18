<jsp:directive.include file="/WEB-INF/views/layouts/_header2.jsp" />
<c:set var="winTitle" value="${f:getText('FN.ProjectAnalysis')}"/>

<title>${winTitle}</title>
	<style type="text/css" media="all">
		.fieldRow LABEL {width:140px}
		.fieldRowTextField {width:160px}
		
	</style>
	<script type="text/javascript" src="/js/erp/lib/ui/field/ERPSearchingSelect.js"></script>
	<script type="text/javascript">
	function page_OnLoad() {
		
		var actionBarItems = [];
		actionBarItems[ACTION_BAR_INDEX.ok] = null;
 	    actionBarItems[ACTION_BAR_INDEX.cancel] = null;
 	    actionBarItems[ACTION_BAR_INDEX.apply] = null;
		var actionBar = new ERP.FormActionBar(actionBarItems);
		
		PApp =  new ERP.FormApplication({
			pageLayout : {
				hiddenHeader : true,
				bodyItems : [{
					xtype : "panel",
					height : 240,
					id : "general-portal",
					layout : 'hbox',
					items : [{
						fieldLabel: "${f:getText('Com.Contranct.Project')}",
						xtype : "erpsearchingselect",
						margin : '20 20 20 20',
						config : ${f:sess('PetrolStation')},
						labelWidth: 100,
				        width : 230,
				        id :  'petrolStation',
				        inputId : 'project_input',
				        value : ""
					},{
						xtype: 'button',
						margin : '20 20 20 20',
						iconCls : 'ss_sprite ss_page_white_excel',
			            text : PRes["ExcelBtn"],
			            width : 60,
			            handler: function() {
			            	var jparams ={
				    				projectId : CUtils.getSSValue('petrolStation')
				    		};
			    	    	var viewArgs = {
			       				fileName : 'ProjectAnalysis',
			       				fileType : 'xlsx',
			       				pageSize : 'ISO_A4',
			       				jasperParams : jparams
			    	    	};
			    	    	CUtils.downLoadReportFile(viewArgs);
			            }
					},{
						xtype: 'button',
						margin : '20 20 20 20',
						iconCls : 'ss_sprite ss_page_white_acrobat',
			            text : PRes["PdfBtn"],
			            width : 60,
			            handler: function() {
			            	var jparams ={
				    				projectId : CUtils.getSSValue('petrolStation')
				    		};
			    	    	var viewArgs = {
			       				fileName : 'ProjectAnalysis',
			       				fileType : 'pdf',
			       				pageSize : 'ISO_A4',
			       				jasperParams : jparams
			    	    	};
			    	    	CUtils.downLoadReportFile(viewArgs);
			            }
					}]
				}]
			},
			actionBar : actionBar
		});
		
		Ext.define('EMP.project.ProjectReportAction', {
			extend : 'ERP.FormAction'
		});
		
		PAction = new EMP.project.ProjectReportAction();
	}

	function page_AfterLoad() {
	}
	</script>
</head>
	
<body>
	<form id="form1" action="" method="post" >
		<jsp:directive.include file="/WEB-INF/views/utils/_hiddenUtil.jsp" />
	</form>
	<jsp:directive.include file="/WEB-INF/views/layouts/_footer.jsp" />
</body>
</html>