package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

public enum AbcClassificationType implements IEnum {
	A, B, C;

	@Override
	public String getKey() {
		return new StringBuffer().append("AbcClassificationType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<AbcClassificationType> getAbcClassificationType() {
		return EnumSet.allOf(AbcClassificationType.class);
	}
}
