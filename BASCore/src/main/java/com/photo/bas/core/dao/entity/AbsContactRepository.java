/**
 * 
 */
package com.photo.bas.core.dao.entity;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.photo.bas.core.model.entity.AbsContactEntity;

/**
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsContactRepository<T extends AbsContactEntity> extends AbsCodeNameEntityRepository<T> {
	public List<T> findBySourceOwnerIdAndActiveTrue(String ownerId);
}
