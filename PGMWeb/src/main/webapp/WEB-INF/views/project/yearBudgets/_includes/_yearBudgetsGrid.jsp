<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/yearBudgets/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'YearBudgets',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'years', header : "${f:getText('Com.Years')}", width : 100, editable : false},
		   { id : 'project', header:"project", width:100, hidden:true },
           { id : 'projectCode', header : "${f:getText('Com.ProjectName')}", width : 120, editable : false,
			   renderer : function (value, metaData, record, rowIndex, colIndex, store, view) {
				   return '<a style="text-decoration: none;" href="javascript:showProject(\''+ record.data.project +'\')">'+ value +'</a>';
        	   }   
           },
           { id : 'pabId', header:"pabId", width:100, hidden:true },
           { id : 'approveContents', header : "${f:getText('Com.ApproveContents')}", width : 180, editable : false},
           { id : 'approveNo', header : "${f:getText('Com.PAB.ApproveNo')}", width : 100, editable : false,
        	   renderer : function (value, metaData, record, rowIndex, colIndex, store, view) {
				   return '<a style="text-decoration: none;" href="javascript:showPab(\''+ record.data.pabId +'\')">'+ value +'</a>';
        	   }    
           },
           { id : 'budgetType', header : "${f:getText('Com.BudgetType')}", width : 100, editable : false},
           { id : 'budgets', header : "${f:getText('Com.ApproveAmount')}", width : 150, fieldType:'number', editable : false}
        ]
};
function showProject(id) {
	LUtils.showProject({id:id});
}
function showPab(id) {
	LUtils.showProjectApproveBudget({id:id});
}
</script>