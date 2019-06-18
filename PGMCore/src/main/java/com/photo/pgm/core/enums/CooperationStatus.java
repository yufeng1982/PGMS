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
public enum CooperationStatus implements IEnum {
	New, Update;

	@Override
	public String getKey() {
		return new StringBuffer().append("CooperationStatus.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<CooperationStatus> getCooperationStatus() {
		return EnumSet.allOf(CooperationStatus.class);
	}
}
