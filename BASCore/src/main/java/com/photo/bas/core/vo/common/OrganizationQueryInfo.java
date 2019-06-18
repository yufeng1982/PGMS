package com.photo.bas.core.vo.common;

import com.photo.bas.core.model.common.Organization;
import com.photo.bas.core.vo.security.AbsCorporationPageInfo;

public class OrganizationQueryInfo extends AbsCorporationPageInfo<Organization> {
	private String sf_LIKE_note;
	private String sf_LIKE_shortName; 
	private String sf_LIKE_code;
	
	public OrganizationQueryInfo(){
		super();
	}

	public String getSf_LIKE_note() {
		return sf_LIKE_note;
	}

	public void setSf_LIKE_note(String sf_LIKE_note) {
		this.sf_LIKE_note = sf_LIKE_note;
	}

	public String getSf_LIKE_shortName() {
		return sf_LIKE_shortName;
	}

	public void setSf_LIKE_shortName(String sf_LIKE_shortName) {
		this.sf_LIKE_shortName = sf_LIKE_shortName;
	}

	public String getSf_LIKE_code() {
		return sf_LIKE_code;
	}

	public void setSf_LIKE_code(String sf_LIKE_code) {
		this.sf_LIKE_code = sf_LIKE_code;
	}
	
}
