/**
 * 
 */
package com.photo.bas.core.vo.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum ReserveStatus implements IEnum {
	Reserved, UnReserved;

	public String getKey() { 
		return new StringBuffer().append("ReserveStatus.").append(name()).toString();
	}
	@Override
	public String getName() {
		return name();
	}
	public static EnumSet<ReserveStatus> getReserveStatusList() {
		return EnumSet.allOf(ReserveStatus.class);
	}

	public String getText() {
		return ResourceUtils.getText(getKey());
	}
}
