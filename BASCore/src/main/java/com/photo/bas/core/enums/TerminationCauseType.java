package com.photo.bas.core.enums;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;



public enum TerminationCauseType implements IEnum{
	Dismissed, Resigned, Retired, Deceased;

	@Override
	public String getKey() {
		return new StringBuffer().append("TerminationCauseType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<TerminationCauseType> getTerminationCauses() {
		return EnumSet.allOf(TerminationCauseType.class);
	}
}
