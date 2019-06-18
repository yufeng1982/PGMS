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
public enum DiPing implements IEnum {
	Lh, Yb, Cha, Oth;

	@Override
	public String getKey() {
		return new StringBuffer().append("DiPing.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<DiPing> getDiPing() {
		return EnumSet.allOf(DiPing.class);
	}
}
