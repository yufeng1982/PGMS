/**
 * 
 */
package com.photo.bas.core.enums;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum EmployeeType implements IEnum {
	Employee, EmployeeTemplate;

	@Override
	public String getKey() {
		return new StringBuffer().append("EmployeeType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<EmployeeType> getEmployeeType() {
		return EnumSet.allOf(EmployeeType.class);
	}
}
