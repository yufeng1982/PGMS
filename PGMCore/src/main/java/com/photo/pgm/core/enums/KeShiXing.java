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
public enum KeShiXing implements IEnum {
	Lh, Yb, Oth;

	@Override
	public String getKey() {
		return new StringBuffer().append("KeShiXing.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<KeShiXing> getKeShiXing() {
		return EnumSet.allOf(KeShiXing.class);
	}
}
