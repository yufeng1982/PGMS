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
public enum ApproveResult implements IEnum {
	Passed, Reject, Archived;
	
	@Override
	public String getKey() {
		return new StringBuffer().append("ApproveResult.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<ApproveResult> getApproveResults() {
		return EnumSet.allOf(ApproveResult.class);
	}
}
