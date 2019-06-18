/**
 * 
 */
package com.photo.pgm.core.dao;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.security.Corporation;
import com.photo.pgm.core.model.Project;

/**
 * @author FengYu
 *
 */
public interface ProjectRepository extends AbsCodeNameEntityRepository<Project> {

	public List<Project> findByCodeContainingAndCorporation(String code, Corporation corporation);
}
