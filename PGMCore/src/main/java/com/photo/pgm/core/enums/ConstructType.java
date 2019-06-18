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
public enum ConstructType implements IEnum {
	GJG, WJJG, HNJG, BX;

	@Override
	public String getKey() {
		return new StringBuffer().append("ConstructType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<ConstructType> getConstructType() {
		return EnumSet.allOf(ConstructType.class);
	}
}
