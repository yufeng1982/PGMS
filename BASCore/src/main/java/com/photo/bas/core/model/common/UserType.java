/**
 * 
 */
package com.photo.bas.core.model.common;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum UserType implements IEnum {
	Supper, Org, Normal;

	@Override
	public String getKey() {
		return new StringBuffer().append("UserType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
}
