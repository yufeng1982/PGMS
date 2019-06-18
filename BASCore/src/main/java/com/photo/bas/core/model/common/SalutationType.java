/**
 * 
 */
package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * @author FengYu
 *
 */
public enum SalutationType implements IEnum {
	Dr, Miss, Mr, Mrs, Ms;

	SalutationType(){}
	
	public static EnumSet<SalutationType> getSalutationTypes() {
		return EnumSet.allOf(SalutationType.class);
	}
	
	@Override
	public String getKey() {
		return new StringBuffer().append("SalutationType.").append(name()).toString();
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
