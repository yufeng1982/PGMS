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
public enum GcCatagory implements IEnum {
	One, Two, Three, Four, Five;

	@Override
	public String getKey() {
		return new StringBuffer().append("GcCatagory.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<GcCatagory> getGcCatagory() {
		return EnumSet.allOf(GcCatagory.class);
	}
}
