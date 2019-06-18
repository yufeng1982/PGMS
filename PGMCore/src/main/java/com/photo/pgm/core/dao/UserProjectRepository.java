/**
 * 
 */
package com.photo.pgm.core.dao;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;
import com.photo.pgm.core.model.UserProject;


/**
 * @author FengYu
 *
 */
public interface UserProjectRepository extends AbsEntityRepository<UserProject> {
	public List<UserProject> findByUsersAndCorporationAndActiveTrue(User user, Corporation corporation);
}
