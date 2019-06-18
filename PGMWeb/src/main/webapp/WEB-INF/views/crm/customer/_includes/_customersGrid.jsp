<jsp:directive.include file="/WEB-INF/views/utils/_taglibUtil.jsp"/>
<script type="text/javascript">
G_CONFIG = {
		url : '/app/core/crm/customer/list/json',
		root : 'data',
		modelName : 'customer',
		idProperty : 'id',
		isEditable : false, // default value is false
		isPaging : false, // default value is true
		isInfinite : true, // deault value is false
		columns : [{id : 'id', hidden: true, header : "id"},
           {id : 'sequence', header : "${f:getText('Com.Code')}", width : 100},
           {id : 'name', header : "${f:getText('Com.Name')}", width : 80},
           {id : 'gender', header : "${f:getText('Com.Gender')}", width : 60},
           {id : 'birthday', header : "${f:getText('Com.Birthday')}", width : 100},
           {id : 'phone', header : "${f:getText('Com.PhoneCode')}", width : 100},
           {id : 'netWorkContact', header : "${f:getText('Com.NetWorkContact')}", width : 120},
           {id : 'vip', header : "${f:getText('Com.Vip')}", width : 35, fieldType : 'checkbox'},
           {id : 'creationDate', header : "${f:getText('Com.CreationDate')}", width : 150},
           {id : 'modificationDate', header : "${f:getText('Com.ModificationDate')}", width : 150}
		]
};
</script>