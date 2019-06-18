package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

public enum MakeTo implements IEnum {
	MTO,MTS;

	@Override
	public String getKey() {
		return new StringBuffer().append("MakeTo.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	public static EnumSet<MakeTo> getMakeTos() {
		return EnumSet.allOf(MakeTo.class);
	}
}
