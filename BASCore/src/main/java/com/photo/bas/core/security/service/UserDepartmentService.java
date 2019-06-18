/**
 * 
 */
package com.photo.bas.core.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.dao.security.UserDepartmentRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.security.UserDepartment;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.vo.security.UserDepartmentQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class UserDepartmentService extends AbsEntityService<UserDepartment, PageInfo<UserDepartment>> {

	@Autowired private UserDepartmentRepository userDepartmentRepository;
	
	public Page<UserDepartment> findByUser(final UserDepartmentQueryInfo queryInfo) {
		if (queryInfo.getSf_EQ_users() == null) return new PageImpl<UserDepartment>(new ArrayList<UserDepartment>());
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	public List<UserDepartment> findByUsersAndCorporation(User user, Corporation corporation) {
		return getRepository().findByUsersAndCorporationAndActiveTrue(user, corporation);
	}
	
	@Override
	protected UserDepartmentRepository getRepository() {
		return userDepartmentRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
