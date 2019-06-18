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
public enum GongNuan implements IEnum {
	Szgn, Zygl, Kt, Oth;

	@Override
	public String getKey() {
		return new StringBuffer().append("GongNuan.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<GongNuan> getGongNuan() {
		return EnumSet.allOf(GongNuan.class);
	}
}
