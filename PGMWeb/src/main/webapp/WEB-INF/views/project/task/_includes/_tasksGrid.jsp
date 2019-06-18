<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
var url;
if (CUtils.isTrueVal('${isHistory}')) {
	url = "/app/pgm/project/todotask/list/historyJson";
} else {
	url = "/app/pgm/project/todotask/list/json";
}
G_CONFIG = {
		url : url,
		root : 'data',
		idProperty : 'id',
		modelName : 'Task',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'taskId', header:"taskId", width:150, hidden:true },
		   { id : 'entityId', header:"entityId", width:150, hidden:true },
           { id : 'taskName', header : "${f:getText('Com.TaskName')}", width : 150, editable : false},
           { id : 'entityCode', header : "${f:getText('Com.Contranct.Code')}", width : 180, editable : false,
        	   renderer : function (value, metaData, record) {
        		   return "<a href=\"javascript:showContract('" + record.get('entityId') + "')\" style=\"text-decoration: none;\"> "+value+"</a>";
        	   }   
           },
           { id : 'entityName', header : "${f:getText('Com.Contranct.Name')}", width : 150, editable : false},
           { id : 'projectName', header : "${f:getText('Com.Contranct.Project')}", width : 150, editable : false},
           { id : 'piId', header : "${f:getText('Com.ProcessInsId')}", width : 150, editable : false},
           { id : 'status', header : "${f:getText('Com.Mark')}", width : 150, editable : false},
           { id : 'completeTime', header : "${f:getText('Com.CompleteTime')}", width : 150, editable : false}, 
        ]
};
</script>