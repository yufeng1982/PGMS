package com.photo.bas.core.model.entity;

import org.json.JSONObject;

public class XTypeUtils {

	//source entity type searching select
	public static JSONObject sess(String className){
		
		XType xtype = SourceEntityType.fromName(className);
		
		if(xtype != null){
			return xtype.regXtype();
		}
		
		return new JSONObject();
		
	}
	
	//maintenance type searching select
	public static JSONObject mss(String className){
		
		XType xtype = MaintenanceType.fromName(className);
		
		if(xtype != null){
			return xtype.regXtype();
		}
		
		return new JSONObject();
		
	}

}
