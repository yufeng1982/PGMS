package com.photo.bas.core.vo.security;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.PageInfo;

public class UserQueryInfo extends PageInfo<User> {

	private String sf_LIKE_loginName;
	private Boolean sf_EQ_enabled = Boolean.TRUE;
	private String sf_EQ_roleId;
	private User sf_EQ_createdBy;
	
	public UserQueryInfo(){
		super();
		setPageSize(300);
	}

	public String getSf_LIKE_loginName() {
		return sf_LIKE_loginName;
	}

	public void setSf_LIKE_loginName(String sf_LIKE_loginName) {
		this.sf_LIKE_loginName = sf_LIKE_loginName;
	}

	public Boolean getSf_EQ_enabled() {
		return sf_EQ_enabled;
	}

	public void setSf_EQ_enabled(Boolean sf_EQ_enabled) {
		this.sf_EQ_enabled = sf_EQ_enabled;
	}

	public String getSf_EQ_roleId() {
		return sf_EQ_roleId;
	}

	public void setSf_EQ_roleId(String sf_EQ_roleId) {
		this.sf_EQ_roleId = sf_EQ_roleId;
	}

	public User getSf_EQ_createdBy() {
		return sf_EQ_createdBy;
	}

	public void setSf_EQ_createdBy(User sf_EQ_createdBy) {
		this.sf_EQ_createdBy = sf_EQ_createdBy;
	}
	
}
