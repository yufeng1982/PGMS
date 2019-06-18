/**
 * 
 */
package com.photo.pgm.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.service.entity.AbsEntityService;
import com.photo.bas.core.utils.PageInfo;
import com.photo.pgm.core.dao.UserProjectRepository;
import com.photo.pgm.core.model.UserProject;
import com.photo.pgm.core.vo.UserProjectQueryInfo;

/**
 * @author FengYu
 *
 */
@Service
@Transactional(readOnly = true)
public class UserProjectService extends AbsEntityService<UserProject, PageInfo<UserProject>> {

	@Autowired private UserProjectRepository userProjectRepository;
	
	public Page<UserProject> findByUser(final UserProjectQueryInfo queryInfo) {
		if (queryInfo.getSf_EQ_users() == null) return new PageImpl<UserProject>(new ArrayList<UserProject>());
		return getRepository().findAll(byPage(queryInfo), queryInfo);
	}
	
	public List<UserProject> findByUsersAndCorporation(User user, Corporation corporation) {
		return getRepository().findByUsersAndCorporationAndActiveTrue(user, corporation);
	}
	
	@Override
	protected UserProjectRepository getRepository() {
		return userProjectRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

}
