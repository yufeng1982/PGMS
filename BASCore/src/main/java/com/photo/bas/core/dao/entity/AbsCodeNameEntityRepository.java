/**
 * 
 */
package com.photo.bas.core.dao.entity;

import org.springframework.data.repository.NoRepositoryBean;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;

/**
 * @author FengYu
 *
 */
@NoRepositoryBean
public interface AbsCodeNameEntityRepository<T extends AbsCodeNameEntity> extends AbsCodeEntityRepository<T> {
}
