/**
 * 
 */
package com.photo.pgm.core.vo;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.model.UserProject;

/**
 * @author FengYu
 *
 */
public class UserProjectQueryInfo extends PageInfo<UserProject> {
	
	private User sf_EQ_users;

	public User getSf_EQ_users() {
		return sf_EQ_users;
	}

	public void setSf_EQ_users(User sf_EQ_users) {
		this.sf_EQ_users = sf_EQ_users;
	}
	
}
