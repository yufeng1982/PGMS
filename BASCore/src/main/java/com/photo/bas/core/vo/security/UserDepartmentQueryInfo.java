/**
 * 
 */
package com.photo.bas.core.vo.security;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.security.UserDepartment;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
public class UserDepartmentQueryInfo extends PageInfo<UserDepartment> {

	private User sf_EQ_users;

	public User getSf_EQ_users() {
		return sf_EQ_users;
	}

	public void setSf_EQ_users(User sf_EQ_users) {
		this.sf_EQ_users = sf_EQ_users;
	}

}
