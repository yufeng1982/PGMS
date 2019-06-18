<div id="divGeneralLeft">
	<t:common type="text" tabindex='20000' path="sequence" id="sequence" key="Com.Code" notNull="true" disabled = "true"/>
	<t:common type="text" tabindex='20001' path="name" id="customerName" key="Com.Name" notNull="true" />
	<t:common type="date" tabindex='20010' path="birthday" id="customerBirthday" key="Com.Birthday" notNull="true" />
</div>

<div id="divGeneralCenter">
	<t:common type="select" tabindex='20020' path="gender" id="customerGender" key="Com.Gender" items="${gender}" itemValue="name" itemLabel="text" notNull="true"/>
	<t:common type="text" tabindex='20030' path="phone" id="customerPhone" key="Com.PhoneCode" notNull="true" />
	<t:common type="text" tabindex='20031' path="screenCount" id="screenCount" key="Com.ScreenCount" disabled = "true" />
</div>

<div id="divGeneralRight">
	<t:common type="checkbox" tabindex='20040' path="vip" id="vip" key="Com.Vip" />
	<t:common type="text" tabindex='20050' path="netWorkContact" id="netWorkContact" key="Com.NetWorkContact" notNull="true"  />
	<t:common type="text" tabindex='20051' path="consumptionAmount" id="consumptionAmount" key="Com.ConsumptionAmount" disabled = "true"  />
</div>

<div id="divAll">
	<t:common type="text" tabindex='20052' path="sinaWeiBoAdr" id="sinaWeiBoAdr" key="Com.SinaWeiBoAdr"  />
	<t:common type="textarea" tabindex='20060' path="description" id="description" key="Com.Description" rows ="5" cols = "114"/>
</div>