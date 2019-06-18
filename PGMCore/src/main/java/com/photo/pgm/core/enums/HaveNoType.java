/**
 * 
 */
package com.photo.pgm.core.enums;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum HaveNoType implements IEnum {
	Have, No;

	@Override
	public String getKey() {
		return new StringBuffer().append("HaveNoType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<HaveNoType> getHaveNoType() {
		return EnumSet.allOf(HaveNoType.class);
	}
}
