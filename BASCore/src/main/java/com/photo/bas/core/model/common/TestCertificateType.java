package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

public enum TestCertificateType implements IEnum {
	NA, N, U, Y;
	
//	NA	Not Applicable
//	N	Test Cert NOT req'd
//	U	Test cert req'd for UM
//	Y	Test Cert Required

	@Override
	public String getKey() {
		return new StringBuffer().append("TestCertificateType.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<TestCertificateType> getTestCertificateTypes() {
		return EnumSet.allOf(TestCertificateType.class);
	}

	public boolean isRequired() {
		return !name().startsWith("N");
	}
}
