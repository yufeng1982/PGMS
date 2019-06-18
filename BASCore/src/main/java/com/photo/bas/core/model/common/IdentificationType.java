package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

public enum IdentificationType implements IEnum {
	
	IdentityCard,Passport;

	@Override
	public String getKey() {
		return new StringBuffer().append("IndentificationType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	public static EnumSet<IdentificationType> geIdentificationType() {
		return EnumSet.allOf(IdentificationType.class);
	}
}
