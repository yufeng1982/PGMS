<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/petrolStation/list/oilJson",
		root : 'data',
		idProperty : 'id',
		modelName : 'PetrolStation',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'project', header:"project", width:100, hidden:true },
           { id : 'projectCode', header : "${f:getText('Com.ProjectCode')}", width : 120, editable : false,
			   renderer : function (value, metaData, record, rowIndex, colIndex, store, view) {
				   return '<a style="text-decoration: none;" href="javascript:showProject(\''+ record.data.project +'\')">'+ value +'</a>';
        	   }   
           },
           { id : 'projectCity', header : "${f:getText('Com.City')}", width : 150, editable : false},
           { id : 'projectAddress', header : "${f:getText('Com.ProjectAddress')}", width : 200, editable : false},
           { id : 'name', header : "${f:getText('Com.ProjectFullName')}", width : 100, editable : false},
           { id : 'shortName', header : "${f:getText('Com.OilShortName')}", width : 100, editable : false},
           { id : 'code', header : "${f:getText('Com.OilEName')}", width : 120, editable : false},
           { id : 'sapCode', header : "${f:getText('Com.SAPCode')}", width : 120, editable : false},
           { id : 'cnCode', header : "${f:getText('Com.CNCode')}", width : 120, editable : false}
        ]
};
function showProject(id) {
	LUtils.showProject({id:id});
}
</script>