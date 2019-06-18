package com.photo.bas.core.enums;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;



public enum InactivityCauseType implements IEnum{
	Leave, Course, Maternity;

	@Override
	public String getKey() {
		return new StringBuffer().append("InactivityCauseType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<InactivityCauseType> getInactivityCauses() {
		return EnumSet.allOf(InactivityCauseType.class);
	}
}
