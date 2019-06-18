/**
 * 
 */
package com.photo.bas.core.dao.security;

import org.springframework.data.repository.NoRepositoryBean;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.security.Corporation;

/**
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsCorporationRepository<T extends Corporation> extends AbsCodeNameEntityRepository<T> {

}
