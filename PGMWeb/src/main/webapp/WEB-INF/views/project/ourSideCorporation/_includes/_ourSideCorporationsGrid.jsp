<%@ taglib prefix="f" uri="functions" %>
<script type="text/javascript">
G_CONFIG = {
		url : "/app/pgm/project/ourSideCorporation/list/json",
		root : 'data',
		idProperty : 'id',
		modelName : 'OurSideCorporation',
		isEditable : true,
		isPaging : true,
		isInfinite : false,
		uniqueColumns : [['code'],['name']],
		columns : [
		   { id : 'id', header:"id", width:100, hidden:true },
           { id : 'code', header : "${f:getText('Com.Code')}", width : 120, editable : true, notNull : true},
           { id : 'name', header : "${f:getText('Com.Name')}", width : 120, editable : true, notNull : true},
           { id : 'pct', header : "${f:getText('Com.City')}", width : 180, fieldType: 'browerButton', editable : true, popupSelectOnClick:'showPct', notNull : true},
           { id : 'description', header : "${f:getText('Com.Description')}", width : 180, editable : true},
           { id : 'province', header : "province", width : 120, hidden:true},
           { id : 'city', header : "city", width : 120, hidden:true},
           { id : 'town', header : "town", width : 120, hidden:true}
        ]
};
</script>