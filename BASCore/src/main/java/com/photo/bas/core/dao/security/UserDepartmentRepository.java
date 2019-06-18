/**
 * 
 */
package com.photo.bas.core.dao.security;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.model.security.UserDepartment;

/**
 * @author FengYu
 *
 */
public interface UserDepartmentRepository extends AbsEntityRepository<UserDepartment> {

	public List<UserDepartment> findByUsersAndCorporationAndActiveTrue(User user, Corporation corporation);
}
