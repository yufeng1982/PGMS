/**
 * 
 */
package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum MaritalStatus implements IEnum {
	Single, Married, Engaged, Divorced, Widowed, Separated;

	@Override
	public String getKey() {
		return new StringBuffer().append("MaritalStatus.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<MaritalStatus> getMaritalStatus() {
		return EnumSet.allOf(MaritalStatus.class);
	}

}
