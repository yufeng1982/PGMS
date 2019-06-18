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
public enum Gender implements IEnum{
	Male, Female;

	@Override
	public String getKey() {
		return new StringBuffer().append("Gender.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}

	public static EnumSet<Gender> getGenders() {
		return EnumSet.allOf(Gender.class);
	}
}
