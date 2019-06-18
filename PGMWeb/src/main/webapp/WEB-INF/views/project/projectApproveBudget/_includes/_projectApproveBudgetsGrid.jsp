<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/projectApproveBudget/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'ProjectApproveBudget',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'projectId', header:"projectId", width:100, hidden:true },
           { id : 'projectCode', header : "${f:getText('Com.ProjectCode')}", width : 120, editable : false,
			   renderer : function (value, metaData, record, rowIndex, colIndex, store, view) {
				   return '<a style="text-decoration: none;" href="javascript:showProject(\''+ record.data.projectId +'\')">'+ value +'</a>';
        	   }   
           },
           { id : 'projectName', header : "${f:getText('Com.ProjectName')}", width : 120, editable : false},
           { id : 'amount', header : "${f:getText('Com.PAB.Amount')}", width : 120, fieldType:'number', editable : false},
           { id : 'oilLevel', header : "${f:getText('Com.PAB.OilLevel')}", width : 120, editable : false},
           { id : 'approveLevel', header : "${f:getText('Com.PAB.ApproveLevel')}", width : 100, editable : false},
           { id : 'code', header : "${f:getText('Com.PAB.ApproveNo')}", width : 100, editable : false},
           { id : 'approveDate', header : "${f:getText('Com.PAB.ApproveDate')}", width : 120, editable : false},
           { id : 'fileName', header : "${f:getText('Com.PAB.FileName')}", width : 200, editable : false,
        	   renderer : function (value, metaData, record, rowIndex, colIndex, store, view) {
				   return '<a style="text-decoration: none;" href="/app/pgm/project/projectApproveBudget/form/' +record.data.id+'/downLoad">'+ value +'</a>';
        	   }  
           }
        ]
};
function showProject(id) {
	LUtils.showProject({id:id});
}
</script>