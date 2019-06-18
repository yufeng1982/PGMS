package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;

public enum UnitType implements IEnum {

	AREA, EACH, LENGTH, VOLUME, WEIGHT;
	
	public String getKey() { 
		return new StringBuffer().append("UnitType.").append(name()).toString();
	}

	UnitType() {}
	public String getName() {
		return name();
	}
	
	public static UnitType fromName(String name) {
    	if(Strings.isEmpty(name)) return null;
        return UnitType.valueOf(name);
	}
	
	public static EnumSet<UnitType> getUnitTypes() {
		return EnumSet.allOf(UnitType.class);
	}

	public String toString() {
		return getKey();
	}
	 
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

}
