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
public enum AdjustType implements IEnum {
	Add, Sub;
	
	@Override
	public String getKey() {
		return new StringBuffer().append("AdjustType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<AdjustType> getAdjustTypes() {
		return EnumSet.allOf(AdjustType.class);
	}
}
