/**
 * 
 */
package com.photo.bas.core.dao.entity;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.photo.bas.core.model.entity.AbsCodeEntity;
import com.photo.bas.core.model.security.Corporation;

/**
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsCodeEntityRepository<T extends AbsCodeEntity> extends AbsEntityRepository<T> {

	public T findByCodeAndActiveTrue(String code);
	
	public T findByCodeAndCorporationAndActiveTrue(String code, Corporation corporation);
	
	public List<T> findAllByActiveTrueOrderByCodeAsc();
	
	public List<T> findAllByCorporationAndActiveTrueOrderByCodeAsc(Corporation corporation);
}
