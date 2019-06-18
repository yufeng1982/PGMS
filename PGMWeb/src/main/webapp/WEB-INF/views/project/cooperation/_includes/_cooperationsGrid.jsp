<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/cooperation/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'Cooperation',
		isEditable : false,
		isPaging : true,
		isInfinite : false,
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
		   { id : 'code', header : "${f:getText('Com.CooperationCode')}", width : 120, editable : false},
           { id : 'name', header : "${f:getText('Com.CooperationName')}", width : 180, editable : false},
           { id : 'corporate', header : "${f:getText('Com.Corporate')}", width : 120, editable : false},
           { id : 'phone', header : "${f:getText('Com.Phone')}", width : 120, editable : false},
           { id : 'email', header : "${f:getText('Com.Email')}", width : 120, editable : false},
           { id : 'address', header : "${f:getText('Com.CompanyAddress')}", width : 200, editable : false}
        ]
};
</script>