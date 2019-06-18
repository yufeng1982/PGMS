package com.photo.bas.core.vo.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

public enum YesNoOption implements IEnum {
	Yes, No;

	public String getKey() { 
		return new StringBuffer().append("Com.").append(name()).toString();
	}
	@Override
	public String getName() {
		return name();
	}
	public static EnumSet<YesNoOption> getYesNoList() {
		return EnumSet.allOf(YesNoOption.class);
	}

	public String getText() {
		return ResourceUtils.getText(getKey());
	}
}
