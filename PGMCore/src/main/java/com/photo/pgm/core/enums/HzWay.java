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
public enum HzWay implements IEnum {
	Zl, Sg, Jm;

	@Override
	public String getKey() {
		return new StringBuffer().append("HzWay.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<HzWay> getHzWay() {
		return EnumSet.allOf(HzWay.class);
	}
}
