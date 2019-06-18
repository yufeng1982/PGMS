package com.photo.bas.core.enums;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;



public enum EmployeeClass implements IEnum {
	HourlyWorker, PieceWorkWorker, SalariedMonthly, SalariedTwiceMonthly, SalariedWeekly;

	public String getKey() {
		return new StringBuffer().append("EmployeeClass.").append(name()).toString();
	}
	
	public String getName() {
		return name();
	}

	public static EnumSet<EmployeeClass> getEmployeeClasses() {
		return EnumSet.allOf(EmployeeClass.class);
	}

	public String getText() {
		return ResourceUtils.getText(getKey());
	}
}
