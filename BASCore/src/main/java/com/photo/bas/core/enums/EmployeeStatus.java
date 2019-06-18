package com.photo.bas.core.enums;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;



public enum EmployeeStatus implements IEnum {
	New, Active, Inactive;

	@Override
	public String getKey() {
		return new StringBuffer().append("EmployeeStatus.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<EmployeeStatus> getEmployeeStatuses() {
		return EnumSet.allOf(EmployeeStatus.class);
	}
}
