<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/cooperationAccount/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'CooperationAccount',
		isEditable : true,
		isPaging : true,
		isInfinite : false,
		autoLoad : true,
		uniqueColumns : [["receiveNo"]],
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'bank', header : "${f:getText('Com.Bank')}", width : 180, editable : true, notNull : true},
           { id : 'receiveName', header : "${f:getText('Com.ReceiveName')}", width : 100, editable : true, notNull : true},
           { id : 'receiveNo', header : "${f:getText('Com.ReceiveNo')}", width : 250, editable : true, notNull : true},
           { id : 'modificationDate', header : "${f:getText('Com.ChangeDate')}", width : 150, editable : false},
           { id : 'filePath', header : "${f:getText('Com.BusinessLicense7')}", fieldType:'browerButton',popupSelectOnClick:'openUploadFile',width : 180, editable : true},
		   { id : 'fileName', header : "${f:getText('Com.DownLoadFile')}", width : 180, editable : false,
        	   renderer : function (value, metaData, record, rowIndex, colIndex, store, view) {
        		   return '<a style="text-decoration: none;" href="/app/pgm/project/cooperation/form/${entityId}/downLoadBankFile?recordId='+record.data.id+'">'+ value +'</a>';
        	   }
		   }
        ]
};
</script>