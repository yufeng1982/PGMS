/**
 * 
 */
package com.photo.bas.core.dao.security;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.security.Role;

/**
 * @author FengYu
 *
 */
public interface RoleRepository extends AbsCodeNameEntityRepository<Role> {

	public Iterable<Role> findAllByFunctionNodeIdsLike(String fnId);
}
