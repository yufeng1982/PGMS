package com.photo.bas.core.model.common;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

public enum BusinessPartnerStatus implements IEnum {
	New, Active, Inactive, LeadNew, LeadQualified, LeadConverted, LeadObsolete;
	
	@Override
	public String getKey() {
		return new StringBuffer().append("BusinessPartnerStatus.").append(name()).toString();
	}

	@Override
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static EnumSet<BusinessPartnerStatus> getBusinessPartnerStatus() {
		return EnumSet.allOf(BusinessPartnerStatus.class);
	}
	
	public static List<BusinessPartnerStatus> getVendorStatus() {
		List<BusinessPartnerStatus> statusList = new ArrayList<BusinessPartnerStatus>();
		statusList.add(BusinessPartnerStatus.Active);
		statusList.add(BusinessPartnerStatus.Inactive);
		return statusList;
	}
	
	public static List<BusinessPartnerStatus> getCustomerStatus() {
		List<BusinessPartnerStatus> statusList = new ArrayList<BusinessPartnerStatus>();
		statusList.add(BusinessPartnerStatus.Active);
		statusList.add(BusinessPartnerStatus.Inactive);
		return statusList;
	}
}
