/**
 * 
 */
package com.photo.bas.core.vo.security;

import com.photo.bas.core.model.security.Role;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public class RoleQueryInfo extends PageInfo<Role> {
	
	private Boolean sf_EQ_isAdminRole;
	
	private User sf_EQ_createdBy;

	public Boolean isSf_EQ_isAdminRole() {
		return sf_EQ_isAdminRole;
	}

	public void setSf_EQ_isAdminRole(Boolean sf_EQ_isAdminRole) {
		this.sf_EQ_isAdminRole = sf_EQ_isAdminRole;
	}

	public User getSf_EQ_createdBy() {
		return sf_EQ_createdBy;
	}

	public void setSf_EQ_createdBy(User sf_EQ_createdBy) {
		this.sf_EQ_createdBy = sf_EQ_createdBy;
	}
	
}
