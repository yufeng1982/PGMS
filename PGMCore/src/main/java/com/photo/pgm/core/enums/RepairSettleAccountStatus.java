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
public enum RepairSettleAccountStatus implements IEnum {
	Draft, Pending, Approving, Approved, Closed;

	@Override
	public String getKey() {
		return new StringBuffer().append("RepairSettleAccountStatus.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<RepairSettleAccountStatus> getRepairSettleAccountStatus() {
		return EnumSet.allOf(RepairSettleAccountStatus.class);
	}
}
