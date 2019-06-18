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
public enum RepairStatus implements IEnum {
	Draft, Pending, Approving, Approved, Repairing, Repaired, Closed, Adjusting,Confirm;

	@Override
	public String getKey() {
		return new StringBuffer().append("RepairStatus.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<RepairStatus> getRepairStatus() {
		return EnumSet.allOf(RepairStatus.class);
	}
}
