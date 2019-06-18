package com.photo.bas.core.dao.entity;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;

public class UserDAO extends DefaultEntityDAO<User, String> {

	public User findByLoginName(String loginName) {
		return findUniqueByFilters(new PropertyFilter("loginName", loginName, MatchType.EQ),
				PropertyFilter.activeFilter());
	}
}
