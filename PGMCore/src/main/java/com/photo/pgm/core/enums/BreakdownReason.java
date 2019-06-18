/**
 * 
 */
package com.photo.pgm.core.enums;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 * sb : 设备原因
 * sy : 使用不当
 * wh : 维护不当
 * zc : 正常保养
 * wb : 外部原因
 * qt : 其他原因
 */
public enum BreakdownReason implements IEnum {
	sb, sy, wh, zc, wb, qt;

	@Override
	public String getKey() {
		return new StringBuffer().append("BreakdownReason.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<BreakdownReason> getBreakdownReason() {
		return EnumSet.allOf(BreakdownReason.class);
	}
}
