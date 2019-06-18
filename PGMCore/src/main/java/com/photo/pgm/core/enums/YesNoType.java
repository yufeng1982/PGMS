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
public enum YesNoType implements IEnum {
	Yes, No;

	@Override
	public String getKey() {
		return new StringBuffer().append("YesNoType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<YesNoType> getYesNoType() {
		return EnumSet.allOf(YesNoType.class);
	}
}
